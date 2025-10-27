package cn.hutool.core.net;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* loaded from: classes.dex */
public class SSLContextBuilder implements SSLProtocols, Builder<SSLContext> {
    private static final long serialVersionUID = 1;
    private KeyManager[] keyManagers;
    private String protocol = "TLS";
    private TrustManager[] trustManagers = {DefaultTrustManager.INSTANCE};
    private SecureRandom secureRandom = new SecureRandom();

    public static SSLContextBuilder create() {
        return new SSLContextBuilder();
    }

    public SSLContext buildChecked() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sSLContext = SSLContext.getInstance(this.protocol);
        sSLContext.init(this.keyManagers, this.trustManagers, this.secureRandom);
        return sSLContext;
    }

    public SSLContext buildQuietly() throws IORuntimeException {
        try {
            return buildChecked();
        } catch (GeneralSecurityException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public SSLContextBuilder setKeyManagers(KeyManager... keyManagerArr) {
        if (ArrayUtil.isNotEmpty((Object[]) keyManagerArr)) {
            this.keyManagers = keyManagerArr;
        }
        return this;
    }

    public SSLContextBuilder setProtocol(String str) {
        if (CharSequenceUtil.isNotBlank(str)) {
            this.protocol = str;
        }
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom secureRandom) {
        if (secureRandom != null) {
            this.secureRandom = secureRandom;
        }
        return this;
    }

    public SSLContextBuilder setTrustManagers(TrustManager... trustManagerArr) {
        if (ArrayUtil.isNotEmpty((Object[]) trustManagerArr)) {
            this.trustManagers = trustManagerArr;
        }
        return this;
    }

    @Override // cn.hutool.core.builder.Builder
    public SSLContext build() {
        return buildQuietly();
    }
}
