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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 P2\u00020\u0001:\u0002OPB¿\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0015\u001a\u00020\u0003\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017¢\u0006\u0002\u0010\u0018BÍ\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003¢\u0006\u0002\u0010\u0019J\u000b\u00100\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u0010\u00102\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u0010\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u0010\u00104\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u000b\u00105\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u00106\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u0010\u00107\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\t\u00108\u001a\u00020\u0003HÆ\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u000b\u0010<\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010=\u001a\u00020\u0003HÆ\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010?\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u0010\u0010@\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u001fJÖ\u0001\u0010A\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010BJ\u0013\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010F\u001a\u00020\u0003HÖ\u0001J\t\u0010G\u001a\u00020\u0005HÖ\u0001J!\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u00002\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020NHÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b%\u0010\u001fR\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b&\u0010\u001fR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b\u000e\u0010\u001fR\u001a\u0010\u0015\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010#\"\u0004\b(\u0010)R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b*\u0010\u001fR\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b+\u0010\u001fR\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b,\u0010\u001fR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001bR\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b.\u0010\u001fR\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010 \u001a\u0004\b/\u0010\u001f¨\u0006Q"}, d2 = {"Lcom/yddmi/doctor/entity/result/BannerData;", "", "seen1", "", "bannerPhotoName", "", "bannerPhotoUrl", "bannerTitle", "bannerType", "content", "contentType", "createTime", "createUser", "id", "isDelete", "pageTurns", "seriaNum", "status", "updateTime", "updateUser", "version", "itemIconType", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;I)V", "getBannerPhotoName", "()Ljava/lang/String;", "getBannerPhotoUrl", "getBannerTitle", "getBannerType", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getContent", "getContentType", "()I", "getCreateTime", "getCreateUser", "getId", "getItemIconType", "setItemIconType", "(I)V", "getPageTurns", "getSeriaNum", "getStatus", "getUpdateTime", "getUpdateUser", "getVersion", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;I)Lcom/yddmi/doctor/entity/result/BannerData;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class BannerData {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String bannerPhotoName;

    @Nullable
    private final String bannerPhotoUrl;

    @Nullable
    private final String bannerTitle;

    @Nullable
    private final Integer bannerType;

    @Nullable
    private final String content;
    private final int contentType;

    @Nullable
    private final String createTime;

    @Nullable
    private final Integer createUser;

    @Nullable
    private final Integer id;

    @Nullable
    private final Integer isDelete;
    private int itemIconType;

    @Nullable
    private final Integer pageTurns;

    @Nullable
    private final Integer seriaNum;

    @Nullable
    private final Integer status;

    @Nullable
    private final String updateTime;

    @Nullable
    private final Integer updateUser;

    @Nullable
    private final Integer version;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/BannerData$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/BannerData;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<BannerData> serializer() {
            return BannerData$$serializer.INSTANCE;
        }
    }

    public BannerData() {
        this((String) null, (String) null, (String) null, (Integer) null, (String) null, 0, (String) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, 0, 131071, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ BannerData(int i2, String str, String str2, String str3, Integer num, String str4, int i3, String str5, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, String str6, Integer num8, Integer num9, int i4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, BannerData$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.bannerPhotoName = "";
        } else {
            this.bannerPhotoName = str;
        }
        if ((i2 & 2) == 0) {
            this.bannerPhotoUrl = "";
        } else {
            this.bannerPhotoUrl = str2;
        }
        if ((i2 & 4) == 0) {
            this.bannerTitle = "";
        } else {
            this.bannerTitle = str3;
        }
        this.bannerType = (i2 & 8) == 0 ? -1 : num;
        if ((i2 & 16) == 0) {
            this.content = "";
        } else {
            this.content = str4;
        }
        if ((i2 & 32) == 0) {
            this.contentType = -1;
        } else {
            this.contentType = i3;
        }
        if ((i2 & 64) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str5;
        }
        this.createUser = (i2 & 128) == 0 ? -1 : num2;
        this.id = (i2 & 256) == 0 ? -1 : num3;
        this.isDelete = (i2 & 512) == 0 ? -1 : num4;
        this.pageTurns = (i2 & 1024) == 0 ? -1 : num5;
        this.seriaNum = (i2 & 2048) == 0 ? -1 : num6;
        this.status = (i2 & 4096) == 0 ? -1 : num7;
        if ((i2 & 8192) == 0) {
            this.updateTime = "";
        } else {
            this.updateTime = str6;
        }
        this.updateUser = (i2 & 16384) == 0 ? -1 : num8;
        this.version = (32768 & i2) == 0 ? -1 : num9;
        if ((i2 & 65536) == 0) {
            this.itemIconType = -1;
        } else {
            this.itemIconType = i4;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull BannerData self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Integer num7;
        Integer num8;
        Integer num9;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.bannerPhotoName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.bannerPhotoName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.bannerPhotoUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.bannerPhotoUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.bannerTitle, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.bannerTitle);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num = self.bannerType) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.bannerType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.contentType != -1) {
            output.encodeIntElement(serialDesc, 5, self.contentType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num2 = self.createUser) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.createUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || (num3 = self.id) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 8, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || (num4 = self.isDelete) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 9, IntSerializer.INSTANCE, self.isDelete);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || (num5 = self.pageTurns) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 10, IntSerializer.INSTANCE, self.pageTurns);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || (num6 = self.seriaNum) == null || num6.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 11, IntSerializer.INSTANCE, self.seriaNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || (num7 = self.status) == null || num7.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 12, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.updateTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.updateTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || (num8 = self.updateUser) == null || num8.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 14, IntSerializer.INSTANCE, self.updateUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || (num9 = self.version) == null || num9.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 15, IntSerializer.INSTANCE, self.version);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || self.itemIconType != -1) {
            output.encodeIntElement(serialDesc, 16, self.itemIconType);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getBannerPhotoName() {
        return this.bannerPhotoName;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final Integer getIsDelete() {
        return this.isDelete;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final Integer getPageTurns() {
        return this.pageTurns;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final Integer getSeriaNum() {
        return this.seriaNum;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getUpdateTime() {
        return this.updateTime;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final Integer getUpdateUser() {
        return this.updateUser;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final Integer getVersion() {
        return this.version;
    }

    /* renamed from: component17, reason: from getter */
    public final int getItemIconType() {
        return this.itemIconType;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getBannerPhotoUrl() {
        return this.bannerPhotoUrl;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getBannerTitle() {
        return this.bannerTitle;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getBannerType() {
        return this.bannerType;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* renamed from: component6, reason: from getter */
    public final int getContentType() {
        return this.contentType;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final Integer getCreateUser() {
        return this.createUser;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @NotNull
    public final BannerData copy(@Nullable String bannerPhotoName, @Nullable String bannerPhotoUrl, @Nullable String bannerTitle, @Nullable Integer bannerType, @Nullable String content, int contentType, @Nullable String createTime, @Nullable Integer createUser, @Nullable Integer id, @Nullable Integer isDelete, @Nullable Integer pageTurns, @Nullable Integer seriaNum, @Nullable Integer status, @Nullable String updateTime, @Nullable Integer updateUser, @Nullable Integer version, int itemIconType) {
        return new BannerData(bannerPhotoName, bannerPhotoUrl, bannerTitle, bannerType, content, contentType, createTime, createUser, id, isDelete, pageTurns, seriaNum, status, updateTime, updateUser, version, itemIconType);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BannerData)) {
            return false;
        }
        BannerData bannerData = (BannerData) other;
        return Intrinsics.areEqual(this.bannerPhotoName, bannerData.bannerPhotoName) && Intrinsics.areEqual(this.bannerPhotoUrl, bannerData.bannerPhotoUrl) && Intrinsics.areEqual(this.bannerTitle, bannerData.bannerTitle) && Intrinsics.areEqual(this.bannerType, bannerData.bannerType) && Intrinsics.areEqual(this.content, bannerData.content) && this.contentType == bannerData.contentType && Intrinsics.areEqual(this.createTime, bannerData.createTime) && Intrinsics.areEqual(this.createUser, bannerData.createUser) && Intrinsics.areEqual(this.id, bannerData.id) && Intrinsics.areEqual(this.isDelete, bannerData.isDelete) && Intrinsics.areEqual(this.pageTurns, bannerData.pageTurns) && Intrinsics.areEqual(this.seriaNum, bannerData.seriaNum) && Intrinsics.areEqual(this.status, bannerData.status) && Intrinsics.areEqual(this.updateTime, bannerData.updateTime) && Intrinsics.areEqual(this.updateUser, bannerData.updateUser) && Intrinsics.areEqual(this.version, bannerData.version) && this.itemIconType == bannerData.itemIconType;
    }

    @Nullable
    public final String getBannerPhotoName() {
        return this.bannerPhotoName;
    }

    @Nullable
    public final String getBannerPhotoUrl() {
        return this.bannerPhotoUrl;
    }

    @Nullable
    public final String getBannerTitle() {
        return this.bannerTitle;
    }

    @Nullable
    public final Integer getBannerType() {
        return this.bannerType;
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    public final int getContentType() {
        return this.contentType;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final Integer getCreateUser() {
        return this.createUser;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    public final int getItemIconType() {
        return this.itemIconType;
    }

    @Nullable
    public final Integer getPageTurns() {
        return this.pageTurns;
    }

    @Nullable
    public final Integer getSeriaNum() {
        return this.seriaNum;
    }

    @Nullable
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    public final String getUpdateTime() {
        return this.updateTime;
    }

    @Nullable
    public final Integer getUpdateUser() {
        return this.updateUser;
    }

    @Nullable
    public final Integer getVersion() {
        return this.version;
    }

    public int hashCode() {
        String str = this.bannerPhotoName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.bannerPhotoUrl;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.bannerTitle;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Integer num = this.bannerType;
        int iHashCode4 = (iHashCode3 + (num == null ? 0 : num.hashCode())) * 31;
        String str4 = this.content;
        int iHashCode5 = (((iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.contentType) * 31;
        String str5 = this.createTime;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        Integer num2 = this.createUser;
        int iHashCode7 = (iHashCode6 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.id;
        int iHashCode8 = (iHashCode7 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.isDelete;
        int iHashCode9 = (iHashCode8 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.pageTurns;
        int iHashCode10 = (iHashCode9 + (num5 == null ? 0 : num5.hashCode())) * 31;
        Integer num6 = this.seriaNum;
        int iHashCode11 = (iHashCode10 + (num6 == null ? 0 : num6.hashCode())) * 31;
        Integer num7 = this.status;
        int iHashCode12 = (iHashCode11 + (num7 == null ? 0 : num7.hashCode())) * 31;
        String str6 = this.updateTime;
        int iHashCode13 = (iHashCode12 + (str6 == null ? 0 : str6.hashCode())) * 31;
        Integer num8 = this.updateUser;
        int iHashCode14 = (iHashCode13 + (num8 == null ? 0 : num8.hashCode())) * 31;
        Integer num9 = this.version;
        return ((iHashCode14 + (num9 != null ? num9.hashCode() : 0)) * 31) + this.itemIconType;
    }

    @Nullable
    public final Integer isDelete() {
        return this.isDelete;
    }

    public final void setItemIconType(int i2) {
        this.itemIconType = i2;
    }

    @NotNull
    public String toString() {
        return "BannerData(bannerPhotoName=" + this.bannerPhotoName + ", bannerPhotoUrl=" + this.bannerPhotoUrl + ", bannerTitle=" + this.bannerTitle + ", bannerType=" + this.bannerType + ", content=" + this.content + ", contentType=" + this.contentType + ", createTime=" + this.createTime + ", createUser=" + this.createUser + ", id=" + this.id + ", isDelete=" + this.isDelete + ", pageTurns=" + this.pageTurns + ", seriaNum=" + this.seriaNum + ", status=" + this.status + ", updateTime=" + this.updateTime + ", updateUser=" + this.updateUser + ", version=" + this.version + ", itemIconType=" + this.itemIconType + ")";
    }

    public BannerData(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Integer num, @Nullable String str4, int i2, @Nullable String str5, @Nullable Integer num2, @Nullable Integer num3, @Nullable Integer num4, @Nullable Integer num5, @Nullable Integer num6, @Nullable Integer num7, @Nullable String str6, @Nullable Integer num8, @Nullable Integer num9, int i3) {
        this.bannerPhotoName = str;
        this.bannerPhotoUrl = str2;
        this.bannerTitle = str3;
        this.bannerType = num;
        this.content = str4;
        this.contentType = i2;
        this.createTime = str5;
        this.createUser = num2;
        this.id = num3;
        this.isDelete = num4;
        this.pageTurns = num5;
        this.seriaNum = num6;
        this.status = num7;
        this.updateTime = str6;
        this.updateUser = num8;
        this.version = num9;
        this.itemIconType = i3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ BannerData(String str, String str2, String str3, Integer num, String str4, int i2, String str5, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6, Integer num7, String str6, Integer num8, Integer num9, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        Integer num10;
        String str7 = (i4 & 1) != 0 ? "" : str;
        String str8 = (i4 & 2) != 0 ? "" : str2;
        String str9 = (i4 & 4) != 0 ? "" : str3;
        Integer num11 = (i4 & 8) != 0 ? -1 : num;
        String str10 = (i4 & 16) != 0 ? "" : str4;
        int i6 = (i4 & 32) != 0 ? -1 : i2;
        String str11 = (i4 & 64) != 0 ? "" : str5;
        Integer num12 = (i4 & 128) != 0 ? -1 : num2;
        Integer num13 = (i4 & 256) != 0 ? -1 : num3;
        Integer num14 = (i4 & 512) != 0 ? -1 : num4;
        Integer num15 = (i4 & 1024) != 0 ? -1 : num5;
        Integer num16 = (i4 & 2048) != 0 ? -1 : num6;
        Integer num17 = (i4 & 4096) != 0 ? -1 : num7;
        String str12 = (i4 & 8192) == 0 ? str6 : "";
        if ((i4 & 16384) != 0) {
            i5 = -1;
            num10 = -1;
        } else {
            i5 = -1;
            num10 = num8;
        }
        this(str7, str8, str9, num11, str10, i6, str11, num12, num13, num14, num15, num16, num17, str12, num10, (i4 & 32768) != 0 ? Integer.valueOf(i5) : num9, (i4 & 65536) == 0 ? i3 : i5);
    }
}
