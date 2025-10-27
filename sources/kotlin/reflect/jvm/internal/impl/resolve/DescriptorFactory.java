package kotlin.reflect.jvm.internal.impl.resolve;

import com.tencent.open.SocialConstants;
import java.util.Collections;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ReceiverParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.NameUtils;
import kotlin.reflect.jvm.internal.impl.name.StandardClassIds;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ContextClassReceiver;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ContextReceiver;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ExtensionReceiver;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class DescriptorFactory {

    public static class DefaultClassConstructorDescriptor extends ClassConstructorDescriptorImpl {
        private static /* synthetic */ void $$$reportNull$$$0(int i2) {
            Object[] objArr = new Object[3];
            if (i2 != 1) {
                objArr[0] = "containingClass";
            } else {
                objArr[0] = SocialConstants.PARAM_SOURCE;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorFactory$DefaultClassConstructorDescriptor";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DefaultClassConstructorDescriptor(@NotNull ClassDescriptor classDescriptor, @NotNull SourceElement sourceElement, boolean z2) {
            super(classDescriptor, null, Annotations.Companion.getEMPTY(), true, CallableMemberDescriptor.Kind.DECLARATION, sourceElement);
            if (classDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (sourceElement == null) {
                $$$reportNull$$$0(1);
            }
            initialize(Collections.emptyList(), DescriptorUtils.getDefaultConstructorVisibility(classDescriptor, z2));
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
        String str = (i2 == 12 || i2 == 23 || i2 == 25) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i2 == 12 || i2 == 23 || i2 == 25) ? 2 : 3];
        switch (i2) {
            case 1:
            case 4:
            case 8:
            case 14:
            case 16:
            case 18:
            case 31:
            case 33:
            case 35:
                objArr[0] = "annotations";
                break;
            case 2:
            case 5:
            case 9:
                objArr[0] = "parameterAnnotations";
                break;
            case 3:
            case 7:
            case 13:
            case 15:
            case 17:
            default:
                objArr[0] = "propertyDescriptor";
                break;
            case 6:
            case 11:
            case 19:
                objArr[0] = "sourceElement";
                break;
            case 10:
                objArr[0] = "visibility";
                break;
            case 12:
            case 23:
            case 25:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorFactory";
                break;
            case 20:
                objArr[0] = "containingClass";
                break;
            case 21:
                objArr[0] = SocialConstants.PARAM_SOURCE;
                break;
            case 22:
            case 24:
            case 26:
                objArr[0] = "enumClass";
                break;
            case 27:
            case 28:
            case 29:
                objArr[0] = "descriptor";
                break;
            case 30:
            case 32:
            case 34:
                objArr[0] = "owner";
                break;
        }
        if (i2 == 12) {
            objArr[1] = "createSetter";
        } else if (i2 == 23) {
            objArr[1] = "createEnumValuesMethod";
        } else if (i2 != 25) {
            objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorFactory";
        } else {
            objArr[1] = "createEnumValueOfMethod";
        }
        switch (i2) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "createSetter";
                break;
            case 12:
            case 23:
            case 25:
                break;
            case 13:
            case 14:
                objArr[2] = "createDefaultGetter";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "createGetter";
                break;
            case 20:
            case 21:
                objArr[2] = "createPrimaryConstructorForObject";
                break;
            case 22:
                objArr[2] = "createEnumValuesMethod";
                break;
            case 24:
                objArr[2] = "createEnumValueOfMethod";
                break;
            case 26:
                objArr[2] = "createEnumEntriesProperty";
                break;
            case 27:
                objArr[2] = "isEnumValuesMethod";
                break;
            case 28:
                objArr[2] = "isEnumValueOfMethod";
                break;
            case 29:
                objArr[2] = "isEnumSpecialMethod";
                break;
            case 30:
            case 31:
                objArr[2] = "createExtensionReceiverParameterForCallable";
                break;
            case 32:
            case 33:
                objArr[2] = "createContextReceiverParameterForCallable";
                break;
            case 34:
            case 35:
                objArr[2] = "createContextReceiverParameterForClass";
                break;
            default:
                objArr[2] = "createDefaultSetter";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i2 != 12 && i2 != 23 && i2 != 25) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Nullable
    public static ReceiverParameterDescriptor createContextReceiverParameterForCallable(@NotNull CallableDescriptor callableDescriptor, @Nullable KotlinType kotlinType, @Nullable Name name, @NotNull Annotations annotations, int i2) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(32);
        }
        if (annotations == null) {
            $$$reportNull$$$0(33);
        }
        if (kotlinType == null) {
            return null;
        }
        return new ReceiverParameterDescriptorImpl(callableDescriptor, new ContextReceiver(callableDescriptor, kotlinType, name, null), annotations, NameUtils.contextReceiverName(i2));
    }

    @Nullable
    public static ReceiverParameterDescriptor createContextReceiverParameterForClass(@NotNull ClassDescriptor classDescriptor, @Nullable KotlinType kotlinType, @Nullable Name name, @NotNull Annotations annotations, int i2) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(34);
        }
        if (annotations == null) {
            $$$reportNull$$$0(35);
        }
        if (kotlinType == null) {
            return null;
        }
        return new ReceiverParameterDescriptorImpl(classDescriptor, new ContextClassReceiver(classDescriptor, kotlinType, name, null), annotations, NameUtils.contextReceiverName(i2));
    }

    @NotNull
    public static PropertyGetterDescriptorImpl createDefaultGetter(@NotNull PropertyDescriptor propertyDescriptor, @NotNull Annotations annotations) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        if (annotations == null) {
            $$$reportNull$$$0(14);
        }
        return createGetter(propertyDescriptor, annotations, true, false, false);
    }

    @NotNull
    public static PropertySetterDescriptorImpl createDefaultSetter(@NotNull PropertyDescriptor propertyDescriptor, @NotNull Annotations annotations, @NotNull Annotations annotations2) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (annotations2 == null) {
            $$$reportNull$$$0(2);
        }
        return createSetter(propertyDescriptor, annotations, annotations2, true, false, false, propertyDescriptor.getSource());
    }

    @Nullable
    public static PropertyDescriptor createEnumEntriesProperty(@NotNull ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(DescriptorUtils.getContainingModule(classDescriptor), StandardClassIds.INSTANCE.getEnumEntries());
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            return null;
        }
        Annotations.Companion companion = Annotations.Companion;
        Annotations empty = companion.getEMPTY();
        Modality modality = Modality.FINAL;
        DescriptorVisibility descriptorVisibility = DescriptorVisibilities.PUBLIC;
        Name name = StandardNames.ENUM_ENTRIES;
        CallableMemberDescriptor.Kind kind = CallableMemberDescriptor.Kind.SYNTHESIZED;
        PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(classDescriptor, empty, modality, descriptorVisibility, false, name, kind, classDescriptor.getSource(), false, false, false, false, false, false);
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl = new PropertyGetterDescriptorImpl(propertyDescriptorImplCreate, companion.getEMPTY(), modality, descriptorVisibility, false, false, false, kind, null, classDescriptor.getSource());
        propertyDescriptorImplCreate.initialize(propertyGetterDescriptorImpl, null);
        propertyDescriptorImplCreate.setType(KotlinTypeFactory.simpleType(TypeAttributes.Companion.getEmpty(), classDescriptorFindClassAcrossModuleDependencies.getTypeConstructor(), Collections.singletonList(new TypeProjectionImpl(classDescriptor.getDefaultType())), false), Collections.emptyList(), null, null, Collections.emptyList());
        propertyGetterDescriptorImpl.initialize(propertyDescriptorImplCreate.getReturnType());
        return propertyDescriptorImplCreate;
    }

    @NotNull
    public static SimpleFunctionDescriptor createEnumValueOfMethod(@NotNull ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        Annotations.Companion companion = Annotations.Companion;
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(classDescriptor, companion.getEMPTY(), StandardNames.ENUM_VALUE_OF, CallableMemberDescriptor.Kind.SYNTHESIZED, classDescriptor.getSource());
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplInitialize = simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, Collections.emptyList(), Collections.emptyList(), Collections.singletonList(new ValueParameterDescriptorImpl(simpleFunctionDescriptorImplCreate, null, 0, companion.getEMPTY(), Name.identifier("value"), DescriptorUtilsKt.getBuiltIns(classDescriptor).getStringType(), false, false, false, null, classDescriptor.getSource())), (KotlinType) classDescriptor.getDefaultType(), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        if (simpleFunctionDescriptorImplInitialize == null) {
            $$$reportNull$$$0(25);
        }
        return simpleFunctionDescriptorImplInitialize;
    }

    @NotNull
    public static SimpleFunctionDescriptor createEnumValuesMethod(@NotNull ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(22);
        }
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplInitialize = SimpleFunctionDescriptorImpl.create(classDescriptor, Annotations.Companion.getEMPTY(), StandardNames.ENUM_VALUES, CallableMemberDescriptor.Kind.SYNTHESIZED, classDescriptor.getSource()).initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), (KotlinType) DescriptorUtilsKt.getBuiltIns(classDescriptor).getArrayType(Variance.INVARIANT, classDescriptor.getDefaultType()), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        if (simpleFunctionDescriptorImplInitialize == null) {
            $$$reportNull$$$0(23);
        }
        return simpleFunctionDescriptorImplInitialize;
    }

    @Nullable
    public static ReceiverParameterDescriptor createExtensionReceiverParameterForCallable(@NotNull CallableDescriptor callableDescriptor, @Nullable KotlinType kotlinType, @NotNull Annotations annotations) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        if (annotations == null) {
            $$$reportNull$$$0(31);
        }
        if (kotlinType == null) {
            return null;
        }
        return new ReceiverParameterDescriptorImpl(callableDescriptor, new ExtensionReceiver(callableDescriptor, kotlinType, null), annotations);
    }

    @NotNull
    public static PropertyGetterDescriptorImpl createGetter(@NotNull PropertyDescriptor propertyDescriptor, @NotNull Annotations annotations, boolean z2, boolean z3, boolean z4) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (annotations == null) {
            $$$reportNull$$$0(16);
        }
        return createGetter(propertyDescriptor, annotations, z2, z3, z4, propertyDescriptor.getSource());
    }

    @NotNull
    public static ClassConstructorDescriptorImpl createPrimaryConstructorForObject(@NotNull ClassDescriptor classDescriptor, @NotNull SourceElement sourceElement) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(21);
        }
        return new DefaultClassConstructorDescriptor(classDescriptor, sourceElement, false);
    }

    @NotNull
    public static PropertySetterDescriptorImpl createSetter(@NotNull PropertyDescriptor propertyDescriptor, @NotNull Annotations annotations, @NotNull Annotations annotations2, boolean z2, boolean z3, boolean z4, @NotNull SourceElement sourceElement) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (annotations == null) {
            $$$reportNull$$$0(4);
        }
        if (annotations2 == null) {
            $$$reportNull$$$0(5);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(6);
        }
        return createSetter(propertyDescriptor, annotations, annotations2, z2, z3, z4, propertyDescriptor.getVisibility(), sourceElement);
    }

    private static boolean isEnumSpecialMethod(@NotNull FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        return functionDescriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED && DescriptorUtils.isEnumClass(functionDescriptor.getContainingDeclaration());
    }

    public static boolean isEnumValueOfMethod(@NotNull FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(28);
        }
        return functionDescriptor.getName().equals(StandardNames.ENUM_VALUE_OF) && isEnumSpecialMethod(functionDescriptor);
    }

    public static boolean isEnumValuesMethod(@NotNull FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return functionDescriptor.getName().equals(StandardNames.ENUM_VALUES) && isEnumSpecialMethod(functionDescriptor);
    }

    @NotNull
    public static PropertyGetterDescriptorImpl createGetter(@NotNull PropertyDescriptor propertyDescriptor, @NotNull Annotations annotations, boolean z2, boolean z3, boolean z4, @NotNull SourceElement sourceElement) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        if (annotations == null) {
            $$$reportNull$$$0(18);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(19);
        }
        return new PropertyGetterDescriptorImpl(propertyDescriptor, annotations, propertyDescriptor.getModality(), propertyDescriptor.getVisibility(), z2, z3, z4, CallableMemberDescriptor.Kind.DECLARATION, null, sourceElement);
    }

    @NotNull
    public static PropertySetterDescriptorImpl createSetter(@NotNull PropertyDescriptor propertyDescriptor, @NotNull Annotations annotations, @NotNull Annotations annotations2, boolean z2, boolean z3, boolean z4, @NotNull DescriptorVisibility descriptorVisibility, @NotNull SourceElement sourceElement) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (annotations == null) {
            $$$reportNull$$$0(8);
        }
        if (annotations2 == null) {
            $$$reportNull$$$0(9);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(10);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(11);
        }
        PropertySetterDescriptorImpl propertySetterDescriptorImpl = new PropertySetterDescriptorImpl(propertyDescriptor, annotations, propertyDescriptor.getModality(), descriptorVisibility, z2, z3, z4, CallableMemberDescriptor.Kind.DECLARATION, null, sourceElement);
        propertySetterDescriptorImpl.initialize(PropertySetterDescriptorImpl.createSetterParameter(propertySetterDescriptorImpl, propertyDescriptor.getType(), annotations2));
        return propertySetterDescriptorImpl;
    }
}
