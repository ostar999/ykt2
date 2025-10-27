package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.view.View;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.util.LogUtil;
import com.app.hubert.guide.util.ViewUtils;

/* loaded from: classes2.dex */
public class HighlightView implements HighLight {
    private View mHole;
    private HighlightOptions options;
    private int padding;
    private RectF rectF;
    private int round;
    private HighLight.Shape shape;

    public HighlightView(View view, HighLight.Shape shape, int i2, int i3) {
        this.mHole = view;
        this.shape = shape;
        this.round = i2;
        this.padding = i3;
    }

    private RectF fetchLocation(View view) {
        RectF rectF = new RectF();
        int i2 = ViewUtils.getLocationInView(view, this.mHole).left;
        int i3 = this.padding;
        rectF.left = i2 - i3;
        rectF.top = r4.top - i3;
        rectF.right = r4.right + i3;
        rectF.bottom = r4.bottom + i3;
        return rectF;
    }

    @Override // com.app.hubert.guide.model.HighLight
    public HighlightOptions getOptions() {
        return this.options;
    }

    @Override // com.app.hubert.guide.model.HighLight
    public float getRadius() {
        if (this.mHole != null) {
            return Math.max(r0.getWidth() / 2, this.mHole.getHeight() / 2) + this.padding;
        }
        throw new IllegalArgumentException("the highlight view is null!");
    }

    @Override // com.app.hubert.guide.model.HighLight
    public RectF getRectF(View view) {
        if (this.mHole == null) {
            throw new IllegalArgumentException("the highlight view is null!");
        }
        if (this.rectF == null) {
            this.rectF = fetchLocation(view);
        } else {
            HighlightOptions highlightOptions = this.options;
            if (highlightOptions != null && highlightOptions.fetchLocationEveryTime) {
                this.rectF = fetchLocation(view);
            }
        }
        LogUtil.i(this.mHole.getClass().getSimpleName() + "'s location:" + this.rectF);
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
