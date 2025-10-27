package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.NullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class DefinitelyNotNullType extends DelegatingSimpleType implements CustomTypeParameter, DefinitelyNotNullTypeMarker {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final SimpleType original;
    private final boolean useCorrectedNullabilityForTypeParameters;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean canHaveUndefinedNullability(UnwrappedType unwrappedType) {
            return (unwrappedType.getConstructor() instanceof NewTypeVariableConstructor) || (unwrappedType.getConstructor().mo2086getDeclarationDescriptor() instanceof TypeParameterDescriptor) || (unwrappedType instanceof NewCapturedType) || (unwrappedType instanceof StubTypeForBuilderInference);
        }

        public static /* synthetic */ DefinitelyNotNullType makeDefinitelyNotNull$default(Companion companion, UnwrappedType unwrappedType, boolean z2, boolean z3, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                z2 = false;
            }
            if ((i2 & 4) != 0) {
                z3 = false;
            }
            return companion.makeDefinitelyNotNull(unwrappedType, z2, z3);
        }

        private final boolean makesSenseToBeDefinitelyNotNull(UnwrappedType unwrappedType, boolean z2) {
            boolean z3 = false;
            if (!canHaveUndefinedNullability(unwrappedType)) {
                return false;
            }
            if (unwrappedType instanceof StubTypeForBuilderInference) {
                return TypeUtils.isNullableType(unwrappedType);
            }
            ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = unwrappedType.getConstructor().mo2086getDeclarationDescriptor();
            TypeParameterDescriptorImpl typeParameterDescriptorImpl = classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptorImpl ? (TypeParameterDescriptorImpl) classifierDescriptorMo2086getDeclarationDescriptor : null;
            if (typeParameterDescriptorImpl != null && !typeParameterDescriptorImpl.isInitialized()) {
                z3 = true;
            }
            if (z3) {
                return true;
            }
            return (z2 && (unwrappedType.getConstructor().mo2086getDeclarationDescriptor() instanceof TypeParameterDescriptor)) ? TypeUtils.isNullableType(unwrappedType) : !NullabilityChecker.INSTANCE.isSubtypeOfAny(unwrappedType);
        }

        @JvmOverloads
        @Nullable
        public final DefinitelyNotNullType makeDefinitelyNotNull(@NotNull UnwrappedType type, boolean z2, boolean z3) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (type instanceof DefinitelyNotNullType) {
                return (DefinitelyNotNullType) type;
            }
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (!z3 && !makesSenseToBeDefinitelyNotNull(type, z2)) {
                return null;
            }
            if (type instanceof FlexibleType) {
                FlexibleType flexibleType = (FlexibleType) type;
                Intrinsics.areEqual(flexibleType.getLowerBound().getConstructor(), flexibleType.getUpperBound().getConstructor());
            }
            return new DefinitelyNotNullType(FlexibleTypesKt.lowerIfFlexible(type).makeNullableAsSpecified(false), z2, defaultConstructorMarker);
        }
    }

    private DefinitelyNotNullType(SimpleType simpleType, boolean z2) {
        this.original = simpleType;
        this.useCorrectedNullabilityForTypeParameters = z2;
    }

    public /* synthetic */ DefinitelyNotNullType(SimpleType simpleType, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(simpleType, z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType
    @NotNull
    public SimpleType getDelegate() {
        return this.original;
    }

    @NotNull
    public final SimpleType getOriginal() {
        return this.original;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    public boolean isMarkedNullable() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.CustomTypeParameter
    public boolean isTypeParameter() {
        return (getDelegate().getConstructor() instanceof NewTypeVariableConstructor) || (getDelegate().getConstructor().mo2086getDeclarationDescriptor() instanceof TypeParameterDescriptor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.CustomTypeParameter
    @NotNull
    public KotlinType substitutionResult(@NotNull KotlinType replacement) {
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return SpecialTypesKt.makeDefinitelyNotNullOrNotNull(replacement.unwrap(), this.useCorrectedNullabilityForTypeParameters);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.SimpleType
    @NotNull
    public String toString() {
        return getDelegate() + " & Any";
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean z2) {
        return z2 ? getDelegate().makeNullableAsSpecified(z2) : this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType replaceAttributes(@NotNull TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new DefinitelyNotNullType(getDelegate().replaceAttributes(newAttributes), this.useCorrectedNullabilityForTypeParameters);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType
    @NotNull
    public DefinitelyNotNullType replaceDelegate(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new DefinitelyNotNullType(delegate, this.useCorrectedNullabilityForTypeParameters);
    }
}
