package org.gtreimagined.tesseract.api.wrapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;
import org.jspecify.annotations.NullMarked;

@NullMarked
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
    public CompoundTag getTag() {
        return stack.getOrCreateTag();
    }

    @Override
    public int getCount() {
        return stack.getCount();
    }

    @Override
    public void setItemStack(ItemStack stack) {

    }

    @Override
    public ItemStack getItemStack() {
        return stack;
    }
}
