package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\ncollections.kt\nKotlin\n*S Kotlin\n*F\n+ 1 collections.kt\norg/jetbrains/kotlin/utils/CollectionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,95:1\n1208#2,2:96\n1238#2,4:98\n1#3:102\n*S KotlinDebug\n*F\n+ 1 collections.kt\norg/jetbrains/kotlin/utils/CollectionsKt\n*L\n22#1:96,2\n22#1:98,4\n*E\n"})
/* loaded from: classes8.dex */
public final class CollectionsKt {
    public static final <T> void addIfNotNull(@NotNull Collection<T> collection, @Nullable T t2) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        if (t2 != null) {
            collection.add(t2);
        }
    }

    private static final int capacity(int i2) {
        if (i2 < 3) {
            return 3;
        }
        return i2 + (i2 / 3) + 1;
    }

    @NotNull
    public static final <T> List<T> compact(@NotNull ArrayList<T> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<this>");
        int size = arrayList.size();
        if (size == 0) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        if (size == 1) {
            return CollectionsKt__CollectionsJVMKt.listOf(CollectionsKt___CollectionsKt.first((List) arrayList));
        }
        arrayList.trimToSize();
        return arrayList;
    }

    @NotNull
    public static final <K> Map<K, Integer> mapToIndex(@NotNull Iterable<? extends K> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<? extends K> it = iterable.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            linkedHashMap.put(it.next(), Integer.valueOf(i2));
            i2++;
        }
        return linkedHashMap;
    }

    @NotNull
    public static final <K, V> HashMap<K, V> newHashMapWithExpectedSize(int i2) {
        return new HashMap<>(capacity(i2));
    }

    @NotNull
    public static final <E> HashSet<E> newHashSetWithExpectedSize(int i2) {
        return new HashSet<>(capacity(i2));
    }

    @NotNull
    public static final <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int i2) {
        return new LinkedHashSet<>(capacity(i2));
    }
}
