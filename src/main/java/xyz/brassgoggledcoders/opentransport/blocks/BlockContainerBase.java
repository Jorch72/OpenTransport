package xyz.brassgoggledcoders.opentransport.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.opentransport.OpenTransport;
import xyz.brassgoggledcoders.opentransport.api.blockcontainers.IBlockContainer;
import xyz.brassgoggledcoders.opentransport.api.entities.IHolderEntity;
import xyz.brassgoggledcoders.opentransport.api.blockcontainers.IInteraction;
import xyz.brassgoggledcoders.opentransport.renderers.RenderType;

public class BlockContainerBase implements IBlockContainer
{
	Block block;
	IBlockState blockState;
	TileEntity tileEntity;
	boolean hasTileEntity;
	World world;
	String unlocalizedName;
	IInteraction clickInteraction;
	RenderType renderType = RenderType.VMC;
	boolean isDirty;

	public BlockContainerBase(Block block)
	{
		this.block = block;
		this.blockState = block.getDefaultState();
		this.unlocalizedName = block.getUnlocalizedName().replaceFirst("tile.", "");
	}

	public BlockContainerBase setBlock(Block block)
	{
		this.block = block;
		this.blockState = block.getDefaultState();
		return this;
	}

	public BlockContainerBase setBlockState(IBlockState blockState)
	{
		this.block = blockState.getBlock();
		this.blockState = blockState;
		return this;
	}

	public BlockContainerBase setUnlocalizedName(String name)
	{
		this.unlocalizedName = name.replaceFirst("tile.", "");
		return this;
	}

	public BlockContainerBase setClickInteraction(IInteraction interaction)
	{
		this.clickInteraction = interaction;
		return this;
	}

	public BlockContainerBase setRenderType(RenderType renderType)
	{
		this.renderType = renderType;
		return this;
	}

	@Override
	public Block getBlock()
	{
		return block;
	}

	@Override
	public IBlockState getBlockState()
	{
		return blockState;
	}

	@Override
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}

	@Override
	public RenderType getRenderType()
	{
		return renderType;
	}

	@Override
	public IInteraction getClickInteraction()
	{
		return clickInteraction;
	}

	@Override
	public boolean onInteract(EntityPlayer entityPlayer, IHolderEntity entity)
	{
		EntityPlayer entityPlayerWrapper = OpenTransport.PROXY.getEntityPlayerWrapper(entityPlayer, entity);
		return this.getClickInteraction() != null && this.getClickInteraction().interact(entityPlayerWrapper, this);
	}

	@Override
	public void tick()
	{
		if(world != null && this.getTileEntity() != null)
		{
			if(this.getTileEntity() instanceof ITickable)
			{
				((ITickable) this.getTileEntity()).update();
			}
		}
	}

	@Override
	public void markDirty()
	{
		isDirty = true;
	}

	@Override
	public void setWorld(World world)
	{
		this.world = world;
	}

	@Override
	public boolean hasTileEntity()
	{
		return hasTileEntity;
	}

	@Override
	public TileEntity getTileEntity()
	{
		if(this.tileEntity == null)
		{
			this.tileEntity = this.getBlock().createTileEntity(this.world, this.getBlockState());
		}
		return this.tileEntity;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		if(world != null && this.getTileEntity() != null)
		{
			tagCompound.setTag("TILE_DATA", this.getTileEntity().writeToNBT(new NBTTagCompound()));
		}
		return tagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		if(world != null && this.getTileEntity() != null)
		{
			if(tagCompound.hasKey("TILE_DATA"))
			{
				this.getTileEntity().readFromNBT(tagCompound.getCompoundTag("TILE_DATA"));
			}
		}
	}

	@Override
	public IBlockContainer copy()
	{
		BlockContainerBase copyBlockContainer = new BlockContainerBase(this.getBlock());
		copyBlockContainer.setBlockState(this.getBlockState()).setClickInteraction(this.getClickInteraction())
				.setUnlocalizedName(this.getUnlocalizedName()).setRenderType(this.getRenderType()).setWorld(this.world);
		return copyBlockContainer;
	}
}