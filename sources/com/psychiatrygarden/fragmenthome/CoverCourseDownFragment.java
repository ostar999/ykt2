package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.util.download.SimpleAliyunDownloadInfoListener;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.source.VidSts;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.SetNewAvtivity;
import com.psychiatrygarden.activity.courselist.CurriculumDownLoadManageActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.adapter.CourseCoverDownloadAdapter;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.DbManager;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseAkBean;
import com.psychiatrygarden.bean.VidteachingBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.RefreshCourseDownloadStateEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CoverCourseDownFragment extends BaseFragment implements OnItemChildClickListener {
    private DbManager db;
    private CourseCoverDownloadAdapter mAdapter;
    private CustomEmptyView mEmptyView;
    private Handler mHandler;
    private final List<Integer> completeList = new ArrayList();
    private final List<CourseCoverBean> completeItems = new ArrayList();
    private final ArrayMap<String, List<String>> course2VidMap = new ArrayMap<>();
    public List<CourseCoverBean> courseCoverBeans = new ArrayList();
    private final ArrayMap<String, VideoDownBean> courseVideoDownMap = new ArrayMap<>();
    private final ArrayMap<String, List<VideoDownBean>> courseVideoListMap = new ArrayMap<>();
    private final List<String> allVids = new ArrayList();
    private final Map<String, String> courseCoverMap = new ArrayMap();
    private final HandlerThread mHandlerThread = new HandlerThread("UpdateDataBase");
    private int clickPosition = -1;
    private long currentMills = 0;

    /* renamed from: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFailure$0() {
            CoverCourseDownFragment.this.initDataList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$3(VidteachingBean vidteachingBean) {
            if (!vidteachingBean.getCode().equals("200")) {
                final CoverCourseDownFragment coverCourseDownFragment = CoverCourseDownFragment.this;
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.y4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CoverCourseDownFragment.access$300(coverCourseDownFragment);
                    }
                });
                return;
            }
            List<VidteachingBean.DataDTO> data = vidteachingBean.getData();
            if (data == null || data.size() <= 0) {
                final CoverCourseDownFragment coverCourseDownFragment2 = CoverCourseDownFragment.this;
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.x4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CoverCourseDownFragment.access$300(coverCourseDownFragment2);
                    }
                });
                return;
            }
            for (int i2 = 0; i2 < data.size(); i2++) {
                VidteachingBean.DataDTO dataDTO = data.get(i2);
                String id = dataDTO.getId();
                if (!TextUtils.isEmpty(id) && id.matches(RegexPool.NUMBERS)) {
                    CourseCoverBean courseCoverBean = new CourseCoverBean();
                    courseCoverBean.setId(Integer.parseInt(id));
                    courseCoverBean.setTitle(dataDTO.getTitle());
                    courseCoverBean.setCover(dataDTO.getCover_img());
                    courseCoverBean.setActivity_id(dataDTO.getActivity_id());
                    courseCoverBean.setSort(i2);
                    CoverCourseDownFragment.this.db.getCourseCoverDao().insertTopic(courseCoverBean);
                    ArrayList arrayList = new ArrayList();
                    CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
                    int i3 = Integer.parseInt(id) * 8;
                    courseDirectoryBean.setId(i3);
                    courseDirectoryBean.setPid(id);
                    courseDirectoryBean.setSort(0);
                    courseDirectoryBean.setTitle(dataDTO.getTitle());
                    arrayList.add(courseDirectoryBean);
                    CoverCourseDownFragment.this.db.getCourseDirectoryDao().insertTopicList(arrayList);
                    List<VideoDownBean> videoDownLoadInfo = CoverCourseDownFragment.this.db.getVideoDownDao().getVideoDownLoadInfo(id);
                    if (videoDownLoadInfo != null && videoDownLoadInfo.size() > 0) {
                        for (VideoDownBean videoDownBean : videoDownLoadInfo) {
                            String strValueOf = String.valueOf(i3);
                            videoDownBean.parent_id = strValueOf;
                            videoDownBean.hasPermission = "1";
                            videoDownBean.videoType = 1;
                            videoDownBean.chapter_id = strValueOf;
                            videoDownBean.cId = "course_" + id;
                            CoverCourseDownFragment.this.db.getVideoDownDao().deleteData(videoDownBean.vid);
                            CoverCourseDownFragment.this.db.getVideoDownDao().insertTopicList(videoDownBean);
                        }
                    }
                }
            }
            CoverCourseDownFragment.this.initDataList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$4() {
            CoverCourseDownFragment.this.initDataList();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.w4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16090c.lambda$onFailure$0();
                }
            });
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                final VidteachingBean vidteachingBean = (VidteachingBean) new Gson().fromJson(s2, VidteachingBean.class);
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.u4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f16033c.lambda$onSuccess$3(vidteachingBean);
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.v4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f16062c.lambda$onSuccess$4();
                    }
                });
            }
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2) {
            CoverCourseDownFragment.this.mAdapter.notifyItemChanged(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(String str, String str2, String str3) {
            CoverCourseDownFragment.this.startDownload(str, str2, str3);
            List<T> data = CoverCourseDownFragment.this.mAdapter.getData();
            for (final int i2 = 0; i2 < data.size(); i2++) {
                CourseCoverBean courseCoverBean = (CourseCoverBean) data.get(i2);
                if (courseCoverBean.isHeader() && courseCoverBean.getType() == 0 && courseCoverBean.getType() == 0) {
                    courseCoverBean.setState(0);
                    courseCoverBean.setCount(CoverCourseDownFragment.this.calculateDownloadingCount());
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.z4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16158c.lambda$onSuccess$0(i2);
                        }
                    });
                    return;
                }
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ToastUtil.shortToast(CoverCourseDownFragment.this.getActivity(), "获取视频信息失败！");
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
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (jSONObjectOptJSONObject == null) {
                        return;
                    }
                    CourseAkBean courseAkBean = (CourseAkBean) new Gson().fromJson(jSONObjectOptJSONObject.toString(), CourseAkBean.class);
                    final String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, courseAkBean.getAkId());
                    final String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, courseAkBean.getAkSecret());
                    final String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, courseAkBean.getSt());
                    ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.a5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15432c.lambda$onSuccess$1(strDecode, strDecode2, strDecode3);
                        }
                    });
                } else {
                    ToastUtil.shortToast(CoverCourseDownFragment.this.getActivity(), jSONObject.optString("message"));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
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

    public static /* synthetic */ void access$300(CoverCourseDownFragment coverCourseDownFragment) {
        coverCourseDownFragment.initDataList();
    }

    private int calculateCompleteCount() {
        Iterator<Map.Entry<String, List<String>>> it = this.course2VidMap.entrySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (isAllDownloadComplete(it.next().getValue())) {
                i2++;
            }
        }
        return i2;
    }

    private int calculateCurrentDownloadCount(List<String> vidList) {
        Iterator<String> it = vidList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(it.next());
            if (aliyunDownloadMediaInfoIsInAliDownloadList != null && (aliyunDownloadMediaInfoIsInAliDownloadList.getProgress() == 100 || aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() == AliyunDownloadMediaInfo.Status.Complete)) {
                i2++;
            }
        }
        if (i2 == 0) {
            return 1;
        }
        int i3 = i2 + 1;
        return i3 < vidList.size() ? i3 : i2;
    }

    private long calculateDownloadSize(List<String> vidList) {
        int progress;
        Iterator<String> it = vidList.iterator();
        long size = 0;
        while (it.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(it.next());
            if (aliyunDownloadMediaInfoIsInAliDownloadList != null && (progress = aliyunDownloadMediaInfoIsInAliDownloadList.getProgress()) > 0) {
                size = (long) (size + (aliyunDownloadMediaInfoIsInAliDownloadList.getSize() * (progress / 100.0f)));
            }
        }
        return size;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int calculateDownloadingCount() {
        Iterator<Map.Entry<String, List<String>>> it = this.course2VidMap.entrySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (!isAllDownloadComplete(it.next().getValue())) {
                i2++;
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDownload() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final List<T> data = this.mAdapter.getData();
        ListIterator listIterator = data.listIterator();
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
            if (aliyunDownloadMediaInfo != null && (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete || aliyunDownloadMediaInfo.getProgress() == 100)) {
                VideoDownBean videoDownBean = ProjectApp.database.getVideoDownDao().getVideoDownBean(aliyunDownloadMediaInfo.getVid());
                if (videoDownBean != null) {
                    String str = videoDownBean.getcId();
                    if (!TextUtils.isEmpty(str)) {
                        String strReplace = str.replace("course_", "");
                        if (!arrayList2.contains(strReplace)) {
                            arrayList2.add(strReplace);
                        }
                    }
                    this.db.getVideoDownDao().deleteData(aliyunDownloadMediaInfo.getVid());
                    LogUtils.d("DELETE", "database 删除数据库记录 => title = " + videoDownBean.mTitle);
                }
                arrayList.add(aliyunDownloadMediaInfo.getVid());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList((String) it.next());
            if (aliyunDownloadMediaInfoIsInAliDownloadList != null) {
                ProjectApp.downloadManager.deleteFile(aliyunDownloadMediaInfoIsInAliDownloadList);
                LogUtils.d("DELETE", "删除视频文件 => title = " + aliyunDownloadMediaInfoIsInAliDownloadList.getTitle());
            }
        }
        while (listIterator.hasNext()) {
            CourseCoverBean courseCoverBean = (CourseCoverBean) listIterator.next();
            if (courseCoverBean.isAllComplete()) {
                listIterator.remove();
                this.course2VidMap.remove(String.valueOf(courseCoverBean.id));
                this.courseVideoListMap.remove(String.valueOf(courseCoverBean.id));
                this.db.getCourseCoverDao().deleSignleData(String.valueOf(courseCoverBean.id));
                LogUtils.d("DELETE", "删除课程 =>" + courseCoverBean.title);
            }
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.w3
            @Override // java.lang.Runnable
            public final void run() {
                this.f16088c.lambda$deleteDownload$15(data);
            }
        });
    }

    private void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AnonymousClass4());
    }

    private void getCourseInfo() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment.2

            /* renamed from: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment$2$1, reason: invalid class name */
            public class AnonymousClass1 extends AjaxCallBack<String> {
                public AnonymousClass1() {
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    final CoverCourseDownFragment coverCourseDownFragment = CoverCourseDownFragment.this;
                    ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.p4
                        @Override // java.lang.Runnable
                        public final void run() {
                            CoverCourseDownFragment.access$300(coverCourseDownFragment);
                        }
                    });
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass1) s2);
                    try {
                        VidteachingBean vidteachingBean = (VidteachingBean) new Gson().fromJson(s2, VidteachingBean.class);
                        if (!TextUtils.equals(vidteachingBean.getCode(), "200")) {
                            final CoverCourseDownFragment coverCourseDownFragment = CoverCourseDownFragment.this;
                            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.s4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CoverCourseDownFragment.access$300(coverCourseDownFragment);
                                }
                            });
                            return;
                        }
                        List<VidteachingBean.DataDTO> data = vidteachingBean.getData();
                        if (data == null || data.size() <= 0) {
                            final CoverCourseDownFragment coverCourseDownFragment2 = CoverCourseDownFragment.this;
                            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.r4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CoverCourseDownFragment.access$300(coverCourseDownFragment2);
                                }
                            });
                            return;
                        }
                        CoverCourseDownFragment.this.courseCoverMap.clear();
                        for (VidteachingBean.DataDTO dataDTO : data) {
                            CoverCourseDownFragment.this.courseCoverMap.put(dataDTO.getId(), dataDTO.getCover_img());
                        }
                        final CoverCourseDownFragment coverCourseDownFragment3 = CoverCourseDownFragment.this;
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.q4
                            @Override // java.lang.Runnable
                            public final void run() {
                                CoverCourseDownFragment.access$300(coverCourseDownFragment3);
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        final CoverCourseDownFragment coverCourseDownFragment4 = CoverCourseDownFragment.this;
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.t4
                            @Override // java.lang.Runnable
                            public final void run() {
                                CoverCourseDownFragment.access$300(coverCourseDownFragment4);
                            }
                        });
                    }
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                List<CourseCoverBean> list = ProjectApp.database.getCourseCoverDao().getList();
                if (list == null || list.size() <= 0) {
                    CoverCourseDownFragment.this.initData();
                    return;
                }
                ArrayList arrayList = new ArrayList();
                Iterator<CourseCoverBean> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(String.valueOf(it.next().id));
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("course_id", new Gson().toJson(arrayList));
                YJYHttpUtils.get(((BaseFragment) CoverCourseDownFragment.this).mContext, NetworkRequestsURL.courseDetailInfo, ajaxParams, new AnonymousClass1());
            }
        });
    }

    private long getCourseTotalVideoSize(List<String> vidList) {
        long size;
        long j2 = 0;
        if (vidList != null && !vidList.isEmpty()) {
            for (String str : vidList) {
                AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(str);
                if (aliyunDownloadMediaInfoIsInAliDownloadList != null) {
                    size = aliyunDownloadMediaInfoIsInAliDownloadList.getSize();
                } else {
                    VideoDownBean videoDownBean = this.courseVideoDownMap.get(str);
                    if (videoDownBean != null) {
                        size = videoDownBean.getmSize();
                    }
                }
                j2 += size;
            }
        }
        return j2;
    }

    private List<Integer> getStatusList(List<String> vidList) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = vidList.iterator();
        while (it.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(it.next());
            if (aliyunDownloadMediaInfoIsInAliDownloadList != null) {
                switch (AnonymousClass5.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfoIsInAliDownloadList.getStatus().ordinal()]) {
                    case 1:
                    case 2:
                        arrayList.add(0);
                        continue;
                    case 3:
                        arrayList.add(3);
                        break;
                    case 5:
                        arrayList.add(4);
                        continue;
                    case 6:
                        arrayList.add(5);
                        continue;
                    case 7:
                        arrayList.add(2);
                        continue;
                }
                arrayList.add(1);
            } else {
                arrayList.add(3);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.a4
            @Override // java.lang.Runnable
            public final void run() {
                this.f15431c.lambda$initData$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0151  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initDataList() {
        /*
            Method dump skipped, instructions count: 814
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment.initDataList():void");
    }

    private boolean isAllDownloadComplete(List<String> vidList) {
        Iterator<String> it = vidList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(it.next());
            if (aliyunDownloadMediaInfoIsInAliDownloadList != null && (aliyunDownloadMediaInfoIsInAliDownloadList.getProgress() == 100 || aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() == AliyunDownloadMediaInfo.Status.Complete)) {
                i2++;
            }
        }
        return i2 == vidList.size() && i2 > 0;
    }

    private int isHadVideoStatus(VideoDownBean videoDownBean) {
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list != null && list.size() > 0) {
            return videoDownBean.getmStatus();
        }
        if (videoDownBean.getmStatus() != 5) {
            return 4;
        }
        if (new File(videoDownBean.getmSavePath()).exists()) {
            return 5;
        }
        return videoDownBean.getmStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDownload$15(List list) {
        int iCalculateDownloadingCount = calculateDownloadingCount();
        int iCalculateCompleteCount = calculateCompleteCount();
        if (iCalculateDownloadingCount <= 0 && iCalculateCompleteCount <= 0) {
            initData();
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            int type = ((CourseCoverBean) list.get(i3)).getType();
            if (type == 1) {
                ((CourseCoverBean) list.get(i3)).setCount(0);
            } else {
                if (type == 0) {
                }
            }
            i2++;
        }
        if (i2 != 2) {
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        this.mAdapter.setList(new ArrayList());
        this.mEmptyView.showEmptyView();
        this.mEmptyView.stopAnim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$5() {
        List<String> cids = this.db.getVideoDownDao().getCids();
        if (cids == null || cids.isEmpty()) {
            initDataList();
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("vidteaching_id", new Gson().toJson(cids));
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.verdetailApi, ajaxParams, new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataList$6() {
        this.mAdapter.setList(new ArrayList());
        this.mEmptyView.showEmptyView();
        this.mEmptyView.stopAnim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataList$7(List list, List list2) {
        this.mAdapter.addData((Collection) list);
        this.mAdapter.addData((Collection) list2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataList$8() {
        this.mAdapter.setList(new ArrayList());
        this.mEmptyView.showEmptyView();
        this.mEmptyView.stopAnim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        CourseCoverBean courseCoverBean = (CourseCoverBean) this.mAdapter.getItem(i2);
        if (courseCoverBean.isHeader()) {
            return;
        }
        startActivity(new Intent(getContext(), (Class<?>) CurriculumDownLoadManageActivity.class).putExtra("title", courseCoverBean.title).putExtra("manage", true).putExtra("course_id", String.valueOf(((CourseCoverBean) this.mAdapter.getItem(i2)).id)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$observeDownload$1(String str, long j2, int i2, int i3) {
        this.db.getVideoDownDao().updateSizeAndProgress(str, j2, i2, i3);
        LogUtils.d("mHandler", "更新数据库状态");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$observeDownload$2(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (this.currentMills == 0) {
            this.currentMills = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.currentMills < 200) {
            this.currentMills = System.currentTimeMillis();
            return;
        }
        final String vid = aliyunDownloadMediaInfo.getVid();
        String key = "";
        List<String> value = null;
        for (Map.Entry<String, List<String>> entry : this.course2VidMap.entrySet()) {
            Iterator<String> it = entry.getValue().iterator();
            while (it.hasNext()) {
                if (it.next().equals(vid)) {
                    key = entry.getKey();
                    value = entry.getValue();
                }
            }
        }
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }
        int i2 = 0;
        long size = 0;
        int progress = 0;
        int i3 = 0;
        for (String str : value) {
            VideoDownBean videoDownBean = this.courseVideoDownMap.get(str);
            if (videoDownBean != null) {
                if (!TextUtils.equals(videoDownBean.getVid(), vid)) {
                    AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(str);
                    if (aliyunDownloadMediaInfoIsInAliDownloadList != null && TextUtils.equals(aliyunDownloadMediaInfoIsInAliDownloadList.getVid(), videoDownBean.vid)) {
                        refreshVideoDownBeanStatus(aliyunDownloadMediaInfoIsInAliDownloadList.getStatus(), videoDownBean);
                    }
                } else {
                    if (videoDownBean.getmProgress() == 100) {
                        return;
                    }
                    videoDownBean.setmProgress(aliyunDownloadMediaInfo.getProgress());
                    videoDownBean.setmSize((int) aliyunDownloadMediaInfo.getSize());
                    refreshVideoDownBeanStatus(aliyunDownloadMediaInfo.getStatus(), videoDownBean);
                    if (aliyunDownloadMediaInfo.getProgress() == 100) {
                        videoDownBean.setmStatus(5);
                    }
                    i3 = videoDownBean.mStatus;
                    progress = aliyunDownloadMediaInfo.getProgress();
                    size = aliyunDownloadMediaInfo.getSize();
                }
            }
        }
        List<T> data = this.mAdapter.getData();
        while (true) {
            if (i2 >= data.size()) {
                break;
            }
            if (TextUtils.equals(String.valueOf(((CourseCoverBean) data.get(i2)).getId()), key)) {
                ((CourseCoverBean) data.get(i2)).setStatus(getStatusList(value));
                ((CourseCoverBean) data.get(i2)).setDownloadingCount(calculateCurrentDownloadCount(value));
                ((CourseCoverBean) data.get(i2)).setAllComplete(isAllDownloadComplete(value));
                ((CourseCoverBean) data.get(i2)).setSizeprocess(calculateDownloadSize(value));
                ((CourseCoverBean) data.get(i2)).setSize(getCourseTotalVideoSize(value));
                this.mAdapter.notifyItemChanged(i2, 999);
                break;
            }
            i2++;
        }
        final long j2 = size;
        final int i4 = progress;
        final int i5 = i3;
        this.mHandler.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.g4
            @Override // java.lang.Runnable
            public final void run() {
                this.f15613c.lambda$observeDownload$1(vid, j2, i4, i5);
            }
        });
        if (isAllDownloadComplete(value)) {
            refreshGroup();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemChildClick$10(boolean z2) {
        List<T> data = this.mAdapter.getData();
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (((CourseCoverBean) data.get(i2)).getType() == 0 && ((CourseCoverBean) data.get(i2)).isHeader()) {
                ((CourseCoverBean) data.get(i2)).setState(z2 ? 2 : 0);
                this.mAdapter.notifyItemChanged(i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemChildClick$11() {
        pauseDownload(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemChildClick$12() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.h4
            @Override // java.lang.Runnable
            public final void run() {
                this.f15638c.deleteDownload();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemChildClick$9(CourseCoverBean courseCoverBean) {
        pauseDownload(String.valueOf(courseCoverBean.id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pauseDownload$13(int i2) {
        this.mAdapter.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pauseDownload$14(int i2, List list) {
        if (i2 != -1) {
            this.mAdapter.notifyItemChanged(i2);
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mAdapter.notifyItemChanged(((Integer) it.next()).intValue(), 999);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshGroup$3() {
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshGroup$4() {
        boolean z2;
        List<String> list;
        if (getActivity() == null) {
            return;
        }
        List<T> data = this.mAdapter.getData();
        this.completeList.clear();
        this.completeItems.clear();
        int i2 = -1;
        for (int i3 = 0; i3 < data.size(); i3++) {
            boolean zIsHeader = ((CourseCoverBean) data.get(i3)).isHeader();
            CourseCoverBean courseCoverBean = (CourseCoverBean) data.get(i3);
            int type = courseCoverBean.getType();
            if (zIsHeader) {
                if (type == 1) {
                    courseCoverBean.setCount(calculateCompleteCount());
                    courseCoverBean.setState(1);
                    i2 = i3;
                } else {
                    courseCoverBean.setCount(calculateDownloadingCount());
                }
            }
        }
        for (int i4 = 0; i4 < data.size(); i4++) {
            CourseCoverBean courseCoverBean2 = (CourseCoverBean) data.get(i4);
            if (!courseCoverBean2.isHeader() && (list = this.course2VidMap.get(String.valueOf(courseCoverBean2.id))) != null) {
                int i5 = 0;
                for (String str : list) {
                    AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(str);
                    if (aliyunDownloadMediaInfoIsInAliDownloadList != null && (aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() == AliyunDownloadMediaInfo.Status.Complete || aliyunDownloadMediaInfoIsInAliDownloadList.getProgress() == 100)) {
                        i5++;
                        this.db.getVideoDownDao().updateVideoStatus(5, aliyunDownloadMediaInfoIsInAliDownloadList.getVid());
                    }
                    if (TextUtils.isEmpty(str)) {
                        i5++;
                    }
                }
                if (i5 == list.size()) {
                    this.completeList.add(Integer.valueOf(i4));
                    courseCoverBean2.setAllComplete(true);
                    this.completeItems.add(courseCoverBean2);
                }
            }
        }
        if (this.completeList.size() > 0) {
            for (int i6 = 0; i6 < data.size(); i6++) {
                Iterator<Integer> it = this.completeList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().intValue() == i6) {
                            data.remove(i6);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (i2 != -1) {
                for (CourseCoverBean courseCoverBean3 : this.completeItems) {
                    Iterator it2 = data.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (((CourseCoverBean) it2.next()).id == courseCoverBean3.id) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (!z2) {
                        data.add(i2, courseCoverBean3);
                    }
                }
            }
            getActivity().runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.o4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15887c.lambda$refreshGroup$3();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show4gDialog$16() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$17() {
        if (isLogin()) {
            goActivity(SetNewAvtivity.class);
        }
    }

    private void observeDownload() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.fragmenthome.n4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f15864a.lambda$observeDownload$2((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    private void pauseDownload(String courseId) {
        List<T> data = this.mAdapter.getData();
        int size = data.size();
        final ArrayList arrayList = new ArrayList();
        List<VideoDownBean> list = this.courseVideoListMap.get(courseId);
        LogUtils.d("pauseDownload", "暂停下载课程 = " + courseId);
        if (list != null && list.size() > 0) {
            for (VideoDownBean videoDownBean : list) {
                String str = videoDownBean.vid;
                AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(str);
                if (aliyunDownloadMediaInfoIsInAliDownloadList != null && aliyunDownloadMediaInfoIsInAliDownloadList.getProgress() < 100 && aliyunDownloadMediaInfoIsInAliDownloadList.getStatus() != AliyunDownloadMediaInfo.Status.Complete) {
                    ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfoIsInAliDownloadList, false);
                    this.db.getVideoDownDao().updateVideoStatus(4, str);
                    videoDownBean.setmStatus(4);
                }
            }
            final int i2 = 0;
            while (true) {
                if (i2 >= data.size()) {
                    break;
                }
                CourseCoverBean courseCoverBean = (CourseCoverBean) data.get(i2);
                if (TextUtils.equals(String.valueOf(courseCoverBean.getId()), courseId)) {
                    courseCoverBean.setStatus(Collections.singletonList(4));
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.l4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15805c.lambda$pauseDownload$13(i2);
                        }
                    }, 500L);
                    break;
                }
                i2++;
            }
        }
        final int i3 = -1;
        if (courseId == null) {
            for (int i4 = 0; i4 < size; i4++) {
                CourseCoverBean courseCoverBean2 = (CourseCoverBean) data.get(i4);
                if (courseCoverBean2.isHeader() && courseCoverBean2.getType() == 0) {
                    courseCoverBean2.setState(2);
                    i3 = i4;
                }
                ArrayList arrayList2 = new ArrayList();
                List<String> list2 = this.course2VidMap.get(String.valueOf(courseCoverBean2.id));
                if (list2 != null && list2.size() > 0) {
                    for (String str2 : list2) {
                        AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList2 = isInAliDownloadList(str2);
                        if (aliyunDownloadMediaInfoIsInAliDownloadList2 == null || aliyunDownloadMediaInfoIsInAliDownloadList2.getProgress() >= 100 || aliyunDownloadMediaInfoIsInAliDownloadList2.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
                            VideoDownBean videoDownBean2 = this.courseVideoDownMap.get(str2);
                            if (videoDownBean2 != null) {
                                videoDownBean2.setmStatus(4);
                                arrayList2.add(4);
                            }
                        } else {
                            ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfoIsInAliDownloadList2);
                            this.db.getVideoDownDao().updateVideoStatus(4, str2);
                            arrayList2.add(4);
                        }
                    }
                }
                courseCoverBean2.setStatus(arrayList2);
                arrayList.add(Integer.valueOf(i4));
            }
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.m4
            @Override // java.lang.Runnable
            public final void run() {
                this.f15829c.lambda$pauseDownload$14(i3, arrayList);
            }
        });
    }

    private void refreshGroup() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.f4
            @Override // java.lang.Runnable
            public final void run() {
                this.f15584c.lambda$refreshGroup$4();
            }
        });
    }

    private void refreshVideoDownBeanStatus(AliyunDownloadMediaInfo.Status status, VideoDownBean videoDownBean) {
        switch (AnonymousClass5.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[status.ordinal()]) {
            case 1:
            case 2:
                videoDownBean.setmStatus(0);
                return;
            case 3:
                videoDownBean.setmStatus(3);
                break;
            case 4:
                break;
            case 5:
                videoDownBean.setmStatus(4);
                return;
            case 6:
                videoDownBean.setmStatus(5);
                return;
            case 7:
                videoDownBean.setmStatus(2);
                return;
            default:
                return;
        }
        videoDownBean.setmStatus(1);
    }

    private void show4gDialog() {
        if (getContext() == null || getActivity() == null) {
            return;
        }
        if (!CommonUtil.hasRequiredPermissionsWriteStorage(getActivity())) {
            new XPopup.Builder(getActivity()).asCustom(new RequestMediaPermissionPop(getContext(), new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.v3
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f16061a.lambda$show4gDialog$16();
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

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.fragmenthome.k4
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f15712a.lambda$showOpenDownloadDialog$17();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    private void startDownload() {
        if (CommonUtil.isNetworkConnected(getContext())) {
            getCourseAk();
        } else {
            AlertToast("请打开网络连接");
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_cover_course_download;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.rvCourseList);
        EventBus.getDefault().register(this);
        this.mAdapter = new CourseCoverDownloadAdapter(new ArrayList());
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(this.mAdapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(getContext(), 0, "暂无数据");
        this.mEmptyView = customEmptyView;
        this.mAdapter.setEmptyView(customEmptyView);
        this.mAdapter.setOnItemChildClickListener(this);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.j4
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15684c.lambda$initViews$0(baseQuickAdapter, view, i2);
            }
        });
        this.db = ProjectApp.database;
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        getCourseInfo();
        observeDownload();
        ProjectApp.downloadManager.addDownloadInfoListener(new SimpleAliyunDownloadInfoListener() { // from class: com.psychiatrygarden.fragmenthome.CoverCourseDownFragment.1
            @Override // com.aliyun.player.alivcplayerexpand.util.download.SimpleAliyunDownloadInfoListener, com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadInfoListener
            public void onError(AliyunDownloadMediaInfo info, ErrorCode code, String msg, String requestId) {
                super.onError(info, code, msg, requestId);
                LogUtils.e("download_error", "vid = " + info.getVid() + " code = " + code.name() + " requestId = " + requestId);
                List<T> data = CoverCourseDownFragment.this.mAdapter.getData();
                for (int i2 = 0; i2 < data.size(); i2++) {
                    CourseCoverBean courseCoverBean = (CourseCoverBean) data.get(i2);
                    if (courseCoverBean.getType() == 0 && courseCoverBean.isHeader()) {
                        courseCoverBean.setState(2);
                        CoverCourseDownFragment.this.mAdapter.notifyItemChanged(i2);
                        return;
                    }
                }
            }
        });
    }

    public AliyunDownloadMediaInfo isInAliDownloadList(String vid) {
        if (vid != null && YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
                if (vid.equals(aliyunDownloadMediaInfo.getVid())) {
                    return aliyunDownloadMediaInfo;
                }
            }
        }
        return null;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandlerThread.quitSafely();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    @Subscribe
    public void onEventMainThread(String s2) {
        if ("REFRESH_DOWNLOAD_LIST".equals(s2)) {
            this.mAdapter.getData().clear();
            this.mAdapter.setList(new ArrayList());
            ThreadUtils.runOnSubThread(new i4(this));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int i2) {
        final CourseCoverBean courseCoverBean = (CourseCoverBean) this.mAdapter.getItem(i2);
        ArrayList arrayList = new ArrayList();
        final boolean z2 = false;
        if (view.getId() == R.id.iv_start_download) {
            this.clickPosition = i2;
            List<String> list = this.course2VidMap.get(String.valueOf(courseCoverBean.id));
            if (list == null || list.isEmpty()) {
                return;
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                VideoDownBean videoDownBean = this.courseVideoDownMap.get(it.next());
                if (videoDownBean != null) {
                    arrayList.add(Integer.valueOf(videoDownBean.getmStatus()));
                }
            }
            if (arrayList.contains(1) || arrayList.contains(3)) {
                new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.b4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15458c.lambda$onItemChildClick$9(courseCoverBean);
                    }
                }).start();
                z2 = true;
            } else {
                startDownload();
            }
            if (getActivity() != null) {
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.c4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15497c.lambda$onItemChildClick$10(z2);
                    }
                }, 500L);
                return;
            }
            return;
        }
        if (view.getId() == R.id.tv_operate) {
            if (!"PAUSE".equals(view.getTag())) {
                if ("START".equals(view.getTag())) {
                    this.clickPosition = -1;
                    show4gDialog();
                    return;
                } else {
                    if ("DELETE".equals(view.getTag())) {
                        new XPopup.Builder(getActivity()).asCustom(new DeleteDownloadDialog(requireContext(), new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.fragmenthome.e4
                            @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                            public final void mClickIml() {
                                this.f15559a.lambda$onItemChildClick$12();
                            }
                        }, null, "取消", "确定")).show();
                        return;
                    }
                    return;
                }
            }
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.d4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15535c.lambda$onItemChildClick$11();
                }
            });
            List<T> data = this.mAdapter.getData();
            for (int i3 = 0; i3 < data.size(); i3++) {
                if (((CourseCoverBean) data.get(i3)).getType() == 0 && ((CourseCoverBean) data.get(i3)).isHeader()) {
                    ((CourseCoverBean) data.get(i3)).setState(2);
                    this.mAdapter.notifyItemChanged(i3);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void startDownload(String ackId, String akSecret, String securityToken) {
        if (this.allVids.size() > 0) {
            ArrayList arrayList = new ArrayList();
            int i2 = this.clickPosition;
            if (i2 != -1) {
                arrayList.add(String.valueOf(((CourseCoverBean) this.mAdapter.getItem(i2)).id));
            } else {
                for (T t2 : this.mAdapter.getData()) {
                    if (!t2.isHeader() && t2.getType() == 0) {
                        arrayList.add(String.valueOf(t2.id));
                    }
                    if (t2.getType() == 0 && t2.isHeader()) {
                        t2.setState(0);
                    }
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                List<String> list = this.course2VidMap.get((String) it.next());
                if (list != null && list.size() > 0) {
                    for (String str : list) {
                        VideoDownBean videoDownBean = this.courseVideoDownMap.get(str);
                        if (videoDownBean != null) {
                            videoDownBean.setmStatus(3);
                        }
                        AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsInAliDownloadList = isInAliDownloadList(str);
                        if (aliyunDownloadMediaInfoIsInAliDownloadList != null && aliyunDownloadMediaInfoIsInAliDownloadList.getProgress() < 100) {
                            if (ProjectApp.downloadManager.getmVidSts() == null) {
                                VidSts vidSts = new VidSts();
                                vidSts.setQuality(TextUtils.isEmpty(aliyunDownloadMediaInfoIsInAliDownloadList.getQuality()) ? QualityValue.QUALITY_FLUENT : aliyunDownloadMediaInfoIsInAliDownloadList.getQuality(), false);
                                vidSts.setVid(str);
                                vidSts.setAccessKeyId(ackId);
                                vidSts.setAccessKeySecret(akSecret);
                                vidSts.setSecurityToken(securityToken);
                                vidSts.setRegion(GlobalPlayerConfig.mRegion);
                                ProjectApp.downloadManager.setmVidSts(vidSts);
                            }
                            this.db.getVideoDownDao().updateVideoStatus(3, str);
                            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoIsInAliDownloadList);
                        } else {
                            this.db.getVideoDownDao().updateVideoStatus(3, str);
                            CommonUtil.initDownAliyunVideo(str, ackId, akSecret, securityToken);
                        }
                    }
                }
            }
        }
    }

    @Subscribe
    public void onEventMainThread(RefreshCourseDownloadStateEvent event) {
        this.mAdapter.getData().clear();
        ThreadUtils.runOnSubThread(new i4(this));
    }
}
