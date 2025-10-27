package cn.hutool.core.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public interface ForestMap<K, V> extends Map<K, TreeEntry<K, V>> {
    @Override // java.util.Map
    void clear();

    boolean containsChildNode(K k2, K k3);

    boolean containsParentNode(K k2, K k3);

    Collection<TreeEntry<K, V>> getChildNodes(K k2);

    Collection<TreeEntry<K, V>> getDeclaredChildNodes(K k2);

    TreeEntry<K, V> getDeclaredParentNode(K k2);

    V getNodeValue(K k2);

    TreeEntry<K, V> getParentNode(K k2, K k3);

    TreeEntry<K, V> getRootNode(K k2);

    Set<TreeEntry<K, V>> getTreeNodes(K k2);

    void linkNodes(K k2, K k3);

    void linkNodes(K k2, K k3, BiConsumer<TreeEntry<K, V>, TreeEntry<K, V>> biConsumer);

    TreeEntry<K, V> put(K k2, TreeEntry<K, V> treeEntry);

    @Override // java.util.Map
    /* bridge */ /* synthetic */ Object put(Object obj, Object obj2);

    @Override // java.util.Map
    void putAll(Map<? extends K, ? extends TreeEntry<K, V>> map);

    <C extends Collection<V>> void putAllNode(C c3, Function<V, K> function, Function<V, K> function2, boolean z2);

    void putLinkedNodes(K k2, K k3, V v2);

    void putLinkedNodes(K k2, V v2, K k3, V v3);

    TreeEntry<K, V> putNode(K k2, V v2);

    @Override // java.util.Map
    TreeEntry<K, V> remove(Object obj);

    @Override // java.util.Map
    /* bridge */ /* synthetic */ Object remove(Object obj);

    void unlinkNode(K k2, K k3);
}
