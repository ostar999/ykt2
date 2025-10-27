package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.util.Base64;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;
import com.google.android.exoplayer2.util.Util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
final class RtspAuthenticationInfo {
    private static final String ALGORITHM = "MD5";
    public static final int BASIC = 1;
    public static final int DIGEST = 2;
    private static final String DIGEST_FORMAT = "Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\"";
    private static final String DIGEST_FORMAT_WITH_OPAQUE = "Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\", opaque=\"%s\"";
    public final int authenticationMechanism;
    public final String nonce;
    public final String opaque;
    public final String realm;

    public RtspAuthenticationInfo(int i2, String str, String str2, String str3) {
        this.authenticationMechanism = i2;
        this.realm = str;
        this.nonce = str2;
        this.opaque = str3;
    }

    private String getBasicAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo) {
        String str = rtspAuthUserInfo.username;
        String str2 = rtspAuthUserInfo.password;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return Base64.encodeToString(RtspMessageUtil.getStringBytes(sb.toString()), 0);
    }

    private String getDigestAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo, Uri uri, int i2) throws ParserException, NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            String methodString = RtspMessageUtil.toMethodString(i2);
            String str = rtspAuthUserInfo.username;
            String str2 = this.realm;
            String str3 = rtspAuthUserInfo.password;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(str2).length() + String.valueOf(str3).length());
            sb.append(str);
            sb.append(":");
            sb.append(str2);
            sb.append(":");
            sb.append(str3);
            String hexString = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(sb.toString())));
            String strValueOf = String.valueOf(uri);
            StringBuilder sb2 = new StringBuilder(String.valueOf(methodString).length() + 1 + strValueOf.length());
            sb2.append(methodString);
            sb2.append(":");
            sb2.append(strValueOf);
            String hexString2 = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(sb2.toString())));
            String str4 = this.nonce;
            StringBuilder sb3 = new StringBuilder(String.valueOf(hexString).length() + 2 + String.valueOf(str4).length() + String.valueOf(hexString2).length());
            sb3.append(hexString);
            sb3.append(":");
            sb3.append(str4);
            sb3.append(":");
            sb3.append(hexString2);
            String hexString3 = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(sb3.toString())));
            return this.opaque.isEmpty() ? Util.formatInvariant(DIGEST_FORMAT, rtspAuthUserInfo.username, this.realm, this.nonce, uri, hexString3) : Util.formatInvariant(DIGEST_FORMAT_WITH_OPAQUE, rtspAuthUserInfo.username, this.realm, this.nonce, uri, hexString3, this.opaque);
        } catch (NoSuchAlgorithmException e2) {
            throw ParserException.createForManifestWithUnsupportedFeature(null, e2);
        }
    }

    public String getAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo, Uri uri, int i2) throws ParserException {
        int i3 = this.authenticationMechanism;
        if (i3 == 1) {
            return getBasicAuthorizationHeaderValue(rtspAuthUserInfo);
        }
        if (i3 == 2) {
            return getDigestAuthorizationHeaderValue(rtspAuthUserInfo, uri, i2);
        }
        throw ParserException.createForManifestWithUnsupportedFeature(null, new UnsupportedOperationException());
    }
}
