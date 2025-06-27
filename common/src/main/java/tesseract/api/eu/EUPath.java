package tesseract.api.eu;

import tesseract.graph.IPath;

public record EUPath(IEUNode node, EURoutingInfo routingInfo) implements IPath<EURoutingInfo, IEUNode, IEUCable, EUNetwork, EUGrid> {
    @Override
    public IEUNode getDestination() {
        return node;
    }

    @Override
    public EURoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
