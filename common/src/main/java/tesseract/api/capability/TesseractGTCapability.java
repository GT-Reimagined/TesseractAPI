package tesseract.api.capability;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import tesseract.TesseractCapUtils;
import tesseract.api.gt.*;
import tesseract.factory.IFactoryPath;
import tesseract.util.Pos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class TesseractGTCapability<T extends BlockEntity & IGTCable> extends TesseractBaseCapability<T> implements IEnergyHandler {

    private final IGTCable cable;
    private GTTransaction old;

    public TesseractGTCapability(T tile, Direction dir, boolean isNode, ITransactionModifier modifier) {
        super(tile, dir, isNode, modifier);
        this.cable = tile;
    }

    @Override
    public long insertEu(long voltage, boolean simulate) {
        if (this.isSending || (!simulate && old == null)) return 0;
        this.isSending = true;
        BlockEntity neighbor = this.tile.getLevel().getBlockEntity(this.tile.getBlockPos().relative(this.side));
        if (neighbor instanceof IGTNode node){
            if (!simulate) {
                old.commit();
            } else {
                long pos = tile.getBlockPos().asLong();
                GTTransaction transaction = new GTTransaction(voltage, t -> {});
                if (!this.isNode) {
                    insert(transaction, node);
                } else {
                    transferAroundPipe(transaction, pos);
                }
                this.old = transaction;
            }
            this.isSending = false;
            return voltage - old.eu;
        }
        return 0;
    }

    private void insert(GTTransaction stack, IGTNode node){
        double previousLoss = 0;
        List<Consumer<Set<IGTCable>>> transferList = new ArrayList<>();
        for (IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> path : tile.getNetwork().getTracker().getPaths(node)) {
            if (path.getDestination().getBlockEntity() != null){
                long remainingEu = stack.eu;
                if (remainingEu <= 0) break;
                double loss = path.getRoutingInfo().actualLoss();
                double appliedLoss = loss == 0 ? 0 : loss > previousLoss ? loss - previousLoss : previousLoss - loss;
                previousLoss = loss;
                long roundedAppliedLoss = Math.round(appliedLoss);
                if (roundedAppliedLoss < 0 || roundedAppliedLoss > remainingEu) {
                    continue;
                }
                long lossyEu = remainingEu - roundedAppliedLoss;
                Optional<IEnergyHandler> handler = TesseractCapUtils.INSTANCE.getEnergyHandler(path.getDestination().getBlockEntity(), path.getRoutingInfo().side());
                long euInserted = handler.map(h -> h.insertEu(lossyEu, true)).orElse(0L);
                if (euInserted <= 0) continue;
                GTTransaction.TransferData data1 = stack.addData(euInserted, euInserted + roundedAppliedLoss, appliedLoss, a -> {});
                transferList.add((l) -> dataCommit(l, path.getRoutingInfo(), handler.get(), data1));
            }
        }
        if (!transferList.isEmpty()){
            stack.addData(0, 0, 0, d-> dataCommit(transferList));
        }
    }

    public void dataCommit(Set<IGTCable> cableList, GTRoutingInfo routingInfo, IEnergyHandler handler, GTTransaction.TransferData data){
        if (routingInfo.maxVoltage() < data.getVoltage()) {
            for (IGTCable c : routingInfo.path()) {
                if (Objects.requireNonNull(c.getHandler(data.getVoltage(), 0)) == GTStatus.FAIL_VOLTAGE) {
                    c.onCableOverVoltage(c.getBlockEntity().getLevel(), c.getBlockEntity().getBlockPos().asLong(), data.getVoltage());
                    return;
                }
            }
        } else {
            cableList.addAll(routingInfo.path());
        }
        handler.insertEu(data.getEu(), false);
    }

    public void dataCommit(List<Consumer<Set<IGTCable>>> list){
        Set<IGTCable> cableList = new HashSet<>();
        for (var pair : list) {
            pair.accept(cableList);
        }
        for (IGTCable c : cableList) {
            c.setHolder(GTHolder.add(c.getHolder(), 1));
            if (GTHolder.isOverAmperage(c.getHolder())) {
                c.onCableOverAmperage(c.getBlockEntity().getLevel(), c.getBlockEntity().getBlockPos().asLong(), GTHolder.getAmperage(c.getHolder()));
                return;
            }
        }
    }

    @Override
    public long extractEu(long voltage, boolean simulate) {
        return 0;
    }

    private void transferAroundPipe(GTTransaction transaction, long pos) {
        boolean hasInserted = false;
        boolean lossAdded = false;
        for (Direction dir : Direction.values()) {
            if (dir == this.side || !this.tile.connects(dir)) continue;
            //First, perform cover modifications.
            BlockEntity otherTile = tile.getLevel().getBlockEntity(BlockPos.of(Pos.offset(pos, dir)));
            if (otherTile != null) {
                //Check the handler.
                var cap = TesseractCapUtils.INSTANCE.getEnergyHandler(otherTile, dir.getOpposite());
                if (cap.isEmpty()) continue;
                //Perform insertion, and add to the transaction.
                var handler = cap.get();
                long loss = Math.round(cable.getLoss());
                if (hasInserted && !lossAdded){
                    transaction.addData(0, loss, 0, d -> {});
                    lossAdded = true;
                }

                long remainingEu = lossAdded ? transaction.eu : transaction.eu - loss;
                GTTransaction.TransferData data = new GTTransaction.TransferData(transaction, remainingEu, transaction.voltage).setLoss(cable.getLoss());
                if (this.callback.modify(data, dir, false, true) || this.callback.modify(data, side, true, true)){
                    continue;
                }
                if (data.getEu() < remainingEu) remainingEu = data.getEu();
                if (data.getLoss() > 0) remainingEu -= Math.round(data.getLoss());
                if (remainingEu <= 0) return;
                long inserted = handler.insertEu(remainingEu, true);
                if (inserted > 0){
                    transaction.addData(inserted, inserted, cable.getLoss(), t -> {
                        if (this.callback.modify(t, dir, false, false) || this.callback.modify(data, side, true, false)){
                            return;
                        }
                        handler.insertEu(t.getEu(), false);
                    });
                    if (transaction.voltage > this.cable.getVoltage()){
                        this.cable.onCableOverVoltage(tile.getLevel(), pos, transaction.voltage);
                    }
                }
                if (transaction.eu == 0) break;
            }
        }
    }

    @Override
    public long getEnergy() {
        return 0;
    }

    @Override
    public long getCapacity() {
        return 0;
    }

    @Override
    public long availableAmpsInput(long voltage) {
        return Long.MAX_VALUE;
    }

    @Override
    public long availableAmpsOutput() {
        return Long.MAX_VALUE;
    }

    @Override
    public long getOutputAmperage() {
        return Long.MAX_VALUE;
    }

    @Override
    public long getOutputVoltage() {
        return cable.getVoltage();
    }

    @Override
    public long getInputAmperage() {
        return Long.MAX_VALUE;
    }

    @Override
    public long getInputVoltage() {
        return cable.getVoltage();
    }

    @Override
    public boolean canOutput() {
        return true;
    }

    @Override
    public boolean canInput() {
        return true;
    }

    @Override
    public boolean canInput(Direction direction) {
        return true;
    }

    @Override
    public boolean canOutput(Direction direction) {
        return true;
    }

    @Override
    public GTState getState() {
        return new GTState(this);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag) {
        return null;
    }

    @Override
    public void deserialize(CompoundTag nbt) {

    }
}
