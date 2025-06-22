package tesseract.api.fe;

import tesseract.factory.IRoutingInfo;

public record FERoutingInfo() implements IRoutingInfo<FERoutingInfo> {
    @Override
    public FERoutingInfo merge(FERoutingInfo other) {
        return null;
    }
}
