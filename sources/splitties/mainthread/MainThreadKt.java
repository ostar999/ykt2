package splitties.mainthread;

import android.os.Looper;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\"\u001d\u0010\u0000\u001a\u0004\u0018\u00010\u00018À\u0002X\u0081\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u0012\u0010\u0006\u001a\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b\"\u0010\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"currentThread", "", "getCurrentThread$annotations", "()V", "getCurrentThread", "()Ljava/lang/Object;", "isMainThread", "", "()Z", "mainLooper", "Landroid/os/Looper;", "mainThread", "Ljava/lang/Thread;", "splitties-mainthread_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class MainThreadKt {

    @JvmField
    @NotNull
    public static final Looper mainLooper;

    @JvmField
    @NotNull
    public static final Thread mainThread;

    static {
        Looper mainLooper2 = Looper.getMainLooper();
        Intrinsics.checkNotNull(mainLooper2);
        mainLooper = mainLooper2;
        Thread thread = mainLooper2.getThread();
        Intrinsics.checkNotNullExpressionValue(thread, "mainLooper.thread");
        mainThread = thread;
    }

    @Nullable
    public static final Object getCurrentThread() {
        return Thread.currentThread();
    }

    @PublishedApi
    public static /* synthetic */ void getCurrentThread$annotations() {
    }

    public static final boolean isMainThread() {
        return mainThread == Thread.currentThread();
    }
}
