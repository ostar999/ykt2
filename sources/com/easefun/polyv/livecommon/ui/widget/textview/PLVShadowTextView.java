package com.easefun.polyv.livecommon.ui.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVShadowTextView extends AppCompatTextView {
    private int shadowColor;
    private float shadowRadius;

    public PLVShadowTextView(Context context) {
        super(context);
        this.shadowRadius = ConvertUtils.dp2px(0.5f);
        this.shadowColor = PLVFormatUtils.parseColor("#66000000");
        initView();
    }

    private void initView() {
        setLayerType(1, null);
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        getPaint().setShadowLayer(this.shadowRadius, 0.0f, 0.0f, this.shadowColor);
        super.onDraw(canvas);
    }

    public PLVShadowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.shadowRadius = ConvertUtils.dp2px(0.5f);
        this.shadowColor = PLVFormatUtils.parseColor("#66000000");
        initView();
    }

    public PLVShadowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.shadowRadius = ConvertUtils.dp2px(0.5f);
        this.shadowColor = PLVFormatUtils.parseColor("#66000000");
        initView();
    }
}
