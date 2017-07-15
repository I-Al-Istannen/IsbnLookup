package me.ialistannen.isbnlookup.io.history;

import android.database.sqlite.SQLiteDatabase;

/**
 * Creates the database.
 */
class DatabaseCreator {

  private final String tableName;

  DatabaseCreator(String tableName) {
    this.tableName = tableName;
  }

  /**
   * @param database The {@link SQLiteDatabase} to create the tables in
   */
  void create(SQLiteDatabase database) {
    database.beginTransaction();

    try {
      //language=SQLite
      String sql = "CREATE TABLE " + tableName + " (\n"
          + "  id    INTEGER PRIMARY KEY,\n"
          + "  isbn  TEXT    NOT NULL,\n"
          + "  title TEXT    NOT NULL,\n"
          + "  date  INTEGER NOT NULL\n"
          + ") ";

      database.execSQL(sql);

      database.setTransactionSuccessful();
    } finally {
      database.endTransaction();
    }
  }
}
