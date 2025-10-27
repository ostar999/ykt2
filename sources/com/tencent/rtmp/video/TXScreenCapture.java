package com.tencent.rtmp.video;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.screencapture.c;

/* loaded from: classes6.dex */
public class TXScreenCapture {

    @TargetApi(21)
    public static class TXScreenCaptureAssistantActivity extends Activity {
        private static final int REQUEST_CODE = 100;
        private static final String TAG = "TXScreenCaptureAssistantActivity";
        private MediaProjectionManager mMediaProjectionManager;

        @Override // android.app.Activity
        public void onActivityResult(int i2, int i3, Intent intent) {
            TXCLog.i(TAG, "onActivityResult " + this);
            c.a(this).a(this.mMediaProjectionManager.getMediaProjection(i3, intent));
            finish();
        }

        @Override // android.app.Activity
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            TXCLog.i(TAG, "onCreate " + this);
            requestWindowFeature(1);
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService("media_projection");
            this.mMediaProjectionManager = mediaProjectionManager;
            try {
                startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 100);
            } catch (Exception e2) {
                TXCLog.e(TAG, "start permission activity failed. " + e2);
                c.a(this).a((MediaProjection) null);
                finish();
            }
        }

        @Override // android.app.Activity
        public void onDestroy() {
            super.onDestroy();
            TXCLog.i(TAG, "onDestroy " + this);
        }
    }
}
