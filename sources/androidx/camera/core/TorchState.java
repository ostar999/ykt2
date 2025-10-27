package androidx.camera.core;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(21)
/* loaded from: classes.dex */
public class TorchState {
    public static final int OFF = 0;
    public static final int ON = 1;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    private TorchState() {
    }
}
