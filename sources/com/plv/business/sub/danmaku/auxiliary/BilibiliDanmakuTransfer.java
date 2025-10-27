package com.plv.business.sub.danmaku.auxiliary;

import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* loaded from: classes4.dex */
public class BilibiliDanmakuTransfer {
    public static long toBilibiliColor(String str) {
        return Integer.parseInt(str.substring(2), 16);
    }

    public static int toBilibiliFontMode(String str) {
        if (str.equals(PLVDanmakuInfo.FONTMODE_TOP)) {
            return 5;
        }
        return str.equals(PLVDanmakuInfo.FONTMODE_BOTTOM) ? 4 : 1;
    }

    public static long toBilibiliTime(String str) {
        if (!str.matches("[0-9][0-9]:[0-5][0-9]:[0-5][0-9]")) {
            str = "00:00:00";
        }
        String[] strArrSplit = str.split(":");
        return (PLVFormatUtils.parseInt(strArrSplit[0]) * 3600) + (PLVFormatUtils.parseInt(strArrSplit[1]) * 60) + PLVFormatUtils.parseInt(strArrSplit[2]);
    }

    public static InputStream transferToInputStream(List<PLVDanmakuInfo> list) throws IllegalStateException, IOException, IllegalArgumentException {
        String str = null;
        if (list == null || list.isEmpty()) {
            return null;
        }
        DanmakuXmlSerializer danmakuXmlSerializer = new DanmakuXmlSerializer();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        StringBuilder sb = new StringBuilder();
        danmakuXmlSerializer.setOutput(byteArrayOutputStream, "utf-8");
        danmakuXmlSerializer.startDocument("utf-8", Boolean.TRUE);
        danmakuXmlSerializer.startTag(null, am.aC).startTag(null, "chatserver").text("www.polyv.net").endTag(null, "chatserver").startTag(null, "chatid").text("123456").endTag(null, "chatid").startTag(null, "mission").text("0").endTag(null, "mission").startTag(null, "maxlimit").text("123456").endTag(null, "maxlimit").startTag(null, SocialConstants.PARAM_SOURCE).text("k-v").endTag(null, SocialConstants.PARAM_SOURCE);
        for (PLVDanmakuInfo pLVDanmakuInfo : list) {
            long bilibiliTime = toBilibiliTime(pLVDanmakuInfo.getTime());
            int bilibiliFontMode = toBilibiliFontMode(pLVDanmakuInfo.getFontMode());
            String fontSize = pLVDanmakuInfo.getFontSize();
            long bilibiliColor = toBilibiliColor(pLVDanmakuInfo.getFontColor());
            String timestamp = pLVDanmakuInfo.getTimestamp();
            String msg = pLVDanmakuInfo.getMsg();
            sb.delete(0, sb.length());
            sb.append(bilibiliTime);
            sb.append(",");
            sb.append(bilibiliFontMode);
            sb.append(",");
            sb.append(fontSize);
            sb.append(",");
            sb.append(bilibiliColor);
            sb.append(",");
            sb.append(timestamp);
            sb.append(",");
            sb.append("123456");
            sb.append(",");
            sb.append("123456");
            sb.append(",");
            sb.append("123456");
            danmakuXmlSerializer.startTag(null, "d");
            danmakuXmlSerializer.attribute(null, "p", sb.toString());
            danmakuXmlSerializer.text(msg);
            danmakuXmlSerializer.endTag(null, "d");
            str = null;
        }
        danmakuXmlSerializer.endTag(str, am.aC);
        danmakuXmlSerializer.endDocument();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
