package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import cn.hutool.core.text.CharPool;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionForAbsentTypeParameter;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributeTranslator;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorTypeKind;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nTypeDeserializer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TypeDeserializer.kt\norg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,305:1\n1549#2:306\n1620#2,3:307\n1559#2:310\n1590#2,4:311\n1549#2:316\n1620#2,3:317\n1#3:315\n*S KotlinDebug\n*F\n+ 1 TypeDeserializer.kt\norg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer\n*L\n76#1:306\n76#1:307,3\n105#1:310\n105#1:311,4\n251#1:316\n251#1:317,3\n*E\n"})
/* loaded from: classes8.dex */
public final class TypeDeserializer {

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final DeserializationContext f27636c;

    @NotNull
    private final Function1<Integer, ClassifierDescriptor> classifierDescriptors;

    @NotNull
    private final String containerPresentableName;

    @NotNull
    private final String debugName;

    @Nullable
    private final TypeDeserializer parent;

    @NotNull
    private final Function1<Integer, ClassifierDescriptor> typeAliasDescriptors;

    @NotNull
    private final Map<Integer, TypeParameterDescriptor> typeParameterDescriptors;

    public TypeDeserializer(@NotNull DeserializationContext c3, @Nullable TypeDeserializer typeDeserializer, @NotNull List<ProtoBuf.TypeParameter> typeParameterProtos, @NotNull String debugName, @NotNull String containerPresentableName) {
        Map<Integer, TypeParameterDescriptor> linkedHashMap;
        Intrinsics.checkNotNullParameter(c3, "c");
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(containerPresentableName, "containerPresentableName");
        this.f27636c = c3;
        this.parent = typeDeserializer;
        this.debugName = debugName;
        this.containerPresentableName = containerPresentableName;
        this.classifierDescriptors = c3.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Integer, ClassifierDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$classifierDescriptors$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ClassifierDescriptor invoke(Integer num) {
                return invoke(num.intValue());
            }

            @Nullable
            public final ClassifierDescriptor invoke(int i2) {
                return this.this$0.computeClassifierDescriptor(i2);
            }
        });
        this.typeAliasDescriptors = c3.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Integer, ClassifierDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeAliasDescriptors$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ClassifierDescriptor invoke(Integer num) {
                return invoke(num.intValue());
            }

            @Nullable
            public final ClassifierDescriptor invoke(int i2) {
                return this.this$0.computeTypeAliasDescriptor(i2);
            }
        });
        if (typeParameterProtos.isEmpty()) {
            linkedHashMap = MapsKt__MapsKt.emptyMap();
        } else {
            linkedHashMap = new LinkedHashMap<>();
            int i2 = 0;
            for (ProtoBuf.TypeParameter typeParameter : typeParameterProtos) {
                linkedHashMap.put(Integer.valueOf(typeParameter.getId()), new DeserializedTypeParameterDescriptor(this.f27636c, typeParameter, i2));
                i2++;
            }
        }
        this.typeParameterDescriptors = linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassifierDescriptor computeClassifierDescriptor(int i2) {
        ClassId classId = NameResolverUtilKt.getClassId(this.f27636c.getNameResolver(), i2);
        return classId.isLocal() ? this.f27636c.getComponents().deserializeClass(classId) : FindClassInModuleKt.findClassifierAcrossModuleDependencies(this.f27636c.getComponents().getModuleDescriptor(), classId);
    }

    private final SimpleType computeLocalClassifierReplacementType(int i2) {
        if (NameResolverUtilKt.getClassId(this.f27636c.getNameResolver(), i2).isLocal()) {
            return this.f27636c.getComponents().getLocalClassifierTypeSettings().getReplacementTypeForLocalClassifiers();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassifierDescriptor computeTypeAliasDescriptor(int i2) {
        ClassId classId = NameResolverUtilKt.getClassId(this.f27636c.getNameResolver(), i2);
        if (classId.isLocal()) {
            return null;
        }
        return FindClassInModuleKt.findTypeAliasAcrossModuleDependencies(this.f27636c.getComponents().getModuleDescriptor(), classId);
    }

    private final SimpleType createSimpleSuspendFunctionType(KotlinType kotlinType, KotlinType kotlinType2) {
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(kotlinType);
        Annotations annotations = kotlinType.getAnnotations();
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(kotlinType);
        List listDropLast = CollectionsKt___CollectionsKt.dropLast(FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getType());
        }
        return FunctionTypesKt.createFunctionType(builtIns, annotations, receiverTypeFromFunctionType, contextReceiverTypesFromFunctionType, arrayList, null, kotlinType2, true).makeNullableAsSpecified(kotlinType.isMarkedNullable());
    }

    private final SimpleType createSuspendFunctionType(TypeAttributes typeAttributes, TypeConstructor typeConstructor, List<? extends TypeProjection> list, boolean z2) {
        SimpleType simpleTypeCreateSuspendFunctionTypeForBasicCase;
        int size;
        int size2 = typeConstructor.getParameters().size() - list.size();
        if (size2 != 0) {
            simpleTypeCreateSuspendFunctionTypeForBasicCase = null;
            if (size2 == 1 && (size = list.size() - 1) >= 0) {
                TypeConstructor typeConstructor2 = typeConstructor.getBuiltIns().getSuspendFunction(size).getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor2, "functionTypeConstructor.…on(arity).typeConstructor");
                simpleTypeCreateSuspendFunctionTypeForBasicCase = KotlinTypeFactory.simpleType$default(typeAttributes, typeConstructor2, list, z2, (KotlinTypeRefiner) null, 16, (Object) null);
            }
        } else {
            simpleTypeCreateSuspendFunctionTypeForBasicCase = createSuspendFunctionTypeForBasicCase(typeAttributes, typeConstructor, list, z2);
        }
        return simpleTypeCreateSuspendFunctionTypeForBasicCase == null ? ErrorUtils.INSTANCE.createErrorTypeWithArguments(ErrorTypeKind.INCONSISTENT_SUSPEND_FUNCTION, list, typeConstructor, new String[0]) : simpleTypeCreateSuspendFunctionTypeForBasicCase;
    }

    private final SimpleType createSuspendFunctionTypeForBasicCase(TypeAttributes typeAttributes, TypeConstructor typeConstructor, List<? extends TypeProjection> list, boolean z2) {
        SimpleType simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(typeAttributes, typeConstructor, list, z2, (KotlinTypeRefiner) null, 16, (Object) null);
        if (FunctionTypesKt.isFunctionType(simpleTypeSimpleType$default)) {
            return transformRuntimeFunctionTypeToSuspendFunction(simpleTypeSimpleType$default);
        }
        return null;
    }

    private final TypeParameterDescriptor loadTypeParameter(int i2) {
        TypeParameterDescriptor typeParameterDescriptor = this.typeParameterDescriptors.get(Integer.valueOf(i2));
        if (typeParameterDescriptor != null) {
            return typeParameterDescriptor;
        }
        TypeDeserializer typeDeserializer = this.parent;
        if (typeDeserializer != null) {
            return typeDeserializer.loadTypeParameter(i2);
        }
        return null;
    }

    private static final List<ProtoBuf.Type.Argument> simpleType$collectAllArguments(ProtoBuf.Type type, TypeDeserializer typeDeserializer) {
        List<ProtoBuf.Type.Argument> argumentList = type.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "argumentList");
        List<ProtoBuf.Type.Argument> list = argumentList;
        ProtoBuf.Type typeOuterType = ProtoTypeTableUtilKt.outerType(type, typeDeserializer.f27636c.getTypeTable());
        List<ProtoBuf.Type.Argument> listSimpleType$collectAllArguments = typeOuterType != null ? simpleType$collectAllArguments(typeOuterType, typeDeserializer) : null;
        if (listSimpleType$collectAllArguments == null) {
            listSimpleType$collectAllArguments = CollectionsKt__CollectionsKt.emptyList();
        }
        return CollectionsKt___CollectionsKt.plus((Collection) list, (Iterable) listSimpleType$collectAllArguments);
    }

    public static /* synthetic */ SimpleType simpleType$default(TypeDeserializer typeDeserializer, ProtoBuf.Type type, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        return typeDeserializer.simpleType(type, z2);
    }

    private final TypeAttributes toAttributes(List<? extends TypeAttributeTranslator> list, Annotations annotations, TypeConstructor typeConstructor, DeclarationDescriptor declarationDescriptor) {
        List<? extends TypeAttributeTranslator> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeAttributeTranslator) it.next()).toAttributes(annotations, typeConstructor, declarationDescriptor));
        }
        return TypeAttributes.Companion.create(CollectionsKt__IterablesKt.flatten(arrayList));
    }

    private final SimpleType transformRuntimeFunctionTypeToSuspendFunction(KotlinType kotlinType) {
        KotlinType type;
        TypeProjection typeProjection = (TypeProjection) CollectionsKt___CollectionsKt.lastOrNull((List) FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType));
        if (typeProjection == null || (type = typeProjection.getType()) == null) {
            return null;
        }
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = type.getConstructor().mo2086getDeclarationDescriptor();
        FqName fqNameSafe = classifierDescriptorMo2086getDeclarationDescriptor != null ? DescriptorUtilsKt.getFqNameSafe(classifierDescriptorMo2086getDeclarationDescriptor) : null;
        if (type.getArguments().size() != 1 || (!Intrinsics.areEqual(fqNameSafe, StandardNames.CONTINUATION_INTERFACE_FQ_NAME) && !Intrinsics.areEqual(fqNameSafe, TypeDeserializerKt.EXPERIMENTAL_CONTINUATION_FQ_NAME))) {
            return (SimpleType) kotlinType;
        }
        KotlinType type2 = ((TypeProjection) CollectionsKt___CollectionsKt.single((List) type.getArguments())).getType();
        Intrinsics.checkNotNullExpressionValue(type2, "continuationArgumentType.arguments.single().type");
        DeclarationDescriptor containingDeclaration = this.f27636c.getContainingDeclaration();
        CallableDescriptor callableDescriptor = containingDeclaration instanceof CallableDescriptor ? (CallableDescriptor) containingDeclaration : null;
        return Intrinsics.areEqual(callableDescriptor != null ? DescriptorUtilsKt.fqNameOrNull(callableDescriptor) : null, SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME) ? createSimpleSuspendFunctionType(kotlinType, type2) : createSimpleSuspendFunctionType(kotlinType, type2);
    }

    private final TypeProjection typeArgument(TypeParameterDescriptor typeParameterDescriptor, ProtoBuf.Type.Argument argument) {
        if (argument.getProjection() == ProtoBuf.Type.Argument.Projection.STAR) {
            return typeParameterDescriptor == null ? new StarProjectionForAbsentTypeParameter(this.f27636c.getComponents().getModuleDescriptor().getBuiltIns()) : new StarProjectionImpl(typeParameterDescriptor);
        }
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        ProtoBuf.Type.Argument.Projection projection = argument.getProjection();
        Intrinsics.checkNotNullExpressionValue(projection, "typeArgumentProto.projection");
        Variance variance = protoEnumFlags.variance(projection);
        ProtoBuf.Type type = ProtoTypeTableUtilKt.type(argument, this.f27636c.getTypeTable());
        return type == null ? new TypeProjectionImpl(ErrorUtils.createErrorType(ErrorTypeKind.NO_RECORDED_TYPE, argument.toString())) : new TypeProjectionImpl(variance, type(type));
    }

    private final TypeConstructor typeConstructor(ProtoBuf.Type type) {
        ClassifierDescriptor classifierDescriptorInvoke;
        Object next;
        if (type.hasClassName()) {
            classifierDescriptorInvoke = this.classifierDescriptors.invoke(Integer.valueOf(type.getClassName()));
            if (classifierDescriptorInvoke == null) {
                classifierDescriptorInvoke = typeConstructor$notFoundClass(this, type, type.getClassName());
            }
        } else if (type.hasTypeParameter()) {
            classifierDescriptorInvoke = loadTypeParameter(type.getTypeParameter());
            if (classifierDescriptorInvoke == null) {
                return ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.CANNOT_LOAD_DESERIALIZE_TYPE_PARAMETER, String.valueOf(type.getTypeParameter()), this.containerPresentableName);
            }
        } else if (type.hasTypeParameterName()) {
            String string = this.f27636c.getNameResolver().getString(type.getTypeParameterName());
            Iterator<T> it = getOwnTypeParameters().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (Intrinsics.areEqual(((TypeParameterDescriptor) next).getName().asString(), string)) {
                    break;
                }
            }
            classifierDescriptorInvoke = (TypeParameterDescriptor) next;
            if (classifierDescriptorInvoke == null) {
                return ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.CANNOT_LOAD_DESERIALIZE_TYPE_PARAMETER_BY_NAME, string, this.f27636c.getContainingDeclaration().toString());
            }
        } else {
            if (!type.hasTypeAliasName()) {
                return ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.UNKNOWN_TYPE, new String[0]);
            }
            classifierDescriptorInvoke = this.typeAliasDescriptors.invoke(Integer.valueOf(type.getTypeAliasName()));
            if (classifierDescriptorInvoke == null) {
                classifierDescriptorInvoke = typeConstructor$notFoundClass(this, type, type.getTypeAliasName());
            }
        }
        TypeConstructor typeConstructor = classifierDescriptorInvoke.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "classifier.typeConstructor");
        return typeConstructor;
    }

    private static final ClassDescriptor typeConstructor$notFoundClass(final TypeDeserializer typeDeserializer, ProtoBuf.Type type, int i2) {
        ClassId classId = NameResolverUtilKt.getClassId(typeDeserializer.f27636c.getNameResolver(), i2);
        List<Integer> mutableList = SequencesKt___SequencesKt.toMutableList(SequencesKt___SequencesKt.map(SequencesKt__SequencesKt.generateSequence(type, (Function1<? super ProtoBuf.Type, ? extends ProtoBuf.Type>) ((Function1<? super Object, ? extends Object>) new Function1<ProtoBuf.Type, ProtoBuf.Type>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$notFoundClass$typeParametersCount$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final ProtoBuf.Type invoke(@NotNull ProtoBuf.Type it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ProtoTypeTableUtilKt.outerType(it, this.this$0.f27636c.getTypeTable());
            }
        })), new Function1<ProtoBuf.Type, Integer>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$notFoundClass$typeParametersCount$2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Integer invoke(@NotNull ProtoBuf.Type it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(it.getArgumentCount());
            }
        }));
        int iCount = SequencesKt___SequencesKt.count(SequencesKt__SequencesKt.generateSequence(classId, (Function1<? super ClassId, ? extends ClassId>) ((Function1<? super Object, ? extends Object>) TypeDeserializer$typeConstructor$notFoundClass$classNestingLevel$1.INSTANCE)));
        while (mutableList.size() < iCount) {
            mutableList.add(0);
        }
        return typeDeserializer.f27636c.getComponents().getNotFoundClasses().getClass(classId, mutableList);
    }

    @NotNull
    public final List<TypeParameterDescriptor> getOwnTypeParameters() {
        return CollectionsKt___CollectionsKt.toList(this.typeParameterDescriptors.values());
    }

    @NotNull
    public final SimpleType simpleType(@NotNull final ProtoBuf.Type proto, boolean z2) {
        SimpleType simpleTypeSimpleType$default;
        SimpleType simpleTypeWithAbbreviation;
        Intrinsics.checkNotNullParameter(proto, "proto");
        SimpleType simpleTypeComputeLocalClassifierReplacementType = proto.hasClassName() ? computeLocalClassifierReplacementType(proto.getClassName()) : proto.hasTypeAliasName() ? computeLocalClassifierReplacementType(proto.getTypeAliasName()) : null;
        if (simpleTypeComputeLocalClassifierReplacementType != null) {
            return simpleTypeComputeLocalClassifierReplacementType;
        }
        TypeConstructor typeConstructor = typeConstructor(proto);
        boolean z3 = true;
        if (ErrorUtils.isError(typeConstructor.mo2086getDeclarationDescriptor())) {
            return ErrorUtils.INSTANCE.createErrorType(ErrorTypeKind.TYPE_FOR_ERROR_TYPE_CONSTRUCTOR, typeConstructor, typeConstructor.toString());
        }
        DeserializedAnnotations deserializedAnnotations = new DeserializedAnnotations(this.f27636c.getStorageManager(), new Function0<List<? extends AnnotationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$simpleType$annotations$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends AnnotationDescriptor> invoke() {
                return this.this$0.f27636c.getComponents().getAnnotationAndConstantLoader().loadTypeAnnotations(proto, this.this$0.f27636c.getNameResolver());
            }
        });
        TypeAttributes attributes = toAttributes(this.f27636c.getComponents().getTypeAttributeTranslators(), deserializedAnnotations, typeConstructor, this.f27636c.getContainingDeclaration());
        List<ProtoBuf.Type.Argument> listSimpleType$collectAllArguments = simpleType$collectAllArguments(proto, this);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSimpleType$collectAllArguments, 10));
        int i2 = 0;
        for (Object obj : listSimpleType$collectAllArguments) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "constructor.parameters");
            arrayList.add(typeArgument((TypeParameterDescriptor) CollectionsKt___CollectionsKt.getOrNull(parameters, i2), (ProtoBuf.Type.Argument) obj));
            i2 = i3;
        }
        List<? extends TypeProjection> list = CollectionsKt___CollectionsKt.toList(arrayList);
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = typeConstructor.mo2086getDeclarationDescriptor();
        if (z2 && (classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeAliasDescriptor)) {
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            SimpleType simpleTypeComputeExpandedType = KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor) classifierDescriptorMo2086getDeclarationDescriptor, list);
            TypeAttributes attributes2 = toAttributes(this.f27636c.getComponents().getTypeAttributeTranslators(), Annotations.Companion.create(CollectionsKt___CollectionsKt.plus((Iterable) deserializedAnnotations, (Iterable) simpleTypeComputeExpandedType.getAnnotations())), typeConstructor, this.f27636c.getContainingDeclaration());
            if (!KotlinTypeKt.isNullable(simpleTypeComputeExpandedType) && !proto.getNullable()) {
                z3 = false;
            }
            simpleTypeSimpleType$default = simpleTypeComputeExpandedType.makeNullableAsSpecified(z3).replaceAttributes(attributes2);
        } else {
            Boolean bool = Flags.SUSPEND_TYPE.get(proto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "SUSPEND_TYPE.get(proto.flags)");
            if (bool.booleanValue()) {
                simpleTypeSimpleType$default = createSuspendFunctionType(attributes, typeConstructor, list, proto.getNullable());
            } else {
                simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(attributes, typeConstructor, list, proto.getNullable(), (KotlinTypeRefiner) null, 16, (Object) null);
                Boolean bool2 = Flags.DEFINITELY_NOT_NULL_TYPE.get(proto.getFlags());
                Intrinsics.checkNotNullExpressionValue(bool2, "DEFINITELY_NOT_NULL_TYPE.get(proto.flags)");
                if (bool2.booleanValue()) {
                    DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$default = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$default(DefinitelyNotNullType.Companion, simpleTypeSimpleType$default, true, false, 4, null);
                    if (definitelyNotNullTypeMakeDefinitelyNotNull$default == null) {
                        throw new IllegalStateException(("null DefinitelyNotNullType for '" + simpleTypeSimpleType$default + CharPool.SINGLE_QUOTE).toString());
                    }
                    simpleTypeSimpleType$default = definitelyNotNullTypeMakeDefinitelyNotNull$default;
                }
            }
        }
        ProtoBuf.Type typeAbbreviatedType = ProtoTypeTableUtilKt.abbreviatedType(proto, this.f27636c.getTypeTable());
        if (typeAbbreviatedType != null && (simpleTypeWithAbbreviation = SpecialTypesKt.withAbbreviation(simpleTypeSimpleType$default, simpleType(typeAbbreviatedType, false))) != null) {
            simpleTypeSimpleType$default = simpleTypeWithAbbreviation;
        }
        return proto.hasClassName() ? this.f27636c.getComponents().getPlatformDependentTypeTransformer().transformPlatformType(NameResolverUtilKt.getClassId(this.f27636c.getNameResolver(), proto.getClassName()), simpleTypeSimpleType$default) : simpleTypeSimpleType$default;
    }

    @NotNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.debugName);
        if (this.parent == null) {
            str = "";
        } else {
            str = ". Child of " + this.parent.debugName;
        }
        sb.append(str);
        return sb.toString();
    }

    @NotNull
    public final KotlinType type(@NotNull ProtoBuf.Type proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (!proto.hasFlexibleTypeCapabilitiesId()) {
            return simpleType(proto, true);
        }
        String string = this.f27636c.getNameResolver().getString(proto.getFlexibleTypeCapabilitiesId());
        SimpleType simpleTypeSimpleType$default = simpleType$default(this, proto, false, 2, null);
        ProtoBuf.Type typeFlexibleUpperBound = ProtoTypeTableUtilKt.flexibleUpperBound(proto, this.f27636c.getTypeTable());
        Intrinsics.checkNotNull(typeFlexibleUpperBound);
        return this.f27636c.getComponents().getFlexibleTypeDeserializer().create(proto, string, simpleTypeSimpleType$default, simpleType$default(this, typeFlexibleUpperBound, false, 2, null));
    }
}
