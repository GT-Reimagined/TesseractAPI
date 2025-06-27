package tesseract.api.heat;

import tesseract.graph.IRoutingInfo;

public record HeatRoutingInfo() implements IRoutingInfo<HeatRoutingInfo> {
    @Override
    public HeatRoutingInfo merge(HeatRoutingInfo other) {
        return null;
    }
}
