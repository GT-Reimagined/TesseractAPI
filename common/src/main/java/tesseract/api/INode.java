package tesseract.api;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import tesseract.graph.IElement;
import tesseract.graph.IGrid;
import tesseract.graph.INetwork;
import tesseract.graph.INotableElement;
import tesseract.graph.IRoutingInfo;
import tesseract.graph.RoutedNode;

import java.util.ArrayList;
import java.util.List;

public interface INode<TSelf extends INode<TSelf, TRoutingInfo, TElement, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IElement<TElement, TSelf, TRoutingInfo, TNetwork, TGrid> & IConnectable, TNetwork extends INetwork<TNetwork, TElement, TSelf, TRoutingInfo, TGrid>, TGrid extends IGrid<TGrid, TElement, TSelf, TRoutingInfo, TNetwork>> extends IElement<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, INotableElement<TSelf, TRoutingInfo, TElement, TNetwork, TGrid>, IConnectable {
    @Override
    default List<RoutedNode<TSelf, TRoutingInfo>> getRoutedNeighbours(){
        List<RoutedNode<TSelf, TRoutingInfo>> list = new ArrayList<>();
        if (!isActuallyNode()) return list;
        for (Direction direction : Direction.values()) {
            if (isOutput(direction)){
                BlockEntity source = getBlockEntity();
                if (source != null) {
                    addNeighbor(direction, source, list, List.of());
                }
            }
        }
        return list;
    }

    boolean isOutput(Direction direction);

    default void addNeighbor(Direction side, BlockEntity from, List<RoutedNode<TSelf, TRoutingInfo>> list, List<TElement> pathSoFar){
        BlockEntity neighbor = from.getLevel().getBlockEntity(from.getBlockPos().relative(side));
        if (neighbor != null && getElementClass().isInstance(from)) {
            TElement fromElement = getElementClass().cast(neighbor);
            TSelf self;
            if(getSelfClass().isInstance(neighbor) && (self = getSelfClass().cast(fromElement)).isActuallyNode()){
                if (pathSoFar.isEmpty()) {
                    return;
                }
                if (self.isOutput(side.getOpposite())) return;
                TRoutingInfo routingInfo = createRoutingInfo(pathSoFar, side.getOpposite());
                list.add(new RoutedNode<>(self, routingInfo));
            } else if (getElementClass().isInstance(neighbor)) {
                TElement element = getElementClass().cast(neighbor);
                if (fromElement.connects(side) && element.connects(side.getOpposite()) && !pathSoFar.contains(element)){
                    for (Direction direction : Direction.values()) {
                        if (direction != side.getOpposite()){
                            addNeighbor(direction, neighbor, list, ImmutableList.<TElement>builder().addAll(pathSoFar).add(element).build());
                        }
                    }
                }
            }
        }
    }

    Class<TSelf> getSelfClass();

    Class<TElement> getElementClass();



    TRoutingInfo createRoutingInfo(List<TElement> pathSoFar, Direction side);
}
