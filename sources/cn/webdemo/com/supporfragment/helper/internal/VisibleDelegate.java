package cn.webdemo.com.supporfragment.helper.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentationMagician;
import cn.webdemo.com.supporfragment.ISupportFragment;
import java.util.List;

/* loaded from: classes.dex */
public class VisibleDelegate {
    private static final String FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE = "fragmentation_compat_replace";
    private static final String FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE = "fragmentation_invisible_when_leave";
    private Fragment mFragment;
    private Handler mHandler;
    private boolean mInvisibleWhenLeave;
    private boolean mIsSupportVisible;
    private Bundle mSaveInstanceState;
    private ISupportFragment mSupportF;
    private boolean mNeedDispatch = true;
    private boolean mIsFirstVisible = true;
    private boolean mFirstCreateViewCompatReplace = true;

    /* JADX WARN: Multi-variable type inference failed */
    public VisibleDelegate(ISupportFragment iSupportFragment) {
        this.mSupportF = iSupportFragment;
        this.mFragment = (Fragment) iSupportFragment;
    }

    private boolean checkAddState() {
        if (this.mFragment.isAdded()) {
            return false;
        }
        this.mIsSupportVisible = !this.mIsSupportVisible;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void dispatchChild(boolean z2) {
        List<Fragment> activeFragments;
        if (!this.mNeedDispatch) {
            this.mNeedDispatch = true;
            return;
        }
        if (checkAddState() || (activeFragments = FragmentationMagician.getActiveFragments(this.mFragment.getChildFragmentManager())) == null) {
            return;
        }
        for (Fragment fragment : activeFragments) {
            if ((fragment instanceof ISupportFragment) && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                ((ISupportFragment) fragment).getSupportDelegate().getVisibleDelegate().dispatchSupportVisible(z2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSupportVisible(boolean z2) {
        if (z2 && isParentInvisible()) {
            return;
        }
        if (this.mIsSupportVisible == z2) {
            this.mNeedDispatch = true;
            return;
        }
        this.mIsSupportVisible = z2;
        if (!z2) {
            dispatchChild(false);
            this.mSupportF.onSupportInvisible();
        } else {
            if (checkAddState()) {
                return;
            }
            this.mSupportF.onSupportVisible();
            if (this.mIsFirstVisible) {
                this.mIsFirstVisible = false;
                this.mSupportF.onLazyInitView(this.mSaveInstanceState);
            }
            dispatchChild(true);
        }
    }

    private void enqueueDispatchVisible() {
        getHandler().post(new Runnable() { // from class: cn.webdemo.com.supporfragment.helper.internal.VisibleDelegate.1
            @Override // java.lang.Runnable
            public void run() {
                VisibleDelegate.this.dispatchSupportVisible(true);
            }
        });
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    private boolean isFragmentVisible(Fragment fragment) {
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }

    private boolean isParentInvisible() {
        ISupportFragment iSupportFragment = (ISupportFragment) this.mFragment.getParentFragment();
        return (iSupportFragment == null || iSupportFragment.isSupportVisible()) ? false : true;
    }

    private void safeDispatchUserVisibleHint(boolean z2) {
        if (!this.mIsFirstVisible) {
            dispatchSupportVisible(z2);
        } else if (z2) {
            enqueueDispatchVisible();
        }
    }

    public boolean isSupportVisible() {
        return this.mIsSupportVisible;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        if (this.mFirstCreateViewCompatReplace || this.mFragment.getTag() == null || !this.mFragment.getTag().startsWith("android:switcher:")) {
            if (this.mFirstCreateViewCompatReplace) {
                this.mFirstCreateViewCompatReplace = false;
            }
            if (this.mInvisibleWhenLeave || this.mFragment.isHidden() || !this.mFragment.getUserVisibleHint()) {
                return;
            }
            if ((this.mFragment.getParentFragment() == null || !isFragmentVisible(this.mFragment.getParentFragment())) && this.mFragment.getParentFragment() != null) {
                return;
            }
            this.mNeedDispatch = false;
            safeDispatchUserVisibleHint(true);
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        if (bundle != null) {
            this.mSaveInstanceState = bundle;
            this.mInvisibleWhenLeave = bundle.getBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE);
            this.mFirstCreateViewCompatReplace = bundle.getBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE);
        }
    }

    public void onDestroyView() {
        this.mIsFirstVisible = true;
    }

    public void onHiddenChanged(boolean z2) {
        if (z2) {
            safeDispatchUserVisibleHint(false);
        } else {
            enqueueDispatchVisible();
        }
    }

    public void onPause() {
        if (!this.mIsSupportVisible || !isFragmentVisible(this.mFragment)) {
            this.mInvisibleWhenLeave = true;
            return;
        }
        this.mNeedDispatch = false;
        this.mInvisibleWhenLeave = false;
        dispatchSupportVisible(false);
    }

    public void onResume() {
        if (this.mIsFirstVisible || this.mIsSupportVisible || this.mInvisibleWhenLeave || !isFragmentVisible(this.mFragment)) {
            return;
        }
        this.mNeedDispatch = false;
        dispatchSupportVisible(true);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE, this.mInvisibleWhenLeave);
        bundle.putBoolean(FRAGMENTATION_STATE_SAVE_COMPAT_REPLACE, this.mFirstCreateViewCompatReplace);
    }

    public void setUserVisibleHint(boolean z2) {
        if (this.mFragment.isResumed() || (!this.mFragment.isAdded() && z2)) {
            boolean z3 = this.mIsSupportVisible;
            if (!z3 && z2) {
                safeDispatchUserVisibleHint(true);
            } else {
                if (!z3 || z2) {
                    return;
                }
                dispatchSupportVisible(false);
            }
        }
    }
}
