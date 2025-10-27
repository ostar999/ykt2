package com.aliyun.player.aliyunplayerbase.net;

import com.aliyun.player.aliyunplayerbase.bean.AliyunMps;
import com.aliyun.player.aliyunplayerbase.bean.AliyunPlayAuth;
import com.aliyun.player.aliyunplayerbase.bean.AliyunSts;
import com.aliyun.player.aliyunplayerbase.bean.AliyunVideoList;
import com.aliyun.svideo.common.okhttp.AlivcOkHttpClient;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.Request;

/* loaded from: classes2.dex */
public class GetAuthInformation {

    public interface OnGetMpsInfoListener {
        void onGetMpsError(String str);

        void onGetMpsSuccess(AliyunMps.MpsBean mpsBean);
    }

    public interface OnGetPlayAuthInfoListener {
        void onGetPlayAuthError(String str);

        void onGetPlayAuthSuccess(AliyunPlayAuth.PlayAuthBean playAuthBean);
    }

    public interface OnGetStsInfoListener {
        void onGetStsError(String str);

        void onGetStsSuccess(AliyunSts.StsBean stsBean);
    }

    public interface OnGetUrlInfoListener {
        void onGetUrlError(String str);

        void onGetUrlSuccess(AliyunVideoList.VideoList videoList);
    }

