package tesseract.forge;

import tesseract.api.GraphWrapper;
import tesseract.api.fe.IFENode;
import tesseract.api.fe.IFECable;
import tesseract.api.fe.FEController;
import tesseract.api.fe.FETransaction;

public class TesseractForgeGraphWrappers {
    public static final GraphWrapper<FETransaction, IFECable, IFENode> RF = new GraphWrapper<>(FEController::new, IFENode.GETTER);
}
