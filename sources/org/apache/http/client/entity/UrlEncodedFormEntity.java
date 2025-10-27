package org.apache.http.client.entity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;

@NotThreadSafe
/* loaded from: classes9.dex */
public class UrlEncodedFormEntity extends StringEntity {
    public UrlEncodedFormEntity(List<? extends NameValuePair> list, String str) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(list, str), str);
        StringBuilder sb = new StringBuilder();
        sb.append("application/x-www-form-urlencoded; charset=");
        sb.append(str == null ? "ISO-8859-1" : str);
        setContentType(sb.toString());
    }

    public UrlEncodedFormEntity(List<? extends NameValuePair> list) throws UnsupportedEncodingException {
        this(list, "ISO-8859-1");
    }
}
