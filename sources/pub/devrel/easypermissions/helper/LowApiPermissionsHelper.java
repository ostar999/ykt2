package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

/* loaded from: classes9.dex */
class LowApiPermissionsHelper<T> extends PermissionHelper<T> {
    public LowApiPermissionsHelper(@NonNull T t2) {
        super(t2);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void directRequestPermissions(int i2, @NonNull String... strArr) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23!");
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public Context getContext() {
        if (getHost() instanceof Activity) {
            return (Context) getHost();
        }
        if (getHost() instanceof Fragment) {
            return ((Fragment) getHost()).getContext();
        }
        throw new IllegalStateException("Unknown host: " + getHost());
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        return false;
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void showRequestPermissionRationale(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String... strArr) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23!");
    }
}
