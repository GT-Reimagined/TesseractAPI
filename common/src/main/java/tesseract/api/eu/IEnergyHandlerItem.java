package tesseract.api.eu;

import org.jetbrains.annotations.NotNull;
import tesseract.api.context.TesseractItemContext;

public interface IEnergyHandlerItem extends IEnergyHandler{
    void setCapacity(long capacity);

    void setEnergy(long energy);

    @NotNull
    TesseractItemContext getContainer();
}
