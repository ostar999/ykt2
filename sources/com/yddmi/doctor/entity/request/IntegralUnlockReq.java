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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \"2\u00020\u0001:\u0002!\"B5\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB\u001f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J)\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001J!\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006#"}, d2 = {"Lcom/yddmi/doctor/entity/request/IntegralUnlockReq;", "", "seen1", "", "practiceListId", "", "shareIntegral", "userName", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;ILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;ILjava/lang/String;)V", "getPracticeListId", "()Ljava/lang/String;", "getShareIntegral", "()I", "getUserName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class IntegralUnlockReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String practiceListId;
    private final int shareIntegral;

    @NotNull
    private final String userName;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/IntegralUnlockReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/IntegralUnlockReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<IntegralUnlockReq> serializer() {
            return IntegralUnlockReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ IntegralUnlockReq(int i2, String str, int i3, String str2, SerializationConstructorMarker serializationConstructorMarker) {
        if (7 != (i2 & 7)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 7, IntegralUnlockReq$$serializer.INSTANCE.getDescriptor());
        }
        this.practiceListId = str;
        this.shareIntegral = i3;
        this.userName = str2;
    }

    public static /* synthetic */ IntegralUnlockReq copy$default(IntegralUnlockReq integralUnlockReq, String str, int i2, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = integralUnlockReq.practiceListId;
        }
        if ((i3 & 2) != 0) {
            i2 = integralUnlockReq.shareIntegral;
        }
        if ((i3 & 4) != 0) {
            str2 = integralUnlockReq.userName;
        }
        return integralUnlockReq.copy(str, i2, str2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull IntegralUnlockReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.practiceListId);
        output.encodeIntElement(serialDesc, 1, self.shareIntegral);
        output.encodeStringElement(serialDesc, 2, self.userName);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getPracticeListId() {
        return this.practiceListId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getShareIntegral() {
        return this.shareIntegral;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getUserName() {
        return this.userName;
    }

    @NotNull
    public final IntegralUnlockReq copy(@Nullable String practiceListId, int shareIntegral, @NotNull String userName) {
        Intrinsics.checkNotNullParameter(userName, "userName");
        return new IntegralUnlockReq(practiceListId, shareIntegral, userName);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntegralUnlockReq)) {
            return false;
        }
        IntegralUnlockReq integralUnlockReq = (IntegralUnlockReq) other;
        return Intrinsics.areEqual(this.practiceListId, integralUnlockReq.practiceListId) && this.shareIntegral == integralUnlockReq.shareIntegral && Intrinsics.areEqual(this.userName, integralUnlockReq.userName);
    }

    @Nullable
    public final String getPracticeListId() {
        return this.practiceListId;
    }

    public final int getShareIntegral() {
        return this.shareIntegral;
    }

    @NotNull
    public final String getUserName() {
        return this.userName;
    }

    public int hashCode() {
        String str = this.practiceListId;
        return ((((str == null ? 0 : str.hashCode()) * 31) + this.shareIntegral) * 31) + this.userName.hashCode();
    }

    @NotNull
    public String toString() {
        return "IntegralUnlockReq(practiceListId=" + this.practiceListId + ", shareIntegral=" + this.shareIntegral + ", userName=" + this.userName + ")";
    }

    public IntegralUnlockReq(@Nullable String str, int i2, @NotNull String userName) {
        Intrinsics.checkNotNullParameter(userName, "userName");
        this.practiceListId = str;
        this.shareIntegral = i2;
        this.userName = userName;
    }
}
