package tesseract.fabric;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import tesseract.TesseractCapUtils;
import tesseract.TesseractPlatformUtils;
import tesseract.api.fabric.TileListeners;
import tesseract.api.eu.IEnergyHandler;
import tesseract.api.hu.IHeatHandler;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class TesseractPlatformUtilsImpl implements TesseractPlatformUtils {
    @Override
    public IEnergyHandler getGTNode(Level level, long pos, Direction direction, Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) return null;
        Optional<IEnergyHandler> capability = TesseractCapUtils.INSTANCE.getEnergyHandler(tile, direction);
        if (capability.isPresent()) {
            if (invalidate != null) ((TileListeners)tile).addListener(() -> invalidate.run());
            return capability.get();
        }
        return null;
    }

    @Override
    public IHeatHandler getHeatNode(Level level, long pos, Direction direction, Runnable invalidate){
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
