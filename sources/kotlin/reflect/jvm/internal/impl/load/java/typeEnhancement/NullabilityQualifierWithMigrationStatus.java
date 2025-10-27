package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class NullabilityQualifierWithMigrationStatus {
    private final boolean isForWarningOnly;

    @NotNull
    private final NullabilityQualifier qualifier;

    public NullabilityQualifierWithMigrationStatus(@NotNull NullabilityQualifier qualifier, boolean z2) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        this.qualifier = qualifier;
        this.isForWarningOnly = z2;
    }

    public static /* synthetic */ NullabilityQualifierWithMigrationStatus copy$default(NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, NullabilityQualifier nullabilityQualifier, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            nullabilityQualifier = nullabilityQualifierWithMigrationStatus.qualifier;
        }
        if ((i2 & 2) != 0) {
            z2 = nullabilityQualifierWithMigrationStatus.isForWarningOnly;
        }
        return nullabilityQualifierWithMigrationStatus.copy(nullabilityQualifier, z2);
    }

    @NotNull
    public final NullabilityQualifierWithMigrationStatus copy(@NotNull NullabilityQualifier qualifier, boolean z2) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        return new NullabilityQualifierWithMigrationStatus(qualifier, z2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NullabilityQualifierWithMigrationStatus)) {
            return false;
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = (NullabilityQualifierWithMigrationStatus) obj;
        return this.qualifier == nullabilityQualifierWithMigrationStatus.qualifier && this.isForWarningOnly == nullabilityQualifierWithMigrationStatus.isForWarningOnly;
    }

    @NotNull
    public final NullabilityQualifier getQualifier() {
        return this.qualifier;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = this.qualifier.hashCode() * 31;
        boolean z2 = this.isForWarningOnly;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    public final boolean isForWarningOnly() {
        return this.isForWarningOnly;
    }

    @NotNull
    public String toString() {
        return "NullabilityQualifierWithMigrationStatus(qualifier=" + this.qualifier + ", isForWarningOnly=" + this.isForWarningOnly + ')';
    }

    public /* synthetic */ NullabilityQualifierWithMigrationStatus(NullabilityQualifier nullabilityQualifier, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(nullabilityQualifier, (i2 & 2) != 0 ? false : z2);
    }
}
