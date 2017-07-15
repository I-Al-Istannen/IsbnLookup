package me.ialistannen.isbnlookup.view.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * An item decoration that provides padding.
 */
public class PaddingItemDecoration extends ItemDecoration {

  private int spacing;

  public PaddingItemDecoration(int spacing) {
    this.spacing = spacing;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
    super.getItemOffsets(outRect, view, parent, state);
    outRect.set(spacing, outRect.top, spacing, spacing);

    if (parent.getChildAdapterPosition(view) > 0) {
      outRect.top = spacing;
    }
  }
}
