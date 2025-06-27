package tesseract.api.heat;

import tesseract.graph.standard.StandardGrid;

public class HeatGrid extends StandardGrid<HeatGrid, IHeatPipe, IHeatNode, HeatRoutingInfo, HeatNetwork> {
    public static final HeatGrid INSTANCE = new HeatGrid();
    @Override
    protected HeatNetwork createNetwork() {
        return new HeatNetwork();
    }
}
