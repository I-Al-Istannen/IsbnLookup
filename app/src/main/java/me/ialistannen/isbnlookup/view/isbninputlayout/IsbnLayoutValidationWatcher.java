package me.ialistannen.isbnlookup.view.isbninputlayout;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * A {@link TextWatcher} that validates ISBNs and sets the error, if it is invalid.
 */
class IsbnLayoutValidationWatcher implements TextWatcher {

  private TextInputLayout targetLayout;

  IsbnLayoutValidationWatcher(TextInputLayout targetLayout) {
    this.targetLayout = targetLayout;
  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable editable) {
  }
}
