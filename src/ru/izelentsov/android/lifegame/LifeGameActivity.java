package ru.izelentsov.android.lifegame;

import ru.izelentsov.android.lifegame.logic.GameController;
import ru.izelentsov.android.lifegame.model.Game;
import ru.izelentsov.android.lifegame.model.Rules;
import ru.izelentsov.android.lifegame.model.SettingsKeys;
import ru.izelentsov.android.lifegame.view.GameView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;




public class LifeGameActivity extends Activity {
	
	private Game game = null;
	private GameController gameController = null;
	private GameView gameView = null;
	private final Rules DEFAULT_RULES = new Rules (3, 2, 3);
	private Rules customRules = null;
	
	
	private static final int SETTINGS_ACTIVITY = 0;
		
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        customRules = DEFAULT_RULES;
        
        game = new Game (14, 14, DEFAULT_RULES);
        game.setAlive (1, 1, true);
        game.setAlive (1, 2, true);
        game.setAlive (2, 2, true);
        
        gameController = new GameController (game);
        gameView = new GameView (game, gameController);

        setGameView ();
    }

    
    
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
    	MenuInflater menuInflater = getMenuInflater ();
    	menuInflater.inflate (R.menu.game_menu, menu);
    	return true;
    }
    
    
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		boolean gameIsRunning = gameController.isRunning ();
		menu.getItem (0).setEnabled (!gameIsRunning);
		menu.getItem (1).setEnabled (!gameIsRunning);
		return super.onPrepareOptionsMenu (menu);
	}
    
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	switch (item.getItemId ()) {
    	case R.id.miClear:
    		gameController.clearRequested ();
    		return true;
    	case R.id.miSettings:
    		setSettingsView ();
    		return true;
    	default:
    		return super.onOptionsItemSelected (item);
    	}
    }
    
    
    private void setGameView () {
    	gameView.activate (this);
    }
    
    
    private void setSettingsView () {
//    	setContentView(R.layout.settings);
    	
    	Intent i = new Intent(this, SettingsActivity.class);
    	
    	Bundle settingsExtras = new Bundle ();
    	
    	settingsExtras.putLong (
    			SettingsKeys.INTERVAL, gameController.getRunStepIntervalMS ());
    	settingsExtras.putBoolean (
    			SettingsKeys.CUSTOM_RULES, !game.rules ().equals (DEFAULT_RULES));
    	settingsExtras.putInt (
    			SettingsKeys.N_TO_ALIVE, customRules.neighsToAlive ());
    	settingsExtras.putInt (
    			SettingsKeys.MIN_N_TO_LIVE, customRules.minNeighsToLive ());
    	settingsExtras.putInt (
    			SettingsKeys.MAX_N_TO_LIVE, customRules.maxNeighsToLive ());
    	i.putExtras (settingsExtras);
    	startActivityForResult(i, SETTINGS_ACTIVITY);
    }



	@Override
	protected void onActivityResult (int requestCode, int resultCode,
			Intent data) {
		super.onActivityResult (requestCode, resultCode, data);
		
		switch (requestCode) {
		case SETTINGS_ACTIVITY:
			if (resultCode == RESULT_OK) {
				
				Bundle settingsBundle = data.getExtras ();
				showSettings (settingsBundle);
				
				boolean useCustomRules = 
					settingsBundle.getBoolean (SettingsKeys.CUSTOM_RULES);
				
				if (useCustomRules) {
					customRules = new Rules (
							settingsBundle.getInt (SettingsKeys.N_TO_ALIVE),
							settingsBundle.getInt (SettingsKeys.MIN_N_TO_LIVE),
							settingsBundle.getInt (SettingsKeys.MAX_N_TO_LIVE));
					game.setRules (customRules);
				} else {
					game.setRules (DEFAULT_RULES);
				}
				gameController.setRunStepInterval (
						settingsBundle.getLong (SettingsKeys.INTERVAL));
				
			}
			break;
		}
	}

	
	
	private void showSettings (Bundle aSettingsBundle) {
		StringBuffer summary = new StringBuffer ();

		summary.append ("Interval: ").append (
				aSettingsBundle.getLong (SettingsKeys.INTERVAL)).append ("\n");
		summary.append ("Custom: ").append (
				aSettingsBundle.getBoolean (SettingsKeys.CUSTOM_RULES)).append ("\n");
		summary.append ("N to alive: ").append (
				aSettingsBundle.getInt (SettingsKeys.N_TO_ALIVE)).append ("\n");
		summary.append ("Min N to live: ").append (
				aSettingsBundle.getInt (SettingsKeys.MIN_N_TO_LIVE)).append ("\n");
		summary.append ("Max N to live: ").append (
				aSettingsBundle.getInt (SettingsKeys.MAX_N_TO_LIVE)).append ("\n");

		Toast.makeText (this, summary.toString (), Toast.LENGTH_LONG).show ();
	}



    
}
