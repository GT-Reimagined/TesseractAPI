package tesseract.api.heat;

import tesseract.factory.IRoutingInfo;

public record HeatRoutingInfo() implements IRoutingInfo<HeatRoutingInfo> {
    @Override
    public HeatRoutingInfo merge(HeatRoutingInfo other) {
        return null;
    }

    @Override
    public boolean canSend() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
