package me.ialistannen.isbnlookup.view.historylist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookup.io.history.HistoryEntry;

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
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    setAdapter(new Adapter(Collections.<HistoryEntry>emptyList()));
  }

  /**
   * @param data The data to display in the list
   */
  public void setData(List<HistoryEntry> data) {
    Adapter adapter = (Adapter) getAdapter();
    adapter.setSearchTimes(data);

    getAdapter().notifyDataSetChanged();

    handlePlaceholderVisibility();
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

  private static class Adapter extends RecyclerView.Adapter {

    private List<HistoryEntry> searchTimes;

    Adapter(List<HistoryEntry> searchTimes) {
      this.searchTimes = new ArrayList<>(searchTimes);
    }

    void setSearchTimes(List<HistoryEntry> searchTimes) {
      this.searchTimes = new ArrayList<>(searchTimes);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new HistoryListEntry(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      HistoryListEntry listEntry = (HistoryListEntry) holder;

      HistoryEntry data = searchTimes.get(position);
      listEntry.setData(data.getIsbn(), data.getDate());
    }

    @Override
    public int getItemCount() {
      return searchTimes.size();
    }
  }
}
