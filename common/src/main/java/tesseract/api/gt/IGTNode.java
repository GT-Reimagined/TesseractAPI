package tesseract.api.gt;

import net.minecraft.core.Direction;
import tesseract.api.INode;

import java.util.List;

public interface IGTNode extends INode<IGTNode, GTRoutingInfo, IGTCable, GTFactoryNetwork, GTFactoryGrid>, IGTCable {
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

    @Override
    default Class<IGTNode> getSelfClass(){
        return IGTNode.class;
    }

    @Override
    default Class<IGTCable> getElementClass(){
        return IGTCable.class;
    }

    @Override
    default GTRoutingInfo createRoutingInfo(List<IGTCable> pathSoFar, Direction side){
        int amps = pathSoFar.stream().reduce((a, b) -> a.getAmps() < b.getAmps() ? a : b).map(IGTCable::getAmps).orElse(0);
        long voltage = pathSoFar.stream().reduce((a, b) -> a.getVoltage() < b.getVoltage() ? a : b).map(IGTCable::getVoltage).orElse(0L);
        double loss = pathSoFar.stream().mapToDouble(IGTCable::getLoss).sum();
        return new GTRoutingInfo(amps, voltage, loss, side, pathSoFar);
    }
}
