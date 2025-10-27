package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class AnnotationQualifiersFqNamesKt {

    @NotNull
    private static final Map<FqName, JavaDefaultQualifiers> BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;

    @NotNull
    private static final Set<FqName> BUILT_IN_TYPE_QUALIFIER_FQ_NAMES;

    @NotNull
    private static final List<AnnotationQualifierApplicabilityType> DEFAULT_JSPECIFY_APPLICABILITY;

    @NotNull
    private static final Map<FqName, JavaDefaultQualifiers> JSPECIFY_DEFAULT_ANNOTATIONS;

    @NotNull
    private static final FqName TYPE_QUALIFIER_NICKNAME_FQNAME = new FqName("javax.annotation.meta.TypeQualifierNickname");

    @NotNull
    private static final FqName TYPE_QUALIFIER_FQNAME = new FqName("javax.annotation.meta.TypeQualifier");

    @NotNull
    private static final FqName TYPE_QUALIFIER_DEFAULT_FQNAME = new FqName("javax.annotation.meta.TypeQualifierDefault");

    @NotNull
    private static final FqName MIGRATION_ANNOTATION_FQNAME = new FqName("kotlin.annotations.jvm.UnderMigration");

    static {
        AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.VALUE_PARAMETER;
        List<AnnotationQualifierApplicabilityType> listListOf = CollectionsKt__CollectionsKt.listOf((Object[]) new AnnotationQualifierApplicabilityType[]{AnnotationQualifierApplicabilityType.FIELD, AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE, annotationQualifierApplicabilityType, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, AnnotationQualifierApplicabilityType.TYPE_USE});
        DEFAULT_JSPECIFY_APPLICABILITY = listListOf;
        FqName jspecify_old_null_marked = JvmAnnotationNamesKt.getJSPECIFY_OLD_NULL_MARKED();
        NullabilityQualifier nullabilityQualifier = NullabilityQualifier.NOT_NULL;
        Map<FqName, JavaDefaultQualifiers> mapMapOf = MapsKt__MapsKt.mapOf(TuplesKt.to(jspecify_old_null_marked, new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, false, 2, null), listListOf, false)), TuplesKt.to(JvmAnnotationNamesKt.getJSPECIFY_NULL_MARKED(), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, false, 2, null), listListOf, false)));
        JSPECIFY_DEFAULT_ANNOTATIONS = mapMapOf;
        BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS = MapsKt__MapsKt.plus(MapsKt__MapsKt.mapOf(TuplesKt.to(new FqName("javax.annotation.ParametersAreNullableByDefault"), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null), CollectionsKt__CollectionsJVMKt.listOf(annotationQualifierApplicabilityType), false, 4, null)), TuplesKt.to(new FqName("javax.annotation.ParametersAreNonnullByDefault"), new JavaDefaultQualifiers(new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, false, 2, null), CollectionsKt__CollectionsJVMKt.listOf(annotationQualifierApplicabilityType), false, 4, null))), mapMapOf);
        BUILT_IN_TYPE_QUALIFIER_FQ_NAMES = SetsKt__SetsKt.setOf((Object[]) new FqName[]{JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION(), JvmAnnotationNamesKt.getJAVAX_CHECKFORNULL_ANNOTATION()});
    }

    @NotNull
    public static final Map<FqName, JavaDefaultQualifiers> getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS() {
        return BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;
    }

    @NotNull
    public static final Set<FqName> getBUILT_IN_TYPE_QUALIFIER_FQ_NAMES() {
        return BUILT_IN_TYPE_QUALIFIER_FQ_NAMES;
    }

    @NotNull
    public static final Map<FqName, JavaDefaultQualifiers> getJSPECIFY_DEFAULT_ANNOTATIONS() {
        return JSPECIFY_DEFAULT_ANNOTATIONS;
    }

    @NotNull
    public static final FqName getMIGRATION_ANNOTATION_FQNAME() {
        return MIGRATION_ANNOTATION_FQNAME;
    }

    @NotNull
    public static final FqName getTYPE_QUALIFIER_DEFAULT_FQNAME() {
        return TYPE_QUALIFIER_DEFAULT_FQNAME;
    }

    @NotNull
    public static final FqName getTYPE_QUALIFIER_FQNAME() {
        return TYPE_QUALIFIER_FQNAME;
    }

    @NotNull
    public static final FqName getTYPE_QUALIFIER_NICKNAME_FQNAME() {
        return TYPE_QUALIFIER_NICKNAME_FQNAME;
    }
}
