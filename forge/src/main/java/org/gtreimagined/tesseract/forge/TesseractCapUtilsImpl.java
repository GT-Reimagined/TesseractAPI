package org.gtreimagined.tesseract.forge;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.tesseract.TesseractCapUtils;
import org.gtreimagined.tesseract.api.forge.TesseractCaps;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.eu.IEnergyHandlerItem;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;

import java.util.Optional;

public class TesseractCapUtilsImpl implements TesseractCapUtils {
    @Override
    public Optional<IEnergyHandlerItem> getEnergyHandlerItem(ItemStack stack){
        return stack.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).map(e -> e);
    }

    @Override
    public Optional<IEnergyHandlerItem> getWrappedEnergyHandlerItem(ItemStack stack){
        IEnergyHandlerItem energyHandler = stack.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM).map(e -> e).orElse(null);
        return Optional.ofNullable(energyHandler);
    }

    @Override
    public Optional<IEnergyHandler> getEnergyHandler(BlockEntity entity, Direction side){
        return entity.getCapability(TesseractCaps.ENERGY_HANDLER_CAPABILITY, side).resolve();
    }

    @Override
    public Optional<IHeatHandler> getHeatHandler(BlockEntity entity, Direction side){
        return entity.getCapability(TesseractCaps.HEAT_CAPABILITY, side).map(e -> e);
    }
}
