package cn.hutool.core.net;

import cn.hutool.core.io.IORuntimeException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* loaded from: classes.dex */
public class SSLUtil {
    public static SSLContext createSSLContext(String str) throws IORuntimeException {
        return SSLContextBuilder.create().setProtocol(str).build();
    }

    public static SSLContext createSSLContext(String str, KeyManager keyManager, TrustManager trustManager) throws IORuntimeException {
        return createSSLContext(str, keyManager == null ? null : new KeyManager[]{keyManager}, trustManager != null ? new TrustManager[]{trustManager} : null);
    }

    public static SSLContext createSSLContext(String str, KeyManager[] keyManagerArr, TrustManager[] trustManagerArr) throws IORuntimeException {
        return SSLContextBuilder.create().setProtocol(str).setKeyManagers(keyManagerArr).setTrustManagers(trustManagerArr).build();
    }
}
