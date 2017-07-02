package me.ialistannen.isbnlookup;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;

public class DisplayTextActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_text);

    Intent intent = getIntent();
    String stringExtra = intent.getStringExtra(MainActivity.MESSAGE_EXTRA_KEY);

    TextView displayedText = (TextView) findViewById(R.id.display_text);
    displayedText.setText(stringExtra);

    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    String[] items = new String[10];
    for (int i = 0; i < items.length; i++) {
      items[i] = "Item " + i;
    }
    recyclerView.setAdapter(new MyAdapter(items));
    recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    recyclerView.addItemDecoration(new ItemDecoration() {
      private int space = 20;

      @Override
      public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.top = space;
      }
    });
  }

  public static class MyAdapter extends Adapter {

    private String[] data;

    public MyAdapter(String[] data) {
      this.data = Arrays.copyOf(data, data.length);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      TextView textView = new TextView(parent.getContext());
      return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      ((MyViewHolder) holder).textView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
      return data.length;
    }

    private static class MyViewHolder extends ViewHolder {

      private TextView textView;

      MyViewHolder(TextView textView) {
        super(textView);
        this.textView = textView;
      }
    }
  }
}
