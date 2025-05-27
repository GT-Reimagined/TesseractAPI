package tesseract.api.forge.wrapper;

import net.minecraft.core.Direction;
import net.minecraftforge.energy.IEnergyStorage;
import tesseract.api.fe.IFENode;

public record FEWrapper(IEnergyStorage storage) implements IFENode {

    @Override
    public int receiveEnergy(int maxAmount, boolean simulate) {
        return storage.receiveEnergy(maxAmount, simulate);
    }

    @Override
    public int extractEnergy(int maxAmount, boolean simulate) {
        return storage.extractEnergy(maxAmount, simulate);
    }


    @Override
    public int getEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public int maxInsert() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int maxExtract() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canReceive() {
        return storage.canReceive();
    }

    @Override
    public boolean canExtract() {
        return storage.canExtract();
    }

    @Override
    public boolean canReceive(Direction direction) {
        return canReceive();
    }

    @Override
    public boolean canExtract(Direction direction) {
        return canExtract();
    }
}
