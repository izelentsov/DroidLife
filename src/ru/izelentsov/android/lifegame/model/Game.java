package ru.izelentsov.android.lifegame.model;

import java.io.IOException;


public class Game {

	
	
	public interface IListener {
		public void newGeneration ();
		public void generationChanged ();
	}
	
	
	private class VoidListener implements IListener {
		@Override
		public void newGeneration () {
		}
		@Override
		public void generationChanged () {
		}
	}
	

	
	private Generation currentGen = null;
	private Rules rules = null;
	private IListener listener = null;
	private int generationNumber = 1;
	
	
	
	public Game (int aWidth, int aHeight, Rules aRules) {
		currentGen = new Generation (aWidth, aHeight);
		rules = aRules;
		listener = new VoidListener ();
	}
	
	
	public void setListener (IListener aListener) {
		listener = (aListener != null) ? aListener : new VoidListener ();
	}
	

	public void resetGenerationNumber () {
		generationNumber = 1;
	}
	
	public int generationNumber () {
		return generationNumber;
	}
	
	
	public Rules rules () {
		return rules;
	}

	
	public void step () {
		currentGen = rules.makeNextGeneration (currentGen);
		++generationNumber;
		listener.generationChanged ();
	}
	
	
	private void setGeneration (Generation aGeneration) {
		currentGen = aGeneration;
		listener.newGeneration ();
	}
	
	
	public void setConf (GameConf aConf) {
		currentGen = new Generation (currentGen.width (), currentGen.height ());
		aConf.applyTo (currentGen);
		resetGenerationNumber ();
		listener.generationChanged ();
	}
	
	public GameConf getConf () {
		return currentGen.getConfiguration ();
	}
	
	
	
	public void setRules (Rules aRules) {
		rules = aRules;
	}
	
	
	public void print () {
		currentGen.print ();
	}
	
	
	public boolean isAlive (int xPos, int yPos) {
		return currentGen.isAlive (xPos, yPos);
	}
	
	
	public void setAlive (int xPos, int yPos, boolean anAliveFlag) {
		currentGen.setAlive (xPos, yPos, anAliveFlag);
		listener.generationChanged ();
	}
	
	public void toggleAlive (int xPos, int yPos) {
		currentGen.toggleAlive (xPos, yPos);
		listener.generationChanged ();
	}
	
	public int width () {
		return currentGen.width ();
	}

	public int height () {
		return currentGen.height ();
	}
	
	public void clear () {
		setGeneration (new Generation (currentGen.width (), currentGen.height ()));
		resetGenerationNumber ();
	}

	
	
	
	public static void main (String[] args) {
		Game g = new Game (10, 10, new Rules (3, 2, 3));
		g.setAlive (1, 3, true);
		g.setAlive (2, 3, true);
		g.setAlive (3, 3, true);
		g.setAlive (3, 2, true);
		g.setAlive (2, 1, true);
		
		int b = 0;
		
		for (;;) {
			g.print ();

			try {
				b = System.in.read ();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			if (b == 'q') {
				break;
			} else {
				g.step ();
			}
		}
	}




	
}
