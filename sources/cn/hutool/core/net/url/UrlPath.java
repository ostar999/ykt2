package cn.hutool.core.net.url;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.net.RFC3986;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class UrlPath {
    private List<String> segments;
    private boolean withEngTag;

    private void addInternal(CharSequence charSequence, boolean z2) {
        if (this.segments == null) {
            this.segments = new LinkedList();
        }
        String str = CharSequenceUtil.str(charSequence);
        if (z2) {
            this.segments.add(0, str);
        } else {
            this.segments.add(str);
        }
    }

    private static String fixPath(CharSequence charSequence) throws IllegalArgumentException {
        Assert.notNull(charSequence, "Path segment must be not null!", new Object[0]);
        return "/".contentEquals(charSequence) ? "" : CharSequenceUtil.trim(CharSequenceUtil.removeSuffix(CharSequenceUtil.removePrefix(CharSequenceUtil.trim(charSequence), "/"), "/"));
    }

    public static UrlPath of(CharSequence charSequence, Charset charset) throws IllegalArgumentException {
        UrlPath urlPath = new UrlPath();
        urlPath.parse(charSequence, charset);
        return urlPath;
    }

    public UrlPath add(CharSequence charSequence) {
        addInternal(fixPath(charSequence), false);
        return this;
    }

    public UrlPath addBefore(CharSequence charSequence) {
        addInternal(fixPath(charSequence), true);
        return this;
    }

    public String build(Charset charset) {
        return build(charset, true);
    }

    public String getSegment(int i2) {
        List<String> list = this.segments;
        if (list == null || i2 >= list.size()) {
            return null;
        }
        return this.segments.get(i2);
    }

    public List<String> getSegments() {
        return (List) ObjectUtil.defaultIfNull(this.segments, ListUtil.empty());
    }

    public UrlPath parse(CharSequence charSequence, Charset charset) throws IllegalArgumentException {
        if (CharSequenceUtil.isNotEmpty(charSequence)) {
            if (CharSequenceUtil.endWith(charSequence, '/')) {
                this.withEngTag = true;
            }
            String strFixPath = fixPath(charSequence);
            if (CharSequenceUtil.isNotEmpty(strFixPath)) {
                Iterator<String> it = CharSequenceUtil.split((CharSequence) strFixPath, '/').iterator();
                while (it.hasNext()) {
                    addInternal(URLDecoder.decodeForPath(it.next(), charset), false);
                }
            }
        }
        return this;
    }

    public UrlPath setWithEndTag(boolean z2) {
        this.withEngTag = z2;
        return this;
    }

    public String toString() {
        return build(null);
    }

    public String build(Charset charset, boolean z2) {
        if (CollUtil.isEmpty((Collection<?>) this.segments)) {
            return this.withEngTag ? "/" : "";
        }
        char[] cArr = z2 ? null : new char[]{'%'};
        StringBuilder sb = new StringBuilder();
        for (String str : this.segments) {
            if (sb.length() == 0) {
                sb.append('/');
                sb.append(RFC3986.SEGMENT_NZ_NC.encode(str, charset, cArr));
            } else {
                sb.append('/');
                sb.append(RFC3986.SEGMENT.encode(str, charset, cArr));
            }
        }
        if (this.withEngTag && (CharSequenceUtil.isEmpty(sb) || !CharSequenceUtil.endWith((CharSequence) sb, '/'))) {
            sb.append('/');
        }
        return sb.toString();
    }
}
