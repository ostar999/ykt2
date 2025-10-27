package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.KotlinVersion;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.load.java.JavaNullabilityAnnotationsStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nJavaNullabilityAnnotationSettings.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavaNullabilityAnnotationSettings.kt\norg/jetbrains/kotlin/load/java/JavaNullabilityAnnotationSettingsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,119:1\n1#2:120\n*E\n"})
/* loaded from: classes8.dex */
public final class JavaNullabilityAnnotationSettingsKt {

    @NotNull
    private static final FqName CHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE;

    @NotNull
    private static final FqName JSPECIFY_ANNOTATIONS_PACKAGE;

    @NotNull
    private static final FqName JSPECIFY_OLD_ANNOTATIONS_PACKAGE;

    @NotNull
    private static final JavaNullabilityAnnotationsStatus JSR_305_DEFAULT_SETTINGS;

    @NotNull
    private static final NullabilityAnnotationStates<JavaNullabilityAnnotationsStatus> NULLABILITY_ANNOTATION_SETTINGS;

    @NotNull
    private static final FqName[] RXJAVA3_ANNOTATIONS;

    @NotNull
    private static final FqName RXJAVA3_ANNOTATIONS_PACKAGE;

    @NotNull
    private static final String RXJAVA3_ANNOTATIONS_PACKAGE_NAME;

