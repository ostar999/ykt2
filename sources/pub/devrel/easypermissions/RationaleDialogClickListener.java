package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.DialogInterface;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.helper.PermissionHelper;

/* loaded from: classes9.dex */
class RationaleDialogClickListener implements DialogInterface.OnClickListener {
    private EasyPermissions.PermissionCallbacks mCallbacks;
    private RationaleDialogConfig mConfig;
    private Object mHost;
    private EasyPermissions.RationaleCallbacks mRationaleCallbacks;

    public RationaleDialogClickListener(RationaleDialogFragmentCompat rationaleDialogFragmentCompat, RationaleDialogConfig rationaleDialogConfig, EasyPermissions.PermissionCallbacks permissionCallbacks, EasyPermissions.RationaleCallbacks rationaleCallbacks) {
        this.mHost = rationaleDialogFragmentCompat.getParentFragment() != null ? rationaleDialogFragmentCompat.getParentFragment() : rationaleDialogFragmentCompat.getActivity();
        this.mConfig = rationaleDialogConfig;
        this.mCallbacks = permissionCallbacks;
        this.mRationaleCallbacks = rationaleCallbacks;
    }

    private void notifyPermissionDenied() {
        EasyPermissions.PermissionCallbacks permissionCallbacks = this.mCallbacks;
        if (permissionCallbacks != null) {
            RationaleDialogConfig rationaleDialogConfig = this.mConfig;
            permissionCallbacks.onPermissionsDenied(rationaleDialogConfig.requestCode, Arrays.asList(rationaleDialogConfig.permissions));
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i2) {
        RationaleDialogConfig rationaleDialogConfig = this.mConfig;
        int i3 = rationaleDialogConfig.requestCode;
        if (i2 != -1) {
            EasyPermissions.RationaleCallbacks rationaleCallbacks = this.mRationaleCallbacks;
            if (rationaleCallbacks != null) {
                rationaleCallbacks.onRationaleDenied(i3);
            }
            notifyPermissionDenied();
            return;
        }
        String[] strArr = rationaleDialogConfig.permissions;
        EasyPermissions.RationaleCallbacks rationaleCallbacks2 = this.mRationaleCallbacks;
        if (rationaleCallbacks2 != null) {
            rationaleCallbacks2.onRationaleAccepted(i3);
        }
        Object obj = this.mHost;
        if (obj instanceof Fragment) {
            PermissionHelper.newInstance((Fragment) obj).directRequestPermissions(i3, strArr);
        } else {
            if (!(obj instanceof Activity)) {
                throw new RuntimeException("Host must be an Activity or Fragment!");
            }
            PermissionHelper.newInstance((Activity) obj).directRequestPermissions(i3, strArr);
        }
    }

    public RationaleDialogClickListener(RationaleDialogFragment rationaleDialogFragment, RationaleDialogConfig rationaleDialogConfig, EasyPermissions.PermissionCallbacks permissionCallbacks, EasyPermissions.RationaleCallbacks rationaleCallbacks) {
        this.mHost = rationaleDialogFragment.getActivity();
        this.mConfig = rationaleDialogConfig;
        this.mCallbacks = permissionCallbacks;
        this.mRationaleCallbacks = rationaleCallbacks;
    }
}
