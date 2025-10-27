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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 72\u00020\u0001:\u000267By\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011Bw\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0012J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J}\u0010)\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010-\u001a\u00020\u0003HÖ\u0001J\t\u0010.\u001a\u00020\u0005HÖ\u0001J!\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u00002\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014¨\u00068"}, d2 = {"Lcom/yddmi/doctor/entity/result/FeedBackPageItem;", "", "seen1", "", "categoryId", "", "categoryName", "createTime", "description", "feedBackId", "feedbackType", "medicalKnowledgeId", "name", "status", "userId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getCategoryId", "()Ljava/lang/String;", "getCategoryName", "getCreateTime", "getDescription", "getFeedBackId", "getFeedbackType", "()I", "getMedicalKnowledgeId", "getName", "getStatus", "getUserId", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class FeedBackPageItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String categoryId;

    @Nullable
    private final String categoryName;

    @Nullable
    private final String createTime;

    @Nullable
    private final String description;

    @Nullable
    private final String feedBackId;
    private final int feedbackType;

    @Nullable
    private final String medicalKnowledgeId;

    @Nullable
    private final String name;
    private final int status;

    @Nullable
    private final String userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/FeedBackPageItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/FeedBackPageItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<FeedBackPageItem> serializer() {
            return FeedBackPageItem$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ FeedBackPageItem(int i2, String str, String str2, String str3, String str4, String str5, int i3, String str6, String str7, int i4, String str8, SerializationConstructorMarker serializationConstructorMarker) {
        if (512 != (i2 & 512)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 512, FeedBackPageItem$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.categoryId = "";
        } else {
            this.categoryId = str;
        }
        if ((i2 & 2) == 0) {
            this.categoryName = "";
        } else {
            this.categoryName = str2;
        }
        if ((i2 & 4) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str3;
        }
        if ((i2 & 8) == 0) {
            this.description = "";
        } else {
            this.description = str4;
        }
        if ((i2 & 16) == 0) {
            this.feedBackId = "";
        } else {
            this.feedBackId = str5;
        }
        if ((i2 & 32) == 0) {
            this.feedbackType = 0;
        } else {
            this.feedbackType = i3;
        }
        if ((i2 & 64) == 0) {
            this.medicalKnowledgeId = "";
        } else {
            this.medicalKnowledgeId = str6;
        }
        if ((i2 & 128) == 0) {
            this.name = "";
        } else {
            this.name = str7;
        }
        if ((i2 & 256) == 0) {
            this.status = -1;
        } else {
            this.status = i4;
        }
        this.userId = str8;
    }

    @JvmStatic
    public static final void write$Self(@NotNull FeedBackPageItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.categoryId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.categoryName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.categoryName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.feedBackId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.feedBackId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.feedbackType != 0) {
            output.encodeIntElement(serialDesc, 5, self.feedbackType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.medicalKnowledgeId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.medicalKnowledgeId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.status != -1) {
            output.encodeIntElement(serialDesc, 8, self.status);
        }
        output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.userId);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCategoryName() {
        return this.categoryName;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getFeedBackId() {
        return this.feedBackId;
    }

    /* renamed from: component6, reason: from getter */
    public final int getFeedbackType() {
        return this.feedbackType;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component9, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @NotNull
    public final FeedBackPageItem copy(@Nullable String categoryId, @Nullable String categoryName, @Nullable String createTime, @Nullable String description, @Nullable String feedBackId, int feedbackType, @Nullable String medicalKnowledgeId, @Nullable String name, int status, @Nullable String userId) {
        return new FeedBackPageItem(categoryId, categoryName, createTime, description, feedBackId, feedbackType, medicalKnowledgeId, name, status, userId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FeedBackPageItem)) {
            return false;
        }
        FeedBackPageItem feedBackPageItem = (FeedBackPageItem) other;
        return Intrinsics.areEqual(this.categoryId, feedBackPageItem.categoryId) && Intrinsics.areEqual(this.categoryName, feedBackPageItem.categoryName) && Intrinsics.areEqual(this.createTime, feedBackPageItem.createTime) && Intrinsics.areEqual(this.description, feedBackPageItem.description) && Intrinsics.areEqual(this.feedBackId, feedBackPageItem.feedBackId) && this.feedbackType == feedBackPageItem.feedbackType && Intrinsics.areEqual(this.medicalKnowledgeId, feedBackPageItem.medicalKnowledgeId) && Intrinsics.areEqual(this.name, feedBackPageItem.name) && this.status == feedBackPageItem.status && Intrinsics.areEqual(this.userId, feedBackPageItem.userId);
    }

    @Nullable
    public final String getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final String getCategoryName() {
        return this.categoryName;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getFeedBackId() {
        return this.feedBackId;
    }

    public final int getFeedbackType() {
        return this.feedbackType;
    }

    @Nullable
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    public final int getStatus() {
        return this.status;
    }

    @Nullable
    public final String getUserId() {
        return this.userId;
    }

    public int hashCode() {
        String str = this.categoryId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.categoryName;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.createTime;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.description;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.feedBackId;
        int iHashCode5 = (((iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31) + this.feedbackType) * 31;
        String str6 = this.medicalKnowledgeId;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.name;
        int iHashCode7 = (((iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31) + this.status) * 31;
        String str8 = this.userId;
        return iHashCode7 + (str8 != null ? str8.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "FeedBackPageItem(categoryId=" + this.categoryId + ", categoryName=" + this.categoryName + ", createTime=" + this.createTime + ", description=" + this.description + ", feedBackId=" + this.feedBackId + ", feedbackType=" + this.feedbackType + ", medicalKnowledgeId=" + this.medicalKnowledgeId + ", name=" + this.name + ", status=" + this.status + ", userId=" + this.userId + ")";
    }

    public FeedBackPageItem(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, @Nullable String str6, @Nullable String str7, int i3, @Nullable String str8) {
        this.categoryId = str;
        this.categoryName = str2;
        this.createTime = str3;
        this.description = str4;
        this.feedBackId = str5;
        this.feedbackType = i2;
        this.medicalKnowledgeId = str6;
        this.name = str7;
        this.status = i3;
        this.userId = str8;
    }

    public /* synthetic */ FeedBackPageItem(String str, String str2, String str3, String str4, String str5, int i2, String str6, String str7, int i3, String str8, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? "" : str, (i4 & 2) != 0 ? "" : str2, (i4 & 4) != 0 ? "" : str3, (i4 & 8) != 0 ? "" : str4, (i4 & 16) != 0 ? "" : str5, (i4 & 32) != 0 ? 0 : i2, (i4 & 64) != 0 ? "" : str6, (i4 & 128) != 0 ? "" : str7, (i4 & 256) != 0 ? -1 : i3, str8);
    }
}
