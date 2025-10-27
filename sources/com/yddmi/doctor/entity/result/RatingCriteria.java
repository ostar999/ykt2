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
import kotlinx.serialization.internal.FloatSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 02\u00020\u0001:\u0002/0BW\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fBM\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u000bHÆ\u0003¢\u0006\u0002\u0010\u0019J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0003JV\u0010!\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007HÆ\u0001¢\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\u0003HÖ\u0001J\t\u0010'\u001a\u00020\u0005HÖ\u0001J!\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.HÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\"\u0010\f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014¨\u00061"}, d2 = {"Lcom/yddmi/doctor/entity/result/RatingCriteria;", "", "seen1", "", "createUserName", "", "treeNodes", "", "Lcom/yddmi/doctor/entity/result/TreeNodesItem;", "ratingCriteriaId", "score", "", "mTreeNodes", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Float;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Float;Ljava/util/List;)V", "getCreateUserName", "()Ljava/lang/String;", "getMTreeNodes", "()Ljava/util/List;", "setMTreeNodes", "(Ljava/util/List;)V", "getRatingCriteriaId", "getScore", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getTreeNodes", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Float;Ljava/util/List;)Lcom/yddmi/doctor/entity/result/RatingCriteria;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class RatingCriteria {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String createUserName;

    @Nullable
    private List<TreeNodesItem> mTreeNodes;

    @Nullable
    private final String ratingCriteriaId;

    @Nullable
    private final Float score;

    @Nullable
    private final List<TreeNodesItem> treeNodes;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/RatingCriteria$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/RatingCriteria;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<RatingCriteria> serializer() {
            return RatingCriteria$$serializer.INSTANCE;
        }
    }

    public RatingCriteria() {
        this((String) null, (List) null, (String) null, (Float) null, (List) null, 31, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ RatingCriteria(int i2, String str, List list, String str2, Float f2, List list2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, RatingCriteria$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.createUserName = "";
        } else {
            this.createUserName = str;
        }
        if ((i2 & 2) == 0) {
            this.treeNodes = null;
        } else {
            this.treeNodes = list;
        }
        if ((i2 & 4) == 0) {
            this.ratingCriteriaId = "";
        } else {
            this.ratingCriteriaId = str2;
        }
        if ((i2 & 8) == 0) {
            this.score = Float.valueOf(-1.0f);
        } else {
            this.score = f2;
        }
        if ((i2 & 16) == 0) {
            this.mTreeNodes = null;
        } else {
            this.mTreeNodes = list2;
        }
    }

    public static /* synthetic */ RatingCriteria copy$default(RatingCriteria ratingCriteria, String str, List list, String str2, Float f2, List list2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = ratingCriteria.createUserName;
        }
        if ((i2 & 2) != 0) {
            list = ratingCriteria.treeNodes;
        }
        List list3 = list;
        if ((i2 & 4) != 0) {
            str2 = ratingCriteria.ratingCriteriaId;
        }
        String str3 = str2;
        if ((i2 & 8) != 0) {
            f2 = ratingCriteria.score;
        }
        Float f3 = f2;
        if ((i2 & 16) != 0) {
            list2 = ratingCriteria.mTreeNodes;
        }
        return ratingCriteria.copy(str, list3, str3, f3, list2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull RatingCriteria self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.createUserName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.createUserName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.treeNodes != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(TreeNodesItem$$serializer.INSTANCE), self.treeNodes);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.ratingCriteriaId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.ratingCriteriaId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual((Object) self.score, (Object) Float.valueOf(-1.0f))) {
            output.encodeNullableSerializableElement(serialDesc, 3, FloatSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.mTreeNodes != null) {
            output.encodeNullableSerializableElement(serialDesc, 4, new ArrayListSerializer(TreeNodesItem$$serializer.INSTANCE), self.mTreeNodes);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getCreateUserName() {
        return this.createUserName;
    }

    @Nullable
    public final List<TreeNodesItem> component2() {
        return this.treeNodes;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getRatingCriteriaId() {
        return this.ratingCriteriaId;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final Float getScore() {
        return this.score;
    }

    @Nullable
    public final List<TreeNodesItem> component5() {
        return this.mTreeNodes;
    }

    @NotNull
    public final RatingCriteria copy(@Nullable String createUserName, @Nullable List<TreeNodesItem> treeNodes, @Nullable String ratingCriteriaId, @Nullable Float score, @Nullable List<TreeNodesItem> mTreeNodes) {
        return new RatingCriteria(createUserName, treeNodes, ratingCriteriaId, score, mTreeNodes);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RatingCriteria)) {
            return false;
        }
        RatingCriteria ratingCriteria = (RatingCriteria) other;
        return Intrinsics.areEqual(this.createUserName, ratingCriteria.createUserName) && Intrinsics.areEqual(this.treeNodes, ratingCriteria.treeNodes) && Intrinsics.areEqual(this.ratingCriteriaId, ratingCriteria.ratingCriteriaId) && Intrinsics.areEqual((Object) this.score, (Object) ratingCriteria.score) && Intrinsics.areEqual(this.mTreeNodes, ratingCriteria.mTreeNodes);
    }

    @Nullable
    public final String getCreateUserName() {
        return this.createUserName;
    }

    @Nullable
    public final List<TreeNodesItem> getMTreeNodes() {
        return this.mTreeNodes;
    }

    @Nullable
    public final String getRatingCriteriaId() {
        return this.ratingCriteriaId;
    }

    @Nullable
    public final Float getScore() {
        return this.score;
    }

    @Nullable
    public final List<TreeNodesItem> getTreeNodes() {
        return this.treeNodes;
    }

    public int hashCode() {
        String str = this.createUserName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        List<TreeNodesItem> list = this.treeNodes;
        int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
        String str2 = this.ratingCriteriaId;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Float f2 = this.score;
        int iHashCode4 = (iHashCode3 + (f2 == null ? 0 : f2.hashCode())) * 31;
        List<TreeNodesItem> list2 = this.mTreeNodes;
        return iHashCode4 + (list2 != null ? list2.hashCode() : 0);
    }

    public final void setMTreeNodes(@Nullable List<TreeNodesItem> list) {
        this.mTreeNodes = list;
    }

    @NotNull
    public String toString() {
        return "RatingCriteria(createUserName=" + this.createUserName + ", treeNodes=" + this.treeNodes + ", ratingCriteriaId=" + this.ratingCriteriaId + ", score=" + this.score + ", mTreeNodes=" + this.mTreeNodes + ")";
    }

    public RatingCriteria(@Nullable String str, @Nullable List<TreeNodesItem> list, @Nullable String str2, @Nullable Float f2, @Nullable List<TreeNodesItem> list2) {
        this.createUserName = str;
        this.treeNodes = list;
        this.ratingCriteriaId = str2;
        this.score = f2;
        this.mTreeNodes = list2;
    }

    public /* synthetic */ RatingCriteria(String str, List list, String str2, Float f2, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? null : list, (i2 & 4) == 0 ? str2 : "", (i2 & 8) != 0 ? Float.valueOf(-1.0f) : f2, (i2 & 16) != 0 ? null : list2);
    }
}
