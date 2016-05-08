package team.project.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment
                    implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TEMP_UNIT_KEY = "pref_key_temp_unit";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Load Preferences from xml
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Set summary to be the user-description for the selected value
        String unitPref = sharedPreferences.getString(key, "");

        Preference connectionPref = findPreference(TEMP_UNIT_KEY);
        connectionPref.setSummary(unitPref);
    }
}
