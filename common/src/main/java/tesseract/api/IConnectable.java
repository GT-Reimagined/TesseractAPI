package tesseract.api;

import net.minecraft.core.Direction;
import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;

/**
 * A simple interface for representing connectable objects.
 */
public interface IConnectable<TSelf extends IConnectable<TSelf, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TSelf, TGrid>, TGrid extends IFactoryGrid<TGrid, TSelf, TNetwork>> extends IFactoryElement<TSelf, TNetwork, TGrid> {

    /**
     * @param direction The direction vector.
     * @return True if connect to the direction, false otherwise.
     */
    boolean connects(Direction direction);

    boolean validate(Direction dir);
}
