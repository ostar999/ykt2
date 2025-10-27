package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class e {
    @NonNull
    public static Executor a(ThreadConfig threadConfig) {
        return (Executor) threadConfig.retrieveOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR);
    }

    @Nullable
    public static Executor b(ThreadConfig threadConfig, @Nullable Executor executor) {
        return (Executor) threadConfig.retrieveOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, executor);
    }
}
