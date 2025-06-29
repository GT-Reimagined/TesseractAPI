package tesseract.graph.standard;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import tesseract.graph.IElement;
import tesseract.graph.IGrid;
import tesseract.graph.INetwork;
import tesseract.graph.INotableElement;
import tesseract.graph.IRouteTracker;
import tesseract.graph.IRoutingInfo;
import tesseract.graph.RoutedNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class StandardRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IElement<TElement, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNetwork extends INetwork<TNetwork, TElement, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TNetwork>> implements IRouteTracker<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> {
    Map<TNotableElement, List<RoutedNode<TNotableElement, TRoutingInfo>>> edges = new Object2ObjectOpenHashMap<>();

    public final HashSet<TNotableElement> notableElements = new HashSet<>();

    @Override
    public List<RoutedNode<TNotableElement, TRoutingInfo>> getPaths(TNotableElement source) {
        if (edges.containsKey(source)) {
            return edges.get(source);
        }
        return List.of();
    }

    @Override
    public void onElementAdded(TElement source) {
        TNotableElement notableElement;
        if (getNotableElementClass().isInstance(source) && (notableElement = getNotableElementClass().cast(source)).isActuallyNode()){
            notableElements.add(notableElement);
        }
    }

    @Override
    public void onElementRemoved(TElement element) {
        TNotableElement notableElement;
        if (getNotableElementClass().isInstance(element) && (notableElement = getNotableElementClass().cast(element)).isActuallyNode()){
            notableElements.remove(notableElement);
        }
    }

    public void updateEdges() {
        edges.clear();

        for (TNotableElement notableElement : notableElements) {
            edges.put(notableElement, makePaths(notableElement));
        }
    }

    public abstract int sort(RoutedNode<TNotableElement, TRoutingInfo> a, RoutedNode<TNotableElement, TRoutingInfo> b);

    public abstract Class<TNotableElement> getNotableElementClass();

    private List<RoutedNode<TNotableElement, TRoutingInfo>> makePaths(TNotableElement source) {
        List<RoutedNode<TNotableElement, TRoutingInfo>> sourcePaths = new ArrayList<>(source.getRoutedNeighbours());
        sourcePaths.sort(this::sort);
        return sourcePaths;
    }
}
