package org.gtreimagined.tesseract.api.eu;

import org.jetbrains.annotations.NotNull;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;

public interface IEnergyHandlerItem extends IEnergyHandler{
    void setCapacity(long capacity);

    void setEnergy(long energy);

    @NotNull
    TesseractItemContext getContainer();
}
