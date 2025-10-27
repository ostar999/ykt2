package com.actionbarsherlock.widget.swipeback;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public View findViewById(int i2) {
        SwipeBackActivityHelper swipeBackActivityHelper;
        View viewFindViewById = super.findViewById(i2);
        return (viewFindViewById != null || (swipeBackActivityHelper = this.mHelper) == null) ? viewFindViewById : swipeBackActivityHelper.findViewById(i2);
    }

    @Override // com.actionbarsherlock.widget.swipeback.SwipeBackActivityBase
    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SwipeBackActivityHelper swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        this.mHelper = swipeBackActivityHelper;
        swipeBackActivityHelper.onActivityCreate();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.mHelper.onPostCreate();
    }

    @Override // com.actionbarsherlock.widget.swipeback.SwipeBackActivityBase
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override // com.actionbarsherlock.widget.swipeback.SwipeBackActivityBase
    public void setSwipeBackEnable(boolean z2) {
        getSwipeBackLayout().setEnableGesture(z2);
    }
}
