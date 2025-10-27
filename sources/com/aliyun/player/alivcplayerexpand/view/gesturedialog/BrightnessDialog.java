package com.aliyun.player.alivcplayerexpand.view.gesturedialog;

import android.app.Activity;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class BrightnessDialog extends BaseGestureDialog {
    private static final String TAG = "BrightnessDialog";
    private int mCurrentBrightness;

    public BrightnessDialog(Activity activity, int i2) {
        super(activity);
        this.mCurrentBrightness = i2;
        this.mImageView.setImageResource(R.drawable.alivc_brightness);
        updateBrightness(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0012 A[PHI: r0
      0x0012: PHI (r0v3 float) = (r0v0 float), (r0v1 float) binds: [B:4:0x0010, B:7:0x0019] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getActivityBrightness(android.app.Activity r2) {
        /*
            if (r2 == 0) goto L21
            android.view.Window r2 = r2.getWindow()
            android.view.WindowManager$LayoutParams r2 = r2.getAttributes()
            float r2 = r2.screenBrightness
            r0 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r1 <= 0) goto L14
        L12:
            r2 = r0
            goto L1c
        L14:
            r0 = 1036831949(0x3dcccccd, float:0.1)
            int r1 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r1 >= 0) goto L1c
            goto L12
        L1c:
            r0 = 1120403456(0x42c80000, float:100.0)
            float r2 = r2 * r0
            int r2 = (int) r2
            return r2
        L21:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.alivcplayerexpand.view.gesturedialog.BrightnessDialog.getActivityBrightness(android.app.Activity):int");
    }

    public int getTargetBrightnessPercent(int i2) {
        int i3 = this.mCurrentBrightness - i2;
        if (i3 > 100) {
            return 100;
        }
        if (i3 < 0) {
            return 0;
        }
        return i3;
    }

    public void updateBrightness(int i2) {
        this.mTextView.setText(i2 + "%");
    }
}
