package com.easefun.polyv.livecommon.module.modules.marquee;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeTextVO;

/* loaded from: classes3.dex */
public class PLVMarqueeTextView extends PLVStrokeTextView {
    private PLVMarqueeTextVO textModel;

    public PLVMarqueeTextView(Context context) {
        super(context);
    }

    private void setFilterStyle() {
        setHasStroke(this.textModel.isFilter());
        setStrokeWidth(this.textModel.getFilterStrength());
        setStrokeBlurX(this.textModel.getFilterBlurX());
        setStrokeBlurY(this.textModel.getFilterBlurY());
        setStrokeColor(Color.argb((int) this.textModel.getFilterAlpha(), Color.red(this.textModel.getFilterColor()), Color.green(this.textModel.getFilterColor()), Color.blue(this.textModel.getFilterColor())));
        invalidate();
    }

    private void setFontStyle() {
        setTextSize(this.textModel.getFontSize());
        setTextColor(Color.argb(this.textModel.getFontAlpha(), Color.red(this.textModel.getFontColor()), Color.green(this.textModel.getFontColor()), Color.blue(this.textModel.getFontColor())));
    }

    public void setMarqueeTextModel(PLVMarqueeTextVO textVO) {
        this.textModel = textVO;
        setText(textVO.getContent());
        setFontStyle();
        setFilterStyle();
    }

    public PLVMarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PLVMarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
