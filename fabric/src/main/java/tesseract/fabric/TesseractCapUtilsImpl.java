package tesseract.fabric;

import aztech.modern_industrialization.api.energy.EnergyApi;
import aztech.modern_industrialization.api.energy.EnergyMoveable;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import team.reborn.energy.api.EnergyStorage;
import tesseract.TesseractCapUtils;
import tesseract.TesseractConfig;
import tesseract.api.fabric.TesseractLookups;
import tesseract.api.fabric.wrapper.*;
import tesseract.api.gt.IEnergyHandler;
import tesseract.api.gt.IEnergyHandlerItem;
import tesseract.api.heat.IHeatHandler;

import java.util.Optional;

public class TesseractCapUtilsImpl implements TesseractCapUtils {
    @Override
    public Optional<IEnergyHandlerItem> getEnergyHandlerItem(ItemStack stack){
        IEnergyHandlerItem energyHandler = ContainerItemContext.withInitial(stack).find(TesseractLookups.ENERGY_HANDLER_ITEM);
        return Optional.ofNullable(energyHandler);
    }

    @Override
    public Optional<IEnergyHandlerItem> getWrappedEnergyHandlerItem(ItemStack stack){
        IEnergyHandlerItem energyHandler = ContainerItemContext.withInitial(stack).find(TesseractLookups.ENERGY_HANDLER_ITEM);
        if (energyHandler == null){
            EnergyStorage storage = ContainerItemContext.withInitial(stack).find(EnergyStorage.ITEM);
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
            if (FabricLoader.getInstance().isModLoaded("modern_industrialization") && TesseractConfig.ENABLE_MI_COMPAT.get()){
                energyHandler = getEnergyMoveable(entity, side);
                if (energyHandler != null) return Optional.of(energyHandler);
            }
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

    private IEnergyHandler getEnergyMoveable(BlockEntity be, Direction direction){
        EnergyMoveable moveable = EnergyApi.MOVEABLE.find(be.getLevel(), be.getBlockPos(), be.getBlockState(), be, direction);
        if (moveable == null) return null;
        if (moveable instanceof IEnergyHandler moveable1) return moveable1;
        if (moveable instanceof IEnergyHandlerMoveable moveable1) return moveable1.getEnergyHandler();
        return new EnergyMoveableWrapper(be, moveable);
    }
}
