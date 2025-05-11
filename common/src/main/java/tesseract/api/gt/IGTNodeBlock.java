package tesseract.api.gt;

import tesseract.api.INode;

public interface IGTNodeBlock extends INode<IGTNodeBlock, GTRoutingInfo, IGTCable, GTFactoryNetwork, GTFactoryGrid>, IGTCable {
    @Override
    default double getLoss(){
        return 0.0;
    }

    @Override
    default int getAmps(){
        return Integer.MAX_VALUE;
    }

    @Override
    default long getVoltage(){
        return Long.MAX_VALUE;
    }

    @Override
    default long getHolder(){
        return 0;
    }

    @Override
    default void setHolder(long holder){
        //NOOP
    }
}
