package com.uuzuche.lib_zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class CaptureActivity extends FragmentActivity {
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() { // from class: com.uuzuche.lib_zxing.activity.CaptureActivity.3
        @Override // com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
        public void onAnalyzeFailed() {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, 2);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            intent.putExtras(bundle);
            CaptureActivity.this.setResult(-1, intent);
            CaptureActivity.this.finish();
        }

        @Override // com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
        public void onAnalyzeSuccess(Bitmap bitmap, String str) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, 1);
            bundle.putString(CodeUtils.RESULT_STRING, str);
            intent.putExtras(bundle);
            CaptureActivity.this.setResult(-1, intent);
            CaptureActivity.this.finish();
        }
    };

    private boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static void setCameraDisplayOrientation(Activity activity, int i2, Camera camera) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i2, cameraInfo);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int i3 = 0;
        if (rotation != 0) {
            if (rotation == 1) {
                i3 = 90;
            } else if (rotation == 2) {
                i3 = 180;
            } else if (rotation == 3) {
                i3 = 270;
            }
        }
        camera.setDisplayOrientation(cameraInfo.facing == 1 ? (360 - ((cameraInfo.orientation + i3) % 360)) % 360 : ((cameraInfo.orientation - i3) + 360) % 360);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        getWindow().clearFlags(67108864);
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triangleHeight);
        getWindow().setStatusBarColor(0);
        super.onCreate(bundle);
        setContentView(R.layout.camera);
        int i2 = R.id.fl_zxing_container;
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.uuzuche.lib_zxing.activity.CaptureActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CaptureActivity.this.finish();
            }
        });
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(this.analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(i2, captureFragment).commit();
        captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() { // from class: com.uuzuche.lib_zxing.activity.CaptureActivity.2
            @Override // com.uuzuche.lib_zxing.activity.CaptureFragment.CameraInitCallBack
            public void callBack(Exception exc) {
                if (exc == null) {
                    return;
                }
                Log.e("TAG", "callBack: ", exc);
            }
        });
        if (isTablet()) {
            setRequestedOrientation(14);
        } else {
            setRequestedOrientation(-1);
        }
    }
}
