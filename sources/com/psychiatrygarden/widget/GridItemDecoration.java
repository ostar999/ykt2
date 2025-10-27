package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private int dividerWidth;
    private Drawable mDivider;
    private int spacingLeft;
    private int spanCount;

    public GridItemDecoration(Context context, int spanCount, int spacingLeft, int dividerWidth, int dividerHeight) {
        this.spanCount = spanCount;
        this.spacingLeft = spacingLeft;
        this.dividerWidth = dividerWidth;
        this.dividerHeight = dividerHeight;
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_one_border_color});
        this.mDivider = new ColorDrawable(typedArrayObtainStyledAttributes.getColor(0, context.getColor(R.color.color_eeeeee)));
        typedArrayObtainStyledAttributes.recycle();
    }

    private void drawHorizontal(Canvas c3, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int iApplyDimension = (int) TypedValue.applyDimension(1, 11.0f, parent.getContext().getResources().getDisplayMetrics());
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = parent.getChildAt(i2);
            int childAdapterPosition = parent.getChildAdapterPosition(childAt);
            if ((childAdapterPosition + 1) % this.spanCount != 0 && childAdapterPosition < parent.getAdapter().getItemCount() - 1) {
                int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) childAt.getLayoutParams())).bottomMargin;
                int i3 = bottom - this.dividerHeight;
                int right = childAt.getRight() + this.spacingLeft;
                this.mDivider.setBounds(right, bottom - iApplyDimension, this.dividerWidth + right, i3 - iApplyDimension);
                this.mDivider.draw(c3);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if ((childAdapterPosition + 1) % this.spanCount == 0 || childAdapterPosition >= parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, this.dividerHeight);
        } else {
            outRect.set(0, 0, this.dividerWidth, this.dividerHeight);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(@NonNull Canvas c3, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() == null || parent.getChildCount() <= 1) {
            return;
        }
        drawHorizontal(c3, parent);
    }
}
