package kotlinx.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.am;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.Triple;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.HashMapSerializer;
import kotlinx.serialization.internal.HashSetSerializer;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.LinkedHashSetSerializer;
import kotlinx.serialization.internal.PlatformKt;
import kotlinx.serialization.internal.Platform_commonKt;
import kotlinx.serialization.internal.PrimitivesKt;
import kotlinx.serialization.modules.SerializersModule;
import kotlinx.serialization.modules.SerializersModuleBuildersKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\u001a\u0014\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a \u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0001\u001a7\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0010\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\tH\u0001¢\u0006\u0002\u0010\n\u001a\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\f0\u0001\"\u0006\b\u0000\u0010\f\u0018\u0001H\u0086\b\u001a\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00012\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u0018\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\u00012\u0006\u0010\u000e\u001a\u00020\u000f\u001aE\u0010\u0011\u001a\f\u0012\u0006\b\u0001\u0012\u00020\r\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\r0\u00072\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00132\u0014\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00010\u0013H\u0002¢\u0006\u0002\b\u0015\u001a7\u0010\u0016\u001a\f\u0012\u0006\b\u0001\u0012\u00020\r\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\r0\u00072\u0014\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00010\u0013H\u0002¢\u0006\u0002\b\u0017\u001a1\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\f0\u0001\"\b\b\u0000\u0010\f*\u00020\r*\b\u0012\u0004\u0012\u0002H\f0\u00012\u0006\u0010\u0019\u001a\u00020\u001aH\u0002¢\u0006\u0002\b\u001b\u001a@\u0010\u001c\u001a\f\u0012\u0006\b\u0001\u0012\u00020\r\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\r0\u00072\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00132\u0014\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00010\u0013H\u0000\u001a\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\f0\u0001\"\b\b\u0000\u0010\f*\u00020\r*\b\u0012\u0004\u0012\u0002H\f0\u0007H\u0007\u001a\u001b\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\f0\u0001\"\u0006\b\u0000\u0010\f\u0018\u0001*\u00020\u0005H\u0086\b\u001a\u001a\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0001*\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f\u001a+\u0010\u001e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\u0001*\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002¢\u0006\u0002\b \u001a$\u0010\u0010\u001a\n\u0012\u0004\u0012\u0002H\f\u0018\u00010\u0001\"\b\b\u0000\u0010\f*\u00020\r*\b\u0012\u0004\u0012\u0002H\f0\u0007H\u0007\u001a\u001c\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\u0001*\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f\u001a2\u0010!\u001a\u0012\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0001\u0018\u00010\u0013*\u00020\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00132\u0006\u0010\u001f\u001a\u00020\u001aH\u0000¨\u0006\""}, d2 = {"noCompiledSerializer", "Lkotlinx/serialization/KSerializer;", "forClass", "", am.f22442e, "Lkotlinx/serialization/modules/SerializersModule;", "kClass", "Lkotlin/reflect/KClass;", "argSerializers", "", "(Lkotlinx/serialization/modules/SerializersModule;Lkotlin/reflect/KClass;[Lkotlinx/serialization/KSerializer;)Lkotlinx/serialization/KSerializer;", "serializer", ExifInterface.GPS_DIRECTION_TRUE, "", "type", "Lkotlin/reflect/KType;", "serializerOrNull", "builtinParametrizedSerializer", "typeArguments", "", "serializers", "builtinParametrizedSerializer$SerializersKt__SerializersKt", "compiledParametrizedSerializer", "compiledParametrizedSerializer$SerializersKt__SerializersKt", "nullable", "shouldBeNullable", "", "nullable$SerializersKt__SerializersKt", "parametrizedSerializerOrNull", "types", "serializerByKTypeImpl", "failOnMissingTypeArgSerializer", "serializerByKTypeImpl$SerializersKt__SerializersKt", "serializersForParameters", "kotlinx-serialization-core"}, k = 5, mv = {1, 8, 0}, xi = 48, xs = "kotlinx/serialization/SerializersKt")
@SourceDebugExtension({"SMAP\nSerializers.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Serializers.kt\nkotlinx/serialization/SerializersKt__SerializersKt\n+ 2 Platform.common.kt\nkotlinx/serialization/internal/Platform_commonKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,303:1\n80#2:304\n80#2:305\n80#2:311\n1549#3:306\n1620#3,2:307\n1622#3:310\n1549#3:312\n1620#3,3:313\n1549#3:316\n1620#3,3:317\n1#4:309\n37#5,2:320\n*S KotlinDebug\n*F\n+ 1 Serializers.kt\nkotlinx/serialization/SerializersKt__SerializersKt\n*L\n35#1:304\n54#1:305\n164#1:311\n138#1:306\n138#1:307,2\n138#1:310\n175#1:312\n175#1:313,3\n177#1:316\n177#1:317,3\n242#1:320,2\n*E\n"})
/* loaded from: classes8.dex */
final /* synthetic */ class SerializersKt__SerializersKt {
    private static final KSerializer<? extends Object> builtinParametrizedSerializer$SerializersKt__SerializersKt(KClass<Object> kClass, List<? extends KType> list, List<? extends KSerializer<Object>> list2) {
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Collection.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(List.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(List.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(ArrayList.class))) {
            return new ArrayListSerializer(list2.get(0));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(HashSet.class))) {
            return new HashSetSerializer(list2.get(0));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Set.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Set.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(LinkedHashSet.class))) {
            return new LinkedHashSetSerializer(list2.get(0));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(HashMap.class))) {
            return new HashMapSerializer(list2.get(0), list2.get(1));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Map.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Map.class)) ? true : Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(LinkedHashMap.class))) {
            return new LinkedHashMapSerializer(list2.get(0), list2.get(1));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Map.Entry.class))) {
            return BuiltinSerializersKt.MapEntrySerializer(list2.get(0), list2.get(1));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Pair.class))) {
            return BuiltinSerializersKt.PairSerializer(list2.get(0), list2.get(1));
        }
        if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(Triple.class))) {
            return BuiltinSerializersKt.TripleSerializer(list2.get(0), list2.get(1), list2.get(2));
        }
        if (!PlatformKt.isReferenceArray(kClass)) {
            return null;
        }
        KClassifier classifier = list.get(0).getClassifier();
        Intrinsics.checkNotNull(classifier, "null cannot be cast to non-null type kotlin.reflect.KClass<kotlin.Any>");
        return BuiltinSerializersKt.ArraySerializer((KClass) classifier, list2.get(0));
    }

    private static final KSerializer<? extends Object> compiledParametrizedSerializer$SerializersKt__SerializersKt(KClass<Object> kClass, List<? extends KSerializer<Object>> list) {
        KSerializer[] kSerializerArr = (KSerializer[]) list.toArray(new KSerializer[0]);
        return PlatformKt.constructSerializerForGivenTypeArgs(kClass, (KSerializer<Object>[]) Arrays.copyOf(kSerializerArr, kSerializerArr.length));
    }

    @PublishedApi
    @NotNull
    public static final KSerializer<?> noCompiledSerializer(@NotNull String forClass) {
        Intrinsics.checkNotNullParameter(forClass, "forClass");
        throw new SerializationException(Platform_commonKt.notRegisteredMessage(forClass));
    }

    private static final <T> KSerializer<T> nullable$SerializersKt__SerializersKt(KSerializer<T> kSerializer, boolean z2) {
        if (z2) {
            return BuiltinSerializersKt.getNullable(kSerializer);
        }
        Intrinsics.checkNotNull(kSerializer, "null cannot be cast to non-null type kotlinx.serialization.KSerializer<T of kotlinx.serialization.SerializersKt__SerializersKt.nullable?>");
        return kSerializer;
    }

    @Nullable
    public static final KSerializer<? extends Object> parametrizedSerializerOrNull(@NotNull KClass<Object> kClass, @NotNull List<? extends KType> types, @NotNull List<? extends KSerializer<Object>> serializers) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(serializers, "serializers");
        KSerializer<? extends Object> kSerializerBuiltinParametrizedSerializer$SerializersKt__SerializersKt = builtinParametrizedSerializer$SerializersKt__SerializersKt(kClass, types, serializers);
        return kSerializerBuiltinParametrizedSerializer$SerializersKt__SerializersKt == null ? compiledParametrizedSerializer$SerializersKt__SerializersKt(kClass, serializers) : kSerializerBuiltinParametrizedSerializer$SerializersKt__SerializersKt;
    }

    public static final /* synthetic */ <T> KSerializer<T> serializer() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        KSerializer<T> kSerializer = (KSerializer<T>) SerializersKt.serializer((KType) null);
        Intrinsics.checkNotNull(kSerializer, "null cannot be cast to non-null type kotlinx.serialization.KSerializer<T of kotlinx.serialization.internal.Platform_commonKt.cast>");
        return kSerializer;
    }

    private static final KSerializer<Object> serializerByKTypeImpl$SerializersKt__SerializersKt(SerializersModule serializersModule, KType kType, boolean z2) {
        KSerializer<Object> kSerializerFindCachedSerializer;
        KSerializer<? extends Object> contextual;
        KClass<Object> kclass = Platform_commonKt.kclass(kType);
        boolean zIsMarkedNullable = kType.isMarkedNullable();
        List<KTypeProjection> arguments = kType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            KType type = ((KTypeProjection) it.next()).getType();
            if (type == null) {
                throw new IllegalArgumentException(("Star projections in type arguments are not allowed, but had " + kType).toString());
            }
            arrayList.add(type);
        }
        if (arrayList.isEmpty()) {
            kSerializerFindCachedSerializer = SerializersCacheKt.findCachedSerializer(kclass, zIsMarkedNullable);
        } else {
            Object objFindParametrizedCachedSerializer = SerializersCacheKt.findParametrizedCachedSerializer(kclass, arrayList, zIsMarkedNullable);
            if (z2) {
                if (Result.m789isFailureimpl(objFindParametrizedCachedSerializer)) {
                    objFindParametrizedCachedSerializer = null;
                }
                kSerializerFindCachedSerializer = (KSerializer) objFindParametrizedCachedSerializer;
            } else {
                if (Result.m786exceptionOrNullimpl(objFindParametrizedCachedSerializer) != null) {
                    return null;
                }
                kSerializerFindCachedSerializer = (KSerializer) objFindParametrizedCachedSerializer;
            }
        }
        if (kSerializerFindCachedSerializer != null) {
            return kSerializerFindCachedSerializer;
        }
        if (arrayList.isEmpty()) {
            contextual = SerializersModule.getContextual$default(serializersModule, kclass, null, 2, null);
        } else {
            List<KSerializer<Object>> listSerializersForParameters = SerializersKt.serializersForParameters(serializersModule, arrayList, z2);
            if (listSerializersForParameters == null) {
                return null;
            }
            KSerializer<? extends Object> kSerializerParametrizedSerializerOrNull = SerializersKt.parametrizedSerializerOrNull(kclass, arrayList, listSerializersForParameters);
            contextual = kSerializerParametrizedSerializerOrNull == null ? serializersModule.getContextual(kclass, listSerializersForParameters) : kSerializerParametrizedSerializerOrNull;
        }
        if (contextual != null) {
            return nullable$SerializersKt__SerializersKt(contextual, zIsMarkedNullable);
        }
        return null;
    }

    @Nullable
    public static final KSerializer<Object> serializerOrNull(@NotNull KType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return SerializersKt.serializerOrNull(SerializersModuleBuildersKt.EmptySerializersModule(), type);
    }

    @Nullable
    public static final List<KSerializer<Object>> serializersForParameters(@NotNull SerializersModule serializersModule, @NotNull List<? extends KType> typeArguments, boolean z2) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(serializersModule, "<this>");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (z2) {
            List<? extends KType> list = typeArguments;
            arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(SerializersKt.serializer(serializersModule, (KType) it.next()));
            }
        } else {
            List<? extends KType> list2 = typeArguments;
            arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                KSerializer<Object> kSerializerSerializerOrNull = SerializersKt.serializerOrNull(serializersModule, (KType) it2.next());
                if (kSerializerSerializerOrNull == null) {
                    return null;
                }
                arrayList.add(kSerializerSerializerOrNull);
            }
        }
        return arrayList;
    }

    @PublishedApi
    @NotNull
    public static final KSerializer<?> noCompiledSerializer(@NotNull SerializersModule module, @NotNull KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        KSerializer<?> contextual$default = SerializersModule.getContextual$default(module, kClass, null, 2, null);
        if (contextual$default != null) {
            return contextual$default;
        }
        Platform_commonKt.serializerNotRegistered(kClass);
        throw new KotlinNothingValueException();
    }

    @Nullable
    public static final KSerializer<Object> serializerOrNull(@NotNull SerializersModule serializersModule, @NotNull KType type) {
        Intrinsics.checkNotNullParameter(serializersModule, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return serializerByKTypeImpl$SerializersKt__SerializersKt(serializersModule, type, false);
    }

    @PublishedApi
    @NotNull
    public static final KSerializer<?> noCompiledSerializer(@NotNull SerializersModule module, @NotNull KClass<?> kClass, @NotNull KSerializer<?>[] argSerializers) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        Intrinsics.checkNotNullParameter(argSerializers, "argSerializers");
        KSerializer<?> contextual = module.getContextual(kClass, ArraysKt___ArraysJvmKt.asList(argSerializers));
        if (contextual != null) {
            return contextual;
        }
        Platform_commonKt.serializerNotRegistered(kClass);
        throw new KotlinNothingValueException();
    }

    public static final /* synthetic */ <T> KSerializer<T> serializer(SerializersModule serializersModule) {
        Intrinsics.checkNotNullParameter(serializersModule, "<this>");
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        KSerializer<T> kSerializer = (KSerializer<T>) SerializersKt.serializer(serializersModule, (KType) null);
        Intrinsics.checkNotNull(kSerializer, "null cannot be cast to non-null type kotlinx.serialization.KSerializer<T of kotlinx.serialization.internal.Platform_commonKt.cast>");
        return kSerializer;
    }

    @InternalSerializationApi
    @Nullable
    public static final <T> KSerializer<T> serializerOrNull(@NotNull KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        KSerializer<T> kSerializerCompiledSerializerImpl = PlatformKt.compiledSerializerImpl(kClass);
        return kSerializerCompiledSerializerImpl == null ? PrimitivesKt.builtinSerializerOrNull(kClass) : kSerializerCompiledSerializerImpl;
    }

    @NotNull
    public static final KSerializer<Object> serializer(@NotNull KType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return SerializersKt.serializer(SerializersModuleBuildersKt.EmptySerializersModule(), type);
    }

    @NotNull
    public static final KSerializer<Object> serializer(@NotNull SerializersModule serializersModule, @NotNull KType type) {
        Intrinsics.checkNotNullParameter(serializersModule, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        KSerializer<Object> kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt = serializerByKTypeImpl$SerializersKt__SerializersKt(serializersModule, type, true);
        if (kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt != null) {
            return kSerializerSerializerByKTypeImpl$SerializersKt__SerializersKt;
        }
        PlatformKt.platformSpecificSerializerNotRegistered(Platform_commonKt.kclass(type));
        throw new KotlinNothingValueException();
    }

    @InternalSerializationApi
    @NotNull
    public static final <T> KSerializer<T> serializer(@NotNull KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        KSerializer<T> kSerializerSerializerOrNull = SerializersKt.serializerOrNull(kClass);
        if (kSerializerSerializerOrNull != null) {
            return kSerializerSerializerOrNull;
        }
        Platform_commonKt.serializerNotRegistered(kClass);
        throw new KotlinNothingValueException();
    }
}
