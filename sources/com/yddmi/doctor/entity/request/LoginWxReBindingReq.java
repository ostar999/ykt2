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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0002 !B)\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fHÇ\u0001R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\r¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/entity/request/LoginWxReBindingReq;", "", "seen1", "", "userId", "platformType", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(II)V", "getPlatformType", "()I", "setPlatformType", "(I)V", "getUserId", "setUserId", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class LoginWxReBindingReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int platformType;
    private int userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/LoginWxReBindingReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/LoginWxReBindingReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<LoginWxReBindingReq> serializer() {
            return LoginWxReBindingReq$$serializer.INSTANCE;
        }
    }

    public LoginWxReBindingReq(int i2, int i3) {
        this.userId = i2;
        this.platformType = i3;
    }

    public static /* synthetic */ LoginWxReBindingReq copy$default(LoginWxReBindingReq loginWxReBindingReq, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = loginWxReBindingReq.userId;
        }
        if ((i4 & 2) != 0) {
            i3 = loginWxReBindingReq.platformType;
        }
        return loginWxReBindingReq.copy(i2, i3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull LoginWxReBindingReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.userId);
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.platformType != -1) {
            output.encodeIntElement(serialDesc, 1, self.platformType);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getPlatformType() {
        return this.platformType;
    }

    @NotNull
    public final LoginWxReBindingReq copy(int userId, int platformType) {
        return new LoginWxReBindingReq(userId, platformType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoginWxReBindingReq)) {
            return false;
        }
        LoginWxReBindingReq loginWxReBindingReq = (LoginWxReBindingReq) other;
        return this.userId == loginWxReBindingReq.userId && this.platformType == loginWxReBindingReq.platformType;
    }

    public final int getPlatformType() {
        return this.platformType;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        return (this.userId * 31) + this.platformType;
    }

    public final void setPlatformType(int i2) {
        this.platformType = i2;
    }

    public final void setUserId(int i2) {
        this.userId = i2;
    }

    @NotNull
    public String toString() {
        return "LoginWxReBindingReq(userId=" + this.userId + ", platformType=" + this.platformType + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ LoginWxReBindingReq(int i2, int i3, int i4, SerializationConstructorMarker serializationConstructorMarker) {
        if (1 != (i2 & 1)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 1, LoginWxReBindingReq$$serializer.INSTANCE.getDescriptor());
        }
        this.userId = i3;
        if ((i2 & 2) == 0) {
            this.platformType = -1;
        } else {
            this.platformType = i4;
        }
    }

    public /* synthetic */ LoginWxReBindingReq(int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i4 & 2) != 0 ? -1 : i3);
    }
}
