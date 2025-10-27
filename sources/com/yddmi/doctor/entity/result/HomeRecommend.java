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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 42\u00020\u0001:\u000234Bm\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010B]\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003¢\u0006\u0002\u0010\u0011J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003Jc\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u0003HÆ\u0001J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\u0003HÖ\u0001J\t\u0010+\u001a\u00020\u0005HÖ\u0001J!\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017¨\u00065"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeRecommend;", "", "seen1", "", "createTime", "", "fileUrl", "inviterCode", "inviterId", "inviterUrl", "name", "num", "phoneNo", "userId", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;I)V", "getCreateTime", "()Ljava/lang/String;", "getFileUrl", "getInviterCode", "getInviterId", "()I", "getInviterUrl", "getName", "getNum", "getPhoneNo", "getUserId", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeRecommend {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String createTime;

    @NotNull
    private final String fileUrl;

    @NotNull
    private final String inviterCode;
    private final int inviterId;

    @NotNull
    private final String inviterUrl;

    @NotNull
    private final String name;
    private final int num;

    @NotNull
    private final String phoneNo;
    private final int userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeRecommend$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeRecommend;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeRecommend> serializer() {
            return HomeRecommend$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeRecommend(int i2, String str, String str2, String str3, int i3, String str4, String str5, int i4, String str6, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if (256 != (i2 & 256)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 256, HomeRecommend$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str;
        }
        if ((i2 & 2) == 0) {
            this.fileUrl = "";
        } else {
            this.fileUrl = str2;
        }
        if ((i2 & 4) == 0) {
            this.inviterCode = "";
        } else {
            this.inviterCode = str3;
        }
        if ((i2 & 8) == 0) {
            this.inviterId = 0;
        } else {
            this.inviterId = i3;
        }
        if ((i2 & 16) == 0) {
            this.inviterUrl = "";
        } else {
            this.inviterUrl = str4;
        }
        if ((i2 & 32) == 0) {
            this.name = "";
        } else {
            this.name = str5;
        }
        if ((i2 & 64) == 0) {
            this.num = 0;
        } else {
            this.num = i4;
        }
        if ((i2 & 128) == 0) {
            this.phoneNo = "";
        } else {
            this.phoneNo = str6;
        }
        this.userId = i5;
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeRecommend self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeStringElement(serialDesc, 0, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.fileUrl, "")) {
            output.encodeStringElement(serialDesc, 1, self.fileUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.inviterCode, "")) {
            output.encodeStringElement(serialDesc, 2, self.inviterCode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.inviterId != 0) {
            output.encodeIntElement(serialDesc, 3, self.inviterId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.inviterUrl, "")) {
            output.encodeStringElement(serialDesc, 4, self.inviterUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeStringElement(serialDesc, 5, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.num != 0) {
            output.encodeIntElement(serialDesc, 6, self.num);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.phoneNo, "")) {
            output.encodeStringElement(serialDesc, 7, self.phoneNo);
        }
        output.encodeIntElement(serialDesc, 8, self.userId);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getFileUrl() {
        return this.fileUrl;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getInviterCode() {
        return this.inviterCode;
    }

    /* renamed from: component4, reason: from getter */
    public final int getInviterId() {
        return this.inviterId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getInviterUrl() {
        return this.inviterUrl;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component7, reason: from getter */
    public final int getNum() {
        return this.num;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getPhoneNo() {
        return this.phoneNo;
    }

    /* renamed from: component9, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @NotNull
    public final HomeRecommend copy(@NotNull String createTime, @NotNull String fileUrl, @NotNull String inviterCode, int inviterId, @NotNull String inviterUrl, @NotNull String name, int num, @NotNull String phoneNo, int userId) {
        Intrinsics.checkNotNullParameter(createTime, "createTime");
        Intrinsics.checkNotNullParameter(fileUrl, "fileUrl");
        Intrinsics.checkNotNullParameter(inviterCode, "inviterCode");
        Intrinsics.checkNotNullParameter(inviterUrl, "inviterUrl");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(phoneNo, "phoneNo");
        return new HomeRecommend(createTime, fileUrl, inviterCode, inviterId, inviterUrl, name, num, phoneNo, userId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeRecommend)) {
            return false;
        }
        HomeRecommend homeRecommend = (HomeRecommend) other;
        return Intrinsics.areEqual(this.createTime, homeRecommend.createTime) && Intrinsics.areEqual(this.fileUrl, homeRecommend.fileUrl) && Intrinsics.areEqual(this.inviterCode, homeRecommend.inviterCode) && this.inviterId == homeRecommend.inviterId && Intrinsics.areEqual(this.inviterUrl, homeRecommend.inviterUrl) && Intrinsics.areEqual(this.name, homeRecommend.name) && this.num == homeRecommend.num && Intrinsics.areEqual(this.phoneNo, homeRecommend.phoneNo) && this.userId == homeRecommend.userId;
    }

    @NotNull
    public final String getCreateTime() {
        return this.createTime;
    }

    @NotNull
    public final String getFileUrl() {
        return this.fileUrl;
    }

    @NotNull
    public final String getInviterCode() {
        return this.inviterCode;
    }

    public final int getInviterId() {
        return this.inviterId;
    }

    @NotNull
    public final String getInviterUrl() {
        return this.inviterUrl;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final int getNum() {
        return this.num;
    }

    @NotNull
    public final String getPhoneNo() {
        return this.phoneNo;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        return (((((((((((((((this.createTime.hashCode() * 31) + this.fileUrl.hashCode()) * 31) + this.inviterCode.hashCode()) * 31) + this.inviterId) * 31) + this.inviterUrl.hashCode()) * 31) + this.name.hashCode()) * 31) + this.num) * 31) + this.phoneNo.hashCode()) * 31) + this.userId;
    }

    @NotNull
    public String toString() {
        return "HomeRecommend(createTime=" + this.createTime + ", fileUrl=" + this.fileUrl + ", inviterCode=" + this.inviterCode + ", inviterId=" + this.inviterId + ", inviterUrl=" + this.inviterUrl + ", name=" + this.name + ", num=" + this.num + ", phoneNo=" + this.phoneNo + ", userId=" + this.userId + ")";
    }

    public HomeRecommend(@NotNull String createTime, @NotNull String fileUrl, @NotNull String inviterCode, int i2, @NotNull String inviterUrl, @NotNull String name, int i3, @NotNull String phoneNo, int i4) {
        Intrinsics.checkNotNullParameter(createTime, "createTime");
        Intrinsics.checkNotNullParameter(fileUrl, "fileUrl");
        Intrinsics.checkNotNullParameter(inviterCode, "inviterCode");
        Intrinsics.checkNotNullParameter(inviterUrl, "inviterUrl");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(phoneNo, "phoneNo");
        this.createTime = createTime;
        this.fileUrl = fileUrl;
        this.inviterCode = inviterCode;
        this.inviterId = i2;
        this.inviterUrl = inviterUrl;
        this.name = name;
        this.num = i3;
        this.phoneNo = phoneNo;
        this.userId = i4;
    }

    public /* synthetic */ HomeRecommend(String str, String str2, String str3, int i2, String str4, String str5, int i3, String str6, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? "" : str, (i5 & 2) != 0 ? "" : str2, (i5 & 4) != 0 ? "" : str3, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) != 0 ? "" : str4, (i5 & 32) != 0 ? "" : str5, (i5 & 64) != 0 ? 0 : i3, (i5 & 128) != 0 ? "" : str6, i4);
    }
}
