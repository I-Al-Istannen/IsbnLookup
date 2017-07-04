package me.ialistannen.isbnlookup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import me.ialistannen.isbnlookup.util.ParcelableIsbn;
import me.ialistannen.isbnlookup.view.isbninputlayout.IsbnInputTextLayout;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Optional;

public class MainActivity extends AppCompatActivity {

  static final String ISBN_KEY = "me.ialistannen.isbnlookup.MainActivity.ISBN";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_action_bar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  /**
   * Called when the "Lookup!" button in the main activity is clicked.
   *
   * @param view The button
   */
  public void onLookupClicked(View view) {
    IsbnInputTextLayout isbnInputTextLayout = (IsbnInputTextLayout) findViewById(
        R.id.activity_main_isbn_input_field);

    Optional<Isbn> isbnOptional = isbnInputTextLayout.getIsbn();
    if (!isbnOptional.isPresent()) {
      return;
    }
    Isbn isbn = isbnOptional.get();

    Toast.makeText(this, "Got ISBN " + isbn, Toast.LENGTH_SHORT).show();

    Intent showInformation = new Intent(this, DisplayBookInformation.class);
    showInformation.putExtra(ISBN_KEY, ParcelableIsbn.of(isbn));
    startActivity(showInformation);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_action_bar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_bar_settings:
        Toast.makeText(this, "Clicked me!", Toast.LENGTH_SHORT).show();
        return true;
    }
    return false;
  }
}
