package tesseract.api.eu;

import tesseract.graph.RoutedNode;
import tesseract.graph.standard.StandardRouteTracker;

public class EURouteTracker extends StandardRouteTracker<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> {
    @Override
    public int sort(RoutedNode<IEUNode, EURoutingInfo> a, RoutedNode<IEUNode, EURoutingInfo> b) {
        if (a.routeInfo().actualLoss() > b.routeInfo().actualLoss()) return 1;
        else if (a.routeInfo().actualLoss() < b.routeInfo().actualLoss()) return -1;
        return a.routeInfo().path().size() > b.routeInfo().path().size() ? 1 : -1;
    }

    @Override
    public Class<IEUNode> getNotableElementClass() {
        return IEUNode.class;
    }
}
