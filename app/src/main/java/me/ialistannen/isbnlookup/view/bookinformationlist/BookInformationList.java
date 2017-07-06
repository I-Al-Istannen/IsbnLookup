package me.ialistannen.isbnlookup.view.bookinformationlist;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.BookDataKey;

/**
 * A {@link android.support.v7.widget.RecyclerView} to display Book information
 */
public class BookInformationList extends RecyclerView {

  private BookFormatter bookFormatter;

  public BookInformationList(Context context) {
    super(context);
  }

  public BookInformationList(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public BookInformationList(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setBook(Book book) {
    setAdapter(new IsbnViewAdapter(book, getContext(), bookFormatter));
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    bookFormatter = new BookFormatter(getContext());

    setLayoutManager(new LinearLayoutManager(getContext()));

    addItemDecoration(new PaddingItemDecoration(10));

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        getContext(),
        DividerItemDecoration.VERTICAL
    );
    dividerItemDecoration
        .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_divider));
    addItemDecoration(dividerItemDecoration);
  }

  private static class PaddingItemDecoration extends ItemDecoration {

    private int spacing;

    private PaddingItemDecoration(int spacing) {
      this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
      super.getItemOffsets(outRect, view, parent, state);
      outRect.set(spacing, outRect.top, spacing, spacing);

      if (parent.getChildAdapterPosition(view) > 0) {
        outRect.top = spacing;
      }
    }
  }

  private static class IsbnViewAdapter extends Adapter {

    private List<Entry<BookDataKey, Object>> values;
    private final Context context;
    private final BookFormatter formatter;

    private IsbnViewAdapter(Book book, Context context, BookFormatter formatter) {
      this.formatter = formatter;
      this.context = context;
      values = new ArrayList<>(book.getAllData().entrySet());

      removeBlacklistedKeys();
      sortEntries();
    }

    private void sortEntries() {
      Collections.sort(values, new Comparator<Entry<BookDataKey, Object>>() {
        @Override
        public int compare(Entry<BookDataKey, Object> o1, Entry<BookDataKey, Object> o2) {
          return Integer.compare(o1.getKey().displayPriority(), o2.getKey().displayPriority());
        }
      });
    }

    private void removeBlacklistedKeys() {
      ListIterator<Entry<BookDataKey, Object>> iterator = values.listIterator();
      while (iterator.hasNext()) {
        Entry<BookDataKey, Object> entry = iterator.next();

        if (!formatter.shouldDisplayKey(entry.getKey())) {
          iterator.remove();
        }
      }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ListViewHolder(new BookInformationListEntryContainer(context, parent, formatter));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      ListViewHolder listViewHolder = (ListViewHolder) holder;

      Entry<BookDataKey, Object> entry = values.get(position);
      listViewHolder.getContainer().setData(entry.getKey(), entry.getValue());
    }

    @Override
    public int getItemCount() {
      return values.size();
    }

    private static class ListViewHolder extends ViewHolder {

      private final BookInformationListEntryContainer container;

      ListViewHolder(BookInformationListEntryContainer container) {
        super(container.getView());
        this.container = container;
      }

      BookInformationListEntryContainer getContainer() {
        return container;
      }
    }
  }
}
