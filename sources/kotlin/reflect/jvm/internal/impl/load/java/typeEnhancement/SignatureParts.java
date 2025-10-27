package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AbstractAnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nsignatureEnhancement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 signatureEnhancement.kt\norg/jetbrains/kotlin/load/java/typeEnhancement/SignatureParts\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,282:1\n1#2:283\n*E\n"})
/* loaded from: classes8.dex */
final class SignatureParts extends AbstractSignatureParts<AnnotationDescriptor> {

    @NotNull
    private final AnnotationQualifierApplicabilityType containerApplicabilityType;

    @NotNull
    private final LazyJavaResolverContext containerContext;
    private final boolean isCovariant;
    private final boolean skipRawTypeArguments;

    @Nullable
    private final Annotated typeContainer;

    public /* synthetic */ SignatureParts(Annotated annotated, boolean z2, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotated, z2, lazyJavaResolverContext, annotationQualifierApplicabilityType, (i2 & 16) != 0 ? false : z3);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @NotNull
    public Iterable<AnnotationDescriptor> getAnnotations(@NotNull KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return ((KotlinType) kotlinTypeMarker).getAnnotations();
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @NotNull
    public Iterable<AnnotationDescriptor> getContainerAnnotations() {
        Annotations annotations;
        Annotated annotated = this.typeContainer;
        return (annotated == null || (annotations = annotated.getAnnotations()) == null) ? CollectionsKt__CollectionsKt.emptyList() : annotations;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @NotNull
    public AnnotationQualifierApplicabilityType getContainerApplicabilityType() {
        return this.containerApplicabilityType;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @Nullable
    public JavaTypeQualifiersByElementType getContainerDefaultTypeQualifiers() {
        return this.containerContext.getDefaultTypeQualifiers();
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getContainerIsVarargParameter() {
        Annotated annotated = this.typeContainer;
        return (annotated instanceof ValueParameterDescriptor) && ((ValueParameterDescriptor) annotated).getVarargElementType() != null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getEnableImprovementsInStrictMode() {
        return this.containerContext.getComponents().getSettings().getTypeEnhancementImprovementsInStrictMode();
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @Nullable
    public FqNameUnsafe getFqNameUnsafe(@NotNull KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor((KotlinType) kotlinTypeMarker);
        if (classDescriptor != null) {
            return DescriptorUtils.getFqName(classDescriptor);
        }
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getSkipRawTypeArguments() {
        return this.skipRawTypeArguments;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isArrayOrPrimitiveArray(@NotNull KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return KotlinBuiltIns.isArrayOrPrimitiveArray((KotlinType) kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isCovariant() {
        return this.isCovariant;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isEqual(@NotNull KotlinTypeMarker kotlinTypeMarker, @NotNull KotlinTypeMarker other) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return this.containerContext.getComponents().getKotlinTypeChecker().equalTypes((KotlinType) kotlinTypeMarker, (KotlinType) other);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isFromJava(@NotNull TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return typeParameterMarker instanceof LazyJavaTypeParameterDescriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isNotNullTypeParameterCompat(@NotNull KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return ((KotlinType) kotlinTypeMarker).unwrap() instanceof NotNullTypeParameterImpl;
    }

    public SignatureParts(@Nullable Annotated annotated, boolean z2, @NotNull LazyJavaResolverContext containerContext, @NotNull AnnotationQualifierApplicabilityType containerApplicabilityType, boolean z3) {
        Intrinsics.checkNotNullParameter(containerContext, "containerContext");
        Intrinsics.checkNotNullParameter(containerApplicabilityType, "containerApplicabilityType");
        this.typeContainer = annotated;
        this.isCovariant = z2;
        this.containerContext = containerContext;
        this.containerApplicabilityType = containerApplicabilityType;
        this.skipRawTypeArguments = z3;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    public boolean forceWarning(@NotNull AnnotationDescriptor annotationDescriptor, @Nullable KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        return ((annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor) && ((PossiblyExternalAnnotationDescriptor) annotationDescriptor).isIdeExternalAnnotation()) || ((annotationDescriptor instanceof LazyJavaAnnotationDescriptor) && !getEnableImprovementsInStrictMode() && (((LazyJavaAnnotationDescriptor) annotationDescriptor).isFreshlySupportedTypeUseAnnotation() || getContainerApplicabilityType() == AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS)) || (kotlinTypeMarker != null && KotlinBuiltIns.isPrimitiveArray((KotlinType) kotlinTypeMarker) && getAnnotationTypeQualifierResolver().isTypeUseAnnotation(annotationDescriptor) && !this.containerContext.getComponents().getSettings().getEnhancePrimitiveArrays());
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @NotNull
    public AbstractAnnotationTypeQualifierResolver<AnnotationDescriptor> getAnnotationTypeQualifierResolver() {
        return this.containerContext.getComponents().getAnnotationTypeQualifierResolver();
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @Nullable
    public KotlinType getEnhancedForWarnings(@NotNull KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return TypeWithEnhancementKt.getEnhancement((KotlinType) kotlinTypeMarker);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.AbstractSignatureParts
    @NotNull
    public TypeSystemInferenceExtensionContext getTypeSystem() {
        return SimpleClassicTypeSystemContext.INSTANCE;
    }
}
