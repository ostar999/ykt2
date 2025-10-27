package okhttp3.internal.platform;

import com.umeng.analytics.pro.d;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjsse.javax.net.ssl.SSLParameters;
import org.openjsse.net.ssl.OpenJSSE;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0011\u0010\u000b\u001a\r\u0012\t\u0012\u00070\r¢\u0006\u0002\b\u000e0\fH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lokhttp3/internal/platform/OpenJSSEPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", d.M, "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "platformTrustManager", "Ljavax/net/ssl/X509TrustManager;", "trustManager", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class OpenJSSEPlatform extends Platform {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE;
    private static final boolean isSupported;

    @NotNull
    private final Provider provider;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\b"}, d2 = {"Lokhttp3/internal/platform/OpenJSSEPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/OpenJSSEPlatform;", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final OpenJSSEPlatform buildIfSupported() {
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (isSupported()) {
                return new OpenJSSEPlatform(defaultConstructorMarker);
            }
            return null;
        }

        public final boolean isSupported() {
            return OpenJSSEPlatform.isSupported;
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        boolean z2 = false;
        try {
            Class.forName("org.openjsse.net.ssl.OpenJSSE", false, companion.getClass().getClassLoader());
            z2 = true;
        } catch (ClassNotFoundException unused) {
        }
        isSupported = z2;
    }

    private OpenJSSEPlatform() {
        this.provider = new OpenJSSE();
    }

    public /* synthetic */ OpenJSSEPlatform(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // okhttp3.internal.platform.Platform
    public void configureTlsExtensions(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<Protocol> protocols) {
        Intrinsics.checkNotNullParameter(sslSocket, "sslSocket");
        Intrinsics.checkNotNullParameter(protocols, "protocols");
        if (!(sslSocket instanceof org.openjsse.javax.net.ssl.SSLSocket)) {
            super.configureTlsExtensions(sslSocket, hostname, protocols);
            return;
        }
        org.openjsse.javax.net.ssl.SSLSocket sSLSocket = (org.openjsse.javax.net.ssl.SSLSocket) sslSocket;
        SSLParameters sSLParameters = sSLSocket.getSSLParameters();
        if (sSLParameters instanceof SSLParameters) {
            SSLParameters sSLParameters2 = sSLParameters;
            Object[] array = Platform.INSTANCE.alpnProtocolNames(protocols).toArray(new String[0]);
            if (array == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
            sSLParameters2.setApplicationProtocols((String[]) array);
            sSLSocket.setSSLParameters(sSLParameters);
        }
    }

    @Override // okhttp3.internal.platform.Platform
    @Nullable
    public String getSelectedProtocol(@NotNull SSLSocket sslSocket) {
        Intrinsics.checkNotNullParameter(sslSocket, "sslSocket");
        if (!(sslSocket instanceof org.openjsse.javax.net.ssl.SSLSocket)) {
            return super.getSelectedProtocol(sslSocket);
        }
        String applicationProtocol = ((org.openjsse.javax.net.ssl.SSLSocket) sslSocket).getApplicationProtocol();
        if (applicationProtocol == null ? true : Intrinsics.areEqual(applicationProtocol, "")) {
            return null;
        }
        return applicationProtocol;
    }

    @Override // okhttp3.internal.platform.Platform
    @NotNull
    public SSLContext newSSLContext() throws NoSuchAlgorithmException {
        SSLContext sSLContext = SSLContext.getInstance("TLSv1.3", this.provider);
        Intrinsics.checkNotNullExpressionValue(sSLContext, "getInstance(\"TLSv1.3\", provider)");
        return sSLContext;
    }

    @Override // okhttp3.internal.platform.Platform
    @NotNull
    public X509TrustManager platformTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm(), this.provider);
        trustManagerFactory.init((KeyStore) null);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        Intrinsics.checkNotNull(trustManagers);
        if (!(trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager))) {
            String string = Arrays.toString(trustManagers);
            Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
            throw new IllegalStateException(Intrinsics.stringPlus("Unexpected default trust managers: ", string).toString());
        }
        TrustManager trustManager = trustManagers[0];
        if (trustManager != null) {
            return (X509TrustManager) trustManager;
        }
        throw new NullPointerException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
    }

    @Override // okhttp3.internal.platform.Platform
    @Nullable
    public X509TrustManager trustManager(@NotNull SSLSocketFactory sslSocketFactory) {
        Intrinsics.checkNotNullParameter(sslSocketFactory, "sslSocketFactory");
        throw new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with OpenJSSE");
    }
}
