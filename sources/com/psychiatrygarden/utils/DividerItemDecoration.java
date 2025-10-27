package com.psychiatrygarden.utils;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* loaded from: classes6.dex */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {R.attr.listDivider};
    public static final int BOTH_SET = 2;
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    private int mDividerHeight;
    private Drawable mDrawable;
    private int mOrientation;
    private Paint mPaint;

    public DividerItemDecoration(Context context, int orientation) {
        this.mDividerHeight = 2;
        setOrientation(orientation);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(ATTRS);
        this.mDrawable = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int paddingLeft = parent.getPaddingLeft();
        int measuredWidth = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = parent.getChildAt(i2);
            int bottom = childAt.getBottom() + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) childAt.getLayoutParams())).bottomMargin;
            int i3 = this.mDividerHeight + bottom;
            Log.e("height", i3 + "===================");
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.setBounds(paddingLeft, bottom, measuredWidth, i3);
                this.mDrawable.draw(canvas);
            }
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(paddingLeft, bottom, measuredWidth, i3, paint);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int paddingTop = parent.getPaddingTop();
        int measuredHeight = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = parent.getChildAt(i2);
            int right = childAt.getRight() + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) childAt.getLayoutParams())).rightMargin;
            int i3 = this.mDividerHeight + right;
            Drawable drawable = this.mDrawable;
            if (drawable != null) {
                drawable.setBounds(right, paddingTop, i3, measuredHeight);
                this.mDrawable.draw(canvas);
            }
            Paint paint = this.mPaint;
            if (paint != null) {
                canvas.drawRect(right, paddingTop, i3, measuredHeight, paint);
            }
        }
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return -1;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getOrientation() == 1 ? (pos + 1) % spanCount == 0 : pos >= childCount - (childCount % spanCount);
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? (pos + 1) % spanCount == 0 : pos >= childCount - (childCount % spanCount);
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int i2 = childCount - (childCount % spanCount);
            return ((GridLayoutManager) layoutManager).getOrientation() == 1 ? pos >= i2 - (i2 % spanCount) : (pos + 1) % spanCount == 0;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 1 ? pos >= childCount - (childCount % spanCount) : (pos + 1) % spanCount == 0;
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int itemCount = parent.getAdapter().getItemCount();
        int i2 = this.mOrientation;
        if (i2 == 0) {
            outRect.set(0, 0, 0, viewLayoutPosition != itemCount + (-1) ? this.mDividerHeight : 0);
            return;
        }
        if (i2 == 1) {
            outRect.set(0, 0, viewLayoutPosition != itemCount + (-1) ? this.mDividerHeight : 0, 0);
            return;
        }
        if (i2 != 2) {
            return;
        }
        int spanCount = getSpanCount(parent);
        if (isLastRaw(parent, viewLayoutPosition, spanCount, itemCount)) {
            outRect.set(0, 0, this.mDividerHeight, 0);
        } else if (isLastColum(parent, viewLayoutPosition, spanCount, itemCount)) {
            outRect.set(0, 0, 0, this.mDividerHeight);
        } else {
            int i3 = this.mDividerHeight;
            outRect.set(0, 0, i3, i3);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas c3, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c3, parent, state);
        int i2 = this.mOrientation;
        if (i2 == 1) {
            drawVertical(c3, parent);
        } else if (i2 == 0) {
            drawHorizontal(c3, parent);
        } else {
            drawHorizontal(c3, parent);
            drawVertical(c3, parent);
        }
    }

    public void setOrientation(int orientation) {
        if (orientation < 0 || orientation > 2) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = orientation;
    }

    public DividerItemDecoration(Context context, int orientation, int drawableId) {
        this.mDividerHeight = 2;
        setOrientation(orientation);
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        this.mDrawable = drawable;
        this.mDividerHeight = drawable.getIntrinsicHeight();
    }

    public DividerItemDecoration(Context context, int orientation, int dividerHeight, int dividerColor) {
        this.mDividerHeight = 2;
        setOrientation(orientation);
        this.mDividerHeight = dividerHeight;
        Log.e("mDividerHeight", this.mDividerHeight + "===================");
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(dividerColor);
        this.mPaint.setStyle(Paint.Style.FILL);
    }
}
