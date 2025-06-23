package tesseract.api.gt;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import tesseract.TesseractCapUtils;
import tesseract.factory.IFactoryPath;
import tesseract.factory.IRouteTracker;
import tesseract.factory.standard.StandardFactoryNetwork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class GTFactoryNetwork extends StandardFactoryNetwork<GTFactoryNetwork, IGTCable, IGTNode, GTRoutingInfo, GTFactoryGrid> {
    public final Object2ObjectMap<ResourceLocation, LongSet> cableIsActive = new Object2ObjectLinkedOpenHashMap<>();
    @Override
    protected IRouteTracker<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> createRouteTracker() {
        return new GTFactoryRouteTracker();
    }

    public void insert(GTTransaction stack, IGTNode node){
        double previousLoss = 0;
        List<Consumer<Set<IGTCable>>> transferList = new ArrayList<>();
        for (IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> path : this.getTracker().getPaths(node)) {
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

}
