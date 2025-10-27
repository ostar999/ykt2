package com.yddmi.doctor.entity.result;

import com.google.gson.annotations.SerializedName;
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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b+\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 F2\u00020\u0001:\u0002EFB\u008b\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014B\u008f\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010¢\u0006\u0002\u0010\u0015J\u000b\u0010,\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010.\u001a\u00020\u0010HÆ\u0003J\t\u0010/\u001a\u00020\u0010HÆ\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u0010\u00101\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u0010\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u0010\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00104\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u00105\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\u0010\u00106\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0019J\t\u00107\u001a\u00020\u0003HÆ\u0003J\u0098\u0001\u00108\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010HÆ\u0001¢\u0006\u0002\u00109J\u0013\u0010:\u001a\u00020\u00102\b\u0010;\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010<\u001a\u00020\u0003HÖ\u0001J\t\u0010=\u001a\u00020\u0005HÖ\u0001J!\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00002\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DHÇ\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001b\u0010\u0019R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001c\u0010\u0019R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\u0011\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001e\"\u0004\b\"\u0010 R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0017\"\u0004\b$\u0010%R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001a\u0010\f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b(\u0010\u0019R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b)\u0010\u0019R\u0018\u0010\n\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0017R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b+\u0010\u0019¨\u0006G"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "", "seen1", "", "answer", "", "askId", "askTypeId", "id", "patientId", "question", "type", "operateId", "kind", "itemTitle", "itemReply", "", "itemSelect", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;ZZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;ZZ)V", "getAnswer", "()Ljava/lang/String;", "getAskId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAskTypeId", "getId", "getItemReply", "()Z", "setItemReply", "(Z)V", "getItemSelect", "setItemSelect", "getItemTitle", "setItemTitle", "(Ljava/lang/String;)V", "getKind", "()I", "getOperateId", "getPatientId", "getQuestion", "getType", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/String;ZZ)Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryAskInfo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("answer")
    @Nullable
    private final String answer;

    @SerializedName("askId")
    @Nullable
    private final Integer askId;

    @SerializedName("askTypeId")
    @Nullable
    private final Integer askTypeId;

    @SerializedName("id")
    @Nullable
    private final Integer id;
    private transient boolean itemReply;
    private transient boolean itemSelect;

    @Nullable
    private transient String itemTitle;

    @SerializedName("kind")
    private final int kind;

    @SerializedName("operateId")
    @Nullable
    private final Integer operateId;

    @SerializedName("patientId")
    @Nullable
    private final Integer patientId;

    @SerializedName("question")
    @Nullable
    private final String question;

    @SerializedName("type")
    @Nullable
    private final Integer type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryAskInfo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryAskInfo> serializer() {
            return InquiryAskInfo$$serializer.INSTANCE;
        }
    }

    public InquiryAskInfo() {
        this((String) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, 0, (String) null, false, false, 4095, (DefaultConstructorMarker) null);
    }

    public InquiryAskInfo(@Nullable String str, @Nullable Integer num, @Nullable Integer num2, @Nullable Integer num3, @Nullable Integer num4, @Nullable String str2, @Nullable Integer num5, @Nullable Integer num6, int i2, @Nullable String str3, boolean z2, boolean z3) {
        this.answer = str;
        this.askId = num;
        this.askTypeId = num2;
        this.id = num3;
        this.patientId = num4;
        this.question = str2;
        this.type = num5;
        this.operateId = num6;
        this.kind = i2;
        this.itemTitle = str3;
        this.itemReply = z2;
        this.itemSelect = z3;
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryAskInfo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.answer, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.answer);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.askId) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.askId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || (num2 = self.askTypeId) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 2, IntSerializer.INSTANCE, self.askTypeId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num3 = self.id) == null || num3.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num4 = self.patientId) == null || num4.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.question, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.question);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num5 = self.type) == null || num5.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num6 = self.operateId) == null || num6.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.operateId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.kind != 0) {
            output.encodeIntElement(serialDesc, 8, self.kind);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.itemTitle, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.itemTitle);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.itemReply) {
            output.encodeBooleanElement(serialDesc, 10, self.itemReply);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.itemSelect) {
            output.encodeBooleanElement(serialDesc, 11, self.itemSelect);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getAnswer() {
        return this.answer;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getItemTitle() {
        return this.itemTitle;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getItemReply() {
        return this.itemReply;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean getItemSelect() {
        return this.itemSelect;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getAskId() {
        return this.askId;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final Integer getAskTypeId() {
        return this.askTypeId;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getQuestion() {
        return this.question;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final Integer getOperateId() {
        return this.operateId;
    }

    /* renamed from: component9, reason: from getter */
    public final int getKind() {
        return this.kind;
    }

    @NotNull
    public final InquiryAskInfo copy(@Nullable String answer, @Nullable Integer askId, @Nullable Integer askTypeId, @Nullable Integer id, @Nullable Integer patientId, @Nullable String question, @Nullable Integer type, @Nullable Integer operateId, int kind, @Nullable String itemTitle, boolean itemReply, boolean itemSelect) {
        return new InquiryAskInfo(answer, askId, askTypeId, id, patientId, question, type, operateId, kind, itemTitle, itemReply, itemSelect);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryAskInfo)) {
            return false;
        }
        InquiryAskInfo inquiryAskInfo = (InquiryAskInfo) other;
        return Intrinsics.areEqual(this.answer, inquiryAskInfo.answer) && Intrinsics.areEqual(this.askId, inquiryAskInfo.askId) && Intrinsics.areEqual(this.askTypeId, inquiryAskInfo.askTypeId) && Intrinsics.areEqual(this.id, inquiryAskInfo.id) && Intrinsics.areEqual(this.patientId, inquiryAskInfo.patientId) && Intrinsics.areEqual(this.question, inquiryAskInfo.question) && Intrinsics.areEqual(this.type, inquiryAskInfo.type) && Intrinsics.areEqual(this.operateId, inquiryAskInfo.operateId) && this.kind == inquiryAskInfo.kind && Intrinsics.areEqual(this.itemTitle, inquiryAskInfo.itemTitle) && this.itemReply == inquiryAskInfo.itemReply && this.itemSelect == inquiryAskInfo.itemSelect;
    }

    @Nullable
    public final String getAnswer() {
        return this.answer;
    }

    @Nullable
    public final Integer getAskId() {
        return this.askId;
    }

    @Nullable
    public final Integer getAskTypeId() {
        return this.askTypeId;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    public final boolean getItemReply() {
        return this.itemReply;
    }

    public final boolean getItemSelect() {
        return this.itemSelect;
    }

    @Nullable
    public final String getItemTitle() {
        return this.itemTitle;
    }

    public final int getKind() {
        return this.kind;
    }

    @Nullable
    public final Integer getOperateId() {
        return this.operateId;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final String getQuestion() {
        return this.question;
    }

    @Nullable
    public final Integer getType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.answer;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.askId;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.askTypeId;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.id;
        int iHashCode4 = (iHashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.patientId;
        int iHashCode5 = (iHashCode4 + (num4 == null ? 0 : num4.hashCode())) * 31;
        String str2 = this.question;
        int iHashCode6 = (iHashCode5 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num5 = this.type;
        int iHashCode7 = (iHashCode6 + (num5 == null ? 0 : num5.hashCode())) * 31;
        Integer num6 = this.operateId;
        int iHashCode8 = (((iHashCode7 + (num6 == null ? 0 : num6.hashCode())) * 31) + this.kind) * 31;
        String str3 = this.itemTitle;
        int iHashCode9 = (iHashCode8 + (str3 != null ? str3.hashCode() : 0)) * 31;
        boolean z2 = this.itemReply;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode9 + i2) * 31;
        boolean z3 = this.itemSelect;
        return i3 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final void setItemReply(boolean z2) {
        this.itemReply = z2;
    }

    public final void setItemSelect(boolean z2) {
        this.itemSelect = z2;
    }

    public final void setItemTitle(@Nullable String str) {
        this.itemTitle = str;
    }

    @NotNull
    public String toString() {
        return "InquiryAskInfo(answer=" + this.answer + ", askId=" + this.askId + ", askTypeId=" + this.askTypeId + ", id=" + this.id + ", patientId=" + this.patientId + ", question=" + this.question + ", type=" + this.type + ", operateId=" + this.operateId + ", kind=" + this.kind + ", itemTitle=" + this.itemTitle + ", itemReply=" + this.itemReply + ", itemSelect=" + this.itemSelect + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryAskInfo(int i2, String str, Integer num, Integer num2, Integer num3, Integer num4, String str2, Integer num5, Integer num6, int i3, String str3, boolean z2, boolean z3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryAskInfo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.answer = "";
        } else {
            this.answer = str;
        }
        if ((i2 & 2) == 0) {
            this.askId = 0;
        } else {
            this.askId = num;
        }
        if ((i2 & 4) == 0) {
            this.askTypeId = 0;
        } else {
            this.askTypeId = num2;
        }
        if ((i2 & 8) == 0) {
            this.id = 0;
        } else {
            this.id = num3;
        }
        if ((i2 & 16) == 0) {
            this.patientId = 0;
        } else {
            this.patientId = num4;
        }
        if ((i2 & 32) == 0) {
            this.question = "";
        } else {
            this.question = str2;
        }
        if ((i2 & 64) == 0) {
            this.type = 0;
        } else {
            this.type = num5;
        }
        if ((i2 & 128) == 0) {
            this.operateId = 0;
        } else {
            this.operateId = num6;
        }
        if ((i2 & 256) == 0) {
            this.kind = 0;
        } else {
            this.kind = i3;
        }
        if ((i2 & 512) == 0) {
            this.itemTitle = "";
        } else {
            this.itemTitle = str3;
        }
        if ((i2 & 1024) == 0) {
            this.itemReply = false;
        } else {
            this.itemReply = z2;
        }
        if ((i2 & 2048) == 0) {
            this.itemSelect = false;
        } else {
            this.itemSelect = z3;
        }
    }

    public /* synthetic */ InquiryAskInfo(String str, Integer num, Integer num2, Integer num3, Integer num4, String str2, Integer num5, Integer num6, int i2, String str3, boolean z2, boolean z3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? 0 : num, (i3 & 4) != 0 ? 0 : num2, (i3 & 8) != 0 ? 0 : num3, (i3 & 16) != 0 ? 0 : num4, (i3 & 32) != 0 ? "" : str2, (i3 & 64) != 0 ? 0 : num5, (i3 & 128) != 0 ? 0 : num6, (i3 & 256) != 0 ? 0 : i2, (i3 & 512) == 0 ? str3 : "", (i3 & 1024) != 0 ? false : z2, (i3 & 2048) == 0 ? z3 : false);
    }
}
