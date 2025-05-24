package tesseract.api.gt;

import tesseract.factory.IFactoryPath;

import java.util.List;

public record GTFactoryPath(IGTNodeBlock node, GTRoutingInfo routingInfo) implements IFactoryPath<GTRoutingInfo, IGTNodeBlock, IGTCable, GTFactoryNetwork, GTFactoryGrid> {
    @Override
    public IGTNodeBlock getDestination() {
        return node;
    }

    @Override
    public GTRoutingInfo getRoutingInfo() {
        return routingInfo;
    }

    @Override
    public List<IGTCable> getPath() {
        return List.of();
    }
}
