package tesseract.api.hu;

import tesseract.api.INode;

public interface IHUNode extends INode<IHUNode, HURoutingInfo, IHUPipe, HUNetwork, HUGrid>, IHUPipe {

    @Override
    default int temperatureCoefficient(){
        return 0;
    }
}
