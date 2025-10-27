package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
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
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 )2\u00020\u0001:\u0002()Bi\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rB_\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u000eJ\u0011\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003Jc\u0010\u001a\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001J!\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'HÇ\u0001R\u0019\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010¨\u0006*"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeDaskResult;", "", "seen1", "", "kingKongArea", "", "Lcom/yddmi/doctor/entity/result/HomeDaskItem;", "kingKongAreaUser", "teachingCenter", "brushQuestionBank", "brushQuestionBankUser", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getBrushQuestionBank", "()Ljava/util/List;", "getBrushQuestionBankUser", "getKingKongArea", "getKingKongAreaUser", "getTeachingCenter", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeDaskResult {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<HomeDaskItem> brushQuestionBank;

    @Nullable
    private final List<HomeDaskItem> brushQuestionBankUser;

    @Nullable
    private final List<HomeDaskItem> kingKongArea;

    @Nullable
    private final List<HomeDaskItem> kingKongAreaUser;

    @Nullable
    private final List<HomeDaskItem> teachingCenter;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeDaskResult$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeDaskResult;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeDaskResult> serializer() {
            return HomeDaskResult$$serializer.INSTANCE;
        }
    }

    public HomeDaskResult() {
        this((List) null, (List) null, (List) null, (List) null, (List) null, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeDaskResult(int i2, List list, List list2, List list3, List list4, List list5, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeDaskResult$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.kingKongArea = null;
        } else {
            this.kingKongArea = list;
        }
        if ((i2 & 2) == 0) {
            this.kingKongAreaUser = null;
        } else {
            this.kingKongAreaUser = list2;
        }
        if ((i2 & 4) == 0) {
            this.teachingCenter = null;
        } else {
            this.teachingCenter = list3;
        }
        if ((i2 & 8) == 0) {
            this.brushQuestionBank = null;
        } else {
            this.brushQuestionBank = list4;
        }
        if ((i2 & 16) == 0) {
            this.brushQuestionBankUser = null;
        } else {
            this.brushQuestionBankUser = list5;
        }
    }

    public static /* synthetic */ HomeDaskResult copy$default(HomeDaskResult homeDaskResult, List list, List list2, List list3, List list4, List list5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = homeDaskResult.kingKongArea;
        }
        if ((i2 & 2) != 0) {
            list2 = homeDaskResult.kingKongAreaUser;
        }
        List list6 = list2;
        if ((i2 & 4) != 0) {
            list3 = homeDaskResult.teachingCenter;
        }
        List list7 = list3;
        if ((i2 & 8) != 0) {
            list4 = homeDaskResult.brushQuestionBank;
        }
        List list8 = list4;
        if ((i2 & 16) != 0) {
            list5 = homeDaskResult.brushQuestionBankUser;
        }
        return homeDaskResult.copy(list, list6, list7, list8, list5);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeDaskResult self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.kingKongArea != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.kingKongArea);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.kingKongAreaUser != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.kingKongAreaUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.teachingCenter != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.teachingCenter);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.brushQuestionBank != null) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.brushQuestionBank);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.brushQuestionBankUser != null) {
            output.encodeNullableSerializableElement(serialDesc, 4, new ArrayListSerializer(HomeDaskItem$$serializer.INSTANCE), self.brushQuestionBankUser);
        }
    }

    @Nullable
    public final List<HomeDaskItem> component1() {
        return this.kingKongArea;
    }

    @Nullable
    public final List<HomeDaskItem> component2() {
        return this.kingKongAreaUser;
    }

    @Nullable
    public final List<HomeDaskItem> component3() {
        return this.teachingCenter;
    }

    @Nullable
    public final List<HomeDaskItem> component4() {
        return this.brushQuestionBank;
    }

    @Nullable
    public final List<HomeDaskItem> component5() {
        return this.brushQuestionBankUser;
    }

    @NotNull
    public final HomeDaskResult copy(@Nullable List<HomeDaskItem> kingKongArea, @Nullable List<HomeDaskItem> kingKongAreaUser, @Nullable List<HomeDaskItem> teachingCenter, @Nullable List<HomeDaskItem> brushQuestionBank, @Nullable List<HomeDaskItem> brushQuestionBankUser) {
        return new HomeDaskResult(kingKongArea, kingKongAreaUser, teachingCenter, brushQuestionBank, brushQuestionBankUser);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeDaskResult)) {
            return false;
        }
        HomeDaskResult homeDaskResult = (HomeDaskResult) other;
        return Intrinsics.areEqual(this.kingKongArea, homeDaskResult.kingKongArea) && Intrinsics.areEqual(this.kingKongAreaUser, homeDaskResult.kingKongAreaUser) && Intrinsics.areEqual(this.teachingCenter, homeDaskResult.teachingCenter) && Intrinsics.areEqual(this.brushQuestionBank, homeDaskResult.brushQuestionBank) && Intrinsics.areEqual(this.brushQuestionBankUser, homeDaskResult.brushQuestionBankUser);
    }

    @Nullable
    public final List<HomeDaskItem> getBrushQuestionBank() {
        return this.brushQuestionBank;
    }

    @Nullable
    public final List<HomeDaskItem> getBrushQuestionBankUser() {
        return this.brushQuestionBankUser;
    }

    @Nullable
    public final List<HomeDaskItem> getKingKongArea() {
        return this.kingKongArea;
    }

    @Nullable
    public final List<HomeDaskItem> getKingKongAreaUser() {
        return this.kingKongAreaUser;
    }

    @Nullable
    public final List<HomeDaskItem> getTeachingCenter() {
        return this.teachingCenter;
    }

    public int hashCode() {
        List<HomeDaskItem> list = this.kingKongArea;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        List<HomeDaskItem> list2 = this.kingKongAreaUser;
        int iHashCode2 = (iHashCode + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<HomeDaskItem> list3 = this.teachingCenter;
        int iHashCode3 = (iHashCode2 + (list3 == null ? 0 : list3.hashCode())) * 31;
        List<HomeDaskItem> list4 = this.brushQuestionBank;
        int iHashCode4 = (iHashCode3 + (list4 == null ? 0 : list4.hashCode())) * 31;
        List<HomeDaskItem> list5 = this.brushQuestionBankUser;
        return iHashCode4 + (list5 != null ? list5.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeDaskResult(kingKongArea=" + this.kingKongArea + ", kingKongAreaUser=" + this.kingKongAreaUser + ", teachingCenter=" + this.teachingCenter + ", brushQuestionBank=" + this.brushQuestionBank + ", brushQuestionBankUser=" + this.brushQuestionBankUser + ")";
    }

    public HomeDaskResult(@Nullable List<HomeDaskItem> list, @Nullable List<HomeDaskItem> list2, @Nullable List<HomeDaskItem> list3, @Nullable List<HomeDaskItem> list4, @Nullable List<HomeDaskItem> list5) {
        this.kingKongArea = list;
        this.kingKongAreaUser = list2;
        this.teachingCenter = list3;
        this.brushQuestionBank = list4;
        this.brushQuestionBankUser = list5;
    }

    public /* synthetic */ HomeDaskResult(List list, List list2, List list3, List list4, List list5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list, (i2 & 2) != 0 ? null : list2, (i2 & 4) != 0 ? null : list3, (i2 & 8) != 0 ? null : list4, (i2 & 16) != 0 ? null : list5);
    }
}
