package fr.eseo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;

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

import fr.eseo.model.Case;
import fr.eseo.model.CaseType;
import fr.eseo.model.HunterMonkey;
import fr.eseo.model.Island;
import fr.eseo.model.Pirate;
import fr.eseo.model.StatePirate;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest(Island.class)

public class TestPametrizedMonkeys {

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
	public Point deplacement;
	
	@Parameters
    public static Collection<Object[]> dt() {
    	Object[][] data = new Object[][]{
    		{3, 3, new Point(0, 1)}, 
    		{3, 3, new Point(0, -1)}, 
    		{3, 3, new Point(1, 0)}, 
    		{3, 3, new Point(-1, 0)}, 
    		{4, 4, new Point(0, 1)}, 
    		{1, 1, new Point(0, -1)}
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
		int erreur = 0;
		HunterMonkey monk = new HunterMonkey(x, y);
		
		try{
			monk.setPositionMonk(deplacement.x, deplacement.y);
			erreur = 0;
		}catch(Exception e){
			erreur = 1;
		}
		
		if(erreur == 0){
			assertNotEquals("error should move x ", x+y, monk.getCoordinateX()+ monk.getCoordinateY());
		}else 
			assertEquals("error shouldn't move x ", x+y, monk.getCoordinateX()+ monk.getCoordinateY());

	}
	
	@Test
	public void testMovementByMonkey(){
		int erreur = 0;
		HunterMonkey monk = new HunterMonkey(x, y);
		HunterMonkey monk1 = new HunterMonkey(3, 4);
		HunterMonkey monk2 = new HunterMonkey(2, 3);
		when(this.isl.collisionMonkey(3, 4)).thenReturn(monk1);
		when(this.isl.collisionMonkey(2, 3)).thenReturn(monk2);
		try{
			monk.setPositionMonk(deplacement.x, deplacement.y);
			erreur = 0;
		}catch(Exception e){
			erreur = 1;
		}
		if(erreur == 0){
			assertNotEquals("error should move x ", x+y, monk.getCoordinateX()+ monk.getCoordinateY());
		}else 
			assertEquals("error shouldn't move x ", x+y, monk.getCoordinateX()+ monk.getCoordinateY());
	}
	
	@Test
	public void testMovementByPirate(){
		int erreur = 0;
		HunterMonkey monk = new HunterMonkey(x, y);
		Pirate pir = new Pirate(StatePirate.sober, 2,4);
		Pirate pir2 = new Pirate(StatePirate.sober, 3,4);
		Pirate pir3 = new Pirate(StatePirate.dead,4,3);
		Pirate pir4 = new Pirate(StatePirate.dead,4,2);
		when(this.isl.collisionPirate(2, 4)).thenReturn(pir);
		when(this.isl.collisionPirate(3, 4)).thenReturn(pir2);
		when(this.isl.collisionPirate(4, 3)).thenReturn(pir3);
		when(this.isl.collisionPirate(4, 2)).thenReturn(pir4);
		try{
			monk.setPositionMonk(deplacement.x, deplacement.y);
			erreur = 0;
		}catch(Exception e){
			erreur = 1;
		}
		
		if(erreur == 0){
			assertNotEquals("error should move x ", x+y, monk.getCoordinateX()+ monk.getCoordinateY());
			if(deplacement.x == 1 && deplacement.y == 0)
				assertEquals("error should be dead ", pir3.getState(), StatePirate.dead);
			else if(deplacement.x == 0 && deplacement.y == 1)
				assertEquals("error should be dead  ", pir2.getState(), StatePirate.dead);
			else if(deplacement.x == -1 && deplacement.y == 0)
				assertEquals("error should be dead  ", pir.getState(), StatePirate.dead);
			else if(deplacement.x == 0 && deplacement.y == -1)
				assertEquals("error should be dead  ", pir4.getState(), StatePirate.dead);
		}else 
			assertEquals("error shouldn't move x ", x+y, monk.getCoordinateX()+ monk.getCoordinateY());
	}
}
