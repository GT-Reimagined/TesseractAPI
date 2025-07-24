package org.gtreimagined.tesseract.api.hu;

import org.gtreimagined.tesseract.graph.IRoutingInfo;

public record HURoutingInfo() implements IRoutingInfo<HURoutingInfo> {
    @Override
    public HURoutingInfo merge(HURoutingInfo other) {
        return null;
    }
}
