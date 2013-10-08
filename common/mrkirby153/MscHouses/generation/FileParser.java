package mrkirby153.MscHouses.generation;

import java.io.File;
import java.io.FileNotFoundException;


public class FileParser {
	
	public static String getFileContents(String fileName) throws FileNotFoundException{
		String contents = "";
		File file = new File(fileName);
		if(file.exists()){
			
		}else{
			throw new FileNotFoundException();
		}
		return null;
	}
	
	public static int[] parseFile(String file){
		return null;
	}
	
	public static int[] parseFile(String file, int material){
		return null;
	}
	
	
}
