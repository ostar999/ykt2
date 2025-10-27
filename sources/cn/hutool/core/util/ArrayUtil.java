package cn.hutool.core.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.UniqueKeySet;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrJoiner;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class ArrayUtil extends PrimitiveArrayUtil {
    @SafeVarargs
    public static <T> T[] addAll(T[]... tArr) {
        if (tArr.length == 1) {
            return tArr[0];
        }
        int length = 0;
        for (T[] tArr2 : tArr) {
            if (isNotEmpty((Object[]) tArr2)) {
                length += tArr2.length;
            }
        }
        T[] tArr3 = (T[]) newArray(tArr.getClass().getComponentType().getComponentType(), length);
        int length2 = 0;
        for (T[] tArr4 : tArr) {
            if (isNotEmpty((Object[]) tArr4)) {
                System.arraycopy(tArr4, 0, tArr3, length2, tArr4.length);
                length2 += tArr4.length;
            }
        }
        return tArr3;
    }

    @SafeVarargs
    public static <T> T[] append(T[] tArr, T... tArr2) {
        return isEmpty((Object[]) tArr) ? tArr2 : (T[]) insert((Object[]) tArr, tArr.length, (Object[]) tArr2);
    }

    public static Object[] cast(Class<?> cls, Object obj) throws IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new NullPointerException("Argument [arrayObj] is null !");
        }
        if (!obj.getClass().isArray()) {
            throw new IllegalArgumentException("Argument [arrayObj] is not array !");
        }
        if (cls == null) {
            return (Object[]) obj;
        }
        if (cls.isArray()) {
            cls = cls.getComponentType();
        }
        Object[] objArr = (Object[]) obj;
        Object[] objArrNewArray = newArray(cls, objArr.length);
        System.arraycopy(objArr, 0, objArrNewArray, 0, objArr.length);
        return objArrNewArray;
    }

    public static <T> T[] clone(T[] tArr) {
        if (tArr == null) {
            return null;
        }
        return (T[]) ((Object[]) tArr.clone());
    }

    public static <T> boolean contains(T[] tArr, T t2) {
        return indexOf(tArr, t2) > -1;
    }

    public static <T> boolean containsAll(T[] tArr, T... tArr2) {
        for (T t2 : tArr2) {
            if (!contains(tArr, t2)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean containsAny(T[] tArr, T... tArr2) {
        for (T t2 : tArr2) {
            if (contains(tArr, t2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(CharSequence[] charSequenceArr, CharSequence charSequence) {
        return indexOfIgnoreCase(charSequenceArr, charSequence) > -1;
    }

    public static Object copy(Object obj, int i2, Object obj2, int i3, int i4) {
        System.arraycopy(obj, i2, obj2, i3, i4);
        return obj2;
    }

    public static <T> T[] defaultIfEmpty(T[] tArr, T[] tArr2) {
        return isEmpty((Object[]) tArr) ? tArr2 : tArr;
    }

    public static <T> T[] distinct(T[] tArr) {
        if (isEmpty((Object[]) tArr)) {
            return tArr;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(tArr.length, 1.0f);
        Collections.addAll(linkedHashSet, tArr);
        return (T[]) toArray((Collection) linkedHashSet, (Class) getComponentType(tArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T[] edit(T[] tArr, Editor<T> editor) {
        if (editor == null) {
            return tArr;
        }
        ArrayList arrayList = new ArrayList(tArr.length);
        for (T t2 : tArr) {
            T tEdit = editor.edit(t2);
            if (tEdit != null) {
                arrayList.add(tEdit);
            }
        }
        return (T[]) arrayList.toArray(newArray(tArr.getClass().getComponentType(), arrayList.size()));
    }

    public static int emptyCount(Object... objArr) {
        if (!isNotEmpty(objArr)) {
            return 0;
        }
        int i2 = 0;
        for (Object obj : objArr) {
            if (ObjectUtil.isEmpty(obj)) {
                i2++;
            }
        }
        return i2;
    }

    public static boolean equals(Object obj, Object obj2) throws Throwable {
        if (obj == obj2) {
            return true;
        }
        if (hasNull(obj, obj2)) {
            return false;
        }
        Assert.isTrue(isArray(obj), "First is not a Array !", new Object[0]);
        Assert.isTrue(isArray(obj2), "Second is not a Array !", new Object[0]);
        return obj instanceof long[] ? Arrays.equals((long[]) obj, (long[]) obj2) : obj instanceof int[] ? Arrays.equals((int[]) obj, (int[]) obj2) : obj instanceof short[] ? Arrays.equals((short[]) obj, (short[]) obj2) : obj instanceof char[] ? Arrays.equals((char[]) obj, (char[]) obj2) : obj instanceof byte[] ? Arrays.equals((byte[]) obj, (byte[]) obj2) : obj instanceof double[] ? Arrays.equals((double[]) obj, (double[]) obj2) : obj instanceof float[] ? Arrays.equals((float[]) obj, (float[]) obj2) : obj instanceof boolean[] ? Arrays.equals((boolean[]) obj, (boolean[]) obj2) : Arrays.deepEquals((Object[]) obj, (Object[]) obj2);
    }

    public static <T> T[] filter(T[] tArr, final Filter<T> filter) {
        return (tArr == null || filter == null) ? tArr : (T[]) edit(tArr, new Editor() { // from class: cn.hutool.core.util.d
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return ArrayUtil.lambda$filter$0(filter, obj);
            }
        });
    }

    public static <T> T firstMatch(Matcher<T> matcher, T... tArr) {
        int iMatchIndex = matchIndex(matcher, tArr);
        if (iMatchIndex < 0) {
            return null;
        }
        return tArr[iMatchIndex];
    }

    public static <T> T firstNonNull(T... tArr) {
        return (T) firstMatch(new Matcher() { // from class: cn.hutool.core.util.e
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return ObjectUtil.isNotNull(obj);
            }
        }, tArr);
    }

    public static <T> T get(Object obj, int i2) {
        if (obj == null) {
            return null;
        }
        if (i2 < 0) {
            i2 += Array.getLength(obj);
        }
        try {
            return (T) Array.get(obj, i2);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T[] getAny(Object obj, int... iArr) {
        if (obj == null) {
            return null;
        }
        if (iArr == null) {
            return (T[]) newArray(obj.getClass().getComponentType(), 0);
        }
        T[] tArr = (T[]) newArray(obj.getClass().getComponentType(), iArr.length);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            tArr[i2] = get(obj, iArr[i2]);
        }
        return tArr;
    }

    public static Class<?> getArrayType(Class<?> cls) {
        return Array.newInstance(cls, 0).getClass();
    }

    public static Class<?> getComponentType(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getComponentType();
    }

    public static boolean hasEmpty(Object... objArr) {
        if (isNotEmpty(objArr)) {
            for (Object obj : objArr) {
                if (ObjectUtil.isEmpty(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> boolean hasNull(T... tArr) {
        if (isNotEmpty((Object[]) tArr)) {
            for (T t2 : tArr) {
                if (ObjectUtil.isNull(t2)) {
                    return true;
                }
            }
        }
        return tArr == null;
    }

    public static <T> int indexOf(T[] tArr, final Object obj, int i2) {
        return matchIndex(new Matcher() { // from class: cn.hutool.core.util.c
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj2) {
                return ObjectUtil.equal(obj, obj2);
            }
        }, i2, tArr);
    }

    public static int indexOfIgnoreCase(CharSequence[] charSequenceArr, CharSequence charSequence) {
        if (charSequenceArr == null) {
            return -1;
        }
        for (int i2 = 0; i2 < charSequenceArr.length; i2++) {
            if (CharSequenceUtil.equalsIgnoreCase(charSequenceArr[i2], charSequence)) {
                return i2;
            }
        }
        return -1;
    }

    public static <T> int indexOfSub(T[] tArr, T[] tArr2) {
        return indexOfSub(tArr, 0, tArr2);
    }

    public static <T> T[] insert(T[] tArr, int i2, T... tArr2) {
        return (T[]) ((Object[]) insert((Object) tArr, i2, (Object[]) tArr2));
    }

    public static boolean isAllEmpty(Object... objArr) {
        for (Object obj : objArr) {
            if (!ObjectUtil.isEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNotEmpty(Object... objArr) {
        return !hasEmpty(objArr);
    }

    public static <T> boolean isAllNotNull(T... tArr) {
        return !hasNull(tArr);
    }

    public static <T> boolean isAllNull(T... tArr) {
        return firstNonNull(tArr) == null;
    }

    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public static <T> boolean isEmpty(T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] tArr) {
        return (tArr == null || tArr.length == 0) ? false : true;
    }

    public static <T> boolean isSorted(T[] tArr, Comparator<? super T> comparator) {
        if (tArr == null || comparator == null) {
            return false;
        }
        int i2 = 0;
        while (i2 < tArr.length - 1) {
            T t2 = tArr[i2];
            i2++;
            if (comparator.compare(t2, tArr[i2]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<? super T>> boolean isSortedASC(T[] tArr) {
        if (tArr == null) {
            return false;
        }
        int i2 = 0;
        while (i2 < tArr.length - 1) {
            T t2 = tArr[i2];
            i2++;
            if (t2.compareTo(tArr[i2]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<? super T>> boolean isSortedDESC(T[] tArr) {
        if (tArr == null) {
            return false;
        }
        int i2 = 0;
        while (i2 < tArr.length - 1) {
            T t2 = tArr[i2];
            i2++;
            if (t2.compareTo(tArr[i2]) < 0) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean isSub(T[] tArr, T[] tArr2) {
        return indexOfSub(tArr, tArr2) > -1;
    }

    public static <T> String join(T[] tArr, CharSequence charSequence) {
        return join(tArr, charSequence, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$filter$0(Filter filter, Object obj) {
        if (filter.accept(obj)) {
            return obj;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$join$5(Editor editor, Object obj) {
        return String.valueOf(editor.edit(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$nullToEmpty$2(String str) {
        return str == null ? "" : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$removeNull$1(Object obj) {
        return obj;
    }

    public static <T> int lastIndexOf(T[] tArr, Object obj) {
        if (isEmpty((Object[]) tArr)) {
            return -1;
        }
        return lastIndexOf(tArr, obj, tArr.length - 1);
    }

    public static <T> int lastIndexOfSub(T[] tArr, T[] tArr2) {
        if (isEmpty((Object[]) tArr) || isEmpty((Object[]) tArr2)) {
            return -1;
        }
        return lastIndexOfSub(tArr, tArr.length - 1, tArr2);
    }

    public static int length(Object obj) throws IllegalArgumentException {
        if (obj == null) {
            return 0;
        }
        return Array.getLength(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T, R> R[] map(T[] tArr, Class<R> cls, Function<? super T, ? extends R> function) {
        R[] rArr = (R[]) newArray(cls, tArr.length);
        for (int i2 = 0; i2 < tArr.length; i2++) {
            rArr[i2] = function.apply(tArr[i2]);
        }
        return rArr;
    }

    public static <T, R> Set<R> mapToSet(T[] tArr, Function<? super T, ? extends R> function) {
        return (Set) Arrays.stream(tArr).map(function).collect(Collectors.toSet());
    }

    public static <T> int matchIndex(Matcher<T> matcher, T... tArr) {
        return matchIndex(matcher, 0, tArr);
    }

    public static <T extends Comparable<? super T>> T max(T[] tArr) {
        return (T) max(tArr, null);
    }

    public static <T extends Comparable<? super T>> T min(T[] tArr) {
        return (T) min(tArr, null);
    }

    public static <T> T[] newArray(Class<?> cls, int i2) {
        return (T[]) ((Object[]) Array.newInstance(cls, i2));
    }

    public static String[] nullToEmpty(String[] strArr) {
        return (String[]) edit(strArr, new Editor() { // from class: cn.hutool.core.util.f
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return ArrayUtil.lambda$nullToEmpty$2((String) obj);
            }
        });
    }

    public static <T> T[] remove(T[] tArr, int i2) throws IllegalArgumentException {
        return (T[]) ((Object[]) PrimitiveArrayUtil.remove(tArr, i2));
    }

    public static <T extends CharSequence> T[] removeBlank(T[] tArr) {
        return (T[]) ((CharSequence[]) filter(tArr, new cn.hutool.core.collection.d0()));
    }

    public static <T> T[] removeEle(T[] tArr, T t2) throws IllegalArgumentException {
        return (T[]) remove((Object[]) tArr, indexOf(tArr, t2));
    }

    public static <T extends CharSequence> T[] removeEmpty(T[] tArr) {
        return (T[]) ((CharSequence[]) filter(tArr, new cn.hutool.core.collection.z()));
    }

    public static <T> T[] removeNull(T[] tArr) {
        return (T[]) edit(tArr, new Editor() { // from class: cn.hutool.core.util.a
            @Override // cn.hutool.core.lang.Editor
            public final Object edit(Object obj) {
                return ArrayUtil.lambda$removeNull$1(obj);
            }
        });
    }

    public static <T> T[] replace(T[] tArr, int i2, T... tArr2) {
        if (isEmpty((Object[]) tArr2)) {
            return tArr;
        }
        if (isEmpty((Object[]) tArr)) {
            return tArr2;
        }
        if (i2 < 0) {
            return (T[]) insert((Object[]) tArr, 0, (Object[]) tArr2);
        }
        if (i2 >= tArr.length) {
            return (T[]) append((Object[]) tArr, (Object[]) tArr2);
        }
        if (tArr.length >= tArr2.length + i2) {
            System.arraycopy(tArr2, 0, tArr, i2, tArr2.length);
            return tArr;
        }
        T[] tArr3 = (T[]) newArray(tArr.getClass().getComponentType(), tArr2.length + i2);
        System.arraycopy(tArr, 0, tArr3, 0, i2);
        System.arraycopy(tArr2, 0, tArr3, i2, tArr2.length);
        return tArr3;
    }

    public static <T> T[] resize(T[] tArr, int i2, Class<?> cls) {
        if (i2 < 0) {
            return tArr;
        }
        T[] tArr2 = (T[]) newArray(cls, i2);
        if (i2 > 0 && isNotEmpty((Object[]) tArr)) {
            System.arraycopy(tArr, 0, tArr2, 0, Math.min(tArr.length, i2));
        }
        return tArr2;
    }

    public static <T> T[] reverse(T[] tArr, int i2, int i3) {
        if (isEmpty((Object[]) tArr)) {
            return tArr;
        }
        int iMin = Math.min(tArr.length, i3) - 1;
        for (int iMax = Math.max(i2, 0); iMin > iMax; iMax++) {
            T t2 = tArr[iMin];
            tArr[iMin] = tArr[iMax];
            tArr[iMax] = t2;
            iMin--;
        }
        return tArr;
    }

    public static <T> T[] setOrAppend(T[] tArr, int i2, T t2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i2 < tArr.length) {
            Array.set(tArr, i2, t2);
            return tArr;
        }
        if (!isEmpty((Object[]) tArr)) {
            return (T[]) append((Object[]) tArr, t2);
        }
        Object[] objArrNewArray = newArray(t2.getClass(), 1);
        objArrNewArray[0] = t2;
        return (T[]) append((Object[]) tArr, objArrNewArray);
    }

    public static <T> T[] shuffle(T[] tArr) {
        return (T[]) shuffle(tArr, RandomUtil.getRandom());
    }

    public static <T> T[] sub(T[] tArr, int i2, int i3) throws IllegalArgumentException {
        int length = length(tArr);
        if (i2 < 0) {
            i2 += length;
        }
        if (i3 < 0) {
            i3 += length;
        }
        if (i2 == length) {
            return (T[]) newArray(tArr.getClass().getComponentType(), 0);
        }
        if (i2 <= i3) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
        }
        if (i2 <= length) {
            length = i2;
        } else if (i3 >= length) {
            return (T[]) newArray(tArr.getClass().getComponentType(), 0);
        }
        return (T[]) Arrays.copyOfRange(tArr, i3, length);
    }

    public static <T> T[] swap(T[] tArr, int i2, int i3) {
        if (isEmpty((Object[]) tArr)) {
            throw new IllegalArgumentException("Array must not empty !");
        }
        T t2 = tArr[i2];
        tArr[i2] = tArr[i3];
        tArr[i3] = t2;
        return tArr;
    }

    public static byte[] toArray(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        }
        int iPosition = byteBuffer.position();
        byteBuffer.position(0);
        byte[] bArr = new byte[byteBuffer.limit()];
        byteBuffer.get(bArr);
        byteBuffer.position(iPosition);
        return bArr;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        }
        if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        }
        if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        }
        if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        }
        if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        }
        if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        }
        if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        }
        if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        }
        if (isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception unused) {
            }
        }
        return obj.toString();
    }

    public static Object[] wrap(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!isArray(obj)) {
            throw new UtilException(CharSequenceUtil.format("[{}] is not Array!", obj.getClass()));
        }
        try {
            return (Object[]) obj;
        } catch (Exception e2) {
            String name = obj.getClass().getComponentType().getName();
            if (name.equals("double")) {
                return PrimitiveArrayUtil.wrap((double[]) obj);
            }
            if (name.equals("int")) {
                return PrimitiveArrayUtil.wrap((int[]) obj);
            }
            if (name.equals("byte")) {
                return PrimitiveArrayUtil.wrap((byte[]) obj);
            }
            if (name.equals("char")) {
                return PrimitiveArrayUtil.wrap((char[]) obj);
            }
            if (name.equals("long")) {
                return PrimitiveArrayUtil.wrap((long[]) obj);
            }
            if (name.equals(TypedValues.Custom.S_BOOLEAN)) {
                return PrimitiveArrayUtil.wrap((boolean[]) obj);
            }
            if (name.equals(TypedValues.Custom.S_FLOAT)) {
                return PrimitiveArrayUtil.wrap((float[]) obj);
            }
            if (name.equals("short")) {
                return PrimitiveArrayUtil.wrap((short[]) obj);
            }
            throw new UtilException(e2);
        }
    }

    public static <K, V> Map<K, V> zip(K[] kArr, V[] vArr, boolean z2) {
        if (isEmpty((Object[]) kArr) || isEmpty((Object[]) vArr)) {
            return null;
        }
        int iMin = Math.min(kArr.length, vArr.length);
        HashMap mapNewHashMap = MapUtil.newHashMap(iMin, z2);
        for (int i2 = 0; i2 < iMin; i2++) {
            mapNewHashMap.put(kArr[i2], vArr[i2]);
        }
        return mapNewHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T clone(T t2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (t2 == 0 || !isArray(t2)) {
            return null;
        }
        Class<?> componentType = t2.getClass().getComponentType();
        if (!componentType.isPrimitive()) {
            return (T) ((Object[]) t2).clone();
        }
        int length = Array.getLength(t2);
        T t3 = (T) Array.newInstance(componentType, length);
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return t3;
            }
            Array.set(t3, i2, Array.get(t2, i2));
            length = i2;
        }
    }

    public static Object copy(Object obj, Object obj2, int i2) {
        System.arraycopy(obj, 0, obj2, 0, i2);
        return obj2;
    }

    public static Class<?> getComponentType(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.getComponentType();
    }

    public static <T> int indexOf(T[] tArr, final Object obj) {
        return matchIndex(new Matcher() { // from class: cn.hutool.core.util.b
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj2) {
                return ObjectUtil.equal(obj, obj2);
            }
        }, tArr);
    }

    public static <T> int indexOfSub(T[] tArr, int i2, T[] tArr2) {
        if (!isEmpty((Object[]) tArr) && !isEmpty((Object[]) tArr2) && tArr2.length <= tArr.length) {
            int iIndexOf = indexOf(tArr, tArr2[0], i2);
            if (iIndexOf >= 0 && tArr2.length + iIndexOf <= tArr.length) {
                for (int i3 = 0; i3 < tArr2.length; i3++) {
                    if (!ObjectUtil.equal(tArr[i3 + iIndexOf], tArr2[i3])) {
                        return indexOfSub(tArr, iIndexOf + 1, tArr2);
                    }
                }
                return iIndexOf;
            }
        }
        return -1;
    }

    public static <T> Object insert(Object obj, int i2, T... tArr) throws IllegalArgumentException, NegativeArraySizeException {
        if (isEmpty((Object[]) tArr)) {
            return obj;
        }
        if (isEmpty(obj)) {
            return tArr;
        }
        int length = length(obj);
        if (i2 < 0) {
            i2 = (i2 % length) + length;
        }
        Class<?> componentType = obj.getClass().getComponentType();
        Object objConvert = componentType.isPrimitive() ? Convert.convert((Class) obj.getClass(), (Object) tArr) : tArr;
        Object objNewInstance = Array.newInstance(componentType, Math.max(length, i2) + tArr.length);
        System.arraycopy(obj, 0, objNewInstance, 0, Math.min(length, i2));
        System.arraycopy(objConvert, 0, objNewInstance, i2, tArr.length);
        if (i2 < length) {
            System.arraycopy(obj, i2, objNewInstance, tArr.length + i2, length - i2);
        }
        return objNewInstance;
    }

    public static boolean isEmpty(Object obj) {
        if (obj != null) {
            return isArray(obj) && Array.getLength(obj) == 0;
        }
        return true;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static <T> String join(T[] tArr, CharSequence charSequence, String str, String str2) {
        if (tArr == null) {
            return null;
        }
        return StrJoiner.of(charSequence, str, str2).setWrapElement(true).append((Object[]) tArr).toString();
    }

    public static <T> int matchIndex(Matcher<T> matcher, int i2, T... tArr) throws IllegalArgumentException {
        Assert.notNull(matcher, "Matcher must be not null !", new Object[0]);
        if (!isNotEmpty((Object[]) tArr)) {
            return -1;
        }
        while (i2 < tArr.length) {
            if (matcher.match(tArr[i2])) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static <T extends Comparable<? super T>> T max(T[] tArr, Comparator<T> comparator) {
        if (isEmpty((Object[]) tArr)) {
            throw new IllegalArgumentException("Number array must not empty !");
        }
        T t2 = tArr[0];
        for (int i2 = 1; i2 < tArr.length; i2++) {
            if (CompareUtil.compare(t2, tArr[i2], comparator) < 0) {
                t2 = tArr[i2];
            }
        }
        return t2;
    }

    public static <T extends Comparable<? super T>> T min(T[] tArr, Comparator<T> comparator) {
        if (isEmpty((Object[]) tArr)) {
            throw new IllegalArgumentException("Number array must not empty !");
        }
        T t2 = tArr[0];
        for (T t3 : tArr) {
            if (CompareUtil.compare(t2, t3, comparator) > 0) {
                t2 = t3;
            }
        }
        return t2;
    }

    public static Object[] newArray(int i2) {
        return new Object[i2];
    }

    public static <T> T[] shuffle(T[] tArr, Random random) {
        if (tArr != null && random != null && tArr.length > 1) {
            for (int length = tArr.length; length > 1; length--) {
                swap((Object[]) tArr, length - 1, random.nextInt(length));
            }
        }
        return tArr;
    }

    @SafeVarargs
    public static <T> Object append(Object obj, T... tArr) {
        return isEmpty(obj) ? tArr : insert(obj, length(obj), tArr);
    }

    public static <T extends Comparable<? super T>> boolean isSorted(T[] tArr) {
        return isSortedASC(tArr);
    }

    public static <T> int lastIndexOf(T[] tArr, Object obj, int i2) {
        if (!isNotEmpty((Object[]) tArr)) {
            return -1;
        }
        while (i2 >= 0) {
            if (ObjectUtil.equal(obj, tArr[i2])) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    public static <T> int lastIndexOfSub(T[] tArr, int i2, T[] tArr2) {
        if (!isEmpty((Object[]) tArr) && !isEmpty((Object[]) tArr2) && tArr2.length <= tArr.length && i2 >= 0) {
            int iLastIndexOf = lastIndexOf(tArr, tArr2[0]);
            if (iLastIndexOf >= 0 && tArr2.length + iLastIndexOf <= tArr.length) {
                for (int i3 = 0; i3 < tArr2.length; i3++) {
                    if (!ObjectUtil.equal(tArr[i3 + iLastIndexOf], tArr2[i3])) {
                        return lastIndexOfSub(tArr, iLastIndexOf - 1, tArr2);
                    }
                }
                return iLastIndexOf;
            }
        }
        return -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T, R> R[] map(Object obj, Class<R> cls, Function<? super T, ? extends R> function) throws IllegalArgumentException {
        int length = length(obj);
        R[] rArr = (R[]) newArray(cls, length);
        for (int i2 = 0; i2 < length; i2++) {
            rArr[i2] = function.apply(get(obj, i2));
        }
        return rArr;
    }

    public static Object resize(Object obj, int i2) throws IllegalArgumentException, NegativeArraySizeException {
        if (i2 < 0) {
            return obj;
        }
        if (obj == null) {
            return null;
        }
        int length = length(obj);
        Object objNewInstance = Array.newInstance(obj.getClass().getComponentType(), i2);
        if (i2 > 0 && isNotEmpty(obj)) {
            System.arraycopy(obj, 0, objNewInstance, 0, Math.min(length, i2));
        }
        return objNewInstance;
    }

    public static <T, K> T[] distinct(T[] tArr, Function<T, K> function, boolean z2) {
        if (isEmpty((Object[]) tArr)) {
            return tArr;
        }
        UniqueKeySet uniqueKeySet = new UniqueKeySet(true, (Function) function);
        if (z2) {
            Collections.addAll(uniqueKeySet, tArr);
        } else {
            for (T t2 : tArr) {
                uniqueKeySet.addIfAbsent(t2);
            }
        }
        return (T[]) toArray((Collection) uniqueKeySet, (Class) getComponentType(tArr));
    }

    public static Object[] sub(Object obj, int i2, int i3) {
        return sub(obj, i2, i3, 1);
    }

    public static <K, V> Map<K, V> zip(K[] kArr, V[] vArr) {
        return zip(kArr, vArr, false);
    }

    public static <T> String join(T[] tArr, CharSequence charSequence, final Editor<T> editor) {
        return StrJoiner.of(charSequence).append(tArr, new Function() { // from class: cn.hutool.core.util.g
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ArrayUtil.lambda$join$5(editor, obj);
            }
        }).toString();
    }

    public static Object[] sub(Object obj, int i2, int i3, int i4) throws IllegalArgumentException {
        int length = length(obj);
        if (i2 < 0) {
            i2 += length;
        }
        if (i3 < 0) {
            i3 += length;
        }
        if (i2 == length) {
            return new Object[0];
        }
        if (i2 <= i3) {
            int i5 = i3;
            i3 = i2;
            i2 = i5;
        }
        if (i2 <= length) {
            length = i2;
        } else if (i3 >= length) {
            return new Object[0];
        }
        if (i4 <= 1) {
            i4 = 1;
        }
        ArrayList arrayList = new ArrayList();
        while (i3 < length) {
            arrayList.add(get(obj, i3));
            i3 += i4;
        }
        return arrayList.toArray();
    }

    public static Object swap(Object obj, int i2, int i3) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (!isEmpty(obj)) {
            Object obj2 = get(obj, i2);
            Array.set(obj, i2, Array.get(obj, i3));
            Array.set(obj, i3, obj2);
            return obj;
        }
        throw new IllegalArgumentException("Array must not empty !");
    }

    public static String join(Object obj, CharSequence charSequence) {
        if (obj == null) {
            return null;
        }
        if (isArray(obj)) {
            return StrJoiner.of(charSequence).append(obj).toString();
        }
        throw new IllegalArgumentException(CharSequenceUtil.format("[{}] is not a Array!", obj.getClass()));
    }

    public static <T, R> List<R> map(T[] tArr, Function<? super T, ? extends R> function) {
        return (List) Arrays.stream(tArr).map(function).collect(Collectors.toList());
    }

    public static <T> T[] reverse(T[] tArr) {
        return (T[]) reverse(tArr, 0, tArr.length);
    }

    public static <T> T[] resize(T[] tArr, int i2) {
        return (T[]) resize(tArr, i2, tArr.getClass().getComponentType());
    }

    public static Object setOrAppend(Object obj, int i2, Object obj2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i2 < length(obj)) {
            Array.set(obj, i2, obj2);
            return obj;
        }
        return append(obj, obj2);
    }

    public static <T> T[] toArray(Iterator<T> it, Class<T> cls) {
        return (T[]) toArray((Collection) CollUtil.newArrayList(it), (Class) cls);
    }

    public static <T> T[] toArray(Iterable<T> iterable, Class<T> cls) {
        return (T[]) toArray(CollUtil.toCollection(iterable), (Class) cls);
    }

    public static <T> T[] toArray(Collection<T> collection, Class<T> cls) {
        return (T[]) collection.toArray(newArray(cls, 0));
    }
}
