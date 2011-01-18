package ru.izelentsov.android.lifegame;

import ru.izelentsov.android.lifegame.logic.GameController;
import ru.izelentsov.android.lifegame.logic.GameSettings;
import ru.izelentsov.android.lifegame.view.GameView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;




public class LifeGameActivity extends Activity {
	
	private GameController sGameController = null;
	
	
	private GameSettings settings = null;
	private GameController gameController = null;
	private GameView gameView = null;
	
	
	private static final int SETTINGS_ACTIVITY = 0;
	private static final int CONF_SAVE_ACTIVITY = 1;
		
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = new GameSettings (this);
        if (sGameController == null) {
        	sGameController = new GameController (settings.defaultRules ());
        }
        gameController = sGameController; 

        gameView = new GameView (gameController);
        gameView.activate (this);
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
		menu.getItem (2).setEnabled (!gameIsRunning);
		return super.onPrepareOptionsMenu (menu);
	}
    
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	switch (item.getItemId ()) {
    	case R.id.miClear:
    		gameController.clearRequested ();
    		return true;
    	case R.id.miSettings:
    		startSettingsActivity ();
    		return true;
    	case R.id.miSaveConf:
    		startConfSaveActivity ();
    		return true;
    	default:
    		return super.onOptionsItemSelected (item);
    	}
    }
    
    
    private void startSettingsActivity () {
    	Intent i = new Intent(this, SettingsActivity.class);
    	startActivityForResult(i, SETTINGS_ACTIVITY);
    }

    
    private void startConfSaveActivity () {
    	Intent i = new Intent (this, ConfSaveLoadActivity.class);
    	startActivityForResult (i, CONF_SAVE_ACTIVITY);
    }
    


	@Override
	protected void onActivityResult (int requestCode, int resultCode,
			Intent data) {
		super.onActivityResult (requestCode, resultCode, data);
		
		switch (requestCode) {
		case SETTINGS_ACTIVITY:
			if (resultCode == RESULT_OK) {
				settings.applyTo (gameController);
				showSettings ();
			}
			break;
			
		case CONF_SAVE_ACTIVITY:
			if (resultCode == RESULT_OK) {
				sayConfSaved (
						data.getStringExtra (ConfSaveLoadActivity.NAME_EXTRA));
			}
			break;
		}
	}

	private void showSettings () {
		Toast.makeText (this, settings.getSummary (), Toast.LENGTH_LONG).show ();
	}

	
	private void sayConfSaved (String aSaveName) {
		Toast.makeText (this, getResources ().getText (R.string.confSavedMsg) + 
				": " + aSaveName, Toast.LENGTH_LONG).show ();
	}


    
}
