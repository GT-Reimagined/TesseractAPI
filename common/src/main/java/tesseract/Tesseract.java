package tesseract;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.LevelAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tesseract.api.GraphWrapper;
import tesseract.api.fluid.FluidTransaction;
import tesseract.api.fluid.IFluidNode;
import tesseract.api.fluid.IFluidPipe;
import tesseract.api.gt.GTTransaction;
import tesseract.api.gt.IGTCable;
import tesseract.api.gt.IGTNode;
import tesseract.api.heat.HeatController;
import tesseract.api.heat.HeatTransaction;
import tesseract.api.heat.IHeatNode;
import tesseract.api.heat.IHeatPipe;
import tesseract.api.item.IItemNode;
import tesseract.api.item.IItemPipe;
import tesseract.api.item.ItemController;
import tesseract.api.item.ItemTransaction;
import tesseract.controller.Fluid;


public class Tesseract {

    public static final long dropletMultiplier = TesseractPlatformUtils.isForge() ? 1L : 81L;
    public static final GraphWrapper<HeatTransaction, IHeatPipe, IHeatNode> HEAT_CONTROLLER = new GraphWrapper<>(HeatController::new, IHeatNode.GETTER);
    public static final GraphWrapper<FluidTransaction, IFluidPipe, IFluidNode> FLUID = new GraphWrapper<>(Fluid::new, IFluidNode.GETTER);
    public static final GraphWrapper<ItemTransaction, IItemPipe, IItemNode> ITEM = new GraphWrapper<>(ItemController::new, IItemNode.GETTER);

    public static final String API_ID = "tesseractapi";
    public static final String API_NAME = "Tesseract API";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDS = "";

    public static final Logger LOGGER = LogManager.getLogger(API_ID);



    public static final int HEALTH_CHECK_TIME = 1000;

    public static void init() {

    }

    public Tesseract() {
    }

    @ExpectPlatform
    public static boolean hadFirstTick(LevelAccessor world) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static GraphWrapper<GTTransaction, IGTCable, IGTNode> getGT_ENERGY(){
        throw new AssertionError();
    }
}
