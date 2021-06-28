package com.thiyaga.ottsampleproject.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Picha Mahakittikun on 7/12/16 AD.
 */

public class VerticalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final boolean isAddToLastItem;
    private final int verticalSpaceHeight;

    public VerticalSpacingItemDecoration(int verticalSpaceHeight, boolean isAddToLastItem) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.isAddToLastItem = isAddToLastItem;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isAddToLastItem || (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

}
