package tesseract.api.heat;

import tesseract.factory.IRoutingInfo;

public record HeatRoutingInfo() implements IRoutingInfo<HeatRoutingInfo> {
    @Override
    public HeatRoutingInfo merge(HeatRoutingInfo other) {
        return null;
    }
}
