package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 '2\u00020\u0001:\u0002&'BA\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB5\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J9\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J!\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f¨\u0006("}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeTeachingCase;", "", "seen1", "", "pageNum", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "rows", "", "Lcom/yddmi/doctor/entity/result/RowCase;", "total", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILjava/util/List;ILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IILjava/util/List;I)V", "getPageNum", "()I", "getPageSize", "getRows", "()Ljava/util/List;", "getTotal", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeTeachingCase {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int pageNum;
    private final int pageSize;

    @Nullable
    private final List<RowCase> rows;
    private final int total;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeTeachingCase$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeTeachingCase;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeTeachingCase> serializer() {
            return HomeTeachingCase$$serializer.INSTANCE;
        }
    }

    public HomeTeachingCase() {
        this(0, 0, (List) null, 0, 15, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeTeachingCase(int i2, int i3, int i4, List list, int i5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeTeachingCase$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.pageNum = 0;
        } else {
            this.pageNum = i3;
        }
        if ((i2 & 2) == 0) {
            this.pageSize = 1;
        } else {
            this.pageSize = i4;
        }
        if ((i2 & 4) == 0) {
            this.rows = null;
        } else {
            this.rows = list;
        }
        if ((i2 & 8) == 0) {
            this.total = 0;
        } else {
            this.total = i5;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HomeTeachingCase copy$default(HomeTeachingCase homeTeachingCase, int i2, int i3, List list, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = homeTeachingCase.pageNum;
        }
        if ((i5 & 2) != 0) {
            i3 = homeTeachingCase.pageSize;
        }
        if ((i5 & 4) != 0) {
            list = homeTeachingCase.rows;
        }
        if ((i5 & 8) != 0) {
            i4 = homeTeachingCase.total;
        }
        return homeTeachingCase.copy(i2, i3, list, i4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeTeachingCase self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.pageNum != 0) {
            output.encodeIntElement(serialDesc, 0, self.pageNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.pageSize != 1) {
            output.encodeIntElement(serialDesc, 1, self.pageSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.rows != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(RowCase$$serializer.INSTANCE), self.rows);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.total != 0) {
            output.encodeIntElement(serialDesc, 3, self.total);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getPageNum() {
        return this.pageNum;
    }

    /* renamed from: component2, reason: from getter */
    public final int getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final List<RowCase> component3() {
        return this.rows;
    }

    /* renamed from: component4, reason: from getter */
    public final int getTotal() {
        return this.total;
    }

    @NotNull
    public final HomeTeachingCase copy(int pageNum, int pageSize, @Nullable List<RowCase> rows, int total) {
        return new HomeTeachingCase(pageNum, pageSize, rows, total);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeTeachingCase)) {
            return false;
        }
        HomeTeachingCase homeTeachingCase = (HomeTeachingCase) other;
        return this.pageNum == homeTeachingCase.pageNum && this.pageSize == homeTeachingCase.pageSize && Intrinsics.areEqual(this.rows, homeTeachingCase.rows) && this.total == homeTeachingCase.total;
    }

    public final int getPageNum() {
        return this.pageNum;
    }

    public final int getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final List<RowCase> getRows() {
        return this.rows;
    }

    public final int getTotal() {
        return this.total;
    }

    public int hashCode() {
        int i2 = ((this.pageNum * 31) + this.pageSize) * 31;
        List<RowCase> list = this.rows;
        return ((i2 + (list == null ? 0 : list.hashCode())) * 31) + this.total;
    }

    @NotNull
    public String toString() {
        return "HomeTeachingCase(pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", rows=" + this.rows + ", total=" + this.total + ")";
    }

    public HomeTeachingCase(int i2, int i3, @Nullable List<RowCase> list, int i4) {
        this.pageNum = i2;
        this.pageSize = i3;
        this.rows = list;
        this.total = i4;
    }

    public /* synthetic */ HomeTeachingCase(int i2, int i3, List list, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0 : i2, (i5 & 2) != 0 ? 1 : i3, (i5 & 4) != 0 ? null : list, (i5 & 8) != 0 ? 0 : i4);
    }
}
