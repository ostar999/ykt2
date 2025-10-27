package com.easefun.polyv.livecommon.ui.widget.webview;

import android.text.TextUtils;
import com.alipay.sdk.util.h;

/* loaded from: classes3.dex */
public class PLVWebViewContentUtils {
    public static String toWebViewContent(String content) {
        return toWebViewContent(content, "#000000");
    }

    public static String toWebViewContent(String content, String color) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        String str = "color:" + color + h.f3376b;
        String strReplaceAll = content.replaceAll("img src=\"//", "img src=\\\"https://").replace("<img ", "<img style=\" width:100%;\" ").replaceAll("<li>", "<li style=\"" + str + "\">").replaceAll("<p>", "<p style=\"word-break:break-all;" + str + "\">").replaceAll("<div>", "<div style=\"word-break:break-all;" + str + "\">");
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' rules=all style=\"");
        sb.append(str);
        sb.append("\">");
        return "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n        <meta charset=\"UTF-8\">\n        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n        <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n        <title>Document</title>\n</head>\n<body style=\" color:" + color + ";margin:0;padding:0\">" + strReplaceAll.replaceAll("<table>", sb.toString()).replaceAll("<td>", "<td width=\"36\">") + "</body>\n</html>";
    }
}
