package pub.devrel.easypermissions.helper;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/* loaded from: classes9.dex */
class SupportFragmentPermissionHelper extends BaseSupportPermissionsHelper<Fragment> {
    public SupportFragmentPermissionHelper(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void directRequestPermissions(int i2, @NonNull String... strArr) {
        getHost().requestPermissions(strArr, i2);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public Context getContext() {
        return getHost().getActivity();
    }

    @Override // pub.devrel.easypermissions.helper.BaseSupportPermissionsHelper
    public FragmentManager getSupportFragmentManager() {
        return getHost().getChildFragmentManager();
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        return getHost().shouldShowRequestPermissionRationale(str);
    }
}
