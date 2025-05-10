package tesseract.factory;

import java.util.List;

public interface IRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IFactoryElement<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> {
    List<IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> getPaths(TNotableElement source);

    void createPaths(TNotableElement source);
}
