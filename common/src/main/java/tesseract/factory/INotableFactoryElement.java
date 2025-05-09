package tesseract.factory;

import it.unimi.dsi.fastutil.Pair;

import java.util.List;

public interface INotableFactoryElement<TSelf extends IFactoryElement<TSelf, TNetwork, TGrid> & INotableFactoryElement<TSelf, TRoutingInfo, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNetwork extends IFactoryNetwork<TNetwork, TSelf, TGrid>, TGrid extends IFactoryGrid<TGrid, TSelf, TNetwork>> {
    /** Exactly the same as getConnections, but with some routing metadata. */
    List<Pair<TSelf, TRoutingInfo>> getRoutedNeighbours();
}
