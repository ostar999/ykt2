package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Locale;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.ParseException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.protocol.HttpContext;
import org.eclipse.jetty.http.HttpHeaderValues;

@Immutable
/* loaded from: classes9.dex */
public class ResponseContentEncoding implements HttpResponseInterceptor {
    @Override // org.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, ParseException, IOException {
        Header contentEncoding;
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null || (contentEncoding = entity.getContentEncoding()) == null) {
            return;
        }
        HeaderElement[] elements = contentEncoding.getElements();
        if (elements.length > 0) {
            HeaderElement headerElement = elements[0];
            String lowerCase = headerElement.getName().toLowerCase(Locale.US);
            if (HttpHeaderValues.GZIP.equals(lowerCase) || "x-gzip".equals(lowerCase)) {
                httpResponse.setEntity(new GzipDecompressingEntity(httpResponse.getEntity()));
                return;
            }
            if (CompressorStreamFactory.DEFLATE.equals(lowerCase)) {
                httpResponse.setEntity(new DeflateDecompressingEntity(httpResponse.getEntity()));
            } else {
                if ("identity".equals(lowerCase)) {
                    return;
                }
                throw new HttpException("Unsupported Content-Coding: " + headerElement.getName());
            }
        }
    }
}
