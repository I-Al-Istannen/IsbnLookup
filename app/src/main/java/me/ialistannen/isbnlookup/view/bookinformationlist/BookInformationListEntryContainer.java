package me.ialistannen.isbnlookup.view.bookinformationlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookuplib.book.BookDataKey;

/**
 * Wraps the Layout for the entries in the BookInformation list
 */
public class BookInformationListEntryContainer {

  private View view;
  private BookFormatter formatter;

  BookInformationListEntryContainer(Context context, ViewGroup parent, BookFormatter formatter) {
    this.formatter = formatter;

    LayoutInflater layoutInflater = LayoutInflater.from(context);

    this.view = layoutInflater
        .inflate(R.layout.book_information_list_view_holder, parent, false);
  }

  /**
   * @param key The {@link BookDataKey} to use
   * @param value The value
   */
  void setData(BookDataKey key, Object value) {
    TextView title = view.findViewById(R.id.book_information_list_view_holder_title);
    if (title != null) {
      title.setText(formatter.formatKey(key));
    }

    TextView description = view.findViewById(R.id.book_information_list_view_holder_description);
    if (description != null) {
      description.setText(formatter.formatValue(key, value));
    }
  }

  public View getView() {
    return view;
  }
}
