package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class c {
    @NonNull
    public static Executor a(IoConfig ioConfig) {
        return (Executor) ioConfig.retrieveOption(IoConfig.OPTION_IO_EXECUTOR);
    }

    @Nullable
    public static Executor b(IoConfig ioConfig, @Nullable Executor executor) {
        return (Executor) ioConfig.retrieveOption(IoConfig.OPTION_IO_EXECUTOR, executor);
    }
}
