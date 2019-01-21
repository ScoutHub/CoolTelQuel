package fr.eseo.model;

// 
/**
 * The Class Treasure.
 */

public final class Treasure extends Item{
	
	private static Treasure treasure = null;
	
	
	/**
	 * Gets the treasure.
	 *
	 * @return the treasure
	 */
	public static Treasure getTreasure(){
		if(treasure == null){
			treasure = new Treasure();
		}
		return treasure;
	}

	/**
	 * Instantiates a new treasure.
	 */
	private Treasure(){
		super();
	}
}
