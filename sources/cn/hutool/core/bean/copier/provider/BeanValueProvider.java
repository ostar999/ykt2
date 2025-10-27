package cn.hutool.core.bean.copier.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.PropDesc;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.map.FuncKeyMap;
import cn.hutool.core.text.CharSequenceUtil;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BeanValueProvider implements ValueProvider<String> {
    private final boolean ignoreError;
    private final Object source;
    final Map<String, PropDesc> sourcePdMap;

    public BeanValueProvider(Object obj, boolean z2, boolean z3) {
        this(obj, z2, z3, null);
    }

    private PropDesc getPropDesc(String str, Type type) {
        PropDesc propDesc = this.sourcePdMap.get(str);
        return propDesc == null ? (type == null || Boolean.class == type || Boolean.TYPE == type) ? this.sourcePdMap.get(CharSequenceUtil.upperFirstAndAddPre(str, "is")) : propDesc : propDesc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$new$9d0d83f1$1(boolean z2, Editor editor, Object obj) {
        if (z2 && (obj instanceof CharSequence)) {
            obj = obj.toString().toLowerCase();
        }
        if (editor != null) {
            obj = editor.edit(obj.toString());
        }
        return obj.toString();
    }

    public BeanValueProvider(Object obj, boolean z2, boolean z3, Editor<String> editor) {
        this.source = obj;
        this.ignoreError = z3;
        Map<String, PropDesc> propMap = BeanUtil.getBeanDesc(obj.getClass()).getPropMap(z2);
        FuncKeyMap funcKeyMap = new FuncKeyMap(new HashMap(propMap.size(), 1.0f), new a(z2, editor));
        this.sourcePdMap = funcKeyMap;
        funcKeyMap.putAll(propMap);
    }

    @Override // cn.hutool.core.bean.copier.ValueProvider
    public boolean containsKey(String str) {
        PropDesc propDesc = getPropDesc(str, null);
        return propDesc != null && propDesc.isReadable(false);
    }

    @Override // cn.hutool.core.bean.copier.ValueProvider
    public Object value(String str, Type type) {
        PropDesc propDesc = getPropDesc(str, type);
        if (propDesc != null) {
            return propDesc.getValue(this.source, type, this.ignoreError);
        }
        return null;
    }
}
