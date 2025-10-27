package kotlin.reflect.jvm.internal.impl.util;

import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public abstract class AbstractArrayMapOwner<K, V> implements Iterable<V>, KMappedMarker {

    public static abstract class AbstractArrayMapAccessor<K, V, T extends V> {
        private final int id;

        @NotNull
        private final KClass<? extends K> key;

        public AbstractArrayMapAccessor(@NotNull KClass<? extends K> key, int i2) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.key = key;
            this.id = i2;
        }

        @Nullable
        public final T extractValue(@NotNull AbstractArrayMapOwner<K, V> thisRef) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            return thisRef.getArrayMap().get(this.id);
        }
    }

    @NotNull
    public abstract ArrayMap<V> getArrayMap();

    @NotNull
    public abstract TypeRegistry<K, V> getTypeRegistry();

    public final boolean isEmpty() {
        return getArrayMap().getSize() == 0;
    }

    @Override // java.lang.Iterable
    @NotNull
    public final Iterator<V> iterator() {
        return getArrayMap().iterator();
    }

    public abstract void registerComponent(@NotNull KClass<? extends K> kClass, @NotNull V v2);
}
