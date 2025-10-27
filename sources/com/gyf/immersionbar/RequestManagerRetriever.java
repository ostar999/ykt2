package com.gyf.immersionbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import cn.hutool.core.text.StrPool;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
class RequestManagerRetriever implements Handler.Callback {
    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_FRAGMENT_MANAGER_REMOVE = 3;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER_REMOVE = 4;
    private final Handler mHandler;
    private final String mNotOnly;
    private final Map<FragmentManager, RequestBarManagerFragment> mPendingFragments;
    private final Map<String, RequestBarManagerFragment> mPendingRemoveFragments;
    private final Map<androidx.fragment.app.FragmentManager, SupportRequestBarManagerFragment> mPendingSupportFragments;
    private final Map<String, SupportRequestBarManagerFragment> mPendingSupportRemoveFragments;
    private final String mTag;

    public static class Holder {
        private static final RequestManagerRetriever INSTANCE = new RequestManagerRetriever();

        private Holder() {
        }
    }

    private static <T> void checkNotNull(@Nullable T t2, @NonNull String str) {
        if (t2 == null) {
            throw new NullPointerException(str);
        }
    }

    private RequestBarManagerFragment getFragment(FragmentManager fragmentManager, String str) {
        return getFragment(fragmentManager, str, false);
    }

    public static RequestManagerRetriever getInstance() {
        return Holder.INSTANCE;
    }

    private SupportRequestBarManagerFragment getSupportFragment(androidx.fragment.app.FragmentManager fragmentManager, String str) {
        return getSupportFragment(fragmentManager, str, false);
    }

