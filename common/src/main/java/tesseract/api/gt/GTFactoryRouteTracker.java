package tesseract.api.gt;

import it.unimi.dsi.fastutil.Pair;
import tesseract.TesseractCapUtils;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

import java.util.Optional;

public class GTFactoryRouteTracker extends StandardFactoryRouteTracker<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> {
    @Override
    public IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> createPath(Pair<IGTNode, GTRoutingInfo> pair) {
        return new GTFactoryPath(pair.first(), pair.second());
    }

    public long insertEU(IGTNode source, long eu, boolean simulate){
        long inserted = 0;
        for (IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> path : getPaths(source)) {
            if (path.getDestination().getBlockEntity() != null){
                Optional<IEnergyHandler> handler = TesseractCapUtils.INSTANCE.getEnergyHandler(path.getDestination().getBlockEntity(), path.getRoutingInfo().side());
                long finalEu = Math.max(0, eu - path.getRoutingInfo().roundedLoss());
                long insert = handler.map(h -> h.insertEu(finalEu, simulate)).orElse(0L);
                if (insert > 0){
                    long used = insert + path.getRoutingInfo().roundedLoss();
                    eu -= used;
                    inserted += used;
                    path.getRoutingInfo().path().forEach(c -> {
                        if (!simulate){
                            GTHolder.add(c.getHolder(), 1);
                            if (c.getVoltage() < used){
                                c.onCableOverVoltage(c.getBlockEntity().getLevel(), c.getBlockEntity().getBlockPos().asLong(), used);
                            }
                            if (GTHolder.isOverAmperage(c.getHolder())){
                                c.onCableOverAmperage(c.getBlockEntity().getLevel(), c.getBlockEntity().getBlockPos().asLong(), 1);
                            }
                        }
                    });
                }
            }
        }
        return inserted;
    }

    @Override
    public int sort(IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> a, IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> b) {
        return a.getRoutingInfo().actualLoss() > b.getRoutingInfo().actualLoss() ? 1 : -1;
    }
}
