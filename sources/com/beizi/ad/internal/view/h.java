package com.beizi.ad.internal.view;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.ViewUtil;

/* loaded from: classes2.dex */
public class h extends a {

    /* renamed from: a, reason: collision with root package name */
    private WebChromeClient.CustomViewCallback f4696a;

    /* renamed from: b, reason: collision with root package name */
    private FrameLayout f4697b;

    /* renamed from: c, reason: collision with root package name */
    private Activity f4698c;

    /* renamed from: d, reason: collision with root package name */
    private AdViewImpl f4699d;

    /* renamed from: e, reason: collision with root package name */
    private AdWebView f4700e;

    public h(Activity activity) {
        this.f4698c = activity;
    }

    private void a(FrameLayout frameLayout) {
        ImageButton imageButton = new ImageButton(this.f4698c);
        imageButton.setImageDrawable(this.f4698c.getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel));
        imageButton.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 53));
        imageButton.setBackgroundColor(0);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.view.h.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                h.this.onHideCustomView();
            }
        });
        frameLayout.addView(imageButton);
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsHidePrompt() {
        AdViewImpl adViewImpl = this.f4699d;
        if (adViewImpl == null || adViewImpl.e() || this.f4699d.a()) {
            return;
        }
        this.f4699d.getAdDispatcher().b();
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(final String str, final GeolocationPermissions.Callback callback) {
        AdWebView adWebView = this.f4700e;
        AlertDialog.Builder builder = new AlertDialog.Builder(adWebView != null ? ViewUtil.getTopContext(adWebView) : this.f4698c);
        builder.setTitle(String.format(this.f4698c.getResources().getString(com.beizi.ad.R.string.html5_geo_permission_prompt_title), str));
        builder.setMessage(com.beizi.ad.R.string.html5_geo_permission_prompt);
        builder.setPositiveButton(com.beizi.ad.R.string.allow, new DialogInterface.OnClickListener() { // from class: com.beizi.ad.internal.view.h.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                callback.invoke(str, true, true);
            }
        });
        builder.setNegativeButton(com.beizi.ad.R.string.deny, new DialogInterface.OnClickListener() { // from class: com.beizi.ad.internal.view.h.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                callback.invoke(str, false, false);
            }
        });
        builder.create().show();
        AdViewImpl adViewImpl = this.f4699d;
        if (adViewImpl == null || adViewImpl.e() || this.f4699d.a()) {
            return;
        }
        this.f4699d.getAdDispatcher().c();
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        super.onHideCustomView();
        Activity activity = this.f4698c;
        if (activity == null || this.f4697b == null) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(com.beizi.ad.R.string.fullscreen_video_hide_error));
            return;
        }
        AdWebView adWebView = this.f4700e;
        ViewGroup viewGroup = adWebView != null ? (ViewGroup) adWebView.getRootView().findViewById(R.id.content) : (ViewGroup) activity.findViewById(R.id.content);
        if (viewGroup == null) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(com.beizi.ad.R.string.fullscreen_video_hide_error));
            return;
        }
        viewGroup.removeView(this.f4697b);
        WebChromeClient.CustomViewCallback customViewCallback = this.f4696a;
        if (customViewCallback != null) {
            try {
                customViewCallback.onCustomViewHidden();
            } catch (NullPointerException e2) {
                HaoboLog.e(HaoboLog.baseLogTag, "Exception calling customViewCallback  onCustomViewHidden: " + e2.getMessage());
            }
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, int i2, WebChromeClient.CustomViewCallback customViewCallback) {
        onShowCustomView(view, customViewCallback);
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        super.onShowCustomView(view, customViewCallback);
        Activity activity = this.f4698c;
        if (activity == null) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(com.beizi.ad.R.string.fullscreen_video_show_error));
            return;
        }
        AdWebView adWebView = this.f4700e;
        ViewGroup viewGroup = adWebView != null ? (ViewGroup) adWebView.getRootView().findViewById(R.id.content) : (ViewGroup) activity.findViewById(R.id.content);
        if (viewGroup == null) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(com.beizi.ad.R.string.fullscreen_video_show_error));
            return;
        }
        this.f4696a = customViewCallback;
        if (!(view instanceof FrameLayout)) {
            this.f4697b = null;
            return;
        }
        FrameLayout frameLayout = (FrameLayout) view;
        this.f4697b = frameLayout;
        frameLayout.setClickable(true);
        this.f4697b.setBackgroundColor(-16777216);
        try {
            a(this.f4697b);
            viewGroup.addView(this.f4697b, new ViewGroup.LayoutParams(-1, -1));
        } catch (Exception e2) {
            HaoboLog.d(HaoboLog.baseLogTag, e2.toString());
        }
    }

    public h(AdWebView adWebView) {
        this.f4698c = (Activity) adWebView.getContextFromMutableContext();
        this.f4700e = adWebView;
        this.f4699d = adWebView.adViewImpl;
    }
}
