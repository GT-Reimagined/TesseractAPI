package tesseract.api.hu;

import tesseract.graph.IRouteTracker;
import tesseract.graph.standard.StandardNetwork;

public class HUNetwork extends StandardNetwork<HUNetwork, IHUPipe, IHUNode, HURoutingInfo, HUGrid> {
    @Override
    protected IRouteTracker<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> createRouteTracker() {
        return new HURouteTracker();
    }
}
