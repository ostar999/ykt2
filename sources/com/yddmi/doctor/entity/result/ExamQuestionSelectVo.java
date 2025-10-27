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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 -2\u00020\u0001:\u0002,-BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB?\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003JC\u0010\u001f\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020\u0003HÖ\u0001J\t\u0010$\u001a\u00020\u0005HÖ\u0001J!\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000f\"\u0004\b\u0019\u0010\u0016¨\u0006."}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamQuestionSelectVo;", "", "seen1", "", "id", "", "name", "questionId", "type", "itemStateColor", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getId", "()Ljava/lang/String;", "getItemStateColor", "()I", "setItemStateColor", "(I)V", "getName", "setName", "(Ljava/lang/String;)V", "getQuestionId", "getType", "setType", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamQuestionSelectVo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String id;
    private int itemStateColor;

    @Nullable
    private String name;

    @Nullable
    private final String questionId;

    @Nullable
    private String type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamQuestionSelectVo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamQuestionSelectVo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamQuestionSelectVo> serializer() {
            return ExamQuestionSelectVo$$serializer.INSTANCE;
        }
    }

    public ExamQuestionSelectVo() {
        this((String) null, (String) null, (String) null, (String) null, 0, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamQuestionSelectVo(int i2, String str, String str2, String str3, String str4, int i3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamQuestionSelectVo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.id = "";
        } else {
            this.id = str;
        }
        if ((i2 & 2) == 0) {
            this.name = "";
        } else {
            this.name = str2;
        }
        if ((i2 & 4) == 0) {
            this.questionId = "";
        } else {
            this.questionId = str3;
        }
        if ((i2 & 8) == 0) {
            this.type = "";
        } else {
            this.type = str4;
        }
        if ((i2 & 16) == 0) {
            this.itemStateColor = 0;
        } else {
            this.itemStateColor = i3;
        }
    }

    public static /* synthetic */ ExamQuestionSelectVo copy$default(ExamQuestionSelectVo examQuestionSelectVo, String str, String str2, String str3, String str4, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = examQuestionSelectVo.id;
        }
        if ((i3 & 2) != 0) {
            str2 = examQuestionSelectVo.name;
        }
        String str5 = str2;
        if ((i3 & 4) != 0) {
            str3 = examQuestionSelectVo.questionId;
        }
        String str6 = str3;
        if ((i3 & 8) != 0) {
            str4 = examQuestionSelectVo.type;
        }
        String str7 = str4;
        if ((i3 & 16) != 0) {
            i2 = examQuestionSelectVo.itemStateColor;
        }
        return examQuestionSelectVo.copy(str, str5, str6, str7, i2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamQuestionSelectVo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.questionId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.questionId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.type, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.itemStateColor != 0) {
            output.encodeIntElement(serialDesc, 4, self.itemStateColor);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getQuestionId() {
        return this.questionId;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* renamed from: component5, reason: from getter */
    public final int getItemStateColor() {
        return this.itemStateColor;
    }

    @NotNull
    public final ExamQuestionSelectVo copy(@Nullable String id, @Nullable String name, @Nullable String questionId, @Nullable String type, int itemStateColor) {
        return new ExamQuestionSelectVo(id, name, questionId, type, itemStateColor);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamQuestionSelectVo)) {
            return false;
        }
        ExamQuestionSelectVo examQuestionSelectVo = (ExamQuestionSelectVo) other;
        return Intrinsics.areEqual(this.id, examQuestionSelectVo.id) && Intrinsics.areEqual(this.name, examQuestionSelectVo.name) && Intrinsics.areEqual(this.questionId, examQuestionSelectVo.questionId) && Intrinsics.areEqual(this.type, examQuestionSelectVo.type) && this.itemStateColor == examQuestionSelectVo.itemStateColor;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    public final int getItemStateColor() {
        return this.itemStateColor;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getQuestionId() {
        return this.questionId;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.name;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.questionId;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.type;
        return ((iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31) + this.itemStateColor;
    }

    public final void setItemStateColor(int i2) {
        this.itemStateColor = i2;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }

    @NotNull
    public String toString() {
        return "ExamQuestionSelectVo(id=" + this.id + ", name=" + this.name + ", questionId=" + this.questionId + ", type=" + this.type + ", itemStateColor=" + this.itemStateColor + ")";
    }

    public ExamQuestionSelectVo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i2) {
        this.id = str;
        this.name = str2;
        this.questionId = str3;
        this.type = str4;
        this.itemStateColor = i2;
    }

    public /* synthetic */ ExamQuestionSelectVo(String str, String str2, String str3, String str4, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? "" : str2, (i3 & 4) != 0 ? "" : str3, (i3 & 8) == 0 ? str4 : "", (i3 & 16) != 0 ? 0 : i2);
    }
}
