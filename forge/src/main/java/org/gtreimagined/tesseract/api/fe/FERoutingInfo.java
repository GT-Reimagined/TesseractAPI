package org.gtreimagined.tesseract.api.fe;

import org.gtreimagined.tesseract.graph.IRoutingInfo;

public record FERoutingInfo() implements IRoutingInfo<FERoutingInfo> {
    @Override
    public FERoutingInfo merge(FERoutingInfo other) {
        return new FERoutingInfo();
    }
}
