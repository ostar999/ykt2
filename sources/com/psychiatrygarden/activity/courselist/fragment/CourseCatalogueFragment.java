package com.psychiatrygarden.activity.courselist.fragment;

import android.view.View;
import android.widget.ExpandableListView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.source.VidSts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.CourseDurationBean;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.viewmodel.CourseChapterViewModel;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseFreeBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseCatalogueFragment extends BaseFragment implements CourseCalalogueListAdapter.CalaInterfaceIml {
    private int childIndex;
    private ExpandableListView exListView;
    private int groupIndex;
    private CourseCalalogueListAdapter mCourseCalalogueListAdapter;
    private CourseChapterViewModel viewModel;
    private final List<CourseCalalogueBean.DataNewBean.DataBean> data = new ArrayList();
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        final /* synthetic */ AjaxParams val$params;

        public AnonymousClass2(final AjaxParams val$params) {
            this.val$params = val$params;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$0(List list, AjaxParams ajaxParams, ObservableEmitter observableEmitter) throws Exception {
            if (list.size() > 0) {
                ProjectApp.database.getCourseCalalogueDao().deleteCourseCalalogue(ajaxParams.getParam().get("user_id") + ajaxParams.getParam().get("app_id"), ajaxParams.getParam().get("category_id"));
                ProjectApp.database.getCourseCalalogueDao().insertCourseCalalogueData(list);
                observableEmitter.onNext("");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(AjaxParams ajaxParams, String str) throws Exception {
            CourseCatalogueFragment.this.getDataList(ajaxParams);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CourseCatalogueFragment.this.getDataList(this.val$params);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    CourseCatalogueFragment.this.getDataList(this.val$params);
                    return;
                }
                String strOptString = jSONObject.optJSONObject("data").optString("permission");
                String strOptString2 = jSONObject.optJSONObject("data").optString("chapterList");
                CourseCalalogueBean.DataNewBean.PerMissionBean perMissionBean = (CourseCalalogueBean.DataNewBean.PerMissionBean) new Gson().fromJson(strOptString, CourseCalalogueBean.DataNewBean.PerMissionBean.class);
                EventBus.getDefault().post(perMissionBean);
                if (CourseCatalogueFragment.this.mCourseCalalogueListAdapter != null) {
                    CourseCatalogueFragment.this.mCourseCalalogueListAdapter.setWatch_permission(perMissionBean.getPermission() + "");
                }
                final List list = (List) new Gson().fromJson(strOptString2, new TypeToken<List<CourseCalalogueBean.DataNewBean.DataBean>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment.2.1
                }.getType());
                final AjaxParams ajaxParams = this.val$params;
                Observable observableObserveOn = Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.j
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                        CourseCatalogueFragment.AnonymousClass2.lambda$onSuccess$0(list, ajaxParams, observableEmitter);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                final AjaxParams ajaxParams2 = this.val$params;
                CourseCatalogueFragment.this.mCompositeDisposable.add(observableObserveOn.subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.k
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f11973c.lambda$onSuccess$1(ajaxParams2, (String) obj);
                    }
                }));
            } catch (Exception unused) {
                CourseCatalogueFragment.this.getDataList(this.val$params);
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status;

        static {
            int[] iArr = new int[AliyunDownloadMediaInfo.Status.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status = iArr;
            try {
                iArr[AliyunDownloadMediaInfo.Status.Idle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Prepare.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Wait.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Start.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Stop.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Complete.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Error.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDataList(final AjaxParams params) {
        this.mCompositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.h
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                CourseCatalogueFragment.lambda$getDataList$7(params, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.i
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11960c.lambda$getDataList$8((List) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDataList$7(AjaxParams ajaxParams, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(ProjectApp.database.getCourseCalalogueDao().getCourseCalalogue(ajaxParams.getParam().get("user_id") + ajaxParams.getParam().get("app_id"), ajaxParams.getParam().get("category_id")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDataList$8(List list) throws Exception {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.viewModel.setCourseCalalogueBean(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        this.data.clear();
        this.data.addAll(list);
        if (this.data.size() > 0) {
            putData(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$1(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (CommonUtil.isFastClick() || 1 == this.data.get(i2).getCourseList().get(i3).getIsSelected()) {
            return true;
        }
        if (this.data.get(this.groupIndex).getCourseList() != null && this.data.get(this.groupIndex).getCourseList().size() > 0) {
            this.data.get(this.groupIndex).getCourseList().get(this.childIndex).setIsSelected(0);
        }
        this.groupIndex = i2;
        this.childIndex = i3;
        this.data.get(i2).getCourseList().get(i3).setIsSelected(1);
        this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        SharePreferencesUtils.writeStrConfig(CommonParameter.coursecategory_id, this.data.get(i2).getCategory_id(), getActivity());
        getCourseAk(i2, i3);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$4(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ObservableEmitter observableEmitter) throws Exception {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.data;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.data.size(); i2++) {
            if (this.data.get(i2).getCourseList() != null && this.data.get(i2).getCourseList().size() > 0) {
                for (int i3 = 0; i3 < this.data.get(i2).getCourseList().size(); i3++) {
                    if (aliyunDownloadMediaInfo.getVid().equals(this.data.get(i2).getCourseList().get(i3).getVid())) {
                        this.data.get(i2).getCourseList().get(i3).setProgress(aliyunDownloadMediaInfo.getProgress());
                        this.data.get(i2).getCourseList().get(i3).setmSavePath(aliyunDownloadMediaInfo.getSavePath());
                        switch (AnonymousClass4.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                            case 1:
                            case 2:
                                this.data.get(i2).getCourseList().get(i3).setmStatus(0);
                                break;
                            case 3:
                                this.data.get(i2).getCourseList().get(i3).setmStatus(3);
                                break;
                            case 4:
                                if (this.data.get(i2).getCourseList().get(i3).getmStatus() != 0) {
                                    this.data.get(i2).getCourseList().get(i3).setmStatus(1);
                                    break;
                                }
                                break;
                            case 5:
                                this.data.get(i2).getCourseList().get(i3).setmStatus(4);
                                break;
                            case 6:
                                this.data.get(i2).getCourseList().get(i3).setmStatus(5);
                                break;
                            case 7:
                                this.data.get(i2).getCourseList().get(i3).setmStatus(2);
                                break;
                        }
                        observableEmitter.onNext(Boolean.TRUE);
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$5(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$6(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.a
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11911a.lambda$mRefreshDownloadData$4(aliyunDownloadMediaInfo, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.b
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11921c.lambda$mRefreshDownloadData$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$2(ObservableEmitter observableEmitter) throws Exception {
        String string = getArguments() == null ? "" : getArguments().getString("category_id");
        List<VideoDownBean> videoDownLoadCourseInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadCourseInfo("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()), "" + string);
        if (videoDownLoadCourseInfo != null && videoDownLoadCourseInfo.size() > 0) {
            for (int i2 = 0; i2 < this.data.size(); i2++) {
                if (this.data.get(i2).getCourseList() != null && this.data.get(i2).getCourseList().size() > 0) {
                    for (int i3 = 0; i3 < this.data.get(i2).getCourseList().size(); i3++) {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= videoDownLoadCourseInfo.size()) {
                                break;
                            }
                            if (!videoDownLoadCourseInfo.get(i4).getVid().equals(this.data.get(i2).getCourseList().get(i3).getVid())) {
                                i4++;
                            } else if (5 != videoDownLoadCourseInfo.get(i4).getmStatus()) {
                                List<VideoDownTempBean> list = ProjectApp.vids;
                                if (list == null || list.size() <= 0) {
                                    this.data.get(i2).getCourseList().get(i3).setmStatus(4);
                                } else {
                                    this.data.get(i2).getCourseList().get(i3).setmStatus(videoDownLoadCourseInfo.get(i4).getmStatus());
                                }
                            } else if (new File(videoDownLoadCourseInfo.get(i4).getmSavePath()).exists()) {
                                this.data.get(i2).getCourseList().get(i3).setmStatus(5);
                                this.data.get(i2).getCourseList().get(i3).setmSavePath(videoDownLoadCourseInfo.get(i4).getmSavePath());
                            } else {
                                this.data.get(i2).getCourseList().get(i3).setmStatus(0);
                            }
                        }
                    }
                }
            }
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$3(boolean z2, Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            if (z2) {
                String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.courseVid, getActivity(), "");
                String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.coursecategory_id, getActivity(), "");
                String strConfig3 = SharePreferencesUtils.readStrConfig(CommonParameter.coursechapter_id, getActivity(), "");
                if (!"".equals(strConfig) && !"".equals(strConfig2) && !"".equals(strConfig3)) {
                    for (int i2 = 0; i2 < this.data.size(); i2++) {
                        if (this.data.get(i2).getCourseList() != null && this.data.get(i2).getCourseList().size() > 0) {
                            for (int i3 = 0; i3 < this.data.get(i2).getCourseList().size(); i3++) {
                                if (strConfig.equals(this.data.get(i2).getCourseList().get(i3).getVid()) && strConfig2.equals(this.data.get(i2).getCourseList().get(i3).getCategory_id()) && strConfig3.equals(this.data.get(i2).getCourseList().get(i3).getChapter_id())) {
                                    this.groupIndex = i2;
                                    this.childIndex = i3;
                                    this.data.get(i2).getCourseList().get(i3).setIsSelected(1);
                                    this.exListView.expandGroup(this.groupIndex, true);
                                    this.exListView.setSelectedGroup(this.groupIndex);
                                    this.exListView.setSelectedChild(this.groupIndex, this.childIndex, true);
                                    getCourseAk(this.groupIndex, this.childIndex);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
    }

    public void getCourseAk(final int gPosition, final int cPosition) {
        if (5 == this.data.get(gPosition).getCourseList().get(cPosition).getmStatus()) {
            String str = this.data.get(gPosition).getCourseList().get(cPosition).getmSavePath();
            if (new File(str).exists()) {
                VidSts vidSts = new VidSts();
                vidSts.setVid(this.data.get(gPosition).getCourseList().get(cPosition).getVid());
                EventBus.getDefault().post(new CourseFreeBean(vidSts, this.data.get(gPosition).getActivity_id(), this.data.get(gPosition).getExpire_str(), this.data.get(gPosition).getWatch_permission(), this.data.get(gPosition).getCourseList().get(cPosition).getFree_watch_time(), this.data.get(gPosition).getCourseList().get(cPosition).getChapter_id(), this.data.get(gPosition).getCourseList().get(cPosition).getDuration(), this.data.get(gPosition).getCourseList().get(cPosition).getId(), this.data.get(gPosition).getCourseList().get(cPosition).getCategory_id(), this.data.get(gPosition).getCourseList().get(cPosition).getDurationTemp(), str, "0"));
                return;
            }
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ToastUtil.shortToast(CourseCatalogueFragment.this.getActivity(), "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        VidSts vidSts2 = new VidSts();
                        vidSts2.setVid(((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getVid());
                        vidSts2.setAccessKeyId(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId")));
                        vidSts2.setAccessKeySecret(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret")));
                        vidSts2.setSecurityToken(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st")));
                        vidSts2.setQuality(QualityValue.QUALITY_FLUENT, false);
                        EventBus.getDefault().post(new CourseFreeBean(vidSts2, ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getActivity_id(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getExpire_str(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getWatch_permission(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getFree_watch_time(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getChapter_id(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getDuration(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getId(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getCategory_id(), ((CourseCalalogueBean.DataNewBean.DataBean) CourseCatalogueFragment.this.data.get(gPosition)).getCourseList().get(cPosition).getDurationTemp(), "", "0"));
                    } else {
                        ToastUtil.shortToast(CourseCatalogueFragment.this.getActivity(), jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getCourseCalalogueDataList(AjaxParams params) {
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getCourseNewApi, params, new AnonymousClass2(params));
    }

    public void getCourseDownAk(final CourseCalalogueBean.DataNewBean.DataBean.CourseListBean childrenBean, final String vid) {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseCatalogueFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseCatalogueFragment.this.hideProgressDialog();
                ToastUtil.shortToast(CourseCatalogueFragment.this.getActivity(), "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CourseCatalogueFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                CourseCatalogueFragment.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId"));
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret"));
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st"));
                        CourseCalalogueBean.DataNewBean.DataBean.CourseListBean courseListBean = childrenBean;
                        if (courseListBean == null) {
                            CourseCatalogueFragment.this.getvideoInfo(vid, strDecode, strDecode2, strDecode3);
                        } else {
                            CourseCatalogueFragment.this.getDownList(strDecode, strDecode2, strDecode3, QualityValue.QUALITY_LOW, courseListBean);
                        }
                    } else {
                        ToastUtil.shortToast(CourseCatalogueFragment.this.getActivity(), jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getCourseListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("category_id", "" + getArguments().getString("category_id"));
        ajaxParams.put("am_pm", "" + SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, getActivity()));
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
        getCourseCalalogueDataList(ajaxParams);
    }

    public void getDownList(String acId, String akSceret, String securityToken, String quality, CourseCalalogueBean.DataNewBean.DataBean.CourseListBean childrenBean) {
        if (!hasVidData(childrenBean.getVid(), ProjectApp.vids).booleanValue()) {
            ProjectApp.vids.add(new VideoDownTempBean(childrenBean.getVid(), acId, akSceret, securityToken));
            VideoDownBean videoDownBean = new VideoDownBean();
            videoDownBean.setcId("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()));
            videoDownBean.setChapter_id("" + childrenBean.getChapter_id());
            videoDownBean.setParent_id("" + childrenBean.getCategory_id());
            videoDownBean.setVid(childrenBean.getVid());
            videoDownBean.setmTitle(childrenBean.getTitle());
            videoDownBean.setThumb(childrenBean.getThumb());
            videoDownBean.setmStatus(3);
            videoDownBean.setmFormat(AliPlayUtils.getDownloadVideoDefinition(requireContext()));
            this.viewModel.putData(videoDownBean);
        }
        childrenBean.setmStatus(3);
        this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsHaveMedia = isHaveMedia(ProjectApp.vids.get(0).vid);
        if (aliyunDownloadMediaInfoIsHaveMedia != null) {
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoIsHaveMedia);
        } else {
            getvideoInfo(ProjectApp.vids.get(0).vid, acId, akSceret, securityToken);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_catalogue;
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.CalaInterfaceIml
    public void getVidData(CourseCalalogueBean.DataNewBean.DataBean.CourseListBean childrenBean) {
        getCourseDownAk(childrenBean, "");
    }

    public void getvideoInfo(String vid, String acId, String akSceret, String securityToken) {
        VidSts vidSts = new VidSts();
        vidSts.setQuality(QualityValue.QUALITY_FLUENT, false);
        vidSts.setVid(vid);
        vidSts.setAccessKeyId(acId);
        vidSts.setAccessKeySecret(akSceret);
        vidSts.setSecurityToken(securityToken);
        ProjectApp.downloadManager.prepareDownload(vidSts);
    }

    public Boolean hasVidData(String vid, List<VideoDownTempBean> vids) {
        if (vids != null && vids.size() > 0) {
            Iterator<VideoDownTempBean> it = vids.iterator();
            while (it.hasNext()) {
                if (vid.equals(it.next().vid)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.viewModel = (CourseChapterViewModel) ViewModelProviders.of(this).get(CourseChapterViewModel.class);
        this.exListView = (ExpandableListView) holder.get(R.id.exListView);
        CourseCalalogueListAdapter courseCalalogueListAdapter = new CourseCalalogueListAdapter(getActivity(), this.data, this);
        this.mCourseCalalogueListAdapter = courseCalalogueListAdapter;
        this.exListView.setAdapter(courseCalalogueListAdapter);
        this.viewModel.getCourseCalalogueBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.e
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11939a.lambda$initViews$0((List) obj);
            }
        });
        getCourseListData();
        this.exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.f
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f11945a.lambda$initViews$1(expandableListView, view, i2, i3, j2);
            }
        });
        mRefreshDownloadData();
    }

    public AliyunDownloadMediaInfo isHaveMedia(String vid) {
        Iterator<AliyunDownloadMediaInfo> it = ProjectApp.downloadManager.getDownloadingList().iterator();
        if (!it.hasNext()) {
            return null;
        }
        AliyunDownloadMediaInfo next = it.next();
        if (vid.equals(next.getVid())) {
            return next;
        }
        return null;
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.g
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11950a.lambda$mRefreshDownloadData$6((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.viewModel.onCleared();
        if (this.mCompositeDisposable.isDisposed()) {
            return;
        }
        this.mCompositeDisposable.dispose();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        CourseCalalogueListAdapter courseCalalogueListAdapter;
        if (!"permission".equals(str) || (courseCalalogueListAdapter = this.mCourseCalalogueListAdapter) == null) {
            return;
        }
        courseCalalogueListAdapter.setWatch_permission("1");
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.CalaInterfaceIml
    public void pauseByVid(String vid) {
        if (isHaveMedia(vid) != null) {
            ProjectApp.downloadManager.stopDownload(isHaveMedia(vid));
        }
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        if (!vid.equals(ProjectApp.vids.get(0).vid)) {
            Iterator<VideoDownTempBean> it = ProjectApp.vids.iterator();
            while (it.hasNext()) {
                if (it.next().vid.equals(vid)) {
                    it.remove();
                    return;
                }
            }
            return;
        }
        List<VideoDownTempBean> list2 = ProjectApp.vids;
        list2.remove(list2.get(0));
        List<VideoDownTempBean> list3 = ProjectApp.vids;
        if (list3 == null || list3.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsHaveMedia = isHaveMedia(ProjectApp.vids.get(0).vid);
        if (aliyunDownloadMediaInfoIsHaveMedia != null) {
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoIsHaveMedia);
        } else {
            getCourseDownAk(null, ProjectApp.vids.get(0).vid);
        }
    }

    public void putData(final boolean isShuaxin) {
        this.mCompositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.c
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11930a.lambda$putData$2(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.d
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11934c.lambda$putData$3(isShuaxin, (Boolean) obj);
            }
        }));
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.CalaInterfaceIml
    public void startByVid(CourseCalalogueBean.DataNewBean.DataBean.CourseListBean childrenBean) {
        getCourseDownAk(childrenBean, "");
    }

    public void onEventMainThread(List<CourseCalalogueBean.DataNewBean.DataBean> dataCourseList) {
        if (dataCourseList == null || dataCourseList.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < dataCourseList.size(); i2++) {
            try {
                for (int i3 = 0; i3 < dataCourseList.get(i2).getCourseList().size(); i3++) {
                    dataCourseList.get(i2).getCourseList().get(i3).setIsSelected(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        this.data.clear();
        this.data.addAll(dataCourseList);
        this.mCourseCalalogueListAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(CourseDurationBean courseDurationBean) {
        if (courseDurationBean == null) {
            return;
        }
        long j2 = courseDurationBean.durations;
        this.data.get(this.groupIndex).getCourseList().get(this.childIndex).setDurationTemp(j2);
        if (j2 == ((long) (Double.parseDouble(this.data.get(this.groupIndex).getCourseList().get(this.childIndex).getDuration()) * 1000.0d))) {
            int i2 = this.groupIndex;
            if (this.childIndex >= this.data.get(i2).getCourseList().size() - 1) {
                while (true) {
                    i2++;
                    if (this.data.get(i2).getCourseList() != null && this.data.get(i2).getCourseList().size() > 0) {
                        this.data.get(this.groupIndex).getCourseList().get(this.childIndex).setIsSelected(0);
                        this.exListView.collapseGroup(this.groupIndex);
                        this.groupIndex = i2;
                        this.childIndex = 0;
                        break;
                    }
                    if (i2 >= this.data.size() - 1) {
                        break;
                    }
                }
            } else {
                this.data.get(this.groupIndex).getCourseList().get(this.childIndex).setIsSelected(0);
                this.childIndex++;
            }
            this.exListView.expandGroup(this.groupIndex, true);
            this.exListView.setSelectedGroup(this.groupIndex);
            this.data.get(this.groupIndex).getCourseList().get(this.childIndex).setIsSelected(1);
            int i3 = this.groupIndex;
            if (i2 == i3) {
                getCourseAk(i3, this.childIndex);
            } else {
                EventBus.getDefault().post("endVideo");
            }
        }
        this.mCourseCalalogueListAdapter.notifyDataSetChanged();
    }
}
