package cn.webdemo.com.supporfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentationMagician;
import cn.webdemo.com.supporfragment.SupportFragmentDelegate;
import cn.webdemo.com.supporfragment.exception.AfterSaveStateTransactionWarning;
import cn.webdemo.com.supporfragment.helper.internal.ResultRecord;
import cn.webdemo.com.supporfragment.helper.internal.TransactionRecord;
import cn.webdemo.com.supporfragment.queue.Action;
import cn.webdemo.com.supporfragment.queue.ActionQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
class TransactionDelegate {
    static final int DEFAULT_POPTO_ANIM = Integer.MAX_VALUE;
    static final String FRAGMENTATION_ARG_CONTAINER = "fragmentation_arg_container";
    static final String FRAGMENTATION_ARG_CUSTOM_ENTER_ANIM = "fragmentation_arg_custom_enter_anim";
    static final String FRAGMENTATION_ARG_CUSTOM_EXIT_ANIM = "fragmentation_arg_custom_exit_anim";
    static final String FRAGMENTATION_ARG_CUSTOM_POP_EXIT_ANIM = "fragmentation_arg_custom_pop_exit_anim";
    static final String FRAGMENTATION_ARG_IS_SHARED_ELEMENT = "fragmentation_arg_is_shared_element";
    static final String FRAGMENTATION_ARG_REPLACE = "fragmentation_arg_replace";
    static final String FRAGMENTATION_ARG_RESULT_RECORD = "fragment_arg_result_record";
    static final String FRAGMENTATION_ARG_ROOT_STATUS = "fragmentation_arg_root_status";
    static final String FRAGMENTATION_STATE_SAVE_ANIMATOR = "fragmentation_state_save_animator";
    static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "fragmentation_state_save_status";
    private static final String FRAGMENTATION_STATE_SAVE_RESULT = "fragmentation_state_save_result";
    private static final String TAG = "Fragmentation";
    static final int TYPE_ADD = 0;
    static final int TYPE_ADD_RESULT = 1;
    static final int TYPE_ADD_RESULT_WITHOUT_HIDE = 3;
    static final int TYPE_ADD_WITHOUT_HIDE = 2;
    static final int TYPE_REPLACE = 10;
    static final int TYPE_REPLACE_DONT_BACK = 11;
    ActionQueue mActionQueue;
    private FragmentActivity mActivity;
    private Handler mHandler;
    private ISupportActivity mSupport;

    /* JADX WARN: Multi-variable type inference failed */
    public TransactionDelegate(ISupportActivity iSupportActivity) {
        this.mSupport = iSupportActivity;
        this.mActivity = (FragmentActivity) iSupportActivity;
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mActionQueue = new ActionQueue(handler);
    }

