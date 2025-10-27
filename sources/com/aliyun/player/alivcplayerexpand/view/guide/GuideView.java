package com.aliyun.player.alivcplayerexpand.view.guide;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.theme.ITheme;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;

/* loaded from: classes2.dex */
public class GuideView extends LinearLayout implements ITheme {
    private TextView mBrightText;
    private TextView mProgressText;
    private TextView mVolumeText;

    public GuideView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#88000000"));
        setGravity(17);
        setOrientation(1);
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_guide, (ViewGroup) this, true);
        this.mBrightText = (TextView) findViewById(R.id.bright_text);
        this.mProgressText = (TextView) findViewById(R.id.progress_text);
        this.mVolumeText = (TextView) findViewById(R.id.volume_text);
        hide();
    }

    public void hide() {
        setVisibility(8);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        hide();
        return true;
    }

    public void setScreenMode(AliyunScreenMode aliyunScreenMode) {
        if (aliyunScreenMode == AliyunScreenMode.Small) {
            hide();
            return;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("alivc_guide_record", 0);
        if (sharedPreferences.getBoolean("has_shown", false)) {
            return;
        }
        setVisibility(0);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putBoolean("has_shown", true);
        editorEdit.apply();
    }

    @Override // com.aliyun.player.alivcplayerexpand.theme.ITheme
    public void setTheme(Theme theme) {
        int i2 = R.color.alivc_blue;
        if (theme != Theme.Blue) {
            if (theme == Theme.Green) {
                i2 = R.color.alivc_green;
            } else if (theme == Theme.Orange) {
                i2 = R.color.alivc_orange;
            } else if (theme == Theme.Red) {
                i2 = R.color.alivc_red;
            }
        }
        int color = ContextCompat.getColor(getContext(), i2);
        this.mBrightText.setTextColor(color);
        this.mProgressText.setTextColor(color);
        this.mVolumeText.setTextColor(color);
    }

    public GuideView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public GuideView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
