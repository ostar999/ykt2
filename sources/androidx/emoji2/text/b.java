package androidx.emoji2.text;

import android.os.Handler;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class b implements Executor {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Handler f1884c;

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.f1884c.post(runnable);
    }
}
