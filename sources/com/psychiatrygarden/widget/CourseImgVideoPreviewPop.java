package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.source.VidSts;
import com.google.android.exoplayer2.C;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.bean.CourseAkBean;
import com.psychiatrygarden.bean.CourseDetailBannerItem;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000k\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t*\u0001\u0017\u0018\u00002\u00020\u0001:\u0001-BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\"\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\t2\b\b\u0002\u0010'\u001a\u00020\u0013H\u0002J\b\u0010(\u001a\u00020\tH\u0014J\b\u0010)\u001a\u00020#H\u0014J\b\u0010*\u001a\u00020#H\u0014J\u000e\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020!R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/psychiatrygarden/widget/CourseImgVideoPreviewPop;", "Lcom/lxj/xpopup/impl/FullScreenPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "data", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/CourseDetailBannerItem;", "Lkotlin/collections/ArrayList;", "curPosition", "", "curPlayPosition", "", "akBean", "Lcom/psychiatrygarden/bean/CourseAkBean;", "(Landroid/content/Context;Ljava/util/ArrayList;IJLcom/psychiatrygarden/bean/CourseAkBean;)V", "countdownTimer", "Landroid/os/CountDownTimer;", "duration", "firstInit", "", "hasInitVideo", "isPlaying", "mAdapter", "com/psychiatrygarden/widget/CourseImgVideoPreviewPop$mAdapter$1", "Lcom/psychiatrygarden/widget/CourseImgVideoPreviewPop$mAdapter$1;", "mPlayer", "Lcom/aliyun/player/AliPlayer;", "playComplete", "playerState", "progress", "seekBar", "Landroid/widget/ProgressBar;", "updateListener", "Lcom/psychiatrygarden/widget/CourseImgVideoPreviewPop$UpdateListener;", "getCourseAk", "", "vid", "", "videoIndex", "play", "getImplLayoutId", "onCreate", "onDismiss", "setUpdateListener", NotifyType.LIGHTS, "UpdateListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCourseImgVideoPreviewPop.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CourseImgVideoPreviewPop.kt\ncom/psychiatrygarden/widget/CourseImgVideoPreviewPop\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,348:1\n1#2:349\n350#3,7:350\n350#3,7:357\n*S KotlinDebug\n*F\n+ 1 CourseImgVideoPreviewPop.kt\ncom/psychiatrygarden/widget/CourseImgVideoPreviewPop\n*L\n279#1:350,7\n197#1:357,7\n*E\n"})
/* loaded from: classes6.dex */
public final class CourseImgVideoPreviewPop extends FullScreenPopupView {

    @Nullable
    private final CourseAkBean akBean;

    @Nullable
    private CountDownTimer countdownTimer;
    private final long curPlayPosition;
    private final int curPosition;

    @NotNull
    private final ArrayList<CourseDetailBannerItem> data;
    private long duration;
    private boolean firstInit;
    private boolean hasInitVideo;
    private boolean isPlaying;

    @NotNull
    private final CourseImgVideoPreviewPop$mAdapter$1 mAdapter;
    private AliPlayer mPlayer;
    private boolean playComplete;
    private int playerState;
    private long progress;
    private ProgressBar seekBar;

