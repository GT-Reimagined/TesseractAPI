package tesseract.api.heat;

import tesseract.api.INode;

public interface IHeatNodeBlock extends INode<IHeatNodeBlock, HeatRoutingInfo, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid>, IHeatPipe {

    @Override
    default int temperatureCoefficient(){
        return 0;
    }
}
