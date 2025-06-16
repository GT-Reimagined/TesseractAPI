package tesseract.api.heat;

import tesseract.factory.IFactoryPath;

import java.util.List;

public record HeatFactoryPath(IHeatNodeBlock node, HeatRoutingInfo routingInfo) implements IFactoryPath<HeatRoutingInfo, IHeatNodeBlock, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> {
    @Override
    public IHeatNodeBlock getDestination() {
        return node;
    }

    @Override
    public HeatRoutingInfo getRoutingInfo() {
        return routingInfo;
    }
}
