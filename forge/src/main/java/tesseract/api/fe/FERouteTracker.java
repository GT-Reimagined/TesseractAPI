package tesseract.api.fe;

import it.unimi.dsi.fastutil.Pair;
import tesseract.graph.IPath;
import tesseract.graph.standard.StandardRouteTracker;

public class FERouteTracker extends StandardRouteTracker<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> {
    @Override
    public IPath<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> createPath(Pair<IFENode, FERoutingInfo> pair) {
        return new FEPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IPath<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> a, IPath<FERoutingInfo, IFENode, IFECable, FENetwork, FEGrid> b) {
        return 0;
    }

    @Override
    public Class<IFENode> getNotableElementClass() {
        return IFENode.class;
    }
}
