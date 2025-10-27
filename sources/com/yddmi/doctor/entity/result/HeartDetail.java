package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
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
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\bQ\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u0088\u00012\u00020\u0001:\u0004\u0087\u0001\u0088\u0001Bã\u0002\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0015\u001a\u00020\u0003\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010 \u001a\u00020\u0003\u0012\b\u0010!\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\"\u001a\u0004\u0018\u00010#\u0012\u0006\u0010$\u001a\u00020\u0003\u0012\u0006\u0010%\u001a\u00020\u0003\u0012\u0006\u0010&\u001a\u00020\u0003\u0012\u0006\u0010'\u001a\u00020\u0003\u0012\b\u0010(\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010)\u001a\u0004\u0018\u00010*¢\u0006\u0002\u0010+B\u0089\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#\u0012\b\b\u0002\u0010$\u001a\u00020\u0003\u0012\b\b\u0002\u0010%\u001a\u00020\u0003\u0012\b\b\u0002\u0010&\u001a\u00020\u0003\u0012\b\b\u0002\u0010'\u001a\u00020\u0003\u0012\b\b\u0002\u0010(\u001a\u00020\u0006¢\u0006\u0002\u0010,J\u000b\u0010W\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010X\u001a\u00020\u0003HÆ\u0003J\u000b\u0010Y\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010[\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\\\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010]\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010^\u001a\u00020\u0003HÆ\u0003J\t\u0010_\u001a\u00020\u0003HÆ\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010a\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010b\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010c\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010d\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010e\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010f\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010g\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010h\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010i\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010j\u001a\u00020\u0003HÆ\u0003J\u000b\u0010k\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010l\u001a\u0004\u0018\u00010#HÆ\u0003J\t\u0010m\u001a\u00020\u0003HÆ\u0003J\t\u0010n\u001a\u00020\u0003HÆ\u0003J\t\u0010o\u001a\u00020\u0003HÆ\u0003J\t\u0010p\u001a\u00020\u0003HÆ\u0003J\t\u0010q\u001a\u00020\u0003HÆ\u0003J\t\u0010r\u001a\u00020\u0006HÆ\u0003J\u000b\u0010s\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010t\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010u\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010v\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0010\u0010w\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u00109J\u000b\u0010x\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0092\u0003\u0010y\u001a\u00020\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010 \u001a\u00020\u00032\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\b\b\u0002\u0010$\u001a\u00020\u00032\b\b\u0002\u0010%\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010'\u001a\u00020\u00032\b\b\u0002\u0010(\u001a\u00020\u0006HÆ\u0001¢\u0006\u0002\u0010zJ\u0013\u0010{\u001a\u00020|2\b\u0010}\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010~\u001a\u00020\u0003HÖ\u0001J\t\u0010\u007f\u001a\u00020\u0006HÖ\u0001J(\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010\u0082\u0001\u001a\u00020\u00002\b\u0010\u0083\u0001\u001a\u00030\u0084\u00012\b\u0010\u0085\u0001\u001a\u00030\u0086\u0001HÇ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b3\u0010.R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b4\u0010.R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b5\u0010.R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b6\u0010.R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b7\u0010.R\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010:\u001a\u0004\b8\u00109R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b;\u0010.R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b<\u00100R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b=\u0010.R\u0011\u0010$\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b>\u00100R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b?\u0010.R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b@\u0010.R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bA\u0010.R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bB\u0010.R\u001a\u0010\u0015\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u00100\"\u0004\bC\u00102R\u001a\u0010\u0016\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u00100\"\u0004\bD\u00102R\u0011\u0010'\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u00100R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bE\u0010.R\u001a\u0010(\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010.\"\u0004\bG\u0010HR\u0011\u0010%\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bI\u00100R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010.R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bK\u0010.R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bL\u0010.R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bM\u0010.R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bN\u0010.R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bO\u0010.R\u0013\u0010\"\u001a\u0004\u0018\u00010#¢\u0006\b\n\u0000\u001a\u0004\bP\u0010QR\u0013\u0010\u001e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bR\u0010.R\u0013\u0010\u001f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bS\u0010.R\u0011\u0010 \u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bT\u00100R\u0011\u0010&\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bU\u00100R\u0013\u0010!\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bV\u0010.¨\u0006\u0089\u0001"}, d2 = {"Lcom/yddmi/doctor/entity/result/HeartDetail;", "", "seen1", "", "seen2", "audioName", "", "audioUrl", "audioTime", "briefMedicalHistory", "categoryId", "categoryName", "characteristics", "code", "commonDiseases", "continueFlag", "diagnosticFormula", "fileName", "fileUrl", "imageName", "imageUrl", "isFavorite", "isMastered", "largeName", "mechanism", "medicalKnowledgeId", "mnemonic", "name", "part", "patientSummary", "ratingCriteriaId", "ratingCriteriaName", "status", "updateTime", "ratingCriteria", "Lcom/yddmi/doctor/entity/result/RatingCriteria;", "favoriteNum", "masteredNum", "totalNum", "isVip", "mDescription", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/yddmi/doctor/entity/result/RatingCriteria;IIIILjava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/yddmi/doctor/entity/result/RatingCriteria;IIIILjava/lang/String;)V", "getAudioName", "()Ljava/lang/String;", "getAudioTime", "()I", "setAudioTime", "(I)V", "getAudioUrl", "getBriefMedicalHistory", "getCategoryId", "getCategoryName", "getCharacteristics", "getCode", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCommonDiseases", "getContinueFlag", "getDiagnosticFormula", "getFavoriteNum", "getFileName", "getFileUrl", "getImageName", "getImageUrl", "setFavorite", "setMastered", "getLargeName", "getMDescription", "setMDescription", "(Ljava/lang/String;)V", "getMasteredNum", "getMechanism", "getMedicalKnowledgeId", "getMnemonic", "getName", "getPart", "getPatientSummary", "getRatingCriteria", "()Lcom/yddmi/doctor/entity/result/RatingCriteria;", "getRatingCriteriaId", "getRatingCriteriaName", "getStatus", "getTotalNum", "getUpdateTime", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/yddmi/doctor/entity/result/RatingCriteria;IIIILjava/lang/String;)Lcom/yddmi/doctor/entity/result/HeartDetail;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HeartDetail {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String audioName;
    private int audioTime;

    @Nullable
    private final String audioUrl;

    @Nullable
    private final String briefMedicalHistory;

    @Nullable
    private final String categoryId;

    @Nullable
    private final String categoryName;

    @Nullable
    private final String characteristics;

    @Nullable
    private final Integer code;

    @Nullable
    private final String commonDiseases;
    private final int continueFlag;

    @Nullable
    private final String diagnosticFormula;
    private final int favoriteNum;

    @Nullable
    private final String fileName;

    @Nullable
    private final String fileUrl;

    @Nullable
    private final String imageName;

    @Nullable
    private final String imageUrl;
    private int isFavorite;
    private int isMastered;
    private final int isVip;

    @Nullable
    private final String largeName;

    @NotNull
    private String mDescription;
    private final int masteredNum;

    @Nullable
    private final String mechanism;

    @Nullable
    private final String medicalKnowledgeId;

    @Nullable
    private final String mnemonic;

    @Nullable
    private final String name;

    @Nullable
    private final String part;

    @Nullable
    private final String patientSummary;

    @Nullable
    private final RatingCriteria ratingCriteria;

    @Nullable
    private final String ratingCriteriaId;

    @Nullable
    private final String ratingCriteriaName;
    private final int status;
    private final int totalNum;

    @Nullable
    private final String updateTime;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HeartDetail$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HeartDetail> serializer() {
            return HeartDetail$$serializer.INSTANCE;
        }
    }

    public HeartDetail() {
        this((String) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, 0, (String) null, (String) null, (String) null, (String) null, (String) null, 0, 0, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 0, (String) null, (RatingCriteria) null, 0, 0, 0, 0, (String) null, -1, 3, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HeartDetail(int i2, int i3, String str, String str2, int i4, String str3, String str4, String str5, String str6, Integer num, String str7, int i5, String str8, String str9, String str10, String str11, String str12, int i6, int i7, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, int i8, String str22, RatingCriteria ratingCriteria, int i9, int i10, int i11, int i12, String str23, SerializationConstructorMarker serializationConstructorMarker) {
        if (((i2 & 0) != 0) | ((i3 & 0) != 0)) {
            PluginExceptionsKt.throwArrayMissingFieldException(new int[]{i2, i3}, new int[]{0, 0}, HeartDetail$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.audioName = "";
        } else {
            this.audioName = str;
        }
        if ((i2 & 2) == 0) {
            this.audioUrl = "";
        } else {
            this.audioUrl = str2;
        }
        if ((i2 & 4) == 0) {
            this.audioTime = 0;
        } else {
            this.audioTime = i4;
        }
        if ((i2 & 8) == 0) {
            this.briefMedicalHistory = "";
        } else {
            this.briefMedicalHistory = str3;
        }
        if ((i2 & 16) == 0) {
            this.categoryId = "";
        } else {
            this.categoryId = str4;
        }
        if ((i2 & 32) == 0) {
            this.categoryName = "";
        } else {
            this.categoryName = str5;
        }
        if ((i2 & 64) == 0) {
            this.characteristics = "";
        } else {
            this.characteristics = str6;
        }
        this.code = (i2 & 128) == 0 ? -1 : num;
        if ((i2 & 256) == 0) {
            this.commonDiseases = "";
        } else {
            this.commonDiseases = str7;
        }
        if ((i2 & 512) == 0) {
            this.continueFlag = 0;
        } else {
            this.continueFlag = i5;
        }
        if ((i2 & 1024) == 0) {
            this.diagnosticFormula = "";
        } else {
            this.diagnosticFormula = str8;
        }
        if ((i2 & 2048) == 0) {
            this.fileName = "";
        } else {
            this.fileName = str9;
        }
        if ((i2 & 4096) == 0) {
            this.fileUrl = "";
        } else {
            this.fileUrl = str10;
        }
        if ((i2 & 8192) == 0) {
            this.imageName = "";
        } else {
            this.imageName = str11;
        }
        if ((i2 & 16384) == 0) {
            this.imageUrl = "";
        } else {
            this.imageUrl = str12;
        }
        if ((32768 & i2) == 0) {
            this.isFavorite = 0;
        } else {
            this.isFavorite = i6;
        }
        if ((65536 & i2) == 0) {
            this.isMastered = 0;
        } else {
            this.isMastered = i7;
        }
        if ((131072 & i2) == 0) {
            this.largeName = "";
        } else {
            this.largeName = str13;
        }
        if ((262144 & i2) == 0) {
            this.mechanism = "";
        } else {
            this.mechanism = str14;
        }
        if ((524288 & i2) == 0) {
            this.medicalKnowledgeId = "";
        } else {
            this.medicalKnowledgeId = str15;
        }
        if ((1048576 & i2) == 0) {
            this.mnemonic = "";
        } else {
            this.mnemonic = str16;
        }
        if ((2097152 & i2) == 0) {
            this.name = "";
        } else {
            this.name = str17;
        }
        if ((4194304 & i2) == 0) {
            this.part = "";
        } else {
            this.part = str18;
        }
        if ((8388608 & i2) == 0) {
            this.patientSummary = "";
        } else {
            this.patientSummary = str19;
        }
        if ((16777216 & i2) == 0) {
            this.ratingCriteriaId = "";
        } else {
            this.ratingCriteriaId = str20;
        }
        if ((33554432 & i2) == 0) {
            this.ratingCriteriaName = "";
        } else {
            this.ratingCriteriaName = str21;
        }
        if ((67108864 & i2) == 0) {
            this.status = 0;
        } else {
            this.status = i8;
        }
        if ((134217728 & i2) == 0) {
            this.updateTime = "";
        } else {
            this.updateTime = str22;
        }
        this.ratingCriteria = (268435456 & i2) == 0 ? null : ratingCriteria;
        if ((536870912 & i2) == 0) {
            this.favoriteNum = 0;
        } else {
            this.favoriteNum = i9;
        }
        if ((1073741824 & i2) == 0) {
            this.masteredNum = 0;
        } else {
            this.masteredNum = i10;
        }
        if ((i2 & Integer.MIN_VALUE) == 0) {
            this.totalNum = 0;
        } else {
            this.totalNum = i11;
        }
        if ((i3 & 1) == 0) {
            this.isVip = 0;
        } else {
            this.isVip = i12;
        }
        if ((i3 & 2) == 0) {
            this.mDescription = "";
        } else {
            this.mDescription = str23;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull HeartDetail self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.audioName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.audioName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.audioUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.audioUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.audioTime != 0) {
            output.encodeIntElement(serialDesc, 2, self.audioTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.briefMedicalHistory, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.briefMedicalHistory);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || !Intrinsics.areEqual(self.categoryId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 4, StringSerializer.INSTANCE, self.categoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.categoryName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.categoryName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.characteristics, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.characteristics);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || (num = self.code) == null || num.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 7, IntSerializer.INSTANCE, self.code);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.commonDiseases, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.commonDiseases);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.continueFlag != 0) {
            output.encodeIntElement(serialDesc, 9, self.continueFlag);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.diagnosticFormula, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.diagnosticFormula);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.fileName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.fileName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.fileUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.fileUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.imageName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.imageName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || !Intrinsics.areEqual(self.imageUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 14, StringSerializer.INSTANCE, self.imageUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || self.isFavorite != 0) {
            output.encodeIntElement(serialDesc, 15, self.isFavorite);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || self.isMastered != 0) {
            output.encodeIntElement(serialDesc, 16, self.isMastered);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || !Intrinsics.areEqual(self.largeName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 17, StringSerializer.INSTANCE, self.largeName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 18) || !Intrinsics.areEqual(self.mechanism, "")) {
            output.encodeNullableSerializableElement(serialDesc, 18, StringSerializer.INSTANCE, self.mechanism);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 19) || !Intrinsics.areEqual(self.medicalKnowledgeId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 19, StringSerializer.INSTANCE, self.medicalKnowledgeId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 20) || !Intrinsics.areEqual(self.mnemonic, "")) {
            output.encodeNullableSerializableElement(serialDesc, 20, StringSerializer.INSTANCE, self.mnemonic);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 21) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 21, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 22) || !Intrinsics.areEqual(self.part, "")) {
            output.encodeNullableSerializableElement(serialDesc, 22, StringSerializer.INSTANCE, self.part);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 23) || !Intrinsics.areEqual(self.patientSummary, "")) {
            output.encodeNullableSerializableElement(serialDesc, 23, StringSerializer.INSTANCE, self.patientSummary);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 24) || !Intrinsics.areEqual(self.ratingCriteriaId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 24, StringSerializer.INSTANCE, self.ratingCriteriaId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 25) || !Intrinsics.areEqual(self.ratingCriteriaName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 25, StringSerializer.INSTANCE, self.ratingCriteriaName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 26) || self.status != 0) {
            output.encodeIntElement(serialDesc, 26, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 27) || !Intrinsics.areEqual(self.updateTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 27, StringSerializer.INSTANCE, self.updateTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 28) || self.ratingCriteria != null) {
            output.encodeNullableSerializableElement(serialDesc, 28, RatingCriteria$$serializer.INSTANCE, self.ratingCriteria);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 29) || self.favoriteNum != 0) {
            output.encodeIntElement(serialDesc, 29, self.favoriteNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 30) || self.masteredNum != 0) {
            output.encodeIntElement(serialDesc, 30, self.masteredNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 31) || self.totalNum != 0) {
            output.encodeIntElement(serialDesc, 31, self.totalNum);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 32) || self.isVip != 0) {
            output.encodeIntElement(serialDesc, 32, self.isVip);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 33) || !Intrinsics.areEqual(self.mDescription, "")) {
            output.encodeStringElement(serialDesc, 33, self.mDescription);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getAudioName() {
        return this.audioName;
    }

    /* renamed from: component10, reason: from getter */
    public final int getContinueFlag() {
        return this.continueFlag;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getDiagnosticFormula() {
        return this.diagnosticFormula;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getFileName() {
        return this.fileName;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getFileUrl() {
        return this.fileUrl;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getImageName() {
        return this.imageName;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getImageUrl() {
        return this.imageUrl;
    }

    /* renamed from: component16, reason: from getter */
    public final int getIsFavorite() {
        return this.isFavorite;
    }

    /* renamed from: component17, reason: from getter */
    public final int getIsMastered() {
        return this.isMastered;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getLargeName() {
        return this.largeName;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getMechanism() {
        return this.mechanism;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getAudioUrl() {
        return this.audioUrl;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getMnemonic() {
        return this.mnemonic;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final String getPart() {
        return this.part;
    }

    @Nullable
    /* renamed from: component24, reason: from getter */
    public final String getPatientSummary() {
        return this.patientSummary;
    }

    @Nullable
    /* renamed from: component25, reason: from getter */
    public final String getRatingCriteriaId() {
        return this.ratingCriteriaId;
    }

    @Nullable
    /* renamed from: component26, reason: from getter */
    public final String getRatingCriteriaName() {
        return this.ratingCriteriaName;
    }

    /* renamed from: component27, reason: from getter */
    public final int getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component28, reason: from getter */
    public final String getUpdateTime() {
        return this.updateTime;
    }

    @Nullable
    /* renamed from: component29, reason: from getter */
    public final RatingCriteria getRatingCriteria() {
        return this.ratingCriteria;
    }

    /* renamed from: component3, reason: from getter */
    public final int getAudioTime() {
        return this.audioTime;
    }

    /* renamed from: component30, reason: from getter */
    public final int getFavoriteNum() {
        return this.favoriteNum;
    }

    /* renamed from: component31, reason: from getter */
    public final int getMasteredNum() {
        return this.masteredNum;
    }

    /* renamed from: component32, reason: from getter */
    public final int getTotalNum() {
        return this.totalNum;
    }

    /* renamed from: component33, reason: from getter */
    public final int getIsVip() {
        return this.isVip;
    }

    @NotNull
    /* renamed from: component34, reason: from getter */
    public final String getMDescription() {
        return this.mDescription;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getBriefMedicalHistory() {
        return this.briefMedicalHistory;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getCategoryName() {
        return this.categoryName;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getCharacteristics() {
        return this.characteristics;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final Integer getCode() {
        return this.code;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getCommonDiseases() {
        return this.commonDiseases;
    }

    @NotNull
    public final HeartDetail copy(@Nullable String audioName, @Nullable String audioUrl, int audioTime, @Nullable String briefMedicalHistory, @Nullable String categoryId, @Nullable String categoryName, @Nullable String characteristics, @Nullable Integer code, @Nullable String commonDiseases, int continueFlag, @Nullable String diagnosticFormula, @Nullable String fileName, @Nullable String fileUrl, @Nullable String imageName, @Nullable String imageUrl, int isFavorite, int isMastered, @Nullable String largeName, @Nullable String mechanism, @Nullable String medicalKnowledgeId, @Nullable String mnemonic, @Nullable String name, @Nullable String part, @Nullable String patientSummary, @Nullable String ratingCriteriaId, @Nullable String ratingCriteriaName, int status, @Nullable String updateTime, @Nullable RatingCriteria ratingCriteria, int favoriteNum, int masteredNum, int totalNum, int isVip, @NotNull String mDescription) {
        Intrinsics.checkNotNullParameter(mDescription, "mDescription");
        return new HeartDetail(audioName, audioUrl, audioTime, briefMedicalHistory, categoryId, categoryName, characteristics, code, commonDiseases, continueFlag, diagnosticFormula, fileName, fileUrl, imageName, imageUrl, isFavorite, isMastered, largeName, mechanism, medicalKnowledgeId, mnemonic, name, part, patientSummary, ratingCriteriaId, ratingCriteriaName, status, updateTime, ratingCriteria, favoriteNum, masteredNum, totalNum, isVip, mDescription);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HeartDetail)) {
            return false;
        }
        HeartDetail heartDetail = (HeartDetail) other;
        return Intrinsics.areEqual(this.audioName, heartDetail.audioName) && Intrinsics.areEqual(this.audioUrl, heartDetail.audioUrl) && this.audioTime == heartDetail.audioTime && Intrinsics.areEqual(this.briefMedicalHistory, heartDetail.briefMedicalHistory) && Intrinsics.areEqual(this.categoryId, heartDetail.categoryId) && Intrinsics.areEqual(this.categoryName, heartDetail.categoryName) && Intrinsics.areEqual(this.characteristics, heartDetail.characteristics) && Intrinsics.areEqual(this.code, heartDetail.code) && Intrinsics.areEqual(this.commonDiseases, heartDetail.commonDiseases) && this.continueFlag == heartDetail.continueFlag && Intrinsics.areEqual(this.diagnosticFormula, heartDetail.diagnosticFormula) && Intrinsics.areEqual(this.fileName, heartDetail.fileName) && Intrinsics.areEqual(this.fileUrl, heartDetail.fileUrl) && Intrinsics.areEqual(this.imageName, heartDetail.imageName) && Intrinsics.areEqual(this.imageUrl, heartDetail.imageUrl) && this.isFavorite == heartDetail.isFavorite && this.isMastered == heartDetail.isMastered && Intrinsics.areEqual(this.largeName, heartDetail.largeName) && Intrinsics.areEqual(this.mechanism, heartDetail.mechanism) && Intrinsics.areEqual(this.medicalKnowledgeId, heartDetail.medicalKnowledgeId) && Intrinsics.areEqual(this.mnemonic, heartDetail.mnemonic) && Intrinsics.areEqual(this.name, heartDetail.name) && Intrinsics.areEqual(this.part, heartDetail.part) && Intrinsics.areEqual(this.patientSummary, heartDetail.patientSummary) && Intrinsics.areEqual(this.ratingCriteriaId, heartDetail.ratingCriteriaId) && Intrinsics.areEqual(this.ratingCriteriaName, heartDetail.ratingCriteriaName) && this.status == heartDetail.status && Intrinsics.areEqual(this.updateTime, heartDetail.updateTime) && Intrinsics.areEqual(this.ratingCriteria, heartDetail.ratingCriteria) && this.favoriteNum == heartDetail.favoriteNum && this.masteredNum == heartDetail.masteredNum && this.totalNum == heartDetail.totalNum && this.isVip == heartDetail.isVip && Intrinsics.areEqual(this.mDescription, heartDetail.mDescription);
    }

    @Nullable
    public final String getAudioName() {
        return this.audioName;
    }

    public final int getAudioTime() {
        return this.audioTime;
    }

    @Nullable
    public final String getAudioUrl() {
        return this.audioUrl;
    }

    @Nullable
    public final String getBriefMedicalHistory() {
        return this.briefMedicalHistory;
    }

    @Nullable
    public final String getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final String getCategoryName() {
        return this.categoryName;
    }

    @Nullable
    public final String getCharacteristics() {
        return this.characteristics;
    }

    @Nullable
    public final Integer getCode() {
        return this.code;
    }

    @Nullable
    public final String getCommonDiseases() {
        return this.commonDiseases;
    }

    public final int getContinueFlag() {
        return this.continueFlag;
    }

    @Nullable
    public final String getDiagnosticFormula() {
        return this.diagnosticFormula;
    }

    public final int getFavoriteNum() {
        return this.favoriteNum;
    }

    @Nullable
    public final String getFileName() {
        return this.fileName;
    }

    @Nullable
    public final String getFileUrl() {
        return this.fileUrl;
    }

    @Nullable
    public final String getImageName() {
        return this.imageName;
    }

    @Nullable
    public final String getImageUrl() {
        return this.imageUrl;
    }

    @Nullable
    public final String getLargeName() {
        return this.largeName;
    }

    @NotNull
    public final String getMDescription() {
        return this.mDescription;
    }

    public final int getMasteredNum() {
        return this.masteredNum;
    }

    @Nullable
    public final String getMechanism() {
        return this.mechanism;
    }

    @Nullable
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    @Nullable
    public final String getMnemonic() {
        return this.mnemonic;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPart() {
        return this.part;
    }

    @Nullable
    public final String getPatientSummary() {
        return this.patientSummary;
    }

    @Nullable
    public final RatingCriteria getRatingCriteria() {
        return this.ratingCriteria;
    }

    @Nullable
    public final String getRatingCriteriaId() {
        return this.ratingCriteriaId;
    }

    @Nullable
    public final String getRatingCriteriaName() {
        return this.ratingCriteriaName;
    }

    public final int getStatus() {
        return this.status;
    }

    public final int getTotalNum() {
        return this.totalNum;
    }

    @Nullable
    public final String getUpdateTime() {
        return this.updateTime;
    }

    public int hashCode() {
        String str = this.audioName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.audioUrl;
        int iHashCode2 = (((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + this.audioTime) * 31;
        String str3 = this.briefMedicalHistory;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.categoryId;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.categoryName;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.characteristics;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        Integer num = this.code;
        int iHashCode7 = (iHashCode6 + (num == null ? 0 : num.hashCode())) * 31;
        String str7 = this.commonDiseases;
        int iHashCode8 = (((iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31) + this.continueFlag) * 31;
        String str8 = this.diagnosticFormula;
        int iHashCode9 = (iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.fileName;
        int iHashCode10 = (iHashCode9 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.fileUrl;
        int iHashCode11 = (iHashCode10 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.imageName;
        int iHashCode12 = (iHashCode11 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.imageUrl;
        int iHashCode13 = (((((iHashCode12 + (str12 == null ? 0 : str12.hashCode())) * 31) + this.isFavorite) * 31) + this.isMastered) * 31;
        String str13 = this.largeName;
        int iHashCode14 = (iHashCode13 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.mechanism;
        int iHashCode15 = (iHashCode14 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.medicalKnowledgeId;
        int iHashCode16 = (iHashCode15 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.mnemonic;
        int iHashCode17 = (iHashCode16 + (str16 == null ? 0 : str16.hashCode())) * 31;
        String str17 = this.name;
        int iHashCode18 = (iHashCode17 + (str17 == null ? 0 : str17.hashCode())) * 31;
        String str18 = this.part;
        int iHashCode19 = (iHashCode18 + (str18 == null ? 0 : str18.hashCode())) * 31;
        String str19 = this.patientSummary;
        int iHashCode20 = (iHashCode19 + (str19 == null ? 0 : str19.hashCode())) * 31;
        String str20 = this.ratingCriteriaId;
        int iHashCode21 = (iHashCode20 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.ratingCriteriaName;
        int iHashCode22 = (((iHashCode21 + (str21 == null ? 0 : str21.hashCode())) * 31) + this.status) * 31;
        String str22 = this.updateTime;
        int iHashCode23 = (iHashCode22 + (str22 == null ? 0 : str22.hashCode())) * 31;
        RatingCriteria ratingCriteria = this.ratingCriteria;
        return ((((((((((iHashCode23 + (ratingCriteria != null ? ratingCriteria.hashCode() : 0)) * 31) + this.favoriteNum) * 31) + this.masteredNum) * 31) + this.totalNum) * 31) + this.isVip) * 31) + this.mDescription.hashCode();
    }

    public final int isFavorite() {
        return this.isFavorite;
    }

    public final int isMastered() {
        return this.isMastered;
    }

    public final int isVip() {
        return this.isVip;
    }

    public final void setAudioTime(int i2) {
        this.audioTime = i2;
    }

    public final void setFavorite(int i2) {
        this.isFavorite = i2;
    }

    public final void setMDescription(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mDescription = str;
    }

    public final void setMastered(int i2) {
        this.isMastered = i2;
    }

    @NotNull
    public String toString() {
        return "HeartDetail(audioName=" + this.audioName + ", audioUrl=" + this.audioUrl + ", audioTime=" + this.audioTime + ", briefMedicalHistory=" + this.briefMedicalHistory + ", categoryId=" + this.categoryId + ", categoryName=" + this.categoryName + ", characteristics=" + this.characteristics + ", code=" + this.code + ", commonDiseases=" + this.commonDiseases + ", continueFlag=" + this.continueFlag + ", diagnosticFormula=" + this.diagnosticFormula + ", fileName=" + this.fileName + ", fileUrl=" + this.fileUrl + ", imageName=" + this.imageName + ", imageUrl=" + this.imageUrl + ", isFavorite=" + this.isFavorite + ", isMastered=" + this.isMastered + ", largeName=" + this.largeName + ", mechanism=" + this.mechanism + ", medicalKnowledgeId=" + this.medicalKnowledgeId + ", mnemonic=" + this.mnemonic + ", name=" + this.name + ", part=" + this.part + ", patientSummary=" + this.patientSummary + ", ratingCriteriaId=" + this.ratingCriteriaId + ", ratingCriteriaName=" + this.ratingCriteriaName + ", status=" + this.status + ", updateTime=" + this.updateTime + ", ratingCriteria=" + this.ratingCriteria + ", favoriteNum=" + this.favoriteNum + ", masteredNum=" + this.masteredNum + ", totalNum=" + this.totalNum + ", isVip=" + this.isVip + ", mDescription=" + this.mDescription + ")";
    }

    public HeartDetail(@Nullable String str, @Nullable String str2, int i2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Integer num, @Nullable String str7, int i3, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11, @Nullable String str12, int i4, int i5, @Nullable String str13, @Nullable String str14, @Nullable String str15, @Nullable String str16, @Nullable String str17, @Nullable String str18, @Nullable String str19, @Nullable String str20, @Nullable String str21, int i6, @Nullable String str22, @Nullable RatingCriteria ratingCriteria, int i7, int i8, int i9, int i10, @NotNull String mDescription) {
        Intrinsics.checkNotNullParameter(mDescription, "mDescription");
        this.audioName = str;
        this.audioUrl = str2;
        this.audioTime = i2;
        this.briefMedicalHistory = str3;
        this.categoryId = str4;
        this.categoryName = str5;
        this.characteristics = str6;
        this.code = num;
        this.commonDiseases = str7;
        this.continueFlag = i3;
        this.diagnosticFormula = str8;
        this.fileName = str9;
        this.fileUrl = str10;
        this.imageName = str11;
        this.imageUrl = str12;
        this.isFavorite = i4;
        this.isMastered = i5;
        this.largeName = str13;
        this.mechanism = str14;
        this.medicalKnowledgeId = str15;
        this.mnemonic = str16;
        this.name = str17;
        this.part = str18;
        this.patientSummary = str19;
        this.ratingCriteriaId = str20;
        this.ratingCriteriaName = str21;
        this.status = i6;
        this.updateTime = str22;
        this.ratingCriteria = ratingCriteria;
        this.favoriteNum = i7;
        this.masteredNum = i8;
        this.totalNum = i9;
        this.isVip = i10;
        this.mDescription = mDescription;
    }

    public /* synthetic */ HeartDetail(String str, String str2, int i2, String str3, String str4, String str5, String str6, Integer num, String str7, int i3, String str8, String str9, String str10, String str11, String str12, int i4, int i5, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, int i6, String str22, RatingCriteria ratingCriteria, int i7, int i8, int i9, int i10, String str23, int i11, int i12, DefaultConstructorMarker defaultConstructorMarker) {
        this((i11 & 1) != 0 ? "" : str, (i11 & 2) != 0 ? "" : str2, (i11 & 4) != 0 ? 0 : i2, (i11 & 8) != 0 ? "" : str3, (i11 & 16) != 0 ? "" : str4, (i11 & 32) != 0 ? "" : str5, (i11 & 64) != 0 ? "" : str6, (i11 & 128) != 0 ? -1 : num, (i11 & 256) != 0 ? "" : str7, (i11 & 512) != 0 ? 0 : i3, (i11 & 1024) != 0 ? "" : str8, (i11 & 2048) != 0 ? "" : str9, (i11 & 4096) != 0 ? "" : str10, (i11 & 8192) != 0 ? "" : str11, (i11 & 16384) != 0 ? "" : str12, (i11 & 32768) != 0 ? 0 : i4, (i11 & 65536) != 0 ? 0 : i5, (i11 & 131072) != 0 ? "" : str13, (i11 & 262144) != 0 ? "" : str14, (i11 & 524288) != 0 ? "" : str15, (i11 & 1048576) != 0 ? "" : str16, (i11 & 2097152) != 0 ? "" : str17, (i11 & 4194304) != 0 ? "" : str18, (i11 & 8388608) != 0 ? "" : str19, (i11 & 16777216) != 0 ? "" : str20, (i11 & 33554432) != 0 ? "" : str21, (i11 & 67108864) != 0 ? 0 : i6, (i11 & 134217728) != 0 ? "" : str22, (i11 & 268435456) != 0 ? null : ratingCriteria, (i11 & 536870912) != 0 ? 0 : i7, (i11 & 1073741824) != 0 ? 0 : i8, (i11 & Integer.MIN_VALUE) != 0 ? 0 : i9, (i12 & 1) != 0 ? 0 : i10, (i12 & 2) != 0 ? "" : str23);
    }
}
