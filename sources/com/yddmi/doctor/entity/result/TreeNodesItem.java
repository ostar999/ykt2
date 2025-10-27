package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
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
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 J2\u00020\u0001:\u0002IJB\u0093\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016B\u0097\u0001\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0017J\u0011\u00100\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0005HÆ\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u00103\u001a\u00020\u0013HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u00105\u001a\u00020\u0003HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\u000fHÆ\u0003¢\u0006\u0002\u0010,J \u0001\u0010<\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0013HÆ\u0001¢\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020\u00132\b\u0010?\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010@\u001a\u00020\u0003HÖ\u0001J\t\u0010A\u001a\u00020\u0007HÖ\u0001J!\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u00002\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020HHÇ\u0001R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001dR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001dR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001dR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001dR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001dR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\n\n\u0002\u0010-\u001a\u0004\b+\u0010,R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001dR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001d¨\u0006K"}, d2 = {"Lcom/yddmi/doctor/entity/result/TreeNodesItem;", "", "seen1", "", "children", "", "id", "", DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "pid", "primaryCategory", "ratingCriteriaDetailId", "ratingCriteriaId", "ratingItem", "score", "", "secondaryCategory", "seq", "mShow", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;ZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;Z)V", "getChildren", "()Ljava/util/List;", "setChildren", "(Ljava/util/List;)V", "getId", "()Ljava/lang/String;", "getLevel", "()I", "setLevel", "(I)V", "getMShow", "()Z", "setMShow", "(Z)V", "getPid", "getPrimaryCategory", "getRatingCriteriaDetailId", "getRatingCriteriaId", "getRatingItem", "getScore", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getSecondaryCategory", "getSeq", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/util/List;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;Z)Lcom/yddmi/doctor/entity/result/TreeNodesItem;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class TreeNodesItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private List<TreeNodesItem> children;

    @Nullable
    private final String id;
    private int level;
    private boolean mShow;

    @Nullable
    private final String pid;

    @Nullable
    private final String primaryCategory;

    @Nullable
    private final String ratingCriteriaDetailId;

    @Nullable
    private final String ratingCriteriaId;

    @Nullable
    private final String ratingItem;

    @Nullable
    private final Float score;

    @Nullable
    private final String secondaryCategory;

    @Nullable
    private final String seq;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/TreeNodesItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/TreeNodesItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<TreeNodesItem> serializer() {
            return TreeNodesItem$$serializer.INSTANCE;
        }
    }

    public TreeNodesItem() {
        this((List) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, (String) null, (Float) null, (String) null, (String) null, false, 4095, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ TreeNodesItem(int i2, List list, String str, int i3, String str2, String str3, String str4, String str5, String str6, Float f2, String str7, String str8, boolean z2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, TreeNodesItem$$serializer.INSTANCE.getDescriptor());
        }
        this.children = (i2 & 1) == 0 ? null : list;
        if ((i2 & 2) == 0) {
            this.id = "";
        } else {
            this.id = str;
        }
        if ((i2 & 4) == 0) {
            this.level = 0;
        } else {
            this.level = i3;
        }
        if ((i2 & 8) == 0) {
            this.pid = "";
        } else {
            this.pid = str2;
        }
        if ((i2 & 16) == 0) {
            this.primaryCategory = "";
        } else {
            this.primaryCategory = str3;
        }
        if ((i2 & 32) == 0) {
            this.ratingCriteriaDetailId = "";
        } else {
            this.ratingCriteriaDetailId = str4;
        }
        if ((i2 & 64) == 0) {
            this.ratingCriteriaId = "";
        } else {
            this.ratingCriteriaId = str5;
        }
        if ((i2 & 128) == 0) {
            this.ratingItem = "";
        } else {
            this.ratingItem = str6;
        }
        if ((i2 & 256) == 0) {
            this.score = Float.valueOf(0.0f);
        } else {
            this.score = f2;
        }
        if ((i2 & 512) == 0) {
            this.secondaryCategory = "";
        } else {
            this.secondaryCategory = str7;
        }
        if ((i2 & 1024) == 0) {
            this.seq = "";
        } else {
            this.seq = str8;
        }
        if ((i2 & 2048) == 0) {
            this.mShow = false;
        } else {
            this.mShow = z2;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull TreeNodesItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.children != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(TreeNodesItem$$serializer.INSTANCE), self.children);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.level != 0) {
            output.encodeIntElement(serialDesc, 2, self.level);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.pid, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.pid);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.primaryCategory, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.primaryCategory);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.ratingCriteriaDetailId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.ratingCriteriaDetailId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.ratingCriteriaId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.ratingCriteriaId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.ratingItem, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.ratingItem);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual((Object) self.score, (Object) Float.valueOf(0.0f))) {
            output.encodeNullableSerializableElement(serialDesc, 8, FloatSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.secondaryCategory, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.secondaryCategory);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.seq, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.seq);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.mShow) {
            output.encodeBooleanElement(serialDesc, 11, self.mShow);
        }
    }

    @Nullable
    public final List<TreeNodesItem> component1() {
        return this.children;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getSecondaryCategory() {
        return this.secondaryCategory;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getSeq() {
        return this.seq;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean getMShow() {
        return this.mShow;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component3, reason: from getter */
    public final int getLevel() {
        return this.level;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getPid() {
        return this.pid;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getPrimaryCategory() {
        return this.primaryCategory;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getRatingCriteriaDetailId() {
        return this.ratingCriteriaDetailId;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getRatingCriteriaId() {
        return this.ratingCriteriaId;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getRatingItem() {
        return this.ratingItem;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final Float getScore() {
        return this.score;
    }

    @NotNull
    public final TreeNodesItem copy(@Nullable List<TreeNodesItem> children, @Nullable String id, int level, @Nullable String pid, @Nullable String primaryCategory, @Nullable String ratingCriteriaDetailId, @Nullable String ratingCriteriaId, @Nullable String ratingItem, @Nullable Float score, @Nullable String secondaryCategory, @Nullable String seq, boolean mShow) {
        return new TreeNodesItem(children, id, level, pid, primaryCategory, ratingCriteriaDetailId, ratingCriteriaId, ratingItem, score, secondaryCategory, seq, mShow);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TreeNodesItem)) {
            return false;
        }
        TreeNodesItem treeNodesItem = (TreeNodesItem) other;
        return Intrinsics.areEqual(this.children, treeNodesItem.children) && Intrinsics.areEqual(this.id, treeNodesItem.id) && this.level == treeNodesItem.level && Intrinsics.areEqual(this.pid, treeNodesItem.pid) && Intrinsics.areEqual(this.primaryCategory, treeNodesItem.primaryCategory) && Intrinsics.areEqual(this.ratingCriteriaDetailId, treeNodesItem.ratingCriteriaDetailId) && Intrinsics.areEqual(this.ratingCriteriaId, treeNodesItem.ratingCriteriaId) && Intrinsics.areEqual(this.ratingItem, treeNodesItem.ratingItem) && Intrinsics.areEqual((Object) this.score, (Object) treeNodesItem.score) && Intrinsics.areEqual(this.secondaryCategory, treeNodesItem.secondaryCategory) && Intrinsics.areEqual(this.seq, treeNodesItem.seq) && this.mShow == treeNodesItem.mShow;
    }

    @Nullable
    public final List<TreeNodesItem> getChildren() {
        return this.children;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    public final int getLevel() {
        return this.level;
    }

    public final boolean getMShow() {
        return this.mShow;
    }

    @Nullable
    public final String getPid() {
        return this.pid;
    }

    @Nullable
    public final String getPrimaryCategory() {
        return this.primaryCategory;
    }

    @Nullable
    public final String getRatingCriteriaDetailId() {
        return this.ratingCriteriaDetailId;
    }

    @Nullable
    public final String getRatingCriteriaId() {
        return this.ratingCriteriaId;
    }

    @Nullable
    public final String getRatingItem() {
        return this.ratingItem;
    }

    @Nullable
    public final Float getScore() {
        return this.score;
    }

    @Nullable
    public final String getSecondaryCategory() {
        return this.secondaryCategory;
    }

    @Nullable
    public final String getSeq() {
        return this.seq;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        List<TreeNodesItem> list = this.children;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        String str = this.id;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.level) * 31;
        String str2 = this.pid;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.primaryCategory;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.ratingCriteriaDetailId;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.ratingCriteriaId;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.ratingItem;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        Float f2 = this.score;
        int iHashCode8 = (iHashCode7 + (f2 == null ? 0 : f2.hashCode())) * 31;
        String str7 = this.secondaryCategory;
        int iHashCode9 = (iHashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.seq;
        int iHashCode10 = (iHashCode9 + (str8 != null ? str8.hashCode() : 0)) * 31;
        boolean z2 = this.mShow;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode10 + i2;
    }

    public final void setChildren(@Nullable List<TreeNodesItem> list) {
        this.children = list;
    }

    public final void setLevel(int i2) {
        this.level = i2;
    }

    public final void setMShow(boolean z2) {
        this.mShow = z2;
    }

    @NotNull
    public String toString() {
        return "TreeNodesItem(children=" + this.children + ", id=" + this.id + ", level=" + this.level + ", pid=" + this.pid + ", primaryCategory=" + this.primaryCategory + ", ratingCriteriaDetailId=" + this.ratingCriteriaDetailId + ", ratingCriteriaId=" + this.ratingCriteriaId + ", ratingItem=" + this.ratingItem + ", score=" + this.score + ", secondaryCategory=" + this.secondaryCategory + ", seq=" + this.seq + ", mShow=" + this.mShow + ")";
    }

    public TreeNodesItem(@Nullable List<TreeNodesItem> list, @Nullable String str, int i2, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Float f2, @Nullable String str7, @Nullable String str8, boolean z2) {
        this.children = list;
        this.id = str;
        this.level = i2;
        this.pid = str2;
        this.primaryCategory = str3;
        this.ratingCriteriaDetailId = str4;
        this.ratingCriteriaId = str5;
        this.ratingItem = str6;
        this.score = f2;
        this.secondaryCategory = str7;
        this.seq = str8;
        this.mShow = z2;
    }

    public /* synthetic */ TreeNodesItem(List list, String str, int i2, String str2, String str3, String str4, String str5, String str6, Float f2, String str7, String str8, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : list, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? "" : str2, (i3 & 16) != 0 ? "" : str3, (i3 & 32) != 0 ? "" : str4, (i3 & 64) != 0 ? "" : str5, (i3 & 128) != 0 ? "" : str6, (i3 & 256) != 0 ? Float.valueOf(0.0f) : f2, (i3 & 512) != 0 ? "" : str7, (i3 & 1024) == 0 ? str8 : "", (i3 & 2048) == 0 ? z2 : false);
    }
}
