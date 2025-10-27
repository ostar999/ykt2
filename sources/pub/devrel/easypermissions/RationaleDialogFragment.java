package pub.devrel.easypermissions;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import pub.devrel.easypermissions.EasyPermissions;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes9.dex */
public class RationaleDialogFragment extends DialogFragment {
    public static final String TAG = "RationaleDialogFragment";
    private EasyPermissions.PermissionCallbacks mPermissionCallbacks;
    private EasyPermissions.RationaleCallbacks mRationaleCallbacks;
    private boolean mStateSaved = false;

    public static RationaleDialogFragment newInstance(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String[] strArr) {
        RationaleDialogFragment rationaleDialogFragment = new RationaleDialogFragment();
        rationaleDialogFragment.setArguments(new RationaleDialogConfig(str, str2, str3, i2, i3, strArr).toBundle());
        return rationaleDialogFragment;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.DialogFragment, android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() != null) {
            if (getParentFragment() instanceof EasyPermissions.PermissionCallbacks) {
                this.mPermissionCallbacks = (EasyPermissions.PermissionCallbacks) getParentFragment();
            }
            if (getParentFragment() instanceof EasyPermissions.RationaleCallbacks) {
                this.mRationaleCallbacks = (EasyPermissions.RationaleCallbacks) getParentFragment();
            }
        }
        if (context instanceof EasyPermissions.PermissionCallbacks) {
            this.mPermissionCallbacks = (EasyPermissions.PermissionCallbacks) context;
        }
        if (context instanceof EasyPermissions.RationaleCallbacks) {
            this.mRationaleCallbacks = (EasyPermissions.RationaleCallbacks) context;
        }
    }

    @Override // android.app.DialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        setCancelable(false);
        RationaleDialogConfig rationaleDialogConfig = new RationaleDialogConfig(getArguments());
        return rationaleDialogConfig.createFrameworkDialog(getActivity(), new RationaleDialogClickListener(this, rationaleDialogConfig, this.mPermissionCallbacks, this.mRationaleCallbacks));
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mPermissionCallbacks = null;
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        this.mStateSaved = true;
        super.onSaveInstanceState(bundle);
    }

    public void showAllowingStateLoss(FragmentManager fragmentManager, String str) {
        if ((Build.VERSION.SDK_INT < 26 || !fragmentManager.isStateSaved()) && !this.mStateSaved) {
            show(fragmentManager, str);
        }
    }
}
