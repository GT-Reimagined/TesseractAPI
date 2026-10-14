package org.gtreimagined.tesseract.api.eu;

import org.gtreimagined.tesseract.api.context.TesseractItemContext;

public interface IEnergyHandlerItem extends IEnergyHandler{
    void setCapacity(long capacity);

    void setEnergy(long energy);

    TesseractItemContext getContainer();
}
