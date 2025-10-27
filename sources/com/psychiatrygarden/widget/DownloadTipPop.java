package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ExoPlayer;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.psychiatrygarden.activity.material.MaterialDownloadListActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DownloadTipPop extends FullScreenPopupView {
    private String content;
    private final Handler mHandler;
    private ViewDownloadListener mListener;
    private boolean showViewBtn;
    private boolean video;
    private String viewText;

    public interface ViewDownloadListener {
        void onClick();
    }

    public DownloadTipPop(@NonNull Context context, String text) {
        super(context);
        this.showViewBtn = true;
        this.video = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.psychiatrygarden.widget.DownloadTipPop.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DownloadTipPop.this.dismiss();
            }
        };
        this.content = text;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        ViewDownloadListener viewDownloadListener = this.mListener;
        if (viewDownloadListener != null) {
            viewDownloadListener.onClick();
        } else {
            getContext().startActivity(new Intent(view.getContext(), (Class<?>) MaterialDownloadListActivity.class));
        }
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.show_download_tips_window;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_view).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16895c.lambda$onCreate$0(view);
            }
        });
        if (!TextUtils.isEmpty(this.viewText)) {
            ((TextView) findViewById(R.id.tv_view)).setText(this.viewText);
        }
        TextView textView = (TextView) findViewById(R.id.tv_content);
        if (!this.showViewBtn) {
            findViewById(R.id.iv_icon).setVisibility(8);
            findViewById(R.id.tv_view).setVisibility(8);
            this.content = this.content.replace(" ", "").replace(HiAnalyticsConstant.REPORT_VAL_SEPARATOR, "");
        }
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            ImageView imageView = (ImageView) findViewById(R.id.iv_icon);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_white_arrow_right);
            if (drawable != null) {
                drawable.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
                imageView.setImageDrawable(drawable);
            }
        }
        textView.setText(this.content);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        this.mHandler.sendEmptyMessageDelayed(1, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public DownloadTipPop(@NonNull Context context, String text, boolean showView) {
        super(context);
        this.showViewBtn = true;
        this.video = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.psychiatrygarden.widget.DownloadTipPop.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DownloadTipPop.this.dismiss();
            }
        };
        this.showViewBtn = showView;
        this.content = text;
    }

    public DownloadTipPop(@NonNull Context context, String text, boolean showView, ViewDownloadListener l2) {
        super(context);
        this.showViewBtn = true;
        this.video = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.psychiatrygarden.widget.DownloadTipPop.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DownloadTipPop.this.dismiss();
            }
        };
        this.showViewBtn = showView;
        this.mListener = l2;
        this.content = text;
    }

    public DownloadTipPop(@NonNull Context context, String text, boolean showView, String viewText, ViewDownloadListener l2) {
        super(context);
        this.showViewBtn = true;
        this.video = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.psychiatrygarden.widget.DownloadTipPop.1
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                DownloadTipPop.this.dismiss();
            }
        };
        this.showViewBtn = showView;
        this.mListener = l2;
        this.content = text;
        this.viewText = viewText;
    }
}
