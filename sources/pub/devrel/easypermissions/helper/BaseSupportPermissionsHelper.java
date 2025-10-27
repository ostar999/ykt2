package pub.devrel.easypermissions.helper;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.FragmentManager;
import pub.devrel.easypermissions.RationaleDialogFragmentCompat;

/* loaded from: classes9.dex */
public abstract class BaseSupportPermissionsHelper<T> extends PermissionHelper<T> {
    private static final String TAG = "BSPermissionsHelper";

    public BaseSupportPermissionsHelper(@NonNull T t2) {
        super(t2);
    }

    public abstract FragmentManager getSupportFragmentManager();

    @Override // pub.devrel.easypermissions.helper.PermissionHelper
    public void showRequestPermissionRationale(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String... strArr) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag(RationaleDialogFragmentCompat.TAG) instanceof RationaleDialogFragmentCompat) {
            Log.d(TAG, "Found existing fragment, not showing rationale.");
        } else {
            RationaleDialogFragmentCompat.newInstance(str, str2, str3, i2, i3, strArr).showAllowingStateLoss(supportFragmentManager, RationaleDialogFragmentCompat.TAG);
        }
    }
}
