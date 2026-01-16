package org.gtreimagined.tesseract.api.wrapper;

import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import org.bson.BsonDocument;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;

public class ItemStackWrapper implements TesseractItemContext {
    private ItemStack stack;
    public ItemStackWrapper(ItemStack stack){
        this.stack = stack;
    }

    @Override
    public Item getItem() {
        return stack.getItem();
    }

    @Override
    public BsonDocument getMetaData() {
        return stack.getMetadata();
    }

    @Override
    public int getCount() {
        return stack.getQuantity();
    }

    @Override
    public void setItemStack(ItemStack stack) {

    }

    @Override
    public ItemStack getItemStack() {
        return stack;
    }
}
