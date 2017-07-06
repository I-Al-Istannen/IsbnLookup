package me.ialistannen.isbnlookup.view.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import me.ialistannen.isbnlookup.R;

/**
 * A {@link PreferenceFragment}.
 */
public class SettingsFragment extends PreferenceFragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preferences);
  }
}
