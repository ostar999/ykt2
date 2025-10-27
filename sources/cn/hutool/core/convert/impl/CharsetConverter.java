package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.util.CharsetUtil;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class CharsetConverter extends AbstractConverter<Charset> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public Charset convertInternal(Object obj) {
        return CharsetUtil.charset(convertToStr(obj));
    }
}
