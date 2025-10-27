package com.alibaba.sdk.android.oss.common.auth;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.aliyun.auth.core.AliyunVodKey;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OSSAuthCredentialsProvider extends OSSFederationCredentialProvider {
    private String mAuthServerUrl;
    private AuthDecoder mDecoder;

    public interface AuthDecoder {
        String decode(String str);
    }

    public OSSAuthCredentialsProvider(String str) {
        this.mAuthServerUrl = str;
    }

    @Override // com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider, com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
    public OSSFederationToken getFederationToken() throws Throwable {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.mAuthServerUrl).openConnection();
            httpURLConnection.setConnectTimeout(10000);
            String streamAsString = IOUtils.readStreamAsString(httpURLConnection.getInputStream(), "utf-8");
            AuthDecoder authDecoder = this.mDecoder;
            if (authDecoder != null) {
                streamAsString = authDecoder.decode(streamAsString);
            }
            JSONObject jSONObject = new JSONObject(streamAsString);
            if (jSONObject.getInt("StatusCode") == 200) {
                return new OSSFederationToken(jSONObject.getString(AliyunVodKey.KEY_VOD_COMMON_ACCESSKEYID), jSONObject.getString("AccessKeySecret"), jSONObject.getString(AliyunVodKey.KEY_VOD_COMMON_SECURITY_TOKEN), jSONObject.getString("Expiration"));
            }
            throw new ClientException("ErrorCode: " + jSONObject.getString("ErrorCode") + "| ErrorMessage: " + jSONObject.getString("ErrorMessage"));
        } catch (Exception e2) {
            throw new ClientException(e2);
        }
    }

    public void setAuthServerUrl(String str) {
        this.mAuthServerUrl = str;
    }

    public void setDecoder(AuthDecoder authDecoder) {
        this.mDecoder = authDecoder;
    }
}
