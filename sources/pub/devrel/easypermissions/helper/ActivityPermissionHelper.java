package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.core.app.ActivityCompat;
import pub.devrel.easypermissions.RationaleDialogFragment;

/* loaded from: classes9.dex */
class ActivityPermissionHelper extends PermissionHelper<Activity> {
    private static final String TAG = "ActPermissionHelper";

    public ActivityPermissionHelper(Activity activity) {
        super(activity);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void directRequestPermissions(int i2, @NonNull String... strArr) {
        ActivityCompat.requestPermissions(getHost(), strArr, i2);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public Context getContext() {
        return getHost();
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(), str);
    }

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void showRequestPermissionRationale(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String... strArr) {
        FragmentManager fragmentManager = getHost().getFragmentManager();
        if (fragmentManager.findFragmentByTag(RationaleDialogFragment.TAG) instanceof RationaleDialogFragment) {
            Log.d(TAG, "Found existing fragment, not showing rationale.");
        } else {
            RationaleDialogFragment.newInstance(str2, str3, str, i2, i3, strArr).showAllowingStateLoss(fragmentManager, RationaleDialogFragment.TAG);
        }
    }
}
