package me.ialistannen.isbnlookup.view.historylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

/**
 * An entry in a HistoryList.
 */
public class HistoryListEntry extends ViewHolder {

  private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

  public HistoryListEntry(Context context, ViewGroup parent) {
    super(createView(context, parent));
  }

  private static View createView(Context context, ViewGroup parent) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    return layoutInflater
        .inflate(R.layout.history_list_view_holder, parent, false);
  }

  /**
   * Sets the data displayed in this entry.
   *
   * @param isbn The {@link Isbn} to use
   * @param date The date it was searched
   */
  void setData(Isbn isbn, Date date) {
    TextView isbnTextView = itemView.findViewById(R.id.history_list_view_holder_isbn);
    isbnTextView.setText(isbn.getDigitsAsString());

    TextView dateTextView = itemView.findViewById(R.id.history_list_view_holder_date);
    dateTextView.setText(dateFormat.format(date));
  }
}
