package androidx.fragment.app;

import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class FragmentationMagician {
    private static boolean sSupportGreaterThan27dot1dot0 = false;
    private static boolean sSupportLessThan25dot4 = false;

    static {
        for (Field field : FragmentManagerImpl.class.getDeclaredFields()) {
            if (field.getName().equals("mStopped")) {
                sSupportGreaterThan27dot1dot0 = true;
                return;
            } else {
                if (field.getName().equals("mAvailIndices")) {
                    sSupportLessThan25dot4 = true;
                    return;
                }
            }
        }
    }

    private static void compatRunAction(FragmentManagerImpl fragmentManagerImpl, Runnable runnable) {
        if (sSupportGreaterThan27dot1dot0) {
            runnable.run();
        } else {
            runnable.run();
        }
    }

    public static void executePendingTransactionsAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() { // from class: androidx.fragment.app.FragmentationMagician.4
            @Override // java.lang.Runnable
            public void run() {
                fragmentManager.executePendingTransactions();
            }
        });
    }

    public static List<Fragment> getActiveFragments(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return Collections.EMPTY_LIST;
        }
        if (sSupportLessThan25dot4) {
            return fragmentManager.getFragments();
        }
        try {
            return ((FragmentManagerImpl) fragmentManager).getActiveFragments();
        } catch (Exception e2) {
            e2.printStackTrace();
            return fragmentManager.getFragments();
        }
    }

    private static List<Fragment> getActiveList(SparseArray<Fragment> sparseArray) {
        if (sparseArray == null) {
            return Collections.EMPTY_LIST;
        }
        int size = sparseArray.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(sparseArray.valueAt(i2));
        }
        return arrayList;
    }

    private static Object getValue(Object obj, String str) throws Exception {
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    private static void hookStateSaved(FragmentManager fragmentManager, Runnable runnable) {
        if (fragmentManager instanceof FragmentManagerImpl) {
            FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
            if (isStateSaved(fragmentManager)) {
                compatRunAction(fragmentManagerImpl, runnable);
            } else {
                runnable.run();
            }
        }
    }

    public static boolean isExecutingActions(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return false;
        }
        try {
            return ((FragmentManagerImpl) fragmentManager).executePendingTransactions();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isStateSaved(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return false;
        }
        try {
            return ((FragmentManagerImpl) fragmentManager).isStateSaved();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isSupportLessThan25dot4() {
        return sSupportLessThan25dot4;
    }

    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() { // from class: androidx.fragment.app.FragmentationMagician.1
            @Override // java.lang.Runnable
            public void run() {
                fragmentManager.popBackStack();
            }
        });
    }

    public static void popBackStackImmediateAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() { // from class: androidx.fragment.app.FragmentationMagician.2
            @Override // java.lang.Runnable
            public void run() {
                fragmentManager.popBackStackImmediate();
            }
        });
    }

    public static void reorderIndices(FragmentManager fragmentManager) {
        if (sSupportLessThan25dot4 && (fragmentManager instanceof FragmentManagerImpl)) {
            try {
                Object value = getValue((FragmentManagerImpl) fragmentManager, "mAvailIndices");
                if (value == null) {
                    return;
                }
                ArrayList arrayList = (ArrayList) value;
                if (arrayList.size() > 1) {
                    Collections.sort(arrayList, Collections.reverseOrder());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager, final String str, final int i2) {
        hookStateSaved(fragmentManager, new Runnable() { // from class: androidx.fragment.app.FragmentationMagician.3
            @Override // java.lang.Runnable
            public void run() {
                fragmentManager.popBackStack(str, i2);
            }
        });
    }
}
