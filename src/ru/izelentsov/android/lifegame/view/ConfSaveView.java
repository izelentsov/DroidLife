package ru.izelentsov.android.lifegame.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import ru.izelentsov.android.lifegame.R;




public class ConfSaveView {

	
	public interface IListener {
		public void saveRequested (String aSaveName);
		public void cancelRequested ();
	}

	private class VoidListener implements IListener {
		@Override
		public void saveRequested (String aSaveName) {
		}
		@Override
		public void cancelRequested () {
		}
	}
	
	
	private IListener listener = null;
	private EditText confNameEdit = null;
	private Button saveButton = null;
	private Button cancelButton = null;
	
	
	
	public ConfSaveView (Activity anActivity) {
		listener = new VoidListener ();
		anActivity.setContentView (R.layout.conf_save);
		setupControls (anActivity);
	}


	public void setListener (IListener aListener) {
		listener = (aListener != null) ? aListener : new VoidListener ();
	}
	
	

	private void setupControls (Activity anActivity) {
		confNameEdit = (EditText) anActivity.findViewById (R.id.confSaveEdit);
		// TODO
//		confNameEdit.setOnEditorActionListener (new NameEditListener ());
		
		saveButton = (Button) anActivity.findViewById (R.id.confSaveBtn);
		saveButton.setOnClickListener (new OnClickListener () {
			@Override
			public void onClick (View v) {
				listener.saveRequested (confNameEdit.getText ().toString ());
			}
		});
		
		cancelButton = (Button) anActivity.findViewById (R.id.confCancelBtn);
		cancelButton.setOnClickListener (new OnClickListener () {
			@Override
			public void onClick (View v) {
				listener.cancelRequested ();
			}
		});
	}

	
	
	
	// TODO
//	private class NameEditListener implements OnEditorActionListener {
//
//		@Override
//		public boolean onEditorAction (TextView aView, int arg1, KeyEvent arg2) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//		
//	}
	
}
