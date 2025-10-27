package cn.hutool.core.bean.copier;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class BeanToMapCopier extends AbsCopier<Object, Map> {
    private final Type targetType;

    public BeanToMapCopier(Object obj, Map map, Type type, CopyOptions copyOptions) {
        super(obj, map, copyOptions);
        this.targetType = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copy$0(String str, PropDesc propDesc) {
        String strEditFieldName;
        if (str == null || !propDesc.isReadable(this.copyOptions.transientSupport) || (strEditFieldName = this.copyOptions.editFieldName(str)) == null || !this.copyOptions.testKeyFilter(strEditFieldName)) {
            return;
        }
        Object value = propDesc.getValue(this.source);
        if (this.copyOptions.testPropertyFilter(propDesc.getField(), value)) {
            Type[] typeArguments = TypeUtil.getTypeArguments(this.targetType);
            if (typeArguments != null) {
                value = this.copyOptions.editFieldValue(strEditFieldName, this.copyOptions.convertField(typeArguments[1], value));
            }
            if (value == null && this.copyOptions.ignoreNullValue) {
                return;
            }
            ((Map) this.target).put(strEditFieldName, value);
        }
    }

    @Override // cn.hutool.core.lang.copier.Copier
    public Map copy() throws Throwable {
        Class<?> cls = this.source.getClass();
        Class<?> cls2 = this.copyOptions.editable;
        if (cls2 != null) {
            Assert.isTrue(cls2.isInstance(this.source), "Source class [{}] not assignable to Editable class [{}]", cls.getName(), this.copyOptions.editable.getName());
            cls = this.copyOptions.editable;
        }
        BeanUtil.getBeanDesc(cls).getPropMap(this.copyOptions.ignoreCase).forEach(new BiConsumer() { // from class: cn.hutool.core.bean.copier.b
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f2381c.lambda$copy$0((String) obj, (PropDesc) obj2);
            }
        });
        return (Map) this.target;
    }
}
