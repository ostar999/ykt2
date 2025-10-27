package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
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
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 32\u00020\u0001:\u000223Bi\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fBe\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0010J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012J\u000b\u0010 \u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0012Jn\u0010$\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010%J\u0013\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010)\u001a\u00020\u0003HÖ\u0001J\t\u0010*\u001a\u00020\u0006HÖ\u0001J!\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0017\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001b\u0010\u0012¨\u00064"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeGuideListItem;", "", "seen1", "", "categoryId", "categoryName", "", "course", "id", AliyunLogCommon.Module.PUBLISHER, "releaseTime", "title", "type", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V", "getCategoryId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCategoryName", "()Ljava/lang/String;", "getCourse", "getId", "getPublisher", "getReleaseTime", "getTitle", "getType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/HomeGuideListItem;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeGuideListItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer categoryId;

    @Nullable
    private final String categoryName;

    @Nullable
    private final String course;

    @Nullable
    private final Integer id;

    @Nullable
    private final String publisher;

    @Nullable
    private final String releaseTime;

    @Nullable
    private final String title;

    @Nullable
    private final Integer type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeGuideListItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeGuideListItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeGuideListItem> serializer() {
            return HomeGuideListItem$$serializer.INSTANCE;
        }
    }

    public HomeGuideListItem() {
        this((Integer) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (String) null, (Integer) null, 255, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeGuideListItem(int i2, Integer num, String str, String str2, Integer num2, String str3, String str4, String str5, Integer num3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeGuideListItem$$serializer.INSTANCE.getDescriptor());
        }
        this.categoryId = (i2 & 1) == 0 ? -1 : num;
        if ((i2 & 2) == 0) {
            this.categoryName = "";
        } else {
            this.categoryName = str;
        }
        if ((i2 & 4) == 0) {
            this.course = "";
        } else {
            this.course = str2;
        }
        if ((i2 & 8) == 0) {
            this.id = -1;
        } else {
            this.id = num2;
        }
        if ((i2 & 16) == 0) {
            this.publisher = "";
        } else {
            this.publisher = str3;
        }
        if ((i2 & 32) == 0) {
            this.releaseTime = "";
        } else {
            this.releaseTime = str4;
        }
        if ((i2 & 64) == 0) {
            this.title = "";
        } else {
            this.title = str5;
        }
        if ((i2 & 128) == 0) {
            this.type = -1;
        } else {
            this.type = num3;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeGuideListItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.categoryId) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.categoryName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.categoryName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.course, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.course);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num2 = self.id) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.publisher, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.publisher);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.releaseTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.releaseTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.title, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.title);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num3 = self.type) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.type);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getCategoryName() {
        return this.categoryName;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCourse() {
        return this.course;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getPublisher() {
        return this.publisher;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getReleaseTime() {
        return this.releaseTime;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final Integer getType() {
        return this.type;
    }

    @NotNull
    public final HomeGuideListItem copy(@Nullable Integer categoryId, @Nullable String categoryName, @Nullable String course, @Nullable Integer id, @Nullable String publisher, @Nullable String releaseTime, @Nullable String title, @Nullable Integer type) {
        return new HomeGuideListItem(categoryId, categoryName, course, id, publisher, releaseTime, title, type);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeGuideListItem)) {
            return false;
        }
        HomeGuideListItem homeGuideListItem = (HomeGuideListItem) other;
        return Intrinsics.areEqual(this.categoryId, homeGuideListItem.categoryId) && Intrinsics.areEqual(this.categoryName, homeGuideListItem.categoryName) && Intrinsics.areEqual(this.course, homeGuideListItem.course) && Intrinsics.areEqual(this.id, homeGuideListItem.id) && Intrinsics.areEqual(this.publisher, homeGuideListItem.publisher) && Intrinsics.areEqual(this.releaseTime, homeGuideListItem.releaseTime) && Intrinsics.areEqual(this.title, homeGuideListItem.title) && Intrinsics.areEqual(this.type, homeGuideListItem.type);
    }

    @Nullable
    public final Integer getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final String getCategoryName() {
        return this.categoryName;
    }

    @Nullable
    public final String getCourse() {
        return this.course;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    public final String getPublisher() {
        return this.publisher;
    }

    @Nullable
    public final String getReleaseTime() {
        return this.releaseTime;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final Integer getType() {
        return this.type;
    }

    public int hashCode() {
        Integer num = this.categoryId;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.categoryName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.course;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num2 = this.id;
        int iHashCode4 = (iHashCode3 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str3 = this.publisher;
        int iHashCode5 = (iHashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.releaseTime;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.title;
        int iHashCode7 = (iHashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31;
        Integer num3 = this.type;
        return iHashCode7 + (num3 != null ? num3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeGuideListItem(categoryId=" + this.categoryId + ", categoryName=" + this.categoryName + ", course=" + this.course + ", id=" + this.id + ", publisher=" + this.publisher + ", releaseTime=" + this.releaseTime + ", title=" + this.title + ", type=" + this.type + ")";
    }

    public HomeGuideListItem(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable Integer num2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable Integer num3) {
        this.categoryId = num;
        this.categoryName = str;
        this.course = str2;
        this.id = num2;
        this.publisher = str3;
        this.releaseTime = str4;
        this.title = str5;
        this.type = num3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ HomeGuideListItem(Integer num, String str, String str2, Integer num2, String str3, String str4, String str5, Integer num3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? num : num, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? num : num2, (i2 & 16) != 0 ? "" : str3, (i2 & 32) != 0 ? "" : str4, (i2 & 64) == 0 ? str5 : "", (i2 & 128) == 0 ? num3 : -1);
    }
}
