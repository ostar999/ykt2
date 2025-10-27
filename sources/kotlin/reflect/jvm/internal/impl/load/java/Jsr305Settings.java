package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class Jsr305Settings {

    @NotNull
    private final Lazy description$delegate;

    @NotNull
    private final ReportLevel globalLevel;
    private final boolean isDisabled;

    @Nullable
    private final ReportLevel migrationLevel;

    @NotNull
    private final Map<FqName, ReportLevel> userDefinedLevelForSpecificAnnotation;

    /* JADX WARN: Multi-variable type inference failed */
    public Jsr305Settings(@NotNull ReportLevel globalLevel, @Nullable ReportLevel reportLevel, @NotNull Map<FqName, ? extends ReportLevel> userDefinedLevelForSpecificAnnotation) {
        Intrinsics.checkNotNullParameter(globalLevel, "globalLevel");
        Intrinsics.checkNotNullParameter(userDefinedLevelForSpecificAnnotation, "userDefinedLevelForSpecificAnnotation");
        this.globalLevel = globalLevel;
        this.migrationLevel = reportLevel;
        this.userDefinedLevelForSpecificAnnotation = userDefinedLevelForSpecificAnnotation;
        this.description$delegate = LazyKt__LazyJVMKt.lazy(new Function0<String[]>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.Jsr305Settings$description$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final String[] invoke() {
                Jsr305Settings jsr305Settings = this.this$0;
                List listCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                listCreateListBuilder.add(jsr305Settings.getGlobalLevel().getDescription());
                ReportLevel migrationLevel = jsr305Settings.getMigrationLevel();
                if (migrationLevel != null) {
                    listCreateListBuilder.add("under-migration:" + migrationLevel.getDescription());
                }
                for (Map.Entry<FqName, ReportLevel> entry : jsr305Settings.getUserDefinedLevelForSpecificAnnotation().entrySet()) {
                    listCreateListBuilder.add('@' + entry.getKey() + ':' + entry.getValue().getDescription());
                }
                return (String[]) CollectionsKt__CollectionsJVMKt.build(listCreateListBuilder).toArray(new String[0]);
            }
        });
        ReportLevel reportLevel2 = ReportLevel.IGNORE;
        this.isDisabled = globalLevel == reportLevel2 && reportLevel == reportLevel2 && userDefinedLevelForSpecificAnnotation.isEmpty();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Jsr305Settings)) {
            return false;
        }
        Jsr305Settings jsr305Settings = (Jsr305Settings) obj;
        return this.globalLevel == jsr305Settings.globalLevel && this.migrationLevel == jsr305Settings.migrationLevel && Intrinsics.areEqual(this.userDefinedLevelForSpecificAnnotation, jsr305Settings.userDefinedLevelForSpecificAnnotation);
    }

    @NotNull
    public final ReportLevel getGlobalLevel() {
        return this.globalLevel;
    }

    @Nullable
    public final ReportLevel getMigrationLevel() {
        return this.migrationLevel;
    }

    @NotNull
    public final Map<FqName, ReportLevel> getUserDefinedLevelForSpecificAnnotation() {
        return this.userDefinedLevelForSpecificAnnotation;
    }

    public int hashCode() {
        int iHashCode = this.globalLevel.hashCode() * 31;
        ReportLevel reportLevel = this.migrationLevel;
        return ((iHashCode + (reportLevel == null ? 0 : reportLevel.hashCode())) * 31) + this.userDefinedLevelForSpecificAnnotation.hashCode();
    }

    public final boolean isDisabled() {
        return this.isDisabled;
    }

    @NotNull
    public String toString() {
        return "Jsr305Settings(globalLevel=" + this.globalLevel + ", migrationLevel=" + this.migrationLevel + ", userDefinedLevelForSpecificAnnotation=" + this.userDefinedLevelForSpecificAnnotation + ')';
    }

    public /* synthetic */ Jsr305Settings(ReportLevel reportLevel, ReportLevel reportLevel2, Map map, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(reportLevel, (i2 & 2) != 0 ? null : reportLevel2, (i2 & 4) != 0 ? MapsKt__MapsKt.emptyMap() : map);
    }
}
