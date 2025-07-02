package org.gtreimagined.tesseract.api.hu;


import org.gtreimagined.tesseract.api.IConnectable;
import org.gtreimagined.tesseract.graph.IElement;

public interface IHUPipe extends IElement<IHUPipe, IHUNode, HURoutingInfo, HUNetwork, HUGrid>, IConnectable {

    /**
     * Returns the heat coefficient of this heat pipes material, q = -k*delta => k
     * @return the value.
     */
    int temperatureCoefficient();
}
