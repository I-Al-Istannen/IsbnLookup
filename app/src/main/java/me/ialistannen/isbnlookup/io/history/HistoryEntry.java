package me.ialistannen.isbnlookup.io.history;

import java.util.Date;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

/**
 * An Entry in the search history.
 */
public class HistoryEntry {

  private Isbn isbn;
  private String title;
  private Date date;
  private int uniqueId;

  HistoryEntry(Isbn isbn, String title, Date date, int uniqueId) {
    this.isbn = isbn;
    this.title = title;
    this.date = date;
    this.uniqueId = uniqueId;
  }

  public Isbn getIsbn() {
    return isbn;
  }

  public Date getDate() {
    return date;
  }

  public String getTitle() {
    return title;
  }

  /**
   * @return The unique ID of this entry
   */
  public int getUniqueId() {
    return uniqueId;
  }

  @Override
  public String toString() {
    return "HistoryEntry{"
        + "isbn=" + isbn
        + ", date=" + date
        + ", uniqueId=" + uniqueId
        + '}';
  }
}
