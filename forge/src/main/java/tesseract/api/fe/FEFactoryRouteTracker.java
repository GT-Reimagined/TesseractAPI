package tesseract.api.fe;

import it.unimi.dsi.fastutil.Pair;
import tesseract.factory.IFactoryPath;
import tesseract.factory.standard.StandardFactoryRouteTracker;

import java.util.List;

public class FEFactoryRouteTracker extends StandardFactoryRouteTracker<FERoutingInfo, IFENodeBlock, IFECable, FEFactoryNetwork, FEFactoryGrid> {
    @Override
    public IFactoryPath<FERoutingInfo, IFENodeBlock, IFECable, FEFactoryNetwork, FEFactoryGrid> createPath(Pair<IFENodeBlock, FERoutingInfo> pair) {
        return new FEFactoryPath(pair.first(), pair.second());
    }

    @Override
    public int sort(IFactoryPath<FERoutingInfo, IFENodeBlock, IFECable, FEFactoryNetwork, FEFactoryGrid> a, IFactoryPath<FERoutingInfo, IFENodeBlock, IFECable, FEFactoryNetwork, FEFactoryGrid> b) {
        return 0;
    }

    @Override
    public Class<IFENodeBlock> getNotableElementClass() {
        return IFENodeBlock.class;
    }
}
