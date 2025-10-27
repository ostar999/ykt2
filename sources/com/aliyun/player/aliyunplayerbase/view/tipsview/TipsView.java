package com.aliyun.player.aliyunplayerbase.view.tipsview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView;
import com.aliyun.player.aliyunplayerbase.view.tipsview.ErrorView;
import com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView;
import com.aliyun.player.aliyunplayerbase.view.tipsview.ReplayView;
import com.aliyun.player.bean.ErrorCode;

/* loaded from: classes2.dex */
public class TipsView extends RelativeLayout {
    private static final String TAG = "TipsView";
    private boolean hideBack;
    private LoadingView mBufferLoadingView;
    private CustomTipsView mCustomTipsView;
    private int mErrorCode;
    private ErrorView mErrorView;
    private NetChangeView mNetChangeView;
    private LoadingView mNetLoadingView;
    private OnTipClickListener mOnTipClickListener;
    private OnTipsViewBackClickListener mOnTipsViewBackClickListener;
    private CustomTipsView.OnTipsViewClickListener mOnTipsViewClickListener;
    private ReplayView mReplayView;
    private NetChangeView.OnNetChangeClickListener onNetChangeClickListener;
    private ReplayView.OnReplayClickListener onReplayClickListener;
    private ErrorView.OnRetryClickListener onRetryClickListener;
    private OnTipsViewBackClickListener onTipsViewBackClickListener;

    public interface OnTipClickListener {
        void onContinuePlay();

        void onExit();

        void onRefreshSts();

        void onReplay();

        void onRetryPlay(int i2);

        void onStopPlay();

        void onWait();
    }

