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
import java.util.Map.Entry;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.BookDataKey;

/**
 * A {@link android.support.v7.widget.RecyclerView} to display Book information
 */
public class BookInformationList extends RecyclerView {

  private Book book;

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
    this.book = book;
    setAdapter(new IsbnViewAdapter(book, getContext()));
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    book = new Book();
    book.setData(new NoDataBookKey(getContext()), new NoDataBookKey(getContext()).name());
    book.setData(new DummyBookKey("Hey"), "Hey");
    book.setData(new DummyBookKey("Hey 2"), "Hey 2");

    setLayoutManager(new LinearLayoutManager(getContext()));

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        getContext(),
        DividerItemDecoration.VERTICAL
    );
    dividerItemDecoration
        .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_divider));
    addItemDecoration(dividerItemDecoration);

    addItemDecoration(new PaddingItemDecoration(10));
    setAdapter(new IsbnViewAdapter(book, getContext()));
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

    private IsbnViewAdapter(Book book, Context context) {
      values = new ArrayList<>(book.getAllData().entrySet());

      sortEntries();

      this.context = context;
      System.out.println("Created adapter with " + book.getAllData() + " keys!");
    }

    private void sortEntries() {
      Collections.sort(values, new Comparator<Entry<BookDataKey, Object>>() {
        @Override
        public int compare(Entry<BookDataKey, Object> o1, Entry<BookDataKey, Object> o2) {
          return o1.getKey().name().compareTo(o2.getKey().name());
        }
      });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ListViewHolder(new BookInformationListEntryContainer(context, parent));
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

  private static class NoDataBookKey implements BookDataKey {

    private Context context;

    private NoDataBookKey(Context context) {
      this.context = context;
    }

    @Override
    public String name() {
      return context.getString(R.string.book_information_no_data);
    }
  }

  private static class DummyBookKey implements BookDataKey {

    private final String name;

    private DummyBookKey(String name) {
      this.name = name;
    }

    @Override
    public String name() {
      return name;
    }
  }
}
