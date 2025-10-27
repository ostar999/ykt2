package com.aliyun.player.alivcplayerexpand.view.gesturedialog;

import android.app.Activity;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;

/* loaded from: classes2.dex */
public class SeekDialog extends BaseGestureDialog {
    private int mFinalPosition;
    private int mInitPosition;

    public SeekDialog(Activity activity, int i2) {
        super(activity);
        this.mFinalPosition = 0;
        this.mInitPosition = i2;
        updatePosition(i2);
    }

    public int getFinalPosition() {
        return this.mFinalPosition;
    }

    public int getTargetPosition(long j2, long j3, long j4) {
        long j5 = (j2 / 1000) / 60;
        int i2 = (int) (j5 / 60);
        int i3 = (int) (j5 % 60);
        if (i2 >= 1) {
            j4 /= 10;
        } else if (i3 > 30) {
            j4 /= 5;
        } else if (i3 > 10) {
            j4 /= 3;
        } else if (i3 > 3) {
            j4 /= 2;
        }
        long j6 = j4 + j3;
        if (j6 < 0) {
            j6 = 0;
        }
        if (j6 <= j2) {
            j2 = j6;
        }
        int i4 = (int) j2;
        this.mFinalPosition = i4;
        return i4;
    }

    public void updatePosition(int i2) {
        if (i2 >= this.mInitPosition) {
            this.mImageView.setImageResource(R.drawable.alivc_seek_forward);
        } else {
            this.mImageView.setImageResource(R.drawable.alivc_seek_rewind);
        }
        this.mTextView.setText(TimeFormater.formatMs(i2));
    }
}
