package com.ykb.ebook.extensions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.Collator;
import android.icu.util.ULocale;
import android.os.Build;
import android.util.TypedValue;
import com.just.agentweb.DefaultWebClient;
import com.ykb.ebook.common.AppPatternKt;
import com.ykb.ebook.common.ReadConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\n\u0010\b\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0002\u001a\f\u0010\n\u001a\u00020\u000b*\u0004\u0018\u00010\u0002\u001a\f\u0010\f\u001a\u00020\u000b*\u0004\u0018\u00010\u0002\u001a\f\u0010\r\u001a\u00020\u000b*\u0004\u0018\u00010\u0002\u001a\u0012\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a3\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0010\"\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0001¢\u0006\u0002\u0010\u0013\u001a\n\u0010\u0014\u001a\u00020\u0002*\u00020\u0015¨\u0006\u0016"}, d2 = {"cnCompare", "", "", "other", "dp", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "formatContent", "hexValue2IntColor", "isAbsUrl", "", "isContentScheme", "isDataUrl", "sp", "splitNotBlank", "", "delimiter", "limit", "(Ljava/lang/String;[Ljava/lang/String;I)[Ljava/lang/String;", "toWanString", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStringExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringExtensions.kt\ncom/ykb/ebook/extensions/StringExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,94:1\n1549#2:95\n1620#2,3:96\n819#2:99\n847#2,2:100\n37#3,2:102\n*S KotlinDebug\n*F\n+ 1 StringExtensions.kt\ncom/ykb/ebook/extensions/StringExtensionsKt\n*L\n20#1:95\n20#1:96,3\n20#1:99\n20#1:100,2\n21#1:102,2\n*E\n"})
/* loaded from: classes7.dex */
public final class StringExtensionsKt {
    @SuppressLint({"ObsoleteSdkInt"})
    public static final int cnCompare(@NotNull String str, @NotNull String other) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Build.VERSION.SDK_INT >= 24 ? Collator.getInstance(ULocale.SIMPLIFIED_CHINESE).compare(str, other) : java.text.Collator.getInstance(Locale.CHINA).compare(str, other);
    }

    public static final float dp(float f2, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    @NotNull
    public static final String formatContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(new Regex("[\\t\\n\\r\\f\\v]").replace(str, ""), "@", "", false, 4, (Object) null), ReadConfig.REVIEW_CHAR, "", false, 4, (Object) null), ReadConfig.QUESTION_SINGLE_CHAR, "", false, 4, (Object) null), ReadConfig.QUESTION_MULTI_CHAR, "", false, 4, (Object) null);
    }

    public static final int hexValue2IntColor(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return Color.parseColor(str);
    }

    public static final boolean isAbsUrl(@Nullable String str) {
        if (str != null) {
            return StringsKt__StringsJVMKt.startsWith(str, DefaultWebClient.HTTP_SCHEME, true) || StringsKt__StringsJVMKt.startsWith(str, DefaultWebClient.HTTPS_SCHEME, true);
        }
        return false;
    }

    public static final boolean isContentScheme(@Nullable String str) {
        return str != null && StringsKt__StringsJVMKt.startsWith$default(str, "content://", false, 2, null);
    }

    public static final boolean isDataUrl(@Nullable String str) {
        if (str != null) {
            return AppPatternKt.getDataUriRegex().matches(str);
        }
        return false;
    }

    public static final float sp(float f2, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    @NotNull
    public static final String[] splitNotBlank(@NotNull String str, @NotNull String[] delimiter, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) str, (String[]) Arrays.copyOf(delimiter, delimiter.length), false, i2, 2, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt__StringsKt.trim((CharSequence) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!StringsKt__StringsJVMKt.isBlank((String) obj)) {
                arrayList2.add(obj);
            }
        }
        return (String[]) arrayList2.toArray(new String[0]);
    }

    public static /* synthetic */ String[] splitNotBlank$default(String str, String[] strArr, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return splitNotBlank(str, strArr, i2);
    }

    @NotNull
    public static final String toWanString(@NotNull Number number) {
        Intrinsics.checkNotNullParameter(number, "<this>");
        double dDoubleValue = number.doubleValue();
        if (dDoubleValue < 10000.0d) {
            return number.toString();
        }
        double d3 = dDoubleValue / 10000;
        double d4 = 1;
        if (d3 % d4 == 0.0d) {
            StringBuilder sb = new StringBuilder();
            sb.append((int) d3);
            sb.append((char) 19975);
            return sb.toString();
        }
        double d5 = 10;
        double dFloor = Math.floor(d3 * d5) / d5;
        if (dFloor % d4 == 0.0d) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append((int) dFloor);
            sb2.append((char) 19975);
            return sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(dFloor);
        sb3.append((char) 19975);
        return sb3.toString();
    }
}
