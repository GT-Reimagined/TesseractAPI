package tesseract.graph;

import tesseract.api.IConnectable;
import tesseract.util.Dir;

/**
 * The Cache is a class that should work with connections.
 */
public class Cache<T extends IConnectable> {

    private final byte connectivity;
    private final T value;

    /**
     * Creates a cache instance.
     */
    public Cache(T value) {
        this.value = value;
        this.connectivity = Connectivity.of(value);
    }

    /**
     * Creates a cache instance from a delegate.
     */
    public Cache(T value, IConnectable delegate) {
        this.value = value;
        this.connectivity = Connectivity.of(delegate);
    }

    /**
     * @param direction The direction index.
     * @return True when connect, false otherwise.
     */
    public boolean connects(Dir direction) {
        return Connectivity.has(connectivity, direction.getIndex());
    }

    /**
     * @return Gets the connection state.
     */
    public byte connectivity() {
        return connectivity;
    }

    /**
     * @return Gets the cache.
     */
    public T value() {
        return value;
    }
}