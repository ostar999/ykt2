package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.InnerClassesScopeWrapper;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.SubstitutingScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public abstract class AbstractClassDescriptor extends ModuleAwareClassDescriptor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final NotNullLazyValue<SimpleType> defaultType;
    private final Name name;
    private final NotNullLazyValue<ReceiverParameterDescriptor> thisAsReceiverParameter;
    private final NotNullLazyValue<MemberScope> unsubstitutedInnerClassesScope;

    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
        String str = (i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6 || i2 == 9 || i2 == 12 || i2 == 14 || i2 == 16 || i2 == 17 || i2 == 19 || i2 == 20) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6 || i2 == 9 || i2 == 12 || i2 == 14 || i2 == 16 || i2 == 17 || i2 == 19 || i2 == 20) ? 2 : 3];
        switch (i2) {
            case 1:
                objArr[0] = "name";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 12:
            case 14:
            case 16:
            case 17:
            case 19:
            case 20:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractClassDescriptor";
                break;
            case 7:
            case 13:
                objArr[0] = "typeArguments";
                break;
            case 8:
            case 11:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 10:
            case 15:
                objArr[0] = "typeSubstitution";
                break;
            case 18:
                objArr[0] = "substitutor";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        if (i2 == 2) {
            objArr[1] = "getName";
        } else if (i2 == 3) {
            objArr[1] = "getOriginal";
        } else if (i2 == 4) {
            objArr[1] = "getUnsubstitutedInnerClassesScope";
        } else if (i2 == 5) {
            objArr[1] = "getThisAsReceiverParameter";
        } else if (i2 == 6) {
            objArr[1] = "getContextReceivers";
        } else if (i2 == 9 || i2 == 12 || i2 == 14 || i2 == 16) {
            objArr[1] = "getMemberScope";
        } else if (i2 == 17) {
            objArr[1] = "getUnsubstitutedMemberScope";
        } else if (i2 == 19) {
            objArr[1] = "substitute";
        } else if (i2 != 20) {
            objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractClassDescriptor";
        } else {
            objArr[1] = "getDefaultType";
        }
        switch (i2) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 12:
            case 14:
            case 16:
            case 17:
            case 19:
            case 20:
                break;
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
                objArr[2] = "getMemberScope";
                break;
            case 18:
                objArr[2] = "substitute";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i2 != 2 && i2 != 3 && i2 != 4 && i2 != 5 && i2 != 6 && i2 != 9 && i2 != 12 && i2 != 14 && i2 != 16 && i2 != 17 && i2 != 19 && i2 != 20) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public AbstractClassDescriptor(@NotNull StorageManager storageManager, @NotNull Name name) {
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        if (name == null) {
            $$$reportNull$$$0(1);
        }
        this.name = name;
        this.defaultType = storageManager.createLazyValue(new Function0<SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor.1
            @Override // kotlin.jvm.functions.Function0
            public SimpleType invoke() {
                AbstractClassDescriptor abstractClassDescriptor = AbstractClassDescriptor.this;
                return TypeUtils.makeUnsubstitutedType(abstractClassDescriptor, abstractClassDescriptor.getUnsubstitutedMemberScope(), new Function1<KotlinTypeRefiner, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor.1.1
                    @Override // kotlin.jvm.functions.Function1
                    public SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                        ClassifierDescriptor classifierDescriptorRefineDescriptor = kotlinTypeRefiner.refineDescriptor(AbstractClassDescriptor.this);
                        return classifierDescriptorRefineDescriptor == null ? AbstractClassDescriptor.this.defaultType.invoke() : classifierDescriptorRefineDescriptor instanceof TypeAliasDescriptor ? KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor) classifierDescriptorRefineDescriptor, TypeUtils.getDefaultTypeProjections(classifierDescriptorRefineDescriptor.getTypeConstructor().getParameters())) : classifierDescriptorRefineDescriptor instanceof ModuleAwareClassDescriptor ? TypeUtils.makeUnsubstitutedType(classifierDescriptorRefineDescriptor.getTypeConstructor().refine(kotlinTypeRefiner), ((ModuleAwareClassDescriptor) classifierDescriptorRefineDescriptor).getUnsubstitutedMemberScope(kotlinTypeRefiner), this) : classifierDescriptorRefineDescriptor.getDefaultType();
                    }
                });
            }
        });
        this.unsubstitutedInnerClassesScope = storageManager.createLazyValue(new Function0<MemberScope>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor.2
            @Override // kotlin.jvm.functions.Function0
            public MemberScope invoke() {
                return new InnerClassesScopeWrapper(AbstractClassDescriptor.this.getUnsubstitutedMemberScope());
            }
        });
        this.thisAsReceiverParameter = storageManager.createLazyValue(new Function0<ReceiverParameterDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor.3
            @Override // kotlin.jvm.functions.Function0
            public ReceiverParameterDescriptor invoke() {
                return new LazyClassReceiverParameterDescriptor(AbstractClassDescriptor.this);
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d3) {
        return declarationDescriptorVisitor.visitClassDescriptor(this, d3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        List<ReceiverParameterDescriptor> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(6);
        }
        return listEmptyList;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor
    @NotNull
    public SimpleType getDefaultType() {
        SimpleType simpleTypeInvoke = this.defaultType.invoke();
        if (simpleTypeInvoke == null) {
            $$$reportNull$$$0(20);
        }
        return simpleTypeInvoke;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (typeSubstitution == null) {
            $$$reportNull$$$0(10);
        }
        if (kotlinTypeRefiner == null) {
            $$$reportNull$$$0(11);
        }
        if (!typeSubstitution.isEmpty()) {
            return new SubstitutingScope(getUnsubstitutedMemberScope(kotlinTypeRefiner), TypeSubstitutor.create(typeSubstitution));
        }
        MemberScope unsubstitutedMemberScope = getUnsubstitutedMemberScope(kotlinTypeRefiner);
        if (unsubstitutedMemberScope == null) {
            $$$reportNull$$$0(12);
        }
        return unsubstitutedMemberScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.Named
    @NotNull
    public Name getName() {
        Name name = this.name;
        if (name == null) {
            $$$reportNull$$$0(2);
        }
        return name;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @NotNull
    public ClassDescriptor getOriginal() {
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        ReceiverParameterDescriptor receiverParameterDescriptorInvoke = this.thisAsReceiverParameter.invoke();
        if (receiverParameterDescriptorInvoke == null) {
            $$$reportNull$$$0(5);
        }
        return receiverParameterDescriptorInvoke;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public MemberScope getUnsubstitutedInnerClassesScope() {
        MemberScope memberScopeInvoke = this.unsubstitutedInnerClassesScope.invoke();
        if (memberScopeInvoke == null) {
            $$$reportNull$$$0(4);
        }
        return memberScopeInvoke;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public MemberScope getUnsubstitutedMemberScope() {
        MemberScope unsubstitutedMemberScope = getUnsubstitutedMemberScope(DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (unsubstitutedMemberScope == null) {
            $$$reportNull$$$0(17);
        }
        return unsubstitutedMemberScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.Substitutable
    @NotNull
    public ClassDescriptor substitute(@NotNull TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(18);
        }
        return typeSubstitutor.isEmpty() ? this : new LazySubstitutingClassDescriptor(this, typeSubstitutor);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution) {
        if (typeSubstitution == null) {
            $$$reportNull$$$0(15);
        }
        MemberScope memberScope = getMemberScope(typeSubstitution, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (memberScope == null) {
            $$$reportNull$$$0(16);
        }
        return memberScope;
    }
}
