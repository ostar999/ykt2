package cn.hutool.core.bean.copier;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class ValueProviderToBeanCopier<T> extends AbsCopier<ValueProvider<String>, T> {
    private final Type targetType;

    public ValueProviderToBeanCopier(ValueProvider<String> valueProvider, T t2, Type type, CopyOptions copyOptions) {
        super(valueProvider, t2, copyOptions);
        this.targetType = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copy$0(String str, PropDesc propDesc) throws Exception {
        String strEditFieldName;
        if (str != null && (strEditFieldName = this.copyOptions.editFieldName(str)) != null && ((ValueProvider) this.source).containsKey(strEditFieldName) && this.copyOptions.testKeyFilter(strEditFieldName) && propDesc != null && propDesc.isWritable(this.copyOptions.transientSupport)) {
            Object objValue = ((ValueProvider) this.source).value(strEditFieldName, TypeUtil.getActualType(this.targetType, propDesc.getFieldType()));
            if (this.copyOptions.testPropertyFilter(propDesc.getField(), objValue)) {
                Object objEditFieldValue = this.copyOptions.editFieldValue(strEditFieldName, objValue);
                T t2 = this.target;
                CopyOptions copyOptions = this.copyOptions;
                propDesc.setValue(t2, objEditFieldValue, copyOptions.ignoreNullValue, copyOptions.ignoreError, copyOptions.override);
            }
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
        BeanUtil.getBeanDesc(cls).getPropMap(this.copyOptions.ignoreCase).forEach(new BiConsumer() { // from class: cn.hutool.core.bean.copier.k
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Exception {
                this.f2387c.lambda$copy$0((String) obj, (PropDesc) obj2);
            }
        });
        return this.target;
    }
}
