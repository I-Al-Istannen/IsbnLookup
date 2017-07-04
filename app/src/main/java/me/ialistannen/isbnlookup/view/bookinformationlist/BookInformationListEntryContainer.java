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

  public BookInformationListEntryContainer(Context context, ViewGroup parent) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    this.view = layoutInflater
        .inflate(R.layout.book_information_list_view_holder, parent, false);
  }

  /**
   * @param key The {@link BookDataKey} to use
   * @param value The value
   */
  public void setData(BookDataKey key, Object value) {
    TextView title = view.findViewById(R.id.book_information_list_view_holder_title);
    if (title != null) {
      title.setText(capitalize(key.name()));
    }

    TextView description = view.findViewById(R.id.book_information_list_view_holder_description);
    if (description != null) {
      description.setText(value.toString());
    }

    System.out.println("Set the data to : " + key + " " + value);
  }

  private String capitalize(String string) {
    StringBuilder stringBuilder = new StringBuilder();

    boolean upperCase = true;
    for (char c : string.toCharArray()) {
      if (upperCase) {
        stringBuilder.append(Character.toUpperCase(c));
        upperCase = false;
      } else {
        stringBuilder.append(Character.toLowerCase(c));
      }
      if (c == '_' || c == ' ') {
        upperCase = true;
      }
    }

    return stringBuilder.toString();
  }

  public View getView() {
    return view;
  }
}
