package org.gtreimagined.tesseract.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.gtreimagined.tesseract.Tesseract;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;
import org.gtreimagined.tesseract.api.fe.FEGrid;
import org.gtreimagined.tesseract.api.forge.Provider;
import org.gtreimagined.tesseract.api.forge.TesseractCaps;
import org.gtreimagined.tesseract.api.eu.EUGrid;
import org.gtreimagined.tesseract.api.hu.HUGrid;
import org.gtreimagined.tesseract.api.wrapper.ItemStackWrapper;
import org.gtreimagined.tesseract.api.eu.IEnergyItem;

@Mod(Tesseract.API_ID)
public class TesseractImpl extends Tesseract {
    public TesseractImpl() {

        Tesseract.init();
        MinecraftForge.EVENT_BUS.addListener(this::serverStoppedEvent);
        MinecraftForge.EVENT_BUS.addListener(this::worldUnloadEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onServerLevelTick);
        MinecraftForge.EVENT_BUS.addListener(this::onServerTick);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::onAttachCapabilitiesEventItemStack);
    }

    public void onAttachCapabilitiesEventItemStack(AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() instanceof IEnergyItem energyItem){
            TesseractItemContext context = new ItemStackWrapper(event.getObject());
            event.addCapability(new ResourceLocation(Tesseract.API_ID, "energy_items"), new Provider<>(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM, energyItem.canCreate(context) ? () -> energyItem.createEnergyHandler(context) : null));
        }
    }

    public void serverStoppedEvent(ServerStoppedEvent e) {
        firstTick.clear();
        //FE_ENERGY.clear();
        //GraphWrapper.getWrappers().forEach(GraphWrapper::clear);
    }

    public void worldUnloadEvent(WorldEvent.Unload e) {
        if (!(e.getWorld() instanceof Level) || ((Level) e.getWorld()).isClientSide) return;
        //FE_ENERGY.removeWorld((World) e.getWorld());
        //GraphWrapper.getWrappers().forEach(g -> g.removeWorld((Level)e.getWorld()));
        firstTick.remove(e.getWorld());
    }

    private void onServerTick(TickEvent.ServerTickEvent e) {
        if (e.phase == Phase.START && e.side == LogicalSide.SERVER) {
            EUGrid.INSTANCE.tick();
            HUGrid.INSTANCE.tick();
            FEGrid.INSTANCE.tick();
        }
    }

    public void onServerLevelTick(TickEvent.WorldTickEvent event) {
        if (event.side.isClient()) return;
        Level dim = event.world;
        if (!hadFirstTick(dim)) {
            firstTick.add(event.world);
            //GraphWrapper.getWrappers().forEach(t -> t.onFirstTick(dim));
        }
        if (event.phase == TickEvent.Phase.START) {
            //GraphWrapper.getWrappers().forEach(t -> t.tick(dim));
        }
        if (Tesseract.HEALTH_CHECK_TIME > 0 && event.world.getGameTime() % Tesseract.HEALTH_CHECK_TIME == 0) {
            //GraphWrapper.getWrappers().forEach(GraphWrapper::healthCheck);
        }
    }
}
