package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes9.dex */
public abstract class PermissionHelper<T> {
    private T mHost;

    public PermissionHelper(@NonNull T t2) {
        this.mHost = t2;
    }

    @NonNull
    public static PermissionHelper<? extends Activity> newInstance(Activity activity) {
        return activity instanceof AppCompatActivity ? new AppCompatActivityPermissionsHelper((AppCompatActivity) activity) : new ActivityPermissionHelper(activity);
    }

    private boolean shouldShowRationale(@NonNull String... strArr) {
        for (String str : strArr) {
            if (shouldShowRequestPermissionRationale(str)) {
                return true;
            }
        }
        return false;
    }

    public abstract void directRequestPermissions(int i2, @NonNull String... strArr);

    public abstract Context getContext();

    @NonNull
    public T getHost() {
        return this.mHost;
    }

    public boolean permissionPermanentlyDenied(@NonNull String str) {
        return !shouldShowRequestPermissionRationale(str);
    }

    public void requestPermissions(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String... strArr) {
        if (shouldShowRationale(strArr)) {
            showRequestPermissionRationale(str, str2, str3, i2, i3, strArr);
        } else {
            directRequestPermissions(i3, strArr);
        }
    }

    public abstract boolean shouldShowRequestPermissionRationale(@NonNull String str);

    public abstract void showRequestPermissionRationale(@NonNull String str, @NonNull String str2, @NonNull String str3, @StyleRes int i2, int i3, @NonNull String... strArr);

    public boolean somePermissionDenied(@NonNull String... strArr) {
        return shouldShowRationale(strArr);
    }

    public boolean somePermissionPermanentlyDenied(@NonNull List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (permissionPermanentlyDenied(it.next())) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public static PermissionHelper<Fragment> newInstance(Fragment fragment) {
        return new SupportFragmentPermissionHelper(fragment);
    }
}
