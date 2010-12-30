package ru.izelentsov.android.lifegame.test.model;

import ru.izelentsov.android.lifegame.model.Game;
import ru.izelentsov.android.lifegame.model.GameConf;
import ru.izelentsov.android.lifegame.model.Rules;
import junit.framework.TestCase;





public class GameTest extends TestCase {

	
	private final Rules DEFAULT_RULES = new Rules (3, 2, 3);
	
	
	public void testWrap () {
		Game g = new Game (10, 10, DEFAULT_RULES);
		g.setAlive (2, 2, true);
		g.setAlive (2, 3, false);
		g.setAlive (12, 4, true);
		g.setAlive (18, 17, true);
		
		assertEquals (false, g.isAlive (2, 1));
		assertEquals (true,  g.isAlive (2, 2));
		assertEquals (false, g.isAlive (2, 3));
		assertEquals (true,  g.isAlive (2, 4));
		assertEquals (true,  g.isAlive (12, 4));
		assertEquals (true,  g.isAlive (22, 4));
		assertEquals (true,  g.isAlive (8, 7));
		assertEquals (true,  g.isAlive (18, 17));
		
		g.toggleAlive (18, 17);
		
		assertEquals (false,  g.isAlive (18, 17));
		assertEquals (false,  g.isAlive (48, 97));
	}
	
	
	public void testGetConfiguration () {
		Game g = new Game (10, 10, DEFAULT_RULES);
		g.setAlive (2, 2, true);
		g.setAlive (2, 3, false);
		g.setAlive (12, 4, true);
		g.setAlive (18, 17, true);
		
		GameConf conf = g.getConf ();
		
		Game g1 = new Game (10, 10, DEFAULT_RULES);
		g1.setConf (conf);
		
		assertEquals (false, g.isAlive (2, 1));
		assertEquals (true,  g.isAlive (2, 2));
		assertEquals (false, g.isAlive (2, 3));
		assertEquals (true,  g.isAlive (2, 4));
		assertEquals (true,  g.isAlive (12, 4));
		assertEquals (true,  g.isAlive (22, 4));
		assertEquals (true,  g.isAlive (8, 7));
		assertEquals (true,  g.isAlive (18, 17));
	}
}
