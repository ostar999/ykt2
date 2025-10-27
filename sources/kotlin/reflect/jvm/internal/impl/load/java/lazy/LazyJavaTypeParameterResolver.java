package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class LazyJavaTypeParameterResolver implements TypeParameterResolver {

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final LazyJavaResolverContext f27625c;

    @NotNull
    private final DeclarationDescriptor containingDeclaration;

    @NotNull
    private final MemoizedFunctionToNullable<JavaTypeParameter, LazyJavaTypeParameterDescriptor> resolve;

    @NotNull
    private final Map<JavaTypeParameter, Integer> typeParameters;
    private final int typeParametersIndexOffset;

    public LazyJavaTypeParameterResolver(@NotNull LazyJavaResolverContext c3, @NotNull DeclarationDescriptor containingDeclaration, @NotNull JavaTypeParameterListOwner typeParameterOwner, int i2) {
        Intrinsics.checkNotNullParameter(c3, "c");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        this.f27625c = c3;
        this.containingDeclaration = containingDeclaration;
        this.typeParametersIndexOffset = i2;
        this.typeParameters = CollectionsKt.mapToIndex(typeParameterOwner.getTypeParameters());
        this.resolve = c3.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<JavaTypeParameter, LazyJavaTypeParameterDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaTypeParameterResolver$resolve$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final LazyJavaTypeParameterDescriptor invoke(@NotNull JavaTypeParameter typeParameter) {
                Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
                Integer num = (Integer) this.this$0.typeParameters.get(typeParameter);
                if (num == null) {
                    return null;
                }
                LazyJavaTypeParameterResolver lazyJavaTypeParameterResolver = this.this$0;
                return new LazyJavaTypeParameterDescriptor(ContextKt.copyWithNewDefaultTypeQualifiers(ContextKt.child(lazyJavaTypeParameterResolver.f27625c, lazyJavaTypeParameterResolver), lazyJavaTypeParameterResolver.containingDeclaration.getAnnotations()), typeParameter, lazyJavaTypeParameterResolver.typeParametersIndexOffset + num.intValue(), lazyJavaTypeParameterResolver.containingDeclaration);
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver
    @Nullable
    public TypeParameterDescriptor resolveTypeParameter(@NotNull JavaTypeParameter javaTypeParameter) {
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptorInvoke = this.resolve.invoke(javaTypeParameter);
        return lazyJavaTypeParameterDescriptorInvoke != null ? lazyJavaTypeParameterDescriptorInvoke : this.f27625c.getTypeParameterResolver().resolveTypeParameter(javaTypeParameter);
    }
}
