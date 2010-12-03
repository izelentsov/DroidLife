package ru.izelentsov.android.lifegame.view;

import ru.izelentsov.android.lifegame.R;
import ru.izelentsov.android.lifegame.logic.SettingsKeys;
import android.app.Activity;
import android.os.Bundle;
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
	
	
	
	public SettingsView (Bundle aSettingsBundle, Activity anActivity) {
		listener = new VoidListener ();
		anActivity.setContentView (R.layout.settings);
		
		setupControls (anActivity);
		setValues (aSettingsBundle);
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
	
	
	private void setValues (Bundle aSettingsBundle) {
		intervalEdit.setText (String.valueOf (
				aSettingsBundle.getLong (SettingsKeys.INTERVAL)));
		
		boolean useCustomRules = aSettingsBundle.getBoolean (SettingsKeys.CUSTOM_RULES);
		customRulesBox.setChecked (useCustomRules);
		
		nToAliveEdit.setText (String.valueOf (
				aSettingsBundle.getInt (SettingsKeys.N_TO_ALIVE)));
		minNToLiveEdit.setText (String.valueOf (
				aSettingsBundle.getInt (SettingsKeys.MIN_N_TO_LIVE)));
		maxNToLiveEdit.setText (String.valueOf (
				aSettingsBundle.getInt (SettingsKeys.MAX_N_TO_LIVE)));
		
		enableCustomRules (useCustomRules);
	}
	
	
	
	private void enableCustomRules (boolean isEnabled) {
		nToAliveEdit.setEnabled (isEnabled);
		minNToLiveEdit.setEnabled (isEnabled);
		maxNToLiveEdit.setEnabled (isEnabled);
	}
	
	
	
	public void storeSettings (Bundle aSettingsBundle) {
		storeInterval (aSettingsBundle);
		storeCustomRulesFlag (aSettingsBundle);
		storeCustomRules (aSettingsBundle);
	}
	
	
	
	private void storeInterval (Bundle aSettingsBundle) {
		long num = -1;
		try {
			num = Long.parseLong (intervalEdit.getText ().toString ());
		} catch (NumberFormatException e) {
			num = -1;
		}
		if (num >= 0) {
			aSettingsBundle.putLong (SettingsKeys.INTERVAL, num);
		}
	}
	
	
	private void storeCustomRules (Bundle aSettingsBundle) {
		storeNumericValue (nToAliveEdit.getText ().toString ().trim (), 
				SettingsKeys.N_TO_ALIVE, aSettingsBundle);
		storeNumericValue (minNToLiveEdit.getText ().toString ().trim (), 
				SettingsKeys.MIN_N_TO_LIVE, aSettingsBundle);
		storeNumericValue (maxNToLiveEdit.getText ().toString ().trim (), 
				SettingsKeys.MAX_N_TO_LIVE, aSettingsBundle);
	}
	
	
	
	private void storeCustomRulesFlag (Bundle aSettingsBundle) {
		aSettingsBundle.putBoolean (SettingsKeys.CUSTOM_RULES, 
				customRulesBox.isChecked ());
	}
	
	
	
	private void storeNumericValue (String aTextValue, String aSettingKey,
			Bundle aSettingsBundle) {
		int num = -1;
		try {
			num = Integer.parseInt (aTextValue);
		} catch (NumberFormatException e) {
			num = -1;
		}
		if (num >= 0) {
			aSettingsBundle.putInt (aSettingKey, num);
		}
	}
	
	
	
	private class CustomRulesBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged (CompoundButton buttonView,
				boolean isChecked) {
			enableCustomRules (isChecked);
		}
	}
	
}
