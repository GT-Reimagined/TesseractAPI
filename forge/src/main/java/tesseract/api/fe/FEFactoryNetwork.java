package tesseract.api.fe;

import tesseract.factory.IRouteTracker;
import tesseract.factory.standard.StandardFactoryNetwork;

public class FEFactoryNetwork extends StandardFactoryNetwork<FEFactoryNetwork, IFECable, IFENode, FERoutingInfo, FEFactoryGrid> {
    @Override
    protected IRouteTracker<FERoutingInfo, IFENode, IFECable, FEFactoryNetwork, FEFactoryGrid> createRouteTracker() {
        return new FEFactoryRouteTracker();
    }
}
