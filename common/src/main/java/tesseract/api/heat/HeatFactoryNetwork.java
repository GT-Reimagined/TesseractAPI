package tesseract.api.heat;

import tesseract.factory.IRouteTracker;
import tesseract.factory.standard.StandardFactoryNetwork;

public class HeatFactoryNetwork extends StandardFactoryNetwork<HeatFactoryNetwork, IHeatPipe, IHeatNode, HeatRoutingInfo, HeatFactoryGrid> {
    @Override
    protected IRouteTracker<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> createRouteTracker() {
        return new HeatFactoryRouteTracker();
    }
}
