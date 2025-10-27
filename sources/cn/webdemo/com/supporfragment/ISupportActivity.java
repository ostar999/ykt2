package cn.webdemo.com.supporfragment;

import android.view.MotionEvent;
import cn.webdemo.com.supporfragment.anim.FragmentAnimator;

/* loaded from: classes.dex */
public interface ISupportActivity {
    boolean dispatchTouchEvent(MotionEvent motionEvent);

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    SupportActivityDelegate getSupportDelegate();

    void onBackPressed();

    void onBackPressedSupport();

    FragmentAnimator onCreateFragmentAnimator();

    void post(Runnable runnable);

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);
}
