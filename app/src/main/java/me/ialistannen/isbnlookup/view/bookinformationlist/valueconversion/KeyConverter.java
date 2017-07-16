package me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion;

import android.text.Spanned;
import android.text.SpannedString;
import me.ialistannen.isbnlookuplib.book.BookDataKey;

/**
 * A Converter for a {@link BookDataKey}.
 */
public abstract class KeyConverter<T> implements ValueToSpannedConverter<T> {

  private BookDataKey bookDataKey;

  protected KeyConverter(BookDataKey bookDataKey) {
    this.bookDataKey = bookDataKey;
  }

  @Override
  public boolean canConvert(Object object) {
    return bookDataKey.equals(object);
  }

  @Override
  public Spanned convertImpl(Object object) {
    @SuppressWarnings("unchecked")
    T t = (T) object;
    return convert(t);
  }


  /**
   * @param string The String to convert
   * @return A {@link Spanned} with the same content
   */
  protected Spanned stringToSpanned(String string) {
    return new SpannedString(string);
  }
}
