package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.hyphenate.easeui.R;
import com.tencent.rtmp.sharp.jni.QLog;

/* loaded from: classes4.dex */
public class EaseSidebar extends View {
    private static final String DEFAULT_COLOR = "#8C8C8C";
    private static final float DEFAULT_TEXT_SIZE = 10.0f;
    private float ItemHeight;
    private Context context;
    private int mBgColor;
    private int mHeight;
    private OnTouchEventListener mListener;
    private float mTextCoefficient;
    private int mTextColor;
    private float mTextSize;
    private int mWidth;
    private Paint paint;
    private String[] sections;
    private String topText;

    public interface OnTouchEventListener {
        void onActionDown(MotionEvent motionEvent, String str);

        void onActionMove(MotionEvent motionEvent, String str);

        void onActionUp(MotionEvent motionEvent);
    }

    public EaseSidebar(Context context) {
        this(context, null);
    }

    private void checkTextSize() {
        Paint paint = this.paint;
        if (paint != null) {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float f2 = fontMetrics.bottom - fontMetrics.top;
            String[] strArr = this.sections;
            float length = strArr.length * f2;
            int i2 = this.mHeight;
            if (length <= i2) {
                this.paint.setTextSize(this.mTextSize);
                return;
            }
            this.mTextCoefficient = i2 / (strArr.length * f2);
            Paint paint2 = this.paint;
            paint2.setTextSize(paint2.getTextSize() * this.mTextCoefficient);
        }
    }

    private void init() {
        if (this.sections.length > 27 && !TextUtils.isEmpty(this.topText)) {
            this.sections[0] = this.topText;
        }
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(this.mTextColor);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.paint.setTextSize(this.mTextSize);
    }

    private void initAttrs(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = this.context.obtainStyledAttributes(attributeSet, R.styleable.EaseSidebar);
            int i2 = R.styleable.EaseSidebar_ease_side_bar_top_text;
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(i2, -1);
            if (resourceId != -1) {
                this.topText = this.context.getResources().getString(resourceId);
            } else {
                this.topText = typedArrayObtainStyledAttributes.getString(i2);
            }
            int i3 = R.styleable.EaseSidebar_ease_side_bar_text_color;
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(i3, -1);
            if (resourceId2 != -1) {
                this.mTextColor = ContextCompat.getColor(this.context, resourceId2);
            } else {
                this.mTextColor = typedArrayObtainStyledAttributes.getColor(i3, Color.parseColor(DEFAULT_COLOR));
            }
            this.mTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseSidebar_ease_side_bar_text_size, DEFAULT_TEXT_SIZE);
            int i4 = R.styleable.EaseSidebar_ease_side_bar_background;
            if (typedArrayObtainStyledAttributes.getResourceId(i4, -1) != -1) {
                this.mBgColor = ContextCompat.getColor(this.context, resourceId2);
            } else {
                this.mBgColor = typedArrayObtainStyledAttributes.getColor(i4, 0);
            }
            int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.EaseSidebar_ease_side_bar_head_arrays, -1);
            if (resourceId3 != -1) {
                this.sections = getResources().getStringArray(resourceId3);
            } else {
                this.sections = new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", QLog.TAG_REPORTLEVEL_DEVELOPER, "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "W", "X", "Y", "Z", DictionaryFactory.SHARP};
            }
        }
    }

    private int sectionForPoint(float f2) {
        int i2 = (int) (f2 / this.ItemHeight);
        if (i2 < 0) {
            i2 = 0;
        }
        return i2 > this.sections.length + (-1) ? r0.length - 1 : i2;
    }

    public void drawBackground(@ColorRes int i2) {
        this.mBgColor = ContextCompat.getColor(this.context, i2);
        postInvalidate();
    }

    public void drawBackgroundDrawable(@DrawableRes int i2) {
        setBackground(ContextCompat.getDrawable(this.context, i2));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i2 = this.mBgColor;
        if (i2 != 0) {
            canvas.drawColor(i2);
        }
        float width = getWidth() / 2;
        int height = getHeight();
        String[] strArr = this.sections;
        this.ItemHeight = height / strArr.length;
        int length = strArr.length;
        while (true) {
            length--;
            if (length <= -1) {
                return;
            } else {
                canvas.drawText(this.sections[length], width, this.ItemHeight * (length + 1), this.paint);
            }
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.mWidth = i2;
        this.mHeight = i3;
        checkTextSize();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        String str = this.sections[sectionForPoint(motionEvent.getY())];
        int action = motionEvent.getAction();
        if (action == 0) {
            OnTouchEventListener onTouchEventListener = this.mListener;
            if (onTouchEventListener != null) {
                onTouchEventListener.onActionDown(motionEvent, str);
            }
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                OnTouchEventListener onTouchEventListener2 = this.mListener;
                if (onTouchEventListener2 != null) {
                    onTouchEventListener2.onActionMove(motionEvent, str);
                }
                return true;
            }
            if (action != 3) {
                return super.onTouchEvent(motionEvent);
            }
        }
        OnTouchEventListener onTouchEventListener3 = this.mListener;
        if (onTouchEventListener3 != null) {
            onTouchEventListener3.onActionUp(motionEvent);
        }
        return true;
    }

    public void setOnTouchEventListener(OnTouchEventListener onTouchEventListener) {
        this.mListener = onTouchEventListener;
    }

    public EaseSidebar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void drawBackgroundDrawable(Drawable drawable) {
        setBackground(drawable);
    }

    public EaseSidebar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.sections = new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", QLog.TAG_REPORTLEVEL_DEVELOPER, "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "W", "X", "Y", "Z", DictionaryFactory.SHARP};
        this.mTextCoefficient = 1.0f;
        this.context = context;
        initAttrs(attributeSet);
        init();
    }
}
