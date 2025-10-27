package com.plv.foundationsdk.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVSDCardUtils {
    private static final String CHECK_MOUNTED = "checkMounted: ";
    private static final String GET_SDCARD_PATHS = "getSDCardPaths:";
    private static final String TAG = "PLVSDCardUtils";

    public static boolean checkMounted(Context context, String str) {
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            String str2 = (String) storageManager.getClass().getMethod("getVolumeState", String.class).invoke(storageManager, str);
            if ("mounted".equals(str2) || "mounted_ro".equals(str2)) {
                return true;
            }
            return "unknown".equals(str2);
        } catch (IllegalAccessException e2) {
            PLVCommonLog.e(TAG, CHECK_MOUNTED + e2.getMessage());
            return true;
        } catch (NoSuchMethodException e3) {
            PLVCommonLog.e(TAG, CHECK_MOUNTED + e3.getMessage());
            return true;
        } catch (InvocationTargetException e4) {
            PLVCommonLog.e(TAG, CHECK_MOUNTED + e4.getMessage());
            return true;
        }
    }

    public static boolean createNoMediaFile(String str) {
        File file = new File(str, ".nomedia");
        try {
            if (file.exists()) {
                return true;
            }
            return file.createNewFile();
        } catch (IOException e2) {
            PLVCommonLog.e(TAG, "createNoMediaFile:" + e2.getMessage());
            return false;
        }
    }

    public static String createPath(Context context, String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        String absolutePath;
        context.getExternalFilesDir(null);
        String str2 = "/Android/data/" + context.getPackageName();
        List<String> sDCardPaths = getSDCardPaths(context, false, true);
        List<String> sDCardPaths2 = getSDCardPaths(context, true, true);
        if (sDCardPaths.isEmpty() && sDCardPaths2.isEmpty()) {
            absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (!checkMounted(context, absolutePath)) {
                absolutePath = context.getCacheDir().getAbsolutePath();
            } else if (Environment.isExternalStorageRemovable()) {
                absolutePath = absolutePath + str2;
            }
        } else if (sDCardPaths.isEmpty()) {
            absolutePath = sDCardPaths2.get(0) + str2;
        } else {
            absolutePath = sDCardPaths.get(0);
        }
        File file = new File(absolutePath, str);
        if ((file.exists() ? false : file.mkdirs()) || file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static List<String> getSDCardPaths(Context context, boolean z2, boolean z3) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        ArrayList arrayList = new ArrayList();
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Class<?> cls = Class.forName("android.os.storage.StorageVolume");
            Method method = StorageManager.class.getMethod("getVolumeList", new Class[0]);
            Method method2 = cls.getMethod("getPath", new Class[0]);
            Method method3 = cls.getMethod("isRemovable", new Class[0]);
            Object objInvoke = method.invoke(storageManager, new Object[0]);
            int length = Array.getLength(objInvoke);
            for (int i2 = 0; i2 < length; i2++) {
                Object obj = Array.get(objInvoke, i2);
                String str = (String) method2.invoke(obj, new Object[0]);
                boolean zBooleanValue = ((Boolean) method3.invoke(obj, new Object[0])).booleanValue();
                if (str != null && z2 == zBooleanValue && (!z3 || checkMounted(context, str))) {
                    arrayList.add(str);
                }
            }
        } catch (ClassNotFoundException e2) {
            PLVCommonLog.e(TAG, GET_SDCARD_PATHS + e2.getMessage());
        } catch (IllegalAccessException e3) {
            PLVCommonLog.e(TAG, GET_SDCARD_PATHS + e3.getMessage());
        } catch (NoSuchMethodException e4) {
            PLVCommonLog.e(TAG, GET_SDCARD_PATHS + e4.getMessage());
        } catch (InvocationTargetException e5) {
            PLVCommonLog.e(TAG, GET_SDCARD_PATHS + e5.getMessage());
        }
        return arrayList;
    }
}
