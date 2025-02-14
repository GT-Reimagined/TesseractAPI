package tesseract.api.fe;

import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tesseract.Tesseract;
import tesseract.api.Controller;
import tesseract.api.ITickingController;
import tesseract.api.capability.ITransactionModifier;
import tesseract.graph.Graph;
import tesseract.graph.Grid;
import tesseract.graph.INode;
import tesseract.graph.NodeCache;
import tesseract.graph.Path;
import tesseract.util.Node;
import tesseract.util.Pos;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Class acts as a controller in the group of a energy components.
 */
public class FEController extends Controller<FETransaction, IFECable, IFENode> {

    private long totalEnergy, lastEnergy;
    private final Long2LongMap holders = new Long2LongOpenHashMap();
    private final Long2ObjectMap<Map<Direction, List<FEConsumer>>> data = new Long2ObjectLinkedOpenHashMap<>();

    /**
     * Creates instance of the controller.
     *
     * @param world The world.
     */
    public FEController(Level world, Graph.INodeGetter<IFENode> getter) {
        super(world, getter);
        holders.defaultReturnValue(-1L);
    }

    /**
     * Executes when the group structure has changed.
     * <p>
     * First, it clears previous controller map, after it lookup for the position of node and looks for the around grids.
     * Second, it collects all producers and collectors for the grid and stores it into data map.
     * Finally, it will pre-build consumer objects which are available for the producers. So each producer has a list of possible
     * consumers with unique information about paths, loss, ect.
     * </p>
     *
     * @see Grid (Cache)
     */
    @Override
    public void change() {
        if (!changeInternal()) {
            Tesseract.LOGGER.warn("Error during FEController::change.");
        }
    }

    private boolean changeInternal() {
        data.clear();
        for (Long2ObjectMap.Entry<NodeCache<IFENode>> e : group.getNodes().long2ObjectEntrySet()) {
            long pos = e.getLongKey();
            for (Map.Entry<Direction, IFENode> tup : e.getValue().values()) {
                IFENode producer = tup.getValue();
                Direction direction = tup.getKey();
                if (producer.canExtract(direction)) {
                    long side = Pos.offset(pos, direction);
                    List<FEConsumer> consumers = new ObjectArrayList<>();

                    Grid<IFECable> grid = group.getGridAt(side, direction);
                    if (grid != null) {
                        for (Path<IFECable> path : grid.getPaths(pos)) {
                            if (!path.isEmpty()) {
                                Node target = path.target();
                                assert target != null;
                                if (!onCheck(producer, consumers, path, target.asLong(), target.getDirection()))
                                    return false;
                            }
                        }
                    } else if (group.getNodes().containsKey(side)) {
                        onCheck(producer, consumers, null, side, direction.getOpposite());
                    }
                    if (!consumers.isEmpty())
                        data.computeIfAbsent(pos, m -> new EnumMap<>(Direction.class))
                                .put(direction.getOpposite(), consumers);
                }
            }
        }

        for (Map<Direction, List<FEConsumer>> map : data.values()) {
            for (List<FEConsumer> consumers : map.values()) {
                consumers.sort(FEConsumer.COMPARATOR);
            }
        }
        return true;
    }

    /**
     * Merge the existing consumers with new ones.
     *
     * @param producer  The producer node.
     * @param consumers The consumer nodes.
     */
    private void onMerge(IFENode producer, List<FEConsumer> consumers) {
        /*List<FEConsumer> existingConsumers = data.get(producer);
        for (FEConsumer c : consumers) {
            boolean found = false;
            for (FEConsumer ec : existingConsumers) {
                if (ec.getNode() == c.getNode()) {
                    found = true;
                }
                if (!found) existingConsumers.add(c);
            }
        }*/
    }

    /**
     * Adds available consumers to the list.
     *
     * @param consumers The consumer nodes.
     * @param path      The paths to consumers.
     * @param pos       The position of the producer.
     */
    private boolean onCheck(IFENode producer, List<FEConsumer> consumers, Path<IFECable> path, long pos, Direction direction) {
        IFENode node = group.getNodes().get(pos).value(direction);
        if (node.canReceive(direction)) {
            consumers.add(new FEConsumer(node, producer, path));
        }
        return true;
    }

