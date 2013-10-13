package mrkirby153.MscHouses.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import mrkirby153.MscHouses.core.MscHouses;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
/**
 * 
 * Msc Houses
 *
 * ItemModuel
 *
 * @author mrkirby153
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemModuel extends Item{
	/** List of all moduel names */
	public String[] module_name;
	@SideOnly(Side.CLIENT)
	private Icon[] field_94594_d;
	public ItemModuel(int par1) {
		super(par1);
		this.makeModules();
		this.setCreativeTab(MscHouses.tabHouse_moduel);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		int j = MathHelper.clamp_int(par1, 0, 15);
		return this.field_94594_d[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.field_94594_d = new Icon[module_name.length];

		for(int i =0; i < module_name.length; i++){
			this.field_94594_d[i] = par1IconRegister.registerIcon("mschouses:module");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tab,
			List itemList) {
		for(int i = 0; i < module_name.length; i++){
			itemList.add(new ItemStack(itemID,1,i));
		}
	}
	@Override
	public String getUnlocalizedName(ItemStack item) {
		return this.getUnlocalizedName(); //+ "." + this.moduelFileName[item.getItemDamage()];
	}

	public void makeModules(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(MscHouses.getConfigDir() + "/MscHouses/Houses/master.txt"));
			String line = null;
			ArrayList<String> houses = new ArrayList<String>();
			while((line = reader.readLine()) != null){
				if(line.startsWith("#"))
					continue;
				if(line.isEmpty())
					continue;
				houses.add(line);
			}
			module_name = new String[houses.toArray(new String[0]).length];
			module_name = houses.toArray(new String[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking() && par1ItemStack.getItemDamage() > 0){
			if(!par2World.isRemote){
				par3EntityPlayer.addChatMessage("Successfully Downgraded " + this.module_name[par1ItemStack.getItemDamage()] + " to " + this.module_name[0]);
			}
			par1ItemStack.setItemDamage(0);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.fizz", 8, 1);

		}
		return par1ItemStack;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if(itemStack.getItemDamage() == 0){
			list.add("Compnent to build all other modules");
		}else{
			list.add("Component to build an "+module_name[itemStack.getItemDamage()]);
		}
	}
}
