package org.apache.http.impl;

import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.web.PLVWebview;
import java.util.Locale;
import org.apache.http.ReasonPhraseCatalog;

/* loaded from: classes9.dex */
public class EnglishReasonPhraseCatalog implements ReasonPhraseCatalog {
    public static final EnglishReasonPhraseCatalog INSTANCE = new EnglishReasonPhraseCatalog();
    private static final String[][] REASON_PHRASES = {null, new String[3], new String[8], new String[8], new String[25], new String[8]};

    static {
        setReason(200, PLVWebview.MESSAGE_OK);
        setReason(201, "Created");
        setReason(202, "Accepted");
        setReason(204, "No Content");
        setReason(301, "Moved Permanently");
        setReason(302, "Moved Temporarily");
        setReason(304, "Not Modified");
        setReason(400, "Bad Request");
        setReason(401, "Unauthorized");
        setReason(403, "Forbidden");
        setReason(404, "Not Found");
        setReason(500, "Internal Server Error");
        setReason(501, "Not Implemented");
        setReason(502, "Bad Gateway");
        setReason(503, "Service Unavailable");
        setReason(100, "Continue");
        setReason(307, "Temporary Redirect");
        setReason(405, "Method Not Allowed");
        setReason(409, "Conflict");
        setReason(412, "Precondition Failed");
        setReason(413, "Request Too Long");
        setReason(414, "Request-URI Too Long");
        setReason(415, "Unsupported Media Type");
        setReason(300, "Multiple Choices");
        setReason(303, "See Other");
        setReason(305, "Use Proxy");
        setReason(402, "Payment Required");
        setReason(406, "Not Acceptable");
        setReason(407, "Proxy Authentication Required");
        setReason(408, "Request Timeout");
        setReason(101, "Switching Protocols");
        setReason(203, "Non Authoritative Information");
        setReason(205, "Reset Content");
        setReason(206, "Partial Content");
        setReason(504, "Gateway Timeout");
        setReason(505, "Http Version Not Supported");
        setReason(410, "Gone");
        setReason(411, "Length Required");
        setReason(416, "Requested Range Not Satisfiable");
        setReason(417, "Expectation Failed");
        setReason(102, "Processing");
        setReason(207, "Multi-Status");
        setReason(422, "Unprocessable Entity");
        setReason(419, "Insufficient Space On Resource");
        setReason(420, "Method Failure");
        setReason(423, "Locked");
        setReason(507, "Insufficient Storage");
        setReason(424, "Failed Dependency");
    }

    private static void setReason(int i2, String str) {
        int i3 = i2 / 100;
        REASON_PHRASES[i3][i2 - (i3 * 100)] = str;
    }

    @Override // org.apache.http.ReasonPhraseCatalog
    public String getReason(int i2, Locale locale) {
        if (i2 < 100 || i2 >= 600) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unknown category for status code ");
            stringBuffer.append(i2);
            stringBuffer.append(StrPool.DOT);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        int i3 = i2 / 100;
        int i4 = i2 - (i3 * 100);
        String[] strArr = REASON_PHRASES[i3];
        if (strArr.length > i4) {
            return strArr[i4];
        }
        return null;
    }
}
