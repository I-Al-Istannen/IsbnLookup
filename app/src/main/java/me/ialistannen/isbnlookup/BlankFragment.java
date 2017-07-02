package me.ialistannen.isbnlookup;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import me.ialistannen.isbnlookup.DisplayTextActivity.MyAdapter;


/**
 */
public class BlankFragment extends Fragment {

  private static final String ARGUMENT_KEY = "me.ialistannen.isbnlookup.blank_fragment.arg_key";

  public BlankFragment() {
    // Required empty public constructor
  }

  /**
   * @param data The data to initialize the fragment with. Will be displayed in a List
   * @return The created fragment
   */
  public static BlankFragment createNew(List<String> data) {
    BlankFragment blankFragment = new BlankFragment();
    Bundle bundle = new Bundle();
    bundle.putStringArrayList(ARGUMENT_KEY, new ArrayList<>(data));
    blankFragment.setArguments(bundle);
    return blankFragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    System.out.println("Attached!");
    System.out.println("And btw, my data is " + getArguments().get(ARGUMENT_KEY));
  }

  @Override
  public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    System.out.println("Created: " + getView());
    if (getView() == null) {
      return;
    }
    RecyclerView recyclerView = getView().findViewById(R.id.blank_fragment_list);
    recyclerView.setAdapter(
        new MyAdapter(getArguments().getStringArrayList(ARGUMENT_KEY).toArray(new String[0]))
    );

    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        getContext(),
        DividerItemDecoration.VERTICAL
    );
    dividerItemDecoration
        .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_divider));
    recyclerView.addItemDecoration(dividerItemDecoration);

    recyclerView.addItemDecoration(new ItemDecoration() {
      private int spacing = 20;

      @Override
      public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(spacing, spacing, spacing, 0);
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    System.out.println("Created!");
    return inflater.inflate(R.layout.fragment_blank, container, false);
  }
}
