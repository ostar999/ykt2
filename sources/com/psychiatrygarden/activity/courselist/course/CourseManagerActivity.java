package com.psychiatrygarden.activity.courselist.course;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.CourseEventBean;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseManagerActivity extends BaseActivity implements CourseCalalogDownLoadAdapter.DeleteDataIml {
    private CheckBox checkeddown;
    private CompositeDisposable compositeDisposable;
    private TextView downTxt;
    private CourseCalalogDownLoadAdapter mCourseCalalogueListAdapters;
    private List<CourseCalalogueBean.DataNewBean.DataBean> videoDownBeans = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.courselist.course.CourseManagerActivity$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
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

    private boolean hasAdded(AliyunDownloadMediaInfo info, List<AliyunDownloadMediaInfo> listsItem) {
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : listsItem) {
            if (info.getFormat().equals(aliyunDownloadMediaInfo.getFormat()) && info.getQuality().equals(aliyunDownloadMediaInfo.getQuality()) && info.getVid().equals(aliyunDownloadMediaInfo.getVid()) && info.isEncripted() == aliyunDownloadMediaInfo.isEncripted()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDownList$9(String str, String str2, String str3, ObservableEmitter observableEmitter) throws Exception {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.videoDownBeans.size(); i2++) {
            if (this.videoDownBeans.get(i2).getCourseList() != null && this.videoDownBeans.get(i2).getCourseList().size() > 0) {
                for (int i3 = 0; i3 < this.videoDownBeans.get(i2).getCourseList().size(); i3++) {
                    if (5 != this.videoDownBeans.get(i2).getCourseList().get(i3).getmStatus()) {
                        arrayList.add(this.videoDownBeans.get(i2).getCourseList().get(i3));
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(3);
                    }
                }
            }
        }
        if (arrayList.size() <= 0) {
            observableEmitter.onNext(Boolean.FALSE);
            return;
        }
        String[] strArr = new String[arrayList.size()];
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            if (!hasVidData(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getVid(), ProjectApp.vids).booleanValue()) {
                ProjectApp.vids.add(new VideoDownTempBean(((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getVid(), str, str2, str3));
            }
            strArr[i4] = ((CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) arrayList.get(i4)).getVid();
        }
        ProjectApp.database.getVideoDownDao().updataModelStatus(3, strArr);
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        if ("全部开始".equals(this.downTxt.getText().toString())) {
            getCourseAk();
        } else {
            putPauseData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(CompoundButton compoundButton, boolean z2) {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.videoDownBeans.size(); i2++) {
            if (z2) {
                this.checkeddown.setText("取消全选");
                this.videoDownBeans.get(i2).setIsSelected(1);
                if (this.videoDownBeans.get(i2).getCourseList() != null && this.videoDownBeans.get(i2).getCourseList().size() > 0) {
                    for (int i3 = 0; i3 < this.videoDownBeans.get(i2).getCourseList().size(); i3++) {
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setIsSelected(1);
                    }
                }
            } else {
                this.checkeddown.setText("全选");
                this.videoDownBeans.get(i2).setIsSelected(0);
                if (this.videoDownBeans.get(i2).getCourseList() != null && this.videoDownBeans.get(i2).getCourseList().size() > 0) {
                    for (int i4 = 0; i4 < this.videoDownBeans.get(i2).getCourseList().size(); i4++) {
                        this.videoDownBeans.get(i2).getCourseList().get(i4).setIsSelected(0);
                    }
                }
            }
        }
        this.mCourseCalalogueListAdapters.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$5(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (this.videoDownBeans.get(i2).getCourseList() != null && this.videoDownBeans.get(i2).getCourseList().size() > 0) {
            if (this.videoDownBeans.get(i2).getCourseList().get(i3).getIsSelected() == 1) {
                this.videoDownBeans.get(i2).getCourseList().get(i3).setIsSelected(0);
            } else {
                this.videoDownBeans.get(i2).getCourseList().get(i3).setIsSelected(1);
            }
            setRefulData(i2);
            this.mCourseCalalogueListAdapters.notifyDataSetChanged();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$6(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ObservableEmitter observableEmitter) throws Exception {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.videoDownBeans.size(); i2++) {
            if (this.videoDownBeans.get(i2).getCourseList() != null && this.videoDownBeans.get(i2).getCourseList().size() > 0) {
                for (int i3 = 0; i3 < this.videoDownBeans.get(i2).getCourseList().size(); i3++) {
                    if (this.videoDownBeans.get(i2).getCourseList().get(i3).getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setProgress(aliyunDownloadMediaInfo.getProgress());
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setmSavePath(aliyunDownloadMediaInfo.getSavePath());
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setIsEncripted(aliyunDownloadMediaInfo.isEncripted());
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setmFormat(aliyunDownloadMediaInfo.getFormat());
                        this.videoDownBeans.get(i2).getCourseList().get(i3).setmQuality(aliyunDownloadMediaInfo.getQuality());
                        switch (AnonymousClass2.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                            case 1:
                            case 2:
                                this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(0);
                                break;
                            case 3:
                                this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(3);
                                break;
                            case 4:
                                this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(1);
                                break;
                            case 5:
                                this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(4);
                                break;
                            case 6:
                                this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(5);
                                this.videoDownBeans.get(i2).getCourseList().remove(i3);
                                break;
                            case 7:
                                this.videoDownBeans.get(i2).getCourseList().get(i3).setmStatus(2);
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
    public /* synthetic */ void lambda$mRefreshDownloadData$7(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            Iterator<CourseCalalogueBean.DataNewBean.DataBean> it = this.videoDownBeans.iterator();
            while (it.hasNext()) {
                CourseCalalogueBean.DataNewBean.DataBean next = it.next();
                if (next.getCourseList() == null || next.getCourseList().size() <= 0) {
                    it.remove();
                }
            }
            this.mCourseCalalogueListAdapters.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$8(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.m0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11874a.lambda$mRefreshDownloadData$6(aliyunDownloadMediaInfo, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.d0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11858c.lambda$mRefreshDownloadData$7((Boolean) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putPauseData$1(ObservableEmitter observableEmitter) throws Exception {
        List<VideoDownBean> videoDownLoadCourseInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadCourseInfo("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()), "" + getIntent().getExtras().getString("category_id"));
        ArrayList arrayList = new ArrayList();
        if (videoDownLoadCourseInfo != null && videoDownLoadCourseInfo.size() > 0) {
            for (int i2 = 0; i2 < videoDownLoadCourseInfo.size(); i2++) {
                if (5 != videoDownLoadCourseInfo.get(i2).getmStatus() && videoDownLoadCourseInfo.get(i2).getmStatus() != 0) {
                    videoDownLoadCourseInfo.get(i2).setmStatus(4);
                    arrayList.add(videoDownLoadCourseInfo.get(i2).getVid());
                    deleteVidsData(videoDownLoadCourseInfo.get(i2).getVid());
                }
            }
        }
        for (int i3 = 0; i3 < this.videoDownBeans.size(); i3++) {
            if (this.videoDownBeans.get(i3).getCourseList() != null && this.videoDownBeans.get(i3).getCourseList().size() > 0) {
                for (int i4 = 0; i4 < this.videoDownBeans.get(i3).getCourseList().size(); i4++) {
                    if (5 != this.videoDownBeans.get(i3).getCourseList().get(i4).getmStatus() && this.videoDownBeans.get(i3).getCourseList().get(i4).getmStatus() != 0) {
                        this.videoDownBeans.get(i3).getCourseList().get(i4).setmStatus(4);
                    }
                }
            }
        }
        if (arrayList.size() > 0) {
            String[] strArr = new String[arrayList.size()];
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                strArr[i5] = (String) arrayList.get(i5);
            }
            ProjectApp.database.getVideoDownDao().updataModelStatus(4, strArr);
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putPauseData$2(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.mCourseCalalogueListAdapters.notifyDataSetChanged();
            ToastUtil.shortToast(this, "已全部暂停");
            TextView textView = this.downTxt;
            if (textView != null) {
                textView.setText("全部开始");
            }
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.DeleteDataIml
    public void CoseAllData() {
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.DeleteDataIml
    public void deleteAllData(String vid) {
    }

    public void deleteVidsData(String vid) {
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        Iterator<VideoDownTempBean> it = ProjectApp.vids.iterator();
        while (it.hasNext()) {
            if (it.next().vid.equals(vid)) {
                it.remove();
                return;
            }
        }
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseManagerActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseManagerActivity.this.hideProgressDialog();
                ToastUtil.shortToast(CourseManagerActivity.this, "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CourseManagerActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        CourseManagerActivity.this.getDownList(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st")));
                    } else {
                        ToastUtil.shortToast(CourseManagerActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                CourseManagerActivity.this.hideProgressDialog();
            }
        });
    }

    public void getDownList(final String acId, final String akSceret, final String securityToken) {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.c0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11853a.lambda$getDownList$9(acId, akSceret, securityToken, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.e0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Boolean) obj).booleanValue();
            }
        }));
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
        this.checkeddown = (CheckBox) findViewById(R.id.checkeddown);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.exListView);
        ((TextView) findViewById(R.id.pauseTxt)).setVisibility(8);
        TextView textView = (TextView) findViewById(R.id.downTxt);
        this.downTxt = textView;
        textView.setText("全部暂停");
        this.checkeddown.setVisibility(8);
        this.downTxt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11865c.lambda$init$3(view);
            }
        });
        this.videoDownBeans = (List) getIntent().getExtras().getSerializable("data");
        CourseCalalogDownLoadAdapter courseCalalogDownLoadAdapter = new CourseCalalogDownLoadAdapter(this, this.videoDownBeans, 1, null);
        this.mCourseCalalogueListAdapters = courseCalalogDownLoadAdapter;
        expandableListView.setAdapter(courseCalalogDownLoadAdapter);
        this.checkeddown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.courselist.course.i0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f11867c.lambda$init$4(compoundButton, z2);
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.j0
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView2, View view, int i2, int i3, long j2) {
                return this.f11869a.lambda$init$5(expandableListView2, view, i2, i3, j2);
            }
        });
        mRefreshDownloadData();
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.course.l0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11872a.lambda$mRefreshDownloadData$8((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("下载中");
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("清空");
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseManagerActivity.lambda$onCreate$0(view);
            }
        });
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new CourseEventBean(this.videoDownBeans, "courseManager"));
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    public void putPauseData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.f0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11861a.lambda$putPauseData$1(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.g0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11863c.lambda$putPauseData$2((Boolean) obj);
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
        for (int i2 = 0; i2 < this.videoDownBeans.get(groupPosition).getCourseList().size(); i2++) {
            if (this.videoDownBeans.get(groupPosition).getCourseList().get(i2).getIsSelected() != 1) {
                this.videoDownBeans.get(groupPosition).setIsSelected(0);
                return;
            }
        }
        this.videoDownBeans.get(groupPosition).setIsSelected(1);
    }
}
