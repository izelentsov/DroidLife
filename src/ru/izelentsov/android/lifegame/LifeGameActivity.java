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
	
	private GameSettings settings = null;
	private GameController gameController = null;
	private GameView gameView = null;
	
	
	private static final int SETTINGS_ACTIVITY = 0;
		
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = new GameSettings (getPreferences (MODE_PRIVATE));
        gameController = new GameController (settings.defaultRules ());
        settings.setGameController (gameController);

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
    	default:
    		return super.onOptionsItemSelected (item);
    	}
    }
    
    
    private void startSettingsActivity () {
    	Intent i = new Intent(this, SettingsActivity.class);
    	i.putExtras (settings.get ());
    	startActivityForResult(i, SETTINGS_ACTIVITY);
    }



	@Override
	protected void onActivityResult (int requestCode, int resultCode,
			Intent data) {
		super.onActivityResult (requestCode, resultCode, data);
		
		switch (requestCode) {
		case SETTINGS_ACTIVITY:
			if (resultCode == RESULT_OK) {
				settings.apply (data.getExtras ());
				showSettings ();
			}
			break;
		}
	}

	private void showSettings () {
		Toast.makeText (this, settings.getSummary (), Toast.LENGTH_LONG).show ();
	}



    
}
