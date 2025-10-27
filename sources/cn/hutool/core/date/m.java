package cn.hutool.core.date;

import java.util.TimeZone;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class m implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return TimeZone.getDefault();
    }
}
