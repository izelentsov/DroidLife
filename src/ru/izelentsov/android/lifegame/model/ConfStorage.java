package ru.izelentsov.android.lifegame.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ru.izelentsov.android.lifegame.model.GameConf.Cell;

import android.content.Context;



public class ConfStorage {

	
	private static final String STORE_DIR_NAME = "saves";
	private static final String SAVE_FILE_EXT = ".sav";
	
	
	public ConfStorage () {
	}

	
	public ArrayList<String> listStoredConfNames (Context aContext) {
		File[] list = storeDir (aContext).listFiles ();
		ArrayList<String> ret = new ArrayList<String> ();

		for (int i = 0; i < list.length; ++i) {
			if (list[i].getName ().endsWith (SAVE_FILE_EXT)) {
				ret.add (list[i].getName ());
			}
		}
		return ret;
	}

	
	public GameConf readConf (Context aContext, String aConfName) 
	throws IOException {
		BufferedReader in = new BufferedReader (new FileReader (
				storeFile (aContext, aConfName)));
		StringBuffer buf = new StringBuffer ();
		String str = null;
		
		try {
			while ((str = in.readLine ()) != null) {
				buf.append (str);
			}
		} finally {
			in.close ();
		}
		
		return parseConf (buf.toString ());
	}
	
	
	public void storeConf (Context aContext, GameConf aConf, String aSaveName) 
	throws IOException {
		if ((aSaveName == null) || (aSaveName.length () == 0)) {
			throw new IOException ("Bad save name: " + aSaveName);
		}
		
		FileWriter writer = new FileWriter (storeFile (aContext, aSaveName));
		
		try {
			writer.write (serializeConf (aConf));
		} finally {
			writer.close ();
		}
	}
	
	
	public void deleteConf (Context aContext, String aConfName) {
		if ((aConfName == null) || (aConfName.length () == 0)) {
			return;
		}
		
		File fileToDelete = storeFile (aContext, aConfName);
		fileToDelete.delete ();
	}
	
	
	
	private String serializeConf (GameConf aConf) {
		aConf.normalize ();
		
		StringBuffer buf = new StringBuffer ();
		buf.append (aConf.left ()).append (",").append (aConf.top ()).append (";");
	
		Iterator<GameConf.Cell> i = aConf.cellIterator ();
		
		while (i.hasNext ()) {
			Cell c = i.next ();
			buf.append (c.x ()).append (",").append (c.y ()).append (";");
		}
		
		return buf.toString ();
	}
	
	
	
	private GameConf parseConf (String aString) {
		String[] tokens = aString.split (";");
		String[] pair = null;
		
		GameConf conf = new GameConf ();
		pair = tokens[0].split (",");
		conf.setLeftTopCell (
				Integer.parseInt (pair[0]), Integer.parseInt (pair[1]));
		
		
		for (int i = 1; i < tokens.length; ++i) {
			pair = tokens[i].split (",");
			conf.addCell (
					Integer.parseInt (pair[0]), Integer.parseInt (pair[1]));
		}
		
		return conf;
	}
	
	
	
	
	
	private File storeDir (Context aContext) {
		return aContext.getDir (STORE_DIR_NAME, Context.MODE_PRIVATE);
	}
	
	private String fileName (String aConfName) {
		return aConfName + SAVE_FILE_EXT;
	}
	
	private File storeFile (Context aContext, String aConfName) {
		return new File (storeDir (aContext), fileName (aConfName));
	}
	
	

	
	
	public String serializeConfForTests (GameConf aConf) {
		return serializeConf (aConf);
	}
	
	public String fileExtForTests () {
		return SAVE_FILE_EXT;
	}
	
}
