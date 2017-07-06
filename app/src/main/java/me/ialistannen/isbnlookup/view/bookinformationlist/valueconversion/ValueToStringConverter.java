package me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion;

import me.ialistannen.isbnlookuplib.book.Book;

/**
 * Converts a {@link Book} value to a String.
 */
public interface ValueToStringConverter<T> {

  /**
   * @param object The value to convert
   * @return True if this object can convert it
   */
  boolean canConvert(Object object);

  /**
   * @param t The value to convert to a String
   * @return The converted value
   */
  String convert(T t);

  /**
   * @param object The object to convert
   * @return The converted value
   */
  String convertImpl(Object object);
}
