package tesseract.forge;

import net.minecraftforge.fml.loading.FMLEnvironment;
import tesseract.factory.GraphUtils;

public class TesseractForgeGraphUtils implements GraphUtils {
    @Override
    public boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }
}
