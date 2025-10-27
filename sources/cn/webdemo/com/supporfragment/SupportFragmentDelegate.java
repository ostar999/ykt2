package cn.webdemo.com.supporfragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentationMagician;
import cn.webdemo.com.supporfragment.ExtraTransaction;
import cn.webdemo.com.supporfragment.anim.FragmentAnimator;
import cn.webdemo.com.supporfragment.helper.internal.AnimatorHelper;
import cn.webdemo.com.supporfragment.helper.internal.ResultRecord;
import cn.webdemo.com.supporfragment.helper.internal.TransactionRecord;
import cn.webdemo.com.supporfragment.helper.internal.VisibleDelegate;

/* loaded from: classes.dex */
public class SupportFragmentDelegate {
    private static final long NOT_FOUND_ANIM_TIME = 300;
    static final int STATUS_ROOT_ANIM_DISABLE = 1;
    static final int STATUS_ROOT_ANIM_ENABLE = 2;
    static final int STATUS_UN_ROOT = 0;
    protected FragmentActivity _mActivity;
    AnimatorHelper mAnimHelper;
    int mContainerId;
    EnterAnimListener mEnterAnimListener;
    private Fragment mFragment;
    FragmentAnimator mFragmentAnimator;
    private Handler mHandler;
    private boolean mIsSharedElement;
    boolean mLockAnim;
    Bundle mNewBundle;
    private boolean mReplaceMode;
    private boolean mRootViewClickable;
    private Bundle mSaveInstanceState;
    private ISupportActivity mSupport;
    private ISupportFragment mSupportF;
    private TransactionDelegate mTransactionDelegate;
    TransactionRecord mTransactionRecord;
    private VisibleDelegate mVisibleDelegate;
    private int mRootStatus = 0;
    private int mCustomEnterAnim = Integer.MIN_VALUE;
    private int mCustomExitAnim = Integer.MIN_VALUE;
    private int mCustomPopExitAnim = Integer.MIN_VALUE;
    private boolean mFirstCreateView = true;
    private boolean mIsHidden = true;
    boolean mAnimByActivity = true;
    private Runnable mNotifyEnterAnimEndRunnable = new Runnable() { // from class: cn.webdemo.com.supporfragment.SupportFragmentDelegate.3
        @Override // java.lang.Runnable
        public void run() {
            final View view;
            ISupportFragment preFragment;
            if (SupportFragmentDelegate.this.mFragment == null) {
                return;
            }
            SupportFragmentDelegate.this.mSupportF.onEnterAnimationEnd(SupportFragmentDelegate.this.mSaveInstanceState);
            if (SupportFragmentDelegate.this.mRootViewClickable || (view = SupportFragmentDelegate.this.mFragment.getView()) == null || (preFragment = SupportHelper.getPreFragment(SupportFragmentDelegate.this.mFragment)) == null) {
                return;
            }
            SupportFragmentDelegate.this.mHandler.postDelayed(new Runnable() { // from class: cn.webdemo.com.supporfragment.SupportFragmentDelegate.3.1
                @Override // java.lang.Runnable
                public void run() {
                    view.setClickable(false);
                }
            }, preFragment.getSupportDelegate().getPopExitAnimDuration() - SupportFragmentDelegate.this.getEnterAnimDuration());
        }
    };

    public interface EnterAnimListener {
        void onEnterAnimStart();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SupportFragmentDelegate(ISupportFragment iSupportFragment) {
        if (!(iSupportFragment instanceof Fragment)) {
            throw new RuntimeException("Must extends Fragment");
        }
        this.mSupportF = iSupportFragment;
        this.mFragment = (Fragment) iSupportFragment;
    }

    private void compatSharedElements() {
        notifyEnterAnimEnd();
    }

    private void fixAnimationListener(Animation animation) {
        getHandler().postDelayed(this.mNotifyEnterAnimEndRunnable, animation.getDuration());
        this.mSupport.getSupportDelegate().mFragmentClickable = true;
        if (this.mEnterAnimListener != null) {
            getHandler().post(new Runnable() { // from class: cn.webdemo.com.supporfragment.SupportFragmentDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    SupportFragmentDelegate.this.mEnterAnimListener.onEnterAnimStart();
                    SupportFragmentDelegate.this.mEnterAnimListener = null;
                }
            });
        }
    }

