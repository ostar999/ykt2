package com.luck.picture.lib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.ToastUtils;

/* loaded from: classes4.dex */
public class PictureOnlyCameraFragment extends PictureCommonFragment {
    public static final String TAG = "PictureOnlyCameraFragment";

    public static PictureOnlyCameraFragment newInstance() {
        return new PictureOnlyCameraFragment();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void dispatchCameraMediaResult(LocalMedia localMedia) {
        SelectedManager.getSelectedResult().add(localMedia);
        dispatchTransformResult();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment
    public String getFragmentTag() {
        return TAG;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int getResourceId() {
        return R.layout.ps_empty;
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionSettingResult() {
        boolean zIsCheckCamera;
        OnPermissionsInterceptListener onPermissionsInterceptListener = PictureSelectionConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null) {
            zIsCheckCamera = onPermissionsInterceptListener.hasPermissions(this);
        } else {
            zIsCheckCamera = PermissionChecker.isCheckCamera(getContext());
            if (!SdkVersionUtils.isQ()) {
                zIsCheckCamera = PermissionChecker.isCheckWriteStorage(getContext());
            }
        }
        if (zIsCheckCamera) {
            openSelectedCamera();
            return;
        }
        if (!PermissionChecker.isCheckCamera(getContext())) {
            ToastUtils.showToast(getContext(), getString(R.string.ps_camera));
        } else if (!PermissionChecker.isCheckWriteStorage(getContext())) {
            ToastUtils.showToast(getContext(), getString(R.string.ps_jurisdiction));
        }
        onBackOffFragment();
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == 0 && i2 == 909) {
            onBackOffFragment();
            onSelectFinish(0, null);
        }
    }

    @Override // com.luck.picture.lib.basic.PictureCommonFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (SdkVersionUtils.isQ()) {
            openSelectedCamera();
        } else {
            PermissionChecker.getInstance().requestPermissions(this, PermissionConfig.WRITE_EXTERNAL_STORAGE, new PermissionResultCallback() { // from class: com.luck.picture.lib.PictureOnlyCameraFragment.1
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureOnlyCameraFragment.this.handlePermissionDenied(PermissionConfig.WRITE_EXTERNAL_STORAGE);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureOnlyCameraFragment.this.openSelectedCamera();
                }
            });
        }
    }
}
