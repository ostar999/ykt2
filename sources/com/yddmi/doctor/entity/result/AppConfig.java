package com.yddmi.doctor.entity.result;

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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001d\u001eB-\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tB\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\nJ\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001J!\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cHÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u001f"}, d2 = {"Lcom/yddmi/doctor/entity/result/AppConfig;", "", "seen1", "", "configKey", "", "configValue", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;)V", "getConfigKey", "()Ljava/lang/String;", "getConfigValue", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class AppConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String configKey;

    @NotNull
    private final String configValue;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/AppConfig$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/AppConfig;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<AppConfig> serializer() {
            return AppConfig$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ AppConfig(int i2, String str, String str2, SerializationConstructorMarker serializationConstructorMarker) {
        if (1 != (i2 & 1)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 1, AppConfig$$serializer.INSTANCE.getDescriptor());
        }
        this.configKey = str;
        if ((i2 & 2) == 0) {
            this.configValue = "";
        } else {
            this.configValue = str2;
        }
    }

    public static /* synthetic */ AppConfig copy$default(AppConfig appConfig, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = appConfig.configKey;
        }
        if ((i2 & 2) != 0) {
            str2 = appConfig.configValue;
        }
        return appConfig.copy(str, str2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull AppConfig self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.configKey);
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.configValue, "")) {
            output.encodeStringElement(serialDesc, 1, self.configValue);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getConfigKey() {
        return this.configKey;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getConfigValue() {
        return this.configValue;
    }

    @NotNull
    public final AppConfig copy(@NotNull String configKey, @NotNull String configValue) {
        Intrinsics.checkNotNullParameter(configKey, "configKey");
        Intrinsics.checkNotNullParameter(configValue, "configValue");
        return new AppConfig(configKey, configValue);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppConfig)) {
            return false;
        }
        AppConfig appConfig = (AppConfig) other;
        return Intrinsics.areEqual(this.configKey, appConfig.configKey) && Intrinsics.areEqual(this.configValue, appConfig.configValue);
    }

    @NotNull
    public final String getConfigKey() {
        return this.configKey;
    }

    @NotNull
    public final String getConfigValue() {
        return this.configValue;
    }

    public int hashCode() {
        return (this.configKey.hashCode() * 31) + this.configValue.hashCode();
    }

    @NotNull
    public String toString() {
        return "AppConfig(configKey=" + this.configKey + ", configValue=" + this.configValue + ")";
    }

    public AppConfig(@NotNull String configKey, @NotNull String configValue) {
        Intrinsics.checkNotNullParameter(configKey, "configKey");
        Intrinsics.checkNotNullParameter(configValue, "configValue");
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public /* synthetic */ AppConfig(String str, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? "" : str2);
    }
}
