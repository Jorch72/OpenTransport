package xyz.brassgoggledcoders.opentransport.boats;

import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xyz.brassgoggledcoders.opentransport.api.transporttypes.ClientTransportType;
import xyz.brassgoggledcoders.opentransport.api.transporttypes.IClientTransportType;
import xyz.brassgoggledcoders.opentransport.boats.entities.EntityBoatHolder;
import xyz.brassgoggledcoders.opentransport.boats.renderers.RenderHolderBoat;
import xyz.brassgoggledcoders.opentransport.boats.renderers.RenderItemHolderBoat;
import xyz.brassgoggledcoders.opentransport.boats.renderers.RenderItemHolderBoatAccessor;
import xyz.brassgoggledcoders.opentransport.renderers.TESRModel;
import xyz.brassgoggledcoders.opentransport.renderers.TESRModelLoader;

@ClientTransportType
public class BoatClientTransport extends BoatTransport implements IClientTransportType<EntityBoat> {
    @Override
    public void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBoatHolder.class, RenderHolderBoat::new);
    }

    @Override
    public void registerItemRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(RenderItemHolderBoat.DummyTile.class, new RenderItemHolderBoat());
        RenderItemHolderBoatAccessor accessor = new RenderItemHolderBoatAccessor();
        this.holderList.forEach(itemBoatHolder -> {
            ForgeHooksClient.registerTESRItemStack(itemBoatHolder, 0, RenderItemHolderBoat.DummyTile.class);
            TESRModelLoader.addTESRModel(itemBoatHolder, new TESRModel<>(itemBoatHolder, accessor));
        });
    }
}
