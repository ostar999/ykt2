package kotlin.reflect.jvm.internal.impl.types.error;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class ErrorClassDescriptor extends ClassDescriptorImpl {
    /* JADX WARN: Illegal instructions before constructor call */
    public ErrorClassDescriptor(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        ErrorUtils errorUtils = ErrorUtils.INSTANCE;
        ModuleDescriptor errorModule = errorUtils.getErrorModule();
        Modality modality = Modality.OPEN;
        ClassKind classKind = ClassKind.CLASS;
        List listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        super(errorModule, name, modality, classKind, listEmptyList, sourceElement, false, LockBasedStorageManager.NO_LOCKS);
        ClassConstructorDescriptorImpl classConstructorDescriptorImplCreate = ClassConstructorDescriptorImpl.create(this, Annotations.Companion.getEMPTY(), true, sourceElement);
        classConstructorDescriptorImplCreate.initialize(CollectionsKt__CollectionsKt.emptyList(), DescriptorVisibilities.INTERNAL);
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorImplCreate, "create(this, Annotations…          )\n            }");
        ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ERROR_CLASS;
        String string = classConstructorDescriptorImplCreate.getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "errorConstructor.name.toString()");
        MemberScope memberScopeCreateErrorScope = ErrorUtils.createErrorScope(errorScopeKind, string, "");
        ErrorTypeKind errorTypeKind = ErrorTypeKind.ERROR_CLASS;
        classConstructorDescriptorImplCreate.setReturnType(new ErrorType(errorUtils.createErrorTypeConstructor(errorTypeKind, new String[0]), memberScopeCreateErrorScope, errorTypeKind, null, false, new String[0], 24, null));
        initialize(memberScopeCreateErrorScope, SetsKt__SetsJVMKt.setOf(classConstructorDescriptorImplCreate), classConstructorDescriptorImplCreate);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ERROR_CLASS;
        String string = getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "name.toString()");
        return ErrorUtils.createErrorScope(errorScopeKind, string, typeSubstitution.toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.Substitutable
    @NotNull
    public ClassDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl
    @NotNull
    public String toString() {
        String strAsString = getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "name.asString()");
        return strAsString;
    }
}
