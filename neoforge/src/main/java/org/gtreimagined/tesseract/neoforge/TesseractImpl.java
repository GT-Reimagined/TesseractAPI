package org.gtreimagined.tesseract.neoforge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.gtreimagined.tesseract.Tesseract;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;
import org.gtreimagined.tesseract.api.fe.FEGrid;
import org.gtreimagined.tesseract.api.eu.EUGrid;
import org.gtreimagined.tesseract.api.hu.HUGrid;
import org.gtreimagined.tesseract.api.neoforge.TesseractCaps.EnergyHandler;
import org.gtreimagined.tesseract.api.wrapper.ItemStackWrapper;
import org.gtreimagined.tesseract.api.eu.IEnergyItem;

@Mod(Tesseract.API_ID)
public class TesseractImpl extends Tesseract {
    public TesseractImpl(IEventBus eventBus) {

        Tesseract.init();
        NeoForge.EVENT_BUS.addListener(this::serverStoppedEvent);
        NeoForge.EVENT_BUS.addListener(this::worldUnloadEvent);
        NeoForge.EVENT_BUS.addListener(this::onServerLevelTick);
        NeoForge.EVENT_BUS.addListener(this::onServerTick);
        eventBus.addListener(this::registerCapabilitiesEvent);
    }

    public void registerCapabilitiesEvent(RegisterCapabilitiesEvent event){
        for (Item item : BuiltInRegistries.ITEM){
            if (item instanceof IEnergyItem energyItem){
                event.registerItem(EnergyHandler.ITEM, (s, c) -> {
                    TesseractItemContext context = new ItemStackWrapper(s);
                    if (!energyItem.canCreate(context)) return null;
                    return energyItem.createEnergyHandler(context);
                }, item);
            }
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

    private void onServerTick(ServerTickEvent.Pre e) {
        EUGrid.INSTANCE.tick();
        HUGrid.INSTANCE.tick();
        FEGrid.INSTANCE.tick();
    }

    public void onServerLevelTick(LevelTickEvent event) {
        if (event.getLevel().isClientSide()) return;
        Level dim = event.getLevel();
        if (!hadFirstTick(dim)) {
            firstTick.add(event.getLevel());
            //GraphWrapper.getWrappers().forEach(t -> t.onFirstTick(dim));
        }
        if (event instanceof Pre) {
            //GraphWrapper.getWrappers().forEach(t -> t.tick(dim));
        }
        if (Tesseract.HEALTH_CHECK_TIME > 0 && event.getLevel().getGameTime() % Tesseract.HEALTH_CHECK_TIME == 0) {
            //GraphWrapper.getWrappers().forEach(GraphWrapper::healthCheck);
        }
    }
}
