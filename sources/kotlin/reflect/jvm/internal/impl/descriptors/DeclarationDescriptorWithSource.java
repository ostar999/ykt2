package kotlin.reflect.jvm.internal.impl.descriptors;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface DeclarationDescriptorWithSource extends DeclarationDescriptor {
    @NotNull
    SourceElement getSource();
}
