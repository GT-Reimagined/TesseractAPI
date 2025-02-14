package tesseract;

import tesseract.api.GraphWrapper;
import tesseract.api.gt.GTTransaction;
import tesseract.api.gt.IGTCable;
import tesseract.api.gt.IGTNode;
import tesseract.api.heat.HeatController;
import tesseract.api.heat.HeatTransaction;
import tesseract.api.heat.IHeatNode;
import tesseract.api.heat.IHeatPipe;
import tesseract.controller.Energy;

public class TesseractGraphWrappers {
    public static final GraphWrapper<HeatTransaction, IHeatPipe, IHeatNode> HEAT_CONTROLLER = new GraphWrapper<>(HeatController::new, IHeatNode.GETTER);

    public static long dropletMultiplier = TesseractPlatformUtils.INSTANCE.isForge() ? 1L : 81L;
    public static GraphWrapper<GTTransaction, IGTCable, IGTNode> GT_ENERGY = new GraphWrapper<>(Energy::new, IGTNode.GT_GETTER);

    public static void init(){}
}
