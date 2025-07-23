package org.gtreimagined.tesseract.mixin.fabric.mi;

import org.spongepowered.asm.mixin.Mixin;
import org.gtreimagined.tesseract.api.fabric.wrapper.IEnergyHandlerMoveable;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;

@Mixin(IEnergyHandler.class)
public interface IEnergyHandlerMixin extends IEnergyHandlerMoveable {
}
