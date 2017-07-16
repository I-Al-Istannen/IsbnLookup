package me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion;

import android.text.Spanned;
import android.text.SpannedString;

/**
 * A converter for a given class.
 */
public abstract class TypeConverter<T> implements ValueToSpannedConverter<T> {

  private Class<T> converterClass;

  protected TypeConverter(Class<T> converterClass) {
    this.converterClass = converterClass;
  }

  @Override
  public boolean canConvert(Object object) {
    return converterClass.isAssignableFrom(object.getClass());
  }

  @Override
  public Spanned convertImpl(Object object) {
    return convert(converterClass.cast(object));
  }

  /**
   * @param string The String to convert
   * @return A {@link Spanned} with the same content
   */
  protected Spanned stringToSpanned(String string) {
    return new SpannedString(string);
  }
}
