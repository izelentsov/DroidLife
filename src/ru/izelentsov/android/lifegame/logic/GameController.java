package ru.izelentsov.android.lifegame.logic;

import java.util.Timer;
import java.util.TimerTask;

import ru.izelentsov.android.lifegame.model.Game;


public class GameController {

	
	private Game game = null;
	private Timer runTimer = null;
	private long runStepIntervalMS = 1000;
	
	
	public GameController (Game aGame) {
		game = aGame;
	}
	
	
	
	public void setRunStepInterval (long anIntervalMS) {
		runStepIntervalMS = anIntervalMS;
	}
	
	public long getRunStepIntervalMS () {
		return runStepIntervalMS;
	}
	
	
	public void cellToggleRequested (int xPos, int yPos) {
		game.toggleAlive (xPos, yPos);
	}


	public void stepRequested () {
		game.step ();
	}
	
	
	public void clearRequested () {
		game.clear ();
	}
	
	
	public void runToggleRequested () {
		if (runTimer != null) {
			runTimer.cancel ();
			runTimer = null;
		} else {
			runTimer = new Timer (true);
			runTimer.schedule (
					new TimerTask () {
						@Override
						public void run () {
							game.step ();
						}
					},
					runStepIntervalMS , runStepIntervalMS);
		}
	}
	
	
	public boolean isRunning () {
		return runTimer != null;
	}


}
