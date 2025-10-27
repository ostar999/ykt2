package com.plv.foundationsdk.utils;

import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Contract;

/* loaded from: classes4.dex */
public class PLVSugarUtil {

    public interface Callback {
        void onCallback();
    }

    public interface Consumer<T> {
        void accept(T t2);
    }

    public interface Function<T, R> {
        R apply(T t2);
    }

    public interface Supplier<T> {
        T get();
    }

    @NonNull
    @SafeVarargs
    public static <T> ArrayList<T> arrayListOf(T... tArr) {
        return (tArr == null || tArr.length == 0) ? new ArrayList<>() : new ArrayList<>(Arrays.asList(tArr));
    }

    @SafeVarargs
    public static <T> T[] arrayOf(T... tArr) {
        return tArr;
    }

    public static void catchingNull(Runnable runnable) {
        try {
            runnable.run();
        } catch (NullPointerException unused) {
        }
    }

    public static int clamp(int i2, int i3, int i4) {
        if (i4 < i3) {
            int i5 = i3 ^ i4;
            i4 ^= i5;
            i3 = i5 ^ i4;
        }
        return Math.max(Math.min(i2, i4), i3);
    }

    @NonNull
    @Contract(pure = true)
    public static <T> List<T> collectionMinus(@NonNull Collection<T> collection, @NonNull Collection<T> collection2) {
        ArrayList arrayList = new ArrayList(collection);
        arrayList.removeAll(collection2);
        return arrayList;
    }

    @Nullable
    public static String firstNotEmpty(@Nullable String... strArr) {
        if (strArr == null) {
            return null;
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return null;
    }

    @Nullable
    @SafeVarargs
    public static <T> T firstNotNull(@Nullable T... tArr) {
        if (tArr == null) {
            return null;
        }
        for (T t2 : tArr) {
            if (t2 != null) {
                return t2;
            }
        }
        return null;
    }

    public static <T> void foreach(@NonNull Iterable<T> iterable, @NonNull Consumer<T> consumer) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    public static String format(String str, Object... objArr) {
        if (objArr == null) {
            objArr = new Object[1];
        }
        int length = objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            str = str.replaceFirst("\\{\\}", obj == null ? "null" : obj.toString());
        }
        return str;
    }

    @NonNull
    public static <T> T getNullableOrDefault(Supplier<T> supplier, @NonNull T t2) {
        return (T) getOrDefault(nullable(supplier), t2);
    }

    @NonNull
    public static <T> T getOrDefault(@Nullable T t2, @NonNull T t3) {
        return t2 != null ? t2 : t3;
    }

    @Deprecated
    public static <T, R> List<R> listMap(List<T> list, Function<T, R> function) {
        return transformList(list, function);
    }

    @NonNull
    @SafeVarargs
    public static <T> List<T> listOf(T... tArr) {
        return new ArrayList(Arrays.asList(tArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(@NonNull Pair<K, V>... pairArr) {
        HashMap map = new HashMap(pairArr.length);
        for (Pair<K, V> pair : pairArr) {
            map.put(pair.first, pair.second);
        }
        return map;
    }

    public static CharSequence notEmptyOrDefault(@Nullable CharSequence charSequence, CharSequence charSequence2) {
        return TextUtils.isEmpty(charSequence) ? charSequence2 : charSequence;
    }

    @Nullable
    public static <T> T nullable(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException unused) {
            return null;
        }
    }

    @NonNull
    public static <K, V> Pair<K, V> pair(K k2, V v2) {
        return new Pair<>(k2, v2);
    }

    @NonNull
    public static <T> T requireNotNull(T t2) {
        t2.getClass();
        return t2;
    }

    @NonNull
    public static <T extends CharSequence> String stringJoin(@NonNull Iterable<T> iterable, @NonNull CharSequence charSequence) {
        StringBuilder sb = null;
        for (T t2 : iterable) {
            if (sb == null) {
                sb = new StringBuilder(t2);
            } else {
                sb.append(charSequence);
                sb.append((CharSequence) t2);
            }
        }
        return sb == null ? "" : sb.toString();
    }

    public static <T, R> List<R> transformList(List<T> list, final Function<T, R> function) {
        if (list == null || function == null) {
            return null;
        }
        final ArrayList arrayList = new ArrayList();
        foreach(list, new Consumer<T>() { // from class: com.plv.foundationsdk.utils.PLVSugarUtil.1
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(T t2) {
                arrayList.add(function.apply(t2));
            }
        });
        return arrayList;
    }
}
