package ru.izelentsov.android.lifegame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;



public class GameConf implements Serializable {

	
	public class Cell {
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
		
		void modify (int aNewX, int aNewY) {
			x = aNewX;
			y = aNewY;
		}
		
		@Override
		public boolean equals (Object o) {
			if (!(o instanceof Cell)) {
				return false;
			}
			Cell c = (Cell) o;
			return (x == c.x) && (y == c.y);
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
	
	
	public void normalize () {
		Iterator<Cell> i = cells.iterator ();
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		while (i.hasNext ()) {
			Cell c = i.next ();
			if (c.x () < minX) {
				minX = c.x ();
			}
			if (c.y () < minY) {
				minY = c.y ();
			}
		}
		
		i = cells.iterator ();
		while (i.hasNext ()) {
			Cell c = i.next ();
			c.modify (c.x () - minX, c.y () - minY);
		}
		
		if ((minX > 0) && (minY > 0)) {
			setLeftTopCell (minX, minY);
		}
	}
	

	public Iterator<Cell> cellIterator () {
		return cells.iterator ();
	}
	
	
	@Override
	public boolean equals (Object anObject) {
		if (!(anObject instanceof GameConf)) {
			return false;
		}
		
		GameConf anotherConf = (GameConf) anObject;
		
		if ((left() != anotherConf.left ()) || (top () != anotherConf.top ())) {
			return false;
		}
		
		Iterator<Cell> myI = cellIterator ();
		Iterator<Cell> hisI = anotherConf.cellIterator ();
		
		while (myI.hasNext ()) {
			if (!hisI.hasNext ()) {
				return false;
			}
			Cell myCell = myI.next ();
			Cell hisCell = hisI.next ();
			if (!myCell.equals (hisCell)) {
				return false;
			}
		}
		
		if (hisI.hasNext ()) {
			return false;
		}
		
		return true;
	}
	
}
