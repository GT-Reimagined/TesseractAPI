package org.gtreimagined.tesseract.api.eu;

import org.gtreimagined.tesseract.graph.standard.StandardGrid;

public class EUGrid extends StandardGrid<EUGrid, IEUCable, IEUNode, EURoutingInfo, EUNetwork> {
    public static final EUGrid INSTANCE = new EUGrid();

    @Override
    protected EUNetwork createNetwork() {
        return new EUNetwork();
    }

    @Override
    public void tick() {
        super.tick();
        for (IEUCable cable : vertices){
            cable.setHolder(EUHolder.create(cable, 0));
        }
    }
}
