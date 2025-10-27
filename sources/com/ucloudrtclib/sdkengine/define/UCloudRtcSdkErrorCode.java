package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkErrorCode {
    NET_ERR_CODE_OK(5015),
    NET_ERR_GETSIGNAL_ADDR_FAIL(5017),
    NET_ERR_SAME_CMD(5036),
    NET_ERR_NOT_IN_ROOM(5037),
    NET_ERR_ROOM_JOINED(5004),
    NET_ERR_NETWORK_ERR(5005),
    NET_ERR_ROOM_RECONNECTING(5006),
    NET_ERR_PUB_TOO_MORE(5007),
    NET_ERR_STREAM_PUBED(5008),
    NET_ERR_STREAM_NOT_PUB(5009),
    NET_ERR_STREAM_SUBED(5010),
    NET_ERR_STREAM_NO_SUB(5011),
    NET_ERR_SUB_NO_USER(5012),
    NET_ERR_SUB_NO_MEDIA(5013),
    NET_ERR_USER_LEAVING(5014),
    NET_ERR_SERVER_CON_FAIL(5000),
    NET_ERR_INVAILD_ST(5016),
    NET_ERR_SERVER_DIS(5001),
    NET_ERR_NO_HAS_TRACK(5018),
    NET_ERR_MSG_TIMEOUT(5019),
    NET_ERR_AUTO_PUB(5020),
    NET_ERR_AUTO_SUB(5021),
    NET_ERR_NOT_INIT(5022),
    NET_ERR_NOT_PUB_TRACK(5023),
    NET_ERR_INVAILED_PARGRAM(5024),
    NET_ERR_INVAILED_WND_HANDLE(5025),
    NET_ERR_INVAILED_MEDIA_TYPE(5026),
    NET_ERR_SUB_ONEMORE(5027),
    NET_ERR_NO_PUB_ROLE(5028),
    NET_ERR_NO_SUB_ROLE(5029),
    NET_ERR_CAM_NOT_ENABLE(5030),
    NET_ERR_SCREEN_NOT_ENABLE(5031),
    NET_ERR_AUDIO_MODE(5032),
    NET_ERR_SECKEY_NULL(5033),
    NET_ERR_GEN_TOKEN_FAIL(5034),
    NET_ERR_SDP_SWAP_FAIL(5035),
    ERR_NOT_SUPPORT_OP(5036),
    ERR_NO_PERMISSIONS(5037);

    private int errorCode;

    UCloudRtcSdkErrorCode(int i2) {
        this.errorCode = i2;
    }

    public static UCloudRtcSdkErrorCode matchValue(int i2) {
        for (UCloudRtcSdkErrorCode uCloudRtcSdkErrorCode : values()) {
            if (uCloudRtcSdkErrorCode.getErrorCode() == i2) {
                return uCloudRtcSdkErrorCode;
            }
        }
        return NET_ERR_CODE_OK;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
