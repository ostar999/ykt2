package kotlinx.coroutines;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.umeng.analytics.pro.d;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@InternalCoroutinesApi
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0097@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\n\u0010\n\u001a\u00060\u000bj\u0002`\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001e\u0010\u000f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011H&\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/Delay;", "", "delay", "", CrashHianalyticsData.TIME, "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", d.R, "Lkotlin/coroutines/CoroutineContext;", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public interface Delay {

    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
        @Nullable
        public static Object delay(@NotNull Delay delay, long j2, @NotNull Continuation<? super Unit> continuation) {
            if (j2 <= 0) {
                return Unit.INSTANCE;
            }
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            delay.mo2363scheduleResumeAfterDelay(j2, cancellableContinuationImpl);
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        }

        @NotNull
        public static DisposableHandle invokeOnTimeout(@NotNull Delay delay, long j2, @NotNull Runnable runnable, @NotNull CoroutineContext coroutineContext) {
            return DefaultExecutorKt.getDefaultDelay().invokeOnTimeout(j2, runnable, coroutineContext);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    @Nullable
    Object delay(long j2, @NotNull Continuation<? super Unit> continuation);

    @NotNull
    DisposableHandle invokeOnTimeout(long timeMillis, @NotNull Runnable block, @NotNull CoroutineContext context);

    /* renamed from: scheduleResumeAfterDelay */
    void mo2363scheduleResumeAfterDelay(long timeMillis, @NotNull CancellableContinuation<? super Unit> continuation);
}
