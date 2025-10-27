package com.psychiatrygarden.activity.courselist.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.viewmodel.CourseChapterViewModel;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseCalalogueDownLoadActivity extends BaseActivity {
    private CheckBox checkeddown;
    private ExpandableListView exListView;
    private CourseCalalogDownLoadAdapter mCourseCalalogueListAdapter;
    private CourseChapterViewModel viewModel;
    private final List<CourseCalalogueBean.DataNewBean.DataBean> dataCourseList = new ArrayList();
    private VideoDownBean[] videoDownList = new VideoDownBean[0];
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isDestroyed = false;

    /* renamed from: com.psychiatrygarden.activity.courselist.course.CourseCalalogueDownLoadActivity$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
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
    public /* synthetic */ void lambda$getConTrolData$12(ObservableEmitter observableEmitter) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataCourseList.size(); i2++) {
            CourseCalalogueBean.DataNewBean.DataBean dataBean = (CourseCalalogueBean.DataNewBean.DataBean) this.dataCourseList.get(i2).clone();
            if (dataBean.getCourseList() != null && dataBean.getCourseList().size() > 0) {
                ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> arrayList2 = new ArrayList<>();
                for (int i3 = 0; i3 < dataBean.getCourseList().size(); i3++) {
                    if (dataBean.getCourseList().get(i3).getmStatus() > 0) {
                        CourseCalalogueBean.DataNewBean.DataBean.CourseListBean courseListBean = (CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) dataBean.getCourseList().get(i3).clone();
                        courseListBean.setIsSelected(0);
                        arrayList2.add(courseListBean);
                    }
                }
                if (arrayList2.size() > 0) {
                    dataBean.setCourseList(arrayList2);
                    dataBean.setIsSelected(0);
                    arrayList.add(dataBean);
                }
            }
        }
        observableEmitter.onNext(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConTrolData$13(List list) throws Exception {
        Intent intent = new Intent(this, (Class<?>) CourseDownLoadNewActivity.class);
        intent.putExtra("data", (Serializable) list);
        intent.putExtra("category_id", "" + getIntent().getExtras().getString("category_id"));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(CompoundButton compoundButton, boolean z2) {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.dataCourseList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataCourseList.size(); i2++) {
            if (z2) {
                this.checkeddown.setText("取消全选");
                this.dataCourseList.get(i2).setIsSelected(1);
                if (this.dataCourseList.get(i2).getCourseList() != null && this.dataCourseList.get(i2).getCourseList().size() > 0) {
                    for (int i3 = 0; i3 < this.dataCourseList.get(i2).getCourseList().size(); i3++) {
                        this.dataCourseList.get(i2).getCourseList().get(i3).setIsSelected(1);
                    }
                }
            } else {
                this.checkeddown.setText("全选");
                this.dataCourseList.get(i2).setIsSelected(0);
                if (this.dataCourseList.get(i2).getCourseList() != null && this.dataCourseList.get(i2).getCourseList().size() > 0) {
                    for (int i4 = 0; i4 < this.dataCourseList.get(i2).getCourseList().size(); i4++) {
                        this.dataCourseList.get(i2).getCourseList().get(i4).setIsSelected(0);
                        getRemoveData(this.dataCourseList.get(i2).getCourseList().get(i4).getVid());
                    }
                }
            }
        }
        this.mCourseCalalogueListAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        getCourseAk();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        putPauseData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$4(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (this.dataCourseList.get(i2).getCourseList() != null && this.dataCourseList.get(i2).getCourseList().size() > 0) {
            if (this.dataCourseList.get(i2).getCourseList().get(i3).getIsSelected() == 1) {
                this.dataCourseList.get(i2).getCourseList().get(i3).setIsSelected(0);
                getRemoveData(this.dataCourseList.get(i2).getCourseList().get(i3).getVid());
            } else {
                this.dataCourseList.get(i2).getCourseList().get(i3).setIsSelected(1);
            }
            setRefulData(i2);
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$5(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ObservableEmitter observableEmitter) throws Exception {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.dataCourseList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataCourseList.size(); i2++) {
            if (this.dataCourseList.get(i2).getCourseList() != null && this.dataCourseList.get(i2).getCourseList().size() > 0) {
                for (int i3 = 0; i3 < this.dataCourseList.get(i2).getCourseList().size(); i3++) {
                    if (aliyunDownloadMediaInfo.getVid().equals(this.dataCourseList.get(i2).getCourseList().get(i3).getVid())) {
                        this.dataCourseList.get(i2).getCourseList().get(i3).setProgress(aliyunDownloadMediaInfo.getProgress());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmSavePath(aliyunDownloadMediaInfo.getSavePath());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setIsEncripted(aliyunDownloadMediaInfo.isEncripted());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmFormat(aliyunDownloadMediaInfo.getFormat());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmQuality(aliyunDownloadMediaInfo.getQuality());
                        switch (AnonymousClass3.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                            case 1:
                            case 2:
                                this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(0);
                                break;
                            case 3:
                                this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(3);
                                break;
                            case 4:
                                this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(1);
                                break;
                            case 5:
                                this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(4);
                                break;
                            case 6:
                                this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(5);
                                break;
                            case 7:
                                this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(2);
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
    public /* synthetic */ void lambda$mRefreshDownloadData$6(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$7(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.b
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11849a.lambda$mRefreshDownloadData$5(aliyunDownloadMediaInfo, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.c
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11852c.lambda$mRefreshDownloadData$6((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        getConTrolData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$10(ObservableEmitter observableEmitter) throws Exception {
        List<VideoDownBean> videoDownLoadCourseInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadCourseInfo("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()), getIntent().getExtras().getString("category_id"));
        if (videoDownLoadCourseInfo != null && videoDownLoadCourseInfo.size() > 0) {
            for (int i2 = 0; i2 < videoDownLoadCourseInfo.size(); i2++) {
                for (int i3 = 0; i3 < this.dataCourseList.size(); i3++) {
                    if (this.dataCourseList.get(i3).getCourseList() != null && this.dataCourseList.get(i3).getCourseList().size() > 0) {
                        for (int i4 = 0; i4 < this.dataCourseList.get(i3).getCourseList().size(); i4++) {
                            if (videoDownLoadCourseInfo.get(i2).getVid().equals(this.dataCourseList.get(i3).getCourseList().get(i4).getVid())) {
                                if (5 != videoDownLoadCourseInfo.get(i2).getmStatus()) {
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setIsSelected(1);
                                } else if (!new File(videoDownLoadCourseInfo.get(i2).getmSavePath()).exists()) {
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setIsEncripted(videoDownLoadCourseInfo.get(i2).getIsEncripted());
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setmFormat(videoDownLoadCourseInfo.get(i2).getmFormat());
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setmQuality(videoDownLoadCourseInfo.get(i2).getmQuality());
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setmStatus(0);
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setProgress(0);
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setmSavePath("");
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setIsSelected(1);
                                    setRefulData(i3);
                                }
                                this.dataCourseList.get(i3).getCourseList().get(i4).setIsEncripted(videoDownLoadCourseInfo.get(i2).getIsEncripted());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmFormat(videoDownLoadCourseInfo.get(i2).getmFormat());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmQuality(videoDownLoadCourseInfo.get(i2).getmQuality());
                                List<VideoDownTempBean> list = ProjectApp.vids;
                                if ((list == null || list.size() <= 0) && videoDownLoadCourseInfo.get(i2).getmStatus() != 5) {
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setmStatus(4);
                                } else {
                                    this.dataCourseList.get(i3).getCourseList().get(i4).setmStatus(videoDownLoadCourseInfo.get(i2).getmStatus());
                                }
                                this.dataCourseList.get(i3).getCourseList().get(i4).setProgress(videoDownLoadCourseInfo.get(i2).getmProgress());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmSavePath(videoDownLoadCourseInfo.get(i2).getmSavePath());
                                setRefulData(i3);
                            }
                        }
                    }
                }
            }
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$11(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putPauseData$8(ObservableEmitter observableEmitter) throws Exception {
        List<VideoDownBean> videoDownLoadCourseInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadCourseInfo("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()), getIntent().getExtras().getString("category_id"));
        ArrayList arrayList = new ArrayList();
        if (videoDownLoadCourseInfo != null && videoDownLoadCourseInfo.size() > 0) {
            for (int i2 = 0; i2 < videoDownLoadCourseInfo.size(); i2++) {
                if (5 != videoDownLoadCourseInfo.get(i2).getmStatus() && videoDownLoadCourseInfo.get(i2).getmStatus() != 0) {
                    videoDownLoadCourseInfo.get(i2).setmStatus(4);
                    arrayList.add(videoDownLoadCourseInfo.get(i2).getVid());
                }
            }
        }
        for (int i3 = 0; i3 < this.dataCourseList.size(); i3++) {
            if (this.dataCourseList.get(i3).getCourseList() != null && this.dataCourseList.get(i3).getCourseList().size() > 0) {
                for (int i4 = 0; i4 < this.dataCourseList.get(i3).getCourseList().size(); i4++) {
                    if (5 != this.dataCourseList.get(i3).getCourseList().get(i4).getmStatus() && this.dataCourseList.get(i3).getCourseList().get(i4).getmStatus() != 0) {
                        this.dataCourseList.get(i3).getCourseList().get(i4).setmStatus(4);
                    }
                }
            }
        }
        if (arrayList.size() > 0) {
            String[] strArr = new String[arrayList.size()];
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                strArr[i5] = (String) arrayList.get(i5);
            }
            this.viewModel.updataStatusData(4, strArr);
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putPauseData$9(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
    }

    public void getConTrolData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.i
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11866a.lambda$getConTrolData$12(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.j
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11868c.lambda$getConTrolData$13((List) obj);
            }
        }));
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseCalalogueDownLoadActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseCalalogueDownLoadActivity.this.hideProgressDialog();
                ToastUtil.shortToast(CourseCalalogueDownLoadActivity.this, "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CourseCalalogueDownLoadActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        CourseCalalogueDownLoadActivity.this.getDownList(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st")));
                    } else {
                        ToastUtil.shortToast(CourseCalalogueDownLoadActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                CourseCalalogueDownLoadActivity.this.hideProgressDialog();
            }
        });
    }

    public void getCourseListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("category_id", "" + getIntent().getExtras().getString("category_id"));
        ajaxParams.put("am_pm", "" + SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, this));
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getCourseNewApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseCalalogueDownLoadActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseCalalogueDownLoadActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CourseCalalogueDownLoadActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optJSONObject("data").optString("chapterList"), new TypeToken<List<CourseCalalogueBean.DataNewBean.DataBean>>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseCalalogueDownLoadActivity.2.1
                        }.getType());
                        CourseCalalogueDownLoadActivity.this.dataCourseList.clear();
                        CourseCalalogueDownLoadActivity.this.dataCourseList.addAll(list);
                        for (int i2 = 0; i2 < CourseCalalogueDownLoadActivity.this.mCourseCalalogueListAdapter.getGroupCount(); i2++) {
                            CourseCalalogueDownLoadActivity.this.exListView.expandGroup(i2);
                        }
                        if (CourseCalalogueDownLoadActivity.this.dataCourseList != null && CourseCalalogueDownLoadActivity.this.dataCourseList.size() > 0) {
                            CourseCalalogueDownLoadActivity.this.putData();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                CourseCalalogueDownLoadActivity.this.hideProgressDialog();
            }
        });
    }

    public void getDownList(String acId, String akSceret, String securityToken) {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.dataCourseList;
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataCourseList.size(); i2++) {
            if (this.dataCourseList.get(i2).getCourseList() != null && this.dataCourseList.get(i2).getCourseList().size() > 0) {
                for (int i3 = 0; i3 < this.dataCourseList.get(i2).getCourseList().size(); i3++) {
                    if (5 != this.dataCourseList.get(i2).getCourseList().get(i3).getmStatus() && 1 == this.dataCourseList.get(i2).getCourseList().get(i3).getIsSelected()) {
                        arrayList.add(this.dataCourseList.get(i2).getCourseList().get(i3));
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmStatus(3);
                    }
                }
            }
        }
        if (arrayList.size() > 0) {
            this.videoDownList = new VideoDownBean[arrayList.size()];
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                if (!hasVidData(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getVid(), ProjectApp.vids).booleanValue()) {
                    ProjectApp.vids.add(new VideoDownTempBean(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getVid(), acId, akSceret, securityToken));
                }
                VideoDownBean videoDownBean = new VideoDownBean();
                videoDownBean.setcId("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()));
                videoDownBean.setChapter_id("" + ((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getChapter_id());
                videoDownBean.setParent_id("" + ((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getCategory_id());
                videoDownBean.setVid(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getVid());
                videoDownBean.setmTitle(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getTitle());
                videoDownBean.setThumb(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getThumb());
                videoDownBean.setmStatus(3);
                videoDownBean.setmFormat(AliPlayUtils.getDownloadVideoDefinition(this));
                this.videoDownList[i4] = videoDownBean;
            }
        }
        VideoDownBean[] videoDownBeanArr = this.videoDownList;
        if (videoDownBeanArr.length > 0) {
            this.viewModel.putData(videoDownBeanArr);
            this.mCourseCalalogueListAdapter.notifyDataSetChanged();
        }
    }

    public void getRemoveData(String vidt) {
        Iterator<VideoDownTempBean> it = ProjectApp.vids.iterator();
        while (it.hasNext()) {
            if (it.next().vid.equals(vidt)) {
                it.remove();
                return;
            }
        }
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.exListView = (ExpandableListView) findViewById(R.id.exListView);
        TextView textView = (TextView) findViewById(R.id.downTxt);
        TextView textView2 = (TextView) findViewById(R.id.pauseTxt);
        this.checkeddown = (CheckBox) findViewById(R.id.checkeddown);
        this.viewModel = (CourseChapterViewModel) ViewModelProviders.of(this).get(CourseChapterViewModel.class);
        CourseCalalogDownLoadAdapter courseCalalogDownLoadAdapter = new CourseCalalogDownLoadAdapter(this, this.dataCourseList);
        this.mCourseCalalogueListAdapter = courseCalalogDownLoadAdapter;
        this.exListView.setAdapter(courseCalalogDownLoadAdapter);
        getCourseListData();
        this.checkeddown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.courselist.course.k
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f11870c.lambda$init$1(compoundButton, z2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11871c.lambda$init$2(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11873c.lambda$init$3(view);
            }
        });
        this.exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.n
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f11876a.lambda$init$4(expandableListView, view, i2, i3, j2);
            }
        });
        mRefreshDownloadData();
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.course.e
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11859a.lambda$mRefreshDownloadData$7((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("批量下载");
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("管理");
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11857c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        onDestroyDataIntent();
        if (!this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    public void onDestroyDataIntent() {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        EventBus.getDefault().post(this.dataCourseList);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("shuaxin".equals(str)) {
            getCourseListData();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            onDestroyDataIntent();
        }
    }

    public void putData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.g
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11862a.lambda$putData$10(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.h
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11864c.lambda$putData$11((Boolean) obj);
            }
        }));
    }

    public void putPauseData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.a
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11847a.lambda$putPauseData$8(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.f
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11860c.lambda$putPauseData$9((Boolean) obj);
            }
        }));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_download);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setRefulData(int groupPosition) {
        for (int i2 = 0; i2 < this.dataCourseList.get(groupPosition).getCourseList().size(); i2++) {
            if (this.dataCourseList.get(groupPosition).getCourseList().get(i2).getIsSelected() != 1) {
                this.dataCourseList.get(groupPosition).setIsSelected(0);
                return;
            }
        }
        this.dataCourseList.get(groupPosition).setIsSelected(1);
    }
}
