package org.apache.http.impl.auth;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.StringTokenizer;
import k.a;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthParams;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

@NotThreadSafe
/* loaded from: classes9.dex */
public class DigestScheme extends RFC2617Scheme {
    private static final char[] HEXADECIMAL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_UNKNOWN = -1;

    /* renamed from: a1, reason: collision with root package name */
    private String f27755a1;

    /* renamed from: a2, reason: collision with root package name */
    private String f27756a2;
    private String cnonce;
    private boolean complete = false;
    private String lastNonce;
    private long nounceCount;

    public static String createCnonce() {
        byte[] bArr = new byte[8];
        new SecureRandom().nextBytes(bArr);
        return encode(bArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Header createDigestHeader(Credentials credentials) throws AuthenticationException {
        String str;
        char c3;
        MessageDigest messageDigest;
        String str2;
        MessageDigest messageDigest2;
        String str3;
        String str4;
        int i2;
        String string;
        String str5;
        String parameter = getParameter("uri");
        String parameter2 = getParameter("realm");
        String parameter3 = getParameter("nonce");
        String parameter4 = getParameter("opaque");
        String parameter5 = getParameter("methodname");
        String parameter6 = getParameter("algorithm");
        if (parameter == null) {
            throw new IllegalStateException("URI may not be null");
        }
        if (parameter2 == null) {
            throw new IllegalStateException("Realm may not be null");
        }
        if (parameter3 == null) {
            throw new IllegalStateException("Nonce may not be null");
        }
        String parameter7 = getParameter("qop");
        if (parameter7 != null) {
            str = "opaque";
            StringTokenizer stringTokenizer = new StringTokenizer(parameter7, ",");
            while (true) {
                if (!stringTokenizer.hasMoreTokens()) {
                    c3 = 65535;
                    break;
                }
                if (stringTokenizer.nextToken().trim().equals("auth")) {
                    c3 = 2;
                    break;
                }
            }
        } else {
            str = "opaque";
            c3 = 0;
        }
        if (c3 == 65535) {
            throw new AuthenticationException("None of the qop methods is supported: " + parameter7);
        }
        if (parameter6 == null) {
            parameter6 = "MD5";
        }
        String parameter8 = getParameter("charset");
        if (parameter8 == null) {
            parameter8 = "ISO-8859-1";
        }
        String str6 = parameter6.equalsIgnoreCase("MD5-sess") ? "MD5" : parameter6;
        try {
            MessageDigest messageDigestCreateMessageDigest = createMessageDigest(str6);
            String name = credentials.getUserPrincipal().getName();
            String password = credentials.getPassword();
            if (parameter3.equals(this.lastNonce)) {
                messageDigest = messageDigestCreateMessageDigest;
                str2 = parameter8;
                this.nounceCount++;
            } else {
                messageDigest = messageDigestCreateMessageDigest;
                str2 = parameter8;
                this.nounceCount = 1L;
                this.cnonce = null;
                this.lastNonce = parameter3;
            }
            StringBuilder sb = new StringBuilder(256);
            new Formatter(sb, Locale.US).format("%08x", Long.valueOf(this.nounceCount));
            String string2 = sb.toString();
            if (this.cnonce == null) {
                this.cnonce = createCnonce();
            }
            this.f27755a1 = null;
            this.f27756a2 = null;
            if (parameter6.equalsIgnoreCase("MD5-sess")) {
                sb.setLength(0);
                sb.append(name);
                sb.append(':');
                sb.append(parameter2);
                sb.append(':');
                sb.append(password);
                messageDigest2 = messageDigest;
                String strEncode = encode(messageDigest2.digest(EncodingUtils.getBytes(sb.toString(), str2)));
                sb.setLength(0);
                sb.append(strEncode);
                sb.append(':');
                str3 = parameter3;
                sb.append(str3);
                sb.append(':');
                sb.append(this.cnonce);
                this.f27755a1 = sb.toString();
            } else {
                messageDigest2 = messageDigest;
                str3 = parameter3;
                sb.setLength(0);
                sb.append(name);
                sb.append(':');
                sb.append(parameter2);
                sb.append(':');
                sb.append(password);
                this.f27755a1 = sb.toString();
            }
            String strEncode2 = encode(messageDigest2.digest(EncodingUtils.getBytes(this.f27755a1, str2)));
            if (c3 == 2) {
                this.f27756a2 = parameter5 + ':' + parameter;
                str4 = parameter;
            } else {
                str4 = parameter;
                if (c3 == 1) {
                    throw new AuthenticationException("qop-int method is not suppported");
                }
                this.f27756a2 = parameter5 + ':' + str4;
            }
            String strEncode3 = encode(messageDigest2.digest(EncodingUtils.getBytes(this.f27756a2, str2)));
            if (c3 == 0) {
                i2 = 0;
                sb.setLength(0);
                sb.append(strEncode2);
                sb.append(':');
                sb.append(str3);
                sb.append(':');
                sb.append(strEncode3);
                string = sb.toString();
            } else {
                i2 = 0;
                sb.setLength(0);
                sb.append(strEncode2);
                sb.append(':');
                sb.append(str3);
                sb.append(':');
                sb.append(string2);
                sb.append(':');
                sb.append(this.cnonce);
                sb.append(':');
                sb.append(c3 == 1 ? "auth-int" : "auth");
                sb.append(':');
                sb.append(strEncode3);
                string = sb.toString();
            }
            String strEncode4 = encode(messageDigest2.digest(EncodingUtils.getAsciiBytes(string)));
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
            if (isProxy()) {
                charArrayBuffer.append("Proxy-Authorization");
            } else {
                charArrayBuffer.append("Authorization");
            }
            charArrayBuffer.append(": Digest ");
            ArrayList arrayList = new ArrayList(20);
            arrayList.add(new BasicNameValuePair("username", name));
            arrayList.add(new BasicNameValuePair("realm", parameter2));
            arrayList.add(new BasicNameValuePair("nonce", str3));
            arrayList.add(new BasicNameValuePair("uri", str4));
            arrayList.add(new BasicNameValuePair("response", strEncode4));
            if (c3 != 0) {
                str5 = "qop";
                arrayList.add(new BasicNameValuePair(str5, c3 != 1 ? "auth" : "auth-int"));
                arrayList.add(new BasicNameValuePair("nc", string2));
                arrayList.add(new BasicNameValuePair("cnonce", this.cnonce));
            } else {
                str5 = "qop";
            }
            arrayList.add(new BasicNameValuePair("algorithm", parameter6));
            if (parameter4 != null) {
                arrayList.add(new BasicNameValuePair(str, parameter4));
            }
            for (int i3 = i2; i3 < arrayList.size(); i3++) {
                NameValuePair nameValuePair = (BasicNameValuePair) arrayList.get(i3);
                if (i3 > 0) {
                    charArrayBuffer.append(", ");
                }
                BasicHeaderValueFormatter.DEFAULT.formatNameValuePair(charArrayBuffer, nameValuePair, (("nc".equals(nameValuePair.getName()) || str5.equals(nameValuePair.getName())) ? 1 : i2) ^ 1);
            }
            return new BufferedHeader(charArrayBuffer);
        } catch (UnsupportedDigestAlgorithmException unused) {
            throw new AuthenticationException("Unsuppported digest algorithm: " + str6);
        }
    }

    private static MessageDigest createMessageDigest(String str) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(str);
        } catch (Exception unused) {
            throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + str);
        }
    }

    private static String encode(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length * 2];
        for (int i2 = 0; i2 < length; i2++) {
            byte b3 = bArr[i2];
            int i3 = i2 * 2;
            char[] cArr2 = HEXADECIMAL;
            cArr[i3] = cArr2[(b3 & 240) >> 4];
            cArr[i3 + 1] = cArr2[b3 & 15];
        }
        return new String(cArr);
    }

    @Override // org.apache.http.auth.AuthScheme
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        if (credentials == null) {
            throw new IllegalArgumentException("Credentials may not be null");
        }
        if (httpRequest == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        getParameters().put("methodname", httpRequest.getRequestLine().getMethod());
        getParameters().put("uri", httpRequest.getRequestLine().getUri());
        if (getParameter("charset") == null) {
            getParameters().put("charset", AuthParams.getCredentialCharset(httpRequest.getParams()));
        }
        return createDigestHeader(credentials);
    }

    public String getA1() {
        return this.f27755a1;
    }

    public String getA2() {
        return this.f27756a2;
    }

    public String getCnonce() {
        return this.cnonce;
    }

    @Override // org.apache.http.auth.AuthScheme
    public String getSchemeName() {
        return "digest";
    }

    @Override // org.apache.http.auth.AuthScheme
    public boolean isComplete() {
        if (a.f27523u.equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.complete;
    }

    @Override // org.apache.http.auth.AuthScheme
    public boolean isConnectionBased() {
        return false;
    }

    public void overrideParamter(String str, String str2) {
        getParameters().put(str, str2);
    }

    @Override // org.apache.http.impl.auth.AuthSchemeBase, org.apache.http.auth.AuthScheme
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        if (getParameter("realm") == null) {
            throw new MalformedChallengeException("missing realm in challenge");
        }
        if (getParameter("nonce") == null) {
            throw new MalformedChallengeException("missing nonce in challenge");
        }
        this.complete = true;
    }
}
