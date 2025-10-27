package com.google.common.html;

import cn.hutool.core.text.CharPool;
import com.google.common.annotations.GwtCompatible;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import kotlin.text.Typography;

@GwtCompatible
/* loaded from: classes4.dex */
public final class HtmlEscapers {
    private static final Escaper HTML_ESCAPER = Escapers.builder().addEscape('\"', "&quot;").addEscape(CharPool.SINGLE_QUOTE, "&#39;").addEscape('&', "&amp;").addEscape(Typography.less, "&lt;").addEscape(Typography.greater, "&gt;").build();

    private HtmlEscapers() {
    }

    public static Escaper htmlEscaper() {
        return HTML_ESCAPER;
    }
}
