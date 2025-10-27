package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import java.util.Currency;

/* loaded from: classes.dex */
public class CurrencyConverter extends AbstractConverter<Currency> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public Currency convertInternal(Object obj) {
        return Currency.getInstance(convertToStr(obj));
    }
}
