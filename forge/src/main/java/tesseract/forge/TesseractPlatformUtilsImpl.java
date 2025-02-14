package tesseract.forge;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import tesseract.TesseractCapUtils;
import tesseract.TesseractPlatformUtils;
import tesseract.api.fe.IFENode;
import tesseract.api.forge.TesseractCaps;
import tesseract.api.forge.wrapper.FEWrapper;
import tesseract.api.gt.IEnergyHandler;
import tesseract.api.gt.IGTNode;
import tesseract.api.heat.IHeatHandler;
import tesseract.api.heat.IHeatNode;

public class TesseractPlatformUtilsImpl implements TesseractPlatformUtils {
    @Override
    public IGTNode getGTNode(Level level, long pos, Direction direction, Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        LazyOptional<IEnergyHandler> capability = TesseractCapUtils.INSTANCE.getEnergyHandler(tile, direction).map(e -> LazyOptional.of(() -> e)).orElse(LazyOptional.empty());
        if (capability.isPresent()) {
            if (invalidate != null )capability.addListener(o -> invalidate.run());
            return capability.resolve().get();
        }
        return null;
    }

    public static IFENode getRFNode(Level level, long pos, Direction capSide, Runnable capCallback){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) {
            return null;
        }
        LazyOptional<IEnergyStorage> capability = tile.getCapability(CapabilityEnergy.ENERGY, capSide);
        if (capability.isPresent()) {
            if (capCallback != null) capability.addListener(o -> capCallback.run());
            IEnergyStorage handler = capability.map(f -> f).orElse(null);
            return handler instanceof IFENode node ? node : new FEWrapper(handler);
        } else {
            return null;
        }
    }

    @Override
    public IHeatNode getHeatNode(Level level, long pos, Direction direction, Runnable invalidate){
        BlockEntity tile = level.getBlockEntity(BlockPos.of(pos));
        if (tile == null) return null;
        LazyOptional<IHeatHandler> capability = tile.getCapability(TesseractCaps.HEAT_CAPABILITY, direction);
        if (capability.isPresent()) {
            if (invalidate != null) capability.addListener(t -> invalidate.run());
            return capability.resolve().get();
        }
        return null;
    }

    @Override
    public boolean isFeCap(Class<?> cap){
        return cap == IEnergyStorage.class;
    }

    @Override
    public boolean isForge(){
        return true;
    }

    @Override
    public ConfigHandler createConfig(Config config){
        return CarbonConfig.CONFIGS.createConfig(config);
    }

    @Override
    public boolean areCapsCompatible(ItemStack a, ItemStack b){
        return a.areCapsCompatible(b);
    }
}