    @Nullable
    private UpdateListener updateListener;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/widget/CourseImgVideoPreviewPop$UpdateListener;", "", PLVUpdateMicSiteEvent.EVENT_NAME, "", "progress", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface UpdateListener {
        void update(long progress);
    }

    public /* synthetic */ CourseImgVideoPreviewPop(Context context, ArrayList arrayList, int i2, long j2, CourseAkBean courseAkBean, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, arrayList, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? 0L : j2, (i3 & 16) != 0 ? null : courseAkBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getCourseAk(final String vid, final int videoIndex, final boolean play) {
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.CourseImgVideoPreviewPop.getCourseAk.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                JSONObject jSONObjectOptJSONObject;
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                if (TextUtils.isEmpty(t2)) {
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code")) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
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
                    vidSts.setQuality(AliPlayUtils.getPlayVideoDefinition(this.getContext()), true);
                    AliPlayer aliPlayer = this.mPlayer;
                    AliPlayer aliPlayer2 = null;
                    if (aliPlayer == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                        aliPlayer = null;
                    }
                    aliPlayer.setDataSource(vidSts);
                    if (videoIndex == this.curPosition || play) {
                        AliPlayer aliPlayer3 = this.mPlayer;
                        if (aliPlayer3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                        } else {
                            aliPlayer2 = aliPlayer3;
                        }
                        aliPlayer2.prepare();
                        this.hasInitVideo = true;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static /* synthetic */ void getCourseAk$default(CourseImgVideoPreviewPop courseImgVideoPreviewPop, String str, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            z2 = false;
        }
        courseImgVideoPreviewPop.getCourseAk(str, i2, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(CourseImgVideoPreviewPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.playComplete = true;
        this$0.isPlaying = false;
        AliPlayer aliPlayer = this$0.mPlayer;
        if (aliPlayer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer = null;
        }
        aliPlayer.stop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(CourseImgVideoPreviewPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AliPlayer aliPlayer = this$0.mPlayer;
        AliPlayer aliPlayer2 = null;
        if (aliPlayer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer = null;
        }
        long duration = aliPlayer.getDuration();
        if (duration > 0) {
            this$0.duration = duration;
            ProgressBar progressBar = this$0.seekBar;
            if (progressBar == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
                progressBar = null;
            }
            progressBar.setMax((int) duration);
        }
        AliPlayer aliPlayer3 = this$0.mPlayer;
        if (aliPlayer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer3 = null;
        }
        aliPlayer3.start();
        if (this$0.curPlayPosition > 0) {
            AliPlayer aliPlayer4 = this$0.mPlayer;
            if (aliPlayer4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            } else {
                aliPlayer2 = aliPlayer4;
            }
            aliPlayer2.seekTo(this$0.curPlayPosition);
        }
        this$0.isPlaying = true;
        Iterator<CourseDetailBannerItem> it = this$0.data.iterator();
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else {
                if (it.next().getType() == 2) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        if (i2 != -1) {
            this$0.mAdapter.notifyItemChanged(i2, 999);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$5(final CourseImgVideoPreviewPop this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.playerState = i2;
        this$0.isPlaying = i2 == 3;
        if (i2 == -1 || i2 == 7) {
            View viewByPosition = this$0.mAdapter.getViewByPosition(0, R.id.videoView);
            if (viewByPosition != null) {
                ViewExtensionsKt.gone(viewByPosition);
            }
            View viewByPosition2 = this$0.mAdapter.getViewByPosition(0, R.id.iv_img);
            if (viewByPosition2 != null) {
                ViewExtensionsKt.visible(viewByPosition2);
            }
            View viewByPosition3 = this$0.mAdapter.getViewByPosition(0, R.id.iv_video_play);
            if (viewByPosition3 != null) {
                ViewExtensionsKt.visible(viewByPosition3);
                return;
            }
            return;
        }
        View viewByPosition4 = this$0.mAdapter.getViewByPosition(0, R.id.videoView);
        if (viewByPosition4 != null) {
            ViewExtensionsKt.visible(viewByPosition4);
        }
        View viewByPosition5 = this$0.mAdapter.getViewByPosition(0, R.id.iv_video_play);
        if (viewByPosition5 != null) {
            ViewExtensionsKt.visible(viewByPosition5);
        }
        View viewByPosition6 = this$0.mAdapter.getViewByPosition(0, R.id.iv_img);
        if (viewByPosition6 != null) {
            ViewExtensionsKt.gone(viewByPosition6);
        }
        View viewByPosition7 = this$0.mAdapter.getViewByPosition(0, R.id.iv_video_play);
        Intrinsics.checkNotNull(viewByPosition7, "null cannot be cast to non-null type android.widget.ImageView");
        ImageView imageView = (ImageView) viewByPosition7;
        if (this$0.firstInit) {
            imageView.setVisibility(8);
            this$0.firstInit = false;
        }
        this$0.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.z3
            @Override // java.lang.Runnable
            public final void run() {
                CourseImgVideoPreviewPop.onCreate$lambda$5$lambda$4(this.f17141c);
            }
        }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        ProgressBar progressBar = this$0.seekBar;
        ProgressBar progressBar2 = null;
        if (progressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            progressBar = null;
        }
        if (progressBar.getVisibility() != 0) {
            ProgressBar progressBar3 = this$0.seekBar;
            if (progressBar3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            } else {
                progressBar2 = progressBar3;
            }
            ViewExtensionsKt.visible(progressBar2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$5$lambda$4(CourseImgVideoPreviewPop this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View viewByPosition = this$0.mAdapter.getViewByPosition(0, R.id.iv_video_play);
        if (viewByPosition != null) {
            ViewExtensionsKt.gone(viewByPosition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$6(CourseImgVideoPreviewPop this$0, InfoBean infoBean) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.duration <= 0 || infoBean.getCode() != InfoCode.CurrentPosition) {
            return;
        }
        this$0.isPlaying = true;
        this$0.progress = infoBean.getExtraValue();
        long extraValue = infoBean.getExtraValue();
        ProgressBar progressBar = null;
        if (Build.VERSION.SDK_INT >= 24) {
            ProgressBar progressBar2 = this$0.seekBar;
            if (progressBar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            } else {
                progressBar = progressBar2;
            }
            progressBar.setProgress((int) extraValue, true);
            return;
        }
        ProgressBar progressBar3 = this$0.seekBar;
        if (progressBar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
        } else {
            progressBar = progressBar3;
        }
        progressBar.setProgress((int) extraValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$7(CourseImgVideoPreviewPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CountDownTimer countDownTimer = this$0.countdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_layout_course_img_video_view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        Object next;
        Object next2;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.seekbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.seekbar)");
        this.seekBar = (ProgressBar) viewFindViewById;
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.viewpager);
        ProgressBar progressBar = this.seekBar;
        Object obj = null;
        AliPlayer aliPlayer = null;
        if (progressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            progressBar = null;
        }
        progressBar.setEnabled(false);
        ProgressBar progressBar2 = this.seekBar;
        if (progressBar2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            progressBar2 = null;
        }
        progressBar2.setClickable(false);
        AliPlayer aliPlayerCreateAliPlayer = AliPlayerFactory.createAliPlayer(getContext());
        Intrinsics.checkNotNullExpressionValue(aliPlayerCreateAliPlayer, "createAliPlayer(context)");
        this.mPlayer = aliPlayerCreateAliPlayer;
        if (aliPlayerCreateAliPlayer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayerCreateAliPlayer = null;
        }
        aliPlayerCreateAliPlayer.setOnCompletionListener(new IPlayer.OnCompletionListener() { // from class: com.psychiatrygarden.widget.a4
            @Override // com.aliyun.player.IPlayer.OnCompletionListener
            public final void onCompletion() {
                CourseImgVideoPreviewPop.onCreate$lambda$1(this.f16306a);
            }
        });
        AliPlayer aliPlayer2 = this.mPlayer;
        if (aliPlayer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer2 = null;
        }
        aliPlayer2.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.widget.b4
            @Override // com.aliyun.player.IPlayer.OnPreparedListener
            public final void onPrepared() {
                CourseImgVideoPreviewPop.onCreate$lambda$3(this.f16333a);
            }
        });
        AliPlayer aliPlayer3 = this.mPlayer;
        if (aliPlayer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer3 = null;
        }
        aliPlayer3.setOnStateChangedListener(new IPlayer.OnStateChangedListener() { // from class: com.psychiatrygarden.widget.c4
            @Override // com.aliyun.player.IPlayer.OnStateChangedListener
            public final void onStateChanged(int i2) {
                CourseImgVideoPreviewPop.onCreate$lambda$5(this.f16359a, i2);
            }
        });
        AliPlayer aliPlayer4 = this.mPlayer;
        if (aliPlayer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer4 = null;
        }
        aliPlayer4.setScaleMode(IPlayer.ScaleMode.SCALE_ASPECT_FIT);
        AliPlayer aliPlayer5 = this.mPlayer;
        if (aliPlayer5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            aliPlayer5 = null;
        }
        aliPlayer5.setOnInfoListener(new IPlayer.OnInfoListener() { // from class: com.psychiatrygarden.widget.d4
            @Override // com.aliyun.player.IPlayer.OnInfoListener
            public final void onInfo(InfoBean infoBean) {
                CourseImgVideoPreviewPop.onCreate$lambda$6(this.f16395a, infoBean);
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.e4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseImgVideoPreviewPop.onCreate$lambda$7(this.f16426c, view);
            }
        });
        final TextView textView = (TextView) findViewById(R.id.tv_indicator);
        StringBuilder sb = new StringBuilder();
        sb.append(this.curPosition + 1);
        sb.append('/');
        sb.append(this.data.size());
        textView.setText(sb.toString());
        viewPager2.setAdapter(this.mAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.widget.CourseImgVideoPreviewPop.onCreate.6
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:46:0x00b6  */
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onPageSelected(int r11) {
                /*
                    Method dump skipped, instructions count: 316
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.CourseImgVideoPreviewPop.AnonymousClass6.onPageSelected(int):void");
            }
        });
        if (this.curPosition < this.data.size()) {
            viewPager2.setCurrentItem(this.curPosition);
        }
        Iterator it = this.data.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if ((((CourseDetailBannerItem) next).getType() == 2) != false) {
                    break;
                }
            }
        }
        if ((next != null) == true) {
            VidSts vidSts = new VidSts();
            Iterator it2 = this.data.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next2 = null;
                    break;
                } else {
                    next2 = it2.next();
                    if ((((CourseDetailBannerItem) next2).getType() == 2) != false) {
                        break;
                    }
                }
            }
            Intrinsics.checkNotNull(next2);
            vidSts.setVid(((CourseDetailBannerItem) next2).getVideoId());
            Iterator<CourseDetailBannerItem> it3 = this.data.iterator();
            int i2 = 0;
            while (true) {
                if (!it3.hasNext()) {
                    i2 = -1;
                    break;
                } else {
                    if ((it3.next().getType() == 2) == true) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            int i3 = i2;
            if (this.akBean == null) {
                Iterator it4 = this.data.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        break;
                    }
                    Object next3 = it4.next();
                    if ((((CourseDetailBannerItem) next3).getType() == 2) != false) {
                        obj = next3;
                        break;
                    }
                }
                Intrinsics.checkNotNull(obj);
                String videoId = ((CourseDetailBannerItem) obj).getVideoId();
                Intrinsics.checkNotNullExpressionValue(videoId, "data.find { it.type == C…em.TYPE_VIDEO }!!.videoId");
                getCourseAk$default(this, videoId, i3, false, 4, null);
                return;
            }
            vidSts.setRegion(GlobalPlayerConfig.mRegion);
            vidSts.setAccessKeyId(this.akBean.getAkId());
            vidSts.setSecurityToken(this.akBean.getSt());
            vidSts.setAccessKeySecret(this.akBean.getAkSecret());
            vidSts.setQuality(AliPlayUtils.getPlayVideoDefinition(getContext()), true);
            AliPlayer aliPlayer6 = this.mPlayer;
            if (aliPlayer6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                aliPlayer6 = null;
            }
            aliPlayer6.setDataSource(vidSts);
            if (i3 == this.curPosition) {
                AliPlayer aliPlayer7 = this.mPlayer;
                if (aliPlayer7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                } else {
                    aliPlayer = aliPlayer7;
                }
                aliPlayer.prepare();
                this.hasInitVideo = true;
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        AliPlayer aliPlayer;
        Object next;
        UpdateListener updateListener;
        super.onDismiss();
        Iterator<T> it = this.data.iterator();
        while (true) {
            aliPlayer = null;
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((CourseDetailBannerItem) next).getType() == 2) {
                    break;
                }
            }
        }
        if (((CourseDetailBannerItem) next) != null && this.hasInitVideo) {
            AliPlayer aliPlayer2 = this.mPlayer;
            if (aliPlayer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                aliPlayer2 = null;
            }
            aliPlayer2.stop();
            AliPlayer aliPlayer3 = this.mPlayer;
            if (aliPlayer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            } else {
                aliPlayer = aliPlayer3;
            }
            aliPlayer.release();
        }
        long j2 = this.progress;
        if (j2 <= 0 || (updateListener = this.updateListener) == null) {
            return;
        }
        updateListener.update(j2);
    }

    public final void setUpdateListener(@NotNull UpdateListener l2) {
        Intrinsics.checkNotNullParameter(l2, "l");
        this.updateListener = l2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CourseImgVideoPreviewPop(@NotNull Context context, @NotNull ArrayList<CourseDetailBannerItem> data, int i2, long j2, @Nullable CourseAkBean courseAkBean) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        this.curPosition = i2;
        this.curPlayPosition = j2;
        this.akBean = courseAkBean;
        this.firstInit = true;
        this.mAdapter = new CourseImgVideoPreviewPop$mAdapter$1(this, context, data);
    }
}
