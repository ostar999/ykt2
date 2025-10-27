package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorScopeKind;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nNewCapturedType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NewCapturedType.kt\norg/jetbrains/kotlin/types/checker/NewCapturedType\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,290:1\n1#2:291\n*E\n"})
/* loaded from: classes8.dex */
public final class NewCapturedType extends SimpleType implements CapturedTypeMarker {

    @NotNull
    private final TypeAttributes attributes;

    @NotNull
    private final CaptureStatus captureStatus;

    @NotNull
    private final NewCapturedTypeConstructor constructor;
    private final boolean isMarkedNullable;
    private final boolean isProjectionNotNull;

    @Nullable
    private final UnwrappedType lowerType;

    public /* synthetic */ NewCapturedType(CaptureStatus captureStatus, NewCapturedTypeConstructor newCapturedTypeConstructor, UnwrappedType unwrappedType, TypeAttributes typeAttributes, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(captureStatus, newCapturedTypeConstructor, unwrappedType, (i2 & 8) != 0 ? TypeAttributes.Companion.getEmpty() : typeAttributes, (i2 & 16) != 0 ? false : z2, (i2 & 32) != 0 ? false : z3);
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

    @NotNull
    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    @Nullable
    public final UnwrappedType getLowerType() {
        return this.lowerType;
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

    public final boolean isProjectionNotNull() {
        return this.isProjectionNotNull;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public NewCapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType replaceAttributes(@NotNull TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, newAttributes, isMarkedNullable(), this.isProjectionNotNull);
    }

    public NewCapturedType(@NotNull CaptureStatus captureStatus, @NotNull NewCapturedTypeConstructor constructor, @Nullable UnwrappedType unwrappedType, @NotNull TypeAttributes attributes, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.captureStatus = captureStatus;
        this.constructor = constructor;
        this.lowerType = unwrappedType;
        this.attributes = attributes;
        this.isMarkedNullable = z2;
        this.isProjectionNotNull = z3;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public NewCapturedType makeNullableAsSpecified(boolean z2) {
        return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, getAttributes(), z2, false, 32, null);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public NewCapturedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        CaptureStatus captureStatus = this.captureStatus;
        NewCapturedTypeConstructor newCapturedTypeConstructorRefine = getConstructor().refine(kotlinTypeRefiner);
        UnwrappedType unwrappedType = this.lowerType;
        return new NewCapturedType(captureStatus, newCapturedTypeConstructorRefine, unwrappedType != null ? kotlinTypeRefiner.refineType((KotlinTypeMarker) unwrappedType).unwrap() : null, getAttributes(), isMarkedNullable(), false, 32, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NewCapturedType(@NotNull CaptureStatus captureStatus, @Nullable UnwrappedType unwrappedType, @NotNull TypeProjection projection, @NotNull TypeParameterDescriptor typeParameter) {
        this(captureStatus, new NewCapturedTypeConstructor(projection, null, null, typeParameter, 6, null), unwrappedType, null, false, false, 56, null);
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
    }
}
