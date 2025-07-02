package org.gtreimagined.tesseract.api.eu;

import org.gtreimagined.tesseract.api.context.TesseractItemContext;

public interface IEnergyItem {
    IEnergyHandlerItem createEnergyHandler(TesseractItemContext context);

    default boolean canCreate(TesseractItemContext context){
        return true;
    }
}
