package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes4.dex */
public final class ArrayListMultimap<K, V> extends ArrayListMultimapGwtSerializationDependencies<K, V> {
    private static final int DEFAULT_VALUES_PER_KEY = 3;

    @GwtIncompatible
    private static final long serialVersionUID = 0;

    @VisibleForTesting
    transient int expectedValuesPerKey;

    private ArrayListMultimap() {
        this(12, 3);
    }

    public static <K, V> ArrayListMultimap<K, V> create() {
        return new ArrayListMultimap<>();
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.expectedValuesPerKey = 3;
        int count = Serialization.readCount(objectInputStream);
        setMap(CompactHashMap.create());
        Serialization.populateMultimap(this, objectInputStream, count);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsKey(@NullableDecl Object obj) {
        return super.containsKey(obj);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection entries() {
        return super.entries();
    }

    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ List get(@NullableDecl Object obj) {
        return super.get((ArrayListMultimap<K, V>) obj);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean put(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.put(obj, obj2);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.remove(obj, obj2);
    }

    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ List removeAll(@NullableDecl Object obj) {
        return super.removeAll(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ List replaceValues(@NullableDecl Object obj, Iterable iterable) {
        return super.replaceValues((ArrayListMultimap<K, V>) obj, iterable);
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // com.google.common.collect.AbstractMultimap
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Deprecated
    public void trimToSize() {
        Iterator<Collection<V>> it = backingMap().values().iterator();
        while (it.hasNext()) {
            ((ArrayList) it.next()).trimToSize();
        }
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    private ArrayListMultimap(int i2, int i3) {
        super(Platform.newHashMapWithExpectedSize(i2));
        CollectPreconditions.checkNonnegative(i3, "expectedValuesPerKey");
        this.expectedValuesPerKey = i3;
    }

    public static <K, V> ArrayListMultimap<K, V> create(int i2, int i3) {
        return new ArrayListMultimap<>(i2, i3);
    }

    @Override // com.google.common.collect.AbstractListMultimap, com.google.common.collect.AbstractMapBasedMultimap
    public List<V> createCollection() {
        return new ArrayList(this.expectedValuesPerKey);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ boolean putAll(@NullableDecl Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    public static <K, V> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new ArrayListMultimap<>(multimap);
    }

    private ArrayListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size(), multimap instanceof ArrayListMultimap ? ((ArrayListMultimap) multimap).expectedValuesPerKey : 3);
        putAll(multimap);
    }
}
