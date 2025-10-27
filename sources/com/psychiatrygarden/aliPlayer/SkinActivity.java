package com.psychiatrygarden.aliPlayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.player.source.VidSts;
import com.aliyun.svideo.common.utils.ScreenUtils;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteCourseChapterActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.ViedeoStatusChangeBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.CommentListAdapter;
import com.psychiatrygarden.aliPlayer.utils.BaseAppCompatActivity;
import com.psychiatrygarden.aliPlayer.utils.ScreenStatusController;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.QuestionCacheVideoBean;
import com.psychiatrygarden.bean.QuestionCacheVideoBeanDao;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.AlphaImageView;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SkinActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static String LOG_ACTION = "com.yikaobang.yixue.logobserver.LOG_ACTION";
    private static final int REFRESH_COMPLETE = 272;
    private static int pageNum = 1;
    private View addFooterView;
    public String collection;
    public String expire_str;
    public long free_watch_time;
    private ImageView iv_my_daka;
    private LinearLayout llay_renew;
    public CommentListAdapter mCommListAdapter;
    private Context mContext;
    public PinnedSectionListView1 mPinnedSecListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    public String note;
    private PopupWindow popupWindow_note;
    public AlphaImageView questiondetails_btn_collect;
    public AlphaImageView questiondetails_btn_edit;
    private RelativeLayout rl_pay_view;
    private Timer timer;
    private TimerTask timerTask;
    public String watch_permission;
    private final String comment_type = "2";
    private AliyunVodPlayerView mAliyunVodPlayerView = null;
    private String is_see = "0";
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS", Locale.CHINA);
    private final List<String> logStrs = new ArrayList();
    private ScreenStatusController mScreenStatusController = null;
    private String course_id = "";
    private String category_id = "";
    private String chapter_id = "";
    private boolean isDestroyed = false;
    View.OnClickListener mOnclick = new AnonymousClass5();

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.14
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 2) {
                Bundle bundle = (Bundle) msg.obj;
                bundle.putInt("result", 1);
                if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    SkinActivity.this.putComment(bundle);
                    return;
                }
                Intent intent = new Intent(SkinActivity.this.mContext, (Class<?>) CorpCupActivity.class);
                intent.putExtra("bundleIntent", bundle);
                SkinActivity.this.startActivityForResult(intent, 1);
                return;
            }
            if (i2 == 272) {
                int unused = SkinActivity.pageNum = 1;
                SkinActivity.this.getCommentListData();
                return;
            }
            if (i2 == 4) {
                SkinActivity.this.editComment(msg);
                return;
            }
            if (i2 == 5) {
                SkinActivity.this.showDeleteDialog(msg);
                return;
            }
            if (i2 == 9) {
                if (SkinManager.getCurrentSkinType(SkinActivity.this.mContext) == 0) {
                    SkinActivity.this.iv_my_daka.setImageResource(R.drawable.video_daka_ok);
                    return;
                } else {
                    SkinActivity.this.iv_my_daka.setImageResource(R.drawable.video_daka_ok_night);
                    return;
                }
            }
            if (i2 != 10) {
                return;
            }
            if (SkinManager.getCurrentSkinType(SkinActivity.this.mContext) == 0) {
                SkinActivity.this.iv_my_daka.setImageResource(R.drawable.video_daka_no);
            } else {
                SkinActivity.this.iv_my_daka.setImageResource(R.drawable.video_daka_no_night);
            }
        }
    };

    /* renamed from: com.psychiatrygarden.aliPlayer.SkinActivity$3, reason: invalid class name */
    public class AnonymousClass3 implements AbsListView.OnScrollListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            SkinActivity.access$208();
            SkinActivity.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && SkinActivity.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                SkinActivity skinActivity = SkinActivity.this;
                skinActivity.mPinnedSecListView.addFooterView(skinActivity.addFooterView);
                SkinActivity.this.addFooterView.setVisibility(0);
                if (SkinActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                    SkinActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.aliPlayer.x
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15290c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.aliPlayer.SkinActivity$5, reason: invalid class name */
    public class AnonymousClass5 implements View.OnClickListener {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(String str, String str2, String str3) {
            Bundle bundle = new Bundle();
            bundle.putString("content", str);
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            Message message = new Message();
            message.what = 2;
            message.obj = bundle;
            SkinActivity.this.mHandler.sendMessage(message);
        }

        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.bt_comment_play) {
                new DialogInput(SkinActivity.this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.aliPlayer.y
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f15291a.lambda$onClick$0(str, str2, str3);
                    }
                }, false).show();
            } else {
                if (id != R.id.iv_my_daka) {
                    return;
                }
                if (SkinActivity.this.getIntent().getExtras().getBoolean("iskedan", false)) {
                    SkinActivity.this.coursenewVideoDaka();
                } else {
                    SkinActivity.this.courseVideoDaka();
                }
            }
        }
    }

    public static class MyCompletionListener implements IPlayer.OnCompletionListener {
        private WeakReference<SkinActivity> activityWeakReference;

        public MyCompletionListener(SkinActivity skinActivity) {
            this.activityWeakReference = new WeakReference<>(skinActivity);
        }

        @Override // com.aliyun.player.IPlayer.OnCompletionListener
        public void onCompletion() {
            this.activityWeakReference.get();
        }
    }

    public static class MyPrepareListener implements IPlayer.OnPreparedListener {
        private WeakReference<SkinActivity> activityWeakReference;

        public MyPrepareListener(SkinActivity skinActivity) {
            this.activityWeakReference = new WeakReference<>(skinActivity);
        }

        @Override // com.aliyun.player.IPlayer.OnPreparedListener
        public void onPrepared() {
            SkinActivity skinActivity = this.activityWeakReference.get();
            if (skinActivity != null) {
                skinActivity.onPrepared();
            }
        }
    }

    public static class MyStoppedListener implements OnStoppedListener {
        private WeakReference<SkinActivity> activityWeakReference;

        public MyStoppedListener(SkinActivity skinActivity) {
            this.activityWeakReference = new WeakReference<>(skinActivity);
        }

        @Override // com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener
        public void onStop() {
        }
    }

    public static /* synthetic */ int access$208() {
        int i2 = pageNum;
        pageNum = i2 + 1;
        return i2;
    }

    private void deleteComment(String comment_id) {
        HashMap map = new HashMap();
        map.put("token", UserConfig.getInstance().getUser().getToken());
        map.put("secret", UserConfig.getInstance().getUser().getSecret());
        map.put("comment_id", comment_id);
        YJYHttpUtils.post(this, "", map, new Response.Listener() { // from class: com.psychiatrygarden.aliPlayer.i
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15257c.lambda$deleteComment$21((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.aliPlayer.j
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                SkinActivity.lambda$deleteComment$22(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void editComment(final Message msg) {
        String string = msg.getData().getString("content");
        HashMap map = new HashMap();
        map.put("token", UserConfig.getInstance().getUser().getToken());
        map.put("secret", UserConfig.getInstance().getUser().getSecret());
        map.put("comment_id", msg.getData().getString("comment_id"));
        map.put("content", string);
        YJYHttpUtils.post(this, "", map, new Response.Listener() { // from class: com.psychiatrygarden.aliPlayer.a
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15250c.lambda$editComment$17((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.aliPlayer.l
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                SkinActivity.lambda$editComment$18(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        HashMap map = new HashMap();
        map.put("module_type", this.module_type + "");
        map.put("comment_type", "2");
        map.put("obj_id", this.course_id + "");
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + pageNum);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCommentList, map, new Response.Listener() { // from class: com.psychiatrygarden.aliPlayer.k
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15258c.lambda$getCommentListData$15((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.aliPlayer.m
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f15259c.lambda$getCommentListData$16(volleyError, str);
            }
        });
    }

    private boolean isStrangePhone() {
        String str = Build.DEVICE;
        return str.equalsIgnoreCase("mx5") || str.equalsIgnoreCase("Redmi Note2") || str.equalsIgnoreCase("Z00A_1") || str.equalsIgnoreCase("hwH60-L02") || str.equalsIgnoreCase("hermes") || (str.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteComment$21(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteComment$22(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$10(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$11(String str, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) NoteCourseChapterActivity.class);
        intent.putExtra("course_id", this.course_id);
        intent.putExtra("notestr", str);
        intent.putExtra("noteStatus", this.note);
        intent.putExtra("iskedan", getIntent().getExtras().getBoolean("iskedan", false));
        intent.putExtra("vidteaching_id", "" + this.chapter_id);
        startActivity(intent);
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$12() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$9(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editComment$17(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.optString("code").equals("200");
            AlertToast(jSONObject.optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$editComment$18(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$15(String str, String str2) {
        try {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (commentBean.getCode().equals("200")) {
                this.hot = commentBean.getData().getHot();
                this.time_line = commentBean.getData().getTime_line();
                if (pageNum == 1) {
                    this.commlist.clear();
                    this.time_lines.clear();
                    CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                    hotBean.setType(1);
                    hotBean.setName("热门评论");
                    this.commlist.add(hotBean);
                    if (this.hot.size() > 0) {
                        this.commlist.addAll(this.hot);
                    } else {
                        CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                        hotBean2.setId("");
                        hotBean2.setContent("暂无热门评论");
                        this.commlist.add(hotBean2);
                    }
                    CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                    hotBean3.setType(1);
                    hotBean3.setName("最新评论");
                    this.commlist.add(hotBean3);
                    if (this.time_line.size() > 0) {
                        this.commlist.addAll(this.time_line);
                    } else {
                        CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                        hotBean4.setId("");
                        hotBean4.setContent("暂无最新评论");
                        this.commlist.add(hotBean4);
                    }
                    this.time_lines.addAll(this.time_line);
                    CommentListAdapter commentListAdapter = new CommentListAdapter(this.mContext, this.commlist, this.time_line, this.module_type, "2", this.course_id + "", this);
                    this.mCommListAdapter = commentListAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter);
                } else {
                    this.mPinnedSecListView.removeFooterView(this.addFooterView);
                    this.addFooterView.setVisibility(8);
                    this.mPinnedSecListView.invalidateViews();
                    if (this.time_line.size() == 0) {
                        AlertToast("已经是最后一条");
                    } else {
                        this.commlist.addAll(this.time_line);
                        this.time_lines.addAll(this.time_line);
                        this.mCommListAdapter.setRefeault(this.time_line);
                        this.mCommListAdapter.notifyDataSetChanged();
                    }
                }
            } else if (pageNum == 1 && this.commlist.size() == 0) {
                CommentBean.DataBean.HotBean hotBean5 = new CommentBean.DataBean.HotBean();
                hotBean5.setType(1);
                hotBean5.setName("热门评论");
                this.commlist.add(hotBean5);
                CommentBean.DataBean.HotBean hotBean6 = new CommentBean.DataBean.HotBean();
                hotBean6.setId("");
                hotBean6.setContent("暂无热门评论");
                this.commlist.add(hotBean6);
                CommentBean.DataBean.HotBean hotBean7 = new CommentBean.DataBean.HotBean();
                hotBean7.setType(1);
                hotBean7.setName("最新评论");
                this.commlist.add(hotBean7);
                CommentBean.DataBean.HotBean hotBean8 = new CommentBean.DataBean.HotBean();
                hotBean8.setId("");
                hotBean8.setContent("暂无最新评论");
                this.commlist.add(hotBean8);
                CommentListAdapter commentListAdapter2 = new CommentListAdapter(this.mContext, this.commlist, this);
                this.mCommListAdapter = commentListAdapter2;
                this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter2);
            } else {
                AlertToast(commentBean.getMessage());
            }
            if (this.mSwipeRefreshLayout.isRefreshing()) {
                this.mSwipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$16(VolleyError volleyError, String str) {
        if (pageNum == 1) {
            if (this.commlist.size() > 0) {
                AlertToast("请求服务器失败");
                return;
            }
            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
            hotBean.setType(1);
            hotBean.setName("热门评论");
            this.commlist.add(hotBean);
            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
            hotBean2.setId("");
            hotBean2.setContent("暂无热门评论");
            this.commlist.add(hotBean2);
            CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
            hotBean3.setType(1);
            hotBean3.setName("最新评论");
            this.commlist.add(hotBean3);
            CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
            hotBean4.setId("");
            hotBean4.setContent("暂无最新评论");
            this.commlist.add(hotBean4);
            CommentListAdapter commentListAdapter = new CommentListAdapter(this.mContext, this.commlist, this);
            this.mCommListAdapter = commentListAdapter;
            this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter);
        }
        if (this.mSwipeRefreshLayout.isRefreshing()) {
            this.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShareData$8() {
        this.rl_pay_view.setVisibility(8);
        this.watch_permission = "1";
        changeVideoStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
        getShareData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
        getShareData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$3(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(View view) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
        getShareData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$5(View view) {
        if (!getIntent().getExtras().getBoolean("iskedan", false)) {
            if (!TextUtils.equals(this.note, "0")) {
                getNoteData();
                return;
            }
            Intent intent = new Intent(this, (Class<?>) NoteCourseChapterActivity.class);
            intent.putExtra("course_id", this.course_id);
            intent.putExtra("notestr", "");
            intent.putExtra("noteStatus", this.note);
            startActivity(intent);
            return;
        }
        if (!TextUtils.equals(this.note, "0")) {
            getKedanNoteData();
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) NoteCourseChapterActivity.class);
        intent2.putExtra("course_id", this.course_id);
        intent2.putExtra("notestr", "");
        intent2.putExtra("noteStatus", this.note);
        intent2.putExtra("iskedan", getIntent().getExtras().getBoolean("iskedan", false));
        intent2.putExtra("vidteaching_id", "" + this.chapter_id);
        startActivity(intent2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$6(View view) {
        if ("1".equals(this.collection)) {
            this.collection = "0";
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.collection = "1";
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        }
        if (getIntent().getExtras().getBoolean("iskedan", false)) {
            getCollectNewData();
        } else {
            getCollectData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$7() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        pageNum = 1;
        getCommentListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putComment$13(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.optString("code").equals("200")) {
                if (jSONObject.optString("code").equals("401")) {
                    new CusomNewDialog(this.mContext).setMessage(jSONObject.optString("message")).show();
                    return;
                } else {
                    AlertToast(jSONObject.optString("message"));
                    return;
                }
            }
            NewToast.showShort(ProjectApp.instance(), "评论成功");
            if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                this.commlist.removeAll(this.time_lines);
                if (this.time_lines.size() == 0) {
                    this.commlist.remove(3);
                }
                this.time_lines.add(0, hotBean);
                this.commlist.addAll(this.time_lines);
                this.mCommListAdapter.notifyDataSetChanged();
            }
            EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
            CommonUtil.showFristDialog(jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putComment$14(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeleteDialog$20(String str, CustomDialog customDialog, View view) {
        deleteComment(str);
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPrepared() {
        this.logStrs.add(this.format.format(new Date()) + getString(R.string.log_prepare_success));
        this.mAliyunVodPlayerView.start();
        if (!TextUtils.isEmpty(getIntent().getExtras().getString("vid"))) {
            int intConfig = SharePreferencesUtils.readIntConfig(getIntent().getExtras().getString("vid") + UserConfig.getUserId(), this.mContext, 0);
            if (intConfig != 0) {
                this.mAliyunVodPlayerView.seekTo(intConfig);
            }
        }
        if (getIntent().getExtras().getBoolean("realVideo", false)) {
            coursePlayNum();
            return;
        }
        if (getIntent().getExtras().getBoolean("iskedan", false)) {
            courseNum();
            return;
        }
        try {
            if (getIntent().getStringExtra("vid") != null) {
                QuestionCacheVideoBean questionCacheVideoBeanUnique = ProjectApp.mDaoSession.getQuestionCacheVideoBeanDao().queryBuilder().where(QuestionCacheVideoBeanDao.Properties.Vid.eq(getIntent().getStringExtra("vid")), QuestionCacheVideoBeanDao.Properties.Is_select.eq(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()) + SdkConstant.UMENG_ALIS + SharePreferencesUtils.readStrConfig(CommonParameter.App_Type, ProjectApp.instance()))).unique();
                if (questionCacheVideoBeanUnique == null || questionCacheVideoBeanUnique.getChapter_id() == null || questionCacheVideoBeanUnique.getChapter_id().equals("")) {
                    return;
                }
                questionCacheVideoBeanUnique.setChapter_id(this.chapter_id);
                ProjectApp.mDaoSession.getQuestionCacheVideoBeanDao().insertOrReplace(questionCacheVideoBeanUnique);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void setPlaySource() {
        String stringExtra = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(stringExtra) && "vidsts".equals(stringExtra)) {
            String stringExtra2 = getIntent().getStringExtra("vid");
            String stringExtra3 = getIntent().getStringExtra("akId");
            String stringExtra4 = getIntent().getStringExtra("akSecret");
            String stringExtra5 = getIntent().getStringExtra("securityToken");
            VidSts vidSts = new VidSts();
            vidSts.setVid(stringExtra2);
            vidSts.setRegion(GlobalPlayerConfig.mRegion);
            vidSts.setAccessKeyId(stringExtra3);
            vidSts.setSecurityToken(stringExtra5);
            vidSts.setAccessKeySecret(stringExtra4);
            this.mAliyunVodPlayerView.setVidSts(vidSts);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog(final Message msg) {
        final String string = msg.getData().getString("comment_id");
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage(R.string.confirm_delete_comment);
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15261c.lambda$showDeleteDialog$20(string, customDialog, view);
            }
        });
        customDialog.show();
    }

    private void updatePlayerViewMode() {
        if (this.mAliyunVodPlayerView != null) {
            int i2 = getResources().getConfiguration().orientation;
            if (i2 == 1) {
                getWindow().clearFlags(1024);
                this.mAliyunVodPlayerView.setSystemUiVisibility(0);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mAliyunVodPlayerView.getLayoutParams();
                layoutParams.height = (int) ((ScreenUtils.getWidth(this) * 9.0f) / 16.0f);
                layoutParams.width = -1;
                this.rl_pay_view.setLayoutParams(layoutParams);
                return;
            }
            if (i2 == 2) {
                if (!isStrangePhone()) {
                    getWindow().setFlags(1024, 1024);
                    this.mAliyunVodPlayerView.setSystemUiVisibility(R2.color.umeng_socialize_text_share_content);
                }
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mAliyunVodPlayerView.getLayoutParams();
                layoutParams2.height = -1;
                layoutParams2.width = -1;
                this.rl_pay_view.setLayoutParams(layoutParams2);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase));
    }

    public void changeVideoStatus() {
        if (getIntent().getExtras().getBoolean("iskedan", false)) {
            ViedeoStatusChangeBean viedeoStatusChangeBean = new ViedeoStatusChangeBean();
            viedeoStatusChangeBean.setVid(getIntent().getExtras().getString("vid"));
            viedeoStatusChangeBean.setC_id(this.chapter_id);
            viedeoStatusChangeBean.setId(this.course_id);
            viedeoStatusChangeBean.setCollect(this.collection);
            viedeoStatusChangeBean.setNote(this.note);
            viedeoStatusChangeBean.setIssee(this.is_see);
            viedeoStatusChangeBean.setWatch_permission(this.watch_permission);
            YkBManager.getInstance().mViedeoStatusChangeBean.postValue(viedeoStatusChangeBean);
        }
    }

    public void courseNum() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.course_id);
        ajaxParams.put("vidteaching_id", "" + this.chapter_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.watchApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.11
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
            }
        });
    }

    public void coursePlayNum() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCoursePlayNum, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.10
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
            }
        });
    }

    public void courseVideoDaka() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("course_id", this.course_id);
        ajaxParams.put("category_id", this.category_id);
        ajaxParams.put("chapter_id", this.chapter_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCourseVideoDaka, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.12
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
                try {
                    if (!new JSONObject(t2).optString("code").equals("200")) {
                        SkinActivity.this.AlertToast("打卡失败");
                        return;
                    }
                    if (SkinActivity.this.is_see.equals("1")) {
                        SkinActivity.this.is_see = "0";
                        SkinActivity.this.mHandler.sendEmptyMessage(10);
                        SkinActivity.this.AlertToast("取消打卡成功");
                    } else {
                        SkinActivity.this.is_see = "1";
                        SkinActivity.this.AlertToast("打卡成功");
                        SkinActivity.this.mHandler.sendEmptyMessage(9);
                    }
                    EventBus.getDefault().post(EventBusConstant.VIDEO_COURSE_DAKA);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    SkinActivity.this.mHandler.sendEmptyMessage(10);
                }
            }
        });
    }

    public void coursenewVideoDaka() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.course_id);
        ajaxParams.put("vidteaching_id", this.chapter_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.handleWatchedApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.13
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
                try {
                    if (!new JSONObject(t2).optString("code").equals("200")) {
                        SkinActivity.this.AlertToast("打卡失败");
                    } else if (SkinActivity.this.is_see.equals("1")) {
                        SkinActivity.this.is_see = "0";
                        SkinActivity.this.mHandler.sendEmptyMessage(10);
                        SkinActivity.this.AlertToast("取消打卡成功");
                    } else {
                        SkinActivity.this.is_see = "1";
                        SkinActivity.this.AlertToast("打卡成功");
                        SkinActivity.this.mHandler.sendEmptyMessage(9);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    SkinActivity.this.mHandler.sendEmptyMessage(10);
                }
                SkinActivity.this.changeVideoStatus();
            }
        });
    }

    public void dialogNote(final String content) {
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.popu_note, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.popu_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.popu_edit);
        ((TextView) viewInflate.findViewById(R.id.tv_note_content)).setText(content);
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15265c.lambda$dialogNote$9(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15266c.lambda$dialogNote$10(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15267c.lambda$dialogNote$11(content, view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_note = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_note.setOutsideTouchable(true);
        this.popupWindow_note.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.aliPlayer.u
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SkinActivity.lambda$dialogNote$12();
            }
        });
        this.popupWindow_note.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_note.showAtLocation(this.mPinnedSecListView, 17, 0, 0);
    }

    public void getCollectData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.addOrCancel, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SkinActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getCollectNewData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.course_id);
        ajaxParams.put("vidteaching_id", this.chapter_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.collectVideoApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SkinActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                    SkinActivity.this.changeVideoStatus();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getKedanNoteData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", this.course_id);
        ajaxParams.put("vidteaching_id", this.chapter_id);
        YJYHttpUtils.get(this, NetworkRequestsURL.getNoteApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    SkinActivity.this.AlertToast("获取评论失败");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SkinActivity.this.dialogNote(new JSONObject(jSONObject.optString("data")).optString("content"));
                    } else {
                        SkinActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getNoteData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.getNoteurl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    SkinActivity.this.AlertToast("获取评论失败");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SkinActivity.this.dialogNote(new JSONObject(jSONObject.optString("data")).optString("content"));
                    } else {
                        SkinActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getShareData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (getIntent().getExtras().getBoolean("realVideo", false)) {
            ajaxParams.put(PushConstants.INTENT_ACTIVITY_NAME, "real_question_video");
        } else {
            if (!getIntent().getExtras().getBoolean("iskedan", false)) {
                ajaxParams.put("module_name", "course");
                ajaxParams.put("chapter_id", "" + this.chapter_id);
                if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                    ajaxParams.put("student_type", "xue");
                } else {
                    ajaxParams.put("student_type", "zhuan");
                }
            }
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + getIntent().getExtras().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID));
        }
        MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.aliPlayer.h
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f15256a.lambda$getShareData$8();
            }
        });
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Object getSystemService(@NonNull String name) {
        return "audio".equals(name) ? getApplicationContext().getSystemService(name) : super.getSystemService(name);
    }

    public void mOnDestroy() {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
        }
        TimerTask timerTask = this.timerTask;
        if (timerTask != null) {
            timerTask.cancel();
        }
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onDestroy();
            this.mAliyunVodPlayerView = null;
        }
        this.mScreenStatusController.stopListen();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            this.mCommListAdapter.getputData(data.getBundleExtra("bundleIntent"));
        } else {
            if (requestCode != 1) {
                return;
            }
            putComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override // com.psychiatrygarden.aliPlayer.utils.BaseAppCompatActivity, com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        this.mContext = this;
        this.course_id = getIntent().getStringExtra("course_id");
        this.category_id = getIntent().getStringExtra("category_id");
        this.chapter_id = getIntent().getStringExtra("chapter_id");
        this.is_see = getIntent().getStringExtra("is_see");
        this.module_type = getIntent().getIntExtra("module_type", 8);
        try {
            this.collection = getIntent().getStringExtra("collection");
            this.note = getIntent().getStringExtra("note");
            this.free_watch_time = getIntent().getLongExtra("free_watch_time", 0L);
            this.watch_permission = getIntent().getStringExtra("watch_permission");
            this.expire_str = getIntent().getStringExtra("expire_str");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black), 0);
        this.mActionBar.hide();
        setSwipeBackEnable(false);
        setContentView(R.layout.activity_skin);
        AliyunVodPlayerView aliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        this.mAliyunVodPlayerView = aliyunVodPlayerView;
        aliyunVodPlayerView.setKeepScreenOn(true);
        this.mAliyunVodPlayerView.setTheme(Theme.Red);
        this.mAliyunVodPlayerView.setCirclePlay(false);
        this.mAliyunVodPlayerView.setOnPreparedListener(new MyPrepareListener(this));
        this.mAliyunVodPlayerView.setOnCompletionListener(new MyCompletionListener(this));
        this.mAliyunVodPlayerView.setOnStoppedListener(new MyStoppedListener(this));
        this.mAliyunVodPlayerView.enableNativeLog();
        ((TextView) findViewById(R.id.tv_free_play)).setText(String.format(Locale.CHINA, "您可以试看%d秒", Long.valueOf(this.free_watch_time)));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llay_buy_course);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15269c.lambda$onCreate$0(view);
            }
        });
        this.rl_pay_view = (RelativeLayout) findViewById(R.id.rl_pay_view);
        TextView textView = (TextView) findViewById(R.id.buy_text);
        TextView textView2 = (TextView) findViewById(R.id.back_tv);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15270c.lambda$onCreate$1(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15251c.lambda$onCreate$2(view);
            }
        });
        this.rl_pay_view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SkinActivity.lambda$onCreate$3(view);
            }
        });
        CountDownTimer countDownTimer = new CountDownTimer(5000L, 1000L) { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                SkinActivity.this.llay_renew.setVisibility(8);
            }

            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
            }
        };
        this.llay_renew = (LinearLayout) findViewById(R.id.llay_renew);
        TextView textView3 = (TextView) findViewById(R.id.tv_renew_day);
        TextView textView4 = (TextView) findViewById(R.id.tv_renew);
        if (TextUtils.isEmpty(this.expire_str)) {
            this.llay_renew.setVisibility(8);
        } else if (TextUtils.equals(this.expire_str, "0")) {
            this.llay_renew.setVisibility(0);
            textView3.setText("您的观看权限不足一天");
            countDownTimer.start();
        } else {
            this.llay_renew.setVisibility(0);
            textView3.setText(String.format("您的观看权限不足%s天", this.expire_str));
            countDownTimer.start();
        }
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15252c.lambda$onCreate$4(view);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.viewdatas);
        if (getIntent().getExtras().getString("type").equals("localSource") && this.course_id.equals("")) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
            if (TextUtils.equals(this.watch_permission, "1")) {
                linearLayout.setVisibility(8);
            } else {
                linearLayout.setVisibility(0);
                this.timer = new Timer();
                TimerTask timerTask = new TimerTask() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 6;
                        SkinActivity.this.mHandler.sendMessage(messageObtain);
                    }
                };
                this.timerTask = timerTask;
                this.timer.schedule(timerTask, 0L, 1000L);
            }
        }
        this.questiondetails_btn_edit = (AlphaImageView) findViewById(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_collect = (AlphaImageView) findViewById(R.id.questiondetails_btn_collect);
        this.questiondetails_btn_edit.setVisibility(0);
        this.questiondetails_btn_collect.setVisibility(0);
        try {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                if (TextUtils.equals(this.collection, "0")) {
                    this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
                } else {
                    this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
                }
                if (TextUtils.equals(this.note, "0")) {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
                } else {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
                }
            } else {
                if (TextUtils.equals(this.collection, "0")) {
                    this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
                } else {
                    this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
                }
                if (TextUtils.equals(this.note, "0")) {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
                } else {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15253c.lambda$onCreate$5(view);
            }
        });
        this.questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15254c.lambda$onCreate$6(view);
            }
        });
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) findViewById(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setAnimationCacheEnabled(false);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.aliPlayer.g
            @Override // java.lang.Runnable
            public final void run() {
                this.f15255c.lambda$onCreate$7();
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass3());
        Button button = (Button) findViewById(R.id.bt_comment_play);
        ImageView imageView = (ImageView) findViewById(R.id.iv_my_daka);
        this.iv_my_daka = imageView;
        imageView.setVisibility(0);
        if (this.is_see.equals("1")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.iv_my_daka.setImageResource(R.drawable.video_daka_ok);
            } else {
                this.iv_my_daka.setImageResource(R.drawable.video_daka_ok_night);
            }
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.iv_my_daka.setImageResource(R.drawable.video_daka_no);
        } else {
            this.iv_my_daka.setImageResource(R.drawable.video_daka_no_night);
        }
        int i2 = this.module_type;
        if (i2 == 10 || i2 == 11 || i2 == 22) {
            this.iv_my_daka.setVisibility(8);
            this.questiondetails_btn_edit.setVisibility(8);
            this.questiondetails_btn_collect.setVisibility(8);
        }
        button.setOnClickListener(this.mOnclick);
        this.iv_my_daka.setOnClickListener(this.mOnclick);
        setPlaySource();
        ScreenStatusController screenStatusController = new ScreenStatusController(this);
        this.mScreenStatusController = screenStatusController;
        screenStatusController.setScreenStatusListener(new ScreenStatusController.ScreenStatusListener() { // from class: com.psychiatrygarden.aliPlayer.SkinActivity.4
            @Override // com.psychiatrygarden.aliPlayer.utils.ScreenStatusController.ScreenStatusListener
            public void onScreenOff() {
            }

            @Override // com.psychiatrygarden.aliPlayer.utils.ScreenStatusController.ScreenStatusListener
            public void onScreenOn() {
            }
        });
        this.mScreenStatusController.startListen();
    }

    @Override // com.psychiatrygarden.aliPlayer.utils.BaseAppCompatActivity, com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() throws IllegalAccessException, NoSuchFieldException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        mOnDestroy();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.aliPlayer.utils.BaseAppCompatActivity, com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        str.hashCode();
        switch (str) {
            case "BuySuccess1":
                this.rl_pay_view.setVisibility(8);
                this.watch_permission = "1";
                changeVideoStatus();
                break;
            case "clearTxt":
                this.note = "0";
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
                } else {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
                }
                changeVideoStatus();
                break;
            case "saveTxt":
                this.note = "1";
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
                } else {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
                }
                changeVideoStatus();
                break;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null || aliyunVodPlayerView.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            mOnDestroy();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updatePlayerViewMode();
    }

    public void putComment(Bundle b3) {
        HashMap map = new HashMap();
        map.put("content", b3.getString("content"));
        map.put("module_type", this.module_type + "");
        map.put("comment_type", "2");
        map.put("obj_id", this.course_id + "");
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                map.put("b_img", string);
                map.put("s_img", string2);
            } else {
                map.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            map.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.mPutComment, map, new Response.Listener() { // from class: com.psychiatrygarden.aliPlayer.p
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15264c.lambda$putComment$13((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.aliPlayer.q
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                SkinActivity.lambda$putComment$14(volleyError, str);
            }
        });
    }
}
