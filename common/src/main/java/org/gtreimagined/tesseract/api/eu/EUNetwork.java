package org.gtreimagined.tesseract.api.eu;

import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.tesseract.TesseractCapUtils;
import org.gtreimagined.tesseract.graph.IRouteTracker;
import org.gtreimagined.tesseract.graph.standard.StandardNetwork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class EUNetwork extends StandardNetwork<EUNetwork, IEUCable, IEUNode, EURoutingInfo, EUGrid> {
    public final Object2ObjectMap<ResourceLocation, LongSet> cableIsActive = new Object2ObjectLinkedOpenHashMap<>();

    protected EUNetwork() {
        super(IEUCable.class, IEUNode.class);
    }

    @Override
    protected IRouteTracker<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> createRouteTracker() {
        return new EURouteTracker();
    }

    public void insert(EUTransaction stack, IEUNode node){
        double previousLoss = 0;
        List<Consumer<Set<IEUCable>>> transferList = new ArrayList<>();
        for (var path : this.getTracker().getPaths(node)) {
            path.element().getBlockEntity();
            long remainingEu = stack.eu;
            if (remainingEu <= 0) break;
            double loss = path.routeInfo().actualLoss();
            double appliedLoss = loss == 0 ? 0 : loss > previousLoss ? loss - previousLoss : previousLoss - loss;
            previousLoss = loss;
            long roundedAppliedLoss = Math.round(appliedLoss);
            if (roundedAppliedLoss < 0 || roundedAppliedLoss > remainingEu) {
                continue;
            }
            long lossyEu = remainingEu - roundedAppliedLoss;
            Optional<IEnergyHandler> handler = TesseractCapUtils.INSTANCE.getEnergyHandler(path.element().getBlockEntity(), path.routeInfo().side());
            long euInserted = handler.map(h -> h.insertEu(lossyEu, true)).orElse(0L);
            if (euInserted <= 0) continue;
            EUTransaction.TransferData data1 = stack.addData(euInserted, euInserted + roundedAppliedLoss, appliedLoss, a -> {});
            transferList.add((l) -> dataCommit(l, path.routeInfo(), handler.get(), data1));
        }
        if (!transferList.isEmpty()){
            stack.addData(0, 0, 0, d-> dataCommit(transferList));
        }
    }

    public void dataCommit(Set<IEUCable> cableList, EURoutingInfo routingInfo, IEnergyHandler handler, EUTransaction.TransferData data){
        if (routingInfo.maxVoltage() < data.getVoltage()) {
            for (IEUCable c : routingInfo.path()) {
                if (Objects.requireNonNull(c.getHandler(data.getVoltage(), 0)) == EUStatus.FAIL_VOLTAGE) {
                    if (c.getBlockEntity().getLevel() == null) return;
                    c.onCableOverVoltage(c.getBlockEntity().getLevel(), c.getBlockEntity().getBlockPos().asLong(), data.getVoltage());
                    return;
                }
            }
        } else {
            cableList.addAll(routingInfo.path());
        }
        handler.insertEu(data.getEu(), false);
    }

    public void dataCommit(List<Consumer<Set<IEUCable>>> list){
        Set<IEUCable> cableList = new HashSet<>();
        for (var pair : list) {
            pair.accept(cableList);
        }
        for (IEUCable c : cableList) {
            c.setHolder(EUHolder.add(c.getHolder(), 1));
            if (EUHolder.isOverAmperage(c.getHolder())) {
                if (c.getBlockEntity().getLevel() == null) return;
                c.onCableOverAmperage(c.getBlockEntity().getLevel(), c.getBlockEntity().getBlockPos().asLong(), EUHolder.getAmperage(c.getHolder()));
                return;
            }
        }
    }
}
