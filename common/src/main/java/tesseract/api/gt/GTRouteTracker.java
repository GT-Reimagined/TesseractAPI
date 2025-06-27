package tesseract.api.gt;

import it.unimi.dsi.fastutil.Pair;
import tesseract.graph.IPath;
import tesseract.graph.standard.StandardRouteTracker;

public class GTRouteTracker extends StandardRouteTracker<GTRoutingInfo, IGTNode, IGTCable, GTNetwork, GTGrid> {
    @Override
    public IPath<GTRoutingInfo, IGTNode, IGTCable, GTNetwork, GTGrid> createPath(Pair<IGTNode, GTRoutingInfo> pair) {
        return new GTPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IPath<GTRoutingInfo, IGTNode, IGTCable, GTNetwork, GTGrid> a, IPath<GTRoutingInfo, IGTNode, IGTCable, GTNetwork, GTGrid> b) {
        if (a.getRoutingInfo().actualLoss() > b.getRoutingInfo().actualLoss()) return 1;
        else if (a.getRoutingInfo().actualLoss() < b.getRoutingInfo().actualLoss()) return -1;
        return a.getRoutingInfo().path().size() > b.getRoutingInfo().path().size() ? 1 : -1;
    }

    @Override
    public Class<IGTNode> getNotableElementClass() {
        return IGTNode.class;
    }
}
