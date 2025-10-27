package com.beizi.ad.internal.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Pair;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.beizi.ad.AdActivity;
import com.beizi.ad.R;
import com.beizi.ad.a.a.j;
import com.beizi.ad.c.d;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.utilities.DeviceInfo;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.UserEnvInfo;
import com.beizi.ad.internal.utilities.WebviewUtil;
import com.beizi.ad.internal.view.AdViewImpl;
import com.tencent.open.SocialConstants;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
class b {
    public static void a(AdWebView adWebView, String str) {
        Uri uri = Uri.parse(str);
        String host = uri.getHost();
        if ("GotoPage".equals(host)) {
            a(adWebView, uri);
            return;
        }
        if ("SendMsg".equals(host)) {
            b(adWebView, uri);
            return;
        }
        if ("CallNo".equals(host)) {
            c(adWebView, uri);
            return;
        }
        if ("ClosePage".equals(host)) {
            d(adWebView, uri);
            return;
        }
        if ("GetDeviceID".equals(host)) {
            e((WebView) adWebView, uri);
            return;
        }
        if ("Download".equals(host)) {
            e(adWebView, uri);
            return;
        }
        if ("PingClick".equals(host)) {
            f(adWebView, uri);
            return;
        }
        if ("PingConvertion".equals(host)) {
            g(adWebView, uri);
            return;
        }
        if ("GetCommonInfo".equals(host)) {
            a((WebView) adWebView, uri);
            return;
        }
        if ("MayDeepLink".equals(host)) {
            b((WebView) adWebView, uri);
            return;
        }
        if ("DeepLink".equals(host)) {
            if (adWebView.getUserInteraction()) {
                c((WebView) adWebView, uri);
                return;
            } else {
                HaoboLog.w(HaoboLog.jsLogTag, HaoboLog.getString(R.string.no_user_interaction, str));
                return;
            }
        }
        if ("ExternalBrowser".equals(host)) {
            if (adWebView.getUserInteraction()) {
                d((WebView) adWebView, uri);
                return;
            } else {
                HaoboLog.w(HaoboLog.jsLogTag, HaoboLog.getString(R.string.no_user_interaction, str));
                return;
            }
        }
        if ("InternalBrowser".equals(host)) {
            if (adWebView.getUserInteraction()) {
                h(adWebView, uri);
                return;
            } else {
                HaoboLog.w(HaoboLog.jsLogTag, HaoboLog.getString(R.string.no_user_interaction, str));
                return;
            }
        }
        if ("RecordEvent".equals(host)) {
            i(adWebView, uri);
            return;
        }
        if ("DispatchAppEvent".equals(host)) {
            j(adWebView, uri);
            return;
        }
        if ("GetDeviceID".equals(host)) {
            e((WebView) adWebView, uri);
            return;
        }
        HaoboLog.w(HaoboLog.baseLogTag, "BeiZiSDK called with unsupported function: " + host);
    }

