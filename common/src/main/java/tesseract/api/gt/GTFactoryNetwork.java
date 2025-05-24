package tesseract.api.gt;

import tesseract.factory.IRouteTracker;
import tesseract.factory.standard.StandardFactoryNetwork;

public class GTFactoryNetwork extends StandardFactoryNetwork<GTFactoryNetwork, IGTCable, IGTNodeBlock, GTRoutingInfo, GTFactoryGrid> {
    @Override
    protected IRouteTracker<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> createRouteTracker() {
        return new GTFactoryRouteTracker();
    }
}
