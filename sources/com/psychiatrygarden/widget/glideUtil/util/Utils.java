package com.psychiatrygarden.widget.glideUtil.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.annotation.ColorRes;
import cn.hutool.core.img.ImgUtil;
import java.util.Collection;

/* loaded from: classes6.dex */
public class Utils {
    private static WindowManager windowManager;

    public static int dp2px(Context context, float dp) {
        return (int) ((getDensity(context) * dp) + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager(context).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static String getPathFormat(String path) {
        int iLastIndexOf;
        int i2;
        if (TextUtils.isEmpty(path) || (iLastIndexOf = path.lastIndexOf(46)) <= 0 || (i2 = iLastIndexOf + 1) >= path.length()) {
            return "";
        }
        String strSubstring = path.substring(i2);
        return !TextUtils.isEmpty(strSubstring) ? strSubstring.toLowerCase() : "";
    }

    public static int getSize(Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static Bitmap getTextBitmap(Context context, int width, int height, int radius, String text, int textSize, @ColorRes int bgColor) {
        int iDp2px = dp2px(context, radius);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dp2px(context, width), dp2px(context, height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        RectF rectF = new RectF(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        Paint paint = new Paint(1);
        paint.setColor(context.getResources().getColor(bgColor));
        float f2 = iDp2px;
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, rectF.width(), rectF.height()), f2, f2, paint);
        paint.setColor(-1);
        paint.setTextSize(dp2px(context, textSize));
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        canvas.drawText(text, rectF.centerX(), (((rectF.bottom + rectF.top) - fontMetricsInt.bottom) - fontMetricsInt.top) / 2.0f, paint);
        return bitmapCreateBitmap;
    }

    public static Drawable getTextDrawable(Context context, int width, int height, int radius, String text, int textSize, @ColorRes int bgColor) {
        return new BitmapDrawable(getTextBitmap(context, width, height, radius, text, textSize, bgColor));
    }

    public static int getWindowHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    private static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService("window");
        }
        return windowManager;
    }

    public static int getWindowWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isGif(String url) {
        return ImgUtil.IMAGE_TYPE_GIF.equals(getPathFormat(url));
    }

    public static int px2dp(Context context, float px) {
        return (int) ((px / getDensity(context)) + 0.5f);
    }

    public static int px2sp(Context context, float px) {
        return (int) ((px / getFontDensity(context)) + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        return (int) ((getFontDensity(context) * sp) + 0.5f);
    }
}
