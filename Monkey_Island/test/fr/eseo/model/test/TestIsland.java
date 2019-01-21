package fr.eseo.model.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;
import fr.eseo.model.*;

public class TestIsland {
	
	private Island isl;
	
	private Pirate pirate;
	
	private Pirate pirate2;
	
	private CrazyMonkey monkey;
	
	private Rhum rhum;
	
	
	@Before
	public void setUp() throws Exception{	
		
		isl = Island.getInstance();
		pirate = new Pirate(0, 0);
		pirate2 = new Pirate(5, 5);
		monkey = new CrazyMonkey(7,7);
		rhum = new Rhum(8, 10, true);
		Treasure.getTreasure().setCoordinateX(12);
		Treasure.getTreasure().setCoordinateY(16);
		monkey.getTimer().stop();
		isl.getPirates().add(pirate2);
		isl.getMonkeys().add(monkey);
		isl.getRhums().add(rhum);
	}
	
	@Test
	public void testInitIsland(){
		isl.setIsland(3, 3);
		for(int i = 0; i< 3; i++){
			assertEquals("Error type case", CaseType.sea, isl.getCase()[i][0].getCaseType());
			assertEquals("Error type case", CaseType.sea, isl.getCase()[i][2].getCaseType());
			assertEquals("Error type case", CaseType.sea, isl.getCase()[0][i].getCaseType());
			assertEquals("Error type case", CaseType.sea, isl.getCase()[2][i].getCaseType());
		}
		assertEquals("Error type case", CaseType.earth, isl.getCase()[1][1].getCaseType());
	}
	
	@Test
	public void testSetIsland(){
		isl.setIsland(3, 3);
		assertEquals("Error type case", Island.getInstance().getnbLines(), 3);
		isl.setIsland(30, 30);
		assertEquals("Error type case", Island.getInstance().getnbLines(), 30);
		isl.getPirates().add(pirate2);
	}

	/**
	 * Test position pirate.
	 * @throws CollisionException 
	 */
	@Test
	public void testRandompirate(){
		isl.setIsland(30, 30);
		int position = 0;
		int errorSea = 0;
		int errorIsl = 0;
		int errorPir = 0;
		int errorMonk = 0;
		int errorRhum = 0;
		int errorTre = 0;
		for (int i = 0; i< 2000; i++){
			try {
				Point p = Island.getInstance().addEntity();
				Island.getInstance().position(p.x, p.y);
				pirate.setCoordinateX(p.x);
				pirate.setCoordinateY(p.y);
				if((pirate.getCoordinateX() > 0 && pirate.getCoordinateX() < Island.getInstance().getnbLines()-1) && (pirate.getCoordinateY() > 0 && pirate.getCoordinateY() < Island.getInstance().getnbRows()-1)){
					position++;
				}
			} catch (CollisionException e) {
				if(e.getExceptionCause() == CollisionException.COLLISION_SEA){
					errorSea++;
				}else if(e.getExceptionCause() == CollisionException.COLLISION_EXIT_ISLAND){
					errorIsl++;
				}else if(e.getExceptionCause() == CollisionException.COLLISION_PIRATE){
					errorPir++;
				}else if(e.getExceptionCause() == CollisionException.COLLISION_MONKEY){
					errorMonk++;
				}else if(e.getExceptionCause() == CollisionException.COLLISION_RHUM){
					errorRhum++;
				}else if(e.getExceptionCause() == CollisionException.COLLISION_TREASURE){
					errorTre++;
				}
			}
		}
		assertEquals("Error, miss some positions", 2000, position);
		assertTrue("Error, pirate on case pirate", errorPir == 0);
		assertTrue("Error, pirate on case monkey", errorMonk == 0);
		assertTrue("Error, pirate on case Rhum", errorRhum == 0);
		assertTrue("Error, pirate on case Tre", errorTre == 0);
		assertEquals("Error, pirateasure on case sea", 0, errorSea);
		assertEquals("Error, exit Island !!!", 0, errorIsl);
	}
	
	
	@Test
	public void testPositionpirateOutIsl(){
		try {
			Island.getInstance().position(30, 30);
			fail("Should throw exception when out of Island");
		}catch (CollisionException e){
			assertTrue(e.getMessage().contains("exit island!!"));
			assertTrue(e.getExceptionCause()==CollisionException.COLLISION_EXIT_ISLAND);
		}
		assertEquals("shouldn t have coordinate X", 0, this.pirate.getCoordinateX());
		assertEquals("shouldn t have coordinate Y", 0, this.pirate.getCoordinateY());
	}
	
