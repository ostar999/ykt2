package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager2.widget.ViewPager2;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.source.VidSts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.CommonProblemActivity;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.bean.CourseAkBean;
import com.psychiatrygarden.bean.CourseDetailBannerItem;
import com.psychiatrygarden.bean.CourseDetailBean;
import com.psychiatrygarden.bean.CourseGiftItem;
import com.psychiatrygarden.bean.CourseLabel;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.bean.SkuItem;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CourseImgVideoPreviewPop;
import com.psychiatrygarden.widget.CourseSkuPop;
import com.psychiatrygarden.widget.DetailCourseTopInfoWidget;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000Ð\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\r*\u0004\u0011(7H\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001a\u0010Q\u001a\u00020+2\u0006\u0010R\u001a\u00020+2\b\b\u0002\u0010S\u001a\u00020\u0018H\u0002J\u0006\u0010T\u001a\u00020UJ\b\u0010V\u001a\u00020UH\u0002J\u0010\u0010W\u001a\u00020U2\u0006\u0010\u001f\u001a\u000201H\u0016J\u0018\u0010X\u001a\u00020U2\u0006\u0010\u001f\u001a\u0002012\b\u0010<\u001a\u0004\u0018\u00010=J\b\u0010Y\u001a\u00020UH\u0002J\b\u0010Z\u001a\u00020UH\u0014J\u0010\u0010[\u001a\u00020U2\u0006\u0010\\\u001a\u00020+H\u0002J\u0010\u0010]\u001a\u00020U2\u0006\u0010^\u001a\u00020\bH\u0002J\b\u0010_\u001a\u00020UH\u0002J\u0010\u0010`\u001a\u00020U2\u0006\u0010a\u001a\u00020\bH\u0002R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012R\u001e\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0014j\b\u0012\u0004\u0012\u00020\u0015`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010#\u001a\u0012\u0012\u0004\u0012\u00020$0\u0014j\b\u0012\u0004\u0012\u00020$`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0004\n\u0002\u0010)R\u001e\u0010*\u001a\u0012\u0012\u0004\u0012\u00020+0\u0014j\b\u0012\u0004\u0012\u00020+`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020.0-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082.¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u00010.X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0004\n\u0002\u00108R\u000e\u00109\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010<\u001a\u0004\u0018\u00010=X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020AX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020AX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u0004\u0018\u00010FX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010G\u001a\u00020HX\u0082\u0004¢\u0006\u0004\n\u0002\u0010IR\u000e\u0010J\u001a\u00020:X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020:X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020:X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010M\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020PX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lcom/psychiatrygarden/widget/DetailCourseTopInfoWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "akBean", "Lcom/psychiatrygarden/bean/CourseAkBean;", "aliPlayer", "Lcom/aliyun/player/AliPlayer;", "autoScrollRunnable", "Ljava/lang/Runnable;", "bannerAdapter", "com/psychiatrygarden/widget/DetailCourseTopInfoWidget$bannerAdapter$1", "Lcom/psychiatrygarden/widget/DetailCourseTopInfoWidget$bannerAdapter$1;", "bannerData", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/CourseDetailBannerItem;", "Lkotlin/collections/ArrayList;", "cancelAutoScroll", "", "clickedPlay", "commonProblemView", "Landroid/view/View;", "countTime", "countdownTimer", "Landroid/os/CountDownTimer;", "data", "Lcom/psychiatrygarden/bean/CourseDetailBean;", "enterTime", "", "giftList", "Lcom/psychiatrygarden/bean/CourseGiftItem;", "hasActivity", "hasFinished", "hourAdapter", "com/psychiatrygarden/widget/DetailCourseTopInfoWidget$hourAdapter$1", "Lcom/psychiatrygarden/widget/DetailCourseTopInfoWidget$hourAdapter$1;", "hourCharList", "", "imgViewArr", "Landroid/util/ArrayMap;", "Landroid/widget/ImageView;", "isPlay", "item", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "ivLoading", "llDay", "mHandler", "Landroid/os/Handler;", "mSkuAdapter", "com/psychiatrygarden/widget/DetailCourseTopInfoWidget$mSkuAdapter$1", "Lcom/psychiatrygarden/widget/DetailCourseTopInfoWidget$mSkuAdapter$1;", "minute1", "Landroid/widget/TextView;", "minute2", "owner", "Landroidx/lifecycle/LifecycleOwner;", "promotionView", "remainCountDown", "rvHours", "Landroidx/recyclerview/widget/RecyclerView;", "rvSku", "second1", "second2", "surfaceView", "Landroid/view/SurfaceView;", "tagAdapter", "com/psychiatrygarden/widget/DetailCourseTopInfoWidget$tagAdapter$1", "Lcom/psychiatrygarden/widget/DetailCourseTopInfoWidget$tagAdapter$1;", "tvDay", "tvSku", "tvValidDate", "videoItemView", "videoProgress", "viewPager", "Landroidx/viewpager2/widget/ViewPager2;", "checkColor", "color", "fontColor", "fixActivityCountDown", "", "initCountDown", com.umeng.socialize.tracker.a.f23806c, "initParams", "initView", "onDetachedFromWindow", "preparePlay", "vid", "showBannerImgPop", "pos", "showPreviewPop", "updateCountDownShow", "totalSecond", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDetailCourseTopInfoWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailCourseTopInfoWidget.kt\ncom/psychiatrygarden/widget/DetailCourseTopInfoWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,961:1\n350#2,7:962\n1855#2,2:969\n1855#2,2:971\n766#2:973\n857#2,2:974\n1864#2,3:977\n1#3:976\n13635#4,2:980\n*S KotlinDebug\n*F\n+ 1 DetailCourseTopInfoWidget.kt\ncom/psychiatrygarden/widget/DetailCourseTopInfoWidget\n*L\n327#1:962,7\n378#1:969,2\n518#1:971,2\n581#1:973\n581#1:974,2\n742#1:977,3\n852#1:980,2\n*E\n"})
/* loaded from: classes6.dex */
public final class DetailCourseTopInfoWidget extends LinearLayout implements BaseContentWidget {

    @Nullable
    private CourseAkBean akBean;

    @NotNull
    private AliPlayer aliPlayer;

    @NotNull
    private final Runnable autoScrollRunnable;

    @NotNull
    private final DetailCourseTopInfoWidget$bannerAdapter$1 bannerAdapter;

    @NotNull
    private final ArrayList<CourseDetailBannerItem> bannerData;
    private boolean cancelAutoScroll;
    private boolean clickedPlay;

    @NotNull
    private View commonProblemView;
    private int countTime;

    @Nullable
    private CountDownTimer countdownTimer;
    private CourseDetailBean data;
    private long enterTime;

    @NotNull
    private final ArrayList<CourseGiftItem> giftList;
    private boolean hasActivity;
    private boolean hasFinished;

    @NotNull
    private final DetailCourseTopInfoWidget$hourAdapter$1 hourAdapter;

    @NotNull
    private final ArrayList<String> hourCharList;

    @NotNull
    private final ArrayMap<Integer, ImageView> imgViewArr;
    private boolean isPlay;
    private GoodsDetailItem item;

    @Nullable
    private ImageView ivLoading;
    private View llDay;

    @NotNull
    private final Handler mHandler;

    @NotNull
    private final DetailCourseTopInfoWidget$mSkuAdapter$1 mSkuAdapter;

    @NotNull
    private TextView minute1;

    @NotNull
    private TextView minute2;

    @Nullable
    private LifecycleOwner owner;

    @NotNull
    private View promotionView;
    private int remainCountDown;

    @NotNull
    private RecyclerView rvHours;

    @NotNull
    private RecyclerView rvSku;

    @NotNull
    private TextView second1;

    @NotNull
    private TextView second2;

    @Nullable
    private SurfaceView surfaceView;

    @NotNull
    private final DetailCourseTopInfoWidget$tagAdapter$1 tagAdapter;
    private TextView tvDay;
    private TextView tvSku;
    private TextView tvValidDate;

    @Nullable
    private View videoItemView;
    private long videoProgress;

    @NotNull
    private ViewPager2 viewPager;

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/widget/DetailCourseTopInfoWidget$initView$14", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$initView$14, reason: invalid class name */
    public static final class AnonymousClass14 extends BaseQuickAdapter<String, BaseViewHolder> {
        public AnonymousClass14(ArrayList<String> arrayList, final DetailCourseTopInfoWidget detailCourseTopInfoWidget) {
            super(R.layout.item_detail_tag, null, 2, null);
            setList(arrayList);
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.u6
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    DetailCourseTopInfoWidget.AnonymousClass14._init_$lambda$0(detailCourseTopInfoWidget, baseQuickAdapter, view, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(DetailCourseTopInfoWidget this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            this$0.findViewById(R.id.fl_gift).performClick();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull String item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            String str = SkinManager.getCurrentSkinType(getContext()) == 0 ? "#1ADBAB55" : "#1A9E7B3D";
            String str2 = SkinManager.getCurrentSkinType(getContext()) == 0 ? "#A36725" : "#A8A093";
            View view = holder.itemView;
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
            TextView textView = (TextView) view;
            textView.setText(item);
            textView.setTextColor(Color.parseColor(str2));
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(SizeUtil.dp2px(getContext(), 4) * 1.0f);
            gradientDrawable.setColor(Color.parseColor(str));
            textView.setBackground(gradientDrawable);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseTopInfoWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v17, types: [com.psychiatrygarden.widget.DetailCourseTopInfoWidget$mSkuAdapter$1] */
    /* JADX WARN: Type inference failed for: r3v18, types: [com.psychiatrygarden.widget.DetailCourseTopInfoWidget$tagAdapter$1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [androidx.recyclerview.widget.RecyclerView$Adapter, com.psychiatrygarden.widget.DetailCourseTopInfoWidget$hourAdapter$1] */
    @JvmOverloads
    public DetailCourseTopInfoWidget(@NotNull final Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.bannerData = new ArrayList<>();
        this.giftList = new ArrayList<>();
        this.imgViewArr = new ArrayMap<>();
        this.hourCharList = new ArrayList<>();
        ?? r3 = new BaseQuickAdapter<String, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$hourAdapter$1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull String item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                holder.setGone(R.id.tv_mh, holder.getLayoutPosition() != getData().size() - 1).setText(R.id.tv_hour, item);
            }
        };
        this.hourAdapter = r3;
        this.mHandler = new Handler(Looper.getMainLooper());
        setOrientation(1);
        View.inflate(context, R.layout.layout_detail_top_info, this);
        View viewFindViewById = findViewById(R.id.ll_day);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.ll_day)");
        this.llDay = viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tv_day);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_day)");
        this.tvDay = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tv_sku_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_sku_text)");
        this.tvSku = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tv_valid_date);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tv_valid_date)");
        this.tvValidDate = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.ll_common_problem);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.ll_common_problem)");
        this.commonProblemView = viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.rvSku);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.rvSku)");
        this.rvSku = (RecyclerView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.ctl_top);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.ctl_top)");
        this.promotionView = viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.rvHours);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.rvHours)");
        this.rvHours = (RecyclerView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.tv_minute_1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.tv_minute_1)");
        this.minute1 = (TextView) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.tv_minute_2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.tv_minute_2)");
        this.minute2 = (TextView) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.tv_second_1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.tv_second_1)");
        this.second1 = (TextView) viewFindViewById11;
        View viewFindViewById12 = findViewById(R.id.tv_second_2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById(R.id.tv_second_2)");
        this.second2 = (TextView) viewFindViewById12;
        this.rvHours.setAdapter(r3);
        View viewFindViewById13 = findViewById(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById(R.id.viewpager)");
        this.viewPager = (ViewPager2) viewFindViewById13;
        if (this.rvHours.getItemAnimator() instanceof SimpleItemAnimator) {
            this.rvHours.setItemAnimator(null);
        }
        AliPlayer aliPlayerCreateAliPlayer = AliPlayerFactory.createAliPlayer(context);
        Intrinsics.checkNotNullExpressionValue(aliPlayerCreateAliPlayer, "createAliPlayer(context)");
        this.aliPlayer = aliPlayerCreateAliPlayer;
        aliPlayerCreateAliPlayer.setScaleMode(IPlayer.ScaleMode.SCALE_ASPECT_FIT);
        this.aliPlayer.setLoop(false);
        this.aliPlayer.setOnInfoListener(new IPlayer.OnInfoListener() { // from class: com.psychiatrygarden.widget.p6
            @Override // com.aliyun.player.IPlayer.OnInfoListener
            public final void onInfo(InfoBean infoBean) {
                DetailCourseTopInfoWidget._init_$lambda$0(this.f16783a, infoBean);
            }
        });
        this.autoScrollRunnable = new Runnable() { // from class: com.psychiatrygarden.widget.q6
            @Override // java.lang.Runnable
            public final void run() {
                DetailCourseTopInfoWidget.autoScrollRunnable$lambda$1(this.f16816c);
            }
        };
        this.bannerAdapter = new DetailCourseTopInfoWidget$bannerAdapter$1(this, context);
        this.mSkuAdapter = new BaseQuickAdapter<SkuItem, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$mSkuAdapter$1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull SkuItem item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                holder.itemView.setSelected(item.getSelect());
                holder.setGone(R.id.iv_select, !item.getSelect());
                TextView textView = (TextView) holder.getView(R.id.tv_sku_name);
                String title_sku = item.getTitle_sku();
                if (title_sku == null && (title_sku = item.getTitle()) == null) {
                    title_sku = "";
                }
                if (title_sku.length() > 7) {
                    StringBuilder sb = new StringBuilder();
                    String strSubstring = title_sku.substring(0, 7);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    sb.append(strSubstring);
                    sb.append("...");
                    textView.setText(sb.toString());
                } else {
                    textView.setText(title_sku);
                }
                textView.setSelected(item.getSelect());
            }
        };
        this.tagAdapter = new BaseQuickAdapter<CourseLabel, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$tagAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_sku_detail_tag, null, 2, null);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull CourseLabel item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
                RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
                ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = holder.getLayoutPosition() == 0 ? 0 : SizeUtil.dp2px(context, 8);
                holder.itemView.setLayoutParams(layoutParams2);
                View view = holder.itemView;
                Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view).setText(item.getValue());
                View view2 = holder.itemView;
                Intrinsics.checkNotNull(view2, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view2).getPaint().setFakeBoldText(true);
                GradientDrawable gradientDrawable = new GradientDrawable();
                DetailCourseTopInfoWidget detailCourseTopInfoWidget = this;
                Context context2 = context;
                gradientDrawable.setColor(Color.parseColor(detailCourseTopInfoWidget.checkColor(item.getBgColor(), false)));
                gradientDrawable.setCornerRadius(SizeUtil.dp2px(context2, 2) * 1.0f);
                View view3 = holder.itemView;
                Intrinsics.checkNotNull(view3, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view3).setTextColor(Color.parseColor(this.checkColor(item.getFontColor(), true)));
                if (!item.getPromotion() && !item.getGift() && !item.getCoupon()) {
                    gradientDrawable.setStroke((int) TypedValue.applyDimension(1, 0.5f, context.getResources().getDisplayMetrics()), Color.parseColor(this.checkColor(item.getFontColor(), true)));
                }
                holder.itemView.setBackground(gradientDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(DetailCourseTopInfoWidget this$0, InfoBean infoBean) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (infoBean.getCode() == InfoCode.CurrentPosition) {
            this$0.videoProgress = infoBean.getExtraValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void autoScrollRunnable$lambda$1(DetailCourseTopInfoWidget this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.cancelAutoScroll) {
            return;
        }
        int currentItem = this$0.viewPager.getCurrentItem() + 1;
        if (currentItem > this$0.bannerData.size() - 1) {
            currentItem = 0;
        }
        this$0.viewPager.setCurrentItem(currentItem, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String checkColor(String color, boolean fontColor) {
        Regex regex = new Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$");
        if (TextUtils.isEmpty(color) || !regex.matches(color)) {
            return !fontColor ? "#F95843" : "#141516";
        }
        if (regex.matches(color) || color.charAt(0) != '#' || color.length() != 4) {
            return color;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        sb.append(color.charAt(1));
        sb.append(color.charAt(1));
        sb.append(color.charAt(2));
        sb.append(color.charAt(2));
        sb.append(color.charAt(3));
        sb.append(color.charAt(3));
        return sb.toString();
    }

    public static /* synthetic */ String checkColor$default(DetailCourseTopInfoWidget detailCourseTopInfoWidget, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return detailCourseTopInfoWidget.checkColor(str, z2);
    }

    private final void initCountDown() throws NumberFormatException {
        CourseDetailBean courseDetailBean = this.data;
        if (courseDetailBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            courseDetailBean = null;
        }
        String promotionCountDown = courseDetailBean.getPromotionCountDown();
        Intrinsics.checkNotNull(promotionCountDown);
        int i2 = Integer.parseInt(promotionCountDown);
        this.countTime = i2;
        CountDownTimer countDownTimer = new CountDownTimer(i2 * 1000) { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.initCountDown.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                DetailCourseTopInfoWidget.this.minute1.setText("0");
                DetailCourseTopInfoWidget.this.minute2.setText("0");
                DetailCourseTopInfoWidget.this.second1.setText("0");
                DetailCourseTopInfoWidget.this.second2.setText("0");
                DetailCourseTopInfoWidget.this.hourCharList.clear();
                DetailCourseTopInfoWidget.this.hourCharList.add("0");
                DetailCourseTopInfoWidget.this.hourCharList.add("0");
                setList(DetailCourseTopInfoWidget.this.hourCharList);
                DetailCourseTopInfoWidget.this.hasFinished = true;
                ViewExtensionsKt.gone(DetailCourseTopInfoWidget.this.promotionView);
            }

            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 2147483647L) {
                    DetailCourseTopInfoWidget.this.remainCountDown = (int) millisUntilFinished;
                }
                DetailCourseTopInfoWidget.this.updateCountDownShow((int) (millisUntilFinished / 1000));
            }
        };
        this.countdownTimer = countDownTimer;
        countDownTimer.start();
    }

    /* JADX WARN: Removed duplicated region for block: B:328:0x0767  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x07bb  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0931  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x093f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x018f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void initView() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 3540
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.initView():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$10(View page, float f2) {
        Intrinsics.checkNotNullParameter(page, "page");
        page.setScaleY(((1 - Math.abs(f2)) * 0.15f) + 0.85f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initView$lambda$11(DetailCourseTopInfoWidget this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.cancelAutoScroll = true;
        this$0.mHandler.removeCallbacksAndMessages(this$0.autoScrollRunnable);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$13(final DetailCourseTopInfoWidget this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        XPopup.Builder builderPopupHeight = new XPopup.Builder(this$0.getContext()).popupHeight((this$0.getContext().getResources().getDisplayMetrics().heightPixels - this$0.getContext().getResources().getDimensionPixelSize(R.dimen.dp_96)) + StatusBarUtil.getStatusBarHeight(this$0.getContext()));
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        CourseDetailBean courseDetailBean = this$0.data;
        if (courseDetailBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            courseDetailBean = null;
        }
        builderPopupHeight.asCustom(new CourseSkuPop(context, courseDetailBean, new CourseSkuPop.SkuUpdateListener() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$initView$15$1
            @Override // com.psychiatrygarden.widget.CourseSkuPop.SkuUpdateListener
            public void updateCourseShow(@NotNull String courseId) throws PackageManager.NameNotFoundException {
                Intrinsics.checkNotNullParameter(courseId, "courseId");
                Context context2 = this.this$0.getContext();
                ActCourseOrGoodsDetail actCourseOrGoodsDetail = context2 instanceof ActCourseOrGoodsDetail ? (ActCourseOrGoodsDetail) context2 : null;
                if (actCourseOrGoodsDetail != null) {
                    actCourseOrGoodsDetail.loadSkuItemDetail(courseId);
                }
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$3(DetailCourseTopInfoWidget this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.findViewById(R.id.fl_gift).performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$4(DetailCourseTopInfoWidget this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Context context = this$0.getContext();
        Intent intent = new Intent(this$0.getContext(), (Class<?>) CommonProblemActivity.class);
        Gson gson = new Gson();
        CourseDetailBean courseDetailBean = this$0.data;
        if (courseDetailBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            courseDetailBean = null;
        }
        context.startActivity(intent.putExtra("data", gson.toJson(courseDetailBean.getCommonProblem())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$7(DetailCourseTopInfoWidget this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.aliPlayer.stop();
        if (this$0.bannerData.size() > 0) {
            CourseDetailBannerItem courseDetailBannerItem = this$0.bannerData.get(0);
            Intrinsics.checkNotNullExpressionValue(courseDetailBannerItem, "bannerData[0]");
            CourseDetailBannerItem courseDetailBannerItem2 = courseDetailBannerItem;
            if (courseDetailBannerItem2.getType() == 2) {
                courseDetailBannerItem2.setPlay(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$8(DetailCourseTopInfoWidget this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isPlay = i2 == 3;
        if (this$0.bannerData.size() > 0) {
            CourseDetailBannerItem courseDetailBannerItem = this$0.bannerData.get(0);
            Intrinsics.checkNotNullExpressionValue(courseDetailBannerItem, "bannerData[0]");
            CourseDetailBannerItem courseDetailBannerItem2 = courseDetailBannerItem;
            if (i2 == 4 || i2 == 5) {
                courseDetailBannerItem2.setPlay(false);
            }
            boolean zIsPlay = courseDetailBannerItem2.isPlay();
            boolean z2 = this$0.isPlay;
            if (zIsPlay != z2) {
                courseDetailBannerItem2.setPlay(z2);
                this$0.bannerAdapter.notifyItemChanged(0);
            }
        }
        LogUtils.d("state", String.valueOf(i2));
        if (i2 == -1 || i2 == 7) {
            this$0.bannerAdapter.notifyItemChanged(0);
            ToastUtil.shortToast(this$0.getContext(), "播放异常");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$9(DetailCourseTopInfoWidget this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.aliPlayer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preparePlay(final String vid) {
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.preparePlay.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C06241) t2);
                if (TextUtils.isEmpty(t2)) {
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        ToastUtil.shortToast(DetailCourseTopInfoWidget.this.getContext(), "获取视频信息失败");
                        View view = DetailCourseTopInfoWidget.this.videoItemView;
                        if (view != null) {
                            View viewFindViewById = view.findViewById(R.id.fl_video);
                            if (viewFindViewById != null) {
                                Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(R.id.fl_video)");
                                ViewExtensionsKt.gone(viewFindViewById);
                            }
                            View viewFindViewById2 = view.findViewById(R.id.iv_video_play);
                            if (viewFindViewById2 != null) {
                                Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<View>(R.id.iv_video_play)");
                                ViewExtensionsKt.visible(viewFindViewById2);
                            }
                            View viewFindViewById3 = view.findViewById(R.id.iv_img);
                            if (viewFindViewById3 != null) {
                                Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById<View>(R.id.iv_img)");
                                ViewExtensionsKt.visible(viewFindViewById3);
                            }
                        }
                        DetailCourseTopInfoWidget.this.mHandler.postDelayed(DetailCourseTopInfoWidget.this.autoScrollRunnable, 5000L);
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (jSONObjectOptJSONObject == null) {
                        return;
                    }
                    DetailCourseTopInfoWidget.this.akBean = (CourseAkBean) new Gson().fromJson(jSONObjectOptJSONObject.toString(), CourseAkBean.class);
                    String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akId"));
                    String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akSecret"));
                    String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("st"));
                    VidSts vidSts = new VidSts();
                    vidSts.setVid(vid);
                    vidSts.setAccessKeyId(strDecode);
                    vidSts.setAccessKeySecret(strDecode2);
                    vidSts.setSecurityToken(strDecode3);
                    vidSts.setRegion(GlobalPlayerConfig.mRegion);
                    vidSts.setQuality(AliPlayUtils.getPlayVideoDefinition(DetailCourseTopInfoWidget.this.getContext()), true);
                    DetailCourseTopInfoWidget.this.aliPlayer.setDataSource(vidSts);
                    DetailCourseTopInfoWidget.this.mHandler.removeCallbacksAndMessages(null);
                    DetailCourseTopInfoWidget.this.aliPlayer.prepare();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showBannerImgPop(int pos) {
        if (this.bannerData.get(pos).getType() == 1) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            new XPopup.Builder(getContext()).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.showBannerImgPop.1
                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onDismiss(@Nullable BasePopupView popupView) {
                    super.onDismiss(popupView);
                    if ((SkinManager.getCurrentSkinType(DetailCourseTopInfoWidget.this.getContext()) == 0) && (DetailCourseTopInfoWidget.this.getContext() instanceof BaseActivity)) {
                        Context context2 = DetailCourseTopInfoWidget.this.getContext();
                        Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type com.psychiatrygarden.activity.BaseActivity");
                        ((BaseActivity) context2).setContentFillFromStatusBar(false);
                    }
                }

                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onShow(@Nullable BasePopupView popupView) {
                    super.onShow(popupView);
                    if ((SkinManager.getCurrentSkinType(DetailCourseTopInfoWidget.this.getContext()) == 0) && (DetailCourseTopInfoWidget.this.getContext() instanceof BaseActivity)) {
                        Context context2 = DetailCourseTopInfoWidget.this.getContext();
                        Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type com.psychiatrygarden.activity.BaseActivity");
                        ((BaseActivity) context2).setContentFillFromStatusBar(true);
                    }
                }
            }).asCustom(new CourseImgVideoPreviewPop(context, this.bannerData, pos, this.videoProgress, null)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showPreviewPop() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        CourseImgVideoPreviewPop courseImgVideoPreviewPop = new CourseImgVideoPreviewPop(context, this.bannerData, this.viewPager.getCurrentItem(), this.videoProgress, null);
        courseImgVideoPreviewPop.setUpdateListener(new CourseImgVideoPreviewPop.UpdateListener() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.showPreviewPop.1
            @Override // com.psychiatrygarden.widget.CourseImgVideoPreviewPop.UpdateListener
            public void update(long progress) {
                DetailCourseTopInfoWidget.this.aliPlayer.seekTo(progress, IPlayer.SeekMode.Accurate);
                DetailCourseTopInfoWidget.this.videoProgress = progress;
            }
        });
        new XPopup.Builder(getContext()).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.showPreviewPop.2
            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onDismiss(@Nullable BasePopupView popupView) {
                super.onDismiss(popupView);
                if (!DetailCourseTopInfoWidget.this.isPlay && DetailCourseTopInfoWidget.this.videoProgress > 0) {
                    DetailCourseTopInfoWidget.this.aliPlayer.start();
                }
                if ((SkinManager.getCurrentSkinType(DetailCourseTopInfoWidget.this.getContext()) == 0) && (DetailCourseTopInfoWidget.this.getContext() instanceof BaseActivity)) {
                    Context context2 = DetailCourseTopInfoWidget.this.getContext();
                    Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type com.psychiatrygarden.activity.BaseActivity");
                    ((BaseActivity) context2).setContentFillFromStatusBar(false);
                }
            }

            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onShow(@Nullable BasePopupView popupView) {
                DetailCourseTopInfoWidget.this.aliPlayer.pause();
                if ((SkinManager.getCurrentSkinType(DetailCourseTopInfoWidget.this.getContext()) == 0) && (DetailCourseTopInfoWidget.this.getContext() instanceof BaseActivity)) {
                    Context context2 = DetailCourseTopInfoWidget.this.getContext();
                    Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type com.psychiatrygarden.activity.BaseActivity");
                    ((BaseActivity) context2).setContentFillFromStatusBar(true);
                }
                super.onShow(popupView);
            }
        }).asCustom(courseImgVideoPreviewPop).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateCountDownShow(int totalSecond) {
        int i2 = totalSecond / 86400;
        TextView textView = null;
        if (i2 > 0) {
            View view = this.llDay;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llDay");
                view = null;
            }
            ViewExtensionsKt.visible(view);
        } else {
            View view2 = this.llDay;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("llDay");
                view2 = null;
            }
            ViewExtensionsKt.gone(view2);
        }
        TextView textView2 = this.tvDay;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDay");
        } else {
            textView = textView2;
        }
        textView.setText(String.valueOf(i2));
        int i3 = totalSecond % 86400;
        int i4 = i3 / 3600;
        int i5 = i3 % 3600;
        int i6 = i5 / 60;
        int i7 = i5 % 60;
        char[] charArray = String.valueOf(i4).toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        char[] charArray2 = String.valueOf(i6).toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray2, "this as java.lang.String).toCharArray()");
        char[] charArray3 = String.valueOf(i7).toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray3, "this as java.lang.String).toCharArray()");
        this.hourCharList.clear();
        for (char c3 : charArray) {
            this.hourCharList.add(String.valueOf(c3));
        }
        if (String.valueOf(i4).length() == 1) {
            this.hourCharList.add(0, "0");
        }
        if (!TextUtils.equals(CollectionsKt___CollectionsKt.joinToString$default(getData(), null, null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$updateCountDownShow$hourStr$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "";
            }
        }, 31, null), CollectionsKt___CollectionsKt.joinToString$default(this.hourCharList, null, null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget.updateCountDownShow.2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "";
            }
        }, 31, null))) {
            LogUtils.d("updateCountDownShow", "刷新小时显示");
            setList(this.hourCharList);
        }
        StringBuilder sb = new StringBuilder();
        sb.append((Object) this.minute1.getText());
        sb.append((Object) this.minute2.getText());
        if (!Intrinsics.areEqual(sb.toString(), String.valueOf(i6))) {
            LogUtils.d("updateCountDownShow", "刷新分钟显示");
            if (i6 >= 10) {
                this.minute1.setText(String.valueOf(charArray2[0]));
                this.minute2.setText(String.valueOf(charArray2[1]));
            } else if (i6 == 0) {
                this.minute1.setText("0");
                this.minute2.setText("0");
            } else {
                this.minute1.setText("0");
                this.minute2.setText(String.valueOf(charArray2[0]));
            }
        }
        if (i7 >= 10) {
            this.second1.setText(String.valueOf(charArray3[0]));
            this.second2.setText(String.valueOf(charArray3[1]));
        } else if (i7 == 0) {
            this.second1.setText("0");
            this.second2.setText("0");
        } else {
            this.second1.setText("0");
            this.second2.setText(String.valueOf(charArray3[0]));
        }
    }

    public final void fixActivityCountDown() throws NumberFormatException {
        if (this.data == null || !this.hasActivity || this.remainCountDown <= 0) {
            return;
        }
        long jElapsedRealtime = (SystemClock.elapsedRealtime() - this.enterTime) / 1000;
        CourseDetailBean courseDetailBean = this.data;
        CourseDetailBean courseDetailBean2 = null;
        if (courseDetailBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            courseDetailBean = null;
        }
        CourseDetailBean courseDetailBean3 = this.data;
        if (courseDetailBean3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            courseDetailBean3 = null;
        }
        Intrinsics.checkNotNull(courseDetailBean3.getPromotionCountDown());
        courseDetailBean.setPromotionCountDown(String.valueOf(Integer.parseInt(r5) - jElapsedRealtime));
        long j2 = this.remainCountDown;
        CourseDetailBean courseDetailBean4 = this.data;
        if (courseDetailBean4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            courseDetailBean4 = null;
        }
        Intrinsics.checkNotNull(courseDetailBean4.getPromotionCountDown());
        if (j2 <= Integer.parseInt(r2) - jElapsedRealtime || this.hasFinished) {
            return;
        }
        LogUtils.d("restoreCountDown", "倒计时异常，重新启动倒计时");
        CountDownTimer countDownTimer = this.countdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.countdownTimer = null;
        this.enterTime = SystemClock.elapsedRealtime();
        CourseDetailBean courseDetailBean5 = this.data;
        if (courseDetailBean5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        } else {
            courseDetailBean2 = courseDetailBean5;
        }
        String promotionCountDown = courseDetailBean2.getPromotionCountDown();
        Intrinsics.checkNotNull(promotionCountDown);
        this.remainCountDown = Integer.parseInt(promotionCountDown);
        initCountDown();
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(data, "data");
        initParams(data, null);
    }

    public final void initParams(@NotNull GoodsDetailItem data, @Nullable LifecycleOwner owner) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(data, "data");
        CourseDetailBean courseData = data.getCourseData();
        Intrinsics.checkNotNullExpressionValue(courseData, "data.courseData");
        this.data = courseData;
        this.item = data;
        this.owner = owner;
        initView();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.mHandler.removeCallbacksAndMessages(null);
        CountDownTimer countDownTimer = this.countdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.aliPlayer.stop();
        this.aliPlayer.release();
        super.onDetachedFromWindow();
    }

    public /* synthetic */ DetailCourseTopInfoWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
