package fr.eseo.model;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

//
/**
 * The Class HunterMonkey.
 */
public class HunterMonkey extends Monkey{
	
	/**
	 * Instantiates a new HunterMonkey.
	 * @param x the coordinate x of the monkey
	 * @param y the coordinate y of the monkey
	 */
	public HunterMonkey( int x, int y){
		super( x, y);
	}
	/**0
	 * Instantiates a new hunter monkey.
	 *
	 * @param speed the speed
	 * @param x the coordinate x of the monkey
	 * @param y the coordinate y of the monkey
	 */
	public HunterMonkey(int speed, int x, int y){
		super(speed, x, y);
	}
	
	/**
	 * Get the closest pirate of the monkey.
	 * @param pirates the list of pirates of the island
	 * @return p a pirate
	 */
	public Pirate closerPirate(ArrayList<Pirate> pirates){
	 
		Pirate p = null;
		final int x = Math.abs(pirates.get(0).getCoordinateX() - this.getCoordinateX());
		final int y = Math.abs(pirates.get(0).getCoordinateY() - this.getCoordinateY());
		for(int i=0; i<pirates.size(); i++){
			if(pirates.get(i).getState()!=StatePirate.dead){
				if ((Math.abs(pirates.get(i).getCoordinateX() - this.getCoordinateX()) <= x) 
						&& Math.abs(pirates.get(i).getCoordinateY() - this.getCoordinateY()) <= y){
					p=pirates.get(i);
				}
			}
		}
		return p;
	}
	
	/**
	 * Hunt the closest pirate.
	 * @param pirate the pirate
	 * @return tab the movement (0,1 or 0,-1 or -1,0 or 1,0)
	 */
	public int[] huntingPirates(Pirate pirate){
		
		final int[] tab = {0, 0};
		while(pirate.getState()!=StatePirate.dead){
			if(Math.abs(pirate.getCoordinateX() - this.getCoordinateX()) >= Math.abs(pirate.getCoordinateY() - this.getCoordinateY())){
				tab[0]=1; 
				tab[1]=0;
			}else if(Math.abs(pirate.getCoordinateX() - this.getCoordinateX())
						<= Math.abs(pirate.getCoordinateY() - this.getCoordinateY())){
				tab[0]=0; 
				tab[1]=1;
			}
		}
		return tab;
	}
	
	
	/**
	 * Move a hunter monkey.
	 * @param tab the movement (0,1 or 0,-1 or -1,0 or 1,0)
	 * @throws CollisionException collision with an entity
	 */
	public void movementHunterMonkey(int[] tab) throws CollisionException{
		this.setPositionMonk(tab[0], tab[1]);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
