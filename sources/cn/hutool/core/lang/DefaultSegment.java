package cn.hutool.core.lang;

import java.lang.Number;

/* loaded from: classes.dex */
public class DefaultSegment<T extends Number> implements Segment<T> {
    protected T endIndex;
    protected T startIndex;

    public DefaultSegment(T t2, T t3) {
        this.startIndex = t2;
        this.endIndex = t3;
    }

    @Override // cn.hutool.core.lang.Segment
    public T getEndIndex() {
        return this.endIndex;
    }

    @Override // cn.hutool.core.lang.Segment
    public T getStartIndex() {
        return this.startIndex;
    }

    @Override // cn.hutool.core.lang.Segment
    public /* synthetic */ Number length() {
        return h0.a(this);
    }
}
