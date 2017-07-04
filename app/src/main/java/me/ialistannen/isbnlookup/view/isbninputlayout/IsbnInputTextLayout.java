package me.ialistannen.isbnlookup.view.isbninputlayout;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.util.AttributeSet;

/**
 * A {@link TextInputLayout} that allows inputting ISBNs.
 */
public class IsbnInputTextLayout extends TextInputLayout {

  public IsbnInputTextLayout(Context context) {
    super(context);
  }

  public IsbnInputTextLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public IsbnInputTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    if (getEditText() != null) {
      getEditText().setFilters(new InputFilter[]{new IsbnInputFilter()});
      getEditText().addTextChangedListener(new IsbnLayoutValidationWatcher(this, getContext()));
    }

  }

  @Override
  public int getBaseline() {
    return getEditText() == null ? -1 : getEditText().getBaseline() + getEditText().getPaddingTop();
  }
}
