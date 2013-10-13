package mrkirby153.MscHouses.items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import mrkirby153.MscHouses.api.IHouseItem;
import mrkirby153.MscHouses.api.MaterialRegistry;
import mrkirby153.MscHouses.configuration.ConfigurationSettings;
import mrkirby153.MscHouses.core.MscHouses;
import mrkirby153.MscHouses.generation.Generation;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * Msc Houses
 *
 * ItemHouseTool
 *
 * @author mrkirby153
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemHouseTool extends Item {
	private IInventory inventory;
	public ItemHouseTool(int par1) {
		super(par1);
		this.setCreativeTab(MscHouses.tabHouse);
		this.setMaxDamage(ConfigurationSettings.House_Tool_damage);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		MovingObjectPosition p = this.getMovingObjectPositionFromPlayer(world, player, true);
		// Run verifications on the block
		if(p == null)
			return item;
		if(!(world.getBlockId(p.blockX, p.blockY, p.blockZ) == MscHouses.BlockHouseGenerator.blockID))
			return item;
		if(world.getBlockTileEntity(p.blockX, p.blockY, p.blockZ) == null)
			return item;
		inventory = (IInventory) world.getBlockTileEntity(p.blockX, p.blockY, p.blockZ);
		if(inventory == null)
			return item;
		// End verifications
		boolean fuel = false;

		if(player.capabilities.isCreativeMode)
			fuel = true;

		String moduelFile = "";
		int modifyerMaterial = 0;
		// Run verification on items
		if(inventory.getStackInSlot(0) == null){
			player.sendChatToPlayer(ChatMessageComponent.createFromText("You are missing a module"));
			return item;
		}

		if(inventory.getStackInSlot(1) == null){
			player.sendChatToPlayer(ChatMessageComponent.createFromText("You are missing fuel"));
			return item;
		}
		if(inventory.getStackInSlot(2) == null){
			player.sendChatToPlayer(ChatMessageComponent.createFromText("You are missing a material modifier"));
		}
		// End verification

		// Get modifier material
		modifyerMaterial = MaterialRegistry.materialLookup(inventory.getStackInSlot(2).getItemDamage());

		// Run verifications on the module
		if(inventory.getStackInSlot(0).getItem() instanceof IHouseItem){
			((IHouseItem)inventory.getStackInSlot(0).getItem()).geneateInit(world, p.blockX, p.blockY, p.blockZ);
		}else{
			if(inventory.getStackInSlot(0).getItem() == MscHouses.moduel){
				try{
					ItemModuel module = (ItemModuel) inventory.getStackInSlot(0).getItem();
					String fileName = module.module_name[inventory.getStackInSlot(0).getItemDamage()];
					BufferedReader reader = new BufferedReader(new FileReader(MscHouses.getConfigDir() + "/MscHouses/Houses/"+fileName+".house"));
					String line = null;
					String[] generation;
					ArrayList<String> houses = new ArrayList<String>();
					while((line = reader.readLine()) != null){
						if(line.startsWith("#"))
							continue;
						if(line.isEmpty())
							continue;
						houses.add(line);
					}
					generation = new String[houses.toArray(new String[0]).length];
					generation = houses.toArray(new String[0]);
					reader.close();
					Generation.generate(generation, modifyerMaterial, world, p.blockX, p.blockY, p.blockZ, fileName);
					inventory.setInventorySlotContents(0, null);
					inventory.setInventorySlotContents(1, null);
					inventory.setInventorySlotContents(2, null);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		return item;
	}



	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		// TODO Auto-generated method stub
		itemIcon = iconRegister.registerIcon("mschouses:HouseTool");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return true;
	}

}
