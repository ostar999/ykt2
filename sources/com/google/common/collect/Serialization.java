package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@GwtIncompatible
/* loaded from: classes4.dex */
final class Serialization {

    public static final class FieldSetter<T> {
        private final Field field;

        public void set(T t2, Object obj) throws IllegalAccessException, IllegalArgumentException {
            try {
                this.field.set(t2, obj);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        private FieldSetter(Field field) throws SecurityException {
            this.field = field;
            field.setAccessible(true);
        }

        public void set(T t2, int i2) throws IllegalAccessException, IllegalArgumentException {
            try {
                this.field.set(t2, Integer.valueOf(i2));
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    private Serialization() {
    }

    public static <T> FieldSetter<T> getFieldSetter(Class<T> cls, String str) {
        try {
            return new FieldSetter<>(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e2) {
            throw new AssertionError(e2);
        }
    }

    public static <K, V> void populateMap(Map<K, V> map, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateMap(map, objectInputStream, objectInputStream.readInt());
    }

    public static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateMultimap(multimap, objectInputStream, objectInputStream.readInt());
    }

    public static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateMultiset(multiset, objectInputStream, objectInputStream.readInt());
    }

    public static int readCount(ObjectInputStream objectInputStream) throws IOException {
        return objectInputStream.readInt();
    }

    public static <K, V> void writeMap(Map<K, V> map, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    public static <K, V> void writeMultimap(Multimap<K, V> multimap, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(multimap.asMap().size());
        for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeInt(entry.getValue().size());
            Iterator<V> it = entry.getValue().iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next());
            }
        }
    }

    public static <E> void writeMultiset(Multiset<E> multiset, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(multiset.entrySet().size());
        for (Multiset.Entry<E> entry : multiset.entrySet()) {
            objectOutputStream.writeObject(entry.getElement());
            objectOutputStream.writeInt(entry.getCount());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void populateMap(Map<K, V> map, ObjectInputStream objectInputStream, int i2) throws IOException, ClassNotFoundException {
        for (int i3 = 0; i3 < i2; i3++) {
            map.put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream objectInputStream, int i2) throws IOException, ClassNotFoundException {
        for (int i3 = 0; i3 < i2; i3++) {
            Collection collection = multimap.get(objectInputStream.readObject());
            int i4 = objectInputStream.readInt();
            for (int i5 = 0; i5 < i4; i5++) {
                collection.add(objectInputStream.readObject());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream objectInputStream, int i2) throws IOException, ClassNotFoundException {
        for (int i3 = 0; i3 < i2; i3++) {
            multiset.add(objectInputStream.readObject(), objectInputStream.readInt());
        }
    }
}
