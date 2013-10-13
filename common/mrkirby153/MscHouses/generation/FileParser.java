package mrkirby153.MscHouses.generation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import mrkirby153.MscHouses.core.MscHouses;


public class FileParser {


	public static String getFileContents(String fileLocation){
		String contents = "";
		File file = new File(fileLocation);
		if(file.exists()){
			try{
				BufferedReader br = new BufferedReader(new FileReader(fileLocation));
				String strLine;
				while((strLine = br.readLine()) != null){
					contents += strLine;
				}
				br.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return contents;
	}

	public static int[][] parseFile(String[] house, String fileName){
		/*	String contents = getFileContents(fileLocation);
		String[] coordinateChunk = contents.split(";");
		int[][] return_value;
		for(int i=0; i < coordinateChunk.length; i++){
			// Split into x, y, z, id+metadata
			String[] coordinateParts = coordinateChunk[i].split(":");
			return_value = new int[coordinateParts.length][coordinateParts.length];
			String id = coordinateParts[3];
			String[] blockAndMeta = id.split(",");
			int x = Integer.parseInt(coordinateParts[0]);
			int y = Integer.parseInt(coordinateParts[1]);
			int z = Integer.parseInt(coordinateParts[2]);
			int block_id = Integer.parseInt(id.substring(0, id.length()-2));
			int block_meta = Integer.parseInt(blockAndMeta[2]);
			return_value[i][0] = x;
			return_value[i][1] = y;
			return_value[i][2] = z;
			return_value[i][3] = block_id;
			return_value[i][4] = block_meta;
			return return_value;
		}

		return null;*/
		int[][] return_value;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(MscHouses.getConfigDir() + "/MscHouses/Houses/"+fileName+".house"));
			String line = null;
			ArrayList<String> lines = new ArrayList<String>();
			String[] var1;
			while((line = reader.readLine()) != null){
				if(line.startsWith("#"))
					continue;
				if(line.isEmpty())
					continue;
				lines.add(line);
			}
			var1 = lines.toArray(new String[0]);
			return_value = new int[5][var1.length];
			// Split File
			for(int i = 0; i < var1.length; i++){
				String[] split = var1[i].split(":");
				String[] blockAndMeta = split[3].split(";");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				int z = Integer.parseInt(split[2]);
				int blockId = Integer.parseInt(blockAndMeta[0]);
				int meta = Integer.parseInt(blockAndMeta[1]);

				return_value[0][i] = x;
				return_value[1][i] = y;
				return_value[2][i] = z;
				return_value[3][i] = blockId;
				return_value[4][i] = meta;
				return return_value;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static int[][] parseFile(String[] house, int materialId, String fileName){
		int[][] return_value;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(MscHouses.getConfigDir() + "/MscHouses/Houses/"+fileName+".house"));
			String line = null;
			ArrayList<String> lines = new ArrayList<String>();
			String[] var1;
			while((line = reader.readLine()) != null){
				if(line.startsWith("#"))
					continue;
				if(line.isEmpty())
					continue;
				lines.add(line);
			}
			var1 = lines.toArray(new String[0]);
			return_value = new int[5][var1.length];
			// Split File
			for(int i = 0; i < var1.length; i++){
				String[] split = var1[i].split(":");
				String[] blockAndMeta = split[3].split(";");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				int z = Integer.parseInt(split[2]);
				int blockId = Integer.parseInt(blockAndMeta[0]);
				int meta = Integer.parseInt(blockAndMeta[1]);

				return_value[0][i] = x;
				return_value[1][i] = y;
				return_value[2][i] = z;
				return_value[3][i] = blockId;
				return_value[4][i] = meta;
				return return_value;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isSpecal(String[] array){
		if(Arrays.asList(array).contains("X"))
			return true;
		else
			return false;
	}
}
