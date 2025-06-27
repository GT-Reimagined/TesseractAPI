package tesseract.api.hu;

import it.unimi.dsi.fastutil.Pair;
import tesseract.graph.IPath;
import tesseract.graph.standard.StandardRouteTracker;

public class HURouteTracker extends StandardRouteTracker<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> {
    @Override
    public IPath<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> createPath(Pair<IHUNode, HURoutingInfo> pair) {
        return new HUPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IPath<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> a, IPath<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> b) {
        return 0;
    }

    @Override
    public Class<IHUNode> getNotableElementClass() {
        return IHUNode.class;
    }
}
