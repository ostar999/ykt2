package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JvmName(name = "SpecialBuiltinMembers")
@SourceDebugExtension({"SMAP\nspecialBuiltinMembers.kt\nKotlin\n*S Kotlin\n*F\n+ 1 specialBuiltinMembers.kt\norg/jetbrains/kotlin/load/java/SpecialBuiltinMembers\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,182:1\n1#2:183\n*E\n"})
/* loaded from: classes8.dex */
public final class SpecialBuiltinMembers {
    public static final boolean doesOverrideBuiltinWithDifferentJvmName(@NotNull CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return getOverriddenBuiltinWithDifferentJvmName(callableMemberDescriptor) != null;
    }

    @Nullable
    public static final String getJvmMethodNameIfSpecial(@NotNull CallableMemberDescriptor callableMemberDescriptor) {
        CallableMemberDescriptor propertyIfAccessor;
        Name jvmName;
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "callableMemberDescriptor");
        CallableMemberDescriptor overriddenBuiltinThatAffectsJvmName = getOverriddenBuiltinThatAffectsJvmName(callableMemberDescriptor);
        if (overriddenBuiltinThatAffectsJvmName == null || (propertyIfAccessor = DescriptorUtilsKt.getPropertyIfAccessor(overriddenBuiltinThatAffectsJvmName)) == null) {
            return null;
        }
        if (propertyIfAccessor instanceof PropertyDescriptor) {
            return ClassicBuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName(propertyIfAccessor);
        }
        if (!(propertyIfAccessor instanceof SimpleFunctionDescriptor) || (jvmName = BuiltinMethodsWithDifferentJvmName.INSTANCE.getJvmName((SimpleFunctionDescriptor) propertyIfAccessor)) == null) {
            return null;
        }
        return jvmName.asString();
    }

    private static final CallableMemberDescriptor getOverriddenBuiltinThatAffectsJvmName(CallableMemberDescriptor callableMemberDescriptor) {
        if (KotlinBuiltIns.isBuiltIn(callableMemberDescriptor)) {
            return getOverriddenBuiltinWithDifferentJvmName(callableMemberDescriptor);
        }
        return null;
    }

    @Nullable
    public static final <T extends CallableMemberDescriptor> T getOverriddenBuiltinWithDifferentJvmName(@NotNull T t2) {
        Intrinsics.checkNotNullParameter(t2, "<this>");
        if (!SpecialGenericSignatures.Companion.getORIGINAL_SHORT_NAMES().contains(t2.getName()) && !BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES().contains(DescriptorUtilsKt.getPropertyIfAccessor(t2).getName())) {
            return null;
        }
        if (t2 instanceof PropertyDescriptor ? true : t2 instanceof PropertyAccessorDescriptor) {
            return (T) DescriptorUtilsKt.firstOverridden$default(t2, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName.1
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Boolean invoke(@NotNull CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(ClassicBuiltinSpecialProperties.INSTANCE.hasBuiltinSpecialPropertyFqName(DescriptorUtilsKt.getPropertyIfAccessor(it)));
                }
            }, 1, null);
        }
        if (t2 instanceof SimpleFunctionDescriptor) {
            return (T) DescriptorUtilsKt.firstOverridden$default(t2, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName.2
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Boolean invoke(@NotNull CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(BuiltinMethodsWithDifferentJvmName.INSTANCE.isBuiltinFunctionWithDifferentNameInJvm((SimpleFunctionDescriptor) it));
                }
            }, 1, null);
        }
        return null;
    }

    @Nullable
    public static final <T extends CallableMemberDescriptor> T getOverriddenSpecialBuiltin(@NotNull T t2) {
        Intrinsics.checkNotNullParameter(t2, "<this>");
        T t3 = (T) getOverriddenBuiltinWithDifferentJvmName(t2);
        if (t3 != null) {
            return t3;
        }
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
        Name name = t2.getName();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        if (builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            return (T) DescriptorUtilsKt.firstOverridden$default(t2, false, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers.getOverriddenSpecialBuiltin.2
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Boolean invoke(@NotNull CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(KotlinBuiltIns.isBuiltIn(it) && BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(it) != null);
                }
            }, 1, null);
        }
        return null;
    }

    public static final boolean hasRealKotlinSuperClassWithOverrideOf(@NotNull ClassDescriptor classDescriptor, @NotNull CallableDescriptor specialCallableDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(specialCallableDescriptor, "specialCallableDescriptor");
        DeclarationDescriptor containingDeclaration = specialCallableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        SimpleType defaultType = ((ClassDescriptor) containingDeclaration).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "specialCallableDescripto…ssDescriptor).defaultType");
        ClassDescriptor superClassDescriptor = DescriptorUtils.getSuperClassDescriptor(classDescriptor);
        while (true) {
            if (superClassDescriptor == null) {
                return false;
            }
            if (!(superClassDescriptor instanceof JavaClassDescriptor)) {
                if (TypeCheckingProcedure.findCorrespondingSupertype(superClassDescriptor.getDefaultType(), defaultType) != null) {
                    return !KotlinBuiltIns.isBuiltIn(superClassDescriptor);
                }
            }
            superClassDescriptor = DescriptorUtils.getSuperClassDescriptor(superClassDescriptor);
        }
    }

    public static final boolean isFromJava(@NotNull CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return DescriptorUtilsKt.getPropertyIfAccessor(callableMemberDescriptor).getContainingDeclaration() instanceof JavaClassDescriptor;
    }

    public static final boolean isFromJavaOrBuiltins(@NotNull CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return isFromJava(callableMemberDescriptor) || KotlinBuiltIns.isBuiltIn(callableMemberDescriptor);
    }
}
