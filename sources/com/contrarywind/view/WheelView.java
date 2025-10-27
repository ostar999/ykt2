package com.contrarywind.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.interfaces.IPickerViewData;
import com.contrarywind.listener.LoopViewGestureListener;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.timer.InertiaTimerTask;
import com.contrarywind.timer.MessageHandler;
import com.contrarywind.timer.SmoothScrollTimerTask;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes3.dex */
public class WheelView extends View {
    private static final float SCALE_CONTENT = 0.8f;
    private static final String[] TIME_NUM = {TarConstants.VERSION_POSIX, HiAnalyticsConstant.KeyAndValue.NUMBER_01, "02", "03", "04", "05", "06", "07", "08", "09"};
    private static final int VELOCITY_FLING = 5;
    private float CENTER_CONTENT_OFFSET;
    private WheelAdapter adapter;
    private float centerY;
    private Context context;
    private int dividerColor;
    private DividerType dividerType;
    private int dividerWidth;
    private int drawCenterContentStart;
    private int drawOutContentStart;
    private float firstLineY;
    private GestureDetector gestureDetector;
    private Handler handler;
    private int initPosition;
    private boolean isAlphaGradient;
    private boolean isCenterLabel;
    private boolean isLoop;
    private boolean isOptions;
    private float itemHeight;
    private int itemsVisible;
    private String label;
    private float lineSpacingMultiplier;
    private ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mFuture;
    private int mGravity;
    private int mOffset;
    private int maxTextHeight;
    private int maxTextWidth;
    private int measuredHeight;
    private int measuredWidth;
    private OnItemSelectedListener onItemSelectedListener;
    private Paint paintCenterText;
    private Paint paintIndicator;
    private Paint paintOuterText;
    private int preCurrentIndex;
    private float previousY;
    private int radius;
    private float secondLineY;
    private int selectedItem;
    private long startTime;
    private int textColorCenter;
    private int textColorOut;
    private int textSize;
    private int textXOffset;
    private float totalScrollY;
    private Typeface typeface;
    private int widthMeasureSpec;

    public enum ACTION {
        CLICK,
        FLING,
        DAGGLE
    }

    public enum DividerType {
        FILL,
        WRAP,
        CIRCLE
    }

    public WheelView(Context context) {
        this(context, null);
    }

    private String getContentText(Object obj) {
        return obj == null ? "" : obj instanceof IPickerViewData ? ((IPickerViewData) obj).getPickerViewText() : obj instanceof Integer ? getFixNum(((Integer) obj).intValue()) : obj.toString();
    }

    private String getFixNum(int i2) {
        return (i2 < 0 || i2 >= 10) ? String.valueOf(i2) : TIME_NUM[i2];
    }

    private int getLoopMappingIndex(int i2) {
        return i2 < 0 ? getLoopMappingIndex(i2 + this.adapter.getItemsCount()) : i2 > this.adapter.getItemsCount() + (-1) ? getLoopMappingIndex(i2 - this.adapter.getItemsCount()) : i2;
    }

    private void initLoopView(Context context) {
        this.context = context;
        this.handler = new MessageHandler(this);
        GestureDetector gestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        this.gestureDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        this.isLoop = true;
        this.totalScrollY = 0.0f;
        this.initPosition = -1;
        initPaints();
    }

    private void initPaints() {
        Paint paint = new Paint();
        this.paintOuterText = paint;
        paint.setColor(this.textColorOut);
        this.paintOuterText.setAntiAlias(true);
        this.paintOuterText.setTypeface(this.typeface);
        this.paintOuterText.setTextSize(this.textSize);
        Paint paint2 = new Paint();
        this.paintCenterText = paint2;
        paint2.setColor(this.textColorCenter);
        this.paintCenterText.setAntiAlias(true);
        this.paintCenterText.setTextScaleX(1.1f);
        this.paintCenterText.setTypeface(this.typeface);
        this.paintCenterText.setTextSize(this.textSize);
        Paint paint3 = new Paint();
        this.paintIndicator = paint3;
        paint3.setColor(this.dividerColor);
        this.paintIndicator.setAntiAlias(true);
        setLayerType(1, null);
    }

