package cn.hutool.core.net.url;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.net.RFC3986;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.just.agentweb.DefaultWebClient;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class UrlBuilder implements Builder<String> {
    private static final String DEFAULT_SCHEME = "http";
    private static final long serialVersionUID = 1;
    private Charset charset;
    private String fragment;
    private String host;
    private boolean needEncodePercent;
    private UrlPath path;
    private int port;
    private UrlQuery query;
    private String scheme;

    public UrlBuilder() {
        this.port = -1;
        this.charset = CharsetUtil.CHARSET_UTF_8;
    }

    @Deprecated
    public static UrlBuilder create() {
        return new UrlBuilder();
    }

    public static UrlBuilder of(URI uri, Charset charset) {
        return of(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getRawQuery(), uri.getFragment(), charset);
    }

    public static UrlBuilder ofHttp(String str) {
        return ofHttp(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static UrlBuilder ofHttpWithoutEncode(String str) {
        return ofHttp(str, null);
    }

    public UrlBuilder addPath(CharSequence charSequence) {
        UrlPath.of(charSequence, this.charset).getSegments().forEach(new Consumer() { // from class: j0.a
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f27518c.addPathSegment((String) obj);
            }
        });
        return this;
    }

    public UrlBuilder addPathSegment(CharSequence charSequence) {
        if (CharSequenceUtil.isEmpty(charSequence)) {
            return this;
        }
        if (this.path == null) {
            this.path = new UrlPath();
        }
        this.path.add(charSequence);
        return this;
    }

    public UrlBuilder addQuery(String str, Object obj) {
        if (CharSequenceUtil.isEmpty(str)) {
            return this;
        }
        if (this.query == null) {
            this.query = new UrlQuery();
        }
        this.query.add(str, obj);
        return this;
    }

    @Deprecated
    public UrlBuilder appendPath(CharSequence charSequence) {
        return addPath(charSequence);
    }

    public String getAuthority() {
        if (this.port < 0) {
            return this.host;
        }
        return this.host + ":" + this.port;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getFragment() {
        return this.fragment;
    }

    public String getFragmentEncoded() {
        return RFC3986.FRAGMENT.encode(this.fragment, this.charset, this.needEncodePercent ? null : new char[]{'%'});
    }

    public String getHost() {
        return this.host;
    }

    public UrlPath getPath() {
        return this.path;
    }

    public String getPathStr() {
        UrlPath urlPath = this.path;
        return urlPath == null ? "/" : urlPath.build(this.charset, this.needEncodePercent);
    }

    public int getPort() {
        return this.port;
    }

    public int getPortWithDefault() {
        int port = getPort();
        return port > 0 ? port : toURL().getDefaultPort();
    }

    public UrlQuery getQuery() {
        return this.query;
    }

    public String getQueryStr() {
        UrlQuery urlQuery = this.query;
        if (urlQuery == null) {
            return null;
        }
        return urlQuery.build(this.charset, this.needEncodePercent);
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getSchemeWithDefault() {
        return CharSequenceUtil.emptyToDefault(this.scheme, "http");
    }

    public UrlBuilder setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public UrlBuilder setFragment(String str) {
        if (CharSequenceUtil.isEmpty(str)) {
            this.fragment = null;
        }
        this.fragment = CharSequenceUtil.removePrefix(str, DictionaryFactory.SHARP);
        return this;
    }

    public UrlBuilder setHost(String str) {
        this.host = str;
        return this;
    }

    public UrlBuilder setPath(UrlPath urlPath) {
        this.path = urlPath;
        return this;
    }

    public UrlBuilder setPort(int i2) {
        this.port = i2;
        return this;
    }

    public UrlBuilder setQuery(UrlQuery urlQuery) {
        this.query = urlQuery;
        return this;
    }

    public UrlBuilder setScheme(String str) {
        this.scheme = str;
        return this;
    }

    public UrlBuilder setWithEndTag(boolean z2) {
        if (this.path == null) {
            this.path = new UrlPath();
        }
        this.path.setWithEndTag(z2);
        return this;
    }

    public String toString() {
        return build();
    }

    public URI toURI() {
        try {
            return toURL().toURI();
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    public URL toURL() {
        return toURL(null);
    }

    public static UrlBuilder of(String str) {
        return of(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static UrlBuilder ofHttp(String str, Charset charset) throws IllegalArgumentException {
        Assert.notBlank(str, "Http url must be not blank!", new Object[0]);
        String strTrimStart = CharSequenceUtil.trimStart(str);
        if (!CharSequenceUtil.startWithAnyIgnoreCase(strTrimStart, DefaultWebClient.HTTP_SCHEME, DefaultWebClient.HTTPS_SCHEME)) {
            strTrimStart = DefaultWebClient.HTTP_SCHEME + strTrimStart;
        }
        return of(strTrimStart, charset);
    }

    @Override // cn.hutool.core.builder.Builder
    public String build() {
        return toURL().toString();
    }

    public URL toURL(URLStreamHandler uRLStreamHandler) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPathStr());
        String queryStr = getQueryStr();
        if (CharSequenceUtil.isNotBlank(queryStr)) {
            sb.append('?');
            sb.append(queryStr);
        }
        if (CharSequenceUtil.isNotBlank(this.fragment)) {
            sb.append('#');
            sb.append(getFragmentEncoded());
        }
        try {
            return new URL(getSchemeWithDefault(), this.host, this.port, sb.toString(), uRLStreamHandler);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public static UrlBuilder of(String str, Charset charset) throws IllegalArgumentException {
        Assert.notBlank(str, "Url must be not blank!", new Object[0]);
        return of(URLUtil.url(CharSequenceUtil.trim(str)), charset);
    }

    public UrlBuilder(String str, String str2, int i2, UrlPath urlPath, UrlQuery urlQuery, String str3, Charset charset) {
        this.charset = charset;
        this.scheme = str;
        this.host = str2;
        this.port = i2;
        this.path = urlPath;
        this.query = urlQuery;
        setFragment(str3);
        this.needEncodePercent = charset != null;
    }

    public static UrlBuilder of(URL url, Charset charset) {
        return of(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef(), charset);
    }

    public static UrlBuilder of(String str, String str2, int i2, String str3, String str4, String str5, Charset charset) {
        return of(str, str2, i2, UrlPath.of(str3, charset), UrlQuery.of(str4, charset, false), str5, charset);
    }

    public static UrlBuilder of(String str, String str2, int i2, UrlPath urlPath, UrlQuery urlQuery, String str3, Charset charset) {
        return new UrlBuilder(str, str2, i2, urlPath, urlQuery, str3, charset);
    }

    public static UrlBuilder of() {
        return new UrlBuilder();
    }
}
