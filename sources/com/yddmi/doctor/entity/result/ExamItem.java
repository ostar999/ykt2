package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.alipay.sdk.util.j;
import com.tencent.open.SocialConstants;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.FloatSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\bJ\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 ~2\u00020\u0001:\u0002}~B×\u0002\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010 \u001a\u00020\u0003\u0012\u0010\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"\u0012\u0010\u0010$\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"\u0012\u0010\u0010%\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"\u0012\b\u0010&\u001a\u0004\u0018\u00010'¢\u0006\u0002\u0010(Bÿ\u0002\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0015\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\u0012\b\u0002\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"\u0012\u0012\b\u0002\u0010$\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"\u0012\u0012\b\u0002\u0010%\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"¢\u0006\u0002\u0010)J\u0010\u0010Q\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u0010\u0010R\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u000b\u0010S\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010T\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010U\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010V\u001a\u00020\u0003HÆ\u0003J\u0010\u0010W\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u0010\u0010X\u001a\u0004\u0018\u00010\u0015HÆ\u0003¢\u0006\u0002\u0010:J\u0010\u0010Y\u001a\u0004\u0018\u00010\u0015HÆ\u0003¢\u0006\u0002\u0010:J\t\u0010Z\u001a\u00020\u0003HÆ\u0003J\u0010\u0010[\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u000b\u0010\\\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010]\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010^\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u0010\u0010_\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u0010\u0010`\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u000b\u0010a\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010b\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\u0010\u0010c\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010+J\t\u0010d\u001a\u00020\u0003HÆ\u0003J\u0013\u0010e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"HÆ\u0003J\u0013\u0010f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"HÆ\u0003J\u000b\u0010g\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0013\u0010h\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"HÆ\u0003J\u000b\u0010i\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010j\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010l\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010m\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010n\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0088\u0003\u0010o\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010 \u001a\u00020\u00032\u0012\b\u0002\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"2\u0012\b\u0002\u0010$\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"2\u0012\b\u0002\u0010%\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"HÆ\u0001¢\u0006\u0002\u0010pJ\u0013\u0010q\u001a\u00020r2\b\u0010s\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010t\u001a\u00020\u0003HÖ\u0001J\t\u0010u\u001a\u00020\u0006HÖ\u0001J!\u0010v\u001a\u00020w2\u0006\u0010x\u001a\u00020\u00002\u0006\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020|HÇ\u0001R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\b*\u0010+R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b/\u0010.R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b0\u0010.R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b1\u0010.R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b2\u0010.R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b3\u0010.R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b4\u0010.R\u001b\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u001b\u0010$\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"¢\u0006\b\n\u0000\u001a\u0004\b7\u00106R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b8\u0010.R\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u0015¢\u0006\n\n\u0002\u0010;\u001a\u0004\b9\u0010:R\u001b\u0010%\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010#\u0018\u00010\"¢\u0006\b\n\u0000\u001a\u0004\b<\u00106R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\b=\u0010+R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b>\u0010.R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b?\u0010.R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b@\u0010.R\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\bA\u0010+R\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bB\u0010CR\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\bD\u0010+R\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\n\n\u0002\u0010;\u001a\u0004\bE\u0010:R\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\bF\u0010+R\u0011\u0010\u0017\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bG\u0010CR\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\bH\u0010+R\u001e\u0010\u001b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010,\u001a\u0004\bI\u0010+\"\u0004\bJ\u0010KR\u0011\u0010 \u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bL\u0010CR\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bM\u0010.R\u0015\u0010\u001a\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\bN\u0010+R\u0015\u0010\u001c\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010,\u001a\u0004\bO\u0010+R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bP\u0010.¨\u0006\u007f"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamItem;", "", "seen1", "", "candidateSize", "contentType", "", "createTime", "createUser", "createUserName", "delayDay", "description", "endTime", "examResultSet", "examTime", "id", "invitationCode", "name", "paperFinishCount", "paperWeight", "passScore", "", "examScore", "patientFinishCount", "patientWeight", "startTime", "status", j.f3383a, "submitPaperSize", "type", "patientChosen", "paperChosen", SocialConstants.PARAM_SPECIFIED, "examCandidateVoList", "", "Lcom/yddmi/doctor/entity/result/ExamRow;", "examPatientVoList", "examTheoryPaperVoList", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/Float;Ljava/lang/Float;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/util/List;Ljava/util/List;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/Float;Ljava/lang/Float;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/util/List;Ljava/util/List;Ljava/util/List;)V", "getCandidateSize", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getContentType", "()Ljava/lang/String;", "getCreateTime", "getCreateUser", "getCreateUserName", "getDelayDay", "getDescription", "getEndTime", "getExamCandidateVoList", "()Ljava/util/List;", "getExamPatientVoList", "getExamResultSet", "getExamScore", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getExamTheoryPaperVoList", "getExamTime", "getId", "getInvitationCode", "getName", "getPaperChosen", "getPaperFinishCount", "()I", "getPaperWeight", "getPassScore", "getPatientChosen", "getPatientFinishCount", "getPatientWeight", "getResultStatus", "setResultStatus", "(Ljava/lang/Integer;)V", "getSpecified", "getStartTime", "getStatus", "getSubmitPaperSize", "getType", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/lang/Float;Ljava/lang/Float;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/util/List;Ljava/util/List;Ljava/util/List;)Lcom/yddmi/doctor/entity/result/ExamItem;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class ExamItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer candidateSize;

    @Nullable
    private final String contentType;

    @Nullable
    private final String createTime;

    @Nullable
    private final String createUser;

    @Nullable
    private final String createUserName;

    @Nullable
    private final String delayDay;

    @Nullable
    private final String description;

    @Nullable
    private final String endTime;

    @Nullable
    private final List<ExamRow> examCandidateVoList;

    @Nullable
    private final List<ExamRow> examPatientVoList;

    @Nullable
    private final String examResultSet;

    @Nullable
    private final Float examScore;

    @Nullable
    private final List<ExamRow> examTheoryPaperVoList;

    @Nullable
    private final Integer examTime;

    @Nullable
    private final String id;

    @Nullable
    private final String invitationCode;

    @Nullable
    private final String name;

    @Nullable
    private final Integer paperChosen;
    private final int paperFinishCount;

    @Nullable
    private final Integer paperWeight;

    @Nullable
    private final Float passScore;

    @Nullable
    private final Integer patientChosen;
    private final int patientFinishCount;

    @Nullable
    private final Integer patientWeight;

    @Nullable
    private Integer resultStatus;
    private final int specified;

    @Nullable
    private final String startTime;

    @Nullable
    private final Integer status;

    @Nullable
    private final Integer submitPaperSize;

    @Nullable
    private final String type;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/ExamItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/ExamItem;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<ExamItem> serializer() {
            return ExamItem$$serializer.INSTANCE;
        }
    }

    public ExamItem() {
        this((Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (String) null, 0, (Integer) null, (Float) null, (Float) null, 0, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, 0, (List) null, (List) null, (List) null, LockFreeTaskQueueCore.MAX_CAPACITY_MASK, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ ExamItem(int i2, Integer num, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Integer num2, String str9, String str10, String str11, int i3, Integer num3, Float f2, Float f3, int i4, Integer num4, String str12, Integer num5, Integer num6, Integer num7, String str13, Integer num8, Integer num9, int i5, List list, List list2, List list3, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, ExamItem$$serializer.INSTANCE.getDescriptor());
        }
        this.candidateSize = (i2 & 1) == 0 ? 0 : num;
        if ((i2 & 2) == 0) {
            this.contentType = "";
        } else {
            this.contentType = str;
        }
        if ((i2 & 4) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str2;
        }
        if ((i2 & 8) == 0) {
            this.createUser = "";
        } else {
            this.createUser = str3;
        }
        if ((i2 & 16) == 0) {
            this.createUserName = "";
        } else {
            this.createUserName = str4;
        }
        if ((i2 & 32) == 0) {
            this.delayDay = "";
        } else {
            this.delayDay = str5;
        }
        if ((i2 & 64) == 0) {
            this.description = "";
        } else {
            this.description = str6;
        }
        if ((i2 & 128) == 0) {
            this.endTime = "";
        } else {
            this.endTime = str7;
        }
        if ((i2 & 256) == 0) {
            this.examResultSet = "";
        } else {
            this.examResultSet = str8;
        }
        this.examTime = (i2 & 512) == 0 ? -1 : num2;
        if ((i2 & 1024) == 0) {
            this.id = "";
        } else {
            this.id = str9;
        }
        if ((i2 & 2048) == 0) {
            this.invitationCode = "";
        } else {
            this.invitationCode = str10;
        }
        if ((i2 & 4096) == 0) {
            this.name = "";
        } else {
            this.name = str11;
        }
        if ((i2 & 8192) == 0) {
            this.paperFinishCount = 0;
        } else {
            this.paperFinishCount = i3;
        }
        this.paperWeight = (i2 & 16384) == 0 ? -1 : num3;
        this.passScore = (32768 & i2) == 0 ? Float.valueOf(0.0f) : f2;
        this.examScore = (65536 & i2) == 0 ? Float.valueOf(0.0f) : f3;
        if ((131072 & i2) == 0) {
            this.patientFinishCount = 0;
        } else {
            this.patientFinishCount = i4;
        }
        this.patientWeight = (262144 & i2) == 0 ? -1 : num4;
        if ((524288 & i2) == 0) {
            this.startTime = "";
        } else {
            this.startTime = str12;
        }
        this.status = (1048576 & i2) == 0 ? -1 : num5;
        this.resultStatus = (2097152 & i2) == 0 ? -1 : num6;
        this.submitPaperSize = (4194304 & i2) == 0 ? -1 : num7;
        if ((8388608 & i2) == 0) {
            this.type = "";
        } else {
            this.type = str13;
        }
        this.patientChosen = (16777216 & i2) == 0 ? -1 : num8;
        this.paperChosen = (33554432 & i2) == 0 ? -1 : num9;
        if ((67108864 & i2) == 0) {
            this.specified = -1;
        } else {
            this.specified = i5;
        }
        if ((134217728 & i2) == 0) {
            this.examCandidateVoList = null;
        } else {
            this.examCandidateVoList = list;
        }
        if ((268435456 & i2) == 0) {
            this.examPatientVoList = null;
        } else {
            this.examPatientVoList = list2;
        }
        if ((i2 & 536870912) == 0) {
            this.examTheoryPaperVoList = null;
        } else {
            this.examTheoryPaperVoList = list3;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull ExamItem self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Integer num7;
        Integer num8;
        Integer num9;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.candidateSize) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.candidateSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.contentType, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.contentType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.createUser, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.createUser);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.createUserName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.createUserName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.delayDay, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.delayDay);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.endTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.endTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.examResultSet, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.examResultSet);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || (num2 = self.examTime) == null || num2.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 9, IntSerializer.INSTANCE, self.examTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.invitationCode, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.invitationCode);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || self.paperFinishCount != 0) {
            output.encodeIntElement(serialDesc, 13, self.paperFinishCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || (num3 = self.paperWeight) == null || num3.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 14, IntSerializer.INSTANCE, self.paperWeight);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || !Intrinsics.areEqual((Object) self.passScore, (Object) Float.valueOf(0.0f))) {
            output.encodeNullableSerializableElement(serialDesc, 15, FloatSerializer.INSTANCE, self.passScore);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || !Intrinsics.areEqual((Object) self.examScore, (Object) Float.valueOf(0.0f))) {
            output.encodeNullableSerializableElement(serialDesc, 16, FloatSerializer.INSTANCE, self.examScore);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || self.patientFinishCount != 0) {
            output.encodeIntElement(serialDesc, 17, self.patientFinishCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 18) || (num4 = self.patientWeight) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 18, IntSerializer.INSTANCE, self.patientWeight);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 19) || !Intrinsics.areEqual(self.startTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 19, StringSerializer.INSTANCE, self.startTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 20) || (num5 = self.status) == null || num5.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 20, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 21) || (num6 = self.resultStatus) == null || num6.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 21, IntSerializer.INSTANCE, self.resultStatus);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 22) || (num7 = self.submitPaperSize) == null || num7.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 22, IntSerializer.INSTANCE, self.submitPaperSize);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 23) || !Intrinsics.areEqual(self.type, "")) {
            output.encodeNullableSerializableElement(serialDesc, 23, StringSerializer.INSTANCE, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 24) || (num8 = self.patientChosen) == null || num8.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 24, IntSerializer.INSTANCE, self.patientChosen);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 25) || (num9 = self.paperChosen) == null || num9.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 25, IntSerializer.INSTANCE, self.paperChosen);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 26) || self.specified != -1) {
            output.encodeIntElement(serialDesc, 26, self.specified);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 27) || self.examCandidateVoList != null) {
            output.encodeNullableSerializableElement(serialDesc, 27, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRow$$serializer.INSTANCE)), self.examCandidateVoList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 28) || self.examPatientVoList != null) {
            output.encodeNullableSerializableElement(serialDesc, 28, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRow$$serializer.INSTANCE)), self.examPatientVoList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 29) || self.examTheoryPaperVoList != null) {
            output.encodeNullableSerializableElement(serialDesc, 29, new ArrayListSerializer(BuiltinSerializersKt.getNullable(ExamRow$$serializer.INSTANCE)), self.examTheoryPaperVoList);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getCandidateSize() {
        return this.candidateSize;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final Integer getExamTime() {
        return this.examTime;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getInvitationCode() {
        return this.invitationCode;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component14, reason: from getter */
    public final int getPaperFinishCount() {
        return this.paperFinishCount;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final Integer getPaperWeight() {
        return this.paperWeight;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final Float getPassScore() {
        return this.passScore;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final Float getExamScore() {
        return this.examScore;
    }

    /* renamed from: component18, reason: from getter */
    public final int getPatientFinishCount() {
        return this.patientFinishCount;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final Integer getPatientWeight() {
        return this.patientWeight;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getContentType() {
        return this.contentType;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getStartTime() {
        return this.startTime;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final Integer getResultStatus() {
        return this.resultStatus;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final Integer getSubmitPaperSize() {
        return this.submitPaperSize;
    }

    @Nullable
    /* renamed from: component24, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component25, reason: from getter */
    public final Integer getPatientChosen() {
        return this.patientChosen;
    }

    @Nullable
    /* renamed from: component26, reason: from getter */
    public final Integer getPaperChosen() {
        return this.paperChosen;
    }

    /* renamed from: component27, reason: from getter */
    public final int getSpecified() {
        return this.specified;
    }

    @Nullable
    public final List<ExamRow> component28() {
        return this.examCandidateVoList;
    }

    @Nullable
    public final List<ExamRow> component29() {
        return this.examPatientVoList;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final List<ExamRow> component30() {
        return this.examTheoryPaperVoList;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getCreateUser() {
        return this.createUser;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getCreateUserName() {
        return this.createUserName;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getDelayDay() {
        return this.delayDay;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getEndTime() {
        return this.endTime;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getExamResultSet() {
        return this.examResultSet;
    }

    @NotNull
    public final ExamItem copy(@Nullable Integer candidateSize, @Nullable String contentType, @Nullable String createTime, @Nullable String createUser, @Nullable String createUserName, @Nullable String delayDay, @Nullable String description, @Nullable String endTime, @Nullable String examResultSet, @Nullable Integer examTime, @Nullable String id, @Nullable String invitationCode, @Nullable String name, int paperFinishCount, @Nullable Integer paperWeight, @Nullable Float passScore, @Nullable Float examScore, int patientFinishCount, @Nullable Integer patientWeight, @Nullable String startTime, @Nullable Integer status, @Nullable Integer resultStatus, @Nullable Integer submitPaperSize, @Nullable String type, @Nullable Integer patientChosen, @Nullable Integer paperChosen, int specified, @Nullable List<ExamRow> examCandidateVoList, @Nullable List<ExamRow> examPatientVoList, @Nullable List<ExamRow> examTheoryPaperVoList) {
        return new ExamItem(candidateSize, contentType, createTime, createUser, createUserName, delayDay, description, endTime, examResultSet, examTime, id, invitationCode, name, paperFinishCount, paperWeight, passScore, examScore, patientFinishCount, patientWeight, startTime, status, resultStatus, submitPaperSize, type, patientChosen, paperChosen, specified, examCandidateVoList, examPatientVoList, examTheoryPaperVoList);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExamItem)) {
            return false;
        }
        ExamItem examItem = (ExamItem) other;
        return Intrinsics.areEqual(this.candidateSize, examItem.candidateSize) && Intrinsics.areEqual(this.contentType, examItem.contentType) && Intrinsics.areEqual(this.createTime, examItem.createTime) && Intrinsics.areEqual(this.createUser, examItem.createUser) && Intrinsics.areEqual(this.createUserName, examItem.createUserName) && Intrinsics.areEqual(this.delayDay, examItem.delayDay) && Intrinsics.areEqual(this.description, examItem.description) && Intrinsics.areEqual(this.endTime, examItem.endTime) && Intrinsics.areEqual(this.examResultSet, examItem.examResultSet) && Intrinsics.areEqual(this.examTime, examItem.examTime) && Intrinsics.areEqual(this.id, examItem.id) && Intrinsics.areEqual(this.invitationCode, examItem.invitationCode) && Intrinsics.areEqual(this.name, examItem.name) && this.paperFinishCount == examItem.paperFinishCount && Intrinsics.areEqual(this.paperWeight, examItem.paperWeight) && Intrinsics.areEqual((Object) this.passScore, (Object) examItem.passScore) && Intrinsics.areEqual((Object) this.examScore, (Object) examItem.examScore) && this.patientFinishCount == examItem.patientFinishCount && Intrinsics.areEqual(this.patientWeight, examItem.patientWeight) && Intrinsics.areEqual(this.startTime, examItem.startTime) && Intrinsics.areEqual(this.status, examItem.status) && Intrinsics.areEqual(this.resultStatus, examItem.resultStatus) && Intrinsics.areEqual(this.submitPaperSize, examItem.submitPaperSize) && Intrinsics.areEqual(this.type, examItem.type) && Intrinsics.areEqual(this.patientChosen, examItem.patientChosen) && Intrinsics.areEqual(this.paperChosen, examItem.paperChosen) && this.specified == examItem.specified && Intrinsics.areEqual(this.examCandidateVoList, examItem.examCandidateVoList) && Intrinsics.areEqual(this.examPatientVoList, examItem.examPatientVoList) && Intrinsics.areEqual(this.examTheoryPaperVoList, examItem.examTheoryPaperVoList);
    }

    @Nullable
    public final Integer getCandidateSize() {
        return this.candidateSize;
    }

    @Nullable
    public final String getContentType() {
        return this.contentType;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final String getCreateUser() {
        return this.createUser;
    }

    @Nullable
    public final String getCreateUserName() {
        return this.createUserName;
    }

    @Nullable
    public final String getDelayDay() {
        return this.delayDay;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getEndTime() {
        return this.endTime;
    }

    @Nullable
    public final List<ExamRow> getExamCandidateVoList() {
        return this.examCandidateVoList;
    }

    @Nullable
    public final List<ExamRow> getExamPatientVoList() {
        return this.examPatientVoList;
    }

    @Nullable
    public final String getExamResultSet() {
        return this.examResultSet;
    }

    @Nullable
    public final Float getExamScore() {
        return this.examScore;
    }

    @Nullable
    public final List<ExamRow> getExamTheoryPaperVoList() {
        return this.examTheoryPaperVoList;
    }

    @Nullable
    public final Integer getExamTime() {
        return this.examTime;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getInvitationCode() {
        return this.invitationCode;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Integer getPaperChosen() {
        return this.paperChosen;
    }

    public final int getPaperFinishCount() {
        return this.paperFinishCount;
    }

    @Nullable
    public final Integer getPaperWeight() {
        return this.paperWeight;
    }

    @Nullable
    public final Float getPassScore() {
        return this.passScore;
    }

    @Nullable
    public final Integer getPatientChosen() {
        return this.patientChosen;
    }

    public final int getPatientFinishCount() {
        return this.patientFinishCount;
    }

    @Nullable
    public final Integer getPatientWeight() {
        return this.patientWeight;
    }

    @Nullable
    public final Integer getResultStatus() {
        return this.resultStatus;
    }

    public final int getSpecified() {
        return this.specified;
    }

    @Nullable
    public final String getStartTime() {
        return this.startTime;
    }

    @Nullable
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    public final Integer getSubmitPaperSize() {
        return this.submitPaperSize;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        Integer num = this.candidateSize;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.contentType;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.createTime;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.createUser;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.createUserName;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.delayDay;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.description;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.endTime;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.examResultSet;
        int iHashCode9 = (iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Integer num2 = this.examTime;
        int iHashCode10 = (iHashCode9 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str9 = this.id;
        int iHashCode11 = (iHashCode10 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.invitationCode;
        int iHashCode12 = (iHashCode11 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.name;
        int iHashCode13 = (((iHashCode12 + (str11 == null ? 0 : str11.hashCode())) * 31) + this.paperFinishCount) * 31;
        Integer num3 = this.paperWeight;
        int iHashCode14 = (iHashCode13 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Float f2 = this.passScore;
        int iHashCode15 = (iHashCode14 + (f2 == null ? 0 : f2.hashCode())) * 31;
        Float f3 = this.examScore;
        int iHashCode16 = (((iHashCode15 + (f3 == null ? 0 : f3.hashCode())) * 31) + this.patientFinishCount) * 31;
        Integer num4 = this.patientWeight;
        int iHashCode17 = (iHashCode16 + (num4 == null ? 0 : num4.hashCode())) * 31;
        String str12 = this.startTime;
        int iHashCode18 = (iHashCode17 + (str12 == null ? 0 : str12.hashCode())) * 31;
        Integer num5 = this.status;
        int iHashCode19 = (iHashCode18 + (num5 == null ? 0 : num5.hashCode())) * 31;
        Integer num6 = this.resultStatus;
        int iHashCode20 = (iHashCode19 + (num6 == null ? 0 : num6.hashCode())) * 31;
        Integer num7 = this.submitPaperSize;
        int iHashCode21 = (iHashCode20 + (num7 == null ? 0 : num7.hashCode())) * 31;
        String str13 = this.type;
        int iHashCode22 = (iHashCode21 + (str13 == null ? 0 : str13.hashCode())) * 31;
        Integer num8 = this.patientChosen;
        int iHashCode23 = (iHashCode22 + (num8 == null ? 0 : num8.hashCode())) * 31;
        Integer num9 = this.paperChosen;
        int iHashCode24 = (((iHashCode23 + (num9 == null ? 0 : num9.hashCode())) * 31) + this.specified) * 31;
        List<ExamRow> list = this.examCandidateVoList;
        int iHashCode25 = (iHashCode24 + (list == null ? 0 : list.hashCode())) * 31;
        List<ExamRow> list2 = this.examPatientVoList;
        int iHashCode26 = (iHashCode25 + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<ExamRow> list3 = this.examTheoryPaperVoList;
        return iHashCode26 + (list3 != null ? list3.hashCode() : 0);
    }

    public final void setResultStatus(@Nullable Integer num) {
        this.resultStatus = num;
    }

    @NotNull
    public String toString() {
        return "ExamItem(candidateSize=" + this.candidateSize + ", contentType=" + this.contentType + ", createTime=" + this.createTime + ", createUser=" + this.createUser + ", createUserName=" + this.createUserName + ", delayDay=" + this.delayDay + ", description=" + this.description + ", endTime=" + this.endTime + ", examResultSet=" + this.examResultSet + ", examTime=" + this.examTime + ", id=" + this.id + ", invitationCode=" + this.invitationCode + ", name=" + this.name + ", paperFinishCount=" + this.paperFinishCount + ", paperWeight=" + this.paperWeight + ", passScore=" + this.passScore + ", examScore=" + this.examScore + ", patientFinishCount=" + this.patientFinishCount + ", patientWeight=" + this.patientWeight + ", startTime=" + this.startTime + ", status=" + this.status + ", resultStatus=" + this.resultStatus + ", submitPaperSize=" + this.submitPaperSize + ", type=" + this.type + ", patientChosen=" + this.patientChosen + ", paperChosen=" + this.paperChosen + ", specified=" + this.specified + ", examCandidateVoList=" + this.examCandidateVoList + ", examPatientVoList=" + this.examPatientVoList + ", examTheoryPaperVoList=" + this.examTheoryPaperVoList + ")";
    }

    public ExamItem(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable Integer num2, @Nullable String str9, @Nullable String str10, @Nullable String str11, int i2, @Nullable Integer num3, @Nullable Float f2, @Nullable Float f3, int i3, @Nullable Integer num4, @Nullable String str12, @Nullable Integer num5, @Nullable Integer num6, @Nullable Integer num7, @Nullable String str13, @Nullable Integer num8, @Nullable Integer num9, int i4, @Nullable List<ExamRow> list, @Nullable List<ExamRow> list2, @Nullable List<ExamRow> list3) {
        this.candidateSize = num;
        this.contentType = str;
        this.createTime = str2;
        this.createUser = str3;
        this.createUserName = str4;
        this.delayDay = str5;
        this.description = str6;
        this.endTime = str7;
        this.examResultSet = str8;
        this.examTime = num2;
        this.id = str9;
        this.invitationCode = str10;
        this.name = str11;
        this.paperFinishCount = i2;
        this.paperWeight = num3;
        this.passScore = f2;
        this.examScore = f3;
        this.patientFinishCount = i3;
        this.patientWeight = num4;
        this.startTime = str12;
        this.status = num5;
        this.resultStatus = num6;
        this.submitPaperSize = num7;
        this.type = str13;
        this.patientChosen = num8;
        this.paperChosen = num9;
        this.specified = i4;
        this.examCandidateVoList = list;
        this.examPatientVoList = list2;
        this.examTheoryPaperVoList = list3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ExamItem(Integer num, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Integer num2, String str9, String str10, String str11, int i2, Integer num3, Float f2, Float f3, int i3, Integer num4, String str12, Integer num5, Integer num6, Integer num7, String str13, Integer num8, Integer num9, int i4, List list, List list2, List list3, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        String str14;
        int i6;
        Integer num10;
        Integer num11 = (i5 & 1) != 0 ? 0 : num;
        String str15 = (i5 & 2) != 0 ? "" : str;
        String str16 = (i5 & 4) != 0 ? "" : str2;
        String str17 = (i5 & 8) != 0 ? "" : str3;
        String str18 = (i5 & 16) != 0 ? "" : str4;
        String str19 = (i5 & 32) != 0 ? "" : str5;
        String str20 = (i5 & 64) != 0 ? "" : str6;
        String str21 = (i5 & 128) != 0 ? "" : str7;
        String str22 = (i5 & 256) != 0 ? "" : str8;
        Integer num12 = (i5 & 512) != 0 ? -1 : num2;
        String str23 = (i5 & 1024) != 0 ? "" : str9;
        String str24 = (i5 & 2048) != 0 ? "" : str10;
        String str25 = (i5 & 4096) != 0 ? "" : str11;
        int i7 = (i5 & 8192) != 0 ? 0 : i2;
        str14 = "";
        Integer num13 = (i5 & 16384) != 0 ? -1 : num3;
        Float fValueOf = (32768 & i5) != 0 ? Float.valueOf(0.0f) : f2;
        Float fValueOf2 = (i5 & 65536) != 0 ? Float.valueOf(0.0f) : f3;
        int i8 = (i5 & 131072) != 0 ? 0 : i3;
        if ((i5 & 262144) != 0) {
            i6 = -1;
            num10 = -1;
        } else {
            i6 = -1;
            num10 = num4;
        }
        this(num11, str15, str16, str17, str18, str19, str20, str21, str22, num12, str23, str24, str25, i7, num13, fValueOf, fValueOf2, i8, num10, (i5 & 524288) != 0 ? str14 : str12, (i5 & 1048576) != 0 ? Integer.valueOf(i6) : num5, (i5 & 2097152) != 0 ? Integer.valueOf(i6) : num6, (i5 & 4194304) != 0 ? Integer.valueOf(i6) : num7, (i5 & 8388608) == 0 ? str13 : "", (i5 & 16777216) != 0 ? Integer.valueOf(i6) : num8, (i5 & 33554432) != 0 ? Integer.valueOf(i6) : num9, (i5 & 67108864) == 0 ? i4 : i6, (i5 & 134217728) != 0 ? null : list, (i5 & 268435456) != 0 ? null : list2, (i5 & 536870912) == 0 ? list3 : null);
    }
}
