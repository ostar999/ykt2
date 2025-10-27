package org.eclipse.jetty.util;

import cn.hutool.core.text.StrPool;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import okhttp3.HttpUrl;

/* loaded from: classes9.dex */
public class LazyList implements Cloneable, Serializable {
    private static final String[] __EMTPY_STRING_ARRAY = new String[0];

    private LazyList() {
    }

    public static Object add(Object obj, Object obj2) {
        if (obj == null) {
            if (!(obj2 instanceof List) && obj2 != null) {
                return obj2;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(obj2);
            return arrayList;
        }
        if (obj instanceof List) {
            ((List) obj).add(obj2);
            return obj;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(obj);
        arrayList2.add(obj2);
        return arrayList2;
    }

    public static Object addArray(Object obj, Object[] objArr) {
        for (int i2 = 0; objArr != null && i2 < objArr.length; i2++) {
            obj = add(obj, objArr[i2]);
        }
        return obj;
    }

    public static Object addCollection(Object obj, Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            obj = add(obj, it.next());
        }
        return obj;
    }

    public static <T> T[] addToArray(T[] tArr, T t2, Class<?> cls) {
        if (tArr != null) {
            T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), Array.getLength(tArr) + 1));
            System.arraycopy(tArr, 0, tArr2, 0, tArr.length);
            tArr2[tArr.length] = t2;
            return tArr2;
        }
        if (cls == null && t2 != null) {
            cls = t2.getClass();
        }
        T[] tArr3 = (T[]) ((Object[]) Array.newInstance(cls, 1));
        tArr3[0] = t2;
        return tArr3;
    }

    public static <E> List<E> array2List(E[] eArr) {
        return (eArr == null || eArr.length == 0) ? new ArrayList() : new ArrayList(Arrays.asList(eArr));
    }

    public static Object clone(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj instanceof List ? new ArrayList((List) obj) : obj;
    }

    public static boolean contains(Object obj, Object obj2) {
        if (obj == null) {
            return false;
        }
        return obj instanceof List ? ((List) obj).contains(obj2) : obj.equals(obj2);
    }

    public static Object ensureSize(Object obj, int i2) {
        if (obj == null) {
            return new ArrayList(i2);
        }
        if (!(obj instanceof ArrayList)) {
            ArrayList arrayList = new ArrayList(i2);
            arrayList.add(obj);
            return arrayList;
        }
        ArrayList arrayList2 = (ArrayList) obj;
        if (arrayList2.size() > i2) {
            return arrayList2;
        }
        ArrayList arrayList3 = new ArrayList(i2);
        arrayList3.addAll(arrayList2);
        return arrayList3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> E get(Object obj, int i2) {
        if (obj == 0) {
            throw new IndexOutOfBoundsException();
        }
        if (obj instanceof List) {
            return (E) ((List) obj).get(i2);
        }
        if (i2 == 0) {
            return obj;
        }
        throw new IndexOutOfBoundsException();
    }

    public static <E> List<E> getList(Object obj) {
        return getList(obj, false);
    }

    public static <E> Iterator<E> iterator(Object obj) {
        return obj == null ? Collections.emptyList().iterator() : obj instanceof List ? ((List) obj).iterator() : getList(obj).iterator();
    }

    public static <E> ListIterator<E> listIterator(Object obj) {
        return obj == null ? Collections.emptyList().listIterator() : obj instanceof List ? ((List) obj).listIterator() : getList(obj).listIterator();
    }

    public static Object remove(Object obj, Object obj2) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            if (obj.equals(obj2)) {
                return null;
            }
            return obj;
        }
        List list = (List) obj;
        list.remove(obj2);
        if (list.size() == 0) {
            return null;
        }
        return obj;
    }

    public static <T> T[] removeFromArray(T[] tArr, Object obj) {
        if (obj != null && tArr != null) {
            int length = tArr.length;
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    break;
                }
                if (obj.equals(tArr[i2])) {
                    T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), Array.getLength(tArr) - 1));
                    if (i2 > 0) {
                        System.arraycopy(tArr, 0, tArr2, 0, i2);
                    }
                    int i3 = i2 + 1;
                    if (i3 < tArr.length) {
                        System.arraycopy(tArr, i3, tArr2, i2, tArr.length - i3);
                    }
                    return tArr2;
                }
                length = i2;
            }
        }
        return tArr;
    }

    public static int size(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof List) {
            return ((List) obj).size();
        }
        return 1;
    }

    public static Object toArray(Object obj, Class<?> cls) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (obj == null) {
            return Array.newInstance(cls, 0);
        }
        if (!(obj instanceof List)) {
            Object objNewInstance = Array.newInstance(cls, 1);
            Array.set(objNewInstance, 0, obj);
            return objNewInstance;
        }
        List list = (List) obj;
        if (!cls.isPrimitive()) {
            return list.toArray((Object[]) Array.newInstance(cls, list.size()));
        }
        Object objNewInstance2 = Array.newInstance(cls, list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            Array.set(objNewInstance2, i2, list.get(i2));
        }
        return objNewInstance2;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        if (obj instanceof List) {
            return obj.toString();
        }
        return StrPool.BRACKET_START + obj + StrPool.BRACKET_END;
    }

    public static String[] toStringArray(Object obj) {
        if (obj == null) {
            return __EMTPY_STRING_ARRAY;
        }
        if (!(obj instanceof List)) {
            return new String[]{obj.toString()};
        }
        List list = (List) obj;
        String[] strArr = new String[list.size()];
        int size = list.size();
        while (true) {
            int i2 = size - 1;
            if (size <= 0) {
                return strArr;
            }
            Object obj2 = list.get(i2);
            if (obj2 != null) {
                strArr[i2] = obj2.toString();
            }
            size = i2;
        }
    }

    public static <E> List<E> getList(Object obj, boolean z2) {
        if (obj != null) {
            return obj instanceof List ? (List) obj : Collections.singletonList(obj);
        }
        if (z2) {
            return null;
        }
        return Collections.emptyList();
    }

    public static Object remove(Object obj, int i2) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            if (i2 == 0) {
                return null;
            }
            return obj;
        }
        List list = (List) obj;
        list.remove(i2);
        if (list.size() == 0) {
            return null;
        }
        return obj;
    }

    public static Object add(Object obj, int i2, Object obj2) {
        if (obj == null) {
            if (i2 <= 0 && !(obj2 instanceof List) && obj2 != null) {
                return obj2;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(i2, obj2);
            return arrayList;
        }
        if (obj instanceof List) {
            ((List) obj).add(i2, obj2);
            return obj;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(obj);
        arrayList2.add(i2, obj2);
        return arrayList2;
    }
}
