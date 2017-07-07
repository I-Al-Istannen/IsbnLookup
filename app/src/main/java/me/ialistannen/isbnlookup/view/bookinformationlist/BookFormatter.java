package me.ialistannen.isbnlookup.view.bookinformationlist;

import android.content.Context;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion.KeyConverter;
import me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion.TypeConverter;
import me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion.ValueToStringConverter;
import me.ialistannen.isbnlookuplib.book.BookDataKey;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Pair;
import me.ialistannen.isbnlookuplib.util.Price;

/**
 * Formats the values of {@link BookDataKey}s.
 */
class BookFormatter {

  private List<ValueToStringConverter<?>> converters;


  private Set<BookDataKey> blacklistedKeys = new HashSet<>(
      Collections.<BookDataKey>singletonList(StandardBookDataKeys.ISBN)
  );

  BookFormatter(final Context context) {
    converters = new ArrayList<>();

    addConverter(new KeyConverter<List<Pair<String, String>>>(StandardBookDataKeys.AUTHORS) {
      @Override
      public String convert(List<Pair<String, String>> pairs) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Pair<String, String> pair : pairs) {
          String formattedValue = context.getString(
              R.string.book_formatter_authors_format, pair.getKey(), pair.getValue()
          );

          stringBuilder.append(formattedValue).append("\n");
        }

        String result = stringBuilder.toString();
        return result.substring(0, result.length() - 1);
      }
    });

    addConverter(new TypeConverter<Isbn>(Isbn.class) {
      @Override
      public String convert(Isbn isbn) {
        return isbn.getDigitsAsString();
      }
    });

    addConverter(new TypeConverter<Price>(Price.class) {
      private NumberFormat numberFormat = NumberFormat.getNumberInstance();

      @Override
      public String convert(Price price) {
        return numberFormat.format(price.getPrice()) + " " + price.getCurrencyIdentifier();
      }
    });

    addConverter(new KeyConverter<Double>(StandardBookDataKeys.RATING) {
      private NumberFormat formatInstance = NumberFormat.getPercentInstance();

      @Override
      public String convert(Double ratingPercentage) {
        return formatInstance.format(ratingPercentage);
      }
    });
  }

  /**
   * @param converter The {@link ValueToStringConverter} to add to this {@link BookFormatter}.
   */
  private void addConverter(ValueToStringConverter<?> converter) {
    converters.add(converter);
  }

  /**
   * @param key The {@link BookDataKey} to check
   * @return True if the key should be added and displayed
   */
  boolean shouldDisplayKey(BookDataKey key) {
    return !blacklistedKeys.contains(key);
  }

  /**
   * @param key The {@link BookDataKey} to format
   * @return The formatted name
   */
  String formatKey(BookDataKey key) {
    return capitalize(key.name());
  }

  /**
   * @param key The {@link BookDataKey}
   * @param value The value to format
   * @return The formatted value, if possible
   */
  String formatValue(BookDataKey key, Object value) {
    for (ValueToStringConverter<?> converter : converters) {
      if (converter.canConvert(key)) {
        return converter.convertImpl(value);
      }
    }

    // Give keys precedence, so we iter over the values after that
    for (ValueToStringConverter<?> converter : converters) {
      if (converter.canConvert(value)) {
        return converter.convertImpl(value);
      }
    }

    // fallback
    return value.toString();
  }

  private String capitalize(String string) {
    StringBuilder stringBuilder = new StringBuilder();

    boolean upperCase = true;
    for (char c : string.toCharArray()) {
      if (c == '_' || c == ' ') {
        upperCase = true;
        stringBuilder.append(' ');
        continue;
      }

      if (upperCase) {
        stringBuilder.append(Character.toUpperCase(c));
        upperCase = false;
      } else {
        stringBuilder.append(Character.toLowerCase(c));
      }
    }

    return stringBuilder.toString();
  }
}
