package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class ContextKt {
    @NotNull
    public static final LazyJavaResolverContext child(@NotNull LazyJavaResolverContext lazyJavaResolverContext, @NotNull TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        return new LazyJavaResolverContext(lazyJavaResolverContext.getComponents(), typeParameterResolver, lazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    @NotNull
    public static final LazyJavaResolverContext childForClassOrPackage(@NotNull final LazyJavaResolverContext lazyJavaResolverContext, @NotNull final ClassOrPackageFragmentDescriptor containingDeclaration, @Nullable JavaTypeParameterListOwner javaTypeParameterListOwner, int i2) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        return child(lazyJavaResolverContext, containingDeclaration, javaTypeParameterListOwner, i2, LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.childForClassOrPackage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(lazyJavaResolverContext, containingDeclaration.getAnnotations());
            }
        }));
    }

    public static /* synthetic */ LazyJavaResolverContext childForClassOrPackage$default(LazyJavaResolverContext lazyJavaResolverContext, ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            javaTypeParameterListOwner = null;
        }
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return childForClassOrPackage(lazyJavaResolverContext, classOrPackageFragmentDescriptor, javaTypeParameterListOwner, i2);
    }

    @NotNull
    public static final LazyJavaResolverContext childForMethod(@NotNull LazyJavaResolverContext lazyJavaResolverContext, @NotNull DeclarationDescriptor containingDeclaration, @NotNull JavaTypeParameterListOwner typeParameterOwner, int i2) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        return child(lazyJavaResolverContext, containingDeclaration, typeParameterOwner, i2, lazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    public static /* synthetic */ LazyJavaResolverContext childForMethod$default(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return childForMethod(lazyJavaResolverContext, declarationDescriptor, javaTypeParameterListOwner, i2);
    }

    @Nullable
    public static final JavaTypeQualifiersByElementType computeNewDefaultTypeQualifiers(@NotNull LazyJavaResolverContext lazyJavaResolverContext, @NotNull Annotations additionalAnnotations) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        return lazyJavaResolverContext.getComponents().getAnnotationTypeQualifierResolver().extractAndMergeDefaultQualifiers(lazyJavaResolverContext.getDefaultTypeQualifiers(), additionalAnnotations);
    }

    @NotNull
    public static final LazyJavaResolverContext copyWithNewDefaultTypeQualifiers(@NotNull final LazyJavaResolverContext lazyJavaResolverContext, @NotNull final Annotations additionalAnnotations) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        return additionalAnnotations.isEmpty() ? lazyJavaResolverContext : new LazyJavaResolverContext(lazyJavaResolverContext.getComponents(), lazyJavaResolverContext.getTypeParameterResolver(), LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.copyWithNewDefaultTypeQualifiers.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(lazyJavaResolverContext, additionalAnnotations);
            }
        }));
    }

    @NotNull
    public static final LazyJavaResolverContext replaceComponents(@NotNull LazyJavaResolverContext lazyJavaResolverContext, @NotNull JavaResolverComponents components) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(components, "components");
        return new LazyJavaResolverContext(components, lazyJavaResolverContext.getTypeParameterResolver(), lazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    private static final LazyJavaResolverContext child(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i2, Lazy<JavaTypeQualifiersByElementType> lazy) {
        return new LazyJavaResolverContext(lazyJavaResolverContext.getComponents(), javaTypeParameterListOwner != null ? new LazyJavaTypeParameterResolver(lazyJavaResolverContext, declarationDescriptor, javaTypeParameterListOwner, i2) : lazyJavaResolverContext.getTypeParameterResolver(), lazy);
    }
}
