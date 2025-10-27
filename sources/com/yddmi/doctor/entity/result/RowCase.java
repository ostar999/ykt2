package com.yddmi.doctor.entity.result;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
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
import kotlinx.serialization.internal.LongSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z.a;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\t\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\br\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 ³\u00012\u00020\u0001:\u0004²\u0001³\u0001Bû\u0003\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010 \u001a\u0004\u0018\u00010!\u0012\b\u0010\"\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010$\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010%\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010&\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010'\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010(\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010)\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010*\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010+\u001a\u00020\u0003\u0012\b\u0010,\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010-\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010.\u001a\u00020!\u0012\b\u0010/\u001a\u0004\u0018\u00010\u0007\u0012\b\u00100\u001a\u0004\u0018\u00010\u0007\u0012\b\u00101\u001a\u0004\u0018\u00010\u0007\u0012\b\u00102\u001a\u0004\u0018\u00010\u0007\u0012\b\u00103\u001a\u0004\u0018\u00010\u0007\u0012\b\u00104\u001a\u0004\u0018\u00010\u0007\u0012\b\u00105\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u00106\u001a\u000207\u0012\b\u00108\u001a\u0004\u0018\u000109¢\u0006\u0002\u0010:B¿\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010+\u001a\u00020\u0003\u0012\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010.\u001a\u00020!\u0012\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00104\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u00105\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u00106\u001a\u000207¢\u0006\u0002\u0010;J\u0010\u0010u\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u000b\u0010v\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0010\u0010w\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0010\u0010x\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0010\u0010y\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u000b\u0010z\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010{\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010|\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010}\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010~\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u007f\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u0082\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\f\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u0088\u0001\u001a\u0004\u0018\u00010!HÆ\u0003¢\u0006\u0002\u0010mJ\u0011\u0010\u0089\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\f\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u008d\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u008e\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\f\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\u0011\u0010\u0091\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\f\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\n\u0010\u0093\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u0096\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\n\u0010\u0097\u0001\u001a\u00020!HÆ\u0003J\f\u0010\u0098\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u0099\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u009e\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\n\u0010\u009f\u0001\u001a\u000207HÆ\u0003J\u0011\u0010 \u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010=J\f\u0010¡\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010¢\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010£\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\f\u0010¤\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003JÊ\u0004\u0010¥\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010+\u001a\u00020\u00032\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010.\u001a\u00020!2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00104\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u00105\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u00106\u001a\u000207HÆ\u0001¢\u0006\u0003\u0010¦\u0001J\u0015\u0010§\u0001\u001a\u0002072\t\u0010¨\u0001\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\n\u0010©\u0001\u001a\u00020\u0003HÖ\u0001J\n\u0010ª\u0001\u001a\u00020\u0007HÖ\u0001J(\u0010«\u0001\u001a\u00030¬\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u00002\b\u0010®\u0001\u001a\u00030¯\u00012\b\u0010°\u0001\u001a\u00030±\u0001HÇ\u0001R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\b<\u0010=R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0015\u0010#\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bA\u0010=R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bB\u0010@R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bC\u0010@R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bD\u0010@R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bE\u0010=R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bF\u0010@R\u0015\u0010$\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bG\u0010=R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bH\u0010@R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bI\u0010@R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010@R\u0013\u0010/\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bK\u0010@R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bL\u0010=R\u0013\u00103\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bM\u0010@R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bN\u0010=R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\b\u0012\u0010=R\u001e\u00105\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010>\u001a\u0004\b5\u0010=\"\u0004\bO\u0010PR\u0015\u0010%\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\b%\u0010=R\u001a\u00106\u001a\u000207X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bU\u0010@R\u0011\u0010+\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bV\u0010WR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bX\u0010@R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bY\u0010@R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010@R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b[\u0010@R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010@R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b]\u0010@R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b^\u0010@R\u0015\u0010)\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\b_\u0010=R\u0015\u0010\u001b\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\b`\u0010=R\u0013\u00104\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\ba\u0010@R\u0013\u0010-\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bb\u0010@R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bc\u0010@R\u0013\u0010,\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bd\u0010@R\u0013\u0010*\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\be\u0010@R\u0013\u0010'\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bf\u0010@R\u0015\u0010\u001d\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bg\u0010=R\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bh\u0010=R\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bi\u0010=R\u0011\u0010.\u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\bj\u0010kR\u0015\u0010 \u001a\u0004\u0018\u00010!¢\u0006\n\n\u0002\u0010n\u001a\u0004\bl\u0010mR\u0015\u0010&\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bo\u0010=R\u0015\u0010(\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bp\u0010=R\u0015\u0010\"\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010>\u001a\u0004\bq\u0010=R\u0013\u00101\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\br\u0010@R\u0013\u00100\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bs\u0010@R\u0013\u00102\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\bt\u0010@¨\u0006´\u0001"}, d2 = {"Lcom/yddmi/doctor/entity/result/RowCase;", "", "seen1", "", "seen2", "age", "allCategory", "", "caseName", "degree", "deptId", "departmentName", "description", "diseaseHistory", "diseaseType", "educationDegree", "gender", "id", "isExam", "job", "lastStopTime", "mainInfo", "marryStatus", "modelId", "name", "nationality", "nativePlace", "patientId", "pregnancy", "score", "status", TypedValues.AttributesType.S_TARGET, "timeCost", "", "userId", "basisCount", "diagnoseId", "isMain", "trainId", "result", "type", "operateId", "resolution", "kind", "remark", "picUrl", CrashHianalyticsData.TIME, "fileName", "videoName", "videoDescription", "videoUrl", "iconPicUrl", "peoplePicUrl", "isIdentity", "itemSelect", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;ZLkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Z)V", "getAge", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAllCategory", "()Ljava/lang/String;", "getBasisCount", "getCaseName", "getDegree", "getDepartmentName", "getDeptId", "getDescription", "getDiagnoseId", "getDiseaseHistory", "getDiseaseType", "getEducationDegree", "getFileName", "getGender", "getIconPicUrl", "getId", "setIdentity", "(Ljava/lang/Integer;)V", "getItemSelect", "()Z", "setItemSelect", "(Z)V", "getJob", "getKind", "()I", "getLastStopTime", "getMainInfo", "getMarryStatus", "getModelId", "getName", "getNationality", "getNativePlace", "getOperateId", "getPatientId", "getPeoplePicUrl", "getPicUrl", "getPregnancy", "getRemark", "getResolution", "getResult", "getScore", "getStatus", "getTarget", "getTime", "()J", "getTimeCost", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getTrainId", "getType", "getUserId", "getVideoDescription", "getVideoName", "getVideoUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component47", "component48", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Z)Lcom/yddmi/doctor/entity/result/RowCase;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class RowCase {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final Integer age;

    @Nullable
    private final String allCategory;

    @Nullable
    private final Integer basisCount;

    @Nullable
    private final String caseName;

    @Nullable
    private final String degree;

    @Nullable
    private final String departmentName;

    @Nullable
    private final Integer deptId;

    @Nullable
    private final String description;

    @Nullable
    private final Integer diagnoseId;

    @Nullable
    private final String diseaseHistory;

    @Nullable
    private final String diseaseType;

    @Nullable
    private final String educationDegree;

    @Nullable
    private final String fileName;

    @Nullable
    private final Integer gender;

    @Nullable
    private final String iconPicUrl;

    @Nullable
    private final Integer id;

    @Nullable
    private final Integer isExam;

    @Nullable
    private Integer isIdentity;

    @Nullable
    private final Integer isMain;
    private boolean itemSelect;

    @Nullable
    private final String job;
    private final int kind;

    @Nullable
    private final String lastStopTime;

    @Nullable
    private final String mainInfo;

    @Nullable
    private final String marryStatus;

    @Nullable
    private final String modelId;

    @Nullable
    private final String name;

    @Nullable
    private final String nationality;

    @Nullable
    private final String nativePlace;

    @Nullable
    private final Integer operateId;

    @Nullable
    private final Integer patientId;

    @Nullable
    private final String peoplePicUrl;

    @Nullable
    private final String picUrl;

    @Nullable
    private final String pregnancy;

    @Nullable
    private final String remark;

    @Nullable
    private final String resolution;

    @Nullable
    private final String result;

    @Nullable
    private final Integer score;

    @Nullable
    private final Integer status;

    @Nullable
    private final Integer target;
    private final long time;

    @Nullable
    private final Long timeCost;

    @Nullable
    private final Integer trainId;

    @Nullable
    private final Integer type;

    @Nullable
    private final Integer userId;

    @Nullable
    private final String videoDescription;

    @Nullable
    private final String videoName;

    @Nullable
    private final String videoUrl;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/RowCase$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/RowCase;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<RowCase> serializer() {
            return RowCase$$serializer.INSTANCE;
        }
    }

    public RowCase() {
        this((Integer) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (Integer) null, (Long) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, (String) null, 0, (String) null, (String) null, 0L, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (Integer) null, false, -1, 65535, (DefaultConstructorMarker) null);
    }

    public RowCase(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Integer num2, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable Integer num3, @Nullable Integer num4, @Nullable Integer num5, @Nullable String str9, @Nullable String str10, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable String str14, @Nullable String str15, @Nullable String str16, @Nullable Integer num6, @Nullable String str17, @Nullable Integer num7, @Nullable Integer num8, @Nullable Integer num9, @Nullable Long l2, @Nullable Integer num10, @Nullable Integer num11, @Nullable Integer num12, @Nullable Integer num13, @Nullable Integer num14, @Nullable String str18, @Nullable Integer num15, @Nullable Integer num16, @Nullable String str19, int i2, @Nullable String str20, @Nullable String str21, long j2, @Nullable String str22, @Nullable String str23, @Nullable String str24, @Nullable String str25, @Nullable String str26, @Nullable String str27, @Nullable Integer num17, boolean z2) {
        this.age = num;
        this.allCategory = str;
        this.caseName = str2;
        this.degree = str3;
        this.deptId = num2;
        this.departmentName = str4;
        this.description = str5;
        this.diseaseHistory = str6;
        this.diseaseType = str7;
        this.educationDegree = str8;
        this.gender = num3;
        this.id = num4;
        this.isExam = num5;
        this.job = str9;
        this.lastStopTime = str10;
        this.mainInfo = str11;
        this.marryStatus = str12;
        this.modelId = str13;
        this.name = str14;
        this.nationality = str15;
        this.nativePlace = str16;
        this.patientId = num6;
        this.pregnancy = str17;
        this.score = num7;
        this.status = num8;
        this.target = num9;
        this.timeCost = l2;
        this.userId = num10;
        this.basisCount = num11;
        this.diagnoseId = num12;
        this.isMain = num13;
        this.trainId = num14;
        this.result = str18;
        this.type = num15;
        this.operateId = num16;
        this.resolution = str19;
        this.kind = i2;
        this.remark = str20;
        this.picUrl = str21;
        this.time = j2;
        this.fileName = str22;
        this.videoName = str23;
        this.videoDescription = str24;
        this.videoUrl = str25;
        this.iconPicUrl = str26;
        this.peoplePicUrl = str27;
        this.isIdentity = num17;
        this.itemSelect = z2;
    }

    @JvmStatic
    public static final void write$Self(@NotNull RowCase self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Integer num7;
        Integer num8;
        Integer num9;
        Long l2;
        Integer num10;
        Integer num11;
        Integer num12;
        Integer num13;
        Integer num14;
        Integer num15;
        Integer num16;
        Integer num17;
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || (num = self.age) == null || num.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 0, IntSerializer.INSTANCE, self.age);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.allCategory, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.allCategory);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.caseName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.caseName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || !Intrinsics.areEqual(self.degree, "")) {
            output.encodeNullableSerializableElement(serialDesc, 3, StringSerializer.INSTANCE, self.degree);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || (num2 = self.deptId) == null || num2.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 4, IntSerializer.INSTANCE, self.deptId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.departmentName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 5, StringSerializer.INSTANCE, self.departmentName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.description, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.description);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.diseaseHistory, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.diseaseHistory);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.diseaseType, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.diseaseType);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || !Intrinsics.areEqual(self.educationDegree, "")) {
            output.encodeNullableSerializableElement(serialDesc, 9, StringSerializer.INSTANCE, self.educationDegree);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || (num3 = self.gender) == null || num3.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 10, IntSerializer.INSTANCE, self.gender);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || (num4 = self.id) == null || num4.intValue() != -1) {
            output.encodeNullableSerializableElement(serialDesc, 11, IntSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || (num5 = self.isExam) == null || num5.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 12, IntSerializer.INSTANCE, self.isExam);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.job, "")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.job);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || !Intrinsics.areEqual(self.lastStopTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 14, StringSerializer.INSTANCE, self.lastStopTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || !Intrinsics.areEqual(self.mainInfo, "")) {
            output.encodeNullableSerializableElement(serialDesc, 15, StringSerializer.INSTANCE, self.mainInfo);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || !Intrinsics.areEqual(self.marryStatus, "")) {
            output.encodeNullableSerializableElement(serialDesc, 16, StringSerializer.INSTANCE, self.marryStatus);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || !Intrinsics.areEqual(self.modelId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 17, StringSerializer.INSTANCE, self.modelId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 18) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeNullableSerializableElement(serialDesc, 18, StringSerializer.INSTANCE, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 19) || !Intrinsics.areEqual(self.nationality, "")) {
            output.encodeNullableSerializableElement(serialDesc, 19, StringSerializer.INSTANCE, self.nationality);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 20) || !Intrinsics.areEqual(self.nativePlace, "")) {
            output.encodeNullableSerializableElement(serialDesc, 20, StringSerializer.INSTANCE, self.nativePlace);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 21) || (num6 = self.patientId) == null || num6.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 21, IntSerializer.INSTANCE, self.patientId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 22) || !Intrinsics.areEqual(self.pregnancy, "")) {
            output.encodeNullableSerializableElement(serialDesc, 22, StringSerializer.INSTANCE, self.pregnancy);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 23) || (num7 = self.score) == null || num7.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 23, IntSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 24) || (num8 = self.status) == null || num8.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 24, IntSerializer.INSTANCE, self.status);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 25) || (num9 = self.target) == null || num9.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 25, IntSerializer.INSTANCE, self.target);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 26) || (l2 = self.timeCost) == null || l2.longValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 26, LongSerializer.INSTANCE, self.timeCost);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 27) || (num10 = self.userId) == null || num10.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 27, IntSerializer.INSTANCE, self.userId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 28) || (num11 = self.basisCount) == null || num11.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 28, IntSerializer.INSTANCE, self.basisCount);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 29) || (num12 = self.diagnoseId) == null || num12.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 29, IntSerializer.INSTANCE, self.diagnoseId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 30) || (num13 = self.isMain) == null || num13.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 30, IntSerializer.INSTANCE, self.isMain);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 31) || (num14 = self.trainId) == null || num14.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 31, IntSerializer.INSTANCE, self.trainId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 32) || !Intrinsics.areEqual(self.result, "")) {
            output.encodeNullableSerializableElement(serialDesc, 32, StringSerializer.INSTANCE, self.result);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 33) || (num15 = self.type) == null || num15.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 33, IntSerializer.INSTANCE, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 34) || (num16 = self.operateId) == null || num16.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 34, IntSerializer.INSTANCE, self.operateId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 35) || !Intrinsics.areEqual(self.resolution, "")) {
            output.encodeNullableSerializableElement(serialDesc, 35, StringSerializer.INSTANCE, self.resolution);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 36) || self.kind != -1) {
            output.encodeIntElement(serialDesc, 36, self.kind);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 37) || !Intrinsics.areEqual(self.remark, "")) {
            output.encodeNullableSerializableElement(serialDesc, 37, StringSerializer.INSTANCE, self.remark);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 38) || !Intrinsics.areEqual(self.picUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 38, StringSerializer.INSTANCE, self.picUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 39) || self.time != 0) {
            output.encodeLongElement(serialDesc, 39, self.time);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 40) || !Intrinsics.areEqual(self.fileName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 40, StringSerializer.INSTANCE, self.fileName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 41) || !Intrinsics.areEqual(self.videoName, "")) {
            output.encodeNullableSerializableElement(serialDesc, 41, StringSerializer.INSTANCE, self.videoName);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 42) || !Intrinsics.areEqual(self.videoDescription, "")) {
            output.encodeNullableSerializableElement(serialDesc, 42, StringSerializer.INSTANCE, self.videoDescription);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 43) || !Intrinsics.areEqual(self.videoUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 43, StringSerializer.INSTANCE, self.videoUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 44) || !Intrinsics.areEqual(self.iconPicUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 44, StringSerializer.INSTANCE, self.iconPicUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 45) || !Intrinsics.areEqual(self.peoplePicUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 45, StringSerializer.INSTANCE, self.peoplePicUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 46) || (num17 = self.isIdentity) == null || num17.intValue() != 0) {
            output.encodeNullableSerializableElement(serialDesc, 46, IntSerializer.INSTANCE, self.isIdentity);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 47) || self.itemSelect) {
            output.encodeBooleanElement(serialDesc, 47, self.itemSelect);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final Integer getAge() {
        return this.age;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getEducationDegree() {
        return this.educationDegree;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final Integer getGender() {
        return this.gender;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final Integer getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final Integer getIsExam() {
        return this.isExam;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getJob() {
        return this.job;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getLastStopTime() {
        return this.lastStopTime;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getMainInfo() {
        return this.mainInfo;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getMarryStatus() {
        return this.marryStatus;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getModelId() {
        return this.modelId;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getAllCategory() {
        return this.allCategory;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getNationality() {
        return this.nationality;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getNativePlace() {
        return this.nativePlace;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final String getPregnancy() {
        return this.pregnancy;
    }

    @Nullable
    /* renamed from: component24, reason: from getter */
    public final Integer getScore() {
        return this.score;
    }

    @Nullable
    /* renamed from: component25, reason: from getter */
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    /* renamed from: component26, reason: from getter */
    public final Integer getTarget() {
        return this.target;
    }

    @Nullable
    /* renamed from: component27, reason: from getter */
    public final Long getTimeCost() {
        return this.timeCost;
    }

    @Nullable
    /* renamed from: component28, reason: from getter */
    public final Integer getUserId() {
        return this.userId;
    }

    @Nullable
    /* renamed from: component29, reason: from getter */
    public final Integer getBasisCount() {
        return this.basisCount;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getCaseName() {
        return this.caseName;
    }

    @Nullable
    /* renamed from: component30, reason: from getter */
    public final Integer getDiagnoseId() {
        return this.diagnoseId;
    }

    @Nullable
    /* renamed from: component31, reason: from getter */
    public final Integer getIsMain() {
        return this.isMain;
    }

    @Nullable
    /* renamed from: component32, reason: from getter */
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    /* renamed from: component33, reason: from getter */
    public final String getResult() {
        return this.result;
    }

    @Nullable
    /* renamed from: component34, reason: from getter */
    public final Integer getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component35, reason: from getter */
    public final Integer getOperateId() {
        return this.operateId;
    }

    @Nullable
    /* renamed from: component36, reason: from getter */
    public final String getResolution() {
        return this.resolution;
    }

    /* renamed from: component37, reason: from getter */
    public final int getKind() {
        return this.kind;
    }

    @Nullable
    /* renamed from: component38, reason: from getter */
    public final String getRemark() {
        return this.remark;
    }

    @Nullable
    /* renamed from: component39, reason: from getter */
    public final String getPicUrl() {
        return this.picUrl;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getDegree() {
        return this.degree;
    }

    /* renamed from: component40, reason: from getter */
    public final long getTime() {
        return this.time;
    }

    @Nullable
    /* renamed from: component41, reason: from getter */
    public final String getFileName() {
        return this.fileName;
    }

    @Nullable
    /* renamed from: component42, reason: from getter */
    public final String getVideoName() {
        return this.videoName;
    }

    @Nullable
    /* renamed from: component43, reason: from getter */
    public final String getVideoDescription() {
        return this.videoDescription;
    }

    @Nullable
    /* renamed from: component44, reason: from getter */
    public final String getVideoUrl() {
        return this.videoUrl;
    }

    @Nullable
    /* renamed from: component45, reason: from getter */
    public final String getIconPicUrl() {
        return this.iconPicUrl;
    }

    @Nullable
    /* renamed from: component46, reason: from getter */
    public final String getPeoplePicUrl() {
        return this.peoplePicUrl;
    }

    @Nullable
    /* renamed from: component47, reason: from getter */
    public final Integer getIsIdentity() {
        return this.isIdentity;
    }

    /* renamed from: component48, reason: from getter */
    public final boolean getItemSelect() {
        return this.itemSelect;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final Integer getDeptId() {
        return this.deptId;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getDepartmentName() {
        return this.departmentName;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getDiseaseHistory() {
        return this.diseaseHistory;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getDiseaseType() {
        return this.diseaseType;
    }

    @NotNull
    public final RowCase copy(@Nullable Integer age, @Nullable String allCategory, @Nullable String caseName, @Nullable String degree, @Nullable Integer deptId, @Nullable String departmentName, @Nullable String description, @Nullable String diseaseHistory, @Nullable String diseaseType, @Nullable String educationDegree, @Nullable Integer gender, @Nullable Integer id, @Nullable Integer isExam, @Nullable String job, @Nullable String lastStopTime, @Nullable String mainInfo, @Nullable String marryStatus, @Nullable String modelId, @Nullable String name, @Nullable String nationality, @Nullable String nativePlace, @Nullable Integer patientId, @Nullable String pregnancy, @Nullable Integer score, @Nullable Integer status, @Nullable Integer target, @Nullable Long timeCost, @Nullable Integer userId, @Nullable Integer basisCount, @Nullable Integer diagnoseId, @Nullable Integer isMain, @Nullable Integer trainId, @Nullable String result, @Nullable Integer type, @Nullable Integer operateId, @Nullable String resolution, int kind, @Nullable String remark, @Nullable String picUrl, long time, @Nullable String fileName, @Nullable String videoName, @Nullable String videoDescription, @Nullable String videoUrl, @Nullable String iconPicUrl, @Nullable String peoplePicUrl, @Nullable Integer isIdentity, boolean itemSelect) {
        return new RowCase(age, allCategory, caseName, degree, deptId, departmentName, description, diseaseHistory, diseaseType, educationDegree, gender, id, isExam, job, lastStopTime, mainInfo, marryStatus, modelId, name, nationality, nativePlace, patientId, pregnancy, score, status, target, timeCost, userId, basisCount, diagnoseId, isMain, trainId, result, type, operateId, resolution, kind, remark, picUrl, time, fileName, videoName, videoDescription, videoUrl, iconPicUrl, peoplePicUrl, isIdentity, itemSelect);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RowCase)) {
            return false;
        }
        RowCase rowCase = (RowCase) other;
        return Intrinsics.areEqual(this.age, rowCase.age) && Intrinsics.areEqual(this.allCategory, rowCase.allCategory) && Intrinsics.areEqual(this.caseName, rowCase.caseName) && Intrinsics.areEqual(this.degree, rowCase.degree) && Intrinsics.areEqual(this.deptId, rowCase.deptId) && Intrinsics.areEqual(this.departmentName, rowCase.departmentName) && Intrinsics.areEqual(this.description, rowCase.description) && Intrinsics.areEqual(this.diseaseHistory, rowCase.diseaseHistory) && Intrinsics.areEqual(this.diseaseType, rowCase.diseaseType) && Intrinsics.areEqual(this.educationDegree, rowCase.educationDegree) && Intrinsics.areEqual(this.gender, rowCase.gender) && Intrinsics.areEqual(this.id, rowCase.id) && Intrinsics.areEqual(this.isExam, rowCase.isExam) && Intrinsics.areEqual(this.job, rowCase.job) && Intrinsics.areEqual(this.lastStopTime, rowCase.lastStopTime) && Intrinsics.areEqual(this.mainInfo, rowCase.mainInfo) && Intrinsics.areEqual(this.marryStatus, rowCase.marryStatus) && Intrinsics.areEqual(this.modelId, rowCase.modelId) && Intrinsics.areEqual(this.name, rowCase.name) && Intrinsics.areEqual(this.nationality, rowCase.nationality) && Intrinsics.areEqual(this.nativePlace, rowCase.nativePlace) && Intrinsics.areEqual(this.patientId, rowCase.patientId) && Intrinsics.areEqual(this.pregnancy, rowCase.pregnancy) && Intrinsics.areEqual(this.score, rowCase.score) && Intrinsics.areEqual(this.status, rowCase.status) && Intrinsics.areEqual(this.target, rowCase.target) && Intrinsics.areEqual(this.timeCost, rowCase.timeCost) && Intrinsics.areEqual(this.userId, rowCase.userId) && Intrinsics.areEqual(this.basisCount, rowCase.basisCount) && Intrinsics.areEqual(this.diagnoseId, rowCase.diagnoseId) && Intrinsics.areEqual(this.isMain, rowCase.isMain) && Intrinsics.areEqual(this.trainId, rowCase.trainId) && Intrinsics.areEqual(this.result, rowCase.result) && Intrinsics.areEqual(this.type, rowCase.type) && Intrinsics.areEqual(this.operateId, rowCase.operateId) && Intrinsics.areEqual(this.resolution, rowCase.resolution) && this.kind == rowCase.kind && Intrinsics.areEqual(this.remark, rowCase.remark) && Intrinsics.areEqual(this.picUrl, rowCase.picUrl) && this.time == rowCase.time && Intrinsics.areEqual(this.fileName, rowCase.fileName) && Intrinsics.areEqual(this.videoName, rowCase.videoName) && Intrinsics.areEqual(this.videoDescription, rowCase.videoDescription) && Intrinsics.areEqual(this.videoUrl, rowCase.videoUrl) && Intrinsics.areEqual(this.iconPicUrl, rowCase.iconPicUrl) && Intrinsics.areEqual(this.peoplePicUrl, rowCase.peoplePicUrl) && Intrinsics.areEqual(this.isIdentity, rowCase.isIdentity) && this.itemSelect == rowCase.itemSelect;
    }

    @Nullable
    public final Integer getAge() {
        return this.age;
    }

    @Nullable
    public final String getAllCategory() {
        return this.allCategory;
    }

    @Nullable
    public final Integer getBasisCount() {
        return this.basisCount;
    }

    @Nullable
    public final String getCaseName() {
        return this.caseName;
    }

    @Nullable
    public final String getDegree() {
        return this.degree;
    }

    @Nullable
    public final String getDepartmentName() {
        return this.departmentName;
    }

    @Nullable
    public final Integer getDeptId() {
        return this.deptId;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final Integer getDiagnoseId() {
        return this.diagnoseId;
    }

    @Nullable
    public final String getDiseaseHistory() {
        return this.diseaseHistory;
    }

    @Nullable
    public final String getDiseaseType() {
        return this.diseaseType;
    }

    @Nullable
    public final String getEducationDegree() {
        return this.educationDegree;
    }

    @Nullable
    public final String getFileName() {
        return this.fileName;
    }

    @Nullable
    public final Integer getGender() {
        return this.gender;
    }

    @Nullable
    public final String getIconPicUrl() {
        return this.iconPicUrl;
    }

    @Nullable
    public final Integer getId() {
        return this.id;
    }

    public final boolean getItemSelect() {
        return this.itemSelect;
    }

    @Nullable
    public final String getJob() {
        return this.job;
    }

    public final int getKind() {
        return this.kind;
    }

    @Nullable
    public final String getLastStopTime() {
        return this.lastStopTime;
    }

    @Nullable
    public final String getMainInfo() {
        return this.mainInfo;
    }

    @Nullable
    public final String getMarryStatus() {
        return this.marryStatus;
    }

    @Nullable
    public final String getModelId() {
        return this.modelId;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getNationality() {
        return this.nationality;
    }

    @Nullable
    public final String getNativePlace() {
        return this.nativePlace;
    }

    @Nullable
    public final Integer getOperateId() {
        return this.operateId;
    }

    @Nullable
    public final Integer getPatientId() {
        return this.patientId;
    }

    @Nullable
    public final String getPeoplePicUrl() {
        return this.peoplePicUrl;
    }

    @Nullable
    public final String getPicUrl() {
        return this.picUrl;
    }

    @Nullable
    public final String getPregnancy() {
        return this.pregnancy;
    }

    @Nullable
    public final String getRemark() {
        return this.remark;
    }

    @Nullable
    public final String getResolution() {
        return this.resolution;
    }

    @Nullable
    public final String getResult() {
        return this.result;
    }

    @Nullable
    public final Integer getScore() {
        return this.score;
    }

    @Nullable
    public final Integer getStatus() {
        return this.status;
    }

    @Nullable
    public final Integer getTarget() {
        return this.target;
    }

    public final long getTime() {
        return this.time;
    }

    @Nullable
    public final Long getTimeCost() {
        return this.timeCost;
    }

    @Nullable
    public final Integer getTrainId() {
        return this.trainId;
    }

    @Nullable
    public final Integer getType() {
        return this.type;
    }

    @Nullable
    public final Integer getUserId() {
        return this.userId;
    }

    @Nullable
    public final String getVideoDescription() {
        return this.videoDescription;
    }

    @Nullable
    public final String getVideoName() {
        return this.videoName;
    }

    @Nullable
    public final String getVideoUrl() {
        return this.videoUrl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        Integer num = this.age;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.allCategory;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.caseName;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.degree;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Integer num2 = this.deptId;
        int iHashCode5 = (iHashCode4 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str4 = this.departmentName;
        int iHashCode6 = (iHashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.description;
        int iHashCode7 = (iHashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.diseaseHistory;
        int iHashCode8 = (iHashCode7 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.diseaseType;
        int iHashCode9 = (iHashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.educationDegree;
        int iHashCode10 = (iHashCode9 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Integer num3 = this.gender;
        int iHashCode11 = (iHashCode10 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.id;
        int iHashCode12 = (iHashCode11 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.isExam;
        int iHashCode13 = (iHashCode12 + (num5 == null ? 0 : num5.hashCode())) * 31;
        String str9 = this.job;
        int iHashCode14 = (iHashCode13 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.lastStopTime;
        int iHashCode15 = (iHashCode14 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.mainInfo;
        int iHashCode16 = (iHashCode15 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.marryStatus;
        int iHashCode17 = (iHashCode16 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.modelId;
        int iHashCode18 = (iHashCode17 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.name;
        int iHashCode19 = (iHashCode18 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.nationality;
        int iHashCode20 = (iHashCode19 + (str15 == null ? 0 : str15.hashCode())) * 31;
        String str16 = this.nativePlace;
        int iHashCode21 = (iHashCode20 + (str16 == null ? 0 : str16.hashCode())) * 31;
        Integer num6 = this.patientId;
        int iHashCode22 = (iHashCode21 + (num6 == null ? 0 : num6.hashCode())) * 31;
        String str17 = this.pregnancy;
        int iHashCode23 = (iHashCode22 + (str17 == null ? 0 : str17.hashCode())) * 31;
        Integer num7 = this.score;
        int iHashCode24 = (iHashCode23 + (num7 == null ? 0 : num7.hashCode())) * 31;
        Integer num8 = this.status;
        int iHashCode25 = (iHashCode24 + (num8 == null ? 0 : num8.hashCode())) * 31;
        Integer num9 = this.target;
        int iHashCode26 = (iHashCode25 + (num9 == null ? 0 : num9.hashCode())) * 31;
        Long l2 = this.timeCost;
        int iHashCode27 = (iHashCode26 + (l2 == null ? 0 : l2.hashCode())) * 31;
        Integer num10 = this.userId;
        int iHashCode28 = (iHashCode27 + (num10 == null ? 0 : num10.hashCode())) * 31;
        Integer num11 = this.basisCount;
        int iHashCode29 = (iHashCode28 + (num11 == null ? 0 : num11.hashCode())) * 31;
        Integer num12 = this.diagnoseId;
        int iHashCode30 = (iHashCode29 + (num12 == null ? 0 : num12.hashCode())) * 31;
        Integer num13 = this.isMain;
        int iHashCode31 = (iHashCode30 + (num13 == null ? 0 : num13.hashCode())) * 31;
        Integer num14 = this.trainId;
        int iHashCode32 = (iHashCode31 + (num14 == null ? 0 : num14.hashCode())) * 31;
        String str18 = this.result;
        int iHashCode33 = (iHashCode32 + (str18 == null ? 0 : str18.hashCode())) * 31;
        Integer num15 = this.type;
        int iHashCode34 = (iHashCode33 + (num15 == null ? 0 : num15.hashCode())) * 31;
        Integer num16 = this.operateId;
        int iHashCode35 = (iHashCode34 + (num16 == null ? 0 : num16.hashCode())) * 31;
        String str19 = this.resolution;
        int iHashCode36 = (((iHashCode35 + (str19 == null ? 0 : str19.hashCode())) * 31) + this.kind) * 31;
        String str20 = this.remark;
        int iHashCode37 = (iHashCode36 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.picUrl;
        int iHashCode38 = (((iHashCode37 + (str21 == null ? 0 : str21.hashCode())) * 31) + a.a(this.time)) * 31;
        String str22 = this.fileName;
        int iHashCode39 = (iHashCode38 + (str22 == null ? 0 : str22.hashCode())) * 31;
        String str23 = this.videoName;
        int iHashCode40 = (iHashCode39 + (str23 == null ? 0 : str23.hashCode())) * 31;
        String str24 = this.videoDescription;
        int iHashCode41 = (iHashCode40 + (str24 == null ? 0 : str24.hashCode())) * 31;
        String str25 = this.videoUrl;
        int iHashCode42 = (iHashCode41 + (str25 == null ? 0 : str25.hashCode())) * 31;
        String str26 = this.iconPicUrl;
        int iHashCode43 = (iHashCode42 + (str26 == null ? 0 : str26.hashCode())) * 31;
        String str27 = this.peoplePicUrl;
        int iHashCode44 = (iHashCode43 + (str27 == null ? 0 : str27.hashCode())) * 31;
        Integer num17 = this.isIdentity;
        int iHashCode45 = (iHashCode44 + (num17 != null ? num17.hashCode() : 0)) * 31;
        boolean z2 = this.itemSelect;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode45 + i2;
    }

    @Nullable
    public final Integer isExam() {
        return this.isExam;
    }

    @Nullable
    public final Integer isIdentity() {
        return this.isIdentity;
    }

    @Nullable
    public final Integer isMain() {
        return this.isMain;
    }

    public final void setIdentity(@Nullable Integer num) {
        this.isIdentity = num;
    }

    public final void setItemSelect(boolean z2) {
        this.itemSelect = z2;
    }

    @NotNull
    public String toString() {
        return "RowCase(age=" + this.age + ", allCategory=" + this.allCategory + ", caseName=" + this.caseName + ", degree=" + this.degree + ", deptId=" + this.deptId + ", departmentName=" + this.departmentName + ", description=" + this.description + ", diseaseHistory=" + this.diseaseHistory + ", diseaseType=" + this.diseaseType + ", educationDegree=" + this.educationDegree + ", gender=" + this.gender + ", id=" + this.id + ", isExam=" + this.isExam + ", job=" + this.job + ", lastStopTime=" + this.lastStopTime + ", mainInfo=" + this.mainInfo + ", marryStatus=" + this.marryStatus + ", modelId=" + this.modelId + ", name=" + this.name + ", nationality=" + this.nationality + ", nativePlace=" + this.nativePlace + ", patientId=" + this.patientId + ", pregnancy=" + this.pregnancy + ", score=" + this.score + ", status=" + this.status + ", target=" + this.target + ", timeCost=" + this.timeCost + ", userId=" + this.userId + ", basisCount=" + this.basisCount + ", diagnoseId=" + this.diagnoseId + ", isMain=" + this.isMain + ", trainId=" + this.trainId + ", result=" + this.result + ", type=" + this.type + ", operateId=" + this.operateId + ", resolution=" + this.resolution + ", kind=" + this.kind + ", remark=" + this.remark + ", picUrl=" + this.picUrl + ", time=" + this.time + ", fileName=" + this.fileName + ", videoName=" + this.videoName + ", videoDescription=" + this.videoDescription + ", videoUrl=" + this.videoUrl + ", iconPicUrl=" + this.iconPicUrl + ", peoplePicUrl=" + this.peoplePicUrl + ", isIdentity=" + this.isIdentity + ", itemSelect=" + this.itemSelect + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ RowCase(int i2, int i3, Integer num, String str, String str2, String str3, Integer num2, String str4, String str5, String str6, String str7, String str8, Integer num3, Integer num4, Integer num5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, Integer num6, String str17, Integer num7, Integer num8, Integer num9, Long l2, Integer num10, Integer num11, Integer num12, Integer num13, Integer num14, String str18, Integer num15, Integer num16, String str19, int i4, String str20, String str21, long j2, String str22, String str23, String str24, String str25, String str26, String str27, Integer num17, boolean z2, SerializationConstructorMarker serializationConstructorMarker) {
        if (((i2 & 0) != 0) | ((i3 & 0) != 0)) {
            PluginExceptionsKt.throwArrayMissingFieldException(new int[]{i2, i3}, new int[]{0, 0}, RowCase$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.age = 0;
        } else {
            this.age = num;
        }
        if ((i2 & 2) == 0) {
            this.allCategory = "";
        } else {
            this.allCategory = str;
        }
        if ((i2 & 4) == 0) {
            this.caseName = "";
        } else {
            this.caseName = str2;
        }
        if ((i2 & 8) == 0) {
            this.degree = "";
        } else {
            this.degree = str3;
        }
        if ((i2 & 16) == 0) {
            this.deptId = 0;
        } else {
            this.deptId = num2;
        }
        if ((i2 & 32) == 0) {
            this.departmentName = "";
        } else {
            this.departmentName = str4;
        }
        if ((i2 & 64) == 0) {
            this.description = "";
        } else {
            this.description = str5;
        }
        if ((i2 & 128) == 0) {
            this.diseaseHistory = "";
        } else {
            this.diseaseHistory = str6;
        }
        if ((i2 & 256) == 0) {
            this.diseaseType = "";
        } else {
            this.diseaseType = str7;
        }
        if ((i2 & 512) == 0) {
            this.educationDegree = "";
        } else {
            this.educationDegree = str8;
        }
        if ((i2 & 1024) == 0) {
            this.gender = 0;
        } else {
            this.gender = num3;
        }
        this.id = (i2 & 2048) == 0 ? -1 : num4;
        if ((i2 & 4096) == 0) {
            this.isExam = 0;
        } else {
            this.isExam = num5;
        }
        if ((i2 & 8192) == 0) {
            this.job = "";
        } else {
            this.job = str9;
        }
        if ((i2 & 16384) == 0) {
            this.lastStopTime = "";
        } else {
            this.lastStopTime = str10;
        }
        if ((i2 & 32768) == 0) {
            this.mainInfo = "";
        } else {
            this.mainInfo = str11;
        }
        if ((65536 & i2) == 0) {
            this.marryStatus = "";
        } else {
            this.marryStatus = str12;
        }
        if ((131072 & i2) == 0) {
            this.modelId = "";
        } else {
            this.modelId = str13;
        }
        if ((262144 & i2) == 0) {
            this.name = "";
        } else {
            this.name = str14;
        }
        if ((524288 & i2) == 0) {
            this.nationality = "";
        } else {
            this.nationality = str15;
        }
        if ((1048576 & i2) == 0) {
            this.nativePlace = "";
        } else {
            this.nativePlace = str16;
        }
        if ((2097152 & i2) == 0) {
            this.patientId = 0;
        } else {
            this.patientId = num6;
        }
        if ((4194304 & i2) == 0) {
            this.pregnancy = "";
        } else {
            this.pregnancy = str17;
        }
        if ((8388608 & i2) == 0) {
            this.score = 0;
        } else {
            this.score = num7;
        }
        if ((16777216 & i2) == 0) {
            this.status = 0;
        } else {
            this.status = num8;
        }
        if ((33554432 & i2) == 0) {
            this.target = 0;
        } else {
            this.target = num9;
        }
        this.timeCost = (67108864 & i2) == 0 ? 0L : l2;
        if ((134217728 & i2) == 0) {
            this.userId = 0;
        } else {
            this.userId = num10;
        }
        if ((268435456 & i2) == 0) {
            this.basisCount = 0;
        } else {
            this.basisCount = num11;
        }
        if ((536870912 & i2) == 0) {
            this.diagnoseId = 0;
        } else {
            this.diagnoseId = num12;
        }
        if ((1073741824 & i2) == 0) {
            this.isMain = 0;
        } else {
            this.isMain = num13;
        }
        if ((i2 & Integer.MIN_VALUE) == 0) {
            this.trainId = 0;
        } else {
            this.trainId = num14;
        }
        if ((i3 & 1) == 0) {
            this.result = "";
        } else {
            this.result = str18;
        }
        if ((i3 & 2) == 0) {
            this.type = 0;
        } else {
            this.type = num15;
        }
        if ((i3 & 4) == 0) {
            this.operateId = 0;
        } else {
            this.operateId = num16;
        }
        if ((i3 & 8) == 0) {
            this.resolution = "";
        } else {
            this.resolution = str19;
        }
        if ((i3 & 16) == 0) {
            this.kind = -1;
        } else {
            this.kind = i4;
        }
        if ((i3 & 32) == 0) {
            this.remark = "";
        } else {
            this.remark = str20;
        }
        if ((i3 & 64) == 0) {
            this.picUrl = "";
        } else {
            this.picUrl = str21;
        }
        if ((i3 & 128) == 0) {
            this.time = 0L;
        } else {
            this.time = j2;
        }
        if ((i3 & 256) == 0) {
            this.fileName = "";
        } else {
            this.fileName = str22;
        }
        if ((i3 & 512) == 0) {
            this.videoName = "";
        } else {
            this.videoName = str23;
        }
        if ((i3 & 1024) == 0) {
            this.videoDescription = "";
        } else {
            this.videoDescription = str24;
        }
        if ((i3 & 2048) == 0) {
            this.videoUrl = "";
        } else {
            this.videoUrl = str25;
        }
        if ((i3 & 4096) == 0) {
            this.iconPicUrl = "";
        } else {
            this.iconPicUrl = str26;
        }
        if ((i3 & 8192) == 0) {
            this.peoplePicUrl = "";
        } else {
            this.peoplePicUrl = str27;
        }
        if ((i3 & 16384) == 0) {
            this.isIdentity = 0;
        } else {
            this.isIdentity = num17;
        }
        if ((i3 & 32768) == 0) {
            this.itemSelect = false;
        } else {
            this.itemSelect = z2;
        }
    }

    public /* synthetic */ RowCase(Integer num, String str, String str2, String str3, Integer num2, String str4, String str5, String str6, String str7, String str8, Integer num3, Integer num4, Integer num5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, Integer num6, String str17, Integer num7, Integer num8, Integer num9, Long l2, Integer num10, Integer num11, Integer num12, Integer num13, Integer num14, String str18, Integer num15, Integer num16, String str19, int i2, String str20, String str21, long j2, String str22, String str23, String str24, String str25, String str26, String str27, Integer num17, boolean z2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : num, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? "" : str2, (i3 & 8) != 0 ? "" : str3, (i3 & 16) != 0 ? 0 : num2, (i3 & 32) != 0 ? "" : str4, (i3 & 64) != 0 ? "" : str5, (i3 & 128) != 0 ? "" : str6, (i3 & 256) != 0 ? "" : str7, (i3 & 512) != 0 ? "" : str8, (i3 & 1024) != 0 ? 0 : num3, (i3 & 2048) != 0 ? -1 : num4, (i3 & 4096) != 0 ? null : num5, (i3 & 8192) != 0 ? "" : str9, (i3 & 16384) != 0 ? "" : str10, (i3 & 32768) != 0 ? "" : str11, (i3 & 65536) != 0 ? "" : str12, (i3 & 131072) != 0 ? "" : str13, (i3 & 262144) != 0 ? "" : str14, (i3 & 524288) != 0 ? "" : str15, (i3 & 1048576) != 0 ? "" : str16, (i3 & 2097152) != 0 ? null : num6, (i3 & 4194304) != 0 ? "" : str17, (i3 & 8388608) != 0 ? null : num7, (i3 & 16777216) != 0 ? null : num8, (i3 & 33554432) != 0 ? null : num9, (i3 & 67108864) != 0 ? 0L : l2, (i3 & 134217728) != 0 ? null : num10, (i3 & 268435456) != 0 ? null : num11, (i3 & 536870912) != 0 ? null : num12, (i3 & 1073741824) != 0 ? null : num13, (i3 & Integer.MIN_VALUE) != 0 ? null : num14, (i4 & 1) != 0 ? "" : str18, (i4 & 2) != 0 ? null : num15, (i4 & 4) != 0 ? null : num16, (i4 & 8) != 0 ? "" : str19, (i4 & 16) == 0 ? i2 : -1, (i4 & 32) != 0 ? "" : str20, (i4 & 64) != 0 ? "" : str21, (i4 & 128) == 0 ? j2 : 0L, (i4 & 256) != 0 ? "" : str22, (i4 & 512) != 0 ? "" : str23, (i4 & 1024) != 0 ? "" : str24, (i4 & 2048) != 0 ? "" : str25, (i4 & 4096) != 0 ? "" : str26, (i4 & 8192) != 0 ? "" : str27, (i4 & 16384) == 0 ? num17 : 0, (i4 & 32768) != 0 ? false : z2);
    }
}
