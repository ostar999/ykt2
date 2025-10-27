package com.psychiatrygarden.activity.knowledge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.source.VidSts;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.android.exoplayer2.C;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity;
import com.psychiatrygarden.activity.knowledge.KnowledgeRuleActivity;
import com.psychiatrygarden.activity.knowledge.adapter.KnowledgeMapAdapter;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.bean.KnowledgeTaskListBean;
import com.psychiatrygarden.bean.KnowledgeTaskListItemBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 N2\u00020\u0001:\u0001NB\u0005¢\u0006\u0002\u0010\u0002J\b\u00103\u001a\u000204H\u0002J\u0012\u00105\u001a\u0002042\b\b\u0002\u00106\u001a\u000207H\u0002J\u0010\u00108\u001a\u0002042\u0006\u00109\u001a\u000207H\u0002J\u0012\u0010:\u001a\u0002042\b\b\u0002\u0010;\u001a\u00020\rH\u0002J\b\u0010<\u001a\u000204H\u0016J\u0010\u0010=\u001a\u0002042\u0006\u0010>\u001a\u00020)H\u0002J\b\u0010?\u001a\u000204H\u0002J\b\u0010@\u001a\u000204H\u0002J\u0012\u0010A\u001a\u0002042\b\u0010B\u001a\u0004\u0018\u00010CH\u0014J\b\u0010D\u001a\u000204H\u0014J\u0012\u0010E\u001a\u0002042\b\u0010F\u001a\u0004\u0018\u000107H\u0016J\b\u0010G\u001a\u000204H\u0014J\b\u0010H\u001a\u000204H\u0014J\b\u0010I\u001a\u000204H\u0002J\b\u0010J\u001a\u000204H\u0002J\b\u0010K\u001a\u000204H\u0016J\b\u0010L\u001a\u000204H\u0016J\u0010\u0010M\u001a\u0002042\u0006\u0010>\u001a\u00020)H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082.¢\u0006\u0002\n\u0000¨\u0006O"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/KnowledgeMapActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "WHAT_SEEKBAR_PROGESS", "", "WHAT_TOUCH", "curPlayPosition", "", "curTouchTime", "duration", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "haveVideo", "", "imageTop", "Landroid/widget/ImageView;", "imgVideoIntroduce", "initVideoInfo", "isPlaying", "isTouchSeekBar", "layoutContent", "Landroid/widget/RelativeLayout;", "layoutTaskList", "Landroid/widget/LinearLayout;", "layoutVideo", "lyBarLayout", "mAdapter", "Lcom/psychiatrygarden/activity/knowledge/adapter/KnowledgeMapAdapter;", "mImgVideo", "Lcom/makeramen/roundedimageview/RoundedImageView;", "mLyVideoView", "mPlayer", "Lcom/aliyun/player/AliPlayer;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "playComplete", "playPauseView", "progress", "seekBar", "Landroid/widget/SeekBar;", "taskData", "Lcom/psychiatrygarden/bean/KnowledgeTaskListBean;", "tvRule", "Landroid/widget/TextView;", "tvTime", "tvVideoCountTime", "tvVideoCurTime", "videoView", "Landroid/view/SurfaceView;", "weakHandler", "Lcom/psychiatrygarden/utils/WeakHandler;", "beginPlay", "", "getCourseAk", "vid", "", "getTaskById", "taskId", "getTaskListData", "isOnlyUpdateList", "init", "initActivityData", "data", "initPlayer", "initViewPic", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onEventMainThread", "str", "onResume", "onStop", "playRelease", "setComplete", "setContentView", "setListenerForWidget", "updateList", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class KnowledgeMapActivity extends BaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private long curPlayPosition;
    private long curTouchTime;
    private long duration;
    private CustomEmptyView emptyView;
    private ImageView imageTop;
    private ImageView imgVideoIntroduce;
    private boolean initVideoInfo;
    private boolean isPlaying;
    private boolean isTouchSeekBar;
    private RelativeLayout layoutContent;
    private LinearLayout layoutTaskList;
    private RelativeLayout layoutVideo;
    private RelativeLayout lyBarLayout;
    private KnowledgeMapAdapter mAdapter;
    private RoundedImageView mImgVideo;
    private RelativeLayout mLyVideoView;

    @Nullable
    private AliPlayer mPlayer;
    private RecyclerView mRecyclerView;
    private boolean playComplete;
    private ImageView playPauseView;
    private long progress;
    private SeekBar seekBar;

    @Nullable
    private KnowledgeTaskListBean taskData;
    private TextView tvRule;
    private TextView tvTime;
    private TextView tvVideoCountTime;
    private TextView tvVideoCurTime;
    private SurfaceView videoView;
    private WeakHandler weakHandler;
    private final int WHAT_SEEKBAR_PROGESS = 2;
    private final int WHAT_TOUCH = 1;
    private boolean haveVideo = true;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/KnowledgeMapActivity$Companion;", "", "()V", "navigationToKnowledgeMap", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void navigationToKnowledgeMap(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) KnowledgeMapActivity.class));
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0002H\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\u0012\u0010\u000b\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\f"}, d2 = {"com/psychiatrygarden/activity/knowledge/KnowledgeMapActivity$getTaskListData$1", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onStart", "onSuccess", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity$getTaskListData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05961 extends AjaxCallBack<String> {
        final /* synthetic */ boolean $isOnlyUpdateList;

        public C05961(boolean z2) {
            this.$isOnlyUpdateList = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$1$lambda$0(KnowledgeMapActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.beginPlay();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
            Intrinsics.checkNotNullParameter(t2, "t");
            Intrinsics.checkNotNullParameter(strMsg, "strMsg");
            super.onFailure(t2, errorNo, strMsg);
            CustomEmptyView customEmptyView = KnowledgeMapActivity.this.emptyView;
            if (customEmptyView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView = null;
            }
            customEmptyView.setLoadFileResUi(KnowledgeMapActivity.this);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@Nullable String t2) {
            super.onSuccess((C05961) t2);
            if (t2 != null) {
                CustomEmptyView customEmptyView = null;
                try {
                    final KnowledgeMapActivity knowledgeMapActivity = KnowledgeMapActivity.this;
                    boolean z2 = this.$isOnlyUpdateList;
                    JSONObject jSONObject = new JSONObject(t2);
                    String strOptString = jSONObject.optString("code");
                    String strOptString2 = jSONObject.optString("data");
                    if (!Intrinsics.areEqual("200", strOptString)) {
                        CustomEmptyView customEmptyView2 = knowledgeMapActivity.emptyView;
                        if (customEmptyView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                            customEmptyView2 = null;
                        }
                        customEmptyView2.setLoadFileResUi(knowledgeMapActivity);
                        return;
                    }
                    knowledgeMapActivity.taskData = (KnowledgeTaskListBean) new Gson().fromJson(strOptString2, KnowledgeTaskListBean.class);
                    CustomEmptyView customEmptyView3 = knowledgeMapActivity.emptyView;
                    if (customEmptyView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                        customEmptyView3 = null;
                    }
                    ViewExtensionsKt.gone(customEmptyView3);
                    RelativeLayout relativeLayout = knowledgeMapActivity.layoutContent;
                    if (relativeLayout == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("layoutContent");
                        relativeLayout = null;
                    }
                    ViewExtensionsKt.visible(relativeLayout);
                    if (knowledgeMapActivity.taskData != null) {
                        if (z2) {
                            KnowledgeTaskListBean knowledgeTaskListBean = knowledgeMapActivity.taskData;
                            Intrinsics.checkNotNull(knowledgeTaskListBean);
                            knowledgeMapActivity.updateList(knowledgeTaskListBean);
                            return;
                        }
                        KnowledgeTaskListBean knowledgeTaskListBean2 = knowledgeMapActivity.taskData;
                        Intrinsics.checkNotNull(knowledgeTaskListBean2);
                        knowledgeMapActivity.initActivityData(knowledgeTaskListBean2);
                        KnowledgeTaskListBean knowledgeTaskListBean3 = knowledgeMapActivity.taskData;
                        Intrinsics.checkNotNull(knowledgeTaskListBean3);
                        String vid = knowledgeTaskListBean3.getVid();
                        knowledgeMapActivity.haveVideo = vid == null || vid.length() == 0 ? false : true;
                        knowledgeMapActivity.initViewPic();
                        if (knowledgeMapActivity.haveVideo) {
                            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.knowledge.j0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    KnowledgeMapActivity.C05961.onSuccess$lambda$1$lambda$0(knowledgeMapActivity);
                                }
                            }, 100L);
                        }
                    }
                } catch (Exception e2) {
                    Log.d(KnowledgeMapActivity.this.TAG, "error: 任务列表异常 " + e2.getMessage());
                    CustomEmptyView customEmptyView4 = KnowledgeMapActivity.this.emptyView;
                    if (customEmptyView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    } else {
                        customEmptyView = customEmptyView4;
                    }
                    customEmptyView.setLoadFileResUi(KnowledgeMapActivity.this);
                }
            }
        }
    }

    public KnowledgeMapActivity() {
        this.TAG = "KnowledgeMapActivity";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void beginPlay() {
        AliPlayer aliPlayer = this.mPlayer;
        if (aliPlayer != null && !this.initVideoInfo) {
            KnowledgeTaskListBean knowledgeTaskListBean = this.taskData;
            Intrinsics.checkNotNull(knowledgeTaskListBean);
            String vid = knowledgeTaskListBean.getVid();
            Intrinsics.checkNotNull(vid);
            getCourseAk(vid);
            RoundedImageView roundedImageView = this.mImgVideo;
            if (roundedImageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mImgVideo");
                roundedImageView = null;
            }
            roundedImageView.setVisibility(8);
            return;
        }
        if (this.playComplete) {
            Intrinsics.checkNotNull(aliPlayer);
            aliPlayer.prepare();
            this.isPlaying = true;
        } else if (this.isPlaying) {
            Intrinsics.checkNotNull(aliPlayer);
            aliPlayer.pause();
            this.isPlaying = false;
        } else {
            this.isPlaying = true;
            Intrinsics.checkNotNull(aliPlayer);
            aliPlayer.start();
        }
    }

    private final void getCourseAk(final String vid) {
        YJYHttpUtils.post(this, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity.getCourseAk.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                if (TextUtils.isEmpty(t2)) {
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        KnowledgeMapActivity.this.initVideoInfo = true;
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akId"));
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akSecret"));
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("st"));
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(vid);
                        vidSts.setAccessKeyId(strDecode);
                        vidSts.setAccessKeySecret(strDecode2);
                        vidSts.setSecurityToken(strDecode3);
                        vidSts.setRegion(GlobalPlayerConfig.mRegion);
                        vidSts.setQuality(AliPlayUtils.getPlayVideoDefinition(KnowledgeMapActivity.this), true);
                        if (KnowledgeMapActivity.this.mPlayer != null) {
                            AliPlayer aliPlayer = KnowledgeMapActivity.this.mPlayer;
                            Intrinsics.checkNotNull(aliPlayer);
                            aliPlayer.setDataSource(vidSts);
                            AliPlayer aliPlayer2 = KnowledgeMapActivity.this.mPlayer;
                            Intrinsics.checkNotNull(aliPlayer2);
                            aliPlayer2.prepare();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static /* synthetic */ void getCourseAk$default(KnowledgeMapActivity knowledgeMapActivity, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "53a6d32341ad4c279ec14a292c44071d";
        }
        knowledgeMapActivity.getCourseAk(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getTaskById(final String taskId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", taskId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getKnowledgeTask, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity.getTaskById.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(KnowledgeMapActivity.this, "领取失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((C05951) t2);
                if (t2 != null) {
                    try {
                        KnowledgeMapActivity knowledgeMapActivity = KnowledgeMapActivity.this;
                        String str = taskId;
                        JSONObject jSONObject = new JSONObject(t2);
                        String strOptString = jSONObject.optString("code");
                        jSONObject.optString("data");
                        String message = jSONObject.optString("message");
                        boolean z2 = true;
                        if (!Intrinsics.areEqual("200", strOptString)) {
                            if (message != null && message.length() != 0) {
                                z2 = false;
                            }
                            if (z2) {
                                message = "领取失败";
                            } else {
                                Intrinsics.checkNotNullExpressionValue(message, "message");
                            }
                            NewToast.showShort(knowledgeMapActivity, message);
                            return;
                        }
                        NewToast.showShort(knowledgeMapActivity, "领取成功");
                        try {
                            KnowledgeTaskListBean knowledgeTaskListBean = knowledgeMapActivity.taskData;
                            Intrinsics.checkNotNull(knowledgeTaskListBean);
                            List<KnowledgeTaskListItemBean> list = knowledgeTaskListBean.getList();
                            Intrinsics.checkNotNull(list);
                            for (KnowledgeTaskListItemBean knowledgeTaskListItemBean : list) {
                                if (Intrinsics.areEqual(knowledgeTaskListItemBean.getId(), str)) {
                                    knowledgeTaskListItemBean.set_receive("1");
                                }
                            }
                            knowledgeMapActivity.getTaskListData(true);
                        } catch (Exception e2) {
                            System.out.println((Object) ("error: " + e2.getMessage()));
                        }
                    } catch (Exception e3) {
                        Log.d(KnowledgeMapActivity.this.TAG, "error: 领取任务异常 " + e3.getMessage());
                        NewToast.showShort(KnowledgeMapActivity.this, "领取失败");
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getTaskListData(boolean isOnlyUpdateList) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.knowledgeTaskList, ajaxParams, new C05961(isOnlyUpdateList));
    }

    public static /* synthetic */ void getTaskListData$default(KnowledgeMapActivity knowledgeMapActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        knowledgeMapActivity.getTaskListData(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(KnowledgeMapActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(KnowledgeMapActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        getTaskListData$default(this$0, false, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$3(KnowledgeMapActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        KnowledgeTaskListBean knowledgeTaskListBean = this$0.taskData;
        if (knowledgeTaskListBean != null) {
            String rule_url = knowledgeTaskListBean.getRule_url();
            if (rule_url == null || rule_url.length() == 0) {
                return;
            }
            KnowledgeRuleActivity.Companion.navigationToKnowledgeRuleActivity$default(KnowledgeRuleActivity.INSTANCE, this$0, knowledgeTaskListBean.getRule_url(), null, 4, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$4(KnowledgeMapActivity this$0, Message message) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int i2 = message.what;
        if (i2 == this$0.WHAT_TOUCH) {
            if (!this$0.isPlaying || System.currentTimeMillis() - this$0.curTouchTime <= C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS) {
                return;
            }
            ImageView imageView = this$0.playPauseView;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                imageView = null;
            }
            ViewExtensionsKt.gone(imageView);
            return;
        }
        if (i2 == this$0.WHAT_SEEKBAR_PROGESS && this$0.initVideoInfo && this$0.isPlaying) {
            String str = this$0.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("init: setSeekBar Player-");
            sb.append(message.arg1);
            Log.d(str, sb.toString());
            AliPlayer aliPlayer = this$0.mPlayer;
            Intrinsics.checkNotNull(aliPlayer);
            aliPlayer.seekTo(message.arg1, IPlayer.SeekMode.Accurate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean init$lambda$5(KnowledgeMapActivity this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.curTouchTime = System.currentTimeMillis();
        WeakHandler weakHandler = null;
        if (this$0.playComplete) {
            this$0.setComplete();
        } else {
            ImageView imageView = this$0.playPauseView;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                imageView = null;
            }
            imageView.setImageResource(this$0.isPlaying ? R.drawable.alivc_playstate_pause : R.drawable.alivc_playstate_play);
        }
        ImageView imageView2 = this$0.playPauseView;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
            imageView2 = null;
        }
        if (imageView2.getVisibility() == 8) {
            ImageView imageView3 = this$0.playPauseView;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                imageView3 = null;
            }
            ViewExtensionsKt.visible(imageView3);
        } else {
            ImageView imageView4 = this$0.playPauseView;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                imageView4 = null;
            }
            if (imageView4.getVisibility() == 0 && this$0.isPlaying) {
                ImageView imageView5 = this$0.playPauseView;
                if (imageView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                    imageView5 = null;
                }
                ViewExtensionsKt.gone(imageView5);
            }
        }
        WeakHandler weakHandler2 = this$0.weakHandler;
        if (weakHandler2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("weakHandler");
        } else {
            weakHandler = weakHandler2;
        }
        weakHandler.sendEmptyMessageDelayed(this$0.WHAT_TOUCH, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$6(KnowledgeMapActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int screenWidth = ScreenUtil.getScreenWidth(this$0) - DpOrPxUtils.dip2px(this$0, 32.0f);
        double d3 = screenWidth / 1.22d;
        ImageView imageView = this$0.imageTop;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageTop");
            imageView = null;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = (int) d3;
        ImageView imageView3 = this$0.imageTop;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageTop");
        } else {
            imageView2 = imageView3;
        }
        imageView2.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initActivityData(KnowledgeTaskListBean data) {
        TextView textView = this.tvTime;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTime");
            textView = null;
        }
        textView.setText("数据统计时间：" + data.getStat_time());
        TextView textView2 = this.tvRule;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvRule");
            textView2 = null;
        }
        String rule_url = data.getRule_url();
        boolean z2 = true;
        textView2.setVisibility(rule_url == null || rule_url.length() == 0 ? 8 : 0);
        this.mAdapter = new KnowledgeMapAdapter();
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRecyclerView");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRecyclerView");
            recyclerView2 = null;
        }
        KnowledgeMapAdapter knowledgeMapAdapter = this.mAdapter;
        if (knowledgeMapAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            knowledgeMapAdapter = null;
        }
        recyclerView2.setAdapter(knowledgeMapAdapter);
        List<KnowledgeTaskListItemBean> list = data.getList();
        if (list == null || list.isEmpty()) {
            KnowledgeMapAdapter knowledgeMapAdapter2 = this.mAdapter;
            if (knowledgeMapAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                knowledgeMapAdapter2 = null;
            }
            knowledgeMapAdapter2.setList(new ArrayList());
            LinearLayout linearLayout = this.layoutTaskList;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutTaskList");
                linearLayout = null;
            }
            ViewExtensionsKt.gone(linearLayout);
        } else {
            KnowledgeMapAdapter knowledgeMapAdapter3 = this.mAdapter;
            if (knowledgeMapAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                knowledgeMapAdapter3 = null;
            }
            knowledgeMapAdapter3.setList(data.getList());
            LinearLayout linearLayout2 = this.layoutTaskList;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutTaskList");
                linearLayout2 = null;
            }
            ViewExtensionsKt.visible(linearLayout2);
        }
        KnowledgeMapAdapter knowledgeMapAdapter4 = this.mAdapter;
        if (knowledgeMapAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            knowledgeMapAdapter4 = null;
        }
        knowledgeMapAdapter4.setTaskClick(new Function1<String, Unit>() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity.initActivityData.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KnowledgeMapActivity.this.getTaskById(it);
            }
        });
        String info_url = data.getInfo_url();
        if (info_url != null && info_url.length() != 0) {
            z2 = false;
        }
        if (z2) {
            return;
        }
        String info_url2 = data.getInfo_url();
        ImageView imageView2 = this.imageTop;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageTop");
        } else {
            imageView = imageView2;
        }
        GlideUtils.loadImageDef(this, info_url2, imageView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initPlayer() {
        AliPlayer aliPlayerCreateAliPlayer = AliPlayerFactory.createAliPlayer(this);
        this.mPlayer = aliPlayerCreateAliPlayer;
        if (aliPlayerCreateAliPlayer != null) {
            Intrinsics.checkNotNull(aliPlayerCreateAliPlayer);
            aliPlayerCreateAliPlayer.setOnErrorListener(new IPlayer.OnErrorListener() { // from class: com.psychiatrygarden.activity.knowledge.h0
                @Override // com.aliyun.player.IPlayer.OnErrorListener
                public final void onError(ErrorInfo errorInfo) {
                    KnowledgeMapActivity.initPlayer$lambda$7(this.f12617a, errorInfo);
                }
            });
            AliPlayer aliPlayer = this.mPlayer;
            Intrinsics.checkNotNull(aliPlayer);
            aliPlayer.setOnCompletionListener(new IPlayer.OnCompletionListener() { // from class: com.psychiatrygarden.activity.knowledge.i0
                @Override // com.aliyun.player.IPlayer.OnCompletionListener
                public final void onCompletion() {
                    KnowledgeMapActivity.initPlayer$lambda$8(this.f12619a);
                }
            });
            AliPlayer aliPlayer2 = this.mPlayer;
            Intrinsics.checkNotNull(aliPlayer2);
            aliPlayer2.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.activity.knowledge.x
                @Override // com.aliyun.player.IPlayer.OnPreparedListener
                public final void onPrepared() {
                    KnowledgeMapActivity.initPlayer$lambda$9(this.f12639a);
                }
            });
            AliPlayer aliPlayer3 = this.mPlayer;
            Intrinsics.checkNotNull(aliPlayer3);
            aliPlayer3.setOnStateChangedListener(new IPlayer.OnStateChangedListener() { // from class: com.psychiatrygarden.activity.knowledge.y
                @Override // com.aliyun.player.IPlayer.OnStateChangedListener
                public final void onStateChanged(int i2) {
                    KnowledgeMapActivity.initPlayer$lambda$10(this.f12640a, i2);
                }
            });
            AliPlayer aliPlayer4 = this.mPlayer;
            Intrinsics.checkNotNull(aliPlayer4);
            aliPlayer4.setScaleMode(IPlayer.ScaleMode.SCALE_ASPECT_FIT);
            AliPlayer aliPlayer5 = this.mPlayer;
            Intrinsics.checkNotNull(aliPlayer5);
            aliPlayer5.setOnInfoListener(new IPlayer.OnInfoListener() { // from class: com.psychiatrygarden.activity.knowledge.z
                @Override // com.aliyun.player.IPlayer.OnInfoListener
                public final void onInfo(InfoBean infoBean) {
                    KnowledgeMapActivity.initPlayer$lambda$11(this.f12641a, infoBean);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPlayer$lambda$10(KnowledgeMapActivity this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d(this$0.TAG, "setOnStateChangedListener:" + i2);
        this$0.isPlaying = i2 == 3;
        if (i2 != -1) {
            TextView textView = null;
            if (i2 == 3) {
                ImageView imageView = this$0.playPauseView;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                    imageView = null;
                }
                imageView.setImageResource(R.drawable.alivc_playstate_pause);
                ImageView imageView2 = this$0.playPauseView;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                    imageView2 = null;
                }
                ViewExtensionsKt.gone(imageView2);
                SeekBar seekBar = this$0.seekBar;
                if (seekBar == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                    seekBar = null;
                }
                ViewExtensionsKt.visible(seekBar);
                TextView textView2 = this$0.tvVideoCurTime;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvVideoCurTime");
                    textView2 = null;
                }
                ViewExtensionsKt.visible(textView2);
                TextView textView3 = this$0.tvVideoCountTime;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvVideoCountTime");
                } else {
                    textView = textView3;
                }
                ViewExtensionsKt.visible(textView);
                return;
            }
            if (i2 == 4 || i2 == 5 || i2 == 6) {
                if (i2 == 6 || this$0.playComplete) {
                    this$0.setComplete();
                } else {
                    ImageView imageView3 = this$0.playPauseView;
                    if (imageView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                        imageView3 = null;
                    }
                    imageView3.setImageResource(R.drawable.alivc_playstate_play);
                }
                ImageView imageView4 = this$0.playPauseView;
                if (imageView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                    imageView4 = null;
                }
                ViewExtensionsKt.visible(imageView4);
                SeekBar seekBar2 = this$0.seekBar;
                if (seekBar2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                    seekBar2 = null;
                }
                ViewExtensionsKt.visible(seekBar2);
                TextView textView4 = this$0.tvVideoCurTime;
                if (textView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvVideoCurTime");
                    textView4 = null;
                }
                ViewExtensionsKt.visible(textView4);
                TextView textView5 = this$0.tvVideoCountTime;
                if (textView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvVideoCountTime");
                } else {
                    textView = textView5;
                }
                ViewExtensionsKt.visible(textView);
                return;
            }
            if (i2 != 7) {
                return;
            }
        }
        Log.d(this$0.TAG, "initPlayerVideo: 播放异常");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPlayer$lambda$11(KnowledgeMapActivity this$0, InfoBean infoBean) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.duration <= 0 || infoBean.getCode() != InfoCode.CurrentPosition || this$0.isTouchSeekBar) {
            return;
        }
        this$0.isPlaying = true;
        this$0.progress = infoBean.getExtraValue();
        long extraValue = infoBean.getExtraValue();
        TextView textView = null;
        if (Build.VERSION.SDK_INT >= 24) {
            SeekBar seekBar = this$0.seekBar;
            if (seekBar == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                seekBar = null;
            }
            seekBar.setProgress((int) extraValue, true);
        } else {
            SeekBar seekBar2 = this$0.seekBar;
            if (seekBar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                seekBar2 = null;
            }
            seekBar2.setProgress((int) extraValue);
        }
        Log.d(this$0.TAG, "设置seekBar progress:" + ((int) extraValue));
        TextView textView2 = this$0.tvVideoCurTime;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvVideoCurTime");
        } else {
            textView = textView2;
        }
        textView.setText(TimeFormater.formatMs(extraValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPlayer$lambda$7(KnowledgeMapActivity this$0, ErrorInfo errorInfo) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d(this$0.TAG, "OnErrorListener: " + errorInfo.getMsg());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPlayer$lambda$8(KnowledgeMapActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.playComplete = true;
        this$0.isPlaying = false;
        AliPlayer aliPlayer = this$0.mPlayer;
        Intrinsics.checkNotNull(aliPlayer);
        aliPlayer.stop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initPlayer$lambda$9(KnowledgeMapActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.d(this$0.TAG, "setOnPreparedListener:");
        this$0.playComplete = false;
        AliPlayer aliPlayer = this$0.mPlayer;
        Intrinsics.checkNotNull(aliPlayer);
        long duration = aliPlayer.getDuration();
        TextView textView = this$0.tvVideoCountTime;
        SeekBar seekBar = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvVideoCountTime");
            textView = null;
        }
        textView.setText(TimeFormater.formatMs(duration));
        if (duration > 0) {
            this$0.duration = duration;
            SeekBar seekBar2 = this$0.seekBar;
            if (seekBar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                seekBar2 = null;
            }
            seekBar2.setMax((int) duration);
            String str = this$0.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("init: setSeekBarMAX-");
            SeekBar seekBar3 = this$0.seekBar;
            if (seekBar3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            } else {
                seekBar = seekBar3;
            }
            sb.append(seekBar.getMax());
            Log.d(str, sb.toString());
        }
        AliPlayer aliPlayer2 = this$0.mPlayer;
        Intrinsics.checkNotNull(aliPlayer2);
        aliPlayer2.start();
        if (this$0.curPlayPosition > 0) {
            AliPlayer aliPlayer3 = this$0.mPlayer;
            Intrinsics.checkNotNull(aliPlayer3);
            aliPlayer3.seekTo(this$0.curPlayPosition);
        }
        this$0.isPlaying = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initViewPic() {
        RelativeLayout relativeLayout = this.layoutVideo;
        ImageView imageView = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutVideo");
            relativeLayout = null;
        }
        relativeLayout.setVisibility(this.haveVideo ? 0 : 8);
        ImageView imageView2 = this.imgVideoIntroduce;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgVideoIntroduce");
            imageView2 = null;
        }
        imageView2.setVisibility(this.haveVideo ? 0 : 8);
        if (this.haveVideo) {
            KnowledgeTaskListBean knowledgeTaskListBean = this.taskData;
            Intrinsics.checkNotNull(knowledgeTaskListBean);
            String cover_url = knowledgeTaskListBean.getCover_url();
            Intrinsics.checkNotNull(cover_url);
            RoundedImageView roundedImageView = this.mImgVideo;
            if (roundedImageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mImgVideo");
                roundedImageView = null;
            }
            GlideUtils.loadImageDef(this, cover_url, roundedImageView);
            RoundedImageView roundedImageView2 = this.mImgVideo;
            if (roundedImageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mImgVideo");
                roundedImageView2 = null;
            }
            roundedImageView2.setVisibility(0);
            ImageView imageView3 = this.playPauseView;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
                imageView3 = null;
            }
            imageView3.setVisibility(0);
            SeekBar seekBar = this.seekBar;
            if (seekBar == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                seekBar = null;
            }
            ViewExtensionsKt.gone(seekBar);
            TextView textView = this.tvVideoCurTime;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvVideoCurTime");
                textView = null;
            }
            ViewExtensionsKt.gone(textView);
            TextView textView2 = this.tvVideoCountTime;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvVideoCountTime");
                textView2 = null;
            }
            ViewExtensionsKt.gone(textView2);
            ImageView imageView4 = this.playPauseView;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
            } else {
                imageView = imageView4;
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.g0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KnowledgeMapActivity.initViewPic$lambda$12(this.f12615c, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViewPic$lambda$12(KnowledgeMapActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.beginPlay();
    }

    private final void playRelease() {
        AliPlayer aliPlayer = this.mPlayer;
        if (aliPlayer != null) {
            Intrinsics.checkNotNull(aliPlayer);
            aliPlayer.stop();
            AliPlayer aliPlayer2 = this.mPlayer;
            Intrinsics.checkNotNull(aliPlayer2);
            aliPlayer2.release();
            this.initVideoInfo = false;
        }
    }

    private final void setComplete() {
        ImageView imageView = this.playPauseView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("playPauseView");
            imageView = null;
        }
        imageView.setImageResource(R.drawable.alivc_player_icon_trailer_play_again);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateList(KnowledgeTaskListBean data) {
        List<KnowledgeTaskListItemBean> list = data.getList();
        LinearLayout linearLayout = null;
        if (list == null || list.isEmpty()) {
            KnowledgeMapAdapter knowledgeMapAdapter = this.mAdapter;
            if (knowledgeMapAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                knowledgeMapAdapter = null;
            }
            knowledgeMapAdapter.setList(new ArrayList());
            LinearLayout linearLayout2 = this.layoutTaskList;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutTaskList");
            } else {
                linearLayout = linearLayout2;
            }
            ViewExtensionsKt.gone(linearLayout);
            return;
        }
        KnowledgeMapAdapter knowledgeMapAdapter2 = this.mAdapter;
        if (knowledgeMapAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            knowledgeMapAdapter2 = null;
        }
        knowledgeMapAdapter2.setList(data.getList());
        LinearLayout linearLayout3 = this.layoutTaskList;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutTaskList");
        } else {
            linearLayout = linearLayout3;
        }
        ViewExtensionsKt.visible(linearLayout);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.layoutIntroduction);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.layoutIntroduction)");
        this.imageTop = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.lyBarLayout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.lyBarLayout)");
        this.lyBarLayout = (RelativeLayout) viewFindViewById2;
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout relativeLayout = this.lyBarLayout;
        ImageView imageView = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lyBarLayout");
            relativeLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = statusBarHeight;
        RelativeLayout relativeLayout2 = this.lyBarLayout;
        if (relativeLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lyBarLayout");
            relativeLayout2 = null;
        }
        relativeLayout2.setLayoutParams(layoutParams2);
        View viewFindViewById3 = findViewById(R.id.tvTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tvTime)");
        this.tvTime = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tvRule);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tvRule)");
        this.tvRule = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.layoutTaskList);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.layoutTaskList)");
        this.layoutTaskList = (LinearLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.layoutContent);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.layoutContent)");
        this.layoutContent = (RelativeLayout) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.emptyView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.emptyView)");
        this.emptyView = (CustomEmptyView) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.tvVideoCountTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.tvVideoCountTime)");
        this.tvVideoCountTime = (TextView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.tvVideoCurTime);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.tvVideoCurTime)");
        this.tvVideoCurTime = (TextView) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.recyclerViewKnowledge);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.recyclerViewKnowledge)");
        this.mRecyclerView = (RecyclerView) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.imgVideoIntroduce);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.imgVideoIntroduce)");
        this.imgVideoIntroduce = (ImageView) viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.layoutVideo);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.layoutVideo)");
        this.layoutVideo = (RelativeLayout) viewFindViewById12;
        View viewFindViewById13 = findViewById(R.id.ly_video_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.ly_video_view)");
        this.mLyVideoView = (RelativeLayout) viewFindViewById13;
        View viewFindViewById14 = findViewById(R.id.img_video);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "findViewById(R.id.img_video)");
        this.mImgVideo = (RoundedImageView) viewFindViewById14;
        View viewFindViewById15 = findViewById(R.id.seekbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById15, "findViewById(R.id.seekbar)");
        this.seekBar = (SeekBar) viewFindViewById15;
        View viewFindViewById16 = findViewById(R.id.imgStatus);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById16, "findViewById(R.id.imgStatus)");
        this.playPauseView = (ImageView) viewFindViewById16;
        View viewFindViewById17 = findViewById(R.id.videoView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById17, "findViewById(R.id.videoView)");
        this.videoView = (SurfaceView) viewFindViewById17;
        View viewFindViewById18 = findViewById(R.id.actionbarBack);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById18, "findViewById(R.id.actionbarBack)");
        ((ImageView) viewFindViewById18).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeMapActivity.init$lambda$0(this.f12601c, view);
            }
        });
        CustomEmptyView customEmptyView = this.emptyView;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeMapActivity.init$lambda$1(this.f12605c, view);
            }
        });
        TextView textView = this.tvRule;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvRule");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeMapActivity.init$lambda$3(this.f12607c, view);
            }
        });
        this.weakHandler = new WeakHandler(this, new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.activity.knowledge.d0
            @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
            public final void handlerMessage(Message message) {
                KnowledgeMapActivity.init$lambda$4(this.f12609a, message);
            }
        });
        SurfaceView surfaceView = this.videoView;
        if (surfaceView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView = null;
        }
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity.init.5
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(@NotNull SurfaceHolder holder, int format, int width, int height) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                if (KnowledgeMapActivity.this.haveVideo) {
                    AliPlayer aliPlayer = KnowledgeMapActivity.this.mPlayer;
                    Intrinsics.checkNotNull(aliPlayer);
                    aliPlayer.surfaceChanged();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(@NotNull SurfaceHolder holder) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                if (KnowledgeMapActivity.this.haveVideo) {
                    KnowledgeMapActivity.this.initPlayer();
                    AliPlayer aliPlayer = KnowledgeMapActivity.this.mPlayer;
                    Intrinsics.checkNotNull(aliPlayer);
                    aliPlayer.setSurface(holder.getSurface());
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(@NotNull SurfaceHolder holder) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                if (KnowledgeMapActivity.this.haveVideo) {
                    AliPlayer aliPlayer = KnowledgeMapActivity.this.mPlayer;
                    Intrinsics.checkNotNull(aliPlayer);
                    aliPlayer.setSurface(null);
                }
            }
        });
        SurfaceView surfaceView2 = this.videoView;
        if (surfaceView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView2 = null;
        }
        surfaceView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.knowledge.e0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return KnowledgeMapActivity.init$lambda$5(this.f12611c, view, motionEvent);
            }
        });
        SurfaceView surfaceView3 = this.videoView;
        if (surfaceView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView3 = null;
        }
        surfaceView3.setOutlineProvider(new ViewOutlineProvider() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity.init.7
            @Override // android.view.ViewOutlineProvider
            public void getOutline(@NotNull View view, @NotNull Outline outline) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                outline.setRoundRect(new Rect(0, 0, (rect.right - rect.left) - 0, (rect.bottom - rect.top) - 0), DpOrPxUtils.dip2px(KnowledgeMapActivity.this, 16.0f));
            }
        });
        SurfaceView surfaceView4 = this.videoView;
        if (surfaceView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView4 = null;
        }
        surfaceView4.setClipToOutline(true);
        SeekBar seekBar = this.seekBar;
        if (seekBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            seekBar = null;
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity.init.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(@Nullable SeekBar seekBar2, int progress, boolean fromUser) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(@Nullable SeekBar seekBar2) {
                KnowledgeMapActivity.this.isTouchSeekBar = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(@NotNull SeekBar seekBar2) {
                Intrinsics.checkNotNullParameter(seekBar2, "seekBar");
                KnowledgeMapActivity.this.isTouchSeekBar = false;
                Log.d(KnowledgeMapActivity.this.TAG, "onStopTrackingTouch:--- ");
                int progress = seekBar2.getProgress();
                WeakHandler weakHandler = KnowledgeMapActivity.this.weakHandler;
                WeakHandler weakHandler2 = null;
                if (weakHandler == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("weakHandler");
                    weakHandler = null;
                }
                Message messageObtainMessage = weakHandler.obtainMessage();
                Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "weakHandler.obtainMessage()");
                messageObtainMessage.what = KnowledgeMapActivity.this.WHAT_SEEKBAR_PROGESS;
                messageObtainMessage.arg1 = progress;
                WeakHandler weakHandler3 = KnowledgeMapActivity.this.weakHandler;
                if (weakHandler3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("weakHandler");
                } else {
                    weakHandler2 = weakHandler3;
                }
                weakHandler2.sendMessage(messageObtainMessage);
            }
        });
        getTaskListData$default(this, false, 1, null);
        ImageView imageView2 = this.imageTop;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageTop");
        } else {
            imageView = imageView2;
        }
        imageView.post(new Runnable() { // from class: com.psychiatrygarden.activity.knowledge.f0
            @Override // java.lang.Runnable
            public final void run() {
                KnowledgeMapActivity.init$lambda$6(this.f12613c);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        playRelease();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.taskData == null || !this.haveVideo) {
            return;
        }
        beginPlay();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (this.mPlayer == null || !this.haveVideo) {
            return;
        }
        playRelease();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_knowledge_map);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
