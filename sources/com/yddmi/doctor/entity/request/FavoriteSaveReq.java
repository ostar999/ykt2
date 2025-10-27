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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 %2\u00020\u0001:\u0002$%B3\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB#\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J'\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u0007HÖ\u0001J!\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#HÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\f\"\u0004\b\u000f\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/entity/request/FavoriteSaveReq;", "", "seen1", "", "isFavorite", "isMastered", "medicalKnowledgeId", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IILjava/lang/String;)V", "()I", "setFavorite", "(I)V", "setMastered", "getMedicalKnowledgeId", "()Ljava/lang/String;", "setMedicalKnowledgeId", "(Ljava/lang/String;)V", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class FavoriteSaveReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int isFavorite;
    private int isMastered;

    @NotNull
    private String medicalKnowledgeId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/FavoriteSaveReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/FavoriteSaveReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<FavoriteSaveReq> serializer() {
            return FavoriteSaveReq$$serializer.INSTANCE;
        }
    }

    public FavoriteSaveReq() {
        this(0, 0, (String) null, 7, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ FavoriteSaveReq(int i2, int i3, int i4, String str, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, FavoriteSaveReq$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.isFavorite = -1;
        } else {
            this.isFavorite = i3;
        }
        if ((i2 & 2) == 0) {
            this.isMastered = -1;
        } else {
            this.isMastered = i4;
        }
        if ((i2 & 4) == 0) {
            this.medicalKnowledgeId = "";
        } else {
            this.medicalKnowledgeId = str;
        }
    }

    public static /* synthetic */ FavoriteSaveReq copy$default(FavoriteSaveReq favoriteSaveReq, int i2, int i3, String str, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = favoriteSaveReq.isFavorite;
        }
        if ((i4 & 2) != 0) {
            i3 = favoriteSaveReq.isMastered;
        }
        if ((i4 & 4) != 0) {
            str = favoriteSaveReq.medicalKnowledgeId;
        }
        return favoriteSaveReq.copy(i2, i3, str);
    }

    @JvmStatic
    public static final void write$Self(@NotNull FavoriteSaveReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.isFavorite != -1) {
            output.encodeIntElement(serialDesc, 0, self.isFavorite);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.isMastered != -1) {
            output.encodeIntElement(serialDesc, 1, self.isMastered);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.medicalKnowledgeId, "")) {
            output.encodeStringElement(serialDesc, 2, self.medicalKnowledgeId);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getIsFavorite() {
        return this.isFavorite;
    }

    /* renamed from: component2, reason: from getter */
    public final int getIsMastered() {
        return this.isMastered;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    @NotNull
    public final FavoriteSaveReq copy(int isFavorite, int isMastered, @NotNull String medicalKnowledgeId) {
        Intrinsics.checkNotNullParameter(medicalKnowledgeId, "medicalKnowledgeId");
        return new FavoriteSaveReq(isFavorite, isMastered, medicalKnowledgeId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FavoriteSaveReq)) {
            return false;
        }
        FavoriteSaveReq favoriteSaveReq = (FavoriteSaveReq) other;
        return this.isFavorite == favoriteSaveReq.isFavorite && this.isMastered == favoriteSaveReq.isMastered && Intrinsics.areEqual(this.medicalKnowledgeId, favoriteSaveReq.medicalKnowledgeId);
    }

    @NotNull
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    public int hashCode() {
        return (((this.isFavorite * 31) + this.isMastered) * 31) + this.medicalKnowledgeId.hashCode();
    }

    public final int isFavorite() {
        return this.isFavorite;
    }

    public final int isMastered() {
        return this.isMastered;
    }

    public final void setFavorite(int i2) {
        this.isFavorite = i2;
    }

    public final void setMastered(int i2) {
        this.isMastered = i2;
    }

    public final void setMedicalKnowledgeId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.medicalKnowledgeId = str;
    }

    @NotNull
    public String toString() {
        return "FavoriteSaveReq(isFavorite=" + this.isFavorite + ", isMastered=" + this.isMastered + ", medicalKnowledgeId=" + this.medicalKnowledgeId + ")";
    }

    public FavoriteSaveReq(int i2, int i3, @NotNull String medicalKnowledgeId) {
        Intrinsics.checkNotNullParameter(medicalKnowledgeId, "medicalKnowledgeId");
        this.isFavorite = i2;
        this.isMastered = i3;
        this.medicalKnowledgeId = medicalKnowledgeId;
    }

    public /* synthetic */ FavoriteSaveReq(int i2, int i3, String str, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? -1 : i2, (i4 & 2) != 0 ? -1 : i3, (i4 & 4) != 0 ? "" : str);
    }
}
