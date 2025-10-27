package org.apache.http.client.utils;

import cn.hutool.core.text.StrPool;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Stack;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class URIUtils {
    private URIUtils() {
    }

    public static URI createURI(String str, String str2, int i2, String str3, String str4, String str5) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        if (str2 != null) {
            if (str != null) {
                sb.append(str);
                sb.append("://");
            }
            sb.append(str2);
            if (i2 > 0) {
                sb.append(':');
                sb.append(i2);
            }
        }
        if (str3 == null || !str3.startsWith("/")) {
            sb.append('/');
        }
        if (str3 != null) {
            sb.append(str3);
        }
        if (str4 != null) {
            sb.append('?');
            sb.append(str4);
        }
        if (str5 != null) {
            sb.append('#');
            sb.append(str5);
        }
        return new URI(sb.toString());
    }

    public static HttpHost extractHost(URI uri) throws NumberFormatException {
        int iIndexOf;
        if (uri == null || !uri.isAbsolute()) {
            return null;
        }
        int port = uri.getPort();
        String host = uri.getHost();
        if (host == null && (host = uri.getAuthority()) != null) {
            int iIndexOf2 = host.indexOf(64);
            if (iIndexOf2 >= 0) {
                int i2 = iIndexOf2 + 1;
                host = host.length() > i2 ? host.substring(i2) : null;
            }
            if (host != null && (iIndexOf = host.indexOf(58)) >= 0) {
                int i3 = iIndexOf + 1;
                if (i3 < host.length()) {
                    port = Integer.parseInt(host.substring(i3));
                }
                host = host.substring(0, iIndexOf);
            }
        }
        String scheme = uri.getScheme();
        if (host != null) {
            return new HttpHost(host, port, scheme);
        }
        return null;
    }

    private static String normalizePath(String str) {
        if (str == null) {
            return null;
        }
        int i2 = 0;
        while (i2 < str.length() && str.charAt(i2) == '/') {
            i2++;
        }
        return i2 > 1 ? str.substring(i2 - 1) : str;
    }

    private static URI removeDotSegments(URI uri) {
        String path = uri.getPath();
        if (path == null || path.indexOf("/.") == -1) {
            return uri;
        }
        String[] strArrSplit = path.split("/");
        Stack stack = new Stack();
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (strArrSplit[i2].length() != 0 && !StrPool.DOT.equals(strArrSplit[i2])) {
                if (!StrPool.DOUBLE_DOT.equals(strArrSplit[i2])) {
                    stack.push(strArrSplit[i2]);
                } else if (!stack.isEmpty()) {
                    stack.pop();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = stack.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            sb.append('/');
            sb.append(str);
        }
        try {
            return new URI(uri.getScheme(), uri.getAuthority(), sb.toString(), uri.getQuery(), uri.getFragment());
        } catch (URISyntaxException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static URI resolve(URI uri, String str) {
        return resolve(uri, URI.create(str));
    }

    private static URI resolveReferenceStartingWithQueryString(URI uri, URI uri2) {
        String string = uri.toString();
        if (string.indexOf(63) > -1) {
            string = string.substring(0, string.indexOf(63));
        }
        return URI.create(string + uri2.toString());
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost, boolean z2) throws URISyntaxException {
        if (uri == null) {
            throw new IllegalArgumentException("URI may nor be null");
        }
        if (httpHost != null) {
            return createURI(httpHost.getSchemeName(), httpHost.getHostName(), httpHost.getPort(), normalizePath(uri.getRawPath()), uri.getRawQuery(), z2 ? null : uri.getRawFragment());
        }
        return createURI(null, null, -1, normalizePath(uri.getRawPath()), uri.getRawQuery(), z2 ? null : uri.getRawFragment());
    }

    public static URI resolve(URI uri, URI uri2) {
        if (uri == null) {
            throw new IllegalArgumentException("Base URI may nor be null");
        }
        if (uri2 == null) {
            throw new IllegalArgumentException("Reference URI may nor be null");
        }
        String string = uri2.toString();
        if (string.startsWith("?")) {
            return resolveReferenceStartingWithQueryString(uri, uri2);
        }
        boolean z2 = string.length() == 0;
        if (z2) {
            uri2 = URI.create(DictionaryFactory.SHARP);
        }
        URI uriResolve = uri.resolve(uri2);
        if (z2) {
            String string2 = uriResolve.toString();
            uriResolve = URI.create(string2.substring(0, string2.indexOf(35)));
        }
        return removeDotSegments(uriResolve);
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost) throws URISyntaxException {
        return rewriteURI(uri, httpHost, false);
    }
}
