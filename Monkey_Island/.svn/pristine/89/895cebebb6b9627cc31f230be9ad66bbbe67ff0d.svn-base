package fr.eseo.controller;

import fr.eseo.communication.ServiceMonkeyIsland;

/**
 * Class Main du jeu
 * 
 * @author romaincrevan
 *
 */
public class Game {
	
	/**
	 * Instantiates a new Game.
	 */
	public Game(){
		
	}

	/**
	 * The main of the game, launch the service.
	 * @param args ..
	 */
	public static void main(String[] args) {
		System.out.println("MonkeyIsland");
		try {
			final int port = 13579;
			// Création du service.
			final ServiceMonkeyIsland serviceMonkeyIsland = new ServiceMonkeyIsland();

			// Lancement du service.
			serviceMonkeyIsland.lanceService(port);
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	
}
