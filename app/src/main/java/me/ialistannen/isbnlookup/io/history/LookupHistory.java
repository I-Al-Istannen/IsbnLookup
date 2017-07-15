package me.ialistannen.isbnlookup.io.history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * A class to manage the lookup history
 */
public class LookupHistory extends SQLiteOpenHelper {

  private static LookupHistory instance;

  private static final String DATABASE_NAME = "LookupDatabase";
  private static final String TABLE_NAME = "History";
  private static final int CURRENT_VERSION = 1;

  private IsbnConverter isbnConverter;

  private LookupHistory(Context context) {
    super(context, DATABASE_NAME, null, CURRENT_VERSION);

    isbnConverter = new IsbnConverter();
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    new DatabaseCreator(TABLE_NAME).create(sqLiteDatabase);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
  }

  /**
   * Adds an entry to the history.
   *
   * @param isbn The {@link Isbn} that was searched
   * @param title The Title of the book
   * @param date The {@link Date} it was searched
   */
  public void addToHistory(Isbn isbn, String title, Date date) {
    SQLiteDatabase database = getWritableDatabase();

    database.beginTransaction();

    try {
      ContentValues contentValues = new ContentValues();
      contentValues.put("date", date.getTime());
      contentValues.put("title", title);
      contentValues.put("isbn", isbn.getDigitsAsString());

      database.insert(TABLE_NAME, null, contentValues);

      database.setTransactionSuccessful();
    } finally {
      database.endTransaction();
    }
  }

  /**
   * Deletes an entry from the history.
   *
   * @param uniqueId The {@link HistoryEntry#getUniqueId()}
   */
  public void deleteFromHistory(int uniqueId) {
    SQLiteDatabase database = getWritableDatabase();

    database.beginTransaction();

    try {
      String sqlWhere = "id == ?";
      String[] args = {String.valueOf(uniqueId)};

      database.delete(TABLE_NAME, sqlWhere, args);

      database.setTransactionSuccessful();
    } finally {
      database.endTransaction();
    }
  }

  /**
   * Clears the whole database.
   */
  public void clear() {
    SQLiteDatabase database = getWritableDatabase();

    database.beginTransaction();

    try {
      database.delete(TABLE_NAME, null, null);

      database.setTransactionSuccessful();
    } finally {
      database.endTransaction();
    }

  }

  /**
   * @return The full history of searches.
   */
  public List<HistoryEntry> getHistory() {
    final List<HistoryEntry> data = new ArrayList<>();

    SQLiteDatabase database = getReadableDatabase();

    String sql = "SELECT * FROM " + TABLE_NAME;

    try (Cursor cursor = database.rawQuery(sql, null)) {

      if (cursor.moveToFirst()) {
        do {
          int id = cursor.getInt(0);
          String isbnString = cursor.getString(1);
          Optional<Isbn> isbnOptional = isbnConverter.fromString(isbnString);

          String title = cursor.getString(2);

          Date date = new Date(cursor.getLong(3));
          if (!isbnOptional.isPresent()) {
            continue;
          }

          Isbn isbn = isbnOptional.get();

          data.add(new HistoryEntry(isbn, title, date, id));

        }
        while (cursor.moveToNext());
      }

      return data;
    }
  }

  /**
   * @param context The {@link Context} to get it for
   * @return The {@link LookupHistory}
   */
  public static synchronized LookupHistory getInstance(Context context) {
    if (instance == null) {
      instance = new LookupHistory(context.getApplicationContext());
    }
    return instance;
  }
}
