package org.gtreimagined.tesseract.fabric;

import net.fabricmc.loader.api.FabricLoader;
import org.gtreimagined.tesseract.graph.GraphUtils;

public class TesseractFabricGraphUtils implements GraphUtils {
    @Override
    public boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
