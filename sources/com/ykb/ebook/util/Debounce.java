package com.ykb.ebook.util;

import android.os.Handler;
import android.os.SystemClock;
import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.socket.engineio.client.Socket;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0016\u0018\u0000 1*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u00011B;\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010!\u001a\u00020\"J\b\u0010#\u001a\u00020\"H\u0002J\r\u0010$\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010%J\u0010\u0010&\u001a\u0004\u0018\u00018\u0000H\u0086\u0002¢\u0006\u0002\u0010%J\u0015\u0010'\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010)J\u0017\u0010*\u001a\u0004\u0018\u00018\u00002\u0006\u0010(\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010)J\u0006\u0010+\u001a\u00020\u0007J\u0010\u0010,\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004H\u0002J\u0010\u0010-\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0004H\u0002J\u0010\u0010.\u001a\u00020\"2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010/\u001a\u00020\"H\u0002J\u0017\u00100\u001a\u0004\u0018\u00018\u00002\u0006\u0010(\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010)R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0010R\u0012\u0010\u0019\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u0012R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0014\"\u0004\b \u0010\u0016¨\u00062"}, d2 = {"Lcom/ykb/ebook/util/Debounce;", ExifInterface.GPS_DIRECTION_TRUE, "", "wait", "", "maxWait", "leading", "", "trailing", com.alipay.sdk.authjs.a.f3174g, "Lkotlin/Function0;", "(JJZZLkotlin/jvm/functions/Function0;)V", "hasTimer", "lastCallTime", "lastInvokeTime", "getLeading", "()Z", "setLeading", "(Z)V", "getMaxWait", "()J", "setMaxWait", "(J)V", "maxing", "getMaxing", "result", "Ljava/lang/Object;", "timerExpiredRunnable", "Ljava/lang/Runnable;", "getTrailing", "setTrailing", "getWait", "setWait", "cancel", "", "cancelTimer", Socket.EVENT_FLUSH, "()Ljava/lang/Object;", "invoke", "invokeFunc", CrashHianalyticsData.TIME, "(J)Ljava/lang/Object;", "leadingEdge", "pending", "remainingWait", "shouldInvoke", "startTimer", "timerExpired", "trailingEdge", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDebounce.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Debounce.kt\ncom/ykb/ebook/util/Debounce\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,132:1\n1#2:133\n*E\n"})
/* loaded from: classes7.dex */
public class Debounce<T> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Lazy<Handler> handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Handler>() { // from class: com.ykb.ebook.util.Debounce$Companion$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final Handler invoke() {
            return HandlerUtilsKt.buildMainHandler();
        }
    });

    @NotNull
    private final Function0<T> func;
    private boolean hasTimer;
    private long lastCallTime;
    private long lastInvokeTime;
    private boolean leading;
    private long maxWait;

    @Nullable
    private T result;

    @NotNull
    private final Runnable timerExpiredRunnable;
    private boolean trailing;
    private long wait;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/util/Debounce$Companion;", "", "()V", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Handler getHandler() {
            return (Handler) Debounce.handler$delegate.getValue();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Debounce(long j2, long j3, boolean z2, boolean z3, @NotNull Function0<? extends T> func) {
        Intrinsics.checkNotNullParameter(func, "func");
        this.wait = j2;
        this.maxWait = j3;
        this.leading = z2;
        this.trailing = z3;
        this.func = func;
        this.lastCallTime = -1L;
        this.timerExpiredRunnable = new Runnable() { // from class: com.ykb.ebook.util.a
            @Override // java.lang.Runnable
            public final void run() {
                Debounce.timerExpiredRunnable$lambda$0(this.f26451c);
            }
        };
        this.maxWait = getMaxing() ? Math.max(this.maxWait, this.wait) : this.maxWait;
    }

    private final void cancelTimer() {
        INSTANCE.getHandler().removeCallbacks(this.timerExpiredRunnable);
    }

    private final boolean getMaxing() {
        return this.maxWait != -1;
    }

    private final T invokeFunc(long time) {
        this.lastInvokeTime = time;
        T tInvoke = this.func.invoke();
        this.result = tInvoke;
        return tInvoke;
    }

    private final T leadingEdge(long time) {
        this.lastInvokeTime = time;
        startTimer(this.wait);
        return this.leading ? invokeFunc(time) : this.result;
    }

    private final long remainingWait(long time) {
        long j2 = time - this.lastCallTime;
        long j3 = time - this.lastInvokeTime;
        long j4 = this.wait - j2;
        return getMaxing() ? RangesKt___RangesKt.coerceAtMost(j4, this.maxWait - j3) : j4;
    }

    private final boolean shouldInvoke(long time) {
        long j2 = this.lastCallTime;
        long j3 = time - j2;
        return j2 == -1 || j3 >= this.wait || j3 < 0 || (getMaxing() && time - this.lastInvokeTime >= this.maxWait);
    }

    private final void startTimer(long wait) {
        this.hasTimer = true;
        INSTANCE.getHandler().postDelayed(this.timerExpiredRunnable, wait);
    }

    private final void timerExpired() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (shouldInvoke(jUptimeMillis)) {
            trailingEdge(jUptimeMillis);
        } else {
            startTimer(remainingWait(jUptimeMillis));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void timerExpiredRunnable$lambda$0(Debounce this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.timerExpired();
    }

    private final T trailingEdge(long time) {
        this.hasTimer = false;
        return this.trailing ? invokeFunc(time) : this.result;
    }

    public final void cancel() {
        if (this.hasTimer) {
            cancelTimer();
        }
        this.lastInvokeTime = 0L;
        this.lastCallTime = -1L;
        this.hasTimer = false;
    }

    @Nullable
    public final T flush() {
        return this.hasTimer ? trailingEdge(SystemClock.uptimeMillis()) : this.result;
    }

    public final boolean getLeading() {
        return this.leading;
    }

    public final long getMaxWait() {
        return this.maxWait;
    }

    public final boolean getTrailing() {
        return this.trailing;
    }

    public final long getWait() {
        return this.wait;
    }

    @Nullable
    public final T invoke() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        boolean zShouldInvoke = shouldInvoke(jUptimeMillis);
        this.lastCallTime = jUptimeMillis;
        if (zShouldInvoke) {
            if (!this.hasTimer) {
                return leadingEdge(jUptimeMillis);
            }
            if (getMaxing()) {
                startTimer(this.wait);
                return invokeFunc(this.lastCallTime);
            }
        }
        if (!this.hasTimer) {
            startTimer(this.wait);
        }
        return this.result;
    }

    /* renamed from: pending, reason: from getter */
    public final boolean getHasTimer() {
        return this.hasTimer;
    }

    public final void setLeading(boolean z2) {
        this.leading = z2;
    }

    public final void setMaxWait(long j2) {
        this.maxWait = j2;
    }

    public final void setTrailing(boolean z2) {
        this.trailing = z2;
    }

    public final void setWait(long j2) {
        this.wait = j2;
    }

    public /* synthetic */ Debounce(long j2, long j3, boolean z2, boolean z3, Function0 function0, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j2, (i2 & 2) != 0 ? -1L : j3, (i2 & 4) != 0 ? false : z2, (i2 & 8) != 0 ? true : z3, function0);
    }
}
