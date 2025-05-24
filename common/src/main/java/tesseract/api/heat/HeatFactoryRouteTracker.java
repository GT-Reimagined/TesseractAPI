package tesseract.api.heat;

import it.unimi.dsi.fastutil.Pair;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

import java.util.List;

public class HeatFactoryRouteTracker extends StandardFactoryRouteTracker<HeatRoutingInfo, IHeatNodeBlock, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> {
    @Override
    public IFactoryPath<HeatRoutingInfo, IHeatNodeBlock, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> createPath(Pair<IHeatNodeBlock, HeatRoutingInfo> pair, List<IHeatPipe> iHeatPipes) {
        return new HeatFactoryPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IFactoryPath<HeatRoutingInfo, IHeatNodeBlock, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> a, IFactoryPath<HeatRoutingInfo, IHeatNodeBlock, IHeatPipe, HeatFactoryNetwork, HeatFactoryGrid> b) {
        return 0;
    }
}
