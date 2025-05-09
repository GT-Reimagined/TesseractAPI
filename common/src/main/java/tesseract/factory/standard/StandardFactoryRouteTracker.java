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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class StandardFactoryRouteTracker<TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IFactoryElement<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> implements IRouteTracker<TRoutingInfo, TElement, TNetwork, TGrid> {
    Cache<TElement, List<IFactoryPath<TRoutingInfo, TElement, TNetwork, TGrid>>> paths = CacheBuilder.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS).build();

    @Override
    public List<IFactoryPath<TRoutingInfo, TElement, TNetwork, TGrid>> getPaths(TElement source) {
        if (!(source instanceof INotableFactoryElement<?,?,?,?,?>)){
            return List.of();
        }
        try {
            return paths.get(source, () -> makePaths((INotableFactoryElement<?, TRoutingInfo, ?, TNetwork, TGrid>) source));
        } catch (ExecutionException e) {
            Tesseract.LOGGER.error(e);
            return List.of();
        }
    }

    @Override
    public void createPaths(TElement source) {
        if (!(source instanceof INotableFactoryElement<?,?,?,?,?>)){
            return;
        }
        if (paths.size() == 0){
            paths.put(source, new ArrayList<>());
        }
        paths.put(source, makePaths((INotableFactoryElement<?, TRoutingInfo, ?, TNetwork, TGrid>) source));
    }

    public abstract IFactoryPath<TRoutingInfo, TElement, TNetwork, TGrid> createPath(Pair<?, TRoutingInfo> pair);

    private List<IFactoryPath<TRoutingInfo, TElement, TNetwork, TGrid>> makePaths(INotableFactoryElement<?, TRoutingInfo, ?, TNetwork, TGrid> source) {
        List<IFactoryPath<TRoutingInfo, TElement, TNetwork, TGrid>> paths = new ArrayList<>();
        List<? extends Pair<?, TRoutingInfo>> sourcePaths = source.getRoutedNeighbours();
        sourcePaths.forEach(p -> paths.add(createPath(p)));
        return paths;
    }
}
