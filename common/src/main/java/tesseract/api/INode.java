package tesseract.api;

import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRoutingInfo;

public interface INode<TSelf extends INode<TSelf, TRoutingInfo, TElement, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IConnectable<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TSelf, TRoutingInfo, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TSelf, TRoutingInfo, TNetwork>> extends IConnectable<TElement, TSelf, TRoutingInfo, TNetwork, TGrid>, INotableFactoryElement<TSelf, TRoutingInfo, TElement, TNetwork, TGrid> {
}
