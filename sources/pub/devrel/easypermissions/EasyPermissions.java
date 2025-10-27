package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import cn.hutool.core.text.StrPool;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.PermissionRequest;
import pub.devrel.easypermissions.helper.PermissionHelper;

/* loaded from: classes9.dex */
public class EasyPermissions {
    private static final String TAG = "EasyPermissions";

    public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {
        void onPermissionsDenied(int i2, @NonNull List<String> list);

        void onPermissionsGranted(int i2, @NonNull List<String> list);
    }

    public interface RationaleCallbacks {
        void onRationaleAccepted(int i2);

        void onRationaleDenied(int i2);
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull @Size(min = 1) String... strArr) {
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUsingAndroidAnnotations(@NonNull Object obj) {
        if (!obj.getClass().getSimpleName().endsWith(StrPool.UNDERLINE)) {
            return false;
        }
        try {
            return Class.forName("org.androidannotations.api.view.HasViews").isInstance(obj);
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private static void notifyAlreadyHasPermissions(@NonNull Object obj, int i2, @NonNull String[] strArr) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int[] iArr = new int[strArr.length];
        for (int i3 = 0; i3 < strArr.length; i3++) {
            iArr[i3] = 0;
        }
        onRequestPermissionsResult(i2, strArr, iArr, obj);
    }

    public static void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr, @NonNull Object... objArr) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < strArr.length; i3++) {
            String str = strArr[i3];
            if (iArr[i3] == 0) {
                arrayList.add(str);
            } else {
                arrayList2.add(str);
            }
        }
        for (Object obj : objArr) {
            if (!arrayList.isEmpty() && (obj instanceof PermissionCallbacks)) {
                ((PermissionCallbacks) obj).onPermissionsGranted(i2, arrayList);
            }
            if (!arrayList2.isEmpty() && (obj instanceof PermissionCallbacks)) {
                ((PermissionCallbacks) obj).onPermissionsDenied(i2, arrayList2);
            }
            if (!arrayList.isEmpty() && arrayList2.isEmpty()) {
                runAnnotatedMethods(obj, i2);
            }
        }
    }

    public static boolean permissionPermanentlyDenied(@NonNull Activity activity, @NonNull String str) {
        return PermissionHelper.newInstance(activity).permissionPermanentlyDenied(str);
    }

    public static void requestPermissions(@NonNull Activity activity, @NonNull String str, int i2, @NonNull @Size(min = 1) String... strArr) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        requestPermissions(new PermissionRequest.Builder(activity, i2, strArr).setRationale(str).build());
    }

    private static void runAnnotatedMethods(@NonNull Object obj, int i2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> superclass = obj.getClass();
        if (isUsingAndroidAnnotations(obj)) {
            superclass = superclass.getSuperclass();
        }
        while (superclass != null) {
            for (Method method : superclass.getDeclaredMethods()) {
                AfterPermissionGranted afterPermissionGranted = (AfterPermissionGranted) method.getAnnotation(AfterPermissionGranted.class);
                if (afterPermissionGranted != null && afterPermissionGranted.value() == i2) {
                    if (method.getParameterTypes().length > 0) {
                        throw new RuntimeException("Cannot execute method " + method.getName() + " because it is non-void method and/or has input parameters.");
                    }
                    try {
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(obj, new Object[0]);
                    } catch (IllegalAccessException e2) {
                        Log.e(TAG, "runDefaultMethod:IllegalAccessException", e2);
                    } catch (InvocationTargetException e3) {
                        Log.e(TAG, "runDefaultMethod:InvocationTargetException", e3);
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
    }

    public static boolean somePermissionDenied(@NonNull Activity activity, @NonNull String... strArr) {
        return PermissionHelper.newInstance(activity).somePermissionDenied(strArr);
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull Activity activity, @NonNull List<String> list) {
        return PermissionHelper.newInstance(activity).somePermissionPermanentlyDenied(list);
    }

    public static boolean permissionPermanentlyDenied(@NonNull Fragment fragment, @NonNull String str) {
        return PermissionHelper.newInstance(fragment).permissionPermanentlyDenied(str);
    }

    public static boolean somePermissionDenied(@NonNull Fragment fragment, @NonNull String... strArr) {
        return PermissionHelper.newInstance(fragment).somePermissionDenied(strArr);
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull Fragment fragment, @NonNull List<String> list) {
        return PermissionHelper.newInstance(fragment).somePermissionPermanentlyDenied(list);
    }

    public static void requestPermissions(@NonNull Fragment fragment, @NonNull String str, int i2, @NonNull @Size(min = 1) String... strArr) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        requestPermissions(new PermissionRequest.Builder(fragment, i2, strArr).setRationale(str).build());
    }

    public static void requestPermissions(PermissionRequest permissionRequest) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (hasPermissions(permissionRequest.getHelper().getContext(), permissionRequest.getPerms())) {
            notifyAlreadyHasPermissions(permissionRequest.getHelper().getHost(), permissionRequest.getRequestCode(), permissionRequest.getPerms());
        } else {
            permissionRequest.getHelper().requestPermissions(permissionRequest.getRationale(), permissionRequest.getPositiveButtonText(), permissionRequest.getNegativeButtonText(), permissionRequest.getTheme(), permissionRequest.getRequestCode(), permissionRequest.getPerms());
        }
    }
}
