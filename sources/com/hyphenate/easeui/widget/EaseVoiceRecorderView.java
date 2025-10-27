package com.hyphenate.easeui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.manager.EaseVoiceRecorder;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoicePlayer;

/* loaded from: classes4.dex */
public class EaseVoiceRecorderView extends RelativeLayout {
    protected Context context;
    protected LayoutInflater inflater;
    protected ImageView ivIcon;
    protected ImageView micImage;
    protected Handler micImageHandler;
    protected Drawable[] micImages;
    protected TextView recordingHint;
    protected EaseVoiceRecorder voiceRecorder;
    protected PowerManager.WakeLock wakeLock;

    public interface EaseVoiceRecorderCallback {
        void onVoiceRecordComplete(String str, int i2);
    }

    public EaseVoiceRecorderView(Context context) {
        super(context);
        this.micImageHandler = new Handler() { // from class: com.hyphenate.easeui.widget.EaseVoiceRecorderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 >= 0) {
                    EaseVoiceRecorderView easeVoiceRecorderView = EaseVoiceRecorderView.this;
                    Drawable[] drawableArr = easeVoiceRecorderView.micImages;
                    if (i2 > drawableArr.length - 1) {
                        return;
                    }
                    easeVoiceRecorderView.micImage.setImageDrawable(drawableArr[i2]);
                }
            }
        };
        init(context);
    }

    public static float dip2px(Context context, float f2) {
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_voice_recorder, this);
        this.ivIcon = (ImageView) findViewById(R.id.iv_icon);
        this.micImage = (ImageView) findViewById(R.id.mic_image);
        this.recordingHint = (TextView) findViewById(R.id.recording_hint);
        this.voiceRecorder = new EaseVoiceRecorder(this.micImageHandler);
        this.micImages = new Drawable[]{getResources().getDrawable(R.drawable.ease_record_animate_01), getResources().getDrawable(R.drawable.ease_record_animate_02), getResources().getDrawable(R.drawable.ease_record_animate_03), getResources().getDrawable(R.drawable.ease_record_animate_04)};
        if (DarkModeHelper.isDarkMode(getContext())) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ease_record_icon);
            drawable.setColorFilter(Color.parseColor("#7380a9"), PorterDuff.Mode.SRC_IN);
            this.recordingHint.setTextColor(Color.parseColor("#7380a9"));
            this.ivIcon.setImageDrawable(drawable);
            for (Drawable drawable2 : this.micImages) {
                drawable2.setColorFilter(Color.parseColor("#7380a9"), PorterDuff.Mode.SRC_IN);
            }
        } else {
            this.ivIcon.setImageResource(R.drawable.ease_record_icon);
        }
        this.wakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(6, "demo");
    }

    private void setTextContent(View view, boolean z2) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                View childAt = viewGroup.getChildAt(0);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setText(getContext().getString(z2 ? R.string.button_pushtotalk_pressed : R.string.button_pushtotalk));
                }
            }
        }
    }

    public void discardRecording() {
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        try {
            if (this.voiceRecorder.isRecording()) {
                this.voiceRecorder.discardRecording();
                setVisibility(4);
            }
        } catch (Exception unused) {
        }
    }

    public String getVoiceFileName() {
        return this.voiceRecorder.getVoiceFileName();
    }

    public String getVoiceFilePath() {
        return this.voiceRecorder.getVoiceFilePath();
    }

    public boolean isRecording() {
        return this.voiceRecorder.isRecording();
    }

    public boolean onPressToSpeakBtnTouch(View view, MotionEvent motionEvent, EaseVoiceRecorderCallback easeVoiceRecorderCallback) {
        int action = motionEvent.getAction();
        if (action == 0) {
            try {
                EaseChatRowVoicePlayer easeChatRowVoicePlayer = EaseChatRowVoicePlayer.getInstance(this.context);
                if (easeChatRowVoicePlayer.isPlaying()) {
                    easeChatRowVoicePlayer.stop();
                }
                view.setPressed(true);
                setTextContent(view, true);
                startRecording();
            } catch (Exception unused) {
                view.setPressed(false);
            }
            return true;
        }
        if (action != 1) {
            if (action != 2) {
                discardRecording();
                return false;
            }
            if (motionEvent.getY() < dip2px(getContext(), 10.0f)) {
                setTextContent(view, false);
                showReleaseToCancelHint();
            } else {
                setTextContent(view, true);
                showMoveUpToCancelHint();
            }
            return true;
        }
        view.setPressed(false);
        setTextContent(view, false);
        if (motionEvent.getY() < 0.0f) {
            discardRecording();
        } else {
            try {
                int iStopRecoding = stopRecoding();
                if (iStopRecoding > 0) {
                    if (easeVoiceRecorderCallback != null) {
                        easeVoiceRecorderCallback.onVoiceRecordComplete(getVoiceFilePath(), iStopRecoding);
                    }
                } else if (iStopRecoding == 401) {
                    Toast.makeText(this.context, R.string.Recording_without_permission, 0).show();
                } else {
                    Toast.makeText(this.context, R.string.The_recording_time_is_too_short, 0).show();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Toast.makeText(this.context, R.string.send_failure_please, 0).show();
            }
        }
        return true;
    }

    public void showMoveUpToCancelHint() {
        this.recordingHint.setText(this.context.getString(R.string.move_up_to_cancel));
        this.recordingHint.setBackgroundColor(0);
        Context context = getContext();
        int i2 = R.drawable.ease_record_icon;
        Drawable drawable = ContextCompat.getDrawable(context, i2);
        if (DarkModeHelper.isDarkMode(getContext())) {
            DarkModeHelper.setDarkModeDrawable(this.ivIcon, i2);
        } else {
            this.ivIcon.setImageDrawable(drawable);
        }
        this.micImage.setVisibility(0);
    }

    public void showReleaseToCancelHint() {
        this.recordingHint.setText(this.context.getString(R.string.release_to_cancel));
        Context context = getContext();
        int i2 = R.drawable.ease_record_cancel;
        Drawable drawable = ContextCompat.getDrawable(context, i2);
        if (DarkModeHelper.isDarkMode(getContext())) {
            DarkModeHelper.setDarkModeDrawable(this.ivIcon, i2);
        } else {
            this.ivIcon.setImageDrawable(drawable);
        }
        this.micImage.setVisibility(8);
    }

    public void startRecording() throws IllegalStateException {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(this.context, R.string.Send_voice_need_sdcard_support, 0).show();
            return;
        }
        try {
            this.wakeLock.acquire();
            setVisibility(0);
            this.recordingHint.setText(this.context.getString(R.string.move_up_to_cancel));
            this.recordingHint.setBackgroundColor(0);
            this.ivIcon.setImageResource(R.drawable.ease_record_icon);
            this.micImage.setVisibility(0);
            this.voiceRecorder.startRecording(this.context);
        } catch (Exception e2) {
            e2.printStackTrace();
            if (this.wakeLock.isHeld()) {
                this.wakeLock.release();
            }
            EaseVoiceRecorder easeVoiceRecorder = this.voiceRecorder;
            if (easeVoiceRecorder != null) {
                easeVoiceRecorder.discardRecording();
            }
            setVisibility(4);
            Toast.makeText(this.context, R.string.recoding_fail, 0).show();
        }
    }

    public int stopRecoding() {
        setVisibility(4);
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        return this.voiceRecorder.stopRecoding();
    }

    public EaseVoiceRecorderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.micImageHandler = new Handler() { // from class: com.hyphenate.easeui.widget.EaseVoiceRecorderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 >= 0) {
                    EaseVoiceRecorderView easeVoiceRecorderView = EaseVoiceRecorderView.this;
                    Drawable[] drawableArr = easeVoiceRecorderView.micImages;
                    if (i2 > drawableArr.length - 1) {
                        return;
                    }
                    easeVoiceRecorderView.micImage.setImageDrawable(drawableArr[i2]);
                }
            }
        };
        init(context);
    }

    public EaseVoiceRecorderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.micImageHandler = new Handler() { // from class: com.hyphenate.easeui.widget.EaseVoiceRecorderView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i22 = message.what;
                if (i22 >= 0) {
                    EaseVoiceRecorderView easeVoiceRecorderView = EaseVoiceRecorderView.this;
                    Drawable[] drawableArr = easeVoiceRecorderView.micImages;
                    if (i22 > drawableArr.length - 1) {
                        return;
                    }
                    easeVoiceRecorderView.micImage.setImageDrawable(drawableArr[i22]);
                }
            }
        };
        init(context);
    }
}
