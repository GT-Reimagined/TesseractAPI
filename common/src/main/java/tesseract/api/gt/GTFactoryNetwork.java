package tesseract.api.gt;

import tesseract.factory.IRouteTracker;
import tesseract.factory.standard.StandardFactoryNetwork;

public class GTFactoryNetwork extends StandardFactoryNetwork<GTFactoryNetwork, IGTCable, IGTNode, GTRoutingInfo, GTFactoryGrid> {
    @Override
    protected IRouteTracker<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> createRouteTracker() {
        return new GTFactoryRouteTracker();
    }
}
