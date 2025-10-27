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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0002 !B7\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB%\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bJ\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J)\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fHÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillLabel;", "", "seen1", "", "dictConfigId", "", "skillId", "labelName", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDictConfigId", "()Ljava/lang/String;", "getLabelName", "getSkillId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
@Deprecated(message = "废了")
/* loaded from: classes6.dex */
public final /* data */ class SkillLabel {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String dictConfigId;

    @NotNull
    private final String labelName;

    @Nullable
    private final String skillId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillLabel$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillLabel;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillLabel> serializer() {
            return SkillLabel$$serializer.INSTANCE;
        }
    }

    public SkillLabel() {
        this((String) null, (String) null, (String) null, 7, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillLabel(int i2, String str, String str2, String str3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, SkillLabel$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.dictConfigId = "";
        } else {
            this.dictConfigId = str;
        }
        if ((i2 & 2) == 0) {
            this.skillId = "";
        } else {
            this.skillId = str2;
        }
        if ((i2 & 4) == 0) {
            this.labelName = "";
        } else {
            this.labelName = str3;
        }
    }

    public static /* synthetic */ SkillLabel copy$default(SkillLabel skillLabel, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = skillLabel.dictConfigId;
        }
        if ((i2 & 2) != 0) {
            str2 = skillLabel.skillId;
        }
        if ((i2 & 4) != 0) {
            str3 = skillLabel.labelName;
        }
        return skillLabel.copy(str, str2, str3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillLabel self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.dictConfigId, "")) {
            output.encodeStringElement(serialDesc, 0, self.dictConfigId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.skillId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.skillId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.labelName, "")) {
            output.encodeStringElement(serialDesc, 2, self.labelName);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getDictConfigId() {
        return this.dictConfigId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getSkillId() {
        return this.skillId;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getLabelName() {
        return this.labelName;
    }

    @NotNull
    public final SkillLabel copy(@NotNull String dictConfigId, @Nullable String skillId, @NotNull String labelName) {
        Intrinsics.checkNotNullParameter(dictConfigId, "dictConfigId");
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        return new SkillLabel(dictConfigId, skillId, labelName);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillLabel)) {
            return false;
        }
        SkillLabel skillLabel = (SkillLabel) other;
        return Intrinsics.areEqual(this.dictConfigId, skillLabel.dictConfigId) && Intrinsics.areEqual(this.skillId, skillLabel.skillId) && Intrinsics.areEqual(this.labelName, skillLabel.labelName);
    }

    @NotNull
    public final String getDictConfigId() {
        return this.dictConfigId;
    }

    @NotNull
    public final String getLabelName() {
        return this.labelName;
    }

    @Nullable
    public final String getSkillId() {
        return this.skillId;
    }

    public int hashCode() {
        int iHashCode = this.dictConfigId.hashCode() * 31;
        String str = this.skillId;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.labelName.hashCode();
    }

    @NotNull
    public String toString() {
        return "SkillLabel(dictConfigId=" + this.dictConfigId + ", skillId=" + this.skillId + ", labelName=" + this.labelName + ")";
    }

    public SkillLabel(@NotNull String dictConfigId, @Nullable String str, @NotNull String labelName) {
        Intrinsics.checkNotNullParameter(dictConfigId, "dictConfigId");
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        this.dictConfigId = dictConfigId;
        this.skillId = str;
        this.labelName = labelName;
    }

    public /* synthetic */ SkillLabel(String str, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3);
    }
}
