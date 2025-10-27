package androidx.core.location;

import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/* loaded from: classes.dex */
public final /* synthetic */ class y {
    public static void a(LocationListenerCompat locationListenerCompat, int i2) {
    }

    public static void b(LocationListenerCompat locationListenerCompat, @NonNull List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            locationListenerCompat.onLocationChanged((Location) list.get(i2));
        }
    }

    public static void c(LocationListenerCompat locationListenerCompat, @NonNull String str) {
    }

    public static void d(LocationListenerCompat locationListenerCompat, @NonNull String str) {
    }

    public static void e(LocationListenerCompat locationListenerCompat, @NonNull String str, int i2, @Nullable Bundle bundle) {
    }
}
