package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorTypeKind;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class KClassValue extends ConstantValue<Value> {

    @NotNull
    public static final Companion Companion = new Companion(null);

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final ConstantValue<?> create(@NotNull KotlinType argumentType) {
            Intrinsics.checkNotNullParameter(argumentType, "argumentType");
            if (KotlinTypeKt.isError(argumentType)) {
                return null;
            }
            KotlinType type = argumentType;
            int i2 = 0;
            while (KotlinBuiltIns.isArray(type)) {
                type = ((TypeProjection) CollectionsKt___CollectionsKt.single((List) type.getArguments())).getType();
                Intrinsics.checkNotNullExpressionValue(type, "type.arguments.single().type");
                i2++;
            }
            ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = type.getConstructor().mo2086getDeclarationDescriptor();
            if (classifierDescriptorMo2086getDeclarationDescriptor instanceof ClassDescriptor) {
                ClassId classId = DescriptorUtilsKt.getClassId(classifierDescriptorMo2086getDeclarationDescriptor);
                return classId == null ? new KClassValue(new Value.LocalClass(argumentType)) : new KClassValue(classId, i2);
            }
            if (!(classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
                return null;
            }
            ClassId classId2 = ClassId.topLevel(StandardNames.FqNames.any.toSafe());
            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(StandardNames.FqNames.any.toSafe())");
            return new KClassValue(classId2, 0);
        }
    }

    public static abstract class Value {

        public static final class LocalClass extends Value {

            @NotNull
            private final KotlinType type;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public LocalClass(@NotNull KotlinType type) {
                super(null);
                Intrinsics.checkNotNullParameter(type, "type");
                this.type = type;
            }

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof LocalClass) && Intrinsics.areEqual(this.type, ((LocalClass) obj).type);
            }

            @NotNull
            public final KotlinType getType() {
                return this.type;
            }

            public int hashCode() {
                return this.type.hashCode();
            }

            @NotNull
            public String toString() {
                return "LocalClass(type=" + this.type + ')';
            }
        }

        public static final class NormalClass extends Value {

            @NotNull
            private final ClassLiteralValue value;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public NormalClass(@NotNull ClassLiteralValue value) {
                super(null);
                Intrinsics.checkNotNullParameter(value, "value");
                this.value = value;
            }

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof NormalClass) && Intrinsics.areEqual(this.value, ((NormalClass) obj).value);
            }

            public final int getArrayDimensions() {
                return this.value.getArrayNestedness();
            }

            @NotNull
            public final ClassId getClassId() {
                return this.value.getClassId();
            }

            @NotNull
            public final ClassLiteralValue getValue() {
                return this.value;
            }

            public int hashCode() {
                return this.value.hashCode();
            }

            @NotNull
            public String toString() {
                return "NormalClass(value=" + this.value + ')';
            }
        }

        private Value() {
        }

        public /* synthetic */ Value(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KClassValue(@NotNull Value value) {
        super(value);
        Intrinsics.checkNotNullParameter(value, "value");
    }

    @NotNull
    public final KotlinType getArgumentType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Value value = getValue();
        if (value instanceof Value.LocalClass) {
            return ((Value.LocalClass) getValue()).getType();
        }
        if (!(value instanceof Value.NormalClass)) {
            throw new NoWhenBranchMatchedException();
        }
        ClassLiteralValue value2 = ((Value.NormalClass) getValue()).getValue();
        ClassId classIdComponent1 = value2.component1();
        int iComponent2 = value2.component2();
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classIdComponent1);
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_KCLASS_CONSTANT_VALUE;
            String string = classIdComponent1.toString();
            Intrinsics.checkNotNullExpressionValue(string, "classId.toString()");
            return ErrorUtils.createErrorType(errorTypeKind, string, String.valueOf(iComponent2));
        }
        SimpleType defaultType = classDescriptorFindClassAcrossModuleDependencies.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "descriptor.defaultType");
        KotlinType kotlinTypeReplaceArgumentsWithStarProjections = TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType);
        for (int i2 = 0; i2 < iComponent2; i2++) {
            kotlinTypeReplaceArgumentsWithStarProjections = module.getBuiltIns().getArrayType(Variance.INVARIANT, kotlinTypeReplaceArgumentsWithStarProjections);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeReplaceArgumentsWithStarProjections, "module.builtIns.getArray…Variance.INVARIANT, type)");
        }
        return kotlinTypeReplaceArgumentsWithStarProjections;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    @NotNull
    public KotlinType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        TypeAttributes empty = TypeAttributes.Companion.getEmpty();
        ClassDescriptor kClass = module.getBuiltIns().getKClass();
        Intrinsics.checkNotNullExpressionValue(kClass, "module.builtIns.kClass");
        return KotlinTypeFactory.simpleNotNullType(empty, kClass, CollectionsKt__CollectionsJVMKt.listOf(new TypeProjectionImpl(getArgumentType(module))));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(@NotNull ClassLiteralValue value) {
        this(new Value.NormalClass(value));
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(@NotNull ClassId classId, int i2) {
        this(new ClassLiteralValue(classId, i2));
        Intrinsics.checkNotNullParameter(classId, "classId");
    }
}
