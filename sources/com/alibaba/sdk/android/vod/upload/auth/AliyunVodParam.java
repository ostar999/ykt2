package com.alibaba.sdk.android.vod.upload.auth;

import android.text.TextUtils;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.auth.common.AliyunVodSignature;
import com.aliyun.auth.core.AliyunVodKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AliyunVodParam {
    public static String generateOpenAPIURL(Map<String, String> map, Map<String, String> map2, String str) {
        return generateURL(AliyunVodHttpCommon.VOD_DOMAIN, "GET", map, map2, str);
    }

    public static Map<String, String> generatePrivateParamtersToReUploadVideo(String str) {
        HashMap map = new HashMap();
        map.put(AliyunVodKey.KEY_VOD_ACTION, AliyunVodHttpCommon.Action.REFRESH_UPLOAD_VIDEO);
        map.put(AliyunVodKey.KEY_VOD_VIDEOID, str);
        return map;
    }

    public static Map<String, String> generatePrivateParamtersToUploadImage(VodInfo vodInfo, String str, String str2) {
        HashMap map = new HashMap();
        map.put(AliyunVodKey.KEY_VOD_ACTION, AliyunVodHttpCommon.Action.CREATE_UPLOAD_IMAGE);
        map.put(AliyunVodKey.KEY_VOD_IMAGETYPE, AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
        map.put(AliyunVodKey.KEY_VOD_IMAGEEXT, "png");
        map.put(AliyunVodKey.KEY_VOD_TITLE, vodInfo.getTitle());
        map.put(AliyunVodKey.KEY_VOD_TAGS, generateTags(vodInfo.getTags()));
        map.put(AliyunVodKey.KEY_VOD_CATEID, String.valueOf(vodInfo.getCateId()));
        map.put(AliyunVodKey.KEY_VOD_DESCRIPTION, vodInfo.getDesc());
        map.put(AliyunVodKey.KEY_VOD_STORAGELOCATION, str);
        map.put(AliyunVodKey.KEY_VOD_USERDATA, vodInfo.getUserData());
        map.put("AppId", str2);
        return map;
    }

    public static Map<String, String> generatePrivateParamtersToUploadVideo(VodInfo vodInfo, boolean z2, String str, String str2, String str3, String str4) {
        HashMap map = new HashMap();
        map.put(AliyunVodKey.KEY_VOD_ACTION, AliyunVodHttpCommon.Action.CREATE_UPLOAD_VIDEO);
        map.put(AliyunVodKey.KEY_VOD_TITLE, vodInfo.getTitle());
        map.put(AliyunVodKey.KEY_VOD_FILENAME, vodInfo.getFileName());
        map.put(AliyunVodKey.KEY_VOD_FILESIZE, vodInfo.getFileSize());
        map.put(AliyunVodKey.KEY_VOD_DESCRIPTION, vodInfo.getDesc());
        map.put(AliyunVodKey.KEY_VOD_COVERURL, vodInfo.getCoverUrl());
        map.put(AliyunVodKey.KEY_VOD_CATEID, String.valueOf(vodInfo.getCateId()));
        map.put(AliyunVodKey.KEY_VOD_TAGS, generateTags(vodInfo.getTags()));
        map.put(AliyunVodKey.KEY_VOD_STORAGELOCATION, str2);
        map.put(AliyunVodKey.KEY_VOD_USERDATA, vodInfo.getUserData());
        if (TextUtils.isEmpty(str)) {
            map.put(AliyunVodKey.KEY_VOD_TRANSCODEMODE, z2 ? AliyunVodHttpCommon.COMON_FAST_TRANSCODEMODE : AliyunVodHttpCommon.COMON_NO_TRANSCODEMODE);
        } else {
            map.put(AliyunVodKey.KEY_VOD_TEMPLATEGROUPID, str);
        }
        map.put(AliyunVodKey.KEY_VOD_WORKFLOWLD, str3);
        map.put("AppId", str4);
        return map;
    }

    public static Map<String, String> generatePublicParamters(String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put(AliyunVodKey.KEY_VOD_COMMON_FORMAT, AliyunVodHttpCommon.Format.FORMAT_JSON);
        map.put("Version", AliyunVodHttpCommon.COMMON_API_VERSION);
        map.put(AliyunVodKey.KEY_VOD_COMMON_ACCESSKEYID, str);
        map.put(AliyunVodKey.KEY_VOD_COMMON_SIGNATURE_METHOD, "HMAC-SHA1");
        map.put(AliyunVodKey.KEY_VOD_COMMON_SIGNATURE_VERSION, "1.0");
        map.put(AliyunVodKey.KEY_VOD_COMMON_SIGNATURE_NONCE, AliyunVodHttpCommon.generateRandom());
        map.put(AliyunVodKey.KEY_VOD_COMMON_REQUEST_ID, str3);
        if (str2 != null && str2.length() > 0) {
            map.put(AliyunVodKey.KEY_VOD_COMMON_SECURITY_TOKEN, str2);
        }
        return map;
    }

    private static String generateTags(List<String> list) {
        String str = "";
        if (list == null || list.size() <= 0) {
            return "";
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            str = str + "," + list.get(i2).toString();
        }
        return trimFirstAndLastChar(str, ',');
    }

    private static String generateURL(String str, String str2, Map<String, String> map, Map<String, String> map2, String str3) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String cqs = AliyunVodSignature.getCQS(AliyunVodSignature.getAllParams(map, map2));
        System.out.print("CanonicalizedQueryString = " + cqs);
        String str4 = str2 + "&" + AliyunVodSignature.percentEncode("/") + "&" + AliyunVodSignature.percentEncode(cqs);
        System.out.print("StringtoSign = " + str4);
        String strHmacSHA1Signature = AliyunVodSignature.hmacSHA1Signature(str3, str4);
        System.out.print("Signature = " + strHmacSHA1Signature);
        return str + "?" + cqs + "&" + AliyunVodSignature.percentEncode("Signature") + "=" + AliyunVodSignature.percentEncode(strHmacSHA1Signature);
    }

    public static String trimFirstAndLastChar(String str, char c3) {
        while (true) {
            str = str.substring(str.indexOf(c3) == 0 ? 1 : 0, str.lastIndexOf(c3) + 1 == str.length() ? str.lastIndexOf(c3) : str.length());
            boolean z2 = str.indexOf(c3) == 0;
            boolean z3 = str.lastIndexOf(c3) + 1 == str.length();
            if (!z2 && !z3) {
                return str;
            }
        }
    }

    public static String generateOpenAPIURL(String str, Map<String, String> map, Map<String, String> map2, String str2) {
        return generateURL(AliyunVodHttpCommon.generateVodDomain(str), "GET", map, map2, str2);
    }
}
