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
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 *2\u00020\u0001:\u0002)*BE\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0010\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB9\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007HÆ\u0003J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0014JB\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\t\u0010 \u001a\u00020!HÖ\u0001J!\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014¨\u0006+"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeAtlas;", "", "seen1", "", "pageNum", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "rows", "", "Lcom/yddmi/doctor/entity/result/AtlasRow;", "total", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIILjava/util/List;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IILjava/util/List;Ljava/lang/Integer;)V", "getPageNum", "()I", "getPageSize", "getRows", "()Ljava/util/List;", "getTotal", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "copy", "(IILjava/util/List;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/HomeAtlas;", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeAtlas {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int pageNum;
    private final int pageSize;

    @Nullable
    private final List<AtlasRow> rows;

    @Nullable
    private final Integer total;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeAtlas$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeAtlas;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeAtlas> serializer() {
            return HomeAtlas$$serializer.INSTANCE;
        }
    }

    public HomeAtlas() {
        this(0, 0, (List) null, (Integer) null, 15, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeAtlas(int i2, int i3, int i4, List list, Integer num, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeAtlas$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.pageNum = 0;
        } else {
            this.pageNum = i3;
        }
        if ((i2 & 2) == 0) {
            this.pageSize = 0;
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
            this.total = num;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HomeAtlas copy$default(HomeAtlas homeAtlas, int i2, int i3, List list, Integer num, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = homeAtlas.pageNum;
        }
        if ((i4 & 2) != 0) {
            i3 = homeAtlas.pageSize;
        }
        if ((i4 & 4) != 0) {
            list = homeAtlas.rows;
        }
        if ((i4 & 8) != 0) {
            num = homeAtlas.total;
        }
        return homeAtlas.copy(i2, i3, list, num);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeAtlas self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.pageNum != 0) {
            output.encodeIntElement(serialDesc, 0, self.pageNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.pageSize != 0) {
            output.encodeIntElement(serialDesc, 1, self.pageSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.rows != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(BuiltinSerializersKt.getNullable(AtlasRow$$serializer.INSTANCE)), self.rows);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num = self.total) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.total);
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
    public final List<AtlasRow> component3() {
        return this.rows;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getTotal() {
        return this.total;
    }

    @NotNull
    public final HomeAtlas copy(int pageNum, int pageSize, @Nullable List<AtlasRow> rows, @Nullable Integer total) {
        return new HomeAtlas(pageNum, pageSize, rows, total);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeAtlas)) {
            return false;
        }
        HomeAtlas homeAtlas = (HomeAtlas) other;
        return this.pageNum == homeAtlas.pageNum && this.pageSize == homeAtlas.pageSize && Intrinsics.areEqual(this.rows, homeAtlas.rows) && Intrinsics.areEqual(this.total, homeAtlas.total);
    }

    public final int getPageNum() {
        return this.pageNum;
    }

    public final int getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final List<AtlasRow> getRows() {
        return this.rows;
    }

    @Nullable
    public final Integer getTotal() {
        return this.total;
    }

    public int hashCode() {
        int i2 = ((this.pageNum * 31) + this.pageSize) * 31;
        List<AtlasRow> list = this.rows;
        int iHashCode = (i2 + (list == null ? 0 : list.hashCode())) * 31;
        Integer num = this.total;
        return iHashCode + (num != null ? num.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeAtlas(pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", rows=" + this.rows + ", total=" + this.total + ")";
    }

    public HomeAtlas(int i2, int i3, @Nullable List<AtlasRow> list, @Nullable Integer num) {
        this.pageNum = i2;
        this.pageSize = i3;
        this.rows = list;
        this.total = num;
    }

    public /* synthetic */ HomeAtlas(int i2, int i3, List list, Integer num, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i2, (i4 & 2) != 0 ? 0 : i3, (i4 & 4) != 0 ? null : list, (i4 & 8) != 0 ? 0 : num);
    }
}
