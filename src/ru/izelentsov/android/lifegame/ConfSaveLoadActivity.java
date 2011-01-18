package ru.izelentsov.android.lifegame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import ru.izelentsov.android.lifegame.model.ConfStorage;
import ru.izelentsov.android.lifegame.view.ConfSaveLoadView;




public class ConfSaveLoadActivity extends Activity {

	public static final String NAME_EXTRA = "saveName";
	public static final String ACTION_EXTRA = "action";
	private ConfSaveLoadView view = null;
	private ConfStorage storage = null;
	
	
	public enum Actions {
		SAVE (0),
		LOAD (1);
	
		private int code = -1;
		private Actions (int aCode) {
			code = aCode;
		}
		public int code () {
			return code;
		}
	}
	
	
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		view = new ConfSaveLoadView (this);
		view.setListener (new ViewListener ());
		storage = new ConfStorage ();
	}
	
	
	
	
	
	
	private class ViewListener implements ConfSaveLoadView.IListener {

		@Override
		public void saveRequested (String aSaveName) {
			Intent i = new Intent ();
			i.putExtra (NAME_EXTRA, aSaveName);
			i.putExtra (ACTION_EXTRA, Actions.SAVE.code ());
			setResult(RESULT_OK, i);
			finish ();
		}

		@Override
		public void loadRequested (String aSaveName) {
			Intent i = new Intent ();
			i.putExtra (NAME_EXTRA, aSaveName);
			i.putExtra (ACTION_EXTRA, Actions.LOAD.code ());
			setResult(RESULT_OK, i);
			finish ();
		}
		
		@Override
		public void deleteRequested (String aSaveName) {
			storage.deleteConf (ConfSaveLoadActivity.this, aSaveName);
			Toast.makeText (ConfSaveLoadActivity.this, 
					getResources ().getText (R.string.confDeletedMsg) + 
					": " + aSaveName, Toast.LENGTH_LONG).show ();
		}

		@Override
		public void cancelRequested () {
			setResult(RESULT_CANCELED);
			finish ();
		}


		@Override
		public String confNameChoiceRequested () {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	

	
}
