package pub.devrel.easypermissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

/* loaded from: classes9.dex */
class RationaleDialogConfig {
    private static final String KEY_NEGATIVE_BUTTON = "negativeButton";
    private static final String KEY_PERMISSIONS = "permissions";
    private static final String KEY_POSITIVE_BUTTON = "positiveButton";
    private static final String KEY_RATIONALE_MESSAGE = "rationaleMsg";
    private static final String KEY_REQUEST_CODE = "requestCode";
    private static final String KEY_THEME = "theme";
    String negativeButton;
    String[] permissions;
    String positiveButton;
    String rationaleMsg;
    int requestCode;
    int theme;

    public RationaleDialogConfig(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String[] strArr) {
        this.positiveButton = str;
        this.negativeButton = str2;
        this.rationaleMsg = str3;
        this.theme = i2;
        this.requestCode = i3;
        this.permissions = strArr;
    }

    public AlertDialog createFrameworkDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        return (this.theme > 0 ? new AlertDialog.Builder(context, this.theme) : new AlertDialog.Builder(context)).setCancelable(false).setPositiveButton(this.positiveButton, onClickListener).setNegativeButton(this.negativeButton, onClickListener).setMessage(this.rationaleMsg).create();
    }

    public androidx.appcompat.app.AlertDialog createSupportDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        int i2 = this.theme;
        return (i2 > 0 ? new AlertDialog.Builder(context, i2) : new AlertDialog.Builder(context)).setCancelable(false).setPositiveButton(this.positiveButton, onClickListener).setNegativeButton(this.negativeButton, onClickListener).setMessage(this.rationaleMsg).create();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_POSITIVE_BUTTON, this.positiveButton);
        bundle.putString(KEY_NEGATIVE_BUTTON, this.negativeButton);
        bundle.putString(KEY_RATIONALE_MESSAGE, this.rationaleMsg);
        bundle.putInt(KEY_THEME, this.theme);
        bundle.putInt("requestCode", this.requestCode);
        bundle.putStringArray(KEY_PERMISSIONS, this.permissions);
        return bundle;
    }

    public RationaleDialogConfig(Bundle bundle) {
        this.positiveButton = bundle.getString(KEY_POSITIVE_BUTTON);
        this.negativeButton = bundle.getString(KEY_NEGATIVE_BUTTON);
        this.rationaleMsg = bundle.getString(KEY_RATIONALE_MESSAGE);
        this.theme = bundle.getInt(KEY_THEME);
        this.requestCode = bundle.getInt("requestCode");
        this.permissions = bundle.getStringArray(KEY_PERMISSIONS);
    }
}
