package com.plv.thirdpart.blankj.utilcode.util;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public final class FragmentUtils {
    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final int TYPE_ADD_FRAGMENT = 1;
    private static final int TYPE_HIDE_FRAGMENT = 4;
    private static final int TYPE_REMOVE_FRAGMENT = 32;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 64;
    private static final int TYPE_REPLACE_FRAGMENT = 16;
    private static final int TYPE_SHOW_FRAGMENT = 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 8;

    public static class Args {
        int id;
        boolean isAddStack;
        boolean isHide;

        private Args(int i2, boolean z2, boolean z3) {
            this.id = i2;
            this.isHide = z2;
            this.isAddStack = z3;
        }
    }

    public static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;

        public FragmentNode(Fragment fragment, List<FragmentNode> list) {
            this.fragment = fragment;
            this.next = list;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.fragment.getClass().getSimpleName());
            sb.append("->");
            List<FragmentNode> list = this.next;
            sb.append((list == null || list.isEmpty()) ? "no child" : this.next.toString());
            return sb.toString();
        }
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2) {
        add(fragmentManager, fragment, i2, false, false);
    }

    private static void addAnim(FragmentTransaction fragmentTransaction, int i2, int i3, int i4, int i5) {
        fragmentTransaction.setCustomAnimations(i2, i3, i4, i5);
    }

    private static void addSharedElement(FragmentTransaction fragmentTransaction, View... viewArr) {
        for (View view : viewArr) {
            fragmentTransaction.addSharedElement(view, view.getTransitionName());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean dispatchBackPress(@NonNull Fragment fragment) {
        return fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint() && (fragment instanceof OnBackClickListener) && ((OnBackClickListener) fragment).onBackClick();
    }

    public static Fragment findFragment(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> cls) {
        return fragmentManager.findFragmentByTag(cls.getName());
    }

    public static List<FragmentNode> getAllFragments(@NonNull FragmentManager fragmentManager) {
        return getAllFragments(fragmentManager, new ArrayList());
    }

    public static List<FragmentNode> getAllFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        return getAllFragmentsInStack(fragmentManager, new ArrayList());
    }

    private static Args getArgs(Fragment fragment) {
        Bundle arguments = fragment.getArguments();
        return new Args(arguments.getInt(ARGS_ID, fragment.getId()), arguments.getBoolean(ARGS_IS_HIDE), arguments.getBoolean(ARGS_IS_ADD_STACK));
    }

    public static List<Fragment> getFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        return (fragments == null || fragments.isEmpty()) ? Collections.emptyList() : fragments;
    }

    public static List<Fragment> getFragmentsInStack(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        ArrayList arrayList = new ArrayList();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                arrayList.add(fragment);
            }
        }
        return arrayList;
    }

    public static String getSimpleName(Fragment fragment) {
        return fragment == null ? "null" : fragment.getClass().getSimpleName();
    }

    public static Fragment getTop(@NonNull FragmentManager fragmentManager) {
        return getTopIsInStack(fragmentManager, false);
    }

    public static Fragment getTopInStack(@NonNull FragmentManager fragmentManager) {
        return getTopIsInStack(fragmentManager, true);
    }

    private static Fragment getTopIsInStack(@NonNull FragmentManager fragmentManager, boolean z2) {
        List<Fragment> fragments = getFragments(fragmentManager);
        for (int size = fragments.size() - 1; size >= 0; size--) {
            Fragment fragment = fragments.get(size);
            if (fragment != null && (!z2 || fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK))) {
                return fragment;
            }
        }
        return null;
    }

    public static Fragment getTopShow(@NonNull FragmentManager fragmentManager) {
        return getTopShowIsInStack(fragmentManager, false);
    }

    public static Fragment getTopShowInStack(@NonNull FragmentManager fragmentManager) {
        return getTopShowIsInStack(fragmentManager, true);
    }

    private static Fragment getTopShowIsInStack(@NonNull FragmentManager fragmentManager, boolean z2) {
        List<Fragment> fragments = getFragments(fragmentManager);
        for (int size = fragments.size() - 1; size >= 0; size--) {
            Fragment fragment = fragments.get(size);
            if (fragment != null && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint() && (!z2 || fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK))) {
                return fragment;
            }
        }
        return null;
    }

    public static void hide(@NonNull Fragment fragment) {
        putArgs(fragment, true);
        operateNoAnim(fragment.getFragmentManager(), 4, null, fragment);
    }

    private static void operate(int i2, FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, Fragment fragment, Fragment... fragmentArr) {
        if (fragment != null && fragment.isRemoving()) {
            Log.e("FragmentUtils", fragment.getClass().getName() + " is isRemoving");
            return;
        }
        int i3 = 0;
        if (i2 == 1) {
            int length = fragmentArr.length;
            while (i3 < length) {
                Fragment fragment2 = fragmentArr[i3];
                String name = fragment2.getClass().getName();
                Bundle arguments = fragment2.getArguments();
                Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(name);
                if (fragmentFindFragmentByTag != null && fragmentFindFragmentByTag.isAdded()) {
                    fragmentTransaction.remove(fragmentFindFragmentByTag);
                }
                fragmentTransaction.add(arguments.getInt(ARGS_ID), fragment2, name);
                if (arguments.getBoolean(ARGS_IS_HIDE)) {
                    fragmentTransaction.hide(fragment2);
                }
                if (arguments.getBoolean(ARGS_IS_ADD_STACK)) {
                    fragmentTransaction.addToBackStack(name);
                }
                i3++;
            }
        } else if (i2 == 2) {
            int length2 = fragmentArr.length;
            while (i3 < length2) {
                fragmentTransaction.show(fragmentArr[i3]);
                i3++;
            }
        } else if (i2 == 4) {
            int length3 = fragmentArr.length;
            while (i3 < length3) {
                fragmentTransaction.hide(fragmentArr[i3]);
                i3++;
            }
        } else if (i2 == 8) {
            fragmentTransaction.show(fragment);
            int length4 = fragmentArr.length;
            while (i3 < length4) {
                Fragment fragment3 = fragmentArr[i3];
                if (fragment3 != fragment) {
                    fragmentTransaction.hide(fragment3);
                }
                i3++;
            }
        } else if (i2 == 16) {
            String name2 = fragmentArr[0].getClass().getName();
            Bundle arguments2 = fragmentArr[0].getArguments();
            fragmentTransaction.replace(arguments2.getInt(ARGS_ID), fragmentArr[0], name2);
            if (arguments2.getBoolean(ARGS_IS_ADD_STACK)) {
                fragmentTransaction.addToBackStack(name2);
            }
        } else if (i2 == 32) {
            int length5 = fragmentArr.length;
            while (i3 < length5) {
                Fragment fragment4 = fragmentArr[i3];
                if (fragment4 != fragment) {
                    fragmentTransaction.remove(fragment4);
                }
                i3++;
            }
        } else if (i2 == 64) {
            int length6 = fragmentArr.length - 1;
            while (true) {
                if (length6 < 0) {
                    break;
                }
                Fragment fragment5 = fragmentArr[length6];
                if (fragment5 != fragmentArr[0]) {
                    fragmentTransaction.remove(fragment5);
                    length6--;
                } else if (fragment != null) {
                    fragmentTransaction.remove(fragment5);
                }
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private static void operateNoAnim(FragmentManager fragmentManager, int i2, Fragment fragment, Fragment... fragmentArr) {
        operate(i2, fragmentManager, fragmentManager.beginTransaction(), fragment, fragmentArr);
    }

    public static void pop(@NonNull FragmentManager fragmentManager) {
        pop(fragmentManager, true);
    }

    public static void popAll(@NonNull FragmentManager fragmentManager) {
        popAll(fragmentManager, true);
    }

    public static void popTo(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> cls, boolean z2) {
        popTo(fragmentManager, cls, z2, true);
    }

    private static void putArgs(Fragment fragment, Args args) {
        Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            fragment.setArguments(arguments);
        }
        arguments.putInt(ARGS_ID, args.id);
        arguments.putBoolean(ARGS_IS_HIDE, args.isHide);
        arguments.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    public static void remove(@NonNull Fragment fragment) {
        operateNoAnim(fragment.getFragmentManager(), 32, null, fragment);
    }

    public static void removeAll(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        operateNoAnim(fragmentManager, 32, null, (Fragment[]) fragments.toArray(new Fragment[fragments.size()]));
    }

    public static void removeTo(@NonNull Fragment fragment, boolean z2) {
        operateNoAnim(fragment.getFragmentManager(), 64, z2 ? fragment : null, fragment);
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2) {
        replace(fragment, fragment2, false);
    }

    public static void setBackground(@NonNull Fragment fragment, Drawable drawable) {
        ViewCompat.setBackground(fragment.getView(), drawable);
    }

    public static void setBackgroundColor(@NonNull Fragment fragment, @ColorInt int i2) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(i2);
        }
    }

    public static void setBackgroundResource(@NonNull Fragment fragment, @DrawableRes int i2) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(i2);
        }
    }

    public static void show(@NonNull Fragment fragment) {
        putArgs(fragment, false);
        operateNoAnim(fragment.getFragmentManager(), 2, null, fragment);
    }

    public static void showHide(int i2, @NonNull List<Fragment> list) {
        showHide(list.get(i2), list);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2) {
        add(fragmentManager, fragment, i2, z2, false);
    }

    private static List<FragmentNode> getAllFragments(@NonNull FragmentManager fragmentManager, List<FragmentNode> list) {
        List<Fragment> fragments = getFragments(fragmentManager);
        for (int size = fragments.size() - 1; size >= 0; size--) {
            Fragment fragment = fragments.get(size);
            if (fragment != null) {
                list.add(new FragmentNode(fragment, getAllFragments(fragment.getChildFragmentManager(), new ArrayList())));
            }
        }
        return list;
    }

    private static List<FragmentNode> getAllFragmentsInStack(@NonNull FragmentManager fragmentManager, List<FragmentNode> list) {
        List<Fragment> fragments = getFragments(fragmentManager);
        for (int size = fragments.size() - 1; size >= 0; size--) {
            Fragment fragment = fragments.get(size);
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                list.add(new FragmentNode(fragment, getAllFragmentsInStack(fragment.getChildFragmentManager(), new ArrayList())));
            }
        }
        return list;
    }

    public static void pop(@NonNull FragmentManager fragmentManager, boolean z2) {
        if (z2) {
            fragmentManager.popBackStackImmediate();
        } else {
            fragmentManager.popBackStack();
        }
    }

    public static void popAll(@NonNull FragmentManager fragmentManager, boolean z2) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            if (z2) {
                fragmentManager.popBackStackImmediate();
            } else {
                fragmentManager.popBackStack();
            }
        }
    }

    public static void popTo(@NonNull FragmentManager fragmentManager, Class<? extends Fragment> cls, boolean z2, boolean z3) {
        if (z3) {
            fragmentManager.popBackStackImmediate(cls.getName(), z2 ? 1 : 0);
        } else {
            fragmentManager.popBackStack(cls.getName(), z2 ? 1 : 0);
        }
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z2) {
        replace(fragment.getFragmentManager(), fragment2, getArgs(fragment).id, z2);
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull List<Fragment> list) {
        Iterator<Fragment> it = list.iterator();
        while (it.hasNext()) {
            Fragment next = it.next();
            putArgs(next, next != fragment);
        }
        operateNoAnim(fragment.getFragmentManager(), 8, fragment, (Fragment[]) list.toArray(new Fragment[list.size()]));
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, boolean z3) {
        putArgs(fragment, new Args(i2, z2, z3));
        operateNoAnim(fragmentManager, 1, null, fragment);
    }

    public static void hide(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        Iterator<Fragment> it = fragments.iterator();
        while (it.hasNext()) {
            putArgs(it.next(), true);
        }
        operateNoAnim(fragmentManager, 4, null, (Fragment[]) fragments.toArray(new Fragment[fragments.size()]));
    }

    public static void show(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        Iterator<Fragment> it = fragments.iterator();
        while (it.hasNext()) {
            putArgs(it.next(), false);
        }
        operateNoAnim(fragmentManager, 2, null, (Fragment[]) fragments.toArray(new Fragment[fragments.size()]));
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, @AnimRes int i2, @AnimRes int i3) {
        replace(fragment, fragment2, false, i2, i3, 0, 0);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, @AnimRes int i3, @AnimRes int i4) {
        add(fragmentManager, fragment, i2, false, i3, i4, 0, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean dispatchBackPress(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (fragments != null && !fragments.isEmpty()) {
            for (int size = fragments.size() - 1; size >= 0; size--) {
                Fragment fragment = fragments.get(size);
                if (fragment != 0 && fragment.isResumed() && fragment.isVisible() && fragment.getUserVisibleHint() && (fragment instanceof OnBackClickListener) && ((OnBackClickListener) fragment).onBackClick()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z2, @AnimRes int i2, @AnimRes int i3) {
        replace(fragment, fragment2, z2, i2, i3, 0, 0);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, @AnimRes int i3, @AnimRes int i4) {
        add(fragmentManager, fragment, i2, z2, i3, i4, 0, 0);
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, @AnimRes int i2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5) {
        replace(fragment, fragment2, false, i2, i3, i4, i5);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5, @AnimRes int i6) {
        add(fragmentManager, fragment, i2, false, i3, i4, i5, i6);
    }

    private static void putArgs(Fragment fragment, boolean z2) {
        Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            fragment.setArguments(arguments);
        }
        arguments.putBoolean(ARGS_IS_HIDE, z2);
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z2, @AnimRes int i2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5) {
        replace(fragment.getFragmentManager(), fragment2, getArgs(fragment).id, z2, i2, i3, i4, i5);
    }

    public static void showHide(int i2, @NonNull Fragment... fragmentArr) {
        showHide(fragmentArr[i2], fragmentArr);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5, @AnimRes int i6) {
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        putArgs(fragment, new Args(i2, false, z2));
        addAnim(fragmentTransactionBeginTransaction, i3, i4, i5, i6);
        operate(1, fragmentManager, fragmentTransactionBeginTransaction, null, fragment);
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull Fragment... fragmentArr) {
        int length = fragmentArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Fragment fragment2 = fragmentArr[i2];
            putArgs(fragment2, fragment2 != fragment);
        }
        operateNoAnim(fragment.getFragmentManager(), 8, fragment, fragmentArr);
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, View... viewArr) {
        replace(fragment, fragment2, false, viewArr);
    }

    public static void replace(@NonNull Fragment fragment, @NonNull Fragment fragment2, boolean z2, View... viewArr) {
        replace(fragment.getFragmentManager(), fragment2, getArgs(fragment).id, z2, viewArr);
    }

    public static void showHide(@NonNull Fragment fragment, @NonNull Fragment fragment2) {
        putArgs(fragment, false);
        putArgs(fragment2, true);
        operateNoAnim(fragment.getFragmentManager(), 8, fragment, fragment2);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, @NonNull View... viewArr) {
        add(fragmentManager, fragment, i2, false, viewArr);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2) {
        replace(fragmentManager, fragment, i2, false);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, @NonNull View... viewArr) {
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        putArgs(fragment, new Args(i2, false, z2));
        addSharedElement(fragmentTransactionBeginTransaction, viewArr);
        operate(1, fragmentManager, fragmentTransactionBeginTransaction, null, fragment);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2) {
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        putArgs(fragment, new Args(i2, false, z2));
        operate(16, fragmentManager, fragmentTransactionBeginTransaction, null, fragment);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, @AnimRes int i3, @AnimRes int i4) {
        replace(fragmentManager, fragment, i2, false, i3, i4, 0, 0);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> list, @IdRes int i2, int i3) {
        add(fragmentManager, (Fragment[]) list.toArray(new Fragment[list.size()]), i2, i3);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, @AnimRes int i3, @AnimRes int i4) {
        replace(fragmentManager, fragment, i2, z2, i3, i4, 0, 0);
    }

    public static void add(@NonNull FragmentManager fragmentManager, @NonNull Fragment[] fragmentArr, @IdRes int i2, int i3) {
        int length = fragmentArr.length;
        boolean z2 = false;
        int i4 = 0;
        while (true) {
            boolean z3 = true;
            if (i4 < length) {
                Fragment fragment = fragmentArr[i4];
                if (i3 == i4) {
                    z3 = false;
                }
                putArgs(fragment, new Args(i2, z3, z2));
                i4++;
            } else {
                operateNoAnim(fragmentManager, 1, null, fragmentArr);
                return;
            }
        }
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5, @AnimRes int i6) {
        replace(fragmentManager, fragment, i2, false, i3, i4, i5, i6);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5, @AnimRes int i6) {
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        putArgs(fragment, new Args(i2, false, z2));
        addAnim(fragmentTransactionBeginTransaction, i3, i4, i5, i6);
        operate(16, fragmentManager, fragmentTransactionBeginTransaction, null, fragment);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, View... viewArr) {
        replace(fragmentManager, fragment, i2, false, viewArr);
    }

    public static void replace(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @IdRes int i2, boolean z2, View... viewArr) {
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        putArgs(fragment, new Args(i2, false, z2));
        addSharedElement(fragmentTransactionBeginTransaction, viewArr);
        operate(16, fragmentManager, fragmentTransactionBeginTransaction, null, fragment);
    }
}
