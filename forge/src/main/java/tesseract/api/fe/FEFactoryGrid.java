package tesseract.api.fe;

import tesseract.factory.standard.StandardFactoryGrid;

public class FEFactoryGrid extends StandardFactoryGrid<FEFactoryGrid, IFECable, IFENode, FERoutingInfo, FEFactoryNetwork> {
    public static final FEFactoryGrid INSTANCE = new FEFactoryGrid();

    @Override
    protected FEFactoryNetwork createNetwork() {
        return new FEFactoryNetwork();
    }
}
