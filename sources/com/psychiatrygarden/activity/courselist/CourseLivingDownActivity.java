package com.psychiatrygarden.activity.courselist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.util.download.StorageUtil;
import com.aliyun.player.aliyunplayerbase.util.Formatter;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseLivingDownActivity extends BaseActivity {
    public CourseLivingAdapter adapter;
    public TextView cacheSizeTv;
    public TextView downTv;
    public TextView iconBack;
    public boolean isHasSelected;
    public String is_hide_teacher;
    public TextView managerTv;
    public RecyclerView recycledown;
    public CheckedTextView selectAllTv;
    public String title;
    public String course_id = "0";
    public String type = "";
    public String activity_id = "";
    public String cover = "";

    /* renamed from: com.psychiatrygarden.activity.courselist.CourseLivingDownActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list) {
            CourseLivingDownActivity.this.adapter.setList(list);
            CourseLivingDownActivity courseLivingDownActivity = CourseLivingDownActivity.this;
            if (courseLivingDownActivity.isHasSelected) {
                courseLivingDownActivity.downTv.setSelected(true);
                CourseLivingDownActivity.this.downTv.setClickable(true);
                CourseLivingDownActivity.this.isHasSelected = false;
            }
            CourseLivingDownActivity.this.showHideBtn();
            CourseLivingDownActivity.this.calculationCache();
            CourseLivingDownActivity.this.isCollespOrExpaend();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(final List list) {
            List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo("course_" + CourseLivingDownActivity.this.course_id);
            for (int i2 = 0; i2 < list.size(); i2++) {
                int iIsHadVideoStatus = CourseLivingDownActivity.this.isHadVideoStatus(videoDownLoadInfo, ((CurriculumLiveBean.DataDTO) list.get(i2)).getVid());
                if (iIsHadVideoStatus == 5) {
                    ((CurriculumLiveBean.DataDTO) list.get(i2)).setIsSelected(0);
                } else if (iIsHadVideoStatus == -1) {
                    ((CurriculumLiveBean.DataDTO) list.get(i2)).setIsSelected(0);
                } else {
                    ((CurriculumLiveBean.DataDTO) list.get(i2)).setIsSelected(1);
                    CourseLivingDownActivity.this.isHasSelected = true;
                }
                ((CurriculumLiveBean.DataDTO) list.get(i2)).setStatus(iIsHadVideoStatus);
                ((CurriculumLiveBean.DataDTO) list.get(i2)).setSize("" + CourseLivingDownActivity.this.isHadVideoSize(videoDownLoadInfo, ((CurriculumLiveBean.DataDTO) list.get(i2)).getVid()));
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.n0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12125c.lambda$onSuccess$0(list);
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
            super.onSuccess((AnonymousClass2) t2);
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
                    CourseLivingDownActivity courseLivingDownActivity = CourseLivingDownActivity.this;
                    CheckedTextView checkedTextView = courseLivingDownActivity.selectAllTv;
                    if (checkedTextView != null && courseLivingDownActivity.downTv != null) {
                        checkedTextView.setText("全选");
                        CourseLivingDownActivity.this.selectAllTv.setChecked(false);
                        CourseLivingDownActivity.this.downTv.setSelected(false);
                        CourseLivingDownActivity.this.downTv.setClickable(false);
                    }
                    if (arrayList.isEmpty()) {
                        CourseLivingDownActivity.this.findViewById(R.id.ll_bottom_pannel).setVisibility(8);
                    } else {
                        CourseLivingDownActivity.this.findViewById(R.id.ll_bottom_pannel).setVisibility(0);
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.o0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f12132c.lambda$onSuccess$1(arrayList);
                            }
                        });
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.CourseLivingDownActivity$3, reason: invalid class name */
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

    public class CourseLivingAdapter extends BaseQuickAdapter<CurriculumLiveBean.DataDTO, BaseViewHolder> {
        public CourseLivingAdapter(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CurriculumLiveBean.DataDTO dataDTO, ImageView imageView, View view) {
            try {
                if (getData().size() > 0) {
                    if (dataDTO.getIsSelected() == 1) {
                        dataDTO.setIsSelected(0);
                        imageView.setSelected(false);
                    } else {
                        dataDTO.setIsSelected(1);
                        imageView.setSelected(true);
                    }
                    CourseLivingDownActivity.this.isCollespOrExpaend();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, final CurriculumLiveBean.DataDTO dataDTO) throws Resources.NotFoundException {
            String str;
            TextView textView = (TextView) baseViewHolder.findView(R.id.title);
            TextView textView2 = (TextView) baseViewHolder.findView(R.id.detailTxt);
            final ImageView imageView = (ImageView) baseViewHolder.findView(R.id.selectTv);
            textView.setText(dataDTO.getTitle());
            if ("1".equals(CourseLivingDownActivity.this.is_hide_teacher) || TextUtils.isEmpty(CourseLivingDownActivity.this.is_hide_teacher)) {
                str = "";
            } else {
                str = "" + dataDTO.getTeacher() + "&#8194;";
            }
            textView.setTextColor(Color.parseColor("#161616"));
            String str2 = str + "直播回放";
            if (dataDTO.getSize() != null && !"".equals(dataDTO.getSize()) && !"0".equals(dataDTO.getSize())) {
                String size = dataDTO.getSize();
                if (!TextUtils.isEmpty(size)) {
                    str2 = str2 + "&#8194;" + ((Long.parseLong(size) / 1024) / 1024) + "MB";
                }
            }
            switch (dataDTO.getStatus()) {
                case 1:
                    str2 = str2 + "&#8194;<font color='#B2575C'>下载中-" + dataDTO.getProcess() + "%</font>";
                    break;
                case 2:
                    str2 = str2 + "&#8194;<font color='#A8A7A7'>请重试</font>";
                    break;
                case 3:
                    str2 = str2 + "&#8194;<font color='#A8A7A7'>队列中</font>";
                    break;
                case 4:
                    str2 = str2 + "&#8194;<font color='#A8A7A7'>暂停中</font>";
                    break;
                case 5:
                    str2 = str2 + "&#8194;<font color='#A8A7A7'>已下载</font>";
                    break;
                case 6:
                    str2 = str2 + "&#8194;<font color='#A8A7A7'>本地文件不存在</font>";
                    break;
            }
            textView2.setText(Html.fromHtml(str2));
            if (dataDTO.getStatus() == 5) {
                imageView.setVisibility(8);
                CommonUtil.mDoDrawable(CourseLivingDownActivity.this, textView, R.drawable.doneimg, 0);
            } else {
                imageView.setVisibility(0);
                textView.setCompoundDrawables(null, null, null, null);
            }
            imageView.setSelected(dataDTO.getIsSelected() == 1);
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.p0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12139c.lambda$convert$0(dataDTO, imageView, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculationCache() {
        Iterator it = ((ArrayList) YkBManager.getInstance().mDownloadMediaLists).iterator();
        long size = 0;
        while (it.hasNext()) {
            size += ((AliyunDownloadMediaInfo) it.next()).getSize();
        }
        this.cacheSizeTv.setText(String.format(getResources().getString(R.string.alivc_player_video_cache_storage_tips), Formatter.getFileSizeDescription(size), Formatter.getFileSizeDescription(StorageUtil.getAvailableExternalMemorySize() * 1024)));
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
        ajaxParams.put("course_id", "" + this.course_id);
        String str = NetworkRequestsURL.curriculumdetailUrl;
        ajaxParams.put("type", "" + this.type);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isCollespOrExpaend() {
        boolean z2;
        if (this.adapter == null || this.selectAllTv == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= this.adapter.getData().size()) {
                z2 = true;
                break;
            } else {
                if (this.adapter.getData().get(i2).getIsSelected() != 1) {
                    z2 = false;
                    break;
                }
                i2++;
            }
        }
        if (z2) {
            this.selectAllTv.setChecked(true);
            this.selectAllTv.setText("取消全选");
        } else {
            this.selectAllTv.setChecked(false);
            this.selectAllTv.setText("全选");
        }
        downTextState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$10(String str, String str2, String str3) {
        this.adapter.notifyDataSetChanged();
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(ProjectApp.vids.get(0).vid);
        if (aliyunDownloadMediaInfoHasAliyunVideo == null || ProjectApp.downloadManager.getmVidSts() == null) {
            CommonUtil.initDownAliyunVideo(ProjectApp.vids.get(0).vid, str, str2, str3);
            return;
        }
        if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() == AliyunDownloadMediaInfo.Status.Error) {
            aliyunDownloadMediaInfoHasAliyunVideo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
        }
        if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() != AliyunDownloadMediaInfo.Status.Start) {
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoHasAliyunVideo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$11(final String str, final String str2, final String str3) {
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
                    videoDownBean.setChapter_id("" + ((CurriculumLiveBean.DataDTO) arrayList2.get(i3)).getId());
                    videoDownBean.setParent_id("" + (Integer.parseInt(this.course_id) * 8));
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
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.b0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11835c.lambda$downloadData$10(str, str2, str3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        startActivity(new Intent(this, (Class<?>) CurriculumDownLoadManagerActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        CourseLivingAdapter courseLivingAdapter = this.adapter;
        if (courseLivingAdapter == null || courseLivingAdapter.getData().isEmpty()) {
            return;
        }
        if (this.selectAllTv.isChecked()) {
            collOrExpend(0);
            this.selectAllTv.setText("全选");
            this.selectAllTv.setChecked(false);
            this.downTv.setSelected(false);
            this.downTv.setClickable(false);
            return;
        }
        collOrExpend(1);
        this.selectAllTv.setText("取消全选");
        this.selectAllTv.setChecked(true);
        this.downTv.setSelected(true);
        this.downTv.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        if (ContextCompat.checkSelfPermission(this.mContext, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.k0
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12110a.lambda$init$3();
                }
            })).show();
        } else {
            show4gDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$9(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        try {
            if (this.adapter == null || aliyunDownloadMediaInfo == null) {
                return;
            }
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if (aliyunDownloadMediaInfo.getVid().equals(this.adapter.getData().get(i2).getVid() + "")) {
                    this.adapter.getData().get(i2).setProcess(aliyunDownloadMediaInfo.getProgress() + "");
                    this.adapter.getData().get(i2).setSize(aliyunDownloadMediaInfo.getSize() + "");
                    switch (AnonymousClass3.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                        case 1:
                        case 2:
                            this.adapter.getData().get(i2).setStatus(0);
                            break;
                        case 3:
                            this.adapter.getData().get(i2).setStatus(3);
                            break;
                        case 4:
                            this.adapter.getData().get(i2).setStatus(1);
                            break;
                        case 5:
                            this.adapter.getData().get(i2).setStatus(4);
                            break;
                        case 6:
                            this.adapter.getData().get(i2).setStatus(5);
                            this.adapter.getData().get(i2).setIsSelected(0);
                            break;
                        case 7:
                            this.adapter.getData().get(i2).setStatus(2);
                            break;
                    }
                    if (aliyunDownloadMediaInfo.getProgress() == 100) {
                        this.adapter.getData().get(i2).setStatus(5);
                        this.adapter.getData().get(i2).setIsSelected(0);
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
            calculationCache();
            showHideBtn();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show4gDialog$5() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
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
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.c0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f11843c.lambda$showNetChangeDialog$7(dialogInterface, i2);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.d0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CourseLivingDownActivity.lambda$showNetChangeDialog$8(dialogInterface, i2);
            }
        });
        builder.create();
        builder.show();
    }

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.courselist.l0
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f12114a.lambda$showOpenDownloadDialog$6();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    public void downTextState() {
        for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
            if (this.adapter.getData().get(i2).getIsSelected() == 1) {
                this.downTv.setSelected(true);
                this.downTv.setClickable(true);
                return;
            }
        }
        this.downTv.setSelected(false);
        this.downTv.setClickable(false);
    }

    public void downloadData(final String acId, final String akSceret, final String securityToken) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.j0
            @Override // java.lang.Runnable
            public final void run() {
                this.f12103c.lambda$downloadData$11(acId, akSceret, securityToken);
            }
        });
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CourseLivingDownActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                ToastUtil.shortToast(CourseLivingDownActivity.this, "获取视频信息失败！");
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
                        CourseLivingDownActivity.this.downloadData(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("st")));
                    } else {
                        ToastUtil.shortToast(CourseLivingDownActivity.this, jSONObject.optString("message"));
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
        this.selectAllTv = (CheckedTextView) findViewById(R.id.selectAllTv);
        this.cacheSizeTv = (TextView) findViewById(R.id.cacheSizeTv);
        this.iconBack = (TextView) findViewById(R.id.iconBack);
        this.managerTv = (TextView) findViewById(R.id.managerTv);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycledown);
        this.recycledown = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.iconBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11905c.lambda$init$0(view);
            }
        });
        this.managerTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11908c.lambda$init$1(view);
            }
        });
        CourseLivingAdapter courseLivingAdapter = new CourseLivingAdapter(R.layout.layout_course_living_down);
        this.adapter = courseLivingAdapter;
        this.recycledown.setAdapter(courseLivingAdapter);
        this.selectAllTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12086c.lambda$init$2(view);
            }
        });
        this.downTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12092c.lambda$init$4(view);
            }
        });
        mRefreshDownloadData();
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
            if (vid.equals(videoDownBean.getVid() + "")) {
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

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.m0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f12118a.lambda$mRefreshDownloadData$9((AliyunDownloadMediaInfo) obj);
            }
        });
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        getLiveListData();
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
        setContentView(R.layout.activity_course_living_down);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void show4gDialog() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.i0
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12097a.lambda$show4gDialog$5();
                }
            })).show();
            return;
        }
        if (!CommonUtil.isNetworkConnected(this.mContext)) {
            NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
        } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
            getCourseAk();
        } else {
            showOpenDownloadDialog();
        }
    }

    public void showHideBtn() {
        boolean z2;
        if (this.adapter != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.adapter.getData().size()) {
                    z2 = true;
                    break;
                } else {
                    if (this.adapter.getData().get(i2).getStatus() != 5) {
                        z2 = false;
                        break;
                    }
                    i2++;
                }
            }
            if (z2) {
                this.selectAllTv.setVisibility(8);
                this.downTv.setVisibility(8);
            } else {
                this.selectAllTv.setVisibility(0);
                this.downTv.setVisibility(0);
            }
        }
    }
}
