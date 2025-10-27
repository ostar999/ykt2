package androidx.datastore.preferences.protobuf;

import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

/* loaded from: classes.dex */
public final class Internal {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final ByteBuffer EMPTY_BYTE_BUFFER;
    public static final CodedInputStream EMPTY_CODED_INPUT_STREAM;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public interface BooleanList extends ProtobufList<Boolean> {
        void addBoolean(boolean z2);

        boolean getBoolean(int i2);

        @Override // 
        ProtobufList<Boolean> mutableCopyWithCapacity(int i2);

        boolean setBoolean(int i2, boolean z2);
    }

    public interface DoubleList extends ProtobufList<Double> {
        void addDouble(double d3);

        double getDouble(int i2);

        @Override // androidx.datastore.preferences.protobuf.Internal.ProtobufList, androidx.datastore.preferences.protobuf.Internal.BooleanList
        ProtobufList<Double> mutableCopyWithCapacity(int i2);

        double setDouble(int i2, double d3);
    }

    public interface EnumLite {
        int getNumber();
    }

    public interface EnumLiteMap<T extends EnumLite> {
        T findValueByNumber(int i2);
    }

    public interface EnumVerifier {
        boolean isInRange(int i2);
    }

    public interface FloatList extends ProtobufList<Float> {
        void addFloat(float f2);

        float getFloat(int i2);

        @Override // androidx.datastore.preferences.protobuf.Internal.ProtobufList, androidx.datastore.preferences.protobuf.Internal.BooleanList
        ProtobufList<Float> mutableCopyWithCapacity(int i2);

        float setFloat(int i2, float f2);
    }

    public interface IntList extends ProtobufList<Integer> {
        void addInt(int i2);

        int getInt(int i2);

        @Override // androidx.datastore.preferences.protobuf.Internal.ProtobufList, androidx.datastore.preferences.protobuf.Internal.BooleanList
        ProtobufList<Integer> mutableCopyWithCapacity(int i2);

        int setInt(int i2, int i3);
    }

    public static class ListAdapter<F, T> extends AbstractList<T> {
        private final Converter<F, T> converter;
        private final List<F> fromList;

        public interface Converter<F, T> {
            T convert(F f2);
        }

        public ListAdapter(List<F> list, Converter<F, T> converter) {
            this.fromList = list;
            this.converter = converter;
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i2) {
            return (T) this.converter.convert(this.fromList.get(i2));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }
    }

    public interface LongList extends ProtobufList<Long> {
        void addLong(long j2);

        long getLong(int i2);

        @Override // androidx.datastore.preferences.protobuf.Internal.ProtobufList, androidx.datastore.preferences.protobuf.Internal.BooleanList
        ProtobufList<Long> mutableCopyWithCapacity(int i2);

        long setLong(int i2, long j2);
    }

    public static class MapAdapter<K, V, RealValue> extends AbstractMap<K, V> {
        private final Map<K, RealValue> realMap;
        private final Converter<RealValue, V> valueConverter;

        public interface Converter<A, B> {
            A doBackward(B b3);

            B doForward(A a3);
        }

        public class EntryAdapter implements Map.Entry<K, V> {
            private final Map.Entry<K, RealValue> realEntry;

            public EntryAdapter(Map.Entry<K, RealValue> entry) {
                this.realEntry = entry;
            }

            @Override // java.util.Map.Entry
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (obj instanceof Map.Entry) {
                    return getKey().equals(((Map.Entry) obj).getKey()) && getValue().equals(getValue());
                }
                return false;
            }

            @Override // java.util.Map.Entry
            public K getKey() {
                return this.realEntry.getKey();
            }

            @Override // java.util.Map.Entry
            public V getValue() {
                return (V) MapAdapter.this.valueConverter.doForward(this.realEntry.getValue());
            }

