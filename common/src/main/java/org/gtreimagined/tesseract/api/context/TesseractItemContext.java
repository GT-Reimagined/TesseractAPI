package org.gtreimagined.tesseract.api.context;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface TesseractItemContext extends DataComponentHolder {
    Item getItem();

    int getCount();

    void setItemStack(ItemStack stack);

    default ItemStack getItemStack(){
        return getComponents() instanceof PatchedDataComponentMap map ?
                new ItemStack(Holder.direct(getItem()), getCount(), map.copy().asPatch()) :
                new ItemStack(getItem(), getCount());
    }
}
