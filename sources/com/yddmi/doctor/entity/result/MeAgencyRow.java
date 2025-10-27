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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 #2\u00020\u0001:\u0002\"#B3\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB%\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\fJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J)\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0006HÖ\u0001J!\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006$"}, d2 = {"Lcom/yddmi/doctor/entity/result/MeAgencyRow;", "", "seen1", "", "id", "name", "", "itemSelected", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;ZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Z)V", "getId", "()I", "getItemSelected", "()Z", "getName", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class MeAgencyRow {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int id;
    private final boolean itemSelected;

    @Nullable
    private final String name;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/MeAgencyRow$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/MeAgencyRow;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<MeAgencyRow> serializer() {
            return MeAgencyRow$$serializer.INSTANCE;
        }
    }

    public MeAgencyRow() {
        this(0, (String) null, false, 7, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ MeAgencyRow(int i2, int i3, String str, boolean z2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, MeAgencyRow$$serializer.INSTANCE.getDescriptor());
        }
        this.id = (i2 & 1) == 0 ? -1 : i3;
        if ((i2 & 2) == 0) {
            this.name = "";
        } else {
            this.name = str;
        }
        if ((i2 & 4) == 0) {
            this.itemSelected = false;
        } else {
            this.itemSelected = z2;
        }
    }

    public static /* synthetic */ MeAgencyRow copy$default(MeAgencyRow meAgencyRow, int i2, String str, boolean z2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = meAgencyRow.id;
        }
        if ((i3 & 2) != 0) {
            str = meAgencyRow.name;
        }
        if ((i3 & 4) != 0) {
            z2 = meAgencyRow.itemSelected;
        }
        return meAgencyRow.copy(i2, str, z2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull MeAgencyRow self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.id != -1) {
            output.encodeIntElement(serialDesc, 0, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.itemSelected) {
            output.encodeBooleanElement(serialDesc, 2, self.itemSelected);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getItemSelected() {
        return this.itemSelected;
    }

    @NotNull
    public final MeAgencyRow copy(int id, @Nullable String name, boolean itemSelected) {
        return new MeAgencyRow(id, name, itemSelected);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MeAgencyRow)) {
            return false;
        }
        MeAgencyRow meAgencyRow = (MeAgencyRow) other;
        return this.id == meAgencyRow.id && Intrinsics.areEqual(this.name, meAgencyRow.name) && this.itemSelected == meAgencyRow.itemSelected;
    }

    public final int getId() {
        return this.id;
    }

    public final boolean getItemSelected() {
        return this.itemSelected;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int i2 = this.id * 31;
        String str = this.name;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        boolean z2 = this.itemSelected;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        return iHashCode + i3;
    }

    @NotNull
    public String toString() {
        return "MeAgencyRow(id=" + this.id + ", name=" + this.name + ", itemSelected=" + this.itemSelected + ")";
    }

    public MeAgencyRow(int i2, @Nullable String str, boolean z2) {
        this.id = i2;
        this.name = str;
        this.itemSelected = z2;
    }

    public /* synthetic */ MeAgencyRow(int i2, String str, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i2, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? false : z2);
    }
}
