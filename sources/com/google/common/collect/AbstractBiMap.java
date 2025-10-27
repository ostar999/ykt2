package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
abstract class AbstractBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {

    @GwtIncompatible
    private static final long serialVersionUID = 0;

    @MonotonicNonNullDecl
    private transient Map<K, V> delegate;

    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> entrySet;

    @RetainedWith
    @MonotonicNonNullDecl
    transient AbstractBiMap<V, K> inverse;

    @MonotonicNonNullDecl
    private transient Set<K> keySet;

    @MonotonicNonNullDecl
    private transient Set<V> valueSet;

    public class BiMapEntry extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> delegate;

        public BiMapEntry(Map.Entry<K, V> entry) {
            this.delegate = entry;
        }

        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
        public V setValue(V v2) {
            AbstractBiMap.this.checkValue(v2);
            Preconditions.checkState(AbstractBiMap.this.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(v2, getValue())) {
                return v2;
            }
            Preconditions.checkArgument(!AbstractBiMap.this.containsValue(v2), "value already present: %s", v2);
            V value = this.delegate.setValue(v2);
            Preconditions.checkState(Objects.equal(v2, AbstractBiMap.this.get(getKey())), "entry no longer in map");
            AbstractBiMap.this.updateInverseMap(getKey(), true, value, v2);
            return value;
        }

        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
        public Map.Entry<K, V> delegate() {
            return this.delegate;
        }
    }

    public class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
        final Set<Map.Entry<K, V>> esDelegate;

        private EntrySet() {
            this.esDelegate = AbstractBiMap.this.delegate.entrySet();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbstractBiMap.this.clear();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return Maps.containsEntryImpl(delegate(), obj);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> collection) {
            return standardContainsAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractBiMap.this.entrySetIterator();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!this.esDelegate.contains(obj)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            ((AbstractBiMap) AbstractBiMap.this.inverse).delegate.remove(entry.getValue());
            this.esDelegate.remove(entry);
            return true;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<Map.Entry<K, V>> delegate() {
            return this.esDelegate;
        }
    }

    public static class Inverse<K, V> extends AbstractBiMap<K, V> {

        @GwtIncompatible
        private static final long serialVersionUID = 0;

        public Inverse(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
            super(map, abstractBiMap);
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            setInverse((AbstractBiMap) objectInputStream.readObject());
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(inverse());
        }

        @Override // com.google.common.collect.AbstractBiMap
        public K checkKey(K k2) {
            return this.inverse.checkValue(k2);
        }

        @Override // com.google.common.collect.AbstractBiMap
        public V checkValue(V v2) {
            return this.inverse.checkKey(v2);
        }

        @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public /* bridge */ /* synthetic */ Object delegate() {
            return super.delegate();
        }

        @GwtIncompatible
        public Object readResolve() {
            return inverse().inverse();
        }

        @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
        public /* bridge */ /* synthetic */ Collection values() {
            return super.values();
        }
    }

    public class ValueSet extends ForwardingSet<V> {
        final Set<V> valuesDelegate;

        private ValueSet() {
            this.valuesDelegate = AbstractBiMap.this.inverse.keySet();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<V> iterator() {
            return Maps.valueIterator(AbstractBiMap.this.entrySet().iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingObject
        public String toString() {
            return standardToString();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<V> delegate() {
            return this.valuesDelegate;
        }
    }

    private V putInBothMaps(@NullableDecl K k2, @NullableDecl V v2, boolean z2) {
        checkKey(k2);
        checkValue(v2);
        boolean zContainsKey = containsKey(k2);
        if (zContainsKey && Objects.equal(v2, get(k2))) {
            return v2;
        }
        if (z2) {
            inverse().remove(v2);
        } else {
            Preconditions.checkArgument(!containsValue(v2), "value already present: %s", v2);
        }
        V vPut = this.delegate.put(k2, v2);
        updateInverseMap(k2, zContainsKey, vPut, v2);
        return vPut;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public V removeFromBothMaps(Object obj) {
        V vRemove = this.delegate.remove(obj);
        removeFromInverseMap(vRemove);
        return vRemove;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromInverseMap(V v2) {
        this.inverse.delegate.remove(v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInverseMap(K k2, boolean z2, V v2, V v3) {
        if (z2) {
            removeFromInverseMap(v2);
        }
        this.inverse.delegate.put(v3, k2);
    }

    @CanIgnoreReturnValue
    public K checkKey(@NullableDecl K k2) {
        return k2;
    }

    @CanIgnoreReturnValue
    public V checkValue(@NullableDecl V v2) {
        return v2;
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public void clear() {
        this.delegate.clear();
        this.inverse.delegate.clear();
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        return this.inverse.containsKey(obj);
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.entrySet = entrySet;
        return entrySet;
    }

    public Iterator<Map.Entry<K, V>> entrySetIterator() {
        final Iterator<Map.Entry<K, V>> it = this.delegate.entrySet().iterator();
        return new Iterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.AbstractBiMap.1

            @NullableDecl
            Map.Entry<K, V> entry;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                CollectPreconditions.checkRemove(this.entry != null);
                V value = this.entry.getValue();
                it.remove();
                AbstractBiMap.this.removeFromInverseMap(value);
                this.entry = null;
            }

            @Override // java.util.Iterator
            public Map.Entry<K, V> next() {
                Map.Entry<K, V> entry = (Map.Entry) it.next();
                this.entry = entry;
                return new BiMapEntry(entry);
            }
        };
    }

    @Override // com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V forcePut(@NullableDecl K k2, @NullableDecl V v2) {
        return putInBothMaps(k2, v2, true);
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        return this.inverse;
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.keySet = keySet;
        return keySet;
    }

    public AbstractBiMap<V, K> makeInverse(Map<V, K> map) {
        return new Inverse(map, this);
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public V put(@NullableDecl K k2, @NullableDecl V v2) {
        return putInBothMaps(k2, v2, false);
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj) {
        if (containsKey(obj)) {
            return removeFromBothMaps(obj);
        }
        return null;
    }

    public void setDelegates(Map<K, V> map, Map<V, K> map2) {
        Preconditions.checkState(this.delegate == null);
        Preconditions.checkState(this.inverse == null);
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkArgument(map2.isEmpty());
        Preconditions.checkArgument(map != map2);
        this.delegate = map;
        this.inverse = makeInverse(map2);
    }

    public void setInverse(AbstractBiMap<V, K> abstractBiMap) {
        this.inverse = abstractBiMap;
    }

    public class KeySet extends ForwardingSet<K> {
        private KeySet() {
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public void clear() {
            AbstractBiMap.this.clear();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return Maps.keyIterator(AbstractBiMap.this.entrySet().iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            AbstractBiMap.this.removeFromBothMaps(obj);
            return true;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            return standardRemoveAll(collection);
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            return standardRetainAll(collection);
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<K> delegate() {
            return AbstractBiMap.this.delegate.keySet();
        }
    }

    public AbstractBiMap(Map<K, V> map, Map<V, K> map2) {
        setDelegates(map, map2);
    }

    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public Map<K, V> delegate() {
        return this.delegate;
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<V> values() {
        Set<V> set = this.valueSet;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.valueSet = valueSet;
        return valueSet;
    }

    private AbstractBiMap(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
        this.delegate = map;
        this.inverse = abstractBiMap;
    }
}
