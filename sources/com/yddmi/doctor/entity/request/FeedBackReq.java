package com.yddmi.doctor.entity.request;

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

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 '2\u00020\u0001:\u0002&'B5\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB#\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J'\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0005HÖ\u0001J!\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%HÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000f¨\u0006("}, d2 = {"Lcom/yddmi/doctor/entity/request/FeedBackReq;", "", "seen1", "", "description", "", "feedbackType", "medicalKnowledgeId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;ILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getFeedbackType", "()I", "setFeedbackType", "(I)V", "getMedicalKnowledgeId", "setMedicalKnowledgeId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class FeedBackReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String description;
    private int feedbackType;

    @NotNull
    private String medicalKnowledgeId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/FeedBackReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/FeedBackReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<FeedBackReq> serializer() {
            return FeedBackReq$$serializer.INSTANCE;
        }
    }

    public FeedBackReq() {
        this((String) null, 0, (String) null, 7, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ FeedBackReq(int i2, String str, int i3, String str2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, FeedBackReq$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.description = "";
        } else {
            this.description = str;
        }
        if ((i2 & 2) == 0) {
            this.feedbackType = -1;
        } else {
            this.feedbackType = i3;
        }
        if ((i2 & 4) == 0) {
            this.medicalKnowledgeId = "";
        } else {
            this.medicalKnowledgeId = str2;
        }
    }

    public static /* synthetic */ FeedBackReq copy$default(FeedBackReq feedBackReq, String str, int i2, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = feedBackReq.description;
        }
        if ((i3 & 2) != 0) {
            i2 = feedBackReq.feedbackType;
        }
        if ((i3 & 4) != 0) {
            str2 = feedBackReq.medicalKnowledgeId;
        }
        return feedBackReq.copy(str, i2, str2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull FeedBackReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeStringElement(serialDesc, 0, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.feedbackType != -1) {
            output.encodeIntElement(serialDesc, 1, self.feedbackType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.medicalKnowledgeId, "")) {
            output.encodeStringElement(serialDesc, 2, self.medicalKnowledgeId);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component2, reason: from getter */
    public final int getFeedbackType() {
        return this.feedbackType;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    @NotNull
    public final FeedBackReq copy(@NotNull String description, int feedbackType, @NotNull String medicalKnowledgeId) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(medicalKnowledgeId, "medicalKnowledgeId");
        return new FeedBackReq(description, feedbackType, medicalKnowledgeId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FeedBackReq)) {
            return false;
        }
        FeedBackReq feedBackReq = (FeedBackReq) other;
        return Intrinsics.areEqual(this.description, feedBackReq.description) && this.feedbackType == feedBackReq.feedbackType && Intrinsics.areEqual(this.medicalKnowledgeId, feedBackReq.medicalKnowledgeId);
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    public final int getFeedbackType() {
        return this.feedbackType;
    }

    @NotNull
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    public int hashCode() {
        return (((this.description.hashCode() * 31) + this.feedbackType) * 31) + this.medicalKnowledgeId.hashCode();
    }

    public final void setDescription(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.description = str;
    }

    public final void setFeedbackType(int i2) {
        this.feedbackType = i2;
    }

    public final void setMedicalKnowledgeId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.medicalKnowledgeId = str;
    }

    @NotNull
    public String toString() {
        return "FeedBackReq(description=" + this.description + ", feedbackType=" + this.feedbackType + ", medicalKnowledgeId=" + this.medicalKnowledgeId + ")";
    }

    public FeedBackReq(@NotNull String description, int i2, @NotNull String medicalKnowledgeId) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(medicalKnowledgeId, "medicalKnowledgeId");
        this.description = description;
        this.feedbackType = i2;
        this.medicalKnowledgeId = medicalKnowledgeId;
    }

    public /* synthetic */ FeedBackReq(String str, int i2, String str2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? -1 : i2, (i3 & 4) != 0 ? "" : str2);
    }
}
