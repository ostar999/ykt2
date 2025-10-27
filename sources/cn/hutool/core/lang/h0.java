package cn.hutool.core.lang;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import java.lang.Number;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public final /* synthetic */ class h0<T extends Number> {
    public static Number a(Segment segment) {
        Number number = (Number) Assert.notNull(segment.getStartIndex(), "Start index must be not null!", new Object[0]);
        return (Number) Convert.convert((Type) number.getClass(), (Object) NumberUtil.sub((Number) Assert.notNull(segment.getEndIndex(), "End index must be not null!", new Object[0]), number).abs());
    }
}