    public void getVideoPlayAuthInfo(final OnGetPlayAuthInfoListener onGetPlayAuthInfoListener) {
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_AUTH, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.7
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetPlayAuthInfoListener onGetPlayAuthInfoListener2 = onGetPlayAuthInfoListener;
                if (onGetPlayAuthInfoListener2 != null) {
                    onGetPlayAuthInfoListener2.onGetPlayAuthError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str) {
                AliyunPlayAuth aliyunPlayAuth = (AliyunPlayAuth) new Gson().fromJson(str, AliyunPlayAuth.class);
                if (aliyunPlayAuth == null || aliyunPlayAuth.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunPlayAuth.PlayAuthBean data = aliyunPlayAuth.getData();
                OnGetPlayAuthInfoListener onGetPlayAuthInfoListener2 = onGetPlayAuthInfoListener;
                if (onGetPlayAuthInfoListener2 != null) {
                    onGetPlayAuthInfoListener2.onGetPlayAuthSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayAuthInfoWithVideoId(String str, final OnGetPlayAuthInfoListener onGetPlayAuthInfoListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("videoId", str);
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_AUTH, map, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.8
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetPlayAuthInfoListener onGetPlayAuthInfoListener2 = onGetPlayAuthInfoListener;
                if (onGetPlayAuthInfoListener2 != null) {
                    onGetPlayAuthInfoListener2.onGetPlayAuthError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str2) {
                AliyunPlayAuth aliyunPlayAuth = (AliyunPlayAuth) new Gson().fromJson(str2, AliyunPlayAuth.class);
                if (aliyunPlayAuth == null || aliyunPlayAuth.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunPlayAuth.PlayAuthBean data = aliyunPlayAuth.getData();
                OnGetPlayAuthInfoListener onGetPlayAuthInfoListener2 = onGetPlayAuthInfoListener;
                if (onGetPlayAuthInfoListener2 != null) {
                    onGetPlayAuthInfoListener2.onGetPlayAuthSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayLiveStsInfo(final OnGetStsInfoListener onGetStsInfoListener) {
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_LIVE_PLAY_STS, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.1
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetStsInfoListener onGetStsInfoListener2 = onGetStsInfoListener;
                if (onGetStsInfoListener2 != null) {
                    onGetStsInfoListener2.onGetStsError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str) {
                AliyunSts aliyunSts = (AliyunSts) new Gson().fromJson(str, AliyunSts.class);
                if (aliyunSts == null || aliyunSts.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunSts.StsBean data = aliyunSts.getData();
                OnGetStsInfoListener onGetStsInfoListener2 = onGetStsInfoListener;
                if (onGetStsInfoListener2 != null) {
                    onGetStsInfoListener2.onGetStsSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayMpsInfo(final OnGetMpsInfoListener onGetMpsInfoListener) {
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_MPS, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.5
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetMpsInfoListener onGetMpsInfoListener2 = onGetMpsInfoListener;
                if (onGetMpsInfoListener2 != null) {
                    onGetMpsInfoListener2.onGetMpsError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str) {
                AliyunMps aliyunMps = (AliyunMps) new Gson().fromJson(str, AliyunMps.class);
                if (aliyunMps == null || aliyunMps.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunMps.MpsBean data = aliyunMps.getData();
                OnGetMpsInfoListener onGetMpsInfoListener2 = onGetMpsInfoListener;
                if (onGetMpsInfoListener2 != null) {
                    onGetMpsInfoListener2.onGetMpsSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayMpsInfoWithVideoId(String str, final OnGetMpsInfoListener onGetMpsInfoListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("videoId", str);
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_MPS, map, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.6
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetMpsInfoListener onGetMpsInfoListener2 = onGetMpsInfoListener;
                if (onGetMpsInfoListener2 != null) {
                    onGetMpsInfoListener2.onGetMpsError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str2) {
                AliyunMps aliyunMps = (AliyunMps) new Gson().fromJson(str2, AliyunMps.class);
                if (aliyunMps == null || aliyunMps.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunMps.MpsBean data = aliyunMps.getData();
                OnGetMpsInfoListener onGetMpsInfoListener2 = onGetMpsInfoListener;
                if (onGetMpsInfoListener2 != null) {
                    onGetMpsInfoListener2.onGetMpsSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayStsInfo(final OnGetStsInfoListener onGetStsInfoListener) {
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_STS, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.2
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetStsInfoListener onGetStsInfoListener2 = onGetStsInfoListener;
                if (onGetStsInfoListener2 != null) {
                    onGetStsInfoListener2.onGetStsError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str) {
                AliyunSts aliyunSts = (AliyunSts) new Gson().fromJson(str, AliyunSts.class);
                if (aliyunSts == null || aliyunSts.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunSts.StsBean data = aliyunSts.getData();
                OnGetStsInfoListener onGetStsInfoListener2 = onGetStsInfoListener;
                if (onGetStsInfoListener2 != null) {
                    onGetStsInfoListener2.onGetStsSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayStsInfoWithVideoId(String str, final OnGetStsInfoListener onGetStsInfoListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("videoId", str);
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_STS, map, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.3
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetStsInfoListener onGetStsInfoListener2 = onGetStsInfoListener;
                if (onGetStsInfoListener2 != null) {
                    onGetStsInfoListener2.onGetStsError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str2) {
                AliyunSts aliyunSts = (AliyunSts) new Gson().fromJson(str2, AliyunSts.class);
                if (aliyunSts == null || aliyunSts.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunSts.StsBean data = aliyunSts.getData();
                OnGetStsInfoListener onGetStsInfoListener2 = onGetStsInfoListener;
                if (onGetStsInfoListener2 != null) {
                    onGetStsInfoListener2.onGetStsSuccess(data);
                }
            }
        });
    }

    public void getVideoPlayUrlInfo(final OnGetUrlInfoListener onGetUrlInfoListener) {
        AlivcOkHttpClient.getInstance().get(ServiceCommon.GET_VIDEO_PLAY_INFO, new AlivcOkHttpClient.HttpCallBack() { // from class: com.aliyun.player.aliyunplayerbase.net.GetAuthInformation.4
            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onError(Request request, IOException iOException) {
                OnGetUrlInfoListener onGetUrlInfoListener2 = onGetUrlInfoListener;
                if (onGetUrlInfoListener2 != null) {
                    onGetUrlInfoListener2.onGetUrlError(iOException.getMessage());
                }
            }

            @Override // com.aliyun.svideo.common.okhttp.AlivcOkHttpClient.HttpCallBack
            public void onSuccess(Request request, String str) {
                AliyunVideoList aliyunVideoList = (AliyunVideoList) new Gson().fromJson(str, AliyunVideoList.class);
                if (aliyunVideoList == null || aliyunVideoList.getCode() != ServiceCommon.RESPONSE_SUCCESS) {
                    return;
                }
                AliyunVideoList.VideoList data = aliyunVideoList.getData();
                OnGetUrlInfoListener onGetUrlInfoListener2 = onGetUrlInfoListener;
                if (onGetUrlInfoListener2 != null) {
                    onGetUrlInfoListener2.onGetUrlSuccess(data);
                }
            }
        });
    }
}
