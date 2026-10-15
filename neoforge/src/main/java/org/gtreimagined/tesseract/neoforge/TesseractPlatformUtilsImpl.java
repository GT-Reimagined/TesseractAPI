package org.gtreimagined.tesseract.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.gtreimagined.tesseract.TesseractCapUtils;
import org.gtreimagined.tesseract.TesseractPlatformUtils;
import org.gtreimagined.tesseract.api.fe.IExtendedEnergyStorage;
import org.gtreimagined.tesseract.api.neoforge.TesseractCaps;
import org.gtreimagined.tesseract.api.neoforge.wrapper.FEWrapper;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;
import org.jspecify.annotations.Nullable;

public class TesseractPlatformUtilsImpl implements TesseractPlatformUtils {
    @Override
    public @Nullable IEnergyHandler getGTNode(Level level, long pos, Direction direction, @Nullable Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) return null;
        IEnergyHandler capability = TesseractCapUtils.INSTANCE.getEnergyHandler(tile, direction).orElse(null);
        if (capability != null) {
            if (invalidate != null )capability.addListener(o -> invalidate.run());
            return capability;
        }
        return null;
    }

    public static @Nullable IExtendedEnergyStorage getRFNode(Level level, long pos, Direction capSide, @Nullable Runnable capCallback){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) {
            return null;
        }
        LazyOptional<IEnergyStorage> capability = tile.getCapability(ForgeCapabilities.ENERGY, capSide);
        if (capability.isPresent()) {
            if (capCallback != null) capability.addListener(o -> capCallback.run());
            IEnergyStorage handler = capability.map(f -> f).orElse(null);
            if (handler == null) return null;
            return handler instanceof IExtendedEnergyStorage node ? node : new FEWrapper(handler);
        } else {
            return null;
        }
    }

    @Override
    public @Nullable IHeatHandler getHeatNode(Level level, long pos, Direction direction, @Nullable Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) return null;
        LazyOptional<IHeatHandler> capability = tile.getCapability(TesseractCaps.HEAT_CAPABILITY, direction);
        if (capability.isPresent()) {
            if (invalidate != null) capability.addListener(t -> invalidate.run());
            return capability.resolve().get();
        }
        return null;
    }
}
