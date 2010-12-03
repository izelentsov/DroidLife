package ru.izelentsov.android.lifegame.logic;

import android.content.SharedPreferences;
import android.os.Bundle;
import ru.izelentsov.android.lifegame.model.Rules;




public class GameSettings {

	private final Rules DEFAULT_RULES = new Rules (3, 2, 3);
	
	private SharedPreferences prefs = null;
	private GameController controller = null;
	private Rules customRules = null;
	
	
	public GameSettings (SharedPreferences aPrefs) {
		prefs = aPrefs;
		customRules = DEFAULT_RULES;
	}
	
	
	public void setGameController (GameController aController) {
		controller = aController;
		apply (get ());
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
	
	
//	public Bundle getSettingsBundle () {
//		Bundle settings = new Bundle ();
//    	
//    	settings.putLong (
//    			SettingsKeys.INTERVAL, controller.getRunStepIntervalMS ());
//    	settings.putBoolean (
//    			SettingsKeys.CUSTOM_RULES, 
//    			!controller.game ().rules ().equals (DEFAULT_RULES));
//    	settings.putInt (
//    			SettingsKeys.N_TO_ALIVE, customRules.neighsToAlive ());
//    	settings.putInt (
//    			SettingsKeys.MIN_N_TO_LIVE, customRules.minNeighsToLive ());
//    	settings.putInt (
//    			SettingsKeys.MAX_N_TO_LIVE, customRules.maxNeighsToLive ());
//    	
//    	return settings;
//	}

	
	
	public void apply (Bundle aSettingsBundle) {
		boolean useCustomRules = 
			aSettingsBundle.getBoolean (SettingsKeys.CUSTOM_RULES);
		
		if (useCustomRules) {
			customRules = new Rules (
					aSettingsBundle.getInt (SettingsKeys.N_TO_ALIVE),
					aSettingsBundle.getInt (SettingsKeys.MIN_N_TO_LIVE),
					aSettingsBundle.getInt (SettingsKeys.MAX_N_TO_LIVE));
			controller.game ().setRules (customRules);
		} else {
			controller.game ().setRules (DEFAULT_RULES);
		}
		controller.setRunStepInterval (
				aSettingsBundle.getLong (SettingsKeys.INTERVAL));
		
		storeSettings (aSettingsBundle);
	}

	
	
	
	private void storeSettings (Bundle aBundle) {
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

	
	
	
	public Rules defaultRules () {
		return DEFAULT_RULES;
	}


	
	public String getSummary () {
		StringBuffer summary = new StringBuffer ();
		Bundle settings = get ();

		summary.append ("Interval: ").append (
				settings.getLong (SettingsKeys.INTERVAL)).append ("\n");
		summary.append ("Custom: ").append (
				settings.getBoolean (SettingsKeys.CUSTOM_RULES)).append ("\n");
		summary.append ("N to alive: ").append (
				settings.getInt (SettingsKeys.N_TO_ALIVE)).append ("\n");
		summary.append ("Min N to live: ").append (
				settings.getInt (SettingsKeys.MIN_N_TO_LIVE)).append ("\n");
		summary.append ("Max N to live: ").append (
				settings.getInt (SettingsKeys.MAX_N_TO_LIVE)).append ("\n");
		return summary.toString ();
	}
	
}
