package fr.eseo.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.lang.Object;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import fr.eseo.model.*;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest(Island.class)
public class TestPirateParameterized {
	
	/** The isl. */
	private Island isl;
	private Case[][] plateau;
	
	@Parameter
	public int x;

	/**
	 * 2e param�tre, repr�sentant la longueur du deuxi�me c�t� du triangle.
	 */
	@Parameter (value = 1)
	public int y;

	/**
	 * 4e param�tre, repr�sentant le type attendu du triangle.
	 */
	@Parameter (value = 2)
	public StatePirate etat;
	
	
	/**
	 * 4e param�tre, repr�sentant le type attendu du triangle.
	 */
	@Parameter (value = 3)
	public Point deplacement;
	
	@Parameters
    public static Collection<Object[]> dt() {
    	Object[][] data = new Object[][]{
    		{3, 3, StatePirate.sober, new Point(0, 1)}, 
    		{3, 3, StatePirate.sober, new Point(0, -1)}, 
    		{3, 3, StatePirate.sober, new Point(1, 0)}, 
    		{3, 3, StatePirate.sober, new Point(-1, 0)}, 
    		{4, 4, StatePirate.sober, new Point(0, 1)}, 
    		{1, 1, StatePirate.sober, new Point(0, -1)}, 
    		{4, 4, StatePirate.sober, new Point(1, 0)}, 
    		{1, 1, StatePirate.sober, new Point(-1, 0)},
    		{3, 3, StatePirate.dead, new Point(0, 1)}, 
    		{3, 3, StatePirate.dead, new Point(0, -1)}, 
    		{3, 3, StatePirate.dead, new Point(1, 0)}, 
    		{3, 3, StatePirate.dead, new Point(-1, 0)}, 
    		{4, 4, StatePirate.dead, new Point(0, 1)}, 
    		{1, 1, StatePirate.dead, new Point(0, -1)}, 
    		{4, 4, StatePirate.dead, new Point(1, 0)}, 
    		{1, 1, StatePirate.dead, new Point(-1, 0)}
    	};
    	return Arrays.asList(data);
    }
        
	@Before
	public void setUp(){	
		this.isl = mock(Island.class);
		this.plateau = new Case[6][6];
	    PowerMockito.mockStatic(Island.class);
		when(Island.getInstance()).thenReturn(isl);
		when(this.isl.getnbLines()).thenReturn(6);
		when(this.isl.getnbRows()).thenReturn(6);
		for(int i = 0; i<6; i++){
			for(int j = 0; j<6; j++){
				if(((i == 0 || i == 5) && j < 6) || ((j == 0 || j == 5)&& i < 6 )){
					this.plateau[i][j] = new Case(i, j, CaseType.sea);
				}else{
					this.plateau[i][j] = new Case(i, j, CaseType.earth);
				}
			}
		}
		when(this.isl.getCase()).thenReturn(plateau);
	}
    
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void testMovementSolo(){
		int erreur;
		Pirate pir = new Pirate(etat, x, y);
		try{
			pir.movementPirate(deplacement.x, deplacement.y);
			erreur = 0;
		}catch(Exception e){
			erreur = 1;
		}
		if(deplacement.x != 0 && erreur ==0){
			if(etat == StatePirate.sober){
				assertNotEquals("error should move x", x, pir.getCoordinateX());
			} else if(etat == StatePirate.dead){
				assertEquals("error shouldn't move x ", x, pir.getCoordinateX());
			  }
		}else if(deplacement.y != 0 && erreur ==0){
				if(etat == StatePirate.sober){
					assertNotEquals("error should move y", y, pir.getCoordinateY());
				}else if(etat == StatePirate.dead){
					assertEquals("error shouldn't move y ", y, pir.getCoordinateY());
				}
		}else{
			assertEquals("error shouldn't move x ", x, pir.getCoordinateX());
			assertEquals("error shouldn't move y ", y, pir.getCoordinateY());
		// Validation du r�sultat (avec check) :
		}

	}
	
	@Test
	public void testMovementByPirate(){
		Pirate pir = new Pirate(etat, x, y);
		Pirate pir2 = new Pirate(StatePirate.sober, 3,4);
		Pirate pir3 = new Pirate(StatePirate.dead,4,3);
		when(this.isl.collisionPirate(3, 4)).thenReturn(pir2);
		when(this.isl.collisionPirate(4, 3)).thenReturn(pir3);
		try{
			pir.movementPirate(deplacement.x, deplacement.y);
		}catch(Exception e){
			
		}
		if(x == 3 && y == 3 && deplacement.x == 0 && deplacement.y == 1 && etat != StatePirate.dead){
			assertEquals("error shouldn't move x ", pir.getCoordinateX(), 3);
			assertEquals("error shouldn't move y ", pir.getCoordinateY(), 3);
		}else if(x == 3 && y == 3 && deplacement.x == 1 && deplacement.y == 0 && etat != StatePirate.dead){
			assertEquals("error should move x ", pir.getCoordinateX(), 4);
			assertEquals("error should move y ", pir.getCoordinateY(), 3);
		}

	}
	
	@Test
	public void testMovementByMonkey(){
		Pirate pir = new Pirate(etat, x, y);
		HunterMonkey monk1 = new HunterMonkey(3,4);
		HunterMonkey monk2 = new HunterMonkey(3,2);
		HunterMonkey monk3 = new HunterMonkey(4,3);
		HunterMonkey monk4 = new HunterMonkey(2,3);
		
		when(this.isl.collisionMonkey(3, 4)).thenReturn(monk1);
		when(this.isl.collisionMonkey(3, 2)).thenReturn(monk2);
		when(this.isl.collisionMonkey(4, 3)).thenReturn(monk3);
		when(this.isl.collisionMonkey(2, 3)).thenReturn(monk4);
		try{
			pir.movementPirate(deplacement.x, deplacement.y);
		}catch(Exception e){
			
		}
		if(x == 3 && y == 3 && (deplacement.x != 0 || deplacement.y != 0) && etat != StatePirate.dead){
			assertEquals("error should be dead ", pir.getState(), StatePirate.dead);
		}

	}
    
} 
