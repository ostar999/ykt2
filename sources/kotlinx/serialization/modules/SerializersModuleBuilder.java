package kotlinx.serialization.modules;

import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.CharPool;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.d;
import com.vivo.push.PushClientConstants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.modules.ContextualProvider;
import kotlinx.serialization.modules.SerializersModuleCollector;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0001¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0001JQ\u0010\u0017\u001a\u00020\u0018\"\b\b\u0000\u0010\u0019*\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00190\u00052/\u0010\u001c\u001a+\u0012\u001d\u0012\u001b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u001d¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u001e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\bH\u0016J.\u0010\u0017\u001a\u00020\u0018\"\b\b\u0000\u0010\u0019*\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00190\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00190\u0013H\u0016J\u000e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u0016JF\u0010\"\u001a\u00020\u0018\"\b\b\u0000\u0010#*\u00020\u001a\"\b\b\u0001\u0010$*\u0002H#2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0\u00052\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H$0\u00052\f\u0010'\u001a\b\u0012\u0004\u0012\u0002H$0\u0013H\u0016JM\u0010(\u001a\u00020\u0018\"\b\b\u0000\u0010#*\u00020\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0\u00052+\u0010)\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\f\u0012\n\u0012\u0004\u0012\u0002H#\u0018\u00010\r0\bH\u0016JK\u0010*\u001a\u00020\u0018\"\b\b\u0000\u0010#*\u00020\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0\u00052)\u0010+\u001a%\u0012\u0013\u0012\u0011H#¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(,\u0012\f\u0012\n\u0012\u0004\u0012\u0002H#\u0018\u00010\u00100\bH\u0016JU\u0010-\u001a\u00020\u0018\"\b\b\u0000\u0010#*\u00020\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0\u00052+\u0010)\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\f\u0012\n\u0012\u0004\u0012\u0002H#\u0018\u00010\r0\b2\u0006\u0010.\u001a\u00020/H\u0001JS\u00100\u001a\u00020\u0018\"\b\b\u0000\u0010#*\u00020\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0\u00052)\u0010+\u001a%\u0012\u0013\u0012\u0011H#¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(,\u0012\f\u0012\n\u0012\u0004\u0012\u0002H#\u0018\u00010\u00100\b2\u0006\u0010.\u001a\u00020/H\u0001JP\u00101\u001a\u00020\u0018\"\b\b\u0000\u0010#*\u00020\u001a\"\b\b\u0001\u0010$*\u0002H#2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0\u00052\f\u00102\u001a\b\u0012\u0004\u0012\u0002H$0\u00052\f\u00103\u001a\b\u0012\u0004\u0012\u0002H$0\u00132\b\b\u0002\u0010.\u001a\u00020/H\u0001J2\u00104\u001a\u00020\u0018\"\b\b\u0000\u0010\u0019*\u00020\u001a2\f\u00105\u001a\b\u0012\u0004\u0012\u0002H\u00190\u00052\u0006\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010.\u001a\u00020/H\u0001R\u001e\u0010\u0003\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000RI\u0010\u0007\u001a=\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012/\u0012-\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\r0\bj\u0006\u0012\u0002\b\u0003`\u000e0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000f\u001a*\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u001c\u0012\u001a\u0012\u0002\b\u0003\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00100\bj\u0006\u0012\u0002\b\u0003`\u00110\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0012\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\u0014\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0018\u0012\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lkotlinx/serialization/modules/SerializersModuleBuilder;", "Lkotlinx/serialization/modules/SerializersModuleCollector;", "()V", "class2ContextualProvider", "", "Lkotlin/reflect/KClass;", "Lkotlinx/serialization/modules/ContextualProvider;", "polyBase2DefaultDeserializerProvider", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", PushClientConstants.TAG_CLASS_NAME, "Lkotlinx/serialization/DeserializationStrategy;", "Lkotlinx/serialization/modules/PolymorphicDeserializerProvider;", "polyBase2DefaultSerializerProvider", "Lkotlinx/serialization/SerializationStrategy;", "Lkotlinx/serialization/modules/PolymorphicSerializerProvider;", "polyBase2NamedSerializers", "Lkotlinx/serialization/KSerializer;", "polyBase2Serializers", "build", "Lkotlinx/serialization/modules/SerializersModule;", "contextual", "", ExifInterface.GPS_DIRECTION_TRUE, "", "kClass", d.M, "", "typeArgumentsSerializers", "serializer", "include", am.f22442e, "polymorphic", "Base", "Sub", "baseClass", "actualClass", "actualSerializer", "polymorphicDefaultDeserializer", "defaultDeserializerProvider", "polymorphicDefaultSerializer", "defaultSerializerProvider", "value", "registerDefaultPolymorphicDeserializer", "allowOverwrite", "", "registerDefaultPolymorphicSerializer", "registerPolymorphicSerializer", "concreteClass", "concreteSerializer", "registerSerializer", "forClass", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSerializersModuleBuilders.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SerializersModuleBuilders.kt\nkotlinx/serialization/modules/SerializersModuleBuilder\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,270:1\n361#2,7:271\n361#2,7:278\n1#3:285\n*S KotlinDebug\n*F\n+ 1 SerializersModuleBuilders.kt\nkotlinx/serialization/modules/SerializersModuleBuilder\n*L\n195#1:271,7\n197#1:278,7\n*E\n"})
/* loaded from: classes8.dex */
public final class SerializersModuleBuilder implements SerializersModuleCollector {

