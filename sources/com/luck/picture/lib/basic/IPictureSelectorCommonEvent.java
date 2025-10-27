package com.luck.picture.lib.basic;

import android.content.Intent;
import android.os.Bundle;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public interface IPictureSelectorCommonEvent {
    boolean checkOnlyMimeTypeValidity(boolean z2, String str, String str2, long j2);

    boolean checkWithMimeTypeValidity(boolean z2, String str, int i2, long j2);

    int confirmSelect(LocalMedia localMedia, boolean z2);

    void dismissLoading();

    void dispatchCameraMediaResult(LocalMedia localMedia);

    int getResourceId();

    void handlePermissionDenied(String[] strArr);

    void handlePermissionSettingResult();

    void initAppLanguage();

    void onCheckOriginalChange();

    void onEditMedia(Intent intent);

    void onEnterFragment();

    void onExitFragment();

    void onFixedSelectedChange(LocalMedia localMedia);

    void onFragmentResume();

    void onKeyBackFragment();

    void onRecreateEngine();

    void onResultEvent(ArrayList<LocalMedia> arrayList);

    void onSelectedChange(boolean z2, LocalMedia localMedia);

    void onSelectedOnlyCamera();

    void openImageCamera();

    void openSelectedCamera();

    void openSoundRecording();

    void openVideoCamera();

    void reStartSavedInstance(Bundle bundle);

    void sendChangeSubSelectPositionEvent(boolean z2);

    void sendFixedSelectedChangeEvent(LocalMedia localMedia);

    void sendSelectedChangeEvent(boolean z2, LocalMedia localMedia);

    void sendSelectedOriginalChangeEvent();

    void showLoading();
}
