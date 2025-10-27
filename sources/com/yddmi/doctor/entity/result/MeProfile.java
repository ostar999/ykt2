package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.socket.user.PLVSocketUserConstant;
import com.umeng.socialize.net.dplus.CommonNetImpl;
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
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b9\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 _2\u00020\u0001:\u0002^_Bó\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\u0006\u0010\u0018\u001a\u00020\u0003\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u001a\u001a\u00020\u0003\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e¢\u0006\u0002\u0010\u001fB\u008b\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010 J\t\u0010;\u001a\u00020\u0005HÆ\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010=\u001a\u00020\u0007HÆ\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010?\u001a\u00020\u0003HÆ\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010C\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010D\u001a\u00020\u0003HÆ\u0003J\t\u0010E\u001a\u00020\u0003HÆ\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010H\u001a\u00020\u0003HÆ\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010M\u001a\u00020\u0003HÆ\u0003J\u000b\u0010N\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010O\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010P\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010Q\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u008f\u0002\u0010R\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00072\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010S\u001a\u00020\u00052\b\u0010T\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010U\u001a\u00020\u0003HÖ\u0001J\t\u0010V\u001a\u00020\u0007HÖ\u0001J!\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020\u00002\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020]HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b%\u0010$R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b)\u0010$R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b,\u0010$R\u0011\u0010\u0017\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b/\u0010$R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b0\u0010$R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b1\u0010$R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b2\u0010$R\u0011\u0010\u0010\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b3\u0010$R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b4\u0010$R\u0011\u0010\u0018\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b5\u0010(R\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b6\u0010(R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b7\u0010$R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b8\u0010$R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b9\u0010$R\u0011\u0010\u001a\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b:\u0010(¨\u0006`"}, d2 = {"Lcom/yddmi/doctor/entity/result/MeProfile;", "", "seen1", "", PLVSocketUserConstant.ROLE_ADMIN, "", "avatar", "", "createTime", "delFlag", "deptId", "email", "loginIp", "nickName", "position", "realName", CommonNetImpl.SEX, "status", "userId", "userName", AliyunLogCommon.TERMINAL_TYPE, "roleId", "orgName", "orgId", "studyMode", "identityName", "wxUnionFlag", "wxNickName", "wxHeadImgUrl", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getAdmin", "()Z", "getAvatar", "()Ljava/lang/String;", "getCreateTime", "getDelFlag", "getDeptId", "()I", "getEmail", "getIdentityName", "getLoginIp", "getNickName", "getOrgId", "getOrgName", "getPhone", "getPosition", "getRealName", "getRoleId", "getSex", "getStatus", "getStudyMode", "getUserId", "getUserName", "getWxHeadImgUrl", "getWxNickName", "getWxUnionFlag", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class MeProfile {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final boolean admin;

    @Nullable
    private final String avatar;

    @Nullable
    private final String createTime;

    @Nullable
    private final String delFlag;
    private final int deptId;

    @Nullable
    private final String email;

    @Nullable
    private final String identityName;

    @Nullable
    private final String loginIp;

    @Nullable
    private final String nickName;
    private final int orgId;

    @Nullable
    private final String orgName;

    @Nullable
    private final String phone;

    @Nullable
    private final String position;

    @Nullable
    private final String realName;

    @Nullable
    private final String roleId;

    @NotNull
    private final String sex;

    @Nullable
    private final String status;
    private final int studyMode;
    private final int userId;

    @Nullable
    private final String userName;

    @Nullable
    private final String wxHeadImgUrl;

    @Nullable
    private final String wxNickName;
    private final int wxUnionFlag;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/MeProfile$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/MeProfile;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<MeProfile> serializer() {
            return MeProfile$$serializer.INSTANCE;
        }
    }

    public MeProfile() {
        this(false, (String) null, (String) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, 0, 0, (String) null, 0, (String) null, (String) null, 8388607, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ MeProfile(int i2, boolean z2, String str, String str2, String str3, int i3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i4, String str11, String str12, String str13, String str14, int i5, int i6, String str15, int i7, String str16, String str17, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, MeProfile$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.admin = false;
        } else {
            this.admin = z2;
        }
        if ((i2 & 2) == 0) {
            this.avatar = "";
        } else {
            this.avatar = str;
        }
        if ((i2 & 4) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str2;
        }
        if ((i2 & 8) == 0) {
            this.delFlag = "";
        } else {
            this.delFlag = str3;
        }
        if ((i2 & 16) == 0) {
            this.deptId = -1;
        } else {
            this.deptId = i3;
        }
        if ((i2 & 32) == 0) {
            this.email = "";
        } else {
            this.email = str4;
        }
        if ((i2 & 64) == 0) {
            this.loginIp = "";
        } else {
            this.loginIp = str5;
        }
        if ((i2 & 128) == 0) {
            this.nickName = "";
        } else {
            this.nickName = str6;
        }
        if ((i2 & 256) == 0) {
            this.position = "";
        } else {
            this.position = str7;
        }
        if ((i2 & 512) == 0) {
            this.realName = "";
        } else {
            this.realName = str8;
        }
        this.sex = (i2 & 1024) == 0 ? "0" : str9;
        if ((i2 & 2048) == 0) {
            this.status = "";
        } else {
            this.status = str10;
        }
        if ((i2 & 4096) == 0) {
            this.userId = -1;
        } else {
            this.userId = i4;
        }
        if ((i2 & 8192) == 0) {
            this.userName = "";
        } else {
            this.userName = str11;
        }
        if ((i2 & 16384) == 0) {
            this.phone = "";
        } else {
            this.phone = str12;
        }
        if ((32768 & i2) == 0) {
            this.roleId = "";
        } else {
            this.roleId = str13;
        }
        if ((65536 & i2) == 0) {
            this.orgName = "";
        } else {
            this.orgName = str14;
        }
        if ((131072 & i2) == 0) {
            this.orgId = 0;
        } else {
            this.orgId = i5;
        }
        if ((262144 & i2) == 0) {
            this.studyMode = -1;
        } else {
            this.studyMode = i6;
        }
        if ((524288 & i2) == 0) {
            this.identityName = "";
        } else {
            this.identityName = str15;
        }
        if ((1048576 & i2) == 0) {
            this.wxUnionFlag = 0;
        } else {
            this.wxUnionFlag = i7;
        }
        if ((2097152 & i2) == 0) {
            this.wxNickName = "";
        } else {
            this.wxNickName = str16;
        }
        if ((i2 & 4194304) == 0) {
            this.wxHeadImgUrl = "";
        } else {
            this.wxHeadImgUrl = str17;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull MeProfile self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.admin) {
            output.encodeBooleanElement(serialDesc, 0, self.admin);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.avatar, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.avatar);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.delFlag, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.delFlag);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.deptId != -1) {
            output.encodeIntElement(serialDesc, 4, self.deptId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.email, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.email);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.loginIp, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.loginIp);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.nickName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.nickName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.position, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.position);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.realName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.realName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.sex, "0")) {
            output.encodeStringElement(serialDesc, 10, self.sex);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.status, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || self.userId != -1) {
            output.encodeIntElement(serialDesc, 12, self.userId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.userName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.userName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || !Intrinsics.areEqual(self.phone, "")) {
            output.encodeNullableSerializableElement(serialDesc, 14, StringSerializer.INSTANCE, self.phone);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || !Intrinsics.areEqual(self.roleId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 15, StringSerializer.INSTANCE, self.roleId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || !Intrinsics.areEqual(self.orgName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 16, StringSerializer.INSTANCE, self.orgName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || self.orgId != 0) {
            output.encodeIntElement(serialDesc, 17, self.orgId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 18) || self.studyMode != -1) {
            output.encodeIntElement(serialDesc, 18, self.studyMode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 19) || !Intrinsics.areEqual(self.identityName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 19, StringSerializer.INSTANCE, self.identityName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 20) || self.wxUnionFlag != 0) {
            output.encodeIntElement(serialDesc, 20, self.wxUnionFlag);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 21) || !Intrinsics.areEqual(self.wxNickName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 21, StringSerializer.INSTANCE, self.wxNickName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 22) || !Intrinsics.areEqual(self.wxHeadImgUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 22, StringSerializer.INSTANCE, self.wxHeadImgUrl);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getAdmin() {
        return this.admin;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getRealName() {
        return this.realName;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getSex() {
        return this.sex;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    /* renamed from: component13, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getUserName() {
        return this.userName;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getRoleId() {
        return this.roleId;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getOrgName() {
        return this.orgName;
    }

    /* renamed from: component18, reason: from getter */
    public final int getOrgId() {
        return this.orgId;
    }

    /* renamed from: component19, reason: from getter */
    public final int getStudyMode() {
        return this.studyMode;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getAvatar() {
        return this.avatar;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getIdentityName() {
        return this.identityName;
    }

    /* renamed from: component21, reason: from getter */
    public final int getWxUnionFlag() {
        return this.wxUnionFlag;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getWxNickName() {
        return this.wxNickName;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final String getWxHeadImgUrl() {
        return this.wxHeadImgUrl;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getDelFlag() {
        return this.delFlag;
    }

    /* renamed from: component5, reason: from getter */
    public final int getDeptId() {
        return this.deptId;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getEmail() {
        return this.email;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getLoginIp() {
        return this.loginIp;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getNickName() {
        return this.nickName;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getPosition() {
        return this.position;
    }

    @NotNull
    public final MeProfile copy(boolean admin, @Nullable String avatar, @Nullable String createTime, @Nullable String delFlag, int deptId, @Nullable String email, @Nullable String loginIp, @Nullable String nickName, @Nullable String position, @Nullable String realName, @NotNull String sex, @Nullable String status, int userId, @Nullable String userName, @Nullable String phone, @Nullable String roleId, @Nullable String orgName, int orgId, int studyMode, @Nullable String identityName, int wxUnionFlag, @Nullable String wxNickName, @Nullable String wxHeadImgUrl) {
        Intrinsics.checkNotNullParameter(sex, "sex");
        return new MeProfile(admin, avatar, createTime, delFlag, deptId, email, loginIp, nickName, position, realName, sex, status, userId, userName, phone, roleId, orgName, orgId, studyMode, identityName, wxUnionFlag, wxNickName, wxHeadImgUrl);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MeProfile)) {
            return false;
        }
        MeProfile meProfile = (MeProfile) other;
        return this.admin == meProfile.admin && Intrinsics.areEqual(this.avatar, meProfile.avatar) && Intrinsics.areEqual(this.createTime, meProfile.createTime) && Intrinsics.areEqual(this.delFlag, meProfile.delFlag) && this.deptId == meProfile.deptId && Intrinsics.areEqual(this.email, meProfile.email) && Intrinsics.areEqual(this.loginIp, meProfile.loginIp) && Intrinsics.areEqual(this.nickName, meProfile.nickName) && Intrinsics.areEqual(this.position, meProfile.position) && Intrinsics.areEqual(this.realName, meProfile.realName) && Intrinsics.areEqual(this.sex, meProfile.sex) && Intrinsics.areEqual(this.status, meProfile.status) && this.userId == meProfile.userId && Intrinsics.areEqual(this.userName, meProfile.userName) && Intrinsics.areEqual(this.phone, meProfile.phone) && Intrinsics.areEqual(this.roleId, meProfile.roleId) && Intrinsics.areEqual(this.orgName, meProfile.orgName) && this.orgId == meProfile.orgId && this.studyMode == meProfile.studyMode && Intrinsics.areEqual(this.identityName, meProfile.identityName) && this.wxUnionFlag == meProfile.wxUnionFlag && Intrinsics.areEqual(this.wxNickName, meProfile.wxNickName) && Intrinsics.areEqual(this.wxHeadImgUrl, meProfile.wxHeadImgUrl);
    }

    public final boolean getAdmin() {
        return this.admin;
    }

    @Nullable
    public final String getAvatar() {
        return this.avatar;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final String getDelFlag() {
        return this.delFlag;
    }

    public final int getDeptId() {
        return this.deptId;
    }

    @Nullable
    public final String getEmail() {
        return this.email;
    }

    @Nullable
    public final String getIdentityName() {
        return this.identityName;
    }

    @Nullable
    public final String getLoginIp() {
        return this.loginIp;
    }

    @Nullable
    public final String getNickName() {
        return this.nickName;
    }

    public final int getOrgId() {
        return this.orgId;
    }

    @Nullable
    public final String getOrgName() {
        return this.orgName;
    }

    @Nullable
    public final String getPhone() {
        return this.phone;
    }

    @Nullable
    public final String getPosition() {
        return this.position;
    }

    @Nullable
    public final String getRealName() {
        return this.realName;
    }

    @Nullable
    public final String getRoleId() {
        return this.roleId;
    }

    @NotNull
    public final String getSex() {
        return this.sex;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }

    public final int getStudyMode() {
        return this.studyMode;
    }

    public final int getUserId() {
        return this.userId;
    }

    @Nullable
    public final String getUserName() {
        return this.userName;
    }

    @Nullable
    public final String getWxHeadImgUrl() {
        return this.wxHeadImgUrl;
    }

    @Nullable
    public final String getWxNickName() {
        return this.wxNickName;
    }

    public final int getWxUnionFlag() {
        return this.wxUnionFlag;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v46 */
    /* JADX WARN: Type inference failed for: r0v47 */
    public int hashCode() {
        boolean z2 = this.admin;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        int i2 = r02 * 31;
        String str = this.avatar;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.createTime;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.delFlag;
        int iHashCode3 = (((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.deptId) * 31;
        String str4 = this.email;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.loginIp;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.nickName;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.position;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.realName;
        int iHashCode8 = (((iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31) + this.sex.hashCode()) * 31;
        String str9 = this.status;
        int iHashCode9 = (((iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31) + this.userId) * 31;
        String str10 = this.userName;
        int iHashCode10 = (iHashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.phone;
        int iHashCode11 = (iHashCode10 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.roleId;
        int iHashCode12 = (iHashCode11 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.orgName;
        int iHashCode13 = (((((iHashCode12 + (str13 == null ? 0 : str13.hashCode())) * 31) + this.orgId) * 31) + this.studyMode) * 31;
        String str14 = this.identityName;
        int iHashCode14 = (((iHashCode13 + (str14 == null ? 0 : str14.hashCode())) * 31) + this.wxUnionFlag) * 31;
        String str15 = this.wxNickName;
        int iHashCode15 = (iHashCode14 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.wxHeadImgUrl;
        return iHashCode15 + (str16 != null ? str16.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "MeProfile(admin=" + this.admin + ", avatar=" + this.avatar + ", createTime=" + this.createTime + ", delFlag=" + this.delFlag + ", deptId=" + this.deptId + ", email=" + this.email + ", loginIp=" + this.loginIp + ", nickName=" + this.nickName + ", position=" + this.position + ", realName=" + this.realName + ", sex=" + this.sex + ", status=" + this.status + ", userId=" + this.userId + ", userName=" + this.userName + ", phone=" + this.phone + ", roleId=" + this.roleId + ", orgName=" + this.orgName + ", orgId=" + this.orgId + ", studyMode=" + this.studyMode + ", identityName=" + this.identityName + ", wxUnionFlag=" + this.wxUnionFlag + ", wxNickName=" + this.wxNickName + ", wxHeadImgUrl=" + this.wxHeadImgUrl + ")";
    }

    public MeProfile(boolean z2, @Nullable String str, @Nullable String str2, @Nullable String str3, int i2, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @NotNull String sex, @Nullable String str9, int i3, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, int i4, int i5, @Nullable String str14, int i6, @Nullable String str15, @Nullable String str16) {
        Intrinsics.checkNotNullParameter(sex, "sex");
        this.admin = z2;
        this.avatar = str;
        this.createTime = str2;
        this.delFlag = str3;
        this.deptId = i2;
        this.email = str4;
        this.loginIp = str5;
        this.nickName = str6;
        this.position = str7;
        this.realName = str8;
        this.sex = sex;
        this.status = str9;
        this.userId = i3;
        this.userName = str10;
        this.phone = str11;
        this.roleId = str12;
        this.orgName = str13;
        this.orgId = i4;
        this.studyMode = i5;
        this.identityName = str14;
        this.wxUnionFlag = i6;
        this.wxNickName = str15;
        this.wxHeadImgUrl = str16;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ MeProfile(boolean z2, String str, String str2, String str3, int i2, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i3, String str11, String str12, String str13, String str14, int i4, int i5, String str15, int i6, String str16, String str17, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        String str18;
        str18 = "";
        this((i7 & 1) != 0 ? false : z2, (i7 & 2) != 0 ? "" : str, (i7 & 4) != 0 ? "" : str2, (i7 & 8) != 0 ? "" : str3, (i7 & 16) != 0 ? -1 : i2, (i7 & 32) != 0 ? "" : str4, (i7 & 64) != 0 ? "" : str5, (i7 & 128) != 0 ? "" : str6, (i7 & 256) != 0 ? "" : str7, (i7 & 512) != 0 ? "" : str8, (i7 & 1024) != 0 ? "0" : str9, (i7 & 2048) != 0 ? "" : str10, (i7 & 4096) != 0 ? -1 : i3, (i7 & 8192) != 0 ? "" : str11, (i7 & 16384) != 0 ? str18 : str12, (i7 & 32768) != 0 ? str18 : str13, (i7 & 65536) != 0 ? str18 : str14, (i7 & 131072) != 0 ? 0 : i4, (i7 & 262144) != 0 ? -1 : i5, (i7 & 524288) != 0 ? str18 : str15, (i7 & 1048576) != 0 ? 0 : i6, (i7 & 2097152) != 0 ? str18 : str16, (i7 & 4194304) == 0 ? str17 : "");
    }
}
