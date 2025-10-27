package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.view.View;
import androidx.annotation.NonNull;
import com.app.hubert.guide.model.HighLight;

/* loaded from: classes2.dex */
public class HighlightRectF implements HighLight {
    private HighlightOptions options;
    private RectF rectF;
    private int round;
    private HighLight.Shape shape;

    public HighlightRectF(@NonNull RectF rectF, @NonNull HighLight.Shape shape, int i2) {
        this.rectF = rectF;
        this.shape = shape;
        this.round = i2;
    }

    @Override // com.app.hubert.guide.model.HighLight
    public HighlightOptions getOptions() {
        return this.options;
    }

    @Override // com.app.hubert.guide.model.HighLight
    public float getRadius() {
        return Math.min(this.rectF.width() / 2.0f, this.rectF.height() / 2.0f);
    }

    @Override // com.app.hubert.guide.model.HighLight
    public RectF getRectF(View view) {
        return this.rectF;
    }

    @Override // com.app.hubert.guide.model.HighLight
    public int getRound() {
        return this.round;
    }

    @Override // com.app.hubert.guide.model.HighLight
    public HighLight.Shape getShape() {
        return this.shape;
    }

    public void setOptions(HighlightOptions highlightOptions) {
        this.options = highlightOptions;
    }
}