    public TipsView(Context context) {
        super(context);
        this.mErrorView = null;
        this.mReplayView = null;
        this.mNetLoadingView = null;
        this.mNetChangeView = null;
        this.mBufferLoadingView = null;
        this.mCustomTipsView = null;
        this.mOnTipClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        this.onNetChangeClickListener = new NetChangeView.OnNetChangeClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.1
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.OnNetChangeClickListener
            public void onContinuePlay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onContinuePlay();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.OnNetChangeClickListener
            public void onStopPlay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onStopPlay();
                }
            }
        };
        this.onRetryClickListener = new ErrorView.OnRetryClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.2
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.ErrorView.OnRetryClickListener
            public void onRetryClick() {
                if (TipsView.this.mOnTipClickListener != null) {
                    if (TipsView.this.mErrorCode == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
                        TipsView.this.mOnTipClickListener.onRefreshSts();
                    } else {
                        TipsView.this.mOnTipClickListener.onRetryPlay(TipsView.this.mErrorCode);
                    }
                }
            }
        };
        this.onReplayClickListener = new ReplayView.OnReplayClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.3
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.ReplayView.OnReplayClickListener
            public void onReplay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onReplay();
                }
            }
        };
        this.onTipsViewBackClickListener = new OnTipsViewBackClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.4
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener
            public void onBackClick() {
                if (TipsView.this.mOnTipsViewBackClickListener != null) {
                    TipsView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        };
        this.mOnTipsViewClickListener = new CustomTipsView.OnTipsViewClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.5
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.OnTipsViewClickListener
            public void onExit() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onExit();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.OnTipsViewClickListener
            public void onWait() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onWait();
                }
            }
        };
        this.hideBack = false;
    }

    public void addSubView(View view) {
        addView(view, new RelativeLayout.LayoutParams(-1, -1));
    }

    public void hideAll() {
        hideCustomTipView();
        hideNetChangeTipView();
        hideErrorTipView();
        hideReplayTipView();
        hideBufferLoadingTipView();
        hideNetLoadingTipView();
    }

    public void hideBackView(boolean z2) {
        this.hideBack = z2;
    }

    public void hideBufferLoadingTipView() {
        LoadingView loadingView = this.mBufferLoadingView;
        if (loadingView == null || loadingView.getVisibility() != 0) {
            return;
        }
        this.mBufferLoadingView.updateLoadingPercent(0);
        this.mBufferLoadingView.setVisibility(4);
    }

    public void hideCustomTipView() {
        CustomTipsView customTipsView = this.mCustomTipsView;
        if (customTipsView == null || customTipsView.getVisibility() != 0) {
            return;
        }
        this.mCustomTipsView.setVisibility(4);
    }

    public void hideErrorTipView() {
        ErrorView errorView = this.mErrorView;
        if (errorView == null || errorView.getVisibility() != 0) {
            return;
        }
        this.mErrorView.setVisibility(4);
    }

    public void hideNetChangeTipView() {
        NetChangeView netChangeView = this.mNetChangeView;
        if (netChangeView == null || netChangeView.getVisibility() != 0) {
            return;
        }
        this.mNetChangeView.setVisibility(4);
    }

    public void hideNetErrorTipView() {
    }

    public void hideNetLoadingTipView() {
        LoadingView loadingView = this.mNetLoadingView;
        if (loadingView == null || loadingView.getVisibility() != 0) {
            return;
        }
        this.mNetLoadingView.setVisibility(4);
    }

    public void hideReplayTipView() {
        ReplayView replayView = this.mReplayView;
        if (replayView == null || replayView.getVisibility() != 0) {
            return;
        }
        this.mReplayView.setVisibility(4);
    }

    public boolean isErrorShow() {
        ErrorView errorView = this.mErrorView;
        return errorView != null && errorView.getVisibility() == 0;
    }

    public void setOnTipClickListener(OnTipClickListener onTipClickListener) {
        this.mOnTipClickListener = onTipClickListener;
    }

    public void setOnTipsViewBackClickListener(OnTipsViewBackClickListener onTipsViewBackClickListener) {
        this.onTipsViewBackClickListener = onTipsViewBackClickListener;
    }

    public void showBufferLoadingTipView() {
        if (this.mBufferLoadingView == null) {
            LoadingView loadingView = new LoadingView(getContext());
            this.mBufferLoadingView = loadingView;
            addSubView(loadingView);
        }
        if (this.mBufferLoadingView.getVisibility() != 0) {
            this.mBufferLoadingView.setVisibility(0);
        }
    }

    public void showCustomTipView(String str, String str2, String str3) {
        if (this.mCustomTipsView == null) {
            CustomTipsView customTipsView = new CustomTipsView(getContext());
            this.mCustomTipsView = customTipsView;
            customTipsView.setOnTipsViewClickListener(this.mOnTipsViewClickListener);
            this.mCustomTipsView.setOnBackClickListener(this.onTipsViewBackClickListener);
            this.mCustomTipsView.setTipsText(str);
            this.mCustomTipsView.setConfirmText(str2);
            this.mCustomTipsView.setCancelText(str3);
            addSubView(this.mCustomTipsView);
        }
        ErrorView errorView = this.mErrorView;
        if (errorView == null || errorView.getVisibility() != 0) {
            this.mCustomTipsView.setVisibility(0);
        }
    }

    public void showErrorTipView(int i2, String str, String str2) {
        if (this.mErrorView == null) {
            ErrorView errorView = new ErrorView(getContext());
            this.mErrorView = errorView;
            errorView.setOnRetryClickListener(this.onRetryClickListener);
            this.mErrorView.setOnBackClickListener(this.onTipsViewBackClickListener);
            addSubView(this.mErrorView);
        }
        hideNetChangeTipView();
        this.mErrorCode = i2;
        this.mErrorView.updateTips(i2, str, str2);
        this.mErrorView.setVisibility(0);
        Log.d(TAG, " errorCode = " + this.mErrorCode);
    }

    public void showErrorTipViewWithoutCode(String str) {
        if (this.mErrorView == null) {
            ErrorView errorView = new ErrorView(getContext());
            this.mErrorView = errorView;
            errorView.updateTipsWithoutCode(str);
            this.mErrorView.setOnBackClickListener(this.onTipsViewBackClickListener);
            this.mErrorView.setOnRetryClickListener(this.onRetryClickListener);
            addSubView(this.mErrorView);
        }
        if (this.mErrorView.getVisibility() != 0) {
            this.mErrorView.setVisibility(0);
        }
    }

    public void showNetChangeTipView() {
        if (this.mNetChangeView == null) {
            NetChangeView netChangeView = new NetChangeView(getContext());
            this.mNetChangeView = netChangeView;
            netChangeView.setOnNetChangeClickListener(this.onNetChangeClickListener);
            this.mNetChangeView.setOnBackClickListener(this.onTipsViewBackClickListener);
            addSubView(this.mNetChangeView);
        }
        if (this.hideBack) {
            this.mNetChangeView.hideBackView();
        } else {
            this.mNetChangeView.showBackView();
        }
        ErrorView errorView = this.mErrorView;
        if (errorView == null || errorView.getVisibility() != 0) {
            this.mNetChangeView.setVisibility(0);
        }
    }

    public void showNetLoadingTipView() {
        if (this.mNetLoadingView == null) {
            LoadingView loadingView = new LoadingView(getContext());
            this.mNetLoadingView = loadingView;
            loadingView.setOnlyLoading();
            addSubView(this.mNetLoadingView);
        }
        if (this.mNetLoadingView.getVisibility() != 0) {
            this.mNetLoadingView.setVisibility(0);
        }
    }

    public void showReplayTipView(boolean z2) {
        if (this.mReplayView == null) {
            ReplayView replayView = new ReplayView(getContext());
            this.mReplayView = replayView;
            replayView.isShowBackBtn(z2);
            this.mReplayView.setOnBackClickListener(this.onTipsViewBackClickListener);
            this.mReplayView.setOnReplayClickListener(this.onReplayClickListener);
            addSubView(this.mReplayView);
        }
        if (this.mReplayView.getVisibility() != 0) {
            this.mReplayView.setVisibility(0);
        }
    }

    public void updateLoadingPercent(int i2) {
        showBufferLoadingTipView();
        this.mBufferLoadingView.updateLoadingPercent(i2);
    }

    public TipsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mErrorView = null;
        this.mReplayView = null;
        this.mNetLoadingView = null;
        this.mNetChangeView = null;
        this.mBufferLoadingView = null;
        this.mCustomTipsView = null;
        this.mOnTipClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        this.onNetChangeClickListener = new NetChangeView.OnNetChangeClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.1
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.OnNetChangeClickListener
            public void onContinuePlay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onContinuePlay();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.OnNetChangeClickListener
            public void onStopPlay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onStopPlay();
                }
            }
        };
        this.onRetryClickListener = new ErrorView.OnRetryClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.2
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.ErrorView.OnRetryClickListener
            public void onRetryClick() {
                if (TipsView.this.mOnTipClickListener != null) {
                    if (TipsView.this.mErrorCode == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
                        TipsView.this.mOnTipClickListener.onRefreshSts();
                    } else {
                        TipsView.this.mOnTipClickListener.onRetryPlay(TipsView.this.mErrorCode);
                    }
                }
            }
        };
        this.onReplayClickListener = new ReplayView.OnReplayClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.3
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.ReplayView.OnReplayClickListener
            public void onReplay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onReplay();
                }
            }
        };
        this.onTipsViewBackClickListener = new OnTipsViewBackClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.4
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener
            public void onBackClick() {
                if (TipsView.this.mOnTipsViewBackClickListener != null) {
                    TipsView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        };
        this.mOnTipsViewClickListener = new CustomTipsView.OnTipsViewClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.5
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.OnTipsViewClickListener
            public void onExit() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onExit();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.OnTipsViewClickListener
            public void onWait() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onWait();
                }
            }
        };
        this.hideBack = false;
    }

    public TipsView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mErrorView = null;
        this.mReplayView = null;
        this.mNetLoadingView = null;
        this.mNetChangeView = null;
        this.mBufferLoadingView = null;
        this.mCustomTipsView = null;
        this.mOnTipClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        this.onNetChangeClickListener = new NetChangeView.OnNetChangeClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.1
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.OnNetChangeClickListener
            public void onContinuePlay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onContinuePlay();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.OnNetChangeClickListener
            public void onStopPlay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onStopPlay();
                }
            }
        };
        this.onRetryClickListener = new ErrorView.OnRetryClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.2
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.ErrorView.OnRetryClickListener
            public void onRetryClick() {
                if (TipsView.this.mOnTipClickListener != null) {
                    if (TipsView.this.mErrorCode == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
                        TipsView.this.mOnTipClickListener.onRefreshSts();
                    } else {
                        TipsView.this.mOnTipClickListener.onRetryPlay(TipsView.this.mErrorCode);
                    }
                }
            }
        };
        this.onReplayClickListener = new ReplayView.OnReplayClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.3
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.ReplayView.OnReplayClickListener
            public void onReplay() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onReplay();
                }
            }
        };
        this.onTipsViewBackClickListener = new OnTipsViewBackClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.4
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener
            public void onBackClick() {
                if (TipsView.this.mOnTipsViewBackClickListener != null) {
                    TipsView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        };
        this.mOnTipsViewClickListener = new CustomTipsView.OnTipsViewClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.5
            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.OnTipsViewClickListener
            public void onExit() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onExit();
                }
            }

            @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.OnTipsViewClickListener
            public void onWait() {
                if (TipsView.this.mOnTipClickListener != null) {
                    TipsView.this.mOnTipClickListener.onWait();
                }
            }
        };
        this.hideBack = false;
    }
}
