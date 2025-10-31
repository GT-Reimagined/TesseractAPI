package org.gtreimagined.tesseract;

import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;

import java.util.ServiceLoader;

public interface TesseractPlatformUtils {
    TesseractPlatformUtils INSTANCE =  ServiceLoader.load(TesseractPlatformUtils.class).findFirst().orElseThrow(() -> new IllegalStateException("No implementation of TesseractPlatformUtils found"));


    IEnergyHandler getGTNode(Level level, long pos, Direction direction, Runnable invalidate);

    IHeatHandler getHeatNode(Level level, long pos, Direction direction, Runnable invalidate);
}
