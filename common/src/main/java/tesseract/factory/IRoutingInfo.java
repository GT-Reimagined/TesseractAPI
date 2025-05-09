package tesseract.factory;

public interface IRoutingInfo<TSelf extends IRoutingInfo<TSelf>> {
    /** Merges the distances/etc of two routing infos and returns a copy. */
    TSelf merge(TSelf other);
    /** Can this edge send stuff from the notable factory element to the connection? */
    //boolean canSend();
    /** Opposite of canSend */
    //boolean canReceive();
}
