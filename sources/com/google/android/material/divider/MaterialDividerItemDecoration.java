package com.google.android.material.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

/* loaded from: classes3.dex */
public class MaterialDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @ColorInt
    private int color;

    @NonNull
    private Drawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private int orientation;
    private final Rect tempRect;
    private int thickness;

    public MaterialDividerItemDecoration(@NonNull Context context, int i2) {
        this(context, null, i2);
    }

    private void drawForHorizontalOrientation(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView) {
        int height;
        int paddingTop;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            paddingTop = recyclerView.getPaddingTop();
            height = recyclerView.getHeight() - recyclerView.getPaddingBottom();
            canvas.clipRect(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getWidth() - recyclerView.getPaddingRight(), height);
        } else {
            height = recyclerView.getHeight();
            paddingTop = 0;
        }
        int i2 = paddingTop + this.insetStart;
        int i3 = height - this.insetEnd;
        int childCount = recyclerView.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = recyclerView.getChildAt(i4);
            recyclerView.getLayoutManager().getDecoratedBoundsWithMargins(childAt, this.tempRect);
            int iRound = this.tempRect.right + Math.round(childAt.getTranslationX());
            this.dividerDrawable.setBounds((iRound - this.dividerDrawable.getIntrinsicWidth()) - this.thickness, i2, iRound, i3);
            this.dividerDrawable.draw(canvas);
        }
        canvas.restore();
    }

    private void drawForVerticalOrientation(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView) {
        int width;
        int paddingLeft;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            paddingLeft = recyclerView.getPaddingLeft();
            width = recyclerView.getWidth() - recyclerView.getPaddingRight();
            canvas.clipRect(paddingLeft, recyclerView.getPaddingTop(), width, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            width = recyclerView.getWidth();
            paddingLeft = 0;
        }
        boolean z2 = ViewCompat.getLayoutDirection(recyclerView) == 1;
        int i2 = paddingLeft + (z2 ? this.insetEnd : this.insetStart);
        int i3 = width - (z2 ? this.insetStart : this.insetEnd);
        int childCount = recyclerView.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = recyclerView.getChildAt(i4);
            recyclerView.getDecoratedBoundsWithMargins(childAt, this.tempRect);
            int iRound = this.tempRect.bottom + Math.round(childAt.getTranslationY());
            this.dividerDrawable.setBounds(i2, (iRound - this.dividerDrawable.getIntrinsicHeight()) - this.thickness, i3, iRound);
            this.dividerDrawable.draw(canvas);
        }
        canvas.restore();
    }

    @ColorInt
    public int getDividerColor() {
        return this.color;
    }

    @Px
    public int getDividerInsetEnd() {
        return this.insetEnd;
    }

    @Px
    public int getDividerInsetStart() {
        return this.insetStart;
    }

    @Px
    public int getDividerThickness() {
        return this.thickness;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        rect.set(0, 0, 0, 0);
        if (this.orientation == 1) {
            rect.bottom = this.dividerDrawable.getIntrinsicHeight() + this.thickness;
        } else {
            rect.right = this.dividerDrawable.getIntrinsicWidth() + this.thickness;
        }
    }

    public int getOrientation() {
        return this.orientation;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        if (recyclerView.getLayoutManager() == null) {
            return;
        }
        if (this.orientation == 1) {
            drawForVerticalOrientation(canvas, recyclerView);
        } else {
            drawForHorizontalOrientation(canvas, recyclerView);
        }
    }

    public void setDividerColor(@ColorInt int i2) {
        this.color = i2;
        Drawable drawableWrap = DrawableCompat.wrap(this.dividerDrawable);
        this.dividerDrawable = drawableWrap;
        DrawableCompat.setTint(drawableWrap, i2);
    }

    public void setDividerColorResource(@NonNull Context context, @ColorRes int i2) {
        setDividerColor(ContextCompat.getColor(context, i2));
    }

    public void setDividerInsetEnd(@Px int i2) {
        this.insetEnd = i2;
    }

    public void setDividerInsetEndResource(@NonNull Context context, @DimenRes int i2) {
        setDividerInsetEnd(context.getResources().getDimensionPixelOffset(i2));
    }

    public void setDividerInsetStart(@Px int i2) {
        this.insetStart = i2;
    }

    public void setDividerInsetStartResource(@NonNull Context context, @DimenRes int i2) {
        setDividerInsetStart(context.getResources().getDimensionPixelOffset(i2));
    }

    public void setDividerThickness(@Px int i2) {
        this.thickness = i2;
    }

    public void setDividerThicknessResource(@NonNull Context context, @DimenRes int i2) {
        setDividerThickness(context.getResources().getDimensionPixelSize(i2));
    }

    public void setOrientation(int i2) {
        if (i2 == 0 || i2 == 1) {
            this.orientation = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid orientation: " + i2 + ". It should be either HORIZONTAL or VERTICAL");
    }

    public MaterialDividerItemDecoration(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, R.attr.materialDividerStyle, i2);
    }

    public MaterialDividerItemDecoration(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        this.tempRect = new Rect();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.MaterialDivider, i2, DEF_STYLE_RES, new int[0]);
        this.color = MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, R.styleable.MaterialDivider_dividerColor).getDefaultColor();
        this.thickness = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialDivider_dividerThickness, context.getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
        this.insetStart = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0);
        this.insetEnd = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.dividerDrawable = new ShapeDrawable();
        setDividerColor(this.color);
        setOrientation(i3);
    }
}
