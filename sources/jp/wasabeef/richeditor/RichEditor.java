package jp.wasabeef.richeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes8.dex */
public abstract class RichEditor extends WebView {
    private static final String CALLBACK_SCHEME = "callback://";
    private static final String FOCUS_CHANGE_SCHEME = "focus://";
    private static final String IMAGE_CLICK_SCHEME = "image://";
    private static final String LINK_CHANGE_SCHEME = "change://";
    private static final String SETUP_HTML = "file:///android_asset/editor.html";
    private static final String STATE_SCHEME = "state://";
    private boolean isReady;
    private long mContentLength;
    private String mContents;
    private AfterInitialLoadListener mLoadListener;
    private OnFocusChangeListener mOnFocusChangeListener;
    private OnImageClickListener mOnImageClickListener;
    private OnLinkClickListener mOnLinkClickListener;
    private OnScrollChangedCallback mOnScrollChangedCallback;
    private OnTextLengthChangeListener mOnTextLengthChangeListener;
    private OnTextTitleListener mOnTextTitleListener;
    private OnStateChangeListener mStateChangeListener;
    private OnTextChangeListener mTextChangeListener;

    public interface AfterInitialLoadListener {
        void onAfterInitialLoad(boolean z2);
    }

    public class Android4JsInterface {
        private Android4JsInterface() {
        }

        @JavascriptInterface
        public void setHtmlContent(String str) {
            RichEditor.this.mContents = str;
            if (RichEditor.this.mTextChangeListener != null) {
                RichEditor.this.mTextChangeListener.onTextChange(str);
            }
        }

        @JavascriptInterface
        public void setViewEnabled(boolean z2) {
            if (RichEditor.this.mOnFocusChangeListener != null) {
                RichEditor.this.mOnFocusChangeListener.onFocusChange(z2);
            }
        }

        @JavascriptInterface
        public void staticWords(long j2) {
            RichEditor.this.mContentLength = j2;
            if (RichEditor.this.mOnTextLengthChangeListener != null) {
                RichEditor.this.mOnTextLengthChangeListener.onTextLengthChange(j2);
            }
        }
    }

    public class EditorWebVIewClient2 extends WebChromeClient {
        public EditorWebVIewClient2() {
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
            return super.onJsBeforeUnload(webView, str, str2, jsResult);
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i2) {
            super.onProgressChanged(webView, i2);
        }
    }

    public class EditorWebViewClient extends WebViewClient {
        private EditorWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            RichEditor.this.isReady = str.equalsIgnoreCase(RichEditor.SETUP_HTML);
            if (RichEditor.this.mLoadListener != null) {
                RichEditor.this.mLoadListener.onAfterInitialLoad(RichEditor.this.isReady);
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) throws UnsupportedEncodingException {
            try {
                String strDecode = URLDecoder.decode(str, "UTF-8");
                if (TextUtils.indexOf(str, RichEditor.CALLBACK_SCHEME) == 0) {
                    RichEditor.this.callback(strDecode);
                    return true;
                }
                if (TextUtils.indexOf(str, RichEditor.STATE_SCHEME) == 0) {
                    RichEditor.this.stateCheck(strDecode);
                    return true;
                }
                if (TextUtils.indexOf(str, RichEditor.LINK_CHANGE_SCHEME) == 0) {
                    RichEditor.this.linkChangeCallBack(strDecode);
                    return true;
                }
                if (TextUtils.indexOf(str, RichEditor.IMAGE_CLICK_SCHEME) != 0) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                RichEditor.this.imageClickCallBack(strDecode);
                return true;
            } catch (UnsupportedEncodingException unused) {
                return false;
            }
        }
    }

    public interface OnFocusChangeListener {
        void onFocusChange(boolean z2);
    }

    public interface OnImageClickListener {
        void onImageClick(Long l2);
    }

    public interface OnLinkClickListener {
        void onLinkClick(String str, String str2, String str3);
    }

    public interface OnScrollChangedCallback {
        void onPageEnd(int i2, int i3, int i4, int i5, float f2);

        void onPageTop(int i2, int i3, int i4, int i5, float f2);

        void onScroll(int i2, int i3);

        void onScrollChanged(int i2, int i3, int i4, int i5, float f2);
    }

