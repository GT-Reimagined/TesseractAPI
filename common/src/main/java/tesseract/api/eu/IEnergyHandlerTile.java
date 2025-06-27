package tesseract.api.eu;

import net.minecraft.core.Direction;

public interface IEnergyHandlerTile {
    IEnergyHandler getEnergyHandler(Direction side);
}
