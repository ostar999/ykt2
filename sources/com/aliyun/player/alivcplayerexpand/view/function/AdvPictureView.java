package com.aliyun.player.alivcplayerexpand.view.function;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.svideo.common.utils.image.ImageLoaderImpl;
import com.aliyun.svideo.common.utils.image.ImageLoaderOptions;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class AdvPictureView extends RelativeLayout {
    private boolean isShowCenterAdv;
    private ImageView mAdvImageView;
    private RelativeLayout mAdvPictureRootRelativeLayout;
    private String mAdvPictureUrl;
    private ImageView mBackImageView;
    private CountDownHandler mCountDownHandler;
    private TextView mCountDownTextView;
    private int mCountDownTime;
    private int mCurrentCountDownTime;
    private int mHeight;
    private boolean mIsCountDown;
    private OnAdvPictureListener mOnAdvPictureListener;
    private int mWidth;

    public static class CountDownHandler extends Handler {
        private WeakReference<AdvPictureView> weakreference;

        public CountDownHandler(AdvPictureView advPictureView) {
            this.weakreference = new WeakReference<>(advPictureView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            AdvPictureView advPictureView = this.weakreference.get();
            if (advPictureView != null) {
                int i2 = message.what;
                advPictureView.mCurrentCountDownTime = i2;
                if (i2 <= 0) {
                    advPictureView.mCountDownHandler.removeCallbacksAndMessages(null);
                    if (advPictureView.mOnAdvPictureListener != null) {
                        advPictureView.mIsCountDown = false;
                        advPictureView.mOnAdvPictureListener.close();
                        return;
                    }
                    return;
                }
                advPictureView.mIsCountDown = true;
                advPictureView.mCountDownTextView.setText(Html.fromHtml("<font color='#00c1de'>" + i2 + "&nbsp;&nbsp;</font><font color='#FFFFFF'>" + advPictureView.getContext().getString(R.string.alivc_check_list_close) + "</font>"));
                Message messageObtain = Message.obtain();
                messageObtain.what = i2 + (-1);
                advPictureView.mCountDownHandler.sendMessageDelayed(messageObtain, 1000L);
            }
        }
    }

    public interface OnAdvPictureListener {
        void close();

        void finish();

        void onClick();
    }

    public AdvPictureView(Context context) {
        super(context);
        this.isShowCenterAdv = false;
        this.mCountDownTime = 5;
        this.mCurrentCountDownTime = 5;
        init(context);
    }

    private void findAllView() {
        this.mAdvImageView = (ImageView) findViewById(R.id.iv_adv);
        this.mBackImageView = (ImageView) findViewById(R.id.alivc_back);
        this.mCountDownTextView = (TextView) findViewById(R.id.tv_count_down);
        this.mAdvPictureRootRelativeLayout = (RelativeLayout) findViewById(R.id.rl_adv_picture_root);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.alivc_view_adv_picture, (ViewGroup) this, true);
        findAllView();
        initCountDown();
        initListener();
    }

    private void initCountDown() {
        CountDownHandler countDownHandler = new CountDownHandler(this);
        this.mCountDownHandler = countDownHandler;
        countDownHandler.sendEmptyMessage(this.mCountDownTime);
    }

    private void initListener() {
        this.mCountDownTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AdvPictureView.this.mOnAdvPictureListener != null) {
                    if (AdvPictureView.this.mCountDownHandler != null) {
                        AdvPictureView.this.mCountDownHandler.removeCallbacksAndMessages(null);
                    }
                    AdvPictureView.this.mIsCountDown = false;
                    AdvPictureView.this.mOnAdvPictureListener.close();
                }
            }
        });
        this.mAdvImageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AdvPictureView.this.mOnAdvPictureListener != null) {
                    AdvPictureView.this.mOnAdvPictureListener.onClick();
                }
            }
        });
        this.mBackImageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.function.AdvPictureView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AdvPictureView.this.mOnAdvPictureListener != null) {
                    AdvPictureView.this.mOnAdvPictureListener.finish();
                }
            }
        });
    }

    private void initPicture() {
        if (this.mAdvImageView != null) {
            new ImageLoaderImpl().loadImage(getContext(), this.mAdvPictureUrl, new ImageLoaderOptions.Builder().crossFade().centerCrop().error(R.drawable.alivc_player_adv_picture).build()).into(this.mAdvImageView);
        }
    }

    public void cancel() {
        CountDownHandler countDownHandler = this.mCountDownHandler;
        if (countDownHandler != null) {
            countDownHandler.removeCallbacksAndMessages(null);
        }
    }

    public void hideAll() {
        RelativeLayout relativeLayout = this.mAdvPictureRootRelativeLayout;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
            this.isShowCenterAdv = false;
        }
    }

    public boolean isInCountDown() {
        return this.mIsCountDown;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CountDownHandler countDownHandler = this.mCountDownHandler;
        if (countDownHandler != null) {
            countDownHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (z2 && this.isShowCenterAdv) {
            showCenterAdv();
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.mWidth = i2;
        this.mHeight = i3;
    }

    public void reStart() {
        CountDownHandler countDownHandler = this.mCountDownHandler;
        if (countDownHandler != null) {
            countDownHandler.sendEmptyMessage(this.mCurrentCountDownTime);
        }
    }

    public void recovery() {
        ImageView imageView = this.mAdvImageView;
        if (imageView != null) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.mAdvImageView.setLayoutParams(layoutParams);
        }
        TextView textView = this.mCountDownTextView;
        if (textView != null) {
            textView.setVisibility(0);
        }
        CountDownHandler countDownHandler = this.mCountDownHandler;
        if (countDownHandler != null) {
            countDownHandler.removeCallbacksAndMessages(null);
        }
        this.mCountDownTime = 5;
        initCountDown();
        this.isShowCenterAdv = false;
    }

    public void setAdvPictureUrl(String str) {
        this.mAdvPictureUrl = str;
        initPicture();
    }

    public void setOnAdvPictureListener(OnAdvPictureListener onAdvPictureListener) {
        this.mOnAdvPictureListener = onAdvPictureListener;
    }

    public void showAll() {
        recovery();
        RelativeLayout relativeLayout = this.mAdvPictureRootRelativeLayout;
        if (relativeLayout != null && !relativeLayout.isShown()) {
            this.mAdvPictureRootRelativeLayout.setVisibility(0);
        }
        ImageView imageView = this.mBackImageView;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
    }

    public void showCenterAdv() {
        ImageView imageView = this.mAdvImageView;
        if (imageView != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height = this.mHeight / 2;
            layoutParams.width = this.mWidth / 2;
            layoutParams.addRule(13);
            this.mAdvImageView.setLayoutParams(layoutParams);
            RelativeLayout relativeLayout = this.mAdvPictureRootRelativeLayout;
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            TextView textView = this.mCountDownTextView;
            if (textView != null) {
                textView.setVisibility(8);
            }
            this.mAdvImageView.invalidate();
            this.isShowCenterAdv = true;
        }
        ImageView imageView2 = this.mBackImageView;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
    }

    public void stop() {
        cancel();
    }

    public AdvPictureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isShowCenterAdv = false;
        this.mCountDownTime = 5;
        this.mCurrentCountDownTime = 5;
        init(context);
    }

    public AdvPictureView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isShowCenterAdv = false;
        this.mCountDownTime = 5;
        this.mCurrentCountDownTime = 5;
        init(context);
    }
}
