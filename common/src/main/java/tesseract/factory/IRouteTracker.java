package tesseract.factory;

import java.util.List;

public interface IRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IFactoryElement<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> {
    List<IFactoryPath<TRoutingInfo, TElement, TNetwork, TGrid>> getPaths(TElement source);

    void createPaths(TElement source);
}
