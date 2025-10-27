package com.plv.livescenes.hiclass.api;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.net.dns.PLVAliHttpDnsStorage;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.livescenes.hiclass.PLVHiClassGlobalConfig;
import com.plv.livescenes.hiclass.vo.PLVHCChangeLessonRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCChangeLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCGroupInfoVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonDetailVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonFinishVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginVerifyVO;
import com.plv.livescenes.net.PLVApiManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class PLVHCApiManager implements IPLVHCApiManager {
    private static volatile PLVHCApiManager INSTANCE;

    private PLVHCApiManager() {
    }

    public static IPLVHCApiManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVHCApiManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVHCApiManager();
                }
            }
        }
        return INSTANCE;
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCChangeLessonStatusVO> changeLessonStatus(int i2) {
        PLVHCChangeLessonRequestVO pLVHCChangeLessonRequestVO = new PLVHCChangeLessonRequestVO();
        pLVHCChangeLessonRequestVO.setLessonId(PLVHiClassGlobalConfig.getLessonId() + "");
        pLVHCChangeLessonRequestVO.setStatus(i2);
        return PLVApiManager.getHiClassApi().changeLessonStatus(pLVHCChangeLessonRequestVO, PLVHiClassGlobalConfig.getToken()).compose(new PLVRxBaseTransformer());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<String> getGroupName(final String str) {
        return getLiveApiChannelToken(PLVHiClassGlobalConfig.getToken(), PLVHiClassGlobalConfig.getLessonId()).filter(new Predicate<PLVHCLiveApiChannelTokenVO>() { // from class: com.plv.livescenes.hiclass.api.PLVHCApiManager.5
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVHCLiveApiChannelTokenVO pLVHCLiveApiChannelTokenVO) throws Exception {
                if ((pLVHCLiveApiChannelTokenVO.getSuccess() != null && pLVHCLiveApiChannelTokenVO.getSuccess().booleanValue() && pLVHCLiveApiChannelTokenVO.getData() != null) || pLVHCLiveApiChannelTokenVO.getError() == null) {
                    return true;
                }
                throw new Exception(pLVHCLiveApiChannelTokenVO.getError().getCode() + "-" + pLVHCLiveApiChannelTokenVO.getError().getDesc());
            }
        }).flatMap(new Function<PLVHCLiveApiChannelTokenVO, ObservableSource<PLVHCGroupInfoVO>>() { // from class: com.plv.livescenes.hiclass.api.PLVHCApiManager.4
            @Override // io.reactivex.functions.Function
            public ObservableSource<PLVHCGroupInfoVO> apply(@NonNull PLVHCLiveApiChannelTokenVO pLVHCLiveApiChannelTokenVO) throws Exception {
                String token = pLVHCLiveApiChannelTokenVO.getData().getToken();
                String appId = pLVHCLiveApiChannelTokenVO.getData().getAppId();
                Integer channelId = pLVHCLiveApiChannelTokenVO.getData().getChannelId();
                return PLVApiManager.getPlvLiveStatusApi().getGroupNameList(channelId + "", appId, System.currentTimeMillis(), token);
            }
        }).map(new Function<PLVHCGroupInfoVO, String>() { // from class: com.plv.livescenes.hiclass.api.PLVHCApiManager.3
            @Override // io.reactivex.functions.Function
            public String apply(PLVHCGroupInfoVO pLVHCGroupInfoVO) throws Exception {
                if (pLVHCGroupInfoVO.getCode().intValue() != 200) {
                    throw new Exception("request groupName fail.");
                }
                for (PLVHCGroupInfoVO.DataBean dataBean : pLVHCGroupInfoVO.getData()) {
                    String str2 = str;
                    if (str2 != null && str2.equals(dataBean.getGroupId())) {
                        return dataBean.getGroupName();
                    }
                }
                throw new Exception("no found groupName.");
            }
        }).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCLessonDetailVO> getLessonDetail() {
        return getLessonDetail(PLVHiClassGlobalConfig.isTeacherType(), PLVHiClassGlobalConfig.getCourseCode(), PLVHiClassGlobalConfig.getLessonId(), PLVHiClassGlobalConfig.getToken());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCLessonFinishVO> getLessonFinishInfo() {
        return PLVApiManager.getHiClassApi().getLessonFinishInfo(PLVHiClassGlobalConfig.getLessonId(), PLVHiClassGlobalConfig.getToken()).compose(new PLVRxBaseTransformer());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCLessonSimpleInfoResultVO> getLessonSimpleInfo(@Nullable String str, @Nullable Long l2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("plv");
        if (l2 != null) {
            sb.append(l2);
        } else if (str != null) {
            sb.append(str);
        }
        sb.append(jCurrentTimeMillis);
        return PLVApiManager.getHiClassApi().getLessonSimpleInfo(str, l2, jCurrentTimeMillis, PLVUtils.MD5(sb.toString()).toLowerCase()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCLessonStatusVO> getLessonStatus(@androidx.annotation.NonNull String str, long j2) {
        return PLVApiManager.getHiClassApi().getLessonStatus(str, j2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCLiveApiChannelTokenVO> getLiveApiChannelToken(@androidx.annotation.NonNull String str, long j2) {
        return PLVHiClassGlobalConfig.isTeacherType() ? PLVApiManager.getHiClassApi().getLiveApiChannelToken(str, new PLVHCLiveApiChannelTokenRequestVO(j2)).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()) : PLVApiManager.getHiClassApi().getStudentLiveApiChannelToken(str, j2, new PLVHCLiveApiChannelTokenRequestVO(j2)).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    @Nullable
    @WorkerThread
    public PLVHCLiveApiChannelTokenVO getLiveApiChannelTokenSync(@androidx.annotation.NonNull String str, long j2) throws IOException {
        Response<PLVHCLiveApiChannelTokenVO> responseExecute = PLVHiClassGlobalConfig.isTeacherType() ? PLVApiManager.getHiClassApi().getLiveApiChannelTokenSync(str, new PLVHCLiveApiChannelTokenRequestVO(j2)).execute() : PLVApiManager.getHiClassApi().getStudentLiveApiChannelTokenSync(str, j2, new PLVHCLiveApiChannelTokenRequestVO(j2)).execute();
        if (responseExecute.isSuccessful()) {
            return responseExecute.body();
        }
        return null;
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCStudentLessonListVO.DataVO> getWatchNextLesson() {
        return PLVApiManager.getHiClassApi().listStudentLesson(PLVHiClassGlobalConfig.getToken(), PLVHiClassGlobalConfig.getCourseCode()).map(new Function<PLVHCStudentLessonListVO, PLVHCStudentLessonListVO.DataVO>() { // from class: com.plv.livescenes.hiclass.api.PLVHCApiManager.2
            @Override // io.reactivex.functions.Function
            public PLVHCStudentLessonListVO.DataVO apply(PLVHCStudentLessonListVO pLVHCStudentLessonListVO) throws Exception {
                if (pLVHCStudentLessonListVO.getData() == null) {
                    return null;
                }
                for (PLVHCStudentLessonListVO.DataVO dataVO : pLVHCStudentLessonListVO.getData()) {
                    if (dataVO.getLessonId() != null && dataVO.getLessonId().longValue() != PLVHiClassGlobalConfig.getLessonId()) {
                        return dataVO;
                    }
                }
                return null;
            }
        }).compose(new PLVRxBaseTransformer());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCStudentLessonListVO> listStudentLesson(@androidx.annotation.NonNull String str, @androidx.annotation.NonNull String str2) {
        return PLVApiManager.getHiClassApi().listStudentLesson(str, str2).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCTeacherLessonListVO> listTeacherLesson(@androidx.annotation.NonNull @NotNull String str) {
        return PLVApiManager.getHiClassApi().listTeacherLesson(str).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCTeacherLoginVerifyVO> verifyTeacherLogin() {
        return PLVApiManager.getHiClassApi().verifyTeacherLogin(PLVHiClassGlobalConfig.getToken(), PLVHiClassGlobalConfig.getLessonId()).compose(new PLVRxBaseTransformer());
    }

    @Override // com.plv.livescenes.hiclass.api.IPLVHCApiManager
    public Observable<PLVHCLessonDetailVO> getLessonDetail(boolean z2, @Nullable String str, long j2, @androidx.annotation.NonNull String str2) {
        Observable observableCompose;
        if (z2) {
            observableCompose = PLVApiManager.getHiClassApi().getTeachLessonDetail(j2, str2).compose(new PLVRxBaseTransformer());
        } else {
            observableCompose = PLVApiManager.getHiClassApi().getWatchLessonDetail(str, j2, str2).compose(new PLVRxBaseTransformer());
        }
        return observableCompose.doOnNext(new Consumer<PLVHCLessonDetailVO>() { // from class: com.plv.livescenes.hiclass.api.PLVHCApiManager.1
            @Override // io.reactivex.functions.Consumer
            public void accept(final PLVHCLessonDetailVO pLVHCLessonDetailVO) throws Exception {
                String str3 = (String) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<String>() { // from class: com.plv.livescenes.hiclass.api.PLVHCApiManager.1.1
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                    public String get() {
                        return pLVHCLessonDetailVO.getData().getHttpDnsKey();
                    }
                });
                if (str3 != null) {
                    PLVAliHttpDnsStorage.INSTANCE.setKey(str3);
                }
            }
        });
    }
}
