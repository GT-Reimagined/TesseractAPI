package org.gtreimagined.tesseract.api.neoforge;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.gtreimagined.tesseract.Tesseract;
import org.gtreimagined.tesseract.api.eu.IEnergyHandler;
import org.gtreimagined.tesseract.api.eu.IEnergyHandlerItem;
import org.gtreimagined.tesseract.api.hu.IHeatHandler;
import org.jetbrains.annotations.Nullable;

public class TesseractCaps {
    public static class EnergyHandler {
        public static final BlockCapability<IEnergyHandler, @Nullable Direction> BLOCK = BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "eu"), IEnergyHandler.class);
        public static final EntityCapability<IEnergyHandler, @Nullable Direction> ENTITY = EntityCapability.createSided(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "eu"), IEnergyHandler.class);
        public static final ItemCapability<IEnergyHandlerItem, @Nullable Void> ITEM = ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "eu"), IEnergyHandlerItem.class);
    }

    public static class HeatHandler {
        public static final BlockCapability<IHeatHandler, @Nullable Direction> BLOCK = BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "hu"), IHeatHandler.class);
        public static final EntityCapability<IHeatHandler, @Nullable Direction> ENTITY = EntityCapability.createSided(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "hu"), IHeatHandler.class);
        public static final ItemCapability<IHeatHandler, @Nullable Void> ITEM = ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "hu"), IHeatHandler.class);
    }
}
