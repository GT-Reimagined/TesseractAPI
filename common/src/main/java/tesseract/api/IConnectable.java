package tesseract.api;

import net.minecraft.core.Direction;
import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRoutingInfo;

/**
 * A simple interface for representing connectable objects.
 */
public interface IConnectable<TSelf extends IConnectable<TSelf, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNotableElement extends INode<TNotableElement, TRoutingInfo, TSelf, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNetwork extends IFactoryNetwork<TNetwork, TSelf, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TSelf, TNotableElement, TRoutingInfo, TNetwork>> extends IFactoryElement<TSelf, TNotableElement, TRoutingInfo, TNetwork, TGrid> {

    /**
     * @param direction The direction vector.
     * @return True if connect to the direction, false otherwise.
     */
    boolean connects(Direction direction);

    boolean validate(Direction dir);
}
