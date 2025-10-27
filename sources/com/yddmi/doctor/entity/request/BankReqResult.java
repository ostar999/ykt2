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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 (2\u00020\u0001:\u0002'(BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB7\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0005HÖ\u0001J!\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006)"}, d2 = {"Lcom/yddmi/doctor/entity/request/BankReqResult;", "", "seen1", "", "bank", "", "cardNo", "idCard", "name", "userId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getBank", "()Ljava/lang/String;", "getCardNo", "getIdCard", "getName", "getUserId", "()I", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class BankReqResult {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String bank;

    @NotNull
    private final String cardNo;

    @NotNull
    private final String idCard;

    @NotNull
    private final String name;
    private final int userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/BankReqResult$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/BankReqResult;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<BankReqResult> serializer() {
            return BankReqResult$$serializer.INSTANCE;
        }
    }

    public BankReqResult() {
        this((String) null, (String) null, (String) null, (String) null, 0, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ BankReqResult(int i2, String str, String str2, String str3, String str4, int i3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, BankReqResult$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.bank = "";
        } else {
            this.bank = str;
        }
        if ((i2 & 2) == 0) {
            this.cardNo = "";
        } else {
            this.cardNo = str2;
        }
        if ((i2 & 4) == 0) {
            this.idCard = "";
        } else {
            this.idCard = str3;
        }
        if ((i2 & 8) == 0) {
            this.name = "";
        } else {
            this.name = str4;
        }
        if ((i2 & 16) == 0) {
            this.userId = -1;
        } else {
            this.userId = i3;
        }
    }

    public static /* synthetic */ BankReqResult copy$default(BankReqResult bankReqResult, String str, String str2, String str3, String str4, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = bankReqResult.bank;
        }
        if ((i3 & 2) != 0) {
            str2 = bankReqResult.cardNo;
        }
        String str5 = str2;
        if ((i3 & 4) != 0) {
            str3 = bankReqResult.idCard;
        }
        String str6 = str3;
        if ((i3 & 8) != 0) {
            str4 = bankReqResult.name;
        }
        String str7 = str4;
        if ((i3 & 16) != 0) {
            i2 = bankReqResult.userId;
        }
        return bankReqResult.copy(str, str5, str6, str7, i2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull BankReqResult self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.bank, "")) {
            output.encodeStringElement(serialDesc, 0, self.bank);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.cardNo, "")) {
            output.encodeStringElement(serialDesc, 1, self.cardNo);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.idCard, "")) {
            output.encodeStringElement(serialDesc, 2, self.idCard);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeStringElement(serialDesc, 3, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.userId != -1) {
            output.encodeIntElement(serialDesc, 4, self.userId);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getBank() {
        return this.bank;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getCardNo() {
        return this.cardNo;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getIdCard() {
        return this.idCard;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component5, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @NotNull
    public final BankReqResult copy(@NotNull String bank, @NotNull String cardNo, @NotNull String idCard, @NotNull String name, int userId) {
        Intrinsics.checkNotNullParameter(bank, "bank");
        Intrinsics.checkNotNullParameter(cardNo, "cardNo");
        Intrinsics.checkNotNullParameter(idCard, "idCard");
        Intrinsics.checkNotNullParameter(name, "name");
        return new BankReqResult(bank, cardNo, idCard, name, userId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BankReqResult)) {
            return false;
        }
        BankReqResult bankReqResult = (BankReqResult) other;
        return Intrinsics.areEqual(this.bank, bankReqResult.bank) && Intrinsics.areEqual(this.cardNo, bankReqResult.cardNo) && Intrinsics.areEqual(this.idCard, bankReqResult.idCard) && Intrinsics.areEqual(this.name, bankReqResult.name) && this.userId == bankReqResult.userId;
    }

    @NotNull
    public final String getBank() {
        return this.bank;
    }

    @NotNull
    public final String getCardNo() {
        return this.cardNo;
    }

    @NotNull
    public final String getIdCard() {
        return this.idCard;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        return (((((((this.bank.hashCode() * 31) + this.cardNo.hashCode()) * 31) + this.idCard.hashCode()) * 31) + this.name.hashCode()) * 31) + this.userId;
    }

    @NotNull
    public String toString() {
        return "BankReqResult(bank=" + this.bank + ", cardNo=" + this.cardNo + ", idCard=" + this.idCard + ", name=" + this.name + ", userId=" + this.userId + ")";
    }

    public BankReqResult(@NotNull String bank, @NotNull String cardNo, @NotNull String idCard, @NotNull String name, int i2) {
        Intrinsics.checkNotNullParameter(bank, "bank");
        Intrinsics.checkNotNullParameter(cardNo, "cardNo");
        Intrinsics.checkNotNullParameter(idCard, "idCard");
        Intrinsics.checkNotNullParameter(name, "name");
        this.bank = bank;
        this.cardNo = cardNo;
        this.idCard = idCard;
        this.name = name;
        this.userId = i2;
    }

    public /* synthetic */ BankReqResult(String str, String str2, String str3, String str4, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? "" : str2, (i3 & 4) != 0 ? "" : str3, (i3 & 8) == 0 ? str4 : "", (i3 & 16) != 0 ? -1 : i2);
    }
}
