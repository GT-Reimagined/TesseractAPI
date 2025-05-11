package tesseract.api.gt;

import tesseract.factory.standard.StandardFactoryGrid;

public class GTFactoryGrid extends StandardFactoryGrid<GTFactoryGrid, IGTCable, IGTNodeBlock, GTRoutingInfo, GTFactoryNetwork> {
    public static final GTFactoryGrid INSTANCE = new GTFactoryGrid();

    @Override
    protected GTFactoryNetwork createNetwork() {
        return new GTFactoryNetwork();
    }
}
