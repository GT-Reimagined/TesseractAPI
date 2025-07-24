package org.gtreimagined.tesseract.api.hu;

import org.gtreimagined.tesseract.api.INode;

public interface IHUNode extends INode<IHUNode, HURoutingInfo, IHUPipe, HUNetwork, HUGrid>, IHUPipe {

    @Override
    default int temperatureCoefficient(){
        return 0;
    }
}
