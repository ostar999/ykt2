package cn.hutool.core.io.resource;

import cn.hutool.core.util.CharsetUtil;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class StringResource extends CharSequenceResource {
    private static final long serialVersionUID = 1;

    public StringResource(String str) {
        super(str, null);
    }

    public StringResource(String str, String str2) {
        super(str, str2, CharsetUtil.CHARSET_UTF_8);
    }

    public StringResource(String str, String str2, Charset charset) {
        super(str, str2, charset);
    }
}
