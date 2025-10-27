package okhttp3.internal.tls;

import cn.hutool.core.text.StrPool;
import com.alipay.sdk.cons.c;
import com.umeng.analytics.pro.d;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okio.Utf8;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\f\u0010\u0017\u001a\u00020\b*\u00020\bH\u0002J\f\u0010\u0018\u001a\u00020\u000e*\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lokhttp3/internal/tls/OkHostnameVerifier;", "Ljavax/net/ssl/HostnameVerifier;", "()V", "ALT_DNS_NAME", "", "ALT_IPA_NAME", "allSubjectAltNames", "", "", "certificate", "Ljava/security/cert/X509Certificate;", "getSubjectAltNames", "type", "verify", "", c.f3231f, d.aw, "Ljavax/net/ssl/SSLSession;", "verifyHostname", "hostname", "pattern", "verifyIpAddress", "ipAddress", "asciiToLowercase", "isAscii", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class OkHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;

    @NotNull
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();

    private OkHostnameVerifier() {
    }

    private final String asciiToLowercase(String str) {
        if (!isAscii(str)) {
            return str;
        }
        Locale US = Locale.US;
        Intrinsics.checkNotNullExpressionValue(US, "US");
        String lowerCase = str.toLowerCase(US);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        return lowerCase;
    }

    private final List<String> getSubjectAltNames(X509Certificate certificate, int type) throws CertificateParsingException {
        Object obj;
        try {
            Collection<List<?>> subjectAlternativeNames = certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return CollectionsKt__CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (List<?> list : subjectAlternativeNames) {
                if (list != null && list.size() >= 2 && Intrinsics.areEqual(list.get(0), Integer.valueOf(type)) && (obj = list.get(1)) != null) {
                    arrayList.add((String) obj);
                }
            }
            return arrayList;
        } catch (CertificateParsingException unused) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
    }

    private final boolean isAscii(String str) {
        return str.length() == ((int) Utf8.size$default(str, 0, 0, 3, null));
    }

    private final boolean verifyHostname(String hostname, X509Certificate certificate) throws CertificateParsingException {
        String strAsciiToLowercase = asciiToLowercase(hostname);
        List<String> subjectAltNames = getSubjectAltNames(certificate, 2);
        if ((subjectAltNames instanceof Collection) && subjectAltNames.isEmpty()) {
            return false;
        }
        Iterator<T> it = subjectAltNames.iterator();
        while (it.hasNext()) {
            if (INSTANCE.verifyHostname(strAsciiToLowercase, (String) it.next())) {
                return true;
            }
        }
        return false;
    }

    private final boolean verifyIpAddress(String ipAddress, X509Certificate certificate) throws CertificateParsingException {
        String canonicalHost = HostnamesKt.toCanonicalHost(ipAddress);
        List<String> subjectAltNames = getSubjectAltNames(certificate, 7);
        if ((subjectAltNames instanceof Collection) && subjectAltNames.isEmpty()) {
            return false;
        }
        Iterator<T> it = subjectAltNames.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(canonicalHost, HostnamesKt.toCanonicalHost((String) it.next()))) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public final List<String> allSubjectAltNames(@NotNull X509Certificate certificate) throws CertificateParsingException {
        Intrinsics.checkNotNullParameter(certificate, "certificate");
        return CollectionsKt___CollectionsKt.plus((Collection) getSubjectAltNames(certificate, 7), (Iterable) getSubjectAltNames(certificate, 2));
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(@NotNull String host, @NotNull SSLSession session) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!isAscii(host)) {
            return false;
        }
        try {
            Certificate certificate = session.getPeerCertificates()[0];
            if (certificate != null) {
                return verify(host, (X509Certificate) certificate);
            }
            throw new NullPointerException("null cannot be cast to non-null type java.security.cert.X509Certificate");
        } catch (SSLException unused) {
            return false;
        }
    }

    public final boolean verify(@NotNull String host, @NotNull X509Certificate certificate) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(certificate, "certificate");
        return Util.canParseAsIpAddress(host) ? verifyIpAddress(host, certificate) : verifyHostname(host, certificate);
    }

    private final boolean verifyHostname(String hostname, String pattern) {
        if (!(hostname == null || hostname.length() == 0) && !StringsKt__StringsJVMKt.startsWith$default(hostname, StrPool.DOT, false, 2, null) && !StringsKt__StringsJVMKt.endsWith$default(hostname, StrPool.DOUBLE_DOT, false, 2, null)) {
            if (!(pattern == null || pattern.length() == 0) && !StringsKt__StringsJVMKt.startsWith$default(pattern, StrPool.DOT, false, 2, null) && !StringsKt__StringsJVMKt.endsWith$default(pattern, StrPool.DOUBLE_DOT, false, 2, null)) {
                if (!StringsKt__StringsJVMKt.endsWith$default(hostname, StrPool.DOT, false, 2, null)) {
                    hostname = Intrinsics.stringPlus(hostname, StrPool.DOT);
                }
                String str = hostname;
                if (!StringsKt__StringsJVMKt.endsWith$default(pattern, StrPool.DOT, false, 2, null)) {
                    pattern = Intrinsics.stringPlus(pattern, StrPool.DOT);
                }
                String strAsciiToLowercase = asciiToLowercase(pattern);
                if (!StringsKt__StringsKt.contains$default((CharSequence) strAsciiToLowercase, (CharSequence) "*", false, 2, (Object) null)) {
                    return Intrinsics.areEqual(str, strAsciiToLowercase);
                }
                if (!StringsKt__StringsJVMKt.startsWith$default(strAsciiToLowercase, "*.", false, 2, null) || StringsKt__StringsKt.indexOf$default((CharSequence) strAsciiToLowercase, '*', 1, false, 4, (Object) null) != -1 || str.length() < strAsciiToLowercase.length() || Intrinsics.areEqual("*.", strAsciiToLowercase)) {
                    return false;
                }
                String strSubstring = strAsciiToLowercase.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                if (!StringsKt__StringsJVMKt.endsWith$default(str, strSubstring, false, 2, null)) {
                    return false;
                }
                int length = str.length() - strSubstring.length();
                return length <= 0 || StringsKt__StringsKt.lastIndexOf$default((CharSequence) str, '.', length + (-1), false, 4, (Object) null) == -1;
            }
        }
        return false;
    }
}
