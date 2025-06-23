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

    @Override
    public int sort(IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> a, IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> b) {
        if (a.getRoutingInfo().actualLoss() > b.getRoutingInfo().actualLoss()) return 1;
        else if (a.getRoutingInfo().actualLoss() < b.getRoutingInfo().actualLoss()) return -1;
        return a.getRoutingInfo().path().size() > b.getRoutingInfo().path().size() ? 1 : -1;
    }

    @Override
    public Class<IGTNode> getNotableElementClass() {
        return IGTNode.class;
    }
}
