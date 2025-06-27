package tesseract.api.heat;

import it.unimi.dsi.fastutil.Pair;
import tesseract.graph.IPath;
import tesseract.graph.standard.StandardRouteTracker;

public class HeatRouteTracker extends StandardRouteTracker<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatNetwork, HeatGrid> {
    @Override
    public IPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatNetwork, HeatGrid> createPath(Pair<IHeatNode, HeatRoutingInfo> pair) {
        return new HeatPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatNetwork, HeatGrid> a, IPath<HeatRoutingInfo, IHeatNode, IHeatPipe, HeatNetwork, HeatGrid> b) {
        return 0;
    }

    @Override
    public Class<IHeatNode> getNotableElementClass() {
        return IHeatNode.class;
    }
}
