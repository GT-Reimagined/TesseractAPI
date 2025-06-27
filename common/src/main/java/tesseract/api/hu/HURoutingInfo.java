package tesseract.api.hu;

import tesseract.graph.IRoutingInfo;

public record HURoutingInfo() implements IRoutingInfo<HURoutingInfo> {
    @Override
    public HURoutingInfo merge(HURoutingInfo other) {
        return null;
    }
}