	@Test
	public void testPositionpirateSeaCase(){
		try {
			Island.getInstance().position(0, 10);
			fail("Should throw exception when case sea");
		}catch (CollisionException e){
			assertTrue(e.getMessage().contains("Case sea not allowed"));
			assertTrue(e.getExceptionCause()==CollisionException.COLLISION_SEA);
		}
		assertEquals("shouldn t have coordinate X", 0, this.pirate.getCoordinateX());
		assertEquals("shouldn t have coordinate Y", 0, this.pirate.getCoordinateY());
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPositionpiratePirateCase(){
		isl.setIsland(30, 30);
		try {
			Island.getInstance().position(5, 5);
			fail("Should throw exception when on Pirate");
		}catch (CollisionException e){
			assertTrue(e.getMessage().contains("Case pirate not allowed"));
			assertTrue(e.getExceptionCause() == CollisionException.COLLISION_PIRATE);
		}
		assertEquals("shouldn t have coordinate X", 0, this.pirate.getCoordinateX());
		assertEquals("shouldn t have coordinate Y", 0, this.pirate.getCoordinateY());
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPositionpirateMonkeyCase(){
		isl.setIsland(30, 30);
		try {
			Island.getInstance().position(7, 7);
			fail("Should throw exception when on Monkey");
		}catch (CollisionException e){
			assertTrue(e.getMessage().contains("Case monkey not allowed"));
			assertTrue(e.getExceptionCause()==CollisionException.COLLISION_MONKEY);
		}
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPositionpirateRhumCase(){
		isl.setIsland(30, 30);
		try {
			Island.getInstance().position(8, 10);
			fail("Should throw exception when on Rhum");
		}catch (CollisionException e){
			assertTrue(e.getMessage().contains("Case rhum not allowed"));
			assertTrue(e.getExceptionCause()==CollisionException.COLLISION_RHUM);
		}
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPositionpirateTeasureCase(){
		isl.setIsland(30, 30);
		try {
			Island.getInstance().position(12, 16);
			fail("Should throw exception when on Treasure");
		}catch (CollisionException e){
			assertTrue(e.getMessage().contains("Case treasure not allowed"));
			assertTrue(e.getExceptionCause()==CollisionException.COLLISION_TREASURE);
		}
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPirateOnMonkey(){
		isl.setIsland(30, 30);
		Pirate piratet = new Pirate(6, 7);
		try {
			piratet.movementPirate(1, 0);
			
		}catch (CollisionException e){
			fail("Should not throw any exception");
		}
		assertEquals("shouldn be dead", StatePirate.dead, piratet.getState());
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMonkeyOnPirate(){
		isl.setIsland(30, 30);
		CrazyMonkey monkeyt = new CrazyMonkey(4, 5);
		monkeyt.getTimer().stop();
		try {
			monkeyt.setPositionMonk(1, 0);
		}catch (CollisionException e){
			fail("Should not throw any exception");
		}
		assertEquals("shouldn be dead", StatePirate.dead, this.pirate2.getState());
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPirateOnTreasure(){
		isl.setIsland(30, 30);
		Pirate piratet = new Pirate(12, 15);
		try {
			piratet.movementPirate(0, 1);
		}catch (CollisionException e){
			fail("Should not throw any exception");
		}
		assertEquals("shouldn be visible", true, Treasure.getTreasure().getVisibility());
	}
	
	/** 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPirateOnRhum(){
		isl.setIsland(30, 30);
		Pirate piratet = new Pirate(8, 11);
		try {
			piratet.movementPirate(0, -1);
		}catch (CollisionException e){
			fail("Should not throw any exception");
		}
		assertEquals("shouldn be not visible", false, this.rhum.getVisibility());
	}
	
	
	@Test
	public void testPositionpirate(){
		isl.setIsland(30, 30);
		try {
			Island.getInstance().position(10, 15);
			pirate.setCoordinateX(10);
			pirate.setCoordinateY(15);
		}catch (CollisionException e){
			fail("Should not have thrown any exception");
		}
		assertEquals("Not in right place X", 10, this.pirate.getCoordinateX());
		assertEquals("Not in right place Y", 15, this.pirate.getCoordinateY());
	}
}
