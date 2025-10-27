package kotlin.text;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
@Metadata(d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\f\u0010\u0017\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0019\u0010&\u001a\u00020#*\u0004\u0018\u00010'2\b\u0010!\u001a\u0004\u0018\u00010'H\u0087\u0004\u001a \u0010&\u001a\u00020#*\u0004\u0018\u00010'2\b\u0010!\u001a\u0004\u0018\u00010'2\u0006\u0010\"\u001a\u00020#H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010(\u001a\u00020'H\u0087\b\u001a\f\u0010)\u001a\u00020\u0002*\u00020\u0002H\u0007\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00104\u001a6\u00100\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0004\b5\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00106\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00107\u001a>\u00100\u001a\u00020\u0002*\u00020\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0004\b5\u00107\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00108\u001a\r\u00109\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u0010:\u001a\u00020#*\u00020'\u001a\r\u0010;\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010;\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u001d\u0010<\u001a\u00020\u0011*\u00020\u00022\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010<\u001a\u00020\u0011*\u00020\u00022\u0006\u0010@\u001a\u00020\u00022\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010A\u001a\u00020\u0011*\u00020\u00022\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010A\u001a\u00020\u0011*\u00020\u00022\u0006\u0010@\u001a\u00020\u00022\u0006\u0010?\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010B\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010C\u001a\u00020\u0011H\u0087\b\u001a4\u0010D\u001a\u00020#*\u00020'2\u0006\u0010E\u001a\u00020\u00112\u0006\u0010!\u001a\u00020'2\u0006\u0010F\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010D\u001a\u00020#*\u00020\u00022\u0006\u0010E\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010F\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010G\u001a\u00020\u0002*\u00020'2\u0006\u0010H\u001a\u00020\u0011\u001a$\u0010I\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020>2\u0006\u0010K\u001a\u00020>2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010I\u001a\u00020\u0002*\u00020\u00022\u0006\u0010L\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020>2\u0006\u0010K\u001a\u00020>2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010L\u001a\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00020P*\u00020'2\u0006\u0010Q\u001a\u00020R2\b\b\u0002\u0010S\u001a\u00020\u0011\u001a\u001c\u0010T\u001a\u00020#*\u00020\u00022\u0006\u0010U\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010T\u001a\u00020#*\u00020\u00022\u0006\u0010U\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010V\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010V\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010W\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010X\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010X\u001a\u00020\u0014*\u00020\u00022\u0006\u0010Y\u001a\u00020\u00142\b\b\u0002\u0010Z\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010X\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010[\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010[\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010\\\u001a\u00020R*\u00020\u00022\b\b\u0002\u0010]\u001a\u00020\u0011H\u0087\b\u001a\r\u0010^\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010^\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\r\u0010_\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010_\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006`"}, d2 = {"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", HttpHeaderValues.BYTES, "", "charset", "Ljava/nio/charset/Charset;", "offset", "", SessionDescription.ATTR_LENGTH, "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "", "charSequence", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", AliyunLogKey.KEY_ARGS, "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "formatNullable", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "lowercase", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", RequestParameters.PREFIX, "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "uppercase", "kotlin-stdlib"}, k = 5, mv = {1, 8, 0}, xi = 49, xs = "kotlin/text/StringsKt")
@SourceDebugExtension({"SMAP\nStringsJVM.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringsJVM.kt\nkotlin/text/StringsKt__StringsJVMKt\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,825:1\n1174#2,2:826\n1#3:828\n1726#4,3:829\n*S KotlinDebug\n*F\n+ 1 StringsJVM.kt\nkotlin/text/StringsKt__StringsJVMKt\n*L\n73#1:826,2\n621#1:829,3\n*E\n"})
/* loaded from: classes8.dex */
public class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
    @InlineOnly
    private static final String String(byte[] bytes, int i2, int i3, Charset charset) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(bytes, i2, i3, charset);
    }

    @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }", imports = {"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @NotNull
    public static final String capitalize(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        return capitalize(str, locale);
    }

    @InlineOnly
    private static final int codePointAt(String str, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.codePointAt(i2);
    }

    @InlineOnly
    private static final int codePointBefore(String str, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.codePointBefore(i2);
    }

    @InlineOnly
    private static final int codePointCount(String str, int i2, int i3) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.codePointCount(i2, i3);
    }

    public static int compareTo(@NotNull String str, @NotNull String other, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return z2 ? str.compareToIgnoreCase(other) : str.compareTo(other);
    }

    public static /* synthetic */ int compareTo$default(String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return compareTo(str, str2, z2);
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static String concatToString(@NotNull char[] cArr) {
        Intrinsics.checkNotNullParameter(cArr, "<this>");
        return new String(cArr);
    }

    public static /* synthetic */ String concatToString$default(char[] cArr, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = cArr.length;
        }
        return concatToString(cArr, i2, i3);
    }

    @InlineOnly
    private static final boolean contentEquals(String str, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(charSequence, "charSequence");
        return str.contentEquals(charSequence);
    }

    @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { it.lowercase(Locale.getDefault()) }", imports = {"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @NotNull
    public static final String decapitalize(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (!(str.length() > 0) || Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(0, 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        Intrinsics.checkNotNull(strSubstring, "null cannot be cast to non-null type java.lang.String");
        String lowerCase = strSubstring.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        sb.append(lowerCase);
        String strSubstring2 = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
        sb.append(strSubstring2);
        return sb.toString();
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static final String decodeToString(@NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return new String(bArr, Charsets.UTF_8);
    }

    public static /* synthetic */ String decodeToString$default(byte[] bArr, int i2, int i3, boolean z2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = bArr.length;
        }
        if ((i4 & 4) != 0) {
            z2 = false;
        }
        return decodeToString(bArr, i2, i3, z2);
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static final byte[] encodeToByteArray(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    public static /* synthetic */ byte[] encodeToByteArray$default(String str, int i2, int i3, boolean z2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = str.length();
        }
        if ((i4 & 4) != 0) {
            z2 = false;
        }
        return encodeToByteArray(str, i2, i3, z2);
    }

    public static boolean endsWith(@NotNull String str, @NotNull String suffix, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return !z2 ? str.endsWith(suffix) : regionMatches(str, str.length() - suffix.length(), suffix, 0, suffix.length(), true);
    }

    public static /* synthetic */ boolean endsWith$default(String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return endsWith(str, str2, z2);
    }

    public static boolean equals(@Nullable String str, @Nullable String str2, boolean z2) {
        return str == null ? str2 == null : !z2 ? str.equals(str2) : str.equalsIgnoreCase(str2);
    }

    public static /* synthetic */ boolean equals$default(String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return equals(str, str2, z2);
    }

    @InlineOnly
    private static final String format(String str, Object... args) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        String str2 = String.format(str, Arrays.copyOf(args, args.length));
        Intrinsics.checkNotNullExpressionValue(str2, "format(this, *args)");
        return str2;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    @JvmName(name = "formatNullable")
    private static final String formatNullable(String str, Locale locale, Object... args) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        String str2 = String.format(locale, str, Arrays.copyOf(args, args.length));
        Intrinsics.checkNotNullExpressionValue(str2, "format(locale, this, *args)");
        return str2;
    }

    @NotNull
    public static Comparator<String> getCASE_INSENSITIVE_ORDER(@NotNull StringCompanionObject stringCompanionObject) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Comparator<String> CASE_INSENSITIVE_ORDER = String.CASE_INSENSITIVE_ORDER;
        Intrinsics.checkNotNullExpressionValue(CASE_INSENSITIVE_ORDER, "CASE_INSENSITIVE_ORDER");
        return CASE_INSENSITIVE_ORDER;
    }

    @InlineOnly
    private static final String intern(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strIntern = str.intern();
        Intrinsics.checkNotNullExpressionValue(strIntern, "this as java.lang.String).intern()");
        return strIntern;
    }

    public static boolean isBlank(@NotNull CharSequence charSequence) {
        boolean z2;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (charSequence.length() == 0) {
            return true;
        }
        Iterable indices = StringsKt__StringsKt.getIndices(charSequence);
        if ((indices instanceof Collection) && ((Collection) indices).isEmpty()) {
            z2 = true;
        } else {
            Iterator it = indices.iterator();
            while (it.hasNext()) {
                if (!CharsKt__CharJVMKt.isWhitespace(charSequence.charAt(((IntIterator) it).nextInt()))) {
                    z2 = false;
                    break;
                }
            }
            z2 = true;
        }
        return z2;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String lowercase(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase;
    }

    @InlineOnly
    private static final int nativeIndexOf(String str, char c3, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.indexOf(c3, i2);
    }

    @InlineOnly
    private static final int nativeLastIndexOf(String str, char c3, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.lastIndexOf(c3, i2);
    }

    @InlineOnly
    private static final int offsetByCodePoints(String str, int i2, int i3) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.offsetByCodePoints(i2, i3);
    }

    public static final boolean regionMatches(@NotNull CharSequence charSequence, int i2, @NotNull CharSequence other, int i3, int i4, boolean z2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return ((charSequence instanceof String) && (other instanceof String)) ? regionMatches((String) charSequence, i2, (String) other, i3, i4, z2) : StringsKt__StringsKt.regionMatchesImpl(charSequence, i2, other, i3, i4, z2);
    }

    public static /* synthetic */ boolean regionMatches$default(CharSequence charSequence, int i2, CharSequence charSequence2, int i3, int i4, boolean z2, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z2 = false;
        }
        return regionMatches(charSequence, i2, charSequence2, i3, i4, z2);
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @NotNull
    public static String repeat(@NotNull CharSequence charSequence, int i2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        if (!(i2 >= 0)) {
            throw new IllegalArgumentException(("Count 'n' must be non-negative, but was " + i2 + '.').toString());
        }
        if (i2 == 0) {
            return "";
        }
        if (i2 == 1) {
            return charSequence.toString();
        }
        int length = charSequence.length();
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            char cCharAt = charSequence.charAt(0);
            char[] cArr = new char[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                cArr[i3] = cCharAt;
            }
            return new String(cArr);
        }
        StringBuilder sb = new StringBuilder(charSequence.length() * i2);
        ?? Iterator2 = new IntRange(1, i2).iterator();
        while (Iterator2.hasNext()) {
            Iterator2.nextInt();
            sb.append(charSequence);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "{\n                    va…tring()\n                }");
        return string;
    }

    @NotNull
    public static final String replace(@NotNull String str, char c3, char c4, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (!z2) {
            String strReplace = str.replace(c3, c4);
            Intrinsics.checkNotNullExpressionValue(strReplace, "this as java.lang.String…replace(oldChar, newChar)");
            return strReplace;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (CharsKt__CharKt.equals(cCharAt, c3, z2)) {
                cCharAt = c4;
            }
            sb.append(cCharAt);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder(capacity).…builderAction).toString()");
        return string;
    }

    public static /* synthetic */ String replace$default(String str, char c3, char c4, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        return replace(str, c3, c4, z2);
    }

    @NotNull
    public static final String replaceFirst(@NotNull String str, char c3, char c4, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str, c3, 0, z2, 2, (Object) null);
        return iIndexOf$default < 0 ? str : StringsKt__StringsKt.replaceRange((CharSequence) str, iIndexOf$default, iIndexOf$default + 1, (CharSequence) String.valueOf(c4)).toString();
    }

    public static /* synthetic */ String replaceFirst$default(String str, char c3, char c4, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        return replaceFirst(str, c3, c4, z2);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence charSequence, @NotNull Pattern regex, int i2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(regex, "regex");
        StringsKt__StringsKt.requireNonNegativeLimit(i2);
        if (i2 == 0) {
            i2 = -1;
        }
        String[] strArrSplit = regex.split(charSequence, i2);
        Intrinsics.checkNotNullExpressionValue(strArrSplit, "regex.split(this, if (limit == 0) -1 else limit)");
        return ArraysKt___ArraysJvmKt.asList(strArrSplit);
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, Pattern pattern, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return split(charSequence, pattern, i2);
    }

    public static boolean startsWith(@NotNull String str, @NotNull String prefix, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return !z2 ? str.startsWith(prefix) : regionMatches(str, 0, prefix, 0, prefix.length(), z2);
    }

    public static /* synthetic */ boolean startsWith$default(String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return startsWith(str, str2, z2);
    }

    @InlineOnly
    private static final String substring(String str, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strSubstring = str.substring(i2);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    @InlineOnly
    private static final byte[] toByteArray(String str, Charset charset) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = str.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    public static /* synthetic */ byte[] toByteArray$default(String str, Charset charset, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = str.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static final char[] toCharArray(@NotNull String str, int i2, int i3) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        AbstractList.INSTANCE.checkBoundsIndexes$kotlin_stdlib(i2, i3, str.length());
        char[] cArr = new char[i3 - i2];
        str.getChars(i2, i3, cArr, 0);
        return cArr;
    }

    public static /* synthetic */ char[] toCharArray$default(String str, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = str.length();
        }
        return toCharArray(str, i2, i3);
    }

    @Deprecated(message = "Use lowercase() instead.", replaceWith = @ReplaceWith(expression = "lowercase(Locale.getDefault())", imports = {"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @InlineOnly
    private static final String toLowerCase(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String lowerCase = str.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        return lowerCase;
    }

    @InlineOnly
    private static final Pattern toPattern(String str, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Pattern patternCompile = Pattern.compile(str, i2);
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(this, flags)");
        return patternCompile;
    }

    public static /* synthetic */ Pattern toPattern$default(String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        Intrinsics.checkNotNullParameter(str, "<this>");
        Pattern patternCompile = Pattern.compile(str, i2);
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(this, flags)");
        return patternCompile;
    }

    @Deprecated(message = "Use uppercase() instead.", replaceWith = @ReplaceWith(expression = "uppercase(Locale.getDefault())", imports = {"java.util.Locale"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @InlineOnly
    private static final String toUpperCase(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String upperCase = str.toUpperCase();
        Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase()");
        return upperCase;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String uppercase(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String upperCase = str.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        return upperCase;
    }

    @InlineOnly
    private static final String String(byte[] bytes, Charset charset) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(bytes, charset);
    }

    @SinceKotlin(version = "1.4")
    @NotNull
    @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }", imports = {}))
    @LowPriorityInOverloadResolution
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    public static final String capitalize(@NotNull String str, @NotNull Locale locale) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (!(str.length() > 0)) {
            return str;
        }
        char cCharAt = str.charAt(0);
        if (!Character.isLowerCase(cCharAt)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        char titleCase = Character.toTitleCase(cCharAt);
        if (titleCase != Character.toUpperCase(cCharAt)) {
            sb.append(titleCase);
        } else {
            String strSubstring = str.substring(0, 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            Intrinsics.checkNotNull(strSubstring, "null cannot be cast to non-null type java.lang.String");
            String upperCase = strSubstring.toUpperCase(locale);
            Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(locale)");
            sb.append(upperCase);
        }
        String strSubstring2 = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
        sb.append(strSubstring2);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static String concatToString(@NotNull char[] cArr, int i2, int i3) {
        Intrinsics.checkNotNullParameter(cArr, "<this>");
        AbstractList.INSTANCE.checkBoundsIndexes$kotlin_stdlib(i2, i3, cArr.length);
        return new String(cArr, i2, i3 - i2);
    }

    @InlineOnly
    private static final boolean contentEquals(String str, StringBuffer stringBuilder) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(stringBuilder, "stringBuilder");
        return str.contentEquals(stringBuilder);
    }

    @SinceKotlin(version = "1.4")
    @NotNull
    @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { it.lowercase(locale) }", imports = {}))
    @LowPriorityInOverloadResolution
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    public static final String decapitalize(@NotNull String str, @NotNull Locale locale) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        if (!(str.length() > 0) || Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(0, 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        Intrinsics.checkNotNull(strSubstring, "null cannot be cast to non-null type java.lang.String");
        String lowerCase = strSubstring.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        sb.append(lowerCase);
        String strSubstring2 = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String).substring(startIndex)");
        sb.append(strSubstring2);
        return sb.toString();
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static final String decodeToString(@NotNull byte[] bArr, int i2, int i3, boolean z2) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        AbstractList.INSTANCE.checkBoundsIndexes$kotlin_stdlib(i2, i3, bArr.length);
        if (!z2) {
            return new String(bArr, i2, i3 - i2, Charsets.UTF_8);
        }
        String string = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(bArr, i2, i3 - i2)).toString();
        Intrinsics.checkNotNullExpressionValue(string, "decoder.decode(ByteBuffe…- startIndex)).toString()");
        return string;
    }

    @SinceKotlin(version = "1.4")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @NotNull
    public static final byte[] encodeToByteArray(@NotNull String str, int i2, int i3, boolean z2) throws CharacterCodingException {
        Intrinsics.checkNotNullParameter(str, "<this>");
        AbstractList.INSTANCE.checkBoundsIndexes$kotlin_stdlib(i2, i3, str.length());
        if (!z2) {
            String strSubstring = str.substring(i2, i3);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            Charset charset = Charsets.UTF_8;
            Intrinsics.checkNotNull(strSubstring, "null cannot be cast to non-null type java.lang.String");
            byte[] bytes = strSubstring.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            return bytes;
        }
        ByteBuffer byteBufferEncode = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap(str, i2, i3));
        if (byteBufferEncode.hasArray() && byteBufferEncode.arrayOffset() == 0) {
            int iRemaining = byteBufferEncode.remaining();
            byte[] bArrArray = byteBufferEncode.array();
            Intrinsics.checkNotNull(bArrArray);
            if (iRemaining == bArrArray.length) {
                byte[] bArrArray2 = byteBufferEncode.array();
                Intrinsics.checkNotNullExpressionValue(bArrArray2, "{\n        byteBuffer.array()\n    }");
                return bArrArray2;
            }
        }
        byte[] bArr = new byte[byteBufferEncode.remaining()];
        byteBufferEncode.get(bArr);
        return bArr;
    }

    @InlineOnly
    private static final String format(StringCompanionObject stringCompanionObject, String format, Object... args) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        String str = String.format(format, Arrays.copyOf(args, args.length));
        Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
        return str;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    @JvmName(name = "formatNullable")
    private static final String formatNullable(StringCompanionObject stringCompanionObject, Locale locale, String format, Object... args) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        String str = String.format(locale, format, Arrays.copyOf(args, args.length));
        Intrinsics.checkNotNullExpressionValue(str, "format(locale, format, *args)");
        return str;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String lowercase(String str, Locale locale) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String lowerCase = str.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        return lowerCase;
    }

    @InlineOnly
    private static final int nativeIndexOf(String str, String str2, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(str2, "str");
        return str.indexOf(str2, i2);
    }

    @InlineOnly
    private static final int nativeLastIndexOf(String str, String str2, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(str2, "str");
        return str.lastIndexOf(str2, i2);
    }

    public static /* synthetic */ boolean regionMatches$default(String str, int i2, String str2, int i3, int i4, boolean z2, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z2 = false;
        }
        return regionMatches(str, i2, str2, i3, i4, z2);
    }

    public static /* synthetic */ String replace$default(String str, String str2, String str3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        return replace(str, str2, str3, z2);
    }

    public static /* synthetic */ String replaceFirst$default(String str, String str2, String str3, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        return replaceFirst(str, str2, str3, z2);
    }

    public static /* synthetic */ boolean startsWith$default(String str, String str2, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            z2 = false;
        }
        return startsWith(str, str2, i2, z2);
    }

    @InlineOnly
    private static final String substring(String str, int i2, int i3) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strSubstring = str.substring(i2, i3);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public static /* synthetic */ char[] toCharArray$default(String str, char[] destination, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 2) != 0) {
            i2 = 0;
        }
        if ((i5 & 4) != 0) {
            i3 = 0;
        }
        if ((i5 & 8) != 0) {
            i4 = str.length();
        }
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        str.getChars(i3, i4, destination, i2);
        return destination;
    }

    @Deprecated(message = "Use lowercase() instead.", replaceWith = @ReplaceWith(expression = "lowercase(locale)", imports = {}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @InlineOnly
    private static final String toLowerCase(String str, Locale locale) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String lowerCase = str.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        return lowerCase;
    }

    @Deprecated(message = "Use uppercase() instead.", replaceWith = @ReplaceWith(expression = "uppercase(locale)", imports = {}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @InlineOnly
    private static final String toUpperCase(String str, Locale locale) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String upperCase = str.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(locale)");
        return upperCase;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final String uppercase(String str, Locale locale) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        String upperCase = str.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(locale)");
        return upperCase;
    }

    @InlineOnly
    private static final String String(byte[] bytes, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return new String(bytes, i2, i3, Charsets.UTF_8);
    }

    @SinceKotlin(version = "1.5")
    public static final boolean contentEquals(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        if ((charSequence instanceof String) && charSequence2 != null) {
            return ((String) charSequence).contentEquals(charSequence2);
        }
        return StringsKt__StringsKt.contentEqualsImpl(charSequence, charSequence2);
    }

    @Deprecated(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    @InlineOnly
    private static final /* synthetic */ String format(String str, Locale locale, Object... args) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        Intrinsics.checkNotNullParameter(args, "args");
        String str2 = String.format(locale, str, Arrays.copyOf(args, args.length));
        Intrinsics.checkNotNullExpressionValue(str2, "format(locale, this, *args)");
        return str2;
    }

    @NotNull
    public static final String replaceFirst(@NotNull String str, @NotNull String oldValue, @NotNull String newValue, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(oldValue, "oldValue");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str, oldValue, 0, z2, 2, (Object) null);
        return iIndexOf$default < 0 ? str : StringsKt__StringsKt.replaceRange((CharSequence) str, iIndexOf$default, oldValue.length() + iIndexOf$default, (CharSequence) newValue).toString();
    }

    public static boolean startsWith(@NotNull String str, @NotNull String prefix, int i2, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!z2) {
            return str.startsWith(prefix, i2);
        }
        return regionMatches(str, i2, prefix, 0, prefix.length(), z2);
    }

    @InlineOnly
    private static final char[] toCharArray(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        char[] charArray = str.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        return charArray;
    }

    @InlineOnly
    private static final String String(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return new String(bytes, Charsets.UTF_8);
    }

    @Deprecated(message = "Use Kotlin compiler 1.4 to avoid deprecation warning.")
    @DeprecatedSinceKotlin(hiddenSince = "1.4")
    @InlineOnly
    private static final /* synthetic */ String format(StringCompanionObject stringCompanionObject, Locale locale, String format, Object... args) {
        Intrinsics.checkNotNullParameter(stringCompanionObject, "<this>");
        Intrinsics.checkNotNullParameter(locale, "locale");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        String str = String.format(locale, format, Arrays.copyOf(args, args.length));
        Intrinsics.checkNotNullExpressionValue(str, "format(locale, format, *args)");
        return str;
    }

    public static boolean regionMatches(@NotNull String str, int i2, @NotNull String other, int i3, int i4, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (!z2) {
            return str.regionMatches(i2, other, i3, i4);
        }
        return str.regionMatches(z2, i2, other, i3, i4);
    }

    @InlineOnly
    private static final char[] toCharArray(String str, char[] destination, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        str.getChars(i3, i4, destination, i2);
        return destination;
    }

    @InlineOnly
    private static final String String(char[] chars) {
        Intrinsics.checkNotNullParameter(chars, "chars");
        return new String(chars);
    }

    @InlineOnly
    private static final String String(char[] chars, int i2, int i3) {
        Intrinsics.checkNotNullParameter(chars, "chars");
        return new String(chars, i2, i3);
    }

    @SinceKotlin(version = "1.5")
    public static final boolean contentEquals(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2, boolean z2) {
        if (z2) {
            return StringsKt__StringsKt.contentEqualsIgnoreCaseImpl(charSequence, charSequence2);
        }
        return contentEquals(charSequence, charSequence2);
    }

    @NotNull
    public static String replace(@NotNull String str, @NotNull String oldValue, @NotNull String newValue, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(oldValue, "oldValue");
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        int i2 = 0;
        int iIndexOf = StringsKt__StringsKt.indexOf(str, oldValue, 0, z2);
        if (iIndexOf < 0) {
            return str;
        }
        int length = oldValue.length();
        int iCoerceAtLeast = RangesKt___RangesKt.coerceAtLeast(length, 1);
        int length2 = (str.length() - length) + newValue.length();
        if (length2 >= 0) {
            StringBuilder sb = new StringBuilder(length2);
            do {
                sb.append((CharSequence) str, i2, iIndexOf);
                sb.append(newValue);
                i2 = iIndexOf + length;
                if (iIndexOf >= str.length()) {
                    break;
                }
                iIndexOf = StringsKt__StringsKt.indexOf(str, oldValue, iIndexOf + iCoerceAtLeast, z2);
            } while (iIndexOf > 0);
            sb.append((CharSequence) str, i2, str.length());
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.append(this, i, length).toString()");
            return string;
        }
        throw new OutOfMemoryError();
    }

    @InlineOnly
    private static final String String(int[] codePoints, int i2, int i3) {
        Intrinsics.checkNotNullParameter(codePoints, "codePoints");
        return new String(codePoints, i2, i3);
    }

    @InlineOnly
    private static final String String(StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(stringBuffer, "stringBuffer");
        return new String(stringBuffer);
    }

    @InlineOnly
    private static final String String(StringBuilder stringBuilder) {
        Intrinsics.checkNotNullParameter(stringBuilder, "stringBuilder");
        return new String(stringBuilder);
    }
}
