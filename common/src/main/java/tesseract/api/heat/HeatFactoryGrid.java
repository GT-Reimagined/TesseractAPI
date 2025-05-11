package tesseract.api.heat;

import tesseract.factory.standard.StandardFactoryGrid;

public class HeatFactoryGrid extends StandardFactoryGrid<HeatFactoryGrid, IHeatPipe, IHeatNodeBlock, HeatRoutingInfo, HeatFactoryNetwork> {
    public static final HeatFactoryGrid INSTANCE = new HeatFactoryGrid();
    @Override
    protected HeatFactoryNetwork createNetwork() {
        return new HeatFactoryNetwork();
    }
}
