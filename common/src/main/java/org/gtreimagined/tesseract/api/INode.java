package org.gtreimagined.tesseract.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtreimagined.tesseract.graph.IElement;
import org.gtreimagined.tesseract.graph.IGrid;
import org.gtreimagined.tesseract.graph.INetwork;
import org.gtreimagined.tesseract.graph.INotableElement;
import org.gtreimagined.tesseract.graph.IRoutingInfo;
import org.gtreimagined.tesseract.graph.RoutedNode;

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
                    addNeighbor(direction, (TElement) this, list, List.of());
                }
            }
        }
        return list;
    }

    boolean isOutput(Direction direction);

    default void addNeighbor(Direction side, TElement from, List<RoutedNode<TSelf, TRoutingInfo>> list, List<TElement> pathSoFar){
        BlockEntity fromBE = from.getBlockEntity();
        if (fromBE == null) return;
        BlockEntity neighbor = fromBE.getLevel().getBlockEntity(fromBE.getBlockPos().relative(side));
        if (neighbor != null) {
            TSelf self;
            if(getSelfClass().isInstance(neighbor) && (self = getSelfClass().cast(neighbor)).isActuallyNode()){
                if (pathSoFar.isEmpty()) {
                    return;
                }
                if (self.isOutput(side.getOpposite())) return;
                if (!from.connects(side) || !self.connects(side.getOpposite())) return;
                TRoutingInfo routingInfo = createRoutingInfo(pathSoFar, side.getOpposite());
                list.add(new RoutedNode<>(self, routingInfo));
            } else if (getElementClass().isInstance(neighbor)) {
                TElement to = getElementClass().cast(neighbor);
                boolean fromConnects = from.connects(side);
                boolean toConnects = to.connects(side.getOpposite());
                if (fromConnects && toConnects && !pathSoFar.contains(to)){
                    for (Direction direction : Direction.values()) {
                        if (direction != side.getOpposite()){
                            addNeighbor(direction, to, list, ImmutableList.<TElement>builder().addAll(pathSoFar).add(to).build());
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
