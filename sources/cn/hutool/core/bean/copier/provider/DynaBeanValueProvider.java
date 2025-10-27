package cn.hutool.core.bean.copier.provider;

import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.convert.Convert;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class DynaBeanValueProvider implements ValueProvider<String> {
    private final DynaBean dynaBean;
    private final boolean ignoreError;

    public DynaBeanValueProvider(DynaBean dynaBean, boolean z2) {
        this.dynaBean = dynaBean;
        this.ignoreError = z2;
    }

    @Override // cn.hutool.core.bean.copier.ValueProvider
    public boolean containsKey(String str) {
        return this.dynaBean.containsProp(str);
    }

    @Override // cn.hutool.core.bean.copier.ValueProvider
    public Object value(String str, Type type) {
        return Convert.convertWithCheck(type, this.dynaBean.get(str), null, this.ignoreError);
    }
}
