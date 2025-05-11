package tesseract.factory;

import it.unimi.dsi.fastutil.Pair;

import java.util.List;

public interface INotableFactoryElement<TSelf extends INotableFactoryElement<TSelf, TRoutingInfo, TElement, TNetwork, TGrid> & IFactoryElement<TElement, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IFactoryElement<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> extends IFactoryElement<TElement, TNetwork, TGrid> {
    /** Exactly the same as getConnections, but with some routing metadata. */
    List<Pair<TSelf, TRoutingInfo>> getRoutedNeighbours();
}
