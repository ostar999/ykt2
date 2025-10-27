package cn.hutool.core.map;

import cn.hutool.core.text.CharSequenceUtil;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CamelCaseMap<K, V> extends FuncKeyMap<K, V> {
    private static final long serialVersionUID = 4043263744224569870L;

    public CamelCaseMap() {
        this(16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$new$eea40c49$1(Object obj) {
        return obj instanceof CharSequence ? CharSequenceUtil.toCamelCase(obj.toString()) : obj;
    }

    public CamelCaseMap(int i2) {
        this(i2, 0.75f);
    }

    public CamelCaseMap(Map<? extends K, ? extends V> map) {
        this(0.75f, map);
    }

    public CamelCaseMap(float f2, Map<? extends K, ? extends V> map) {
        this(map.size(), f2);
        putAll(map);
    }

    public CamelCaseMap(int i2, float f2) {
        this(MapBuilder.create(new HashMap(i2, f2)));
    }

    public CamelCaseMap(MapBuilder<K, V> mapBuilder) {
        super(mapBuilder.build(), new c());
    }
}
