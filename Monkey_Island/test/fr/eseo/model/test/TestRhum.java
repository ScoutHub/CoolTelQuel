package fr.eseo.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.*;

import fr.eseo.model.Case;
import fr.eseo.model.CaseType;
import fr.eseo.model.CollisionException;
import fr.eseo.model.CrazyMonkey;
import fr.eseo.model.Island;
import fr.eseo.model.Monkey;
import fr.eseo.model.Pirate;
import fr.eseo.model.Rhum;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Island.class)

/**
 * The Class TestRhum.
 */

public class TestRhum {
	
	/** The Constant NB_ROWS. */
	/* Largeur de l'�le */
	private static final int NB_ROWS = 30;
	
	/** The Constant NB_LINES. */
	/* Longueur de l'�le */
	private static final int NB_LINES = 30;
	
	private Island isl;
	private Case[][] plateau;
	private Rhum rhum;
	private Pirate pir;
	private Monkey monk;
	
	@Before
	public void setUp(){
		this.isl = mock(Island.class);
	    PowerMockito.mockStatic(Island.class);
		this.plateau = new Case[NB_LINES][NB_ROWS];
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

		this.pir = new Pirate(5, 5);
		this.monk = new CrazyMonkey(7, 7);
		when(this.isl.getCase()).thenReturn(plateau);
		when(this.isl.collisionPirate(5, 5)).thenReturn(pir);
		when(this.isl.collisionMonkey(7, 7)).thenReturn(monk);
	}
	
	
	@Test
	public void testRhumFound(){
		this.rhum = new Rhum(5, 6, true);
		when(this.isl.collisionRhum (5, 6)).thenReturn(rhum);
		try {
			this.pir.movementPirate(0, 1);
		}catch (CollisionException e){
			fail("Should throw exception when out of Island");
		}
		assertEquals("shouldn t have coordinate X", false, rhum.getVisibility());
	}
	
	@Test
	public void testRhumMonkey(){
		this.rhum = new Rhum(7, 8, true);
		when(this.isl.collisionRhum (7, 8)).thenReturn(rhum);
		try {
			this.monk.setPositionMonk(0, 1);
		}catch (CollisionException e){
			fail("Should throw exception when out of Island");
		}
		assertEquals("shouldn t have coordinate X", true, rhum.getVisibility());
	}

}
