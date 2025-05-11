package tesseract.api.heat;


import tesseract.api.IConnectable;

public interface IHeatPipe extends IConnectable<IHeatPipe, IHeatNodeBlock, HeatRoutingInfo, HeatFactoryNetwork, HeatFactoryGrid> {

    /**
     * Returns the heat coefficient of this heat pipes material, q = -k*delta => k
     * @return the value.
     */
    int temperatureCoefficient();
}
