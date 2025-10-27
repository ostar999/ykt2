package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
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
import z.a;

@Keep
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0002()BC\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB7\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J;\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\t\u0010 \u001a\u00020\u0006HÖ\u0001J!\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000f¨\u0006*"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillHistory;", "", "seen1", "", "isSatisfy", "name", "", "practiceId", "", "score", "sort", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;JIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;JII)V", "()I", "getName", "()Ljava/lang/String;", "getPracticeId", "()J", "getScore", "getSort", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class SkillHistory {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int isSatisfy;

    @NotNull
    private final String name;
    private final long practiceId;
    private final int score;
    private final int sort;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillHistory$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillHistory;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillHistory> serializer() {
            return SkillHistory$$serializer.INSTANCE;
        }
    }

    public SkillHistory() {
        this(0, (String) null, 0L, 0, 0, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillHistory(int i2, int i3, String str, long j2, int i4, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, SkillHistory$$serializer.INSTANCE.getDescriptor());
        }
        this.isSatisfy = (i2 & 1) == 0 ? -1 : i3;
        if ((i2 & 2) == 0) {
            this.name = "";
        } else {
            this.name = str;
        }
        if ((i2 & 4) == 0) {
            this.practiceId = -1L;
        } else {
            this.practiceId = j2;
        }
        if ((i2 & 8) == 0) {
            this.score = 0;
        } else {
            this.score = i4;
        }
        if ((i2 & 16) == 0) {
            this.sort = 1;
        } else {
            this.sort = i5;
        }
    }

    public static /* synthetic */ SkillHistory copy$default(SkillHistory skillHistory, int i2, String str, long j2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = skillHistory.isSatisfy;
        }
        if ((i5 & 2) != 0) {
            str = skillHistory.name;
        }
        String str2 = str;
        if ((i5 & 4) != 0) {
            j2 = skillHistory.practiceId;
        }
        long j3 = j2;
        if ((i5 & 8) != 0) {
            i3 = skillHistory.score;
        }
        int i6 = i3;
        if ((i5 & 16) != 0) {
            i4 = skillHistory.sort;
        }
        return skillHistory.copy(i2, str2, j3, i6, i4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillHistory self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.isSatisfy != -1) {
            output.encodeIntElement(serialDesc, 0, self.isSatisfy);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeStringElement(serialDesc, 1, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.practiceId != -1) {
            output.encodeLongElement(serialDesc, 2, self.practiceId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.score != 0) {
            output.encodeIntElement(serialDesc, 3, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.sort != 1) {
            output.encodeIntElement(serialDesc, 4, self.sort);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getIsSatisfy() {
        return this.isSatisfy;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final long getPracticeId() {
        return this.practiceId;
    }

    /* renamed from: component4, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    /* renamed from: component5, reason: from getter */
    public final int getSort() {
        return this.sort;
    }

    @NotNull
    public final SkillHistory copy(int isSatisfy, @NotNull String name, long practiceId, int score, int sort) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new SkillHistory(isSatisfy, name, practiceId, score, sort);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillHistory)) {
            return false;
        }
        SkillHistory skillHistory = (SkillHistory) other;
        return this.isSatisfy == skillHistory.isSatisfy && Intrinsics.areEqual(this.name, skillHistory.name) && this.practiceId == skillHistory.practiceId && this.score == skillHistory.score && this.sort == skillHistory.sort;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final long getPracticeId() {
        return this.practiceId;
    }

    public final int getScore() {
        return this.score;
    }

    public final int getSort() {
        return this.sort;
    }

    public int hashCode() {
        return (((((((this.isSatisfy * 31) + this.name.hashCode()) * 31) + a.a(this.practiceId)) * 31) + this.score) * 31) + this.sort;
    }

    public final int isSatisfy() {
        return this.isSatisfy;
    }

    @NotNull
    public String toString() {
        return "SkillHistory(isSatisfy=" + this.isSatisfy + ", name=" + this.name + ", practiceId=" + this.practiceId + ", score=" + this.score + ", sort=" + this.sort + ")";
    }

    public SkillHistory(int i2, @NotNull String name, long j2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.isSatisfy = i2;
        this.name = name;
        this.practiceId = j2;
        this.score = i3;
        this.sort = i4;
    }

    public /* synthetic */ SkillHistory(int i2, String str, long j2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? -1 : i2, (i5 & 2) != 0 ? "" : str, (i5 & 4) != 0 ? -1L : j2, (i5 & 8) != 0 ? 0 : i3, (i5 & 16) != 0 ? 1 : i4);
    }
}
