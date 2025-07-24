package org.gtreimagined.tesseract.api.fe;

import org.gtreimagined.tesseract.api.IConnectable;
import org.gtreimagined.tesseract.graph.IElement;

/**
 * A flux cable is the unit of interaction with electric inventories.
 */
public interface IFECable extends IElement<IFECable, IFENode, FERoutingInfo, FENetwork, FEGrid>, IConnectable {

    /**
     * Returns the maximum amount of energy that this item component will permit to pass through or be received in a single tick.
     *
     * @return A positive integer representing the maximum packets, zero or negative indicates that this component accepts no energy.
     */
    long getCapacity();
}