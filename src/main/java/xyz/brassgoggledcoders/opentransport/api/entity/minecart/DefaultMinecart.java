package xyz.brassgoggledcoders.opentransport.api.entity.minecart;

import net.minecraft.client.model.ModelMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DefaultMinecart extends CustomMinecart {
    public DefaultMinecart() {
        this.setRegistryName("opentransport","minecart");
    }

    @Override
    public ItemStack getItemMinecart() {
        return new ItemStack(Items.MINECART);
    }

    @Override
    public EntityMinecart getEmptyMinecart(World world) {
        return new EntityMinecartEmpty(world);
    }

    @SideOnly(Side.CLIENT)
    private ModelMinecart modelMinecart;

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMinecartModel(EntityMinecart entityMinecart) {
        if (modelMinecart == null) {
            modelMinecart = new ModelMinecart();
        }

        modelMinecart.render(entityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    }
}
