package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.contrarywind.interfaces.IPickerViewData;
import com.contrarywind.view.WheelView;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public class FlatWheelView extends WheelView {
    private static final String[] TIME_NUM = {TarConstants.VERSION_POSIX, HiAnalyticsConstant.KeyAndValue.NUMBER_01, "02", "03", "04", "05", "06", "07", "08", "09"};

    public FlatWheelView(Context context) {
        super(context);
    }

    private float calculateDrawX(String contentText, Paint paint) {
        paint.getTextBounds(contentText, 0, contentText.length(), new Rect());
        return (getMeasuredWidth() - r0.width()) / 2.0f;
    }

    private void drawDividers(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float measuredWidth = getMeasuredWidth();
        float measuredHeight = getMeasuredHeight();
        float itemHeight = getItemHeight();
        float f2 = (measuredHeight - itemHeight) / 2.0f;
        float f3 = (measuredHeight + itemHeight) / 2.0f;
        canvas.drawLine(0.0f, f2, measuredWidth, f2, paint);
        canvas.drawLine(0.0f, f3, measuredWidth, f3, paint);
    }

    private void drawItem(Canvas canvas, int counter, int preCurrentIndex, float itemHeight, float itemHeightOffset, int itemsVisible) {
        int i2 = itemsVisible / 2;
        int i3 = preCurrentIndex - (i2 - counter);
        String contentText = getContentText(isLoop() ? getAdapter().getItem(getLoopMappingIndex(i3)) : (i3 >= 0 && i3 <= getAdapter().getItemsCount() - 1) ? getAdapter().getItem(i3) : "");
        if (TextUtils.isEmpty(contentText)) {
            return;
        }
        float measuredHeight = (((counter - i2) * itemHeight) - itemHeightOffset) + (getMeasuredHeight() / 2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (counter == i2) {
            setupCenterTextPaint(paint);
        } else {
            setupOuterTextPaint(paint, counter, itemsVisible);
        }
        canvas.drawText(contentText, calculateDrawX(contentText, paint), measuredHeight, paint);
    }

    private void drawLabel(Canvas canvas) {
    }

    private String getContentText(Object item) {
        return item == null ? "" : item instanceof IPickerViewData ? ((IPickerViewData) item).getPickerViewText() : item instanceof Integer ? getFixNum(((Integer) item).intValue()) : item.toString();
    }

    private String getFixNum(int timeNum) {
        return (timeNum < 0 || timeNum >= 10) ? String.valueOf(timeNum) : TIME_NUM[timeNum];
    }

    private int getLoopMappingIndex(int index) {
        return index < 0 ? getLoopMappingIndex(index + getAdapter().getItemsCount()) : index > getAdapter().getItemsCount() + (-1) ? getLoopMappingIndex(index - getAdapter().getItemsCount()) : index;
    }

    private float getTextSize() {
        return 48.0f;
    }

    private void setupCenterTextPaint(Paint paint) {
        paint.setTextSize(getTextSize());
        paint.setTextScaleX(1.1f);
    }

    private void setupOuterTextPaint(Paint paint, int counter, int itemsVisible) {
        paint.setTextSize(getTextSize());
        paint.setAlpha(Math.max(80, 255 - (Math.abs(counter - (itemsVisible / 2)) * 40)));
    }

    @Override // com.contrarywind.view.WheelView, android.view.View
    public void onDraw(Canvas canvas) {
        if (getAdapter() == null) {
            return;
        }
        float itemHeight = getItemHeight();
        float totalScrollY = getTotalScrollY();
        int initPosition = getInitPosition() + (((int) (totalScrollY / itemHeight)) % getAdapter().getItemsCount());
        if (isLoop()) {
            if (initPosition < 0) {
                initPosition += getAdapter().getItemsCount();
            }
            if (initPosition > getAdapter().getItemsCount() - 1) {
                initPosition -= getAdapter().getItemsCount();
            }
        } else {
            if (initPosition < 0) {
                initPosition = 0;
            }
            if (initPosition > getAdapter().getItemsCount() - 1) {
                initPosition = getAdapter().getItemsCount() - 1;
            }
        }
        int i2 = initPosition;
        float f2 = totalScrollY % itemHeight;
        drawDividers(canvas);
        drawLabel(canvas);
        for (int i3 = 0; i3 < 11; i3++) {
            drawItem(canvas, i3, i2, itemHeight, f2, 11);
        }
    }

    public FlatWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