    @NonNull
    private ViewGroup addMockView(View view, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = new ViewGroup(this.mActivity) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.18
            @Override // android.view.ViewGroup, android.view.View
            public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
            }
        };
        viewGroup2.addView(view);
        viewGroup.addView(viewGroup2);
        return viewGroup2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void bindContainerId(int i2, ISupportFragment iSupportFragment) {
        getArguments((Fragment) iSupportFragment).putInt(FRAGMENTATION_ARG_CONTAINER, i2);
    }

    private static <T> void checkNotNull(T t2, String str) {
        if (t2 == null) {
            throw new NullPointerException(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0091 A[PHI: r0 r3
      0x0091: PHI (r0v7 java.lang.String) = (r0v6 java.lang.String), (r0v11 java.lang.String) binds: [B:20:0x007e, B:25:0x0089] A[DONT_GENERATE, DONT_INLINE]
      0x0091: PHI (r3v5 boolean) = (r3v4 boolean), (r3v10 boolean) binds: [B:20:0x007e, B:25:0x0089] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doDispatchStartTransaction(androidx.fragment.app.FragmentManager r16, cn.webdemo.com.supporfragment.ISupportFragment r17, cn.webdemo.com.supporfragment.ISupportFragment r18, int r19, int r20, int r21) {
        /*
            r15 = this;
            r9 = r15
            r6 = r16
            r0 = r17
            r7 = r18
            r8 = r21
            java.lang.String r1 = "toFragment == null"
            checkNotNull(r7, r1)
            r1 = 1
            java.lang.String r2 = "Fragmentation"
            if (r8 == r1) goto L16
            r1 = 3
            if (r8 != r1) goto L46
        L16:
            if (r0 == 0) goto L46
            r1 = r0
            androidx.fragment.app.Fragment r1 = (androidx.fragment.app.Fragment) r1
            boolean r3 = r1.isAdded()
            if (r3 != 0) goto L3e
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getSimpleName()
            r3.append(r1)
            java.lang.String r1 = " has not been attached yet! startForResult() converted to start()"
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Log.w(r2, r1)
            goto L46
        L3e:
            r3 = r7
            androidx.fragment.app.Fragment r3 = (androidx.fragment.app.Fragment) r3
            r4 = r19
            r15.saveRequestCode(r6, r1, r3, r4)
        L46:
            cn.webdemo.com.supporfragment.ISupportFragment r10 = r15.getTopFragmentForStart(r0, r6)
            r0 = r7
            androidx.fragment.app.Fragment r0 = (androidx.fragment.app.Fragment) r0
            android.os.Bundle r0 = r15.getArguments(r0)
            java.lang.String r1 = "fragmentation_arg_container"
            r3 = 0
            int r0 = r0.getInt(r1, r3)
            if (r10 != 0) goto L62
            if (r0 != 0) goto L62
            java.lang.String r0 = "There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment()!"
            android.util.Log.e(r2, r0)
            return
        L62:
            if (r10 == 0) goto L6f
            if (r0 != 0) goto L6f
            cn.webdemo.com.supporfragment.SupportFragmentDelegate r0 = r10.getSupportDelegate()
            int r0 = r0.mContainerId
            r15.bindContainerId(r0, r7)
        L6f:
            java.lang.Class r0 = r18.getClass()
            java.lang.String r0 = r0.getName()
            cn.webdemo.com.supporfragment.SupportFragmentDelegate r1 = r18.getSupportDelegate()
            cn.webdemo.com.supporfragment.helper.internal.TransactionRecord r1 = r1.mTransactionRecord
            r2 = 0
            if (r1 == 0) goto L91
            java.lang.String r3 = r1.tag
            if (r3 == 0) goto L85
            r0 = r3
        L85:
            boolean r3 = r1.dontAddToBackStack
            java.util.ArrayList<cn.webdemo.com.supporfragment.helper.internal.TransactionRecord$SharedElement> r1 = r1.sharedElementList
            if (r1 == 0) goto L91
            androidx.fragment.app.FragmentationMagician.reorderIndices(r16)
            r11 = r0
            r13 = r1
            goto L93
        L91:
            r11 = r0
            r13 = r2
        L93:
            r12 = r3
            r0 = r15
            r1 = r16
            r2 = r10
            r3 = r18
            r4 = r11
            r5 = r20
            boolean r0 = r0.handleLaunchMode(r1, r2, r3, r4, r5)
            if (r0 == 0) goto La4
            return
        La4:
            r14 = 0
            r0 = r15
            r1 = r16
            r2 = r10
            r3 = r18
            r4 = r11
            r5 = r12
            r6 = r13
            r7 = r14
            r8 = r21
            r0.start(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.webdemo.com.supporfragment.TransactionDelegate.doDispatchStartTransaction(androidx.fragment.app.FragmentManager, cn.webdemo.com.supporfragment.ISupportFragment, cn.webdemo.com.supporfragment.ISupportFragment, int, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPopTo(String str, boolean z2, FragmentManager fragmentManager, int i2) {
        handleAfterSaveInStateTransactionException(fragmentManager, "popTo()");
        if (fragmentManager.findFragmentByTag(str) != null) {
            List<Fragment> willPopFragments = SupportHelper.getWillPopFragments(fragmentManager, str, z2);
            if (willPopFragments.size() <= 0) {
                return;
            }
            mockPopToAnim(willPopFragments.get(0), str, fragmentManager, z2 ? 1 : 0, willPopFragments, i2);
            return;
        }
        Log.e(TAG, "Pop failure! Can't find FragmentTag:" + str + " in the FragmentManager's Stack.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void doShowHideFragment(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        if (iSupportFragment == iSupportFragment2) {
            return;
        }
        FragmentTransaction fragmentTransactionShow = fragmentManager.beginTransaction().show((Fragment) iSupportFragment);
        if (iSupportFragment2 == 0) {
            List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
            if (activeFragments != null) {
                for (Fragment fragment : activeFragments) {
                    if (fragment != null && fragment != iSupportFragment) {
                        fragmentTransactionShow.hide(fragment);
                    }
                }
            }
        } else {
            fragmentTransactionShow.hide((Fragment) iSupportFragment2);
        }
        supportCommit(fragmentManager, fragmentTransactionShow);
    }

    private void enqueue(FragmentManager fragmentManager, Action action) {
        if (fragmentManager == null) {
            Log.w(TAG, "FragmentManager is null, skip the action!");
        } else {
            this.mActionQueue.enqueue(action);
        }
    }

    private ViewGroup findContainerById(Fragment fragment, int i2) {
        if (fragment.getView() == null) {
            return null;
        }
        Fragment parentFragment = fragment.getParentFragment();
        KeyEvent.Callback callbackFindViewById = parentFragment != null ? parentFragment.getView() != null ? parentFragment.getView().findViewById(i2) : findContainerById(parentFragment, i2) : this.mActivity.findViewById(i2);
        if (callbackFindViewById instanceof ViewGroup) {
            return (ViewGroup) callbackFindViewById;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle getArguments(Fragment fragment) {
        Bundle arguments = fragment.getArguments();
        if (arguments != null) {
            return arguments;
        }
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public ISupportFragment getTopFragmentForStart(ISupportFragment iSupportFragment, FragmentManager fragmentManager) {
        if (iSupportFragment == 0) {
            return SupportHelper.getTopFragment(fragmentManager);
        }
        if (iSupportFragment.getSupportDelegate().mContainerId == 0) {
            Fragment fragment = (Fragment) iSupportFragment;
            if (fragment.getTag() != null && !fragment.getTag().startsWith("android:switcher:")) {
                throw new IllegalStateException("Can't find container, please call loadRootFragment() first!");
            }
        }
        return SupportHelper.getTopFragment(fragmentManager, iSupportFragment.getSupportDelegate().mContainerId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAfterSaveInStateTransactionException(FragmentManager fragmentManager, String str) {
        if (FragmentationMagician.isStateSaved(fragmentManager)) {
            AfterSaveStateTransactionWarning afterSaveStateTransactionWarning = new AfterSaveStateTransactionWarning(str);
            if (Fragmentation.getDefault().getHandler() != null) {
                Fragmentation.getDefault().getHandler().onException(afterSaveStateTransactionWarning);
            }
        }
    }

    private boolean handleLaunchMode(FragmentManager fragmentManager, ISupportFragment iSupportFragment, final ISupportFragment iSupportFragment2, String str, int i2) {
        final ISupportFragment iSupportFragmentFindBackStackFragment;
        if (iSupportFragment == null || (iSupportFragmentFindBackStackFragment = SupportHelper.findBackStackFragment(iSupportFragment2.getClass(), str, fragmentManager)) == null) {
            return false;
        }
        if (i2 == 1) {
            if (iSupportFragment2 == iSupportFragment || iSupportFragment2.getClass().getName().equals(iSupportFragment.getClass().getName())) {
                handleNewBundle(iSupportFragment2, iSupportFragmentFindBackStackFragment);
                return true;
            }
        } else if (i2 == 2) {
            doPopTo(str, false, fragmentManager, Integer.MAX_VALUE);
            this.mHandler.post(new Runnable() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.12
                @Override // java.lang.Runnable
                public void run() {
                    TransactionDelegate.this.handleNewBundle(iSupportFragment2, iSupportFragmentFindBackStackFragment);
                }
            });
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void handleNewBundle(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        Bundle bundle = iSupportFragment.getSupportDelegate().mNewBundle;
        Bundle arguments = getArguments((Fragment) iSupportFragment);
        if (arguments.containsKey(FRAGMENTATION_ARG_CONTAINER)) {
            arguments.remove(FRAGMENTATION_ARG_CONTAINER);
        }
        if (bundle != null) {
            arguments.putAll(bundle);
        }
        iSupportFragment2.onNewBundle(arguments);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mockPopToAnim(Fragment fragment, String str, FragmentManager fragmentManager, int i2, List<Fragment> list, int i3) {
        final View view;
        Animation animationLoadAnimation;
        if (!(fragment instanceof ISupportFragment)) {
            safePopTo(str, fragmentManager, i2, list);
            return;
        }
        ISupportFragment iSupportFragment = (ISupportFragment) fragment;
        final ViewGroup viewGroupFindContainerById = findContainerById(fragment, iSupportFragment.getSupportDelegate().mContainerId);
        if (viewGroupFindContainerById == null || (view = fragment.getView()) == null) {
            return;
        }
        viewGroupFindContainerById.removeViewInLayout(view);
        final ViewGroup viewGroupAddMockView = addMockView(view, viewGroupFindContainerById);
        safePopTo(str, fragmentManager, i2, list);
        if (i3 == Integer.MAX_VALUE) {
            animationLoadAnimation = iSupportFragment.getSupportDelegate().getExitAnim();
            if (animationLoadAnimation == null) {
                animationLoadAnimation = new Animation() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.14
                };
            }
        } else {
            animationLoadAnimation = i3 == 0 ? new Animation() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.15
            } : AnimationUtils.loadAnimation(this.mActivity, i3);
        }
        view.startAnimation(animationLoadAnimation);
        this.mHandler.postDelayed(new Runnable() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.16
            @Override // java.lang.Runnable
            public void run() {
                try {
                    viewGroupAddMockView.removeViewInLayout(view);
                    viewGroupFindContainerById.removeViewInLayout(viewGroupAddMockView);
                } catch (Exception unused) {
                }
            }
        }, animationLoadAnimation.getDuration());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void mockStartWithPopAnim(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2, final Animation animation) {
        final View view;
        Fragment fragment = (Fragment) iSupportFragment;
        final ViewGroup viewGroupFindContainerById = findContainerById(fragment, iSupportFragment.getSupportDelegate().mContainerId);
        if (viewGroupFindContainerById == null || (view = fragment.getView()) == null) {
            return;
        }
        viewGroupFindContainerById.removeViewInLayout(view);
        final ViewGroup viewGroupAddMockView = addMockView(view, viewGroupFindContainerById);
        iSupportFragment2.getSupportDelegate().mEnterAnimListener = new SupportFragmentDelegate.EnterAnimListener() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.17
            @Override // cn.webdemo.com.supporfragment.SupportFragmentDelegate.EnterAnimListener
            public void onEnterAnimStart() {
                view.startAnimation(animation);
                TransactionDelegate.this.mHandler.postDelayed(new Runnable() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.17.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AnonymousClass17 anonymousClass17 = AnonymousClass17.this;
                            viewGroupAddMockView.removeViewInLayout(view);
                            AnonymousClass17 anonymousClass172 = AnonymousClass17.this;
                            viewGroupFindContainerById.removeViewInLayout(viewGroupAddMockView);
                        } catch (Exception unused) {
                        }
                    }
                }, animation.getDuration());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeTopFragment(FragmentManager fragmentManager) {
        try {
            Object backStackTopFragment = SupportHelper.getBackStackTopFragment(fragmentManager);
            if (backStackTopFragment != null) {
                fragmentManager.beginTransaction().setTransition(8194).remove((Fragment) backStackTopFragment).commitAllowingStateLoss();
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void safePopTo(String str, final FragmentManager fragmentManager, int i2, List<Fragment> list) {
        this.mSupport.getSupportDelegate().mPopMultipleNoAnim = true;
        FragmentTransaction transition = fragmentManager.beginTransaction().setTransition(8194);
        Iterator<Fragment> it = list.iterator();
        while (it.hasNext()) {
            transition.remove(it.next());
        }
        transition.commitAllowingStateLoss();
        FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager, str, i2);
        FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
        this.mSupport.getSupportDelegate().mPopMultipleNoAnim = false;
        if (FragmentationMagician.isSupportLessThan25dot4()) {
            this.mHandler.post(new Runnable() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.13
                @Override // java.lang.Runnable
                public void run() {
                    FragmentationMagician.reorderIndices(fragmentManager);
                }
            });
        }
    }

    private void saveRequestCode(FragmentManager fragmentManager, Fragment fragment, Fragment fragment2, int i2) {
        Bundle arguments = getArguments(fragment2);
        ResultRecord resultRecord = new ResultRecord();
        resultRecord.requestCode = i2;
        arguments.putParcelable(FRAGMENTATION_ARG_RESULT_RECORD, resultRecord);
        fragmentManager.putFragment(arguments, FRAGMENTATION_STATE_SAVE_RESULT, fragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void start(FragmentManager fragmentManager, ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2, String str, boolean z2, ArrayList<TransactionRecord.SharedElement> arrayList, boolean z3, int i2) {
        int i3;
        FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
        boolean z4 = i2 == 0 || i2 == 1 || i2 == 2 || i2 == 3;
        Fragment fragment = (Fragment) iSupportFragment;
        Fragment fragment2 = (Fragment) iSupportFragment2;
        Bundle arguments = getArguments(fragment2);
        arguments.putBoolean(FRAGMENTATION_ARG_REPLACE, !z4);
        if (arrayList != null) {
            arguments.putBoolean(FRAGMENTATION_ARG_IS_SHARED_ELEMENT, true);
            Iterator<TransactionRecord.SharedElement> it = arrayList.iterator();
            while (it.hasNext()) {
                TransactionRecord.SharedElement next = it.next();
                fragmentTransactionBeginTransaction.addSharedElement(next.sharedElement, next.sharedName);
            }
        } else if (z4) {
            TransactionRecord transactionRecord = iSupportFragment2.getSupportDelegate().mTransactionRecord;
            if (transactionRecord == null || (i3 = transactionRecord.targetFragmentEnter) == Integer.MIN_VALUE) {
                fragmentTransactionBeginTransaction.setTransition(4097);
            } else {
                fragmentTransactionBeginTransaction.setCustomAnimations(i3, transactionRecord.currentFragmentPopExit, transactionRecord.currentFragmentPopEnter, transactionRecord.targetFragmentExit);
                arguments.putInt(FRAGMENTATION_ARG_CUSTOM_ENTER_ANIM, transactionRecord.targetFragmentEnter);
                arguments.putInt(FRAGMENTATION_ARG_CUSTOM_EXIT_ANIM, transactionRecord.targetFragmentExit);
                arguments.putInt(FRAGMENTATION_ARG_CUSTOM_POP_EXIT_ANIM, transactionRecord.currentFragmentPopExit);
            }
        } else {
            arguments.putInt(FRAGMENTATION_ARG_ROOT_STATUS, 1);
        }
        if (iSupportFragment == 0) {
            fragmentTransactionBeginTransaction.replace(arguments.getInt(FRAGMENTATION_ARG_CONTAINER), fragment2, str);
            if (!z4) {
                fragmentTransactionBeginTransaction.setTransition(4097);
                arguments.putInt(FRAGMENTATION_ARG_ROOT_STATUS, z3 ? 2 : 1);
            }
        } else if (z4) {
            fragmentTransactionBeginTransaction.add(iSupportFragment.getSupportDelegate().mContainerId, fragment2, str);
            if (i2 != 2 && i2 != 3) {
                fragmentTransactionBeginTransaction.hide(fragment);
            }
        } else {
            fragmentTransactionBeginTransaction.replace(iSupportFragment.getSupportDelegate().mContainerId, fragment2, str);
        }
        if (!z2 && i2 != 11) {
            fragmentTransactionBeginTransaction.addToBackStack(str);
        }
        supportCommit(fragmentManager, fragmentTransactionBeginTransaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void supportCommit(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        handleAfterSaveInStateTransactionException(fragmentManager, "commit()");
        fragmentTransaction.commitAllowingStateLoss();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean dispatchBackPressedEvent(ISupportFragment iSupportFragment) {
        if (iSupportFragment != 0) {
            return iSupportFragment.onBackPressedSupport() || dispatchBackPressedEvent((ISupportFragment) ((Fragment) iSupportFragment).getParentFragment());
        }
        return false;
    }

    public void dispatchStartTransaction(final FragmentManager fragmentManager, final ISupportFragment iSupportFragment, final ISupportFragment iSupportFragment2, final int i2, final int i3, final int i4) {
        enqueue(fragmentManager, new Action(i3 != 2 ? 0 : 2) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.4
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                TransactionDelegate.this.doDispatchStartTransaction(fragmentManager, iSupportFragment, iSupportFragment2, i2, i3, i4);
            }
        });
    }

    public void handleResultRecord(Fragment fragment) {
        ResultRecord resultRecord;
        try {
            Bundle arguments = fragment.getArguments();
            if (arguments == null || (resultRecord = (ResultRecord) arguments.getParcelable(FRAGMENTATION_ARG_RESULT_RECORD)) == null) {
                return;
            }
            ((ISupportFragment) fragment.getFragmentManager().getFragment(fragment.getArguments(), FRAGMENTATION_STATE_SAVE_RESULT)).onFragmentResult(resultRecord.requestCode, resultRecord.resultCode, resultRecord.resultBundle);
        } catch (IllegalStateException unused) {
        }
    }

    public void loadMultipleRootTransaction(final FragmentManager fragmentManager, final int i2, final int i3, final ISupportFragment... iSupportFragmentArr) {
        enqueue(fragmentManager, new Action(4) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.3
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
                int i4 = 0;
                while (true) {
                    Object[] objArr = iSupportFragmentArr;
                    if (i4 >= objArr.length) {
                        TransactionDelegate.this.supportCommit(fragmentManager, fragmentTransactionBeginTransaction);
                        return;
                    }
                    Fragment fragment = (Fragment) objArr[i4];
                    TransactionDelegate.this.getArguments(fragment).putInt(TransactionDelegate.FRAGMENTATION_ARG_ROOT_STATUS, 1);
                    TransactionDelegate.this.bindContainerId(i2, iSupportFragmentArr[i4]);
                    fragmentTransactionBeginTransaction.add(i2, fragment, fragment.getClass().getName());
                    if (i4 != i3) {
                        fragmentTransactionBeginTransaction.hide(fragment);
                    }
                    i4++;
                }
            }
        });
    }

    public void loadRootTransaction(final FragmentManager fragmentManager, final int i2, final ISupportFragment iSupportFragment, final boolean z2, final boolean z3) {
        enqueue(fragmentManager, new Action(4) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.2
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                String str;
                TransactionDelegate.this.bindContainerId(i2, iSupportFragment);
                String name = iSupportFragment.getClass().getName();
                TransactionRecord transactionRecord = iSupportFragment.getSupportDelegate().mTransactionRecord;
                TransactionDelegate.this.start(fragmentManager, null, iSupportFragment, (transactionRecord == null || (str = transactionRecord.tag) == null) ? name : str, !z2, null, z3, 10);
            }
        });
    }

    public void pop(final FragmentManager fragmentManager) {
        enqueue(fragmentManager, new Action(1, fragmentManager) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.9
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager, "pop()");
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager);
                TransactionDelegate.this.removeTopFragment(fragmentManager);
            }
        });
    }

    public void popQuiet(final FragmentManager fragmentManager) {
        enqueue(fragmentManager, new Action(2) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.10
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                TransactionDelegate.this.mSupport.getSupportDelegate().mPopMultipleNoAnim = true;
                TransactionDelegate.this.removeTopFragment(fragmentManager);
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager);
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                TransactionDelegate.this.mSupport.getSupportDelegate().mPopMultipleNoAnim = false;
            }
        });
    }

    public void popTo(final String str, final boolean z2, final Runnable runnable, final FragmentManager fragmentManager, final int i2) {
        enqueue(fragmentManager, new Action(2) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.11
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                TransactionDelegate.this.doPopTo(str, z2, fragmentManager, i2);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
    }

    public void post(final Runnable runnable) {
        this.mActionQueue.enqueue(new Action() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.1
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                runnable.run();
            }
        });
    }

    public void remove(final FragmentManager fragmentManager, final Fragment fragment, final boolean z2) {
        enqueue(fragmentManager, new Action(1, fragmentManager) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.8
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                FragmentTransaction fragmentTransactionRemove = fragmentManager.beginTransaction().setTransition(8194).remove(fragment);
                if (z2) {
                    Object preFragment = SupportHelper.getPreFragment(fragment);
                    if (preFragment instanceof Fragment) {
                        fragmentTransactionRemove.show((Fragment) preFragment);
                    }
                }
                TransactionDelegate.this.supportCommit(fragmentManager, fragmentTransactionRemove);
            }
        });
    }

    public void showHideFragment(final FragmentManager fragmentManager, final ISupportFragment iSupportFragment, final ISupportFragment iSupportFragment2) {
        enqueue(fragmentManager, new Action() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.5
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                TransactionDelegate.this.doShowHideFragment(fragmentManager, iSupportFragment, iSupportFragment2);
            }
        });
    }

    public void startWithPop(final FragmentManager fragmentManager, final ISupportFragment iSupportFragment, final ISupportFragment iSupportFragment2) {
        enqueue(fragmentManager, new Action(2) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.6
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                ISupportFragment topFragmentForStart = TransactionDelegate.this.getTopFragmentForStart(iSupportFragment, fragmentManager);
                if (topFragmentForStart == null) {
                    throw new NullPointerException("There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment() first!");
                }
                TransactionDelegate.this.bindContainerId(topFragmentForStart.getSupportDelegate().mContainerId, iSupportFragment2);
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager, "popTo()");
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                topFragmentForStart.getSupportDelegate().mLockAnim = true;
                if (!FragmentationMagician.isStateSaved(fragmentManager)) {
                    TransactionDelegate.this.mockStartWithPopAnim(SupportHelper.getTopFragment(fragmentManager), iSupportFragment2, topFragmentForStart.getSupportDelegate().mAnimHelper.popExitAnim);
                }
                TransactionDelegate.this.removeTopFragment(fragmentManager);
                FragmentationMagician.popBackStackAllowingStateLoss(fragmentManager);
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                TransactionDelegate.this.mHandler.post(new Runnable() { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FragmentationMagician.reorderIndices(fragmentManager);
                    }
                });
            }
        });
        dispatchStartTransaction(fragmentManager, iSupportFragment, iSupportFragment2, 0, 0, 0);
    }

    public void startWithPopTo(final FragmentManager fragmentManager, final ISupportFragment iSupportFragment, final ISupportFragment iSupportFragment2, final String str, final boolean z2) {
        enqueue(fragmentManager, new Action(2) { // from class: cn.webdemo.com.supporfragment.TransactionDelegate.7
            @Override // cn.webdemo.com.supporfragment.queue.Action
            public void run() {
                boolean z3 = z2;
                List<Fragment> willPopFragments = SupportHelper.getWillPopFragments(fragmentManager, str, z3);
                ISupportFragment topFragmentForStart = TransactionDelegate.this.getTopFragmentForStart(iSupportFragment, fragmentManager);
                if (topFragmentForStart == null) {
                    throw new NullPointerException("There is no Fragment in the FragmentManager, maybe you need to call loadRootFragment() first!");
                }
                TransactionDelegate.this.bindContainerId(topFragmentForStart.getSupportDelegate().mContainerId, iSupportFragment2);
                if (willPopFragments.size() <= 0) {
                    return;
                }
                TransactionDelegate.this.handleAfterSaveInStateTransactionException(fragmentManager, "startWithPopTo()");
                FragmentationMagician.executePendingTransactionsAllowingStateLoss(fragmentManager);
                if (!FragmentationMagician.isStateSaved(fragmentManager)) {
                    TransactionDelegate.this.mockStartWithPopAnim(SupportHelper.getTopFragment(fragmentManager), iSupportFragment2, topFragmentForStart.getSupportDelegate().mAnimHelper.popExitAnim);
                }
                TransactionDelegate.this.safePopTo(str, fragmentManager, z3 ? 1 : 0, willPopFragments);
            }
        });
        dispatchStartTransaction(fragmentManager, iSupportFragment, iSupportFragment2, 0, 0, 0);
    }
}
