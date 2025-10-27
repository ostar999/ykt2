package cn.hutool.core.bean.copier;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.map.MapWrapper;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class MapToBeanCopier<T> extends AbsCopier<Map<?, ?>, T> {
    private final Type targetType;

    public MapToBeanCopier(Map<?, ?> map, T t2, Type type, CopyOptions copyOptions) {
        super(map, t2, copyOptions);
        if ((map instanceof MapWrapper) && (((MapWrapper) map).getRaw() instanceof CaseInsensitiveMap)) {
            copyOptions.setIgnoreCase(true);
        }
        this.targetType = type;
    }

    private PropDesc findPropDesc(Map<String, PropDesc> map, String str) {
        PropDesc propDesc = map.get(str);
        return propDesc != null ? propDesc : map.get(CharSequenceUtil.toCamelCase(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copy$0(Map map, Object obj, Object obj2) throws Exception {
        String strEditFieldName;
        PropDesc propDescFindPropDesc;
        if (obj == null || (strEditFieldName = this.copyOptions.editFieldName(obj.toString())) == null || !this.copyOptions.testKeyFilter(strEditFieldName) || (propDescFindPropDesc = findPropDesc(map, strEditFieldName)) == null || !propDescFindPropDesc.isWritable(this.copyOptions.transientSupport)) {
            return;
        }
        String fieldName = propDescFindPropDesc.getFieldName();
        if (this.copyOptions.testPropertyFilter(propDescFindPropDesc.getField(), obj2)) {
            Object objEditFieldValue = this.copyOptions.editFieldValue(fieldName, this.copyOptions.convertField(TypeUtil.getActualType(this.targetType, propDescFindPropDesc.getFieldType()), obj2));
            T t2 = this.target;
            CopyOptions copyOptions = this.copyOptions;
            propDescFindPropDesc.setValue(t2, objEditFieldValue, copyOptions.ignoreNullValue, copyOptions.ignoreError, copyOptions.override);
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
        ((Map) this.source).forEach(new BiConsumer() { // from class: cn.hutool.core.bean.copier.i
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Exception {
                this.f2384c.lambda$copy$0(propMap, obj, obj2);
            }
        });
        return this.target;
    }
}
