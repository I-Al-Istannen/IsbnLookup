package me.ialistannen.isbnlookup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import me.ialistannen.isbnlookup.logic.isbn.IsbnRetriever;
import me.ialistannen.isbnlookup.view.bookinformationlist.BookInformationList;
import me.ialistannen.isbnlookuplib.book.AbstractBookDataKey;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Consumer;
import me.ialistannen.isbnlookuplib.util.Optional;

public class DisplayBookInformation extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_book_information);

    Toolbar toolbar = (Toolbar) findViewById(R.id.activity_book_information_action_bar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    Isbn isbn = getIntent().getExtras().getParcelable(MainActivity.ISBN_KEY);

    //0-306-40615-2

    final BookInformationList bookInformationList = (BookInformationList) findViewById(
        R.id.activity_display_book_information_list
    );
    bookInformationList.setBook(getPlaceholderBook(isbn));

    new IsbnRetriever(new Consumer<Optional<Book>>() {
      @Override
      public void accept(Optional<Book> bookOptional) {
        if (bookOptional.isPresent()) {
          bookInformationList.setBook(bookOptional.get());
        } else {
          bookInformationList.setBook(getNoDataBook());
        }
      }
    }, this).execute(isbn);
  }

  private Book getPlaceholderBook(Isbn isbn) {
    Book book = new Book();

    final String fetchingString = getString(
        R.string.book_information_fetching, isbn.getDigitsAsString()
    );
    book.setData(new AbstractBookDataKey() {
      @Override
      public String name() {
        return fetchingString;
      }
    }, fetchingString);

    return book;
  }

  private Book getNoDataBook() {
    Book book = new Book();
    book.setData(new NoDataBookKey(this), new NoDataBookKey(this).name());
    return book;
  }

  private static class NoDataBookKey extends AbstractBookDataKey {

    private Context context;

    private NoDataBookKey(Context context) {
      this.context = context;
    }

    @Override
    public String name() {
      return context.getString(R.string.book_information_no_data);
    }
  }

}
