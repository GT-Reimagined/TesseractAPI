package tesseract.api.gt;

import tesseract.factory.standard.StandardFactoryGrid;

public class GTFactoryGrid extends StandardFactoryGrid<GTFactoryGrid, IGTCable, IGTNode, GTRoutingInfo, GTFactoryNetwork> {
    public static final GTFactoryGrid INSTANCE = new GTFactoryGrid();

    @Override
    protected GTFactoryNetwork createNetwork() {
        return new GTFactoryNetwork();
    }

    public void tick(){
        for (GTFactoryNetwork network : networks ) {
            network.tick();
        }
    }
}
