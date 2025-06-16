package tesseract.api.fe;

import tesseract.factory.IFactoryPath;

import java.util.List;

public record FEFactoryPath(IFENodeBlock node, FERoutingInfo routingInfo) implements IFactoryPath<FERoutingInfo, IFENodeBlock, IFECable, FEFactoryNetwork, FEFactoryGrid> {
    @Override
    public IFENodeBlock getDestination() {
        return node;
    }

    @Override
    public FERoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
