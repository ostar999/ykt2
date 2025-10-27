package cn.webdemo.com.supporfragment;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.activity.result.ActivityResultCaller;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentationMagician;
import java.util.List;

/* loaded from: classes.dex */
public class SupportHelper {
    private static final long SHOW_SPACE = 200;

    private SupportHelper() {
    }

    public static <T extends ISupportFragment> T findBackStackFragment(Class<T> cls, String str, FragmentManager fragmentManager) {
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (str == null) {
            str = cls.getName();
        }
        for (int i2 = backStackEntryCount - 1; i2 >= 0; i2--) {
            FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(i2);
            if (str.equals(backStackEntryAt.getName())) {
                ActivityResultCaller activityResultCallerFindFragmentByTag = fragmentManager.findFragmentByTag(backStackEntryAt.getName());
                if (activityResultCallerFindFragmentByTag instanceof ISupportFragment) {
                    return (T) activityResultCallerFindFragmentByTag;
                }
            }
        }
        return null;
    }

    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, Class<T> cls) {
        return (T) findStackFragment(cls, null, fragmentManager);
    }

    public static <T extends ISupportFragment> T findStackFragment(Class<T> cls, String str, FragmentManager fragmentManager) {
        Object obj = null;
        if (str == null) {
            List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
            if (activeFragments != null) {
                int size = activeFragments.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    Fragment fragment = activeFragments.get(size);
                    if ((fragment instanceof ISupportFragment) && fragment.getClass().getName().equals(cls.getName())) {
                        obj = fragment;
                        break;
                    }
                    size--;
                }
            } else {
                return null;
            }
        } else {
            Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(str);
            if (fragmentFindFragmentByTag == null) {
                return null;
            }
            obj = fragmentFindFragmentByTag;
        }
        return (T) obj;
    }

    public static ISupportFragment getActiveFragment(FragmentManager fragmentManager) {
        return getActiveFragment(fragmentManager, null);
    }

    public static ISupportFragment getBackStackTopFragment(FragmentManager fragmentManager) {
        return getBackStackTopFragment(fragmentManager, 0);
    }

    public static ISupportFragment getPreFragment(Fragment fragment) {
        List<Fragment> activeFragments;
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager == null || (activeFragments = FragmentationMagician.getActiveFragments(fragmentManager)) == null) {
            return null;
        }
        for (int iIndexOf = activeFragments.indexOf(fragment) - 1; iIndexOf >= 0; iIndexOf--) {
            ActivityResultCaller activityResultCaller = (Fragment) activeFragments.get(iIndexOf);
            if (activityResultCaller instanceof ISupportFragment) {
                return (ISupportFragment) activityResultCaller;
            }
        }
        return null;
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager) {
        return getTopFragment(fragmentManager, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
    
        r3 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<androidx.fragment.app.Fragment> getWillPopFragments(androidx.fragment.app.FragmentManager r6, java.lang.String r7, boolean r8) {
        /*
            androidx.fragment.app.Fragment r7 = r6.findFragmentByTag(r7)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r6 = androidx.fragment.app.FragmentationMagician.getActiveFragments(r6)
            if (r6 != 0) goto L10
            return r0
        L10:
            int r1 = r6.size()
            int r2 = r1 + (-1)
            r3 = r2
        L17:
            r4 = -1
            if (r3 < 0) goto L2b
            java.lang.Object r5 = r6.get(r3)
            if (r7 != r5) goto L28
            if (r8 == 0) goto L23
            goto L2c
        L23:
            int r3 = r3 + 1
            if (r3 >= r1) goto L2b
            goto L2c
        L28:
            int r3 = r3 + (-1)
            goto L17
        L2b:
            r3 = r4
        L2c:
            if (r3 != r4) goto L2f
            return r0
        L2f:
            if (r2 < r3) goto L45
            java.lang.Object r7 = r6.get(r2)
            androidx.fragment.app.Fragment r7 = (androidx.fragment.app.Fragment) r7
            if (r7 == 0) goto L42
            android.view.View r8 = r7.getView()
            if (r8 == 0) goto L42
            r0.add(r7)
        L42:
            int r2 = r2 + (-1)
            goto L2f
        L45:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.webdemo.com.supporfragment.SupportHelper.getWillPopFragments(androidx.fragment.app.FragmentManager, java.lang.String, boolean):java.util.List");
    }

    public static void hideSoftInput(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void logFragmentStackHierarchy(ISupportActivity iSupportActivity, String str) {
        iSupportActivity.getSupportDelegate().logFragmentStackHierarchy(str);
    }

    public static void showFragmentStackHierarchyView(ISupportActivity iSupportActivity) {
        iSupportActivity.getSupportDelegate().showFragmentStackHierarchyView();
    }

    public static void showSoftInput(final View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        final InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        view.requestFocus();
        view.postDelayed(new Runnable() { // from class: cn.webdemo.com.supporfragment.SupportHelper.1
            @Override // java.lang.Runnable
            public void run() {
                inputMethodManager.showSoftInput(view, 2);
            }
        }, 200L);
    }

    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, String str) {
        return (T) findStackFragment(null, str, fragmentManager);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static ISupportFragment getActiveFragment(FragmentManager fragmentManager, ISupportFragment iSupportFragment) {
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
        if (activeFragments == null) {
            return iSupportFragment;
        }
        for (int size = activeFragments.size() - 1; size >= 0; size--) {
            Fragment fragment = activeFragments.get(size);
            if ((fragment instanceof ISupportFragment) && fragment.isResumed() && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                return getActiveFragment(fragment.getChildFragmentManager(), (ISupportFragment) fragment);
            }
        }
        return iSupportFragment;
    }

    public static ISupportFragment getBackStackTopFragment(FragmentManager fragmentManager, int i2) {
        for (int backStackEntryCount = fragmentManager.getBackStackEntryCount() - 1; backStackEntryCount >= 0; backStackEntryCount--) {
            ActivityResultCaller activityResultCallerFindFragmentByTag = fragmentManager.findFragmentByTag(fragmentManager.getBackStackEntryAt(backStackEntryCount).getName());
            if (activityResultCallerFindFragmentByTag instanceof ISupportFragment) {
                ISupportFragment iSupportFragment = (ISupportFragment) activityResultCallerFindFragmentByTag;
                if (i2 == 0 || i2 == iSupportFragment.getSupportDelegate().mContainerId) {
                    return iSupportFragment;
                }
            }
        }
        return null;
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager, int i2) {
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
        if (activeFragments == null) {
            return null;
        }
        for (int size = activeFragments.size() - 1; size >= 0; size--) {
            ActivityResultCaller activityResultCaller = (Fragment) activeFragments.get(size);
            if (activityResultCaller instanceof ISupportFragment) {
                ISupportFragment iSupportFragment = (ISupportFragment) activityResultCaller;
                if (i2 == 0 || i2 == iSupportFragment.getSupportDelegate().mContainerId) {
                    return iSupportFragment;
                }
            }
        }
        return null;
    }
}