    public void destroy(Fragment fragment, boolean z2) {
        if (fragment == null) {
            return;
        }
        String str = this.mTag + fragment.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(fragment) + ".tag.notOnly.";
        }
        getSupportFragment(fragment.getChildFragmentManager(), str, true);
    }

    public ImmersionBar get(Activity activity, boolean z2) {
        checkNotNull(activity, "activity is null");
        String str = this.mTag + activity.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(activity) + ".tag.notOnly.";
        }
        return activity instanceof FragmentActivity ? getSupportFragment(((FragmentActivity) activity).getSupportFragmentManager(), str).get(activity) : getFragment(activity.getFragmentManager(), str).get(activity);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            this.mPendingFragments.remove((FragmentManager) message.obj);
            return true;
        }
        if (i2 == 2) {
            this.mPendingSupportFragments.remove((androidx.fragment.app.FragmentManager) message.obj);
            return true;
        }
        if (i2 == 3) {
            this.mPendingRemoveFragments.remove((String) message.obj);
            return true;
        }
        if (i2 != 4) {
            return false;
        }
        this.mPendingSupportRemoveFragments.remove((String) message.obj);
        return true;
    }

    private RequestManagerRetriever() {
        this.mTag = ImmersionBar.class.getName() + StrPool.DOT;
        this.mNotOnly = ".tag.notOnly.";
        this.mPendingFragments = new HashMap();
        this.mPendingSupportFragments = new HashMap();
        this.mPendingRemoveFragments = new HashMap();
        this.mPendingSupportRemoveFragments = new HashMap();
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }

    private RequestBarManagerFragment getFragment(FragmentManager fragmentManager, String str, boolean z2) {
        RequestBarManagerFragment requestBarManagerFragment = (RequestBarManagerFragment) fragmentManager.findFragmentByTag(str);
        if (requestBarManagerFragment == null && (requestBarManagerFragment = this.mPendingFragments.get(fragmentManager)) == null) {
            if (z2) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 26) {
                for (android.app.Fragment fragment : fragmentManager.getFragments()) {
                    if (fragment instanceof RequestBarManagerFragment) {
                        String tag = fragment.getTag();
                        if (tag == null) {
                            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                        } else if (tag.contains(".tag.notOnly.")) {
                            fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                        }
                    }
                }
            }
            requestBarManagerFragment = new RequestBarManagerFragment();
            this.mPendingFragments.put(fragmentManager, requestBarManagerFragment);
            fragmentManager.beginTransaction().add(requestBarManagerFragment, str).commitAllowingStateLoss();
            this.mHandler.obtainMessage(1, fragmentManager).sendToTarget();
        }
        if (!z2) {
            return requestBarManagerFragment;
        }
        if (this.mPendingRemoveFragments.get(str) == null) {
            this.mPendingRemoveFragments.put(str, requestBarManagerFragment);
            fragmentManager.beginTransaction().remove(requestBarManagerFragment).commitAllowingStateLoss();
            this.mHandler.obtainMessage(3, str).sendToTarget();
        }
        return null;
    }

    private SupportRequestBarManagerFragment getSupportFragment(androidx.fragment.app.FragmentManager fragmentManager, String str, boolean z2) {
        SupportRequestBarManagerFragment supportRequestBarManagerFragment = (SupportRequestBarManagerFragment) fragmentManager.findFragmentByTag(str);
        if (supportRequestBarManagerFragment == null && (supportRequestBarManagerFragment = this.mPendingSupportFragments.get(fragmentManager)) == null) {
            if (z2) {
                return null;
            }
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment instanceof SupportRequestBarManagerFragment) {
                    String tag = fragment.getTag();
                    if (tag == null) {
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    } else if (tag.contains(".tag.notOnly.")) {
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    }
                }
            }
            supportRequestBarManagerFragment = new SupportRequestBarManagerFragment();
            this.mPendingSupportFragments.put(fragmentManager, supportRequestBarManagerFragment);
            fragmentManager.beginTransaction().add(supportRequestBarManagerFragment, str).commitAllowingStateLoss();
            this.mHandler.obtainMessage(2, fragmentManager).sendToTarget();
        }
        if (!z2) {
            return supportRequestBarManagerFragment;
        }
        if (this.mPendingSupportRemoveFragments.get(str) == null) {
            this.mPendingSupportRemoveFragments.put(str, supportRequestBarManagerFragment);
            fragmentManager.beginTransaction().remove(supportRequestBarManagerFragment).commitAllowingStateLoss();
            this.mHandler.obtainMessage(4, str).sendToTarget();
        }
        return null;
    }

    @RequiresApi(api = 17)
    public void destroy(android.app.Fragment fragment, boolean z2) {
        if (fragment == null) {
            return;
        }
        String str = this.mTag + fragment.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(fragment) + ".tag.notOnly.";
        }
        getFragment(fragment.getChildFragmentManager(), str, true);
    }

    public ImmersionBar get(Fragment fragment, boolean z2) {
        checkNotNull(fragment, "fragment is null");
        checkNotNull(fragment.getActivity(), "fragment.getActivity() is null");
        if (fragment instanceof DialogFragment) {
            checkNotNull(((DialogFragment) fragment).getDialog(), "fragment.getDialog() is null");
        }
        String str = this.mTag + fragment.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(fragment) + ".tag.notOnly.";
        }
        return getSupportFragment(fragment.getChildFragmentManager(), str).get(fragment);
    }

    public void destroy(Activity activity, Dialog dialog, boolean z2) {
        if (activity == null || dialog == null) {
            return;
        }
        String str = this.mTag + dialog.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(dialog) + ".tag.notOnly.";
        }
        if (activity instanceof FragmentActivity) {
            getSupportFragment(((FragmentActivity) activity).getSupportFragmentManager(), str, true);
        } else {
            getFragment(activity.getFragmentManager(), str, true);
        }
    }

    @RequiresApi(api = 17)
    public ImmersionBar get(android.app.Fragment fragment, boolean z2) {
        checkNotNull(fragment, "fragment is null");
        checkNotNull(fragment.getActivity(), "fragment.getActivity() is null");
        if (fragment instanceof android.app.DialogFragment) {
            checkNotNull(((android.app.DialogFragment) fragment).getDialog(), "fragment.getDialog() is null");
        }
        String str = this.mTag + fragment.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(fragment) + ".tag.notOnly.";
        }
        return getFragment(fragment.getChildFragmentManager(), str).get(fragment);
    }

    public ImmersionBar get(Activity activity, Dialog dialog, boolean z2) {
        checkNotNull(activity, "activity is null");
        checkNotNull(dialog, "dialog is null");
        String str = this.mTag + dialog.getClass().getName();
        if (!z2) {
            str = str + System.identityHashCode(dialog) + ".tag.notOnly.";
        }
        if (activity instanceof FragmentActivity) {
            return getSupportFragment(((FragmentActivity) activity).getSupportFragmentManager(), str).get(activity, dialog);
        }
        return getFragment(activity.getFragmentManager(), str).get(activity, dialog);
    }
}
