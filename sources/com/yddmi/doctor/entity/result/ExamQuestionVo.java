package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
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
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 N2\u00020\u0001:\u0002MNB\u009f\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0010\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016B¥\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003¢\u0006\u0002\u0010\u0017J\u000b\u00102\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u00105\u001a\u00020\u0003HÆ\u0003J\t\u00106\u001a\u00020\u0003HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0013\u0010:\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\nHÆ\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010<\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010!J\u000b\u0010=\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0005HÆ\u0003J®\u0001\u0010?\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\u0012\b\u0002\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010@J\u0013\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010D\u001a\u00020\u0003HÖ\u0001J\t\u0010E\u001a\u00020\u0005HÖ\u0001J!\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u00002\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020LHÇ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0019\"\u0004\b\u001b\u0010\u001cR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u001e\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010$\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0019R\u001a\u0010\u0012\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010\u0013\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010&\"\u0004\b*\u0010(R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0019R$\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0019R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0019¨\u0006O"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamQuestionVo;", "", "seen1", "", "analysis", "", "answer", "content", "correct", "selectList", "", "Lcom/yddmi/doctor/entity/result/ExamQuestionSelectVo;", "id", "index", "isRight", "questionType", "type", "stem", "itemVpIndex", "itemVpItemIndex", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V", "getAnalysis", "()Ljava/lang/String;", "getAnswer", "setAnswer", "(Ljava/lang/String;)V", "getContent", "getCorrect", "getId", "getIndex", "()Ljava/lang/Integer;", "setIndex", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getItemVpIndex", "()I", "setItemVpIndex", "(I)V", "getItemVpItemIndex", "setItemVpItemIndex", "getQuestionType", "getSelectList", "()Ljava/util/List;", "setSelectList", "(Ljava/util/List;)V", "getStem", "getType", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)Lcom/yddmi/doctor/entity/result/ExamQuestionVo;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamQuestionVo {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String analysis;

    @Nullable
    private String answer;

    @Nullable
    private final String content;

    @Nullable
    private final String correct;

    @Nullable
    private final String id;

    @Nullable
    private Integer index;

    @Nullable
    private final String isRight;
    private int itemVpIndex;
    private int itemVpItemIndex;

    @Nullable
    private final String questionType;

    @Nullable
    private List<ExamQuestionSelectVo> selectList;

    @Nullable
    private final String stem;

    @Nullable
    private final String type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamQuestionVo$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamQuestionVo;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamQuestionVo> serializer() {
            return ExamQuestionVo$$serializer.INSTANCE;
        }
    }

    public ExamQuestionVo() {
        this((String) null, (String) null, (String) null, (String) null, (List) null, (String) null, (Integer) null, (String) null, (String) null, (String) null, (String) null, 0, 0, R2.dimen.preference_seekbar_padding_start, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamQuestionVo(int i2, String str, String str2, String str3, String str4, List list, String str5, Integer num, String str6, String str7, String str8, String str9, int i3, int i4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamQuestionVo$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.analysis = "";
        } else {
            this.analysis = str;
        }
        if ((i2 & 2) == 0) {
            this.answer = "";
        } else {
            this.answer = str2;
        }
        if ((i2 & 4) == 0) {
            this.content = "";
        } else {
            this.content = str3;
        }
        if ((i2 & 8) == 0) {
            this.correct = "";
        } else {
            this.correct = str4;
        }
        this.selectList = (i2 & 16) == 0 ? new ArrayList() : list;
        if ((i2 & 32) == 0) {
            this.id = "";
        } else {
            this.id = str5;
        }
        this.index = (i2 & 64) == 0 ? -1 : num;
        if ((i2 & 128) == 0) {
            this.isRight = "";
        } else {
            this.isRight = str6;
        }
        if ((i2 & 256) == 0) {
            this.questionType = "";
        } else {
            this.questionType = str7;
        }
        if ((i2 & 512) == 0) {
            this.type = "";
        } else {
            this.type = str8;
        }
        if ((i2 & 1024) == 0) {
            this.stem = "";
        } else {
            this.stem = str9;
        }
        if ((i2 & 2048) == 0) {
            this.itemVpIndex = 0;
        } else {
            this.itemVpIndex = i3;
        }
        if ((i2 & 4096) == 0) {
            this.itemVpItemIndex = 0;
        } else {
            this.itemVpItemIndex = i4;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamQuestionVo self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.analysis, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.analysis);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.answer, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.answer);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.correct, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.correct);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.selectList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 4, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamQuestionSelectVo$$serializer.INSTANCE)), self.selectList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || (num = self.index) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 6, IntSerializer.INSTANCE, self.index);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.isRight, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.isRight);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.questionType, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.questionType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.type, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.stem, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.stem);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || self.itemVpIndex != 0) {
            output.encodeIntElement(serialDesc, 11, self.itemVpIndex);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || self.itemVpItemIndex != 0) {
            output.encodeIntElement(serialDesc, 12, self.itemVpItemIndex);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getAnalysis() {
        return this.analysis;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getStem() {
        return this.stem;
    }

    /* renamed from: component12, reason: from getter */
    public final int getItemVpIndex() {
        return this.itemVpIndex;
    }

    /* renamed from: component13, reason: from getter */
    public final int getItemVpItemIndex() {
        return this.itemVpItemIndex;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getAnswer() {
        return this.answer;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getCorrect() {
        return this.correct;
    }

    @Nullable
    public final List<ExamQuestionSelectVo> component5() {
        return this.selectList;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final Integer getIndex() {
        return this.index;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getIsRight() {
        return this.isRight;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getQuestionType() {
        return this.questionType;
    }

    @NotNull
    public final ExamQuestionVo copy(@Nullable String analysis, @Nullable String answer, @Nullable String content, @Nullable String correct, @Nullable List<ExamQuestionSelectVo> selectList, @Nullable String id, @Nullable Integer index, @Nullable String isRight, @Nullable String questionType, @Nullable String type, @Nullable String stem, int itemVpIndex, int itemVpItemIndex) {
        return new ExamQuestionVo(analysis, answer, content, correct, selectList, id, index, isRight, questionType, type, stem, itemVpIndex, itemVpItemIndex);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamQuestionVo)) {
            return false;
        }
        ExamQuestionVo examQuestionVo = (ExamQuestionVo) other;
        return Intrinsics.areEqual(this.analysis, examQuestionVo.analysis) && Intrinsics.areEqual(this.answer, examQuestionVo.answer) && Intrinsics.areEqual(this.content, examQuestionVo.content) && Intrinsics.areEqual(this.correct, examQuestionVo.correct) && Intrinsics.areEqual(this.selectList, examQuestionVo.selectList) && Intrinsics.areEqual(this.id, examQuestionVo.id) && Intrinsics.areEqual(this.index, examQuestionVo.index) && Intrinsics.areEqual(this.isRight, examQuestionVo.isRight) && Intrinsics.areEqual(this.questionType, examQuestionVo.questionType) && Intrinsics.areEqual(this.type, examQuestionVo.type) && Intrinsics.areEqual(this.stem, examQuestionVo.stem) && this.itemVpIndex == examQuestionVo.itemVpIndex && this.itemVpItemIndex == examQuestionVo.itemVpItemIndex;
    }

    @Nullable
    public final String getAnalysis() {
        return this.analysis;
    }

    @Nullable
    public final String getAnswer() {
        return this.answer;
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final String getCorrect() {
        return this.correct;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final Integer getIndex() {
        return this.index;
    }

    public final int getItemVpIndex() {
        return this.itemVpIndex;
    }

    public final int getItemVpItemIndex() {
        return this.itemVpItemIndex;
    }

    @Nullable
    public final String getQuestionType() {
        return this.questionType;
    }

    @Nullable
    public final List<ExamQuestionSelectVo> getSelectList() {
        return this.selectList;
    }

    @Nullable
    public final String getStem() {
        return this.stem;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        String str = this.analysis;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.answer;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.content;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.correct;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        List<ExamQuestionSelectVo> list = this.selectList;
        int iHashCode5 = (iHashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        String str5 = this.id;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        Integer num = this.index;
        int iHashCode7 = (iHashCode6 + (num == null ? 0 : num.hashCode())) * 31;
        String str6 = this.isRight;
        int iHashCode8 = (iHashCode7 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.questionType;
        int iHashCode9 = (iHashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.type;
        int iHashCode10 = (iHashCode9 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.stem;
        return ((((iHashCode10 + (str9 != null ? str9.hashCode() : 0)) * 31) + this.itemVpIndex) * 31) + this.itemVpItemIndex;
    }

    @Nullable
    public final String isRight() {
        return this.isRight;
    }

    public final void setAnswer(@Nullable String str) {
        this.answer = str;
    }

    public final void setIndex(@Nullable Integer num) {
        this.index = num;
    }

    public final void setItemVpIndex(int i2) {
        this.itemVpIndex = i2;
    }

    public final void setItemVpItemIndex(int i2) {
        this.itemVpItemIndex = i2;
    }

    public final void setSelectList(@Nullable List<ExamQuestionSelectVo> list) {
        this.selectList = list;
    }

    @NotNull
    public String toString() {
        return "ExamQuestionVo(analysis=" + this.analysis + ", answer=" + this.answer + ", content=" + this.content + ", correct=" + this.correct + ", selectList=" + this.selectList + ", id=" + this.id + ", index=" + this.index + ", isRight=" + this.isRight + ", questionType=" + this.questionType + ", type=" + this.type + ", stem=" + this.stem + ", itemVpIndex=" + this.itemVpIndex + ", itemVpItemIndex=" + this.itemVpItemIndex + ")";
    }

    public ExamQuestionVo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable List<ExamQuestionSelectVo> list, @Nullable String str5, @Nullable Integer num, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, int i2, int i3) {
        this.analysis = str;
        this.answer = str2;
        this.content = str3;
        this.correct = str4;
        this.selectList = list;
        this.id = str5;
        this.index = num;
        this.isRight = str6;
        this.questionType = str7;
        this.type = str8;
        this.stem = str9;
        this.itemVpIndex = i2;
        this.itemVpItemIndex = i3;
    }

    public /* synthetic */ ExamQuestionVo(String str, String str2, String str3, String str4, List list, String str5, Integer num, String str6, String str7, String str8, String str9, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? "" : str, (i4 & 2) != 0 ? "" : str2, (i4 & 4) != 0 ? "" : str3, (i4 & 8) != 0 ? "" : str4, (i4 & 16) != 0 ? new ArrayList() : list, (i4 & 32) != 0 ? "" : str5, (i4 & 64) != 0 ? -1 : num, (i4 & 128) != 0 ? "" : str6, (i4 & 256) != 0 ? "" : str7, (i4 & 512) != 0 ? "" : str8, (i4 & 1024) == 0 ? str9 : "", (i4 & 2048) != 0 ? 0 : i2, (i4 & 4096) == 0 ? i3 : 0);
    }
}
