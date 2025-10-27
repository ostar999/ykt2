package com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVBeautyItemSelectorTextView extends AppCompatTextView {
    private boolean canDrawIndicator;
    private final int indicatorRadius;
    private final Paint selectedIndicatePaint;
    private final int selectedTextColor;
    private final int unselectedTextColor;

    public PLVBeautyItemSelectorTextView(Context context) {
        super(context);
        this.unselectedTextColor = PLVFormatUtils.parseColor("#99F0F1F5");
        this.selectedTextColor = PLVFormatUtils.parseColor("#F0F1F5");
        this.selectedIndicatePaint = new Paint() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget.PLVBeautyItemSelectorTextView.1
            {
                setColor(PLVFormatUtils.parseColor("#3399FF"));
                setAntiAlias(true);
            }
        };
        this.indicatorRadius = ConvertUtils.dp2px(2.0f);
        this.canDrawIndicator = true;
    }

    private void drawIndicator(Canvas canvas) {
        if (this.canDrawIndicator && isSelected()) {
            int width = getWidth() / 2;
            int height = getHeight();
            canvas.drawCircle(width, height - r2, this.indicatorRadius, this.selectedIndicatePaint);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        boolean z2 = View.MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE && getMeasuredHeight() + (this.indicatorRadius * 2) <= View.MeasureSpec.getSize(heightMeasureSpec);
        this.canDrawIndicator = z2;
        if (z2) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + (this.indicatorRadius * 2));
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setTextColor(selected ? this.selectedTextColor : this.unselectedTextColor);
    }

    public PLVBeautyItemSelectorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.unselectedTextColor = PLVFormatUtils.parseColor("#99F0F1F5");
        this.selectedTextColor = PLVFormatUtils.parseColor("#F0F1F5");
        this.selectedIndicatePaint = new Paint() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget.PLVBeautyItemSelectorTextView.1
            {
                setColor(PLVFormatUtils.parseColor("#3399FF"));
                setAntiAlias(true);
            }
        };
        this.indicatorRadius = ConvertUtils.dp2px(2.0f);
        this.canDrawIndicator = true;
    }

    public PLVBeautyItemSelectorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.unselectedTextColor = PLVFormatUtils.parseColor("#99F0F1F5");
        this.selectedTextColor = PLVFormatUtils.parseColor("#F0F1F5");
        this.selectedIndicatePaint = new Paint() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget.PLVBeautyItemSelectorTextView.1
            {
                setColor(PLVFormatUtils.parseColor("#3399FF"));
                setAntiAlias(true);
            }
        };
        this.indicatorRadius = ConvertUtils.dp2px(2.0f);
        this.canDrawIndicator = true;
    }
}
