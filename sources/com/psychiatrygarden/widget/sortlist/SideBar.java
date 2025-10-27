package com.psychiatrygarden.widget.sortlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.rtmp.sharp.jni.QLog;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SideBar extends View {

    /* renamed from: b, reason: collision with root package name */
    public static String[] f16913b = {"↑", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", QLog.TAG_REPORTLEVEL_DEVELOPER, "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "W", "X", "Y", "Z", DictionaryFactory.SHARP};
    private int choose;
    private TextView mTextDialog;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private Paint paint;

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s2);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.choose = -1;
        this.paint = new Paint();
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y2 = event.getY();
        int i2 = this.choose;
        OnTouchingLetterChangedListener onTouchingLetterChangedListener = this.onTouchingLetterChangedListener;
        int height = (int) ((y2 / getHeight()) * f16913b.length);
        if (action == 1) {
            setBackground(new ColorDrawable(0));
            this.choose = -1;
            invalidate();
            TextView textView = this.mTextDialog;
            if (textView != null) {
                textView.setVisibility(4);
            }
        } else {
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                setBackgroundResource(R.drawable.sidebar_background);
            } else {
                setBackgroundResource(R.drawable.sidebar_background_night);
            }
            if (i2 != height && height >= 0) {
                String[] strArr = f16913b;
                if (height < strArr.length) {
                    if (onTouchingLetterChangedListener != null) {
                        onTouchingLetterChangedListener.onTouchingLetterChanged(strArr[height]);
                    }
                    TextView textView2 = this.mTextDialog;
                    if (textView2 != null) {
                        textView2.setText(f16913b[height]);
                        this.mTextDialog.setVisibility(0);
                    }
                    this.choose = height;
                    invalidate();
                }
            }
        }
        return true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int length = height / f16913b.length;
        for (int i2 = 0; i2 < f16913b.length; i2++) {
            this.paint.setColor(Color.rgb(33, 65, 98));
            this.paint.setTypeface(Typeface.DEFAULT);
            this.paint.setAntiAlias(true);
            this.paint.setTextSize(CommonUtil.dip2px(getContext(), 12.0f));
            if (i2 == this.choose) {
                this.paint.setColor(Color.parseColor("#3399ff"));
                this.paint.setFakeBoldText(true);
            }
            canvas.drawText(f16913b[i2], (width / 2) - (this.paint.measureText(f16913b[i2]) / 2.0f), (length * i2) + length, this.paint);
            this.paint.reset();
        }
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.choose = -1;
        this.paint = new Paint();
    }

    public SideBar(Context context) {
        super(context);
        this.choose = -1;
        this.paint = new Paint();
    }
}
