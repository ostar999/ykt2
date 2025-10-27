package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface DeclarationDescriptor extends Named, Annotated {
    <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d3);

    @Nullable
    DeclarationDescriptor getContainingDeclaration();

    @NotNull
    DeclarationDescriptor getOriginal();
}