            @Override // java.util.Map.Entry
            public int hashCode() {
                return this.realEntry.hashCode();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Map.Entry
            public V setValue(V v2) {
                Object value = this.realEntry.setValue(MapAdapter.this.valueConverter.doBackward(v2));
                if (value == null) {
                    return null;
                }
                return (V) MapAdapter.this.valueConverter.doForward(value);
            }
        }

        public class IteratorAdapter implements Iterator<Map.Entry<K, V>> {
            private final Iterator<Map.Entry<K, RealValue>> realIterator;

            public IteratorAdapter(Iterator<Map.Entry<K, RealValue>> it) {
                this.realIterator = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.realIterator.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.realIterator.remove();
            }

            @Override // java.util.Iterator
            public Map.Entry<K, V> next() {
                return new EntryAdapter(this.realIterator.next());
            }
        }

        public class SetAdapter extends AbstractSet<Map.Entry<K, V>> {
            private final Set<Map.Entry<K, RealValue>> realSet;

            public SetAdapter(Set<Map.Entry<K, RealValue>> set) {
                this.realSet = set;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new IteratorAdapter(this.realSet.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.realSet.size();
            }
        }

        public MapAdapter(Map<K, RealValue> map, Converter<RealValue, V> converter) {
            this.realMap = map;
            this.valueConverter = converter;
        }

        public static <T extends EnumLite> Converter<Integer, T> newEnumConverter(final EnumLiteMap<T> enumLiteMap, final T t2) {
            return (Converter<Integer, T>) new Converter<Integer, T>() { // from class: androidx.datastore.preferences.protobuf.Internal.MapAdapter.1
                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Integer; */
                @Override // androidx.datastore.preferences.protobuf.Internal.MapAdapter.Converter
                public Integer doBackward(EnumLite enumLite) {
                    return Integer.valueOf(enumLite.getNumber());
                }

                /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/Integer;)TT; */
                @Override // androidx.datastore.preferences.protobuf.Internal.MapAdapter.Converter
                public EnumLite doForward(Integer num) {
                    EnumLite enumLiteFindValueByNumber = enumLiteMap.findValueByNumber(num.intValue());
                    return enumLiteFindValueByNumber == null ? t2 : enumLiteFindValueByNumber;
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return new SetAdapter(this.realMap.entrySet());
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            RealValue realvalue = this.realMap.get(obj);
            if (realvalue == null) {
                return null;
            }
            return this.valueConverter.doForward(realvalue);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k2, V v2) {
            Object objPut = this.realMap.put(k2, this.valueConverter.doBackward(v2));
            if (objPut == null) {
                return null;
            }
            return (V) this.valueConverter.doForward(objPut);
        }
    }

    public interface ProtobufList<E> extends List<E>, RandomAccess {
        boolean isModifiable();

        void makeImmutable();

        ProtobufList<E> mutableCopyWithCapacity(int i2);
    }

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        EMPTY_BYTE_BUFFER = ByteBuffer.wrap(bArr);
        EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(bArr);
    }

    private Internal() {
    }

    public static byte[] byteArrayDefaultValue(String str) {
        return str.getBytes(ISO_8859_1);
    }

    public static ByteBuffer byteBufferDefaultValue(String str) {
        return ByteBuffer.wrap(byteArrayDefaultValue(str));
    }

    public static ByteString bytesDefaultValue(String str) {
        return ByteString.copyFrom(str.getBytes(ISO_8859_1));
    }

    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }

