package org.gtreimagined.tesseract.neoforge;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.gtreimagined.tesseract.TesseractCapUtils;
import org.gtreimagined.tesseract.api.neoforge.TesseractCaps;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.eu.IEnergyHandlerItem;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;
import org.gtreimagined.tesseract.api.neoforge.TesseractCaps.EnergyHandler;
import org.gtreimagined.tesseract.api.neoforge.TesseractCaps.HeatHandler;

import java.util.Optional;

public class TesseractCapUtilsImpl implements TesseractCapUtils {
    @Override
    public Optional<IEnergyHandlerItem> getEnergyHandlerItem(ItemStack stack){
        return Optional.ofNullable(stack.getCapability(EnergyHandler.ITEM));
    }

    @Override
    public Optional<IEnergyHandlerItem> getWrappedEnergyHandlerItem(ItemStack stack){
        IEnergyHandlerItem energyHandler = stack.getCapability(EnergyHandler.ITEM);
        return Optional.ofNullable(energyHandler);
    }

    @Override
    public Optional<IEnergyHandler> getEnergyHandler(BlockEntity entity, Direction side){
        return Optional.ofNullable(entity.getLevel() == null ? null : entity.getLevel().getCapability(EnergyHandler.BLOCK, entity.getBlockPos(), side));
    }

    @Override
    public Optional<IHeatHandler> getHeatHandler(BlockEntity entity, Direction side){
        return Optional.ofNullable(entity.getLevel() == null ? null : entity.getLevel().getCapability(HeatHandler.BLOCK, entity.getBlockPos(), side));
    }
}
