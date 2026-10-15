package org.gtreimagined.tesseract.api.fe;

import org.gtreimagined.tesseract.graph.standard.StandardGrid;

public class FEGrid extends StandardGrid<FEGrid, IFECable, IFENode, FERoutingInfo, FENetwork> {
    public static final FEGrid INSTANCE = new FEGrid();

    @Override
    protected FENetwork createNetwork() {
        return new FENetwork();
    }
}
