package com.ykb.ebook.common_interface;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\nH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/common_interface/HandlerAction;", "", "getHandler", "Landroid/os/Handler;", "post", "", "runnable", "Ljava/lang/Runnable;", "postAtTime", "uptimeMillis", "", "postDelayed", "delayMillis", "removeCallbacks", "", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface HandlerAction {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/common_interface/HandlerAction$Companion;", "", "()V", "HANDLER", "Landroid/os/Handler;", "getHANDLER", "()Landroid/os/Handler;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        @NotNull
        private static final Handler HANDLER = new Handler(Looper.getMainLooper());

        private Companion() {
        }

        @NotNull
        public final Handler getHANDLER() {
            return HANDLER;
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        @NotNull
        public static Handler getHandler(@NotNull HandlerAction handlerAction) {
            return HandlerAction.INSTANCE.getHANDLER();
        }

        public static boolean post(@NotNull HandlerAction handlerAction, @NotNull Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            return handlerAction.postDelayed(runnable, 0L);
        }

        public static boolean postAtTime(@NotNull HandlerAction handlerAction, @NotNull Runnable runnable, long j2) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            return HandlerAction.INSTANCE.getHANDLER().postAtTime(runnable, handlerAction, j2);
        }

        public static boolean postDelayed(@NotNull HandlerAction handlerAction, @NotNull Runnable runnable, long j2) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            long jUptimeMillis = SystemClock.uptimeMillis();
            if (j2 < 0) {
                j2 = 0;
            }
            return handlerAction.postAtTime(runnable, jUptimeMillis + j2);
        }

        public static void removeCallbacks(@NotNull HandlerAction handlerAction, @NotNull Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            HandlerAction.INSTANCE.getHANDLER().removeCallbacks(runnable);
        }

        public static void removeCallbacks(@NotNull HandlerAction handlerAction) {
            HandlerAction.INSTANCE.getHANDLER().removeCallbacksAndMessages(handlerAction);
        }
    }

    @NotNull
    Handler getHandler();

    boolean post(@NotNull Runnable runnable);

    boolean postAtTime(@NotNull Runnable runnable, long uptimeMillis);

    boolean postDelayed(@NotNull Runnable runnable, long delayMillis);

    void removeCallbacks();

    void removeCallbacks(@NotNull Runnable runnable);
}
