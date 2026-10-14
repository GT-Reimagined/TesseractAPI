package org.gtreimagined.tesseract.api.context;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface TesseractItemContext {
    Item getItem();

    CompoundTag getTag();

    int getCount();

    void setItemStack(ItemStack stack);

    default ItemStack getItemStack(){
        ItemStack stack = new ItemStack(getItem(), getCount());
        stack.setTag(getTag());
        return stack;
    }

    default CompoundTag getOrCreateTagElement(String key) {
        if (this.getTag().contains(key, 10)) {
            return this.getTag().getCompound(key);
        } else {
            CompoundTag compoundTag = new CompoundTag();
            this.getTag().put(key, compoundTag);
            return compoundTag;
        }
    }
}