    static {
        FqName fqName = new FqName("org.jspecify.nullness");
        JSPECIFY_OLD_ANNOTATIONS_PACKAGE = fqName;
        FqName fqName2 = new FqName("org.jspecify.annotations");
        JSPECIFY_ANNOTATIONS_PACKAGE = fqName2;
        FqName fqName3 = new FqName("io.reactivex.rxjava3.annotations");
        RXJAVA3_ANNOTATIONS_PACKAGE = fqName3;
        FqName fqName4 = new FqName("org.checkerframework.checker.nullness.compatqual");
        CHECKER_FRAMEWORK_COMPATQUAL_ANNOTATIONS_PACKAGE = fqName4;
        String strAsString = fqName3.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "RXJAVA3_ANNOTATIONS_PACKAGE.asString()");
        RXJAVA3_ANNOTATIONS_PACKAGE_NAME = strAsString;
        RXJAVA3_ANNOTATIONS = new FqName[]{new FqName(strAsString + ".Nullable"), new FqName(strAsString + ".NonNull")};
        FqName fqName5 = new FqName("org.jetbrains.annotations");
        JavaNullabilityAnnotationsStatus.Companion companion = JavaNullabilityAnnotationsStatus.Companion;
        FqName fqName6 = new FqName("androidx.annotation.RecentlyNullable");
        ReportLevel reportLevel = ReportLevel.WARN;
        KotlinVersion kotlinVersion = new KotlinVersion(1, 9);
        ReportLevel reportLevel2 = ReportLevel.STRICT;
        NULLABILITY_ANNOTATION_SETTINGS = new NullabilityAnnotationStatesImpl(MapsKt__MapsKt.mapOf(TuplesKt.to(fqName5, companion.getDEFAULT()), TuplesKt.to(new FqName("androidx.annotation"), companion.getDEFAULT()), TuplesKt.to(new FqName("android.support.annotation"), companion.getDEFAULT()), TuplesKt.to(new FqName("android.annotation"), companion.getDEFAULT()), TuplesKt.to(new FqName("com.android.annotations"), companion.getDEFAULT()), TuplesKt.to(new FqName("org.eclipse.jdt.annotation"), companion.getDEFAULT()), TuplesKt.to(new FqName("org.checkerframework.checker.nullness.qual"), companion.getDEFAULT()), TuplesKt.to(fqName4, companion.getDEFAULT()), TuplesKt.to(new FqName("javax.annotation"), companion.getDEFAULT()), TuplesKt.to(new FqName("edu.umd.cs.findbugs.annotations"), companion.getDEFAULT()), TuplesKt.to(new FqName("io.reactivex.annotations"), companion.getDEFAULT()), TuplesKt.to(fqName6, new JavaNullabilityAnnotationsStatus(reportLevel, null, null, 4, null)), TuplesKt.to(new FqName("androidx.annotation.RecentlyNonNull"), new JavaNullabilityAnnotationsStatus(reportLevel, null, null, 4, null)), TuplesKt.to(new FqName("lombok"), companion.getDEFAULT()), TuplesKt.to(fqName, new JavaNullabilityAnnotationsStatus(reportLevel, kotlinVersion, reportLevel2)), TuplesKt.to(fqName2, new JavaNullabilityAnnotationsStatus(reportLevel, new KotlinVersion(1, 9), reportLevel2)), TuplesKt.to(fqName3, new JavaNullabilityAnnotationsStatus(reportLevel, new KotlinVersion(1, 8), reportLevel2))));
        JSR_305_DEFAULT_SETTINGS = new JavaNullabilityAnnotationsStatus(reportLevel, null, null, 4, null);
    }

    @NotNull
    public static final Jsr305Settings getDefaultJsr305Settings(@NotNull KotlinVersion configuredKotlinVersion) {
        Intrinsics.checkNotNullParameter(configuredKotlinVersion, "configuredKotlinVersion");
        JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus = JSR_305_DEFAULT_SETTINGS;
        ReportLevel reportLevelBefore = (javaNullabilityAnnotationsStatus.getSinceVersion() == null || javaNullabilityAnnotationsStatus.getSinceVersion().compareTo(configuredKotlinVersion) > 0) ? javaNullabilityAnnotationsStatus.getReportLevelBefore() : javaNullabilityAnnotationsStatus.getReportLevelAfter();
        return new Jsr305Settings(reportLevelBefore, getDefaultMigrationJsr305ReportLevelForGivenGlobal(reportLevelBefore), null, 4, null);
    }

    public static /* synthetic */ Jsr305Settings getDefaultJsr305Settings$default(KotlinVersion kotlinVersion, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            kotlinVersion = KotlinVersion.CURRENT;
        }
        return getDefaultJsr305Settings(kotlinVersion);
    }

    @Nullable
    public static final ReportLevel getDefaultMigrationJsr305ReportLevelForGivenGlobal(@NotNull ReportLevel globalReportLevel) {
        Intrinsics.checkNotNullParameter(globalReportLevel, "globalReportLevel");
        if (globalReportLevel == ReportLevel.WARN) {
            return null;
        }
        return globalReportLevel;
    }

    @NotNull
    public static final ReportLevel getDefaultReportLevelForAnnotation(@NotNull FqName annotationFqName) {
        Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
        return getReportLevelForAnnotation$default(annotationFqName, NullabilityAnnotationStates.Companion.getEMPTY(), null, 4, null);
    }

    @NotNull
    public static final FqName getJSPECIFY_ANNOTATIONS_PACKAGE() {
        return JSPECIFY_ANNOTATIONS_PACKAGE;
    }

    @NotNull
    public static final FqName[] getRXJAVA3_ANNOTATIONS() {
        return RXJAVA3_ANNOTATIONS;
    }

    @NotNull
    public static final ReportLevel getReportLevelForAnnotation(@NotNull FqName annotation, @NotNull NullabilityAnnotationStates<? extends ReportLevel> configuredReportLevels, @NotNull KotlinVersion configuredKotlinVersion) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(configuredReportLevels, "configuredReportLevels");
        Intrinsics.checkNotNullParameter(configuredKotlinVersion, "configuredKotlinVersion");
        ReportLevel reportLevel = configuredReportLevels.get(annotation);
        if (reportLevel != null) {
            return reportLevel;
        }
        JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus = NULLABILITY_ANNOTATION_SETTINGS.get(annotation);
        return javaNullabilityAnnotationsStatus == null ? ReportLevel.IGNORE : (javaNullabilityAnnotationsStatus.getSinceVersion() == null || javaNullabilityAnnotationsStatus.getSinceVersion().compareTo(configuredKotlinVersion) > 0) ? javaNullabilityAnnotationsStatus.getReportLevelBefore() : javaNullabilityAnnotationsStatus.getReportLevelAfter();
    }

    public static /* synthetic */ ReportLevel getReportLevelForAnnotation$default(FqName fqName, NullabilityAnnotationStates nullabilityAnnotationStates, KotlinVersion kotlinVersion, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            kotlinVersion = new KotlinVersion(1, 7, 20);
        }
        return getReportLevelForAnnotation(fqName, nullabilityAnnotationStates, kotlinVersion);
    }
}
