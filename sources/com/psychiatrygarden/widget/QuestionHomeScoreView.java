package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;

/* loaded from: classes6.dex */
public class QuestionHomeScoreView extends View {
    public QuestionHomeScoreView(Context context) {
        this(context, null);
    }

    private int dpToPx(float value) {
        return (int) TypedValue.applyDimension(1, value, getContext().getResources().getDisplayMetrics());
    }

    private int spToPx(int sp) {
        return (int) (sp * getResources().getDisplayMetrics().scaledDensity);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
    }

    public QuestionHomeScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionHomeScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
