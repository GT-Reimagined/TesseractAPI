package org.gtreimagined.tesseract.api.fe;

import org.gtreimagined.tesseract.graph.IRouteTracker;
import org.gtreimagined.tesseract.graph.standard.StandardNetwork;

public class FENetwork extends StandardNetwork<FENetwork, IFECable, IFENode, FERoutingInfo, FEGrid> {
    @Override
    protected IRouteTracker<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> createRouteTracker() {
        return new FERouteTracker();
    }
}
