package cn.hutool.core.net.url;

import cn.hutool.core.codec.PercentCodec;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.TableMap;
import cn.hutool.core.net.FormUrlencoded;
import cn.hutool.core.net.RFC3986;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.text.CharSequenceUtil;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class UrlQuery {
    private final boolean isFormUrlEncoded;
    private boolean isStrict;
    private final TableMap<CharSequence, CharSequence> query;

    public UrlQuery() {
        this((Map<? extends CharSequence, ?>) null);
    }

    private void addParam(String str, String str2, Charset charset) {
        if (str != null) {
            this.query.put(URLDecoder.decode(str, charset, this.isFormUrlEncoded), CharSequenceUtil.nullToEmpty(URLDecoder.decode(str2, charset, this.isFormUrlEncoded)));
        } else if (str2 != null) {
            this.query.put(URLDecoder.decode(str2, charset, this.isFormUrlEncoded), null);
        }
    }

    private UrlQuery doParse(String str, Charset charset) {
        int length = str.length();
        int i2 = 0;
        String strSubstring = null;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '&') {
                addParam(strSubstring, str.substring(i3, i2), charset);
                int i4 = i2 + 4;
                if (i4 < length && "amp;".equals(str.substring(i2 + 1, i2 + 5))) {
                    i2 = i4;
                }
                i3 = i2 + 1;
                strSubstring = null;
            } else if (cCharAt == '=' && strSubstring == null) {
                strSubstring = str.substring(i3, i2);
                i3 = i2 + 1;
            }
            i2++;
        }
        addParam(strSubstring, str.substring(i3, i2), charset);
        return this;
    }

    public static UrlQuery of(Map<? extends CharSequence, ?> map) {
        return new UrlQuery(map);
    }

    private static String toStr(Object obj) {
        return obj instanceof Iterable ? CollUtil.join((Iterable) obj, ",") : obj instanceof Iterator ? IterUtil.join((Iterator) obj, ",") : Convert.toStr(obj);
    }

    public UrlQuery add(CharSequence charSequence, Object obj) {
        this.query.put(charSequence, toStr(obj));
        return this;
    }

    public UrlQuery addAll(Map<? extends CharSequence, ?> map) {
        if (MapUtil.isNotEmpty(map)) {
            map.forEach(new BiConsumer() { // from class: j0.b
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f27519c.add((CharSequence) obj, obj2);
                }
            });
        }
        return this;
    }

    public String build(Charset charset) {
        return build(charset, true);
    }

    public CharSequence get(CharSequence charSequence) {
        if (MapUtil.isEmpty(this.query)) {
            return null;
        }
        return this.query.get(charSequence);
    }

    public Map<CharSequence, CharSequence> getQueryMap() {
        return MapUtil.unmodifiable(this.query);
    }

    public UrlQuery parse(String str, Charset charset) {
        return parse(str, charset, true);
    }

    public UrlQuery setStrict(boolean z2) {
        this.isStrict = z2;
        return this;
    }

    public String toString() {
        return build(null);
    }

    public UrlQuery(boolean z2) {
        this(null, z2);
    }

    public static UrlQuery of(Map<? extends CharSequence, ?> map, boolean z2) {
        return new UrlQuery(map, z2);
    }

    public String build(Charset charset, boolean z2) {
        if (!this.isFormUrlEncoded) {
            return this.isStrict ? build(RFC3986.QUERY_PARAM_NAME_STRICT, RFC3986.QUERY_PARAM_VALUE_STRICT, charset, z2) : build(RFC3986.QUERY_PARAM_NAME, RFC3986.QUERY_PARAM_VALUE, charset, z2);
        }
        PercentCodec percentCodec = FormUrlencoded.ALL;
        return build(percentCodec, percentCodec, charset, z2);
    }

    public UrlQuery parse(String str, Charset charset, boolean z2) {
        int iIndexOf;
        if (CharSequenceUtil.isBlank(str)) {
            return this;
        }
        if (z2 && (iIndexOf = str.indexOf(63)) > -1) {
            str = CharSequenceUtil.subSuf(str, iIndexOf + 1);
            if (CharSequenceUtil.isBlank(str)) {
                return this;
            }
        }
        return doParse(str, charset);
    }

    public UrlQuery(Map<? extends CharSequence, ?> map) {
        this(map, false);
    }

    public static UrlQuery of(String str, Charset charset) {
        return of(str, charset, true);
    }

    public UrlQuery(Map<? extends CharSequence, ?> map, boolean z2) {
        if (MapUtil.isNotEmpty(map)) {
            this.query = new TableMap<>(map.size());
            addAll(map);
        } else {
            this.query = new TableMap<>(16);
        }
        this.isFormUrlEncoded = z2;
    }

    public static UrlQuery of(String str, Charset charset, boolean z2) {
        return of(str, charset, z2, false);
    }

    public static UrlQuery of(String str, Charset charset, boolean z2, boolean z3) {
        return new UrlQuery(z3).parse(str, charset, z2);
    }

    public String build(PercentCodec percentCodec, PercentCodec percentCodec2, Charset charset) {
        return build(percentCodec, percentCodec2, charset, true);
    }

    public String build(PercentCodec percentCodec, PercentCodec percentCodec2, Charset charset, boolean z2) {
        if (MapUtil.isEmpty(this.query)) {
            return "";
        }
        char[] cArr = z2 ? null : new char[]{'%'};
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<CharSequence, CharSequence>> it = this.query.iterator();
        while (it.hasNext()) {
            Map.Entry<CharSequence, CharSequence> next = it.next();
            CharSequence key = next.getKey();
            if (key != null) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(percentCodec.encode(key, charset, cArr));
                CharSequence value = next.getValue();
                if (value != null) {
                    sb.append("=");
                    sb.append(percentCodec2.encode(value, charset, cArr));
                }
            }
        }
        return sb.toString();
    }
}
