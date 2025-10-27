package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorScopeKind;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public abstract class AbstractStubType extends SimpleType {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean isMarkedNullable;

    @NotNull
    private final MemberScope memberScope;

    @NotNull
    private final NewTypeVariableConstructor originalTypeVariable;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AbstractStubType(@NotNull NewTypeVariableConstructor originalTypeVariable, boolean z2) {
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        this.originalTypeVariable = originalTypeVariable;
        this.isMarkedNullable = z2;
        this.memberScope = ErrorUtils.createErrorScope(ErrorScopeKind.STUB_TYPE_SCOPE, originalTypeVariable.toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public List<TypeProjection> getArguments() {
        return CollectionsKt__CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public TypeAttributes getAttributes() {
        return TypeAttributes.Companion.getEmpty();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    @NotNull
    public final NewTypeVariableConstructor getOriginalTypeVariable() {
        return this.originalTypeVariable;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @NotNull
    public abstract AbstractStubType materialize(boolean z2);

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public AbstractStubType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType replaceAttributes(@NotNull TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean z2) {
        return z2 == isMarkedNullable() ? this : materialize(z2);
    }
}
