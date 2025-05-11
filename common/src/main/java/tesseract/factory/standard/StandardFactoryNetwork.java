package tesseract.factory.standard;

import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRouteTracker;
import tesseract.factory.IRoutingInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A pretty basic factory network.
 * This doesn't do much beyond tracking elements and components.
 */
public class StandardFactoryNetwork<TSelf extends StandardFactoryNetwork<TSelf, TElement, TNotableElement, TRoutingInfo, TGrid>, TElement extends IFactoryElement<TElement, TNotableElement, TRoutingInfo, TSelf, TGrid>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TSelf, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TGrid extends IFactoryGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TSelf>>
        implements IFactoryNetwork<TSelf, TElement, TNotableElement, TRoutingInfo, TGrid> {

    public final HashSet<TElement> elements = new HashSet<>();
    public final HashMap<Class<?>, Collection<Object>> components = new HashMap<>();

    public IRouteTracker<TRoutingInfo, TNotableElement, TElement, TSelf, TGrid> routeTracker;

    @Override
    public void addElement(TElement element) {
        elements.add(element);

        if (element instanceof INotableFactoryElement<?,?,?,?,?> factoryElement){
            routeTracker.createPaths((TNotableElement) element);
        }
        for (var component : element.getComponents()) {
            addComponentImpl(component.left(), component.right());
        }
    }

    @Override
    public void removeElement(TElement element) {
        elements.remove(element);

        if (element instanceof INotableFactoryElement<?,?,?,?,?>){
            routeTracker.removePaths((TNotableElement) element);
        }
        if (element != null && element.getNetwork() == this) {
            for (var component : element.getComponents()) {
                removeComponentImpl(component.left(), component.right());
            }
        }
    }

    private void addComponentImpl(Class<?> iface, Object impl) {
        components.computeIfAbsent(iface, x -> new HashSet<>())
                .add(impl);
    }

    public <TIface, TImpl extends TIface> void addComponent(Class<TIface> iface, TImpl impl) {
        addComponentImpl(iface, impl);
    }

    private void removeComponentImpl(Class<?> iface, Object impl) {
        Collection<Object> s = components.get(iface);

        if (s != null) {
            s.remove(impl);

            if (s.isEmpty()) {
                components.remove(iface);
            }
        }
    }

    public <TIface, TImpl extends TIface> void removeComponent(Class<TIface> iface, TImpl impl) {
        removeComponentImpl(iface, impl);
    }

    @SuppressWarnings("unchecked")
    public <TIface> Collection<TIface> getComponents(Class<TIface> iface) {
        return (Collection<TIface>) components.getOrDefault(iface, Collections.emptyList());
    }

    @Override
    public Collection<TElement> getElements() {
        return elements;
    }

    @Override
    public IRouteTracker<TRoutingInfo, TNotableElement, TElement, TSelf, TGrid> getTracker() {
        return routeTracker;
    }
}
