package com.easefun.polyv.livecommon.module.modules.interact;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer;
import com.easefun.polyv.livecommon.ui.widget.menudrawer.Position;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVSafeWebView;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewHelper;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVInsideWebViewLayout extends FrameLayout {
    private ImageView closeIv;
    private PLVMenuDrawer landscapeMenuDrawer;
    private PLVMenuDrawer menuDrawer;
    private PLVOrientationManager.OnConfigurationChangedListener onConfigurationChangedListener;
    private PLVMenuDrawer.OnDrawerStateChangeListener onDrawerStateChangeListener;
    private ViewGroup parentLy;
    private PLVMenuDrawer portraitMenuDrawer;
    private int portraitTop;
    private PLVSafeWebView webView;

    public PLVInsideWebViewLayout(@NonNull Context context) {
        this(context, null);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_interact_inside_webview_layout, this);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.plv_webview_parent);
        this.parentLy = viewGroup;
        viewGroup.setBackgroundColor(-16777216);
        PLVSafeWebView pLVSafeWebView = new PLVSafeWebView(getContext());
        this.webView = pLVSafeWebView;
        pLVSafeWebView.setBackgroundColor(0);
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.parentLy.addView(this.webView, 0);
        PLVWebViewHelper.initWebView(getContext(), this.webView, false);
        ImageView imageView = (ImageView) findViewById(R.id.plv_close_iv);
        this.closeIv = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInsideWebViewLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVInsideWebViewLayout.this.close();
            }
        });
        observeOnOrientationChanged();
    }

    private void loadWebView(String url) {
        PLVSafeWebView pLVSafeWebView;
        if (TextUtils.isEmpty(url) || (pLVSafeWebView = this.webView) == null) {
            return;
        }
        pLVSafeWebView.loadUrl(url);
    }

    private void observeOnOrientationChanged() {
        PLVOrientationManager pLVOrientationManager = PLVOrientationManager.getInstance();
        PLVOrientationManager.OnConfigurationChangedListener onConfigurationChangedListener = new PLVOrientationManager.OnConfigurationChangedListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInsideWebViewLayout.2
            @Override // com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager.OnConfigurationChangedListener
            public void onCall(Context context, boolean isLandscape) {
                if (context == PLVInsideWebViewLayout.this.getContext() && PLVInsideWebViewLayout.this.isShowing()) {
                    if ((!isLandscape || PLVInsideWebViewLayout.this.menuDrawer == PLVInsideWebViewLayout.this.landscapeMenuDrawer) && (isLandscape || PLVInsideWebViewLayout.this.menuDrawer == PLVInsideWebViewLayout.this.portraitMenuDrawer)) {
                        return;
                    }
                    PLVInsideWebViewLayout.this.close();
                }
            }
        };
        this.onConfigurationChangedListener = onConfigurationChangedListener;
        pLVOrientationManager.addOnConfigurationChangedListener(onConfigurationChangedListener);
    }

    public void close() {
        PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
        if (pLVMenuDrawer != null) {
            pLVMenuDrawer.closeMenu();
        }
    }

    public void destroy() {
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.destroy();
            this.webView = null;
        }
        close();
        PLVOrientationManager.getInstance().removeOnConfigurationChangedListener(this.onConfigurationChangedListener);
    }

    public boolean isShowing() {
        PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
        return (pLVMenuDrawer == null || pLVMenuDrawer.getDrawerState() == 0) ? false : true;
    }

    public boolean onBackPressed() {
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null && pLVSafeWebView.canGoBack() && isShowing()) {
            this.webView.goBack();
            return true;
        }
        PLVMenuDrawer pLVMenuDrawer = this.menuDrawer;
        if (pLVMenuDrawer == null) {
            return false;
        }
        if (pLVMenuDrawer.getDrawerState() != 8 && this.menuDrawer.getDrawerState() != 4) {
            return false;
        }
        close();
        return true;
    }

    public void onPause() {
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onPause();
        }
    }

    public void onResume() {
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onResume();
        }
    }

    public void open(int portraitTop, String url, ViewGroup containerView) {
        open(portraitTop, url, containerView, PLVScreenUtils.isLandscape(getContext()));
    }

    public void setOnDrawerStateChangeListener(PLVMenuDrawer.OnDrawerStateChangeListener listener) {
        this.onDrawerStateChangeListener = listener;
    }

    public PLVInsideWebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void open(int portraitTop, String url, ViewGroup containerView, boolean isLandscape) {
        this.portraitTop = portraitTop;
        ImageView imageView = this.closeIv;
        if (imageView != null) {
            imageView.setVisibility(isLandscape ? 8 : 0);
        }
        loadWebView(url);
        View viewFindViewById = ((Activity) getContext()).findViewById(android.R.id.content);
        int iMax = Math.max(viewFindViewById.getWidth(), viewFindViewById.getHeight());
        if (this.landscapeMenuDrawer == null) {
            PLVMenuDrawer pLVMenuDrawerAttach = PLVMenuDrawer.attach((Activity) getContext(), PLVMenuDrawer.Type.OVERLAY, Position.RIGHT, 2, containerView);
            this.landscapeMenuDrawer = pLVMenuDrawerAttach;
            pLVMenuDrawerAttach.setMenuSize(ConvertUtils.dp2px(375.0f));
        }
        if (this.portraitMenuDrawer == null) {
            this.portraitMenuDrawer = PLVMenuDrawer.attach((Activity) getContext(), PLVMenuDrawer.Type.OVERLAY, Position.BOTTOM, 2, containerView);
        }
        this.portraitMenuDrawer.setMenuSize(iMax - portraitTop);
        PLVMenuDrawer pLVMenuDrawer = isLandscape ? this.landscapeMenuDrawer : this.portraitMenuDrawer;
        this.menuDrawer = pLVMenuDrawer;
        pLVMenuDrawer.setMenuView(this);
        this.menuDrawer.setTouchMode(1);
        this.menuDrawer.setDrawOverlay(false);
        this.menuDrawer.setDropShadowEnabled(false);
        this.menuDrawer.setOnDrawerStateChangeListener(new PLVMenuDrawer.OnDrawerStateChangeListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInsideWebViewLayout.3
            @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                if (PLVInsideWebViewLayout.this.onDrawerStateChangeListener != null) {
                    PLVInsideWebViewLayout.this.onDrawerStateChangeListener.onDrawerSlide(openRatio, offsetPixels);
                }
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer.OnDrawerStateChangeListener
            public void onDrawerStateChange(int oldState, int newState) {
                if (PLVInsideWebViewLayout.this.onDrawerStateChangeListener != null) {
                    PLVInsideWebViewLayout.this.onDrawerStateChangeListener.onDrawerStateChange(oldState, newState);
                }
                if (newState == 0) {
                    PLVInsideWebViewLayout.this.menuDrawer.detachToContainer();
                    if (PLVInsideWebViewLayout.this.webView != null) {
                        PLVInsideWebViewLayout.this.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                        PLVInsideWebViewLayout.this.webView.clearHistory();
                    }
                }
            }
        });
        this.menuDrawer.attachToContainer();
        this.menuDrawer.openMenu();
    }

    public PLVInsideWebViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
