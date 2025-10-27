package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nConstantValueFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConstantValueFactory.kt\norg/jetbrains/kotlin/resolve/constants/ConstantValueFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,103:1\n1603#2,9:104\n1855#2:113\n1856#2:115\n1612#2:116\n1#3:114\n*S KotlinDebug\n*F\n+ 1 ConstantValueFactory.kt\norg/jetbrains/kotlin/resolve/constants/ConstantValueFactory\n*L\n64#1:104,9\n64#1:113\n64#1:115\n64#1:116\n64#1:114\n*E\n"})
/* loaded from: classes8.dex */
public final class ConstantValueFactory {

    @NotNull
    public static final ConstantValueFactory INSTANCE = new ConstantValueFactory();

    private ConstantValueFactory() {
    }

    public static /* synthetic */ ConstantValue createConstantValue$default(ConstantValueFactory constantValueFactory, Object obj, ModuleDescriptor moduleDescriptor, int i2, Object obj2) {
        if ((i2 & 2) != 0) {
            moduleDescriptor = null;
        }
        return constantValueFactory.createConstantValue(obj, moduleDescriptor);
    }

    @NotNull
    public final ArrayValue createArrayValue(@NotNull List<? extends ConstantValue<?>> value, @NotNull KotlinType type) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        return new TypedArrayValue(value, type);
    }

    @Nullable
    public final ConstantValue<?> createConstantValue(@Nullable Object obj, @Nullable ModuleDescriptor moduleDescriptor) {
        if (obj instanceof Byte) {
            return new ByteValue(((Number) obj).byteValue());
        }
        if (obj instanceof Short) {
            return new ShortValue(((Number) obj).shortValue());
        }
        if (obj instanceof Integer) {
            return new IntValue(((Number) obj).intValue());
        }
        if (obj instanceof Long) {
            return new LongValue(((Number) obj).longValue());
        }
        if (obj instanceof Character) {
            return new CharValue(((Character) obj).charValue());
        }
        if (obj instanceof Float) {
            return new FloatValue(((Number) obj).floatValue());
        }
        if (obj instanceof Double) {
            return new DoubleValue(((Number) obj).doubleValue());
        }
        if (obj instanceof Boolean) {
            return new BooleanValue(((Boolean) obj).booleanValue());
        }
        if (obj instanceof String) {
            return new StringValue((String) obj);
        }
        if (obj instanceof byte[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((byte[]) obj), moduleDescriptor, PrimitiveType.BYTE);
        }
        if (obj instanceof short[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((short[]) obj), moduleDescriptor, PrimitiveType.SHORT);
        }
        if (obj instanceof int[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((int[]) obj), moduleDescriptor, PrimitiveType.INT);
        }
        if (obj instanceof long[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((long[]) obj), moduleDescriptor, PrimitiveType.LONG);
        }
        if (obj instanceof char[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((char[]) obj), moduleDescriptor, PrimitiveType.CHAR);
        }
        if (obj instanceof float[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((float[]) obj), moduleDescriptor, PrimitiveType.FLOAT);
        }
        if (obj instanceof double[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((double[]) obj), moduleDescriptor, PrimitiveType.DOUBLE);
        }
        if (obj instanceof boolean[]) {
            return createArrayValue(ArraysKt___ArraysKt.toList((boolean[]) obj), moduleDescriptor, PrimitiveType.BOOLEAN);
        }
        if (obj == null) {
            return new NullValue();
        }
        return null;
    }

    private final ArrayValue createArrayValue(List<?> list, ModuleDescriptor moduleDescriptor, final PrimitiveType primitiveType) {
        List list2 = CollectionsKt___CollectionsKt.toList(list);
        ArrayList arrayList = new ArrayList();
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            ConstantValue constantValueCreateConstantValue$default = createConstantValue$default(this, it.next(), null, 2, null);
            if (constantValueCreateConstantValue$default != null) {
                arrayList.add(constantValueCreateConstantValue$default);
            }
        }
        if (moduleDescriptor == null) {
            return new ArrayValue(arrayList, new Function1<ModuleDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory.createArrayValue.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final KotlinType invoke(@NotNull ModuleDescriptor it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    SimpleType primitiveArrayKotlinType = it2.getBuiltIns().getPrimitiveArrayKotlinType(primitiveType);
                    Intrinsics.checkNotNullExpressionValue(primitiveArrayKotlinType, "it.builtIns.getPrimitive…KotlinType(componentType)");
                    return primitiveArrayKotlinType;
                }
            });
        }
        SimpleType primitiveArrayKotlinType = moduleDescriptor.getBuiltIns().getPrimitiveArrayKotlinType(primitiveType);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayKotlinType, "module.builtIns.getPrimi…KotlinType(componentType)");
        return new TypedArrayValue(arrayList, primitiveArrayKotlinType);
    }
}
