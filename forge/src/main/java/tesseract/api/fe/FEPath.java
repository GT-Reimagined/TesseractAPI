package tesseract.api.fe;

import tesseract.graph.IPath;

public record FEPath(IFENode node, FERoutingInfo routingInfo) implements IPath<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> {
    @Override
    public IFENode getDestination() {
        return node;
    }

    @Override
    public FERoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
