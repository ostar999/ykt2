package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ExpandedTypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\ndescriptorBasedTypeSignatureMapping.kt\nKotlin\n*S Kotlin\n*F\n+ 1 descriptorBasedTypeSignatureMapping.kt\norg/jetbrains/kotlin/load/kotlin/DescriptorBasedTypeSignatureMappingKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,198:1\n1#2:199\n*E\n"})
/* loaded from: classes8.dex */
public final class DescriptorBasedTypeSignatureMappingKt {
    @NotNull
    public static final String computeInternalName(@NotNull ClassDescriptor klass, @NotNull TypeMappingConfiguration<?> typeMappingConfiguration) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        String predefinedFullInternalNameForClass = typeMappingConfiguration.getPredefinedFullInternalNameForClass(klass);
        if (predefinedFullInternalNameForClass != null) {
            return predefinedFullInternalNameForClass;
        }
        DeclarationDescriptor containingDeclaration = klass.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "klass.containingDeclaration");
        String identifier = SpecialNames.safeIdentifier(klass.getName()).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "safeIdentifier(klass.name).identifier");
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            FqName fqName = ((PackageFragmentDescriptor) containingDeclaration).getFqName();
            if (fqName.isRoot()) {
                return identifier;
            }
            StringBuilder sb = new StringBuilder();
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "fqName.asString()");
            sb.append(StringsKt__StringsJVMKt.replace$default(strAsString, '.', '/', false, 4, (Object) null));
            sb.append('/');
            sb.append(identifier);
            return sb.toString();
        }
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null) {
            throw new IllegalArgumentException("Unexpected container: " + containingDeclaration + " for " + klass);
        }
        String predefinedInternalNameForClass = typeMappingConfiguration.getPredefinedInternalNameForClass(classDescriptor);
        if (predefinedInternalNameForClass == null) {
            predefinedInternalNameForClass = computeInternalName(classDescriptor, typeMappingConfiguration);
        }
        return predefinedInternalNameForClass + Typography.dollar + identifier;
    }

    public static /* synthetic */ String computeInternalName$default(ClassDescriptor classDescriptor, TypeMappingConfiguration typeMappingConfiguration, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            typeMappingConfiguration = TypeMappingConfigurationImpl.INSTANCE;
        }
        return computeInternalName(classDescriptor, typeMappingConfiguration);
    }

    public static final boolean hasVoidReturnType(@NotNull CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof ConstructorDescriptor) {
            return true;
        }
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        if (KotlinBuiltIns.isUnit(returnType)) {
            KotlinType returnType2 = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType2);
            if (!TypeUtils.isNullableType(returnType2) && !(descriptor instanceof PropertyGetterDescriptor)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v10, types: [T, java.lang.Object] */
    @NotNull
    public static final <T> T mapType(@NotNull KotlinType kotlinType, @NotNull JvmTypeFactory<T> factory, @NotNull TypeMappingMode mode, @NotNull TypeMappingConfiguration<? extends T> typeMappingConfiguration, @Nullable JvmDescriptorTypeWriter<T> jvmDescriptorTypeWriter, @NotNull Function3<? super KotlinType, ? super T, ? super TypeMappingMode, Unit> writeGenericType) {
        T t2;
        KotlinType kotlinType2;
        Object objMapType;
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        Intrinsics.checkNotNullParameter(writeGenericType, "writeGenericType");
        KotlinType kotlinTypePreprocessType = typeMappingConfiguration.preprocessType(kotlinType);
        if (kotlinTypePreprocessType != null) {
            return (T) mapType(kotlinTypePreprocessType, factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return (T) mapType(SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(kotlinType), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        SimpleClassicTypeSystemContext simpleClassicTypeSystemContext = SimpleClassicTypeSystemContext.INSTANCE;
        Object objMapBuiltInType = TypeSignatureMappingKt.mapBuiltInType(simpleClassicTypeSystemContext, kotlinType, factory, mode);
        if (objMapBuiltInType != null) {
            ?? r9 = (Object) TypeSignatureMappingKt.boxTypeIfNeeded(factory, objMapBuiltInType, mode.getNeedPrimitiveBoxing());
            writeGenericType.invoke(kotlinType, r9, mode);
            return r9;
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor) constructor;
            KotlinType alternativeType = intersectionTypeConstructor.getAlternativeType();
            if (alternativeType == null) {
                alternativeType = typeMappingConfiguration.commonSupertype(intersectionTypeConstructor.mo2087getSupertypes());
            }
            return (T) mapType(TypeUtilsKt.replaceArgumentsWithStarProjections(alternativeType), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = constructor.mo2086getDeclarationDescriptor();
        if (classifierDescriptorMo2086getDeclarationDescriptor == null) {
            throw new UnsupportedOperationException("no descriptor for type constructor of " + kotlinType);
        }
        if (ErrorUtils.isError(classifierDescriptorMo2086getDeclarationDescriptor)) {
            T t3 = (T) factory.createObjectType("error/NonExistentClass");
            typeMappingConfiguration.processErrorType(kotlinType, (ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor);
            if (jvmDescriptorTypeWriter != 0) {
                jvmDescriptorTypeWriter.writeClass(t3);
            }
            return t3;
        }
        boolean z2 = classifierDescriptorMo2086getDeclarationDescriptor instanceof ClassDescriptor;
        if (z2 && KotlinBuiltIns.isArray(kotlinType)) {
            if (kotlinType.getArguments().size() != 1) {
                throw new UnsupportedOperationException("arrays must have one type argument");
            }
            TypeProjection typeProjection = kotlinType.getArguments().get(0);
            KotlinType type = typeProjection.getType();
            Intrinsics.checkNotNullExpressionValue(type, "memberProjection.type");
            if (typeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                objMapType = factory.createObjectType("java/lang/Object");
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayType();
                    jvmDescriptorTypeWriter.writeClass(objMapType);
                    jvmDescriptorTypeWriter.writeArrayEnd();
                }
            } else {
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayType();
                }
                Variance projectionKind = typeProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind, "memberProjection.projectionKind");
                objMapType = mapType(type, factory, mode.toGenericArgumentMode(projectionKind, true), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayEnd();
                }
            }
            return (T) factory.createFromString('[' + factory.toString(objMapType));
        }
        if (!z2) {
            if (!(classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
                if ((classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeAliasDescriptor) && mode.getMapTypeAliases()) {
                    return (T) mapType(((TypeAliasDescriptor) classifierDescriptorMo2086getDeclarationDescriptor).getExpandedType(), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
                }
                throw new UnsupportedOperationException("Unknown type " + kotlinType);
            }
            KotlinType representativeUpperBound = TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) classifierDescriptorMo2086getDeclarationDescriptor);
            if (kotlinType.isMarkedNullable()) {
                representativeUpperBound = TypeUtilsKt.makeNullable(representativeUpperBound);
            }
            T t4 = (T) mapType(representativeUpperBound, factory, mode, typeMappingConfiguration, null, FunctionsKt.getDO_NOTHING_3());
            if (jvmDescriptorTypeWriter != 0) {
                Name name = classifierDescriptorMo2086getDeclarationDescriptor.getName();
                Intrinsics.checkNotNullExpressionValue(name, "descriptor.getName()");
                jvmDescriptorTypeWriter.writeTypeVariable(name, t4);
            }
            return t4;
        }
        if (InlineClassesUtilsKt.isInlineClass(classifierDescriptorMo2086getDeclarationDescriptor) && !mode.getNeedInlineClassWrapping() && (kotlinType2 = (KotlinType) ExpandedTypeUtilsKt.computeExpandedTypeForInlineClass(simpleClassicTypeSystemContext, kotlinType)) != null) {
            return (T) mapType(kotlinType2, factory, mode.wrapInlineClassesMode(), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        if (mode.isForAnnotationParameter() && KotlinBuiltIns.isKClass((ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor)) {
            t2 = (Object) factory.getJavaLangClassType();
        } else {
            ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor;
            ClassDescriptor original = classDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "descriptor.original");
            T predefinedTypeForClass = typeMappingConfiguration.getPredefinedTypeForClass(original);
            if (predefinedTypeForClass == null) {
                if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY) {
                    DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
                    Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    classDescriptor = (ClassDescriptor) containingDeclaration;
                }
                ClassDescriptor original2 = classDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original2, "enumClassIfEnumEntry.original");
                t2 = (Object) factory.createObjectType(computeInternalName(original2, typeMappingConfiguration));
            } else {
                t2 = (Object) predefinedTypeForClass;
            }
        }
        writeGenericType.invoke(kotlinType, t2, mode);
        return t2;
    }

    public static /* synthetic */ Object mapType$default(KotlinType kotlinType, JvmTypeFactory jvmTypeFactory, TypeMappingMode typeMappingMode, TypeMappingConfiguration typeMappingConfiguration, JvmDescriptorTypeWriter jvmDescriptorTypeWriter, Function3 function3, int i2, Object obj) {
        if ((i2 & 32) != 0) {
            function3 = FunctionsKt.getDO_NOTHING_3();
        }
        return mapType(kotlinType, jvmTypeFactory, typeMappingMode, typeMappingConfiguration, jvmDescriptorTypeWriter, function3);
    }
}
