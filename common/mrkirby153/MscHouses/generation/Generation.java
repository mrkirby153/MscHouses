package mrkirby153.MscHouses.generation;

import net.minecraft.world.World;

public class Generation {
	
	public static void build(String[] house, World world, int x, int y, int z, String fileName){
		int[][] parsedFile = FileParser.parseFile(house, fileName);
		System.out.println(parsedFile.length);
		for(int i = 0; i < parsedFile[0].length; i++){
			world.setBlock(x+parsedFile[0][i], y+parsedFile[1][i], z+parsedFile[2][i], parsedFile[3][i]);
			world.setBlockMetadataWithNotify(x+parsedFile[0][i], y+parsedFile[1][i], z+parsedFile[2][i], parsedFile[4][i], 0);
			System.out.println("X: "+parsedFile[0][i]+" Y: "+parsedFile[1][i]+" Z: "+parsedFile[2][i]+" ID: "+parsedFile[3][i] + " Meta: "+parsedFile[4][i]);
		}
	}
	
	public static void build(String[] house, int id, World world, int x, int y, int z, String fileName){
		int[][] parsedFile = FileParser.parseFile(house, id, fileName);
		for(int i = 0; i < parsedFile[0].length; i++){
			world.setBlock(x+parsedFile[0][i], y+parsedFile[1][i], z+parsedFile[2][i], parsedFile[3][i]);
			world.setBlockMetadataWithNotify(x+parsedFile[0][i], y+parsedFile[1][i], z+parsedFile[2][i], parsedFile[4][i], 0);
			System.out.println("X: "+parsedFile[0][i]+" Y: "+parsedFile[1][i]+" Z: "+parsedFile[2][i]+" ID: "+parsedFile[3][i] + " Meta: "+parsedFile[4][i]);
		}
	}
	
	public static void generate(String[] house, int id, World world, int x, int y, int z, String fileName){
		if(FileParser.isSpecal(house))
			build(house, id, world, x, y, z, fileName);
		else
			build(house, world, x, y, z, fileName);
	}

}
