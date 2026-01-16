package org.gtreimagined.tesseract.api.context;


import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import org.bson.BsonDocument;

public interface TesseractItemContext {
    Item getItem();

    BsonDocument getMetaData();

    int getCount();

    void setItemStack(ItemStack stack);

    default ItemStack getItemStack(){
        return new ItemStack(getItem().getId(), getCount(), getMetaData());
    }

    default BsonDocument getOrCreateTagElement(String key) {
        if (this.getMetaData().containsKey(key)) {
            return this.getMetaData().getDocument(key);
        } else {
            BsonDocument metadata = new BsonDocument();
            this.getMetaData().put(key, metadata);
            return metadata;
        }
    }
}
