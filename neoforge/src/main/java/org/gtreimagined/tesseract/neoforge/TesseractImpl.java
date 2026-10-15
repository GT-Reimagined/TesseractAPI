package org.gtreimagined.tesseract.neoforge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.gtreimagined.tesseract.Tesseract;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;
import org.gtreimagined.tesseract.api.fe.FEGrid;
import org.gtreimagined.tesseract.api.neoforge.Provider;
import org.gtreimagined.tesseract.api.neoforge.TesseractCaps;
import org.gtreimagined.tesseract.api.eu.EUGrid;
import org.gtreimagined.tesseract.api.hu.HUGrid;
import org.gtreimagined.tesseract.api.wrapper.ItemStackWrapper;
import org.gtreimagined.tesseract.api.eu.IEnergyItem;

@Mod(Tesseract.API_ID)
public class TesseractImpl extends Tesseract {
    public TesseractImpl() {

        Tesseract.init();
        NeoForge.EVENT_BUS.addListener(this::serverStoppedEvent);
        NeoForge.EVENT_BUS.addListener(this::worldUnloadEvent);
        NeoForge.EVENT_BUS.addListener(this::onServerLevelTick);
        NeoForge.EVENT_BUS.addListener(this::onServerTick);
        NeoForge.EVENT_BUS.addGenericListener(ItemStack.class, this::onAttachCapabilitiesEventItemStack);
    }

    public void onAttachCapabilitiesEventItemStack(AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() instanceof IEnergyItem energyItem){
            TesseractItemContext context = new ItemStackWrapper(event.getObject());
            event.addCapability(ResourceLocation.fromNamespaceAndPath(Tesseract.API_ID, "energy_items"), new Provider<>(TesseractCaps.ENERGY_HANDLER_CAPABILITY_ITEM, energyItem.canCreate(context) ? () -> energyItem.createEnergyHandler(context) : null));
        }
    }

    public void serverStoppedEvent(ServerStoppedEvent e) {
        firstTick.clear();
        //FE_ENERGY.clear();
        //GraphWrapper.getWrappers().forEach(GraphWrapper::clear);
    }

    public void worldUnloadEvent(LevelEvent.Unload e) {
        if (!(e.getLevel() instanceof Level) || ((Level) e.getLevel()).isClientSide) return;
        //FE_ENERGY.removeWorld((World) e.getWorld());
        //GraphWrapper.getWrappers().forEach(g -> g.removeWorld((Level)e.getWorld()));
        firstTick.remove(e.getLevel());
    }

    private void onServerTick(TickEvent.ServerTickEvent e) {
        if (e.phase == Phase.START && e.side == LogicalSide.SERVER) {
            EUGrid.INSTANCE.tick();
            HUGrid.INSTANCE.tick();
            FEGrid.INSTANCE.tick();
        }
    }

    public void onServerLevelTick(TickEvent.LevelTickEvent event) {
        if (event.side.isClient()) return;
        Level dim = event.level;
        if (!hadFirstTick(dim)) {
            firstTick.add(event.level);
            //GraphWrapper.getWrappers().forEach(t -> t.onFirstTick(dim));
        }
        if (event.phase == TickEvent.Phase.START) {
            //GraphWrapper.getWrappers().forEach(t -> t.tick(dim));
        }
        if (Tesseract.HEALTH_CHECK_TIME > 0 && event.level.getGameTime() % Tesseract.HEALTH_CHECK_TIME == 0) {
            //GraphWrapper.getWrappers().forEach(GraphWrapper::healthCheck);
        }
    }
}
