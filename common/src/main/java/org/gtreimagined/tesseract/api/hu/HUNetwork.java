package org.gtreimagined.tesseract.api.hu;

import org.gtreimagined.tesseract.graph.IRouteTracker;
import org.gtreimagined.tesseract.graph.standard.StandardNetwork;

public class HUNetwork extends StandardNetwork<HUNetwork, IHUPipe, IHUNode, HURoutingInfo, HUGrid> {
    @Override
    protected IRouteTracker<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> createRouteTracker() {
        return new HURouteTracker();
    }
}
