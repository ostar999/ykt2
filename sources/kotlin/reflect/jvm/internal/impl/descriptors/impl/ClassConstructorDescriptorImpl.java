package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class ClassConstructorDescriptorImpl extends FunctionDescriptorImpl implements ClassConstructorDescriptor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final boolean isPrimary;

    /* JADX WARN: Removed duplicated region for block: B:13:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static /* synthetic */ void $$$reportNull$$$0(int r8) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl.$$$reportNull$$$0(int):void");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassConstructorDescriptorImpl(@NotNull ClassDescriptor classDescriptor, @Nullable ConstructorDescriptor constructorDescriptor, @NotNull Annotations annotations, boolean z2, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement sourceElement) {
        super(classDescriptor, constructorDescriptor, annotations, SpecialNames.INIT, kind, sourceElement);
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (kind == null) {
            $$$reportNull$$$0(2);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(3);
        }
        this.isPrimary = z2;
    }

    @NotNull
    private List<ReceiverParameterDescriptor> calculateContextReceiverParameters() {
        ClassDescriptor containingDeclaration = getContainingDeclaration();
        if (containingDeclaration.getContextReceivers().isEmpty()) {
            List<ReceiverParameterDescriptor> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(16);
            }
            return listEmptyList;
        }
        List<ReceiverParameterDescriptor> contextReceivers = containingDeclaration.getContextReceivers();
        if (contextReceivers == null) {
            $$$reportNull$$$0(15);
        }
        return contextReceivers;
    }

    @NotNull
    public static ClassConstructorDescriptorImpl create(@NotNull ClassDescriptor classDescriptor, @NotNull Annotations annotations, boolean z2, @NotNull SourceElement sourceElement) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(6);
        }
        return new ClassConstructorDescriptorImpl(classDescriptor, null, annotations, z2, CallableMemberDescriptor.Kind.DECLARATION, sourceElement);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d3) {
        return declarationDescriptorVisitor.visitConstructorDescriptor(this, d3);
    }

    @Nullable
    public ReceiverParameterDescriptor calculateDispatchReceiverParameter() {
        ClassDescriptor containingDeclaration = getContainingDeclaration();
        if (!containingDeclaration.isInner()) {
            return null;
        }
        DeclarationDescriptor containingDeclaration2 = containingDeclaration.getContainingDeclaration();
        if (containingDeclaration2 instanceof ClassDescriptor) {
            return ((ClassDescriptor) containingDeclaration2).getThisAsReceiverParameter();
        }
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor
    @NotNull
    public ClassDescriptor getConstructedClass() {
        ClassDescriptor containingDeclaration = getContainingDeclaration();
        if (containingDeclaration == null) {
            $$$reportNull$$$0(18);
        }
        return containingDeclaration;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
    @NotNull
    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        Set setEmptySet = Collections.emptySet();
        if (setEmptySet == null) {
            $$$reportNull$$$0(21);
        }
        return setEmptySet;
    }

    public ClassConstructorDescriptorImpl initialize(@NotNull List<ValueParameterDescriptor> list, @NotNull DescriptorVisibility descriptorVisibility, @NotNull List<TypeParameterDescriptor> list2) {
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(11);
        }
        if (list2 == null) {
            $$$reportNull$$$0(12);
        }
        super.initialize(null, calculateDispatchReceiverParameter(), calculateContextReceiverParameters(), list2, list, null, Modality.FINAL, descriptorVisibility);
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor
    public boolean isPrimary() {
        return this.isPrimary;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> collection) {
        if (collection == null) {
            $$$reportNull$$$0(22);
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    @NotNull
    public ClassConstructorDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor declarationDescriptor, @Nullable FunctionDescriptor functionDescriptor, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name name, @NotNull Annotations annotations, @NotNull SourceElement sourceElement) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        if (kind == null) {
            $$$reportNull$$$0(24);
        }
        if (annotations == null) {
            $$$reportNull$$$0(25);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(26);
        }
        CallableMemberDescriptor.Kind kind2 = CallableMemberDescriptor.Kind.DECLARATION;
        if (kind == kind2 || kind == CallableMemberDescriptor.Kind.SYNTHESIZED) {
            return new ClassConstructorDescriptorImpl((ClassDescriptor) declarationDescriptor, this, annotations, this.isPrimary, kind2, sourceElement);
        }
        throw new IllegalStateException("Attempt at creating a constructor that is not a declaration: \ncopy from: " + this + "\nnewOwner: " + declarationDescriptor + "\nkind: " + kind);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor
    @NotNull
    public ClassConstructorDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z2) {
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) super.copy(declarationDescriptor, modality, descriptorVisibility, kind, z2);
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return classConstructorDescriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @NotNull
    public ClassDescriptor getContainingDeclaration() {
        ClassDescriptor classDescriptor = (ClassDescriptor) super.getContainingDeclaration();
        if (classDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        return classDescriptor;
    }

    public ClassConstructorDescriptorImpl initialize(@NotNull List<ValueParameterDescriptor> list, @NotNull DescriptorVisibility descriptorVisibility) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(14);
        }
        initialize(list, descriptorVisibility, getContainingDeclaration().getDeclaredTypeParameters());
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.Substitutable
    @Nullable
    public ClassConstructorDescriptor substitute(@NotNull TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(20);
        }
        return (ClassConstructorDescriptor) super.substitute(typeSubstitutor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @NotNull
    public ClassConstructorDescriptor getOriginal() {
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) super.getOriginal();
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(19);
        }
        return classConstructorDescriptor;
    }
}
