package cn.hutool.core.bean.copier;

import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class MapToMapCopier extends AbsCopier<Map, Map> {
    private final Type targetType;

    public MapToMapCopier(Map map, Map map2, Type type, CopyOptions copyOptions) {
        super(map, map2, copyOptions);
        this.targetType = type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copy$0(Object obj, Object obj2) {
        String strEditFieldName;
        if (obj == null) {
            return;
        }
        CopyOptions copyOptions = this.copyOptions;
        if ((true == copyOptions.ignoreNullValue && obj2 == null) || (strEditFieldName = copyOptions.editFieldName(obj.toString())) == null || !this.copyOptions.testKeyFilter(strEditFieldName)) {
            return;
        }
        Object obj3 = ((Map) this.target).get(strEditFieldName);
        if (this.copyOptions.override || obj3 == null) {
            Type[] typeArguments = TypeUtil.getTypeArguments(this.targetType);
            if (typeArguments != null) {
                obj2 = this.copyOptions.editFieldValue(strEditFieldName, this.copyOptions.convertField(typeArguments[1], obj2));
            }
            ((Map) this.target).put(strEditFieldName, obj2);
        }
    }

    @Override // cn.hutool.core.lang.copier.Copier
    public Map copy() {
        ((Map) this.source).forEach(new BiConsumer() { // from class: cn.hutool.core.bean.copier.j
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f2386c.lambda$copy$0(obj, obj2);
            }
        });
        return (Map) this.target;
    }
}
