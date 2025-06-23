package tesseract.api.heat;


import tesseract.api.IConnectable;
import tesseract.factory.IFactoryElement;

public interface IHeatPipe extends IFactoryElement<IHeatPipe, IHeatNode, HeatRoutingInfo, HeatFactoryNetwork, HeatFactoryGrid>, IConnectable {

    /**
     * Returns the heat coefficient of this heat pipes material, q = -k*delta => k
     * @return the value.
     */
    int temperatureCoefficient();
}
