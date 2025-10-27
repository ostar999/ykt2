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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u001f2\u00020\u0001:\u0002\u001e\u001fB1\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tB\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J!\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dHÇ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\f¨\u0006 "}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryDiseaseUpdateReq;", "", "seen1", "", "isMain", "isIdentity", "id", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(III)V", "getId", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryDiseaseUpdateReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int id;
    private final int isIdentity;
    private final int isMain;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryDiseaseUpdateReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquiryDiseaseUpdateReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryDiseaseUpdateReq> serializer() {
            return InquiryDiseaseUpdateReq$$serializer.INSTANCE;
        }
    }

    public InquiryDiseaseUpdateReq(int i2, int i3, int i4) {
        this.isMain = i2;
        this.isIdentity = i3;
        this.id = i4;
    }

    public static /* synthetic */ InquiryDiseaseUpdateReq copy$default(InquiryDiseaseUpdateReq inquiryDiseaseUpdateReq, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = inquiryDiseaseUpdateReq.isMain;
        }
        if ((i5 & 2) != 0) {
            i3 = inquiryDiseaseUpdateReq.isIdentity;
        }
        if ((i5 & 4) != 0) {
            i4 = inquiryDiseaseUpdateReq.id;
        }
        return inquiryDiseaseUpdateReq.copy(i2, i3, i4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryDiseaseUpdateReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.isMain);
        output.encodeIntElement(serialDesc, 1, self.isIdentity);
        output.encodeIntElement(serialDesc, 2, self.id);
    }

    /* renamed from: component1, reason: from getter */
    public final int getIsMain() {
        return this.isMain;
    }

    /* renamed from: component2, reason: from getter */
    public final int getIsIdentity() {
        return this.isIdentity;
    }

    /* renamed from: component3, reason: from getter */
    public final int getId() {
        return this.id;
    }

    @NotNull
    public final InquiryDiseaseUpdateReq copy(int isMain, int isIdentity, int id) {
        return new InquiryDiseaseUpdateReq(isMain, isIdentity, id);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryDiseaseUpdateReq)) {
            return false;
        }
        InquiryDiseaseUpdateReq inquiryDiseaseUpdateReq = (InquiryDiseaseUpdateReq) other;
        return this.isMain == inquiryDiseaseUpdateReq.isMain && this.isIdentity == inquiryDiseaseUpdateReq.isIdentity && this.id == inquiryDiseaseUpdateReq.id;
    }

    public final int getId() {
        return this.id;
    }

    public int hashCode() {
        return (((this.isMain * 31) + this.isIdentity) * 31) + this.id;
    }

    public final int isIdentity() {
        return this.isIdentity;
    }

    public final int isMain() {
        return this.isMain;
    }

    @NotNull
    public String toString() {
        return "InquiryDiseaseUpdateReq(isMain=" + this.isMain + ", isIdentity=" + this.isIdentity + ", id=" + this.id + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryDiseaseUpdateReq(int i2, int i3, int i4, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if (7 != (i2 & 7)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 7, InquiryDiseaseUpdateReq$$serializer.INSTANCE.getDescriptor());
        }
        this.isMain = i3;
        this.isIdentity = i4;
        this.id = i5;
    }
}
