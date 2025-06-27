package tesseract.api.heat;

import tesseract.graph.IPath;

public record HeatPath(IHeatNode node, HeatRoutingInfo routingInfo) implements IPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatNetwork, HeatGrid> {
    @Override
    public IHeatNode getDestination() {
        return node;
    }

    @Override
    public HeatRoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
