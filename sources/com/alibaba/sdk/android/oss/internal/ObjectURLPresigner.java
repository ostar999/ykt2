package com.alibaba.sdk.android.oss.internal;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.HttpMethod;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.DateUtil;
import com.alibaba.sdk.android.oss.common.utils.HttpUtil;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.GeneratePresignedUrlRequest;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ObjectURLPresigner {
    private ClientConfiguration conf;
    private OSSCredentialProvider credentialProvider;
    private URI endpoint;

    public ObjectURLPresigner(URI uri, OSSCredentialProvider oSSCredentialProvider, ClientConfiguration clientConfiguration) {
        this.endpoint = uri;
        this.credentialProvider = oSSCredentialProvider;
        this.conf = clientConfiguration;
    }

    private String buildCanonicalHost(URI uri, String str, ClientConfiguration clientConfiguration) {
        String str2;
        String host = uri.getHost();
        String path = uri.getPath();
        int port = uri.getPort();
        String strValueOf = port != -1 ? String.valueOf(port) : null;
        if (TextUtils.isEmpty(strValueOf)) {
            str2 = host;
        } else {
            str2 = host + ":" + strValueOf;
        }
        boolean zIsValidateIP = false;
        if (!TextUtils.isEmpty(str)) {
            if (OSSUtils.isOssOriginHost(host)) {
                str2 = str + StrPool.DOT + host;
            } else if (!OSSUtils.isInCustomCnameExcludeList(host, clientConfiguration.getCustomCnameExcludeList())) {
                try {
                    zIsValidateIP = OSSUtils.isValidateIP(host);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if (clientConfiguration.isPathStyleAccessEnable()) {
                zIsValidateIP = true;
            } else {
                str2 = str + StrPool.DOT + host;
            }
        }
        if (clientConfiguration.isCustomPathPrefixEnable() && path != null) {
            str2 = str2 + path;
        }
        if (!zIsValidateIP) {
            return str2;
        }
        return str2 + "/" + str;
    }

    public String presignConstrainedURL(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws ClientException {
        OSSFederationToken federationToken;
        String strSign;
        String bucketName = generatePresignedUrlRequest.getBucketName();
        String key = generatePresignedUrlRequest.getKey();
        String strValueOf = String.valueOf((DateUtil.getFixedSkewedTimeMillis() / 1000) + generatePresignedUrlRequest.getExpiration());
        HttpMethod method = generatePresignedUrlRequest.getMethod() != null ? generatePresignedUrlRequest.getMethod() : HttpMethod.GET;
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setEndpoint(this.endpoint);
        requestMessage.setMethod(method);
        requestMessage.setBucketName(bucketName);
        requestMessage.setObjectKey(key);
        requestMessage.getHeaders().put("Date", strValueOf);
        if (generatePresignedUrlRequest.getContentType() != null && !generatePresignedUrlRequest.getContentType().trim().equals("")) {
            requestMessage.getHeaders().put("Content-Type", generatePresignedUrlRequest.getContentType());
        }
        if (generatePresignedUrlRequest.getContentMD5() != null && !generatePresignedUrlRequest.getContentMD5().trim().equals("")) {
            requestMessage.getHeaders().put("Content-MD5", generatePresignedUrlRequest.getContentMD5());
        }
        if (generatePresignedUrlRequest.getQueryParameter() != null && generatePresignedUrlRequest.getQueryParameter().size() > 0) {
            for (Map.Entry<String, String> entry : generatePresignedUrlRequest.getQueryParameter().entrySet()) {
                requestMessage.getParameters().put(entry.getKey(), entry.getValue());
            }
        }
        if (generatePresignedUrlRequest.getProcess() != null && !generatePresignedUrlRequest.getProcess().trim().equals("")) {
            requestMessage.getParameters().put(RequestParameters.X_OSS_PROCESS, generatePresignedUrlRequest.getProcess());
        }
        OSSCredentialProvider oSSCredentialProvider = this.credentialProvider;
        if (oSSCredentialProvider instanceof OSSFederationCredentialProvider) {
            federationToken = ((OSSFederationCredentialProvider) oSSCredentialProvider).getValidFederationToken();
            requestMessage.getParameters().put(RequestParameters.SECURITY_TOKEN, federationToken.getSecurityToken());
        } else if (oSSCredentialProvider instanceof OSSStsTokenCredentialProvider) {
            federationToken = ((OSSStsTokenCredentialProvider) oSSCredentialProvider).getFederationToken();
            requestMessage.getParameters().put(RequestParameters.SECURITY_TOKEN, federationToken.getSecurityToken());
        } else {
            federationToken = null;
        }
        String strBuildCanonicalString = OSSUtils.buildCanonicalString(requestMessage);
        OSSCredentialProvider oSSCredentialProvider2 = this.credentialProvider;
        if ((oSSCredentialProvider2 instanceof OSSFederationCredentialProvider) || (oSSCredentialProvider2 instanceof OSSStsTokenCredentialProvider)) {
            strSign = OSSUtils.sign(federationToken.getTempAK(), federationToken.getTempSK(), strBuildCanonicalString);
        } else if (oSSCredentialProvider2 instanceof OSSPlainTextAKSKCredentialProvider) {
            strSign = OSSUtils.sign(((OSSPlainTextAKSKCredentialProvider) oSSCredentialProvider2).getAccessKeyId(), ((OSSPlainTextAKSKCredentialProvider) this.credentialProvider).getAccessKeySecret(), strBuildCanonicalString);
        } else {
            if (!(oSSCredentialProvider2 instanceof OSSCustomSignerCredentialProvider)) {
                throw new ClientException("Unknown credentialProvider!");
            }
            strSign = ((OSSCustomSignerCredentialProvider) oSSCredentialProvider2).signContent(strBuildCanonicalString);
        }
        String strSubstring = strSign.split(":")[0].substring(4);
        String str = strSign.split(":")[1];
        String strBuildCanonicalHost = buildCanonicalHost(this.endpoint, bucketName, this.conf);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Expires", strValueOf);
        linkedHashMap.put(RequestParameters.OSS_ACCESS_KEY_ID, strSubstring);
        linkedHashMap.put("Signature", str);
        linkedHashMap.putAll(requestMessage.getParameters());
        return this.endpoint.getScheme() + "://" + strBuildCanonicalHost + "/" + HttpUtil.urlEncode(key, "utf-8") + "?" + HttpUtil.paramToQueryString(linkedHashMap, "utf-8");
    }

    public String presignPublicURL(String str, String str2) {
        return this.endpoint.getScheme() + "://" + buildCanonicalHost(this.endpoint, str, this.conf) + "/" + HttpUtil.urlEncode(str2, "utf-8");
    }

    public String presignConstrainedURL(String str, String str2, long j2) throws ClientException {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(str, str2);
        generatePresignedUrlRequest.setExpiration(j2);
        return presignConstrainedURL(generatePresignedUrlRequest);
    }
}
