package cn.webdemo.com.supporfragment.base;

import android.os.Bundle;
import android.view.MotionEvent;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.webdemo.com.supporfragment.ExtraTransaction;
import cn.webdemo.com.supporfragment.ISupportActivity;
import cn.webdemo.com.supporfragment.ISupportFragment;
import cn.webdemo.com.supporfragment.SupportActivityDelegate;
import cn.webdemo.com.supporfragment.SupportHelper;
import cn.webdemo.com.supporfragment.anim.FragmentAnimator;

/* loaded from: classes.dex */
public class SupportActivity extends AppCompatActivity implements ISupportActivity {
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    @Override // android.app.Activity, android.view.Window.Callback, cn.webdemo.com.supporfragment.ISupportActivity
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.dispatchTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public ExtraTransaction extraTransaction() {
        return this.mDelegate.extraTransaction();
    }

    public <T extends ISupportFragment> T findFragment(Class<T> cls) {
        return (T) SupportHelper.findFragment(getSupportFragmentManager(), cls);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public FragmentAnimator getFragmentAnimator() {
        return this.mDelegate.getFragmentAnimator();
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public SupportActivityDelegate getSupportDelegate() {
        return this.mDelegate;
    }

    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    public void loadMultipleRootFragment(int i2, int i3, ISupportFragment... iSupportFragmentArr) {
        this.mDelegate.loadMultipleRootFragment(i2, i3, iSupportFragmentArr);
    }

    public void loadRootFragment(int i2, @NonNull ISupportFragment iSupportFragment) {
        this.mDelegate.loadRootFragment(i2, iSupportFragment);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        this.mDelegate.onBackPressed();
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public void onBackPressedSupport() {
        this.mDelegate.onBackPressedSupport();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public FragmentAnimator onCreateFragmentAnimator() {
        return this.mDelegate.onCreateFragmentAnimator();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mDelegate.onDestroy();
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
        this.mDelegate.onPostCreate(bundle);
    }

    public void pop() {
        this.mDelegate.pop();
    }

    public void popTo(Class<?> cls, boolean z2) {
        this.mDelegate.popTo(cls, z2);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public void post(Runnable runnable) {
        this.mDelegate.post(runnable);
    }

    public void replaceFragment(ISupportFragment iSupportFragment, boolean z2) {
        this.mDelegate.replaceFragment(iSupportFragment, z2);
    }

    public void setDefaultFragmentBackground(@DrawableRes int i2) {
        this.mDelegate.setDefaultFragmentBackground(i2);
    }

    @Override // cn.webdemo.com.supporfragment.ISupportActivity
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        this.mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    public void showHideFragment(ISupportFragment iSupportFragment) {
        this.mDelegate.showHideFragment(iSupportFragment);
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

    public void showHideFragment(ISupportFragment iSupportFragment, ISupportFragment iSupportFragment2) {
        this.mDelegate.showHideFragment(iSupportFragment, iSupportFragment2);
    }

    public void start(ISupportFragment iSupportFragment, int i2) {
        this.mDelegate.start(iSupportFragment, i2);
    }

    public void popTo(Class<?> cls, boolean z2, Runnable runnable, int i2) {
        this.mDelegate.popTo(cls, z2, runnable, i2);
    }
}
