package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 '2\u00020\u0001:\u0002&'B?\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB-\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006¢\u0006\u0002\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0006HÖ\u0001J!\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%HÇ\u0001R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0014¨\u0006("}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;", "", "seen1", "", "rowNum", PLVLinkMicManager.USER, "", "practiceTime", "score", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getPracticeTime", "()Ljava/lang/String;", "getRowNum", "()I", "getScore", "getUser", "setUser", "(Ljava/lang/String;)V", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamLeaderBoardItemResult {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String practiceTime;
    private final int rowNum;

    @NotNull
    private final String score;

    @NotNull
    private String user;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamLeaderBoardItemResult> serializer() {
            return ExamLeaderBoardItemResult$$serializer.INSTANCE;
        }
    }

    public ExamLeaderBoardItemResult() {
        this(0, (String) null, (String) null, (String) null, 15, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamLeaderBoardItemResult(int i2, int i3, String str, String str2, String str3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamLeaderBoardItemResult$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.rowNum = 0;
        } else {
            this.rowNum = i3;
        }
        if ((i2 & 2) == 0) {
            this.user = "";
        } else {
            this.user = str;
        }
        if ((i2 & 4) == 0) {
            this.practiceTime = "0";
        } else {
            this.practiceTime = str2;
        }
        if ((i2 & 8) == 0) {
            this.score = "0";
        } else {
            this.score = str3;
        }
    }

    public static /* synthetic */ ExamLeaderBoardItemResult copy$default(ExamLeaderBoardItemResult examLeaderBoardItemResult, int i2, String str, String str2, String str3, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = examLeaderBoardItemResult.rowNum;
        }
        if ((i3 & 2) != 0) {
            str = examLeaderBoardItemResult.user;
        }
        if ((i3 & 4) != 0) {
            str2 = examLeaderBoardItemResult.practiceTime;
        }
        if ((i3 & 8) != 0) {
            str3 = examLeaderBoardItemResult.score;
        }
        return examLeaderBoardItemResult.copy(i2, str, str2, str3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamLeaderBoardItemResult self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.rowNum != 0) {
            output.encodeIntElement(serialDesc, 0, self.rowNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.user, "")) {
            output.encodeStringElement(serialDesc, 1, self.user);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.practiceTime, "0")) {
            output.encodeStringElement(serialDesc, 2, self.practiceTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.score, "0")) {
            output.encodeStringElement(serialDesc, 3, self.score);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getRowNum() {
        return this.rowNum;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getUser() {
        return this.user;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getPracticeTime() {
        return this.practiceTime;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getScore() {
        return this.score;
    }

    @NotNull
    public final ExamLeaderBoardItemResult copy(int rowNum, @NotNull String user, @NotNull String practiceTime, @NotNull String score) {
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(practiceTime, "practiceTime");
        Intrinsics.checkNotNullParameter(score, "score");
        return new ExamLeaderBoardItemResult(rowNum, user, practiceTime, score);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamLeaderBoardItemResult)) {
            return false;
        }
        ExamLeaderBoardItemResult examLeaderBoardItemResult = (ExamLeaderBoardItemResult) other;
        return this.rowNum == examLeaderBoardItemResult.rowNum && Intrinsics.areEqual(this.user, examLeaderBoardItemResult.user) && Intrinsics.areEqual(this.practiceTime, examLeaderBoardItemResult.practiceTime) && Intrinsics.areEqual(this.score, examLeaderBoardItemResult.score);
    }

    @NotNull
    public final String getPracticeTime() {
        return this.practiceTime;
    }

    public final int getRowNum() {
        return this.rowNum;
    }

    @NotNull
    public final String getScore() {
        return this.score;
    }

    @NotNull
    public final String getUser() {
        return this.user;
    }

    public int hashCode() {
        return (((((this.rowNum * 31) + this.user.hashCode()) * 31) + this.practiceTime.hashCode()) * 31) + this.score.hashCode();
    }

    public final void setUser(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.user = str;
    }

    @NotNull
    public String toString() {
        return "ExamLeaderBoardItemResult(rowNum=" + this.rowNum + ", user=" + this.user + ", practiceTime=" + this.practiceTime + ", score=" + this.score + ")";
    }

    public ExamLeaderBoardItemResult(int i2, @NotNull String user, @NotNull String practiceTime, @NotNull String score) {
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(practiceTime, "practiceTime");
        Intrinsics.checkNotNullParameter(score, "score");
        this.rowNum = i2;
        this.user = user;
        this.practiceTime = practiceTime;
        this.score = score;
    }

    public /* synthetic */ ExamLeaderBoardItemResult(int i2, String str, String str2, String str3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i2, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? "0" : str2, (i3 & 8) != 0 ? "0" : str3);
    }
}
