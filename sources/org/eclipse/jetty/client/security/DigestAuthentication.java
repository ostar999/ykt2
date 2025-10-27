package org.eclipse.jetty.client.security;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.http.client.params.AuthPolicy;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.http.HttpTokens;
import org.eclipse.jetty.util.TypeUtil;

/* loaded from: classes9.dex */
public class DigestAuthentication implements Authentication {
    private static final String NC = "00000001";
    Map details;
    Realm securityRealm;

    public DigestAuthentication(Realm realm, Map map) {
        this.securityRealm = realm;
        this.details = map;
    }

    private static String encode(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < bArr.length; i2++) {
            sb.append(Integer.toHexString((bArr[i2] & 240) >>> 4));
            sb.append(Integer.toHexString(bArr[i2] & 15));
        }
        return sb.toString();
    }

    public String newCnonce(HttpExchange httpExchange, Realm realm, Map map) {
        try {
            return encode(MessageDigest.getInstance("MD5").digest(String.valueOf(System.currentTimeMillis()).getBytes("ISO-8859-1")));
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public String newResponse(String str, HttpExchange httpExchange, Realm realm, Map map) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(realm.getPrincipal().getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(String.valueOf(map.get("realm")).getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(realm.getCredentials().getBytes("ISO-8859-1"));
            byte[] bArrDigest = messageDigest.digest();
            messageDigest.reset();
            messageDigest.update(httpExchange.getMethod().getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(httpExchange.getURI().getBytes("ISO-8859-1"));
            byte[] bArrDigest2 = messageDigest.digest();
            messageDigest.update(TypeUtil.toString(bArrDigest, 16).getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(String.valueOf(map.get("nonce")).getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(NC.getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(str.getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(String.valueOf(map.get("qop")).getBytes("ISO-8859-1"));
            messageDigest.update(HttpTokens.COLON);
            messageDigest.update(TypeUtil.toString(bArrDigest2, 16).getBytes("ISO-8859-1"));
            return encode(messageDigest.digest());
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.eclipse.jetty.client.security.Authentication
    public void setCredentials(HttpExchange httpExchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(AuthPolicy.DIGEST);
        sb.append(" ");
        sb.append("username");
        sb.append('=');
        sb.append('\"');
        sb.append(this.securityRealm.getPrincipal());
        sb.append('\"');
        sb.append(", ");
        sb.append("realm");
        sb.append('=');
        sb.append('\"');
        sb.append(String.valueOf(this.details.get("realm")));
        sb.append('\"');
        sb.append(", ");
        sb.append("nonce");
        sb.append('=');
        sb.append('\"');
        sb.append(String.valueOf(this.details.get("nonce")));
        sb.append('\"');
        sb.append(", ");
        sb.append("uri");
        sb.append('=');
        sb.append('\"');
        sb.append(httpExchange.getURI());
        sb.append('\"');
        sb.append(", ");
        sb.append("algorithm");
        sb.append('=');
        sb.append(String.valueOf(this.details.get("algorithm")));
        String strNewCnonce = newCnonce(httpExchange, this.securityRealm, this.details);
        sb.append(", ");
        sb.append("response");
        sb.append('=');
        sb.append('\"');
        sb.append(newResponse(strNewCnonce, httpExchange, this.securityRealm, this.details));
        sb.append('\"');
        sb.append(", ");
        sb.append("qop");
        sb.append('=');
        sb.append(String.valueOf(this.details.get("qop")));
        sb.append(", ");
        sb.append("nc");
        sb.append('=');
        sb.append(NC);
        sb.append(", ");
        sb.append("cnonce");
        sb.append('=');
        sb.append('\"');
        sb.append(strNewCnonce);
        sb.append('\"');
        httpExchange.setRequestHeader("Authorization", new String(sb.toString().getBytes("ISO-8859-1")));
    }
}
