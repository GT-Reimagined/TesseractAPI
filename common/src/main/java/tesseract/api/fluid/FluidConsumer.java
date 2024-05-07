package tesseract.api.fluid;


import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.core.Direction;
import tesseract.api.ConnectionType;
import tesseract.api.Consumer;
import tesseract.graph.Path;


/**
 * A class that acts as a container for a fluid consumer.
 */
public class FluidConsumer extends Consumer<IFluidPipe, IFluidNode> {

    private int isProof = 1;
    private int minCapacity = Integer.MAX_VALUE;

    public long getMinPressure() {
        return minPressure;
    }

    private long minPressure = Long.MAX_VALUE;
    private int minTemperature = Integer.MAX_VALUE;
    public final Direction input;

    public long lowestPipePosition = -1;

    /**
     * Creates instance of the consumer.
     *
     * @param consumer The consumer node.
     * @param path     The path information.
     * @param dir      The added direction.
     */
    public FluidConsumer(IFluidNode consumer,IFluidNode producer, Path<IFluidPipe> path, Direction dir) {
        super(consumer,producer, path);
        init();
        this.input = dir;
    }

    /**
     * Adds fluid to the node. Returns amount of fluid that was filled.
     *
     * @param data     FluidData attempting to fill the tank.
     * @param simulate If true, the fill will only be simulated.
     * @return Amount of fluid that was accepted (or would be, if simulated) by the tank.
     */
    public long insert(FluidHolder data, boolean simulate) {
        return node.insertFluid(data, simulate);
    }

    /**
     * @param fluid The Fluid to be queried.
     * @return If the tank can hold the fluid (EVER, not at the time of query).
     */
    public boolean canHold(FluidHolder fluid) {
        return node.canInput(fluid, input);
    }

    /**
     * @return Returns the priority of this node as a number.
     */
    public int getPriority() {
        return node.getPriority(input);
    }

    /**
     * @param temperature The current temperature.
     * @param isGas       True if current liquid is in a gas state.
     * @return Checks that the consumer is able to receive fluid.
     */
    public boolean canHandle(int temperature, boolean isGas) {
        return minTemperature >= temperature /*&& minPressure >= pressure */ && isProof == (isGas ? 1 : 0);
    }

    @Override
    protected void onConnectorCatch(long pos, IFluidPipe pipe) {
        isProof = Math.min(isProof, pipe.isGasProof() ? 1 : 0);
        minTemperature = Math.min(minTemperature, pipe.getTemperature());
        minCapacity = Math.min(minCapacity, pipe.getCapacity());
        if (pipe.getPressure() < minPressure && connection == ConnectionType.SINGLE) {
            lowestPipePosition = this.getFull().long2ObjectEntrySet().stream().filter(t -> t.getValue() == pipe).findFirst().get().getLongKey();
        }
        minPressure = Math.min(minPressure, pipe.getPressureInDroplets());
    }
}
