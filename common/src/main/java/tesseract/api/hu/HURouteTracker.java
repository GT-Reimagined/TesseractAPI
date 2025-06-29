package tesseract.api.hu;

import tesseract.graph.RoutedNode;
import tesseract.graph.standard.StandardRouteTracker;

public class HURouteTracker extends StandardRouteTracker<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> {
    @Override
    public int sort(RoutedNode<IHUNode, HURoutingInfo> a, RoutedNode<IHUNode, HURoutingInfo> b) {
        return 0;
    }

    @Override
    public Class<IHUNode> getNotableElementClass() {
        return IHUNode.class;
    }
}
