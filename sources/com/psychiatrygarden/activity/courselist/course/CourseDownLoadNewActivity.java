package com.psychiatrygarden.activity.courselist.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.activity.courselist.bean.CourseEventBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
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

/* loaded from: classes5.dex */
public class CourseDownLoadNewActivity extends BaseActivity implements CourseCalalogDownLoadAdapter.DeleteDataIml {
    private CheckBox checkeddown;
    private CompositeDisposable compositeDisposable;
    private TextView downTxt;
    private CourseCalalogDownLoadAdapter mCourseCalalogueListAdapters;
    private TextView pauseTxt;
    private RelativeLayout relbuttom;
    private List<CourseCalalogueBean.DataNewBean.DataBean> videoDownBeans = new ArrayList();
    private List<CourseCalalogueBean.DataNewBean.DataBean> dataCourseList = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.courselist.course.CourseDownLoadNewActivity$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
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
    public /* synthetic */ void lambda$getConData$10(List list) throws Exception {
        Intent intent = new Intent(this, (Class<?>) CourseManagerActivity.class);
        intent.putExtra("data", (Serializable) list);
        intent.putExtra("category_id", "" + getIntent().getExtras().getString("category_id"));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getConData$9(ObservableEmitter observableEmitter) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataCourseList.size(); i2++) {
            CourseCalalogueBean.DataNewBean.DataBean dataBean = (CourseCalalogueBean.DataNewBean.DataBean) this.dataCourseList.get(i2).clone();
            if (dataBean.getCourseList() != null && dataBean.getCourseList().size() > 0) {
                ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> arrayList2 = new ArrayList<>();
                for (int i3 = 0; i3 < dataBean.getCourseList().size(); i3++) {
                    if (5 != dataBean.getCourseList().get(i3).getmStatus()) {
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
    public /* synthetic */ void lambda$getConTrolData$8() {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            this.relbuttom.setVisibility(8);
        } else {
            this.relbuttom.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.videoDownBeans.size(); i2++) {
            if (this.checkeddown.isChecked()) {
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
        setDownTextColor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(CompoundButton compoundButton, boolean z2) {
        if (z2) {
            this.checkeddown.setText("取消全选");
        } else {
            this.checkeddown.setText("全选");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$4(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (this.videoDownBeans.get(i2).getCourseList() != null && this.videoDownBeans.get(i2).getCourseList().size() > 0) {
            if (this.videoDownBeans.get(i2).getCourseList().get(i3).getIsSelected() == 1) {
                this.videoDownBeans.get(i2).getCourseList().get(i3).setIsSelected(0);
            } else {
                this.videoDownBeans.get(i2).getCourseList().get(i3).setIsSelected(1);
            }
            setRefulData(i2);
            setDownTextColor();
            this.mCourseCalalogueListAdapters.notifyDataSetChanged();
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
                    if (this.dataCourseList.get(i2).getCourseList().get(i3).getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                        this.dataCourseList.get(i2).getCourseList().get(i3).setProgress(aliyunDownloadMediaInfo.getProgress());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmSavePath(aliyunDownloadMediaInfo.getSavePath());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setIsEncripted(aliyunDownloadMediaInfo.isEncripted());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmFormat(aliyunDownloadMediaInfo.getFormat());
                        this.dataCourseList.get(i2).getCourseList().get(i3).setmQuality(aliyunDownloadMediaInfo.getQuality());
                        switch (AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
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
                                getConTrolData();
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
            this.mCourseCalalogueListAdapters.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$7(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.t
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11888a.lambda$mRefreshDownloadData$5(aliyunDownloadMediaInfo, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.u
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11891c.lambda$mRefreshDownloadData$6((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        getConData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$11(ObservableEmitter observableEmitter) throws Exception {
        List<VideoDownBean> videoDownLoadCourseInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadCourseInfo("course_" + SharePreferencesUtils.readStrConfig(CommonParameter.courseParentId, ProjectApp.instance()), "" + getIntent().getExtras().getString("category_id"));
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
                                }
                                this.dataCourseList.get(i3).getCourseList().get(i4).setIsEncripted(videoDownLoadCourseInfo.get(i2).getIsEncripted());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmFormat(videoDownLoadCourseInfo.get(i2).getmFormat());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmQuality(videoDownLoadCourseInfo.get(i2).getmQuality());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmStatus(videoDownLoadCourseInfo.get(i2).getmStatus());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setProgress(videoDownLoadCourseInfo.get(i2).getmProgress());
                                this.dataCourseList.get(i3).getCourseList().get(i4).setmSavePath(videoDownLoadCourseInfo.get(i2).getmSavePath());
                            }
                        }
                    }
                }
            }
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$12(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            getConTrolData();
            this.mCourseCalalogueListAdapters.notifyDataSetChanged();
        }
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.DeleteDataIml
    public void CoseAllData() {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.videoDownBeans.size(); i2++) {
            if (this.videoDownBeans.get(i2).getIsSelected() != 1) {
                setDownTextColor();
                this.checkeddown.setChecked(false);
                return;
            }
        }
        this.checkeddown.setChecked(true);
        setDownTextColor();
    }

    @Override // com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.DeleteDataIml
    public void deleteAllData(String vid) {
    }

    public void getConData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.r
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11884a.lambda$getConData$9(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.s
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11886c.lambda$getConData$10((List) obj);
            }
        }));
    }

    public void getConTrolData() {
        try {
            this.videoDownBeans.clear();
            for (int i2 = 0; i2 < this.dataCourseList.size(); i2++) {
                CourseCalalogueBean.DataNewBean.DataBean dataBean = (CourseCalalogueBean.DataNewBean.DataBean) this.dataCourseList.get(i2).clone();
                if (dataBean.getCourseList() != null && dataBean.getCourseList().size() > 0) {
                    ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> arrayList = new ArrayList<>();
                    for (int i3 = 0; i3 < dataBean.getCourseList().size(); i3++) {
                        if (5 == dataBean.getCourseList().get(i3).getmStatus()) {
                            CourseCalalogueBean.DataNewBean.DataBean.CourseListBean courseListBean = (CourseCalalogueBean.DataNewBean.DataBean.CourseListBean) dataBean.getCourseList().get(i3).clone();
                            courseListBean.setIsSelected(0);
                            arrayList.add(courseListBean);
                        }
                    }
                    if (arrayList.size() > 0) {
                        dataBean.setCourseList(arrayList);
                        dataBean.setIsSelected(0);
                        this.videoDownBeans.add(dataBean);
                    }
                }
            }
            runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.course.q
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11882c.lambda$getConTrolData$8();
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.checkeddown = (CheckBox) findViewById(R.id.checkeddown);
        this.relbuttom = (RelativeLayout) findViewById(R.id.relbuttom);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.exListView);
        TextView textView = (TextView) findViewById(R.id.pauseTxt);
        this.pauseTxt = textView;
        textView.setVisibility(8);
        TextView textView2 = (TextView) findViewById(R.id.downTxt);
        this.downTxt = textView2;
        textView2.setText("立即删除");
        this.downTxt.setTextColor(this.mContext.getResources().getColor(R.color.write_color_press));
        this.downTxt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseDownLoadNewActivity.lambda$init$1(view);
            }
        });
        this.dataCourseList = (List) getIntent().getExtras().getSerializable("data");
        CourseCalalogDownLoadAdapter courseCalalogDownLoadAdapter = new CourseCalalogDownLoadAdapter(this, this.videoDownBeans, this);
        this.mCourseCalalogueListAdapters = courseCalalogDownLoadAdapter;
        expandableListView.setAdapter(courseCalalogDownLoadAdapter);
        this.checkeddown.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11898c.lambda$init$2(view);
            }
        });
        this.checkeddown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.courselist.course.a0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f11848c.lambda$init$3(compoundButton, z2);
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.b0
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView2, View view, int i2, int i3, long j2) {
                return this.f11851a.lambda$init$4(expandableListView2, view, i2, i3, j2);
            }
        });
        getConTrolData();
        mRefreshDownloadData();
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.course.x
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11897a.lambda$mRefreshDownloadData$7((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("已下载");
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("下载中");
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11880c.lambda$onCreate$0(view);
            }
        });
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post("shuaxin");
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("shuaxin2".equals(str)) {
            putData();
        }
    }

    public void putData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.course.v
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11892a.lambda$putData$11(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.course.w
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11894c.lambda$putData$12((Boolean) obj);
            }
        }));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_download);
    }

    public void setDownTextColor() {
        List<CourseCalalogueBean.DataNewBean.DataBean> list = this.videoDownBeans;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.videoDownBeans.size(); i2++) {
            for (int i3 = 0; i3 < this.videoDownBeans.get(i2).getCourseList().size(); i3++) {
                if (this.videoDownBeans.get(i2).getCourseList().get(i3).getIsSelected() == 1) {
                    this.downTxt.setTextColor(this.mContext.getResources().getColor(R.color.white));
                    return;
                }
            }
        }
        this.downTxt.setTextColor(this.mContext.getResources().getColor(R.color.write_color_press));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setRefulData(int groupPosition) {
        for (int i2 = 0; i2 < this.videoDownBeans.get(groupPosition).getCourseList().size(); i2++) {
            if (this.videoDownBeans.get(groupPosition).getCourseList().get(i2).getIsSelected() != 1) {
                this.videoDownBeans.get(groupPosition).setIsSelected(0);
                this.checkeddown.setChecked(false);
                return;
            }
        }
        this.videoDownBeans.get(groupPosition).setIsSelected(1);
        for (int i3 = 0; i3 < this.videoDownBeans.size(); i3++) {
            if (this.videoDownBeans.get(i3).getIsSelected() != 1) {
                return;
            }
        }
        this.checkeddown.setChecked(true);
    }

    public void updateAllCourse() {
        Iterator<CourseCalalogueBean.DataNewBean.DataBean> it = this.dataCourseList.iterator();
        while (it.hasNext()) {
            ArrayList<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> courseList = it.next().getCourseList();
            if (courseList.size() > 0) {
                Iterator<CourseCalalogueBean.DataNewBean.DataBean.CourseListBean> it2 = courseList.iterator();
                while (it2.hasNext()) {
                    if (5 != it2.next().getmStatus()) {
                        it2.remove();
                    }
                }
            } else {
                it.remove();
            }
        }
    }

    public void onEventMainThread(CourseEventBean courseEventBean) {
        List<CourseCalalogueBean.DataNewBean.DataBean> list;
        if (!courseEventBean.evestr.equals("courseManager") || (list = courseEventBean.videoDownBeans) == null || list.size() > 0) {
            return;
        }
        updateAllCourse();
    }
}
