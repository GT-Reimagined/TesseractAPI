package org.gtreimagined.tesseract;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.World;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

public class Tesseract extends JavaPlugin {
    protected final static Set<World> firstTick = new ObjectOpenHashSet<>();

    public static final String API_ID = "tesseractapi";

    public static boolean TEST = false;
    public static final HytaleLogger LOGGER = HytaleLogger.get(API_ID);
    public Tesseract(@NonNull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
    }

    public static boolean hadFirstTick(World world) {
        return TEST || firstTick.contains(world);
    }
}
