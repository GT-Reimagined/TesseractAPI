package tesseract.factory;

import java.util.List;

public interface IRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IFactoryElement<TElement, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TNetwork>> {
    List<IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> getPaths(TNotableElement source);

    void onElementAdded(TElement element);

    void onElementRemoved(TElement element);
}
