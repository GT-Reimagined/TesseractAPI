package tesseract.api.eu;

import net.minecraft.core.Direction;
import tesseract.api.INode;

import java.util.List;

public interface IEUNode extends INode<IEUNode, EURoutingInfo, IEUCable, EUNetwork, EUGrid>, IEUCable {
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
    default Class<IEUNode> getSelfClass(){
        return IEUNode.class;
    }

    @Override
    default Class<IEUCable> getElementClass(){
        return IEUCable.class;
    }

    @Override
    default EURoutingInfo createRoutingInfo(List<IEUCable> pathSoFar, Direction side){
        int amps = pathSoFar.stream().reduce((a, b) -> a.getAmps() < b.getAmps() ? a : b).map(IEUCable::getAmps).orElse(0);
        long voltage = pathSoFar.stream().reduce((a, b) -> a.getVoltage() < b.getVoltage() ? a : b).map(IEUCable::getVoltage).orElse(0L);
        double loss = pathSoFar.stream().mapToDouble(IEUCable::getLoss).sum();
        return new EURoutingInfo(amps, voltage, loss, side, pathSoFar);
    }
}
