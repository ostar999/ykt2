package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.psychiatrygarden.utils.Constants;
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

@Keep
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 :2\u00020\u0001:\u00029:B]\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fBU\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\f¢\u0006\u0002\u0010\u0010J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\u0011\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\fHÆ\u0003J[\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\fHÆ\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00100\u001a\u00020\u0003HÖ\u0001J\t\u00101\u001a\u00020\u0006HÖ\u0001J!\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u000208HÇ\u0001R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0012\"\u0004\b\u001e\u0010\u0014R\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0012\"\u0004\b \u0010\u0014R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0016\"\u0004\b\"\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0012\"\u0004\b$\u0010\u0014¨\u0006;"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeGuideReq;", "", "seen1", "", "type", "title", "", Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "categoryId", "pageNum", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "labels", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;IIILjava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Ljava/lang/String;IIILjava/util/List;)V", "getCategoryId", "()I", "setCategoryId", "(I)V", "getKeyword", "()Ljava/lang/String;", "setKeyword", "(Ljava/lang/String;)V", "getLabels", "()Ljava/util/List;", "setLabels", "(Ljava/util/List;)V", "getPageNum", "setPageNum", "getPageSize", "setPageSize", "getTitle", "setTitle", "getType", "setType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeGuideReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private int categoryId;

    @Nullable
    private String keyword;

    @Nullable
    private List<String> labels;
    private int pageNum;
    private int pageSize;

    @Nullable
    private String title;
    private int type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeGuideReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/HomeGuideReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeGuideReq> serializer() {
            return HomeGuideReq$$serializer.INSTANCE;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeGuideReq(int i2, int i3, String str, String str2, int i4, int i5, int i6, List list, SerializationConstructorMarker serializationConstructorMarker) {
        if (1 != (i2 & 1)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 1, HomeGuideReq$$serializer.INSTANCE.getDescriptor());
        }
        this.type = i3;
        if ((i2 & 2) == 0) {
            this.title = null;
        } else {
            this.title = str;
        }
        if ((i2 & 4) == 0) {
            this.keyword = null;
        } else {
            this.keyword = str2;
        }
        if ((i2 & 8) == 0) {
            this.categoryId = -1;
        } else {
            this.categoryId = i4;
        }
        if ((i2 & 16) == 0) {
            this.pageNum = 0;
        } else {
            this.pageNum = i5;
        }
        if ((i2 & 32) == 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = i6;
        }
        if ((i2 & 64) == 0) {
            this.labels = null;
        } else {
            this.labels = list;
        }
    }

    public static /* synthetic */ HomeGuideReq copy$default(HomeGuideReq homeGuideReq, int i2, String str, String str2, int i3, int i4, int i5, List list, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = homeGuideReq.type;
        }
        if ((i6 & 2) != 0) {
            str = homeGuideReq.title;
        }
        String str3 = str;
        if ((i6 & 4) != 0) {
            str2 = homeGuideReq.keyword;
        }
        String str4 = str2;
        if ((i6 & 8) != 0) {
            i3 = homeGuideReq.categoryId;
        }
        int i7 = i3;
        if ((i6 & 16) != 0) {
            i4 = homeGuideReq.pageNum;
        }
        int i8 = i4;
        if ((i6 & 32) != 0) {
            i5 = homeGuideReq.pageSize;
        }
        int i9 = i5;
        if ((i6 & 64) != 0) {
            list = homeGuideReq.labels;
        }
        return homeGuideReq.copy(i2, str3, str4, i7, i8, i9, list);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeGuideReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.type);
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.title != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.title);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.keyword != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.keyword);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.categoryId != -1) {
            output.encodeIntElement(serialDesc, 3, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.pageNum != 0) {
            output.encodeIntElement(serialDesc, 4, self.pageNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || self.pageSize != 10) {
            output.encodeIntElement(serialDesc, 5, self.pageSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.labels != null) {
            output.encodeNullableSerializableElement(serialDesc, 6, new ArrayListSerializer(StringSerializer.INSTANCE), self.labels);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getKeyword() {
        return this.keyword;
    }

    /* renamed from: component4, reason: from getter */
    public final int getCategoryId() {
        return this.categoryId;
    }

    /* renamed from: component5, reason: from getter */
    public final int getPageNum() {
        return this.pageNum;
    }

    /* renamed from: component6, reason: from getter */
    public final int getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final List<String> component7() {
        return this.labels;
    }

    @NotNull
    public final HomeGuideReq copy(int type, @Nullable String title, @Nullable String keyword, int categoryId, int pageNum, int pageSize, @Nullable List<String> labels) {
        return new HomeGuideReq(type, title, keyword, categoryId, pageNum, pageSize, labels);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeGuideReq)) {
            return false;
        }
        HomeGuideReq homeGuideReq = (HomeGuideReq) other;
        return this.type == homeGuideReq.type && Intrinsics.areEqual(this.title, homeGuideReq.title) && Intrinsics.areEqual(this.keyword, homeGuideReq.keyword) && this.categoryId == homeGuideReq.categoryId && this.pageNum == homeGuideReq.pageNum && this.pageSize == homeGuideReq.pageSize && Intrinsics.areEqual(this.labels, homeGuideReq.labels);
    }

    public final int getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final String getKeyword() {
        return this.keyword;
    }

    @Nullable
    public final List<String> getLabels() {
        return this.labels;
    }

    public final int getPageNum() {
        return this.pageNum;
    }

    public final int getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        int i2 = this.type * 31;
        String str = this.title;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.keyword;
        int iHashCode2 = (((((((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.categoryId) * 31) + this.pageNum) * 31) + this.pageSize) * 31;
        List<String> list = this.labels;
        return iHashCode2 + (list != null ? list.hashCode() : 0);
    }

    public final void setCategoryId(int i2) {
        this.categoryId = i2;
    }

    public final void setKeyword(@Nullable String str) {
        this.keyword = str;
    }

    public final void setLabels(@Nullable List<String> list) {
        this.labels = list;
    }

    public final void setPageNum(int i2) {
        this.pageNum = i2;
    }

    public final void setPageSize(int i2) {
        this.pageSize = i2;
    }

    public final void setTitle(@Nullable String str) {
        this.title = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    @NotNull
    public String toString() {
        return "HomeGuideReq(type=" + this.type + ", title=" + this.title + ", keyword=" + this.keyword + ", categoryId=" + this.categoryId + ", pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", labels=" + this.labels + ")";
    }

    public HomeGuideReq(int i2, @Nullable String str, @Nullable String str2, int i3, int i4, int i5, @Nullable List<String> list) {
        this.type = i2;
        this.title = str;
        this.keyword = str2;
        this.categoryId = i3;
        this.pageNum = i4;
        this.pageSize = i5;
        this.labels = list;
    }

    public /* synthetic */ HomeGuideReq(int i2, String str, String str2, int i3, int i4, int i5, List list, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i6 & 2) != 0 ? null : str, (i6 & 4) != 0 ? null : str2, (i6 & 8) != 0 ? -1 : i3, (i6 & 16) != 0 ? 0 : i4, (i6 & 32) != 0 ? 10 : i5, (i6 & 64) == 0 ? list : null);
    }
}
