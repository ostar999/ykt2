package com.plv.livescenes.feature.login;

import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.feature.login.PolyvLiveLoginResult;
import com.easefun.polyv.livescenes.feature.login.PolyvPlaybackLoginResult;
import com.easefun.polyv.livescenes.feature.login.model.PLVSLoginVO;
import com.easefun.polyv.livescenes.log.PLVELogRequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.business.model.video.PLVPlayBackVO;
import com.plv.business.service.PLVLoginManager;
import com.plv.foundationsdk.config.PLVVideoViewConstant;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.model.domain.PLVChatDomain;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVEncryptDataVO;
import com.plv.livescenes.access.PLVChannelFeature;
import com.plv.livescenes.access.PLVChannelFeatureManager;
import com.plv.livescenes.access.PLVUserAbilityManager;
import com.plv.livescenes.access.PLVUserRole;
import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.config.PLVLiveStatusType;
import com.plv.livescenes.feature.login.IPLVSceneLoginManager;
import com.plv.livescenes.feature.login.model.PLVHCTeacherLoginVO;
import com.plv.livescenes.feature.login.model.PLVLoginVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentVerifyRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentVerifyResultVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginResultVO;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.livescenes.model.PLVLiveStatusVO2;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class PLVSceneLoginManager implements IPLVSceneLoginManager {
    private static final String TAG = "PLVSceneLoginManager";
    private Disposable getPushStreamDisposable;
    private Disposable loginDisposable;
    private Disposable loginHiClassDisposable;
    private Disposable loginLiveDisposable;
    private Disposable loginPlaybackDisposable;

    /* renamed from: com.plv.livescenes.feature.login.PLVSceneLoginManager$24, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass24 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$livescenes$feature$login$PLVSceneLoginManager$PLVHCStudentLoginVerifyType;

        static {
            int[] iArr = new int[PLVHCStudentLoginVerifyType.values().length];
            $SwitchMap$com$plv$livescenes$feature$login$PLVSceneLoginManager$PLVHCStudentLoginVerifyType = iArr;
            try {
                iArr[PLVHCStudentLoginVerifyType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$livescenes$feature$login$PLVSceneLoginManager$PLVHCStudentLoginVerifyType[PLVHCStudentLoginVerifyType.CODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$plv$livescenes$feature$login$PLVSceneLoginManager$PLVHCStudentLoginVerifyType[PLVHCStudentLoginVerifyType.WHITE_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface IPLVSOnGetPushStreamUrlListener {
        void onGet(String str);
    }

    public enum PLVHCStudentLoginVerifyType {
        NONE,
        CODE,
        WHITE_LIST
    }

    public static class PLVResponseCallbackAdapter<Bean> extends PLVrResponseCallback<Bean> {
        private ObservableEmitter<Bean> emitter;

        public PLVResponseCallbackAdapter(ObservableEmitter<Bean> observableEmitter) {
            this.emitter = observableEmitter;
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onError(Throwable th) {
            super.onError(th);
            if (this.emitter.isDisposed()) {
                return;
            }
            this.emitter.onError(th);
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onFailure(PLVResponseBean<Bean> pLVResponseBean) {
            super.onFailure(pLVResponseBean);
            if (this.emitter.isDisposed()) {
                return;
            }
            this.emitter.onError(new Throwable(pLVResponseBean.toString()));
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onFinish() {
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onSuccess(Bean bean) {
            this.emitter.onNext(bean);
        }
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPushStream(final PLVLoginVO pLVLoginVO, final IPLVSOnGetPushStreamUrlListener iPLVSOnGetPushStreamUrlListener) {
        boolean zEquals = pLVLoginVO.getIsNgbEnabled().equals("Y");
        dispose(this.getPushStreamDisposable);
        if (zEquals) {
            this.getPushStreamDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getNGBPushApi().getNGBPushStreamUrl(pLVLoginVO.getNgbUrl() + pLVLoginVO.getStream(), "https://sdkoptedge.chinanetcenter.com/"), new PLVrResponseCallback<ResponseBody>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.22
                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onError(Throwable th) {
                    super.onError(th);
                    PLVSceneLoginManager.this.getPushStreamDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getNGBPushApi().getNGBPushStreamUrl(pLVLoginVO.getBakUrl() + pLVLoginVO.getStream(), "https://sdkoptedge.chinanetcenter.com/"), new PLVrResponseCallback<ResponseBody>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.22.1
                        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                        public void onError(Throwable th2) {
                            super.onError(th2);
                            iPLVSOnGetPushStreamUrlListener.onGet(pLVLoginVO.getUrl() + pLVLoginVO.getStream());
                        }

                        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                        public void onFailure(PLVResponseBean<ResponseBody> pLVResponseBean) {
                            super.onFailure(pLVResponseBean);
                        }

                        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                        public void onFinish() {
                        }

                        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                        public void onSuccess(ResponseBody responseBody) {
                            String strString;
                            try {
                                strString = responseBody.string();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                strString = "";
                            }
                            PLVCommonLog.d(PLVSceneLoginManager.TAG, "NBG推流地址2=" + strString);
                            iPLVSOnGetPushStreamUrlListener.onGet(strString);
                        }
                    });
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onFinish() {
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onSuccess(ResponseBody responseBody) {
                    String strString;
                    try {
                        strString = responseBody.string();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        strString = "";
                    }
                    PLVCommonLog.d(PLVSceneLoginManager.TAG, "NBG推流地址1=" + strString);
                    iPLVSOnGetPushStreamUrlListener.onGet(strString);
                }
            });
            return;
        }
        if (!pLVLoginVO.getIsUrlProtected().equals("Y")) {
            iPLVSOnGetPushStreamUrlListener.onGet(pLVLoginVO.getUrl() + pLVLoginVO.getStream());
            return;
        }
        iPLVSOnGetPushStreamUrlListener.onGet(pLVLoginVO.getBakUrl() + pLVLoginVO.getStream() + pLVLoginVO.getSuffix());
    }

    private Observable<PLVLiveClassDetailVO> requestLiveDetail(final String str, final String str2, final String str3) {
        return Observable.create(new ObservableOnSubscribe<PLVLiveClassDetailVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.6
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<PLVLiveClassDetailVO> observableEmitter) throws Exception {
                PLVResponseExcutor.excuteUndefinData(PLVChatApiRequestHelper.getInstance().requestLiveClassDetailApi(str, str2, str3), new PLVResponseCallbackAdapter<PLVLiveClassDetailVO>(observableEmitter) { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.6.1
                    @Override // com.plv.livescenes.feature.login.PLVSceneLoginManager.PLVResponseCallbackAdapter, com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(PLVLiveClassDetailVO pLVLiveClassDetailVO) {
                        if (pLVLiveClassDetailVO.getCode() == 200) {
                            observableEmitter.onNext(pLVLiveClassDetailVO);
                        } else {
                            if (observableEmitter.isDisposed()) {
                                return;
                            }
                            observableEmitter.onError(new Throwable(pLVLiveClassDetailVO.toString()));
                        }
                    }
                });
            }
        });
    }

    private Observable<PLVLiveStatusVO2> requestLiveStatus(final String str, final String str2, final String str3) {
        return Observable.create(new ObservableOnSubscribe<PLVLiveStatusVO2>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.5
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<PLVLiveStatusVO2> observableEmitter) throws Exception {
                long jCurrentTimeMillis = System.currentTimeMillis();
                ArrayMap arrayMap = new ArrayMap();
                arrayMap.put("appId", str2);
                arrayMap.put("timestamp", jCurrentTimeMillis + "");
                arrayMap.put("channelId", str);
                String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, arrayMap);
                PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().getLiveStatusJson3(str, jCurrentTimeMillis + "", str2, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVLiveStatusVO2>(String.class) { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.5.1
                    @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
                    public Pair<Object, Boolean> accept(PLVLiveStatusVO2 pLVLiveStatusVO2) {
                        return new Pair<>(pLVLiveStatusVO2.getDataObj(), Boolean.valueOf(pLVLiveStatusVO2.isEncryption()));
                    }
                }), new PLVResponseCallbackAdapter(observableEmitter));
            }
        });
    }

    private Observable<PLVPlayBackVO> requestPlayBackStatusByChannelId(String str, String str2, String str3) {
        return requestLiveDetail(str, str2, str3).map(new Function<PLVLiveClassDetailVO, PLVPlayBackVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.12
            @Override // io.reactivex.functions.Function
            public PLVPlayBackVO apply(PLVLiveClassDetailVO pLVLiveClassDetailVO) throws Exception {
                PLVPlayBackVO pLVPlayBackVO = new PLVPlayBackVO();
                if (pLVLiveClassDetailVO.getData().getScene().equals("ppt")) {
                    pLVPlayBackVO.setLiveType(1);
                } else {
                    pLVPlayBackVO.setLiveType(0);
                }
                return pLVPlayBackVO;
            }
        });
    }

    private Observable<PLVPlayBackVO> requestPlaybackStatus(final String str) {
        return Observable.create(new ObservableOnSubscribe<PLVPlayBackVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.11
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<PLVPlayBackVO> observableEmitter) throws Exception {
                PLVLoginManager.getPlayBackType(str, new PLVResponseCallbackAdapter(observableEmitter));
            }
        });
    }

    private Observable<PLVChatDomain> verify(final String str, final String str2, final String str3, final String str4, final String str5) {
        return Observable.create(new ObservableOnSubscribe<PLVChatDomain>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.23
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<PLVChatDomain> observableEmitter) throws Exception {
                PLVLoginManager.checkLoginToken(str, str2, str5, str3, str4, new PLVResponseCallbackAdapter(observableEmitter));
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void destroy() {
        dispose(this.loginLiveDisposable);
        dispose(this.loginPlaybackDisposable);
        dispose(this.loginDisposable);
        dispose(this.getPushStreamDisposable);
        dispose(this.loginHiClassDisposable);
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginHiClassStudent(@NonNull PLVHCStudentLoginVerifyType pLVHCStudentLoginVerifyType, @Nullable String str, @Nullable Long l2, @Nullable String str2, @Nullable String str3, @Nullable String str4, final IPLVSceneLoginManager.OnLoginListener<PLVHCStudentVerifyResultVO> onLoginListener) {
        dispose(this.loginHiClassDisposable);
        PLVHCStudentVerifyRequestVO pLVHCStudentVerifyRequestVO = new PLVHCStudentVerifyRequestVO();
        pLVHCStudentVerifyRequestVO.setCourseCode(str);
        pLVHCStudentVerifyRequestVO.setLessonId(l2);
        pLVHCStudentVerifyRequestVO.setName(str2);
        pLVHCStudentVerifyRequestVO.setCode(str3);
        pLVHCStudentVerifyRequestVO.setStudentCode(str4);
        int i2 = AnonymousClass24.$SwitchMap$com$plv$livescenes$feature$login$PLVSceneLoginManager$PLVHCStudentLoginVerifyType[pLVHCStudentLoginVerifyType.ordinal()];
        Observable<PLVHCStudentVerifyResultVO> observableLoginStudentWithWhiteListVerify = i2 != 1 ? i2 != 2 ? i2 != 3 ? null : PLVApiManager.getHiClassApi().loginStudentWithWhiteListVerify(pLVHCStudentVerifyRequestVO) : PLVApiManager.getHiClassApi().loginStudentWithCodeVerify(pLVHCStudentVerifyRequestVO) : PLVApiManager.getHiClassApi().loginStudentWithNoVerify(pLVHCStudentVerifyRequestVO);
        if (observableLoginStudentWithWhiteListVerify != null) {
            this.loginHiClassDisposable = observableLoginStudentWithWhiteListVerify.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVHCStudentVerifyResultVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.20
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVHCStudentVerifyResultVO pLVHCStudentVerifyResultVO) throws Exception {
                    if (pLVHCStudentVerifyResultVO == null || onLoginListener == null) {
                        return;
                    }
                    if (pLVHCStudentVerifyResultVO.getSuccess() != null && pLVHCStudentVerifyResultVO.getSuccess().booleanValue()) {
                        onLoginListener.onLoginSuccess(pLVHCStudentVerifyResultVO);
                    } else if (pLVHCStudentVerifyResultVO.getError() != null) {
                        onLoginListener.onLoginFailed(pLVHCStudentVerifyResultVO.getError().getDesc(), null);
                    }
                }
            }, new Consumer<Throwable>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.21
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                    if (onLoginListener2 != null) {
                        onLoginListener2.onLoginFailed(th.getMessage(), th);
                    }
                }
            });
        } else if (onLoginListener != null) {
            onLoginListener.onLoginFailed("观看条件不正确", null);
        }
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginHiClassTeacher(@Nullable String str, @NonNull String str2, @NonNull String str3, @Nullable Long l2, @Nullable String str4, final IPLVSceneLoginManager.OnLoginListener<PLVHCTeacherLoginVO> onLoginListener) {
        dispose(this.loginHiClassDisposable);
        PLVHCTeacherLoginRequestVO pLVHCTeacherLoginRequestVO = new PLVHCTeacherLoginRequestVO();
        pLVHCTeacherLoginRequestVO.setCode(str);
        pLVHCTeacherLoginRequestVO.setMobile(str2);
        pLVHCTeacherLoginRequestVO.setPasswd(str3);
        pLVHCTeacherLoginRequestVO.setLessonId(l2);
        pLVHCTeacherLoginRequestVO.setUserId(str4);
        this.loginHiClassDisposable = PLVApiManager.getHiClassApi().loginTeacher(pLVHCTeacherLoginRequestVO).map(new Function<PLVHCTeacherLoginResultVO, PLVHCTeacherLoginVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.19
            @Override // io.reactivex.functions.Function
            public PLVHCTeacherLoginVO apply(@NotNull PLVHCTeacherLoginResultVO pLVHCTeacherLoginResultVO) throws Exception {
                PLVHCTeacherLoginVO pLVHCTeacherLoginVO = new PLVHCTeacherLoginVO();
                if (pLVHCTeacherLoginResultVO.getSuccess() != null && pLVHCTeacherLoginResultVO.getSuccess().booleanValue()) {
                    pLVHCTeacherLoginVO.setStatus(0);
                    pLVHCTeacherLoginVO.setSuccessData((PLVHCTeacherLoginResultVO.DataVO) new Gson().fromJson(pLVHCTeacherLoginResultVO.getData(), PLVHCTeacherLoginResultVO.DataVO.class));
                } else if (pLVHCTeacherLoginResultVO.getError() != null && pLVHCTeacherLoginResultVO.getError().getCode() != null) {
                    if (pLVHCTeacherLoginResultVO.getError().getCode().intValue() == 20028) {
                        List<PLVHCTeacherLoginResultVO.CompanyVO> list = (List) new Gson().fromJson(pLVHCTeacherLoginResultVO.getData(), new TypeToken<List<PLVHCTeacherLoginResultVO.CompanyVO>>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.19.1
                        }.getType());
                        pLVHCTeacherLoginVO.setStatus(1);
                        pLVHCTeacherLoginVO.setCompanyList(list);
                    } else {
                        pLVHCTeacherLoginVO.setStatus(-1);
                        pLVHCTeacherLoginVO.setServerResponseErrorCode(pLVHCTeacherLoginResultVO.getError().getCode());
                        pLVHCTeacherLoginVO.setServerResponseErrorDesc(pLVHCTeacherLoginResultVO.getError().getDesc());
                    }
                }
                return pLVHCTeacherLoginVO;
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVHCTeacherLoginVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.17
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVHCTeacherLoginVO pLVHCTeacherLoginVO) throws Exception {
                if (onLoginListener != null) {
                    if (pLVHCTeacherLoginVO.getStatus() == null || pLVHCTeacherLoginVO.getStatus().intValue() != -1) {
                        onLoginListener.onLoginSuccess(pLVHCTeacherLoginVO);
                    } else {
                        onLoginListener.onLoginFailed(pLVHCTeacherLoginVO.getServerResponseErrorDesc(), null);
                    }
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.18
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginFailed(th.getMessage(), th);
                }
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginLive(String str, String str2, String str3, String str4, final IPLVSceneLoginManager.OnLoginListener<PolyvLiveLoginResult> onLoginListener) {
        loginLiveNew(str, str2, str3, str4, new IPLVSceneLoginManager.OnLoginListener<PLVLiveLoginResult>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.4
            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginFailed(String str5, Throwable th) {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginFailed(str5, th);
                }
            }

            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginSuccess(PLVLiveLoginResult pLVLiveLoginResult) {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginSuccess(new PolyvLiveLoginResult(pLVLiveLoginResult.getChannelTypeNew(), pLVLiveLoginResult.getLiveStatus()));
                }
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginLiveNew(final String str, final String str2, final String str3, final String str4, final IPLVSceneLoginManager.OnLoginListener<PLVLiveLoginResult> onLoginListener) {
        dispose(this.loginLiveDisposable);
        this.loginLiveDisposable = Observable.zip(verify(str3, str2, str4, "", str), requestLiveStatus(str4, str, str2), requestLiveDetail(str4, str, str2), new Function3<PLVChatDomain, PLVLiveStatusVO2, PLVLiveClassDetailVO, PLVLiveLoginResult>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.3
            @Override // io.reactivex.functions.Function3
            public PLVLiveLoginResult apply(PLVChatDomain pLVChatDomain, PLVLiveStatusVO2 pLVLiveStatusVO2, PLVLiveClassDetailVO pLVLiveClassDetailVO) throws Exception {
                PolyvLiveSDKClient.getInstance().setAppIdSecret(str3, str, str2);
                PolyvLiveSDKClient.getInstance().setChannelId(str4);
                PLVChatDomainManager.getInstance().setChatDomain(pLVChatDomain);
                PLVLiveChannelType pLVLiveChannelTypeMapFromServerString = PLVLiveChannelType.mapFromServerString(pLVLiveStatusVO2.getChannelType());
                PLVLiveStatusType pLVLiveStatusTypeMapFromServerString = PLVLiveStatusType.mapFromServerString(pLVLiveStatusVO2.getLiveStatus());
                String rtcType = pLVLiveClassDetailVO.getData().getRtcType();
                String rtcAudioSubEnabled = pLVLiveClassDetailVO.getData().getRtcAudioSubEnabled();
                String pureRtcAvailState = pLVLiveClassDetailVO.getData().getPureRtcAvailState();
                String pureRtcEnabled = pLVLiveClassDetailVO.getData().getPureRtcEnabled();
                boolean zIsQuickLiveEnabled = pLVLiveClassDetailVO.getData().isQuickLiveEnabled();
                boolean zIsRestrictChatEnabled = pLVLiveClassDetailVO.getData().isRestrictChatEnabled();
                boolean zIsChatRobotEnabled = pLVLiveClassDetailVO.getData().isChatRobotEnabled();
                long jLongValue = ((Long) PLVSugarUtil.getOrDefault(pLVLiveClassDetailVO.getData().getMaxViewer(), -1L)).longValue();
                PLVLinkMicConfig.getInstance().setRtcType(rtcType).setLiveChannelType(pLVLiveChannelTypeMapFromServerString).setQuickLiveEnable(rtcType.equals(PLVLinkMicConstant.RtcType.RTC_TYPE_T) && "Y".equals(pureRtcAvailState) && zIsQuickLiveEnabled).setPureRtcWatchEnabled(rtcType.equals(PLVLinkMicConstant.RtcType.RTC_TYPE_U) && "Y".equals(pureRtcAvailState) && "Y".equals(pureRtcEnabled)).setPureRtcOnlySubscribeMainScreenVideo("Y".equals(rtcAudioSubEnabled));
                PLVChannelFeatureManager.onChannel(str4).set(PLVChannelFeature.LIVE_CHATROOM_RESTRICT_MAX_VIEWER, Long.valueOf((!zIsRestrictChatEnabled || zIsChatRobotEnabled) ? -1L : jLongValue));
                PLVELogsService.getInstance().setELogSender(PLVELogRequestManager.getInstance());
                return new PLVLiveLoginResult(pLVLiveChannelTypeMapFromServerString, pLVLiveStatusTypeMapFromServerString);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVLiveLoginResult>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVLiveLoginResult pLVLiveLoginResult) throws Exception {
                PLVChannelFeatureManager.onChannel(str4).set(PLVChannelFeature.LIVE_CHANNEL_TYPE, pLVLiveLoginResult.getChannelTypeNew());
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginSuccess(pLVLiveLoginResult);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginFailed(th.getMessage(), th);
                }
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginPlayback(String str, String str2, String str3, String str4, String str5, final IPLVSceneLoginManager.OnLoginListener<PolyvPlaybackLoginResult> onLoginListener) {
        loginPlaybackNew(str, str2, str3, str4, str5, new IPLVSceneLoginManager.OnLoginListener<PLVPlaybackLoginResult>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.10
            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginFailed(String str6, Throwable th) {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginFailed(str6, th);
                }
            }

            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginSuccess(PLVPlaybackLoginResult pLVPlaybackLoginResult) {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginSuccess(new PolyvPlaybackLoginResult(pLVPlaybackLoginResult.getChannelTypeNew()));
                }
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginPlaybackNew(final String str, final String str2, final String str3, final String str4, String str5, final IPLVSceneLoginManager.OnLoginListener<PLVPlaybackLoginResult> onLoginListener) {
        dispose(this.loginPlaybackDisposable);
        this.loginPlaybackDisposable = Observable.zip(verify(str3, str2, str4, str5, str), TextUtils.isEmpty(str5) ? requestPlayBackStatusByChannelId(str4, str, str2) : requestPlaybackStatus(str5), new BiFunction<PLVChatDomain, PLVPlayBackVO, PLVPlaybackLoginResult>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.9
            @Override // io.reactivex.functions.BiFunction
            public PLVPlaybackLoginResult apply(PLVChatDomain pLVChatDomain, PLVPlayBackVO pLVPlayBackVO) throws Exception {
                PolyvLiveSDKClient.getInstance().setAppIdSecret(str3, str, str2);
                PolyvLiveSDKClient.getInstance().setChannelId(str4);
                PLVLiveChannelType pLVLiveChannelType = PLVLiveChannelType.PPT;
                if (pLVPlayBackVO.getLiveType() == 0) {
                    pLVLiveChannelType = PLVLiveChannelType.ALONE;
                }
                PLVELogsService.getInstance().setELogSender(PLVELogRequestManager.getInstance());
                return new PLVPlaybackLoginResult(pLVLiveChannelType);
            }
        }).subscribe(new Consumer<PLVPlaybackLoginResult>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.7
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackLoginResult pLVPlaybackLoginResult) throws Exception {
                PLVChannelFeatureManager.onChannel(str4).set(PLVChannelFeature.LIVE_CHANNEL_TYPE, pLVPlaybackLoginResult.getChannelTypeNew());
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginSuccess(pLVPlaybackLoginResult);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginFailed(th.getMessage(), th);
                }
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginStreamer(String str, String str2, final IPLVSceneLoginManager.OnLoginListener<PLVSLoginVO> onLoginListener) {
        loginStreamerNew(str, str2, new IPLVSceneLoginManager.OnLoginListener<PLVLoginVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.16
            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginFailed(String str3, Throwable th) {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginFailed(str3, th);
                }
            }

            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginSuccess(PLVLoginVO pLVLoginVO) {
                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                if (onLoginListener2 != null) {
                    onLoginListener2.onLoginSuccess(PLVReflectionUtil.copyField(pLVLoginVO, new PLVSLoginVO()));
                }
            }
        });
    }

    @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager
    public void loginStreamerNew(final String str, String str2, final IPLVSceneLoginManager.OnLoginListener<PLVLoginVO> onLoginListener) {
        dispose(this.loginDisposable);
        dispose(this.getPushStreamDisposable);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            onLoginListener.onLoginFailed("频道号与密码不可为空", new Throwable("频道号与密码不可为空"));
            return;
        }
        String lowerCase = EncryptUtils.encryptMD5ToString(str2).toLowerCase();
        String strValueOf = String.valueOf(System.currentTimeMillis());
        String polyvLiveAndroidSdkName = PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdkName();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("channelId", str);
        arrayMap.put("passwd", lowerCase);
        arrayMap.put("timestamp", strValueOf);
        arrayMap.put("version", polyvLiveAndroidSdkName);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PLVVideoViewConstant.PREFIX, arrayMap);
        this.loginDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().loginV3(str, lowerCase, strValueOf, strArrCreateSignWithSignatureNonceEncrypt[0], null, polyvLiveAndroidSdkName, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<PLVLoginVO>>(PLVLoginVO.class) { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.14
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<PLVLoginVO> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<PLVLoginVO>, PLVLoginVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.13
            @Override // io.reactivex.functions.Function
            public PLVLoginVO apply(PLVEncryptDataVO<PLVLoginVO> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData();
            }
        }), new PLVrResponseCallback<PLVLoginVO>() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.15
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                if (onLoginListener != null) {
                    String errorMessage = PLVUtils.getErrorMessage(th);
                    PLVEncryptDataVO pLVEncryptDataVO = (PLVEncryptDataVO) PLVGsonUtil.fromJson(PLVEncryptDataVO.class, errorMessage);
                    if (pLVEncryptDataVO != null) {
                        errorMessage = pLVEncryptDataVO.getMessage();
                    }
                    IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                    if (onLoginListener2 instanceof IPLVSceneLoginManager.OnStringCodeLoginListener) {
                        ((IPLVSceneLoginManager.OnStringCodeLoginListener) onLoginListener2).onLoginFailed(errorMessage, errorMessage, new Throwable(errorMessage));
                    } else {
                        onLoginListener2.onLoginFailed(errorMessage, new Throwable(errorMessage));
                    }
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(final PLVLoginVO pLVLoginVO) {
                String status = pLVLoginVO.getStatus();
                if (AliyunLogKey.KEY_OBJECT_KEY.equals(status)) {
                    PLVSceneLoginManager.this.getPushStream(pLVLoginVO, new IPLVSOnGetPushStreamUrlListener() { // from class: com.plv.livescenes.feature.login.PLVSceneLoginManager.15.1
                        @Override // com.plv.livescenes.feature.login.PLVSceneLoginManager.IPLVSOnGetPushStreamUrlListener
                        public void onGet(String str3) {
                            PLVLiveChannelType pLVLiveChannelTypeMapFromServerString = PLVLiveChannelType.PPT;
                            try {
                                pLVLiveChannelTypeMapFromServerString = PLVLiveChannelType.mapFromServerString(pLVLoginVO.getLiveScene());
                            } catch (PLVLiveChannelType.UnknownChannelTypeException unused) {
                            }
                            if (str.startsWith(TarConstants.VERSION_POSIX) && !pLVLiveChannelTypeMapFromServerString.isSupportGuest()) {
                                onLoginListener.onLoginFailed("当前场景暂不支持嘉宾登录", new Throwable("当前场景暂不支持嘉宾登录"));
                                return;
                            }
                            String rtcType = pLVLoginVO.getRtcType();
                            if (!(PLVLinkMicConstant.RtcType.RTC_TYPE_A.equals(rtcType) || PLVLinkMicConstant.RtcType.RTC_TYPE_U.equals(rtcType) || PLVLinkMicConstant.RtcType.RTC_TYPE_T.equals(rtcType))) {
                                IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                                if (onLoginListener2 != null) {
                                    onLoginListener2.onLoginFailed("暂不支持该频道登录", new Throwable("暂不支持该频道登录"));
                                    return;
                                }
                                return;
                            }
                            PLVStreamerInnerDataTransfer.getInstance().setLiveTranscodingEnabled("Y".equals(pLVLoginVO.getMultiplexingEnabled()));
                            PLVStreamerInnerDataTransfer.getInstance().setSupportedMaxBitrate(pLVLoginVO.getRtcMaxResolution());
                            PLVStreamerInnerDataTransfer.getInstance().setColinMicType(pLVLoginVO.getColinMicType());
                            PLVStreamerInnerDataTransfer.getInstance().setInteractNumLimit(pLVLoginVO.getInteractNumLimit());
                            PLVStreamerInnerDataTransfer.getInstance().setPushStreamUrl(str3);
                            PLVStreamerInnerDataTransfer.getInstance().setOnlyAudio("Y".equals(pLVLoginVO.getIsOnlyAudio()));
                            PLVStreamerInnerDataTransfer.getInstance().setLiveStreamingWhenLogin(pLVLoginVO.isLiveStatus());
                            PLVStreamerInnerDataTransfer.getInstance().setRole(pLVLoginVO.getRole());
                            PolyvLiveSDKClient.getInstance().setAppIdSecret(pLVLoginVO.getUseId(), pLVLoginVO.getAppId(), pLVLoginVO.getAppSecret());
                            PolyvLiveSDKClient.getInstance().setChannelId(pLVLoginVO.getChannelId());
                            PolyvLiveSDKClient.getInstance().setStreamId(pLVLoginVO.getStream());
                            PolyvLiveSDKClient.getInstance().setCurrentStream(pLVLoginVO.getCurrentStream());
                            PLVChatDomain pLVChatDomain = new PLVChatDomain();
                            pLVChatDomain.setChatApiDomain(pLVLoginVO.getChatApiDomain());
                            pLVChatDomain.setChatDomain(pLVLoginVO.getChatDomain());
                            PLVChatDomainManager.getInstance().setChatDomain(pLVChatDomain);
                            PLVLinkMicConfig.getInstance().setFrameRate(pLVLoginVO.getClientParams().getFps()).setRtcType(rtcType).setLiveChannelType(pLVLiveChannelTypeMapFromServerString);
                            PLVUserAbilityManager.myAbility().clearRole();
                            PLVUserAbilityManager.myAbility().addRole(str.startsWith(TarConstants.VERSION_POSIX) ? PLVUserRole.STREAMER_NORMAL_GUEST : PLVUserRole.STREAMER_TEACHER);
                            PLVChannelFeatureManager.onChannel(pLVLoginVO.getChannelId()).set(PLVChannelFeature.STREAMER_BEAUTY_ENABLE, Boolean.valueOf(pLVLoginVO.isAppBeautyEnabled())).set(PLVChannelFeature.STREAMER_GUEST_TRANSFER_SPEAKER_ENABLE, Boolean.valueOf(pLVLoginVO.isGuestTranAuthEnabled())).set(PLVChannelFeature.STREAMER_ALONE_ALLOW_CHANGE_PUSH_RESOLUTION_RATIO, Boolean.valueOf(pLVLoginVO.isAppWebStartResolutionRatioEnabled())).set(PLVChannelFeature.STREAMER_ALONE_DEFAULT_PUSH_RESOLUTION_RATIO, pLVLoginVO.getParsedAppWebStartResolutionRatio());
                            PLVELogsService.getInstance().setELogSender(PLVELogRequestManager.getInstance());
                            IPLVSceneLoginManager.OnLoginListener onLoginListener3 = onLoginListener;
                            if (onLoginListener3 != null) {
                                onLoginListener3.onLoginSuccess(pLVLoginVO);
                            }
                        }
                    });
                    return;
                }
                if ("error".equals(status)) {
                    String msg = pLVLoginVO.getMsg();
                    IPLVSceneLoginManager.OnLoginListener onLoginListener2 = onLoginListener;
                    if (onLoginListener2 != null) {
                        if (onLoginListener2 instanceof IPLVSceneLoginManager.OnStringCodeLoginListener) {
                            ((IPLVSceneLoginManager.OnStringCodeLoginListener) onLoginListener2).onLoginFailed(msg, pLVLoginVO.getCode(), new Throwable(msg));
                        } else {
                            onLoginListener2.onLoginFailed(msg, new Throwable(msg));
                        }
                    }
                }
            }
        });
    }
}
