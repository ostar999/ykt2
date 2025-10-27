package com.yddmi.doctor.entity.result;

import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.List;
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
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 62\u00020\u0001:\u000256Bw\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011B_\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0005¢\u0006\u0002\u0010\u0012J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J\u0011\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000bHÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0005HÆ\u0003Jo\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0005HÆ\u0001J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010,\u001a\u00020\u0003HÖ\u0001J\t\u0010-\u001a\u00020\u0005HÖ\u0001J!\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014¨\u00067"}, d2 = {"Lcom/yddmi/doctor/entity/result/WxUserInfo;", "", "seen1", "", "city", "", am.O, "headimgurl", "nickname", "openid", "privilege", "", "province", CommonNetImpl.SEX, "unionid", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;ILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;ILjava/lang/String;)V", "getCity", "()Ljava/lang/String;", "getCountry", "getHeadimgurl", "getNickname", "getOpenid", "getPrivilege", "()Ljava/util/List;", "getProvince", "getSex", "()I", "getUnionid", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class WxUserInfo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String city;

    @NotNull
    private final String country;

    @Nullable
    private final String headimgurl;

    @NotNull
    private final String nickname;

    @NotNull
    private final String openid;

    @Nullable
    private final List<String> privilege;

    @Nullable
    private final String province;
    private final int sex;

    @NotNull
    private final String unionid;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/WxUserInfo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/WxUserInfo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<WxUserInfo> serializer() {
            return WxUserInfo$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ WxUserInfo(int i2, String str, String str2, String str3, String str4, String str5, List list, String str6, int i3, String str7, SerializationConstructorMarker serializationConstructorMarker) {
        if (411 != (i2 & 411)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 411, WxUserInfo$$serializer.INSTANCE.getDescriptor());
        }
        this.city = str;
        this.country = str2;
        if ((i2 & 4) == 0) {
            this.headimgurl = "";
        } else {
            this.headimgurl = str3;
        }
        this.nickname = str4;
        this.openid = str5;
        if ((i2 & 32) == 0) {
            this.privilege = null;
        } else {
            this.privilege = list;
        }
        if ((i2 & 64) == 0) {
            this.province = "";
        } else {
            this.province = str6;
        }
        this.sex = i3;
        this.unionid = str7;
    }

    @JvmStatic
    public static final void write$Self(@NotNull WxUserInfo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeStringElement(serialDesc, 0, self.city);
        output.encodeStringElement(serialDesc, 1, self.country);
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.headimgurl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.headimgurl);
        }
        output.encodeStringElement(serialDesc, 3, self.nickname);
        output.encodeStringElement(serialDesc, 4, self.openid);
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.privilege != null) {
            output.encodeNullableSerializableElement(serialDesc, 5, new ArrayListSerializer(StringSerializer.INSTANCE), self.privilege);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.province, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.province);
        }
        output.encodeIntElement(serialDesc, 7, self.sex);
        output.encodeStringElement(serialDesc, 8, self.unionid);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getCity() {
        return this.city;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getCountry() {
        return this.country;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getHeadimgurl() {
        return this.headimgurl;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getOpenid() {
        return this.openid;
    }

    @Nullable
    public final List<String> component6() {
        return this.privilege;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getProvince() {
        return this.province;
    }

    /* renamed from: component8, reason: from getter */
    public final int getSex() {
        return this.sex;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getUnionid() {
        return this.unionid;
    }

    @NotNull
    public final WxUserInfo copy(@NotNull String city, @NotNull String country, @Nullable String headimgurl, @NotNull String nickname, @NotNull String openid, @Nullable List<String> privilege, @Nullable String province, int sex, @NotNull String unionid) {
        Intrinsics.checkNotNullParameter(city, "city");
        Intrinsics.checkNotNullParameter(country, "country");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(openid, "openid");
        Intrinsics.checkNotNullParameter(unionid, "unionid");
        return new WxUserInfo(city, country, headimgurl, nickname, openid, privilege, province, sex, unionid);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WxUserInfo)) {
            return false;
        }
        WxUserInfo wxUserInfo = (WxUserInfo) other;
        return Intrinsics.areEqual(this.city, wxUserInfo.city) && Intrinsics.areEqual(this.country, wxUserInfo.country) && Intrinsics.areEqual(this.headimgurl, wxUserInfo.headimgurl) && Intrinsics.areEqual(this.nickname, wxUserInfo.nickname) && Intrinsics.areEqual(this.openid, wxUserInfo.openid) && Intrinsics.areEqual(this.privilege, wxUserInfo.privilege) && Intrinsics.areEqual(this.province, wxUserInfo.province) && this.sex == wxUserInfo.sex && Intrinsics.areEqual(this.unionid, wxUserInfo.unionid);
    }

    @NotNull
    public final String getCity() {
        return this.city;
    }

    @NotNull
    public final String getCountry() {
        return this.country;
    }

    @Nullable
    public final String getHeadimgurl() {
        return this.headimgurl;
    }

    @NotNull
    public final String getNickname() {
        return this.nickname;
    }

    @NotNull
    public final String getOpenid() {
        return this.openid;
    }

    @Nullable
    public final List<String> getPrivilege() {
        return this.privilege;
    }

    @Nullable
    public final String getProvince() {
        return this.province;
    }

    public final int getSex() {
        return this.sex;
    }

    @NotNull
    public final String getUnionid() {
        return this.unionid;
    }

    public int hashCode() {
        int iHashCode = ((this.city.hashCode() * 31) + this.country.hashCode()) * 31;
        String str = this.headimgurl;
        int iHashCode2 = (((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.nickname.hashCode()) * 31) + this.openid.hashCode()) * 31;
        List<String> list = this.privilege;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        String str2 = this.province;
        return ((((iHashCode3 + (str2 != null ? str2.hashCode() : 0)) * 31) + this.sex) * 31) + this.unionid.hashCode();
    }

    @NotNull
    public String toString() {
        return "WxUserInfo(city=" + this.city + ", country=" + this.country + ", headimgurl=" + this.headimgurl + ", nickname=" + this.nickname + ", openid=" + this.openid + ", privilege=" + this.privilege + ", province=" + this.province + ", sex=" + this.sex + ", unionid=" + this.unionid + ")";
    }

    public WxUserInfo(@NotNull String city, @NotNull String country, @Nullable String str, @NotNull String nickname, @NotNull String openid, @Nullable List<String> list, @Nullable String str2, int i2, @NotNull String unionid) {
        Intrinsics.checkNotNullParameter(city, "city");
        Intrinsics.checkNotNullParameter(country, "country");
        Intrinsics.checkNotNullParameter(nickname, "nickname");
        Intrinsics.checkNotNullParameter(openid, "openid");
        Intrinsics.checkNotNullParameter(unionid, "unionid");
        this.city = city;
        this.country = country;
        this.headimgurl = str;
        this.nickname = nickname;
        this.openid = openid;
        this.privilege = list;
        this.province = str2;
        this.sex = i2;
        this.unionid = unionid;
    }

    public /* synthetic */ WxUserInfo(String str, String str2, String str3, String str4, String str5, List list, String str6, int i2, String str7, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? "" : str3, str4, str5, (i3 & 32) != 0 ? null : list, (i3 & 64) != 0 ? "" : str6, i2, str7);
    }
}
