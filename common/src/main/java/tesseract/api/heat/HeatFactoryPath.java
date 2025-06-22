package tesseract.api.heat;

import tesseract.factory.IFactoryPath;

public record HeatFactoryPath(IHeatNode node, HeatRoutingInfo routingInfo) implements IFactoryPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> {
    @Override
    public IHeatNode getDestination() {
        return node;
    }

    @Override
    public HeatRoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
