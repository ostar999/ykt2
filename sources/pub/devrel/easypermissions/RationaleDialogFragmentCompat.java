package pub.devrel.easypermissions;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import pub.devrel.easypermissions.EasyPermissions;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes9.dex */
public class RationaleDialogFragmentCompat extends AppCompatDialogFragment {
    public static final String TAG = "RationaleDialogFragmentCompat";
    private EasyPermissions.PermissionCallbacks mPermissionCallbacks;
    private EasyPermissions.RationaleCallbacks mRationaleCallbacks;

    public static RationaleDialogFragmentCompat newInstance(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String[] strArr) {
        RationaleDialogFragmentCompat rationaleDialogFragmentCompat = new RationaleDialogFragmentCompat();
        rationaleDialogFragmentCompat.setArguments(new RationaleDialogConfig(str2, str3, str, i2, i3, strArr).toBundle());
        return rationaleDialogFragmentCompat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
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

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        setCancelable(false);
        RationaleDialogConfig rationaleDialogConfig = new RationaleDialogConfig(getArguments());
        return rationaleDialogConfig.createSupportDialog(getContext(), new RationaleDialogClickListener(this, rationaleDialogConfig, this.mPermissionCallbacks, this.mRationaleCallbacks));
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mPermissionCallbacks = null;
        this.mRationaleCallbacks = null;
    }

    public void showAllowingStateLoss(FragmentManager fragmentManager, String str) {
        if (fragmentManager.isStateSaved()) {
            return;
        }
        show(fragmentManager, str);
    }
}
