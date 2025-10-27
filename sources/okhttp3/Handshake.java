package okhttp3;

import com.yikaobang.yixue.R2;
import io.socket.engineio.client.Socket;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import org.eclipse.jetty.util.security.Constraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 &2\u00020\u0001:\u0001&B9\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n¢\u0006\u0002\u0010\u000bJ\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b J\u000f\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\b!J\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b\"J\u000f\u0010\u0014\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\b#J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b$J\b\u0010%\u001a\u00020\u0017H\u0016R\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f8G¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00078GX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u000f8G¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0018\u0010\u0016\u001a\u00020\u0017*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lokhttp3/Handshake;", "", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "localCertificates", "", "Ljava/security/cert/Certificate;", "peerCertificatesFn", "Lkotlin/Function0;", "(Lokhttp3/TlsVersion;Lokhttp3/CipherSuite;Ljava/util/List;Lkotlin/jvm/functions/Function0;)V", "()Lokhttp3/CipherSuite;", "()Ljava/util/List;", "localPrincipal", "Ljava/security/Principal;", "()Ljava/security/Principal;", "peerCertificates", "peerCertificates$delegate", "Lkotlin/Lazy;", "peerPrincipal", "()Lokhttp3/TlsVersion;", "name", "", "getName", "(Ljava/security/cert/Certificate;)Ljava/lang/String;", "-deprecated_cipherSuite", "equals", "", "other", "hashCode", "", "-deprecated_localCertificates", "-deprecated_localPrincipal", "-deprecated_peerCertificates", "-deprecated_peerPrincipal", "-deprecated_tlsVersion", "toString", "Companion", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class Handshake {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final CipherSuite cipherSuite;

    @NotNull
    private final List<Certificate> localCertificates;

    /* renamed from: peerCertificates$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy peerCertificates;

    @NotNull
    private final TlsVersion tlsVersion;

    /* JADX WARN: Multi-variable type inference failed */
    public Handshake(@NotNull TlsVersion tlsVersion, @NotNull CipherSuite cipherSuite, @NotNull List<? extends Certificate> localCertificates, @NotNull final Function0<? extends List<? extends Certificate>> peerCertificatesFn) {
        Intrinsics.checkNotNullParameter(tlsVersion, "tlsVersion");
        Intrinsics.checkNotNullParameter(cipherSuite, "cipherSuite");
        Intrinsics.checkNotNullParameter(localCertificates, "localCertificates");
        Intrinsics.checkNotNullParameter(peerCertificatesFn, "peerCertificatesFn");
        this.tlsVersion = tlsVersion;
        this.cipherSuite = cipherSuite;
        this.localCertificates = localCertificates;
        this.peerCertificates = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends Certificate>>() { // from class: okhttp3.Handshake.peerCertificates.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends Certificate> invoke() {
                try {
                    return peerCertificatesFn.invoke();
                } catch (SSLPeerUnverifiedException unused) {
                    return CollectionsKt__CollectionsKt.emptyList();
                }
            }
        });
    }

    @JvmStatic
    @JvmName(name = "get")
    @NotNull
    public static final Handshake get(@NotNull SSLSession sSLSession) throws IOException {
        return INSTANCE.get(sSLSession);
    }

    @JvmStatic
    @NotNull
    public static final Handshake get(@NotNull TlsVersion tlsVersion, @NotNull CipherSuite cipherSuite, @NotNull List<? extends Certificate> list, @NotNull List<? extends Certificate> list2) {
        return INSTANCE.get(tlsVersion, cipherSuite, list, list2);
    }

    private final String getName(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return ((X509Certificate) certificate).getSubjectDN().toString();
        }
        String type = certificate.getType();
        Intrinsics.checkNotNullExpressionValue(type, "type");
        return type;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cipherSuite", imports = {}))
    @JvmName(name = "-deprecated_cipherSuite")
    @NotNull
    /* renamed from: -deprecated_cipherSuite, reason: not valid java name and from getter */
    public final CipherSuite getCipherSuite() {
        return this.cipherSuite;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "localCertificates", imports = {}))
    @JvmName(name = "-deprecated_localCertificates")
    @NotNull
    /* renamed from: -deprecated_localCertificates, reason: not valid java name */
    public final List<Certificate> m2457deprecated_localCertificates() {
        return this.localCertificates;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "localPrincipal", imports = {}))
    @JvmName(name = "-deprecated_localPrincipal")
    @Nullable
    /* renamed from: -deprecated_localPrincipal, reason: not valid java name */
    public final Principal m2458deprecated_localPrincipal() {
        return localPrincipal();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "peerCertificates", imports = {}))
    @JvmName(name = "-deprecated_peerCertificates")
    @NotNull
    /* renamed from: -deprecated_peerCertificates, reason: not valid java name */
    public final List<Certificate> m2459deprecated_peerCertificates() {
        return peerCertificates();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "peerPrincipal", imports = {}))
    @JvmName(name = "-deprecated_peerPrincipal")
    @Nullable
    /* renamed from: -deprecated_peerPrincipal, reason: not valid java name */
    public final Principal m2460deprecated_peerPrincipal() {
        return peerPrincipal();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "tlsVersion", imports = {}))
    @JvmName(name = "-deprecated_tlsVersion")
    @NotNull
    /* renamed from: -deprecated_tlsVersion, reason: not valid java name and from getter */
    public final TlsVersion getTlsVersion() {
        return this.tlsVersion;
    }

    @JvmName(name = "cipherSuite")
    @NotNull
    public final CipherSuite cipherSuite() {
        return this.cipherSuite;
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof Handshake) {
            Handshake handshake = (Handshake) other;
            if (handshake.tlsVersion == this.tlsVersion && Intrinsics.areEqual(handshake.cipherSuite, this.cipherSuite) && Intrinsics.areEqual(handshake.peerCertificates(), peerCertificates()) && Intrinsics.areEqual(handshake.localCertificates, this.localCertificates)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((R2.attr.bl_checkable_gradient_endColor + this.tlsVersion.hashCode()) * 31) + this.cipherSuite.hashCode()) * 31) + peerCertificates().hashCode()) * 31) + this.localCertificates.hashCode();
    }

    @JvmName(name = "localCertificates")
    @NotNull
    public final List<Certificate> localCertificates() {
        return this.localCertificates;
    }

    @JvmName(name = "localPrincipal")
    @Nullable
    public final Principal localPrincipal() {
        Object objFirstOrNull = CollectionsKt___CollectionsKt.firstOrNull((List<? extends Object>) this.localCertificates);
        X509Certificate x509Certificate = objFirstOrNull instanceof X509Certificate ? (X509Certificate) objFirstOrNull : null;
        if (x509Certificate == null) {
            return null;
        }
        return x509Certificate.getSubjectX500Principal();
    }

    @JvmName(name = "peerCertificates")
    @NotNull
    public final List<Certificate> peerCertificates() {
        return (List) this.peerCertificates.getValue();
    }

    @JvmName(name = "peerPrincipal")
    @Nullable
    public final Principal peerPrincipal() {
        Object objFirstOrNull = CollectionsKt___CollectionsKt.firstOrNull((List<? extends Object>) peerCertificates());
        X509Certificate x509Certificate = objFirstOrNull instanceof X509Certificate ? (X509Certificate) objFirstOrNull : null;
        if (x509Certificate == null) {
            return null;
        }
        return x509Certificate.getSubjectX500Principal();
    }

    @JvmName(name = "tlsVersion")
    @NotNull
    public final TlsVersion tlsVersion() {
        return this.tlsVersion;
    }

    @NotNull
    public String toString() {
        List<Certificate> listPeerCertificates = peerCertificates();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listPeerCertificates, 10));
        Iterator<T> it = listPeerCertificates.iterator();
        while (it.hasNext()) {
            arrayList.add(getName((Certificate) it.next()));
        }
        String string = arrayList.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("Handshake{tlsVersion=");
        sb.append(this.tlsVersion);
        sb.append(" cipherSuite=");
        sb.append(this.cipherSuite);
        sb.append(" peerCertificates=");
        sb.append(string);
        sb.append(" localCertificates=");
        List<Certificate> list = this.localCertificates;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList2.add(getName((Certificate) it2.next()));
        }
        sb.append(arrayList2);
        sb.append('}');
        return sb.toString();
    }

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0007J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0007J\u0011\u0010\u0010\u001a\u00020\u0004*\u00020\u0006H\u0007¢\u0006\u0002\b\u0003J!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\f\u0012\u0006\b\u0001\u0012\u00020\u000e\u0018\u00010\u0012H\u0002¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"Lokhttp3/Handshake$Companion;", "", "()V", "get", "Lokhttp3/Handshake;", "sslSession", "Ljavax/net/ssl/SSLSession;", "-deprecated_get", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "peerCertificates", "", "Ljava/security/cert/Certificate;", "localCertificates", Socket.EVENT_HANDSHAKE, "toImmutableList", "", "([Ljava/security/cert/Certificate;)Ljava/util/List;", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final List<Certificate> toImmutableList(Certificate[] certificateArr) {
            return certificateArr != null ? Util.immutableListOf(Arrays.copyOf(certificateArr, certificateArr.length)) : CollectionsKt__CollectionsKt.emptyList();
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "sslSession.handshake()", imports = {}))
        @JvmName(name = "-deprecated_get")
        @NotNull
        /* renamed from: -deprecated_get, reason: not valid java name */
        public final Handshake m2462deprecated_get(@NotNull SSLSession sslSession) throws IOException {
            Intrinsics.checkNotNullParameter(sslSession, "sslSession");
            return get(sslSession);
        }

        @JvmStatic
        @JvmName(name = "get")
        @NotNull
        public final Handshake get(@NotNull SSLSession sSLSession) throws IOException {
            final List<Certificate> listEmptyList;
            Intrinsics.checkNotNullParameter(sSLSession, "<this>");
            String cipherSuite = sSLSession.getCipherSuite();
            if (cipherSuite == null) {
                throw new IllegalStateException("cipherSuite == null".toString());
            }
            if (Intrinsics.areEqual(cipherSuite, "TLS_NULL_WITH_NULL_NULL") ? true : Intrinsics.areEqual(cipherSuite, "SSL_NULL_WITH_NULL_NULL")) {
                throw new IOException(Intrinsics.stringPlus("cipherSuite == ", cipherSuite));
            }
            CipherSuite cipherSuiteForJavaName = CipherSuite.INSTANCE.forJavaName(cipherSuite);
            String protocol = sSLSession.getProtocol();
            if (protocol == null) {
                throw new IllegalStateException("tlsVersion == null".toString());
            }
            if (Intrinsics.areEqual(Constraint.NONE, protocol)) {
                throw new IOException("tlsVersion == NONE");
            }
            TlsVersion tlsVersionForJavaName = TlsVersion.INSTANCE.forJavaName(protocol);
            try {
                listEmptyList = toImmutableList(sSLSession.getPeerCertificates());
            } catch (SSLPeerUnverifiedException unused) {
                listEmptyList = CollectionsKt__CollectionsKt.emptyList();
            }
            return new Handshake(tlsVersionForJavaName, cipherSuiteForJavaName, toImmutableList(sSLSession.getLocalCertificates()), new Function0<List<? extends Certificate>>() { // from class: okhttp3.Handshake$Companion$handshake$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final List<? extends Certificate> invoke() {
                    return listEmptyList;
                }
            });
        }

        @JvmStatic
        @NotNull
        public final Handshake get(@NotNull TlsVersion tlsVersion, @NotNull CipherSuite cipherSuite, @NotNull List<? extends Certificate> peerCertificates, @NotNull List<? extends Certificate> localCertificates) {
            Intrinsics.checkNotNullParameter(tlsVersion, "tlsVersion");
            Intrinsics.checkNotNullParameter(cipherSuite, "cipherSuite");
            Intrinsics.checkNotNullParameter(peerCertificates, "peerCertificates");
            Intrinsics.checkNotNullParameter(localCertificates, "localCertificates");
            final List immutableList = Util.toImmutableList(peerCertificates);
            return new Handshake(tlsVersion, cipherSuite, Util.toImmutableList(localCertificates), new Function0<List<? extends Certificate>>() { // from class: okhttp3.Handshake$Companion$get$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final List<? extends Certificate> invoke() {
                    return immutableList;
                }
            });
        }
    }
}
