package com.psychiatrygarden.widget;

import android.content.Context;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0012R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/widget/DetailCourseContentWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "llContent", "Landroid/view/View;", "loadingView", "wTop", "", com.umeng.socialize.tracker.a.f23806c, "", "data", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "initParams", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDetailCourseContentWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailCourseContentWidget.kt\ncom/psychiatrygarden/widget/DetailCourseContentWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,130:1\n1855#2,2:131\n*S KotlinDebug\n*F\n+ 1 DetailCourseContentWidget.kt\ncom/psychiatrygarden/widget/DetailCourseContentWidget\n*L\n103#1:131,2\n*E\n"})
/* loaded from: classes6.dex */
public final class DetailCourseContentWidget extends LinearLayout implements BaseContentWidget {
    private View llContent;
    private View loadingView;
    private float wTop;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseContentWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseContentWidget(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        View.inflate(context, R.layout.layout_course_detail_content, this);
        View viewFindViewById = findViewById(R.id.loadingView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.loadingView)");
        this.loadingView = viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.ll_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.ll_content)");
        this.llContent = viewFindViewById2;
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) {
        Intrinsics.checkNotNullParameter(data, "data");
        initParams(data);
    }

    public final void initParams(@NotNull GoodsDetailItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getCourseData().getDataType() == 2) {
            ((TextView) findViewById(R.id.tv_title)).setText("商品详情");
        }
        final WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "mWebview.settings");
        webView.removeJavascriptInterface("searchBoxJavaBredge_");
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setMixedContentMode(0);
        webView.loadUrl("about:blank");
        webView.setWebViewClient(new WebViewClient() { // from class: com.psychiatrygarden.widget.DetailCourseContentWidget.initParams.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(@NotNull WebView view, @NotNull String url) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(url, "url");
                super.onPageFinished(view, url);
                if (webView != null) {
                    View view2 = this.llContent;
                    if (view2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llContent");
                        view2 = null;
                    }
                    ViewExtensionsKt.visible(view2);
                    View view3 = this.loadingView;
                    if (view3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("loadingView");
                        view3 = null;
                    }
                    ViewExtensionsKt.gone(view3);
                    EventBus.getDefault().post("CALCULATE");
                    if (SkinManager.getCurrentSkinType(this.getContext()) == 1) {
                        webView.evaluateJavascript("document.body.style.backgroundColor='#121622';document.body.style.color='#7380A9';", null);
                    }
                }
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(@NotNull WebView view, @NotNull WebResourceRequest request, @NotNull WebResourceError error) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(request, "request");
                Intrinsics.checkNotNullParameter(error, "error");
                super.onReceivedError(view, request, error);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedHttpError(@NotNull WebView view, @NotNull WebResourceRequest request, @NotNull WebResourceResponse errorResponse) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(request, "request");
                Intrinsics.checkNotNullParameter(errorResponse, "errorResponse");
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(@NotNull WebView view, @NotNull SslErrorHandler handler, @NotNull SslError error) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(handler, "handler");
                Intrinsics.checkNotNullParameter(error, "error");
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }

            @Override // android.webkit.WebViewClient
            @Deprecated(message = "Deprecated in Java")
            public boolean shouldOverrideUrlLoading(@NotNull WebView view, @NotNull String url) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(url, "url");
                view.loadUrl(url);
                return true;
            }
        });
        String str = "<HTML><HEAD><style>*{margin:0;padding:0;font-size:" + (SizeUtil.sp2px(getContext(), 12) + "px") + ";} img{vertical-align:middle;}</style></HEAD><BODY style='margin:0;padding:0'>";
        String detail = item.getCourseData().getDetail();
        if (detail != null) {
            Document document = Jsoup.parse(str + detail + "</BODY></HTML>");
            Elements elements = document.select("img");
            Intrinsics.checkNotNullExpressionValue(elements, "elements");
            for (Element element : elements) {
                String strAttr = element.attr(TtmlNode.TAG_STYLE);
                String str2 = "width: 100%;height: auto !important;";
                if (!(strAttr == null || strAttr.length() == 0)) {
                    str2 = strAttr + "width: 100%;height: auto !important;";
                }
                element.attr(TtmlNode.TAG_STYLE, str2);
            }
            String strHtml = document.html();
            if (item.getCourseData().getDetail() != null) {
                webView.loadData(strHtml, "text/html", "UTF-8");
            }
        }
    }

    public /* synthetic */ DetailCourseContentWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
