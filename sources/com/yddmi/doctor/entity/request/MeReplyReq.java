package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import androidx.core.app.NotificationCompat;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.tencent.open.SocialConstants;
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
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 C2\u00020\u0001:\u0002BCBw\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011Bs\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003¢\u0006\u0002\u0010\u0012J\t\u0010+\u001a\u00020\u0005HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u00100\u001a\u00020\u0005HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0005HÆ\u0003Jw\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0003HÆ\u0001J\u0013\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00109\u001a\u00020\u0003HÖ\u0001J\t\u0010:\u001a\u00020\u0005HÖ\u0001J!\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u00002\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AHÇ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0014\"\u0004\b\u001a\u0010\u0016R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016R\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0014\"\u0004\b\"\u0010\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001e\"\u0004\b$\u0010 R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u001e\"\u0004\b&\u0010 R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0014\"\u0004\b(\u0010\u0016R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0014\"\u0004\b*\u0010\u0016¨\u0006D"}, d2 = {"Lcom/yddmi/doctor/entity/request/MeReplyReq;", "", "seen1", "", "content", "", "type", "userId", "fileId", NotificationCompat.CATEGORY_SYSTEM, "name", AliyunLogCommon.TERMINAL_TYPE, "userName", "userPhone", SocialConstants.PARAM_SOURCE, "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getFileId", "setFileId", "getName", "setName", "getPhone", "setPhone", "getSource", "()I", "setSource", "(I)V", "getSys", "setSys", "getType", "setType", "getUserId", "setUserId", "getUserName", "setUserName", "getUserPhone", "setUserPhone", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class MeReplyReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private String content;

    @Nullable
    private String fileId;

    @Nullable
    private String name;

    @Nullable
    private String phone;
    private int source;

    @NotNull
    private String sys;
    private int type;
    private int userId;

    @Nullable
    private String userName;

    @Nullable
    private String userPhone;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/MeReplyReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/MeReplyReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<MeReplyReq> serializer() {
            return MeReplyReq$$serializer.INSTANCE;
        }
    }

    public MeReplyReq() {
        this((String) null, 0, 0, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 0, 1023, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ MeReplyReq(int i2, String str, int i3, int i4, String str2, String str3, String str4, String str5, String str6, String str7, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, MeReplyReq$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.content = "";
        } else {
            this.content = str;
        }
        if ((i2 & 2) == 0) {
            this.type = -1;
        } else {
            this.type = i3;
        }
        if ((i2 & 4) == 0) {
            this.userId = -1;
        } else {
            this.userId = i4;
        }
        if ((i2 & 8) == 0) {
            this.fileId = "";
        } else {
            this.fileId = str2;
        }
        if ((i2 & 16) == 0) {
            this.sys = "";
        } else {
            this.sys = str3;
        }
        if ((i2 & 32) == 0) {
            this.name = "";
        } else {
            this.name = str4;
        }
        if ((i2 & 64) == 0) {
            this.phone = "";
        } else {
            this.phone = str5;
        }
        if ((i2 & 128) == 0) {
            this.userName = "";
        } else {
            this.userName = str6;
        }
        if ((i2 & 256) == 0) {
            this.userPhone = "";
        } else {
            this.userPhone = str7;
        }
        if ((i2 & 512) == 0) {
            this.source = 0;
        } else {
            this.source = i5;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull MeReplyReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeStringElement(serialDesc, 0, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.type != -1) {
            output.encodeIntElement(serialDesc, 1, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.userId != -1) {
            output.encodeIntElement(serialDesc, 2, self.userId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.fileId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.fileId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.sys, "")) {
            output.encodeStringElement(serialDesc, 4, self.sys);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.phone, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.phone);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.userName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.userName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.userPhone, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.userPhone);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.source != 0) {
            output.encodeIntElement(serialDesc, 9, self.source);
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* renamed from: component10, reason: from getter */
    public final int getSource() {
        return this.source;
    }

    /* renamed from: component2, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component3, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getFileId() {
        return this.fileId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getSys() {
        return this.sys;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getUserName() {
        return this.userName;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getUserPhone() {
        return this.userPhone;
    }

    @NotNull
    public final MeReplyReq copy(@NotNull String content, int type, int userId, @Nullable String fileId, @NotNull String sys, @Nullable String name, @Nullable String phone, @Nullable String userName, @Nullable String userPhone, int source) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(sys, "sys");
        return new MeReplyReq(content, type, userId, fileId, sys, name, phone, userName, userPhone, source);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MeReplyReq)) {
            return false;
        }
        MeReplyReq meReplyReq = (MeReplyReq) other;
        return Intrinsics.areEqual(this.content, meReplyReq.content) && this.type == meReplyReq.type && this.userId == meReplyReq.userId && Intrinsics.areEqual(this.fileId, meReplyReq.fileId) && Intrinsics.areEqual(this.sys, meReplyReq.sys) && Intrinsics.areEqual(this.name, meReplyReq.name) && Intrinsics.areEqual(this.phone, meReplyReq.phone) && Intrinsics.areEqual(this.userName, meReplyReq.userName) && Intrinsics.areEqual(this.userPhone, meReplyReq.userPhone) && this.source == meReplyReq.source;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final String getFileId() {
        return this.fileId;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    public final int getSource() {
        return this.source;
    }

    @NotNull
    public final String getSys() {
        return this.sys;
    }

    public final int getType() {
        return this.type;
    }

    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    public final String getUserName() {
        return this.userName;
    }

    @Nullable
    public final String getUserPhone() {
        return this.userPhone;
    }

    public int hashCode() {
        int iHashCode = ((((this.content.hashCode() * 31) + this.type) * 31) + this.userId) * 31;
        String str = this.fileId;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.sys.hashCode()) * 31;
        String str2 = this.name;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.phone;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.userName;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.userPhone;
        return ((iHashCode5 + (str5 != null ? str5.hashCode() : 0)) * 31) + this.source;
    }

    public final void setContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
    }

    public final void setFileId(@Nullable String str) {
        this.fileId = str;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setPhone(@Nullable String str) {
        this.phone = str;
    }

    public final void setSource(int i2) {
        this.source = i2;
    }

    public final void setSys(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sys = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    public final void setUserId(int i2) {
        this.userId = i2;
    }

    public final void setUserName(@Nullable String str) {
        this.userName = str;
    }

    public final void setUserPhone(@Nullable String str) {
        this.userPhone = str;
    }

    @NotNull
    public String toString() {
        return "MeReplyReq(content=" + this.content + ", type=" + this.type + ", userId=" + this.userId + ", fileId=" + this.fileId + ", sys=" + this.sys + ", name=" + this.name + ", phone=" + this.phone + ", userName=" + this.userName + ", userPhone=" + this.userPhone + ", source=" + this.source + ")";
    }

    public MeReplyReq(@NotNull String content, int i2, int i3, @Nullable String str, @NotNull String sys, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i4) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(sys, "sys");
        this.content = content;
        this.type = i2;
        this.userId = i3;
        this.fileId = str;
        this.sys = sys;
        this.name = str2;
        this.phone = str3;
        this.userName = str4;
        this.userPhone = str5;
        this.source = i4;
    }

    public /* synthetic */ MeReplyReq(String str, int i2, int i3, String str2, String str3, String str4, String str5, String str6, String str7, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? "" : str, (i5 & 2) != 0 ? -1 : i2, (i5 & 4) == 0 ? i3 : -1, (i5 & 8) != 0 ? "" : str2, (i5 & 16) != 0 ? "" : str3, (i5 & 32) != 0 ? "" : str4, (i5 & 64) != 0 ? "" : str5, (i5 & 128) != 0 ? "" : str6, (i5 & 256) == 0 ? str7 : "", (i5 & 512) != 0 ? 0 : i4);
    }
}
