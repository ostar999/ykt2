package com.plv.foundationsdk.log.elog;

import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class PLVElogHelper {
    private static final String CREATE_ELOG_NORMAL_ENTITY = "createElogNormalEntity：";
    private static final String TAG = "PLVElogHelper";
    private static HashMap<String, Constructor> modulesClass = new HashMap<>();
    public static final boolean muteElogRetryFunction = false;

    public static <T extends PLVStatisticsBase> T createElogNormalEntity(Class<T> cls, String str, PLVLogFileBase pLVLogFileBase, String... strArr) throws NoSuchMethodException, SecurityException {
        try {
            Constructor classConstructor = getClassConstructor(cls, strArr);
            if (classConstructor == null) {
                return null;
            }
            if (strArr.length <= 0) {
                return (T) classConstructor.newInstance(pLVLogFileBase, str);
            }
            Object[] objArr = new Object[strArr.length + 2];
            objArr[0] = pLVLogFileBase;
            objArr[1] = str;
            int length = strArr.length + 2;
            for (int i2 = 2; i2 < length; i2++) {
                objArr[i2] = strArr[i2 - 2];
            }
            return (T) classConstructor.newInstance(objArr);
        } catch (IllegalAccessException e2) {
            PLVCommonLog.e(TAG, CREATE_ELOG_NORMAL_ENTITY + e2.getMessage());
            return null;
        } catch (InstantiationException e3) {
            PLVCommonLog.e(TAG, CREATE_ELOG_NORMAL_ENTITY + e3.getMessage());
            return null;
        } catch (InvocationTargetException e4) {
            PLVCommonLog.e(TAG, CREATE_ELOG_NORMAL_ENTITY + e4.getMessage());
            return null;
        }
    }

    private static <T extends PLVStatisticsBase> Constructor getClassConstructor(Class<T> cls, String... strArr) throws NoSuchMethodException, SecurityException {
        Constructor constructor = modulesClass.get(cls.getName() + strArr.length);
        if (constructor != null) {
            return constructor;
        }
        if (cls.getConstructors().length <= 0) {
            return getClassConstructor(cls.getSuperclass(), strArr);
        }
        try {
            if (strArr.length <= 0) {
                Constructor<T> declaredConstructor = cls.getDeclaredConstructor(PLVLogFileBase.class, String.class);
                modulesClass.put(cls.getName() + strArr.length, declaredConstructor);
                return declaredConstructor;
            }
            Class<?>[] clsArr = new Class[strArr.length + 2];
            clsArr[0] = PLVLogFileBase.class;
            clsArr[1] = String.class;
            int length = strArr.length + 2;
            for (int i2 = 2; i2 < length; i2++) {
                clsArr[i2] = strArr[i2 - 2].getClass();
            }
            Constructor<T> declaredConstructor2 = cls.getDeclaredConstructor(clsArr);
            modulesClass.put(cls.getName() + strArr.length, declaredConstructor2);
            return declaredConstructor2;
        } catch (NoSuchMethodException e2) {
            PLVCommonLog.e(TAG, "getClassConstructor:" + e2.getMessage());
            try {
                return strArr.length > 0 ? cls.getDeclaredConstructor(PLVLogFileBase.class, String.class, strArr.getClass()) : cls.getDeclaredConstructor(PLVLogFileBase.class, String.class);
            } catch (NoSuchMethodException e3) {
                PLVCommonLog.e(TAG, "getClassConstructor:" + e3.getMessage());
                return null;
            }
        }
    }

    public static <T extends PLVErrorCodeInfoBase> T getMessageInfo(Class<T> cls, int i2) throws NoSuchMethodException, SecurityException {
        try {
            Constructor declaredConstructor = modulesClass.get(cls.getName());
            if (declaredConstructor == null) {
                declaredConstructor = cls.getDeclaredConstructor(Integer.TYPE);
                modulesClass.put(cls.getName(), declaredConstructor);
            }
            return declaredConstructor.newInstance(Integer.valueOf(i2));
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException(e4);
        } catch (InvocationTargetException e5) {
            throw new RuntimeException(e5);
        }
    }

    public static <T extends PLVStatisticsBase> T createElogNormalEntity(Class<T> cls, String str, String str2, String... strArr) {
        return (T) createElogNormalEntity(cls, str, new PLVLogFileBase(str2), strArr);
    }
}
