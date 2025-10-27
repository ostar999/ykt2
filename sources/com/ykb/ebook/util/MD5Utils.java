package com.ykb.ebook.util;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nJ\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u000f\u001a\u00020\n2\b\u0010\u0012\u001a\u0004\u0018\u00010\nJ\u000e\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\nR\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/util/MD5Utils;", "", "()V", "MD5Digester", "Lcn/hutool/crypto/digest/Digester;", "getMD5Digester", "()Lcn/hutool/crypto/digest/Digester;", "threadLocal", "Ljava/lang/ThreadLocal;", "MD5Encode", "", "sourceString", "byte2hexString", HttpHeaderValues.BYTES, "", "md5Encode", "inputStream", "Ljava/io/InputStream;", "str", "md5Encode16", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class MD5Utils {

    @NotNull
    public static final MD5Utils INSTANCE = new MD5Utils();

    @NotNull
    private static final ThreadLocal<Digester> threadLocal = new ThreadLocal<>();

    private MD5Utils() {
    }

    private final Digester getMD5Digester() {
        ThreadLocal<Digester> threadLocal2 = threadLocal;
        Digester digester = threadLocal2.get();
        if (digester == null) {
            digester = DigestUtil.digester("MD5");
            Intrinsics.checkNotNullExpressionValue(digester, "digester(\"MD5\")");
            threadLocal2.set(digester);
        }
        return digester;
    }

    @Nullable
    public final String MD5Encode(@Nullable String sourceString) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            Intrinsics.checkNotNull(sourceString);
            Charset charsetForName = Charset.forName("UTF-8");
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(charsetName)");
            byte[] bytes = sourceString.getBytes(charsetForName);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] bArrDigest = messageDigest.digest(bytes);
            Intrinsics.checkNotNullExpressionValue(bArrDigest, "md.digest(resultString!!…eArray(charset(\"UTF-8\")))");
            return byte2hexString(bArrDigest);
        } catch (Exception unused) {
            return sourceString;
        }
    }

    @NotNull
    public final String byte2hexString(@NotNull byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
        int length = bytes.length;
        for (int i2 = 0; i2 < length; i2++) {
            if ((bytes[i2] & 255) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(bytes[i2] & 255, 16));
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "buf.toString()");
        return string;
    }

    @NotNull
    public final String md5Encode(@Nullable String str) {
        String strDigestHex = getMD5Digester().digestHex(str);
        Intrinsics.checkNotNullExpressionValue(strDigestHex, "MD5Digester.digestHex(str)");
        return strDigestHex;
    }

    @NotNull
    public final String md5Encode16(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        String strSubstring = md5Encode(str).substring(8, 24);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    @NotNull
    public final String md5Encode(@NotNull InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        String strDigestHex = getMD5Digester().digestHex(inputStream);
        Intrinsics.checkNotNullExpressionValue(strDigestHex, "MD5Digester.digestHex(inputStream)");
        return strDigestHex;
    }
}
