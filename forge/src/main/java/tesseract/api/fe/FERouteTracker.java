package tesseract.api.fe;

import tesseract.graph.RoutedNode;
import tesseract.graph.standard.StandardRouteTracker;

public class FERouteTracker extends StandardRouteTracker<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> {
    @Override
    public int sort(RoutedNode<IFENode, FERoutingInfo> a, RoutedNode<IFENode, FERoutingInfo> b) {
        return 0;
    }

    @Override
    public Class<IFENode> getNotableElementClass() {
        return IFENode.class;
    }
}
