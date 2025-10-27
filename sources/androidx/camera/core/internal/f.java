package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.UseCase;

/* loaded from: classes.dex */
public final /* synthetic */ class f {
    @NonNull
    public static UseCase.EventCallback a(UseCaseEventConfig useCaseEventConfig) {
        return (UseCase.EventCallback) useCaseEventConfig.retrieveOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK);
    }

    @Nullable
    public static UseCase.EventCallback b(UseCaseEventConfig useCaseEventConfig, @Nullable UseCase.EventCallback eventCallback) {
        return (UseCase.EventCallback) useCaseEventConfig.retrieveOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
    }
}
