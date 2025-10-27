package com.beizi.ad.internal.view;

import android.view.View;

/* loaded from: classes2.dex */
public interface c {
    void destroy();

    boolean failed();

    int getCreativeHeight();

    int getCreativeWidth();

    int getRefreshInterval();

    View getView();

    void onDestroy();

    void onPause();

    void onResume();

    void visible();
}
