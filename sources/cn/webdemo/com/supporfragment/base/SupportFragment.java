package cn.webdemo.com.supporfragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import cn.webdemo.com.supporfragment.ExtraTransaction;
import cn.webdemo.com.supporfragment.ISupportFragment;
import cn.webdemo.com.supporfragment.SupportFragmentDelegate;
import cn.webdemo.com.supporfragment.SupportHelper;
import cn.webdemo.com.supporfragment.anim.FragmentAnimator;

/* loaded from: classes.dex */
public class SupportFragment extends Fragment implements ISupportFragment {
    protected FragmentActivity _mActivity;
    final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    @Deprecated
    public void enqueueAction(Runnable runnable) {
        this.mDelegate.enqueueAction(runnable);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public ExtraTransaction extraTransaction() {
        return this.mDelegate.extraTransaction();
    }

    public <T extends ISupportFragment> T findChildFragment(Class<T> cls) {
        return (T) SupportHelper.findFragment(getChildFragmentManager(), cls);
    }

    public <T extends ISupportFragment> T findFragment(Class<T> cls) {
        return (T) SupportHelper.findFragment(getFragmentManager(), cls);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public FragmentAnimator getFragmentAnimator() {
        return this.mDelegate.getFragmentAnimator();
    }

    public ISupportFragment getPreFragment() {
        return SupportHelper.getPreFragment(this);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public SupportFragmentDelegate getSupportDelegate() {
        return this.mDelegate;
    }

    public ISupportFragment getTopChildFragment() {
        return SupportHelper.getTopFragment(getChildFragmentManager());
    }

    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getFragmentManager());
    }

    public void hideSoftInput() {
        this.mDelegate.hideSoftInput();
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public final boolean isSupportVisible() {
        return this.mDelegate.isSupportVisible();
    }

    public void loadMultipleRootFragment(int i2, int i3, ISupportFragment... iSupportFragmentArr) {
        this.mDelegate.loadMultipleRootFragment(i2, i3, iSupportFragmentArr);
    }

    public void loadRootFragment(int i2, ISupportFragment iSupportFragment) {
        this.mDelegate.loadRootFragment(i2, iSupportFragment);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mDelegate.onActivityCreated(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mDelegate.onAttach(activity);
        this._mActivity = this.mDelegate.getActivity();
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public boolean onBackPressedSupport() {
        return this.mDelegate.onBackPressedSupport();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int i2, boolean z2, int i3) {
        return this.mDelegate.onCreateAnimation(i2, z2, i3);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public FragmentAnimator onCreateFragmentAnimator() {
        return this.mDelegate.onCreateFragmentAnimator();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mDelegate.onDestroy();
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mDelegate.onDestroyView();
        super.onDestroyView();
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void onEnterAnimationEnd(Bundle bundle) {
        this.mDelegate.onEnterAnimationEnd(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void onFragmentResult(int i2, int i3, Bundle bundle) {
        this.mDelegate.onFragmentResult(i2, i3, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z2) {
        super.onHiddenChanged(z2);
        this.mDelegate.onHiddenChanged(z2);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle bundle) {
        this.mDelegate.onLazyInitView(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void onNewBundle(Bundle bundle) {
        this.mDelegate.onNewBundle(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mDelegate.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mDelegate.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mDelegate.onSaveInstanceState(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportInvisible() {
        this.mDelegate.onSupportInvisible();
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        this.mDelegate.onSupportVisible();
    }

    public void pop() {
        this.mDelegate.pop();
    }

    public void popChild() {
        this.mDelegate.popChild();
    }

    public void popTo(Class<?> cls, boolean z2) {
        this.mDelegate.popTo(cls, z2);
    }

    public void popToChild(Class<?> cls, boolean z2) {
        this.mDelegate.popToChild(cls, z2);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void post(Runnable runnable) {
        this.mDelegate.post(runnable);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void putNewBundle(Bundle bundle) {
        this.mDelegate.putNewBundle(bundle);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z2) {
        this.mDelegate.replaceFragment(iSupportFragment, z2);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportFragment
    public void setFragmentResult(int i2, Bundle bundle) {
        this.mDelegate.setFragmentResult(i2, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z2) {
        super.setUserVisibleHint(z2);
        this.mDelegate.setUserVisibleHint(z2);
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        this.mDelegate.showHideFragment(iSupportFragment);
    }

    public void showSoftInput(View view) {
        this.mDelegate.showSoftInput(view);
    }

    public void start(ISupportFragment iSupportFragment) {
        this.mDelegate.start(iSupportFragment);
    }

    public void startForResult(ISupportFragment iSupportFragment, int i2) {
        this.mDelegate.startForResult(iSupportFragment, i2);
    }

    public void startWithPop(ISupportFragment iSupportFragment) {
        this.mDelegate.startWithPop(iSupportFragment);
    }

    public void startWithPopTo(ISupportFragment iSupportFragment, Class<?> cls, boolean z2) {
        this.mDelegate.startWithPopTo(iSupportFragment, cls, z2);
    }

    public void loadRootFragment(int i2, ISupportFragment iSupportFragment, boolean z2, boolean z3) {
        this.mDelegate.loadRootFragment(i2, iSupportFragment, z2, z3);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable) {
        this.mDelegate.popTo(cls, z2, runnable);
    }

    public void popToChild(Class<?> cls, boolean z2, Runnable runnable) {
        this.mDelegate.popToChild(cls, z2, runnable);
    }

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mDelegate.showHideFragment(iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment, int i2) {
        this.mDelegate.start(iSupportFragment, i2);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable, int i2) {
        this.mDelegate.popTo(cls, z2, runnable, i2);
    }

    public void popToChild(Class<?> cls, boolean z2, Runnable runnable, int i2) {
        this.mDelegate.popToChild(cls, z2, runnable, i2);
    }
}
