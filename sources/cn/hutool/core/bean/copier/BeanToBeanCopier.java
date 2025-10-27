package cn.hutool.core.bean.copier;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class BeanToBeanCopier<S, T> extends AbsCopier<S, T> {
    private final Type targetType;

    public BeanToBeanCopier(S s2, T t2, Type type, CopyOptions copyOptions) {
        super(s2, t2, copyOptions);
        this.targetType = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copy$0(Map map, String str, PropDesc propDesc) throws Exception {
        String strEditFieldName;
        PropDesc propDesc2;
        if (str == null || !propDesc.isReadable(this.copyOptions.transientSupport) || (strEditFieldName = this.copyOptions.editFieldName(str)) == null || !this.copyOptions.testKeyFilter(strEditFieldName) || (propDesc2 = (PropDesc) map.get(strEditFieldName)) == null || !propDesc2.isWritable(this.copyOptions.transientSupport)) {
            return;
        }
        Object value = propDesc.getValue(this.source);
        if (this.copyOptions.testPropertyFilter(propDesc.getField(), value)) {
            Object objEditFieldValue = this.copyOptions.editFieldValue(strEditFieldName, this.copyOptions.convertField(TypeUtil.getActualType(this.targetType, propDesc2.getFieldType()), value));
            T t2 = this.target;
            CopyOptions copyOptions = this.copyOptions;
            propDesc2.setValue(t2, objEditFieldValue, copyOptions.ignoreNullValue, copyOptions.ignoreError, copyOptions.override);
        }
    }

    @Override // cn.hutool.core.lang.copier.Copier
    public T copy() throws Throwable {
        Class<?> cls = this.target.getClass();
        Class<?> cls2 = this.copyOptions.editable;
        if (cls2 != null) {
            Assert.isTrue(cls2.isInstance(this.target), "Target class [{}] not assignable to Editable class [{}]", cls.getName(), this.copyOptions.editable.getName());
            cls = this.copyOptions.editable;
        }
        final Map<String, PropDesc> propMap = BeanUtil.getBeanDesc(cls).getPropMap(this.copyOptions.ignoreCase);
        BeanUtil.getBeanDesc(this.source.getClass()).getPropMap(this.copyOptions.ignoreCase).forEach(new BiConsumer() { // from class: cn.hutool.core.bean.copier.a
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Exception {
                this.f2379c.lambda$copy$0(propMap, (String) obj, (PropDesc) obj2);
            }
        });
        return this.target;
    }
}
