package tesseract.api;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRoutingInfo;

/**
 * A simple interface for representing connectable objects.
 */
public interface IConnectable {

    /**
     * @param direction The direction vector.
     * @return True if connect to the direction, false otherwise.
     */
    boolean connects(Direction direction);

    boolean validate(Direction dir);

    BlockEntity getBlockEntity();
}
