package com.plv.livescenes.upload.oss;

import android.content.Context;
import androidx.annotation.WorkerThread;
import cn.hutool.core.text.CharPool;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import java.io.IOException;

/* loaded from: classes5.dex */
public class PLVOSSClientCreator {

    public interface OnTokenNeedRefresh {
        STSToken refreshToken() throws IOException;
    }

    public static class STSToken {
        private String expirationInGMTFormat;
        private String securityToken;
        private String tempAK;
        private String tempSK;

        public STSToken(String str, String str2, String str3, String str4) {
            this.tempAK = str;
            this.tempSK = str2;
            this.securityToken = str3;
            this.expirationInGMTFormat = str4;
        }

        public String toString() {
            return "STSToken{tempAK='" + this.tempAK + CharPool.SINGLE_QUOTE + ", tempSK='" + this.tempSK + CharPool.SINGLE_QUOTE + ", securityToken='" + this.securityToken + CharPool.SINGLE_QUOTE + ", expirationInGMTFormat='" + this.expirationInGMTFormat + CharPool.SINGLE_QUOTE + '}';
        }
    }

    @WorkerThread
    public static OSSClient createOSSClient(Context context, String str, final OnTokenNeedRefresh onTokenNeedRefresh) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(15000);
        clientConfiguration.setSocketTimeout(15000);
        clientConfiguration.setMaxConcurrentRequest(5);
        clientConfiguration.setMaxErrorRetry(2);
        return new OSSClient(context, str, new OSSFederationCredentialProvider() { // from class: com.plv.livescenes.upload.oss.PLVOSSClientCreator.1
            @Override // com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider, com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
            public OSSFederationToken getFederationToken() throws ClientException {
                STSToken sTSToken;
                try {
                    sTSToken = onTokenNeedRefresh.refreshToken();
                } catch (IOException e2) {
                    e2.printStackTrace();
                    sTSToken = new STSToken("", "", "", "");
                }
                return new OSSFederationToken(sTSToken.tempAK, sTSToken.tempSK, sTSToken.securityToken, sTSToken.expirationInGMTFormat);
            }
        }, clientConfiguration);
    }
}
