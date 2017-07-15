package me.ialistannen.isbnlookup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.List;
import me.ialistannen.isbnlookup.io.history.HistoryEntry;
import me.ialistannen.isbnlookup.io.history.LookupHistory;
import me.ialistannen.isbnlookup.view.historylist.HistoryListView;

public class HistoryActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history);

    setSupportActionBar((Toolbar) findViewById(R.id.activity_history_action_bar));

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    HistoryListView historyList = (HistoryListView) findViewById(R.id.activity_history_list_view);

    getFetchingTask().execute(historyList);
  }

  private AsyncTask<HistoryListView, Void, List<HistoryEntry>> getFetchingTask() {
    return new AsyncTask<HistoryListView, Void, List<HistoryEntry>>() {

      private HistoryListView historyListView;

      @Override
      protected List<HistoryEntry> doInBackground(HistoryListView... historyListViews) {
        if (historyListViews.length < 1) {
          return null;
        }
        historyListView = historyListViews[0];

        return LookupHistory.getInstance(HistoryActivity.this).getHistory();
      }

      @Override
      protected void onPostExecute(List<HistoryEntry> historyEntries) {
        if (historyEntries == null || historyEntries.isEmpty()) {
          return;
        }

        historyListView.setData(historyEntries);
      }
    };
  }
}
