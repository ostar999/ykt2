package com.catchpig.mvvm.ext;

import cn.hutool.core.text.StrPool;
import com.luck.picture.lib.config.PictureMimeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0003\u001a\f\u0010\u0004\u001a\u00020\u0005*\u0004\u0018\u00010\u0001\u001a\f\u0010\u0006\u001a\u00020\u0005*\u0004\u0018\u00010\u0001\u001a\f\u0010\u0007\u001a\u00020\u0005*\u0004\u0018\u00010\u0001\u001a\u0016\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\t*\u00020\u0001¨\u0006\n"}, d2 = {"get2EffectiveNum", "", "", "(Ljava/lang/Float;)Ljava/lang/String;", "isJsonArray", "", "isJsonObject", "isPicture", "parseQueryParams", "", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStringExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringExt.kt\ncom/catchpig/mvvm/ext/StringExtKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,72:1\n1855#2,2:73\n*S KotlinDebug\n*F\n+ 1 StringExt.kt\ncom/catchpig/mvvm/ext/StringExtKt\n*L\n16#1:73,2\n*E\n"})
/* loaded from: classes2.dex */
public final class StringExtKt {
    @NotNull
    public static final String get2EffectiveNum(@Nullable Float f2) {
        if (Intrinsics.areEqual(0.0f, f2)) {
            return "0";
        }
        String strValueOf = String.valueOf(f2);
        if (!StringsKt__StringsJVMKt.endsWith$default(strValueOf, ".0", false, 2, null) && !StringsKt__StringsJVMKt.endsWith$default(strValueOf, ".00", false, 2, null)) {
            return strValueOf;
        }
        String strSubstring = strValueOf.substring(0, StringsKt__StringsKt.indexOf$default((CharSequence) strValueOf, StrPool.DOT, 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public static final boolean isJsonArray(@Nullable String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            new JSONArray(str);
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public static final boolean isJsonObject(@Nullable String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public static final boolean isPicture(@Nullable String str) {
        if ((str == null || str.length() == 0) || StringsKt__StringsKt.indexOf$default((CharSequence) str, StrPool.DOT, 0, false, 6, (Object) null) < 0) {
            return false;
        }
        String strSubstring = str.substring(StringsKt__StringsKt.lastIndexOf$default((CharSequence) str, StrPool.DOT, 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return Intrinsics.areEqual(PictureMimeType.PNG, strSubstring) || Intrinsics.areEqual(".jpg", strSubstring) || Intrinsics.areEqual(PictureMimeType.GIF, strSubstring) || Intrinsics.areEqual(".jpeg", strSubstring);
    }

    @NotNull
    public static final Map<String, String> parseQueryParams(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default((CharSequence) str, "?", 0, false, 6, (Object) null) + 1;
        HashMap map = new HashMap();
        if (iLastIndexOf$default > 0) {
            String strSubstring = str.substring(iLastIndexOf$default, str.length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            for (String str2 : StringsKt__StringsKt.split$default((CharSequence) strSubstring, new String[]{"&"}, false, 0, 6, (Object) null)) {
                if (StringsKt__StringsKt.contains$default((CharSequence) str2, (CharSequence) "=", false, 2, (Object) null)) {
                    List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) str2, new String[]{"="}, false, 0, 6, (Object) null);
                    if (listSplit$default.size() > 1) {
                        map.put(listSplit$default.get(0), listSplit$default.get(1));
                    }
                }
            }
        }
        return map;
    }
}