    private FragmentManager getChildFragmentManager() {
        return this.mFragment.getChildFragmentManager();
    }

    private Animation getEnterAnim() {
        Animation animation;
        int i2 = this.mCustomEnterAnim;
        if (i2 != Integer.MIN_VALUE) {
            try {
                return AnimationUtils.loadAnimation(this._mActivity, i2);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        AnimatorHelper animatorHelper = this.mAnimHelper;
        if (animatorHelper == null || (animation = animatorHelper.enterAnim) == null) {
            return null;
        }
        return animation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getEnterAnimDuration() {
        Animation enterAnim = getEnterAnim();
        if (enterAnim != null) {
            return enterAnim.getDuration();
        }
        return 300L;
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getPopExitAnimDuration() {
        Animation animation;
        int i2 = this.mCustomPopExitAnim;
        if (i2 != Integer.MIN_VALUE) {
            try {
                return AnimationUtils.loadAnimation(this._mActivity, i2).getDuration();
            } catch (Exception e2) {
                e2.printStackTrace();
                return 300L;
            }
        }
        AnimatorHelper animatorHelper = this.mAnimHelper;
        if (animatorHelper == null || (animation = animatorHelper.popExitAnim) == null) {
            return 300L;
        }
        return animation.getDuration();
    }

    private ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getChildFragmentManager());
    }

    private int getWindowBackground() {
        TypedArray typedArrayObtainStyledAttributes = this._mActivity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    private void notifyEnterAnimEnd() {
        getHandler().post(this.mNotifyEnterAnimEndRunnable);
        this.mSupport.getSupportDelegate().mFragmentClickable = true;
    }

    private void processRestoreInstanceState(Bundle bundle) {
        if (bundle != null) {
            FragmentTransaction fragmentTransactionBeginTransaction = this.mFragment.getFragmentManager().beginTransaction();
            if (this.mIsHidden) {
                fragmentTransactionBeginTransaction.hide(this.mFragment);
            } else {
                fragmentTransactionBeginTransaction.show(this.mFragment);
            }
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        }
    }

    @Deprecated
    public void enqueueAction(Runnable runnable) {
        post(runnable);
    }

    public ExtraTransaction extraTransaction() {
        TransactionDelegate transactionDelegate = this.mTransactionDelegate;
        if (transactionDelegate != null) {
            return new ExtraTransaction.ExtraTransactionImpl((FragmentActivity) this.mSupport, this.mSupportF, transactionDelegate, false);
        }
        throw new RuntimeException(this.mFragment.getClass().getSimpleName() + " not attach!");
    }

    public FragmentActivity getActivity() {
        return this._mActivity;
    }

    @Nullable
    public Animation getExitAnim() {
        Animation animation;
        int i2 = this.mCustomExitAnim;
        if (i2 != Integer.MIN_VALUE) {
            try {
                return AnimationUtils.loadAnimation(this._mActivity, i2);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        AnimatorHelper animatorHelper = this.mAnimHelper;
        if (animatorHelper == null || (animation = animatorHelper.exitAnim) == null) {
            return null;
        }
        return animation;
    }

    public long getExitAnimDuration() {
        Animation animation;
        int i2 = this.mCustomExitAnim;
        if (i2 != Integer.MIN_VALUE) {
            try {
                return AnimationUtils.loadAnimation(this._mActivity, i2).getDuration();
            } catch (Exception e2) {
                e2.printStackTrace();
                return 300L;
            }
        }
        AnimatorHelper animatorHelper = this.mAnimHelper;
        if (animatorHelper == null || (animation = animatorHelper.exitAnim) == null) {
            return 300L;
        }
        return animation.getDuration();
    }

    public FragmentAnimator getFragmentAnimator() {
        if (this.mSupport == null) {
            throw new RuntimeException("Fragment has not been attached to Activity!");
        }
        if (this.mFragmentAnimator == null) {
            FragmentAnimator fragmentAnimatorOnCreateFragmentAnimator = this.mSupportF.onCreateFragmentAnimator();
            this.mFragmentAnimator = fragmentAnimatorOnCreateFragmentAnimator;
            if (fragmentAnimatorOnCreateFragmentAnimator == null) {
                this.mFragmentAnimator = this.mSupport.getFragmentAnimator();
            }
        }
        return this.mFragmentAnimator;
    }

    public VisibleDelegate getVisibleDelegate() {
        if (this.mVisibleDelegate == null) {
            this.mVisibleDelegate = new VisibleDelegate(this.mSupportF);
        }
        return this.mVisibleDelegate;
    }

    public void hideSoftInput() {
        FragmentActivity activity = this.mFragment.getActivity();
        if (activity == null) {
            return;
        }
        SupportHelper.hideSoftInput(activity.getWindow().getDecorView());
    }

    public final boolean isSupportVisible() {
        return getVisibleDelegate().isSupportVisible();
    }

    public void loadMultipleRootFragment(int i2, int i3, ISupportFragment... iSupportFragmentArr) {
        this.mTransactionDelegate.loadMultipleRootTransaction(getChildFragmentManager(), i2, i3, iSupportFragmentArr);
    }

    public void loadRootFragment(int i2, ISupportFragment iSupportFragment) {
        loadRootFragment(i2, iSupportFragment, true, false);
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        getVisibleDelegate().onActivityCreated(bundle);
        View view = this.mFragment.getView();
        if (view != null) {
            this.mRootViewClickable = view.isClickable();
            view.setClickable(true);
            setBackground(view);
        }
        if (bundle != null || this.mRootStatus == 1 || ((this.mFragment.getTag() != null && this.mFragment.getTag().startsWith("android:switcher:")) || (this.mReplaceMode && !this.mFirstCreateView))) {
            notifyEnterAnimEnd();
        } else {
            int i2 = this.mCustomEnterAnim;
            if (i2 != Integer.MIN_VALUE) {
                fixAnimationListener(i2 == 0 ? this.mAnimHelper.getNoneAnim() : AnimationUtils.loadAnimation(this._mActivity, i2));
            }
        }
        if (this.mFirstCreateView) {
            this.mFirstCreateView = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onAttach(Activity activity) {
        if (!(activity instanceof ISupportActivity)) {
            throw new RuntimeException(activity.getClass().getSimpleName() + " must impl ISupportActivity!");
        }
        ISupportActivity iSupportActivity = (ISupportActivity) activity;
        this.mSupport = iSupportActivity;
        this._mActivity = (FragmentActivity) activity;
        this.mTransactionDelegate = iSupportActivity.getSupportDelegate().getTransactionDelegate();
    }

    public boolean onBackPressedSupport() {
        return false;
    }

    public void onCreate(@Nullable Bundle bundle) {
        getVisibleDelegate().onCreate(bundle);
        Bundle arguments = this.mFragment.getArguments();
        if (arguments != null) {
            this.mRootStatus = arguments.getInt("fragmentation_arg_root_status", 0);
            this.mIsSharedElement = arguments.getBoolean("fragmentation_arg_is_shared_element", false);
            this.mContainerId = arguments.getInt("fragmentation_arg_container");
            this.mReplaceMode = arguments.getBoolean("fragmentation_arg_replace", false);
            this.mCustomEnterAnim = arguments.getInt("fragmentation_arg_custom_enter_anim", Integer.MIN_VALUE);
            this.mCustomExitAnim = arguments.getInt("fragmentation_arg_custom_exit_anim", Integer.MIN_VALUE);
            this.mCustomPopExitAnim = arguments.getInt("fragmentation_arg_custom_pop_exit_anim", Integer.MIN_VALUE);
        }
        if (bundle == null) {
            getFragmentAnimator();
        } else {
            this.mSaveInstanceState = bundle;
            this.mFragmentAnimator = (FragmentAnimator) bundle.getParcelable("fragmentation_state_save_animator");
            this.mIsHidden = bundle.getBoolean("fragmentation_state_save_status");
            this.mContainerId = bundle.getInt("fragmentation_arg_container");
            if (this.mRootStatus != 0) {
                FragmentationMagician.reorderIndices(this.mFragment.getFragmentManager());
            }
        }
        processRestoreInstanceState(bundle);
        this.mAnimHelper = new AnimatorHelper(this._mActivity.getApplicationContext(), this.mFragmentAnimator);
        final Animation enterAnim = getEnterAnim();
        if (enterAnim == null) {
            return;
        }
        getEnterAnim().setAnimationListener(new Animation.AnimationListener() { // from class: cn.webdemo.com.supporfragment.SupportFragmentDelegate.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                SupportFragmentDelegate.this.mSupport.getSupportDelegate().mFragmentClickable = false;
                SupportFragmentDelegate.this.mHandler.postDelayed(new Runnable() { // from class: cn.webdemo.com.supporfragment.SupportFragmentDelegate.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SupportFragmentDelegate.this.mSupport.getSupportDelegate().mFragmentClickable = true;
                    }
                }, enterAnim.getDuration());
            }
        });
    }

    public Animation onCreateAnimation(int i2, boolean z2, int i3) {
        if (this.mSupport.getSupportDelegate().mPopMultipleNoAnim || this.mLockAnim) {
            return (i2 == 8194 && z2) ? this.mAnimHelper.getNoneAnimFixed() : this.mAnimHelper.getNoneAnim();
        }
        if (i2 == 4097) {
            if (!z2) {
                return this.mAnimHelper.popExitAnim;
            }
            if (this.mRootStatus == 1) {
                return this.mAnimHelper.getNoneAnim();
            }
            Animation animation = this.mAnimHelper.enterAnim;
            fixAnimationListener(animation);
            return animation;
        }
        if (i2 == 8194) {
            AnimatorHelper animatorHelper = this.mAnimHelper;
            return z2 ? animatorHelper.popEnterAnim : animatorHelper.exitAnim;
        }
        if (this.mIsSharedElement && z2) {
            compatSharedElements();
        }
        if (z2) {
            return null;
        }
        return this.mAnimHelper.compatChildFragmentExitAnim(this.mFragment);
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return this.mSupport.getFragmentAnimator();
    }

    public void onDestroy() {
        this.mTransactionDelegate.handleResultRecord(this.mFragment);
    }

    public void onDestroyView() {
        this.mSupport.getSupportDelegate().mFragmentClickable = true;
        getVisibleDelegate().onDestroyView();
        getHandler().removeCallbacks(this.mNotifyEnterAnimEndRunnable);
    }

    public void onEnterAnimationEnd(Bundle bundle) {
    }

    public void onFragmentResult(int i2, int i3, Bundle bundle) {
    }

    public void onHiddenChanged(boolean z2) {
        getVisibleDelegate().onHiddenChanged(z2);
    }

    public void onLazyInitView(@Nullable Bundle bundle) {
    }

    public void onNewBundle(Bundle bundle) {
    }

    public void onPause() {
        getVisibleDelegate().onPause();
    }

    public void onResume() {
        getVisibleDelegate().onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        getVisibleDelegate().onSaveInstanceState(bundle);
        bundle.putParcelable("fragmentation_state_save_animator", this.mFragmentAnimator);
        bundle.putBoolean("fragmentation_state_save_status", this.mFragment.isHidden());
        bundle.putInt("fragmentation_arg_container", this.mContainerId);
    }

    public void onSupportInvisible() {
    }

    public void onSupportVisible() {
    }

    public void pop() {
        this.mTransactionDelegate.pop(this.mFragment.getFragmentManager());
    }

    public void popChild() {
        this.mTransactionDelegate.pop(getChildFragmentManager());
    }

    public void popQuiet() {
        this.mTransactionDelegate.popQuiet(this.mFragment.getFragmentManager());
    }

    public void popTo(Class<?> cls, boolean z2) {
        popTo(cls, z2, null);
    }

    public void popToChild(Class<?> cls, boolean z2) {
        popToChild(cls, z2, null);
    }

    public void post(Runnable runnable) {
        this.mTransactionDelegate.post(runnable);
    }

    public void putNewBundle(Bundle bundle) {
        this.mNewBundle = bundle;
    }

    public void replaceChildFragment(ISupportFragment iSupportFragment, boolean z2) {
        this.mTransactionDelegate.dispatchStartTransaction(getChildFragmentManager(), getTopFragment(), iSupportFragment, 0, 0, z2 ? 10 : 11);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z2) {
        this.mTransactionDelegate.dispatchStartTransaction(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, z2 ? 10 : 11);
    }

    public void setBackground(View view) {
        if ((this.mFragment.getTag() == null || !this.mFragment.getTag().startsWith("android:switcher:")) && this.mRootStatus == 0 && view.getBackground() == null) {
            int defaultFragmentBackground = this.mSupport.getSupportDelegate().getDefaultFragmentBackground();
            if (defaultFragmentBackground == 0) {
                view.setBackgroundResource(getWindowBackground());
            } else {
                view.setBackgroundResource(defaultFragmentBackground);
            }
        }
    }

    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mFragmentAnimator = fragmentAnimator;
        AnimatorHelper animatorHelper = this.mAnimHelper;
        if (animatorHelper != null) {
            animatorHelper.notifyChanged(fragmentAnimator);
        }
        this.mAnimByActivity = false;
    }

    public void setFragmentResult(int i2, Bundle bundle) {
        ResultRecord resultRecord;
        Bundle arguments = this.mFragment.getArguments();
        if (arguments == null || !arguments.containsKey("fragment_arg_result_record") || (resultRecord = (ResultRecord) arguments.getParcelable("fragment_arg_result_record")) == null) {
            return;
        }
        resultRecord.resultCode = i2;
        resultRecord.resultBundle = bundle;
    }

    public void setUserVisibleHint(boolean z2) {
        getVisibleDelegate().setUserVisibleHint(z2);
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        showHideFragment(iSupportFragment, null);
    }

    public void showSoftInput(View view) {
        SupportHelper.showSoftInput(view);
    }

    public void start(ISupportFragment iSupportFragment) {
        start(iSupportFragment, 0);
    }

    public void startChild(ISupportFragment iSupportFragment) {
        startChild(iSupportFragment, 0);
    }

    public void startChildForResult(ISupportFragment iSupportFragment, int i2) {
        this.mTransactionDelegate.dispatchStartTransaction(getChildFragmentManager(), getTopFragment(), iSupportFragment, i2, 0, 1);
    }

    public void startChildWithPop(ISupportFragment iSupportFragment) {
        this.mTransactionDelegate.startWithPop(getChildFragmentManager(), getTopFragment(), iSupportFragment);
    }

    public void startForResult(ISupportFragment iSupportFragment, int i2) {
        this.mTransactionDelegate.dispatchStartTransaction(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, i2, 0, 1);
    }

    public void startWithPop(ISupportFragment iSupportFragment) {
        this.mTransactionDelegate.startWithPop(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment);
    }

    public void startWithPopTo(ISupportFragment iSupportFragment, Class<?> cls, boolean z2) {
        this.mTransactionDelegate.startWithPopTo(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, cls.getName(), z2);
    }

    public void loadRootFragment(int i2, ISupportFragment iSupportFragment, boolean z2, boolean z3) {
        this.mTransactionDelegate.loadRootTransaction(getChildFragmentManager(), i2, iSupportFragment, z2, z3);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable) {
        popTo(cls, z2, runnable, Integer.MAX_VALUE);
    }

    public void popToChild(Class<?> cls, boolean z2, Runnable runnable) {
        popToChild(cls, z2, runnable, Integer.MAX_VALUE);
    }

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mTransactionDelegate.showHideFragment(getChildFragmentManager(), iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment, int i2) {
        this.mTransactionDelegate.dispatchStartTransaction(this.mFragment.getFragmentManager(), this.mSupportF, iSupportFragment, 0, i2, 0);
    }

    public void startChild(ISupportFragment iSupportFragment, int i2) {
        this.mTransactionDelegate.dispatchStartTransaction(getChildFragmentManager(), getTopFragment(), iSupportFragment, 0, i2, 0);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable, int i2) {
        this.mTransactionDelegate.popTo(cls.getName(), z2, runnable, this.mFragment.getFragmentManager(), i2);
    }

    public void popToChild(Class<?> cls, boolean z2, Runnable runnable, int i2) {
        this.mTransactionDelegate.popTo(cls.getName(), z2, runnable, getChildFragmentManager(), i2);
    }
}
