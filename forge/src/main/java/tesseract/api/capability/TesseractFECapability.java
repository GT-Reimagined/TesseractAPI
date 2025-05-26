package tesseract.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import tesseract.api.fe.IFECable;
import tesseract.api.fe.IFENode;
import tesseract.api.fe.FETransaction;
import tesseract.forge.TesseractForgeGraphWrappers;
import tesseract.graph.Graph;
import tesseract.util.Pos;

public class TesseractFECapability<T extends BlockEntity & IFECable> extends TesseractBaseCapability<T> implements IFENode {
    private FETransaction old;
    public TesseractFECapability(T tile, Direction side, boolean isNode, ITransactionModifier callback) {
        super(tile, side, isNode, callback);
    }

    @Override
    public boolean canReceive(Direction direction) {
        return true;
    }

    @Override
    public boolean canExtract(Direction direction) {
        return true;
    }

    @Override
    public int receiveEnergy(int maxAmount, boolean simulate) {
        if (this.isSending) return 0;
        this.isSending = true;
        if (!simulate) {
            if (old == null) return 0;
            old.commit();
        } else {
            long pos = tile.getBlockPos().asLong();
            FETransaction transaction = new FETransaction(maxAmount, a -> {});
            if (!this.isNode) {
                TesseractForgeGraphWrappers.RF.getController(tile.getLevel(), pos).insert(pos, side, transaction, callback);
            } else {
                transferAroundPipe(transaction, pos);
            }
            this.old = transaction;
        }
        this.isSending = false;
        return maxAmount - old.rf;
    }

    private void transferAroundPipe(FETransaction transaction, long pos) {
        for (Direction dir : Graph.DIRECTIONS) {
            if (dir == this.side || !this.tile.connects(dir)) continue;
            BlockEntity otherTile = tile.getLevel().getBlockEntity(BlockPos.of(Pos.offset(pos, dir)));
            if (otherTile != null) {
                int rf = transaction.rf;
                if (this.callback.modify(rf, dir, false, true) || this.callback.modify(rf, side, true, true)) continue;
                //Check the handler.
                var cap = otherTile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite());
                if (!cap.isPresent()) continue;
                //Perform insertion, and add to the transaction.
                var handler = cap.resolve().get();
                long amount = handler.receiveEnergy(rf,  true);
                if (amount > 0) {
                    transaction.addData(rf, a -> {
                        if (this.callback.modify(a, dir, false, true) || this.callback.modify(a, side, true, true)) return;
                        handler.receiveEnergy(a, false);
                    });
                }
                if (transaction.rf == 0) break;
            }
        }
    }

    @Override
    public int extractEnergy(int maxAmount, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public int maxInsert() {
        return 0;
    }

    @Override
    public int maxExtract() {
        return 0;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public boolean canExtract() {
        return true;
    }
}
