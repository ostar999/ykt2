package com.aliyun.player.alivcplayerexpand.view.gesturedialog;

import android.app.Activity;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class VolumeDialog extends BaseGestureDialog {
    private static final String TAG = "VolumeDialog";
    private float initVolume;

    public VolumeDialog(Activity activity, float f2) {
        super(activity);
        this.initVolume = f2;
        this.mImageView.setImageResource(R.drawable.alivc_volume_img);
        updateVolume(f2);
    }

    public float getTargetVolume(int i2) {
        float f2 = i2;
        float f3 = 100.0f;
        if (f2 <= 100.0f) {
            f3 = 0.0f;
            if (f2 >= 0.0f) {
                return f2;
            }
        }
        return f3;
    }

    public void updateVolume(float f2) {
        TextView textView = this.mTextView;
        StringBuilder sb = new StringBuilder();
        int i2 = (int) f2;
        sb.append(i2);
        sb.append("%");
        textView.setText(sb.toString());
        this.mImageView.setImageLevel(i2);
    }
}
