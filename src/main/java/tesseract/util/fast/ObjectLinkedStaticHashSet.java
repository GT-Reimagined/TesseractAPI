package tesseract.util.fast;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import java.util.Optional;

/**
 * Wrapper for a ObjectSet class with custom hashing.
 */
public class ObjectLinkedStaticHashSet<K> extends ObjectLinkedOpenHashSet<K> {

    private Integer hash = null;

    /**
     * Sets the hash for the object.
     * @param hash The hash index.
     */
    public void setHash(int hash) {
        this.hash = hash;
    }

    /**
     * @return Gets the null save hash of the object.
     */
    public Optional<Integer> getHash() {
        return Optional.ofNullable(hash);
    }
}
