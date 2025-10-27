package com.blankj.utilcode.util;

import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class ResourceUtils {
    private static final int BUFFER_SIZE = 8192;

    private ResourceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean copyFileFromAssets(String str, String str2) throws IOException {
        try {
            String[] list = Utils.getApp().getAssets().list(str);
            if (list == null || list.length <= 0) {
                return UtilsBridge.writeFileFromIS(str2, Utils.getApp().getAssets().open(str));
            }
            boolean zCopyFileFromAssets = true;
            for (String str3 : list) {
                zCopyFileFromAssets &= copyFileFromAssets(str + "/" + str3, str2 + "/" + str3);
            }
            return zCopyFileFromAssets;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean copyFileFromRaw(@RawRes int i2, String str) {
        return UtilsBridge.writeFileFromIS(str, Utils.getApp().getResources().openRawResource(i2));
    }

    public static int getAnimIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "anim", Utils.getApp().getPackageName());
    }

    public static int getColorIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "color", Utils.getApp().getPackageName());
    }

    public static int getDimenIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "dimen", Utils.getApp().getPackageName());
    }

    public static Drawable getDrawable(@DrawableRes int i2) {
        return ContextCompat.getDrawable(Utils.getApp(), i2);
    }

    public static int getDrawableIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "drawable", Utils.getApp().getPackageName());
    }

    public static int getIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "id", Utils.getApp().getPackageName());
    }

    public static int getLayoutIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, TtmlNode.TAG_LAYOUT, Utils.getApp().getPackageName());
    }

    public static int getMenuIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "menu", Utils.getApp().getPackageName());
    }

    public static int getMipmapIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, "mipmap", Utils.getApp().getPackageName());
    }

    public static int getStringIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, TypedValues.Custom.S_STRING, Utils.getApp().getPackageName());
    }

    public static int getStyleIdByName(String str) {
        return Utils.getApp().getResources().getIdentifier(str, TtmlNode.TAG_STYLE, Utils.getApp().getPackageName());
    }

    public static List<String> readAssets2List(String str) {
        return readAssets2List(str, "");
    }

    public static String readAssets2String(String str) {
        return readAssets2String(str, null);
    }

    public static List<String> readRaw2List(@RawRes int i2) {
        return readRaw2List(i2, "");
    }

    public static String readRaw2String(@RawRes int i2) {
        return readRaw2String(i2, null);
    }

    public static List<String> readAssets2List(String str, String str2) {
        try {
            return UtilsBridge.inputStream2Lines(Utils.getApp().getResources().getAssets().open(str), str2);
        } catch (IOException e2) {
            e2.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static String readAssets2String(String str, String str2) {
        try {
            byte[] bArrInputStream2Bytes = UtilsBridge.inputStream2Bytes(Utils.getApp().getAssets().open(str));
            if (bArrInputStream2Bytes == null) {
                return "";
            }
            if (UtilsBridge.isSpace(str2)) {
                return new String(bArrInputStream2Bytes);
            }
            try {
                return new String(bArrInputStream2Bytes, str2);
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                return "";
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static List<String> readRaw2List(@RawRes int i2, String str) {
        return UtilsBridge.inputStream2Lines(Utils.getApp().getResources().openRawResource(i2), str);
    }

    public static String readRaw2String(@RawRes int i2, String str) {
        byte[] bArrInputStream2Bytes = UtilsBridge.inputStream2Bytes(Utils.getApp().getResources().openRawResource(i2));
        if (bArrInputStream2Bytes == null) {
            return null;
        }
        if (UtilsBridge.isSpace(str)) {
            return new String(bArrInputStream2Bytes);
        }
        try {
            return new String(bArrInputStream2Bytes, str);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
