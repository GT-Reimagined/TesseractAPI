package tesseract.api.eu;

import tesseract.graph.standard.StandardGrid;

public class EUGrid extends StandardGrid<EUGrid, IEUCable, IEUNode, EURoutingInfo, EUNetwork> {
    public static final EUGrid INSTANCE = new EUGrid();

    @Override
    protected EUNetwork createNetwork() {
        return new EUNetwork();
    }

    public void tick(){
        for (EUNetwork network : networks ) {
            network.tick();
        }
    }
}
