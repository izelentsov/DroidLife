package ru.izelentsov.android.lifegame.model;

import java.util.ArrayList;
import java.util.Iterator;



public class GameConf {

	
	private class Cell {
		private int x = 0;
		private int y = 0;
		public Cell (int anX, int anY) {
			x = anX;
			y = anY;
		}
		public int x () {
			return x;
		}
		public int y () {
			return y;
		}
	}
	
	
	private ArrayList<Cell> cells = null;
	private Cell leftTopCell = null;

	
	
	public GameConf () {
		cells = new ArrayList<Cell> ();
	}
	
	public GameConf (int aLeftPos, int aTopPos) {
		this ();
		leftTopCell = new Cell (aLeftPos, aTopPos);
	}

	
	
	
	public void addCell (int xPos, int yPos) {
		cells.add (new Cell (xPos, yPos));
	}

	
	public void applyTo (Generation aGen) {
		Iterator<Cell> i = cells.iterator ();
		while (i.hasNext ()) {
			Cell c = i.next ();
			aGen.setAlive (c.x () + left (), c.y () + top (), true);
		}
	}
	
	public void applyWithoutWrapTo (Generation aGen) {
		Iterator<Cell> i = cells.iterator ();
		int xPos = 0;
		int yPos = 0;
		while (i.hasNext ()) {
			Cell c = i.next ();
			xPos = c.x () + left ();
			yPos = c.y () + top ();
			if ((xPos < aGen.width ()) && (yPos < aGen.height ())) {
				aGen.setAlive (xPos, yPos, true);
			}
		}
	}	
	
	
	
	public void setLeftTopCell (int xPos, int yPos) {
		leftTopCell = new Cell (xPos, yPos);
	}
	
	public void resetLeftTopCell () {
		leftTopCell = null;
	}
	
	public boolean isLeftTopSet () {
		return leftTopCell != null;
	}
	
	public int left () {
		return isLeftTopSet () ? leftTopCell.x () : 0;
	}
	
	public int top () {
		return isLeftTopSet () ? leftTopCell.y () : 0;
	}
	
}
