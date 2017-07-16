package me.ialistannen.isbnlookup.view.historylist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookup.io.history.HistoryEntry;
import me.ialistannen.isbnlookup.view.util.PaddingItemDecoration;

/**
 * A List recylcer view showing your search history
 */
public class HistoryListView extends RecyclerView {

  public HistoryListView(Context context) {
    super(context);
  }

  public HistoryListView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public HistoryListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    setLayoutManager(new LinearLayoutManager(getContext()));

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        getContext(), DividerItemDecoration.VERTICAL
    );
    dividerItemDecoration.setDrawable(
        ContextCompat.getDrawable(getContext(), R.drawable.list_divider)
    );
    addItemDecoration(dividerItemDecoration);
    addItemDecoration(new PaddingItemDecoration(20));
    setAdapter(new Adapter(Collections.<HistoryEntry>emptyList()));
  }

  /**
   * @param data The data to display in the list
   */
  public void setData(List<HistoryEntry> data) {
    Adapter adapter = (Adapter) getAdapter();

    sortData(data);

    adapter.setSearchTimes(data);

    getAdapter().notifyDataSetChanged();

    handlePlaceholderVisibility();
  }

  private void sortData(List<HistoryEntry> data) {
    Comparator<HistoryEntry> comparator = new Comparator<HistoryEntry>() {
      @Override
      public int compare(HistoryEntry o1, HistoryEntry o2) {
        return o1.getDate().compareTo(o2.getDate());
      }
    };
    comparator = Collections.reverseOrder(comparator);

    Collections.sort(data, comparator);
  }

  private void handlePlaceholderVisibility() {
    if (!(getParent() instanceof View)) {
      return;
    }
    View parent = (View) getParent();
    TextView emptyView = parent.findViewById(R.id.activity_history_empty_view);

    if (getAdapter().getItemCount() == 0) {
      setVisibility(INVISIBLE);
      emptyView.setVisibility(VISIBLE);
    } else {
      setVisibility(VISIBLE);
      emptyView.setVisibility(INVISIBLE);
    }
  }

  static class Adapter extends RecyclerView.Adapter {

    private List<HistoryEntry> searchTimes;
    private HistoryListView historyListView;

    Adapter(List<HistoryEntry> searchTimes) {
      this.searchTimes = new ArrayList<>(searchTimes);
    }

    /**
     * Sets the data to display.
     *
     * @param searchTimes The {@link HistoryEntry}s to display
     */
    void setSearchTimes(List<HistoryEntry> searchTimes) {
      this.searchTimes = new ArrayList<>(searchTimes);
    }

    /**
     * Removes the element at the given index.
     *
     * @param index The index of the element
     */
    void remove(int index) {
      searchTimes.remove(index);
      notifyDataSetChanged();

      historyListView.handlePlaceholderVisibility();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new HistoryListEntry(parent.getContext(), parent, historyListView);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
      super.onAttachedToRecyclerView(recyclerView);
      this.historyListView = (HistoryListView) recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
      super.onDetachedFromRecyclerView(recyclerView);
      this.historyListView = null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      HistoryListEntry listEntry = (HistoryListEntry) holder;

      HistoryEntry data = searchTimes.get(position);
      listEntry.setData(data);
    }

    @Override
    public int getItemCount() {
      return searchTimes.size();
    }
  }
}
