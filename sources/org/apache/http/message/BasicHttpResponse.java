package org.apache.http.message;

import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;

/* loaded from: classes9.dex */
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {
    private HttpEntity entity;
    private Locale locale;
    private ReasonPhraseCatalog reasonCatalog;
    private StatusLine statusline;

    public BasicHttpResponse(StatusLine statusLine, ReasonPhraseCatalog reasonPhraseCatalog, Locale locale) {
        if (statusLine == null) {
            throw new IllegalArgumentException("Status line may not be null.");
        }
        this.statusline = statusLine;
        this.reasonCatalog = reasonPhraseCatalog;
        this.locale = locale == null ? Locale.getDefault() : locale;
    }

    @Override // org.apache.http.HttpResponse
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override // org.apache.http.HttpResponse
    public Locale getLocale() {
        return this.locale;
    }

    @Override // org.apache.http.HttpMessage
    public ProtocolVersion getProtocolVersion() {
        return this.statusline.getProtocolVersion();
    }

    public String getReason(int i2) {
        ReasonPhraseCatalog reasonPhraseCatalog = this.reasonCatalog;
        if (reasonPhraseCatalog == null) {
            return null;
        }
        return reasonPhraseCatalog.getReason(i2, this.locale);
    }

    @Override // org.apache.http.HttpResponse
    public StatusLine getStatusLine() {
        return this.statusline;
    }

    @Override // org.apache.http.HttpResponse
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }

    @Override // org.apache.http.HttpResponse
    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale may not be null.");
        }
        this.locale = locale;
        int statusCode = this.statusline.getStatusCode();
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), statusCode, getReason(statusCode));
    }

    @Override // org.apache.http.HttpResponse
    public void setReasonPhrase(String str) {
        if (str != null && (str.indexOf(10) >= 0 || str.indexOf(13) >= 0)) {
            throw new IllegalArgumentException("Line break in reason phrase.");
        }
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), this.statusline.getStatusCode(), str);
    }

    @Override // org.apache.http.HttpResponse
    public void setStatusCode(int i2) {
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), i2, getReason(i2));
    }

    @Override // org.apache.http.HttpResponse
    public void setStatusLine(StatusLine statusLine) {
        if (statusLine == null) {
            throw new IllegalArgumentException("Status line may not be null");
        }
        this.statusline = statusLine;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.statusline);
        stringBuffer.append(" ");
        stringBuffer.append(this.headergroup);
        return stringBuffer.toString();
    }

    @Override // org.apache.http.HttpResponse
    public void setStatusLine(ProtocolVersion protocolVersion, int i2) {
        this.statusline = new BasicStatusLine(protocolVersion, i2, getReason(i2));
    }

    @Override // org.apache.http.HttpResponse
    public void setStatusLine(ProtocolVersion protocolVersion, int i2, String str) {
        this.statusline = new BasicStatusLine(protocolVersion, i2, str);
    }

    public BasicHttpResponse(StatusLine statusLine) {
        this(statusLine, (ReasonPhraseCatalog) null, (Locale) null);
    }

    public BasicHttpResponse(ProtocolVersion protocolVersion, int i2, String str) {
        this(new BasicStatusLine(protocolVersion, i2, str), (ReasonPhraseCatalog) null, (Locale) null);
    }
}
