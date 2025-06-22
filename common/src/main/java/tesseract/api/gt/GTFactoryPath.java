package tesseract.api.gt;

import tesseract.factory.IFactoryPath;

public record GTFactoryPath(IGTNode node, GTRoutingInfo routingInfo) implements IFactoryPath<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> {
    @Override
    public IGTNode getDestination() {
        return node;
    }

    @Override
    public GTRoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