    /**
     * Call on the updates to send energy.
     * <p>
     * Most of the magic going in producer class which acts as wrapper double it around controller map.
     * Firstly, method will look for the available producer and consumer.
     * Secondly, some amperage calculation is going using the consumer and producer data.
     * Thirdly, it will check the voltage and amperage for the single impulse by the lowest cost cable.
     * </p>
     * If that function will find corrupted cables, it will execute loop to find the corrupted cables and exit.
     * However, if corrupted cables wasn't found, it will looks for variate connection type and store the amp for that path.
     * After energy was send, loop will check the amp holder instances on ampers map to find cross-nodes where amps/voltage is exceed max limit.
     */
    @Override
    public void tick() {
        super.tick();
        /*holders.clear();

        for (Object2ObjectMap.Entry<IFENode, List<FEConsumer>> e : data.object2ObjectEntrySet()) {
            IFENode producer = e.getKey();

            long outputEnergy = Math.min(producer.getStoredEnergy(), producer.maxExtract());
            if (outputEnergy <= 0L) {
                continue;
            }

            for (FEConsumer consumer : e.getValue()) {

                long extracted = producer.extractEnergy(outputEnergy, true);
                if (extracted <= 0L) {
                    break;
                }

                long inserted = consumer.insert(extracted, true);
                if (inserted <= 0L) {
                    continue;
                }

                // Stores the energy into holder for path only for variate connection
                switch (consumer.getConnection()) {
                    case SINGLE:
                        long min = consumer.getMinCapacity(); // Fast check by the lowest cost cable
                        if (min < inserted) {
                            inserted = min;
                        }
                        break;

                    case VARIATE:
                        long limit = inserted;
                        for (Long2ObjectMap.Entry<IFECable> p : consumer.getCross().long2ObjectEntrySet()) {
                            long pos = p.getLongKey();
                            IFECable cable = p.getValue();

                            long capacity = holders.get(pos);
                            if (capacity == -1L) {
                                capacity = cable.getCapacity();
                                holders.put(pos, capacity);
                            }
                            limit = Math.min(limit, capacity);
                        }

                        if (limit > 0) {
                            for (long pos : consumer.getCross().keySet()) {
                                holders.put(pos, Math.max(holders.get(pos) - limit, 0L));
                            }
                        }

                        inserted = limit;
                        break;
                }

                if (inserted <= 0L) {
                    continue;
                }

                extracted = producer.extractEnergy(inserted, false);

                consumer.insert(extracted, false);

                totalEnergy += inserted;

                outputEnergy -= inserted;
                if (outputEnergy <= 0L) {
                    break;
                }
            }
        }*/
    }

    // @Override
    // public int insert(long producerPos, long direction, Integer stack, boolean simulate) {
    //    return 0;
    // }

    @Override
    protected void onFrame() {
        lastEnergy = totalEnergy;
        totalEnergy = 0L;
    }

    @Override
    public void getInfo(long pos, @NotNull List<String> list) {
        this.group.getGroupInfo(pos, list);
        list.add(String.format("FE Data size: %d", this.data.size()));
    }

    /*@Override
    public void insert(long producerPos, long pipePos, Integer transaction) {

    }*/

    @Override
    public ITickingController clone(INode group) {
        return new FEController(dim, getter).set(group);
    }

    @Override
    public void insert(long pipePos, Direction side, FETransaction transaction, ITransactionModifier modifier) {
        Map<Direction, List<FEConsumer>> map = this.data.get(Pos.offset(pipePos, side));
        if (map == null)
            return;
        List<FEConsumer> list = map.get(side);
        if (list == null)
            return;

        for (FEConsumer consumer : list) {
            int added = consumer.insert(Math.min(transaction.rf, consumer.getNode().maxInsert()), true);
            if (added <= 0) continue;
            transaction.addData(added, rf -> consumer.insert(rf, false));
        }
    }
}