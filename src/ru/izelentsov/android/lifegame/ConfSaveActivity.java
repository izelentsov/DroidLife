package ru.izelentsov.android.lifegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ru.izelentsov.android.lifegame.view.ConfSaveView;




public class ConfSaveActivity extends Activity {

	public static final String SAVE_NAME_EXTRA = "saveName";
	private ConfSaveView view = null;
	
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		view = new ConfSaveView (this);
		view.setListener (new ViewListener ());
	}
	
	
	
	private class ViewListener implements ConfSaveView.IListener {

		@Override
		public void saveRequested (String aSaveName) {
			Intent i = new Intent ();
			i.putExtra (SAVE_NAME_EXTRA, aSaveName);
			setResult(RESULT_OK, i);
			finish ();
		}

		@Override
		public void cancelRequested () {
			setResult(RESULT_CANCELED);
			finish ();
		}
		
	}
	
}
