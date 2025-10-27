package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.psychiatrygarden.utils.Constants;
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
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.FloatSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z.a;

@Keep
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\bn\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 «\u00012\u00020\u0001:\u0004ª\u0001«\u0001B¯\u0003\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u0012\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001b\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001f\u001a\u0004\u0018\u00010 \u0012\u0006\u0010!\u001a\u00020\u0003\u0012\b\u0010\"\u001a\u0004\u0018\u00010\u0006\u0012\u000e\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001b\u0012\u000e\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b\u0012\u0006\u0010&\u001a\u00020\u0017\u0012\u000e\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b\u0012\b\u0010(\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010)\u001a\u0004\u0018\u00010*\u0012\b\u0010+\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010,\u001a\u00020\u0003\u0012\u0006\u0010-\u001a\u00020\u0003\u0012\u000e\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b\u0012\u0006\u0010/\u001a\u000200\u0012\u0006\u00101\u001a\u00020\u0003\u0012\u0006\u00102\u001a\u00020\u0003\u0012\u0006\u00103\u001a\u00020\u0003\u0012\b\u00104\u001a\u0004\u0018\u000105¢\u0006\u0002\u00106BÕ\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001b\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 \u0012\b\b\u0002\u0010!\u001a\u00020\u0003\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001b\u0012\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b\u0012\b\b\u0002\u0010&\u001a\u00020\u0017\u0012\u0010\b\u0002\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b\u0012\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*\u0012\b\b\u0002\u0010+\u001a\u00020\u0006\u0012\b\b\u0002\u0010,\u001a\u00020\u0003\u0012\b\b\u0002\u0010-\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b\u0012\b\b\u0002\u0010/\u001a\u000200\u0012\b\b\u0002\u00101\u001a\u00020\u0003\u0012\b\b\u0002\u00102\u001a\u00020\u0003\u0012\b\b\u0002\u00103\u001a\u00020\u0003¢\u0006\u0002\u00107J\u000b\u0010v\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010w\u001a\u00020\u0010HÆ\u0003J\u000b\u0010x\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010y\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010z\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010{\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010|\u001a\u00020\u0006HÆ\u0003J\u0010\u0010}\u001a\u0004\u0018\u00010\u0017HÆ\u0003¢\u0006\u0002\u0010lJ\u000b\u0010~\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u007f\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0012\u0010\u0080\u0001\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001bHÆ\u0003J\f\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0006HÆ\u0003J\n\u0010\u0083\u0001\u001a\u00020\u0006HÆ\u0003J\n\u0010\u0084\u0001\u001a\u00020\u0006HÆ\u0003J\f\u0010\u0085\u0001\u001a\u0004\u0018\u00010 HÆ\u0003J\n\u0010\u0086\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010\u0087\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u0012\u0010\u0088\u0001\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001bHÆ\u0003J\u0012\u0010\u0089\u0001\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001bHÆ\u0003J\n\u0010\u008a\u0001\u001a\u00020\u0017HÆ\u0003J\u0012\u0010\u008b\u0001\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001bHÆ\u0003J\f\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\f\u0010\u008d\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\f\u0010\u008e\u0001\u001a\u0004\u0018\u00010*HÆ\u0003J\n\u0010\u008f\u0001\u001a\u00020\u0006HÆ\u0003J\n\u0010\u0090\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0003HÆ\u0003J\u0012\u0010\u0092\u0001\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001bHÆ\u0003J\n\u0010\u0093\u0001\u001a\u000200HÆ\u0003J\n\u0010\u0094\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0095\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0096\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0098\u0001\u001a\u00020\u0003HÆ\u0003J\n\u0010\u0099\u0001\u001a\u00020\u0006HÆ\u0003J\f\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\f\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\f\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0006HÆ\u0003Jà\u0003\u0010\u009d\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00062\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\u001d\u001a\u00020\u00062\b\b\u0002\u0010\u001e\u001a\u00020\u00062\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\b\b\u0002\u0010!\u001a\u00020\u00032\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001b2\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b2\b\b\u0002\u0010&\u001a\u00020\u00172\u0010\b\u0002\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*2\b\b\u0002\u0010+\u001a\u00020\u00062\b\b\u0002\u0010,\u001a\u00020\u00032\b\b\u0002\u0010-\u001a\u00020\u00032\u0010\b\u0002\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b2\b\b\u0002\u0010/\u001a\u0002002\b\b\u0002\u00101\u001a\u00020\u00032\b\b\u0002\u00102\u001a\u00020\u00032\b\b\u0002\u00103\u001a\u00020\u0003HÆ\u0001¢\u0006\u0003\u0010\u009e\u0001J\u0015\u0010\u009f\u0001\u001a\u0002002\t\u0010 \u0001\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\n\u0010¡\u0001\u001a\u00020\u0003HÖ\u0001J\n\u0010¢\u0001\u001a\u00020\u0006HÖ\u0001J(\u0010£\u0001\u001a\u00030¤\u00012\u0007\u0010¥\u0001\u001a\u00020\u00002\b\u0010¦\u0001\u001a\u00030§\u00012\b\u0010¨\u0001\u001a\u00030©\u0001HÇ\u0001R\u0019\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0013\u0010\u001f\u001a\u0004\u0018\u00010 ¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0019\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b¢\u0006\b\n\u0000\u001a\u0004\b<\u00109R\u001c\u0010\"\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u0011\u0010\u001c\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\bA\u0010>R\u0013\u0010(\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bB\u0010>R\u0013\u0010)\u001a\u0004\u0018\u00010*¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bE\u0010>R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bF\u0010>R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bG\u0010>R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bH\u0010>R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bI\u0010>R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010>R\"\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u00109\"\u0004\bL\u0010MR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010NR\u001a\u0010+\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010>\"\u0004\bP\u0010@R\u0011\u0010,\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010NR\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010N\"\u0004\bS\u0010TR\"\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u00109\"\u0004\bV\u0010MR\u001a\u00102\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010N\"\u0004\bX\u0010TR\u001a\u00101\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010N\"\u0004\bZ\u0010TR\u001a\u00103\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010N\"\u0004\b\\\u0010TR\u001a\u0010/\u001a\u000200X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R\u0011\u0010\u001d\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\ba\u0010>R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bb\u0010>R\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010>\"\u0004\bd\u0010@R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\be\u0010>R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bf\u0010>R\u0011\u0010\u001e\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\bg\u0010>R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\bh\u0010iR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bj\u0010>R\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u0017¢\u0006\n\n\u0002\u0010m\u001a\u0004\bk\u0010lR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bn\u0010>R\u0011\u0010!\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bo\u0010NR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\bp\u0010>R\u0019\u0010.\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u001b¢\u0006\b\n\u0000\u001a\u0004\bq\u00109R\u0011\u0010&\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\br\u0010sR\u0011\u0010-\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bt\u0010NR\u0011\u0010\u0015\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\bu\u0010>¨\u0006¬\u0001"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillHome;", "", "seen1", "", "seen2", RemoteMessageConst.Notification.ICON, "", "id", "introduce", "isPlay", "lockStatus", "name", "pid", "skillId", "skillCategoryId", "productId", "", "createTime", "remainTime", "maturityTime", "practiceTime", "url", "score", "", "iconUrl", "introduceUrls", "introduceUrlsList", "", "costPrice", "marketPrice", "preferentialPrice", "batchInfo", "Lcom/yddmi/doctor/entity/result/BatchInfo;", "skillExchangeIntegral", "content", "mContentList", "Lcom/yddmi/doctor/entity/result/SkillHistory;", "children", PLVLiveClassDetailVO.LiveStatus.LIVE_START, "allItemList", "coupon", "couponRecordDTO", "Lcom/yddmi/doctor/entity/result/SkillTicket;", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "type", "skills", "mSelected", "", "mItemState", "mFolder", "mLevel", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/yddmi/doctor/entity/result/BatchInfo;ILjava/lang/String;Ljava/util/List;Ljava/util/List;FLjava/util/List;Ljava/lang/String;Lcom/yddmi/doctor/entity/result/SkillTicket;Ljava/lang/String;IILjava/util/List;ZIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/yddmi/doctor/entity/result/BatchInfo;ILjava/lang/String;Ljava/util/List;Ljava/util/List;FLjava/util/List;Ljava/lang/String;Lcom/yddmi/doctor/entity/result/SkillTicket;Ljava/lang/String;IILjava/util/List;ZIII)V", "getAllItemList", "()Ljava/util/List;", "getBatchInfo", "()Lcom/yddmi/doctor/entity/result/BatchInfo;", "getChildren", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "getCostPrice", "getCoupon", "getCouponRecordDTO", "()Lcom/yddmi/doctor/entity/result/SkillTicket;", "getCreateTime", "getIcon", "getIconUrl", "getId", "getIntroduce", "getIntroduceUrls", "getIntroduceUrlsList", "setIntroduceUrlsList", "(Ljava/util/List;)V", "()I", "getLabel", "setLabel", "getLevel", "getLockStatus", "setLockStatus", "(I)V", "getMContentList", "setMContentList", "getMFolder", "setMFolder", "getMItemState", "setMItemState", "getMLevel", "setMLevel", "getMSelected", "()Z", "setMSelected", "(Z)V", "getMarketPrice", "getMaturityTime", "getName", "setName", "getPid", "getPracticeTime", "getPreferentialPrice", "getProductId", "()J", "getRemainTime", "getScore", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getSkillCategoryId", "getSkillExchangeIntegral", "getSkillId", "getSkills", "getStar", "()F", "getType", "getUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/yddmi/doctor/entity/result/BatchInfo;ILjava/lang/String;Ljava/util/List;Ljava/util/List;FLjava/util/List;Ljava/lang/String;Lcom/yddmi/doctor/entity/result/SkillTicket;Ljava/lang/String;IILjava/util/List;ZIII)Lcom/yddmi/doctor/entity/result/SkillHome;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class SkillHome {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<SkillHome> allItemList;

    @Nullable
    private final BatchInfo batchInfo;

    @Nullable
    private final List<SkillHome> children;

    @Nullable
    private String content;

    @NotNull
    private final String costPrice;

    @Nullable
    private final String coupon;

    @Nullable
    private final SkillTicket couponRecordDTO;

    @Nullable
    private final String createTime;

    @Nullable
    private final String icon;

    @Nullable
    private final String iconUrl;

    @Nullable
    private final String id;

    @Nullable
    private final String introduce;

    @Nullable
    private final String introduceUrls;

    @Nullable
    private List<String> introduceUrlsList;
    private final int isPlay;

    @NotNull
    private String label;
    private final int level;
    private int lockStatus;

    @Nullable
    private List<SkillHistory> mContentList;
    private int mFolder;
    private int mItemState;
    private int mLevel;
    private boolean mSelected;

    @NotNull
    private final String marketPrice;

    @Nullable
    private final String maturityTime;

    @NotNull
    private String name;

    @Nullable
    private final String pid;

    @Nullable
    private final String practiceTime;

    @NotNull
    private final String preferentialPrice;
    private final long productId;

    @Nullable
    private final String remainTime;

    @Nullable
    private final Float score;

    @Nullable
    private final String skillCategoryId;
    private final int skillExchangeIntegral;

    @Nullable
    private final String skillId;

    @Nullable
    private final List<SkillHome> skills;
    private final float star;
    private final int type;

    @NotNull
    private final String url;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/SkillHome$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/SkillHome;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<SkillHome> serializer() {
            return SkillHome$$serializer.INSTANCE;
        }
    }

    public SkillHome() {
        this((String) null, (String) null, (String) null, 0, 0, (String) null, (String) null, (String) null, (String) null, 0L, (String) null, (String) null, (String) null, (String) null, (String) null, (Float) null, (String) null, (String) null, (List) null, (String) null, (String) null, (String) null, (BatchInfo) null, 0, (String) null, (List) null, (List) null, 0.0f, (List) null, (String) null, (SkillTicket) null, (String) null, 0, 0, (List) null, false, 0, 0, 0, -1, 127, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ SkillHome(int i2, int i3, String str, String str2, String str3, int i4, int i5, String str4, String str5, String str6, String str7, long j2, String str8, String str9, String str10, String str11, String str12, Float f2, String str13, String str14, List list, String str15, String str16, String str17, BatchInfo batchInfo, int i6, String str18, List list2, List list3, float f3, List list4, String str19, SkillTicket skillTicket, String str20, int i7, int i8, List list5, boolean z2, int i9, int i10, int i11, SerializationConstructorMarker serializationConstructorMarker) {
        if (((i2 & 0) != 0) | ((i3 & 0) != 0)) {
            PluginExceptionsKt.throwArrayMissingFieldException(new int[]{i2, i3}, new int[]{0, 0}, SkillHome$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.icon = "";
        } else {
            this.icon = str;
        }
        if ((i2 & 2) == 0) {
            this.id = "";
        } else {
            this.id = str2;
        }
        if ((i2 & 4) == 0) {
            this.introduce = "";
        } else {
            this.introduce = str3;
        }
        if ((i2 & 8) == 0) {
            this.isPlay = -1;
        } else {
            this.isPlay = i4;
        }
        if ((i2 & 16) == 0) {
            this.lockStatus = -1;
        } else {
            this.lockStatus = i5;
        }
        if ((i2 & 32) == 0) {
            this.name = "";
        } else {
            this.name = str4;
        }
        if ((i2 & 64) == 0) {
            this.pid = "";
        } else {
            this.pid = str5;
        }
        if ((i2 & 128) == 0) {
            this.skillId = "";
        } else {
            this.skillId = str6;
        }
        if ((i2 & 256) == 0) {
            this.skillCategoryId = "";
        } else {
            this.skillCategoryId = str7;
        }
        this.productId = (i2 & 512) == 0 ? -1L : j2;
        if ((i2 & 1024) == 0) {
            this.createTime = "";
        } else {
            this.createTime = str8;
        }
        if ((i2 & 2048) == 0) {
            this.remainTime = "";
        } else {
            this.remainTime = str9;
        }
        if ((i2 & 4096) == 0) {
            this.maturityTime = "";
        } else {
            this.maturityTime = str10;
        }
        this.practiceTime = (i2 & 8192) == 0 ? "0" : str11;
        if ((i2 & 16384) == 0) {
            this.url = "";
        } else {
            this.url = str12;
        }
        this.score = (32768 & i2) == 0 ? Float.valueOf(0.0f) : f2;
        if ((65536 & i2) == 0) {
            this.iconUrl = "";
        } else {
            this.iconUrl = str13;
        }
        if ((131072 & i2) == 0) {
            this.introduceUrls = "";
        } else {
            this.introduceUrls = str14;
        }
        if ((262144 & i2) == 0) {
            this.introduceUrlsList = null;
        } else {
            this.introduceUrlsList = list;
        }
        if ((524288 & i2) == 0) {
            this.costPrice = "";
        } else {
            this.costPrice = str15;
        }
        if ((1048576 & i2) == 0) {
            this.marketPrice = "";
        } else {
            this.marketPrice = str16;
        }
        if ((2097152 & i2) == 0) {
            this.preferentialPrice = "";
        } else {
            this.preferentialPrice = str17;
        }
        if ((4194304 & i2) == 0) {
            this.batchInfo = null;
        } else {
            this.batchInfo = batchInfo;
        }
        if ((8388608 & i2) == 0) {
            this.skillExchangeIntegral = 0;
        } else {
            this.skillExchangeIntegral = i6;
        }
        if ((16777216 & i2) == 0) {
            this.content = "";
        } else {
            this.content = str18;
        }
        this.mContentList = (33554432 & i2) == 0 ? new ArrayList() : list2;
        this.children = (67108864 & i2) == 0 ? new ArrayList() : list3;
        if ((134217728 & i2) == 0) {
            this.star = 0.0f;
        } else {
            this.star = f3;
        }
        this.allItemList = (268435456 & i2) == 0 ? new ArrayList() : list4;
        if ((536870912 & i2) == 0) {
            this.coupon = "";
        } else {
            this.coupon = str19;
        }
        if ((1073741824 & i2) == 0) {
            this.couponRecordDTO = null;
        } else {
            this.couponRecordDTO = skillTicket;
        }
        if ((i2 & Integer.MIN_VALUE) == 0) {
            this.label = "";
        } else {
            this.label = str20;
        }
        if ((i3 & 1) == 0) {
            this.level = 1;
        } else {
            this.level = i7;
        }
        if ((i3 & 2) == 0) {
            this.type = 1;
        } else {
            this.type = i8;
        }
        this.skills = (i3 & 4) == 0 ? new ArrayList() : list5;
        if ((i3 & 8) == 0) {
            this.mSelected = false;
        } else {
            this.mSelected = z2;
        }
        if ((i3 & 16) == 0) {
            this.mItemState = 0;
        } else {
            this.mItemState = i9;
        }
        if ((i3 & 32) == 0) {
            this.mFolder = 0;
        } else {
            this.mFolder = i10;
        }
        if ((i3 & 64) == 0) {
            this.mLevel = 0;
        } else {
            this.mLevel = i11;
        }
    }

    @JvmStatic
    public static final void write$Self(@NotNull SkillHome self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || !Intrinsics.areEqual(self.icon, "")) {
            output.encodeNullableSerializableElement(serialDesc, 0, StringSerializer.INSTANCE, self.icon);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || !Intrinsics.areEqual(self.id, "")) {
            output.encodeNullableSerializableElement(serialDesc, 1, StringSerializer.INSTANCE, self.id);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || !Intrinsics.areEqual(self.introduce, "")) {
            output.encodeNullableSerializableElement(serialDesc, 2, StringSerializer.INSTANCE, self.introduce);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.isPlay != -1) {
            output.encodeIntElement(serialDesc, 3, self.isPlay);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 4) || self.lockStatus != -1) {
            output.encodeIntElement(serialDesc, 4, self.lockStatus);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 5) || !Intrinsics.areEqual(self.name, "")) {
            output.encodeStringElement(serialDesc, 5, self.name);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 6) || !Intrinsics.areEqual(self.pid, "")) {
            output.encodeNullableSerializableElement(serialDesc, 6, StringSerializer.INSTANCE, self.pid);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 7) || !Intrinsics.areEqual(self.skillId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 7, StringSerializer.INSTANCE, self.skillId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 8) || !Intrinsics.areEqual(self.skillCategoryId, "")) {
            output.encodeNullableSerializableElement(serialDesc, 8, StringSerializer.INSTANCE, self.skillCategoryId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 9) || self.productId != -1) {
            output.encodeLongElement(serialDesc, 9, self.productId);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 10) || !Intrinsics.areEqual(self.createTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 10, StringSerializer.INSTANCE, self.createTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 11) || !Intrinsics.areEqual(self.remainTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 11, StringSerializer.INSTANCE, self.remainTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 12) || !Intrinsics.areEqual(self.maturityTime, "")) {
            output.encodeNullableSerializableElement(serialDesc, 12, StringSerializer.INSTANCE, self.maturityTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 13) || !Intrinsics.areEqual(self.practiceTime, "0")) {
            output.encodeNullableSerializableElement(serialDesc, 13, StringSerializer.INSTANCE, self.practiceTime);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 14) || !Intrinsics.areEqual(self.url, "")) {
            output.encodeStringElement(serialDesc, 14, self.url);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 15) || !Intrinsics.areEqual((Object) self.score, (Object) Float.valueOf(0.0f))) {
            output.encodeNullableSerializableElement(serialDesc, 15, FloatSerializer.INSTANCE, self.score);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 16) || !Intrinsics.areEqual(self.iconUrl, "")) {
            output.encodeNullableSerializableElement(serialDesc, 16, StringSerializer.INSTANCE, self.iconUrl);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 17) || !Intrinsics.areEqual(self.introduceUrls, "")) {
            output.encodeNullableSerializableElement(serialDesc, 17, StringSerializer.INSTANCE, self.introduceUrls);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 18) || self.introduceUrlsList != null) {
            output.encodeNullableSerializableElement(serialDesc, 18, new ArrayListSerializer(StringSerializer.INSTANCE), self.introduceUrlsList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 19) || !Intrinsics.areEqual(self.costPrice, "")) {
            output.encodeStringElement(serialDesc, 19, self.costPrice);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 20) || !Intrinsics.areEqual(self.marketPrice, "")) {
            output.encodeStringElement(serialDesc, 20, self.marketPrice);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 21) || !Intrinsics.areEqual(self.preferentialPrice, "")) {
            output.encodeStringElement(serialDesc, 21, self.preferentialPrice);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 22) || self.batchInfo != null) {
            output.encodeNullableSerializableElement(serialDesc, 22, BatchInfo$$serializer.INSTANCE, self.batchInfo);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 23) || self.skillExchangeIntegral != 0) {
            output.encodeIntElement(serialDesc, 23, self.skillExchangeIntegral);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 24) || !Intrinsics.areEqual(self.content, "")) {
            output.encodeNullableSerializableElement(serialDesc, 24, StringSerializer.INSTANCE, self.content);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 25) || !Intrinsics.areEqual(self.mContentList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 25, new ArrayListSerializer(SkillHistory$$serializer.INSTANCE), self.mContentList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 26) || !Intrinsics.areEqual(self.children, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 26, new ArrayListSerializer(SkillHome$$serializer.INSTANCE), self.children);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 27) || Float.compare(self.star, 0.0f) != 0) {
            output.encodeFloatElement(serialDesc, 27, self.star);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 28) || !Intrinsics.areEqual(self.allItemList, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 28, new ArrayListSerializer(SkillHome$$serializer.INSTANCE), self.allItemList);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 29) || !Intrinsics.areEqual(self.coupon, "")) {
            output.encodeNullableSerializableElement(serialDesc, 29, StringSerializer.INSTANCE, self.coupon);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 30) || self.couponRecordDTO != null) {
            output.encodeNullableSerializableElement(serialDesc, 30, SkillTicket$$serializer.INSTANCE, self.couponRecordDTO);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 31) || !Intrinsics.areEqual(self.label, "")) {
            output.encodeStringElement(serialDesc, 31, self.label);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 32) || self.level != 1) {
            output.encodeIntElement(serialDesc, 32, self.level);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 33) || self.type != 1) {
            output.encodeIntElement(serialDesc, 33, self.type);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 34) || !Intrinsics.areEqual(self.skills, new ArrayList())) {
            output.encodeNullableSerializableElement(serialDesc, 34, new ArrayListSerializer(SkillHome$$serializer.INSTANCE), self.skills);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 35) || self.mSelected) {
            output.encodeBooleanElement(serialDesc, 35, self.mSelected);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 36) || self.mItemState != 0) {
            output.encodeIntElement(serialDesc, 36, self.mItemState);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 37) || self.mFolder != 0) {
            output.encodeIntElement(serialDesc, 37, self.mFolder);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 38) || self.mLevel != 0) {
            output.encodeIntElement(serialDesc, 38, self.mLevel);
        }
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    /* renamed from: component10, reason: from getter */
    public final long getProductId() {
        return this.productId;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    /* renamed from: component12, reason: from getter */
    public final String getRemainTime() {
        return this.remainTime;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getMaturityTime() {
        return this.maturityTime;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getPracticeTime() {
        return this.practiceTime;
    }

    @NotNull
    /* renamed from: component15, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final Float getScore() {
        return this.score;
    }

    @Nullable
    /* renamed from: component17, reason: from getter */
    public final String getIconUrl() {
        return this.iconUrl;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getIntroduceUrls() {
        return this.introduceUrls;
    }

    @Nullable
    public final List<String> component19() {
        return this.introduceUrlsList;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component20, reason: from getter */
    public final String getCostPrice() {
        return this.costPrice;
    }

    @NotNull
    /* renamed from: component21, reason: from getter */
    public final String getMarketPrice() {
        return this.marketPrice;
    }

    @NotNull
    /* renamed from: component22, reason: from getter */
    public final String getPreferentialPrice() {
        return this.preferentialPrice;
    }

    @Nullable
    /* renamed from: component23, reason: from getter */
    public final BatchInfo getBatchInfo() {
        return this.batchInfo;
    }

    /* renamed from: component24, reason: from getter */
    public final int getSkillExchangeIntegral() {
        return this.skillExchangeIntegral;
    }

    @Nullable
    /* renamed from: component25, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    @Nullable
    public final List<SkillHistory> component26() {
        return this.mContentList;
    }

    @Nullable
    public final List<SkillHome> component27() {
        return this.children;
    }

    /* renamed from: component28, reason: from getter */
    public final float getStar() {
        return this.star;
    }

    @Nullable
    public final List<SkillHome> component29() {
        return this.allItemList;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getIntroduce() {
        return this.introduce;
    }

    @Nullable
    /* renamed from: component30, reason: from getter */
    public final String getCoupon() {
        return this.coupon;
    }

    @Nullable
    /* renamed from: component31, reason: from getter */
    public final SkillTicket getCouponRecordDTO() {
        return this.couponRecordDTO;
    }

    @NotNull
    /* renamed from: component32, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    /* renamed from: component33, reason: from getter */
    public final int getLevel() {
        return this.level;
    }

    /* renamed from: component34, reason: from getter */
    public final int getType() {
        return this.type;
    }

    @Nullable
    public final List<SkillHome> component35() {
        return this.skills;
    }

    /* renamed from: component36, reason: from getter */
    public final boolean getMSelected() {
        return this.mSelected;
    }

    /* renamed from: component37, reason: from getter */
    public final int getMItemState() {
        return this.mItemState;
    }

    /* renamed from: component38, reason: from getter */
    public final int getMFolder() {
        return this.mFolder;
    }

    /* renamed from: component39, reason: from getter */
    public final int getMLevel() {
        return this.mLevel;
    }

    /* renamed from: component4, reason: from getter */
    public final int getIsPlay() {
        return this.isPlay;
    }

    /* renamed from: component5, reason: from getter */
    public final int getLockStatus() {
        return this.lockStatus;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPid() {
        return this.pid;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getSkillId() {
        return this.skillId;
    }

    @Nullable
    /* renamed from: component9, reason: from getter */
    public final String getSkillCategoryId() {
        return this.skillCategoryId;
    }

    @NotNull
    public final SkillHome copy(@Nullable String icon, @Nullable String id, @Nullable String introduce, int isPlay, int lockStatus, @NotNull String name, @Nullable String pid, @Nullable String skillId, @Nullable String skillCategoryId, long productId, @Nullable String createTime, @Nullable String remainTime, @Nullable String maturityTime, @Nullable String practiceTime, @NotNull String url, @Nullable Float score, @Nullable String iconUrl, @Nullable String introduceUrls, @Nullable List<String> introduceUrlsList, @NotNull String costPrice, @NotNull String marketPrice, @NotNull String preferentialPrice, @Nullable BatchInfo batchInfo, int skillExchangeIntegral, @Nullable String content, @Nullable List<SkillHistory> mContentList, @Nullable List<SkillHome> children, float star, @Nullable List<SkillHome> allItemList, @Nullable String coupon, @Nullable SkillTicket couponRecordDTO, @NotNull String label, int level, int type, @Nullable List<SkillHome> skills, boolean mSelected, int mItemState, int mFolder, int mLevel) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(costPrice, "costPrice");
        Intrinsics.checkNotNullParameter(marketPrice, "marketPrice");
        Intrinsics.checkNotNullParameter(preferentialPrice, "preferentialPrice");
        Intrinsics.checkNotNullParameter(label, "label");
        return new SkillHome(icon, id, introduce, isPlay, lockStatus, name, pid, skillId, skillCategoryId, productId, createTime, remainTime, maturityTime, practiceTime, url, score, iconUrl, introduceUrls, introduceUrlsList, costPrice, marketPrice, preferentialPrice, batchInfo, skillExchangeIntegral, content, mContentList, children, star, allItemList, coupon, couponRecordDTO, label, level, type, skills, mSelected, mItemState, mFolder, mLevel);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillHome)) {
            return false;
        }
        SkillHome skillHome = (SkillHome) other;
        return Intrinsics.areEqual(this.icon, skillHome.icon) && Intrinsics.areEqual(this.id, skillHome.id) && Intrinsics.areEqual(this.introduce, skillHome.introduce) && this.isPlay == skillHome.isPlay && this.lockStatus == skillHome.lockStatus && Intrinsics.areEqual(this.name, skillHome.name) && Intrinsics.areEqual(this.pid, skillHome.pid) && Intrinsics.areEqual(this.skillId, skillHome.skillId) && Intrinsics.areEqual(this.skillCategoryId, skillHome.skillCategoryId) && this.productId == skillHome.productId && Intrinsics.areEqual(this.createTime, skillHome.createTime) && Intrinsics.areEqual(this.remainTime, skillHome.remainTime) && Intrinsics.areEqual(this.maturityTime, skillHome.maturityTime) && Intrinsics.areEqual(this.practiceTime, skillHome.practiceTime) && Intrinsics.areEqual(this.url, skillHome.url) && Intrinsics.areEqual((Object) this.score, (Object) skillHome.score) && Intrinsics.areEqual(this.iconUrl, skillHome.iconUrl) && Intrinsics.areEqual(this.introduceUrls, skillHome.introduceUrls) && Intrinsics.areEqual(this.introduceUrlsList, skillHome.introduceUrlsList) && Intrinsics.areEqual(this.costPrice, skillHome.costPrice) && Intrinsics.areEqual(this.marketPrice, skillHome.marketPrice) && Intrinsics.areEqual(this.preferentialPrice, skillHome.preferentialPrice) && Intrinsics.areEqual(this.batchInfo, skillHome.batchInfo) && this.skillExchangeIntegral == skillHome.skillExchangeIntegral && Intrinsics.areEqual(this.content, skillHome.content) && Intrinsics.areEqual(this.mContentList, skillHome.mContentList) && Intrinsics.areEqual(this.children, skillHome.children) && Float.compare(this.star, skillHome.star) == 0 && Intrinsics.areEqual(this.allItemList, skillHome.allItemList) && Intrinsics.areEqual(this.coupon, skillHome.coupon) && Intrinsics.areEqual(this.couponRecordDTO, skillHome.couponRecordDTO) && Intrinsics.areEqual(this.label, skillHome.label) && this.level == skillHome.level && this.type == skillHome.type && Intrinsics.areEqual(this.skills, skillHome.skills) && this.mSelected == skillHome.mSelected && this.mItemState == skillHome.mItemState && this.mFolder == skillHome.mFolder && this.mLevel == skillHome.mLevel;
    }

    @Nullable
    public final List<SkillHome> getAllItemList() {
        return this.allItemList;
    }

    @Nullable
    public final BatchInfo getBatchInfo() {
        return this.batchInfo;
    }

    @Nullable
    public final List<SkillHome> getChildren() {
        return this.children;
    }

    @Nullable
    public final String getContent() {
        return this.content;
    }

    @NotNull
    public final String getCostPrice() {
        return this.costPrice;
    }

    @Nullable
    public final String getCoupon() {
        return this.coupon;
    }

    @Nullable
    public final SkillTicket getCouponRecordDTO() {
        return this.couponRecordDTO;
    }

    @Nullable
    public final String getCreateTime() {
        return this.createTime;
    }

    @Nullable
    public final String getIcon() {
        return this.icon;
    }

    @Nullable
    public final String getIconUrl() {
        return this.iconUrl;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getIntroduce() {
        return this.introduce;
    }

    @Nullable
    public final String getIntroduceUrls() {
        return this.introduceUrls;
    }

    @Nullable
    public final List<String> getIntroduceUrlsList() {
        return this.introduceUrlsList;
    }

    @NotNull
    public final String getLabel() {
        return this.label;
    }

    public final int getLevel() {
        return this.level;
    }

    public final int getLockStatus() {
        return this.lockStatus;
    }

    @Nullable
    public final List<SkillHistory> getMContentList() {
        return this.mContentList;
    }

    public final int getMFolder() {
        return this.mFolder;
    }

    public final int getMItemState() {
        return this.mItemState;
    }

    public final int getMLevel() {
        return this.mLevel;
    }

    public final boolean getMSelected() {
        return this.mSelected;
    }

    @NotNull
    public final String getMarketPrice() {
        return this.marketPrice;
    }

    @Nullable
    public final String getMaturityTime() {
        return this.maturityTime;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getPid() {
        return this.pid;
    }

    @Nullable
    public final String getPracticeTime() {
        return this.practiceTime;
    }

    @NotNull
    public final String getPreferentialPrice() {
        return this.preferentialPrice;
    }

    public final long getProductId() {
        return this.productId;
    }

    @Nullable
    public final String getRemainTime() {
        return this.remainTime;
    }

    @Nullable
    public final Float getScore() {
        return this.score;
    }

    @Nullable
    public final String getSkillCategoryId() {
        return this.skillCategoryId;
    }

    public final int getSkillExchangeIntegral() {
        return this.skillExchangeIntegral;
    }

    @Nullable
    public final String getSkillId() {
        return this.skillId;
    }

    @Nullable
    public final List<SkillHome> getSkills() {
        return this.skills;
    }

    public final float getStar() {
        return this.star;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.icon;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.id;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.introduce;
        int iHashCode3 = (((((((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.isPlay) * 31) + this.lockStatus) * 31) + this.name.hashCode()) * 31;
        String str4 = this.pid;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.skillId;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.skillCategoryId;
        int iHashCode6 = (((iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31) + a.a(this.productId)) * 31;
        String str7 = this.createTime;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.remainTime;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.maturityTime;
        int iHashCode9 = (iHashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.practiceTime;
        int iHashCode10 = (((iHashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31) + this.url.hashCode()) * 31;
        Float f2 = this.score;
        int iHashCode11 = (iHashCode10 + (f2 == null ? 0 : f2.hashCode())) * 31;
        String str11 = this.iconUrl;
        int iHashCode12 = (iHashCode11 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.introduceUrls;
        int iHashCode13 = (iHashCode12 + (str12 == null ? 0 : str12.hashCode())) * 31;
        List<String> list = this.introduceUrlsList;
        int iHashCode14 = (((((((iHashCode13 + (list == null ? 0 : list.hashCode())) * 31) + this.costPrice.hashCode()) * 31) + this.marketPrice.hashCode()) * 31) + this.preferentialPrice.hashCode()) * 31;
        BatchInfo batchInfo = this.batchInfo;
        int iHashCode15 = (((iHashCode14 + (batchInfo == null ? 0 : batchInfo.hashCode())) * 31) + this.skillExchangeIntegral) * 31;
        String str13 = this.content;
        int iHashCode16 = (iHashCode15 + (str13 == null ? 0 : str13.hashCode())) * 31;
        List<SkillHistory> list2 = this.mContentList;
        int iHashCode17 = (iHashCode16 + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<SkillHome> list3 = this.children;
        int iHashCode18 = (((iHashCode17 + (list3 == null ? 0 : list3.hashCode())) * 31) + Float.floatToIntBits(this.star)) * 31;
        List<SkillHome> list4 = this.allItemList;
        int iHashCode19 = (iHashCode18 + (list4 == null ? 0 : list4.hashCode())) * 31;
        String str14 = this.coupon;
        int iHashCode20 = (iHashCode19 + (str14 == null ? 0 : str14.hashCode())) * 31;
        SkillTicket skillTicket = this.couponRecordDTO;
        int iHashCode21 = (((((((iHashCode20 + (skillTicket == null ? 0 : skillTicket.hashCode())) * 31) + this.label.hashCode()) * 31) + this.level) * 31) + this.type) * 31;
        List<SkillHome> list5 = this.skills;
        int iHashCode22 = (iHashCode21 + (list5 != null ? list5.hashCode() : 0)) * 31;
        boolean z2 = this.mSelected;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return ((((((iHashCode22 + i2) * 31) + this.mItemState) * 31) + this.mFolder) * 31) + this.mLevel;
    }

    public final int isPlay() {
        return this.isPlay;
    }

    public final void setContent(@Nullable String str) {
        this.content = str;
    }

    public final void setIntroduceUrlsList(@Nullable List<String> list) {
        this.introduceUrlsList = list;
    }

    public final void setLabel(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.label = str;
    }

    public final void setLockStatus(int i2) {
        this.lockStatus = i2;
    }

    public final void setMContentList(@Nullable List<SkillHistory> list) {
        this.mContentList = list;
    }

    public final void setMFolder(int i2) {
        this.mFolder = i2;
    }

    public final void setMItemState(int i2) {
        this.mItemState = i2;
    }

    public final void setMLevel(int i2) {
        this.mLevel = i2;
    }

    public final void setMSelected(boolean z2) {
        this.mSelected = z2;
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    @NotNull
    public String toString() {
        return "SkillHome(icon=" + this.icon + ", id=" + this.id + ", introduce=" + this.introduce + ", isPlay=" + this.isPlay + ", lockStatus=" + this.lockStatus + ", name=" + this.name + ", pid=" + this.pid + ", skillId=" + this.skillId + ", skillCategoryId=" + this.skillCategoryId + ", productId=" + this.productId + ", createTime=" + this.createTime + ", remainTime=" + this.remainTime + ", maturityTime=" + this.maturityTime + ", practiceTime=" + this.practiceTime + ", url=" + this.url + ", score=" + this.score + ", iconUrl=" + this.iconUrl + ", introduceUrls=" + this.introduceUrls + ", introduceUrlsList=" + this.introduceUrlsList + ", costPrice=" + this.costPrice + ", marketPrice=" + this.marketPrice + ", preferentialPrice=" + this.preferentialPrice + ", batchInfo=" + this.batchInfo + ", skillExchangeIntegral=" + this.skillExchangeIntegral + ", content=" + this.content + ", mContentList=" + this.mContentList + ", children=" + this.children + ", star=" + this.star + ", allItemList=" + this.allItemList + ", coupon=" + this.coupon + ", couponRecordDTO=" + this.couponRecordDTO + ", label=" + this.label + ", level=" + this.level + ", type=" + this.type + ", skills=" + this.skills + ", mSelected=" + this.mSelected + ", mItemState=" + this.mItemState + ", mFolder=" + this.mFolder + ", mLevel=" + this.mLevel + ")";
    }

    public SkillHome(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, @NotNull String name, @Nullable String str4, @Nullable String str5, @Nullable String str6, long j2, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @NotNull String url, @Nullable Float f2, @Nullable String str11, @Nullable String str12, @Nullable List<String> list, @NotNull String costPrice, @NotNull String marketPrice, @NotNull String preferentialPrice, @Nullable BatchInfo batchInfo, int i4, @Nullable String str13, @Nullable List<SkillHistory> list2, @Nullable List<SkillHome> list3, float f3, @Nullable List<SkillHome> list4, @Nullable String str14, @Nullable SkillTicket skillTicket, @NotNull String label, int i5, int i6, @Nullable List<SkillHome> list5, boolean z2, int i7, int i8, int i9) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(costPrice, "costPrice");
        Intrinsics.checkNotNullParameter(marketPrice, "marketPrice");
        Intrinsics.checkNotNullParameter(preferentialPrice, "preferentialPrice");
        Intrinsics.checkNotNullParameter(label, "label");
        this.icon = str;
        this.id = str2;
        this.introduce = str3;
        this.isPlay = i2;
        this.lockStatus = i3;
        this.name = name;
        this.pid = str4;
        this.skillId = str5;
        this.skillCategoryId = str6;
        this.productId = j2;
        this.createTime = str7;
        this.remainTime = str8;
        this.maturityTime = str9;
        this.practiceTime = str10;
        this.url = url;
        this.score = f2;
        this.iconUrl = str11;
        this.introduceUrls = str12;
        this.introduceUrlsList = list;
        this.costPrice = costPrice;
        this.marketPrice = marketPrice;
        this.preferentialPrice = preferentialPrice;
        this.batchInfo = batchInfo;
        this.skillExchangeIntegral = i4;
        this.content = str13;
        this.mContentList = list2;
        this.children = list3;
        this.star = f3;
        this.allItemList = list4;
        this.coupon = str14;
        this.couponRecordDTO = skillTicket;
        this.label = label;
        this.level = i5;
        this.type = i6;
        this.skills = list5;
        this.mSelected = z2;
        this.mItemState = i7;
        this.mFolder = i8;
        this.mLevel = i9;
    }

    public /* synthetic */ SkillHome(String str, String str2, String str3, int i2, int i3, String str4, String str5, String str6, String str7, long j2, String str8, String str9, String str10, String str11, String str12, Float f2, String str13, String str14, List list, String str15, String str16, String str17, BatchInfo batchInfo, int i4, String str18, List list2, List list3, float f3, List list4, String str19, SkillTicket skillTicket, String str20, int i5, int i6, List list5, boolean z2, int i7, int i8, int i9, int i10, int i11, DefaultConstructorMarker defaultConstructorMarker) {
        this((i10 & 1) != 0 ? "" : str, (i10 & 2) != 0 ? "" : str2, (i10 & 4) != 0 ? "" : str3, (i10 & 8) != 0 ? -1 : i2, (i10 & 16) == 0 ? i3 : -1, (i10 & 32) != 0 ? "" : str4, (i10 & 64) != 0 ? "" : str5, (i10 & 128) != 0 ? "" : str6, (i10 & 256) != 0 ? "" : str7, (i10 & 512) != 0 ? -1L : j2, (i10 & 1024) != 0 ? "" : str8, (i10 & 2048) != 0 ? "" : str9, (i10 & 4096) != 0 ? "" : str10, (i10 & 8192) != 0 ? "0" : str11, (i10 & 16384) != 0 ? "" : str12, (i10 & 32768) != 0 ? Float.valueOf(0.0f) : f2, (i10 & 65536) != 0 ? "" : str13, (i10 & 131072) != 0 ? "" : str14, (i10 & 262144) != 0 ? null : list, (i10 & 524288) != 0 ? "" : str15, (i10 & 1048576) != 0 ? "" : str16, (i10 & 2097152) != 0 ? "" : str17, (i10 & 4194304) != 0 ? null : batchInfo, (i10 & 8388608) != 0 ? 0 : i4, (i10 & 16777216) != 0 ? "" : str18, (i10 & 33554432) != 0 ? new ArrayList() : list2, (i10 & 67108864) != 0 ? new ArrayList() : list3, (i10 & 134217728) == 0 ? f3 : 0.0f, (i10 & 268435456) != 0 ? new ArrayList() : list4, (i10 & 536870912) != 0 ? "" : str19, (i10 & 1073741824) == 0 ? skillTicket : null, (i10 & Integer.MIN_VALUE) != 0 ? "" : str20, (i11 & 1) != 0 ? 1 : i5, (i11 & 2) == 0 ? i6 : 1, (i11 & 4) != 0 ? new ArrayList() : list5, (i11 & 8) != 0 ? false : z2, (i11 & 16) != 0 ? 0 : i7, (i11 & 32) != 0 ? 0 : i8, (i11 & 64) == 0 ? i9 : 0);
    }
}
