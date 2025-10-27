package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.model.StubTypeMarker;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class StubTypeForBuilderInference extends AbstractStubType implements StubTypeMarker {

    @NotNull
    private final TypeConstructor constructor;

    @NotNull
    private final MemberScope memberScope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubTypeForBuilderInference(@NotNull NewTypeVariableConstructor originalTypeVariable, boolean z2, @NotNull TypeConstructor constructor) {
        super(originalTypeVariable, z2);
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        this.constructor = constructor;
        this.memberScope = originalTypeVariable.getBuiltIns().getAnyType().getMemberScope();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractStubType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractStubType
    @NotNull
    public AbstractStubType materialize(boolean z2) {
        return new StubTypeForBuilderInference(getOriginalTypeVariable(), z2, getConstructor());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.SimpleType
    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stub (BI): ");
        sb.append(getOriginalTypeVariable());
        sb.append(isMarkedNullable() ? "?" : "");
        return sb.toString();
    }
}
