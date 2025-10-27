package cn.webdemo.com.supporfragment;

import android.os.Bundle;
import android.view.MotionEvent;
import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentationMagician;
import cn.webdemo.com.supporfragment.ExtraTransaction;
import cn.webdemo.com.supporfragment.anim.DefaultVerticalAnimator;
import cn.webdemo.com.supporfragment.anim.FragmentAnimator;
import cn.webdemo.com.supporfragment.debug.DebugStackDelegate;
import cn.webdemo.com.supporfragment.helper.internal.AnimatorHelper;
import cn.webdemo.com.supporfragment.queue.Action;

/* loaded from: classes.dex */
public class SupportActivityDelegate {
    private FragmentActivity mActivity;
    private DebugStackDelegate mDebugStackDelegate;
    private FragmentAnimator mFragmentAnimator;
    private ISupportActivity mSupport;
    private TransactionDelegate mTransactionDelegate;
    boolean mPopMultipleNoAnim = false;
    boolean mFragmentClickable = true;
    private int mDefaultFragmentBackground = 0;

    /* JADX WARN: Multi-variable type inference failed */
    public SupportActivityDelegate(ISupportActivity iSupportActivity) {
        if (!(iSupportActivity instanceof FragmentActivity)) {
            throw new RuntimeException("Must extends FragmentActivity/AppCompatActivity");
        }
        this.mSupport = iSupportActivity;
        this.mActivity = (FragmentActivity) iSupportActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FragmentManager getSupportFragmentManager() {
        return this.mActivity.getSupportFragmentManager();
    }

    private ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return !this.mFragmentClickable;
    }

    public ExtraTransaction extraTransaction() {
        return new ExtraTransaction.ExtraTransactionImpl((FragmentActivity) this.mSupport, getTopFragment(), getTransactionDelegate(), true);
    }

    public int getDefaultFragmentBackground() {
        return this.mDefaultFragmentBackground;
    }

    public FragmentAnimator getFragmentAnimator() {
        return this.mFragmentAnimator.copy();
    }

    public TransactionDelegate getTransactionDelegate() {
        if (this.mTransactionDelegate == null) {
            this.mTransactionDelegate = new TransactionDelegate(this.mSupport);
        }
        return this.mTransactionDelegate;
    }

    public void loadMultipleRootFragment(int i2, int i3, ISupportFragment... iSupportFragmentArr) {
        this.mTransactionDelegate.loadMultipleRootTransaction(getSupportFragmentManager(), i2, i3, iSupportFragmentArr);
    }

    public void loadRootFragment(int i2, ISupportFragment iSupportFragment) {
        loadRootFragment(i2, iSupportFragment, true, false);
    }

    public void logFragmentStackHierarchy(String str) {
        this.mDebugStackDelegate.logFragmentRecords(str);
    }

    public void onBackPressed() {
        this.mTransactionDelegate.mActionQueue.enqueue(new Action(3) { // from class: cn.webdemo.com.supporfragment.SupportActivityDelegate.1
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                SupportActivityDelegate supportActivityDelegate = SupportActivityDelegate.this;
                if (!supportActivityDelegate.mFragmentClickable) {
                    supportActivityDelegate.mFragmentClickable = true;
                }
                if (SupportActivityDelegate.this.mTransactionDelegate.dispatchBackPressedEvent(SupportHelper.getActiveFragment(supportActivityDelegate.getSupportFragmentManager()))) {
                    return;
                }
                SupportActivityDelegate.this.mSupport.onBackPressedSupport();
            }
        });
    }

    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this.mActivity);
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        this.mTransactionDelegate = getTransactionDelegate();
        this.mDebugStackDelegate = new DebugStackDelegate(this.mActivity);
        this.mFragmentAnimator = this.mSupport.onCreateFragmentAnimator();
        this.mDebugStackDelegate.onCreate(Fragmentation.getDefault().getMode());
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    public void onDestroy() {
        this.mDebugStackDelegate.onDestroy();
    }

    public void onPostCreate(@Nullable Bundle bundle) {
        this.mDebugStackDelegate.onPostCreate(Fragmentation.getDefault().getMode());
    }

    public void pop() {
        this.mTransactionDelegate.pop(getSupportFragmentManager());
    }

    public void popTo(Class<?> cls, boolean z2) {
        popTo(cls, z2, null);
    }

    public void post(Runnable runnable) {
        this.mTransactionDelegate.post(runnable);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z2) {
        this.mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), iSupportFragment, 0, 0, z2 ? 10 : 11);
    }

    public void setDefaultFragmentBackground(@DrawableRes int i2) {
        this.mDefaultFragmentBackground = i2;
    }

    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mFragmentAnimator = fragmentAnimator;
        for (ActivityResultCaller activityResultCaller : FragmentationMagician.getActiveFragments(getSupportFragmentManager())) {
            if (activityResultCaller instanceof ISupportFragment) {
                SupportFragmentDelegate supportDelegate = ((ISupportFragment) activityResultCaller).getSupportDelegate();
                if (supportDelegate.mAnimByActivity) {
                    FragmentAnimator fragmentAnimatorCopy = fragmentAnimator.copy();
                    supportDelegate.mFragmentAnimator = fragmentAnimatorCopy;
                    AnimatorHelper animatorHelper = supportDelegate.mAnimHelper;
                    if (animatorHelper != null) {
                        animatorHelper.notifyChanged(fragmentAnimatorCopy);
                    }
                }
            }
        }
    }

    public void showFragmentStackHierarchyView() {
        this.mDebugStackDelegate.showFragmentStackHierarchyView();
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        showHideFragment(iSupportFragment, null);
    }

    public void start(ISupportFragment iSupportFragment) {
        start(iSupportFragment, 0);
    }

    public void startForResult(ISupportFragment iSupportFragment, int i2) {
        this.mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), iSupportFragment, i2, 0, 1);
    }

    public void startWithPop(ISupportFragment iSupportFragment) {
        this.mTransactionDelegate.startWithPop(getSupportFragmentManager(), getTopFragment(), iSupportFragment);
    }

    public void startWithPopTo(ISupportFragment iSupportFragment, Class<?> cls, boolean z2) {
        this.mTransactionDelegate.startWithPopTo(getSupportFragmentManager(), getTopFragment(), iSupportFragment, cls.getName(), z2);
    }

    public void loadRootFragment(int i2, ISupportFragment iSupportFragment, boolean z2, boolean z3) {
        this.mTransactionDelegate.loadRootTransaction(getSupportFragmentManager(), i2, iSupportFragment, z2, z3);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable) {
        popTo(cls, z2, runnable, Integer.MAX_VALUE);
    }

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mTransactionDelegate.showHideFragment(getSupportFragmentManager(), iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment, int i2) {
        this.mTransactionDelegate.dispatchStartTransaction(getSupportFragmentManager(), getTopFragment(), iSupportFragment, 0, i2, 0);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable, int i2) {
        this.mTransactionDelegate.popTo(cls.getName(), z2, runnable, getSupportFragmentManager(), i2);
    }
}
