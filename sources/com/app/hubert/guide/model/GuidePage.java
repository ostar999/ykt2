package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.view.View;
import android.view.animation.Animation;
import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import com.app.hubert.guide.listener.OnHighlightDrewListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.HighlightOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class GuidePage {
    private int backgroundColor;
    private int[] clickToDismissIds;
    private Animation enterAnimation;
    private Animation exitAnimation;
    private int layoutResId;
    private OnHighlightDrewListener onHighlightDrewListener;
    private OnLayoutInflatedListener onLayoutInflatedListener;
    private List<HighLight> highLights = new ArrayList();
    private boolean everywhereCancelable = true;

    public static GuidePage newInstance() {
        return new GuidePage();
    }

    public GuidePage addHighLight(View view) {
        return addHighLight(view, HighLight.Shape.RECTANGLE, 0, 0, null);
    }

    public GuidePage addHighLightWithOptions(View view, HighlightOptions highlightOptions) {
        return addHighLightWithOptions(view, HighLight.Shape.RECTANGLE, 0, 0, highlightOptions);
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public int[] getClickToDismissIds() {
        return this.clickToDismissIds;
    }

    public Animation getEnterAnimation() {
        return this.enterAnimation;
    }

    public Animation getExitAnimation() {
        return this.exitAnimation;
    }

    public List<HighLight> getHighLights() {
        return this.highLights;
    }

    public int getLayoutResId() {
        return this.layoutResId;
    }

    public OnLayoutInflatedListener getOnLayoutInflatedListener() {
        return this.onLayoutInflatedListener;
    }

    public List<RelativeGuide> getRelativeGuides() {
        RelativeGuide relativeGuide;
        ArrayList arrayList = new ArrayList();
        Iterator<HighLight> it = this.highLights.iterator();
        while (it.hasNext()) {
            HighlightOptions options = it.next().getOptions();
            if (options != null && (relativeGuide = options.relativeGuide) != null) {
                arrayList.add(relativeGuide);
            }
        }
        return arrayList;
    }

    public boolean isEmpty() {
        return this.layoutResId == 0 && this.highLights.size() == 0;
    }

    public boolean isEverywhereCancelable() {
        return this.everywhereCancelable;
    }

    public GuidePage setBackgroundColor(@ColorInt int i2) {
        this.backgroundColor = i2;
        return this;
    }

    public GuidePage setEnterAnimation(Animation animation) {
        this.enterAnimation = animation;
        return this;
    }

    public GuidePage setEverywhereCancelable(boolean z2) {
        this.everywhereCancelable = z2;
        return this;
    }

    public GuidePage setExitAnimation(Animation animation) {
        this.exitAnimation = animation;
        return this;
    }

    public GuidePage setLayoutRes(@LayoutRes int i2, int... iArr) {
        this.layoutResId = i2;
        this.clickToDismissIds = iArr;
        return this;
    }

    public GuidePage setOnLayoutInflatedListener(OnLayoutInflatedListener onLayoutInflatedListener) {
        this.onLayoutInflatedListener = onLayoutInflatedListener;
        return this;
    }

    public GuidePage addHighLight(View view, RelativeGuide relativeGuide) {
        return addHighLight(view, HighLight.Shape.RECTANGLE, 0, 0, relativeGuide);
    }

    public GuidePage addHighLightWithOptions(View view, HighLight.Shape shape, HighlightOptions highlightOptions) {
        return addHighLightWithOptions(view, shape, 0, 0, highlightOptions);
    }

    public GuidePage addHighLight(View view, HighLight.Shape shape) {
        return addHighLight(view, shape, 0, 0, null);
    }

    public GuidePage addHighLightWithOptions(View view, HighLight.Shape shape, int i2, int i3, HighlightOptions highlightOptions) {
        RelativeGuide relativeGuide;
        HighlightView highlightView = new HighlightView(view, shape, i2, i3);
        if (highlightOptions != null && (relativeGuide = highlightOptions.relativeGuide) != null) {
            relativeGuide.highLight = highlightView;
        }
        highlightView.setOptions(highlightOptions);
        this.highLights.add(highlightView);
        return this;
    }

    public GuidePage addHighLight(View view, HighLight.Shape shape, RelativeGuide relativeGuide) {
        return addHighLight(view, shape, 0, 0, relativeGuide);
    }

    public GuidePage addHighLight(View view, HighLight.Shape shape, int i2) {
        return addHighLight(view, shape, 0, i2, null);
    }

    public GuidePage addHighLight(View view, HighLight.Shape shape, int i2, RelativeGuide relativeGuide) {
        return addHighLight(view, shape, 0, i2, relativeGuide);
    }

    public GuidePage addHighLight(View view, HighLight.Shape shape, int i2, int i3, @Nullable RelativeGuide relativeGuide) {
        HighlightView highlightView = new HighlightView(view, shape, i2, i3);
        if (relativeGuide != null) {
            relativeGuide.highLight = highlightView;
            highlightView.setOptions(new HighlightOptions.Builder().setRelativeGuide(relativeGuide).build());
        }
        this.highLights.add(highlightView);
        return this;
    }

    public GuidePage addHighLightWithOptions(RectF rectF, HighlightOptions highlightOptions) {
        return addHighLightWithOptions(rectF, HighLight.Shape.RECTANGLE, 0, highlightOptions);
    }

    public GuidePage addHighLightWithOptions(RectF rectF, HighLight.Shape shape, HighlightOptions highlightOptions) {
        return addHighLightWithOptions(rectF, shape, 0, highlightOptions);
    }

    public GuidePage addHighLightWithOptions(RectF rectF, HighLight.Shape shape, int i2, HighlightOptions highlightOptions) {
        RelativeGuide relativeGuide;
        HighlightRectF highlightRectF = new HighlightRectF(rectF, shape, i2);
        if (highlightOptions != null && (relativeGuide = highlightOptions.relativeGuide) != null) {
            relativeGuide.highLight = highlightRectF;
        }
        highlightRectF.setOptions(highlightOptions);
        this.highLights.add(highlightRectF);
        return this;
    }

    public GuidePage addHighLight(RectF rectF) {
        return addHighLight(rectF, HighLight.Shape.RECTANGLE, 0, (RelativeGuide) null);
    }

    public GuidePage addHighLight(RectF rectF, RelativeGuide relativeGuide) {
        return addHighLight(rectF, HighLight.Shape.RECTANGLE, 0, relativeGuide);
    }

    public GuidePage addHighLight(RectF rectF, HighLight.Shape shape) {
        return addHighLight(rectF, shape, 0, (RelativeGuide) null);
    }

    public GuidePage addHighLight(RectF rectF, HighLight.Shape shape, RelativeGuide relativeGuide) {
        return addHighLight(rectF, shape, 0, relativeGuide);
    }

    public GuidePage addHighLight(RectF rectF, HighLight.Shape shape, int i2) {
        return addHighLight(rectF, shape, i2, (RelativeGuide) null);
    }

    public GuidePage addHighLight(RectF rectF, HighLight.Shape shape, int i2, RelativeGuide relativeGuide) {
        HighlightRectF highlightRectF = new HighlightRectF(rectF, shape, i2);
        if (relativeGuide != null) {
            relativeGuide.highLight = highlightRectF;
            highlightRectF.setOptions(new HighlightOptions.Builder().setRelativeGuide(relativeGuide).build());
        }
        this.highLights.add(highlightRectF);
        return this;
    }
}
