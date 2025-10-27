package kotlin.reflect.jvm.internal.impl.types;

import java.util.HashMap;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class DescriptorSubstitutor {
    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
        String str = i2 != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i2 != 4 ? 3 : 2];
        switch (i2) {
            case 1:
            case 6:
                objArr[0] = "originalSubstitution";
                break;
            case 2:
            case 7:
                objArr[0] = "newContainingDeclaration";
                break;
            case 3:
            case 8:
                objArr[0] = "result";
                break;
            case 4:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/types/DescriptorSubstitutor";
                break;
            case 5:
            default:
                objArr[0] = "typeParameters";
                break;
        }
        if (i2 != 4) {
            objArr[1] = "kotlin/reflect/jvm/internal/impl/types/DescriptorSubstitutor";
        } else {
            objArr[1] = "substituteTypeParameters";
        }
        if (i2 != 4) {
            objArr[2] = "substituteTypeParameters";
        }
        String str2 = String.format(str, objArr);
        if (i2 == 4) {
            throw new IllegalStateException(str2);
        }
    }

    @NotNull
    public static TypeSubstitutor substituteTypeParameters(@NotNull List<TypeParameterDescriptor> list, @NotNull TypeSubstitution typeSubstitution, @NotNull DeclarationDescriptor declarationDescriptor, @NotNull List<TypeParameterDescriptor> list2) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        if (typeSubstitution == null) {
            $$$reportNull$$$0(1);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (list2 == null) {
            $$$reportNull$$$0(3);
        }
        TypeSubstitutor typeSubstitutorSubstituteTypeParameters = substituteTypeParameters(list, typeSubstitution, declarationDescriptor, list2, null);
        if (typeSubstitutorSubstituteTypeParameters != null) {
            return typeSubstitutorSubstituteTypeParameters;
        }
        throw new AssertionError("Substitution failed");
    }

    @Nullable
    public static TypeSubstitutor substituteTypeParameters(@NotNull List<TypeParameterDescriptor> list, @NotNull TypeSubstitution typeSubstitution, @NotNull DeclarationDescriptor declarationDescriptor, @NotNull List<TypeParameterDescriptor> list2, @Nullable boolean[] zArr) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (typeSubstitution == null) {
            $$$reportNull$$$0(6);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (list2 == null) {
            $$$reportNull$$$0(8);
        }
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        int i2 = 0;
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            TypeParameterDescriptorImpl typeParameterDescriptorImplCreateForFurtherModification = TypeParameterDescriptorImpl.createForFurtherModification(declarationDescriptor, typeParameterDescriptor.getAnnotations(), typeParameterDescriptor.isReified(), typeParameterDescriptor.getVariance(), typeParameterDescriptor.getName(), i2, SourceElement.NO_SOURCE, typeParameterDescriptor.getStorageManager());
            map.put(typeParameterDescriptor.getTypeConstructor(), new TypeProjectionImpl(typeParameterDescriptorImplCreateForFurtherModification.getDefaultType()));
            map2.put(typeParameterDescriptor, typeParameterDescriptorImplCreateForFurtherModification);
            list2.add(typeParameterDescriptorImplCreateForFurtherModification);
            i2++;
        }
        TypeConstructorSubstitution typeConstructorSubstitutionCreateByConstructorsMap = TypeConstructorSubstitution.createByConstructorsMap(map);
        TypeSubstitutor typeSubstitutorCreateChainedSubstitutor = TypeSubstitutor.createChainedSubstitutor(typeSubstitution, typeConstructorSubstitutionCreateByConstructorsMap);
        TypeSubstitutor typeSubstitutorCreateChainedSubstitutor2 = TypeSubstitutor.createChainedSubstitutor(typeSubstitution.replaceWithNonApproximating(), typeConstructorSubstitutionCreateByConstructorsMap);
        for (TypeParameterDescriptor typeParameterDescriptor2 : list) {
            TypeParameterDescriptorImpl typeParameterDescriptorImpl = (TypeParameterDescriptorImpl) map2.get(typeParameterDescriptor2);
            for (KotlinType kotlinType : typeParameterDescriptor2.getUpperBounds()) {
                ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = kotlinType.getConstructor().mo2086getDeclarationDescriptor();
                KotlinType kotlinTypeSubstitute = (((classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptor) && TypeUtilsKt.hasTypeParameterRecursiveBounds((TypeParameterDescriptor) classifierDescriptorMo2086getDeclarationDescriptor)) ? typeSubstitutorCreateChainedSubstitutor : typeSubstitutorCreateChainedSubstitutor2).substitute(kotlinType, Variance.OUT_VARIANCE);
                if (kotlinTypeSubstitute == null) {
                    return null;
                }
                if (kotlinTypeSubstitute != kotlinType && zArr != null) {
                    zArr[0] = true;
                }
                typeParameterDescriptorImpl.addUpperBound(kotlinTypeSubstitute);
            }
            typeParameterDescriptorImpl.setInitialized();
        }
        return typeSubstitutorCreateChainedSubstitutor;
    }
}
