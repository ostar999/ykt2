package cn.hutool.core.map;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class CaseInsensitiveTreeMap<K, V> extends CaseInsensitiveMap<K, V> {
    private static final long serialVersionUID = 4043263744224569870L;

    public CaseInsensitiveTreeMap() {
        this((Comparator) null);
    }

    public CaseInsensitiveTreeMap(Map<? extends K, ? extends V> map) {
        this();
        putAll(map);
    }

    public CaseInsensitiveTreeMap(SortedMap<? extends K, ? extends V> sortedMap) {
        super(new TreeMap((Map) sortedMap));
    }

    public CaseInsensitiveTreeMap(Comparator<? super K> comparator) {
        super(new TreeMap(comparator));
    }
}
