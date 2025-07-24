package org.gtreimagined.tesseract.forge;

import net.minecraftforge.fml.loading.FMLEnvironment;
import org.gtreimagined.tesseract.graph.GraphUtils;

public class TesseractForgeGraphUtils implements GraphUtils {
    @Override
    public boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }
}
