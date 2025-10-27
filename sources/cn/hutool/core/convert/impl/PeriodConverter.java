package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import java.time.Period;
import java.time.temporal.TemporalAmount;

/* loaded from: classes.dex */
public class PeriodConverter extends AbstractConverter<Period> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public Period convertInternal(Object obj) {
        return obj instanceof TemporalAmount ? Period.from((TemporalAmount) obj) : obj instanceof Integer ? Period.ofDays(((Integer) obj).intValue()) : Period.parse(convertToStr(obj));
    }
}
