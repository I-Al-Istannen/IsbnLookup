package me.ialistannen.isbnlookup.view.historylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookup.io.history.HistoryEntry;
import me.ialistannen.isbnlookup.io.history.LookupHistory;
import me.ialistannen.isbnlookup.view.historylist.HistoryListView.Adapter;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

/**
 * An entry in a HistoryList.
 */
class HistoryListEntry extends ViewHolder {

  private final HistoryListView historyListView;
  private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
  private int uniqueId;

  HistoryListEntry(Context context, ViewGroup parent, HistoryListView historyListView) {
    super(createView(context, parent));
    this.historyListView = historyListView;

    attachContextMenu(context);
  }

  private static View createView(Context context, ViewGroup parent) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);

    return layoutInflater.inflate(R.layout.history_list_view_holder, parent, false);
  }

  private void attachContextMenu(final Context context) {
    itemView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

      @Override
      public void onCreateContextMenu(ContextMenu contextMenu, final View view,
          ContextMenuInfo contextMenuInfo) {

        contextMenu.setHeaderTitle(context.getString(R.string.history_list_element_popup_title));

        contextMenu.add(context.getString(R.string.history_list_element_popup_delete))
            .setOnMenuItemClickListener(new OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem menuItem) {
                Adapter adapter = (Adapter) historyListView.getAdapter();
                adapter.remove(getAdapterPosition());
                LookupHistory.getInstance(context).deleteFromHistory(uniqueId);
                return true;
              }
            });
      }
    });
  }

  /**
   * Sets the data displayed in this entry.
   *
   * @param isbn The {@link Isbn} to use
   * @param date The date it was searched
   * @param uniqueId The {@link HistoryEntry#getUniqueId()}
   */
  void setData(Isbn isbn, Date date, int uniqueId) {
    this.uniqueId = uniqueId;

    TextView isbnTextView = itemView.findViewById(R.id.history_list_view_holder_isbn);
    isbnTextView.setText(isbn.getDigitsAsString());

    TextView dateTextView = itemView.findViewById(R.id.history_list_view_holder_date);
    dateTextView.setText(dateFormat.format(date));
  }
}
