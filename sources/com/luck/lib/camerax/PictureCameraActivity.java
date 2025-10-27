package com.luck.lib.camerax;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hjq.permissions.Permission;
import com.luck.lib.camerax.listener.CameraListener;
import com.luck.lib.camerax.listener.ClickListener;
import com.luck.lib.camerax.listener.IObtainCameraView;
import com.luck.lib.camerax.listener.ImageCallbackListener;
import com.luck.lib.camerax.listener.OnSimpleXPermissionDescriptionListener;
import com.luck.lib.camerax.permissions.PermissionChecker;
import com.luck.lib.camerax.permissions.PermissionResultCallback;
import com.luck.lib.camerax.utils.SimpleXSpUtils;

/* loaded from: classes4.dex */
public class PictureCameraActivity extends AppCompatActivity implements IObtainCameraView {
    private CustomCameraView mCameraView;
    private PermissionResultCallback mPermissionResultCallback;

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCameraCancel() {
        setResult(0);
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCameraSuccess() {
        new Intent().putExtra("output", (Uri) getIntent().getParcelableExtra("output"));
        setResult(-1, getIntent());
        onBackPressed();
    }

    @Override // com.luck.lib.camerax.listener.IObtainCameraView
    public ViewGroup getCustomCameraView() {
        return this.mCameraView;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i2, int i3, @Nullable Intent intent) {
        super.onActivityResult(i2, i3, intent);
        OnSimpleXPermissionDescriptionListener onSimpleXPermissionDescriptionListener = CustomCameraConfig.explainListener;
        if (onSimpleXPermissionDescriptionListener != null) {
            onSimpleXPermissionDescriptionListener.onDismiss(this.mCameraView);
        }
        if (i2 == 1102) {
            if (PermissionChecker.checkSelfPermission(this, new String[]{Permission.CAMERA})) {
                this.mCameraView.buildUseCameraCases();
                return;
            } else {
                SimpleXSpUtils.putBoolean(this, Permission.CAMERA, true);
                handleCameraCancel();
                return;
            }
        }
        if (i2 != 1103 || PermissionChecker.checkSelfPermission(this, new String[]{Permission.RECORD_AUDIO})) {
            return;
        }
        SimpleXSpUtils.putBoolean(this, Permission.RECORD_AUDIO, true);
        Toast.makeText(getApplicationContext(), "Missing recording permission", 1).show();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        CustomCameraConfig.destroy();
        if (Build.VERSION.SDK_INT == 29) {
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mCameraView.onConfigurationChanged(configuration);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        getWindow().setFlags(1024, 1024);
        getWindow().setFlags(67108864, 67108864);
        getWindow().setFlags(134217728, 134217728);
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        getWindow().addFlags(128);
        super.onCreate(bundle);
        this.mCameraView = new CustomCameraView(this);
        this.mCameraView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        setContentView(this.mCameraView);
        this.mCameraView.post(new Runnable() { // from class: com.luck.lib.camerax.PictureCameraActivity.1
            @Override // java.lang.Runnable
            public void run() {
                PictureCameraActivity.this.mCameraView.setCameraConfig(PictureCameraActivity.this.getIntent());
            }
        });
        this.mCameraView.setImageCallbackListener(new ImageCallbackListener() { // from class: com.luck.lib.camerax.PictureCameraActivity.2
            @Override // com.luck.lib.camerax.listener.ImageCallbackListener
            public void onLoadImage(String str, ImageView imageView) {
                CameraImageEngine cameraImageEngine = CustomCameraConfig.imageEngine;
                if (cameraImageEngine != null) {
                    cameraImageEngine.loadImage(imageView.getContext(), str, imageView);
                }
            }
        });
        this.mCameraView.setCameraListener(new CameraListener() { // from class: com.luck.lib.camerax.PictureCameraActivity.3
            @Override // com.luck.lib.camerax.listener.CameraListener
            public void onError(int i2, @NonNull String str, @Nullable Throwable th) {
                Toast.makeText(PictureCameraActivity.this.getApplicationContext(), str, 1).show();
            }

            @Override // com.luck.lib.camerax.listener.CameraListener
            public void onPictureSuccess(@NonNull String str) {
                PictureCameraActivity.this.handleCameraSuccess();
            }

            @Override // com.luck.lib.camerax.listener.CameraListener
            public void onRecordSuccess(@NonNull String str) {
                PictureCameraActivity.this.handleCameraSuccess();
            }
        });
        this.mCameraView.setOnCancelClickListener(new ClickListener() { // from class: com.luck.lib.camerax.PictureCameraActivity.4
            @Override // com.luck.lib.camerax.listener.ClickListener
            public void onClick() {
                PictureCameraActivity.this.handleCameraCancel();
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mCameraView.onDestroy();
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            this.mCameraView.onCancelMedia();
        }
        return super.onKeyDown(i2, keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (this.mPermissionResultCallback != null) {
            PermissionChecker.getInstance().onRequestPermissionsResult(iArr, this.mPermissionResultCallback);
            this.mPermissionResultCallback = null;
        }
    }

    public void setPermissionsResultAction(PermissionResultCallback permissionResultCallback) {
        this.mPermissionResultCallback = permissionResultCallback;
    }
}
