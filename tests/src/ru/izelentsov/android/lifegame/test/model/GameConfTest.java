package ru.izelentsov.android.lifegame.test.model;

import ru.izelentsov.android.lifegame.model.GameConf;
import junit.framework.TestCase;


public class GameConfTest extends TestCase {

	
	public void testEquals () {
		GameConf conf1 = new GameConf ();
		conf1.addCell (2, 2);
		conf1.addCell (2, 3);

		GameConf conf2 = new GameConf ();
		conf2.addCell (2, 2);
		conf2.addCell (2, 3);
		
		
		assertTrue (conf1.equals (conf2));
	}
	
}
