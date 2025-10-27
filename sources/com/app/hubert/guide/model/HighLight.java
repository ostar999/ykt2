package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.view.View;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public interface HighLight {

    public enum Shape {
        CIRCLE,
        RECTANGLE,
        OVAL,
        ROUND_RECTANGLE
    }

    @Nullable
    HighlightOptions getOptions();

    float getRadius();

    RectF getRectF(View view);

    int getRound();

    Shape getShape();
}
