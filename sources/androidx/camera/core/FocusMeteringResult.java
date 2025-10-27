package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class FocusMeteringResult {
    private boolean mIsFocusSuccessful;

    private FocusMeteringResult(boolean z2) {
        this.mIsFocusSuccessful = z2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static FocusMeteringResult create(boolean z2) {
        return new FocusMeteringResult(z2);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static FocusMeteringResult emptyInstance() {
        return new FocusMeteringResult(false);
    }

    public boolean isFocusSuccessful() {
        return this.mIsFocusSuccessful;
    }
}
