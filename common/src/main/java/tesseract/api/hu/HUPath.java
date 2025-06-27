package tesseract.api.hu;

import tesseract.graph.IPath;

public record HUPath(IHUNode node, HURoutingInfo routingInfo) implements IPath<HURoutingInfo, IHUNode, IHUPipe, HUNetwork, HUGrid> {
    @Override
    public IHUNode getDestination() {
        return node;
    }

    @Override
    public HURoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
