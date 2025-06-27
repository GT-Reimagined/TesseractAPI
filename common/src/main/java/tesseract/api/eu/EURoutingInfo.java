package tesseract.api.eu;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Direction;
import tesseract.graph.IRoutingInfo;

import java.util.List;

public record EURoutingInfo(long maxAmps, long maxVoltage, double actualLoss, long roundedLoss, Direction side, List<IEUCable> path) implements IRoutingInfo<EURoutingInfo> {
    public EURoutingInfo(long maxAmps, long maxVoltage, double actualLoss, Direction side, List<IEUCable> path) {
        this(maxAmps, maxVoltage, actualLoss, Math.round(actualLoss), side, path);
    }

    @Override
    public EURoutingInfo merge(EURoutingInfo other) {
        long maxAmps = Math.min(this.maxAmps, other.maxAmps);
        long maxVoltage = Math.min(this.maxVoltage, other.maxVoltage);
        double actualLoss = this.actualLoss + other.actualLoss;
        long roundedLoss = Math.round(actualLoss);
        return new EURoutingInfo(maxAmps, maxVoltage, actualLoss, roundedLoss, other.side, ImmutableSet.<IEUCable>builder().addAll(path).addAll(other.path).build().stream().toList());
    }
}
