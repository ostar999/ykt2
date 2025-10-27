package com.plv.business.api.auxiliary;

import android.widget.ImageView;
import java.util.HashMap;

/* loaded from: classes4.dex */
public interface IPLVAuxiliaryVideoView<T> {
    void bindData(T t2);

    ImageView getAdHeadImage();

    String getHeadAdUrl();

    int getPlayStage();

    String getTailAdUrl();

    String getTeaserUrl();

    boolean hasNextHeadAd();

    void hide();

    void initOption(HashMap<String, Object> map);

    boolean isOpenHeadAd();

    boolean isOpenTailAd();

    boolean isOpenTeaser();

    boolean isShow();

    void resetPlayStage();

    void setOpenRemind(boolean z2, int i2);

    void setOpenTeaser(boolean z2);

    void show();

    void showWaittingImage(String str, boolean z2, String str2);

    void startHeadAd();

    void startTailAd();

    void startTeaser();
}
