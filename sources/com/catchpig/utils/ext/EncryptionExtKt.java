package com.catchpig.utils.ext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0003"}, d2 = {"md5", "", "", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class EncryptionExtKt {
    @NotNull
    public static final String md5(@NotNull String str) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(str, "<this>");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Intrinsics.checkNotNullExpressionValue(messageDigest, "getInstance(\"MD5\")");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        byte[] bArrDigest = messageDigest.digest(bytes);
        Intrinsics.checkNotNullExpressionValue(bArrDigest, "instance.digest(this.toByteArray())");
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b3 : bArrDigest) {
            String hexString = Integer.toHexString(b3 & 255);
            if (hexString.length() < 2) {
                hexString = '0' + hexString;
            }
            stringBuffer.append(hexString);
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    @NotNull
    public static final String md5(@NotNull byte[] bArr) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Intrinsics.checkNotNullExpressionValue(messageDigest, "getInstance(\"MD5\")");
        byte[] bArrDigest = messageDigest.digest(bArr);
        Intrinsics.checkNotNullExpressionValue(bArrDigest, "instance.digest(this)");
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b3 : bArrDigest) {
            String hexString = Integer.toHexString(b3 & 255);
            if (hexString.length() < 2) {
                hexString = '0' + hexString;
            }
            stringBuffer.append(hexString);
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }
}