    public static ByteBuffer copyByteBuffer(ByteBuffer byteBuffer) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.clear();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBufferDuplicate.capacity());
        byteBufferAllocate.put(byteBufferDuplicate);
        byteBufferAllocate.clear();
        return byteBufferAllocate;
    }

    public static boolean equals(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (!Arrays.equals(list.get(i2), list2.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        if (byteBuffer.capacity() != byteBuffer2.capacity()) {
            return false;
        }
        return byteBuffer.duplicate().clear().equals(byteBuffer2.duplicate().clear());
    }

    public static <T extends MessageLite> T getDefaultInstance(Class<T> cls) throws NoSuchMethodException, SecurityException {
        try {
            java.lang.reflect.Method method = cls.getMethod("getDefaultInstance", new Class[0]);
            return (T) method.invoke(method, new Object[0]);
        } catch (Exception e2) {
            throw new RuntimeException("Failed to get default instance for " + cls, e2);
        }
    }

    public static int hashBoolean(boolean z2) {
        return z2 ? R2.attr.customColorDrawableValue : R2.attr.customIsDrag;
    }

    public static int hashCode(List<byte[]> list) {
        Iterator<byte[]> it = list.iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + hashCode(it.next());
        }
        return iHashCode;
    }

    public static int hashCodeByteBuffer(List<ByteBuffer> list) {
        Iterator<ByteBuffer> it = list.iterator();
        int iHashCodeByteBuffer = 1;
        while (it.hasNext()) {
            iHashCodeByteBuffer = (iHashCodeByteBuffer * 31) + hashCodeByteBuffer(it.next());
        }
        return iHashCodeByteBuffer;
    }

    public static int hashEnum(EnumLite enumLite) {
        return enumLite.getNumber();
    }

    public static int hashEnumList(List<? extends EnumLite> list) {
        Iterator<? extends EnumLite> it = list.iterator();
        int iHashEnum = 1;
        while (it.hasNext()) {
            iHashEnum = (iHashEnum * 31) + hashEnum(it.next());
        }
        return iHashEnum;
    }

    public static int hashLong(long j2) {
        return (int) (j2 ^ (j2 >>> 32));
    }

    public static boolean isValidUtf8(ByteString byteString) {
        return byteString.isValidUtf8();
    }

    public static Object mergeMessage(Object obj, Object obj2) {
        return ((MessageLite) obj).toBuilder().mergeFrom((MessageLite) obj2).buildPartial();
    }

    public static int partialHash(int i2, byte[] bArr, int i3, int i4) {
        for (int i5 = i3; i5 < i3 + i4; i5++) {
            i2 = (i2 * 31) + bArr[i5];
        }
        return i2;
    }

    public static String stringDefaultValue(String str) {
        return new String(str.getBytes(ISO_8859_1), UTF_8);
    }

    public static byte[] toByteArray(String str) {
        return str.getBytes(UTF_8);
    }

    public static String toStringUtf8(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static <T> T checkNotNull(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }

    public static boolean isValidUtf8(byte[] bArr) {
        return Utf8.isValidUtf8(bArr);
    }

    public static boolean equalsByteBuffer(List<ByteBuffer> list, List<ByteBuffer> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (!equalsByteBuffer(list.get(i2), list2.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(byte[] bArr) {
        return hashCode(bArr, 0, bArr.length);
    }

    public static int hashCodeByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            int iPartialHash = partialHash(byteBuffer.capacity(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            if (iPartialHash == 0) {
                return 1;
            }
            return iPartialHash;
        }
        int iCapacity = byteBuffer.capacity() <= 4096 ? byteBuffer.capacity() : 4096;
        byte[] bArr = new byte[iCapacity];
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.clear();
        int iCapacity2 = byteBuffer.capacity();
        while (byteBufferDuplicate.remaining() > 0) {
            int iRemaining = byteBufferDuplicate.remaining() <= iCapacity ? byteBufferDuplicate.remaining() : iCapacity;
            byteBufferDuplicate.get(bArr, 0, iRemaining);
            iCapacity2 = partialHash(iCapacity2, bArr, 0, iRemaining);
        }
        if (iCapacity2 == 0) {
            return 1;
        }
        return iCapacity2;
    }

    public static int hashCode(byte[] bArr, int i2, int i3) {
        int iPartialHash = partialHash(i3, bArr, i2, i3);
        if (iPartialHash == 0) {
            return 1;
        }
        return iPartialHash;
    }
}
