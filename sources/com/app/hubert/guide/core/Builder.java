package com.app.hubert.guide.core;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class Builder {
    Activity activity;
    boolean alwaysShow;
    View anchor;
    Fragment fragment;
    String label;
    OnGuideChangedListener onGuideChangedListener;
    OnPageChangedListener onPageChangedListener;
    int showCounts = 1;
    List<GuidePage> guidePages = new ArrayList();

    public Builder(Activity activity) {
        this.activity = activity;
    }

    private void check() {
        if (TextUtils.isEmpty(this.label)) {
            throw new IllegalArgumentException("the param 'label' is missing, please call setLabel()");
        }
        if (this.activity == null && this.fragment != null) {
            throw new IllegalStateException("activity is null, please make sure that fragment is showing when call NewbieGuide");
        }
    }

    public Builder addGuidePage(GuidePage guidePage) {
        this.guidePages.add(guidePage);
        return this;
    }

    public Builder alwaysShow(boolean z2) {
        this.alwaysShow = z2;
        return this;
    }

    public Builder anchor(View view) {
        this.anchor = view;
        return this;
    }

    public Controller build() {
        check();
        return new Controller(this);
    }

    public Builder setLabel(String str) {
        this.label = str;
        return this;
    }

    public Builder setOnGuideChangedListener(OnGuideChangedListener onGuideChangedListener) {
        this.onGuideChangedListener = onGuideChangedListener;
        return this;
    }

    public Builder setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
        this.onPageChangedListener = onPageChangedListener;
        return this;
    }

    public Builder setShowCounts(int i2) {
        this.showCounts = i2;
        return this;
    }

    public Controller show() {
        check();
        Controller controller = new Controller(this);
        controller.show();
        return controller;
    }

    public Builder(Fragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
    }
}
