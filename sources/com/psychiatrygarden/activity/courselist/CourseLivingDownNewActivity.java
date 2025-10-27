package com.psychiatrygarden.activity.courselist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumLiveBean;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseLivingDownNewActivity extends BaseActivity {
    public BaseQuickAdapter<CurriculumLiveBean.DataDTO, BaseViewHolder> adapter;
    public TextView downTv;
    public ImageView iconBack;
    public boolean isHasSelected;
    public String is_hide_teacher;
    public RecyclerView rvList;
    private boolean selectAll;
    public TextView selectAllTv;
    public String title;
    public TextView tvTitle;
    public String course_id = "0";
    public String type = "";
    public String activity_id = "";
    public String cover = "";

    /* renamed from: com.psychiatrygarden.activity.courselist.CourseLivingDownNewActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list) {
            CourseLivingDownNewActivity.this.adapter.setList(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(final List list) {
            List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo("course_" + CourseLivingDownNewActivity.this.course_id);
            for (int i2 = 0; i2 < list.size(); i2++) {
                int iIsHadVideoStatus = CourseLivingDownNewActivity.this.isHadVideoStatus(videoDownLoadInfo, ((CurriculumLiveBean.DataDTO) list.get(i2)).getVid());
                if (iIsHadVideoStatus == 5) {
                    ((CurriculumLiveBean.DataDTO) list.get(i2)).setIsSelected(0);
                } else if (iIsHadVideoStatus == -1) {
                    ((CurriculumLiveBean.DataDTO) list.get(i2)).setIsSelected(0);
                } else {
                    ((CurriculumLiveBean.DataDTO) list.get(i2)).setIsSelected(1);
                    CourseLivingDownNewActivity.this.isHasSelected = true;
                }
                ((CurriculumLiveBean.DataDTO) list.get(i2)).setStatus(iIsHadVideoStatus);
                ((CurriculumLiveBean.DataDTO) list.get(i2)).setSize(String.valueOf(CourseLivingDownNewActivity.this.isHadVideoSize(videoDownLoadInfo, ((CurriculumLiveBean.DataDTO) list.get(i2)).getVid())));
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.c1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11844c.lambda$onSuccess$0(list);
                }
            });
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass3) t2);
            try {
                CurriculumLiveBean curriculumLiveBean = (CurriculumLiveBean) new Gson().fromJson(t2, CurriculumLiveBean.class);
                if (curriculumLiveBean.getCode().equals("200")) {
                    final ArrayList arrayList = new ArrayList();
                    if (curriculumLiveBean.getData() != null) {
                        for (int i2 = 0; i2 < curriculumLiveBean.getData().size(); i2++) {
                            if (!TextUtils.isEmpty(curriculumLiveBean.getData().get(i2).getVid())) {
                                arrayList.add(curriculumLiveBean.getData().get(i2));
                            }
                        }
                    }
                    CourseLivingDownNewActivity courseLivingDownNewActivity = CourseLivingDownNewActivity.this;
                    TextView textView = courseLivingDownNewActivity.selectAllTv;
                    if (textView != null && courseLivingDownNewActivity.downTv != null) {
                        textView.setText("全选");
                        CourseLivingDownNewActivity.this.downTv.setSelected(false);
                        CourseLivingDownNewActivity.this.downTv.setEnabled(false);
                        CourseLivingDownNewActivity.this.downTv.setClickable(false);
                    }
                    if (arrayList.isEmpty()) {
                        CourseLivingDownNewActivity.this.findViewById(R.id.downTv).setVisibility(8);
                    } else {
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.b1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f11839c.lambda$onSuccess$1(arrayList);
                            }
                        });
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void collOrExpend(int status) {
        if (this.adapter != null) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                this.adapter.getData().get(i2).setIsSelected(status);
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    private void getLiveListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        String str = NetworkRequestsURL.curriculumdetailUrl;
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        ajaxParams.put("type", this.type);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AnonymousClass3());
    }

    private void itemClick(int position) {
        CurriculumLiveBean.DataDTO item = this.adapter.getItem(position);
        if (item.getStatus() == 5) {
            AlertToast("已下载");
            return;
        }
        if (hasAliyunVideo(item.getVid()) != null) {
            AlertToast("已在下载列表中");
            return;
        }
        item.setIsSelected((item.getIsSelected() == 1 ? 1 : 0) ^ 1);
        this.adapter.notifyItemChanged(position, 999);
        List<CurriculumLiveBean.DataDTO> data = this.adapter.getData();
        Iterator<CurriculumLiveBean.DataDTO> it = data.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 += it.next().getIsSelected() == 1 ? 1 : 0;
        }
        this.downTv.setEnabled(i2 > 0);
        this.downTv.setClickable(i2 > 0);
        boolean z2 = i2 == data.size();
        this.selectAll = z2;
        this.selectAllTv.setText(z2 ? "取消全选" : "全选");
        this.tvTitle.setText(String.format("已选择%d个文件", Integer.valueOf(i2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$10(final String str, final String str2, final String str3) {
        CourseCoverBean courseCoverBean = new CourseCoverBean();
        courseCoverBean.setCover(this.cover);
        courseCoverBean.setActivity_id(this.activity_id);
        courseCoverBean.setId(Integer.parseInt(this.course_id));
        courseCoverBean.setTitle(this.title);
        courseCoverBean.setSort(0);
        ProjectApp.database.getCourseCoverDao().insertTopic(courseCoverBean);
        if (this.adapter != null) {
            ArrayList arrayList = new ArrayList();
            CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
            courseDirectoryBean.setId(Integer.parseInt(this.course_id) * 8);
            courseDirectoryBean.setPid(this.course_id);
            courseDirectoryBean.setSort(0);
            courseDirectoryBean.setTitle(this.title);
            arrayList.add(courseDirectoryBean);
            ProjectApp.database.getCourseDirectoryDao().insertTopicList(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if (this.adapter.getData().get(i2).getIsSelected() == 1 && this.adapter.getData().get(i2).getStatus() != 5 && !"".equals(this.adapter.getData().get(i2).getVid())) {
                    arrayList2.add(this.adapter.getData().get(i2));
                }
            }
            if (!arrayList2.isEmpty()) {
                VideoDownBean[] videoDownBeanArr = new VideoDownBean[arrayList2.size()];
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    if (!hasVidData(((CurriculumLiveBean.DataDTO) arrayList2.get(i3)).getVid(), ProjectApp.vids).booleanValue()) {
                        ProjectApp.vids.add(new VideoDownTempBean(((CurriculumLiveBean.DataDTO) arrayList2.get(i3)).getVid(), str, str2, str3));
                    }
                    VideoDownBean videoDownBean = new VideoDownBean();
                    videoDownBean.setcId("course_" + this.course_id);
                    videoDownBean.setChapter_id(((CurriculumLiveBean.DataDTO) arrayList2.get(i3)).getId());
                    videoDownBean.setParent_id(String.valueOf(Integer.parseInt(this.course_id) * 8));
                    videoDownBean.setVid(((CurriculumLiveBean.DataDTO) arrayList2.get(i3)).getVid());
                    videoDownBean.setmTitle(((CurriculumLiveBean.DataDTO) arrayList2.get(i3)).getTitle());
                    videoDownBean.setmSize(0L);
                    videoDownBean.setSort(i3);
                    videoDownBean.setmStatus(3);
                    videoDownBean.setmFormat(AliPlayUtils.getDownloadVideoDefinition(this));
                    videoDownBeanArr[i3] = videoDownBean;
                }
                ProjectApp.database.getVideoDownDao().insertTopicList(videoDownBeanArr);
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.t0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12168c.lambda$downloadData$9(str, str2, str3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$9(String str, String str2, String str3) {
        this.adapter.notifyDataSetChanged();
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(ProjectApp.vids.get(0).vid);
        if (aliyunDownloadMediaInfoHasAliyunVideo == null || ProjectApp.downloadManager.getmVidSts() == null) {
            CommonUtil.initDownAliyunVideo(ProjectApp.vids.get(0).vid, str, str2, str3);
        } else {
            if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() == AliyunDownloadMediaInfo.Status.Error) {
                aliyunDownloadMediaInfoHasAliyunVideo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
            }
            if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() != AliyunDownloadMediaInfo.Status.Start) {
                ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoHasAliyunVideo);
            }
        }
        AlertToast("已加入下载列表");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        itemClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        itemClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        BaseQuickAdapter<CurriculumLiveBean.DataDTO, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter == null || baseQuickAdapter.getData().isEmpty()) {
            return;
        }
        boolean z2 = !this.selectAll;
        this.selectAll = z2;
        if (z2) {
            collOrExpend(1);
            this.selectAllTv.setText("取消全选");
            this.downTv.setSelected(true);
            this.downTv.setClickable(true);
            this.downTv.setEnabled(true);
            return;
        }
        collOrExpend(0);
        this.selectAllTv.setText("全选");
        this.downTv.setSelected(false);
        this.downTv.setClickable(false);
        this.downTv.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        if (ContextCompat.checkSelfPermission(this.mContext, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.w0
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12191a.lambda$init$4();
                }
            })).show();
        } else {
            show4gDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$7(DialogInterface dialogInterface, int i2) {
        getCourseAk();
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showNetChangeDialog$8(DialogInterface dialogInterface, int i2) {
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$6() {
        if (isLogin()) {
            goActivity(DownloadSetting.class);
        }
    }

    private void showNetChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle("网络切换为4G");
        builder.setMessage("是否继续下载？");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.u0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f12175c.lambda$showNetChangeDialog$7(dialogInterface, i2);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.v0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CourseLivingDownNewActivity.lambda$showNetChangeDialog$8(dialogInterface, i2);
            }
        });
        builder.create();
        builder.show();
    }

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.courselist.q0
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f12151a.lambda$showOpenDownloadDialog$6();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    public void downloadData(final String acId, final String akSceret, final String securityToken) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.s0
            @Override // java.lang.Runnable
            public final void run() {
                this.f12162c.lambda$downloadData$10(acId, akSceret, securityToken);
            }
        });
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CourseLivingDownNewActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                ToastUtil.shortToast(CourseLivingDownNewActivity.this, "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        CourseLivingDownNewActivity.this.downloadData(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("st")));
                    } else {
                        ToastUtil.shortToast(CourseLivingDownNewActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ProjectApp.instance().hideDialogWindow();
            }
        });
    }

    public AliyunDownloadMediaInfo hasAliyunVideo(String vid) {
        if (!TextUtils.isEmpty(vid) && YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
                if (vid.equals(aliyunDownloadMediaInfo.getVid())) {
                    return aliyunDownloadMediaInfo;
                }
            }
        }
        return null;
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
        this.is_hide_teacher = getIntent().getExtras().getString("is_hide_teacher");
        this.title = getIntent().getExtras().getString("title");
        this.cover = getIntent().getExtras().getString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
        this.course_id = getIntent().getExtras().getString("course_id", "0");
        this.type = getIntent().getExtras().getString("type");
        this.activity_id = getIntent().getExtras().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
        this.downTv = (TextView) findViewById(R.id.downTv);
        this.selectAllTv = (TextView) findViewById(R.id.selectAllTv);
        this.iconBack = (ImageView) findViewById(R.id.iconBack);
        this.rvList = (RecyclerView) findViewById(R.id.recycledown);
        this.tvTitle = (TextView) findViewById(R.id.title);
        this.rvList.setLayoutManager(new LinearLayoutManager(this));
        this.iconBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.x0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12205c.lambda$init$0(view);
            }
        });
        BaseQuickAdapter<CurriculumLiveBean.DataDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CurriculumLiveBean.DataDTO, BaseViewHolder>(R.layout.layout_course_living_down_new) { // from class: com.psychiatrygarden.activity.courselist.CourseLivingDownNewActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, CurriculumLiveBean.DataDTO item, @NonNull List payloads) {
                convert2(holder, item, (List<?>) payloads);
            }

            /* renamed from: convert, reason: avoid collision after fix types in other method */
            public void convert2(@NonNull BaseViewHolder holder, CurriculumLiveBean.DataDTO item, @NonNull List<?> payloads) {
                super.convert((AnonymousClass1) holder, (BaseViewHolder) item, (List<? extends Object>) payloads);
                ((ImageView) holder.getView(R.id.selectTv)).setSelected(item.getIsSelected() == 1);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, CurriculumLiveBean.DataDTO dataDTO) {
                ImageView imageView = (ImageView) holder.getView(R.id.selectTv);
                ((TextView) holder.getView(R.id.title)).setText(dataDTO.getTitle());
                imageView.setSelected(dataDTO.getIsSelected() == 1);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.addChildClickViewIds(R.id.selectTv);
        this.adapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.y0
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12210c.lambda$init$1(baseQuickAdapter2, view, i2);
            }
        });
        this.rvList.setAdapter(this.adapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.courselist.z0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12214c.lambda$init$2(baseQuickAdapter2, view, i2);
            }
        });
        this.selectAllTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.a1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11832c.lambda$init$3(view);
            }
        });
        this.downTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12156c.lambda$init$5(view);
            }
        });
        getLiveListData();
    }

    public long isHadVideoSize(List<VideoDownBean> videoDownBeans, String vid) {
        if (videoDownBeans == null || videoDownBeans.size() <= 0) {
            return 0L;
        }
        for (VideoDownBean videoDownBean : videoDownBeans) {
            if (vid.equals(videoDownBean.getVid() + "")) {
                return videoDownBean.getmSize();
            }
        }
        return 0L;
    }

    public int isHadVideoStatus(List<VideoDownBean> videoDownBeans, String vid) {
        if (videoDownBeans == null || videoDownBeans.size() <= 0) {
            return -1;
        }
        for (VideoDownBean videoDownBean : videoDownBeans) {
            if (vid.equals(videoDownBean.getVid())) {
                List<VideoDownTempBean> list = ProjectApp.vids;
                if (list != null && list.size() > 0) {
                    return videoDownBean.getmStatus();
                }
                if (videoDownBean.getmStatus() == 5) {
                    return !new File(videoDownBean.getmSavePath()).exists() ? 6 : 5;
                }
                return 4;
            }
        }
        return -1;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initwriteStatusBar();
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "无法下载，请检查app存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_living_down_new);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void show4gDialog() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if (!CommonUtil.isNetworkConnected(context)) {
            NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
        } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
            getCourseAk();
        } else {
            showOpenDownloadDialog();
        }
    }
}
