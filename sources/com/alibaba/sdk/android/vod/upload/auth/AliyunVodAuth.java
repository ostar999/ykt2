package com.alibaba.sdk.android.vod.upload.auth;

import android.util.Log;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.aliyun.auth.common.AliyunVodUploadType;
import com.aliyun.auth.core.AliyunVodErrorCode;
import com.aliyun.auth.core.VodThreadService;
import com.aliyun.auth.model.CreateImageForm;
import com.aliyun.auth.model.CreateVideoForm;
import com.aliyun.auth.model.VodErrorResponse;
import com.aliyun.vod.jasonparse.JSONSupport;
import com.aliyun.vod.jasonparse.JSONSupportImpl;
import com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback;
import com.aliyun.vod.qupaiokhttp.HttpRequest;
import com.aliyun.vod.qupaiokhttp.StringHttpRequestCallback;
import com.arialyy.aria.core.inf.IOptionConstant;
import com.google.gson.JsonSyntaxException;
import okhttp3.Headers;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class AliyunVodAuth {
    private static final String TAG = "AliyunVodAuth";
    private String createImageUrl = null;
    private String createVideoUrl = null;
    private String domainRegion = null;
    private JSONSupport jsonSupportImpl = new JSONSupportImpl();
    private VodThreadService mHttpService = new VodThreadService(String.valueOf(System.currentTimeMillis()));
    private VodAuthCallBack vodAuthCallBack;

    public interface VodAuthCallBack {
        void onCreateUploadImaged(CreateImageForm createImageForm);

        void onCreateUploadVideoed(CreateVideoForm createVideoForm, String str);

        void onError(String str, String str2);

        void onSTSExpired(AliyunVodUploadType aliyunVodUploadType);
    }

    public AliyunVodAuth(VodAuthCallBack vodAuthCallBack) {
        this.vodAuthCallBack = vodAuthCallBack;
    }

    public void cancel() {
        this.vodAuthCallBack = null;
        String str = this.createImageUrl;
        if (str != null) {
            HttpRequest.cancel(str);
        }
        String str2 = this.createVideoUrl;
        if (str2 != null) {
            HttpRequest.cancel(str2);
        }
    }

    public void createUploadImage(final String str, final String str2, final String str3, final VodInfo vodInfo, final String str4, final String str5, final String str6) {
        this.mHttpService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.auth.AliyunVodAuth.1
            @Override // java.lang.Runnable
            public void run() {
                AliyunVodAuth aliyunVodAuth = AliyunVodAuth.this;
                aliyunVodAuth.createImageUrl = AliyunVodParam.generateOpenAPIURL(aliyunVodAuth.domainRegion, AliyunVodParam.generatePublicParamters(str, str3, str6), AliyunVodParam.generatePrivateParamtersToUploadImage(vodInfo, str4, str5), str2);
                HttpRequest.get(AliyunVodAuth.this.createImageUrl, new StringHttpRequestCallback() { // from class: com.alibaba.sdk.android.vod.upload.auth.AliyunVodAuth.1.1
                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onFailure(int i2, String str7) {
                        super.onFailure(i2, str7);
                        Log.d(AliyunVodAuth.TAG, "code" + i2 + "msg" + str7 + "time:" + System.currentTimeMillis());
                        if (i2 != 1003 || AliyunVodAuth.this.vodAuthCallBack == null) {
                            return;
                        }
                        AliyunVodAuth.this.vodAuthCallBack.onError(AliyunVodErrorCode.VODERRORCODE_HTTP_ABNORMAL, "http error response unknown.");
                    }

                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onResponse(Response response, String str7, Headers headers) {
                        super.onResponse(response, str7, headers);
                        Log.d(AliyunVodAuth.TAG, "httpResponse" + response + "\nmsg" + str7 + "\nheaders" + headers);
                        if (response == null || response.code() == 200) {
                            return;
                        }
                        try {
                            VodErrorResponse vodErrorResponse = (VodErrorResponse) AliyunVodAuth.this.jsonSupportImpl.readValue(str7, VodErrorResponse.class);
                            if (vodErrorResponse.getCode().equals(AliyunVodErrorCode.VODERRORCODE_INVALIDSECURITYTOKEN_EXPIRED)) {
                                if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                    AliyunVodAuth.this.vodAuthCallBack.onSTSExpired(AliyunVodUploadType.IMAGE);
                                }
                            } else if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                AliyunVodAuth.this.vodAuthCallBack.onError(vodErrorResponse.getCode(), vodErrorResponse.getMessage());
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }

                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onSuccess(Headers headers, String str7) {
                        super.onSuccess(headers, (Headers) str7);
                        Log.d(AliyunVodAuth.TAG, IOptionConstant.headers + headers + "\nmsg" + str7);
                        try {
                            CreateImageForm createImageForm = (CreateImageForm) AliyunVodAuth.this.jsonSupportImpl.readValue(str7, CreateImageForm.class);
                            if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                AliyunVodAuth.this.vodAuthCallBack.onCreateUploadImaged(createImageForm);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            if (!(e2 instanceof JsonSyntaxException) || AliyunVodAuth.this.vodAuthCallBack == null) {
                                return;
                            }
                            AliyunVodAuth.this.vodAuthCallBack.onError(AliyunVodErrorCode.VODERRORCODE_HTTP_ABNORMAL, "The network is abnormal, please check your network connection.");
                        }
                    }
                });
            }
        });
    }

    public void createUploadVideo(final String str, final String str2, final String str3, final VodInfo vodInfo, final boolean z2, final String str4, final String str5, final String str6, final String str7, final String str8) {
        this.mHttpService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.auth.AliyunVodAuth.2
            @Override // java.lang.Runnable
            public void run() {
                AliyunVodAuth aliyunVodAuth = AliyunVodAuth.this;
                aliyunVodAuth.createVideoUrl = AliyunVodParam.generateOpenAPIURL(aliyunVodAuth.domainRegion, AliyunVodParam.generatePublicParamters(str, str3, str8), AliyunVodParam.generatePrivateParamtersToUploadVideo(vodInfo, z2, str4, str5, str6, str7), str2);
                HttpRequest.get(AliyunVodAuth.this.createVideoUrl, new StringHttpRequestCallback() { // from class: com.alibaba.sdk.android.vod.upload.auth.AliyunVodAuth.2.1
                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onFailure(int i2, String str9) {
                        super.onFailure(i2, str9);
                        Log.d(AliyunVodAuth.TAG, "code" + i2 + "msg" + str9);
                        if (i2 == 1003) {
                            AliyunVodAuth.this.vodAuthCallBack.onError(AliyunVodErrorCode.VODERRORCODE_HTTP_ABNORMAL, "http error response unknown.");
                        }
                    }

                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onResponse(Response response, String str9, Headers headers) {
                        super.onResponse(response, str9, headers);
                        if (response == null || response.code() == 200) {
                            return;
                        }
                        Log.d(AliyunVodAuth.TAG, "onResponse --- createUploadVideo" + response + str9);
                        try {
                            VodErrorResponse vodErrorResponse = (VodErrorResponse) AliyunVodAuth.this.jsonSupportImpl.readValue(str9, VodErrorResponse.class);
                            if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                if (vodErrorResponse.getCode().equals(AliyunVodErrorCode.VODERRORCODE_INVALIDSECURITYTOKEN_EXPIRED)) {
                                    AliyunVodAuth.this.vodAuthCallBack.onSTSExpired(AliyunVodUploadType.VIDEO);
                                } else {
                                    AliyunVodAuth.this.vodAuthCallBack.onError(vodErrorResponse.getCode(), vodErrorResponse.getMessage());
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }

                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onSuccess(Headers headers, String str9) {
                        super.onSuccess(headers, (Headers) str9);
                        Log.d(AliyunVodAuth.TAG, "onSuccess --- createUploadVideo");
                        try {
                            CreateVideoForm createVideoForm = (CreateVideoForm) AliyunVodAuth.this.jsonSupportImpl.readValue(str9, CreateVideoForm.class);
                            Log.d(AliyunVodAuth.TAG, "onSuccess --- createUploadVideogetUploadAuth:" + createVideoForm.getUploadAuth() + "getUploadAddress" + createVideoForm.getUploadAddress() + "\nrequestID:" + createVideoForm.getRequestId());
                            if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                AliyunVodAuth.this.vodAuthCallBack.onCreateUploadVideoed(createVideoForm, vodInfo.getCoverUrl());
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            if (!(e2 instanceof JsonSyntaxException) || AliyunVodAuth.this.vodAuthCallBack == null) {
                                return;
                            }
                            AliyunVodAuth.this.vodAuthCallBack.onError(AliyunVodErrorCode.VODERRORCODE_HTTP_ABNORMAL, "The network is abnormal. Please check your network connection. Your network may need to log in.");
                        }
                    }
                });
            }
        });
    }

    public void refreshUploadVideo(final String str, final String str2, final String str3, final String str4, final String str5, final String str6) {
        this.mHttpService.execute(new Runnable() { // from class: com.alibaba.sdk.android.vod.upload.auth.AliyunVodAuth.3
            @Override // java.lang.Runnable
            public void run() {
                AliyunVodAuth aliyunVodAuth = AliyunVodAuth.this;
                aliyunVodAuth.createVideoUrl = AliyunVodParam.generateOpenAPIURL(aliyunVodAuth.domainRegion, AliyunVodParam.generatePublicParamters(str, str3, str6), AliyunVodParam.generatePrivateParamtersToReUploadVideo(str4), str2);
                HttpRequest.get(AliyunVodAuth.this.createVideoUrl, new BaseHttpRequestCallback() { // from class: com.alibaba.sdk.android.vod.upload.auth.AliyunVodAuth.3.1
                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onFailure(int i2, String str7) {
                        super.onFailure(i2, str7);
                        Log.d(AliyunVodAuth.TAG, "code" + i2 + "msg" + str7);
                    }

                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onResponse(Response response, String str7, Headers headers) {
                        String code;
                        String message;
                        super.onResponse(response, str7, headers);
                        if (response == null || response.code() == 200) {
                            return;
                        }
                        try {
                            if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                VodErrorResponse vodErrorResponse = (VodErrorResponse) AliyunVodAuth.this.jsonSupportImpl.readValue(str7, VodErrorResponse.class);
                                if (vodErrorResponse != null) {
                                    code = vodErrorResponse.getCode();
                                    message = vodErrorResponse.getMessage();
                                } else {
                                    code = "UNKNOWN";
                                    message = "UNKNOWN";
                                }
                                if (AliyunVodErrorCode.VODERRORCODE_INVALIDSECURITYTOKEN_EXPIRED.equals(code)) {
                                    AliyunVodAuth.this.vodAuthCallBack.onSTSExpired(AliyunVodUploadType.VIDEO);
                                } else {
                                    AliyunVodAuth.this.vodAuthCallBack.onError(code, message);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }

                    @Override // com.aliyun.vod.qupaiokhttp.BaseHttpRequestCallback
                    public void onSuccess(Headers headers, Object obj) {
                        super.onSuccess(headers, obj);
                        try {
                            if (AliyunVodAuth.this.vodAuthCallBack != null) {
                                CreateVideoForm createVideoForm = (CreateVideoForm) AliyunVodAuth.this.jsonSupportImpl.readValue((String) obj, CreateVideoForm.class);
                                createVideoForm.setVideoId(str4);
                                AliyunVodAuth.this.vodAuthCallBack.onCreateUploadVideoed(createVideoForm, str5);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            if (!(e2 instanceof JsonSyntaxException) || AliyunVodAuth.this.vodAuthCallBack == null) {
                                return;
                            }
                            AliyunVodAuth.this.vodAuthCallBack.onError(AliyunVodErrorCode.VODERRORCODE_HTTP_ABNORMAL, "The network is abnormal. Please check your network connection. Your network may need to log in.");
                        }
                    }
                });
            }
        });
    }

    public void setDomainRegion(String str) {
        this.domainRegion = str;
    }
}
