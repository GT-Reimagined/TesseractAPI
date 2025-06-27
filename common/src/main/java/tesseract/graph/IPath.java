package tesseract.graph;

public interface IPath<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IElement<TElement, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNetwork extends INetwork<TNetwork, TElement, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TNetwork>> {
    TNotableElement getDestination();
    TRoutingInfo getRoutingInfo();
}
