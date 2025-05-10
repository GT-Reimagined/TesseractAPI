package tesseract.factory;

import java.util.List;

public interface IFactoryPath<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IFactoryElement<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> {
    TNotableElement getDestination();
    TRoutingInfo getRoutingInfo();
    List<TElement> getPath();
}
