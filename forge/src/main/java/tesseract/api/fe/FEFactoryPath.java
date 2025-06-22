package tesseract.api.fe;

import tesseract.factory.IFactoryPath;

public record FEFactoryPath(IFENode node, FERoutingInfo routingInfo) implements IFactoryPath<FERoutingInfo, IFENode, IFECable, FEFactoryNetwork, FEFactoryGrid> {
    @Override
    public IFENode getDestination() {
        return node;
    }

    @Override
    public FERoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
