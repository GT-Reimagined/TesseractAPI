package tesseract.api.gt;

import tesseract.graph.IPath;

public record GTPath(IGTNode node, GTRoutingInfo routingInfo) implements IPath<GTRoutingInfo, IGTNode, IGTCable, GTNetwork, GTGrid> {
    @Override
    public IGTNode getDestination() {
        return node;
    }

    @Override
    public GTRoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
