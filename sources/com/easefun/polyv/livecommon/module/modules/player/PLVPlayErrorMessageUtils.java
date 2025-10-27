package com.easefun.polyv.livecommon.module.modules.player;

import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.plv.foundationsdk.log.elog.logcode.play.PLVErrorCodePlayVideoInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlayErrorMessageUtils {
    private static List<Integer> ERROR_CODE_SUGGEST_EXIT = new ArrayList<Integer>() { // from class: com.easefun.polyv.livecommon.module.modules.player.PLVPlayErrorMessageUtils.1
        {
            add(Integer.valueOf(PLVErrorCodePlayVideoInfo.ErrorCode.PLAYBACK_INFO_DATA_ERROR));
            add(Integer.valueOf(PLVErrorCodePlayVideoInfo.ErrorCode.PLAYBACK_INFO_VID_ERROR));
            add(Integer.valueOf(PLVErrorCodePlayVideoInfo.ErrorCode.LIVE_INFO_DATA_ERROR));
            add(Integer.valueOf(PLVErrorCodePlayVideoInfo.ErrorCode.LIVE_INFO_CODE_ERROR));
            add(Integer.valueOf(PLVErrorCodePlayVideoInfo.ErrorCode.LIVE_RESTRICT_WATCH_ERROR));
        }
    };
    private static String TIPS_LIVE_LOAD_SLOW = "视频加载缓慢，请切换线路或退出重进";
    private static String TIPS_PLAYBACK_LOAD_SLOW = "视频加载缓慢，请刷新或退出重进";
    private static String TIPS_RESTRICT_WATCH = "存在观看限制，暂不支持进入%s";
    private static String TIPS_SUGGEST_EXIT = "视频加载失败，请退出重进%s";
    private static String TIPS_SUGGEST_REFRESH = "视频加载失败，请检查网络或退出重进%s";

    public static class PlayErrorContent {
        private String errorTips;
        private boolean isShowChangeLinesView;
        private boolean isShowRefreshView;

        public PlayErrorContent(String errorTips, boolean isShowChangeLinesView, boolean isShowRefreshView) {
            this.errorTips = errorTips;
            this.isShowChangeLinesView = isShowChangeLinesView;
            this.isShowRefreshView = isShowRefreshView;
        }
    }

    private static PlayErrorContent buildPlayErrorContent(PolyvPlayError playErrorReason, boolean isLive) {
        String str;
        boolean z2 = true;
        if (playErrorReason == null) {
            str = isLive ? TIPS_LIVE_LOAD_SLOW : TIPS_PLAYBACK_LOAD_SLOW;
        } else {
            int i2 = playErrorReason.errorCode;
            String str2 = "(错误码:" + i2 + ")";
            if (ERROR_CODE_SUGGEST_EXIT.contains(Integer.valueOf(i2))) {
                if (i2 == PLVErrorCodePlayVideoInfo.ErrorCode.LIVE_RESTRICT_WATCH_ERROR) {
                    str = String.format(TIPS_RESTRICT_WATCH, playErrorReason.errorDescribe + str2);
                } else {
                    str = String.format(TIPS_SUGGEST_EXIT, str2);
                }
                isLive = false;
                z2 = false;
            } else {
                str = String.format(TIPS_SUGGEST_REFRESH, str2);
                isLive = false;
            }
        }
        return new PlayErrorContent(str, isLive, z2);
    }

    public static void showOnLoadSlow(IPLVPlayErrorView playErrorView, boolean isLive) {
        showOnPlayError(playErrorView, null, isLive);
    }

    public static void showOnPlayError(IPLVPlayErrorView playErrorView, PolyvPlayError playErrorReason, boolean isLive) {
        PlayErrorContent playErrorContentBuildPlayErrorContent = buildPlayErrorContent(playErrorReason, isLive);
        playErrorView.setChangeLinesViewVisibility(playErrorContentBuildPlayErrorContent.isShowChangeLinesView ? 0 : 8);
        playErrorView.setRefreshViewVisibility(playErrorContentBuildPlayErrorContent.isShowRefreshView ? 0 : 8);
        playErrorView.setPlaceHolderText(playErrorContentBuildPlayErrorContent.errorTips);
        playErrorView.setViewVisibility(0);
    }
}
