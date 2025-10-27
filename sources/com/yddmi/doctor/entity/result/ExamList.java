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
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0002()BI\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0010\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fB=\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0017\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007HÆ\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u000fJF\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001J!\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u0011\u0010\u000fR\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u0014\u0010\u000f¨\u0006*"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamList;", "", "seen1", "", "pageNum", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "rows", "", "Lcom/yddmi/doctor/entity/result/ExamItem;", "total", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/Integer;)V", "getPageNum", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPageSize", "getRows", "()Ljava/util/List;", "getTotal", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/Integer;)Lcom/yddmi/doctor/entity/result/ExamList;", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamList {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer pageNum;

    @Nullable
    private final Integer pageSize;

    @Nullable
    private final List<ExamItem> rows;

    @Nullable
    private final Integer total;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamList$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamList;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamList> serializer() {
            return ExamList$$serializer.INSTANCE;
        }
    }

    public ExamList() {
        this((Integer) null, (Integer) null, (List) null, (Integer) null, 15, (DefaultConstructorMarker) null);
    }

    public ExamList(@Nullable Integer num, @Nullable Integer num2, @Nullable List<ExamItem> list, @Nullable Integer num3) {
        this.pageNum = num;
        this.pageSize = num2;
        this.rows = list;
        this.total = num3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExamList copy$default(ExamList examList, Integer num, Integer num2, List list, Integer num3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = examList.pageNum;
        }
        if ((i2 & 2) != 0) {
            num2 = examList.pageSize;
        }
        if ((i2 & 4) != 0) {
            list = examList.rows;
        }
        if ((i2 & 8) != 0) {
            num3 = examList.total;
        }
        return examList.copy(num, num2, list, num3);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamList self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.pageNum) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.pageNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || (num2 = self.pageSize) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 1, IntSerializer.INSTANCE, self.pageSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.rows != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamItem$$serializer.INSTANCE)), self.rows);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || (num3 = self.total) == null || num3.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 3, IntSerializer.INSTANCE, self.total);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getPageNum() {
        return this.pageNum;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final Integer getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final List<ExamItem> component3() {
        return this.rows;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Integer getTotal() {
        return this.total;
    }

    @NotNull
    public final ExamList copy(@Nullable Integer pageNum, @Nullable Integer pageSize, @Nullable List<ExamItem> rows, @Nullable Integer total) {
        return new ExamList(pageNum, pageSize, rows, total);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamList)) {
            return false;
        }
        ExamList examList = (ExamList) other;
        return Intrinsics.areEqual(this.pageNum, examList.pageNum) && Intrinsics.areEqual(this.pageSize, examList.pageSize) && Intrinsics.areEqual(this.rows, examList.rows) && Intrinsics.areEqual(this.total, examList.total);
    }

    @Nullable
    public final Integer getPageNum() {
        return this.pageNum;
    }

    @Nullable
    public final Integer getPageSize() {
        return this.pageSize;
    }

    @Nullable
    public final List<ExamItem> getRows() {
        return this.rows;
    }

    @Nullable
    public final Integer getTotal() {
        return this.total;
    }

    public int hashCode() {
        Integer num = this.pageNum;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.pageSize;
        int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        List<ExamItem> list = this.rows;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        Integer num3 = this.total;
        return iHashCode3 + (num3 != null ? num3.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ExamList(pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", rows=" + this.rows + ", total=" + this.total + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamList(int i2, Integer num, Integer num2, List list, Integer num3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamList$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.pageNum = 0;
        } else {
            this.pageNum = num;
        }
        if ((i2 & 2) == 0) {
            this.pageSize = 0;
        } else {
            this.pageSize = num2;
        }
        if ((i2 & 4) == 0) {
            this.rows = null;
        } else {
            this.rows = list;
        }
        if ((i2 & 8) == 0) {
            this.total = 0;
        } else {
            this.total = num3;
        }
    }

    public /* synthetic */ ExamList(Integer num, Integer num2, List list, Integer num3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : num, (i2 & 2) != 0 ? 0 : num2, (i2 & 4) != 0 ? null : list, (i2 & 8) != 0 ? 0 : num3);
    }
}
