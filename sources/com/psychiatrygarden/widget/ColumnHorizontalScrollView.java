package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/* loaded from: classes6.dex */
public class ColumnHorizontalScrollView extends HorizontalScrollView {
    private ImageView leftImage;
    private View ll_content;
    private View ll_more;
    private ImageView rightImage;
    private View rl_column;

    public ColumnHorizontalScrollView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