    private static void b(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        uri.getQueryParameter("msg");
        uri.getQueryParameter(SocialConstants.PARAM_RECEIVER);
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "SendMsg"));
        linkedList.add(new Pair("success", String.valueOf(false)));
        a(adWebView, queryParameter, linkedList);
    }

    private static void c(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        uri.getQueryParameter(SocialConstants.PARAM_RECEIVER);
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "CallNo"));
        linkedList.add(new Pair("success", String.valueOf(false)));
        a(adWebView, queryParameter, linkedList);
    }

    private static void d(final AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        adWebView.adViewImpl.getMyHandler().post(new Runnable() { // from class: com.beizi.ad.internal.view.b.1
            @Override // java.lang.Runnable
            public void run() {
                if (!adWebView.adViewImpl.e()) {
                    adWebView.d();
                    return;
                }
                adWebView.adViewImpl.getAdDispatcher().b();
                Activity activity = (Activity) adWebView.getContextFromMutableContext();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                activity.finish();
            }
        });
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "ClosePage"));
        linkedList.add(new Pair("success", String.valueOf(true)));
        a(adWebView, queryParameter, linkedList);
    }

    private static void e(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        String queryParameter2 = uri.getQueryParameter("cb");
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(queryParameter)));
            if (adWebView != null) {
                adWebView.getContext().startActivity(intent);
            }
        } catch (Exception unused) {
            Toast.makeText(adWebView.getContext(), R.string.action_cant_be_completed, 0).show();
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "Download"));
        linkedList.add(new Pair("success", String.valueOf(true)));
        a(adWebView, queryParameter2, linkedList);
    }

    private static void f(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        String queryParameter2 = uri.getQueryParameter("cb");
        adWebView.adViewImpl.pingClick(queryParameter);
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "PingClick"));
        linkedList.add(new Pair("success", String.valueOf(true)));
        a(adWebView, queryParameter2, linkedList);
    }

    private static void g(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        String queryParameter2 = uri.getQueryParameter("cb");
        adWebView.adViewImpl.pingConvert(queryParameter);
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "PingConvertion"));
        linkedList.add(new Pair("success", String.valueOf(true)));
        a(adWebView, queryParameter2, linkedList);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private static void h(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (adWebView.getContext() == null || queryParameter == null || !queryParameter.startsWith("http")) {
            return;
        }
        String strDecode = Uri.decode(queryParameter);
        Class clsA = AdActivity.a();
        Intent intent = new Intent(adWebView.getContext(), (Class<?>) clsA);
        intent.putExtra("ACTIVITY_TYPE", "BROWSER");
        WebView webView = new WebView(adWebView.getContext());
        WebviewUtil.setWebViewSettings(webView);
        com.beizi.ad.internal.a.a.f3988a.add(webView);
        webView.loadUrl(strDecode);
        if (adWebView.adViewImpl.getBrowserStyle() != null) {
            String str = "" + webView.hashCode();
            intent.putExtra("bridgeid", str);
            AdViewImpl.c.f4566a.add(new Pair<>(str, adWebView.adViewImpl.getBrowserStyle()));
        }
        try {
            adWebView.getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(adWebView.getContext(), R.string.action_cant_be_completed, 0).show();
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_missing, clsA.getName()));
            com.beizi.ad.internal.a.a.f3988a.remove();
        }
    }

    private static void i(AdWebView adWebView, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (queryParameter == null || !queryParameter.startsWith("http")) {
            return;
        }
        WebView webView = new WebView(adWebView.getContext());
        webView.setWebViewClient(new WebViewClient() { // from class: com.beizi.ad.internal.view.b.2
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
                HaoboLog.d(HaoboLog.baseLogTag, "RecordEvent completed loading: " + str);
                CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
                if (cookieSyncManager != null) {
                    cookieSyncManager.sync();
                }
            }
        });
        webView.loadUrl(queryParameter);
        webView.setVisibility(8);
        adWebView.addView(webView);
    }

    private static void j(AdWebView adWebView, Uri uri) {
        adWebView.adViewImpl.getAdDispatcher().a(uri.getQueryParameter(NotificationCompat.CATEGORY_EVENT), uri.getQueryParameter("data"));
    }

    private static void c(WebView webView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        String queryParameter2 = uri.getQueryParameter("url");
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "DeepLink"));
        if (webView.getContext() != null && queryParameter2 != null) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(queryParameter2)));
                intent.setFlags(268435456);
                webView.getContext().startActivity(intent);
                return;
            } catch (ActivityNotFoundException unused) {
                a(webView, queryParameter, linkedList);
                return;
            }
        }
        a(webView, queryParameter, linkedList);
    }

    private static void d(WebView webView, Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        if (webView.getContext() == null || queryParameter == null || !queryParameter.startsWith("http")) {
            return;
        }
        try {
            webView.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(queryParameter))));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(webView.getContext(), R.string.action_cant_be_completed, 0).show();
        }
    }

    private static void b(WebView webView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        String queryParameter2 = uri.getQueryParameter("url");
        boolean z2 = false;
        if (webView.getContext() != null && webView.getContext().getPackageManager() != null && queryParameter2 != null) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(queryParameter2)));
            intent.setFlags(268435456);
            if (intent.resolveActivity(webView.getContext().getPackageManager()) != null) {
                z2 = true;
            }
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "MayDeepLink"));
        linkedList.add(new Pair("mayDeepLink", String.valueOf(z2)));
        a(webView, queryParameter, linkedList);
    }

    private static void e(WebView webView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "GetDeviceID"));
        linkedList.add(new Pair("idname", "imei"));
        a(webView, queryParameter, linkedList);
    }

    private static void a(AdWebView adWebView, Uri uri) {
        boolean z2;
        String queryParameter = uri.getQueryParameter("cb");
        if (adWebView.loadAdAt(Integer.parseInt(uri.getQueryParameter("index")))) {
            AdViewImpl adViewImpl = adWebView.adViewImpl;
            if (adViewImpl instanceof InterstitialAdViewImpl) {
                ((com.beizi.ad.internal.a.b) ((InterstitialAdViewImpl) adViewImpl).getAdImplementation()).g();
            }
            z2 = true;
        } else {
            z2 = false;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "GotoPage"));
        linkedList.add(new Pair("success", String.valueOf(z2)));
        a(adWebView, queryParameter, linkedList);
    }

    private static void a(WebView webView, Uri uri) {
        String queryParameter = uri.getQueryParameter("cb");
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        d.a aVarA = new d.a.C0048a().a(deviceInfo.sdkUID).l(j.a(com.beizi.ad.internal.g.a().f4184i)).m(j.b(com.beizi.ad.internal.g.a().f4184i)).n(deviceInfo.bootMark).o(deviceInfo.updateMark).b("").c(deviceInfo.os).a(e.EnumC0049e.PLATFORM_ANDROID).a(deviceInfo.devType).d(deviceInfo.brand).e(deviceInfo.model).f(deviceInfo.manufacturer).g(deviceInfo.resolution).h(deviceInfo.screenSize).i(deviceInfo.language).p(deviceInfo.agVercode).a(deviceInfo.wxInstalled).q(DeviceInfo.physicalMemoryByte).r(DeviceInfo.harddiskSizeByte).a();
        UserEnvInfo userEnvInfo = UserEnvInfo.getInstance();
        d.c cVarA = new d.c.a().a(userEnvInfo.f4406net).a(userEnvInfo.isp).a();
        LinkedList linkedList = new LinkedList();
        linkedList.add(new Pair("caller", "GetDeviceID"));
        linkedList.add(new Pair("deviceInfo", Base64.encodeToString(aVarA.b(), 0)));
        linkedList.add(new Pair("userEnvInfo", Base64.encodeToString(cVarA.b(), 0)));
        a(webView, queryParameter, linkedList);
    }

    private static void a(WebView webView, String str, List<Pair<String, String>> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("cb=");
        if (str == null) {
            str = "-1";
        }
        sb.append(str);
        if (list != null) {
            for (Pair<String, String> pair : list) {
                if (pair.first != null && pair.second != null) {
                    sb.append("&");
                    sb.append((String) pair.first);
                    sb.append("=");
                    sb.append(Uri.encode((String) pair.second));
                }
            }
        }
        webView.evaluateJavascript(String.format("javascript:window.sdkjs.client.result(\"%s\")", sb.toString()), null);
    }
}
