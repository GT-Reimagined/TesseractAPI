package tesseract.factory;

import it.unimi.dsi.fastutil.Pair;

import java.util.List;

public interface INotableFactoryElement<TSelf extends INotableFactoryElement<TSelf, TRoutingInfo, TElement, TNetwork, TGrid> & IFactoryElement<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IFactoryElement<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TSelf, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TSelf, TRoutingInfo, TNetwork>> extends IFactoryElement<TElement, TSelf, TRoutingInfo, TNetwork, TGrid> {
    /** Exactly the same as getConnections, but with some routing metadata. */
    List<Pair<TSelf, TRoutingInfo>> getRoutedNeighbours();
}
