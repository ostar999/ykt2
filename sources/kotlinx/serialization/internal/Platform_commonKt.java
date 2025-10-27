package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import com.vivo.push.PushClientConstants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.SerialDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0000\u001a\u0012\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n*\u00020\u0002H\u0000\u001a\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r*\u0006\u0012\u0002\b\u00030\fH\u0081\b\u001a\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\u000e\"\u0004\b\u0000\u0010\r*\u0006\u0012\u0002\b\u00030\u000eH\u0081\b\u001a\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\u000f\"\u0004\b\u0000\u0010\r*\u0006\u0012\u0002\b\u00030\u000fH\u0081\b\u001a\u001f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0011H\u0000¢\u0006\u0002\u0010\u0012\u001a6\u0010\u0013\u001a\u00020\u0014\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\r0\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00150\u0018H\u0080\bø\u0001\u0000\u001a\u0012\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a*\u00020\u001cH\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\u001aH\u0000\u001a\u0010\u0010\u001d\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u001aH\u0000\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0002X\u0083\u0004¢\u0006\n\n\u0002\u0010\u0005\u0012\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001f"}, d2 = {"EMPTY_DESCRIPTOR_ARRAY", "", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getEMPTY_DESCRIPTOR_ARRAY$annotations", "()V", "[Lkotlinx/serialization/descriptors/SerialDescriptor;", "notRegisteredMessage", "", PushClientConstants.TAG_CLASS_NAME, "cachedSerialNames", "", "cast", "Lkotlinx/serialization/DeserializationStrategy;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/serialization/KSerializer;", "Lkotlinx/serialization/SerializationStrategy;", "compactArray", "", "(Ljava/util/List;)[Lkotlinx/serialization/descriptors/SerialDescriptor;", "elementsHashCodeBy", "", "K", "", "selector", "Lkotlin/Function1;", "kclass", "Lkotlin/reflect/KClass;", "", "Lkotlin/reflect/KType;", "serializerNotRegistered", "", "kotlinx-serialization-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPlatform.common.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Platform.common.kt\nkotlinx/serialization/internal/Platform_commonKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,177:1\n1#2:178\n37#3,2:179\n1789#4,3:181\n*S KotlinDebug\n*F\n+ 1 Platform.common.kt\nkotlinx/serialization/internal/Platform_commonKt\n*L\n76#1:179,2\n154#1:181,3\n*E\n"})
/* loaded from: classes8.dex */
public final class Platform_commonKt {

    @NotNull
    private static final SerialDescriptor[] EMPTY_DESCRIPTOR_ARRAY = new SerialDescriptor[0];

    @NotNull
    public static final Set<String> cachedSerialNames(@NotNull SerialDescriptor serialDescriptor) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        if (serialDescriptor instanceof CachedNames) {
            return ((CachedNames) serialDescriptor).getSerialNames();
        }
        HashSet hashSet = new HashSet(serialDescriptor.getElementsCount());
        int elementsCount = serialDescriptor.getElementsCount();
        for (int i2 = 0; i2 < elementsCount; i2++) {
            hashSet.add(serialDescriptor.getElementName(i2));
        }
        return hashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @PublishedApi
    @NotNull
    public static final <T> DeserializationStrategy<T> cast(@NotNull DeserializationStrategy<?> deserializationStrategy) {
        Intrinsics.checkNotNullParameter(deserializationStrategy, "<this>");
        return deserializationStrategy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @PublishedApi
    @NotNull
    public static final <T> KSerializer<T> cast(@NotNull KSerializer<?> kSerializer) {
        Intrinsics.checkNotNullParameter(kSerializer, "<this>");
        return kSerializer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @PublishedApi
    @NotNull
    public static final <T> SerializationStrategy<T> cast(@NotNull SerializationStrategy<?> serializationStrategy) {
        Intrinsics.checkNotNullParameter(serializationStrategy, "<this>");
        return serializationStrategy;
    }

    @NotNull
    public static final SerialDescriptor[] compactArray(@Nullable List<? extends SerialDescriptor> list) {
        SerialDescriptor[] serialDescriptorArr;
        List<? extends SerialDescriptor> list2 = list;
        if (list2 == null || list2.isEmpty()) {
            list = null;
        }
        return (list == null || (serialDescriptorArr = (SerialDescriptor[]) list.toArray(new SerialDescriptor[0])) == null) ? EMPTY_DESCRIPTOR_ARRAY : serialDescriptorArr;
    }

    public static final <T, K> int elementsHashCodeBy(@NotNull Iterable<? extends T> iterable, @NotNull Function1<? super T, ? extends K> selector) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator<? extends T> it = iterable.iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            int i2 = iHashCode * 31;
            K kInvoke = selector.invoke(it.next());
            iHashCode = i2 + (kInvoke != null ? kInvoke.hashCode() : 0);
        }
        return iHashCode;
    }

    private static /* synthetic */ void getEMPTY_DESCRIPTOR_ARRAY$annotations() {
    }

    @NotNull
    public static final KClass<Object> kclass(@NotNull KType kType) {
        Intrinsics.checkNotNullParameter(kType, "<this>");
        KClassifier classifier = kType.getClassifier();
        if (classifier instanceof KClass) {
            return (KClass) classifier;
        }
        if (!(classifier instanceof KTypeParameter)) {
            throw new IllegalStateException(("Only KClass supported as classifier, got " + classifier).toString());
        }
        throw new IllegalStateException(("Captured type parameter " + classifier + " from generic non-reified function. Such functionality cannot be supported as " + classifier + " is erased, either specify serializer explicitly or make calling function inline with reified " + classifier).toString());
    }

    @NotNull
    public static final String notRegisteredMessage(@NotNull KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        String simpleName = kClass.getSimpleName();
        if (simpleName == null) {
            simpleName = "<local class name not available>";
        }
        return notRegisteredMessage(simpleName);
    }

    @NotNull
    public static final Void serializerNotRegistered(@NotNull KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        throw new SerializationException(notRegisteredMessage(kClass));
    }

    @NotNull
    public static final String notRegisteredMessage(@NotNull String className) {
        Intrinsics.checkNotNullParameter(className, "className");
        return "Serializer for class '" + className + "' is not found.\nPlease ensure that class is marked as '@Serializable' and that the serialization compiler plugin is applied.\n";
    }
}
