package me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion;

/**
 * A converter for a given class.
 */
public abstract class TypeConverter<T> implements ValueToStringConverter<T> {

  private Class<T> converterClass;

  protected TypeConverter(Class<T> converterClass) {
    this.converterClass = converterClass;
  }

  @Override
  public boolean canConvert(Object object) {
    return converterClass.isAssignableFrom(object.getClass());
  }

  @Override
  public String convertImpl(Object object) {
    return convert(converterClass.cast(object));
  }
}
