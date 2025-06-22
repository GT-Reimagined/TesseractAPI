package tesseract.api.gt;

import it.unimi.dsi.fastutil.Pair;
import tesseract.TesseractCapUtils;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

import java.util.List;
import java.util.Optional;

public class GTFactoryRouteTracker extends StandardFactoryRouteTracker<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> {
    @Override
    public IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> createPath(Pair<IGTNodeBlock, GTRoutingInfo> pair) {
        return new GTFactoryPath(pair.first(), pair.second());
    }

    public long insertEU(IGTNodeBlock source, long eu, boolean simulate){
        long inserted = 0;
        for (IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> path : getPaths(source)) {
            if (path.getDestination().getBlockEntity() != null){
                Optional<IEnergyHandler> handler = TesseractCapUtils.INSTANCE.getEnergyHandler(path.getDestination().getBlockEntity(), path.getRoutingInfo().side());
                long finalEu = eu;
                long insert = handler.map(h -> h.insertEu(finalEu, simulate)).orElse(0L);
                eu -= insert;
                inserted += insert;
            }
        }
        return inserted;
    }

    @Override
    public int sort(IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> a, IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> b) {
        return a.getRoutingInfo().actualLoss() > b.getRoutingInfo().actualLoss() ? 1 : -1;
    }
}
