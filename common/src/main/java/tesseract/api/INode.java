package tesseract.api;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRoutingInfo;

import java.util.ArrayList;
import java.util.List;

public interface INode<TSelf extends INode<TSelf, TRoutingInfo, TElement, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IConnectable<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TSelf, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TSelf, TRoutingInfo, TNetwork>> extends IConnectable<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, INotableFactoryElement<TSelf, TRoutingInfo, TElement, TNetwork, TGrid> {
    @Override
    default List<Pair<TSelf, TRoutingInfo>> getRoutedNeighbours(){
        List<Pair<TSelf, TRoutingInfo>> list = new ArrayList<>();
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

    default void addNeighbor(Direction side, BlockEntity from, List<Pair<TSelf, TRoutingInfo>> list, List<TElement> pathSoFar){
        BlockEntity neighbor = from.getLevel().getBlockEntity(from.getBlockPos().relative(side));
        if (neighbor != null && getElementClass().isInstance(from)) {
            TElement fromElement = getElementClass().cast(neighbor);
            if(getSelfClass().isInstance(neighbor)){
                TSelf self = getSelfClass().cast(neighbor);
                if (pathSoFar.isEmpty()) {
                    return;
                }
                if (self.isOutput(side.getOpposite())) return;
                TRoutingInfo routingInfo = createRoutingInfo(pathSoFar, side.getOpposite());
                list.add(Pair.of(self, routingInfo));
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
