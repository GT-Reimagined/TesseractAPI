package tesseract.api.fe;

import net.minecraft.core.Direction;
import net.minecraftforge.energy.IEnergyStorage;
import tesseract.api.GraphWrapper;
import tesseract.forge.TesseractPlatformUtilsImpl;

/**
 * A flux node is the unit of interaction with flux inventories.
 * <p>
 * Derived from the Redstone Flux power system designed by King Lemming and originally utilized in Thermal Expansion and related mods.
 * Created with consent and permission of King Lemming and Team CoFH. Released with permission under LGPL 2.1 when bundled with Forge.
 * </p>
 */
public interface IFENode extends IEnergyStorage {

    /**
     * Used to determine if this storage can receive energy in the given direction.
     *
     * @param direction the direction.
     * @return If this is false, then any calls to receiveEnergy will return 0.
     */
    boolean canReceive(Direction direction);

    /**
     * Used to determine which sides can output energy (if any).
     *
     * @param direction Direction to the output.
     * @return Returns true if the given direction is output side.
     */
    boolean canExtract(Direction direction);

    int maxInsert();

    int maxExtract();

    GraphWrapper.ICapabilityGetter<IFENode> GETTER = TesseractPlatformUtilsImpl::getRFNode;
}
