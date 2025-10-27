package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.Set;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeUsage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class JavaTypeAttributes extends ErasureTypeAttributes {

    @Nullable
    private final SimpleType defaultType;

    @NotNull
    private final JavaTypeFlexibility flexibility;

    @NotNull
    private final TypeUsage howThisTypeIsUsed;
    private final boolean isForAnnotationParameter;
    private final boolean isRaw;

    @Nullable
    private final Set<TypeParameterDescriptor> visitedTypeParameters;

    public /* synthetic */ JavaTypeAttributes(TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z2, boolean z3, Set set, SimpleType simpleType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeUsage, (i2 & 2) != 0 ? JavaTypeFlexibility.INFLEXIBLE : javaTypeFlexibility, (i2 & 4) != 0 ? false : z2, (i2 & 8) != 0 ? false : z3, (i2 & 16) != 0 ? null : set, (i2 & 32) != 0 ? null : simpleType);
    }

    public static /* synthetic */ JavaTypeAttributes copy$default(JavaTypeAttributes javaTypeAttributes, TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z2, boolean z3, Set set, SimpleType simpleType, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            typeUsage = javaTypeAttributes.howThisTypeIsUsed;
        }
        if ((i2 & 2) != 0) {
            javaTypeFlexibility = javaTypeAttributes.flexibility;
        }
        JavaTypeFlexibility javaTypeFlexibility2 = javaTypeFlexibility;
        if ((i2 & 4) != 0) {
            z2 = javaTypeAttributes.isRaw;
        }
        boolean z4 = z2;
        if ((i2 & 8) != 0) {
            z3 = javaTypeAttributes.isForAnnotationParameter;
        }
        boolean z5 = z3;
        if ((i2 & 16) != 0) {
            set = javaTypeAttributes.visitedTypeParameters;
        }
        Set set2 = set;
        if ((i2 & 32) != 0) {
            simpleType = javaTypeAttributes.defaultType;
        }
        return javaTypeAttributes.copy(typeUsage, javaTypeFlexibility2, z4, z5, set2, simpleType);
    }

    @NotNull
    public final JavaTypeAttributes copy(@NotNull TypeUsage howThisTypeIsUsed, @NotNull JavaTypeFlexibility flexibility, boolean z2, boolean z3, @Nullable Set<? extends TypeParameterDescriptor> set, @Nullable SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(howThisTypeIsUsed, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter(flexibility, "flexibility");
        return new JavaTypeAttributes(howThisTypeIsUsed, flexibility, z2, z3, set, simpleType);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof JavaTypeAttributes)) {
            return false;
        }
        JavaTypeAttributes javaTypeAttributes = (JavaTypeAttributes) obj;
        return Intrinsics.areEqual(javaTypeAttributes.getDefaultType(), getDefaultType()) && javaTypeAttributes.getHowThisTypeIsUsed() == getHowThisTypeIsUsed() && javaTypeAttributes.flexibility == this.flexibility && javaTypeAttributes.isRaw == this.isRaw && javaTypeAttributes.isForAnnotationParameter == this.isForAnnotationParameter;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes
    @Nullable
    public SimpleType getDefaultType() {
        return this.defaultType;
    }

    @NotNull
    public final JavaTypeFlexibility getFlexibility() {
        return this.flexibility;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes
    @NotNull
    public TypeUsage getHowThisTypeIsUsed() {
        return this.howThisTypeIsUsed;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes
    @Nullable
    public Set<TypeParameterDescriptor> getVisitedTypeParameters() {
        return this.visitedTypeParameters;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes
    public int hashCode() {
        SimpleType defaultType = getDefaultType();
        int iHashCode = defaultType != null ? defaultType.hashCode() : 0;
        int iHashCode2 = iHashCode + (iHashCode * 31) + getHowThisTypeIsUsed().hashCode();
        int iHashCode3 = iHashCode2 + (iHashCode2 * 31) + this.flexibility.hashCode();
        int i2 = iHashCode3 + (iHashCode3 * 31) + (this.isRaw ? 1 : 0);
        return i2 + (i2 * 31) + (this.isForAnnotationParameter ? 1 : 0);
    }

    public final boolean isForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    public final boolean isRaw() {
        return this.isRaw;
    }

    @NotNull
    public final JavaTypeAttributes markIsRaw(boolean z2) {
        return copy$default(this, null, null, z2, false, null, null, 59, null);
    }

    @NotNull
    public String toString() {
        return "JavaTypeAttributes(howThisTypeIsUsed=" + this.howThisTypeIsUsed + ", flexibility=" + this.flexibility + ", isRaw=" + this.isRaw + ", isForAnnotationParameter=" + this.isForAnnotationParameter + ", visitedTypeParameters=" + this.visitedTypeParameters + ", defaultType=" + this.defaultType + ')';
    }

    @NotNull
    public JavaTypeAttributes withDefaultType(@Nullable SimpleType simpleType) {
        return copy$default(this, null, null, false, false, null, simpleType, 31, null);
    }

    @NotNull
    public final JavaTypeAttributes withFlexibility(@NotNull JavaTypeFlexibility flexibility) {
        Intrinsics.checkNotNullParameter(flexibility, "flexibility");
        return copy$default(this, null, flexibility, false, false, null, null, 61, null);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErasureTypeAttributes
    @NotNull
    public JavaTypeAttributes withNewVisitedTypeParameter(@NotNull TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return copy$default(this, null, null, false, false, getVisitedTypeParameters() != null ? SetsKt___SetsKt.plus((Set<? extends TypeParameterDescriptor>) ((Set<? extends Object>) getVisitedTypeParameters()), typeParameter) : SetsKt__SetsJVMKt.setOf(typeParameter), null, 47, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public JavaTypeAttributes(@NotNull TypeUsage howThisTypeIsUsed, @NotNull JavaTypeFlexibility flexibility, boolean z2, boolean z3, @Nullable Set<? extends TypeParameterDescriptor> set, @Nullable SimpleType simpleType) {
        super(howThisTypeIsUsed, set, simpleType);
        Intrinsics.checkNotNullParameter(howThisTypeIsUsed, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter(flexibility, "flexibility");
        this.howThisTypeIsUsed = howThisTypeIsUsed;
        this.flexibility = flexibility;
        this.isRaw = z2;
        this.isForAnnotationParameter = z3;
        this.visitedTypeParameters = set;
        this.defaultType = simpleType;
    }
}
