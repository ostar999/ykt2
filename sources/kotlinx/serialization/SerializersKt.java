package kotlinx.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MagicApiIntrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlinx.serialization.modules.SerializersModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"kotlinx/serialization/SerializersKt__SerializersJvmKt", "kotlinx/serialization/SerializersKt__SerializersKt"}, k = 4, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class SerializersKt {
    @PublishedApi
    @NotNull
    public static final KSerializer<?> noCompiledSerializer(@NotNull String str) {
        return SerializersKt__SerializersKt.noCompiledSerializer(str);
    }

    @Nullable
    public static final KSerializer<? extends Object> parametrizedSerializerOrNull(@NotNull KClass<Object> kClass, @NotNull List<? extends KType> list, @NotNull List<? extends KSerializer<Object>> list2) {
        return SerializersKt__SerializersKt.parametrizedSerializerOrNull(kClass, list, list2);
    }

    public static final /* synthetic */ <T> KSerializer<T> serializer() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return (KSerializer<T>) serializer((KType) null);
    }

    @Nullable
    public static final KSerializer<Object> serializerOrNull(@NotNull Type type) {
        return SerializersKt__SerializersJvmKt.serializerOrNull(type);
    }

    @Nullable
    public static final List<KSerializer<Object>> serializersForParameters(@NotNull SerializersModule serializersModule, @NotNull List<? extends KType> list, boolean z2) {
        return SerializersKt__SerializersKt.serializersForParameters(serializersModule, list, z2);
    }

    @PublishedApi
    @NotNull
    public static final KSerializer<?> noCompiledSerializer(@NotNull SerializersModule serializersModule, @NotNull KClass<?> kClass) {
        return SerializersKt__SerializersKt.noCompiledSerializer(serializersModule, kClass);
    }

    @NotNull
    public static final KSerializer<Object> serializer(@NotNull Type type) {
        return SerializersKt__SerializersJvmKt.serializer(type);
    }

    @InternalSerializationApi
    @Nullable
    public static final <T> KSerializer<T> serializerOrNull(@NotNull KClass<T> kClass) {
        return SerializersKt__SerializersKt.serializerOrNull(kClass);
    }

    @PublishedApi
    @NotNull
    public static final KSerializer<?> noCompiledSerializer(@NotNull SerializersModule serializersModule, @NotNull KClass<?> kClass, @NotNull KSerializer<?>[] kSerializerArr) {
        return SerializersKt__SerializersKt.noCompiledSerializer(serializersModule, kClass, kSerializerArr);
    }

    @InternalSerializationApi
    @NotNull
    public static final <T> KSerializer<T> serializer(@NotNull KClass<T> kClass) {
        return SerializersKt__SerializersKt.serializer(kClass);
    }

    @Nullable
    public static final KSerializer<Object> serializerOrNull(@NotNull KType kType) {
        return SerializersKt__SerializersKt.serializerOrNull(kType);
    }

    @NotNull
    public static final KSerializer<Object> serializer(@NotNull KType kType) {
        return SerializersKt__SerializersKt.serializer(kType);
    }

    @Nullable
    public static final KSerializer<Object> serializerOrNull(@NotNull SerializersModule serializersModule, @NotNull Type type) {
        return SerializersKt__SerializersJvmKt.serializerOrNull(serializersModule, type);
    }

    public static final /* synthetic */ <T> KSerializer<T> serializer(SerializersModule serializersModule) {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.withModule");
        return (KSerializer<T>) serializer(serializersModule, (KType) null);
    }

    @Nullable
    public static final KSerializer<Object> serializerOrNull(@NotNull SerializersModule serializersModule, @NotNull KType kType) {
        return SerializersKt__SerializersKt.serializerOrNull(serializersModule, kType);
    }

    @NotNull
    public static final KSerializer<Object> serializer(@NotNull SerializersModule serializersModule, @NotNull Type type) {
        return SerializersKt__SerializersJvmKt.serializer(serializersModule, type);
    }

    @NotNull
    public static final KSerializer<Object> serializer(@NotNull SerializersModule serializersModule, @NotNull KType kType) {
        return SerializersKt__SerializersKt.serializer(serializersModule, kType);
    }
}
