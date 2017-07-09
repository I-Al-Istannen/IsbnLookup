package me.ialistannen.isbnlookup.io.history;

import java.util.Date;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

/**
 * An Entry in the search history.
 */
public class HistoryEntry {

  private Isbn isbn;
  private Date date;
  private int uniqueId;

  HistoryEntry(Isbn isbn, Date date, int uniqueId) {
    this.isbn = isbn;
    this.date = date;
    this.uniqueId = uniqueId;
  }

  public Isbn getIsbn() {
    return isbn;
  }

  public Date getDate() {
    return date;
  }

  /**
   * @return The unique ID of this entry
   */
  public int getUniqueId() {
    return uniqueId;
  }
}
