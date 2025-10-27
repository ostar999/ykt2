package com.aliyun.player.alivcplayerexpand.view.gesture;

import android.app.Activity;
import android.view.View;
import com.aliyun.player.alivcplayerexpand.view.gesturedialog.BrightnessDialog;
import com.aliyun.player.alivcplayerexpand.view.gesturedialog.SeekDialog;
import com.aliyun.player.alivcplayerexpand.view.gesturedialog.VolumeDialog;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;

/* loaded from: classes2.dex */
public class GestureDialogManager {
    private Activity mActivity;
    private SeekDialog mSeekDialog = null;
    private BrightnessDialog mBrightnessDialog = null;
    private VolumeDialog mVolumeDialog = null;
    private AliyunScreenMode mCurrentScreenMode = AliyunScreenMode.Small;

    public GestureDialogManager(Activity activity) {
        this.mActivity = activity;
    }

    public void dismissBrightnessDialog() {
        BrightnessDialog brightnessDialog = this.mBrightnessDialog;
        if (brightnessDialog != null && brightnessDialog.isShowing()) {
            this.mBrightnessDialog.dismiss();
        }
        this.mBrightnessDialog = null;
    }

    public int dismissSeekDialog() {
        int finalPosition;
        SeekDialog seekDialog = this.mSeekDialog;
        if (seekDialog == null || !seekDialog.isShowing()) {
            finalPosition = -1;
        } else {
            finalPosition = this.mSeekDialog.getFinalPosition();
            this.mSeekDialog.dismiss();
        }
        this.mSeekDialog = null;
        return finalPosition;
    }

    public void dismissVolumeDialog() {
        VolumeDialog volumeDialog = this.mVolumeDialog;
        if (volumeDialog != null && volumeDialog.isShowing()) {
            this.mVolumeDialog.dismiss();
        }
        this.mVolumeDialog = null;
    }

    public void initDialog(Activity activity, float f2) {
        this.mActivity = activity;
        if (this.mVolumeDialog == null) {
            this.mVolumeDialog = new VolumeDialog(activity, f2);
        }
    }

    public boolean isVolumeDialogIsShow() {
        VolumeDialog volumeDialog = this.mVolumeDialog;
        if (volumeDialog == null) {
            return false;
        }
        return volumeDialog.isShowing();
    }

    public void setCurrentScreenMode(AliyunScreenMode aliyunScreenMode) {
        this.mCurrentScreenMode = aliyunScreenMode;
    }

    public void showBrightnessDialog(View view, int i2) {
        if (this.mBrightnessDialog == null) {
            this.mBrightnessDialog = new BrightnessDialog(this.mActivity, i2);
        }
        if (this.mBrightnessDialog.isShowing()) {
            return;
        }
        this.mBrightnessDialog.setScreenMode(this.mCurrentScreenMode);
        this.mBrightnessDialog.show(view);
        this.mBrightnessDialog.updateBrightness(i2);
    }

    public void showSeekDialog(View view, int i2) {
        if (this.mSeekDialog == null) {
            this.mSeekDialog = new SeekDialog(this.mActivity, i2);
        }
        if (this.mSeekDialog.isShowing()) {
            return;
        }
        this.mSeekDialog.show(view);
        this.mSeekDialog.updatePosition(i2);
    }

    public void showVolumeDialog(View view, float f2) {
        if (this.mVolumeDialog == null) {
            this.mVolumeDialog = new VolumeDialog(this.mActivity, f2);
        }
        if (this.mVolumeDialog.isShowing()) {
            return;
        }
        this.mVolumeDialog.setScreenMode(this.mCurrentScreenMode);
        this.mVolumeDialog.show(view);
        this.mVolumeDialog.updateVolume(f2);
    }

    public int updateBrightnessDialog(int i2) {
        int targetBrightnessPercent = this.mBrightnessDialog.getTargetBrightnessPercent(i2);
        this.mBrightnessDialog.updateBrightness(targetBrightnessPercent);
        return targetBrightnessPercent;
    }

    public void updateSeekDialog(long j2, long j3, long j4) {
        this.mSeekDialog.updatePosition(this.mSeekDialog.getTargetPosition(j2, j3, j4));
    }

    public float updateVolumeDialog(int i2) {
        float targetVolume = this.mVolumeDialog.getTargetVolume(i2);
        this.mVolumeDialog.updateVolume(targetVolume);
        return targetVolume;
    }
}
