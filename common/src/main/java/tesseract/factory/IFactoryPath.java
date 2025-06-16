package tesseract.factory;

import java.util.List;

public interface IFactoryPath<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IFactoryElement<TElement, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TNetwork>> {
    TNotableElement getDestination();
    TRoutingInfo getRoutingInfo();
}
