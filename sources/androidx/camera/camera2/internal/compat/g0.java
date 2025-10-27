package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;

/* loaded from: classes.dex */
public final /* synthetic */ class g0 {
    @NonNull
    public static CameraManagerCompat.CameraManagerCompatImpl a(@NonNull Context context, @NonNull Handler handler) {
        int i2 = Build.VERSION.SDK_INT;
        return i2 >= 29 ? new CameraManagerCompatApi29Impl(context) : i2 >= 28 ? CameraManagerCompatApi28Impl.create(context) : CameraManagerCompatBaseImpl.create(context, handler);
    }
}
