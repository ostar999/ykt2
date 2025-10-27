package kotlinx.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.internal.CachingKt;
import kotlinx.serialization.internal.ParametrizedSerializerCache;
import kotlinx.serialization.internal.SerializerCache;
import kotlinx.serialization.modules.SerializersModuleBuildersKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0000\u001aD\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\r0\u00132\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0010\u001a\u00020\u0011H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0017\"\u001e\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0003\u0010\u0004\"\u001e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0004\"\u001e\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\b8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\t\u0010\u0004\"\u001e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0004\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, d2 = {"PARAMETRIZED_SERIALIZERS_CACHE", "Lkotlinx/serialization/internal/ParametrizedSerializerCache;", "", "getPARAMETRIZED_SERIALIZERS_CACHE$annotations", "()V", "PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE", "getPARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$annotations", "SERIALIZERS_CACHE", "Lkotlinx/serialization/internal/SerializerCache;", "getSERIALIZERS_CACHE$annotations", "SERIALIZERS_CACHE_NULLABLE", "getSERIALIZERS_CACHE_NULLABLE$annotations", "findCachedSerializer", "Lkotlinx/serialization/KSerializer;", "clazz", "Lkotlin/reflect/KClass;", "isNullable", "", "findParametrizedCachedSerializer", "Lkotlin/Result;", "types", "", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KClass;Ljava/util/List;Z)Ljava/lang/Object;", "kotlinx-serialization-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSerializersCache.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SerializersCache.kt\nkotlinx/serialization/SerializersCacheKt\n+ 2 Platform.common.kt\nkotlinx/serialization/internal/Platform_commonKt\n*L\n1#1,75:1\n80#2:76\n*S KotlinDebug\n*F\n+ 1 SerializersCache.kt\nkotlinx/serialization/SerializersCacheKt\n*L\n53#1:76\n*E\n"})
/* loaded from: classes8.dex */
public final class SerializersCacheKt {

    @NotNull
    private static final SerializerCache<? extends Object> SERIALIZERS_CACHE = CachingKt.createCache(new Function1<KClass<?>, KSerializer<? extends Object>>() { // from class: kotlinx.serialization.SerializersCacheKt$SERIALIZERS_CACHE$1
        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final KSerializer<? extends Object> invoke(@NotNull KClass<?> it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return SerializersKt.serializerOrNull(it);
        }
    });

    @NotNull
    private static final SerializerCache<Object> SERIALIZERS_CACHE_NULLABLE = CachingKt.createCache(new Function1<KClass<?>, KSerializer<Object>>() { // from class: kotlinx.serialization.SerializersCacheKt$SERIALIZERS_CACHE_NULLABLE$1
        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final KSerializer<Object> invoke(@NotNull KClass<?> it) {
            KSerializer<Object> nullable;
            Intrinsics.checkNotNullParameter(it, "it");
            KSerializer kSerializerSerializerOrNull = SerializersKt.serializerOrNull(it);
            if (kSerializerSerializerOrNull == null || (nullable = BuiltinSerializersKt.getNullable(kSerializerSerializerOrNull)) == null) {
                return null;
            }
            return nullable;
        }
    });

    @NotNull
    private static final ParametrizedSerializerCache<? extends Object> PARAMETRIZED_SERIALIZERS_CACHE = CachingKt.createParametrizedCache(new Function2<KClass<Object>, List<? extends KType>, KSerializer<? extends Object>>() { // from class: kotlinx.serialization.SerializersCacheKt$PARAMETRIZED_SERIALIZERS_CACHE$1
        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final KSerializer<? extends Object> invoke(@NotNull KClass<Object> clazz, @NotNull List<? extends KType> types) {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            Intrinsics.checkNotNullParameter(types, "types");
            List<KSerializer<Object>> listSerializersForParameters = SerializersKt.serializersForParameters(SerializersModuleBuildersKt.EmptySerializersModule(), types, true);
            Intrinsics.checkNotNull(listSerializersForParameters);
            return SerializersKt.parametrizedSerializerOrNull(clazz, types, listSerializersForParameters);
        }
    });

    @NotNull
    private static final ParametrizedSerializerCache<Object> PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE = CachingKt.createParametrizedCache(new Function2<KClass<Object>, List<? extends KType>, KSerializer<Object>>() { // from class: kotlinx.serialization.SerializersCacheKt$PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$1
        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final KSerializer<Object> invoke(@NotNull KClass<Object> clazz, @NotNull List<? extends KType> types) {
            KSerializer<Object> nullable;
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            Intrinsics.checkNotNullParameter(types, "types");
            List<KSerializer<Object>> listSerializersForParameters = SerializersKt.serializersForParameters(SerializersModuleBuildersKt.EmptySerializersModule(), types, true);
            Intrinsics.checkNotNull(listSerializersForParameters);
            KSerializer<? extends Object> kSerializerParametrizedSerializerOrNull = SerializersKt.parametrizedSerializerOrNull(clazz, types, listSerializersForParameters);
            if (kSerializerParametrizedSerializerOrNull == null || (nullable = BuiltinSerializersKt.getNullable(kSerializerParametrizedSerializerOrNull)) == null) {
                return null;
            }
            return nullable;
        }
    });

    @Nullable
    public static final KSerializer<Object> findCachedSerializer(@NotNull KClass<Object> clazz, boolean z2) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        if (z2) {
            return SERIALIZERS_CACHE_NULLABLE.get(clazz);
        }
        KSerializer<? extends Object> kSerializer = SERIALIZERS_CACHE.get(clazz);
        if (kSerializer != null) {
            return kSerializer;
        }
        return null;
    }

    @NotNull
    public static final Object findParametrizedCachedSerializer(@NotNull KClass<Object> clazz, @NotNull List<? extends KType> types, boolean z2) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(types, "types");
        return !z2 ? PARAMETRIZED_SERIALIZERS_CACHE.mo2375getgIAlus(clazz, types) : PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE.mo2375getgIAlus(clazz, types);
    }

    private static /* synthetic */ void getPARAMETRIZED_SERIALIZERS_CACHE$annotations() {
    }

    private static /* synthetic */ void getPARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$annotations() {
    }

    private static /* synthetic */ void getSERIALIZERS_CACHE$annotations() {
    }

    private static /* synthetic */ void getSERIALIZERS_CACHE_NULLABLE$annotations() {
    }
}
