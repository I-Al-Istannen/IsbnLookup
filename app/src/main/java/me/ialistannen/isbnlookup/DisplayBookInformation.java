package me.ialistannen.isbnlookup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import me.ialistannen.isbnlookup.view.bookinformationlist.BookInformationList;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

public class DisplayBookInformation extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_book_information2);

    Toolbar toolbar = (Toolbar) findViewById(R.id.activity_book_information_action_bar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    Isbn isbn = getIntent().getExtras().getParcelable(MainActivity.ISBN_KEY);
    System.out.println("Isbn: " + isbn);

    //0-306-40615-2

    BookInformationList bookInformationList = (BookInformationList) findViewById(
        R.id.activity_display_book_information_list
    );
//    bookInformationList.setBook(new Book());
  }
}
