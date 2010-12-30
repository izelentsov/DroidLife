package ru.izelentsov.android.lifegame;

import ru.izelentsov.android.lifegame.logic.GameSettings;
import ru.izelentsov.android.lifegame.view.SettingsView;
import android.app.Activity;
import android.os.Bundle;



public class SettingsActivity extends Activity {

	private SettingsView settingsView = null;
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		GameSettings settings = new GameSettings (this);
		settingsView = new SettingsView (this);
		settingsView.setListener (new ViewListener ());
		
		settingsView.setInterval (settings.getInterval ());
		settingsView.setUseCustomRules (settings.getUseCustomRules ());
		settingsView.setNToAlive (settings.getNToAlive ());
		settingsView.setMinNToLive (settings.getMinNToLive ());
		settingsView.setMaxNToLive (settings.getMaxNToLive ());
	}
	
	
	
	private class ViewListener implements SettingsView.IListener {

		@Override
		public void confirmRequested () {
			GameSettings settings = new GameSettings (SettingsActivity.this);
			settings.setInterval (settingsView.getInterval ());
			settings.setUseCustomRules (settingsView.getUseCustomRules ());
			settings.setNToAlive (settingsView.getNToAlive ());
			settings.setMinNToLive (settingsView.getMinNToLive ());
			settings.setMaxNToLive (settingsView.getMaxNToLive ());
			
			setResult(RESULT_OK);
			finish ();
		}

		@Override
		public void cancelRequested () {
			setResult(RESULT_CANCELED);
			finish ();
		}
		
	}

}
