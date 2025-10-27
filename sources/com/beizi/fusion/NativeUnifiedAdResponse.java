package com.beizi.fusion;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/* loaded from: classes2.dex */
public interface NativeUnifiedAdResponse {
    String getActionText();

    String getDescription();

    int getECPM();

    String getIconUrl();

    String getImageUrl();

    List<String> getImgList();

    int getMaterialType();

    String getTitle();

    View getVideoView();

    ViewGroup getViewContainer();

    boolean isVideo();

    void registerViewForInteraction(List<View> list);
}
