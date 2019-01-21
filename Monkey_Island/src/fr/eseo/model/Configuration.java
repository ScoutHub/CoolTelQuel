package fr.eseo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The Class Configuration.
 */
public class Configuration {
	
	private int nrjPirateMax = 0;
	
	private static Configuration config = null;
	
	/**
	 * Gets the single instance of Island.
	 *
	 * @return single instance of Island
	 */
	public static Configuration getInstance(){
		if(config == null){
			config= new Configuration();
		}
		return config;
	}
	
	/**
	 * Instantiates a new Configuration.
	 */
	public Configuration(){
		
	}
	
	/**
	 * Get the energy of the pirate for the configuration.
	 * @return nrjPirateMax the energy of the pirate
	 */
	public int getNRJMax(){
		return this.nrjPirateMax;
	}
	
	/**
	 * Configure the data from the configuration file.
	 */
	public void loading(){
		try {
			final Island isl = Island.getInstance();
			final File file = new File("resources/config.properties");
			final FileInputStream fileInput = new FileInputStream(file);
			final Properties properties = new Properties();
			properties.load(fileInput);

			final int xIsl = Integer.parseInt(properties.getProperty("IslandWidth"));
			final int yIsl = Integer.parseInt(properties.getProperty("IslandWidth"));
			isl.setIsland(xIsl, yIsl);
			
			
			if(properties.getProperty("TreasureX") != null && properties.getProperty("TreasureY") != null){
				final int xTre = Integer.parseInt(properties.getProperty("TreasureX"));
				final int yTre = Integer.parseInt(properties.getProperty("TreasureY"));
				Treasure.getTreasure().setCoordinateX(xTre);
				Treasure.getTreasure().setCoordinateY(yTre);
			}

			if(properties.getProperty("nbRhum") != null){
				for(int i = 0; i<Integer.parseInt(properties.getProperty("nbRhum")); i++){
					isl.getRhums().add(new Rhum(Integer.parseInt(properties.getProperty("Rhumx"+i+"")),
							Integer.parseInt(properties.getProperty("Rhumy"+i+"")),
							true,
							Integer.parseInt(properties.getProperty("NRJRhum")), 
							Integer.parseInt(properties.getProperty("TMPRhum"))));
				}
			}
			
			if(properties.getProperty("nbCrazyMonkey") != null){
				for(int i = 0; i<Integer.parseInt(properties.getProperty("nbCrazyMonkey")); i++){
					isl.getMonkeys().add(
							new CrazyMonkey(Integer.parseInt(properties.getProperty("VitesseSingeErratique")), 
							Integer.parseInt(properties.getProperty("CrazyMonkeyx"+i+"")),
							Integer.parseInt(properties.getProperty("CrazyMonkeyy"+i+""))));
				}
			}
			
			if(properties.getProperty("nbHunterMonkey") != null){
				for(int i = 0; i<Integer.parseInt(properties.getProperty("nbHunterMonkey")); i++){
					isl.getMonkeys().add(
							new HunterMonkey(Integer.parseInt(properties.getProperty("VitesseSingeChasseur")), 
							Integer.parseInt(properties.getProperty("HunterMonkeyx"+i+"")),
							Integer.parseInt(properties.getProperty("HunterMonkeyy"+i+""))));
				}
			}
			
			if(properties.getProperty("NRJMaxPirate") != null){
				this.nrjPirateMax = Integer.parseInt(properties.getProperty("NRJMaxPirate"));
			}

			fileInput.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
