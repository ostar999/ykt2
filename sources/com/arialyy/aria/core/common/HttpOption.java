package com.arialyy.aria.core.common;

import android.text.TextUtils;
import com.arialyy.aria.core.processor.IHttpFileLenAdapter;
import com.arialyy.aria.core.processor.IHttpFileNameAdapter;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class HttpOption extends BaseOption {
    private String attachment;
    private IHttpFileLenAdapter fileLenAdapter;
    private IHttpFileNameAdapter fileNameAdapter;
    private Map<String, String> formFields;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Proxy proxy;
    private RequestEnum requestEnum = RequestEnum.GET;
    private boolean useServerFileName = false;

    public HttpOption addHeader(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            ALog.w(this.TAG, "设置header失败，header对应的key不能为null");
            return this;
        }
        if (TextUtils.isEmpty(str2)) {
            ALog.w(this.TAG, "设置header失败，header对应的value不能为null");
            return this;
        }
        if (this.headers == null) {
            this.headers = new HashMap();
        }
        this.headers.put(str, str2);
        return this;
    }

    public HttpOption addHeaders(Map<String, String> map) {
        if (map.size() == 0) {
            ALog.w(this.TAG, "设置header失败，map没有header数据");
            return this;
        }
        if (this.headers == null) {
            this.headers = new HashMap();
        }
        this.headers.putAll(map);
        return this;
    }

    public HttpOption setAttachment(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "file";
        }
        this.attachment = str;
        return this;
    }

    public HttpOption setFilNameAdapter(IHttpFileNameAdapter iHttpFileNameAdapter) {
        if (iHttpFileNameAdapter == null) {
            throw new IllegalArgumentException("adapter为空");
        }
        CheckUtil.checkMemberClass(iHttpFileNameAdapter.getClass());
        this.fileNameAdapter = iHttpFileNameAdapter;
        return this;
    }

    public HttpOption setFileLenAdapter(IHttpFileLenAdapter iHttpFileLenAdapter) {
        if (iHttpFileLenAdapter == null) {
            throw new IllegalArgumentException("adapter为空");
        }
        CheckUtil.checkMemberClass(iHttpFileLenAdapter.getClass());
        this.fileLenAdapter = iHttpFileLenAdapter;
        return this;
    }

    public HttpOption setFormFields(Map<String, String> map) {
        this.formFields = map;
        return this;
    }

    public HttpOption setParam(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.d(this.TAG, "key 或value 为空");
            return this;
        }
        if (this.params == null) {
            this.params = new HashMap();
        }
        this.params.put(str, str2);
        return this;
    }

    public HttpOption setParams(Map<String, String> map) {
        if (this.params == null) {
            this.params = new HashMap();
        }
        this.params.putAll(map);
        return this;
    }

    public HttpOption setRequestType(RequestEnum requestEnum) {
        this.requestEnum = requestEnum;
        return this;
    }

    public HttpOption setUrlProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public HttpOption useServerFileName(boolean z2) {
        this.useServerFileName = z2;
        return this;
    }
}
