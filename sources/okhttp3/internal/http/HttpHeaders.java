package okhttp3.internal.http;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.arialyy.aria.core.inf.IOptionConstant;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.ByteString;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0005\n\u0000\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0004*\u00020\u0006\u001a\u001a\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0012H\u0002\u001a\u000e\u0010\u0013\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u000e\u0010\u0014\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u001a\u0010\u0015\u001a\u00020\u000f*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n\u001a\f\u0010\u001a\u001a\u00020\u0004*\u00020\u0010H\u0002\u001a\u0014\u0010\u001b\u001a\u00020\u0004*\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"QUOTED_STRING_DELIMITERS", "Lokio/ByteString;", "TOKEN_DELIMITERS", "hasBody", "", "response", "Lokhttp3/Response;", "parseChallenges", "", "Lokhttp3/Challenge;", "Lokhttp3/Headers;", "headerName", "", "promisesBody", "readChallengeHeader", "", "Lokio/Buffer;", "result", "", "readQuotedString", "readToken", "receiveHeaders", "Lokhttp3/CookieJar;", "url", "Lokhttp3/HttpUrl;", IOptionConstant.headers, "skipCommasAndWhitespace", "startsWith", RequestParameters.PREFIX, "", "okhttp"}, k = 2, mv = {1, 6, 0}, xi = 48)
@JvmName(name = "HttpHeaders")
/* loaded from: classes9.dex */
public final class HttpHeaders {

    @NotNull
    private static final ByteString QUOTED_STRING_DELIMITERS;

    @NotNull
    private static final ByteString TOKEN_DELIMITERS;

    static {
        ByteString.Companion companion = ByteString.INSTANCE;
        QUOTED_STRING_DELIMITERS = companion.encodeUtf8(BasicHeaderValueFormatter.UNSAFE_CHARS);
        TOKEN_DELIMITERS = companion.encodeUtf8("\t ,=");
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "No longer supported", replaceWith = @ReplaceWith(expression = "response.promisesBody()", imports = {}))
    public static final boolean hasBody(@NotNull Response response) {
        Intrinsics.checkNotNullParameter(response, "response");
        return promisesBody(response);
    }

