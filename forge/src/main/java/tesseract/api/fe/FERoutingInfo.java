package tesseract.api.fe;

import tesseract.factory.IRoutingInfo;

public record FERoutingInfo() implements IRoutingInfo<FERoutingInfo> {
    @Override
    public FERoutingInfo merge(FERoutingInfo other) {
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
