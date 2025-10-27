package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class DescriptorEquivalenceForOverrides {

    @NotNull
    public static final DescriptorEquivalenceForOverrides INSTANCE = new DescriptorEquivalenceForOverrides();

    private DescriptorEquivalenceForOverrides() {
    }

    public static /* synthetic */ boolean areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2, boolean z2, boolean z3, boolean z4, KotlinTypeRefiner kotlinTypeRefiner, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z3 = true;
        }
        boolean z5 = z3;
        if ((i2 & 16) != 0) {
            z4 = false;
        }
        return descriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent(callableDescriptor, callableDescriptor2, z2, z5, z4, kotlinTypeRefiner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean areCallableDescriptorsEquivalent$lambda$0(boolean z2, final CallableDescriptor a3, final CallableDescriptor b3, TypeConstructor c12, TypeConstructor c22) {
        Intrinsics.checkNotNullParameter(a3, "$a");
        Intrinsics.checkNotNullParameter(b3, "$b");
        Intrinsics.checkNotNullParameter(c12, "c1");
        Intrinsics.checkNotNullParameter(c22, "c2");
        if (Intrinsics.areEqual(c12, c22)) {
            return true;
        }
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = c12.mo2086getDeclarationDescriptor();
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor2 = c22.mo2086getDeclarationDescriptor();
        if ((classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptor) && (classifierDescriptorMo2086getDeclarationDescriptor2 instanceof TypeParameterDescriptor)) {
            return INSTANCE.areTypeParametersEquivalent((TypeParameterDescriptor) classifierDescriptorMo2086getDeclarationDescriptor, (TypeParameterDescriptor) classifierDescriptorMo2086getDeclarationDescriptor2, z2, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                @NotNull
                public final Boolean invoke(@Nullable DeclarationDescriptor declarationDescriptor, @Nullable DeclarationDescriptor declarationDescriptor2) {
                    return Boolean.valueOf(Intrinsics.areEqual(declarationDescriptor, a3) && Intrinsics.areEqual(declarationDescriptor2, b3));
                }
            });
        }
        return false;
    }

    private final boolean areClassesEquivalent(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        return Intrinsics.areEqual(classDescriptor.getTypeConstructor(), classDescriptor2.getTypeConstructor());
    }

    public static /* synthetic */ boolean areEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z3 = true;
        }
        return descriptorEquivalenceForOverrides.areEquivalent(declarationDescriptor, declarationDescriptor2, z2, z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean areTypeParametersEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, boolean z2, Function2 function2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            function2 = new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides.areTypeParametersEquivalent.1
                @Override // kotlin.jvm.functions.Function2
                @NotNull
                public final Boolean invoke(@Nullable DeclarationDescriptor declarationDescriptor, @Nullable DeclarationDescriptor declarationDescriptor2) {
                    return Boolean.FALSE;
                }
            };
        }
        return descriptorEquivalenceForOverrides.areTypeParametersEquivalent(typeParameterDescriptor, typeParameterDescriptor2, z2, function2);
    }

    private final boolean ownersEquivalent(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> function2, boolean z2) {
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
        DeclarationDescriptor containingDeclaration2 = declarationDescriptor2.getContainingDeclaration();
        return ((containingDeclaration instanceof CallableMemberDescriptor) || (containingDeclaration2 instanceof CallableMemberDescriptor)) ? function2.invoke(containingDeclaration, containingDeclaration2).booleanValue() : areEquivalent$default(this, containingDeclaration, containingDeclaration2, z2, false, 8, null);
    }

    private final SourceElement singleSource(CallableDescriptor callableDescriptor) {
        while (callableDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) callableDescriptor;
            if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                break;
            }
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "overriddenDescriptors");
            callableDescriptor = (CallableMemberDescriptor) CollectionsKt___CollectionsKt.singleOrNull(overriddenDescriptors);
            if (callableDescriptor == null) {
                return null;
            }
        }
        return callableDescriptor.getSource();
    }

    public final boolean areCallableDescriptorsEquivalent(@NotNull final CallableDescriptor a3, @NotNull final CallableDescriptor b3, final boolean z2, boolean z3, boolean z4, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(a3, "a");
        Intrinsics.checkNotNullParameter(b3, "b");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        if (Intrinsics.areEqual(a3, b3)) {
            return true;
        }
        if (!Intrinsics.areEqual(a3.getName(), b3.getName())) {
            return false;
        }
        if (z3 && (a3 instanceof MemberDescriptor) && (b3 instanceof MemberDescriptor) && ((MemberDescriptor) a3).isExpect() != ((MemberDescriptor) b3).isExpect()) {
            return false;
        }
        if ((Intrinsics.areEqual(a3.getContainingDeclaration(), b3.getContainingDeclaration()) && (!z2 || !Intrinsics.areEqual(singleSource(a3), singleSource(b3)))) || DescriptorUtils.isLocal(a3) || DescriptorUtils.isLocal(b3) || !ownersEquivalent(a3, b3, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent.1
            @Override // kotlin.jvm.functions.Function2
            @NotNull
            public final Boolean invoke(@Nullable DeclarationDescriptor declarationDescriptor, @Nullable DeclarationDescriptor declarationDescriptor2) {
                return Boolean.FALSE;
            }
        }, z2)) {
            return false;
        }
        OverridingUtil overridingUtilCreate = OverridingUtil.create(kotlinTypeRefiner, new KotlinTypeChecker.TypeConstructorEquality(z2, a3, b3) { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$$Lambda$0
            private final boolean arg$0;
            private final CallableDescriptor arg$1;
            private final CallableDescriptor arg$2;

            {
                this.arg$0 = z2;
                this.arg$1 = a3;
                this.arg$2 = b3;
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.TypeConstructorEquality
            public boolean equals(TypeConstructor typeConstructor, TypeConstructor typeConstructor2) {
                return DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent$lambda$0(this.arg$0, this.arg$1, this.arg$2, typeConstructor, typeConstructor2);
            }
        });
        Intrinsics.checkNotNullExpressionValue(overridingUtilCreate, "create(kotlinTypeRefiner…= a && y == b }\n        }");
        OverridingUtil.OverrideCompatibilityInfo.Result result = overridingUtilCreate.isOverridableBy(a3, b3, null, !z4).getResult();
        OverridingUtil.OverrideCompatibilityInfo.Result result2 = OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
        return result == result2 && overridingUtilCreate.isOverridableBy(b3, a3, null, z4 ^ true).getResult() == result2;
    }

    public final boolean areEquivalent(@Nullable DeclarationDescriptor declarationDescriptor, @Nullable DeclarationDescriptor declarationDescriptor2, boolean z2, boolean z3) {
        return ((declarationDescriptor instanceof ClassDescriptor) && (declarationDescriptor2 instanceof ClassDescriptor)) ? areClassesEquivalent((ClassDescriptor) declarationDescriptor, (ClassDescriptor) declarationDescriptor2) : ((declarationDescriptor instanceof TypeParameterDescriptor) && (declarationDescriptor2 instanceof TypeParameterDescriptor)) ? areTypeParametersEquivalent$default(this, (TypeParameterDescriptor) declarationDescriptor, (TypeParameterDescriptor) declarationDescriptor2, z2, null, 8, null) : ((declarationDescriptor instanceof CallableDescriptor) && (declarationDescriptor2 instanceof CallableDescriptor)) ? areCallableDescriptorsEquivalent$default(this, (CallableDescriptor) declarationDescriptor, (CallableDescriptor) declarationDescriptor2, z2, z3, false, KotlinTypeRefiner.Default.INSTANCE, 16, null) : ((declarationDescriptor instanceof PackageFragmentDescriptor) && (declarationDescriptor2 instanceof PackageFragmentDescriptor)) ? Intrinsics.areEqual(((PackageFragmentDescriptor) declarationDescriptor).getFqName(), ((PackageFragmentDescriptor) declarationDescriptor2).getFqName()) : Intrinsics.areEqual(declarationDescriptor, declarationDescriptor2);
    }

    @JvmOverloads
    public final boolean areTypeParametersEquivalent(@NotNull TypeParameterDescriptor a3, @NotNull TypeParameterDescriptor b3, boolean z2) {
        Intrinsics.checkNotNullParameter(a3, "a");
        Intrinsics.checkNotNullParameter(b3, "b");
        return areTypeParametersEquivalent$default(this, a3, b3, z2, null, 8, null);
    }

    @JvmOverloads
    public final boolean areTypeParametersEquivalent(@NotNull TypeParameterDescriptor a3, @NotNull TypeParameterDescriptor b3, boolean z2, @NotNull Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> equivalentCallables) {
        Intrinsics.checkNotNullParameter(a3, "a");
        Intrinsics.checkNotNullParameter(b3, "b");
        Intrinsics.checkNotNullParameter(equivalentCallables, "equivalentCallables");
        if (Intrinsics.areEqual(a3, b3)) {
            return true;
        }
        return !Intrinsics.areEqual(a3.getContainingDeclaration(), b3.getContainingDeclaration()) && ownersEquivalent(a3, b3, equivalentCallables, z2) && a3.getIndex() == b3.getIndex();
    }
}
