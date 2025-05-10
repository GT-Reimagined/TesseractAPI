package tesseract.api;

import tesseract.factory.IFactoryElement;
import tesseract.factory.IFactoryGrid;
import tesseract.factory.IFactoryNetwork;
import tesseract.factory.INotableFactoryElement;
import tesseract.factory.IRoutingInfo;

public interface INode<TSelf extends INode<TSelf, TRoutingInfo, TElement, TNetwork, TGrid>, TRoutingInfo extends IRoutingInfo<TRoutingInfo>, TElement extends IConnectable<TElement, TNetwork, TGrid>, TNetwork extends IFactoryNetwork<TNetwork, TElement, TGrid>, TGrid extends IFactoryGrid<TGrid, TElement, TNetwork>> extends IConnectable<TElement, TNetwork, TGrid>, INotableFactoryElement<TSelf, TRoutingInfo, TElement, TNetwork, TGrid> {
}
