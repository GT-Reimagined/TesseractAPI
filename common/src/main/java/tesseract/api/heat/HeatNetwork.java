package tesseract.api.heat;

import tesseract.graph.IRouteTracker;
import tesseract.graph.standard.StandardNetwork;

public class HeatNetwork extends StandardNetwork<HeatNetwork, IHeatPipe, IHeatNode, HeatRoutingInfo, HeatGrid> {
    @Override
    protected IRouteTracker<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatNetwork, HeatGrid> createRouteTracker() {
        return new HeatRouteTracker();
    }
}
