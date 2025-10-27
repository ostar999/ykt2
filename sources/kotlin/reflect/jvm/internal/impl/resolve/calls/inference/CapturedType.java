package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorScopeKind;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class CapturedType extends SimpleType implements CapturedTypeMarker {

    @NotNull
    private final TypeAttributes attributes;

    @NotNull
    private final CapturedTypeConstructor constructor;
    private final boolean isMarkedNullable;

    @NotNull
    private final TypeProjection typeProjection;

    public /* synthetic */ CapturedType(TypeProjection typeProjection, CapturedTypeConstructor capturedTypeConstructor, boolean z2, TypeAttributes typeAttributes, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, (i2 & 2) != 0 ? new CapturedTypeConstructorImpl(typeProjection) : capturedTypeConstructor, (i2 & 4) != 0 ? false : z2, (i2 & 8) != 0 ? TypeAttributes.Companion.getEmpty() : typeAttributes);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public List<TypeProjection> getArguments() {
        return CollectionsKt__CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public TypeAttributes getAttributes() {
        return this.attributes;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public MemberScope getMemberScope() {
        return ErrorUtils.createErrorScope(ErrorScopeKind.CAPTURED_TYPE_SCOPE, true, new String[0]);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.SimpleType
    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Captured(");
        sb.append(this.typeProjection);
        sb.append(')');
        sb.append(isMarkedNullable() ? "?" : "");
        return sb.toString();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public CapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType replaceAttributes(@NotNull TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new CapturedType(this.typeProjection, getConstructor(), isMarkedNullable(), newAttributes);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public CapturedType makeNullableAsSpecified(boolean z2) {
        return z2 == isMarkedNullable() ? this : new CapturedType(this.typeProjection, getConstructor(), z2, getAttributes());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public CapturedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjectionRefine = this.typeProjection.refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeProjectionRefine, "typeProjection.refine(kotlinTypeRefiner)");
        return new CapturedType(typeProjectionRefine, getConstructor(), isMarkedNullable(), getAttributes());
    }

    public CapturedType(@NotNull TypeProjection typeProjection, @NotNull CapturedTypeConstructor constructor, boolean z2, @NotNull TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.typeProjection = typeProjection;
        this.constructor = constructor;
        this.isMarkedNullable = z2;
        this.attributes = attributes;
    }
}
