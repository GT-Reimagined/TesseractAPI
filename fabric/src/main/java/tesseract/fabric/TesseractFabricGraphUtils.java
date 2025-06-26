package tesseract.fabric;

import net.fabricmc.loader.api.FabricLoader;
import tesseract.factory.GraphUtils;

public class TesseractFabricGraphUtils implements GraphUtils {
    @Override
    public boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
