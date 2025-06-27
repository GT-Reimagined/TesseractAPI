package tesseract.api.fe;

import tesseract.graph.IRoutingInfo;

public record FERoutingInfo() implements IRoutingInfo<FERoutingInfo> {
    @Override
    public FERoutingInfo merge(FERoutingInfo other) {
        return null;
    }
}
