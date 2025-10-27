package com.huawei.hms.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.exoplayer2.text.ttml.TtmlNode;

/* loaded from: classes4.dex */
public abstract class ResourceLoaderUtil {

    /* renamed from: a, reason: collision with root package name */
    public static Context f8162a;

    /* renamed from: b, reason: collision with root package name */
    public static String f8163b;

    public static int getAnimId(String str) {
        return f8162a.getResources().getIdentifier(str, "anim", f8163b);
    }

    public static int getColorId(String str) {
        return f8162a.getResources().getIdentifier(str, "color", f8163b);
    }

    public static Drawable getDrawable(String str) {
        return f8162a.getResources().getDrawable(getDrawableId(str));
    }

    public static int getDrawableId(String str) {
        return f8162a.getResources().getIdentifier(str, "drawable", f8163b);
    }

    public static int getIdId(String str) {
        return f8162a.getResources().getIdentifier(str, "id", f8163b);
    }

    public static int getLayoutId(String str) {
        return f8162a.getResources().getIdentifier(str, TtmlNode.TAG_LAYOUT, f8163b);
    }

    public static String getString(String str) {
        return f8162a.getResources().getString(getStringId(str));
    }

    public static int getStringId(String str) {
        return f8162a.getResources().getIdentifier(str, TypedValues.Custom.S_STRING, f8163b);
    }

    public static int getStyleId(String str) {
        return f8162a.getResources().getIdentifier(str, TtmlNode.TAG_STYLE, f8163b);
    }

    public static Context getmContext() {
        return f8162a;
    }

    public static void setmContext(Context context) {
        f8162a = context;
        f8163b = context.getPackageName();
    }

    public static String getString(String str, Object... objArr) {
        return f8162a.getResources().getString(getStringId(str), objArr);
    }
}
