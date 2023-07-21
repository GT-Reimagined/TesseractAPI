package tesseract;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import tesseract.api.gt.IGTNode;
import tesseract.api.heat.IHeatNode;
import tesseract.api.rf.IRFNode;
import tesseract.graph.INode;

public class TesseractPlatformUtils {
    @ExpectPlatform
    public static IGTNode getGTNode(Level level, long pos, Direction direction, Runnable invalidate){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IRFNode getRFNode(Level level, long pos, Direction direction, Runnable invalidate){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IHeatNode getHeatNode(Level level, long pos, Direction direction, Runnable invalidate){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isFeCap(Class<?> cap){
        throw new AssertionError();
    }


    @ExpectPlatform
    public static boolean isForge(){
        throw new AssertionError();
    }
}
