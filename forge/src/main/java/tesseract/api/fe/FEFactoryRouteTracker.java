package tesseract.api.fe;

import it.unimi.dsi.fastutil.Pair;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

public class FEFactoryRouteTracker extends StandardFactoryRouteTracker<FERoutingInfo, IFENode, IFECable, FEFactoryNetwork, FEFactoryGrid> {
    @Override
    public IFactoryPath<FERoutingInfo, IFENode, IFECable, FEFactoryNetwork, FEFactoryGrid> createPath(Pair<IFENode, FERoutingInfo> pair) {
        return new FEFactoryPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IFactoryPath<FERoutingInfo, IFENode, IFECable, FEFactoryNetwork, FEFactoryGrid> a, IFactoryPath<FERoutingInfo, IFENode, IFECable, FEFactoryNetwork, FEFactoryGrid> b) {
        return 0;
    }

    @Override
    public Class<IFENode> getNotableElementClass() {
        return IFENode.class;
    }
}
