package com.psychiatrygarden.activity.courselist.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.activity.CourseSurveyActivity;
import com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshVideoProgressEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.CourseDataSpUtilKt;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LiveStatus;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.CommentCoursePop;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDirectoryListFragment extends BaseFragment {
    private static final int EXPAND_COLLAPSE_PAYLOAD = 110;
    public static final String EXTRA_DATA_COLLECT = "extra.data.collect";
    public static final String EXTRA_DATA_PAGE_TYPE = "extra.data.page.type";
    private static final String TAG = "CourseDirectoryListFragment";
    private TreeNodeAdapter<CourseDirectoryTreeBean> adapter;
    private int appThemeRedColor;
    private String courseId;
    private String courseType;
    private List<CourseDirectoryItemData> curListData;
    private CustomEmptyView emptyView;
    private String expandFirstId;
    private String expandSecondId;
    private int firstColor;
    private TreeNode<CourseDirectoryTreeBean> firstExpandTree;
    private int positionUpdate;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private int secondColor;
    private TreeNode<CourseDirectoryTreeBean> secondExpandTree;
    private int thirdColor;
    private int waitColor;
    private int position = 0;
    private List<TreeNode<CourseDirectoryTreeBean>> list = new ArrayList();
    private String updateChapterId = "";
    private String lastLearnVid = "";
    private String lastLearnChapterId = "";
    private List<String> lastVideoIdChapter = new ArrayList();
    private final HashMap<String, String> allMap = new HashMap<>();
    private final HashMap<String, String> haveVidMap = new HashMap<>();
    private final HashMap<String, String> seeMap = new HashMap<>();
    private final HashMap<String, Boolean> freeMap = new HashMap<>();
    private int allSeeCount = 0;
    private int allHaveVidCount = 0;
    private String pageType = "0";
    private boolean isShowDialog = false;

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        final /* synthetic */ List val$data;

        public AnonymousClass4(final List val$data) {
            this.val$data = val$data;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(boolean z2, String str, CourseDirectoryContentItem courseDirectoryContentItem, List list, View view) {
            if (z2) {
                CourseDirectoryListFragment courseDirectoryListFragment = CourseDirectoryListFragment.this;
                courseDirectoryListFragment.toastOnUiThread(courseDirectoryListFragment.getString(R.string.videoWaitUpdate));
                return;
            }
            if (!"2".equals(str)) {
                if (!"1".equals(str) || TextUtils.isEmpty(courseDirectoryContentItem.getVid())) {
                    return;
                }
                TreeNodeUtilKt.initWaitPlayList(list, courseDirectoryContentItem.getVid());
                CourseDirectoryListFragment.this.getContext().sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
                NavigationUtilKt.goToAliPlayerVideoPlayActivity(CourseDirectoryListFragment.this.getContext(), courseDirectoryContentItem.getObj_id(), courseDirectoryContentItem.getCourse_id(), courseDirectoryContentItem.getVid(), courseDirectoryContentItem.getCover(), courseDirectoryContentItem.getType());
                return;
            }
            int i2 = AnonymousClass9.$SwitchMap$com$psychiatrygarden$utils$LiveStatus[TreeNodeUtilKt.getLivingStatus(courseDirectoryContentItem.getStart_live_time(), courseDirectoryContentItem.getEnd_live_time(), courseDirectoryContentItem.getVid()).ordinal()];
            if (i2 == 1) {
                TreeNodeUtilKt.initWaitPlayList(list, courseDirectoryContentItem.getVid());
                CourseDirectoryListFragment.this.getContext().sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
                NavigationUtilKt.goToAliPlayerVideoPlayActivity(CourseDirectoryListFragment.this.getContext(), courseDirectoryContentItem.getObj_id(), courseDirectoryContentItem.getCourse_id(), courseDirectoryContentItem.getVid(), courseDirectoryContentItem.getCover(), courseDirectoryContentItem.getType());
            } else {
                if (i2 == 2) {
                    if (DateTimeUtilKt.timeWithinHalfAnHour(courseDirectoryContentItem.getStart_live_time())) {
                        CommonUtil.launchLiving(CourseDirectoryListFragment.this.getContext(), courseDirectoryContentItem.getUser_id(), courseDirectoryContentItem.getApp_id(), courseDirectoryContentItem.getApp_secret(), courseDirectoryContentItem.getRoom_id(), courseDirectoryContentItem.getCourse_id(), courseDirectoryContentItem.getObj_id());
                        return;
                    } else {
                        CourseDirectoryListFragment courseDirectoryListFragment2 = CourseDirectoryListFragment.this;
                        courseDirectoryListFragment2.toastOnUiThread(courseDirectoryListFragment2.getContext().getString(R.string.livingNoBegin));
                        return;
                    }
                }
                if (i2 == 3) {
                    CommonUtil.launchLiving(CourseDirectoryListFragment.this.getContext(), courseDirectoryContentItem.getUser_id(), courseDirectoryContentItem.getApp_id(), courseDirectoryContentItem.getApp_secret(), courseDirectoryContentItem.getRoom_id(), courseDirectoryContentItem.getCourse_id(), courseDirectoryContentItem.getObj_id());
                } else {
                    if (i2 != 4) {
                        return;
                    }
                    CourseDirectoryListFragment courseDirectoryListFragment3 = CourseDirectoryListFragment.this;
                    courseDirectoryListFragment3.toastOnUiThread(courseDirectoryListFragment3.getContext().getString(R.string.livingCutting));
                }
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) throws NumberFormatException {
            TextView textView;
            boolean zEquals;
            boolean zIsDownload;
            int i2;
            CourseDirectoryContentItem courseDirectoryContentItem;
            String str;
            int i3;
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) CourseDirectoryListFragment.this).mContext, 16.0f);
            Log.d(CourseDirectoryListFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getContentItem().getTitle());
            View view = holder.getView(R.id.itemThirdViewTag);
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemRootThird);
            ImageView imageView = (ImageView) holder.getView(R.id.itemThirdImgCourseType);
            ImageView imageView2 = (ImageView) holder.getView(R.id.itemThirdImgCourseAnim);
            TextView textView2 = (TextView) holder.getView(R.id.itemThirdTvCourseTitle);
            TextView textView3 = (TextView) holder.getView(R.id.tvLastLearn);
            TextView textView4 = (TextView) holder.getView(R.id.tvTeacherName);
            ImageView imageView3 = (ImageView) holder.getView(R.id.itemThirdImgCourseDownload);
            TextView textView5 = (TextView) holder.getView(R.id.tvDownload);
            TextView textView6 = (TextView) holder.getView(R.id.tvDate);
            TextView textView7 = (TextView) holder.getView(R.id.tvTime);
            TextView textView8 = (TextView) holder.getView(R.id.tvLearnProgress);
            TextView textView9 = (TextView) holder.getView(R.id.tvLiveStatus);
            RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            View view2 = holder.getView(R.id.thirdItemLine);
            CourseDirectoryContentItem contentItem = treeNode.getValue().getContentItem();
            final boolean zTreeNodeIsWaitPush = TreeNodeUtilKt.treeNodeIsWaitPush(treeNode);
            String waitPushTimeFormat = zTreeNodeIsWaitPush ? TreeNodeUtilKt.getWaitPushTimeFormat(TreeNodeUtilKt.getTreeNodeWaitPushTimeStr(treeNode)) : "";
            if (TextUtils.isEmpty(contentItem.getVid())) {
                textView = textView6;
                zEquals = false;
                zIsDownload = false;
            } else {
                if (TextUtils.isEmpty(contentItem.getChapter_id())) {
                    textView = textView6;
                    zEquals = CourseDirectoryListFragment.this.lastLearnVid.equals(contentItem.getVid());
                } else {
                    textView = textView6;
                    zEquals = CourseDirectoryListFragment.this.lastLearnVid.equals(contentItem.getVid()) && CourseDirectoryListFragment.this.lastLearnChapterId.equals(contentItem.getChapter_id());
                }
                zIsDownload = TreeNodeUtilKt.isDownload(contentItem.getVid());
            }
            boolean z2 = zEquals;
            if (TreeNodeUtilKt.haveSecondPresent(treeNode)) {
                view.setVisibility(0);
                relativeLayout2.setPadding(iDip2px, 0, iDip2px, 0);
                if (TreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                    view2.setVisibility(4);
                    relativeLayout2.setBackground(CourseDirectoryListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) CourseDirectoryListFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                    view2.setVisibility(0);
                    relativeLayout2.setBackground(CourseDirectoryListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) CourseDirectoryListFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                    view2.setVisibility(4);
                    relativeLayout2.setBackground(CourseDirectoryListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) CourseDirectoryListFragment.this).mContext.getTheme()));
                } else {
                    view2.setVisibility(0);
                    relativeLayout2.setBackground(CourseDirectoryListFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) CourseDirectoryListFragment.this).mContext.getTheme()));
                }
            } else {
                if (TreeNodeUtilKt.isFirstItem(treeNode)) {
                    view.setVisibility(8);
                    i2 = 0;
                } else {
                    i2 = 0;
                    view.setVisibility(0);
                }
                relativeLayout2.setBackground(null);
                relativeLayout2.setPadding(i2, i2, i2, i2);
            }
            textView2.setText(contentItem.getTitle());
            CourseDirectoryContentItem contentItem2 = treeNode.getValue().getContentItem();
            String teacher_name = contentItem2.getTeacher_name();
            if (TextUtils.isEmpty(teacher_name) || "1".equals(contentItem2.getIs_hide_teacher())) {
                textView4.setVisibility(8);
            } else {
                textView4.setVisibility(0);
                textView4.setText(teacher_name);
            }
            String type = contentItem2.getType();
            if ("2".equals(type)) {
                textView3.setVisibility(8);
                imageView3.setVisibility(zIsDownload ? 0 : 8);
                textView5.setVisibility(zIsDownload ? 0 : 8);
                textView8.setVisibility(8);
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                String start_live_time = contentItem2.getStart_live_time();
                String end_live_time = contentItem2.getEnd_live_time();
                long j2 = Long.parseLong(start_live_time);
                long j3 = Long.parseLong(end_live_time);
                if (((Animatable) imageView2.getDrawable()).isRunning()) {
                    ((Animatable) imageView2.getDrawable()).stop();
                }
                imageView2.setVisibility(8);
                imageView.setVisibility(0);
                if (zTreeNodeIsWaitPush) {
                    textView2.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    TextView textView10 = textView;
                    textView10.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    textView9.setText("等待更新");
                    textView9.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    textView10.setText(waitPushTimeFormat);
                    imageView.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.icon_live_not_start_night : R.drawable.icon_live_not_start_day);
                    textView10.setVisibility(0);
                    textView7.setVisibility(8);
                    textView9.setVisibility(0);
                    textView4.setTextColor(CourseDirectoryListFragment.this.waitColor);
                } else {
                    TextView textView11 = textView;
                    if (jCurrentTimeMillis < j2) {
                        String[] livingDate = TreeNodeUtilKt.getLivingDate(start_live_time, end_live_time);
                        textView11.setText(livingDate[0]);
                        textView7.setText(livingDate[1]);
                        textView2.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        textView11.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        textView7.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        textView9.setText("即将直播");
                        textView9.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        textView4.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        imageView.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.icon_live_not_start_night : R.drawable.icon_live_not_start_day);
                        textView11.setVisibility(0);
                        textView7.setVisibility(0);
                        textView9.setVisibility(0);
                    } else if (jCurrentTimeMillis < j3) {
                        imageView2.setVisibility(0);
                        imageView2.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.living_anim3_night : R.drawable.living_anim3_day);
                        ((Animatable) imageView2.getDrawable()).start();
                        imageView.setVisibility(8);
                        String[] livingDate2 = TreeNodeUtilKt.getLivingDate(start_live_time, end_live_time);
                        textView11.setText(livingDate2[0]);
                        textView7.setText(livingDate2[1]);
                        textView9.setText("正在直播");
                        textView9.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                        textView2.setTextColor(CourseDirectoryListFragment.this.firstColor);
                        textView11.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView7.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView4.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView11.setVisibility(0);
                        textView7.setVisibility(0);
                        textView9.setVisibility(0);
                    } else if (TextUtils.isEmpty(contentItem2.getVid())) {
                        imageView.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.icon_live_cutting_night : R.drawable.icon_live_cutting_day);
                        String[] livingDate3 = TreeNodeUtilKt.getLivingDate(start_live_time, end_live_time);
                        textView11.setText(livingDate3[0]);
                        textView7.setText(livingDate3[1]);
                        textView9.setText("直播结束");
                        textView2.setTextColor(CourseDirectoryListFragment.this.firstColor);
                        textView11.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView7.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView9.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView4.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView9.setVisibility(0);
                        textView11.setVisibility(0);
                        textView7.setVisibility(0);
                    } else {
                        textView2.setTextColor(CourseDirectoryListFragment.this.firstColor);
                        imageView.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.icon_live_video_night : R.drawable.icon_live_video_day);
                        String[] livingDate4 = TreeNodeUtilKt.getLivingDate(start_live_time, end_live_time);
                        if (TreeNodeUtilKt.timeIsCurrentYear(start_live_time)) {
                            i3 = 0;
                            textView11.setText(livingDate4[0]);
                        } else {
                            i3 = 0;
                            textView11.setText(livingDate4[0].substring(2));
                        }
                        textView11.setVisibility(i3);
                        textView7.setVisibility(8);
                        textView11.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView7.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView4.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        if (z2) {
                            textView3.setVisibility(0);
                            textView3.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                        } else {
                            textView3.setVisibility(8);
                        }
                        String percentStr = TreeNodeUtilKt.getPercentStr(contentItem.getSee(), contentItem.getDuration(), true);
                        textView8.setText(percentStr);
                        textView8.setTextColor("已完成".equals(percentStr) ? CourseDirectoryListFragment.this.firstColor : CourseDirectoryListFragment.this.appThemeRedColor);
                        textView8.setVisibility(0);
                        textView9.setVisibility(8);
                    }
                }
                str = type;
                courseDirectoryContentItem = contentItem;
            } else {
                TextView textView12 = textView;
                courseDirectoryContentItem = contentItem;
                if ("1".equals(type)) {
                    if (((Animatable) imageView2.getDrawable()).isRunning()) {
                        ((Animatable) imageView2.getDrawable()).stop();
                    }
                    str = type;
                    imageView2.setVisibility(8);
                    imageView.setVisibility(0);
                    if (zTreeNodeIsWaitPush) {
                        textView2.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        imageView.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.icon_record_video_update_night : R.drawable.icon_record_video_update_day);
                        textView3.setVisibility(8);
                        imageView3.setVisibility(8);
                        textView5.setVisibility(8);
                        textView12.setText(waitPushTimeFormat);
                        textView12.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        textView12.setVisibility(0);
                        textView7.setVisibility(8);
                        textView4.setTextColor(CourseDirectoryListFragment.this.waitColor);
                        textView8.setVisibility(8);
                        textView9.setText("等待更新");
                        textView9.setVisibility(0);
                        textView9.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    } else {
                        textView2.setTextColor(CourseDirectoryListFragment.this.firstColor);
                        imageView.setImageResource(CourseDirectoryListFragment.this.isNightTheme() ? R.drawable.icon_record_video_night : R.drawable.icon_record_video_day);
                        textView3.setVisibility(z2 ? 0 : 8);
                        textView3.setText("上次学习");
                        textView3.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                        imageView3.setVisibility(zIsDownload ? 0 : 8);
                        textView5.setVisibility(zIsDownload ? 0 : 8);
                        textView12.setVisibility(8);
                        textView12.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        textView4.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                        if (TextUtils.isEmpty(contentItem2.getDuration())) {
                            textView7.setVisibility(8);
                        } else {
                            textView7.setText((((int) Double.parseDouble(contentItem2.getDuration())) / 60) + " 分钟");
                            textView7.setTextColor(CourseDirectoryListFragment.this.thirdColor);
                            textView7.setVisibility(0);
                        }
                        if ("1".equals(contentItem2.getIs_end())) {
                            textView8.setText("已完成");
                            textView8.setTextColor(CourseDirectoryListFragment.this.firstColor);
                        } else {
                            String percentStr2 = TreeNodeUtilKt.getPercentStr(contentItem2.getSee(), contentItem2.getDuration(), true);
                            textView8.setText(percentStr2);
                            textView8.setTextColor("已完成".equals(percentStr2) ? CourseDirectoryListFragment.this.firstColor : CourseDirectoryListFragment.this.appThemeRedColor);
                        }
                        textView8.setVisibility(0);
                        textView9.setVisibility(8);
                    }
                } else {
                    str = type;
                }
            }
            final List list = this.val$data;
            final String str2 = str;
            final CourseDirectoryContentItem courseDirectoryContentItem2 = courseDirectoryContentItem;
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.q0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f12017c.lambda$convert$0(zTreeNodeIsWaitPush, str2, courseDirectoryContentItem2, list, view3);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_directory_third;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 2;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment$9, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$utils$LiveStatus;

        static {
            int[] iArr = new int[LiveStatus.values().length];
            $SwitchMap$com$psychiatrygarden$utils$LiveStatus = iArr;
            try {
                iArr[LiveStatus.HAVE_VID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.NO_BEGIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.LIVING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.CUTTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void getAllData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("type", this.courseType);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryCombine, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryListFragment.this.emptyView.setLoadFileResUi(CourseDirectoryListFragment.this.getContext());
                CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass7) t2);
                try {
                    CourseDirectoryListFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.7.1
                        }.getType());
                        if (list == null || list.isEmpty()) {
                            CourseDirectoryListFragment.this.emptyView.uploadEmptyViewResUi();
                            CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                            CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
                        } else {
                            CourseDirectoryListFragment.this.curListData = list;
                            CourseDirectoryListFragment.this.initRecyclerView();
                        }
                    } else {
                        CourseDirectoryListFragment.this.emptyView.uploadEmptyViewResUi();
                        CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                        CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    Log.e(CourseDirectoryListFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    CourseDirectoryListFragment.this.emptyView.setLoadFileResUi(CourseDirectoryListFragment.this.getContext());
                    CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                    CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
                }
            }
        });
    }

    private void getCollectData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("type", this.courseType);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryCollect, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryListFragment.this.emptyView.setLoadFileResUi(CourseDirectoryListFragment.this.getContext());
                CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass6) t2);
                try {
                    CourseDirectoryListFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.6.1
                        }.getType());
                        if (list == null || list.isEmpty()) {
                            CourseDirectoryListFragment.this.emptyView.uploadEmptyViewResUi();
                            CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                            CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
                        } else {
                            CourseDirectoryListFragment.this.curListData = list;
                            CourseDirectoryListFragment.this.initRecyclerView();
                        }
                    } else {
                        CourseDirectoryListFragment.this.emptyView.uploadEmptyViewResUi();
                        CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                        CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    Log.e(CourseDirectoryListFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    CourseDirectoryListFragment.this.emptyView.setLoadFileResUi(CourseDirectoryListFragment.this.getContext());
                    CourseDirectoryListFragment.this.emptyView.setVisibility(0);
                    CourseDirectoryListFragment.this.recyclerView.setVisibility(8);
                }
            }
        });
    }

    private void getCollectNotify() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof CourseDirectoryNewFragment) {
            List<CourseDirectoryItemData> collectData = ((CourseDirectoryNewFragment) parentFragment).getCollectData();
            this.curListData.clear();
            this.curListData.addAll(collectData);
            if (this.curListData.isEmpty()) {
                return;
            }
            initRecyclerView();
        }
    }

    private void getListData() {
        if ("1".equals(this.pageType)) {
            getCollectData();
        } else {
            getAllData();
        }
    }

    private void initColor() {
        if (getContext() != null) {
            if (isNightTheme()) {
                this.waitColor = getContext().getColor(R.color.forth_txt_color_night);
                this.firstColor = getContext().getColor(R.color.first_txt_color_night);
                this.thirdColor = getContext().getColor(R.color.third_txt_color_night);
                this.appThemeRedColor = getContext().getColor(R.color.main_theme_color_night);
                this.secondColor = getContext().getColor(R.color.second_txt_color_night);
                return;
            }
            this.waitColor = getContext().getColor(R.color.forth_txt_color);
            this.firstColor = getContext().getColor(R.color.first_txt_color);
            this.thirdColor = getContext().getColor(R.color.third_txt_color);
            this.appThemeRedColor = getContext().getColor(R.color.main_theme_color);
            this.secondColor = getContext().getColor(R.color.second_txt_color);
        }
    }

    private void initLastLearnId() {
        this.lastLearnVid = CourseDataSpUtilKt.readLastLearnVid(this.courseId, getActivity(), "");
        this.lastLearnChapterId = CourseDataSpUtilKt.readLastLearnChapterId(this.courseId, getActivity(), "");
    }

    private void initListLayout(List<CourseDirectoryItemData> data) {
        this.positionUpdate = TreeNodeUtilKt.getPositionByChapterId(data, this.updateChapterId);
        setCourseMsg(data);
        this.list = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData2(data, this.expandFirstId, this.expandSecondId);
        this.lastVideoIdChapter = TreeNodeUtilKt.getChapterIdByVideoId(data, this.lastLearnVid, this.lastLearnChapterId);
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new TreeNodeDelegate<CourseDirectoryTreeBean>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.2
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
                TextView textView = (TextView) holder.getView(R.id.itemFirstTitle);
                TextView textView2 = (TextView) holder.getView(R.id.itemFirstCount);
                ImageView imageView = (ImageView) holder.getView(R.id.itemArrow);
                TextView textView3 = (TextView) holder.getView(R.id.itemTvFirstProgress);
                TextView textView4 = (TextView) holder.getView(R.id.itemTvLaseLearn);
                CourseDirectoryItemData item = treeNode.getValue().getItem();
                String chapter_id = item.getChapter_id();
                boolean zTreeNodeIsWaitPush = TreeNodeUtilKt.treeNodeIsWaitPush(treeNode);
                String waitPushTimeFormat = zTreeNodeIsWaitPush ? TreeNodeUtilKt.getWaitPushTimeFormat(TreeNodeUtilKt.getTreeNodeWaitPushTimeStr(treeNode)) : "";
                if (TextUtils.isEmpty(chapter_id)) {
                    return;
                }
                textView.setText(item.getTitle());
                CourseDirectoryListFragment courseDirectoryListFragment = CourseDirectoryListFragment.this;
                textView.setTextColor(zTreeNodeIsWaitPush ? courseDirectoryListFragment.waitColor : courseDirectoryListFragment.firstColor);
                String str = (String) CourseDirectoryListFragment.this.allMap.get(chapter_id);
                String str2 = (String) CourseDirectoryListFragment.this.seeMap.get(chapter_id);
                String str3 = (String) CourseDirectoryListFragment.this.haveVidMap.get(chapter_id);
                boolean zEquals = "0".equals(str);
                boolean z2 = treeNode.getChildren() == null || treeNode.getChildren().isEmpty();
                textView2.setVisibility(zEquals ? 8 : 0);
                imageView.setVisibility(z2 ? 8 : 0);
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    textView2.setText(str2 + "/" + str);
                    CourseDirectoryListFragment courseDirectoryListFragment2 = CourseDirectoryListFragment.this;
                    textView2.setTextColor(zTreeNodeIsWaitPush ? courseDirectoryListFragment2.waitColor : courseDirectoryListFragment2.secondColor);
                }
                if (zTreeNodeIsWaitPush) {
                    textView3.setText(waitPushTimeFormat);
                    textView3.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    textView3.setVisibility(0);
                } else if (zEquals || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                    textView3.setVisibility(8);
                } else {
                    textView3.setText(TreeNodeUtilKt.getPercentStr(str2, str3, false));
                    textView3.setVisibility(0);
                    textView3.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                            CourseDirectoryListFragment.this.toastOnUiThread("暂无子章节");
                            return;
                        }
                        if (CourseDirectoryListFragment.this.firstExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(treeNode, CourseDirectoryListFragment.this.firstExpandTree)) {
                            CourseDirectoryListFragment.this.firstExpandTree.isExpand();
                        }
                        AnonymousClass2.this.adapter.expandOrCollapseTreeNode(treeNode);
                    }
                });
                boolean zContains = CourseDirectoryListFragment.this.lastVideoIdChapter.contains(treeNode.getValue().getItem().getChapter_id());
                if (treeNode.isExpand()) {
                    CourseDirectoryListFragment.this.setArrowSpin(holder, treeNode, false);
                    CourseDirectoryListFragment.this.firstExpandTree = treeNode;
                    textView4.setVisibility(zTreeNodeIsWaitPush ? 0 : 8);
                    if (zTreeNodeIsWaitPush) {
                        textView4.setText("等待更新");
                        textView4.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    }
                } else {
                    CourseDirectoryListFragment.this.setArrowSpin(holder, treeNode, false);
                    if (zTreeNodeIsWaitPush) {
                        textView4.setText("等待更新");
                        textView4.setVisibility(0);
                        textView4.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    } else {
                        textView4.setText("上次学习");
                        textView4.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                        textView4.setVisibility(zContains ? 0 : 8);
                    }
                }
                if (zTreeNodeIsWaitPush || zContains) {
                    return;
                }
                textView4.setVisibility(8);
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_course_directory_first;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
                return treeNode.getCustomerLevel() == 0;
            }
        });
        this.adapter.addItemViewDelegate(new TreeNodeDelegate<CourseDirectoryTreeBean>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.3
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
                TextView textView;
                TextView textView2 = (TextView) holder.getView(R.id.itemSecondCount);
                TextView textView3 = (TextView) holder.getView(R.id.itemSecondTitle);
                ImageView imageView = (ImageView) holder.getView(R.id.itemArrow);
                TextView textView4 = (TextView) holder.getView(R.id.itemTvProgress);
                TextView textView5 = (TextView) holder.getView(R.id.itemTvLaseLearn);
                CourseDirectoryItemData item = treeNode.getValue().getItem();
                boolean zTreeNodeIsWaitPush = TreeNodeUtilKt.treeNodeIsWaitPush(treeNode);
                String waitPushTimeFormat = TreeNodeUtilKt.getWaitPushTimeFormat(TreeNodeUtilKt.getTreeNodeWaitPushTimeStr(treeNode));
                final String chapter_id = item.getChapter_id();
                String str = (String) CourseDirectoryListFragment.this.allMap.get(chapter_id);
                String str2 = (String) CourseDirectoryListFragment.this.seeMap.get(chapter_id);
                String str3 = (String) CourseDirectoryListFragment.this.haveVidMap.get(chapter_id);
                boolean zContains = CourseDirectoryListFragment.this.lastVideoIdChapter.contains(item.getChapter_id());
                textView3.setText(item.getTitle());
                CourseDirectoryListFragment courseDirectoryListFragment = CourseDirectoryListFragment.this;
                textView3.setTextColor(zTreeNodeIsWaitPush ? courseDirectoryListFragment.waitColor : courseDirectoryListFragment.firstColor);
                boolean zEquals = "0".equals(str);
                textView2.setVisibility(zEquals ? 8 : 0);
                imageView.setVisibility(zEquals ? 8 : 0);
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    textView2.setText(str2 + "/" + str);
                    CourseDirectoryListFragment courseDirectoryListFragment2 = CourseDirectoryListFragment.this;
                    textView2.setTextColor(zTreeNodeIsWaitPush ? courseDirectoryListFragment2.waitColor : courseDirectoryListFragment2.secondColor);
                }
                if (zTreeNodeIsWaitPush) {
                    textView4.setText(waitPushTimeFormat);
                    textView4.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    textView4.setVisibility(0);
                } else if (zEquals || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                    textView4.setVisibility(8);
                } else {
                    textView4.setText(TreeNodeUtilKt.getPercentStr(str2, str3, false));
                    textView4.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                    textView4.setVisibility(0);
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if ("0".equals(CourseDirectoryListFragment.this.allMap.get(chapter_id))) {
                            CourseDirectoryListFragment.this.toastOnUiThread("暂无视频");
                            return;
                        }
                        if (CourseDirectoryListFragment.this.secondExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(CourseDirectoryListFragment.this.secondExpandTree, treeNode)) {
                            CourseDirectoryListFragment.this.secondExpandTree.isExpand();
                        }
                        AnonymousClass3.this.adapter.expandOrCollapseTreeNode(treeNode);
                    }
                });
                if (treeNode.isExpand()) {
                    CourseDirectoryListFragment.this.secondExpandTree = treeNode;
                    CourseDirectoryListFragment.this.setArrowSpin(holder, treeNode, false);
                    textView = textView5;
                    textView.setVisibility(zTreeNodeIsWaitPush ? 0 : 8);
                    if (zTreeNodeIsWaitPush) {
                        textView.setText("等待更新");
                        textView.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    }
                } else {
                    textView = textView5;
                    CourseDirectoryListFragment.this.setArrowSpin(holder, treeNode, false);
                    if (zTreeNodeIsWaitPush) {
                        textView.setText("等待更新");
                        textView.setVisibility(0);
                        textView.setTextColor(CourseDirectoryListFragment.this.waitColor);
                    } else {
                        textView.setText("上次学习");
                        textView.setTextColor(CourseDirectoryListFragment.this.appThemeRedColor);
                        textView.setVisibility(zContains ? 0 : 8);
                    }
                }
                if (zTreeNodeIsWaitPush || zContains) {
                    return;
                }
                textView.setVisibility(8);
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_course_directory_second;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
                return treeNode.getCustomerLevel() == 1;
            }
        });
        this.adapter.addItemViewDelegate(new AnonymousClass4(data));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.p0
            @Override // java.lang.Runnable
            public final void run() {
                this.f12011c.lambda$initListLayout$2();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRecyclerView() {
        initLastLearnId();
        if (!TextUtils.isEmpty(this.updateChapterId)) {
            List<String> chapterId = TreeNodeUtilKt.getChapterId(this.curListData, this.updateChapterId);
            if (chapterId.size() == 2) {
                this.expandFirstId = chapterId.get(0);
                this.expandSecondId = chapterId.get(1);
            }
        } else if (!TextUtils.isEmpty(this.lastLearnVid)) {
            List<String> chapterIdByVideoId = TreeNodeUtilKt.getChapterIdByVideoId(this.curListData, this.lastLearnVid, this.lastLearnChapterId);
            if (!chapterIdByVideoId.isEmpty()) {
                this.expandFirstId = chapterIdByVideoId.get(0);
                this.expandSecondId = chapterIdByVideoId.get(1);
            }
        }
        List<Integer> listInitSeeAndAll2 = TreeNodeUtilKt.initSeeAndAll2(this.curListData, this.allMap, this.seeMap, this.haveVidMap, this.freeMap);
        if (listInitSeeAndAll2.size() == 2) {
            this.allSeeCount = listInitSeeAndAll2.get(0).intValue();
            this.allHaveVidCount = listInitSeeAndAll2.get(1).intValue();
            Log.d(TAG, "initRecyclerView: 全部学习的课程数量:" + this.allSeeCount + "--全部有vid的课程数量:" + this.allHaveVidCount);
        }
        showCourseProgressMoreThan70Dialog();
        initListLayout(this.curListData);
        this.emptyView.setVisibility(8);
        this.recyclerView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNightTheme() {
        return 1 == SkinManager.getCurrentSkinType(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListLayout$2() {
        LinearLayoutManager linearLayoutManager;
        try {
            if (TextUtils.isEmpty(this.updateChapterId) || (linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager()) == null) {
                return;
            }
            linearLayoutManager.scrollToPositionWithOffset(this.positionUpdate, 0);
        } catch (Exception e2) {
            System.out.println("ErrorTag:" + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCourseProgressMoreThan70Dialog$3(double d3) {
        Intent intent = new Intent(getActivity(), (Class<?>) CourseSurveyActivity.class);
        intent.putExtra("courseId", this.courseId).putExtra("progress", String.valueOf((int) d3));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setArrowSpin(ViewHolder helper, TreeNode<CourseDirectoryTreeBean> data, boolean isAnimate) {
        final ImageView imageView = (ImageView) helper.getView(R.id.itemArrow);
        if (data.isExpand()) {
            Log.d(TAG, "setArrowSpin: --- 展开 ");
            if (isAnimate) {
                imageView.animate().rotation(180.0f).setDuration(300L).setInterpolator(new LinearInterpolator()).start();
                return;
            } else if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                imageView.setImageResource(R.drawable.icon_top_arrow_night);
                return;
            } else {
                imageView.setImageResource(R.drawable.icon_top_arrow_day);
                return;
            }
        }
        Log.d(TAG, "setArrowSpin: --- 关闭 ");
        if (isAnimate) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, 0.0f, 180.0f);
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.5
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(CourseDirectoryListFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
                    } else {
                        imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
                    }
                    animation.cancel();
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                }
            });
            objectAnimatorOfFloat.start();
        } else if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
        } else {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
        }
    }

    private void setCourseMsg(List<CourseDirectoryItemData> data) {
        try {
            CourseDirectoryActivity courseDirectoryActivity = (CourseDirectoryActivity) getActivity();
            if (courseDirectoryActivity != null) {
                List<String> courseTitleFromDirectory = TreeNodeUtilKt.getCourseTitleFromDirectory(data);
                courseDirectoryActivity.setCourseMsg(courseTitleFromDirectory.get(0), courseTitleFromDirectory.get(1));
            }
        } catch (Exception e2) {
            Log.d(TAG, "setCourseMsg: " + e2.getMessage());
        }
    }

    private void showCourseProgressMoreThan70Dialog() {
        if (this.isShowDialog) {
            return;
        }
        final double d3 = (this.allSeeCount / this.allHaveVidCount) * 100.0d;
        Log.d(TAG, "initRecyclerView: 全部学习的课程进度：" + d3);
        boolean booleanConfig = SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_INVESTIGATION_TABLE + this.courseId + StrPool.UNDERLINE + UserConfig.getUserId(), false, getContext());
        if (d3 < 70.0d || booleanConfig) {
            return;
        }
        new XPopup.Builder(getActivity()).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.8
            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onDismiss(BasePopupView popupView) {
                super.onDismiss(popupView);
                CourseDirectoryListFragment.this.isShowDialog = false;
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_INVESTIGATION_TABLE + CourseDirectoryListFragment.this.courseId + StrPool.UNDERLINE + UserConfig.getUserId(), true, CourseDirectoryListFragment.this.getContext());
            }

            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onShow(BasePopupView popupView) {
                super.onShow(popupView);
                CourseDirectoryListFragment.this.isShowDialog = true;
            }
        }).asCustom(new CommentCoursePop(getActivity(), new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.m0
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f11987a.lambda$showCourseProgressMoreThan70Dialog$3(d3);
            }
        })).show();
        this.isShowDialog = true;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        List<CourseDirectoryItemData> list;
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.courseType = arguments.getString(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE);
            this.courseId = arguments.getString("course_id");
            this.updateChapterId = arguments.getString(CourseDirectoryActivity.EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID);
            this.pageType = arguments.getString(EXTRA_DATA_PAGE_TYPE);
            String string = arguments.getString(EXTRA_DATA_COLLECT);
            if ("1".equals(this.pageType) && !TextUtils.isEmpty(string)) {
                this.curListData = (List) new Gson().fromJson(string, new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryListFragment.1
                }.getType());
            }
        }
        this.recyclerView = (RecyclerView) holder.get(R.id.courseDirectoryListRecyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.courseDirectoryListRefresh);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setEnableRefresh(true);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.n0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11993c.lambda$initViews$0(refreshLayout);
            }
        });
        initLastLearnId();
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12000c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.emptyView.setVisibility(0);
        initColor();
        if (!"1".equals(this.pageType) || (list = this.curListData) == null || list.isEmpty()) {
            getListData();
        } else {
            initRecyclerView();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        try {
            TreeNode<CourseDirectoryTreeBean> treeNode = this.firstExpandTree;
            if (treeNode != null) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.COURSE_DIRECTORY_FIRST_EXPAND, treeNode.getValue().getItem().getChapter_id(), getActivity());
            }
            TreeNode<CourseDirectoryTreeBean> treeNode2 = this.secondExpandTree;
            if (treeNode2 != null) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.COURSE_DIRECTORY_SECOND_EXPAND, treeNode2.getValue().getItem().getChapter_id(), getActivity());
            }
        } catch (Exception e2) {
            Log.d(TAG, "onDestroy: " + e2.getMessage());
        }
    }

    @Subscribe
    public void onEventMainThread(RefreshVideoProgressEvent event) {
        try {
            this.lastLearnVid = CourseDataSpUtilKt.readLastLearnVid(this.courseId, getActivity(), "");
            this.lastLearnChapterId = CourseDataSpUtilKt.readLastLearnChapterId(this.courseId, getActivity(), "");
            getListData();
        } catch (Exception e2) {
            Log.e(TAG, "onEventMainThread: " + e2.getMessage());
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (EventBusConstant.DIRECTORY_TAB_UPDATE_COLLECT.equals(str) && "1".equals(this.pageType)) {
            getCollectNotify();
        }
        if (EventBusConstant.DIRECTORY_UPDATE_FRAGMENT_DATA.equals(str)) {
            try {
                this.lastLearnVid = CourseDataSpUtilKt.readLastLearnVid(this.courseId, getActivity(), "");
                this.lastLearnChapterId = CourseDataSpUtilKt.readLastLearnChapterId(this.courseId, getActivity(), "");
                getListData();
            } catch (Exception e2) {
                Log.e(TAG, "onEventMainThread: " + e2.getMessage());
            }
        }
    }
}
