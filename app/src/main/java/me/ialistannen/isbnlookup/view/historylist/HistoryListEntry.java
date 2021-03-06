package me.ialistannen.isbnlookup.view.historylist;

import android.content.Context;
import android.content.Intent;
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
import me.ialistannen.isbnlookup.DisplayBookInformation;
import me.ialistannen.isbnlookup.MainActivity;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookup.io.history.HistoryEntry;
import me.ialistannen.isbnlookup.io.history.LookupHistory;
import me.ialistannen.isbnlookup.util.ParcelableIsbn;
import me.ialistannen.isbnlookup.view.historylist.HistoryListView.Adapter;

/**
 * An entry in a HistoryList.
 */
class HistoryListEntry extends ViewHolder {

  private final HistoryListView historyListView;
  private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
  private HistoryEntry entry;

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
                LookupHistory.getInstance(context).deleteFromHistory(entry.getUniqueId());
                return true;
              }
            });

        contextMenu.add(context.getString(R.string.history_list_element_popup_lookup))
            .setOnMenuItemClickListener(new OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(context, DisplayBookInformation.class);

                ParcelableIsbn parcelableIsbn = ParcelableIsbn.of(entry.getIsbn());
                intent.putExtra(MainActivity.ISBN_KEY, parcelableIsbn);

                context.startActivity(intent);
                return true;
              }
            });
      }
    });
  }

  /**
   * Sets the data displayed in this entry.
   *
   * @param historyEntry The {@link HistoryEntry}
   */
  void setData(HistoryEntry historyEntry) {
    this.entry = historyEntry;

    TextView titleTextView = itemView.findViewById(R.id.history_list_view_holder_title);
    titleTextView.setText(historyEntry.getTitle());

    TextView isbnTextView = itemView.findViewById(R.id.history_list_view_holder_isbn);
    isbnTextView.setText(historyEntry.getIsbn().getDigitsAsString());

    TextView dateTextView = itemView.findViewById(R.id.history_list_view_holder_date);
    dateTextView.setText(dateFormat.format(historyEntry.getDate()));
  }
}
