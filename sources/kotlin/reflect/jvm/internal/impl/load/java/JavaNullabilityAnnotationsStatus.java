package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.KotlinVersion;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class JavaNullabilityAnnotationsStatus {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final JavaNullabilityAnnotationsStatus DEFAULT = new JavaNullabilityAnnotationsStatus(ReportLevel.STRICT, null, null, 6, null);

    @NotNull
    private final ReportLevel reportLevelAfter;

    @NotNull
    private final ReportLevel reportLevelBefore;

    @Nullable
    private final KotlinVersion sinceVersion;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final JavaNullabilityAnnotationsStatus getDEFAULT() {
            return JavaNullabilityAnnotationsStatus.DEFAULT;
        }
    }

    public JavaNullabilityAnnotationsStatus(@NotNull ReportLevel reportLevelBefore, @Nullable KotlinVersion kotlinVersion, @NotNull ReportLevel reportLevelAfter) {
        Intrinsics.checkNotNullParameter(reportLevelBefore, "reportLevelBefore");
        Intrinsics.checkNotNullParameter(reportLevelAfter, "reportLevelAfter");
        this.reportLevelBefore = reportLevelBefore;
        this.sinceVersion = kotlinVersion;
        this.reportLevelAfter = reportLevelAfter;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JavaNullabilityAnnotationsStatus)) {
            return false;
        }
        JavaNullabilityAnnotationsStatus javaNullabilityAnnotationsStatus = (JavaNullabilityAnnotationsStatus) obj;
        return this.reportLevelBefore == javaNullabilityAnnotationsStatus.reportLevelBefore && Intrinsics.areEqual(this.sinceVersion, javaNullabilityAnnotationsStatus.sinceVersion) && this.reportLevelAfter == javaNullabilityAnnotationsStatus.reportLevelAfter;
    }

    @NotNull
    public final ReportLevel getReportLevelAfter() {
        return this.reportLevelAfter;
    }

    @NotNull
    public final ReportLevel getReportLevelBefore() {
        return this.reportLevelBefore;
    }

    @Nullable
    public final KotlinVersion getSinceVersion() {
        return this.sinceVersion;
    }

    public int hashCode() {
        int iHashCode = this.reportLevelBefore.hashCode() * 31;
        KotlinVersion kotlinVersion = this.sinceVersion;
        return ((iHashCode + (kotlinVersion == null ? 0 : kotlinVersion.getVersion())) * 31) + this.reportLevelAfter.hashCode();
    }

    @NotNull
    public String toString() {
        return "JavaNullabilityAnnotationsStatus(reportLevelBefore=" + this.reportLevelBefore + ", sinceVersion=" + this.sinceVersion + ", reportLevelAfter=" + this.reportLevelAfter + ')';
    }

    public /* synthetic */ JavaNullabilityAnnotationsStatus(ReportLevel reportLevel, KotlinVersion kotlinVersion, ReportLevel reportLevel2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(reportLevel, (i2 & 2) != 0 ? new KotlinVersion(1, 0) : kotlinVersion, (i2 & 4) != 0 ? reportLevel : reportLevel2);
    }
}
