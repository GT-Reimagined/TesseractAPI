package tesseract.api.eu;

import tesseract.api.context.TesseractItemContext;

public interface IEnergyItem {
    IEnergyHandlerItem createEnergyHandler(TesseractItemContext context);

    default boolean canCreate(TesseractItemContext context){
        return true;
    }
}
