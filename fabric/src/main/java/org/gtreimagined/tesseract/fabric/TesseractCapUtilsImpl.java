package org.gtreimagined.tesseract.fabric;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.tesseract.api.fabric.wrapper.EnergyTileWrapper;
import org.gtreimagined.tesseract.api.fabric.wrapper.IEnergyHandlerStorage;
import org.jetbrains.annotations.NotNull;
import team.reborn.energy.api.EnergyStorage;
import org.gtreimagined.tesseract.TesseractCapUtils;
import org.gtreimagined.tesseract.api.fabric.TesseractLookups;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.eu.IEnergyHandlerItem;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class TesseractCapUtilsImpl implements TesseractCapUtils {
    @Override
    public Optional<IEnergyHandlerItem> getEnergyHandlerItem(ItemStack stack){
        IEnergyHandlerItem energyHandler = ContainerItemContext.withConstant(stack).find(TesseractLookups.ENERGY_HANDLER_ITEM);
        return Optional.ofNullable(energyHandler);
    }

    @Override
    public Optional<IEnergyHandlerItem> getWrappedEnergyHandlerItem(ItemStack stack){
        IEnergyHandlerItem energyHandler = ContainerItemContext.withConstant(stack).find(TesseractLookups.ENERGY_HANDLER_ITEM);
        if (energyHandler == null){
            EnergyStorage storage = ContainerItemContext.withConstant(stack).find(EnergyStorage.ITEM);
            if (storage instanceof IEnergyHandlerItem e){
                energyHandler = e;
            }
        }
        return Optional.ofNullable(energyHandler);
    }

    @Override
    public Optional<IEnergyHandler> getEnergyHandler(@NotNull BlockEntity entity, Direction side){
        IEnergyHandler energyHandler = TesseractLookups.ENERGY_HANDLER_SIDED.find(entity.getLevel(), entity.getBlockPos(), entity.getBlockState(), entity, side);
        if (energyHandler == null) {
            energyHandler = getEnergyStorage(entity, side);
        }
        return Optional.ofNullable(energyHandler);
    }

    @Override
    public Optional<IHeatHandler> getHeatHandler(BlockEntity entity, Direction side){
        IHeatHandler heatHandler = TesseractLookups.HEAT_HANDLER_SIDED.find(entity.getLevel(), entity.getBlockPos(), entity.getBlockState(), entity, side);
        return Optional.ofNullable(heatHandler);
    }

    private IEnergyHandler getEnergyStorage(BlockEntity be, Direction direction){
        EnergyStorage storage = EnergyStorage.SIDED.find(be.getLevel(), be.getBlockPos(), be.getBlockState(), be, direction);
        if (storage == null) return null;
        if (storage instanceof IEnergyHandler moveable1) return moveable1;
        if (storage instanceof IEnergyHandlerStorage handlerStorage) return handlerStorage.getEnergyHandler();
        return new EnergyTileWrapper(be, storage);
    }
}
