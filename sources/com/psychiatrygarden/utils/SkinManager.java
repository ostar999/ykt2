package com.psychiatrygarden.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.webkit.WebView;
import androidx.annotation.AttrRes;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SkinManager {
    public static final int THEME_DAY = 0;
    public static int THEME_ID_Day = 0;
    public static int THEME_ID_Night = 0;
    public static final int THEME_NIGHT = 1;
    private static String jsStyle = "javascript:(function(){\n\t\t   document.body.style.backgroundColor=\"%s\";\n\t\t    document.body.style.color=\"%s\";\n\t\t\tvar as = document.getElementsByTagName(\"a\");\n\t\tfor(var i=0;i<as.length;i++){\n\t\t\tas[i].style.color = \"%s\";\n\t\t}\n\t\tvar divs = document.getElementsByTagName(\"p\");\n\t\tfor(var i=0;i<divs.length;i++){\n\t\t\tdivs[i].style.color = \"%s\";\n\t\t}\n\t\tvar divclass = document.getElementsByClassName(\"author_info\");\n\t\tfor(var i=0;i<divclass.length;i++){\n\t\t\tdivclass[i].style.backgroundColor=\"%s\"; \n\t\t}\n\t\tvar divclass2 = document.getElementsByClassName(\"psm\");\n\t\tfor(var i=0;i<divclass2.length;i++){\n\t\t\tdivclass2[i].style.backgroundColor=\"%s\"; \n\t\t}\n\t\t\tvar h2class = document.getElementsByTagName(\"h2\");\n\t\tfor(var i=0;i<h2class.length;i++){\nh2class[i].style.color=\"%s\";\n\t\t}\n\t\t\tvar h2span = document.getElementsByTagName(\"span\");\n\t\tfor(var i=0;i<h2span.length;i++){\n\t\th2span[i].style.color=\"%s\";}\t\t})()";
    private static ThemeInterface mThemeInterface;

    public static void changeSkin(Activity activity, int theme) {
        LogUtils.e("isBySystem", "初始化主题===>" + getCurrentSkinType(activity) + ";传进来的theme:" + theme);
        setSkinType(activity.getApplicationContext(), theme);
        mThemeInterface.mChageThemeView(theme);
    }

    public static int getCurrentSkinTheme(Context context) {
        return getCurrentSkinType(context) != 1 ? THEME_ID_Day : THEME_ID_Night;
    }

    public static int getCurrentSkinType(Context context) {
        return getSharePreSkin(context, 0);
    }

    private static int getSharePreSkin(Context context, int defValue) {
        return SharePreferencesUtils.readIntConfig(CommonParameter.SkinMananer, context, defValue);
    }

    public static int getThemeColor(Context context, int attrInt) {
        return context.getTheme().obtainStyledAttributes(new int[]{attrInt}).getColor(0, context.getColor(R.color.first_txt_color));
    }

    public static Drawable getThemeDrawable(Context context, @AttrRes int attrInt) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{attrInt});
        return typedArrayObtainStyledAttributes.getDrawable(0) == null ? context.getDrawable(R.mipmap.ic_order_default) : typedArrayObtainStyledAttributes.getDrawable(0);
    }

    public static void onActivityCreateSetSkin(Activity activity) {
        activity.setTheme(getCurrentSkinTheme(activity.getApplicationContext()));
    }

    private static void saveSharePreSkin(Context context, int value) {
        SharePreferencesUtils.writeIntConfig(CommonParameter.SkinMananer, value, context);
    }

    public static void setColorWebView(WebView webView, String backgroudColor, String fontColor, String urlColor) {
        if (webView != null) {
            webView.setBackgroundColor(0);
            if (getCurrentSkinType(webView.getContext().getApplicationContext()) == 1) {
                webView.loadUrl(String.format(jsStyle, backgroudColor, fontColor, urlColor, fontColor, urlColor, backgroudColor, fontColor, fontColor));
            }
        }
    }

    private static void setSkinType(Context context, int theme) {
        saveSharePreSkin(context, theme);
    }

    public static void setThemeID(int DayTheme, int NightTheme) {
        THEME_ID_Day = DayTheme;
        THEME_ID_Night = NightTheme;
    }

    public static void setmThemeInterface(ThemeInterface mThemeInterface2) {
        mThemeInterface = mThemeInterface2;
    }

    public static void changeSkin(Activity activity) {
        if (getCurrentSkinType(activity.getApplicationContext()) == 0) {
            changeSkin(activity, 1);
        } else {
            changeSkin(activity, 0);
        }
    }
}
