package fr.eseo.model.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

import fr.eseo.model.Case;
import fr.eseo.model.CaseType;
import fr.eseo.model.CrazyMonkey;
import fr.eseo.model.Island;
import fr.eseo.model.Pirate;
import fr.eseo.model.Rhum;
import fr.eseo.model.StatePirate;
import fr.eseo.model.Treasure;

/**
 * The Class TestPirate.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Island.class)
public class TestPirate {

	/**  Largeur de l'île. */
	private static final int NB_ROWS = 30;
	
	/**  Longueur de l'île. */
	private static final int NB_LINES = 30;
	
	/**  Coordonnée X d'un pirate. */
	private static final int X_PIRATE = 10;
	
	/**  Coordonnée Y d'un pirate. */
	private static final int Y_PIRATE = 10;

	/**  Coordonnée X d'un deuxième pirate. */
	private static final int X_PIRATE2 = 20;
	
	/**  Coordonnée Y d'un deuxième pirate. */
	private static final int Y_PIRATE2 = 20;
	
	/**  Coordonnée X d'un singe. */
	private static final int X_MONKEY = 20;
	
	/**  Coordonnée Y d'un singe. */
	private static final int Y_MONKEY = 21;
	
	/** The pirate. */
	private Pirate pirate;
	
	/** The pirate 2. */
	private Pirate pirate2;
	
	/** The monkey. */
	private CrazyMonkey monkey;
	
	/** The rhum. */
	private Rhum rhum;
	
	/** The monkey island. */
	private Island monkeyIsland;

	
	/** Plateau de jeu */
	private Case[][] plateau = new Case[NB_ROWS][NB_LINES];
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp(){
		this.monkeyIsland = mock(Island.class);
	    PowerMockito.mockStatic(Island.class);
		when(Island.getInstance()).thenReturn(monkeyIsland);
		when(this.monkeyIsland.getnbLines()).thenReturn(NB_LINES);
		when(this.monkeyIsland.getnbRows()).thenReturn(NB_ROWS);		
		for(int i = 0; i< NB_LINES; i++){
			for(int j = 0; j< NB_ROWS; j++){
				if(i==21 && j==20){
					this.plateau[i][j] = new Case(i, j, CaseType.sea);
				}else{
					this.plateau[i][j] = new Case(i, j, CaseType.earth);
				}
			}
		}
		when(this.monkeyIsland.getCase()).thenReturn(plateau);
		
		this.pirate = new Pirate(X_PIRATE, Y_PIRATE);
		this.pirate2 = new Pirate(X_PIRATE2, Y_PIRATE2);
		this.monkey = new CrazyMonkey(X_MONKEY, Y_MONKEY);
		this.rhum = new Rhum(20, 21, true);
	}
	
	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception 
	{}
	
	/** 
	 * 
	 * @throws Exception
	 * Teste le déplacement vers le bas du pirate
	 */
	@Test
	public void testMovementPirateDown(){
		try{
			this.pirate.movementPirate(0, 1);
		}catch(Exception e){
			fail("Should not have thrown any exception");
		} 
		assertEquals("erreur mouvement ", 11, this.pirate.getCoordinateY());
	}
	
	/**
	 * Test movement up.
	 *
	 * @throws Exception Teste le déplacement vers le haut du pirate
	 */
	@Test
	public void testMovementPirateUp(){
		
		try {
			this.pirate.movementPirate(0, -1);
		} catch (Exception e) {
			fail("Should not have thrown any exception");
		}
		
		assertEquals("erreur mouvement ", 9, this.pirate.getCoordinateY());
	}

	/**
	 * Test movement left.
	 *
	 * @throws Exception Teste le déplacement vers la gauche du pirate
	 */
	@Test
	public void testMovementPirateLeft(){
		try{
			this.pirate.movementPirate(-1, 0);
		} catch(Exception e){
			fail("Should not have thrown any exception");
		}
		assertEquals("error movement ", 9, this.pirate.getCoordinateX());
	}
	
	/** 
	 * 
	 * @throws Exception
	 * Teste le déplacement vers la droite du pirate
	 */
	@Test
	public void testMovementPirateRight() throws Exception {
		try{
			this.pirate.movementPirate(1, 0);
		} catch(Exception e){
			fail("Should not have thrown any exception");
		}
		assertEquals("error movement ", 11, this.pirate.getCoordinateX());
	}
	
	/** 
	 * 
	 * @throws Exception
	 * Teste le déplacement de (0,0)
	 */
	@Test
	public void testMovementPirateZero(){
		try{
			this.pirate.movementPirate(0, 0);
		} catch(Exception e){
			fail("Should not have thrown any exception");
		}
	
		assertEquals("error movement ", 10, this.pirate.getCoordinateX());
		assertEquals("error movement ", 10, this.pirate.getCoordinateY());
	}
	
	/**
	 * Test movement not allowed.
	 *
	 * @throws Exception Teste qu'on ne peut pas se déplacer de 2 cases
	 */
	@Test
	public void testMovementPirateNotAllowed(){
		try{
			this.pirate.movementPirate(0, 2);
			fail("movement 2 impossible");
		}catch (Exception e){
			assertTrue(e.getMessage().contains("MOVEMENT not allowed"));
		}
		
		assertEquals("Movement impossible X", 10, this.pirate.getCoordinateX());
		assertEquals("Movement impossible Y", 10, this.pirate.getCoordinateY());
		
	}
	
	/**
	 * Test movement out of limits.
	 *
	 * @throws Exception Teste qu'on ne peut pas sortir des limites de l'île
	 */
	@Test
	public void testMovementPirateOutOfLimits(){
		this.pirate.setCoordinateX(29);
		
		try{
			this.pirate.movementPirate(1, 0);
			fail("Exit island");
		}catch (Exception e){
			assertTrue(e.getMessage().contains("exit island!!"));
		}

		assertEquals("Error exit island X", 29, this.pirate.getCoordinateX());
		assertEquals("Error exit island Y", 10, this.pirate.getCoordinateY());
		
	}
	
	/** 
	 * 
	 * @throws Exception
	 * Teste qu'un pirate meurt après collision avec un singe
	 */
	@Test
	public void testDeathPirate(){
		
		when(this.monkeyIsland.collisionMonkey(this.pirate2.getCoordinateX(), this.pirate2.getCoordinateY()+1)).thenReturn(monkey);
		
		try {
			this.pirate2.movementPirate(0, 1);
		}catch(Exception e){
			fail("Should not have thrown any exception");
		}
		
		assertEquals("error state", StatePirate.dead, this.pirate2.getState());
		assertEquals("error energy", 0, this.pirate2.getEnergy());
	}
	
	/**
	 * 
	 * @throws Exception
	 * Méthode qui vérifie que le pirate ne bouge pas lorsqu'il rentre en collision 
	 * avec un autre pirate.
	 */
	@Test
	public void testMovementPirateByPirate(){
		
		when(this.monkeyIsland.collisionPirate(this.pirate.getCoordinateX(), this.pirate.getCoordinateY()+1)).thenReturn(pirate2);
		
		try {
			this.pirate.movementPirate(0, 1);
			fail("Should return pirate");
		}catch(Exception e){
			assertTrue(e.getMessage().contains("Case pirate not allowed"));
		}
		
		assertEquals("Error Collision Pirate X", 10, this.pirate.getCoordinateX());
		assertEquals("Error Collision Pirate Y", 10, this.pirate.getCoordinateY());
	}
	
	
	/**
	 * 
	 * @throws Exception
	 * Méthode qui teste la détection du trésor par le pirate.
	 */
	@Test
	public void testMovementPirateByTreasure(){
		
		when(this.monkeyIsland.collisionPirate(this.pirate.getCoordinateX(), this.pirate.getCoordinateY()+1)).thenReturn(null);
		when(this.monkeyIsland.collisionMonkey(this.pirate.getCoordinateX(), this.pirate.getCoordinateY()+1)).thenReturn(null);
		Treasure.getTreasure().setCoordinateX(this.pirate.getCoordinateX());
		Treasure.getTreasure().setCoordinateY(this.pirate.getCoordinateY()+1);
		try{
			this.pirate.movementPirate(0, 1);
		}catch(Exception e){
			fail("Should not have thrown any exception");
		}
		assertTrue(Treasure.getTreasure().getVisibility());
	}
	
	/**
	 * Test movement on a sea case.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMovementPirateOnSeaCase(){
		try{
			this.pirate2.movementPirate(1, 0);
			fail("Should return sea case");
		}catch (Exception e){
			assertTrue(e.getMessage().contains("Case sea not allowed"));
		}

		assertEquals("Error exit island X", 20, this.pirate2.getCoordinateX());
		assertEquals("Error exit island Y", 20, this.pirate2.getCoordinateY());
		
	}
	
	/**
	 * Test movement on a rhum case.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMovementPirateOnRhumCase(){
		
		when(this.monkeyIsland.collisionRhum(this.pirate2.getCoordinateX(), this.pirate2.getCoordinateY()+1)).thenReturn(rhum);

		try{
			this.pirate2.movementPirate(0, 1);
		}catch (Exception e){
			fail("Should not have thrown any exception");
		}

		assertEquals("error energy pirate", 114, this.pirate2.getEnergy());
		assertEquals("error visibility rhum", false, this.rhum.getVisibility());
	}
	
	/**
	 * Test that pirate dies when his energy is at 0.
	 *
	 * @throws Exception
	 */
	@Test
	public void testMovementPirateNoEnergy(){
		
		this.pirate2.setEnergy(1);
		
		try{
			this.pirate2.movementPirate(0, 1);
		}catch (Exception e){
			fail("Should not have thrown any exception");
		}

		assertEquals("error energy pirate", 0, this.pirate2.getEnergy());
		assertEquals("error state pirate", StatePirate.dead, this.pirate2.getState());
	}
	
	/**
	 * Test that dead pirate can't move
	 *
	 * @throws Exception
	 */
	@Test
	public void testMovementDeadPirate(){
		
		this.pirate2.setState(StatePirate.dead);
		
		try{
			this.pirate2.movementPirate(0, 1);
		}catch (Exception e){
			fail("Should not have thrown any exception");
		}

		assertEquals("error cooridnate pirate", 20, this.pirate2.getCoordinateX());
		assertEquals("error cooridnate pirate", 20, this.pirate2.getCoordinateY());
		assertEquals("error state pirate", StatePirate.dead, this.pirate2.getState());
	}
	
	/**
	 * Test movement of a pirate on a dead pirate.
	 *
	 * @throws Exception
	 */
	/*@Test
	public void testMovementOnDeadPirate() throws Exception {
		boolean deplacementpirate1Effectue=false;
		boolean deplacementpirate2Effectue=false;
		
		this.pirate.setCoordinateX(20);
		this.pirate.getCaseCharacter().setCoordinateY(20);
		
		when(Island.getInstance().getPirates()).thenReturn(pirates);
		pirates.add(this.pirate);
		pirates.add(this.pirate2);
		
		when(Island.getInstance().getMonkeys()).thenReturn(monkeys);
		monkeys.add(monkey);
		
		when(Island.getInstance().collisionMonkey(this.pirate2.getCoordinateX(), this.pirate2.getCoordinateY()+1)).thenReturn(monkey);
			
		try {
			this.pirate2.movementPirate(0, 1);
			deplacementpirate2Effectue = true;
		}catch(CollisionException e){
			if(e.getExceptionCause() == CollisionException.COLLISION_EXIT_ISLAND){
				System.out.println("error exit island");
			}
			else if(e.getExceptionCause() == CollisionException.COLLISION_SEA){
				System.out.println("error collision sea");
			}
			else if(e.getExceptionCause() == CollisionException.COLLISION_PIRATE){
				 System.out.println("error collision");
			}
		}
		
		assertEquals("error state ", StatePirate.dead, this.pirate2.getState());
		
		when(Island.getInstance().collisionMonkey(this.pirate.getCoordinateX(), this.pirate.getCoordinateY()+1)).thenReturn(null);
		when(Island.getInstance().collisionPirate(this.pirate.getCoordinateX(), this.pirate.getCoordinateY()+1)).thenReturn(pirate2);	
		
		try{
			this.pirate.movementPirate(0, 1);
			deplacementpirate1Effectue=true;
		}catch(CollisionException e){
			if(e.getExceptionCause() == CollisionException.COLLISION_EXIT_ISLAND){
				System.out.println("error exit island");
			}
			else if(e.getExceptionCause() == CollisionException.COLLISION_SEA){
				System.out.println("error collision sea");
			}
			else if(e.getExceptionCause() == CollisionException.COLLISION_PIRATE){
				 System.out.println("error collision");
			}
		}
		
		assertTrue(deplacementpirate1Effectue);
		assertEquals("Error exit island X", 20, this.pirate.getCoordinateX());
		assertEquals("Error exit island Y", 21, this.pirate.getCoordinateY());
		
	}*/

}
