package tesseract.api.heat;


import tesseract.api.IConnectable;
import tesseract.graph.IElement;

public interface IHeatPipe extends IElement<IHeatPipe, IHeatNode, HeatRoutingInfo, HeatNetwork, HeatGrid>, IConnectable {

    /**
     * Returns the heat coefficient of this heat pipes material, q = -k*delta => k
     * @return the value.
     */
    int temperatureCoefficient();
}
