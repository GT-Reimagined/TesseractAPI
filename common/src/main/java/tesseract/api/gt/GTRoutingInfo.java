package tesseract.api.gt;

import tesseract.factory.IRoutingInfo;

public record GTRoutingInfo() implements IRoutingInfo<GTRoutingInfo> {
    @Override
    public GTRoutingInfo merge(GTRoutingInfo other) {
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
