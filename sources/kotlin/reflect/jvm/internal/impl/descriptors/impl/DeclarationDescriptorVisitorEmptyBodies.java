package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;

/* loaded from: classes8.dex */
public class DeclarationDescriptorVisitorEmptyBodies<R, D> implements DeclarationDescriptorVisitor<R, D> {
    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitClassDescriptor(ClassDescriptor classDescriptor, D d3) {
        return visitDeclarationDescriptor(classDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, D d3) {
        return visitFunctionDescriptor(constructorDescriptor, d3);
    }

    public R visitDeclarationDescriptor(DeclarationDescriptor declarationDescriptor, D d3) {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitFunctionDescriptor(FunctionDescriptor functionDescriptor, D d3) {
        return visitDeclarationDescriptor(functionDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitModuleDeclaration(ModuleDescriptor moduleDescriptor, D d3) {
        return visitDeclarationDescriptor(moduleDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, D d3) {
        return visitDeclarationDescriptor(packageFragmentDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, D d3) {
        return visitDeclarationDescriptor(packageViewDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, D d3) {
        return visitVariableDescriptor(propertyDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, D d3) {
        return visitFunctionDescriptor(propertyGetterDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, D d3) {
        return visitFunctionDescriptor(propertySetterDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, D d3) {
        return visitDeclarationDescriptor(receiverParameterDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, D d3) {
        return visitDeclarationDescriptor(typeAliasDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, D d3) {
        return visitDeclarationDescriptor(typeParameterDescriptor, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
    public R visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, D d3) {
        return visitVariableDescriptor(valueParameterDescriptor, d3);
    }

    public R visitVariableDescriptor(VariableDescriptor variableDescriptor, D d3) {
        return visitDeclarationDescriptor(variableDescriptor, d3);
    }
}
