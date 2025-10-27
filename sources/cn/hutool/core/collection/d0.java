package cn.hutool.core.collection;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public final /* synthetic */ class d0 implements Filter {
    @Override // cn.hutool.core.lang.Filter
    public final boolean accept(Object obj) {
        return CharSequenceUtil.isNotBlank((CharSequence) obj);
    }
}
