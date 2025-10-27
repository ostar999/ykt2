package com.tencent.smtt.sdk;

import android.graphics.Bitmap;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;

/* loaded from: classes6.dex */
public class WebHistoryItem {

    /* renamed from: a, reason: collision with root package name */
    private IX5WebHistoryItem f21049a = null;

    /* renamed from: b, reason: collision with root package name */
    private android.webkit.WebHistoryItem f21050b = null;

    private WebHistoryItem() {
    }

    public static WebHistoryItem a(android.webkit.WebHistoryItem webHistoryItem) {
        if (webHistoryItem == null) {
            return null;
        }
        WebHistoryItem webHistoryItem2 = new WebHistoryItem();
        webHistoryItem2.f21050b = webHistoryItem;
        return webHistoryItem2;
    }

    public static WebHistoryItem a(IX5WebHistoryItem iX5WebHistoryItem) {
        if (iX5WebHistoryItem == null) {
            return null;
        }
        WebHistoryItem webHistoryItem = new WebHistoryItem();
        webHistoryItem.f21049a = iX5WebHistoryItem;
        return webHistoryItem;
    }

    public Bitmap getFavicon() {
        IX5WebHistoryItem iX5WebHistoryItem = this.f21049a;
        return iX5WebHistoryItem != null ? iX5WebHistoryItem.getFavicon() : this.f21050b.getFavicon();
    }

    public String getOriginalUrl() {
        IX5WebHistoryItem iX5WebHistoryItem = this.f21049a;
        return iX5WebHistoryItem != null ? iX5WebHistoryItem.getOriginalUrl() : this.f21050b.getOriginalUrl();
    }

    public String getTitle() {
        IX5WebHistoryItem iX5WebHistoryItem = this.f21049a;
        return iX5WebHistoryItem != null ? iX5WebHistoryItem.getTitle() : this.f21050b.getTitle();
    }

    public String getUrl() {
        IX5WebHistoryItem iX5WebHistoryItem = this.f21049a;
        return iX5WebHistoryItem != null ? iX5WebHistoryItem.getUrl() : this.f21050b.getUrl();
    }
}
