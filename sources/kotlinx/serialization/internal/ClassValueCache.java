package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B%\u0012\u001e\u0010\u0003\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00060\u0004¢\u0006\u0002\u0010\u0007J\u001e\u0010\r\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005H\u0016R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R)\u0010\u0003\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0010"}, d2 = {"Lkotlinx/serialization/internal/ClassValueCache;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/serialization/internal/SerializerCache;", "compute", "Lkotlin/Function1;", "Lkotlin/reflect/KClass;", "Lkotlinx/serialization/KSerializer;", "(Lkotlin/jvm/functions/Function1;)V", "classValue", "Lkotlinx/serialization/internal/ClassValueReferences;", "Lkotlinx/serialization/internal/CacheEntry;", "getCompute", "()Lkotlin/jvm/functions/Function1;", "get", "key", "", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCaching.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Caching.kt\nkotlinx/serialization/internal/ClassValueCache\n+ 2 Caching.kt\nkotlinx/serialization/internal/ClassValueReferences\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,203:1\n77#2,3:204\n82#2:208\n1#3:207\n*S KotlinDebug\n*F\n+ 1 Caching.kt\nkotlinx/serialization/internal/ClassValueCache\n*L\n49#1:204,3\n49#1:208\n49#1:207\n*E\n"})
/* loaded from: classes8.dex */
final class ClassValueCache<T> implements SerializerCache<T> {

    @NotNull
    private final ClassValueReferences<CacheEntry<T>> classValue;

    @NotNull
    private final Function1<KClass<?>, KSerializer<T>> compute;

    /* JADX WARN: Multi-variable type inference failed */
    public ClassValueCache(@NotNull Function1<? super KClass<?>, ? extends KSerializer<T>> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        this.compute = compute;
        this.classValue = new ClassValueReferences<>();
    }

    @Override // kotlinx.serialization.internal.SerializerCache
    @Nullable
    public KSerializer<T> get(@NotNull final KClass<Object> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        CacheEntry<T> cacheEntry = this.classValue.get(JvmClassMappingKt.getJavaClass((KClass) key));
        Intrinsics.checkNotNullExpressionValue(cacheEntry, "get(key)");
        MutableSoftReference mutableSoftReference = (MutableSoftReference) cacheEntry;
        T t2 = mutableSoftReference.reference.get();
        if (t2 == null) {
            t2 = (T) mutableSoftReference.getOrSetWithLock(new Function0<T>() { // from class: kotlinx.serialization.internal.ClassValueCache$get$$inlined$getOrSet$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final T invoke() {
                    return (T) new CacheEntry(this.this$0.getCompute().invoke(key));
                }
            });
        }
        return t2.serializer;
    }

    @NotNull
    public final Function1<KClass<?>, KSerializer<T>> getCompute() {
        return this.compute;
    }
}
