package tesseract.api.gt;

import tesseract.graph.standard.StandardGrid;

public class GTGrid extends StandardGrid<GTGrid, IGTCable, IGTNode, GTRoutingInfo, GTNetwork> {
    public static final GTGrid INSTANCE = new GTGrid();

    @Override
    protected GTNetwork createNetwork() {
        return new GTNetwork();
    }

    public void tick(){
        for (GTNetwork network : networks ) {
            network.tick();
        }
    }
}
