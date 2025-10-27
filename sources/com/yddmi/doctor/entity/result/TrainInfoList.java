package com.yddmi.doctor.entity.result;

import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0002 !B1\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB!\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bJ\u0011\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J%\u0010\u0012\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fHÇ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/entity/result/TrainInfoList;", "", "seen1", "", "rows", "", "Lcom/yddmi/doctor/entity/result/RowCase;", "total", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;I)V", "getRows", "()Ljava/util/List;", "getTotal", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class TrainInfoList {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<RowCase> rows;
    private final int total;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/TrainInfoList$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/TrainInfoList;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<TrainInfoList> serializer() {
            return TrainInfoList$$serializer.INSTANCE;
        }
    }

    public TrainInfoList() {
        this((List) null, 0, 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ TrainInfoList(int i2, List list, int i3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, TrainInfoList$$serializer.INSTANCE.getDescriptor());
        }
        this.rows = (i2 & 1) == 0 ? null : list;
        if ((i2 & 2) == 0) {
            this.total = 0;
        } else {
            this.total = i3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TrainInfoList copy$default(TrainInfoList trainInfoList, List list, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            list = trainInfoList.rows;
        }
        if ((i3 & 2) != 0) {
            i2 = trainInfoList.total;
        }
        return trainInfoList.copy(list, i2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull TrainInfoList self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.rows != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(RowCase$$serializer.INSTANCE), self.rows);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.total != 0) {
            output.encodeIntElement(serialDesc, 1, self.total);
        }
    }

    @Nullable
    public final List<RowCase> component1() {
        return this.rows;
    }

    /* renamed from: component2, reason: from getter */
    public final int getTotal() {
        return this.total;
    }

    @NotNull
    public final TrainInfoList copy(@Nullable List<RowCase> rows, int total) {
        return new TrainInfoList(rows, total);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TrainInfoList)) {
            return false;
        }
        TrainInfoList trainInfoList = (TrainInfoList) other;
        return Intrinsics.areEqual(this.rows, trainInfoList.rows) && this.total == trainInfoList.total;
    }

    @Nullable
    public final List<RowCase> getRows() {
        return this.rows;
    }

    public final int getTotal() {
        return this.total;
    }

    public int hashCode() {
        List<RowCase> list = this.rows;
        return ((list == null ? 0 : list.hashCode()) * 31) + this.total;
    }

    @NotNull
    public String toString() {
        return "TrainInfoList(rows=" + this.rows + ", total=" + this.total + ")";
    }

    public TrainInfoList(@Nullable List<RowCase> list, int i2) {
        this.rows = list;
        this.total = i2;
    }

    public /* synthetic */ TrainInfoList(List list, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : list, (i3 & 2) != 0 ? 0 : i2);
    }
}
