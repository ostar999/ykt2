package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a_\u0010\u0006\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\b\"\b\b\u0001\u0010\t*\u0002H\b2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\n2'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001aU\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0086@ø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\u0013\u001a]\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0086@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0004\b\u0016\u0010\u0013\u001aJ\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aR\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0086@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0013\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0019"}, d2 = {"TimeoutCancellationException", "Lkotlinx/coroutines/TimeoutCancellationException;", CrashHianalyticsData.TIME, "", "coroutine", "Lkotlinx/coroutines/Job;", "setupTimeout", "", "U", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/TimeoutCoroutine;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "withTimeout", "timeMillis", "(JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "timeout", "Lkotlin/time/Duration;", "withTimeout-KLykuaI", "withTimeoutOrNull", "withTimeoutOrNull-KLykuaI", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class TimeoutKt {

    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.TimeoutKt", f = "Timeout.kt", i = {0, 0, 0}, l = {100}, m = "withTimeoutOrNull", n = {"block", "coroutine", "timeMillis"}, s = {"L$0", "L$1", "J$0"})
    /* renamed from: kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1, reason: invalid class name */
    public static final class AnonymousClass1<T> extends ContinuationImpl {
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TimeoutKt.withTimeoutOrNull(0L, null, this);
        }
    }

    @NotNull
    public static final TimeoutCancellationException TimeoutCancellationException(long j2, @NotNull Job job) {
        return new TimeoutCancellationException("Timed out waiting for " + j2 + " ms", job);
    }

    private static final <U, T extends U> Object setupTimeout(TimeoutCoroutine<U, ? super T> timeoutCoroutine, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        JobKt.disposeOnCompletion(timeoutCoroutine, DelayKt.getDelay(timeoutCoroutine.uCont.getContext()).invokeOnTimeout(timeoutCoroutine.time, timeoutCoroutine, timeoutCoroutine.getContext()));
        return UndispatchedKt.startUndispatchedOrReturnIgnoreTimeout(timeoutCoroutine, timeoutCoroutine, function2);
    }

    @Nullable
    public static final <T> Object withTimeout(long j2, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        if (j2 <= 0) {
            throw new TimeoutCancellationException("Timed out immediately");
        }
        Object obj = setupTimeout(new TimeoutCoroutine(j2, continuation), function2);
        if (obj == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return obj;
    }

    @Nullable
    /* renamed from: withTimeout-KLykuaI, reason: not valid java name */
    public static final <T> Object m2308withTimeoutKLykuaI(long j2, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        return withTimeout(DelayKt.m2300toDelayMillisLRDsOJo(j2), function2, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.TimeoutCoroutine] */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.lang.Object withTimeoutOrNull(long r7, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super kotlinx.coroutines.CoroutineScope, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super T> r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.TimeoutKt.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r10
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = (kotlinx.coroutines.TimeoutKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = new kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3c
            if (r2 != r4) goto L34
            java.lang.Object r7 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r7 = (kotlin.jvm.internal.Ref.ObjectRef) r7
            java.lang.Object r8 = r0.L$0
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L32
            goto L6b
        L32:
            r8 = move-exception
            goto L6e
        L34:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3c:
            kotlin.ResultKt.throwOnFailure(r10)
            r5 = 0
            int r10 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r10 > 0) goto L46
            return r3
        L46:
            kotlin.jvm.internal.Ref$ObjectRef r10 = new kotlin.jvm.internal.Ref$ObjectRef
            r10.<init>()
            r0.L$0 = r9     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            r0.L$1 = r10     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            r0.J$0 = r7     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            r0.label = r4     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            kotlinx.coroutines.TimeoutCoroutine r2 = new kotlinx.coroutines.TimeoutCoroutine     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            r2.<init>(r7, r0)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            r10.element = r2     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            java.lang.Object r7 = setupTimeout(r2, r9)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            java.lang.Object r8 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
            if (r7 != r8) goto L67
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r0)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L6c
        L67:
            if (r7 != r1) goto L6a
            return r1
        L6a:
            r10 = r7
        L6b:
            return r10
        L6c:
            r8 = move-exception
            r7 = r10
        L6e:
            kotlinx.coroutines.Job r9 = r8.coroutine
            T r7 = r7.element
            if (r9 != r7) goto L75
            return r3
        L75:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(long, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Nullable
    /* renamed from: withTimeoutOrNull-KLykuaI, reason: not valid java name */
    public static final <T> Object m2309withTimeoutOrNullKLykuaI(long j2, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2, @NotNull Continuation<? super T> continuation) {
        return withTimeoutOrNull(DelayKt.m2300toDelayMillisLRDsOJo(j2), function2, continuation);
    }
}
