package org.gtreimagined.tesseract.neoforge;

import net.neoforged.fml.loading.FMLEnvironment;
import org.gtreimagined.tesseract.graph.GraphUtils;

public class TesseractForgeGraphUtils implements GraphUtils {
    @Override
    public boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }
}
