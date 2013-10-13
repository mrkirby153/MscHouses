package mrkirby153.MscHouses.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import mrkirby153.MscHouses.core.helpers.LogHelper;
import mrkirby153.MscHouses.lib.Reference;



/**
 * 
 * MscHouses
 *
 * HouseManager.java
 *
 * @author mrkirby153
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class HouseManager{
	private static HouseManager instance = new HouseManager();

	private static final String REMOTE_HOUSE_ROOT = "https://dl.dropboxusercontent.com/u/121954827/Modding/MscHouses/Houses/";
	private static final String REMOTE_HOUSE_MASTER = "https://dl.dropboxusercontent.com/u/121954827/Modding/MscHouses/Houses/master.txt";
	private static final String version = Reference.VERSION_NUMBER;

	private final static  void downloadHouse(String houseName, String over){
		LogHelper.info("Beginning download of "+houseName);
		boolean overwrite = false;
		if(over.equalsIgnoreCase("true")){
			overwrite = true;
		}else if(over.equalsIgnoreCase("yes")){
			overwrite = true;
		}
		try {
			String houseName_url = URLEncoder.encode(houseName, "utf-8");
			houseName_url = houseName_url.replace("+", "%20");
			String location = REMOTE_HOUSE_ROOT + "/"+ houseName_url + ".house";
			HttpURLConnection conn = null;
			while (location != null && !location.isEmpty()) {
				URL url = new URL(location);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
				conn.connect();
				location = conn.getHeaderField("Location");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;
			File directory = new File(MscHouses.getConfigDir() +"/MscHouses/Houses");
			File file = new File(MscHouses.getConfigDir() +"/MscHouses/Houses/"+houseName+".house");
			if(!directory.exists()){
				LogHelper.info("Creating directory for houses");
				directory.mkdirs();
			}
			if(!file.exists()){
				LogHelper.info("Found new house "+houseName);
				file.createNewFile();
			}else{
				if(!overwrite){
					LogHelper.info("Skipping download of "+houseName+". Reason: Already Downloaded");
					return;
				}
			}
			Writer out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MscHouses.getConfigDir() +"/MscHouses/Houses/"+houseName+".house"), "utf-8"));

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.isEmpty()) {
					continue;
				}
				out.write(line);
			}
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LogHelper.info("Download of "+houseName+" complete");
	}


	public static String[] getFiles(){
		try {

			String location = REMOTE_HOUSE_MASTER;
			HttpURLConnection conn = null;
			while (location != null && !location.isEmpty()) {
				URL url = new URL(location);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
				conn.connect();
				location = conn.getHeaderField("Location");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;
			ArrayList<String> houses = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.isEmpty()) {
					continue;
				}
				houses.add(line);
			}
			LogHelper.info("Caching master file for reference...");
			// Cache master file
			cacheMaster();
			return houses.toArray(new String[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public static void downloadInit(){
		LogHelper.warning("Beginning download of all house files. This could take a while...");
		String[] houses = getFiles();
		if(houses == null)
			return;
		LogHelper.info("Houses to download: "+houses.length);
		for(int i=0; i< houses.length; i++){
			String house = houses[i];
			String[] house_split = house.split(";");
			if(house_split.length == 1){
				downloadHouse(house, "false");
			}else{
				downloadHouse(house_split[0], house_split[1]);
			}
		}
		LogHelper.info("Download complete!");
	}

	public static void cacheMaster(){
		try {

			String location = REMOTE_HOUSE_MASTER;
			HttpURLConnection conn = null;
			while (location != null && !location.isEmpty()) {
				URL url = new URL(location);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
				conn.connect();
				location = conn.getHeaderField("Location");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;
			File directory = new File(MscHouses.getConfigDir() +"/MscHouses/Houses");
			File file = new File(directory.toString() +"/master.txt");
			if(!directory.exists()){
				LogHelper.info("Creating directory for houses");
				directory.mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			Writer out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(directory.toString() + "/master.txt"), "utf-8"));

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.isEmpty()) {
					continue;
				}
				out.write(line+"\n");
			}
			out.close();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static HouseManager getInstance(){
		return instance;
	}


}
