package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import cn.hutool.core.lang.RegexPool;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.SetNewAvtivity;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseAkBean;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.RefreshCourseDownloadStateEvent;
import com.psychiatrygarden.event.RefreshCourseDownloadedEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.DeleteDownloadDialog;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import kotlin.Triple;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDownloadManageFragment extends BaseFragment implements View.OnClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CODE_DOWNLOAD_PAUSE = 4;
    private static final int CODE_REFRESH_CHILD_OUT_SELECT_ICON = 2;
    private static final int CODE_REFRESH_CHILD_VIDEO_COL_EXP_ICON = 4;
    private static final int CODE_REFRESH_OUT_ARROW = 999;
    private static final int CODE_REFRESH_OUT_SELECT_ICON = 1;
    private static final int CODE_REFRESH_VID_VIDEO_SELECT_ICON = 3;
    private String courseCover;
    private String courseTitle;
    File file;
    private boolean isEditMode;
    private CurriculumDownloadManageAdapter mDownloadAdapter;
    private Handler mHandler;
    private boolean selectAll;
    private TextView tvSelectAll;
    private TextView tvTitle;
    private final Map<Integer, Integer> childRvHeightMap = new ArrayMap();
    private final List<MultiItemEntity> mDataList = new ArrayList();
    private final Map<String, Boolean> expColMap = new ArrayMap();
    private final Map<String, List<VideoDownBean>> chapterVidMap = new ArrayMap();
    private final Map<String, List<VideoDownBean>> childVidMap = new ArrayMap();
    private final Map<String, List<VideoDownBean>> chapterChildIdMap = new ArrayMap();
    private final Map<String, Pair<Integer, Integer>> chapterDataPosition = new ArrayMap();
    private final Map<String, List<Integer>> chapterDataNewPosition = new ArrayMap();
    private final Map<String, List<VideoDownBean>> chapterAllVidList = new ArrayMap();
    private String course_id = "0";
    private boolean fromManage = false;
    private boolean fromCourseDirectory = false;
    private boolean fromCourseDetail = false;
    private final List<String> vidChangeList = new ArrayList();
    private final HandlerThread mHandlerThread = new HandlerThread(AliyunLogCommon.SubModule.download);

    /* renamed from: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            CourseDownloadManageFragment.this.fillChapterVid();
            CourseDownloadManageFragment.this.fillChildChapter();
            CourseDownloadManageFragment.this.fillChildVid();
            CourseDownloadManageFragment.this.calculateItemMapPosition();
            CourseDownloadManageFragment.this.mDownloadAdapter.setList(CourseDownloadManageFragment.this.mDataList);
            CourseDownloadManageFragment.this.selectAll = false;
            CourseDownloadManageFragment.this.tvSelectAll.setText("全选");
            CourseDownloadManageFragment.this.mRefreshDownloadData();
            CourseDownloadManageFragment.this.tvSelectAll.performClick();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:101:0x02f1  */
        /* JADX WARN: Removed duplicated region for block: B:105:0x0320  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x0336  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$onSuccess$1(java.util.List r19, java.util.List r20, java.util.List r21) throws java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 1541
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment.AnonymousClass2.lambda$onSuccess$1(java.util.List, java.util.List, java.util.List):void");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if ("200".equals(jSONObject.optString("code"))) {
                    final List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment.2.1
                    }.getType());
                    if (list != null && list.size() > 0) {
                        final ArrayList arrayList = new ArrayList();
                        final ArrayList arrayList2 = new ArrayList();
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.r3
                            @Override // java.lang.Runnable
                            public final void run() throws NumberFormatException {
                                this.f15959c.lambda$onSuccess$1(list, arrayList, arrayList2);
                            }
                        });
                    }
                } else {
                    String strOptString = jSONObject.optString("message");
                    if (!TextUtils.isEmpty(strOptString)) {
                        CourseDownloadManageFragment.this.AlertToast(strOptString);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment$3, reason: invalid class name */
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
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Delete.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public class CurriculumDownloadManageAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
        public CurriculumDownloadManageAdapter() {
            addItemType(0, R.layout.item_curriculum_download_parent_node);
            addItemType(1, R.layout.item_down_manage_child);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, Object multiItemEntity, @NonNull List payloads) {
            convert(holder, (MultiItemEntity) multiItemEntity, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, MultiItemEntity multiItemEntity) {
            if (multiItemEntity.getItemType() == 0) {
                CourseDownloadManageFragment.this.convertFirst(holder, (CourseDirectoryBean) multiItemEntity, new ArrayList());
            } else {
                CourseDownloadManageFragment.this.convertSecond(holder, (VideoDownBean) multiItemEntity, new ArrayList());
            }
        }

        public void convert(@NonNull BaseViewHolder holder, MultiItemEntity multiItemEntity, @NonNull List<?> payloads) {
            super.convert((CurriculumDownloadManageAdapter) holder, (BaseViewHolder) multiItemEntity, (List<? extends Object>) payloads);
            if (multiItemEntity.getItemType() == 0) {
                CourseDownloadManageFragment.this.convertFirst(holder, (CourseDirectoryBean) multiItemEntity, payloads);
            } else {
                CourseDownloadManageFragment.this.convertSecond(holder, (VideoDownBean) multiItemEntity, payloads);
            }
        }
    }

    private void calculateChapterVidList() {
        List<VideoDownBean> list;
        this.chapterAllVidList.clear();
        for (MultiItemEntity multiItemEntity : this.mDataList) {
            if (multiItemEntity instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity;
                if (TextUtils.isEmpty(videoDownBean.vid) && (list = this.childVidMap.get(videoDownBean.chapter_id)) != null) {
                    List<VideoDownBean> arrayList = this.chapterAllVidList.get(videoDownBean.parent_id);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                        this.chapterAllVidList.put(videoDownBean.parent_id, arrayList);
                    }
                    for (VideoDownBean videoDownBean2 : list) {
                        if (!TextUtils.isEmpty(videoDownBean2.vid)) {
                            arrayList.add(videoDownBean2);
                        }
                    }
                }
            } else if (multiItemEntity instanceof CourseDirectoryBean) {
                CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) multiItemEntity;
                for (MultiItemEntity multiItemEntity2 : this.mDataList) {
                    if (multiItemEntity2 instanceof VideoDownBean) {
                        VideoDownBean videoDownBean3 = (VideoDownBean) multiItemEntity2;
                        if (!TextUtils.isEmpty(videoDownBean3.vid) && TextUtils.equals(videoDownBean3.parent_id, String.valueOf(courseDirectoryBean.id))) {
                            List<VideoDownBean> arrayList2 = this.chapterAllVidList.get(String.valueOf(courseDirectoryBean.id));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList<>();
                                this.chapterAllVidList.put(String.valueOf(courseDirectoryBean.id), arrayList2);
                            }
                            arrayList2.add(videoDownBean3);
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void calculateItemMapPosition() {
        /*
            Method dump skipped, instructions count: 450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment.calculateItemMapPosition():void");
    }

    private boolean checkHasChildVideo(VideoDownBean downBean) {
        for (MultiItemEntity multiItemEntity : this.mDataList) {
            if (multiItemEntity instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity;
                if (!TextUtils.isEmpty(videoDownBean.vid) && TextUtils.equals(videoDownBean.chapter_id, downBean.chapter_id)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<BaseNode> convertData(List<MultiItemEntity> data) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : data) {
            if (obj instanceof BaseNode) {
                arrayList.add((BaseNode) obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertFirst(@NotNull final BaseViewHolder baseViewHolder, final CourseDirectoryBean dataDTO, @NonNull List<?> payloads) {
        if (!payloads.isEmpty()) {
            for (Object obj : payloads) {
                if ((obj instanceof Integer) && ((Integer) obj).intValue() == 1) {
                    ImageView imageView = (ImageView) baseViewHolder.findView(R.id.selectTv);
                    if (imageView != null) {
                        imageView.setSelected(dataDTO.getSelected() == 1);
                    }
                    List<VideoDownBean> list = this.chapterVidMap.get(String.valueOf(dataDTO.id));
                    ArrayList arrayList = new ArrayList();
                    if (list != null) {
                        arrayList.addAll(list);
                    }
                    refreshSelectShowInfo(imageView, arrayList);
                    refreshSelectShowInfo();
                }
            }
            return;
        }
        baseViewHolder.setGone(R.id.ic_video, dataDTO.isEditMode()).setGone(R.id.selectTv, !dataDTO.isEditMode());
        final TextView textView = (TextView) baseViewHolder.findView(R.id.title);
        final ImageView imageView2 = (ImageView) baseViewHolder.findView(R.id.croteimg);
        ImageView imageView3 = (ImageView) baseViewHolder.findView(R.id.selectTv);
        textView.setText(dataDTO.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.b3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15455c.lambda$convertFirst$17(dataDTO, baseViewHolder, view);
            }
        });
        final int layoutPosition = baseViewHolder.getLayoutPosition();
        if (dataDTO.getChildNode() == null || dataDTO.getChildNode().isEmpty()) {
            return;
        }
        imageView3.setSelected(dataDTO.getSelected() == 1);
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.download_new_not_select, R.attr.download_select});
        imageView3.setImageDrawable(dataDTO.getSelect() == 1 ? typedArrayObtainStyledAttributes.getDrawable(1) : typedArrayObtainStyledAttributes.getDrawable(0));
        typedArrayObtainStyledAttributes.recycle();
        if (dataDTO.getIsExpanded()) {
            imageView2.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.icon_top_arrow_day : R.drawable.icon_top_arrow_night);
        } else {
            imageView2.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.icon_bottom_arrow_day : R.drawable.icon_bottom_arrow_night);
        }
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15492c.lambda$convertFirst$18(dataDTO, layoutPosition, imageView2, textView, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:106:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0345  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0436  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x04a5  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x04bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convertSecond(@org.jetbrains.annotations.NotNull final com.chad.library.adapter.base.viewholder.BaseViewHolder r29, @org.jetbrains.annotations.NotNull final com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean r30, @androidx.annotation.NonNull java.util.List<?> r31) {
        /*
            Method dump skipped, instructions count: 1954
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment.convertSecond(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean, java.util.List):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillChapterVid() {
        this.chapterVidMap.clear();
        for (MultiItemEntity multiItemEntity : this.mDataList) {
            if (multiItemEntity instanceof CourseDirectoryBean) {
                String strValueOf = String.valueOf(((CourseDirectoryBean) multiItemEntity).id);
                for (MultiItemEntity multiItemEntity2 : this.mDataList) {
                    if (multiItemEntity2 instanceof VideoDownBean) {
                        VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity2;
                        if (TextUtils.isEmpty(videoDownBean.vid) && TextUtils.equals(strValueOf, videoDownBean.parent_id)) {
                            String str = videoDownBean.chapter_id;
                            ArrayList<VideoDownBean> arrayList = new ArrayList();
                            for (MultiItemEntity multiItemEntity3 : this.mDataList) {
                                if (multiItemEntity3 instanceof VideoDownBean) {
                                    VideoDownBean videoDownBean2 = (VideoDownBean) multiItemEntity3;
                                    if (!TextUtils.isEmpty(videoDownBean2.vid) && TextUtils.equals(videoDownBean2.chapter_id, str)) {
                                        arrayList.add(videoDownBean2);
                                    }
                                }
                            }
                            List<VideoDownBean> arrayList2 = this.chapterVidMap.get(strValueOf);
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList<>();
                                this.chapterVidMap.put(strValueOf, arrayList2);
                            }
                            for (VideoDownBean videoDownBean3 : arrayList) {
                                Iterator<VideoDownBean> it = arrayList2.iterator();
                                boolean z2 = false;
                                while (it.hasNext()) {
                                    if (TextUtils.equals(it.next().vid, videoDownBean3.vid)) {
                                        z2 = true;
                                    }
                                }
                                if (!z2) {
                                    arrayList2.add(videoDownBean3);
                                }
                            }
                        } else if (TextUtils.equals(strValueOf, videoDownBean.chapter_id) || TextUtils.equals(strValueOf, videoDownBean.parent_id)) {
                            List<VideoDownBean> arrayList3 = this.chapterVidMap.get(strValueOf);
                            if (arrayList3 == null) {
                                arrayList3 = new ArrayList<>();
                                this.chapterVidMap.put(strValueOf, arrayList3);
                            }
                            arrayList3.add(videoDownBean);
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillChildChapter() {
        this.chapterChildIdMap.clear();
        for (MultiItemEntity multiItemEntity : this.mDataList) {
            if (multiItemEntity instanceof CourseDirectoryBean) {
                String strValueOf = String.valueOf(((CourseDirectoryBean) multiItemEntity).id);
                ArrayList arrayList = new ArrayList();
                for (MultiItemEntity multiItemEntity2 : this.mDataList) {
                    if (multiItemEntity2 instanceof VideoDownBean) {
                        VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity2;
                        if (TextUtils.isEmpty(videoDownBean.vid) && TextUtils.equals(videoDownBean.parent_id, strValueOf)) {
                            arrayList.add(videoDownBean);
                        }
                    }
                }
                this.chapterChildIdMap.put(strValueOf, arrayList);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillChildVid() {
        this.childVidMap.clear();
        for (MultiItemEntity multiItemEntity : this.mDataList) {
            if (multiItemEntity instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity;
                if (TextUtils.isEmpty(videoDownBean.vid)) {
                    ArrayList arrayList = new ArrayList();
                    for (MultiItemEntity multiItemEntity2 : this.mDataList) {
                        if (multiItemEntity2 instanceof VideoDownBean) {
                            VideoDownBean videoDownBean2 = (VideoDownBean) multiItemEntity2;
                            if (!TextUtils.isEmpty(videoDownBean2.vid) && TextUtils.equals(videoDownBean.chapter_id, videoDownBean2.chapter_id)) {
                                arrayList.add(videoDownBean2);
                            }
                        }
                    }
                    this.childVidMap.put(videoDownBean.chapter_id, arrayList);
                }
            }
        }
    }

    private VideoDownBean getChildChapterParent(VideoDownBean bean) {
        for (MultiItemEntity multiItemEntity : this.mDataList) {
            if (multiItemEntity instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity;
                if (TextUtils.isEmpty(videoDownBean.vid) && TextUtils.equals(videoDownBean.chapter_id, bean.chapter_id)) {
                    return videoDownBean;
                }
            }
        }
        return null;
    }

    private Triple<Integer, Drawable, Boolean> getChildNodeBg(VideoDownBean videoDownBean) {
        List<VideoDownBean> list = this.childVidMap.get(videoDownBean.getChapter_id());
        if (list == null || list.isEmpty()) {
            return new Triple<>(0, new ColorDrawable(-1), Boolean.FALSE);
        }
        if (list.size() == 1) {
            return new Triple<>(0, ContextCompat.getDrawable(this.mContext, R.drawable.bg_course_download_node_3), Boolean.FALSE);
        }
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (TextUtils.equals(list.get(i3).vid, videoDownBean.vid)) {
                i2 = i3;
            }
        }
        return i2 == 0 ? new Triple<>(0, ContextCompat.getDrawable(this.mContext, R.drawable.bg_course_download_child_top), Boolean.FALSE) : i2 == list.size() - 1 ? new Triple<>(Integer.valueOf(list.size() - 1), ContextCompat.getDrawable(this.mContext, R.drawable.bg_course_download_child_bottom), Boolean.TRUE) : new Triple<>(0, ContextCompat.getDrawable(this.mContext, R.drawable.bg_course_download_child_middle), Boolean.FALSE);
    }

    private void getCourseAk() {
        List<T> data = this.mDownloadAdapter.getData();
        ArrayList<BaseNode> arrayList = new ArrayList();
        for (T t2 : data) {
            if (t2 instanceof BaseNode) {
                arrayList.add((BaseNode) t2);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        for (BaseNode baseNode : arrayList) {
            if (baseNode instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                List<BaseNode> notes = videoDownBean.getNotes();
                if (!TextUtils.isEmpty(videoDownBean.vid)) {
                    if (videoDownBean.getSelected() == 1) {
                        i2++;
                        arrayList3.add(videoDownBean.vid);
                    }
                    if (videoDownBean.getmStatus() == 5) {
                        arrayList2.add(videoDownBean.vid);
                        i3++;
                    }
                } else if (notes != null && notes.size() > 0) {
                    Iterator<BaseNode> it = notes.iterator();
                    while (it.hasNext()) {
                        VideoDownBean videoDownBean2 = (VideoDownBean) it.next();
                        if (!TextUtils.isEmpty(videoDownBean2.vid)) {
                            if (videoDownBean2.getSelected() == 1) {
                                i2++;
                                arrayList3.add(videoDownBean2.vid);
                            }
                            if (videoDownBean2.getmStatus() == 5) {
                                arrayList2.add(videoDownBean2.vid);
                                i3++;
                            }
                        }
                    }
                }
            }
        }
        if (i2 == i3 && i2 > 0 && new HashSet(arrayList2).containsAll(arrayList3)) {
            return;
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t3, int errorNo, String strMsg) {
                super.onFailure(t3, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                NewToast.showShort(((BaseFragment) CourseDownloadManageFragment.this).mContext, "获取视频信息失败！");
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
                        CourseAkBean courseAkBean = (CourseAkBean) new Gson().fromJson(jSONObjectOptJSONObject.toString(), CourseAkBean.class);
                        CourseDownloadManageFragment.this.downloadData(DesUtil.decode(CommonParameter.DES_KEY_ALI, courseAkBean.getAkId()), DesUtil.decode(CommonParameter.DES_KEY_ALI, courseAkBean.getAkSecret()), DesUtil.decode(CommonParameter.DES_KEY_ALI, courseAkBean.getSt()));
                    } else {
                        NewToast.showShort(((BaseFragment) CourseDownloadManageFragment.this).mContext, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ProjectApp.instance().hideDialogWindow();
            }
        });
    }

    private void getCourseListData() {
        if (this.fromManage) {
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.x2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16108c.loadDbData();
                }
            });
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        Bundle arguments = getArguments();
        if (arguments != null) {
            ajaxParams.put("type", arguments.getString("type", "1"));
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryCombine, ajaxParams, new AnonymousClass2());
    }

    public static CourseDownloadManageFragment getInstance(Bundle args) {
        CourseDownloadManageFragment courseDownloadManageFragment = new CourseDownloadManageFragment();
        courseDownloadManageFragment.setArguments(args);
        return courseDownloadManageFragment;
    }

    private CourseDirectoryBean getVideoChapter(VideoDownBean videoDownBean) {
        for (Map.Entry<String, List<VideoDownBean>> entry : this.chapterVidMap.entrySet()) {
            Iterator<VideoDownBean> it = entry.getValue().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (TextUtils.equals(videoDownBean.vid, it.next().vid)) {
                        for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
                            MultiItemEntity multiItemEntity = this.mDataList.get(i2);
                            if (multiItemEntity instanceof CourseDirectoryBean) {
                                CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) multiItemEntity;
                                if (TextUtils.equals(String.valueOf(courseDirectoryBean.id), entry.getKey())) {
                                    return courseDirectoryBean;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertFirst$17(CourseDirectoryBean courseDirectoryBean, BaseViewHolder baseViewHolder, View view) {
        Boolean bool = this.expColMap.get(String.valueOf(courseDirectoryBean.getId()));
        if (bool == null) {
            bool = Boolean.TRUE;
        }
        Boolean boolValueOf = Boolean.valueOf(!bool.booleanValue());
        this.expColMap.put(String.valueOf(courseDirectoryBean.getId()), boolValueOf);
        Pair<Integer, Integer> pair = this.chapterDataPosition.get(String.valueOf(courseDirectoryBean.getId()));
        if (pair != null) {
            List<VideoDownBean> list = this.chapterAllVidList.get(String.valueOf(courseDirectoryBean.getId()));
            for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
                MultiItemEntity multiItemEntity = this.mDataList.get(i2);
                if (multiItemEntity instanceof VideoDownBean) {
                    VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity;
                    if (i2 >= ((Integer) pair.first).intValue() && i2 <= ((Integer) pair.second).intValue()) {
                        videoDownBean.show = boolValueOf.booleanValue();
                        this.mDownloadAdapter.notifyItemChanged(i2);
                    }
                    if (list != null && list.size() > 0) {
                        Iterator<VideoDownBean> it = list.iterator();
                        while (it.hasNext()) {
                            if (TextUtils.equals(it.next().vid, videoDownBean.vid) && videoDownBean.show != boolValueOf.booleanValue()) {
                                videoDownBean.show = boolValueOf.booleanValue();
                                this.mDownloadAdapter.notifyItemChanged(i2);
                            }
                        }
                    }
                    if (TextUtils.isEmpty(videoDownBean.vid) && TextUtils.equals(videoDownBean.parent_id, String.valueOf(courseDirectoryBean.id)) && videoDownBean.show != boolValueOf.booleanValue()) {
                        videoDownBean.show = boolValueOf.booleanValue();
                        this.mDownloadAdapter.notifyItemChanged(i2);
                    }
                }
            }
        }
        courseDirectoryBean.setExpanded(boolValueOf.booleanValue());
        this.mDownloadAdapter.notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertFirst$18(CourseDirectoryBean courseDirectoryBean, int i2, ImageView imageView, TextView textView, View view) {
        int i3 = courseDirectoryBean.getSelected() == 1 ? 1 : 0;
        courseDirectoryBean.setSelected(i3 ^ 1);
        String strValueOf = String.valueOf(courseDirectoryBean.id);
        Pair<Integer, Integer> pair = this.chapterDataPosition.get(strValueOf);
        List<VideoDownBean> list = this.chapterAllVidList.get(strValueOf);
        if (pair != null) {
            if (list != null) {
                for (VideoDownBean videoDownBean : list) {
                    for (int i4 = 0; i4 < this.mDataList.size(); i4++) {
                        MultiItemEntity multiItemEntity = this.mDataList.get(i4);
                        if (multiItemEntity instanceof VideoDownBean) {
                            VideoDownBean videoDownBean2 = (VideoDownBean) multiItemEntity;
                            if (TextUtils.equals(videoDownBean2.vid, videoDownBean.vid)) {
                                videoDownBean2.setSelected(courseDirectoryBean.getSelected());
                                this.mDownloadAdapter.notifyItemChanged(i4, 3);
                            }
                        }
                    }
                }
            }
            List<VideoDownBean> list2 = this.chapterChildIdMap.get(strValueOf);
            if (list2 != null && list2.size() > 0) {
                for (int i5 = 0; i5 < list2.size(); i5++) {
                    VideoDownBean videoDownBean3 = list2.get(i5);
                    videoDownBean3.setSelected(i3 ^ 1);
                    int i6 = 0;
                    while (true) {
                        if (i6 < this.mDataList.size()) {
                            MultiItemEntity multiItemEntity2 = this.mDataList.get(i6);
                            if (multiItemEntity2 instanceof VideoDownBean) {
                                VideoDownBean videoDownBean4 = (VideoDownBean) multiItemEntity2;
                                if (TextUtils.isEmpty(videoDownBean4.vid) && TextUtils.equals(videoDownBean3.chapter_id, videoDownBean4.chapter_id)) {
                                    videoDownBean4.setSelected(videoDownBean3.getSelected());
                                    this.mDownloadAdapter.notifyItemChanged(i6, 2);
                                    break;
                                }
                            }
                            i6++;
                        }
                    }
                }
            }
            this.mDownloadAdapter.notifyItemChanged(i2, 1);
            refreshSelectShowInfo();
        }
        List<VideoDownBean> list3 = this.chapterVidMap.get(strValueOf);
        int i7 = R.drawable.icon_bottom_arrow_night;
        if (list3 == null || list3.isEmpty()) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
                textView.setTextColor(this.mContext.getColor(R.color.tertiary_text_color_night));
            } else {
                imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
                textView.setTextColor(this.mContext.getColor(R.color.tertiary_text_color));
            }
            imageView.setVisibility(4);
            return;
        }
        imageView.setVisibility(0);
        if (courseDirectoryBean.getIsExpanded()) {
            imageView.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.icon_top_arrow_day : R.drawable.icon_top_arrow_night);
            return;
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            i7 = R.drawable.icon_bottom_arrow_day;
        }
        imageView.setImageResource(i7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertSecond$19(VideoDownBean videoDownBean, int i2, View view) {
        List<VideoDownBean> list = this.childVidMap.get(videoDownBean.chapter_id);
        if (list == null || list.size() <= 0) {
            return;
        }
        boolean z2 = !list.get(0).show;
        videoDownBean.childViewExpand = !videoDownBean.childViewExpand;
        this.mDownloadAdapter.notifyItemChanged(i2, 4);
        for (VideoDownBean videoDownBean2 : list) {
            videoDownBean2.show = z2;
            int i3 = 0;
            while (true) {
                if (i3 < this.mDataList.size()) {
                    MultiItemEntity multiItemEntity = this.mDataList.get(i3);
                    if (multiItemEntity instanceof VideoDownBean) {
                        VideoDownBean videoDownBean3 = (VideoDownBean) multiItemEntity;
                        if (TextUtils.equals(videoDownBean3.vid, videoDownBean2.vid)) {
                            videoDownBean3.show = z2;
                            this.mDownloadAdapter.notifyItemChanged(i3);
                            break;
                        }
                    }
                    i3++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertSecond$20(VideoDownBean videoDownBean, int i2, View view) {
        if (videoDownBean.isEditMode()) {
            List<VideoDownBean> list = this.childVidMap.get(videoDownBean.getChapter_id());
            Pair<Integer, Integer> pair = this.chapterDataPosition.get(videoDownBean.getParent_id());
            List<Integer> arrayList = this.chapterDataNewPosition.get(videoDownBean.getChapter_id());
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (list != null) {
                videoDownBean.setSelected((videoDownBean.getSelected() == 1 ? 1 : 0) ^ 1);
                if (pair != null || arrayList.size() > 0) {
                    for (int i3 = 0; i3 < this.mDataList.size(); i3++) {
                        for (Integer num : arrayList) {
                            MultiItemEntity multiItemEntity = this.mDataList.get(num.intValue());
                            if (multiItemEntity instanceof VideoDownBean) {
                                VideoDownBean videoDownBean2 = (VideoDownBean) multiItemEntity;
                                videoDownBean2.setSelected(videoDownBean.getSelected());
                                this.mDownloadAdapter.notifyItemChanged(num.intValue(), 3);
                                Log.d("REFRESH", "刷新视频 " + videoDownBean2.mTitle + " 图标选中状态");
                            }
                        }
                    }
                }
                Iterator<VideoDownBean> it = list.iterator();
                while (it.hasNext()) {
                    it.next().setSelected(videoDownBean.getSelected());
                    int i4 = 0;
                    while (true) {
                        if (i4 < this.mDataList.size()) {
                            MultiItemEntity multiItemEntity2 = this.mDataList.get(i4);
                            if (multiItemEntity2 instanceof CourseDirectoryBean) {
                                CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) multiItemEntity2;
                                if (TextUtils.equals(videoDownBean.parent_id, String.valueOf(courseDirectoryBean.id))) {
                                    LogUtils.d("REFRESH", "刷新外层目录" + courseDirectoryBean.title + " 图标选中状态");
                                    this.mDownloadAdapter.notifyItemChanged(i4, 1);
                                    break;
                                }
                            }
                            i4++;
                        }
                    }
                }
                LogUtils.d("REFRESH", "刷新子章节目录 " + videoDownBean.mTitle + " 图标选中状态");
                this.mDownloadAdapter.notifyItemChanged(i2, 2);
                refreshSelectShowInfo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertSecond$21(VideoDownBean videoDownBean, int i2, ImageView imageView, View view) {
        try {
            if (!videoDownBean.isEditMode()) {
                if (TextUtils.isEmpty(videoDownBean.getVid())) {
                    NewToast.showShort(this.mContext, "直播未完成，无法选择");
                    return;
                }
                if (this.fromManage) {
                    CommonUtil.copyEncryptedFile(this.mContext);
                    Intent intent = new Intent(this.mContext, (Class<?>) AliPlayerVideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", videoDownBean.vid);
                    bundle.putBoolean("fromDownload", true);
                    bundle.putString("title", videoDownBean.mTitle);
                    bundle.putString("seeDuration", videoDownBean.getSeeDuration());
                    int i3 = videoDownBean.obj_id;
                    if (i3 != 0) {
                        bundle.putString("obj_id", String.valueOf(i3));
                    } else {
                        bundle.putString("obj_id", videoDownBean.chapter_id);
                    }
                    bundle.putString(DatabaseManager.SIZE, String.valueOf(videoDownBean.getmSize()));
                    bundle.putString("chapter_id", this.course_id);
                    bundle.putString("watch_permission", "1");
                    bundle.putString("video_title", videoDownBean.mTitle);
                    bundle.putString("pid", String.valueOf(videoDownBean.parent_id));
                    bundle.putInt("module_type", 8);
                    bundle.putSerializable("commentEnum", DiscussStatus.CourseReview);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return;
                }
                return;
            }
            videoDownBean.setSelected(videoDownBean.getSelected() == 1 ? 0 : 1);
            LogUtils.d("REFRESH", "刷新视频 " + videoDownBean.mTitle + " 图标选中状态");
            this.mDownloadAdapter.notifyItemChanged(i2, 3);
            if (videoDownBean.isNode3()) {
                for (Map.Entry<String, List<VideoDownBean>> entry : this.childVidMap.entrySet()) {
                    Iterator<VideoDownBean> it = entry.getValue().iterator();
                    while (it.hasNext()) {
                        if (TextUtils.equals(it.next().vid, videoDownBean.vid)) {
                            int i4 = 0;
                            while (true) {
                                if (i4 < this.mDataList.size()) {
                                    MultiItemEntity multiItemEntity = this.mDataList.get(i4);
                                    if ((multiItemEntity instanceof VideoDownBean) && TextUtils.isEmpty(((VideoDownBean) multiItemEntity).vid) && TextUtils.equals(entry.getKey(), ((VideoDownBean) multiItemEntity).chapter_id)) {
                                        this.mDownloadAdapter.notifyItemChanged(i4, 2);
                                        LogUtils.d("REFRESH", "刷新子章节目录 " + ((VideoDownBean) multiItemEntity).mTitle + " 图标选中状态");
                                        for (Map.Entry<String, List<VideoDownBean>> entry2 : this.chapterChildIdMap.entrySet()) {
                                            Iterator<VideoDownBean> it2 = entry2.getValue().iterator();
                                            while (true) {
                                                if (!it2.hasNext()) {
                                                    break;
                                                }
                                                if (TextUtils.equals(it2.next().getChapter_id(), entry.getKey())) {
                                                    String key = entry2.getKey();
                                                    int i5 = 0;
                                                    while (true) {
                                                        if (i5 < this.mDataList.size()) {
                                                            MultiItemEntity multiItemEntity2 = this.mDataList.get(i5);
                                                            if ((multiItemEntity2 instanceof CourseDirectoryBean) && TextUtils.equals(key, String.valueOf(((CourseDirectoryBean) multiItemEntity2).id))) {
                                                                LogUtils.d("REFRESH", "刷新外层目录 " + ((CourseDirectoryBean) multiItemEntity2).title + " 图标选中状态");
                                                                this.mDownloadAdapter.notifyItemChanged(i5, 1);
                                                                break;
                                                            }
                                                            i5++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        i4++;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                List<VideoDownBean> list = this.childVidMap.get(videoDownBean.getChapter_id());
                if (list != null) {
                    videoDownBean.setSelected(videoDownBean.getSelected() == 1 ? 0 : 1);
                    for (VideoDownBean videoDownBean2 : list) {
                        videoDownBean2.setSelected(videoDownBean.getSelected());
                        for (int i6 = 0; i6 < this.mDataList.size(); i6++) {
                            MultiItemEntity multiItemEntity3 = this.mDataList.get(i6);
                            if (multiItemEntity3 instanceof VideoDownBean) {
                                ((VideoDownBean) multiItemEntity3).setSelected(videoDownBean2.getSelected());
                                if (TextUtils.equals(videoDownBean2.vid, ((VideoDownBean) multiItemEntity3).vid)) {
                                    LogUtils.d("REFRESH", "刷新视频 " + ((VideoDownBean) multiItemEntity3).mTitle + " 图标选中状态");
                                    this.mDownloadAdapter.notifyItemChanged(i6, 3);
                                }
                            }
                        }
                    }
                    LogUtils.d("REFRESH", "刷新子章节目录 " + videoDownBean.mTitle + " 图标选中状态");
                    this.mDownloadAdapter.notifyItemChanged(i2, 2);
                    refreshSelectShowInfo(imageView, new ArrayList(list));
                    refreshSelectShowInfo();
                } else {
                    for (int i7 = 0; i7 < this.mDataList.size(); i7++) {
                        MultiItemEntity multiItemEntity4 = this.mDataList.get(i7);
                        if (multiItemEntity4 instanceof CourseDirectoryBean) {
                            String strValueOf = String.valueOf(((CourseDirectoryBean) multiItemEntity4).id);
                            if (TextUtils.equals(strValueOf, videoDownBean.parent_id) || TextUtils.equals(strValueOf, videoDownBean.chapter_id)) {
                                this.mDownloadAdapter.notifyItemChanged(i7, 1);
                                break;
                            }
                        }
                    }
                }
            }
            refreshSelectShowInfo();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertSecond$22(BaseViewHolder baseViewHolder, int i2) {
        int height = baseViewHolder.itemView.getHeight();
        Integer num = this.childRvHeightMap.get(Integer.valueOf(i2));
        if (height > 0) {
            if (num == null || num.intValue() <= 0) {
                this.childRvHeightMap.put(Integer.valueOf(i2), Integer.valueOf(height));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$downloadData$13(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$downloadData$14(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$downloadData$15(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0430  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x048b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$downloadData$16(java.lang.String r18, java.lang.String r19, java.lang.String r20) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 1174
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CourseDownloadManageFragment.lambda$downloadData$16(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$loadDbData$23(CourseDirectoryBean courseDirectoryBean, CourseDirectoryBean courseDirectoryBean2) {
        int i2 = courseDirectoryBean.id;
        int i3 = courseDirectoryBean2.id;
        if (i2 == i3) {
            return 0;
        }
        return i2 > i3 ? 1 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$loadDbData$24(BaseNode baseNode, BaseNode baseNode2) {
        VideoDownBean videoDownBean = (VideoDownBean) baseNode;
        VideoDownBean videoDownBean2 = (VideoDownBean) baseNode2;
        int i2 = (TextUtils.isEmpty(videoDownBean.chapter_id) || !videoDownBean.chapter_id.matches(RegexPool.NUMBERS)) ? 0 : Integer.parseInt(videoDownBean.chapter_id);
        int i3 = (TextUtils.isEmpty(videoDownBean2.chapter_id) || !videoDownBean2.chapter_id.matches(RegexPool.NUMBERS)) ? 0 : Integer.parseInt(videoDownBean2.chapter_id);
        if (i2 == i3) {
            return 0;
        }
        return i2 > i3 ? 1 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadDbData$25(List list) {
        List<BaseNode> notes;
        if (Build.VERSION.SDK_INT >= 24) {
            list.sort(new Comparator() { // from class: com.psychiatrygarden.fragmenthome.o3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return CourseDownloadManageFragment.lambda$loadDbData$23((CourseDirectoryBean) obj, (CourseDirectoryBean) obj2);
                }
            });
            Iterator it = list.iterator();
            while (it.hasNext()) {
                List<BaseNode> childNode = ((CourseDirectoryBean) it.next()).getChildNode();
                if (childNode != null && childNode.size() > 0) {
                    childNode.sort(new Comparator() { // from class: com.psychiatrygarden.fragmenthome.p3
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return CourseDownloadManageFragment.lambda$loadDbData$24((BaseNode) obj, (BaseNode) obj2);
                        }
                    });
                }
            }
        }
        this.mDataList.clear();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) it2.next();
            this.mDataList.add(courseDirectoryBean);
            List<BaseNode> notes2 = courseDirectoryBean.getNotes();
            if (notes2 != null && notes2.size() > 0) {
                for (Object obj : notes2) {
                    this.mDataList.add((MultiItemEntity) obj);
                    if ((obj instanceof VideoDownBean) && (notes = ((VideoDownBean) obj).getNotes()) != null) {
                        Iterator<BaseNode> it3 = notes.iterator();
                        while (it3.hasNext()) {
                            this.mDataList.add((MultiItemEntity) ((BaseNode) it3.next()));
                        }
                    }
                }
            }
        }
        fillChapterVid();
        fillChildChapter();
        fillChildVid();
        calculateItemMapPosition();
        this.mDownloadAdapter.setList(this.mDataList);
        mRefreshDownloadData();
        if (getArguments().getBoolean("editMode", false)) {
            getViewHolder().get(R.id.tv_select_all).performClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadDbData$26() {
        getViewHolder().get(R.id.ll_bottom_pannel).setVisibility(8);
        getViewHolder().get(R.id.tv_select_all).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$mRefreshDownloadData$11(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, VideoDownBean videoDownBean) {
        ProjectApp.database.getVideoDownDao().updateSizeAndProgress(aliyunDownloadMediaInfo.getVid(), aliyunDownloadMediaInfo.getSize(), aliyunDownloadMediaInfo.getProgress(), videoDownBean.mStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$12(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (this.fromCourseDetail && aliyunDownloadMediaInfo.getProgress() == 100 && !this.vidChangeList.contains(aliyunDownloadMediaInfo.getVid())) {
            this.vidChangeList.add(aliyunDownloadMediaInfo.getVid());
        }
        CurriculumDownloadManageAdapter curriculumDownloadManageAdapter = this.mDownloadAdapter;
        if (curriculumDownloadManageAdapter != null) {
            List<T> data = curriculumDownloadManageAdapter.getData();
            ArrayList arrayList = new ArrayList();
            for (T t2 : data) {
                if (t2 instanceof BaseNode) {
                    arrayList.add((BaseNode) t2);
                }
            }
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                BaseNode baseNode = (BaseNode) arrayList.get(i2);
                if (!(baseNode instanceof CourseDirectoryBean)) {
                    final VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                    long size2 = aliyunDownloadMediaInfo.getSize();
                    if (!TextUtils.isEmpty(videoDownBean.vid) && videoDownBean.getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                        if (isExistInAliDownloadList(aliyunDownloadMediaInfo.getVid()) != null) {
                            videoDownBean.setShowStatus(true);
                        }
                        videoDownBean.setmProgress(aliyunDownloadMediaInfo.getProgress());
                        if (!TextUtils.equals(videoDownBean.getDuration(), String.valueOf(aliyunDownloadMediaInfo.getDuration()))) {
                            videoDownBean.setDuration(String.valueOf(aliyunDownloadMediaInfo.getDuration()));
                        }
                        if (videoDownBean.getmSize() != size2) {
                            videoDownBean.setmSize(size2);
                        }
                        refreshVideoDownBeanStatus(aliyunDownloadMediaInfo.getStatus(), videoDownBean);
                        String savePath = aliyunDownloadMediaInfo.getSavePath();
                        if (!TextUtils.isEmpty(savePath) && aliyunDownloadMediaInfo.getProgress() == 100) {
                            File file = new File(savePath);
                            this.file = file;
                            if (file.exists() && this.file.length() > 0) {
                                videoDownBean.setmStatus(5);
                            }
                        }
                        this.mHandler.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.h3
                            @Override // java.lang.Runnable
                            public final void run() {
                                CourseDownloadManageFragment.lambda$mRefreshDownloadData$11(aliyunDownloadMediaInfo, videoDownBean);
                            }
                        });
                        this.mDownloadAdapter.notifyItemChanged(i2, "progress");
                        if (aliyunDownloadMediaInfo.getProgress() < 100) {
                            refreshSelectShowInfo();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(List list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            BaseNode baseNode = (BaseNode) list.get(i2);
            if (baseNode instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                if (!TextUtils.isEmpty(videoDownBean.vid) && videoDownBean.getSelected() == 1 && videoDownBean.getmStatus() != 5 && videoDownBean.mProgress < 100) {
                    videoDownBean.setmStatus(4);
                    this.mDownloadAdapter.notifyItemChanged(i2, "progress");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$1(MultiItemEntity multiItemEntity) {
        ProjectApp.database.getVideoDownDao().deleteData(((VideoDownBean) multiItemEntity).vid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$2(VideoDownBean videoDownBean) {
        ProjectApp.database.getVideoDownDao().deleteEmptyVideo(videoDownBean.chapter_id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$3(MultiItemEntity multiItemEntity) {
        ProjectApp.database.getCourseDirectoryDao().deleSignleData(String.valueOf(((CourseDirectoryBean) multiItemEntity).id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$4(MultiItemEntity multiItemEntity) {
        ProjectApp.database.getCourseDirectoryDao().deleSignleData(String.valueOf(((CourseDirectoryBean) multiItemEntity).id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5() {
        ProjectApp.database.getCourseCoverDao().deleSignleData(this.course_id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(View view) {
        if (this.mDataList.size() > 0) {
            ListIterator<MultiItemEntity> listIterator = this.mDataList.listIterator();
            while (listIterator.hasNext()) {
                final MultiItemEntity next = listIterator.next();
                if (next instanceof CourseDirectoryBean) {
                    boolean z2 = false;
                    for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
                        MultiItemEntity multiItemEntity = this.mDataList.get(i2);
                        if (multiItemEntity instanceof VideoDownBean) {
                            VideoDownBean videoDownBean = (VideoDownBean) multiItemEntity;
                            CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) next;
                            boolean zEquals = TextUtils.equals(videoDownBean.parent_id, String.valueOf(courseDirectoryBean.id));
                            boolean zEquals2 = TextUtils.equals(videoDownBean.chapter_id, String.valueOf(courseDirectoryBean.id));
                            if (zEquals || zEquals2) {
                                z2 = true;
                            }
                        }
                    }
                    if (!z2) {
                        new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.z2
                            @Override // java.lang.Runnable
                            public final void run() {
                                CourseDownloadManageFragment.lambda$onClick$4(next);
                            }
                        }).start();
                        listIterator.remove();
                    }
                }
            }
        }
        this.mDownloadAdapter.setList(this.mDataList);
        if (this.mDataList.isEmpty()) {
            new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.a3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15430c.lambda$onClick$5();
                }
            }).start();
            ((TextView) getViewHolder().get(R.id.title)).setText("下载管理");
            this.tvSelectAll.setVisibility(8);
            view.setEnabled(false);
            TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_black_back});
            ((ImageView) getViewHolder().get(R.id.iv_close_back)).setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            typedArrayObtainStyledAttributes.recycle();
        }
        fillChildChapter();
        fillChapterVid();
        fillChildVid();
        calculateItemMapPosition();
        EventBus.getDefault().post(new RefreshCourseDownloadStateEvent(false, ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$7(List list, List list2, final View view) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            VideoDownBean videoDownBean = ProjectApp.database.getVideoDownDao().getVideoDownBean(str);
            if (videoDownBean != null) {
                String str2 = videoDownBean.chapter_id;
                if (!TextUtils.isEmpty(str2) && !list2.contains(str2)) {
                    list2.add(str2);
                }
            }
            AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(str);
            if (aliyunDownloadMediaInfoHasAliyunVideo != null) {
                LogUtils.d(RequestParameters.SUBRESOURCE_DELETE, "删除视频文件 = " + aliyunDownloadMediaInfoHasAliyunVideo.getTitle());
                if (aliyunDownloadMediaInfoHasAliyunVideo.getProgress() < 100 || aliyunDownloadMediaInfoHasAliyunVideo.getStatus() != AliyunDownloadMediaInfo.Status.Complete) {
                    ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfoHasAliyunVideo);
                }
                ProjectApp.downloadManager.deleteFile(aliyunDownloadMediaInfoHasAliyunVideo);
            }
        }
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            List<VideoDownBean> videoDownListByChapterId = ProjectApp.database.getVideoDownDao().getVideoDownListByChapterId(str3, "course_" + this.course_id);
            if (videoDownListByChapterId == null || videoDownListByChapterId.isEmpty()) {
                LogUtils.d(RequestParameters.SUBRESOURCE_DELETE, "删除数据库记录 = 章节 id " + str3);
                ProjectApp.database.getCourseDirectoryDao().deleSignleData(str3);
            }
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.n2
            @Override // java.lang.Runnable
            public final void run() {
                this.f15860c.lambda$onClick$6(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$8(final List list, final View view) {
        Iterator<MultiItemEntity> it = this.mDataList.iterator();
        while (it.hasNext()) {
            final MultiItemEntity next = it.next();
            if (next instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) next;
                if (!TextUtils.isEmpty(videoDownBean.vid) && videoDownBean.getSelected() == 1 && videoDownBean.mProgress > 0) {
                    it.remove();
                    this.mDownloadAdapter.remove((CurriculumDownloadManageAdapter) next);
                    list.add(videoDownBean.vid);
                    LogUtils.d(RequestParameters.SUBRESOURCE_DELETE, "从列表移除视频文件  = " + videoDownBean.mTitle);
                    new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.y2
                        @Override // java.lang.Runnable
                        public final void run() {
                            CourseDownloadManageFragment.lambda$onClick$1(next);
                        }
                    }).start();
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
            MultiItemEntity multiItemEntity = this.mDataList.get(i2);
            if (multiItemEntity instanceof VideoDownBean) {
                VideoDownBean videoDownBean2 = (VideoDownBean) multiItemEntity;
                if (TextUtils.isEmpty(videoDownBean2.vid) && !checkHasChildVideo(videoDownBean2)) {
                    arrayList.add(videoDownBean2.chapter_id);
                }
            }
        }
        ListIterator<MultiItemEntity> listIterator = this.mDataList.listIterator();
        while (listIterator.hasNext()) {
            MultiItemEntity next2 = listIterator.next();
            if (next2 instanceof VideoDownBean) {
                final VideoDownBean videoDownBean3 = (VideoDownBean) next2;
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    if (TextUtils.equals((String) it2.next(), videoDownBean3.chapter_id)) {
                        Log.d(RequestParameters.SUBRESOURCE_DELETE, "删除空的子章节目录 title = " + videoDownBean3.mTitle);
                        listIterator.remove();
                        new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.i3
                            @Override // java.lang.Runnable
                            public final void run() {
                                CourseDownloadManageFragment.lambda$onClick$2(videoDownBean3);
                            }
                        }).start();
                    }
                }
            }
        }
        int i3 = 0;
        while (true) {
            if (i3 >= this.mDataList.size()) {
                break;
            }
            final MultiItemEntity multiItemEntity2 = this.mDataList.get(i3);
            if (multiItemEntity2 instanceof CourseDirectoryBean) {
                CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) multiItemEntity2;
                List<VideoDownBean> arrayList2 = this.chapterVidMap.get(String.valueOf(courseDirectoryBean.id));
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>();
                }
                boolean z2 = false;
                for (VideoDownBean videoDownBean4 : arrayList2) {
                    for (MultiItemEntity multiItemEntity3 : this.mDataList) {
                        if (multiItemEntity3 instanceof VideoDownBean) {
                            VideoDownBean videoDownBean5 = (VideoDownBean) multiItemEntity3;
                            if (!TextUtils.isEmpty(videoDownBean5.vid) && TextUtils.equals(videoDownBean5.chapter_id, videoDownBean4.chapter_id)) {
                                z2 = true;
                            }
                        }
                    }
                }
                if (!z2) {
                    this.mDownloadAdapter.remove((CurriculumDownloadManageAdapter) multiItemEntity2);
                    LogUtils.d(RequestParameters.SUBRESOURCE_DELETE, "删除空的章节目录 title = " + courseDirectoryBean.title);
                    new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.j3
                        @Override // java.lang.Runnable
                        public final void run() {
                            CourseDownloadManageFragment.lambda$onClick$3(multiItemEntity2);
                        }
                    }).start();
                    break;
                }
            }
            i3++;
        }
        if (list.size() > 0) {
            final ArrayList arrayList3 = new ArrayList();
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.k3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15708c.lambda$onClick$7(list, arrayList3, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$9(String[] strArr) {
        ActivityCompat.requestPermissions(getActivity(), strArr, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$10() {
        if (isLogin()) {
            goActivity(SetNewAvtivity.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadDbData() {
        List<CourseDirectoryBean> list;
        List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo("course_" + this.course_id);
        List<CourseDirectoryBean> courseDirectoryByCourseId = ProjectApp.database.getCourseDirectoryDao().getCourseDirectoryByCourseId(this.course_id);
        if (videoDownLoadInfo == null) {
            videoDownLoadInfo = new ArrayList<>();
        }
        if (courseDirectoryByCourseId == null) {
            courseDirectoryByCourseId = new ArrayList<>();
        }
        if (!videoDownLoadInfo.isEmpty() && courseDirectoryByCourseId.isEmpty() && (list = ProjectApp.database.getCourseDirectoryDao().getList()) != null && list.size() > 0) {
            for (CourseDirectoryBean courseDirectoryBean : list) {
                String strValueOf = String.valueOf(courseDirectoryBean.id);
                Iterator<VideoDownBean> it = videoDownLoadInfo.iterator();
                while (true) {
                    if (it.hasNext()) {
                        VideoDownBean next = it.next();
                        if (TextUtils.equals(strValueOf, next.parent_id) && TextUtils.isEmpty(next.vid)) {
                            courseDirectoryByCourseId.add(courseDirectoryBean);
                            break;
                        }
                    }
                }
            }
        }
        if (courseDirectoryByCourseId.size() <= 0 || videoDownLoadInfo.size() <= 0) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.w2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16087c.lambda$loadDbData$26();
                }
            });
            return;
        }
        if (this.mDownloadAdapter.getData().size() > 0) {
            this.mDownloadAdapter.getData().clear();
        }
        ArrayMap arrayMap = new ArrayMap();
        final ArrayList arrayList = new ArrayList();
        for (CourseDirectoryBean courseDirectoryBean2 : courseDirectoryByCourseId) {
            if (!TextUtils.isEmpty(courseDirectoryBean2.title) || !TextUtils.isEmpty(courseDirectoryBean2.pid)) {
                ArrayList arrayList2 = new ArrayList();
                for (VideoDownBean videoDownBean : videoDownLoadInfo) {
                    if (!TextUtils.isEmpty(videoDownBean.vid)) {
                        boolean z2 = false;
                        boolean z3 = TextUtils.equals(videoDownBean.parent_id, String.valueOf(courseDirectoryBean2.id)) || TextUtils.equals(videoDownBean.chapter_id, String.valueOf(courseDirectoryBean2.id));
                        VideoDownBean childVideoParent = ProjectApp.database.getVideoDownDao().getChildVideoParent(videoDownBean.chapter_id);
                        boolean z4 = childVideoParent != null && TextUtils.equals(childVideoParent.parent_id, String.valueOf(courseDirectoryBean2.getId()));
                        if (z3 || z4) {
                            AliyunDownloadMediaInfo aliyunDownloadMediaInfoIsExistInAliDownloadList = isExistInAliDownloadList(videoDownBean.vid);
                            VideoDownBean childVideoParent2 = ProjectApp.database.getVideoDownDao().getChildVideoParent(videoDownBean.chapter_id);
                            if (childVideoParent2 != null) {
                                if (!arrayMap.containsKey(videoDownBean.chapter_id)) {
                                    arrayMap.put(videoDownBean.chapter_id, childVideoParent2);
                                }
                                List<BaseNode> childNode = courseDirectoryBean2.getChildNode();
                                if (childNode != null && childNode.size() > 0) {
                                    Iterator<BaseNode> it2 = childNode.iterator();
                                    while (it2.hasNext()) {
                                        if (TextUtils.equals(((VideoDownBean) it2.next()).chapter_id, videoDownBean.chapter_id)) {
                                            z2 = true;
                                        }
                                    }
                                }
                                if (!z2) {
                                    courseDirectoryBean2.addChildren(childVideoParent2);
                                }
                                VideoDownBean videoDownBean2 = (VideoDownBean) arrayMap.get(videoDownBean.chapter_id);
                                if (videoDownBean2 != null && !TextUtils.isEmpty(videoDownBean.vid)) {
                                    videoDownBean2.addChildren(videoDownBean);
                                }
                                videoDownBean.setNode3(true);
                            } else {
                                courseDirectoryBean2.addChildren(videoDownBean);
                            }
                            if (!TextUtils.isEmpty(videoDownBean.vid)) {
                                arrayList2.add(videoDownBean);
                            }
                            if (aliyunDownloadMediaInfoIsExistInAliDownloadList != null) {
                                videoDownBean.setmSize(aliyunDownloadMediaInfoIsExistInAliDownloadList.getSize());
                                videoDownBean.setDuration(String.valueOf(aliyunDownloadMediaInfoIsExistInAliDownloadList.getDuration()));
                                videoDownBean.setmProgress(aliyunDownloadMediaInfoIsExistInAliDownloadList.getProgress());
                                if (TextUtils.isEmpty(videoDownBean.mTitle)) {
                                    videoDownBean.mTitle = aliyunDownloadMediaInfoIsExistInAliDownloadList.getTitle();
                                }
                                refreshVideoDownBeanStatus(aliyunDownloadMediaInfoIsExistInAliDownloadList.getStatus(), videoDownBean);
                                if (aliyunDownloadMediaInfoIsExistInAliDownloadList.getProgress() == 100 && aliyunDownloadMediaInfoIsExistInAliDownloadList.getStatus() != AliyunDownloadMediaInfo.Status.Complete) {
                                    String savePath = aliyunDownloadMediaInfoIsExistInAliDownloadList.getSavePath();
                                    if (!TextUtils.isEmpty(savePath)) {
                                        File file = new File(savePath);
                                        if (file.exists() && file.length() > 0) {
                                            videoDownBean.setmStatus(5);
                                            Iterator<AliyunDownloadMediaInfo> it3 = YkBManager.getInstance().mDownloadMediaLists.iterator();
                                            while (true) {
                                                if (!it3.hasNext()) {
                                                    break;
                                                }
                                                AliyunDownloadMediaInfo next2 = it3.next();
                                                if (next2.getVid().equals(videoDownBean.vid)) {
                                                    next2.setStatus(AliyunDownloadMediaInfo.Status.Complete);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            LogUtils.d("add_child", "pid = " + courseDirectoryBean2.id + " video pid = " + videoDownBean.parent_id);
                        }
                    }
                }
                if (arrayList2.size() > 0) {
                    if (TextUtils.isEmpty(courseDirectoryBean2.title)) {
                        courseDirectoryBean2.setTitle(this.courseTitle);
                    }
                    arrayList.add(courseDirectoryBean2);
                    courseDirectoryBean2.setVideoDownBeans(arrayList2);
                }
            }
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.v2
            @Override // java.lang.Runnable
            public final void run() {
                this.f16059c.lambda$loadDbData$25(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.fragmenthome.t2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f16004a.lambda$mRefreshDownloadData$12((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    private void refreshSelectShowInfo() {
        int i2;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        for (BaseNode baseNode : convertData(this.mDownloadAdapter.getData())) {
            if (!(baseNode instanceof CourseDirectoryBean)) {
                VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                if (!TextUtils.isEmpty(videoDownBean.vid)) {
                    i4++;
                    if (videoDownBean.getSelected() == 1) {
                        i3++;
                    }
                    if (videoDownBean.getSelected() == 1 && videoDownBean.mProgress > 0) {
                        i6++;
                    }
                    if (videoDownBean.getSelected() == 1 && (i2 = videoDownBean.mProgress) > 0 && i2 < 100) {
                        i7++;
                    }
                    if (videoDownBean.getmStatus() == 5 && videoDownBean.getSelected() == 1) {
                        i5++;
                    }
                    if (videoDownBean.getmStatus() != 5 && videoDownBean.getSelected() == 1 && videoDownBean.mProgress < 100) {
                        i8++;
                    }
                }
            }
        }
        TextView textView = (TextView) getViewHolder().get(R.id.title);
        this.selectAll = i3 == i4;
        if (this.isEditMode) {
            textView.setText(String.format("已选择%d个文件", Integer.valueOf(i3)));
            this.tvSelectAll.setText(this.selectAll ? "取消全选" : "全选");
        } else {
            textView.setText("下载管理");
            this.tvSelectAll.setText("编辑");
        }
        getViewHolder().get(R.id.tv_delete).setEnabled(i6 > 0 || i5 > 0);
        getViewHolder().get(R.id.tv_pause).setEnabled(i7 > 0);
        getViewHolder().get(R.id.tv_download).setEnabled(i8 > 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshVideoDownBeanStatus(AliyunDownloadMediaInfo.Status status, VideoDownBean videoDownBean) {
        switch (AnonymousClass3.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[status.ordinal()]) {
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
            case 8:
                videoDownBean.setmStatus(0);
                videoDownBean.setmProgress(0);
                return;
            default:
                return;
        }
        videoDownBean.setmStatus(1);
    }

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.fragmenthome.u2
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f16030a.lambda$showOpenDownloadDialog$10();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    private void updateEditMode(boolean isEditMode) {
        List<BaseNode> notes;
        for (BaseNode baseNode : convertData(this.mDownloadAdapter.getData())) {
            if (baseNode instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                videoDownBean.setEditMode(isEditMode);
                if (TextUtils.isEmpty(videoDownBean.vid) && (notes = videoDownBean.getNotes()) != null && notes.size() > 0) {
                    Iterator<BaseNode> it = notes.iterator();
                    while (it.hasNext()) {
                        ((VideoDownBean) it.next()).setEditMode(isEditMode);
                    }
                }
            } else {
                ((CourseDirectoryBean) baseNode).setEditMode(isEditMode);
            }
        }
        this.mDownloadAdapter.notifyDataSetChanged();
    }

    public void collOrExpandList(int select) {
        CurriculumDownloadManageAdapter curriculumDownloadManageAdapter = this.mDownloadAdapter;
        if (curriculumDownloadManageAdapter == null || curriculumDownloadManageAdapter.getData().size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
            MultiItemEntity multiItemEntity = this.mDataList.get(i2);
            if (multiItemEntity instanceof CourseDirectoryBean) {
                ((CourseDirectoryBean) multiItemEntity).setSelected(select);
            } else if (multiItemEntity instanceof VideoDownBean) {
                ((VideoDownBean) multiItemEntity).setSelected(select);
            }
        }
        this.mDownloadAdapter.notifyDataSetChanged();
    }

    public void downloadData(final String acId, final String akSceret, final String securityToken) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.g3
            @Override // java.lang.Runnable
            public final void run() throws NumberFormatException {
                this.f15609c.lambda$downloadData$16(acId, akSceret, securityToken);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_course_download_manage;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.tvTitle = (TextView) holder.get(R.id.title);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.course_id = arguments.getString("course_id", "0");
            this.fromManage = arguments.getBoolean("manage", false);
            this.courseCover = arguments.getString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
            this.courseTitle = arguments.getString("title");
            this.fromCourseDetail = arguments.getBoolean("fromCourseDetail", false);
            this.fromCourseDirectory = arguments.getBoolean("fromCourseDirectory", false);
        }
        ImageView imageView = (ImageView) holder.get(R.id.iv_close_back);
        this.tvSelectAll = (TextView) holder.get(R.id.tv_select_all);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycledown);
        holder.get(R.id.tv_delete).setOnClickListener(this);
        holder.get(R.id.tv_pause).setOnClickListener(this);
        imageView.setOnClickListener(this);
        CurriculumDownloadManageAdapter curriculumDownloadManageAdapter = new CurriculumDownloadManageAdapter();
        this.mDownloadAdapter = curriculumDownloadManageAdapter;
        curriculumDownloadManageAdapter.setList(this.mDataList);
        recyclerView.setAdapter(this.mDownloadAdapter);
        this.mDownloadAdapter.setEmptyView(R.layout.layout_empty_view);
        if (recyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            recyclerView.setItemAnimator(null);
        }
        this.tvSelectAll.setOnClickListener(this);
        holder.get(R.id.tv_download).setOnClickListener(this);
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        if (!this.fromManage) {
            this.tvTitle.setText("已选择0个文件");
        }
        if (this.fromCourseDirectory) {
            holder.get(R.id.editLayout).setVisibility(8);
            holder.get(R.id.ll_bottom_pannel).setVisibility(8);
            recyclerView.setPadding(recyclerView.getPaddingLeft(), 0, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
            marginLayoutParams.topMargin = 0;
            recyclerView.setLayoutParams(marginLayoutParams);
        }
        getCourseListData();
    }

    public AliyunDownloadMediaInfo isExistInAliDownloadList(String vid) {
        if (vid != null && YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
                if (vid.equals(aliyunDownloadMediaInfo.getVid())) {
                    return aliyunDownloadMediaInfo;
                }
            }
        }
        return null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(final View v2) {
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo;
        if (v2.getId() == R.id.tv_pause) {
            final List<BaseNode> listConvertData = convertData(this.mDownloadAdapter.getData());
            for (int i2 = 0; i2 < listConvertData.size(); i2++) {
                BaseNode baseNode = listConvertData.get(i2);
                if (baseNode instanceof VideoDownBean) {
                    VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                    if (!TextUtils.isEmpty(videoDownBean.vid) && videoDownBean.getSelected() == 1 && videoDownBean.getmStatus() != 5 && videoDownBean.mProgress < 100 && (aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(videoDownBean.vid)) != null) {
                        ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfoHasAliyunVideo, false);
                    }
                }
            }
            v2.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.l3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15803c.lambda$onClick$0(listConvertData);
                }
            }, 1000L);
            return;
        }
        if (v2.getId() == R.id.tv_delete) {
            final ArrayList arrayList = new ArrayList();
            new XPopup.Builder(this.mContext).asCustom(new DeleteDownloadDialog(this.mContext, new DeleteDownloadDialog.ClickIml() { // from class: com.psychiatrygarden.fragmenthome.m3
                @Override // com.psychiatrygarden.widget.DeleteDownloadDialog.ClickIml
                public final void mClickIml() {
                    this.f15826a.lambda$onClick$8(arrayList, v2);
                }
            }, new SpannableStringBuilder("是否删除所选，已下载的会删除下载到本地的文件?"), "取消", "确定")).show();
            return;
        }
        if (v2.getId() == R.id.tv_download) {
            final String[] strArr = {Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE};
            if (!CommonUtil.hasRequiredPermissionsWriteStorage(getActivity())) {
                new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.n3
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f15862a.lambda$onClick$9(strArr);
                    }
                })).show();
                return;
            }
            if (!CommonUtil.isNetworkConnected(this.mContext)) {
                NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
                return;
            } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
                getCourseAk();
                return;
            } else {
                showOpenDownloadDialog();
                return;
            }
        }
        if (v2.getId() != R.id.tv_select_all) {
            if (v2.getId() == R.id.iv_close_back) {
                if (this.mDownloadAdapter.getData().isEmpty() || !this.fromManage) {
                    getActivity().finish();
                    return;
                }
                if (!this.isEditMode) {
                    getActivity().finish();
                    return;
                }
                this.isEditMode = false;
                this.tvSelectAll.setText("编辑");
                updateEditMode(false);
                collOrExpandList(0);
                refreshSelectShowInfo();
                getViewHolder().get(R.id.ll_bottom_pannel).setVisibility(8);
                TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_black_back});
                ((ImageView) getViewHolder().get(R.id.iv_close_back)).setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
                typedArrayObtainStyledAttributes.recycle();
                this.selectAll = false;
                ((TextView) getViewHolder().get(R.id.title)).setText(this.fromManage ? "下载管理" : "已选择0个文件");
                return;
            }
            return;
        }
        if (this.mDownloadAdapter.getData().isEmpty()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes2 = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_black_back, R.attr.ic_nav_close});
        if (this.isEditMode) {
            typedArrayObtainStyledAttributes2.recycle();
            if (this.selectAll || TextUtils.equals("取消全选", this.tvSelectAll.getText())) {
                collOrExpandList(0);
                this.tvSelectAll.setText("全选");
                refreshSelectShowInfo();
            } else {
                collOrExpandList(1);
                this.tvSelectAll.setText("取消全选");
                refreshSelectShowInfo();
            }
            refreshSelectShowInfo();
            return;
        }
        this.isEditMode = true;
        this.tvSelectAll.setText("全选");
        this.tvSelectAll.setSelected(true);
        ((ImageView) getViewHolder().get(R.id.iv_close_back)).setImageDrawable(typedArrayObtainStyledAttributes2.getDrawable(1));
        this.selectAll = false;
        updateEditMode(true);
        collOrExpandList(0);
        this.tvSelectAll.setEnabled(true);
        getViewHolder().get(R.id.ll_bottom_pannel).setVisibility(0);
        this.tvTitle.setText("已选择0个文件");
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        if (!this.fromManage || this.mDataList.isEmpty()) {
            EventBus.getDefault().post(new RefreshCourseDownloadedEvent(this.vidChangeList));
        } else {
            EventBus.getDefault().post(new RefreshCourseDownloadStateEvent(false, NotifyType.LIGHTS));
        }
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(RefreshCourseDownloadedEvent e2) {
        this.fromManage = true;
        getCourseListData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Permission.WRITE_EXTERNAL_STORAGE)) {
                return;
            }
            ToastUtil.shortToast(this.mContext, "无法下载，请检查app存储权限是否打开！");
        } else if (requestCode == 1 && grantResults[0] == 0) {
            if (!CommonUtil.isNetworkConnected(this.mContext)) {
                NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
            } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
                getCourseAk();
            } else {
                showOpenDownloadDialog();
            }
        }
    }

    public void refreshSelectShowInfo(ImageView selectTv, List<BaseNode> childNode) {
        if (childNode == null || childNode.isEmpty()) {
            return;
        }
        int size = childNode.size();
        int i2 = 0;
        for (BaseNode baseNode : childNode) {
            if (baseNode instanceof VideoDownBean) {
                VideoDownBean videoDownBean = (VideoDownBean) baseNode;
                if (videoDownBean.getSelected() == 1 && !TextUtils.isEmpty(videoDownBean.vid)) {
                    i2++;
                }
            }
        }
        if (size == i2) {
            selectTv.setSelected(true);
            TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{R.attr.download_select});
            selectTv.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        if (i2 > 0) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                selectTv.setImageResource(R.drawable.download_part_select_night);
                return;
            } else {
                selectTv.setImageResource(R.drawable.download_part_select);
                return;
            }
        }
        selectTv.setSelected(false);
        TypedArray typedArrayObtainStyledAttributes2 = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.download_new_not_select});
        selectTv.setImageDrawable(typedArrayObtainStyledAttributes2.getDrawable(0));
        typedArrayObtainStyledAttributes2.recycle();
    }
}
