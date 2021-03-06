package fr.eseo.model.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.*;

import fr.eseo.model.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Island.class)

// 
/**
 * The Class TestTreasure.
 */

public class TestTreasure {
	
	/** The Constant NB_ROWS. */
	/* Largeur de l'�le */
	private static final int NB_ROWS = 30;
	
	/** The Constant NB_LINES. */
	/* Longueur de l'�le */
	private static final int NB_LINES = 30;
	
	/** The isl. */
	private Island isl;
	private Case[][] plateau;
	private Pirate pir;
	private Monkey monk;
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp(){	
		this.isl = mock(Island.class);
		this.plateau = new Case[NB_LINES ][NB_ROWS];
		this.pir = new Pirate(5, 5);
		this.monk = new CrazyMonkey(7, 7);
	    PowerMockito.mockStatic(Island.class);
		when(Island.getInstance()).thenReturn(isl);
		when(this.isl.getnbLines()).thenReturn(NB_LINES);
		when(this.isl.getnbRows()).thenReturn(NB_ROWS);
		for(int i = 0; i< NB_LINES; i++){
			for(int j = 0; j< NB_ROWS; j++){
				if(((i == 0 || i == NB_LINES-1) && j < NB_ROWS) || ((j == 0 || j == NB_ROWS-1)&& i < NB_LINES )){
					this.plateau[i][j] = new Case(i, j, CaseType.sea);
				}else{
					this.plateau[i][j] = new Case(i, j, CaseType.earth);
				}
			}
		}
		when(this.isl.getCase()).thenReturn(plateau);
		when(this.isl.collisionPirate(5, 5)).thenReturn(pir);
		when(this.isl.collisionMonkey(7, 7)).thenReturn(monk);

	}
	
	@Test
	public void testTreasureFound(){
		Treasure.getTreasure().setCoordinateX(5);
		Treasure.getTreasure().setCoordinateY(6);
		Treasure.getTreasure().setVisibility(false);
		try {
			this.pir.movementPirate(0, 1);
		}catch (CollisionException e){
			fail("Should throw exception when out of Island");
		}
		assertEquals("shouldn t have coordinate X", true, Treasure.getTreasure().getVisibility());
	}
	
	@Test
	public void testTreasureMonkey(){
		Treasure.getTreasure().setCoordinateX(7);
		Treasure.getTreasure().setCoordinateY(8);
		Treasure.getTreasure().setVisibility(false);
		try {
			this.monk.setPositionMonk(0, 1);
		}catch (CollisionException e){
			fail("Should throw exception when out of Island");
		}
		assertEquals("shouldn t have coordinate X", false, Treasure.getTreasure().getVisibility());
	}
}
