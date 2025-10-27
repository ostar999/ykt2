package org.eclipse.jetty.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferUtil;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes9.dex */
public class ContentExchange extends CachedExchange {
    private int _bufferSize;
    private String _encoding;
    private File _fileForUpload;
    private ByteArrayOutputStream _responseContent;

    public ContentExchange() {
        super(false);
        this._bufferSize = 4096;
        this._encoding = "utf-8";
    }

    private synchronized InputStream getInputStream() throws IOException {
        return new FileInputStream(this._fileForUpload);
    }

    public synchronized File getFileForUpload() {
        return this._fileForUpload;
    }

    public synchronized String getResponseContent() throws UnsupportedEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = this._responseContent;
        if (byteArrayOutputStream == null) {
            return null;
        }
        return byteArrayOutputStream.toString(this._encoding);
    }

    public synchronized byte[] getResponseContentBytes() {
        ByteArrayOutputStream byteArrayOutputStream = this._responseContent;
        if (byteArrayOutputStream == null) {
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // org.eclipse.jetty.client.HttpExchange
    public synchronized void onResponseContent(Buffer buffer) throws IOException {
        super.onResponseContent(buffer);
        if (this._responseContent == null) {
            this._responseContent = new ByteArrayOutputStream(this._bufferSize);
        }
        buffer.writeTo(this._responseContent);
    }

    @Override // org.eclipse.jetty.client.CachedExchange, org.eclipse.jetty.client.HttpExchange
    public synchronized void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
        String strAsciiToLowerCase;
        int iIndexOf;
        super.onResponseHeader(buffer, buffer2);
        int ordinal = HttpHeaders.CACHE.getOrdinal(buffer);
        if (ordinal == 12) {
            this._bufferSize = BufferUtil.toInt(buffer2);
        } else if (ordinal == 16 && (iIndexOf = (strAsciiToLowerCase = StringUtil.asciiToLowerCase(buffer2.toString())).indexOf("charset=")) > 0) {
            String strSubstring = strAsciiToLowerCase.substring(iIndexOf + 8);
            this._encoding = strSubstring;
            int iIndexOf2 = strSubstring.indexOf(59);
            if (iIndexOf2 > 0) {
                this._encoding = this._encoding.substring(0, iIndexOf2);
            }
        }
    }

    @Override // org.eclipse.jetty.client.CachedExchange, org.eclipse.jetty.client.HttpExchange
    public synchronized void onResponseStatus(Buffer buffer, int i2, Buffer buffer2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = this._responseContent;
        if (byteArrayOutputStream != null) {
            byteArrayOutputStream.reset();
        }
        super.onResponseStatus(buffer, i2, buffer2);
    }

    @Override // org.eclipse.jetty.client.HttpExchange
    public synchronized void onRetry() throws IOException {
        if (this._fileForUpload != null) {
            setRequestContent(null);
            setRequestContentSource(getInputStream());
        } else {
            super.onRetry();
        }
    }

    public synchronized void setFileForUpload(File file) throws IOException {
        this._fileForUpload = file;
        setRequestContentSource(getInputStream());
    }

    public ContentExchange(boolean z2) {
        super(z2);
        this._bufferSize = 4096;
        this._encoding = "utf-8";
    }
}
