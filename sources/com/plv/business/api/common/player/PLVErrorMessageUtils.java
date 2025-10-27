package com.plv.business.api.common.player;

/* loaded from: classes4.dex */
public class PLVErrorMessageUtils {
    private static final String BITRATE_ERROR_TIPS = "清晰度不正确，请设置正确的清晰度进行播放";
    private static final String CAN_NOT_CHANGE_BITRATE_TIPS = "未开始播放视频不能切换清晰度，请先播放视频";
    private static final String CAN_NOT_CHANGE_HLS_SPEED_TIPS = "未开始播放视频不能切换播放速度，请先播放视频";
    private static final String CHANGE_BITRATE_NOT_EXIST_TIPS = "视频没有这个清晰度，请切换其它清晰度";
    private static final String CHANGE_EQUAL_BITRATE_TIPS = "切换清晰度相同，请选择其它清晰度";
    private static final String CHANGE_EQUAL_HLS_SPEED_TIPS = "切换播放速度相同，请选择其它播放速度";
    private static final String EXCEPTION_COMPLETION_TIPS = "视频异常结束，请重新播放或者向管理员反馈";
    private static final String HLS_SPEED_TYPE_NULL_TIPS = "播放速度不正确，请设置正确的播放速度进行播放";
    private static final String LOCAL_VIDEO_ERROR_TIPS = "本地视频文件损坏，请重新下载";
    private static final String NETWORK_DENIED_TIPS = "网络异常，重试中";
    private static final String NOT_LOCAL_VIDEO_TIPS = "找不到缓存的视频文件，请连网后重新下载";
    private static final String NOT_PERMISSION_TIPS = "非法播放，请向管理员反馈";
    private static final String OUT_FLOW_TIPS = "流量超标，请向管理员反馈";
    private static final String QUESTION_ERROR_TIPS = "视频问答数据加载失败，请重新播放或者切换网络重新播放";
    private static final String START_ERROR_TIPS = "播放异常，请重新播放";
    private static final String TIMEOUT_FLOW_TIPS = "账号过期，请向管理员反馈";
    private static final String TOKEN_NULL_TIPS = "播放授权获取失败，请重新播放或者切换网络重新播放或者向管理员反馈";
    private static final String USER_TOKEN_ERROR_TIPS = "请先设置播放凭证，再进行播放";
    private static final String VIDEO_CANNOT_PLAY_TIPS = "当前视频无法播放，请向管理员反馈";
    private static final String VIDEO_ID_NOT_CORRECT_TIPS = "视频id不正确，请设置正确的视频id进行播放";
    private static final String VIDEO_NULL_TIPS = "视频信息加载失败，请尝试切换网络重新播放";
    private static final String VIDEO_STATUS_ERROR_TIPS = "视频状态异常，无法播放，请向管理员反馈";
    private static final String WRITE_EXTERNAL_STORAGE_DENIED_TIPS = "检测到拒绝读取存储设备，请先为应用程序分配权限，再重新播放";

    public static String getPlayErrorMessage(int i2) {
        if (i2 == 30001) {
            return HLS_SPEED_TYPE_NULL_TIPS;
        }
        if (i2 == 30002) {
            return NOT_LOCAL_VIDEO_TIPS;
        }
        if (i2 == 30012) {
            return QUESTION_ERROR_TIPS;
        }
        if (i2 == 30014) {
            return CHANGE_BITRATE_NOT_EXIST_TIPS;
        }
        if (i2 == 30015) {
            return TOKEN_NULL_TIPS;
        }
        if (i2 == 30017) {
            return EXCEPTION_COMPLETION_TIPS;
        }
        if (i2 == 30018) {
            return WRITE_EXTERNAL_STORAGE_DENIED_TIPS;
        }
        switch (i2) {
            case 20003:
                return NETWORK_DENIED_TIPS;
            case 20004:
                return OUT_FLOW_TIPS;
            case 20005:
                return TIMEOUT_FLOW_TIPS;
            default:
                switch (i2) {
                    case 20007:
                        return LOCAL_VIDEO_ERROR_TIPS;
                    case 20008:
                        return START_ERROR_TIPS;
                    case 20009:
                        return NOT_PERMISSION_TIPS;
                    case 20010:
                        return USER_TOKEN_ERROR_TIPS;
                    case 20011:
                        return VIDEO_STATUS_ERROR_TIPS;
                    default:
                        switch (i2) {
                            case 20014:
                                return VIDEO_ID_NOT_CORRECT_TIPS;
                            case 20015:
                                return BITRATE_ERROR_TIPS;
                            case 20016:
                                return VIDEO_NULL_TIPS;
                            default:
                                switch (i2) {
                                    case 30007:
                                        return CHANGE_EQUAL_BITRATE_TIPS;
                                    case 30008:
                                        return CHANGE_EQUAL_HLS_SPEED_TIPS;
                                    case 30009:
                                        return CAN_NOT_CHANGE_BITRATE_TIPS;
                                    case 30010:
                                        return CAN_NOT_CHANGE_HLS_SPEED_TIPS;
                                    default:
                                        return VIDEO_CANNOT_PLAY_TIPS;
                                }
                        }
                }
        }
    }
}
