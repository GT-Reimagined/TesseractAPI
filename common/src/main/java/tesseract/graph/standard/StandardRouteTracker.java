package tesseract.graph.standard;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.Pair;
import tesseract.Tesseract;
import tesseract.graph.IElement;
import tesseract.graph.IGrid;
import tesseract.graph.INetwork;
import tesseract.graph.IPath;
import tesseract.graph.INotableElement;
import tesseract.graph.IRouteTracker;
import tesseract.graph.IRoutingInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class StandardRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IElement<TElement, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNetwork extends INetwork<TNetwork, TElement, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TNetwork>> implements IRouteTracker<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> {
    Cache<TNotableElement, List<IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>>> paths = CacheBuilder.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS).build();

    public final HashSet<TNotableElement> notableElements = new HashSet<>();

    @Override
    public List<IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> getPaths(TNotableElement source) {
        try {
            return paths.get(source, () -> makePaths(source));
        } catch (ExecutionException e) {
            Tesseract.LOGGER.error(e);
            return List.of();
        }
    }

    @Override
    public void onElementAdded(TElement source) {
        TNotableElement notableElement;
        if (getNotableElementClass().isInstance(source) && (notableElement = getNotableElementClass().cast(source)).isActuallyNode()){
            notableElements.add(notableElement);
        }
        paths.invalidateAll();
    }

    @Override
    public void onElementRemoved(TElement element) {
        TNotableElement notableElement;
        if (getNotableElementClass().isInstance(element) && (notableElement = getNotableElementClass().cast(element)).isActuallyNode()){
            notableElements.remove(notableElement);
        }
        paths.invalidateAll();
    }

    public abstract IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> createPath(Pair<TNotableElement, TRoutingInfo> pair);

    public abstract int sort(IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> a, IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> b);

    public abstract Class<TNotableElement> getNotableElementClass();

    private List<IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> makePaths(TNotableElement source) {
        List<IPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> paths = new ArrayList<>();
        List<Pair<TNotableElement, TRoutingInfo>> sourcePaths = source.getRoutedNeighbours();
        sourcePaths.forEach(p -> paths.add(createPath(p)));
        paths.sort(this::sort);
        return paths;
    }
}
