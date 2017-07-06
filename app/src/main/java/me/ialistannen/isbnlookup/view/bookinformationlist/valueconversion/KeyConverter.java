package me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion;

import me.ialistannen.isbnlookuplib.book.BookDataKey;

/**
 * A Converter for a {@link BookDataKey}.
 */
public abstract class KeyConverter<T> implements ValueToStringConverter<T> {

  private BookDataKey bookDataKey;

  protected KeyConverter(BookDataKey bookDataKey) {
    this.bookDataKey = bookDataKey;
  }

  @Override
  public boolean canConvert(Object object) {
    return bookDataKey.equals(object);
  }

  @Override
  public String convertImpl(Object object) {
    @SuppressWarnings("unchecked")
    T t = (T) object;
    return convert(t);
  }
}
