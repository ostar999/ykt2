package org.apache.http.conn.ssl;

import com.alipay.sdk.app.statistic.c;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import kotlin.text.Typography;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.util.InetAddressUtils;

@Immutable
/* loaded from: classes9.dex */
public abstract class AbstractVerifier implements X509HostnameVerifier {
    private static final String[] BAD_COUNTRY_2LDS;

    static {
        String[] strArr = {"ac", AliyunLogKey.KEY_CONNECTION, "com", "ed", "edu", "go", "gouv", "gov", AliyunLogCommon.LogLevel.INFO, "lg", "ne", c.f3111a, "or", "org"};
        BAD_COUNTRY_2LDS = strArr;
        Arrays.sort(strArr);
    }

    public static boolean acceptableCountryWildcard(String str) {
        String[] strArrSplit = str.split("\\.");
        return (strArrSplit.length == 3 && strArrSplit[2].length() == 2 && Arrays.binarySearch(BAD_COUNTRY_2LDS, strArrSplit[1]) >= 0) ? false : true;
    }

    public static int countDots(String str) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) == '.') {
                i2++;
            }
        }
        return i2;
    }

    public static String[] getCNs(X509Certificate x509Certificate) {
        LinkedList linkedList = new LinkedList();
        StringTokenizer stringTokenizer = new StringTokenizer(x509Certificate.getSubjectX500Principal().toString(), ",");
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            int iIndexOf = strNextToken.indexOf("CN=");
            if (iIndexOf >= 0) {
                linkedList.add(strNextToken.substring(iIndexOf + 3));
            }
        }
        if (linkedList.isEmpty()) {
            return null;
        }
        String[] strArr = new String[linkedList.size()];
        linkedList.toArray(strArr);
        return strArr;
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509Certificate) {
        return getSubjectAlts(x509Certificate, null);
    }

    private static String[] getSubjectAlts(X509Certificate x509Certificate, String str) throws CertificateParsingException {
        Collection<List<?>> subjectAlternativeNames;
        int i2 = isIPAddress(str) ? 7 : 2;
        LinkedList linkedList = new LinkedList();
        try {
            subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
        } catch (CertificateParsingException e2) {
            Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", (Throwable) e2);
            subjectAlternativeNames = null;
        }
        if (subjectAlternativeNames != null) {
            for (List<?> list : subjectAlternativeNames) {
                if (((Integer) list.get(0)).intValue() == i2) {
                    linkedList.add((String) list.get(1));
                }
            }
        }
        if (linkedList.isEmpty()) {
            return null;
        }
        String[] strArr = new String[linkedList.size()];
        linkedList.toArray(strArr);
        return strArr;
    }

    private static boolean isIPAddress(String str) {
        return str != null && (InetAddressUtils.isIPv4Address(str) || InetAddressUtils.isIPv6Address(str));
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, SSLSocket sSLSocket) throws IOException {
        if (str == null) {
            throw new NullPointerException("host to verify is null");
        }
        SSLSession session = sSLSocket.getSession();
        if (session == null) {
            sSLSocket.getInputStream().available();
            session = sSLSocket.getSession();
            if (session == null) {
                sSLSocket.startHandshake();
                session = sSLSocket.getSession();
            }
        }
        verify(str, (X509Certificate) session.getPeerCertificates()[0]);
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        try {
            verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (SSLException unused) {
            return false;
        }
    }

    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, X509Certificate x509Certificate) throws SSLException {
        verify(str, getCNs(x509Certificate), getSubjectAlts(x509Certificate, str));
    }

    public final void verify(String str, String[] strArr, String[] strArr2, boolean z2) throws SSLException {
        boolean zEndsWith;
        String str2;
        LinkedList linkedList = new LinkedList();
        if (strArr != null && strArr.length > 0 && (str2 = strArr[0]) != null) {
            linkedList.add(str2);
        }
        if (strArr2 != null) {
            for (String str3 : strArr2) {
                if (str3 != null) {
                    linkedList.add(str3);
                }
            }
        }
        if (!linkedList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            String lowerCase = str.trim().toLowerCase(Locale.ENGLISH);
            Iterator it = linkedList.iterator();
            boolean zEquals = false;
            while (it.hasNext()) {
                String lowerCase2 = ((String) it.next()).toLowerCase(Locale.ENGLISH);
                sb.append(" <");
                sb.append(lowerCase2);
                sb.append(Typography.greater);
                if (it.hasNext()) {
                    sb.append(" OR");
                }
                String[] strArrSplit = lowerCase2.split("\\.");
                if (strArrSplit.length >= 3 && strArrSplit[0].endsWith("*") && acceptableCountryWildcard(lowerCase2) && !isIPAddress(str)) {
                    if (strArrSplit[0].length() > 1) {
                        String strSubstring = strArrSplit[0].substring(0, strArrSplit.length - 2);
                        zEndsWith = lowerCase.startsWith(strSubstring) && lowerCase.substring(strSubstring.length()).endsWith(lowerCase2.substring(strArrSplit[0].length()));
                    } else {
                        zEndsWith = lowerCase.endsWith(lowerCase2.substring(1));
                    }
                    if (zEndsWith && z2) {
                        zEquals = countDots(lowerCase) == countDots(lowerCase2);
                    } else {
                        zEquals = zEndsWith;
                    }
                } else {
                    zEquals = lowerCase.equals(lowerCase2);
                }
                if (zEquals) {
                    break;
                }
            }
            if (zEquals) {
                return;
            }
            throw new SSLException("hostname in certificate didn't match: <" + str + "> !=" + ((Object) sb));
        }
        throw new SSLException("Certificate for <" + str + "> doesn't contain CN or DNS subjectAlt");
    }
}
