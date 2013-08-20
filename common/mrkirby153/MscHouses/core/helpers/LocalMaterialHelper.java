package mrkirby153.MscHouses.core.helpers;

import java.util.ArrayList;
import java.util.logging.Level;

import mrkirby153.MscHouses.api.MaterialRegistry;
import net.minecraft.block.Block;


public class LocalMaterialHelper {
	/*private static Block[] materials = {Block.blockClay, Block.blockDiamond, Block.blockEmerald, Block.blockGold,
			Block.blockIron, Block.blockLapis, Block.blockRedstone, Block.brick, Block.cloth, Block.cobblestone,
			Block.dirt, Block.glass, Block.glowStone, Block.netherBrick, Block.obsidian, Block.planks, Block.redstoneLampIdle,
			Block.sandStone, Block.stone, Block.snow, Block.stoneBrick, Block.thinGlass, Block.tnt, Block.whiteStone, Block.wood};*/
	private static Block[] blocks = Block.blocksList;
	private static ArrayList<Integer> bl= MaterialRegistry.getBlacklisted();
	public static void init(){
		// LogHelper.log(Level.INFO, "Loading " + materials.length + " material recipies!");
		//for(int i = 1; i < materials.length; i++){
		//	MaterialRegistry.registerBlock(materials[i]);
		//}
		LogHelper.log(Level.INFO, "Loading "+blocks.length+" material recipies!");
		for(int i=1; i < blocks.length; i++){
			if(blocks[i] != null){
				Block b = blocks[i];
					if(!bl.contains(b.blockID))
						MaterialRegistry.registerBlock(blocks[i]);
			}

		}
		LogHelper.log(Level.INFO, "Loaded "+blocks.length+" material recipies successfully!");
	}

}
