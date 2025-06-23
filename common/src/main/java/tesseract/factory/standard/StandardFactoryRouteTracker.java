package tesseract.factory.standard;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import it.unimi.dsi.fastutil.Pair;
import tesseract.Tesseract;
import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.IFactoryPath;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRouteTracker;
import tesseract.factory.IRoutingInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class StandardFactoryRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TNotableElement extends INotableFactoryElement<TNotableElement, TRoutingInfo, TElement, TNetwork, TGrid>, TElement extends IFactoryElement<TElement, TNotableElement, TRoutingInfo, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TNotableElement, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNotableElement, TRoutingInfo, TNetwork>> implements IRouteTracker<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> {
    Cache<TNotableElement, List<IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>>> paths = CacheBuilder.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS).build();

    public final HashSet<TNotableElement> notableElements = new HashSet<>();

    @Override
    public List<IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> getPaths(TNotableElement source) {
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

    public abstract IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> createPath(Pair<TNotableElement, TRoutingInfo> pair);

    public abstract int sort(IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> a, IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid> b);

    public abstract Class<TNotableElement> getNotableElementClass();

    private List<IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> makePaths(TNotableElement source) {
        List<IFactoryPath<TRoutingInfo, TNotableElement, TElement, TNetwork, TGrid>> paths = new ArrayList<>();
        List<Pair<TNotableElement, TRoutingInfo>> sourcePaths = source.getRoutedNeighbours();
        sourcePaths.forEach(p -> paths.add(createPath(p)));
        paths.sort(this::sort);
        return paths;
    }
}
