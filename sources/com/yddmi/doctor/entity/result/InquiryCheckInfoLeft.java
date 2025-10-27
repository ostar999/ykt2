package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.psychiatrygarden.utils.Constants;
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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 H2\u00020\u0001:\u0002GHB\u0085\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014B\u0089\u0001\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003¢\u0006\u0002\u0010\u0015J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\t\u00100\u001a\u00020\u000fHÆ\u0003J\t\u00101\u001a\u00020\u000fHÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u00109\u001a\u00020\u0003HÆ\u0003J\t\u0010:\u001a\u00020\u0003HÆ\u0003J\u008d\u0001\u0010;\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0003HÆ\u0001J\u0013\u0010<\u001a\u00020\u000f2\b\u0010=\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010>\u001a\u00020\u0003HÖ\u0001J\t\u0010?\u001a\u00020\u0006HÖ\u0001J!\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u00002\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FHÇ\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0010\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0011\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0019\"\u0004\b!\u0010\u001bR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001d\"\u0004\b#\u0010\u001fR\u001a\u0010\r\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0019\"\u0004\b%\u0010\u001bR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0017\"\u0004\b'\u0010(R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0017R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0017R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0017\"\u0004\b,\u0010(R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0019¨\u0006I"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryCheckInfoLeft;", "", "seen1", "", "id", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, "", "result", "course", AliyunLogCommon.Module.PUBLISHER, "releaseTime", "title", "type", "itemState", "itemSelected", "", "itemIsTitle", "itemSelectNum", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZZILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZZI)V", "getCourse", "()Ljava/lang/String;", "getId", "()I", "setId", "(I)V", "getItemIsTitle", "()Z", "setItemIsTitle", "(Z)V", "getItemSelectNum", "setItemSelectNum", "getItemSelected", "setItemSelected", "getItemState", "setItemState", "getLabel", "setLabel", "(Ljava/lang/String;)V", "getPublisher", "getReleaseTime", "getResult", "setResult", "getTitle", "getType", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class InquiryCheckInfoLeft {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String course;
    private int id;
    private boolean itemIsTitle;
    private transient int itemSelectNum;
    private boolean itemSelected;
    private int itemState;

    @Nullable
    private String label;

    @Nullable
    private final String publisher;

    @Nullable
    private final String releaseTime;

    @Nullable
    private String result;

    @Nullable
    private final String title;
    private final int type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/InquiryCheckInfoLeft$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfoLeft;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<InquiryCheckInfoLeft> serializer() {
            return InquiryCheckInfoLeft$$serializer.INSTANCE;
        }
    }

    public InquiryCheckInfoLeft() {
        this(0, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 0, 0, false, false, 0, 4095, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ InquiryCheckInfoLeft(int i2, int i3, String str, String str2, String str3, String str4, String str5, String str6, int i4, int i5, boolean z2, boolean z3, int i6, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, InquiryCheckInfoLeft$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.id = -1;
        } else {
            this.id = i3;
        }
        if ((i2 & 2) == 0) {
            this.label = "";
        } else {
            this.label = str;
        }
        if ((i2 & 4) == 0) {
            this.result = "";
        } else {
            this.result = str2;
        }
        if ((i2 & 8) == 0) {
            this.course = "";
        } else {
            this.course = str3;
        }
        if ((i2 & 16) == 0) {
            this.publisher = "";
        } else {
            this.publisher = str4;
        }
        if ((i2 & 32) == 0) {
            this.releaseTime = "";
        } else {
            this.releaseTime = str5;
        }
        if ((i2 & 64) == 0) {
            this.title = "";
        } else {
            this.title = str6;
        }
        if ((i2 & 128) == 0) {
            this.type = -1;
        } else {
            this.type = i4;
        }
        if ((i2 & 256) == 0) {
            this.itemState = -1;
        } else {
            this.itemState = i5;
        }
        if ((i2 & 512) == 0) {
            this.itemSelected = false;
        } else {
            this.itemSelected = z2;
        }
        if ((i2 & 1024) == 0) {
            this.itemIsTitle = false;
        } else {
            this.itemIsTitle = z3;
        }
        if ((i2 & 2048) == 0) {
            this.itemSelectNum = 0;
        } else {
            this.itemSelectNum = i6;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull InquiryCheckInfoLeft self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.id != -1) {
            output.encodeIntElement(serialDesc, 0, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.label, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.label);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.result);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.course, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.course);
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
        if (output.shouldEncodeElementDefault(serialDesc, 7) || self.type != -1) {
            output.encodeIntElement(serialDesc, 7, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || self.itemState != -1) {
            output.encodeIntElement(serialDesc, 8, self.itemState);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.itemSelected) {
            output.encodeBooleanElement(serialDesc, 9, self.itemSelected);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || self.itemIsTitle) {
            output.encodeBooleanElement(serialDesc, 10, self.itemIsTitle);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.itemSelectNum != 0) {
            output.encodeIntElement(serialDesc, 11, self.itemSelectNum);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getItemSelected() {
        return this.itemSelected;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getItemIsTitle() {
        return this.itemIsTitle;
    }

    /* renamed from: component12, reason: from getter */
    public final int getItemSelectNum() {
        return this.itemSelectNum;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getCourse() {
        return this.course;
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

    /* renamed from: component8, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component9, reason: from getter */
    public final int getItemState() {
        return this.itemState;
    }

    @NotNull
    public final InquiryCheckInfoLeft copy(int id, @Nullable String label, @Nullable String result, @Nullable String course, @Nullable String publisher, @Nullable String releaseTime, @Nullable String title, int type, int itemState, boolean itemSelected, boolean itemIsTitle, int itemSelectNum) {
        return new InquiryCheckInfoLeft(id, label, result, course, publisher, releaseTime, title, type, itemState, itemSelected, itemIsTitle, itemSelectNum);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InquiryCheckInfoLeft)) {
            return false;
        }
        InquiryCheckInfoLeft inquiryCheckInfoLeft = (InquiryCheckInfoLeft) other;
        return this.id == inquiryCheckInfoLeft.id && Intrinsics.areEqual(this.label, inquiryCheckInfoLeft.label) && Intrinsics.areEqual(this.result, inquiryCheckInfoLeft.result) && Intrinsics.areEqual(this.course, inquiryCheckInfoLeft.course) && Intrinsics.areEqual(this.publisher, inquiryCheckInfoLeft.publisher) && Intrinsics.areEqual(this.releaseTime, inquiryCheckInfoLeft.releaseTime) && Intrinsics.areEqual(this.title, inquiryCheckInfoLeft.title) && this.type == inquiryCheckInfoLeft.type && this.itemState == inquiryCheckInfoLeft.itemState && this.itemSelected == inquiryCheckInfoLeft.itemSelected && this.itemIsTitle == inquiryCheckInfoLeft.itemIsTitle && this.itemSelectNum == inquiryCheckInfoLeft.itemSelectNum;
    }

    @Nullable
    public final String getCourse() {
        return this.course;
    }

    public final int getId() {
        return this.id;
    }

    public final boolean getItemIsTitle() {
        return this.itemIsTitle;
    }

    public final int getItemSelectNum() {
        return this.itemSelectNum;
    }

    public final boolean getItemSelected() {
        return this.itemSelected;
    }

    public final int getItemState() {
        return this.itemState;
    }

    @Nullable
    public final String getLabel() {
        return this.label;
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
    public final String getResult() {
        return this.result;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public final int getType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int i2 = this.id * 31;
        String str = this.label;
        int iHashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.result;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.course;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.publisher;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.releaseTime;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.title;
        int iHashCode6 = (((((iHashCode5 + (str6 != null ? str6.hashCode() : 0)) * 31) + this.type) * 31) + this.itemState) * 31;
        boolean z2 = this.itemSelected;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (iHashCode6 + i3) * 31;
        boolean z3 = this.itemIsTitle;
        return ((i4 + (z3 ? 1 : z3 ? 1 : 0)) * 31) + this.itemSelectNum;
    }

    public final void setId(int i2) {
        this.id = i2;
    }

    public final void setItemIsTitle(boolean z2) {
        this.itemIsTitle = z2;
    }

    public final void setItemSelectNum(int i2) {
        this.itemSelectNum = i2;
    }

    public final void setItemSelected(boolean z2) {
        this.itemSelected = z2;
    }

    public final void setItemState(int i2) {
        this.itemState = i2;
    }

    public final void setLabel(@Nullable String str) {
        this.label = str;
    }

    public final void setResult(@Nullable String str) {
        this.result = str;
    }

    @NotNull
    public String toString() {
        return "InquiryCheckInfoLeft(id=" + this.id + ", label=" + this.label + ", result=" + this.result + ", course=" + this.course + ", publisher=" + this.publisher + ", releaseTime=" + this.releaseTime + ", title=" + this.title + ", type=" + this.type + ", itemState=" + this.itemState + ", itemSelected=" + this.itemSelected + ", itemIsTitle=" + this.itemIsTitle + ", itemSelectNum=" + this.itemSelectNum + ")";
    }

    public InquiryCheckInfoLeft(int i2, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, int i3, int i4, boolean z2, boolean z3, int i5) {
        this.id = i2;
        this.label = str;
        this.result = str2;
        this.course = str3;
        this.publisher = str4;
        this.releaseTime = str5;
        this.title = str6;
        this.type = i3;
        this.itemState = i4;
        this.itemSelected = z2;
        this.itemIsTitle = z3;
        this.itemSelectNum = i5;
    }

    public /* synthetic */ InquiryCheckInfoLeft(int i2, String str, String str2, String str3, String str4, String str5, String str6, int i3, int i4, boolean z2, boolean z3, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? -1 : i2, (i6 & 2) != 0 ? "" : str, (i6 & 4) != 0 ? "" : str2, (i6 & 8) != 0 ? "" : str3, (i6 & 16) != 0 ? "" : str4, (i6 & 32) != 0 ? "" : str5, (i6 & 64) == 0 ? str6 : "", (i6 & 128) != 0 ? -1 : i3, (i6 & 256) == 0 ? i4 : -1, (i6 & 512) != 0 ? false : z2, (i6 & 1024) != 0 ? false : z3, (i6 & 2048) == 0 ? i5 : 0);
    }
}
