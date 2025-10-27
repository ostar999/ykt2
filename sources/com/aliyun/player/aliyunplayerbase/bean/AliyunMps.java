package com.aliyun.player.aliyunplayerbase.bean;

import com.aliyun.auth.core.AliyunVodKey;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class AliyunMps {
    private int code;
    private MpsBean data;
    private String message;

    public class MpsBean {

        @SerializedName("AkInfo")
        private AkInfoBean akInfo;
        private String authInfo;

        @SerializedName("HlsUriToken")
        private String hlsUriToken;

        @SerializedName("MediaId")
        private String mediaId;

        @SerializedName("RegionId")
        private String regionId;

        public class AkInfoBean {

            @SerializedName(AliyunVodKey.KEY_VOD_COMMON_ACCESSKEYID)
            private String accessKeyId;

            @SerializedName("AccessKeySecret")
            private String accessKeySecret;

            @SerializedName("Expiration")
            private String expiration;

            @SerializedName(AliyunVodKey.KEY_VOD_COMMON_SECURITY_TOKEN)
            private String securityToken;

            public AkInfoBean() {
            }

            public String getAccessKeyId() {
                return this.accessKeyId;
            }

            public String getAccessKeySecret() {
                return this.accessKeySecret;
            }

            public String getExpiration() {
                return this.expiration;
            }

            public String getSecurityToken() {
                return this.securityToken;
            }

            public void setAccessKeyId(String str) {
                this.accessKeyId = str;
            }

            public void setAccessKeySecret(String str) {
                this.accessKeySecret = str;
            }

            public void setExpiration(String str) {
                this.expiration = str;
            }

            public void setSecurityToken(String str) {
                this.securityToken = str;
            }
        }

        public MpsBean() {
        }

        public AkInfoBean getAkInfo() {
            return this.akInfo;
        }

        public String getAuthInfo() {
            return this.authInfo;
        }

        public String getHlsUriToken() {
            return this.hlsUriToken;
        }

        public String getMediaId() {
            return this.mediaId;
        }

        public String getRegionId() {
            return this.regionId;
        }

        public void setAkInfo(AkInfoBean akInfoBean) {
            this.akInfo = akInfoBean;
        }

        public void setAuthInfo(String str) {
            this.authInfo = str;
        }

        public void setHlsUriToken(String str) {
            this.hlsUriToken = str;
        }

        public void setMediaId(String str) {
            this.mediaId = str;
        }

        public void setRegionId(String str) {
            this.regionId = str;
        }
    }

    public int getCode() {
        return this.code;
    }

    public MpsBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(MpsBean mpsBean) {
        this.data = mpsBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
