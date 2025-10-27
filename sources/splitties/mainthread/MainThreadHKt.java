package splitties.mainthread;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0086\b\u001a\t\u0010\u0002\u001a\u00020\u0001H\u0086\b¨\u0006\u0003"}, d2 = {"checkMainThread", "", "checkNotMainThread", "splitties-mainthread_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class MainThreadHKt {
    public static final void checkMainThread() {
        if (!(MainThreadKt.mainThread == Thread.currentThread())) {
            throw new IllegalStateException(Intrinsics.stringPlus("This should ONLY be called on the main thread! Current: ", Thread.currentThread()).toString());
        }
    }

    public static final void checkNotMainThread() {
        if (!(!(MainThreadKt.mainThread == Thread.currentThread()))) {
            throw new IllegalStateException(Intrinsics.stringPlus("This should NEVER be called on the main thread! Current: ", Thread.currentThread()).toString());
        }
    }
}
