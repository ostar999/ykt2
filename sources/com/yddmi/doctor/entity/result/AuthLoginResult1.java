package com.yddmi.doctor.entity.result;

import com.mobile.auth.BuildConfig;
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

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000  2\u00020\u0001:\u0002\u001f B-\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB\u001d\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J!\u0010\u0012\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0007HÖ\u0001J!\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eHÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006!"}, d2 = {"Lcom/yddmi/doctor/entity/result/AuthLoginResult1;", "", "seen1", "", "access_token", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", BuildConfig.FLAVOR_type, "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILcom/yddmi/doctor/entity/result/AuthLoginResult;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Lcom/yddmi/doctor/entity/result/AuthLoginResult;Ljava/lang/String;)V", "getAccess_token", "()Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "getLog", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class AuthLoginResult1 {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final AuthLoginResult access_token;

    @Nullable
    private final String log;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/AuthLoginResult1$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult1;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<AuthLoginResult1> serializer() {
            return AuthLoginResult1$$serializer.INSTANCE;
        }
    }

    public AuthLoginResult1() {
        this((AuthLoginResult) null, (String) (0 == true ? 1 : 0), 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ AuthLoginResult1(int i2, AuthLoginResult authLoginResult, String str, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, AuthLoginResult1$$serializer.INSTANCE.getDescriptor());
        }
        this.access_token = (i2 & 1) == 0 ? null : authLoginResult;
        if ((i2 & 2) == 0) {
            this.log = "";
        } else {
            this.log = str;
        }
    }

    public static /* synthetic */ AuthLoginResult1 copy$default(AuthLoginResult1 authLoginResult1, AuthLoginResult authLoginResult, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            authLoginResult = authLoginResult1.access_token;
        }
        if ((i2 & 2) != 0) {
            str = authLoginResult1.log;
        }
        return authLoginResult1.copy(authLoginResult, str);
    }

    @JvmStatic
    public static final void write$Self(@NotNull AuthLoginResult1 self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.access_token != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, AuthLoginResult$$serializer.INSTANCE, self.access_token);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.log, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.log);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final AuthLoginResult getAccess_token() {
        return this.access_token;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getLog() {
        return this.log;
    }

    @NotNull
    public final AuthLoginResult1 copy(@Nullable AuthLoginResult access_token, @Nullable String log) {
        return new AuthLoginResult1(access_token, log);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AuthLoginResult1)) {
            return false;
        }
        AuthLoginResult1 authLoginResult1 = (AuthLoginResult1) other;
        return Intrinsics.areEqual(this.access_token, authLoginResult1.access_token) && Intrinsics.areEqual(this.log, authLoginResult1.log);
    }

    @Nullable
    public final AuthLoginResult getAccess_token() {
        return this.access_token;
    }

    @Nullable
    public final String getLog() {
        return this.log;
    }

    public int hashCode() {
        AuthLoginResult authLoginResult = this.access_token;
        int iHashCode = (authLoginResult == null ? 0 : authLoginResult.hashCode()) * 31;
        String str = this.log;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "AuthLoginResult1(access_token=" + this.access_token + ", log=" + this.log + ")";
    }

    public AuthLoginResult1(@Nullable AuthLoginResult authLoginResult, @Nullable String str) {
        this.access_token = authLoginResult;
        this.log = str;
    }

    public /* synthetic */ AuthLoginResult1(AuthLoginResult authLoginResult, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : authLoginResult, (i2 & 2) != 0 ? "" : str);
    }
}
