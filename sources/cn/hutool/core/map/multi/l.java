package cn.hutool.core.map.multi;

import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class l implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((Map) obj).keySet();
    }
}
