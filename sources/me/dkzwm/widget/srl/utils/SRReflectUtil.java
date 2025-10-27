package me.dkzwm.widget.srl.utils;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.view.View;
import android.widget.AbsListView;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes9.dex */
public class SRReflectUtil {
    private static Class sFlingRunnableClass;
    private static Constructor sFlingRunnableConstructor;
    private static Field sFlingRunnableField;
    private static Method sFlingRunnableStartMethod;
    private static Method sGetInverseMatrixMethod;
    private static Method sHasIdentityMatrixMethod;
    private static Method sReportScrollStateChangeMethod;
    private static Method sTrackMotionScrollMethod;

    @SuppressLint({"PrivateApi"})
    public static void compatMapTheInverseMatrix(View view, float[] fArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Matrix matrix;
        try {
            if (sHasIdentityMatrixMethod == null) {
                Method declaredMethod = View.class.getDeclaredMethod("hasIdentityMatrix", new Class[0]);
                sHasIdentityMatrixMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            Method method = sHasIdentityMatrixMethod;
            if (method != null) {
                Object objInvoke = method.invoke(view, new Object[0]);
                if (!(objInvoke instanceof Boolean) || ((Boolean) objInvoke).booleanValue()) {
                    return;
                }
                if (sGetInverseMatrixMethod == null) {
                    Method declaredMethod2 = View.class.getDeclaredMethod("getInverseMatrix", new Class[0]);
                    sGetInverseMatrixMethod = declaredMethod2;
                    declaredMethod2.setAccessible(true);
                }
                Method method2 = sGetInverseMatrixMethod;
                if (method2 == null || (matrix = (Matrix) method2.invoke(view, new Object[0])) == null) {
                    return;
                }
                matrix.mapPoints(fArr);
            }
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"PrivateApi"})
    public static void compatOlderAbsListViewFling(AbsListView absListView, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (sFlingRunnableClass == null) {
            Class<?>[] declaredClasses = AbsListView.class.getDeclaredClasses();
            int length = declaredClasses.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                Class<?> cls = declaredClasses[i3];
                if (cls.getCanonicalName().endsWith("FlingRunnable")) {
                    sFlingRunnableClass = cls;
                    break;
                }
                i3++;
            }
        }
        if (sFlingRunnableClass == null) {
            return;
        }
        try {
            if (sFlingRunnableField == null) {
                Field declaredField = AbsListView.class.getDeclaredField("mFlingRunnable");
                sFlingRunnableField = declaredField;
                if (declaredField != null) {
                    declaredField.setAccessible(true);
                }
            }
            Field field = sFlingRunnableField;
            if (field == null) {
                return;
            }
            Object objNewInstance = field.get(absListView);
            if (objNewInstance == null) {
                if (sFlingRunnableConstructor == null) {
                    Constructor declaredConstructor = sFlingRunnableClass.getDeclaredConstructor(AbsListView.class);
                    sFlingRunnableConstructor = declaredConstructor;
                    if (declaredConstructor != null) {
                        declaredConstructor.setAccessible(true);
                    }
                }
                Constructor constructor = sFlingRunnableConstructor;
                if (constructor == null) {
                    return;
                } else {
                    objNewInstance = constructor.newInstance(absListView);
                }
            }
            sFlingRunnableField.set(absListView, objNewInstance);
            if (sReportScrollStateChangeMethod == null) {
                Method declaredMethod = AbsListView.class.getDeclaredMethod("reportScrollStateChange", Integer.TYPE);
                sReportScrollStateChangeMethod = declaredMethod;
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                }
            }
            Method method = sReportScrollStateChangeMethod;
            if (method == null) {
                return;
            }
            method.invoke(absListView, 2);
            if (sFlingRunnableStartMethod == null) {
                Method declaredMethod2 = sFlingRunnableClass.getDeclaredMethod("start", Integer.TYPE);
                sFlingRunnableStartMethod = declaredMethod2;
                if (declaredMethod2 != null) {
                    declaredMethod2.setAccessible(true);
                }
            }
            Method method2 = sFlingRunnableStartMethod;
            if (method2 == null) {
                return;
            }
            method2.invoke(objNewInstance, Integer.valueOf(i2));
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"PrivateApi"})
    public static void compatOlderAbsListViewScrollListBy(AbsListView absListView, int i2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            if (sTrackMotionScrollMethod == null) {
                Class cls = Integer.TYPE;
                Method declaredMethod = AbsListView.class.getDeclaredMethod("trackMotionScroll", cls, cls);
                sTrackMotionScrollMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            Method method = sTrackMotionScrollMethod;
            if (method != null) {
                int i3 = -i2;
                method.invoke(absListView, Integer.valueOf(i3), Integer.valueOf(i3));
            }
        } catch (Exception unused) {
        }
    }
}
