package kotlin.reflect.jvm.internal.impl.types;

import java.util.Set;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nErasureTypeAttributes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ErasureTypeAttributes.kt\norg/jetbrains/kotlin/types/ErasureTypeAttributes\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,35:1\n1#2:36\n*E\n"})
/* loaded from: classes8.dex */
public class ErasureTypeAttributes {

    @Nullable
    private final SimpleType defaultType;

    @NotNull
    private final TypeUsage howThisTypeIsUsed;

    @Nullable
    private final Set<TypeParameterDescriptor> visitedTypeParameters;

    /* JADX WARN: Multi-variable type inference failed */
    public ErasureTypeAttributes(@NotNull TypeUsage howThisTypeIsUsed, @Nullable Set<? extends TypeParameterDescriptor> set, @Nullable SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(howThisTypeIsUsed, "howThisTypeIsUsed");
        this.howThisTypeIsUsed = howThisTypeIsUsed;
        this.visitedTypeParameters = set;
        this.defaultType = simpleType;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ErasureTypeAttributes)) {
            return false;
        }
        ErasureTypeAttributes erasureTypeAttributes = (ErasureTypeAttributes) obj;
        return Intrinsics.areEqual(erasureTypeAttributes.getDefaultType(), getDefaultType()) && erasureTypeAttributes.getHowThisTypeIsUsed() == getHowThisTypeIsUsed();
    }

    @Nullable
    public SimpleType getDefaultType() {
        return this.defaultType;
    }

    @NotNull
    public TypeUsage getHowThisTypeIsUsed() {
        return this.howThisTypeIsUsed;
    }

    @Nullable
    public Set<TypeParameterDescriptor> getVisitedTypeParameters() {
        return this.visitedTypeParameters;
    }

    public int hashCode() {
        SimpleType defaultType = getDefaultType();
        int iHashCode = defaultType != null ? defaultType.hashCode() : 0;
        return iHashCode + (iHashCode * 31) + getHowThisTypeIsUsed().hashCode();
    }

    @NotNull
    public ErasureTypeAttributes withNewVisitedTypeParameter(@NotNull TypeParameterDescriptor typeParameter) {
        Set of;
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        TypeUsage howThisTypeIsUsed = getHowThisTypeIsUsed();
        Set<TypeParameterDescriptor> visitedTypeParameters = getVisitedTypeParameters();
        if (visitedTypeParameters == null || (of = SetsKt___SetsKt.plus((Set<? extends TypeParameterDescriptor>) ((Set<? extends Object>) visitedTypeParameters), typeParameter)) == null) {
            of = SetsKt__SetsJVMKt.setOf(typeParameter);
        }
        return new ErasureTypeAttributes(howThisTypeIsUsed, of, getDefaultType());
    }
}
