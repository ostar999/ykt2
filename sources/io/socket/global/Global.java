package io.socket.global;

import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.slf4j.Marker;

/* loaded from: classes8.dex */
public class Global {
    private Global() {
    }

    public static String decodeURIComponent(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String encodeURIComponent(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace(Marker.ANY_NON_NULL_MARKER, "%20").replace("%21", "!").replace("%27", "'").replace("%28", "(").replace("%29", ")").replace("%7E", Constants.WAVE_SEPARATOR);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }
}
