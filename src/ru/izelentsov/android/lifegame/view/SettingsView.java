package ru.izelentsov.android.lifegame.view;

import ru.izelentsov.android.lifegame.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;




public class SettingsView {

	
	public interface IListener {
		public void confirmRequested ();
		public void cancelRequested ();
	}

	private class VoidListener implements IListener {
		@Override
		public void confirmRequested () {
		}
		@Override
		public void cancelRequested () {
		}
	}
	
	
	private EditText intervalEdit = null;
	private CheckBox customRulesBox = null;
	private EditText nToAliveEdit = null;
	private EditText minNToLiveEdit = null;
	private EditText maxNToLiveEdit = null;
	private Button confirmButton = null;
	private Button cancelButton = null;
	
	private IListener listener = null;
	
	
	
	public SettingsView (Activity anActivity) {
		listener = new VoidListener ();
		anActivity.setContentView (R.layout.settings);
		
		setupControls (anActivity);
	}
	
	
	public void setListener (IListener aListener) {
		listener = (aListener != null) ? aListener : new VoidListener ();
	}
	
	
	
	private void setupControls (Activity anActivity) {
		intervalEdit = (EditText) anActivity.findViewById (R.id.intervalSettingEdit);
		customRulesBox = (CheckBox) anActivity.findViewById (R.id.customRulesCheckBox);
		nToAliveEdit = (EditText) anActivity.findViewById (R.id.neighsToAliveSettingEdit);
		minNToLiveEdit = (EditText) anActivity.findViewById (R.id.minNeighsToLiveSettingEdit);
		maxNToLiveEdit = (EditText) anActivity.findViewById (R.id.maxNeighsToLiveSettingEdit);
		
		customRulesBox.setOnCheckedChangeListener (new CustomRulesBoxListener ());
		
		
		confirmButton = (Button) anActivity.findViewById (R.id.confirmBtn);
		confirmButton.setOnClickListener (new OnClickListener () {
			@Override
			public void onClick (View v) {
				listener.confirmRequested ();
			}
		});
		
		cancelButton = (Button) anActivity.findViewById (R.id.cancelBtn);
		cancelButton.setOnClickListener (new OnClickListener () {
			@Override
			public void onClick (View v) {
				listener.cancelRequested ();
			}
		});
	}
	
	
	public void setInterval (long anInterval) {
		intervalEdit.setText (String.valueOf (anInterval));
	}
		
	public void setUseCustomRules (boolean aUseFlag) {
		customRulesBox.setChecked (aUseFlag);
	}
	
	public void setNToAlive (int aValue) {
		nToAliveEdit.setText (String.valueOf (aValue));
	}
	
	public void setMinNToLive (int aValue) {
		minNToLiveEdit.setText (String.valueOf (aValue));
	}
	
	public void setMaxNToLive (int aValue) {
		maxNToLiveEdit.setText (String.valueOf (aValue));
	}
		
	
	
	private void enableCustomRules (boolean isEnabled) {
		nToAliveEdit.setEnabled (isEnabled);
		minNToLiveEdit.setEnabled (isEnabled);
		maxNToLiveEdit.setEnabled (isEnabled);
	}
	
	
	
	public long getInterval () {
		long ret = -1;
		try {
			ret = Long.parseLong (intervalEdit.getText ().toString ());
		} catch (NumberFormatException e) {
			ret = -1;
		}
		return ret;
	}
	
	
	public boolean getUseCustomRules () {
		return customRulesBox.isChecked ();
	}
	
	
	public int getNToAlive () {
		return getNumericValue (nToAliveEdit.getText ().toString ().trim ());
	}
	
	public int getMinNToLive () {
		return getNumericValue (minNToLiveEdit.getText ().toString ().trim ());
	}
	
	public int getMaxNToLive () {
		return getNumericValue (maxNToLiveEdit.getText ().toString ().trim ());
	}
	
	
	
	private int getNumericValue (String aTextValue) {
		int ret = -1;
		try {
			ret = Integer.parseInt (aTextValue);
		} catch (NumberFormatException e) {
			ret = -1;
		}
		return ret;
	}
	
	
	
	private class CustomRulesBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged (CompoundButton buttonView,
				boolean isChecked) {
			enableCustomRules (isChecked);
		}
	}
	
}
