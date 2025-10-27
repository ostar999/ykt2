package cn.hutool.core.map;

import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public interface TreeEntry<K, V> extends Map.Entry<K, V> {
    boolean containsChild(K k2);

    boolean containsParent(K k2);

    @Override // java.util.Map.Entry
    boolean equals(Object obj);

    void forEachChild(boolean z2, Consumer<TreeEntry<K, V>> consumer);

    TreeEntry<K, V> getChild(K k2);

    Map<K, TreeEntry<K, V>> getChildren();

    Map<K, TreeEntry<K, V>> getDeclaredChildren();

    TreeEntry<K, V> getDeclaredParent();

    TreeEntry<K, V> getParent(K k2);

    TreeEntry<K, V> getRoot();

    int getWeight();

    boolean hasChildren();

    boolean hasParent();

    @Override // java.util.Map.Entry
    int hashCode();
}
