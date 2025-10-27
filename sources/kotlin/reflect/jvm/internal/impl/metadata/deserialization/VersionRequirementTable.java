package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class VersionRequirementTable {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final VersionRequirementTable EMPTY = new VersionRequirementTable(CollectionsKt__CollectionsKt.emptyList());

    @NotNull
    private final List<ProtoBuf.VersionRequirement> infos;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final VersionRequirementTable create(@NotNull ProtoBuf.VersionRequirementTable table) {
            Intrinsics.checkNotNullParameter(table, "table");
            if (table.getRequirementCount() == 0) {
                return getEMPTY();
            }
            List<ProtoBuf.VersionRequirement> requirementList = table.getRequirementList();
            Intrinsics.checkNotNullExpressionValue(requirementList, "table.requirementList");
            return new VersionRequirementTable(requirementList, null);
        }

        @NotNull
        public final VersionRequirementTable getEMPTY() {
            return VersionRequirementTable.EMPTY;
        }
    }

    private VersionRequirementTable(List<ProtoBuf.VersionRequirement> list) {
        this.infos = list;
    }

    public /* synthetic */ VersionRequirementTable(List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(list);
    }
}
