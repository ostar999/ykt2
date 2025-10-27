package com.tencent.smtt.sdk;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import com.tencent.smtt.export.external.interfaces.ServiceWorkerClient;
import com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings;
import com.tencent.smtt.sdk.SystemWebViewClient;

/* loaded from: classes6.dex */
public class k extends ServiceWorkerController {
    @Override // com.tencent.smtt.sdk.ServiceWorkerController
    public ServiceWorkerWebSettings getServiceWorkerWebSettings() {
        if (Build.VERSION.SDK_INT < 24) {
            return null;
        }
        final android.webkit.ServiceWorkerWebSettings serviceWorkerWebSettings = android.webkit.ServiceWorkerController.getInstance().getServiceWorkerWebSettings();
        return new ServiceWorkerWebSettings() { // from class: com.tencent.smtt.sdk.k.1
            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public boolean getAllowContentAccess() {
                if (Build.VERSION.SDK_INT >= 24) {
                    return serviceWorkerWebSettings.getAllowContentAccess();
                }
                return false;
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public boolean getAllowFileAccess() {
                if (Build.VERSION.SDK_INT >= 24) {
                    return serviceWorkerWebSettings.getAllowFileAccess();
                }
                return false;
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public boolean getBlockNetworkLoads() {
                if (Build.VERSION.SDK_INT >= 24) {
                    return serviceWorkerWebSettings.getBlockNetworkLoads();
                }
                return false;
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public int getCacheMode() {
                if (Build.VERSION.SDK_INT >= 24) {
                    return serviceWorkerWebSettings.getCacheMode();
                }
                return -1;
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public void setAllowContentAccess(boolean z2) {
                if (Build.VERSION.SDK_INT >= 24) {
                    serviceWorkerWebSettings.setAllowContentAccess(z2);
                }
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public void setAllowFileAccess(boolean z2) {
                if (Build.VERSION.SDK_INT >= 24) {
                    serviceWorkerWebSettings.setAllowContentAccess(z2);
                }
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public void setBlockNetworkLoads(boolean z2) {
                if (Build.VERSION.SDK_INT >= 24) {
                    serviceWorkerWebSettings.setBlockNetworkLoads(z2);
                }
            }

            @Override // com.tencent.smtt.export.external.interfaces.ServiceWorkerWebSettings
            public void setCacheMode(int i2) {
                if (Build.VERSION.SDK_INT >= 24) {
                    serviceWorkerWebSettings.setCacheMode(i2);
                }
            }
        };
    }

    @Override // com.tencent.smtt.sdk.ServiceWorkerController
    public void setServiceWorkerClient(final ServiceWorkerClient serviceWorkerClient) {
        if (Build.VERSION.SDK_INT >= 24) {
            android.webkit.ServiceWorkerController.getInstance().setServiceWorkerClient(new android.webkit.ServiceWorkerClient() { // from class: com.tencent.smtt.sdk.k.2
                @Override // android.webkit.ServiceWorkerClient
                public WebResourceResponse shouldInterceptRequest(WebResourceRequest webResourceRequest) {
                    com.tencent.smtt.export.external.interfaces.WebResourceResponse webResourceResponseShouldInterceptRequest = serviceWorkerClient.shouldInterceptRequest(new SystemWebViewClient.e(webResourceRequest.getUrl().toString(), webResourceRequest.isForMainFrame(), webResourceRequest.isRedirect(), webResourceRequest.hasGesture(), webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders()));
                    if (webResourceResponseShouldInterceptRequest == null) {
                        return null;
                    }
                    WebResourceResponse webResourceResponse = new WebResourceResponse(webResourceResponseShouldInterceptRequest.getMimeType(), webResourceResponseShouldInterceptRequest.getEncoding(), webResourceResponseShouldInterceptRequest.getData());
                    webResourceResponse.setResponseHeaders(webResourceResponseShouldInterceptRequest.getResponseHeaders());
                    int statusCode = webResourceResponseShouldInterceptRequest.getStatusCode();
                    String reasonPhrase = webResourceResponseShouldInterceptRequest.getReasonPhrase();
                    if (statusCode != webResourceResponse.getStatusCode() || (reasonPhrase != null && !reasonPhrase.equals(webResourceResponse.getReasonPhrase()))) {
                        webResourceResponse.setStatusCodeAndReasonPhrase(statusCode, reasonPhrase);
                    }
                    return webResourceResponse;
                }
            });
        }
    }
}
