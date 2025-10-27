package pub.devrel.easypermissions.helper;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

/* loaded from: classes9.dex */
class AppCompatActivityPermissionsHelper extends BaseSupportPermissionsHelper<AppCompatActivity> {
    public AppCompatActivityPermissionsHelper(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void directRequestPermissions(int i2, @NonNull String... strArr) {
        ActivityCompat.requestPermissions(getHost(), strArr, i2);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public Context getContext() {
        return getHost();
    }

    @Override // pub.devrel.easypermissions.helper.BaseSupportPermissionsHelper
    public FragmentManager getSupportFragmentManager() {
        return getHost().getSupportFragmentManager();
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(), str);
    }
}
