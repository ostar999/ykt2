package com.yddmi.doctor.entity.result;

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

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 %2\u00020\u0001:\u0002$%B5\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB'\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\fJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0010J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J0\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001¢\u0006\u0002\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001J!\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "", "seen1", "", "access_token", "", "expires_in", "newer", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/Integer;ZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/Integer;Z)V", "getAccess_token", "()Ljava/lang/String;", "getExpires_in", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getNewer", "()Z", "component1", "component2", "component3", "copy", "(Ljava/lang/String;Ljava/lang/Integer;Z)Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class AuthLoginResult {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String access_token;

    @Nullable
    private final Integer expires_in;
    private final boolean newer;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/AuthLoginResult$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<AuthLoginResult> serializer() {
            return AuthLoginResult$$serializer.INSTANCE;
        }
    }

    public AuthLoginResult() {
        this((String) null, (Integer) null, false, 7, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ AuthLoginResult(int i2, String str, Integer num, boolean z2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, AuthLoginResult$$serializer.INSTANCE.getDescriptor());
        }
        this.access_token = (i2 & 1) == 0 ? "" : str;
        if ((i2 & 2) == 0) {
            this.expires_in = -1;
        } else {
            this.expires_in = num;
        }
        if ((i2 & 4) == 0) {
            this.newer = false;
        } else {
            this.newer = z2;
        }
    }

    public static /* synthetic */ AuthLoginResult copy$default(AuthLoginResult authLoginResult, String str, Integer num, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = authLoginResult.access_token;
        }
        if ((i2 & 2) != 0) {
            num = authLoginResult.expires_in;
        }
        if ((i2 & 4) != 0) {
            z2 = authLoginResult.newer;
        }
        return authLoginResult.copy(str, num, z2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull AuthLoginResult self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.access_token, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.access_token);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num = self.expires_in) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.expires_in);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.newer) {
            output.encodeBooleanElement(serialDesc, 2, self.newer);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getAccess_token() {
        return this.access_token;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getExpires_in() {
        return this.expires_in;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getNewer() {
        return this.newer;
    }

    @NotNull
    public final AuthLoginResult copy(@Nullable String access_token, @Nullable Integer expires_in, boolean newer) {
        return new AuthLoginResult(access_token, expires_in, newer);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AuthLoginResult)) {
            return false;
        }
        AuthLoginResult authLoginResult = (AuthLoginResult) other;
        return Intrinsics.areEqual(this.access_token, authLoginResult.access_token) && Intrinsics.areEqual(this.expires_in, authLoginResult.expires_in) && this.newer == authLoginResult.newer;
    }

    @Nullable
    public final String getAccess_token() {
        return this.access_token;
    }

    @Nullable
    public final Integer getExpires_in() {
        return this.expires_in;
    }

    public final boolean getNewer() {
        return this.newer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.access_token;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.expires_in;
        int iHashCode2 = (iHashCode + (num != null ? num.hashCode() : 0)) * 31;
        boolean z2 = this.newer;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode2 + i2;
    }

    @NotNull
    public String toString() {
        return "AuthLoginResult(access_token=" + this.access_token + ", expires_in=" + this.expires_in + ", newer=" + this.newer + ")";
    }

    public AuthLoginResult(@Nullable String str, @Nullable Integer num, boolean z2) {
        this.access_token = str;
        this.expires_in = num;
        this.newer = z2;
    }

    public /* synthetic */ AuthLoginResult(String str, Integer num, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? -1 : num, (i2 & 4) != 0 ? false : z2);
    }
}
