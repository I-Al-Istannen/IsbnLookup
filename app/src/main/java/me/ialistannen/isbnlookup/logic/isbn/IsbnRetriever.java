package me.ialistannen.isbnlookup.logic.isbn;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;
import java.util.Locale;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.lookup.IsbnLookupProvider;
import me.ialistannen.isbnlookuplib.lookup.providers.amazon.AmazonIsbnLookupProvider;
import me.ialistannen.isbnlookuplib.util.Consumer;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * Retrieves information about an ISBN.
 */
public class IsbnRetriever extends AsyncTask<Isbn, Void, Optional<Book>> {

  private IsbnLookupProvider isbnLookupProvider;

  private Consumer<Optional<Book>> callback;

  /**
   * @param callback The result callback
   */
  public IsbnRetriever(Consumer<Optional<Book>> callback, Context context) {
    this.callback = callback;

    String localeTag = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("preferences_group_fetcher_locale", "de");
    Locale locale = new Locale(localeTag);

    isbnLookupProvider = getIsbnLookupProvider(context, locale, new IsbnConverter());

    Toast.makeText(
        context,
        "Using lookup provider " + isbnLookupProvider.getClass().getSimpleName(),
        Toast.LENGTH_SHORT
    ).show();

    Toast.makeText(context, "Using locale " + localeTag, Toast.LENGTH_SHORT).show();
  }

  private IsbnLookupProvider getIsbnLookupProvider(Context context, Locale locale,
      IsbnConverter converter) {
    String fetcherName = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("preferences_group_fetcher_chosen", null);

    Toast.makeText(context, "Fetcher name '" + fetcherName + "'", Toast.LENGTH_SHORT).show();

    switch (fetcherName) {
      case "amazon":
        return new AmazonIsbnLookupProvider(locale, converter);
      default:
        return new AmazonIsbnLookupProvider(locale, converter);
    }
  }

  @Override
  protected Optional<Book> doInBackground(Isbn... isbns) {
    if (isbns.length < 1) {
      return Optional.empty();
    }

    return isbnLookupProvider.lookup(isbns[0]);
  }

  @Override
  protected void onPostExecute(Optional<Book> book) {
    callback.accept(book);
  }
}
