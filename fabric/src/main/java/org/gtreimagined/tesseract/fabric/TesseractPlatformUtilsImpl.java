package org.gtreimagined.tesseract.fabric;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.tesseract.TesseractCapUtils;
import org.gtreimagined.tesseract.TesseractPlatformUtils;
import org.gtreimagined.tesseract.api.fabric.TileListeners;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class TesseractPlatformUtilsImpl implements TesseractPlatformUtils {
    @Override
    public @Nullable IEnergyHandler getGTNode(Level level, long pos, Direction direction, @Nullable Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) return null;
        Optional<IEnergyHandler> capability = TesseractCapUtils.INSTANCE.getEnergyHandler(tile, direction);
        if (capability.isPresent()) {
            if (invalidate != null) ((TileListeners)tile).addListener(invalidate);
            return capability.get();
        }
        return null;
    }

    @Override
    public @Nullable IHeatHandler getHeatNode(Level level, long pos, Direction direction, @Nullable Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) return null;
        Optional<IHeatHandler> capability = TesseractCapUtils.INSTANCE.getHeatHandler(tile, direction);
        if (capability.isPresent()) {
            if (invalidate != null) ((TileListeners)tile).addListener(invalidate);
            return capability.get();
        }
        return null;
    }
}
