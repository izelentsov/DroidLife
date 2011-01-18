package ru.izelentsov.android.lifegame.test.model;

import java.io.IOException;
import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ru.izelentsov.android.lifegame.ConfSaveLoadActivity;
import ru.izelentsov.android.lifegame.model.ConfStorage;
import ru.izelentsov.android.lifegame.model.Game;
import ru.izelentsov.android.lifegame.model.GameConf;
import ru.izelentsov.android.lifegame.model.Rules;




public class ConfStorageTest extends 
	ActivityInstrumentationTestCase2<ConfSaveLoadActivity> {

	private ConfSaveLoadActivity activity = null;
	private ConfStorage storage = null;

	
	private static final Rules DEFAULT_RULES = new Rules (3, 2, 3);
	private static final String SAVE_NAME = "testSave";
	private static final String SAVE_NAME2 = "testSave2";
	
	
	public ConfStorageTest () {
		super ("ru.izelentsov.android.lifegame", ConfSaveLoadActivity.class);
	}

	@Override
	protected void setUp () throws Exception {
		super.setUp ();
		activity = this.getActivity ();
		storage = new ConfStorage ();
		storage.cleanStorage (activity);
	}
	
	
	
	
	public void testSerializeConf () {
		Game g = new Game (10, 10, DEFAULT_RULES);
		g.setAlive (2, 2, true);
		g.setAlive (2, 3, false);
		g.setAlive (12, 4, true);
		g.setAlive (18, 17, true);
		
		GameConf conf = g.getConf ();
		
		ConfStorage s = new ConfStorage ();
		String str = s.serializeConfForTests (conf);
		
		assertEquals ("2,2;0,0;0,2;6,5;", str);
	}
	
	
	
	public void testListStoredConfNames () {
		
		GameConf conf = createTestConf ();
		
		storeConf (conf, SAVE_NAME);
		checkFilesCount (1);
		checkRead (SAVE_NAME, conf);
		
		storeConf (conf, SAVE_NAME2);
		checkFilesCount (2);
		checkRead (SAVE_NAME, conf);
		checkRead (SAVE_NAME2, conf);
		
		storage.deleteConf (activity, SAVE_NAME);
		checkFilesCount (1);
		checkRead (SAVE_NAME2, conf);
		
		storage.deleteConf (activity, SAVE_NAME + "blah");
		checkFilesCount (1);
		checkRead (SAVE_NAME2, conf);
		
		storage.deleteConf (activity, SAVE_NAME2);
		checkFilesCount (0);
	}
	
	
	public void testCleanStorage () {
		GameConf conf = createTestConf ();

		checkFilesCount (0);
		storeConf (conf, SAVE_NAME);
		checkFilesCount (1);
		storeConf (conf, SAVE_NAME2);
		checkFilesCount (2);
		storage.cleanStorage (activity);
		checkFilesCount (0);
	}
	
	
	
	
	private void storeConf (GameConf aConf, String aSaveName) {
		try {
			storage.storeConf (activity, aConf, aSaveName);
		} catch (IOException e) {
			e.printStackTrace();
			assertFalse (e.getMessage (), true);
		}
	}
	
	
	private void checkFilesCount (int aCount) {
		ArrayList<String> list = storage.listStoredConfNames (activity);
		assertEquals (aCount, list.size ());
		
//		
//		Iterator<String> i = list.iterator ();
//		while (i.hasNext ()) {
//			String name = i.next ();
//			assertTrue ("Not a .sav file: " + name, 
//					name.endsWith (storage.fileExtForTests ()));
//		}
	}
	
	
	private void checkRead (String aReadName, GameConf aCheckConf) {
		GameConf readConf = null;
		try {
			readConf = storage.readConf (activity, aReadName);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue (e.getMessage (), false);
			return;
		}
		
		assertTrue ("Read config does not match", readConf.equals (aCheckConf));
	}

	
	
	private static GameConf createTestConf () {
		Game g = new Game (10, 10, DEFAULT_RULES);
		g.setAlive (2, 2, true);
		g.setAlive (2, 3, false);
		g.setAlive (12, 4, true);
		g.setAlive (18, 17, true);
		
		return g.getConf ();		
	}
	
}
