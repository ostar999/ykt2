package com.tencent.smtt.sdk;

import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;

/* loaded from: classes6.dex */
public class WebBackForwardList {

    /* renamed from: a, reason: collision with root package name */
    private IX5WebBackForwardList f21047a = null;

    /* renamed from: b, reason: collision with root package name */
    private android.webkit.WebBackForwardList f21048b = null;

    public static WebBackForwardList a(android.webkit.WebBackForwardList webBackForwardList) {
        if (webBackForwardList == null) {
            return null;
        }
        WebBackForwardList webBackForwardList2 = new WebBackForwardList();
        webBackForwardList2.f21048b = webBackForwardList;
        return webBackForwardList2;
    }

    public static WebBackForwardList a(IX5WebBackForwardList iX5WebBackForwardList) {
        if (iX5WebBackForwardList == null) {
            return null;
        }
        WebBackForwardList webBackForwardList = new WebBackForwardList();
        webBackForwardList.f21047a = iX5WebBackForwardList;
        return webBackForwardList;
    }

    public int getCurrentIndex() {
        IX5WebBackForwardList iX5WebBackForwardList = this.f21047a;
        return iX5WebBackForwardList != null ? iX5WebBackForwardList.getCurrentIndex() : this.f21048b.getCurrentIndex();
    }

    public WebHistoryItem getCurrentItem() {
        IX5WebBackForwardList iX5WebBackForwardList = this.f21047a;
        return iX5WebBackForwardList != null ? WebHistoryItem.a(iX5WebBackForwardList.getCurrentItem()) : WebHistoryItem.a(this.f21048b.getCurrentItem());
    }

    public WebHistoryItem getItemAtIndex(int i2) {
        IX5WebBackForwardList iX5WebBackForwardList = this.f21047a;
        return iX5WebBackForwardList != null ? WebHistoryItem.a(iX5WebBackForwardList.getItemAtIndex(i2)) : WebHistoryItem.a(this.f21048b.getItemAtIndex(i2));
    }

    public int getSize() {
        IX5WebBackForwardList iX5WebBackForwardList = this.f21047a;
        return iX5WebBackForwardList != null ? iX5WebBackForwardList.getSize() : this.f21048b.getSize();
    }
}
