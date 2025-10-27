package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

/* loaded from: classes9.dex */
public class AppSettingsDialog implements Parcelable {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Parcelable.Creator<AppSettingsDialog> CREATOR = new Parcelable.Creator<AppSettingsDialog>() { // from class: pub.devrel.easypermissions.AppSettingsDialog.1
        @Override // android.os.Parcelable.Creator
        public AppSettingsDialog createFromParcel(Parcel parcel) {
            return new AppSettingsDialog(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public AppSettingsDialog[] newArray(int i2) {
            return new AppSettingsDialog[i2];
        }
    };
    public static final int DEFAULT_SETTINGS_REQ_CODE = 16061;
    static final String EXTRA_APP_SETTINGS = "extra_app_settings";
    private static final String TAG = "EasyPermissions";
    private Object mActivityOrFragment;
    private Context mContext;
    private final int mIntentFlags;
    private final String mNegativeButtonText;
    private final String mPositiveButtonText;
    private final String mRationale;
    private final int mRequestCode;

    @StyleRes
    private final int mThemeResId;
    private final String mTitle;

    public static class Builder {
        private final Object mActivityOrFragment;
        private final Context mContext;
        private String mNegativeButtonText;
        private String mPositiveButtonText;
        private String mRationale;
        private String mTitle;

        @StyleRes
        private int mThemeResId = -1;
        private int mRequestCode = -1;
        private boolean mOpenInNewTask = false;

        public Builder(@NonNull Activity activity) {
            this.mActivityOrFragment = activity;
            this.mContext = activity;
        }

        @NonNull
        public AppSettingsDialog build() {
            this.mRationale = TextUtils.isEmpty(this.mRationale) ? this.mContext.getString(R.string.rationale_ask_again) : this.mRationale;
            this.mTitle = TextUtils.isEmpty(this.mTitle) ? this.mContext.getString(R.string.title_settings_dialog) : this.mTitle;
            this.mPositiveButtonText = TextUtils.isEmpty(this.mPositiveButtonText) ? this.mContext.getString(android.R.string.ok) : this.mPositiveButtonText;
            this.mNegativeButtonText = TextUtils.isEmpty(this.mNegativeButtonText) ? this.mContext.getString(android.R.string.cancel) : this.mNegativeButtonText;
            int i2 = this.mRequestCode;
            if (i2 <= 0) {
                i2 = 16061;
            }
            this.mRequestCode = i2;
            return new AppSettingsDialog(this.mActivityOrFragment, this.mThemeResId, this.mRationale, this.mTitle, this.mPositiveButtonText, this.mNegativeButtonText, this.mRequestCode, this.mOpenInNewTask ? 268435456 : 0);
        }

        @NonNull
        public Builder setNegativeButton(@Nullable String str) {
            this.mNegativeButtonText = str;
            return this;
        }

        @NonNull
        public Builder setOpenInNewTask(boolean z2) {
            this.mOpenInNewTask = z2;
            return this;
        }

        @NonNull
        public Builder setPositiveButton(@Nullable String str) {
            this.mPositiveButtonText = str;
            return this;
        }

        @NonNull
        public Builder setRationale(@Nullable String str) {
            this.mRationale = str;
            return this;
        }

        @NonNull
        public Builder setRequestCode(int i2) {
            this.mRequestCode = i2;
            return this;
        }

        @NonNull
        public Builder setThemeResId(@StyleRes int i2) {
            this.mThemeResId = i2;
            return this;
        }

        @NonNull
        public Builder setTitle(@Nullable String str) {
            this.mTitle = str;
            return this;
        }

        @NonNull
        public Builder setNegativeButton(@StringRes int i2) {
            this.mNegativeButtonText = this.mContext.getString(i2);
            return this;
        }

        @NonNull
        public Builder setPositiveButton(@StringRes int i2) {
            this.mPositiveButtonText = this.mContext.getString(i2);
            return this;
        }

        @NonNull
        public Builder setRationale(@StringRes int i2) {
            this.mRationale = this.mContext.getString(i2);
            return this;
        }

        @NonNull
        public Builder setTitle(@StringRes int i2) {
            this.mTitle = this.mContext.getString(i2);
            return this;
        }

        public Builder(@NonNull Fragment fragment) {
            this.mActivityOrFragment = fragment;
            this.mContext = fragment.getContext();
        }
    }

    public static AppSettingsDialog fromIntent(Intent intent, Activity activity) {
        AppSettingsDialog appSettingsDialogBuild = (AppSettingsDialog) intent.getParcelableExtra(EXTRA_APP_SETTINGS);
        if (appSettingsDialogBuild == null) {
            Log.e(TAG, "Intent contains null value for EXTRA_APP_SETTINGS: intent=" + intent + ", extras=" + intent.getExtras());
            appSettingsDialogBuild = new Builder(activity).build();
        }
        appSettingsDialogBuild.setActivityOrFragment(activity);
        return appSettingsDialogBuild;
    }

    private void setActivityOrFragment(Object obj) {
        this.mActivityOrFragment = obj;
        if (obj instanceof Activity) {
            this.mContext = (Activity) obj;
        } else {
            if (obj instanceof Fragment) {
                this.mContext = ((Fragment) obj).getContext();
                return;
            }
            throw new IllegalStateException("Unknown object: " + obj);
        }
    }

    private void startForResult(Intent intent) {
        Object obj = this.mActivityOrFragment;
        if (obj instanceof Activity) {
            ((Activity) obj).startActivityForResult(intent, this.mRequestCode);
        } else if (obj instanceof Fragment) {
            ((Fragment) obj).startActivityForResult(intent, this.mRequestCode);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getIntentFlags() {
        return this.mIntentFlags;
    }

    public void show() {
        startForResult(AppSettingsDialogHolderActivity.createShowDialogIntent(this.mContext, this));
    }

    public AlertDialog showDialog(DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2) {
        int i2 = this.mThemeResId;
        return (i2 != -1 ? new AlertDialog.Builder(this.mContext, i2) : new AlertDialog.Builder(this.mContext)).setCancelable(false).setTitle(this.mTitle).setMessage(this.mRationale).setPositiveButton(this.mPositiveButtonText, onClickListener).setNegativeButton(this.mNegativeButtonText, onClickListener2).show();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeInt(this.mThemeResId);
        parcel.writeString(this.mRationale);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mPositiveButtonText);
        parcel.writeString(this.mNegativeButtonText);
        parcel.writeInt(this.mRequestCode);
        parcel.writeInt(this.mIntentFlags);
    }

    private AppSettingsDialog(Parcel parcel) {
        this.mThemeResId = parcel.readInt();
        this.mRationale = parcel.readString();
        this.mTitle = parcel.readString();
        this.mPositiveButtonText = parcel.readString();
        this.mNegativeButtonText = parcel.readString();
        this.mRequestCode = parcel.readInt();
        this.mIntentFlags = parcel.readInt();
    }

    private AppSettingsDialog(@NonNull Object obj, @StyleRes int i2, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i3, int i4) {
        setActivityOrFragment(obj);
        this.mThemeResId = i2;
        this.mRationale = str;
        this.mTitle = str2;
        this.mPositiveButtonText = str3;
        this.mNegativeButtonText = str4;
        this.mRequestCode = i3;
        this.mIntentFlags = i4;
    }
}