    public interface OnStateChangeListener {
        void onStateChangeListener(String str, List<Type> list);
    }

    public interface OnTextChangeListener {
        void onTextChange(String str);
    }

    public interface OnTextLengthChangeListener {
        void onTextLengthChange(long j2);
    }

    public interface OnTextTitleListener {
        void onTitleChange(String str);
    }

    public enum Type {
        BOLD(6),
        ITALIC(7),
        STRIKETHROUGH(8),
        BLOCKQUOTE(9),
        H1(10),
        H2(11),
        H3(12),
        H4(13);

        private long typeCode;

        Type(long j2) {
            this.typeCode = j2;
        }

        public long getTypeCode() {
            return this.typeCode;
        }

        public boolean isMapTo(long j2) {
            return this.typeCode == j2;
        }
    }

    public RichEditor(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callback(String str) {
        String strReplaceFirst = str.replaceFirst(CALLBACK_SCHEME, "");
        this.mContents = strReplaceFirst;
        OnTextChangeListener onTextChangeListener = this.mTextChangeListener;
        if (onTextChangeListener != null) {
            onTextChangeListener.onTextChange(strReplaceFirst);
        }
        OnTextTitleListener onTextTitleListener = this.mOnTextTitleListener;
        if (onTextTitleListener != null) {
            onTextTitleListener.onTitleChange(this.mContents);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void imageClickCallBack(String str) {
        OnImageClickListener onImageClickListener = this.mOnImageClickListener;
        if (onImageClickListener != null) {
            onImageClickListener.onImageClick(Long.valueOf(str.replaceFirst(IMAGE_CLICK_SCHEME, "")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void linkChangeCallBack(String str) {
        String[] strArrSplit = str.replaceFirst(LINK_CHANGE_SCHEME, "").split("@_@");
        OnLinkClickListener onLinkClickListener = this.mOnLinkClickListener;
        if (onLinkClickListener == null || strArrSplit.length < 3) {
            return;
        }
        onLinkClickListener.onLinkClick(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
    }

    public void changeLink(String str, String str2) {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.changeLink('" + str2 + "', '" + str + "');");
    }

    public void clearFocusEditor() {
        exec("javascript:RE.blurFocus();");
    }

    public EditorWebViewClient createWebViewClient() {
        return new EditorWebViewClient();
    }

    public void deleteImageById(Long l2) {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.removeImage(" + l2 + ");");
    }

    public void exec(final String str) {
        if (this.isReady) {
            load(str);
        } else {
            postDelayed(new Runnable() { // from class: jp.wasabeef.richeditor.RichEditor.1
                @Override // java.lang.Runnable
                public void run() {
                    RichEditor.this.exec(str);
                }
            }, 100L);
        }
    }

    public void focusEditor() {
        requestFocus();
    }

    public long getContentLength() {
        return this.mContentLength;
    }

    public String getHtml() {
        return this.mContents;
    }

    public void getHtmlAsyn() {
        exec("javascript:RE.getHtml4Android()");
    }

    public void insertHr() {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.insertLine();");
    }

    public void insertImage(String str, Long l2, long j2, long j3) {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.insertImage('" + str + "'," + l2 + ", " + j2 + "," + j3 + ");");
    }

    public void insertLink(long j2, String str, String str2) {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.insertLink('" + j2 + "','" + str2 + "', '" + str + "');");
    }

    public void insertTodo() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setTodo('" + System.currentTimeMillis() + "');");
    }

    public void load() {
        loadUrl(SETUP_HTML);
    }

    public void loadCSS(String str) {
        exec(BridgeUtil.JAVASCRIPT_STR + ("(function() {    var head  = document.getElementsByTagName(\"head\")[0];    var link  = document.createElement(\"link\");    link.rel  = \"stylesheet\";    link.type = \"text/css\";    link.href = \"" + str + "\";    link.media = \"all\";    head.appendChild(link);}) ();") + "");
    }

    @Override // android.webkit.WebView, android.view.View
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
    }

    public void redo() {
        exec("javascript:RE.exec('redo');");
    }

    @Override // android.webkit.WebView, android.view.View
    public void setBackgroundColor(int i2) {
        super.setBackgroundColor(i2);
    }

    public void setBlockquote(boolean z2) {
        exec("javascript:RE.saveRange();");
        if (z2) {
            exec("javascript:RE.exec('blockquote')");
        } else {
            exec("javascript:RE.exec('p')");
        }
    }

    public void setBold() {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.exec('bold');");
    }

    public void setEditorBackgroundColor(String str, int i2) {
        setBackgroundColor(i2);
        exec("javascript:RE.setBackgroundColor('" + str + "');");
    }

    public void setFontView(String str) {
        exec("javascript:RE.setFontColor('" + str + "');");
    }

    public void setHeading(int i2, boolean z2) {
        exec("javascript:RE.saveRange();");
        if (!z2) {
            exec("javascript:RE.exec('p')");
            return;
        }
        exec("javascript:RE.exec('h" + i2 + "')");
    }

    public void setHtml(String str) {
        exec("javascript:RE.setHtml('" + str + "');");
    }

    public void setHtmlContent(String str) {
        exec("javascript:RE.insertHtml('" + str + "');");
    }

    public void setImageFailed(long j2) {
        exec("javascript:RE.uploadFailure(" + j2 + ");");
    }

    public void setImageReload(long j2) {
        exec("javascript:RE.uploadReload(" + j2 + ");");
    }

    public void setImageUploadProcess(long j2, int i2) {
        exec("javascript:RE.changeProcess(" + j2 + ", " + i2 + ");");
    }

    public void setItalic() {
        exec("javascript:RE.saveRange();");
        exec("javascript:RE.exec('italic');");
    }

    public void setOnDecorationChangeListener(OnStateChangeListener onStateChangeListener) {
        this.mStateChangeListener = onStateChangeListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.mOnImageClickListener = onImageClickListener;
    }

    public void setOnInitialLoadListener(AfterInitialLoadListener afterInitialLoadListener) {
        this.mLoadListener = afterInitialLoadListener;
    }

    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener) {
        this.mOnLinkClickListener = onLinkClickListener;
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.mTextChangeListener = onTextChangeListener;
    }

    public void setOnTextLengthChangeListener(OnTextLengthChangeListener onTextLengthChangeListener) {
        this.mOnTextLengthChangeListener = onTextLengthChangeListener;
    }

    @Override // android.view.View
    public void setPadding(int i2, int i3, int i4, int i5) {
        super.setPadding(i2, i3, i4, i5);
        exec("javascript:RE.setPadding('" + i2 + "px', '" + i3 + "px', '" + i4 + "px', '" + i5 + "px');");
    }

    @Override // android.view.View
    public void setPaddingRelative(int i2, int i3, int i4, int i5) {
        setPadding(i2, i3, i4, i5);
    }

    public void setPlaceholder(String str) {
        exec("javascript:RE.setPlaceholder('" + str + "');");
    }

    public void setStrikeThrough() {
        exec("javascript:RE.saveRange()");
        exec("javascript:RE.exec('strikethrough');");
    }

    public void setmOnTextTitleListener(OnTextTitleListener onTextTitleListener) {
        this.mOnTextTitleListener = onTextTitleListener;
    }

    public void stateCheck(String str) {
        String upperCase = str.replaceFirst(STATE_SCHEME, "").toUpperCase(Locale.ENGLISH);
        ArrayList arrayList = new ArrayList();
        for (Type type : Type.values()) {
            if (TextUtils.indexOf(upperCase, type.name()) != -1) {
                arrayList.add(type);
            }
        }
        OnStateChangeListener onStateChangeListener = this.mStateChangeListener;
        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChangeListener(upperCase, arrayList);
        }
    }

    public void undo() {
        exec("javascript:RE.exec('undo');");
    }

    public RichEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.webViewStyle);
    }

    private void load(String str) {
        evaluateJavascript(str, null);
    }

    @SuppressLint({"SetJavaScriptEnabled", "addJavascriptInterface"})
    public RichEditor(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isReady = false;
        if (isInEditMode()) {
            return;
        }
        addJavascriptInterface(new Android4JsInterface(), "AndroidInterface");
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setWebViewClient(createWebViewClient());
        setWebChromeClient(new WebChromeClient());
        this.mContentLength = 0L;
        getSettings().setJavaScriptEnabled(true);
        load();
    }
}
