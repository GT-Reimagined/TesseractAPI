package tesseract.api.heat;

import it.unimi.dsi.fastutil.Pair;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

public class HeatFactoryRouteTracker extends StandardFactoryRouteTracker<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> {
    @Override
    public IFactoryPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> createPath(Pair<IHeatNode, HeatRoutingInfo> pair) {
        return new HeatFactoryPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IFactoryPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> a, IFactoryPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> b) {
        return 0;
    }

    @Override
    public Class<IHeatNode> getNotableElementClass() {
        return IHeatNode.class;
    }
}
