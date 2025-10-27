package cn.hutool.core.map.multi;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.map.MapUtil;
import java.io.Serializable;

/* loaded from: classes.dex */
public final /* synthetic */ class e implements Builder, Serializable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ boolean f2539c;

    public /* synthetic */ e(boolean z2) {
        this.f2539c = z2;
    }

    @Override // cn.hutool.core.builder.Builder
    public final Object build() {
        return MapUtil.newHashMap(this.f2539c);
    }
}
