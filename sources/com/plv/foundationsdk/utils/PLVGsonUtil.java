package com.plv.foundationsdk.utils;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes4.dex */
public class PLVGsonUtil {
    private static final String TAG = "PLVGsonUtil";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Nullable
    public static <T> T fromJson(Class<T> cls, String str) {
        return (T) fromJson(cls, str, true);
    }

    public static <T> String toJson(T t2) {
        return t2 == null ? "" : gson.toJson(t2);
    }

    public static <T> String toJsonSimple(T t2) {
        try {
            String strReplaceAll = gson.toJson(t2).replaceAll(StrPool.CR, "").replaceAll("\n", "").replaceAll(StrPool.TAB, "");
            PLVCommonLog.d(TAG, "toJson===========》：" + strReplaceAll);
            return strReplaceAll;
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return "";
        }
    }

    public static <T> String toSerialJson(T t2) {
        String json = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().enableComplexMapKeySerialization().create().toJson(t2);
        PLVCommonLog.d(TAG, "toJson===========》：" + json);
        return json;
    }

    public static <T> T fromJson(Class<T> cls, String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return (T) gson.fromJson(str, (Class) cls);
        } catch (Exception e2) {
            if (z2) {
                PLVCommonLog.e(TAG, e2.getMessage());
            } else {
                PLVCommonLog.d(TAG, e2.getMessage());
            }
            return null;
        }
    }

    @Nullable
    public static <T> T fromJson(Class<T> cls, JsonElement jsonElement) {
        if (jsonElement == null) {
            return null;
        }
        return (T) gson.fromJson(jsonElement, (Class) cls);
    }

    @Nullable
    public static <T> T fromJson(TypeToken<T> typeToken, String str) {
        try {
            return (T) gson.fromJson(str, typeToken.getType());
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return null;
        }
    }
}
