package com.kftc.openbankingsample2.common.util.view.recyclerview;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class KmRecyclerViewDividerHeight extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public KmRecyclerViewDividerHeight(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
