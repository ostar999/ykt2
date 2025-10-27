package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
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

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0002 !B3\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB#\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000bJ\u0011\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0006HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fHÇ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamLeaderBoardResult;", "", "seen1", "", "leaderBoard", "", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;", "userRanking", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;)V", "getLeaderBoard", "()Ljava/util/List;", "getUserRanking", "()Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamLeaderBoardResult {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<ExamLeaderBoardItemResult> leaderBoard;

    @Nullable
    private final ExamLeaderBoardItemResult userRanking;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamLeaderBoardResult$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardResult;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamLeaderBoardResult> serializer() {
            return ExamLeaderBoardResult$$serializer.INSTANCE;
        }
    }

    public ExamLeaderBoardResult() {
        this((List) null, (ExamLeaderBoardItemResult) (0 == true ? 1 : 0), 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamLeaderBoardResult(int i2, List list, ExamLeaderBoardItemResult examLeaderBoardItemResult, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamLeaderBoardResult$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.leaderBoard = null;
        } else {
            this.leaderBoard = list;
        }
        if ((i2 & 2) == 0) {
            this.userRanking = null;
        } else {
            this.userRanking = examLeaderBoardItemResult;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExamLeaderBoardResult copy$default(ExamLeaderBoardResult examLeaderBoardResult, List list, ExamLeaderBoardItemResult examLeaderBoardItemResult, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = examLeaderBoardResult.leaderBoard;
        }
        if ((i2 & 2) != 0) {
            examLeaderBoardItemResult = examLeaderBoardResult.userRanking;
        }
        return examLeaderBoardResult.copy(list, examLeaderBoardItemResult);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamLeaderBoardResult self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.leaderBoard != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(ExamLeaderBoardItemResult$$serializer.INSTANCE), self.leaderBoard);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.userRanking != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, ExamLeaderBoardItemResult$$serializer.INSTANCE, self.userRanking);
        }
    }

    @Nullable
    public final List<ExamLeaderBoardItemResult> component1() {
        return this.leaderBoard;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final ExamLeaderBoardItemResult getUserRanking() {
        return this.userRanking;
    }

    @NotNull
    public final ExamLeaderBoardResult copy(@Nullable List<ExamLeaderBoardItemResult> leaderBoard, @Nullable ExamLeaderBoardItemResult userRanking) {
        return new ExamLeaderBoardResult(leaderBoard, userRanking);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamLeaderBoardResult)) {
            return false;
        }
        ExamLeaderBoardResult examLeaderBoardResult = (ExamLeaderBoardResult) other;
        return Intrinsics.areEqual(this.leaderBoard, examLeaderBoardResult.leaderBoard) && Intrinsics.areEqual(this.userRanking, examLeaderBoardResult.userRanking);
    }

    @Nullable
    public final List<ExamLeaderBoardItemResult> getLeaderBoard() {
        return this.leaderBoard;
    }

    @Nullable
    public final ExamLeaderBoardItemResult getUserRanking() {
        return this.userRanking;
    }

    public int hashCode() {
        List<ExamLeaderBoardItemResult> list = this.leaderBoard;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        ExamLeaderBoardItemResult examLeaderBoardItemResult = this.userRanking;
        return iHashCode + (examLeaderBoardItemResult != null ? examLeaderBoardItemResult.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ExamLeaderBoardResult(leaderBoard=" + this.leaderBoard + ", userRanking=" + this.userRanking + ")";
    }

    public ExamLeaderBoardResult(@Nullable List<ExamLeaderBoardItemResult> list, @Nullable ExamLeaderBoardItemResult examLeaderBoardItemResult) {
        this.leaderBoard = list;
        this.userRanking = examLeaderBoardItemResult;
    }

    public /* synthetic */ ExamLeaderBoardResult(List list, ExamLeaderBoardItemResult examLeaderBoardItemResult, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list, (i2 & 2) != 0 ? null : examLeaderBoardItemResult);
    }
}
