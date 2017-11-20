package com.enm.block;

import com.enm.block.util.BlockENM;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guicontainer.GuiContainerGasStorageInfo;
import com.enm.tileEntity.TileEntityGasStorageInfo;
import com.enm.tileEntityutil.GuiRegister;
import com.enm.tileEntityutil.ModDependence;
import com.enm.util.Tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGasStorageInfo extends BlockENM implements GuiRegister, ModDependence
{
	
	@Override
	public Container GetContainer(InventoryPlayer pl, TileEntity te)
	{
		return new ENMContainer();
	}

	@SideOnly(Side.CLIENT)
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te)
	{
		return new GuiContainerGasStorageInfo(pl, (TileEntityGasStorageInfo)te);
	}
	
	IIcon[] textures;
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		textures = new IIcon[]
		{
			register.registerIcon(CoreMod.MODID+":GASstorageinfoside"),
			register.registerIcon(CoreMod.MODID+":GASstorageinfopanel")
		};
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityGasStorageInfo)
		{
			((TileEntityGasStorageInfo)te).inputdir = Tools.EntityLivingBaseToForgeDirection(player, true).getOpposite();
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(side == 4) return textures[0];
		return textures[1];
	}
	
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityGasStorageInfo)
		{
			TileEntityGasStorageInfo tes = (TileEntityGasStorageInfo) te;
			if(Tools.ForgeDirectionToIntegerSide(tes.inputdir) == side)
			{
				return textures[0];
			}
		}
		return textures[1];
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float fx, float fy, float fz)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
	    if(tileEntity == null || player.isSneaking())
	    {
	    	return false;
	    }
	    player.openGui(CoreMod.instance, 0, world, x, y, z);
	    return true;
	}
	
	public BlockGasStorageInfo(Material material)
	{
		super(material);
	}

	@Override
	public String DisplayBlockName()
	{
		return "Gas Storage Info";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int id)
	{
		return new TileEntityGasStorageInfo();
	}
	
	@Override
	public String[] ModsDependence()
	{
		return new String[]{"Mekanism"};
	}

}
