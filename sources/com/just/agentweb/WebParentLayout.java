package com.just.agentweb;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class WebParentLayout extends FrameLayout implements Provider<AbsAgentWebUIController> {
    private static final String TAG = "WebParentLayout";
    private AbsAgentWebUIController mAgentWebUIController;

    @IdRes
    private int mClickId;
    private FrameLayout mErrorLayout;

    @LayoutRes
    private int mErrorLayoutRes;
    private View mErrorView;
    private WebView mWebView;

    public WebParentLayout(@NonNull Context context) {
        this(context, null);
        LogUtils.i(TAG, "WebParentLayout");
    }

    public WebParentLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public WebParentLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mAgentWebUIController = null;
        this.mClickId = -1;
        this.mErrorLayout = null;
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("WebParentLayout context must be activity or activity sub class .");
        }
        this.mErrorLayoutRes = R.layout.agentweb_error_page;
    }

    private void createErrorLayout() {
        final FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setBackgroundColor(-1);
        frameLayout.setId(R.id.mainframe_error_container_id);
        View view = this.mErrorView;
        if (view == null) {
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
            LogUtils.i(TAG, "mErrorLayoutRes:" + this.mErrorLayoutRes);
            layoutInflaterFrom.inflate(this.mErrorLayoutRes, (ViewGroup) frameLayout, true);
        } else {
            frameLayout.addView(view);
        }
        View view2 = (ViewStub) findViewById(R.id.mainframe_error_viewsub_id);
        int iIndexOfChild = indexOfChild(view2);
        removeViewInLayout(view2);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        this.mErrorLayout = frameLayout;
        if (layoutParams != null) {
            addView(frameLayout, iIndexOfChild, layoutParams);
        } else {
            addView(frameLayout, iIndexOfChild);
        }
        frameLayout.setVisibility(0);
        int i2 = this.mClickId;
        if (i2 != -1) {
            final View viewFindViewById = frameLayout.findViewById(i2);
            if (viewFindViewById != null) {
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.just.agentweb.WebParentLayout.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view3) {
                        if (WebParentLayout.this.getWebView() != null) {
                            viewFindViewById.setClickable(false);
                            WebParentLayout.this.getWebView().reload();
                        }
                    }
                });
                return;
            } else if (LogUtils.isDebug()) {
                LogUtils.e(TAG, "ClickView is null , cannot bind accurate view to refresh or reload .");
            }
        }
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.just.agentweb.WebParentLayout.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                if (WebParentLayout.this.getWebView() != null) {
                    frameLayout.setClickable(false);
                    WebParentLayout.this.getWebView().reload();
                }
            }
        });
    }

    public void bindController(AbsAgentWebUIController absAgentWebUIController) {
        this.mAgentWebUIController = absAgentWebUIController;
        absAgentWebUIController.bindWebParent(this, (Activity) getContext());
    }

    public void bindWebView(WebView webView) {
        if (this.mWebView == null) {
            this.mWebView = webView;
        }
    }

    public WebView getWebView() {
        return this.mWebView;
    }

    public void hideErrorLayout() {
        View viewFindViewById = findViewById(R.id.mainframe_error_container_id);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.just.agentweb.Provider
    public AbsAgentWebUIController provide() {
        return this.mAgentWebUIController;
    }

    public void setErrorLayoutRes(@LayoutRes int i2, @IdRes int i3) {
        this.mClickId = i3;
        if (i3 <= 0) {
            this.mClickId = -1;
        }
        this.mErrorLayoutRes = i2;
        if (i2 <= 0) {
            this.mErrorLayoutRes = R.layout.agentweb_error_page;
        }
    }

    public void setErrorView(@NonNull View view) {
        this.mErrorView = view;
    }

    public void showPageMainFrameError() {
        View viewFindViewById;
        FrameLayout frameLayout = this.mErrorLayout;
        if (frameLayout != null) {
            frameLayout.setVisibility(0);
        } else {
            createErrorLayout();
            frameLayout = this.mErrorLayout;
        }
        int i2 = this.mClickId;
        if (i2 == -1 || (viewFindViewById = frameLayout.findViewById(i2)) == null) {
            frameLayout.setClickable(true);
        } else {
            viewFindViewById.setClickable(true);
        }
    }
}
