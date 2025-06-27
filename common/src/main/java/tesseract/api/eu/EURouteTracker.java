package tesseract.api.eu;

import it.unimi.dsi.fastutil.Pair;
import tesseract.graph.IPath;
import tesseract.graph.standard.StandardRouteTracker;

public class EURouteTracker extends StandardRouteTracker<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> {
    @Override
    public IPath<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> createPath(Pair<IEUNode, EURoutingInfo> pair) {
        return new EUPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IPath<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> a, IPath<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> b) {
        if (a.getRoutingInfo().actualLoss() > b.getRoutingInfo().actualLoss()) return 1;
        else if (a.getRoutingInfo().actualLoss() < b.getRoutingInfo().actualLoss()) return -1;
        return a.getRoutingInfo().path().size() > b.getRoutingInfo().path().size() ? 1 : -1;
    }

    @Override
    public Class<IEUNode> getNotableElementClass() {
        return IEUNode.class;
    }
}
