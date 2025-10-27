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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 %2\u00020\u0001:\u0002$%B?\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB)\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J3\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001J!\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#HÇ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000e¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/entity/request/IntegralAcquireReq;", "", "seen1", "", "practiceListId", "", "nickName", "userName", "shareType", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getNickName", "()Ljava/lang/String;", "getPracticeListId", "getShareType", "()I", "getUserName", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class IntegralAcquireReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String nickName;

    @Nullable
    private final String practiceListId;
    private final int shareType;

    @NotNull
    private final String userName;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/IntegralAcquireReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/IntegralAcquireReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<IntegralAcquireReq> serializer() {
            return IntegralAcquireReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ IntegralAcquireReq(int i2, String str, String str2, String str3, int i3, SerializationConstructorMarker serializationConstructorMarker) {
        if (14 != (i2 & 14)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 14, IntegralAcquireReq$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.practiceListId = "";
        } else {
            this.practiceListId = str;
        }
        this.nickName = str2;
        this.userName = str3;
        this.shareType = i3;
    }

    public static /* synthetic */ IntegralAcquireReq copy$default(IntegralAcquireReq integralAcquireReq, String str, String str2, String str3, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = integralAcquireReq.practiceListId;
        }
        if ((i3 & 2) != 0) {
            str2 = integralAcquireReq.nickName;
        }
        if ((i3 & 4) != 0) {
            str3 = integralAcquireReq.userName;
        }
        if ((i3 & 8) != 0) {
            i2 = integralAcquireReq.shareType;
        }
        return integralAcquireReq.copy(str, str2, str3, i2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull IntegralAcquireReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.practiceListId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.practiceListId);
        }
        output.encodeStringElement(serialDesc, 1, self.nickName);
        output.encodeStringElement(serialDesc, 2, self.userName);
        output.encodeIntElement(serialDesc, 3, self.shareType);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getPracticeListId() {
        return this.practiceListId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getNickName() {
        return this.nickName;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getUserName() {
        return this.userName;
    }

    /* renamed from: component4, reason: from getter */
    public final int getShareType() {
        return this.shareType;
    }

    @NotNull
    public final IntegralAcquireReq copy(@Nullable String practiceListId, @NotNull String nickName, @NotNull String userName, int shareType) {
        Intrinsics.checkNotNullParameter(nickName, "nickName");
        Intrinsics.checkNotNullParameter(userName, "userName");
        return new IntegralAcquireReq(practiceListId, nickName, userName, shareType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntegralAcquireReq)) {
            return false;
        }
        IntegralAcquireReq integralAcquireReq = (IntegralAcquireReq) other;
        return Intrinsics.areEqual(this.practiceListId, integralAcquireReq.practiceListId) && Intrinsics.areEqual(this.nickName, integralAcquireReq.nickName) && Intrinsics.areEqual(this.userName, integralAcquireReq.userName) && this.shareType == integralAcquireReq.shareType;
    }

    @NotNull
    public final String getNickName() {
        return this.nickName;
    }

    @Nullable
    public final String getPracticeListId() {
        return this.practiceListId;
    }

    public final int getShareType() {
        return this.shareType;
    }

    @NotNull
    public final String getUserName() {
        return this.userName;
    }

    public int hashCode() {
        String str = this.practiceListId;
        return ((((((str == null ? 0 : str.hashCode()) * 31) + this.nickName.hashCode()) * 31) + this.userName.hashCode()) * 31) + this.shareType;
    }

    @NotNull
    public String toString() {
        return "IntegralAcquireReq(practiceListId=" + this.practiceListId + ", nickName=" + this.nickName + ", userName=" + this.userName + ", shareType=" + this.shareType + ")";
    }

    public IntegralAcquireReq(@Nullable String str, @NotNull String nickName, @NotNull String userName, int i2) {
        Intrinsics.checkNotNullParameter(nickName, "nickName");
        Intrinsics.checkNotNullParameter(userName, "userName");
        this.practiceListId = str;
        this.nickName = nickName;
        this.userName = userName;
        this.shareType = i2;
    }

    public /* synthetic */ IntegralAcquireReq(String str, String str2, String str3, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, str2, str3, i2);
    }
}
