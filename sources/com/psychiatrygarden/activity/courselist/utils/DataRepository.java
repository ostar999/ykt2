package com.psychiatrygarden.activity.courselist.utils;

import android.util.Log;
import androidx.lifecycle.LiveData;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.utils.DataRepository;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class DataRepository {
    private List<CourseListChapterBean.DataBean> dataList = new ArrayList();
    public List<CourseVideoListBean.DataBean> dataVideoList = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.courselist.utils.DataRepository$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        final /* synthetic */ AjaxParams val$params;

        public AnonymousClass1(final AjaxParams val$params) {
            this.val$params = val$params;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(AjaxParams ajaxParams, ObservableEmitter observableEmitter) throws Exception {
            if (DataRepository.this.dataList.size() > 0) {
                ProjectApp.database.getChapterDao().deleteData();
                for (int i2 = 0; i2 < DataRepository.this.dataList.size(); i2++) {
                    ((CourseListChapterBean.DataBean) DataRepository.this.dataList.get(i2)).setType(ajaxParams.getParam().get("type"));
                }
                ProjectApp.database.getChapterDao().insertTopicList(DataRepository.this.dataList);
                observableEmitter.onNext("插入成功！");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$1(String str) throws Exception {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass1) s2);
            try {
                CourseListChapterBean courseListChapterBean = (CourseListChapterBean) new Gson().fromJson(s2, CourseListChapterBean.class);
                if (courseListChapterBean.getCode() == 200) {
                    DataRepository.this.dataList = courseListChapterBean.getData();
                    final AjaxParams ajaxParams = this.val$params;
                    Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.utils.e
                        @Override // io.reactivex.ObservableOnSubscribe
                        public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                            this.f12181a.lambda$onSuccess$0(ajaxParams, observableEmitter);
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.utils.f
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) throws Exception {
                            DataRepository.AnonymousClass1.lambda$onSuccess$1((String) obj);
                        }
                    });
                } else {
                    ToastUtil.shortToast(ProjectApp.instance(), "加载失败！");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.utils.DataRepository$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        final /* synthetic */ AjaxParams val$params;
        final /* synthetic */ Long val$ss;

        public AnonymousClass2(final AjaxParams val$params, final Long val$ss) {
            this.val$params = val$params;
            this.val$ss = val$ss;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(AjaxParams ajaxParams, Long l2, ObservableEmitter observableEmitter) throws Exception {
            if (DataRepository.this.dataVideoList.size() > 0) {
                ProjectApp.database.getCourseVideoDao().deleteData();
                for (int i2 = 0; i2 < DataRepository.this.dataVideoList.size(); i2++) {
                    DataRepository.this.dataVideoList.get(i2).setType(ajaxParams.getParam().get("type"));
                    DataRepository.this.dataVideoList.get(i2).setThumb(CommonUtil.getVideoMd5keyMyvalue(DataRepository.this.dataVideoList.get(i2).getThumb(), l2));
                }
                ProjectApp.database.getCourseVideoDao().insertTopicList(DataRepository.this.dataVideoList);
                observableEmitter.onNext("插入成功");
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                CourseVideoListBean courseVideoListBean = (CourseVideoListBean) new Gson().fromJson(s2, CourseVideoListBean.class);
                if (courseVideoListBean.getCode().equals("200")) {
                    DataRepository.this.dataVideoList = courseVideoListBean.getData();
                    final AjaxParams ajaxParams = this.val$params;
                    final Long l2 = this.val$ss;
                    Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.utils.g
                        @Override // io.reactivex.ObservableOnSubscribe
                        public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                            this.f12183a.lambda$onSuccess$0(ajaxParams, l2, observableEmitter);
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() { // from class: com.psychiatrygarden.activity.courselist.utils.DataRepository.2.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(String s3) throws Exception {
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putVideoData$2(VideoDownBean[] videoDownBeanArr, ObservableEmitter observableEmitter) throws Exception {
        ProjectApp.database.getVideoDownDao().insertTopicList(videoDownBeanArr);
        observableEmitter.onNext("插入视频成功");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putVideoData$3(String str) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateData$0(int i2, String[] strArr, ObservableEmitter observableEmitter) throws Exception {
        try {
            ProjectApp.database.getVideoDownDao().updataModelStatus(i2, strArr);
            observableEmitter.onNext(Boolean.TRUE);
        } catch (Exception unused) {
            observableEmitter.onNext(Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateData$1(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            Log.d("更新状态", "accept: true");
        } else {
            Log.d("更新状态", "accept: false");
        }
    }

    public void getChapterData(AjaxParams params) {
        YJYHttpUtils.get(ProjectApp.instance(), NetworkRequestsURL.chapterApi, params, new AnonymousClass1(params));
    }

    public LiveData<List<CourseListChapterBean.DataBean>> getChapterInfo(AjaxParams params) {
        getChapterData(params);
        return ProjectApp.database.getChapterDao().getChapterList(params.getParam().get("vidteaching_id"), params.getParam().get("type"));
    }

    public LiveData<List<CourseVideoListBean.DataBean>> getChapterVideo(AjaxParams params) {
        getChapterVideoData(params);
        return ProjectApp.database.getCourseVideoDao().getChapterVideoList(params.getParam().get("vidteaching_id"), params.getParam().get("type"), UserConfig.getUserId() + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), params.getParam().get("chapter_id") == "" ? "0" : params.getParam().get("chapter_id"));
    }

    public void getChapterVideoData(AjaxParams params) {
        YJYHttpUtils.get(ProjectApp.instance(), NetworkRequestsURL.coursevideoApi, params, new AnonymousClass2(params, Long.valueOf((System.currentTimeMillis() / 1000) + 1800)));
    }

    public LiveData<List<VideoDownBean>> getDownloadInfo(String cId) {
        return ProjectApp.database.getVideoDownDao().getDownLoadInfo(cId);
    }

    public void putVideoData(final VideoDownBean... videoDownBean) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.utils.a
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                DataRepository.lambda$putVideoData$2(videoDownBean, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.utils.b
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                DataRepository.lambda$putVideoData$3((String) obj);
            }
        });
    }

    public void updateData(final int u2, final String[] spl) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.utils.c
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                DataRepository.lambda$updateData$0(u2, spl, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.utils.d
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                DataRepository.lambda$updateData$1((Boolean) obj);
            }
        });
    }

    public void updateVideo(CourseVideoListBean.DataBean bean) {
        ProjectApp.database.getCourseVideoDao().updateVideoStatus(bean);
    }
}
