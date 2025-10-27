package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 #2\u00020\u0001:\u0002\"#B-\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB\u001d\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000bJ&\u0010\u0013\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J!\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!HÇ\u0001R\"\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u000e\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\"\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u000e\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r¨\u0006$"}, d2 = {"Lcom/yddmi/doctor/entity/request/Operate;", "", "seen1", "", "operateId", "type", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/Integer;)V", "getOperateId", "()Ljava/lang/Integer;", "setOperateId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getType", "setType", "component1", "component2", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/request/Operate;", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class Operate {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @SerializedName("operateId")
    @Nullable
    private Integer operateId;

    @SerializedName("type")
    @Nullable
    private Integer type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/Operate$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/Operate;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<Operate> serializer() {
            return Operate$$serializer.INSTANCE;
        }
    }

    public Operate() {
        this((Integer) null, (Integer) (0 == true ? 1 : 0), 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    public Operate(@Nullable Integer num, @Nullable Integer num2) {
        this.operateId = num;
        this.type = num2;
    }

    public static /* synthetic */ Operate copy$default(Operate operate, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = operate.operateId;
        }
        if ((i2 & 2) != 0) {
            num2 = operate.type;
        }
        return operate.copy(num, num2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull Operate self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.operateId) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.operateId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num2 = self.type) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.type);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getOperateId() {
        return this.operateId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getType() {
        return this.type;
    }

    @NotNull
    public final Operate copy(@Nullable Integer operateId, @Nullable Integer type) {
        return new Operate(operateId, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Operate)) {
            return false;
        }
        Operate operate = (Operate) other;
        return Intrinsics.areEqual(this.operateId, operate.operateId) && Intrinsics.areEqual(this.type, operate.type);
    }

    @Nullable
    public final Integer getOperateId() {
        return this.operateId;
    }

    @Nullable
    public final Integer getType() {
        return this.type;
    }

    public int hashCode() {
        Integer num = this.operateId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.type;
        return iHashCode + (num2 != null ? num2.hashCode() : 0);
    }

    public final void setOperateId(@Nullable Integer num) {
        this.operateId = num;
    }

    public final void setType(@Nullable Integer num) {
        this.type = num;
    }

    @NotNull
    public String toString() {
        return "Operate(operateId=" + this.operateId + ", type=" + this.type + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ Operate(int i2, Integer num, Integer num2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, Operate$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.operateId = 0;
        } else {
            this.operateId = num;
        }
        if ((i2 & 2) == 0) {
            this.type = 0;
        } else {
            this.type = num2;
        }
    }

    public /* synthetic */ Operate(Integer num, Integer num2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : num, (i2 & 2) != 0 ? 0 : num2);
    }
}
