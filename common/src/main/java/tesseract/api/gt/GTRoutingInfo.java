package tesseract.api.gt;

import tesseract.factory.IRoutingInfo;

public record GTRoutingInfo(long maxAmps, long maxVoltage, double actualLoss, long roundedLoss) implements IRoutingInfo<GTRoutingInfo> {
    @Override
    public GTRoutingInfo merge(GTRoutingInfo other) {
        long maxAmps = Math.min(this.maxAmps, other.maxAmps);
        long maxVoltage = Math.min(this.maxVoltage, other.maxVoltage);
        double actualLoss = this.actualLoss + other.actualLoss;
        long roundedLoss = Math.round(actualLoss);
        return new GTRoutingInfo(maxAmps, maxVoltage, actualLoss, roundedLoss);
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
