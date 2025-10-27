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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 '2\u00020\u0001:\u0002&'B3\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB!\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J)\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0006HÖ\u0001J!\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%HÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000f¨\u0006("}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryCheckReqList;", "", "seen1", "", "operateId", "result", "", "type", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;I)V", "getOperateId", "()I", "setOperateId", "(I)V", "getResult", "()Ljava/lang/String;", "setResult", "(Ljava/lang/String;)V", "getType", "setType", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryCheckReqList {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int operateId;

    @Nullable
    private String result;
    private int type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/InquiryCheckReqList$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/InquiryCheckReqList;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryCheckReqList> serializer() {
            return InquiryCheckReqList$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryCheckReqList(int i2, int i3, String str, int i4, SerializationConstructorMarker serializationConstructorMarker) {
        if (5 != (i2 & 5)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 5, InquiryCheckReqList$$serializer.INSTANCE.getDescriptor());
        }
        this.operateId = i3;
        if ((i2 & 2) == 0) {
            this.result = "";
        } else {
            this.result = str;
        }
        this.type = i4;
    }

    public static /* synthetic */ InquiryCheckReqList copy$default(InquiryCheckReqList inquiryCheckReqList, int i2, String str, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = inquiryCheckReqList.operateId;
        }
        if ((i4 & 2) != 0) {
            str = inquiryCheckReqList.result;
        }
        if ((i4 & 4) != 0) {
            i3 = inquiryCheckReqList.type;
        }
        return inquiryCheckReqList.copy(i2, str, i3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryCheckReqList self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.operateId);
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.result);
        }
        output.encodeIntElement(serialDesc, 2, self.type);
    }

    /* renamed from: component1, reason: from getter */
    public final int getOperateId() {
        return this.operateId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    /* renamed from: component3, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @NotNull
    public final InquiryCheckReqList copy(int operateId, @Nullable String result, int type) {
        return new InquiryCheckReqList(operateId, result, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryCheckReqList)) {
            return false;
        }
        InquiryCheckReqList inquiryCheckReqList = (InquiryCheckReqList) other;
        return this.operateId == inquiryCheckReqList.operateId && Intrinsics.areEqual(this.result, inquiryCheckReqList.result) && this.type == inquiryCheckReqList.type;
    }

    public final int getOperateId() {
        return this.operateId;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        int i2 = this.operateId * 31;
        String str = this.result;
        return ((i2 + (str == null ? 0 : str.hashCode())) * 31) + this.type;
    }

    public final void setOperateId(int i2) {
        this.operateId = i2;
    }

    public final void setResult(@Nullable String str) {
        this.result = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    @NotNull
    public String toString() {
        return "InquiryCheckReqList(operateId=" + this.operateId + ", result=" + this.result + ", type=" + this.type + ")";
    }

    public InquiryCheckReqList(int i2, @Nullable String str, int i3) {
        this.operateId = i2;
        this.result = str;
        this.type = i3;
    }

    public /* synthetic */ InquiryCheckReqList(int i2, String str, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i4 & 2) != 0 ? "" : str, i3);
    }
}
