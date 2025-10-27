package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 '2\u00020\u0001:\u0002&'B5\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÆ\u0003J'\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u0006HÖ\u0001J!\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%HÇ\u0001R\u001a\u0010\u0007\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000f¨\u0006("}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeMeAuditSubmit;", "", "seen1", "", "orgId", AliyunLogCommon.TERMINAL_TYPE, "", "name", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getOrgId", "()I", "setOrgId", "(I)V", "getPhone", "setPhone", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeMeAuditSubmit {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String name;
    private int orgId;

    @NotNull
    private String phone;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeMeAuditSubmit$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/HomeMeAuditSubmit;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeMeAuditSubmit> serializer() {
            return HomeMeAuditSubmit$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeMeAuditSubmit(int i2, int i3, String str, String str2, SerializationConstructorMarker serializationConstructorMarker) {
        if (7 != (i2 & 7)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 7, HomeMeAuditSubmit$$serializer.INSTANCE.getDescriptor());
        }
        this.orgId = i3;
        this.phone = str;
        this.name = str2;
    }

    public static /* synthetic */ HomeMeAuditSubmit copy$default(HomeMeAuditSubmit homeMeAuditSubmit, int i2, String str, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = homeMeAuditSubmit.orgId;
        }
        if ((i3 & 2) != 0) {
            str = homeMeAuditSubmit.phone;
        }
        if ((i3 & 4) != 0) {
            str2 = homeMeAuditSubmit.name;
        }
        return homeMeAuditSubmit.copy(i2, str, str2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeMeAuditSubmit self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.orgId);
        output.encodeStringElement(serialDesc, 1, self.phone);
        output.encodeStringElement(serialDesc, 2, self.name);
    }

    /* renamed from: component1, reason: from getter */
    public final int getOrgId() {
        return this.orgId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final HomeMeAuditSubmit copy(int orgId, @NotNull String phone, @NotNull String name) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(name, "name");
        return new HomeMeAuditSubmit(orgId, phone, name);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeMeAuditSubmit)) {
            return false;
        }
        HomeMeAuditSubmit homeMeAuditSubmit = (HomeMeAuditSubmit) other;
        return this.orgId == homeMeAuditSubmit.orgId && Intrinsics.areEqual(this.phone, homeMeAuditSubmit.phone) && Intrinsics.areEqual(this.name, homeMeAuditSubmit.name);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final int getOrgId() {
        return this.orgId;
    }

    @NotNull
    public final String getPhone() {
        return this.phone;
    }

    public int hashCode() {
        return (((this.orgId * 31) + this.phone.hashCode()) * 31) + this.name.hashCode();
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final void setOrgId(int i2) {
        this.orgId = i2;
    }

    public final void setPhone(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.phone = str;
    }

    @NotNull
    public String toString() {
        return "HomeMeAuditSubmit(orgId=" + this.orgId + ", phone=" + this.phone + ", name=" + this.name + ")";
    }

    public HomeMeAuditSubmit(int i2, @NotNull String phone, @NotNull String name) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(name, "name");
        this.orgId = i2;
        this.phone = phone;
        this.name = name;
    }
}
