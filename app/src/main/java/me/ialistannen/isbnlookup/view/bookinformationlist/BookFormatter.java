package me.ialistannen.isbnlookup.view.bookinformationlist;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import me.ialistannen.isbnlookup.R;
import me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion.KeyConverter;
import me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion.TypeConverter;
import me.ialistannen.isbnlookup.view.bookinformationlist.valueconversion.ValueToSpannedConverter;
import me.ialistannen.isbnlookuplib.book.BookDataKey;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Pair;
import me.ialistannen.isbnlookuplib.util.Price;

/**
 * Formats the values of {@link BookDataKey}s.
 */
class BookFormatter {

  private final Context context;
  private List<ValueToSpannedConverter<?>> converters;

  private Set<BookDataKey> blacklistedKeys = new HashSet<>(
      Collections.<BookDataKey>singletonList(StandardBookDataKeys.ISBN)
  );

  BookFormatter(final Context context) {
    this.context = context;
    converters = new ArrayList<>();

    addConverter(new KeyConverter<List<Pair<String, String>>>(StandardBookDataKeys.AUTHORS) {

      @Override
      public Spanned convert(List<Pair<String, String>> pairs) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Pair<String, String> pair : pairs) {
          String formattedValue = context.getString(
              R.string.book_formatter_authors_format, pair.getKey(), pair.getValue()
          );

          String linkTarget = context.getString(
              R.string.book_formatter_wikipedia_search_url, pair.getKey()
          );

          stringBuilder.append(getMaskedLinkHtml(linkTarget, formattedValue)).append("<br>");
        }

        String result = stringBuilder.toString();
        result = result.substring(0, result.length() - 1);
        return getHtmlSpannedFromString(result);
      }

      private String getMaskedLinkHtml(String target, String text) {
        String format = "<a href=\"%s\">%s</a>";

        return String.format(Locale.ROOT, format, target, text);
      }

      private Spanned getHtmlSpannedFromString(String html) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
          return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }

        @SuppressWarnings("deprecation")
        Spanned fromHtml = Html.fromHtml(html);
        return fromHtml;
      }
    });

    addConverter(new TypeConverter<Isbn>(Isbn.class) {
      @Override
      public Spanned convert(Isbn isbn) {
        return stringToSpanned(isbn.getDigitsAsString());
      }
    });

    addConverter(new TypeConverter<Price>(Price.class) {
      private NumberFormat numberFormat = NumberFormat.getNumberInstance();

      @Override
      public Spanned convert(Price price) {
        String formatted =
            numberFormat.format(price.getPrice()) + " " + price.getCurrencyIdentifier();
        return stringToSpanned(formatted);
      }
    });

    addConverter(new KeyConverter<Double>(StandardBookDataKeys.RATING) {
      private NumberFormat formatInstance = NumberFormat.getPercentInstance();

      @Override
      public Spanned convert(Double ratingPercentage) {
        return stringToSpanned(formatInstance.format(ratingPercentage));
      }
    });
  }

  /**
   * @param converter The {@link ValueToSpannedConverter} to add to this {@link BookFormatter}.
   */
  private void addConverter(ValueToSpannedConverter<?> converter) {
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
    String localizedName = getStringByName(key.name());
    if (localizedName != null) {
      return localizedName;
    }
    return capitalize(key.name());
  }

  private String getStringByName(String key) {
    int resourceId = context.getResources()
        .getIdentifier(key, "string", context.getPackageName());

    if (resourceId == 0) {
      return null;
    }

    return context.getString(resourceId);
  }

  /**
   * @param key The {@link BookDataKey}
   * @param value The value to format
   * @return The formatted value, if possible
   */
  Spanned formatValue(BookDataKey key, Object value) {
    for (ValueToSpannedConverter<?> converter : converters) {
      if (converter.canConvert(key)) {
        return converter.convertImpl(value);
      }
    }

    // Give keys precedence, so we iter over the values after that
    for (ValueToSpannedConverter<?> converter : converters) {
      if (converter.canConvert(value)) {
        return converter.convertImpl(value);
      }
    }

    // fallback
    return new SpannedString(value.toString());
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
