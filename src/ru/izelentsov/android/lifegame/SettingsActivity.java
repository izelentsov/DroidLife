package ru.izelentsov.android.lifegame;

import ru.izelentsov.android.lifegame.view.SettingsView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SettingsActivity extends Activity {

	private Bundle settingsBundle = null;
	private SettingsView settingsView = null;
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		settingsBundle = new Bundle (getIntent ().getExtras ());
		settingsView = new SettingsView (settingsBundle, this);
		settingsView.setListener (new ViewListener ());
	}
	
	
	
	private class ViewListener implements SettingsView.IListener {

		@Override
		public void confirmRequested () {
			settingsView.storeSettings (settingsBundle);
	
			Intent intent = new Intent();
			intent.putExtras (settingsBundle);
			setResult(RESULT_OK, intent);
			finish ();
		}

		@Override
		public void cancelRequested () {
			setResult(RESULT_CANCELED);
			finish ();
		}
		
	}

//
//	@Override
//	public void onBackPressed () {
//		Intent intent = new Intent();
//		intent.putExtras (settingsBundle);
//		setResult(RESULT_OK, intent);
//
//		super.onBackPressed ();
//	}
//
//
//	@Override
//	protected void onPause () {
//		settingsView.storeSettings ();
//		settingsBundle.putInt ("Optional", 1);
//		Intent intent = new Intent();
//		intent.putExtras (settingsBundle);
//		intent.putExtra ("Optional2", 2);
//		setResult(RESULT_OK, intent);
//		super.onPause ();
//	}


	

}