    private void judgeLineSpace() {
        float f2 = this.lineSpacingMultiplier;
        if (f2 < 1.0f) {
            this.lineSpacingMultiplier = 1.0f;
        } else if (f2 > 4.0f) {
            this.lineSpacingMultiplier = 4.0f;
        }
    }

    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i2 = 0; i2 < this.adapter.getItemsCount(); i2++) {
            String contentText = getContentText(this.adapter.getItem(i2));
            this.paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
            int iWidth = rect.width();
            if (iWidth > this.maxTextWidth) {
                this.maxTextWidth = iWidth;
            }
        }
        this.paintCenterText.getTextBounds("星期", 0, 2, rect);
        int iHeight = rect.height() + 2;
        this.maxTextHeight = iHeight;
        this.itemHeight = this.lineSpacingMultiplier * iHeight;
    }

    private void measuredCenterContentStart(String str) {
        String str2;
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
        int i2 = this.mGravity;
        if (i2 == 3) {
            this.drawCenterContentStart = 0;
            return;
        }
        if (i2 == 5) {
            this.drawCenterContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTER_CONTENT_OFFSET);
            return;
        }
        if (i2 != 17) {
            return;
        }
        if (this.isOptions || (str2 = this.label) == null || str2.equals("") || !this.isCenterLabel) {
            this.drawCenterContentStart = (int) ((this.measuredWidth - rect.width()) * 0.5d);
        } else {
            this.drawCenterContentStart = (int) ((this.measuredWidth - rect.width()) * 0.25d);
        }
    }

    private void measuredOutContentStart(String str) {
        String str2;
        Rect rect = new Rect();
        this.paintOuterText.getTextBounds(str, 0, str.length(), rect);
        int i2 = this.mGravity;
        if (i2 == 3) {
            this.drawOutContentStart = 0;
            return;
        }
        if (i2 == 5) {
            this.drawOutContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTER_CONTENT_OFFSET);
            return;
        }
        if (i2 != 17) {
            return;
        }
        if (this.isOptions || (str2 = this.label) == null || str2.equals("") || !this.isCenterLabel) {
            this.drawOutContentStart = (int) ((this.measuredWidth - rect.width()) * 0.5d);
        } else {
            this.drawOutContentStart = (int) ((this.measuredWidth - rect.width()) * 0.25d);
        }
    }

    private void reMeasure() {
        if (this.adapter == null) {
            return;
        }
        measureTextWidthHeight();
        int i2 = (int) (this.itemHeight * (this.itemsVisible - 1));
        this.measuredHeight = (int) ((i2 * 2) / 3.141592653589793d);
        this.radius = (int) (i2 / 3.141592653589793d);
        this.measuredWidth = View.MeasureSpec.getSize(this.widthMeasureSpec);
        int i3 = this.measuredHeight;
        float f2 = this.itemHeight;
        this.firstLineY = (i3 - f2) / 2.0f;
        float f3 = (i3 + f2) / 2.0f;
        this.secondLineY = f3;
        this.centerY = (f3 - ((f2 - this.maxTextHeight) / 2.0f)) - this.CENTER_CONTENT_OFFSET;
        if (this.initPosition == -1) {
            if (this.isLoop) {
                this.initPosition = (this.adapter.getItemsCount() + 1) / 2;
            } else {
                this.initPosition = 0;
            }
        }
        this.preCurrentIndex = this.initPosition;
    }

    private void reMeasureTextSize(String str) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
        int i2 = this.textSize;
        for (int iWidth = rect.width(); iWidth > this.measuredWidth; iWidth = rect.width()) {
            i2--;
            this.paintCenterText.setTextSize(i2);
            this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
        }
        this.paintOuterText.setTextSize(i2);
    }

    private void setOutPaintStyle(float f2, float f3) {
        int i2 = this.textXOffset;
        this.paintOuterText.setTextSkewX((i2 > 0 ? 1 : i2 < 0 ? -1 : 0) * (f3 <= 0.0f ? 1 : -1) * 0.5f * f2);
        this.paintOuterText.setAlpha(this.isAlphaGradient ? (int) (((90.0f - Math.abs(f3)) / 90.0f) * 255.0f) : 255);
    }

    public void cancelFuture() {
        ScheduledFuture<?> scheduledFuture = this.mFuture;
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            return;
        }
        this.mFuture.cancel(true);
        this.mFuture = null;
    }

    public final WheelAdapter getAdapter() {
        return this.adapter;
    }

    public final int getCurrentItem() {
        int i2;
        WheelAdapter wheelAdapter = this.adapter;
        if (wheelAdapter == null) {
            return 0;
        }
        return (!this.isLoop || ((i2 = this.selectedItem) >= 0 && i2 < wheelAdapter.getItemsCount())) ? Math.max(0, Math.min(this.selectedItem, this.adapter.getItemsCount() - 1)) : Math.max(0, Math.min(Math.abs(Math.abs(this.selectedItem) - this.adapter.getItemsCount()), this.adapter.getItemsCount() - 1));
    }

    @Override // android.view.View
    public Handler getHandler() {
        return this.handler;
    }

    public int getInitPosition() {
        return this.initPosition;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public int getItemsCount() {
        WheelAdapter wheelAdapter = this.adapter;
        if (wheelAdapter != null) {
            return wheelAdapter.getItemsCount();
        }
        return 0;
    }

    public int getTextWidth(Paint paint, String str) {
        if (str == null || str.length() <= 0) {
            return 0;
        }
        int length = str.length();
        paint.getTextWidths(str, new float[length]);
        int iCeil = 0;
        for (int i2 = 0; i2 < length; i2++) {
            iCeil += (int) Math.ceil(r2[i2]);
        }
        return iCeil;
    }

    public float getTotalScrollY() {
        return this.totalScrollY;
    }

    public void isCenterLabel(boolean z2) {
        this.isCenterLabel = z2;
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Object item;
        boolean z2;
        float f2;
        String contentText;
        if (this.adapter == null) {
            return;
        }
        boolean z3 = false;
        int iMin = Math.min(Math.max(0, this.initPosition), this.adapter.getItemsCount() - 1);
        this.initPosition = iMin;
        try {
            this.preCurrentIndex = iMin + (((int) (this.totalScrollY / this.itemHeight)) % this.adapter.getItemsCount());
        } catch (ArithmeticException unused) {
            Log.e("WheelView", "出错了！adapter.getItemsCount() == 0，联动数据不匹配");
        }
        if (this.isLoop) {
            if (this.preCurrentIndex < 0) {
                this.preCurrentIndex = this.adapter.getItemsCount() + this.preCurrentIndex;
            }
            if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                this.preCurrentIndex -= this.adapter.getItemsCount();
            }
        } else {
            if (this.preCurrentIndex < 0) {
                this.preCurrentIndex = 0;
            }
            if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                this.preCurrentIndex = this.adapter.getItemsCount() - 1;
            }
        }
        float f3 = this.totalScrollY % this.itemHeight;
        DividerType dividerType = this.dividerType;
        if (dividerType == DividerType.WRAP) {
            float f4 = (TextUtils.isEmpty(this.label) ? (this.measuredWidth - this.maxTextWidth) / 2 : (this.measuredWidth - this.maxTextWidth) / 4) - 12;
            float f5 = f4 <= 0.0f ? 10.0f : f4;
            float f6 = this.measuredWidth - f5;
            float f7 = this.firstLineY;
            float f8 = f5;
            canvas.drawLine(f8, f7, f6, f7, this.paintIndicator);
            float f9 = this.secondLineY;
            canvas.drawLine(f8, f9, f6, f9, this.paintIndicator);
        } else if (dividerType == DividerType.CIRCLE) {
            this.paintIndicator.setStyle(Paint.Style.STROKE);
            this.paintIndicator.setStrokeWidth(this.dividerWidth);
            float f10 = (TextUtils.isEmpty(this.label) ? (this.measuredWidth - this.maxTextWidth) / 2.0f : (this.measuredWidth - this.maxTextWidth) / 4.0f) - 12.0f;
            float f11 = f10 > 0.0f ? f10 : 10.0f;
            canvas.drawCircle(this.measuredWidth / 2.0f, this.measuredHeight / 2.0f, Math.max((this.measuredWidth - f11) - f11, this.itemHeight) / 1.8f, this.paintIndicator);
        } else {
            float f12 = this.firstLineY;
            canvas.drawLine(0.0f, f12, this.measuredWidth, f12, this.paintIndicator);
            float f13 = this.secondLineY;
            canvas.drawLine(0.0f, f13, this.measuredWidth, f13, this.paintIndicator);
        }
        if (!TextUtils.isEmpty(this.label) && this.isCenterLabel) {
            canvas.drawText(this.label, (this.measuredWidth - getTextWidth(this.paintCenterText, this.label)) - this.CENTER_CONTENT_OFFSET, this.centerY, this.paintCenterText);
        }
        int i2 = 0;
        while (true) {
            int i3 = this.itemsVisible;
            if (i2 >= i3) {
                return;
            }
            int i4 = this.preCurrentIndex - ((i3 / 2) - i2);
            if (this.isLoop) {
                item = this.adapter.getItem(getLoopMappingIndex(i4));
            } else {
                item = "";
                if (i4 >= 0 && i4 <= this.adapter.getItemsCount() - 1) {
                    item = this.adapter.getItem(i4);
                }
            }
            canvas.save();
            double d3 = ((this.itemHeight * i2) - f3) / this.radius;
            float f14 = (float) (90.0d - ((d3 / 3.141592653589793d) * 180.0d));
            if (f14 > 90.0f || f14 < -90.0f) {
                z2 = z3;
                f2 = f3;
                canvas.restore();
            } else {
                if (this.isCenterLabel || TextUtils.isEmpty(this.label) || TextUtils.isEmpty(getContentText(item))) {
                    contentText = getContentText(item);
                } else {
                    contentText = getContentText(item) + this.label;
                }
                float fPow = (float) Math.pow(Math.abs(f14) / 90.0f, 2.2d);
                reMeasureTextSize(contentText);
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                f2 = f3;
                float fCos = (float) ((this.radius - (Math.cos(d3) * this.radius)) - ((Math.sin(d3) * this.maxTextHeight) / 2.0d));
                canvas.translate(0.0f, fCos);
                float f15 = this.firstLineY;
                if (fCos > f15 || this.maxTextHeight + fCos < f15) {
                    float f16 = this.secondLineY;
                    if (fCos > f16 || this.maxTextHeight + fCos < f16) {
                        if (fCos >= f15) {
                            int i5 = this.maxTextHeight;
                            if (i5 + fCos <= f16) {
                                canvas.drawText(contentText, this.drawCenterContentStart, i5 - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                                this.selectedItem = this.preCurrentIndex - ((this.itemsVisible / 2) - i2);
                            }
                        }
                        canvas.save();
                        z2 = false;
                        canvas.clipRect(0, 0, this.measuredWidth, (int) this.itemHeight);
                        canvas.scale(1.0f, ((float) Math.sin(d3)) * SCALE_CONTENT);
                        setOutPaintStyle(fPow, f14);
                        canvas.drawText(contentText, this.drawOutContentStart + (this.textXOffset * fPow), this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                        canvas.restore();
                        this.paintCenterText.setTextSize(this.textSize);
                    } else {
                        canvas.save();
                        canvas.clipRect(0.0f, 0.0f, this.measuredWidth, this.secondLineY - fCos);
                        canvas.scale(1.0f, ((float) Math.sin(d3)) * 1.0f);
                        canvas.drawText(contentText, this.drawCenterContentStart, this.maxTextHeight - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0.0f, this.secondLineY - fCos, this.measuredWidth, (int) this.itemHeight);
                        canvas.scale(1.0f, ((float) Math.sin(d3)) * SCALE_CONTENT);
                        setOutPaintStyle(fPow, f14);
                        canvas.drawText(contentText, this.drawOutContentStart, this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                    }
                } else {
                    canvas.save();
                    canvas.clipRect(0.0f, 0.0f, this.measuredWidth, this.firstLineY - fCos);
                    canvas.scale(1.0f, ((float) Math.sin(d3)) * SCALE_CONTENT);
                    setOutPaintStyle(fPow, f14);
                    canvas.drawText(contentText, this.drawOutContentStart, this.maxTextHeight, this.paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0.0f, this.firstLineY - fCos, this.measuredWidth, (int) this.itemHeight);
                    canvas.scale(1.0f, ((float) Math.sin(d3)) * 1.0f);
                    canvas.drawText(contentText, this.drawCenterContentStart, this.maxTextHeight - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                    canvas.restore();
                }
                z2 = false;
                canvas.restore();
                this.paintCenterText.setTextSize(this.textSize);
            }
            i2++;
            z3 = z2;
            f3 = f2;
        }
    }

    public final void onItemSelected() {
        if (this.onItemSelectedListener != null) {
            postDelayed(new Runnable() { // from class: com.contrarywind.view.WheelView.1
                @Override // java.lang.Runnable
                public void run() {
                    WheelView.this.onItemSelectedListener.onItemSelected(WheelView.this.getCurrentItem());
                }
            }, 200L);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        this.widthMeasureSpec = i2;
        reMeasure();
        setMeasuredDimension(this.measuredWidth, this.measuredHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = this.gestureDetector.onTouchEvent(motionEvent);
        float f2 = (-this.initPosition) * this.itemHeight;
        float itemsCount = ((this.adapter.getItemsCount() - 1) - this.initPosition) * this.itemHeight;
        int action = motionEvent.getAction();
        boolean z2 = false;
        if (action == 0) {
            this.startTime = System.currentTimeMillis();
            cancelFuture();
            this.previousY = motionEvent.getRawY();
        } else if (action == 2) {
            float rawY = this.previousY - motionEvent.getRawY();
            this.previousY = motionEvent.getRawY();
            float f3 = this.totalScrollY + rawY;
            this.totalScrollY = f3;
            if (!this.isLoop) {
                float f4 = this.itemHeight;
                if ((f3 - (f4 * 0.25f) < f2 && rawY < 0.0f) || ((f4 * 0.25f) + f3 > itemsCount && rawY > 0.0f)) {
                    this.totalScrollY = f3 - rawY;
                    z2 = true;
                }
            }
        } else if (!zOnTouchEvent) {
            float y2 = motionEvent.getY();
            int i2 = this.radius;
            double dAcos = Math.acos((i2 - y2) / i2) * this.radius;
            float f5 = this.itemHeight;
            this.mOffset = (int) (((((int) ((dAcos + (f5 / 2.0f)) / f5)) - (this.itemsVisible / 2)) * f5) - (((this.totalScrollY % f5) + f5) % f5));
            if (System.currentTimeMillis() - this.startTime > 120) {
                smoothScroll(ACTION.DAGGLE);
            } else {
                smoothScroll(ACTION.CLICK);
            }
        }
        if (!z2 && motionEvent.getAction() != 0) {
            invalidate();
        }
        return true;
    }

    public final void scrollBy(float f2) {
        cancelFuture();
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, f2), 0L, 5L, TimeUnit.MILLISECONDS);
    }

    public final void setAdapter(WheelAdapter wheelAdapter) {
        this.adapter = wheelAdapter;
        reMeasure();
        invalidate();
    }

    public void setAlphaGradient(boolean z2) {
        this.isAlphaGradient = z2;
    }

    public final void setCurrentItem(int i2) {
        this.selectedItem = i2;
        this.initPosition = i2;
        this.totalScrollY = 0.0f;
        invalidate();
    }

    public final void setCyclic(boolean z2) {
        this.isLoop = z2;
    }

    public void setDividerColor(int i2) {
        this.dividerColor = i2;
        this.paintIndicator.setColor(i2);
    }

    public void setDividerType(DividerType dividerType) {
        this.dividerType = dividerType;
    }

    public void setDividerWidth(int i2) {
        this.dividerWidth = i2;
        this.paintIndicator.setStrokeWidth(i2);
    }

    public void setGravity(int i2) {
        this.mGravity = i2;
    }

    public void setIsOptions(boolean z2) {
        this.isOptions = z2;
    }

    public void setItemsVisibleCount(int i2) {
        if (i2 % 2 == 0) {
            i2++;
        }
        this.itemsVisible = i2 + 2;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setLineSpacingMultiplier(float f2) {
        if (f2 != 0.0f) {
            this.lineSpacingMultiplier = f2;
            judgeLineSpace();
        }
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void setTextColorCenter(int i2) {
        this.textColorCenter = i2;
        this.paintCenterText.setColor(i2);
    }

    public void setTextColorOut(int i2) {
        this.textColorOut = i2;
        this.paintOuterText.setColor(i2);
    }

    public final void setTextSize(float f2) {
        if (f2 > 0.0f) {
            int i2 = (int) (this.context.getResources().getDisplayMetrics().density * f2);
            this.textSize = i2;
            this.paintOuterText.setTextSize(i2);
            this.paintCenterText.setTextSize(this.textSize);
        }
    }

    public void setTextXOffset(int i2) {
        this.textXOffset = i2;
        if (i2 != 0) {
            this.paintCenterText.setTextScaleX(1.0f);
        }
    }

    public void setTotalScrollY(float f2) {
        this.totalScrollY = f2;
    }

    public final void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        this.paintOuterText.setTypeface(typeface);
        this.paintCenterText.setTypeface(this.typeface);
    }

    public void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            float f2 = this.totalScrollY;
            float f3 = this.itemHeight;
            int i2 = (int) (((f2 % f3) + f3) % f3);
            this.mOffset = i2;
            if (i2 > f3 / 2.0f) {
                this.mOffset = (int) (f3 - i2);
            } else {
                this.mOffset = -i2;
            }
        }
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, this.mOffset), 0L, 10L, TimeUnit.MILLISECONDS);
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isOptions = false;
        this.isCenterLabel = true;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.typeface = Typeface.MONOSPACE;
        this.lineSpacingMultiplier = 1.6f;
        this.itemsVisible = 11;
        this.mOffset = 0;
        this.previousY = 0.0f;
        this.startTime = 0L;
        this.mGravity = 17;
        this.drawCenterContentStart = 0;
        this.drawOutContentStart = 0;
        this.isAlphaGradient = false;
        this.textSize = getResources().getDimensionPixelSize(R.dimen.pickerview_textsize);
        float f2 = getResources().getDisplayMetrics().density;
        if (f2 < 1.0f) {
            this.CENTER_CONTENT_OFFSET = 2.4f;
        } else if (1.0f <= f2 && f2 < 2.0f) {
            this.CENTER_CONTENT_OFFSET = 4.0f;
        } else if (2.0f <= f2 && f2 < 3.0f) {
            this.CENTER_CONTENT_OFFSET = 6.0f;
        } else if (f2 >= 3.0f) {
            this.CENTER_CONTENT_OFFSET = f2 * 2.5f;
        }
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.pickerview, 0, 0);
            this.mGravity = typedArrayObtainStyledAttributes.getInt(R.styleable.pickerview_wheelview_gravity, 17);
            this.textColorOut = typedArrayObtainStyledAttributes.getColor(R.styleable.pickerview_wheelview_textColorOut, -5723992);
            this.textColorCenter = typedArrayObtainStyledAttributes.getColor(R.styleable.pickerview_wheelview_textColorCenter, -14013910);
            this.dividerColor = typedArrayObtainStyledAttributes.getColor(R.styleable.pickerview_wheelview_dividerColor, -2763307);
            this.dividerWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.pickerview_wheelview_dividerWidth, 2);
            this.textSize = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.pickerview_wheelview_textSize, this.textSize);
            this.lineSpacingMultiplier = typedArrayObtainStyledAttributes.getFloat(R.styleable.pickerview_wheelview_lineSpacingMultiplier, this.lineSpacingMultiplier);
            typedArrayObtainStyledAttributes.recycle();
        }
        judgeLineSpace();
        initLoopView(context);
    }
}