    @NotNull
    public static final List<Challenge> parseChallenges(@NotNull Headers headers, @NotNull String headerName) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        Intrinsics.checkNotNullParameter(headerName, "headerName");
        ArrayList arrayList = new ArrayList();
        int size = headers.size();
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2 + 1;
            if (StringsKt__StringsJVMKt.equals(headerName, headers.name(i2), true)) {
                try {
                    readChallengeHeader(new Buffer().writeUtf8(headers.value(i2)), arrayList);
                } catch (EOFException e2) {
                    Platform.INSTANCE.get().log("Unable to parse challenge", 5, e2);
                }
            }
            i2 = i3;
        }
        return arrayList;
    }

    public static final boolean promisesBody(@NotNull Response response) {
        Intrinsics.checkNotNullParameter(response, "<this>");
        if (Intrinsics.areEqual(response.request().method(), "HEAD")) {
            return false;
        }
        int iCode = response.code();
        return (((iCode >= 100 && iCode < 200) || iCode == 204 || iCode == 304) && Util.headersContentLength(response) == -1 && !StringsKt__StringsJVMKt.equals("chunked", Response.header$default(response, "Transfer-Encoding", null, 2, null), true)) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x0079, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0079, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final void readChallengeHeader(okio.Buffer r7, java.util.List<okhttp3.Challenge> r8) throws java.io.EOFException {
        /*
            r0 = 0
        L1:
            r1 = r0
        L2:
            if (r1 != 0) goto Le
            skipCommasAndWhitespace(r7)
            java.lang.String r1 = readToken(r7)
            if (r1 != 0) goto Le
            return
        Le:
            boolean r2 = skipCommasAndWhitespace(r7)
            java.lang.String r3 = readToken(r7)
            if (r3 != 0) goto L2c
            boolean r7 = r7.exhausted()
            if (r7 != 0) goto L1f
            return
        L1f:
            okhttp3.Challenge r7 = new okhttp3.Challenge
            java.util.Map r0 = kotlin.collections.MapsKt.emptyMap()
            r7.<init>(r1, r0)
            r8.add(r7)
            return
        L2c:
            r4 = 61
            int r5 = okhttp3.internal.Util.skipAll(r7, r4)
            boolean r6 = skipCommasAndWhitespace(r7)
            if (r2 != 0) goto L5c
            if (r6 != 0) goto L40
            boolean r2 = r7.exhausted()
            if (r2 == 0) goto L5c
        L40:
            okhttp3.Challenge r2 = new okhttp3.Challenge
            java.lang.String r4 = "="
            java.lang.String r4 = kotlin.text.StringsKt.repeat(r4, r5)
            java.lang.String r3 = kotlin.jvm.internal.Intrinsics.stringPlus(r3, r4)
            java.util.Map r3 = java.util.Collections.singletonMap(r0, r3)
            java.lang.String r4 = "singletonMap<String, Str…ek + \"=\".repeat(eqCount))"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r2.<init>(r1, r3)
            r8.add(r2)
            goto L1
        L5c:
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            int r6 = okhttp3.internal.Util.skipAll(r7, r4)
            int r5 = r5 + r6
        L66:
            if (r3 != 0) goto L77
            java.lang.String r3 = readToken(r7)
            boolean r5 = skipCommasAndWhitespace(r7)
            if (r5 == 0) goto L73
            goto L79
        L73:
            int r5 = okhttp3.internal.Util.skipAll(r7, r4)
        L77:
            if (r5 != 0) goto L84
        L79:
            okhttp3.Challenge r4 = new okhttp3.Challenge
            r4.<init>(r1, r2)
            r8.add(r4)
            r1 = r3
            goto L2
        L84:
            r6 = 1
            if (r5 <= r6) goto L88
            return
        L88:
            boolean r6 = skipCommasAndWhitespace(r7)
            if (r6 == 0) goto L8f
            return
        L8f:
            r6 = 34
            boolean r6 = startsWith(r7, r6)
            if (r6 == 0) goto L9c
            java.lang.String r6 = readQuotedString(r7)
            goto La0
        L9c:
            java.lang.String r6 = readToken(r7)
        La0:
            if (r6 != 0) goto La3
            return
        La3:
            java.lang.Object r3 = r2.put(r3, r6)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto Lac
            return
        Lac:
            boolean r3 = skipCommasAndWhitespace(r7)
            if (r3 != 0) goto Lb9
            boolean r3 = r7.exhausted()
            if (r3 != 0) goto Lb9
            return
        Lb9:
            r3 = r0
            goto L66
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.HttpHeaders.readChallengeHeader(okio.Buffer, java.util.List):void");
    }

    private static final String readQuotedString(Buffer buffer) throws EOFException {
        if (!(buffer.readByte() == 34)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Buffer buffer2 = new Buffer();
        while (true) {
            long jIndexOfElement = buffer.indexOfElement(QUOTED_STRING_DELIMITERS);
            if (jIndexOfElement == -1) {
                return null;
            }
            if (buffer.getByte(jIndexOfElement) == 34) {
                buffer2.write(buffer, jIndexOfElement);
                buffer.readByte();
                return buffer2.readUtf8();
            }
            if (buffer.size() == jIndexOfElement + 1) {
                return null;
            }
            buffer2.write(buffer, jIndexOfElement);
            buffer.readByte();
            buffer2.write(buffer, 1L);
        }
    }

    private static final String readToken(Buffer buffer) {
        long jIndexOfElement = buffer.indexOfElement(TOKEN_DELIMITERS);
        if (jIndexOfElement == -1) {
            jIndexOfElement = buffer.size();
        }
        if (jIndexOfElement != 0) {
            return buffer.readUtf8(jIndexOfElement);
        }
        return null;
    }

    public static final void receiveHeaders(@NotNull CookieJar cookieJar, @NotNull HttpUrl url, @NotNull Headers headers) {
        Intrinsics.checkNotNullParameter(cookieJar, "<this>");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(headers, "headers");
        if (cookieJar == CookieJar.NO_COOKIES) {
            return;
        }
        List<Cookie> all = Cookie.INSTANCE.parseAll(url, headers);
        if (all.isEmpty()) {
            return;
        }
        cookieJar.saveFromResponse(url, all);
    }

    private static final boolean skipCommasAndWhitespace(Buffer buffer) throws EOFException {
        boolean z2 = false;
        while (!buffer.exhausted()) {
            byte b3 = buffer.getByte(0L);
            boolean z3 = true;
            if (b3 != 44) {
                if (b3 != 32 && b3 != 9) {
                    z3 = false;
                }
                if (!z3) {
                    break;
                }
                buffer.readByte();
            } else {
                buffer.readByte();
                z2 = true;
            }
        }
        return z2;
    }

    private static final boolean startsWith(Buffer buffer, byte b3) {
        return !buffer.exhausted() && buffer.getByte(0L) == b3;
    }
}
