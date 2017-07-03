package me.ialistannen.isbnlookup;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import me.ialistannen.isbnlookup.view.isbninputlayout.IsbnInputFilter;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_action_bar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    EditText isbnEditText = getIsbnEditText();
    if (isbnEditText != null) {
      isbnEditText.setFilters(new InputFilter[]{new IsbnInputFilter()});
      isbnEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
      });
    }
  }

  /**
   * Called when the "Lookup!" button in the main activity is clicked.
   *
   * @param view The button
   */
  public void onLookupClicked(View view) {
    EditText editText = getIsbnEditText();
    if (editText == null) {
      return;
    }

    String isbn = editText.getText().toString();
    Toast.makeText(this, "Got ISBN " + isbn, Toast.LENGTH_SHORT).show();
  }

  /**
   * @return The {@link EditText} for inputting the ISBN.
   */
  private EditText getIsbnEditText() {
    TextInputLayout textInputLayout = (TextInputLayout) findViewById(
        R.id.activity_main_isbn_input_field
    );
    EditText editText = textInputLayout.getEditText();

    if (editText == null) {
      return null;
    }
    return editText;
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
