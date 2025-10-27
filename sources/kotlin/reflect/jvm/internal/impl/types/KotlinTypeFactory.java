package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptorKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpansionReportStrategy;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorScopeKind;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nKotlinTypeFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KotlinTypeFactory.kt\norg/jetbrains/kotlin/types/KotlinTypeFactory\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,301:1\n1#2:302\n*E\n"})
/* loaded from: classes8.dex */
public final class KotlinTypeFactory {

    @NotNull
    public static final KotlinTypeFactory INSTANCE = new KotlinTypeFactory();

    @NotNull
    private static final Function1<KotlinTypeRefiner, SimpleType> EMPTY_REFINED_TYPE_FACTORY = new Function1() { // from class: kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory$EMPTY_REFINED_TYPE_FACTORY$1
        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public final Void invoke(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "<anonymous parameter 0>");
            return null;
        }
    };

    public static final class ExpandedTypeOrRefinedConstructor {

        @Nullable
        private final SimpleType expandedType;

        @Nullable
        private final TypeConstructor refinedConstructor;

        public ExpandedTypeOrRefinedConstructor(@Nullable SimpleType simpleType, @Nullable TypeConstructor typeConstructor) {
            this.expandedType = simpleType;
            this.refinedConstructor = typeConstructor;
        }

        @Nullable
        public final SimpleType getExpandedType() {
            return this.expandedType;
        }

        @Nullable
        public final TypeConstructor getRefinedConstructor() {
            return this.refinedConstructor;
        }
    }

    private KotlinTypeFactory() {
    }

    @JvmStatic
    @NotNull
    public static final SimpleType computeExpandedType(@NotNull TypeAliasDescriptor typeAliasDescriptor, @NotNull List<? extends TypeProjection> arguments) {
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new TypeAliasExpander(TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false).expand(TypeAliasExpansion.Companion.create(null, typeAliasDescriptor, arguments), TypeAttributes.Companion.getEmpty());
    }

    private final MemberScope computeMemberScope(TypeConstructor typeConstructor, List<? extends TypeProjection> list, KotlinTypeRefiner kotlinTypeRefiner) {
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = typeConstructor.mo2086getDeclarationDescriptor();
        if (classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            return ((TypeParameterDescriptor) classifierDescriptorMo2086getDeclarationDescriptor).getDefaultType().getMemberScope();
        }
        if (classifierDescriptorMo2086getDeclarationDescriptor instanceof ClassDescriptor) {
            if (kotlinTypeRefiner == null) {
                kotlinTypeRefiner = DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtilsKt.getModule(classifierDescriptorMo2086getDeclarationDescriptor));
            }
            return list.isEmpty() ? ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible((ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor, kotlinTypeRefiner) : ModuleAwareClassDescriptorKt.getRefinedMemberScopeIfPossible((ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor, TypeConstructorSubstitution.Companion.create(typeConstructor, list), kotlinTypeRefiner);
        }
        if (classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeAliasDescriptor) {
            ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ABBREVIATION_TYPE;
            String string = ((TypeAliasDescriptor) classifierDescriptorMo2086getDeclarationDescriptor).getName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "descriptor.name.toString()");
            return ErrorUtils.createErrorScope(errorScopeKind, true, string);
        }
        if (typeConstructor instanceof IntersectionTypeConstructor) {
            return ((IntersectionTypeConstructor) typeConstructor).createScopeForKotlinType();
        }
        throw new IllegalStateException("Unsupported classifier: " + classifierDescriptorMo2086getDeclarationDescriptor + " for constructor: " + typeConstructor);
    }

    @JvmStatic
    @NotNull
    public static final UnwrappedType flexibleType(@NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return Intrinsics.areEqual(lowerBound, upperBound) ? lowerBound : new FlexibleTypeImpl(lowerBound, upperBound);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType integerLiteralType(@NotNull TypeAttributes attributes, @NotNull IntegerLiteralTypeConstructor constructor, boolean z2) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return simpleTypeWithNonTrivialMemberScope(attributes, constructor, CollectionsKt__CollectionsKt.emptyList(), z2, ErrorUtils.createErrorScope(ErrorScopeKind.INTEGER_LITERAL_TYPE_SCOPE, true, "unknown integer literal type"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpandedTypeOrRefinedConstructor refineConstructor(TypeConstructor typeConstructor, KotlinTypeRefiner kotlinTypeRefiner, List<? extends TypeProjection> list) {
        ClassifierDescriptor classifierDescriptorRefineDescriptor;
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = typeConstructor.mo2086getDeclarationDescriptor();
        if (classifierDescriptorMo2086getDeclarationDescriptor == null || (classifierDescriptorRefineDescriptor = kotlinTypeRefiner.refineDescriptor(classifierDescriptorMo2086getDeclarationDescriptor)) == null) {
            return null;
        }
        if (classifierDescriptorRefineDescriptor instanceof TypeAliasDescriptor) {
            return new ExpandedTypeOrRefinedConstructor(computeExpandedType((TypeAliasDescriptor) classifierDescriptorRefineDescriptor, list), null);
        }
        TypeConstructor typeConstructorRefine = classifierDescriptorRefineDescriptor.getTypeConstructor().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeConstructorRefine, "descriptor.typeConstruct…refine(kotlinTypeRefiner)");
        return new ExpandedTypeOrRefinedConstructor(null, typeConstructorRefine);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleNotNullType(@NotNull TypeAttributes attributes, @NotNull ClassDescriptor descriptor, @NotNull List<? extends TypeProjection> arguments) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        TypeConstructor typeConstructor = descriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "descriptor.typeConstructor");
        return simpleType$default(attributes, typeConstructor, (List) arguments, false, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final SimpleType simpleType(@NotNull TypeAttributes attributes, @NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments, boolean z2) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return simpleType$default(attributes, constructor, arguments, z2, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final SimpleType simpleType(@NotNull final TypeAttributes attributes, @NotNull final TypeConstructor constructor, @NotNull final List<? extends TypeProjection> arguments, final boolean z2, @Nullable KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (!attributes.isEmpty() || !arguments.isEmpty() || z2 || constructor.mo2086getDeclarationDescriptor() == null) {
            return simpleTypeWithNonTrivialMemberScope(attributes, constructor, arguments, z2, INSTANCE.computeMemberScope(constructor, arguments, kotlinTypeRefiner), new Function1<KotlinTypeRefiner, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory.simpleType.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final SimpleType invoke(@NotNull KotlinTypeRefiner refiner) {
                    Intrinsics.checkNotNullParameter(refiner, "refiner");
                    ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructorRefineConstructor = KotlinTypeFactory.INSTANCE.refineConstructor(constructor, refiner, arguments);
                    if (expandedTypeOrRefinedConstructorRefineConstructor == null) {
                        return null;
                    }
                    SimpleType expandedType = expandedTypeOrRefinedConstructorRefineConstructor.getExpandedType();
                    if (expandedType != null) {
                        return expandedType;
                    }
                    TypeAttributes typeAttributes = attributes;
                    TypeConstructor refinedConstructor = expandedTypeOrRefinedConstructorRefineConstructor.getRefinedConstructor();
                    Intrinsics.checkNotNull(refinedConstructor);
                    return KotlinTypeFactory.simpleType(typeAttributes, refinedConstructor, arguments, z2, refiner);
                }
            });
        }
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = constructor.mo2086getDeclarationDescriptor();
        Intrinsics.checkNotNull(classifierDescriptorMo2086getDeclarationDescriptor);
        SimpleType defaultType = classifierDescriptorMo2086getDeclarationDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "constructor.declarationDescriptor!!.defaultType");
        return defaultType;
    }

    public static /* synthetic */ SimpleType simpleType$default(TypeAttributes typeAttributes, TypeConstructor typeConstructor, List list, boolean z2, KotlinTypeRefiner kotlinTypeRefiner, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            kotlinTypeRefiner = null;
        }
        return simpleType(typeAttributes, typeConstructor, (List<? extends TypeProjection>) list, z2, kotlinTypeRefiner);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(@NotNull final TypeAttributes attributes, @NotNull final TypeConstructor constructor, @NotNull final List<? extends TypeProjection> arguments, final boolean z2, @NotNull final MemberScope memberScope) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments, z2, memberScope, new Function1<KotlinTypeRefiner, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final SimpleType invoke(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructorRefineConstructor = KotlinTypeFactory.INSTANCE.refineConstructor(constructor, kotlinTypeRefiner, arguments);
                if (expandedTypeOrRefinedConstructorRefineConstructor == null) {
                    return null;
                }
                SimpleType expandedType = expandedTypeOrRefinedConstructorRefineConstructor.getExpandedType();
                if (expandedType != null) {
                    return expandedType;
                }
                TypeAttributes typeAttributes = attributes;
                TypeConstructor refinedConstructor = expandedTypeOrRefinedConstructorRefineConstructor.getRefinedConstructor();
                Intrinsics.checkNotNull(refinedConstructor);
                return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(typeAttributes, refinedConstructor, arguments, z2, memberScope);
            }
        });
        return attributes.isEmpty() ? simpleTypeImpl : new SimpleTypeWithAttributes(simpleTypeImpl, attributes);
    }

    public static /* synthetic */ SimpleType simpleType$default(SimpleType simpleType, TypeAttributes typeAttributes, TypeConstructor typeConstructor, List list, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            typeAttributes = simpleType.getAttributes();
        }
        if ((i2 & 4) != 0) {
            typeConstructor = simpleType.getConstructor();
        }
        if ((i2 & 8) != 0) {
            list = simpleType.getArguments();
        }
        if ((i2 & 16) != 0) {
            z2 = simpleType.isMarkedNullable();
        }
        return simpleType(simpleType, typeAttributes, typeConstructor, (List<? extends TypeProjection>) list, z2);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleType(@NotNull SimpleType baseType, @NotNull TypeAttributes annotations, @NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments, boolean z2) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return simpleType$default(annotations, constructor, arguments, z2, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(@NotNull TypeAttributes attributes, @NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments, boolean z2, @NotNull MemberScope memberScope, @NotNull Function1<? super KotlinTypeRefiner, ? extends SimpleType> refinedTypeFactory) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(refinedTypeFactory, "refinedTypeFactory");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments, z2, memberScope, refinedTypeFactory);
        return attributes.isEmpty() ? simpleTypeImpl : new SimpleTypeWithAttributes(simpleTypeImpl, attributes);
    }
}
