package com.plv.foundationsdk.download.utils;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.download.bean.PLVMultimedia;
import com.plv.foundationsdk.download.listener.IPLVDownloaderSDKListener;
import com.plv.foundationsdk.utils.PLVDnsUtil;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class PLVDownloaderSDKUtils {
    public static void downloadError(@NonNull String str, int i2, @Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2, @Nullable IPLVDownloaderSDKListener iPLVDownloaderSDKListener) {
        if (iPLVDownloaderSDKListener != null) {
            iPLVDownloaderSDKListener.onDownloadError(str, i2, arrayList, arrayList2);
        }
    }

    public static Map<String, String> getHttpResponseHeader(HttpURLConnection httpURLConnection) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i2 = 0;
        while (true) {
            String headerField = httpURLConnection.getHeaderField(i2);
            if (headerField == null) {
                return linkedHashMap;
            }
            String headerFieldKey = httpURLConnection.getHeaderFieldKey(i2);
            if (!TextUtils.isEmpty(headerFieldKey)) {
                linkedHashMap.put(headerFieldKey, headerField);
            }
            i2++;
        }
    }

    private static String getNeedReplacePptUrlPart(String str) {
        return str.substring(0, str.lastIndexOf("/", str.lastIndexOf("/") - 1) + 1);
    }

    public static List<PLVMultimedia> getTSFileList(String str, String str2, String str3) {
        int i2;
        Pattern patternCompile;
        String strTsReplaceInM3U8 = PLVDnsUtil.tsReplaceInM3U8(str);
        if (str.equals(strTsReplaceInM3U8)) {
            patternCompile = Pattern.compile("(https?://[^/]*)(.*\\.ts)");
            i2 = 2;
        } else {
            i2 = 4;
            patternCompile = Pattern.compile("(https?://(([0-9]{1,3}\\.){3}?[0-9]{1,3})?/[^/]*)(.*\\.ts)");
            str = strTsReplaceInM3U8;
        }
        Matcher matcher = Pattern.compile("https?://.*ts").matcher(str);
        ArrayList arrayList = new ArrayList();
        str2.substring(0, str2.indexOf(StrPool.UNDERLINE));
        StringBuilder sb = new StringBuilder();
        String strGroup = "";
        while (matcher.find()) {
            String strGroup2 = matcher.group();
            Matcher matcher2 = patternCompile.matcher(strGroup2);
            if (matcher2.find()) {
                strGroup = matcher2.group(i2);
            }
            String strReplace = strGroup.substring(strGroup.lastIndexOf("/") + 1).replace(StrPool.DOT, StrPool.UNDERLINE);
            sb.delete(0, sb.length());
            sb.append(str3);
            sb.append(File.separator);
            arrayList.add(new PLVMultimedia(strGroup2, sb.toString(), strReplace));
        }
        return arrayList;
    }
}
