package tesseract.api.gt;

import it.unimi.dsi.fastutil.Pair;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

import java.util.List;

public class GTFactoryRouteTracker extends StandardFactoryRouteTracker<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> {
    @Override
    public IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> createPath(Pair<IGTNodeBlock, GTRoutingInfo> pair, List<IGTCable> igtCables) {
        return new GTFactoryPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> a, IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> b) {
        return 0;
    }
}
