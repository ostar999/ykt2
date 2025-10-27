package pub.devrel.easypermissions;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.Size;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import cn.hutool.core.text.CharPool;
import java.util.Arrays;
import pub.devrel.easypermissions.helper.PermissionHelper;

/* loaded from: classes9.dex */
public final class PermissionRequest {
    private final PermissionHelper mHelper;
    private final String mNegativeButtonText;
    private final String[] mPerms;
    private final String mPositiveButtonText;
    private final String mRationale;
    private final int mRequestCode;
    private final int mTheme;

    public static final class Builder {
        private final PermissionHelper mHelper;
        private String mNegativeButtonText;
        private final String[] mPerms;
        private String mPositiveButtonText;
        private String mRationale;
        private final int mRequestCode;
        private int mTheme = -1;

        public Builder(@NonNull Activity activity, int i2, @NonNull @Size(min = 1) String... strArr) {
            this.mHelper = PermissionHelper.newInstance(activity);
            this.mRequestCode = i2;
            this.mPerms = strArr;
        }

        @NonNull
        public PermissionRequest build() {
            if (this.mRationale == null) {
                this.mRationale = this.mHelper.getContext().getString(R.string.rationale_ask);
            }
            if (this.mPositiveButtonText == null) {
                this.mPositiveButtonText = this.mHelper.getContext().getString(android.R.string.ok);
            }
            if (this.mNegativeButtonText == null) {
                this.mNegativeButtonText = this.mHelper.getContext().getString(android.R.string.cancel);
            }
            return new PermissionRequest(this.mHelper, this.mPerms, this.mRequestCode, this.mRationale, this.mPositiveButtonText, this.mNegativeButtonText, this.mTheme);
        }

        @NonNull
        public Builder setNegativeButtonText(@Nullable String str) {
            this.mNegativeButtonText = str;
            return this;
        }

        @NonNull
        public Builder setPositiveButtonText(@Nullable String str) {
            this.mPositiveButtonText = str;
            return this;
        }

        @NonNull
        public Builder setRationale(@Nullable String str) {
            this.mRationale = str;
            return this;
        }

        @NonNull
        public Builder setTheme(@StyleRes int i2) {
            this.mTheme = i2;
            return this;
        }

        @NonNull
        public Builder setNegativeButtonText(@StringRes int i2) {
            this.mNegativeButtonText = this.mHelper.getContext().getString(i2);
            return this;
        }

        @NonNull
        public Builder setPositiveButtonText(@StringRes int i2) {
            this.mPositiveButtonText = this.mHelper.getContext().getString(i2);
            return this;
        }

        @NonNull
        public Builder setRationale(@StringRes int i2) {
            this.mRationale = this.mHelper.getContext().getString(i2);
            return this;
        }

        public Builder(@NonNull Fragment fragment, int i2, @NonNull @Size(min = 1) String... strArr) {
            this.mHelper = PermissionHelper.newInstance(fragment);
            this.mRequestCode = i2;
            this.mPerms = strArr;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PermissionRequest.class != obj.getClass()) {
            return false;
        }
        PermissionRequest permissionRequest = (PermissionRequest) obj;
        return Arrays.equals(this.mPerms, permissionRequest.mPerms) && this.mRequestCode == permissionRequest.mRequestCode;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PermissionHelper getHelper() {
        return this.mHelper;
    }

    @NonNull
    public String getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    @NonNull
    public String[] getPerms() {
        return (String[]) this.mPerms.clone();
    }

    @NonNull
    public String getPositiveButtonText() {
        return this.mPositiveButtonText;
    }

    @NonNull
    public String getRationale() {
        return this.mRationale;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }

    @StyleRes
    public int getTheme() {
        return this.mTheme;
    }

    public int hashCode() {
        return (Arrays.hashCode(this.mPerms) * 31) + this.mRequestCode;
    }

    public String toString() {
        return "PermissionRequest{mHelper=" + this.mHelper + ", mPerms=" + Arrays.toString(this.mPerms) + ", mRequestCode=" + this.mRequestCode + ", mRationale='" + this.mRationale + CharPool.SINGLE_QUOTE + ", mPositiveButtonText='" + this.mPositiveButtonText + CharPool.SINGLE_QUOTE + ", mNegativeButtonText='" + this.mNegativeButtonText + CharPool.SINGLE_QUOTE + ", mTheme=" + this.mTheme + '}';
    }

    private PermissionRequest(PermissionHelper permissionHelper, String[] strArr, int i2, String str, String str2, String str3, int i3) {
        this.mHelper = permissionHelper;
        this.mPerms = (String[]) strArr.clone();
        this.mRequestCode = i2;
        this.mRationale = str;
        this.mPositiveButtonText = str2;
        this.mNegativeButtonText = str3;
        this.mTheme = i3;
    }
}
