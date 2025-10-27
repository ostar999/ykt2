package com.plv.thirdpart.blankj.utilcode.util;

import android.os.storage.StorageManager;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
public final class SDCardUtils {
    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static List<String> getSDCardPaths(boolean z2) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        ArrayList arrayList = new ArrayList();
        StorageManager storageManager = (StorageManager) Utils.getApp().getSystemService("storage");
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
                if (z2 == ((Boolean) method3.invoke(obj, new Object[0])).booleanValue()) {
                    arrayList.add(str);
                }
            }
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
        return arrayList;
    }

    public static boolean isSDCardEnable() {
        return !getSDCardPaths().isEmpty();
    }

    public static List<String> getSDCardPaths() throws NoSuchMethodException, SecurityException {
        StorageManager storageManager = (StorageManager) Utils.getApp().getSystemService("storage");
        ArrayList arrayList = new ArrayList();
        try {
            Method method = StorageManager.class.getMethod("getVolumePaths", new Class[0]);
            method.setAccessible(true);
            return Arrays.asList((String[]) method.invoke(storageManager, new Object[0]));
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return arrayList;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return arrayList;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return arrayList;
        }
    }
}
