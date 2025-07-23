package org.gtreimagined.tesseract.api.hu;

import org.gtreimagined.tesseract.graph.standard.StandardGrid;

public class HUGrid extends StandardGrid<HUGrid, IHUPipe, IHUNode, HURoutingInfo, HUNetwork> {
    public static final HUGrid INSTANCE = new HUGrid();
    @Override
    protected HUNetwork createNetwork() {
        return new HUNetwork();
    }
}
