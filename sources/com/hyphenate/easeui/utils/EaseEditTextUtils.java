package com.hyphenate.easeui.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.hyphenate.easeui.R;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EaseEditTextUtils {
    public static void changePwdDrawableRight(final EditText editText, final Drawable drawable, final Drawable drawable2, final Drawable drawable3, final Drawable drawable4, final Drawable drawable5) {
        final boolean[] zArr = {false};
        editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.hyphenate.easeui.utils.c
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return EaseEditTextUtils.lambda$changePwdDrawableRight$0(editText, zArr, drawable3, drawable4, drawable, drawable5, drawable2, view, motionEvent);
            }
        });
    }

    public static void clearEditTextListener(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() { // from class: com.hyphenate.easeui.utils.b
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return EaseEditTextUtils.lambda$clearEditTextListener$1(editText, view, motionEvent);
            }
        });
    }

    public static String ellipsizeMiddleString(TextView textView, String str, int i2, int i3) {
        textView.setMaxLines(i2);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        TextPaint paint = textView.getPaint();
        if (!TextUtils.isEmpty(str) && i3 > 0) {
            float f2 = i3;
            if (paint.measureText(str) >= f2) {
                int i4 = 0;
                int iBreakText = 0;
                for (int i5 = 0; i5 < i2; i5++) {
                    if (i4 < str.length()) {
                        iBreakText += paint.breakText(str, i4, str.length(), true, f2, null);
                        i4 = iBreakText - 1;
                    }
                }
                if (str.length() < iBreakText) {
                    return str;
                }
                try {
                    int lineEnd = textView.getLayout().getLineEnd(i2 - 1);
                    if (str.length() < lineEnd || !str.contains(StrPool.DOT)) {
                        return str;
                    }
                    String str2 = "..." + str.substring(str.lastIndexOf(StrPool.DOT) - 5);
                    float fMeasureText = paint.measureText(str2);
                    String string = new StringBuilder(str.substring(0, lineEnd)).reverse().toString();
                    String str3 = str.substring(0, lineEnd - getTakeUpCount(paint, string, paint.breakText(string, 0, string.length(), true, fMeasureText, null), fMeasureText)) + str2;
                    EMLog.i("EaseEditTextUtils", "last str = " + str3);
                    return str3;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return str;
    }

    public static String ellipsizeString(TextView textView, String str, String str2, int i2) {
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        TextPaint paint = textView.getPaint();
        float f2 = i2;
        if (paint.measureText(str) < f2) {
            return str;
        }
        int iBreakText = paint.breakText(str, 0, str.length(), true, f2, null);
        int iIndexOf = str.indexOf(str2);
        if (str2.length() + iIndexOf < iBreakText) {
            return str;
        }
        if (str.length() - iIndexOf <= iBreakText - 3) {
            return "..." + str.substring(str.length() - iBreakText).substring(3);
        }
        int length = (iBreakText - str2.length()) / 2;
        String str3 = "..." + str.substring(iIndexOf - length, iIndexOf + str2.length() + length).substring(3);
        return str3.substring(0, str3.length() - 3) + "...";
    }

    private static int getTakeUpCount(Paint paint, String str, int i2, float f2) {
        return paint.measureText(str.substring(0, i2)) <= f2 ? getTakeUpCount(paint, str, i2 + 1, f2) : i2 + 1;
    }

    public static SpannableStringBuilder highLightKeyword(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !str.contains(str2)) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.em_color_brand)), str.indexOf(str2), str.indexOf(str2) + str2.length(), 33);
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$changePwdDrawableRight$0(EditText editText, boolean[] zArr, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, View view, MotionEvent motionEvent) {
        if (editText.getCompoundDrawables()[2] == null || motionEvent.getAction() != 1 || motionEvent.getX() <= (editText.getWidth() - editText.getPaddingRight()) - r11.getIntrinsicWidth()) {
            return false;
        }
        if (zArr[0]) {
            editText.setInputType(129);
            editText.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            zArr[0] = false;
        } else {
            editText.setInputType(1);
            editText.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable5, drawable4);
            zArr[0] = true;
        }
        editText.setSelection(editText.getText().toString().length());
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$clearEditTextListener$1(EditText editText, View view, MotionEvent motionEvent) {
        if (editText.getCompoundDrawables()[2] == null || motionEvent.getAction() != 1 || motionEvent.getX() <= (editText.getWidth() - editText.getPaddingRight()) - r5.getIntrinsicWidth()) {
            return false;
        }
        editText.setText("");
        return true;
    }

    public static void showRightDrawable(EditText editText, Drawable drawable) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            drawable = null;
        }
        editText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
    }
}
