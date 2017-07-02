package me.ialistannen.isbnlookup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  static final String MESSAGE_EXTRA_KEY = "me.ialistannen.isbnlookup.Sent_message";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      List<String> list = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
        list.add("Item " + i);
      }
      BlankFragment fragment = BlankFragment.createNew(list);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit();
    }
  }

  public void onSendMessage(View view) {
    Intent intent = new Intent(this, DisplayTextActivity.class);

    TextView messageView = (TextView) findViewById(R.id.edit_text);

    intent.putExtra(MESSAGE_EXTRA_KEY, messageView.getText().toString());

    startActivity(intent);
  }
}
