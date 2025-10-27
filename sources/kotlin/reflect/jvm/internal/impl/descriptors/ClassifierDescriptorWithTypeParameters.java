package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface ClassifierDescriptorWithTypeParameters extends ClassifierDescriptor, DeclarationDescriptorWithVisibility, MemberDescriptor, Substitutable<ClassifierDescriptorWithTypeParameters> {
    @NotNull
    List<TypeParameterDescriptor> getDeclaredTypeParameters();

    boolean isInner();
}
