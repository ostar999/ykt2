package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.auto.value.AutoValue;

@AutoValue
@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class Identifier {
    @NonNull
    public static Identifier create(@NonNull Object obj) {
        return new AutoValue_Identifier(obj);
    }

    @NonNull
    public abstract Object getValue();
}
