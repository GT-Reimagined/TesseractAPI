package tesseract.api.heat;

import tesseract.api.INode;

public interface IHeatNode extends INode<IHeatNode, HeatRoutingInfo, IHeatPipe, HeatNetwork, HeatGrid>, IHeatPipe {

    @Override
    default int temperatureCoefficient(){
        return 0;
    }
}
