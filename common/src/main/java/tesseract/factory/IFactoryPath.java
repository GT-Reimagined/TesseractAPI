package tesseract.factory;

import java.util.List;

public interface IFactoryPath<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IFactoryElement<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> {
    TElement getDestination();
    TRoutingInfo getRoutingInfo();
    List<TElement> getPath();
}
