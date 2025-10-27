package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public abstract class UnwrappedType extends KotlinType {
    private UnwrappedType() {
        super(null);
    }

    public /* synthetic */ UnwrappedType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @NotNull
    public abstract UnwrappedType makeNullableAsSpecified(boolean z2);

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public abstract UnwrappedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner);

    @NotNull
    public abstract UnwrappedType replaceAttributes(@NotNull TypeAttributes typeAttributes);

    @Override // kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public final UnwrappedType unwrap() {
        return this;
    }
}
