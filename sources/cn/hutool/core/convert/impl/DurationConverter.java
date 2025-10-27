package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import java.time.Duration;
import java.time.temporal.TemporalAmount;

/* loaded from: classes.dex */
public class DurationConverter extends AbstractConverter<Duration> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public Duration convertInternal(Object obj) {
        return obj instanceof TemporalAmount ? Duration.from((TemporalAmount) obj) : obj instanceof Long ? Duration.ofMillis(((Long) obj).longValue()) : Duration.parse(convertToStr(obj));
    }
}
