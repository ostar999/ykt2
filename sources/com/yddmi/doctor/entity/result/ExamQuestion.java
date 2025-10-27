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
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 42\u00020\u0001:\u000234Bc\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0010\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011B]\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u0012J\u000b\u0010 \u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0013\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u000eHÆ\u0003Ja\u0010'\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001J\u0013\u0010(\u001a\u00020\u000e2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\u0003HÖ\u0001J\t\u0010+\u001a\u00020\u0005HÖ\u0001J!\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202HÇ\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u00065"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamQuestion;", "", "seen1", "", "description", "", "examQuestionVoList", "", "Lcom/yddmi/doctor/entity/result/ExamQuestionVo;", "questionType", "type", "correct", "content", "itemA1XFirst", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/util/List;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;ZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Z)V", "getContent", "()Ljava/lang/String;", "getCorrect", "getDescription", "getExamQuestionVoList", "()Ljava/util/List;", "getItemA1XFirst", "()Z", "setItemA1XFirst", "(Z)V", "getQuestionType", "getType", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamQuestion {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String content;

    @Nullable
    private final String correct;

    @Nullable
    private final String description;

    @Nullable
    private final List<ExamQuestionVo> examQuestionVoList;
    private boolean itemA1XFirst;

    @Nullable
    private final String questionType;
    private final int type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamQuestion$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamQuestion;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamQuestion> serializer() {
            return ExamQuestion$$serializer.INSTANCE;
        }
    }

    public ExamQuestion() {
        this((String) null, (List) null, (String) null, 0, (String) null, (String) null, false, 127, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamQuestion(int i2, String str, List list, String str2, int i3, String str3, String str4, boolean z2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamQuestion$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.description = "";
        } else {
            this.description = str;
        }
        if ((i2 & 2) == 0) {
            this.examQuestionVoList = null;
        } else {
            this.examQuestionVoList = list;
        }
        if ((i2 & 4) == 0) {
            this.questionType = "";
        } else {
            this.questionType = str2;
        }
        if ((i2 & 8) == 0) {
            this.type = -1;
        } else {
            this.type = i3;
        }
        if ((i2 & 16) == 0) {
            this.correct = "";
        } else {
            this.correct = str3;
        }
        if ((i2 & 32) == 0) {
            this.content = "";
        } else {
            this.content = str4;
        }
        if ((i2 & 64) == 0) {
            this.itemA1XFirst = false;
        } else {
            this.itemA1XFirst = z2;
        }
    }

    public static /* synthetic */ ExamQuestion copy$default(ExamQuestion examQuestion, String str, List list, String str2, int i2, String str3, String str4, boolean z2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = examQuestion.description;
        }
        if ((i3 & 2) != 0) {
            list = examQuestion.examQuestionVoList;
        }
        List list2 = list;
        if ((i3 & 4) != 0) {
            str2 = examQuestion.questionType;
        }
        String str5 = str2;
        if ((i3 & 8) != 0) {
            i2 = examQuestion.type;
        }
        int i4 = i2;
        if ((i3 & 16) != 0) {
            str3 = examQuestion.correct;
        }
        String str6 = str3;
        if ((i3 & 32) != 0) {
            str4 = examQuestion.content;
        }
        String str7 = str4;
        if ((i3 & 64) != 0) {
            z2 = examQuestion.itemA1XFirst;
        }
        return examQuestion.copy(str, list2, str5, i4, str6, str7, z2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamQuestion self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.examQuestionVoList != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamQuestionVo$$serializer.INSTANCE)), self.examQuestionVoList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.questionType, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.questionType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.type != -1) {
            output.encodeIntElement(serialDesc, 3, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.correct, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.correct);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || self.itemA1XFirst) {
            output.encodeBooleanElement(serialDesc, 6, self.itemA1XFirst);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final List<ExamQuestionVo> component2() {
        return this.examQuestionVoList;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getQuestionType() {
        return this.questionType;
    }

    /* renamed from: component4, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getCorrect() {
        return this.correct;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getItemA1XFirst() {
        return this.itemA1XFirst;
    }

    @NotNull
    public final ExamQuestion copy(@Nullable String description, @Nullable List<ExamQuestionVo> examQuestionVoList, @Nullable String questionType, int type, @Nullable String correct, @Nullable String content, boolean itemA1XFirst) {
        return new ExamQuestion(description, examQuestionVoList, questionType, type, correct, content, itemA1XFirst);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamQuestion)) {
            return false;
        }
        ExamQuestion examQuestion = (ExamQuestion) other;
        return Intrinsics.areEqual(this.description, examQuestion.description) && Intrinsics.areEqual(this.examQuestionVoList, examQuestion.examQuestionVoList) && Intrinsics.areEqual(this.questionType, examQuestion.questionType) && this.type == examQuestion.type && Intrinsics.areEqual(this.correct, examQuestion.correct) && Intrinsics.areEqual(this.content, examQuestion.content) && this.itemA1XFirst == examQuestion.itemA1XFirst;
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
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final List<ExamQuestionVo> getExamQuestionVoList() {
        return this.examQuestionVoList;
    }

    public final boolean getItemA1XFirst() {
        return this.itemA1XFirst;
    }

    @Nullable
    public final String getQuestionType() {
        return this.questionType;
    }

    public final int getType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.description;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        List<ExamQuestionVo> list = this.examQuestionVoList;
        int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
        String str2 = this.questionType;
        int iHashCode3 = (((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.type) * 31;
        String str3 = this.correct;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.content;
        int iHashCode5 = (iHashCode4 + (str4 != null ? str4.hashCode() : 0)) * 31;
        boolean z2 = this.itemA1XFirst;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode5 + i2;
    }

    public final void setItemA1XFirst(boolean z2) {
        this.itemA1XFirst = z2;
    }

    @NotNull
    public String toString() {
        return "ExamQuestion(description=" + this.description + ", examQuestionVoList=" + this.examQuestionVoList + ", questionType=" + this.questionType + ", type=" + this.type + ", correct=" + this.correct + ", content=" + this.content + ", itemA1XFirst=" + this.itemA1XFirst + ")";
    }

    public ExamQuestion(@Nullable String str, @Nullable List<ExamQuestionVo> list, @Nullable String str2, int i2, @Nullable String str3, @Nullable String str4, boolean z2) {
        this.description = str;
        this.examQuestionVoList = list;
        this.questionType = str2;
        this.type = i2;
        this.correct = str3;
        this.content = str4;
        this.itemA1XFirst = z2;
    }

    public /* synthetic */ ExamQuestion(String str, List list, String str2, int i2, String str3, String str4, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? null : list, (i3 & 4) != 0 ? "" : str2, (i3 & 8) != 0 ? -1 : i2, (i3 & 16) != 0 ? "" : str3, (i3 & 32) == 0 ? str4 : "", (i3 & 64) != 0 ? false : z2);
    }
}
