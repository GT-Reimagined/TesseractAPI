package tesseract.factory;

import tesseract.factory.standard.StandardFactoryNetwork;

import java.util.Collection;

/**
 * A factory network is a logical group of factory elements.
 * You usually want to extend {@link StandardFactoryNetwork}, not this.
 */
public interface IFactoryNetwork<TSelf extends IFactoryNetwork<TSelf, TElement, TNotableElement, TRoutingInfo, TGrid>, TElement extends IFactoryElement<TElement, TNotableElement, TRoutingInfo, TSelf, TGrid>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TSelf, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TGrid extends IFactoryGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TSelf>> {

    public void addElement(TElement element);

    public void removeElement(TElement element);

    public default void onNetworkRemoved() {

    }

    public Collection<TElement> getElements();

    IRouteTracker<TRoutingInfo, TNotableElement, TElement, TSelf, TGrid> getTracker();
}
