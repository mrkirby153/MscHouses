package mrkirby153.MscHouses.generation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


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

	public static int[][] parseFile(String fileLocation){
		String contents = getFileContents(fileLocation);
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
		
		return null;
	}
	
	public static int[][] parseFile(String fileLocation, int materialId){
		String contents = getFileContents(fileLocation);
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
			int block_id = 0;
			if(id.substring(0, id.length()-2).equalsIgnoreCase("X")){
				block_id = materialId;
			}else{
				block_id = Integer.parseInt(id.substring(0, id.length()-2));
			}
			int block_meta = Integer.parseInt(blockAndMeta[2]);
			return_value[0][i] = x;
			return_value[1][i] = y;
			return_value[2][i] = z;
			return_value[3][i] = block_id;
			return_value[4][i] = block_meta;
			return return_value;
		}

		return null;
	}
	
	public static boolean isSpecal(String fileLocation){
		String contents = getFileContents(fileLocation);
		if(contents.contains("X")){
			return true;
		}else if(contents.contains("x")){
			return true;
		}
		return false;
	}


}
