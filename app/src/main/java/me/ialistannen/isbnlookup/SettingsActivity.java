package me.ialistannen.isbnlookup;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.ialistannen.isbnlookup.view.settings.SettingsFragment;

/**
 * An Activity to display settings
 */
public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    String string = PreferenceManager.getDefaultSharedPreferences(this)
        .getString("preferences_group_misc_locale", null);
    Toast.makeText(this, "Test: '" + string + "'", Toast.LENGTH_SHORT).show();

    Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new SettingsFragment())
        .commit();
  }
}
