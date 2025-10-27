package cn.hutool.core.lang;

import java.lang.Number;

/* loaded from: classes.dex */
public interface Segment<T extends Number> {
    T getEndIndex();

    T getStartIndex();

    T length();
}
