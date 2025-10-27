package com.alibaba.sdk.android.oss.internal;

import android.net.Uri;
import com.alibaba.sdk.android.oss.common.HttpMethod;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.HttpdnsMini;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.BucketLifecycleRule;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import k.a;
import org.wrtca.api.PeerConnectionFactory;

/* loaded from: classes2.dex */
public class RequestMessage extends HttpMessage {
    private String bucketName;
    private boolean checkCRC64;
    private OSSCredentialProvider credentialProvider;
    private URI endpoint;
    private String ipWithHeader;
    private HttpMethod method;
    private String objectKey;
    private URI service;
    private byte[] uploadData;
    private String uploadFilePath;
    private Uri uploadUri;
    private boolean isAuthorizationRequired = true;
    private Map<String, String> parameters = new LinkedHashMap();
    private boolean httpDnsEnable = false;
    private boolean pathStyleAccessEnable = false;
    private boolean customPathPrefixEnable = false;
    private boolean isInCustomCnameExcludeList = false;

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void addHeader(String str, String str2) {
        super.addHeader(str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x012c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String buildCanonicalURL() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 607
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.oss.internal.RequestMessage.buildCanonicalURL():java.lang.String");
    }

    public String buildOSSServiceURL() {
        String ipByHostAsync;
        OSSUtils.assertTrue(this.service != null, "Service haven't been set!");
        String host = this.service.getHost();
        String scheme = this.service.getScheme();
        if (isHttpDnsEnable() && scheme.equalsIgnoreCase("http")) {
            ipByHostAsync = HttpdnsMini.getInstance().getIpByHostAsync(host);
        } else {
            OSSLog.logDebug("[buildOSSServiceURL], disable httpdns or http is not need httpdns");
            ipByHostAsync = null;
        }
        if (ipByHostAsync == null) {
            ipByHostAsync = host;
        }
        getHeaders().put("Host", host);
        String str = scheme + "://" + ipByHostAsync;
        String strParamToQueryString = OSSUtils.paramToQueryString(this.parameters, "utf-8");
        if (OSSUtils.isEmptyString(strParamToQueryString)) {
            return str;
        }
        return str + "?" + strParamToQueryString;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void close() throws IOException {
        super.close();
    }

    public void createBucketRequestBodyMarshall(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (map != null) {
            stringBuffer.append("<CreateBucketConfiguration>");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuffer.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
            }
            stringBuffer.append("</CreateBucketConfiguration>");
            byte[] bytes = stringBuffer.toString().getBytes("utf-8");
            long length = bytes.length;
            setContent(new ByteArrayInputStream(bytes));
            setContentLength(length);
        }
    }

    public byte[] deleteMultipleObjectRequestBodyMarshall(List<String> list, boolean z2) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<Delete>");
        if (z2) {
            stringBuffer.append("<Quiet>true</Quiet>");
        } else {
            stringBuffer.append("<Quiet>false</Quiet>");
        }
        for (String str : list) {
            stringBuffer.append("<Object>");
            stringBuffer.append("<Key>");
            stringBuffer.append(str);
            stringBuffer.append("</Key>");
            stringBuffer.append("</Object>");
        }
        stringBuffer.append("</Delete>");
        byte[] bytes = stringBuffer.toString().getBytes("utf-8");
        long length = bytes.length;
        setContent(new ByteArrayInputStream(bytes));
        setContentLength(length);
        return bytes;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ InputStream getContent() {
        return super.getContent();
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ long getContentLength() {
        return super.getContentLength();
    }

    public OSSCredentialProvider getCredentialProvider() {
        return this.credentialProvider;
    }

    public URI getEndpoint() {
        return this.endpoint;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ Map getHeaders() {
        return super.getHeaders();
    }

    public String getIpWithHeader() {
        return this.ipWithHeader;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getObjectKey() {
        return this.objectKey;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public URI getService() {
        return this.service;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ String getStringBody() {
        return super.getStringBody();
    }

    public byte[] getUploadData() {
        return this.uploadData;
    }

    public String getUploadFilePath() {
        return this.uploadFilePath;
    }

    public Uri getUploadUri() {
        return this.uploadUri;
    }

    public boolean isAuthorizationRequired() {
        return this.isAuthorizationRequired;
    }

    public boolean isCheckCRC64() {
        return this.checkCRC64;
    }

    public boolean isCustomPathPrefixEnable() {
        return this.customPathPrefixEnable;
    }

    public boolean isHttpDnsEnable() {
        return this.httpDnsEnable;
    }

    public boolean isInCustomCnameExcludeList() {
        return this.isInCustomCnameExcludeList;
    }

    public boolean isPathStyleAccessEnable() {
        return this.pathStyleAccessEnable;
    }

    public void putBucketLifecycleRequestBodyMarshall(ArrayList<BucketLifecycleRule> arrayList) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<LifecycleConfiguration>");
        Iterator<BucketLifecycleRule> it = arrayList.iterator();
        while (it.hasNext()) {
            BucketLifecycleRule next = it.next();
            stringBuffer.append("<Rule>");
            if (next.getIdentifier() != null) {
                stringBuffer.append("<ID>" + next.getIdentifier() + "</ID>");
            }
            if (next.getPrefix() != null) {
                stringBuffer.append("<Prefix>" + next.getPrefix() + "</Prefix>");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<Status>");
            sb.append(next.getStatus() ? PeerConnectionFactory.TRIAL_ENABLED : "Disabled");
            sb.append("</Status>");
            stringBuffer.append(sb.toString());
            if (next.getDays() != null) {
                stringBuffer.append("<Days>" + next.getDays() + "</Days>");
            } else if (next.getExpireDate() != null) {
                stringBuffer.append("<Date>" + next.getExpireDate() + "</Date>");
            }
            if (next.getMultipartDays() != null) {
                stringBuffer.append("<AbortMultipartUpload><Days>" + next.getMultipartDays() + "</Days></AbortMultipartUpload>");
            } else if (next.getMultipartExpireDate() != null) {
                stringBuffer.append("<AbortMultipartUpload><Date>" + next.getMultipartDays() + "</Date></AbortMultipartUpload>");
            }
            if (next.getIADays() != null) {
                stringBuffer.append("<Transition><Days>" + next.getIADays() + "</Days><StorageClass>IA</StorageClass></Transition>");
            } else if (next.getIAExpireDate() != null) {
                stringBuffer.append("<Transition><Date>" + next.getIAExpireDate() + "</Date><StorageClass>IA</StorageClass></Transition>");
            } else if (next.getArchiveDays() != null) {
                stringBuffer.append("<Transition><Days>" + next.getArchiveDays() + "</Days><StorageClass>Archive</StorageClass></Transition>");
            } else if (next.getArchiveExpireDate() != null) {
                stringBuffer.append("<Transition><Date>" + next.getArchiveExpireDate() + "</Date><StorageClass>Archive</StorageClass></Transition>");
            }
            stringBuffer.append("</Rule>");
        }
        stringBuffer.append("</LifecycleConfiguration>");
        byte[] bytes = stringBuffer.toString().getBytes("utf-8");
        long length = bytes.length;
        setContent(new ByteArrayInputStream(bytes));
        setContentLength(length);
    }

    public void putBucketLoggingRequestBodyMarshall(String str, String str2) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<BucketLoggingStatus>");
        if (str != null) {
            stringBuffer.append("<LoggingEnabled><TargetBucket>" + str + "</TargetBucket>");
            if (str2 != null) {
                stringBuffer.append("<TargetPrefix>" + str2 + "</TargetPrefix>");
            }
            stringBuffer.append("</LoggingEnabled>");
        }
        stringBuffer.append("</BucketLoggingStatus>");
        byte[] bytes = stringBuffer.toString().getBytes("utf-8");
        long length = bytes.length;
        setContent(new ByteArrayInputStream(bytes));
        setContentLength(length);
    }

    public void putBucketRefererRequestBodyMarshall(ArrayList<String> arrayList, boolean z2) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<RefererConfiguration>");
        StringBuilder sb = new StringBuilder();
        sb.append("<AllowEmptyReferer>");
        sb.append(z2 ? a.f27523u : a.f27524v);
        sb.append("</AllowEmptyReferer>");
        stringBuffer.append(sb.toString());
        if (arrayList != null && arrayList.size() > 0) {
            stringBuffer.append("<RefererList>");
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                stringBuffer.append("<Referer>" + it.next() + "</Referer>");
            }
            stringBuffer.append("</RefererList>");
        }
        stringBuffer.append("</RefererConfiguration>");
        byte[] bytes = stringBuffer.toString().getBytes("utf-8");
        long length = bytes.length;
        setContent(new ByteArrayInputStream(bytes));
        setContentLength(length);
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public void setCheckCRC64(boolean z2) {
        this.checkCRC64 = z2;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setContent(InputStream inputStream) {
        super.setContent(inputStream);
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setContentLength(long j2) {
        super.setContentLength(j2);
    }

    public void setCredentialProvider(OSSCredentialProvider oSSCredentialProvider) {
        this.credentialProvider = oSSCredentialProvider;
    }

    public void setCustomPathPrefixEnable(boolean z2) {
        this.customPathPrefixEnable = z2;
    }

    public void setEndpoint(URI uri) {
        this.endpoint = uri;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setHeaders(Map map) {
        super.setHeaders(map);
    }

    public void setHttpDnsEnable(boolean z2) {
        this.httpDnsEnable = z2;
    }

    public void setIpWithHeader(String str) {
        this.ipWithHeader = str;
    }

    public void setIsAuthorizationRequired(boolean z2) {
        this.isAuthorizationRequired = z2;
    }

    public void setIsInCustomCnameExcludeList(boolean z2) {
        this.isInCustomCnameExcludeList = z2;
    }

    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod;
    }

    public void setObjectKey(String str) {
        this.objectKey = str;
    }

    public void setParameters(Map<String, String> map) {
        this.parameters = map;
    }

    public void setPathStyleAccessEnable(boolean z2) {
        this.pathStyleAccessEnable = z2;
    }

    public void setService(URI uri) {
        this.service = uri;
    }

    @Override // com.alibaba.sdk.android.oss.internal.HttpMessage
    public /* bridge */ /* synthetic */ void setStringBody(String str) {
        super.setStringBody(str);
    }

    public void setUploadData(byte[] bArr) {
        this.uploadData = bArr;
    }

    public void setUploadFilePath(String str) {
        this.uploadFilePath = str;
    }

    public void setUploadUri(Uri uri) {
        this.uploadUri = uri;
    }
}
