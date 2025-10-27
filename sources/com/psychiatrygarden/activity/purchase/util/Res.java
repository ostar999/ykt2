package com.psychiatrygarden.activity.purchase.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.android.exoplayer2.text.ttml.TtmlNode;

/* loaded from: classes5.dex */
public class Res {
    private static String pkgName;
    private static Resources resources;

    public static int getAnimID(String animName) {
        return resources.getIdentifier(animName, "anim", pkgName);
    }

    public static int getAttrID(String attrName) {
        return resources.getIdentifier(attrName, "attr", pkgName);
    }

    public static int getColor(String colorName) {
        return resources.getColor(getColorID(colorName));
    }

    public static int getColorID(String colorName) {
        return resources.getIdentifier(colorName, "color", pkgName);
    }

    public static int getDimenID(String dimenName) {
        return resources.getIdentifier(dimenName, "dimen", pkgName);
    }

    public static Drawable getDrawable(String drawName) {
        return resources.getDrawable(getDrawableID(drawName));
    }

    public static int getDrawableID(String drawName) {
        return resources.getIdentifier(drawName, "drawable", pkgName);
    }

    public static int[] getInteger(String strName) {
        Resources resources2 = resources;
        return resources2.getIntArray(resources2.getIdentifier(strName, "array", pkgName));
    }

    public static int getLayoutID(String layoutName) {
        return resources.getIdentifier(layoutName, TtmlNode.TAG_LAYOUT, pkgName);
    }

    public static int getRawID(String rawName) {
        return resources.getIdentifier(rawName, "raw", pkgName);
    }

    public static String getString(String strName) {
        return resources.getString(getStringID(strName));
    }

    public static int getStringID(String strName) {
        return resources.getIdentifier(strName, TypedValues.Custom.S_STRING, pkgName);
    }

    public static int getStyleID(String styleName) {
        return resources.getIdentifier(styleName, TtmlNode.TAG_STYLE, pkgName);
    }

    public static int getWidgetID(String widgetName) {
        return resources.getIdentifier(widgetName, "id", pkgName);
    }

    public static XmlResourceParser getXml(String xmlName) {
        return resources.getXml(getXmlID(xmlName));
    }

    public static int getXmlID(String xmlName) {
        return resources.getIdentifier(xmlName, AliyunVodHttpCommon.Format.FORMAT_XML, pkgName);
    }

    public static void init(Context context) {
        pkgName = context.getPackageName();
        resources = context.getResources();
    }
}
