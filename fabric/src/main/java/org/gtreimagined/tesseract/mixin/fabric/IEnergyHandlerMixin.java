package org.gtreimagined.tesseract.mixin.fabric;

import org.spongepowered.asm.mixin.Mixin;
import org.gtreimagined.tesseract.api.fabric.wrapper.IEnergyHandlerStorage;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;

@Mixin(IEnergyHandler.class)
public interface IEnergyHandlerMixin extends IEnergyHandlerStorage {
    @Override
    default IEnergyHandler getEnergyHandler(){
        return (IEnergyHandler) this;
    }
}
