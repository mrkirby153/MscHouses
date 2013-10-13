package mrkirby153.MscHouses.core;

import java.io.File;
import java.util.ArrayList;

import mrkirby153.MscHouses.block.BlockCopperOre;
import mrkirby153.MscHouses.block.BlockHouseGenerator;
import mrkirby153.MscHouses.block.GUI.GuiHandler;
import mrkirby153.MscHouses.block.tileEntity.TileEntityHouseGen;
import mrkirby153.MscHouses.configuration.ConfigurationSettings;
import mrkirby153.MscHouses.configuration.MscHousesConfiguration;
import mrkirby153.MscHouses.core.command.MscHousesCommand;
import mrkirby153.MscHouses.core.handlers.VersionCheckTickHandler;
import mrkirby153.MscHouses.core.helpers.FuelHelper;
import mrkirby153.MscHouses.core.helpers.LocalMaterialHelper;
import mrkirby153.MscHouses.core.helpers.LogHelper;
import mrkirby153.MscHouses.core.helpers.OreHelper;
import mrkirby153.MscHouses.core.network.CommonProxy;
import mrkirby153.MscHouses.creativeTab.CreativeTabHouse;
import mrkirby153.MscHouses.creativeTab.CreativeTabModifyer;
import mrkirby153.MscHouses.creativeTab.CreativeTabModuel;
import mrkirby153.MscHouses.generation.HouseGen;
import mrkirby153.MscHouses.generation.MscHouses_WorldGen;
import mrkirby153.MscHouses.items.ItemBlockGenerator;
import mrkirby153.MscHouses.items.ItemCopper;
import mrkirby153.MscHouses.items.ItemHouseTool;
import mrkirby153.MscHouses.items.ItemInfiniteDimensons;
import mrkirby153.MscHouses.items.ItemMaterialModifyer;
import mrkirby153.MscHouses.items.ItemModuel;
import mrkirby153.MscHouses.items.ItemPCB;
import mrkirby153.MscHouses.items.Item_Debug;
import mrkirby153.MscHouses.lib.BlockId;
import mrkirby153.MscHouses.lib.ItemId;
import mrkirby153.MscHouses.lib.Reference;
import mrkirby153.MscHouses.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * 
 * Msc Houses
 *
 * MscHouses
 *
 * @author mrkirby153
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDANCIES, certificateFingerprint=Reference.FINGERPRINT)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MscHouses {
	public static HouseGen h = new HouseGen();
	public static final CreativeTabs tabHouse = new CreativeTabHouse(
			CreativeTabs.getNextID(), "MscHouses-main");
	public static final CreativeTabs tabHouse_moduel = new CreativeTabModuel(CreativeTabs.getNextID(), "MscHouses-Moduel");
	public static final CreativeTabs tabHouse_modifyers = new CreativeTabModifyer(CreativeTabs.getNextID(), "MscHouses-Modifyers");
	@Instance("MscHouses")
	public static MscHouses instance;
	public static final char COLOR_CODE = '\u00A7';
	@SidedProxy(clientSide =Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	public static MscHousesConfiguration config;
	public static boolean isPlayerSneaking;
	

	public static Block OreCopper;
	public static Block BlockHouseGenerator;

	public static Item Debug;
	public static Item ingotCopper;
	public static Item HouseTool;
	public static Item PCB;
	public static Item moduel;
	public static Item modifyer;
	public static Item infiniteDimensions;

	public static ArrayList<Integer> blacklisted_ids = new ArrayList<Integer>();

	public static String blacklisted_items_string;
	

	@EventHandler
	public void invalidFingerprint(FMLFingerprintViolationEvent event) {

		// Report (log) to the user that the version of MscHouses
		// they are using has been changed/tampered with
		if (Reference.FINGERPRINT.equals("@FINGERPRINT@")) {
			LogHelper.warning(Strings.NO_FINGERPRINT_MESSAGE);
		}
		else {
			LogHelper.severe(Strings.INVALID_FINGERPRINT_MESSAGE);
		}
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new MscHousesConfiguration(new File(event.getModConfigurationDirectory(), "MscHouses/main.conf"));
		try{
			config.load();
			Property oreCopperId = config.get(Configuration.CATEGORY_BLOCK, "copperOre.id", BlockId.ORE_COPPER_DEFAULT);
			oreCopperId.comment = "The id for Copper Ore";
			Property oreCopperEnabled = config.get(Configuration.CATEGORY_GENERAL, "copperOre.genreation", ConfigurationSettings.generteCopper_Default);
			oreCopperEnabled.comment= "Determines whether copper ore generates in the world. Set to false to disable";

			Property houseGenId = config.get(Configuration.CATEGORY_BLOCK, "houseGen.id", BlockId.HOUSE_GENERATOR_DEFAULT);
			houseGenId.comment = "The Id for the House Generator.";

			Property debugId = config.get(Configuration.CATEGORY_GENERAL, "debug.id", ItemId.DEBUG_DEFAULT);
			debugId.comment = "The Id for the debug tool.";

			Property ingotCopperId = config.get(Configuration.CATEGORY_ITEM, "ingotCopper.id", ItemId.INGOT_COPPER_DEFAULT);
			ingotCopperId.comment = "The Id for Copper Ingots.";

			Property itemHouseToolId = config.get(Configuration.CATEGORY_ITEM, "houseTool.id", ItemId.ITEM_HOUSETOOL_DEFAULT);
			itemHouseToolId.comment = "The Id for the House Tool.";

			Property itemPcbId = config.get(Configuration.CATEGORY_ITEM, "pcb.id", ItemId.ITEM_PCB_DEFAULT);
			itemPcbId.comment = "The ID for the PCB's.";

			Property itemModuelId = config.get(Configuration.CATEGORY_ITEM, "moduel.id", ItemId.ITEM_MODUEL_DEFAULT);
			itemModuelId.comment = "The ID for the House Moduels.";

			Property itemModifyerId = config.get(Configuration.CATEGORY_ITEM, "modifyer.id", ItemId.ITEM_MODIFYER_DEFAULT);
			itemModifyerId.comment = "The ID for the material modifyers.";

			Property infiniteDimId = config.get(Configuration.CATEGORY_ITEM, "infinitedim.id", ItemId.ITEM_INFINITE_DIM_DEFAULT);
			infiniteDimId.comment = "The ID for the jar of infinite dimensons.";

			Property blacklistItems = config.get(Configuration.CATEGORY_ITEM, "blacklist.item", ConfigurationSettings.blacklist_ids_default);
			blacklistItems.comment = "Place item Id's you DON'T want houses made of here. Seperated by commas(EX: 1,3,5,6)";


			//Split blacklisted id's and put them in an array
			String ids = blacklistItems.getString();
			String ids_stripped = ids.replace(" ", "");
			String[] split_ids = ids_stripped.split(",");
			for(int i=0; i < split_ids.length; i++){
				String var1 = split_ids[i];
				int ids_int = Integer.parseInt(var1);
				MscHouses.blacklisted_ids.add(ids_int);
			}
			LocalMaterialHelper.init();
			//Inintialize the Log Helper
			LogHelper.init();
			//Check version
			Version.check();
			//Defines Blocks
			HouseManager.downloadInit();
			OreCopper = new BlockCopperOre(oreCopperId.getInt()).setUnlocalizedName(Strings.BLOCK_ORE_COPPER_NAME);
			BlockHouseGenerator = new BlockHouseGenerator(houseGenId.getInt()).setUnlocalizedName(Strings.BLOCK_HOUSE_GEN_NAME);
			//Defines Items
			Debug = new Item_Debug(debugId.getInt()).setUnlocalizedName(Strings.ITEM_DEBUG_NAME);
			ingotCopper = new ItemCopper(ingotCopperId.getInt()).setUnlocalizedName(Strings.ITEM_INGOT_COPPER_NAME);
			HouseTool = new ItemHouseTool(itemHouseToolId.getInt()).setUnlocalizedName(Strings.ITEM_HOUSE_TOOL_NAME);
			PCB = new ItemPCB(itemPcbId.getInt()).setUnlocalizedName(Strings.ITEM_PCB_NAME);
			moduel = new ItemModuel(itemModuelId.getInt()).setUnlocalizedName(Strings.ITEM_MODUEL_NAME);
			modifyer = new ItemMaterialModifyer(itemModifyerId.getInt()).setUnlocalizedName(Strings.ITEM_MATERIAL_MODIFYER_NAME);
			infiniteDimensions = new ItemInfiniteDimensons(infiniteDimId.getInt()).setUnlocalizedName(Strings.ITEM_INFINITE_DIM_NAME);
			
			GameRegistry.registerBlock(BlockHouseGenerator, ItemBlockGenerator.class, Strings.BLOCK_HOUSE_GEN_NAME);
			GameRegistry.registerBlock(OreCopper, Strings.BLOCK_ORE_COPPER_NAME);
		} finally{
			if(config.hasChanged())
				config.save();
		}


		//Register Version Handler
		TickRegistry.registerTickHandler(new VersionCheckTickHandler(), Side.CLIENT);
		TickRegistry.registerScheduledTickHandler(new HouseTickTimer(), Side.SERVER);
		FuelHelper.registerFuels();

	}


	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new MscHouses_WorldGen());
		//	proxy.registerTileEntity();
		GameRegistry.registerTileEntity(TileEntityHouseGen.class, "TileEntity_HouseGen");
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		//Inialize crafting/smelting recipies
		Crafting.addCrafting();
		Crafting.addSmelting();
		//Localization.localize();
		OreHelper.registerOres();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new MscHousesCommand());
	}

	public static String getMCVersion(){
		return Loader.instance().getMinecraftModContainer().getVersion();
	}
	
	public static File getConfigDir(){
		return Loader.instance().getConfigDir();
	}

}
