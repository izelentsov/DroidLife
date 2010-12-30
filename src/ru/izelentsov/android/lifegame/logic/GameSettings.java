package ru.izelentsov.android.lifegame.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import ru.izelentsov.android.lifegame.model.Rules;




public class GameSettings {

	private static final String SETTINGS_FILE_NAME = "lifeGameRules";
	private final Rules DEFAULT_RULES = new Rules (3, 2, 3);
	
	private SharedPreferences prefs = null;
	
	
	public GameSettings (Context aContext) {
		prefs = aContext.getSharedPreferences (
				SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
	}
	

	
	
	public void applyTo (GameController aController) {
		Bundle settingsBundle = get ();
		boolean useCustomRules = 
			settingsBundle.getBoolean (SettingsKeys.CUSTOM_RULES);
		
		if (useCustomRules) {
			Rules customRules = new Rules (
					settingsBundle.getInt (SettingsKeys.N_TO_ALIVE),
					settingsBundle.getInt (SettingsKeys.MIN_N_TO_LIVE),
					settingsBundle.getInt (SettingsKeys.MAX_N_TO_LIVE));
			aController.game ().setRules (customRules);
		} else {
			aController.game ().setRules (DEFAULT_RULES);
		}
		aController.setRunStepInterval (
				settingsBundle.getLong (SettingsKeys.INTERVAL));
	}
	
	
	
	public Bundle get () {
		Bundle settings = new Bundle ();
    	
    	settings.putLong (SettingsKeys.INTERVAL, 
    			prefs.getLong (SettingsKeys.INTERVAL, 500));
    	settings.putBoolean (SettingsKeys.CUSTOM_RULES, 
    			prefs.getBoolean (SettingsKeys.CUSTOM_RULES, false));
    	settings.putInt (SettingsKeys.N_TO_ALIVE, 
    			prefs.getInt (SettingsKeys.N_TO_ALIVE, 3));
    	settings.putInt (SettingsKeys.MIN_N_TO_LIVE, 
    			prefs.getInt (SettingsKeys.MIN_N_TO_LIVE, 2));
    	settings.putInt (SettingsKeys.MAX_N_TO_LIVE, 
    			prefs.getInt (SettingsKeys.MAX_N_TO_LIVE, 3));
    	
    	return settings;
	}
	
	
	public long getInterval () {
		return prefs.getLong (SettingsKeys.INTERVAL, 500);
	}
	
	public boolean getUseCustomRules () {
		return prefs.getBoolean (SettingsKeys.CUSTOM_RULES, false);
	}
	
	public int getNToAlive () {
		return prefs.getInt (SettingsKeys.N_TO_ALIVE, 3);
	}
	
	public int getMinNToLive () {
		return prefs.getInt (SettingsKeys.MIN_N_TO_LIVE, 2);
	}
	
	public int getMaxNToLive () {
		return prefs.getInt (SettingsKeys.MAX_N_TO_LIVE, 3);
	}
	
	
	
	public void set (Bundle aBundle) {
		SharedPreferences.Editor editor = prefs.edit ();
		editor.putLong (SettingsKeys.INTERVAL, 
				aBundle.getLong (SettingsKeys.INTERVAL));
		editor.putBoolean (SettingsKeys.CUSTOM_RULES,
				aBundle.getBoolean (SettingsKeys.CUSTOM_RULES));
		editor.putInt (SettingsKeys.N_TO_ALIVE,
				aBundle.getInt (SettingsKeys.N_TO_ALIVE));
		editor.putInt (SettingsKeys.MIN_N_TO_LIVE,
				aBundle.getInt (SettingsKeys.MIN_N_TO_LIVE));
		editor.putInt (SettingsKeys.MAX_N_TO_LIVE,
				aBundle.getInt (SettingsKeys.MAX_N_TO_LIVE));
		editor.commit ();
	}

	
	
	public void setInterval (long anInterval) {
		SharedPreferences.Editor editor = prefs.edit ();
		editor.putLong (SettingsKeys.INTERVAL, anInterval);
		editor.commit ();
	}
	
	public void setUseCustomRules (boolean aUseFlag) {
		SharedPreferences.Editor editor = prefs.edit ();
		editor.putBoolean (SettingsKeys.CUSTOM_RULES, aUseFlag);
		editor.commit ();
	}
	
	public void setNToAlive (int aValue) {
		SharedPreferences.Editor editor = prefs.edit ();
		editor.putInt (SettingsKeys.N_TO_ALIVE, aValue);
		editor.commit ();
	}
	
	public void setMinNToLive (int aValue) {
		SharedPreferences.Editor editor = prefs.edit ();
		editor.putInt (SettingsKeys.MIN_N_TO_LIVE, aValue);
		editor.commit ();
	}
	
	public void setMaxNToLive (int aValue) {
		SharedPreferences.Editor editor = prefs.edit ();
		editor.putInt (SettingsKeys.MAX_N_TO_LIVE, aValue);
		editor.commit ();
	}
	
	
	
	public Rules defaultRules () {
		return DEFAULT_RULES;
	}


	
	public String getSummary () {
		StringBuffer summary = new StringBuffer ();
		summary.append ("Interval: ").append (getInterval ()).append ("\n");
		summary.append ("Custom: ").append (getUseCustomRules ()).append ("\n");
		summary.append ("N to alive: ").append (getNToAlive ()).append ("\n");
		summary.append ("Min N to live: ").append (getMinNToLive ()).append ("\n");
		summary.append ("Max N to live: ").append (getMaxNToLive ()).append ("\n");
		return summary.toString ();
	}
	
}
