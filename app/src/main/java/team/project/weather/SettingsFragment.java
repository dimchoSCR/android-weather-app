package team.project.weather;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by dimcho on 3/16/16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Load Preferences from xml
        addPreferencesFromResource(R.xml.preferences);
    }
}
