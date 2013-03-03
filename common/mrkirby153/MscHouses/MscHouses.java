package mrkirby153.MscHouses;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "MscHouses", name = "MscHouses", version = "CLOSED BETA")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MscHouses {
	public static HouseGen h= new HouseGen();
	public static final CreativeTabs tabHouse = new CreativeTabHouse(CreativeTabs.getNextID(), "MscHouses");
	public static final char COLOR_CODE = '\u00A7';
	public static int HutID;
	public static Block House_Hut;
	
	public static int HouseToolID;
	public static Item HouseTool;
	
	public static int ninebynineID;
	public static Block House_9x9;
	
	public static int villageId;
	public static Block village;
	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try{
			cfg.load();
			HutID = cfg.getBlock("Basic Hut", 700).getInt();
			HouseToolID = cfg.getItem("House Tool", 3000).getInt();
			ninebynineID = cfg.getBlock("9x9 House", 701).getInt();
			villageId = cfg.getBlock("Village Generator", 701).getInt();
		}finally{
			cfg.save();
		}
	}
	
	@Init
	public void init(FMLInitializationEvent event){
		House_Hut= new BlockHouse_Hut(HutID, 0).setBlockName("hut");
		House_9x9 = new BlockHouse_9x9(ninebynineID, 1).setBlockName("9x9");
		HouseTool = new ItemHouseTool(HouseToolID).setIconCoord(0,0).setItemName("HouseTool");
		village = new BlockVillage(villageId, 2).setBlockName("village");
		addCrafting();
		addNames();
	}
	
	private void addNames() {
		LanguageRegistry.addName(House_Hut, "Hut Building House Block");
		LanguageRegistry.addName(HouseTool, this.COLOR_CODE +  "dHouse Tool");
		LanguageRegistry.addName(House_9x9, "9x9 House Building Block");
		LanguageRegistry.addName(village, "Village Generator");
		LanguageRegistry.instance().addStringLocalization("itemGroup.MscHouses", "Msc. Houses");
		
	}

	public void addCrafting(){
		GameRegistry.addRecipe(new ItemStack(village, 1), new Object[] {"x", 'x', Block.dirt});
	}

}