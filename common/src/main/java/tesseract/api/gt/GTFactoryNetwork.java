package tesseract.api.gt;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import tesseract.factory.IRouteTracker;
import tesseract.factory.standard.StandardFactoryNetwork;

public class GTFactoryNetwork extends StandardFactoryNetwork<GTFactoryNetwork, IGTCable, IGTNode, GTRoutingInfo, GTFactoryGrid> {
    public final Object2ObjectMap<ResourceLocation, LongSet> cableIsActive = new Object2ObjectLinkedOpenHashMap<>();
    @Override
    protected IRouteTracker<GTRoutingInfo, IGTNode, IGTCable, GTFactoryNetwork, GTFactoryGrid> createRouteTracker() {
        return new GTFactoryRouteTracker();
    }
}
