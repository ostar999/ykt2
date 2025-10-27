package com.ykb.ebook.util;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R2;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.TimeoutKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 K*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0004JKLMBW\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012'\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020)2\b\b\u0002\u0010*\u001a\u00020+J?\u0010,\u001a\u00020)\"\u0004\b\u0001\u0010-2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010.\u001a\u0002H-2\u0016\u0010/\u001a\u0012\u0012\u0004\u0012\u0002H-0\u0012R\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0082Hø\u0001\u0000¢\u0006\u0002\u00100J+\u00101\u001a\u00020)2\u0006\u0010\u0003\u001a\u00020\u00042\u0010\u0010/\u001a\f0\u0010R\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0082Hø\u0001\u0000¢\u0006\u0002\u00102JT\u00103\u001a\u00028\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010&\u001a\u00020'2)\b\b\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b¢\u0006\u0002\b\rH\u0082Hø\u0001\u0000¢\u0006\u0002\u00104JA\u00105\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u00062'\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b¢\u0006\u0002\b\rH\u0002ø\u0001\u0000¢\u0006\u0002\u00106J/\u00107\u001a\u0002082'\u00109\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0013¢\u0006\f\b;\u0012\b\b<\u0012\u0004\b\b(*\u0012\u0004\u0012\u00020)0:j\u0002`=JI\u0010>\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010?JO\u0010@\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062-\u0010\n\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020A¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010BJ\u001c\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\u0010.\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000DJ\u001b\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010.\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010EJI\u0010F\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010?JI\u0010G\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062'\u0010\n\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000b¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010?JO\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062-\u0010\n\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\f\u0012\u0006\u0012\u0004\u0018\u00010\u00020A¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010BJ\u0006\u0010\"\u001a\u00020)J\u001a\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010&\u001a\b\u0012\u0004\u0012\u00020'0DJ\u0014\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010&\u001a\u00020'R\u001a\u0010\u000f\u001a\u000e\u0018\u00010\u0010R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u000e\u0018\u00010\u0010R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0019\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u001d\u001a\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u000e\u0018\u00010\u0010R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R \u0010%\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0012R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0004\n\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006N"}, d2 = {"Lcom/ykb/ebook/util/Coroutine;", ExifInterface.GPS_DIRECTION_TRUE, "", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "startOption", "Lkotlinx/coroutines/CoroutineStart;", "executeContext", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "cancel", "Lcom/ykb/ebook/util/Coroutine$VoidCallback;", "error", "Lcom/ykb/ebook/util/Coroutine$Callback;", "", "errorReturn", "Lcom/ykb/ebook/util/Coroutine$Result;", "getExecuteContext", "()Lkotlin/coroutines/CoroutineContext;", "finally", "isActive", "", "()Z", "isCancelled", "isCompleted", "job", "Lkotlinx/coroutines/Job;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "start", "getStartOption", "()Lkotlinx/coroutines/CoroutineStart;", "success", "timeMillis", "", "Ljava/lang/Long;", "", "cause", "Lcom/ykb/ebook/util/ActivelyCancelException;", "dispatchCallback", "R", "value", com.alipay.sdk.authjs.a.f3170c, "(Lkotlinx/coroutines/CoroutineScope;Ljava/lang/Object;Lcom/ykb/ebook/util/Coroutine$Callback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatchVoidCallback", "(Lkotlinx/coroutines/CoroutineScope;Lcom/ykb/ebook/util/Coroutine$VoidCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeBlock", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "executeInternal", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/Job;", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "onCancel", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lcom/ykb/ebook/util/Coroutine;", "onError", "Lkotlin/Function3;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lcom/ykb/ebook/util/Coroutine;", "onErrorReturn", "Lkotlin/Function0;", "(Ljava/lang/Object;)Lcom/ykb/ebook/util/Coroutine;", "onFinally", "onStart", "onSuccess", "timeout", "Callback", "Companion", "Result", "VoidCallback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class Coroutine<T> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final CoroutineScope DEFAULT = CoroutineScopeKt.MainScope();

    @Nullable
    private Coroutine<T>.VoidCallback cancel;

    @Nullable
    private Coroutine<T>.Callback<Throwable> error;

    @Nullable
    private Result<? extends T> errorReturn;

    @NotNull
    private final CoroutineContext executeContext;

    @Nullable
    private Coroutine<T>.VoidCallback finally;

    @NotNull
    private final Job job;

    @NotNull
    private final CoroutineScope scope;

    @Nullable
    private Coroutine<T>.VoidCallback start;

    @NotNull
    private final CoroutineStart startOption;

    @Nullable
    private Coroutine<T>.Callback<T> success;

    @Nullable
    private Long timeMillis;

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0082\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002BA\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012-\u0010\u0005\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006¢\u0006\u0002\b\nø\u0001\u0000¢\u0006\u0002\u0010\u000bR=\u0010\u0005\u001a)\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006¢\u0006\u0002\b\nø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"Lcom/ykb/ebook/util/Coroutine$Callback;", "VALUE", "", com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function3;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lcom/ykb/ebook/util/Coroutine;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)V", "getBlock", "()Lkotlin/jvm/functions/Function3;", "Lkotlin/jvm/functions/Function3;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class Callback<VALUE> {

        @NotNull
        private final Function3<CoroutineScope, VALUE, Continuation<? super Unit>, Object> block;

        @Nullable
        private final CoroutineContext context;
        final /* synthetic */ Coroutine<T> this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public Callback(@Nullable Coroutine coroutine, @NotNull CoroutineContext coroutineContext, Function3<? super CoroutineScope, ? super VALUE, ? super Continuation<? super Unit>, ? extends Object> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            this.this$0 = coroutine;
            this.context = coroutineContext;
            this.block = block;
        }

        @NotNull
        public final Function3<CoroutineScope, VALUE, Continuation<? super Unit>, Object> getBlock() {
            return this.block;
        }

        @Nullable
        public final CoroutineContext getContext() {
            return this.context;
        }
    }

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jk\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\n2'\u0010\u000e\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f¢\u0006\u0002\b\u0011ø\u0001\u0000¢\u0006\u0002\u0010\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/util/Coroutine$Companion;", "", "()V", "DEFAULT", "Lkotlinx/coroutines/CoroutineScope;", "async", "Lcom/ykb/ebook/util/Coroutine;", ExifInterface.GPS_DIRECTION_TRUE, Constants.PARAM_SCOPE, com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "start", "Lkotlinx/coroutines/CoroutineStart;", "executeContext", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lcom/ykb/ebook/util/Coroutine;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ Coroutine async$default(Companion companion, CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, CoroutineContext coroutineContext2, Function2 function2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                coroutineScope = Coroutine.DEFAULT;
            }
            CoroutineScope coroutineScope2 = coroutineScope;
            if ((i2 & 2) != 0) {
                coroutineContext = Dispatchers.getIO();
            }
            CoroutineContext coroutineContext3 = coroutineContext;
            if ((i2 & 4) != 0) {
                coroutineStart = CoroutineStart.DEFAULT;
            }
            CoroutineStart coroutineStart2 = coroutineStart;
            if ((i2 & 8) != 0) {
                coroutineContext2 = Dispatchers.getMain();
            }
            return companion.async(coroutineScope2, coroutineContext3, coroutineStart2, coroutineContext2, function2);
        }

        @NotNull
        public final <T> Coroutine<T> async(@NotNull CoroutineScope scope, @NotNull CoroutineContext context, @NotNull CoroutineStart start, @NotNull CoroutineContext executeContext, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(start, "start");
            Intrinsics.checkNotNullParameter(executeContext, "executeContext");
            Intrinsics.checkNotNullParameter(block, "block");
            return new Coroutine<>(scope, context, start, executeContext, block);
        }
    }

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00018\u0001¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u0004\u0018\u00018\u0001HÆ\u0003¢\u0006\u0002\u0010\u0006J \u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00018\u0001HÆ\u0001¢\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0015\u0010\u0003\u001a\u0004\u0018\u00018\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/util/Coroutine$Result;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/ykb/ebook/util/Coroutine$Result;", "equals", "", "other", "hashCode", "", "toString", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Result<T> {

        @Nullable
        private final T value;

        public Result(@Nullable T t2) {
            this.value = t2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Result copy$default(Result result, Object obj, int i2, Object obj2) {
            if ((i2 & 1) != 0) {
                obj = result.value;
            }
            return result.copy(obj);
        }

        @Nullable
        public final T component1() {
            return this.value;
        }

        @NotNull
        public final Result<T> copy(@Nullable T value) {
            return new Result<>(value);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Result) && Intrinsics.areEqual(this.value, ((Result) other).value);
        }

        @Nullable
        public final T getValue() {
            return this.value;
        }

        public int hashCode() {
            T t2 = this.value;
            if (t2 == null) {
                return 0;
            }
            return t2.hashCode();
        }

        @NotNull
        public String toString() {
            return "Result(value=" + this.value + ')';
        }
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0082\u0004\u0018\u00002\u00020\u0001B;\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\nR7\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/util/Coroutine$VoidCallback;", "", com.umeng.analytics.pro.d.R, "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lcom/ykb/ebook/util/Coroutine;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "getBlock", "()Lkotlin/jvm/functions/Function2;", "Lkotlin/jvm/functions/Function2;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class VoidCallback {

        @NotNull
        private final Function2<CoroutineScope, Continuation<? super Unit>, Object> block;

        @Nullable
        private final CoroutineContext context;
        final /* synthetic */ Coroutine<T> this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public VoidCallback(@Nullable Coroutine coroutine, @NotNull CoroutineContext coroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            this.this$0 = coroutine;
            this.context = coroutineContext;
            this.block = block;
        }

        @NotNull
        public final Function2<CoroutineScope, Continuation<? super Unit>, Object> getBlock() {
            return this.block;
        }

        @Nullable
        public final CoroutineContext getContext() {
            return this.context;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@"}, d2 = {"R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.util.Coroutine$dispatchCallback$2", f = "Coroutine.kt", i = {}, l = {212}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nCoroutine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$dispatchCallback$2\n*L\n1#1,244:1\n*E\n"})
    /* renamed from: com.ykb.ebook.util.Coroutine$dispatchCallback$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Coroutine<T>.Callback<R> $callback;
        final /* synthetic */ R $value;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Coroutine<T>.Callback<R> callback, R r2, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$callback = callback;
            this.$value = r2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$callback, this.$value, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Function3 block = this.$callback.getBlock();
                R r2 = this.$value;
                this.label = 1;
                if (block.invoke(coroutineScope, r2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.util.Coroutine$dispatchVoidCallback$2", f = "Coroutine.kt", i = {}, l = {193}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nCoroutine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$dispatchVoidCallback$2\n*L\n1#1,244:1\n*E\n"})
    /* renamed from: com.ykb.ebook.util.Coroutine$dispatchVoidCallback$2, reason: invalid class name and case insensitive filesystem */
    public static final class C10552 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Coroutine<T>.VoidCallback $callback;
        final /* synthetic */ CoroutineScope $scope;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10552(Coroutine<T>.VoidCallback voidCallback, CoroutineScope coroutineScope, Continuation<? super C10552> continuation) {
            super(2, continuation);
            this.$callback = voidCallback;
            this.$scope = coroutineScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C10552(this.$callback, this.$scope, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C10552) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Function2<CoroutineScope, Continuation<? super Unit>, Object> block = this.$callback.getBlock();
                CoroutineScope coroutineScope = this.$scope;
                this.label = 1;
                if (block.invoke(coroutineScope, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.util.Coroutine$dispatchVoidCallback$3", f = "Coroutine.kt", i = {}, l = {R2.array.ease_pdf_file_suffix}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nCoroutine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$dispatchVoidCallback$3\n*L\n1#1,244:1\n*E\n"})
    /* renamed from: com.ykb.ebook.util.Coroutine$dispatchVoidCallback$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Coroutine<T>.VoidCallback $callback;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Coroutine<T>.VoidCallback voidCallback, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$callback = voidCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$callback, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Function2<CoroutineScope, Continuation<? super Unit>, Object> block = this.$callback.getBlock();
                this.label = 1;
                if (block.invoke(coroutineScope, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.util.Coroutine$executeBlock$2", f = "Coroutine.kt", i = {}, l = {224, 227}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nCoroutine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$executeBlock$2\n*L\n1#1,244:1\n*E\n"})
    /* renamed from: com.ykb.ebook.util.Coroutine$executeBlock$2, reason: invalid class name and case insensitive filesystem */
    public static final class C10562 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
        final /* synthetic */ Function2<CoroutineScope, Continuation<? super T>, Object> $block;
        final /* synthetic */ long $timeMillis;
        private /* synthetic */ Object L$0;
        int label;

        @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.ykb.ebook.util.Coroutine$executeBlock$2$1", f = "Coroutine.kt", i = {}, l = {225}, m = "invokeSuspend", n = {}, s = {})
        @SourceDebugExtension({"SMAP\nCoroutine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$executeBlock$2$1\n*L\n1#1,244:1\n*E\n"})
        /* renamed from: com.ykb.ebook.util.Coroutine$executeBlock$2$1, reason: invalid class name */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
            final /* synthetic */ Function2<CoroutineScope, Continuation<? super T>, Object> $block;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass1(Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$block = function2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super T> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = this.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Function2<CoroutineScope, Continuation<? super T>, Object> function2 = this.$block;
                    this.label = 1;
                    obj = function2.invoke(coroutineScope, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return obj;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C10562(long j2, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, Continuation<? super C10562> continuation) {
            super(2, continuation);
            this.$timeMillis = j2;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10562 c10562 = new C10562(this.$timeMillis, this.$block, continuation);
            c10562.L$0 = obj;
            return c10562;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super T> continuation) {
            return ((C10562) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                long j2 = this.$timeMillis;
                if (j2 > 0) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, null);
                    this.label = 1;
                    obj = TimeoutKt.withTimeout(j2, anonymousClass1, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    Function2<CoroutineScope, Continuation<? super T>, Object> function2 = this.$block;
                    this.label = 2;
                    obj = function2.invoke(coroutineScope, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i2 != 1 && i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\u008a@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.util.Coroutine$executeInternal$1", f = "Coroutine.kt", i = {0, 1, 2, 3, 4, 7, 7, 8, 8, 9, 10}, l = {R2.attr.actionModeCutDrawable, R2.attr.actionModeSelectAllDrawable, 256, R2.attr.actionProviderClass, R2.attr.actionSheetPadding, R2.attr.adScopeCircleColor, R2.attr.adScopeStrokeWidth, R2.attr.ad_marker_width, R2.attr.add_address_bg_color, R2.attr.align, R2.attr.alignItems, R2.attr.all_course_live_calendar, R2.attr.all_course_subject_bg2_end_color, R2.attr.all_course_live_calendar, R2.attr.all_course_subject_bg2_end_color}, m = "invokeSuspend", n = {"$this$launch", "$this$launch", "$this$launch", "$this$launch", "$this$launch", "$this$launch", AliyunLogKey.KEY_EVENT, "$this$launch", AliyunLogKey.KEY_EVENT, "$this$launch", "$this$launch"}, s = {"L$0", "L$0", "L$0", "L$0", "L$0", "L$0", "L$1", "L$0", "L$1", "L$0", "L$0"})
    @SourceDebugExtension({"SMAP\nCoroutine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$executeInternal$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Coroutine.kt\ncom/ykb/ebook/util/Coroutine\n*L\n1#1,244:1\n1#2:245\n191#3,10:246\n223#3:256\n207#3,9:257\n191#3,10:266\n207#3,9:276\n207#3,9:285\n191#3,10:294\n*S KotlinDebug\n*F\n+ 1 Coroutine.kt\ncom/ykb/ebook/util/Coroutine$executeInternal$1\n*L\n167#1:246,10\n169#1:256\n171#1:257,9\n185#1:266,10\n178#1:276,9\n182#1:285,9\n185#1:294,10\n*E\n"})
    /* renamed from: com.ykb.ebook.util.Coroutine$executeInternal$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function2<CoroutineScope, Continuation<? super T>, Object> $block;
        final /* synthetic */ CoroutineContext $context;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ Coroutine<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(Coroutine<T> coroutine, CoroutineContext coroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = coroutine;
            this.$context = coroutineContext;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$context, this.$block, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:104:0x01f6 A[Catch: all -> 0x002a, TRY_ENTER, TryCatch #2 {all -> 0x002a, blocks: (B:9:0x0025, B:104:0x01f6, B:106:0x01fe, B:108:0x0204, B:110:0x020a, B:113:0x021d), top: B:146:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0245  */
        /* JADX WARN: Removed duplicated region for block: B:131:0x028f  */
        /* JADX WARN: Removed duplicated region for block: B:141:0x02d0 A[ORIG_RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00c8 A[Catch: all -> 0x016e, TryCatch #3 {all -> 0x016e, blocks: (B:21:0x0054, B:25:0x0062, B:49:0x00ea, B:51:0x00f5, B:53:0x00fb, B:55:0x0101, B:58:0x0111, B:28:0x006b, B:42:0x00bb, B:44:0x00c8, B:46:0x00cf, B:32:0x007c, B:34:0x0084, B:36:0x008a, B:39:0x009e), top: B:146:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00cd  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x00e9 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00f5 A[Catch: all -> 0x016e, TryCatch #3 {all -> 0x016e, blocks: (B:21:0x0054, B:25:0x0062, B:49:0x00ea, B:51:0x00f5, B:53:0x00fb, B:55:0x0101, B:58:0x0111, B:28:0x006b, B:42:0x00bb, B:44:0x00c8, B:46:0x00cf, B:32:0x007c, B:34:0x0084, B:36:0x008a, B:39:0x009e), top: B:146:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0101 A[Catch: all -> 0x016e, TryCatch #3 {all -> 0x016e, blocks: (B:21:0x0054, B:25:0x0062, B:49:0x00ea, B:51:0x00f5, B:53:0x00fb, B:55:0x0101, B:58:0x0111, B:28:0x006b, B:42:0x00bb, B:44:0x00c8, B:46:0x00cf, B:32:0x007c, B:34:0x0084, B:36:0x008a, B:39:0x009e), top: B:146:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0111 A[Catch: all -> 0x016e, TRY_LEAVE, TryCatch #3 {all -> 0x016e, blocks: (B:21:0x0054, B:25:0x0062, B:49:0x00ea, B:51:0x00f5, B:53:0x00fb, B:55:0x0101, B:58:0x0111, B:28:0x006b, B:42:0x00bb, B:44:0x00c8, B:46:0x00cf, B:32:0x007c, B:34:0x0084, B:36:0x008a, B:39:0x009e), top: B:146:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x0136  */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v12 */
        /* JADX WARN: Type inference failed for: r1v3 */
        /* JADX WARN: Type inference failed for: r1v46 */
        /* JADX WARN: Type inference failed for: r1v47 */
        /* JADX WARN: Type inference failed for: r1v48 */
        /* JADX WARN: Type inference failed for: r1v49 */
        /* JADX WARN: Type inference failed for: r1v50 */
        /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.Object, kotlinx.coroutines.CoroutineScope] */
        /* JADX WARN: Type inference failed for: r1v9, types: [kotlinx.coroutines.CoroutineScope] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 758
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.util.Coroutine.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public Coroutine(@NotNull CoroutineScope scope, @NotNull CoroutineContext context, @NotNull CoroutineStart startOption, @NotNull CoroutineContext executeContext, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(startOption, "startOption");
        Intrinsics.checkNotNullParameter(executeContext, "executeContext");
        Intrinsics.checkNotNullParameter(block, "block");
        this.scope = scope;
        this.startOption = startOption;
        this.executeContext = executeContext;
        this.job = executeInternal(context, block);
    }

    public static /* synthetic */ void cancel$default(Coroutine coroutine, ActivelyCancelException activelyCancelException, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            activelyCancelException = new ActivelyCancelException();
        }
        coroutine.cancel(activelyCancelException);
    }

    private final <R> Object dispatchCallback(CoroutineScope coroutineScope, R r2, Coroutine<T>.Callback<R> callback, Continuation<? super Unit> continuation) throws Throwable {
        if (!CoroutineScopeKt.isActive(coroutineScope)) {
            return Unit.INSTANCE;
        }
        if (callback.getContext() == null) {
            Function3<CoroutineScope, R, Continuation<? super Unit>, Object> block = callback.getBlock();
            InlineMarker.mark(0);
            block.invoke(coroutineScope, r2, continuation);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }
        CoroutineContext coroutineContextPlus = coroutineScope.getCoroutineContext().plus(callback.getContext());
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(callback, r2, null);
        InlineMarker.mark(0);
        BuildersKt.withContext(coroutineContextPlus, anonymousClass2, continuation);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    private final Object dispatchVoidCallback(CoroutineScope coroutineScope, Coroutine<T>.VoidCallback voidCallback, Continuation<? super Unit> continuation) throws Throwable {
        if (voidCallback.getContext() == null) {
            CoroutineContext coroutineContext = coroutineScope.getCoroutineContext();
            C10552 c10552 = new C10552(voidCallback, coroutineScope, null);
            InlineMarker.mark(0);
            BuildersKt.withContext(coroutineContext, c10552, continuation);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }
        CoroutineContext coroutineContextPlus = coroutineScope.getCoroutineContext().plus(voidCallback.getContext());
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(voidCallback, null);
        InlineMarker.mark(0);
        BuildersKt.withContext(coroutineContextPlus, anonymousClass3, continuation);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    private final Object executeBlock(CoroutineScope coroutineScope, CoroutineContext coroutineContext, long j2, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, Continuation<? super T> continuation) throws Throwable {
        CoroutineContext coroutineContextPlus = coroutineScope.getCoroutineContext().plus(coroutineContext);
        C10562 c10562 = new C10562(j2, function2, null);
        InlineMarker.mark(0);
        Object objWithContext = BuildersKt.withContext(coroutineContextPlus, c10562, continuation);
        InlineMarker.mark(1);
        return objWithContext;
    }

    private final Job executeInternal(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        return BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.plus(this.scope, this.executeContext), null, this.startOption, new AnonymousClass1(this, context, block, null), 1, null);
    }

    public static /* synthetic */ Coroutine onCancel$default(Coroutine coroutine, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onCancel(coroutineContext, function2);
    }

    public static /* synthetic */ Coroutine onError$default(Coroutine coroutine, CoroutineContext coroutineContext, Function3 function3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onError(coroutineContext, function3);
    }

    public static /* synthetic */ Coroutine onFinally$default(Coroutine coroutine, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onFinally(coroutineContext, function2);
    }

    public static /* synthetic */ Coroutine onStart$default(Coroutine coroutine, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onStart(coroutineContext, function2);
    }

    public static /* synthetic */ Coroutine onSuccess$default(Coroutine coroutine, CoroutineContext coroutineContext, Function3 function3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = null;
        }
        return coroutine.onSuccess(coroutineContext, function3);
    }

    public final void cancel(@NotNull ActivelyCancelException cause) {
        Intrinsics.checkNotNullParameter(cause, "cause");
        if (!this.job.isCancelled()) {
            this.job.cancel((CancellationException) cause);
        }
        Coroutine<T>.VoidCallback voidCallback = this.cancel;
        if (voidCallback != null) {
            BuildersKt__Builders_commonKt.launch$default(DEFAULT, this.executeContext, null, new Coroutine$cancel$1$1(voidCallback, this, null), 2, null);
        }
    }

    @NotNull
    public final CoroutineContext getExecuteContext() {
        return this.executeContext;
    }

    @NotNull
    public final CoroutineScope getScope() {
        return this.scope;
    }

    @NotNull
    public final CoroutineStart getStartOption() {
        return this.startOption;
    }

    @NotNull
    public final DisposableHandle invokeOnCompletion(@NotNull Function1<? super Throwable, Unit> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        return this.job.invokeOnCompletion(handler);
    }

    public final boolean isActive() {
        return this.job.isActive();
    }

    public final boolean isCancelled() {
        return this.job.isCancelled();
    }

    public final boolean isCompleted() {
        return this.job.isCompleted();
    }

    @NotNull
    public final Coroutine<T> onCancel(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.cancel = new VoidCallback(this, context, block);
        return this;
    }

    @NotNull
    public final Coroutine<T> onError(@Nullable CoroutineContext context, @NotNull Function3<? super CoroutineScope, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.error = new Callback<>(this, context, block);
        return this;
    }

    @NotNull
    public final Coroutine<T> onErrorReturn(@NotNull Function0<? extends T> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.errorReturn = new Result<>(value.invoke());
        return this;
    }

    @NotNull
    public final Coroutine<T> onFinally(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.finally = new VoidCallback(this, context, block);
        return this;
    }

    @NotNull
    public final Coroutine<T> onStart(@Nullable CoroutineContext context, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.start = new VoidCallback(this, context, block);
        return this;
    }

    @NotNull
    public final Coroutine<T> onSuccess(@Nullable CoroutineContext context, @NotNull Function3<? super CoroutineScope, ? super T, ? super Continuation<? super Unit>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.success = new Callback<>(this, context, block);
        return this;
    }

    public final void start() {
        this.job.start();
    }

    @NotNull
    public final Coroutine<T> timeout(@NotNull Function0<Long> timeMillis) {
        Intrinsics.checkNotNullParameter(timeMillis, "timeMillis");
        this.timeMillis = timeMillis.invoke();
        return this;
    }

    @NotNull
    public final Coroutine<T> onErrorReturn(@Nullable T value) {
        this.errorReturn = new Result<>(value);
        return this;
    }

    @NotNull
    public final Coroutine<T> timeout(long timeMillis) {
        this.timeMillis = Long.valueOf(timeMillis);
        return this;
    }

    public /* synthetic */ Coroutine(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, CoroutineContext coroutineContext2, Function2 function2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, (i2 & 2) != 0 ? Dispatchers.getIO() : coroutineContext, (i2 & 4) != 0 ? CoroutineStart.DEFAULT : coroutineStart, (i2 & 8) != 0 ? Dispatchers.getMain() : coroutineContext2, function2);
    }
}
