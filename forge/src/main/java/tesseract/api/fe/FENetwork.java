package tesseract.api.fe;

import tesseract.graph.IRouteTracker;
import tesseract.graph.standard.StandardNetwork;

public class FENetwork extends StandardNetwork<FENetwork, IFECable, IFENode, FERoutingInfo, FEGrid> {
    @Override
    protected IRouteTracker<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> createRouteTracker() {
        return new FERouteTracker();
    }
}
