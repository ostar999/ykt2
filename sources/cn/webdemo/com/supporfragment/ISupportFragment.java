package cn.webdemo.com.supporfragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.anim.FragmentAnimator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public interface ISupportFragment {
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final int SINGLETASK = 2;
    public static final int SINGLETOP = 1;
    public static final int STANDARD = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    void enqueueAction(Runnable runnable);

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    SupportFragmentDelegate getSupportDelegate();

    boolean isSupportVisible();

    boolean onBackPressedSupport();

    FragmentAnimator onCreateFragmentAnimator();

    void onEnterAnimationEnd(@Nullable Bundle bundle);

    void onFragmentResult(int i2, int i3, Bundle bundle);

    void onLazyInitView(@Nullable Bundle bundle);

    void onNewBundle(Bundle bundle);

    void onSupportInvisible();

    void onSupportVisible();

    void post(Runnable runnable);

    void putNewBundle(Bundle bundle);

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);

    void setFragmentResult(int i2, Bundle bundle);
}