    @NotNull
    private final Map<KClass<?>, ContextualProvider> class2ContextualProvider = new HashMap();

    @NotNull
    private final Map<KClass<?>, Map<KClass<?>, KSerializer<?>>> polyBase2Serializers = new HashMap();

    @NotNull
    private final Map<KClass<?>, Function1<?, SerializationStrategy<?>>> polyBase2DefaultSerializerProvider = new HashMap();

    @NotNull
    private final Map<KClass<?>, Map<String, KSerializer<?>>> polyBase2NamedSerializers = new HashMap();

    @NotNull
    private final Map<KClass<?>, Function1<String, DeserializationStrategy<?>>> polyBase2DefaultDeserializerProvider = new HashMap();

    @PublishedApi
    public SerializersModuleBuilder() {
    }

    public static /* synthetic */ void registerPolymorphicSerializer$default(SerializersModuleBuilder serializersModuleBuilder, KClass kClass, KClass kClass2, KSerializer kSerializer, boolean z2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z2 = false;
        }
        serializersModuleBuilder.registerPolymorphicSerializer(kClass, kClass2, kSerializer, z2);
    }

    public static /* synthetic */ void registerSerializer$default(SerializersModuleBuilder serializersModuleBuilder, KClass kClass, ContextualProvider contextualProvider, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        serializersModuleBuilder.registerSerializer(kClass, contextualProvider, z2);
    }

    @PublishedApi
    @NotNull
    public final SerializersModule build() {
        return new SerialModuleImpl(this.class2ContextualProvider, this.polyBase2Serializers, this.polyBase2DefaultSerializerProvider, this.polyBase2NamedSerializers, this.polyBase2DefaultDeserializerProvider);
    }

    @Override // kotlinx.serialization.modules.SerializersModuleCollector
    public <T> void contextual(@NotNull KClass<T> kClass, @NotNull KSerializer<T> serializer) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        registerSerializer$default(this, kClass, new ContextualProvider.Argless(serializer), false, 4, null);
    }

    public final void include(@NotNull SerializersModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        module.dumpTo(this);
    }

    @Override // kotlinx.serialization.modules.SerializersModuleCollector
    public <Base, Sub extends Base> void polymorphic(@NotNull KClass<Base> baseClass, @NotNull KClass<Sub> actualClass, @NotNull KSerializer<Sub> actualSerializer) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        Intrinsics.checkNotNullParameter(actualClass, "actualClass");
        Intrinsics.checkNotNullParameter(actualSerializer, "actualSerializer");
        registerPolymorphicSerializer$default(this, baseClass, actualClass, actualSerializer, false, 8, null);
    }

    @Override // kotlinx.serialization.modules.SerializersModuleCollector
    @Deprecated(level = DeprecationLevel.WARNING, message = "Deprecated in favor of function with more precise name: polymorphicDefaultDeserializer", replaceWith = @ReplaceWith(expression = "polymorphicDefaultDeserializer(baseClass, defaultDeserializerProvider)", imports = {}))
    public <Base> void polymorphicDefault(@NotNull KClass<Base> kClass, @NotNull Function1<? super String, ? extends DeserializationStrategy<? extends Base>> function1) {
        SerializersModuleCollector.DefaultImpls.polymorphicDefault(this, kClass, function1);
    }

    @Override // kotlinx.serialization.modules.SerializersModuleCollector
    public <Base> void polymorphicDefaultDeserializer(@NotNull KClass<Base> baseClass, @NotNull Function1<? super String, ? extends DeserializationStrategy<? extends Base>> defaultDeserializerProvider) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        Intrinsics.checkNotNullParameter(defaultDeserializerProvider, "defaultDeserializerProvider");
        registerDefaultPolymorphicDeserializer(baseClass, defaultDeserializerProvider, false);
    }

    @Override // kotlinx.serialization.modules.SerializersModuleCollector
    public <Base> void polymorphicDefaultSerializer(@NotNull KClass<Base> baseClass, @NotNull Function1<? super Base, ? extends SerializationStrategy<? super Base>> defaultSerializerProvider) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        Intrinsics.checkNotNullParameter(defaultSerializerProvider, "defaultSerializerProvider");
        registerDefaultPolymorphicSerializer(baseClass, defaultSerializerProvider, false);
    }

    @JvmName(name = "registerDefaultPolymorphicDeserializer")
    public final <Base> void registerDefaultPolymorphicDeserializer(@NotNull KClass<Base> baseClass, @NotNull Function1<? super String, ? extends DeserializationStrategy<? extends Base>> defaultDeserializerProvider, boolean allowOverwrite) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        Intrinsics.checkNotNullParameter(defaultDeserializerProvider, "defaultDeserializerProvider");
        Function1<String, DeserializationStrategy<?>> function1 = this.polyBase2DefaultDeserializerProvider.get(baseClass);
        if (function1 == null || Intrinsics.areEqual(function1, defaultDeserializerProvider) || allowOverwrite) {
            this.polyBase2DefaultDeserializerProvider.put(baseClass, defaultDeserializerProvider);
            return;
        }
        throw new IllegalArgumentException("Default deserializers provider for " + baseClass + " is already registered: " + function1);
    }

    @JvmName(name = "registerDefaultPolymorphicSerializer")
    public final <Base> void registerDefaultPolymorphicSerializer(@NotNull KClass<Base> baseClass, @NotNull Function1<? super Base, ? extends SerializationStrategy<? super Base>> defaultSerializerProvider, boolean allowOverwrite) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        Intrinsics.checkNotNullParameter(defaultSerializerProvider, "defaultSerializerProvider");
        Function1<?, SerializationStrategy<?>> function1 = this.polyBase2DefaultSerializerProvider.get(baseClass);
        if (function1 == null || Intrinsics.areEqual(function1, defaultSerializerProvider) || allowOverwrite) {
            this.polyBase2DefaultSerializerProvider.put(baseClass, defaultSerializerProvider);
            return;
        }
        throw new IllegalArgumentException("Default serializers provider for " + baseClass + " is already registered: " + function1);
    }

    @JvmName(name = "registerPolymorphicSerializer")
    public final <Base, Sub extends Base> void registerPolymorphicSerializer(@NotNull KClass<Base> baseClass, @NotNull KClass<Sub> concreteClass, @NotNull KSerializer<Sub> concreteSerializer, boolean allowOverwrite) {
        Object next;
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        Intrinsics.checkNotNullParameter(concreteClass, "concreteClass");
        Intrinsics.checkNotNullParameter(concreteSerializer, "concreteSerializer");
        String serialName = concreteSerializer.getDescriptor().getSerialName();
        Map<KClass<?>, Map<KClass<?>, KSerializer<?>>> map = this.polyBase2Serializers;
        Map<KClass<?>, KSerializer<?>> map2 = map.get(baseClass);
        if (map2 == null) {
            map2 = new HashMap<>();
            map.put(baseClass, map2);
        }
        Map<KClass<?>, KSerializer<?>> map3 = map2;
        KSerializer<?> kSerializer = map3.get(concreteClass);
        Map<KClass<?>, Map<String, KSerializer<?>>> map4 = this.polyBase2NamedSerializers;
        Map<String, KSerializer<?>> map5 = map4.get(baseClass);
        if (map5 == null) {
            map5 = new HashMap<>();
            map4.put(baseClass, map5);
        }
        Map<String, KSerializer<?>> map6 = map5;
        if (allowOverwrite) {
            if (kSerializer != null) {
                map6.remove(kSerializer.getDescriptor().getSerialName());
            }
            map3.put(concreteClass, concreteSerializer);
            map6.put(serialName, concreteSerializer);
            return;
        }
        if (kSerializer != null) {
            if (!Intrinsics.areEqual(kSerializer, concreteSerializer)) {
                throw new SerializerAlreadyRegisteredException(baseClass, concreteClass);
            }
            map6.remove(kSerializer.getDescriptor().getSerialName());
        }
        KSerializer<?> kSerializer2 = map6.get(serialName);
        if (kSerializer2 == null) {
            map3.put(concreteClass, concreteSerializer);
            map6.put(serialName, concreteSerializer);
            return;
        }
        Map<KClass<?>, KSerializer<?>> map7 = this.polyBase2Serializers.get(baseClass);
        Intrinsics.checkNotNull(map7);
        Iterator it = MapsKt___MapsKt.asSequence(map7).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((Map.Entry) next).getValue() == kSerializer2) {
                    break;
                }
            }
        }
        throw new IllegalArgumentException("Multiple polymorphic serializers for base class '" + baseClass + "' have the same serial name '" + serialName + "': '" + concreteClass + "' and '" + ((Map.Entry) next) + CharPool.SINGLE_QUOTE);
    }

    @JvmName(name = "registerSerializer")
    public final <T> void registerSerializer(@NotNull KClass<T> forClass, @NotNull ContextualProvider provider, boolean allowOverwrite) {
        ContextualProvider contextualProvider;
        Intrinsics.checkNotNullParameter(forClass, "forClass");
        Intrinsics.checkNotNullParameter(provider, "provider");
        if (allowOverwrite || (contextualProvider = this.class2ContextualProvider.get(forClass)) == null || Intrinsics.areEqual(contextualProvider, provider)) {
            this.class2ContextualProvider.put(forClass, provider);
            return;
        }
        throw new SerializerAlreadyRegisteredException("Contextual serializer or serializer provider for " + forClass + " already registered in this module");
    }

    @Override // kotlinx.serialization.modules.SerializersModuleCollector
    public <T> void contextual(@NotNull KClass<T> kClass, @NotNull Function1<? super List<? extends KSerializer<?>>, ? extends KSerializer<?>> provider) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        Intrinsics.checkNotNullParameter(provider, "provider");
        registerSerializer$default(this, kClass, new ContextualProvider.WithTypeArguments(provider), false, 4, null);
    }
}
