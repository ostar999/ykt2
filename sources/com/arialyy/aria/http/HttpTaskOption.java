package com.arialyy.aria.http;

import android.text.TextUtils;
import com.arialyy.aria.core.common.RequestEnum;
import com.arialyy.aria.core.inf.ITaskOption;
import com.arialyy.aria.core.processor.IHttpFileLenAdapter;
import com.arialyy.aria.core.processor.IHttpFileNameAdapter;
import java.lang.ref.SoftReference;
import java.net.CookieManager;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class HttpTaskOption implements ITaskOption {
    private String attachment;
    private CookieManager cookieManager;
    private SoftReference<IHttpFileLenAdapter> fileLenAdapter;
    private SoftReference<IHttpFileNameAdapter> fileNameAdapter;
    private Map<String, String> params;
    private Proxy proxy;
    private Map<String, String> headers = new HashMap();
    private String charSet = "utf-8";
    private RequestEnum requestEnum = RequestEnum.GET;
    private boolean useServerFileName = false;
    private String redirectUrl = "";
    private boolean isChunked = false;
    private Map<String, String> formFields = new HashMap();

    public String getAttachment() {
        return TextUtils.isEmpty(this.attachment) ? "file" : this.attachment;
    }

    public String getCharSet() {
        return TextUtils.isEmpty(this.charSet) ? "utf-8" : this.charSet;
    }

    public CookieManager getCookieManager() {
        return this.cookieManager;
    }

    public IHttpFileLenAdapter getFileLenAdapter() {
        SoftReference<IHttpFileLenAdapter> softReference = this.fileLenAdapter;
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }

    public IHttpFileNameAdapter getFileNameAdapter() {
        SoftReference<IHttpFileNameAdapter> softReference = this.fileNameAdapter;
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }

    public Map<String, String> getFormFields() {
        return this.formFields;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public RequestEnum getRequestEnum() {
        return this.requestEnum;
    }

    public boolean isChunked() {
        return this.isChunked;
    }

    public boolean isUseServerFileName() {
        return this.useServerFileName;
    }

    public void setAttachment(String str) {
        this.attachment = str;
    }

    public void setCharSet(String str) {
        this.charSet = str;
    }

    public void setChunked(boolean z2) {
        this.isChunked = z2;
    }

    public void setCookieManager(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    public void setFileLenAdapter(IHttpFileLenAdapter iHttpFileLenAdapter) {
        this.fileLenAdapter = new SoftReference<>(iHttpFileLenAdapter);
    }

    public void setFileNameAdapter(IHttpFileNameAdapter iHttpFileNameAdapter) {
        this.fileNameAdapter = new SoftReference<>(iHttpFileNameAdapter);
    }

    public void setFormFields(Map<String, String> map) {
        this.formFields = map;
    }

    public void setHeaders(Map<String, String> map) {
        this.headers = map;
    }

    public void setParams(Map<String, String> map) {
        this.params = map;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setRedirectUrl(String str) {
        this.redirectUrl = str;
    }

    public void setRequestEnum(RequestEnum requestEnum) {
        this.requestEnum = requestEnum;
    }

    public void setUseServerFileName(boolean z2) {
        this.useServerFileName = z2;
    }
}
