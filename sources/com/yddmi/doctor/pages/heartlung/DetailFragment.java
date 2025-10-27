package com.yddmi.doctor.pages.heartlung;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.mvvm.base.fragment.BaseVMFragment;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.TextViewExtKt;
import com.hjq.toast.Toaster;
import com.lwkandroid.widget.ngv.DefaultNgvAdapter;
import com.lwkandroid.widget.ngv.NgvChildImageView;
import com.lwkandroid.widget.ngv.NineGridView;
import com.noober.background.drawable.DrawableCreator;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.databinding.GarbledHeartlungFragmentDetailBinding;
import com.yddmi.doctor.entity.request.FavoriteSaveReq;
import com.yddmi.doctor.entity.result.HeartDetail;
import com.yddmi.doctor.entity.result.LocalMedia;
import com.yddmi.doctor.entity.result.RatingCriteria;
import com.yddmi.doctor.pages.heartlung.DetailFragment;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.GlideDisplayer;
import com.yddmi.doctor.widget.SimplePagerSizeTitleView;
import com.yikaobang.yixue.R2;
import com.zhpan.indicator.utils.IndicatorUtils;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\u0018\u0000 02\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u00010B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u001dH\u0016J\b\u0010!\u001a\u00020\u001dH\u0016J\b\u0010\"\u001a\u00020\u001dH\u0016J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0006\u0010%\u001a\u00020\u001dJ\u0016\u0010&\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001fH\u0002J\b\u0010*\u001a\u00020\u001dH\u0002J\b\u0010+\u001a\u00020\u001dH\u0002J\b\u0010,\u001a\u00020\u001dH\u0002J\b\u0010-\u001a\u00020\u001dH\u0002J\b\u0010.\u001a\u00020\u001dH\u0002J\u0010\u0010/\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\u00160\u0018j\b\u0012\u0004\u0012\u00020\u0016`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/DetailFragment;", "Lcom/catchpig/mvvm/base/fragment/BaseVMFragment;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungFragmentDetailBinding;", "Lcom/yddmi/doctor/pages/heartlung/DetailViewModel;", "index", "", "total", "code", "data", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "(IIILcom/yddmi/doctor/entity/result/HeartDetail;)V", "mActivity", "Lcom/yddmi/doctor/pages/heartlung/HeartlungDetailActivity;", "mAdapter", "Lcom/yddmi/doctor/pages/heartlung/AdapterScore;", "mCode", "mData", "mFragmentContainerHelper", "Lnet/lucode/hackware/magicindicator/FragmentContainerHelper;", "mIndex", "mNineGridAdapter", "Lcom/lwkandroid/widget/ngv/DefaultNgvAdapter;", "Lcom/yddmi/doctor/entity/result/LocalMedia;", "mPictureList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "mTotal", "maxSeekBarDefault", "httpPostAppFavoriteSave", "", "master", "", "initFlow", "initParam", "initView", "showImagePreview", "position", "viewAudioComplete", "viewAudioProgress", "currentProgress", "viewSeekBarCanSeek", "can", "viewShowAudio", "viewShowCode", "viewShowGoBtnCollectImgv", "viewShowImage", "viewShowIndicator", "viewShowIndicatorChange", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDetailFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailFragment.kt\ncom/yddmi/doctor/pages/heartlung/DetailFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,508:1\n1855#2,2:509\n*S KotlinDebug\n*F\n+ 1 DetailFragment.kt\ncom/yddmi/doctor/pages/heartlung/DetailFragment\n*L\n203#1:509,2\n*E\n"})
/* loaded from: classes6.dex */
public final class DetailFragment extends BaseVMFragment<GarbledHeartlungFragmentDetailBinding, DetailViewModel> {

    @NotNull
    private static final String TAG = "DetailFragment";
    private HeartlungDetailActivity mActivity;

    @Nullable
    private AdapterScore mAdapter;
    private final int mCode;

    @NotNull
    private final HeartDetail mData;

    @NotNull
    private final FragmentContainerHelper mFragmentContainerHelper;
    private final int mIndex;
    private DefaultNgvAdapter<LocalMedia> mNineGridAdapter;

    @NotNull
    private final ArrayList<LocalMedia> mPictureList;
    private final int mTotal;
    private final int maxSeekBarDefault;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.DetailFragment$initFlow$1", f = "DetailFragment.kt", i = {}, l = {138}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.DetailFragment$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06561 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06561(Continuation<? super C06561> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return DetailFragment.this.new C06561(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06561) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> dataIndicatorMsf = DetailFragment.access$getViewModel(DetailFragment.this).getDataIndicatorMsf();
                final DetailFragment detailFragment = DetailFragment.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            detailFragment.viewShowIndicator();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataIndicatorMsf.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungFragmentDetailBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.DetailFragment$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06581 extends Lambda implements Function1<GarbledHeartlungFragmentDetailBinding, Unit> {
        public C06581() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(DetailFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            DetailFragment.httpPostAppFavoriteSave$default(this$0, false, 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(DetailFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            HeartlungDetailActivity heartlungDetailActivity = this$0.mActivity;
            if (heartlungDetailActivity == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mActivity");
                heartlungDetailActivity = null;
            }
            heartlungDetailActivity.viewShowCorrectionPop(this$0.mData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(DetailFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpPostAppFavoriteSave(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(DetailFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            HeartlungDetailActivity heartlungDetailActivity = this$0.mActivity;
            if (heartlungDetailActivity == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mActivity");
                heartlungDetailActivity = null;
            }
            heartlungDetailActivity.viewShowMasteredPop();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungFragmentDetailBinding garbledHeartlungFragmentDetailBinding) {
            invoke2(garbledHeartlungFragmentDetailBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungFragmentDetailBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            TextView textView = bodyBinding.nameTv;
            String name = DetailFragment.this.mData.getName();
            if (name == null) {
                name = "";
            }
            textView.setText(name);
            bodyBinding.numTv.setText((DetailFragment.this.mIndex + 1) + "/" + DetailFragment.this.mTotal);
            BLTextView goBtv = bodyBinding.goBtv;
            Intrinsics.checkNotNullExpressionValue(goBtv, "goBtv");
            final DetailFragment detailFragment = DetailFragment.this;
            ViewExtKt.setDebounceClickListener$default(goBtv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailFragment.C06581.invoke$lambda$0(detailFragment, view);
                }
            }, 0L, 2, null);
            ConstraintLayout errorCl = bodyBinding.errorCl;
            Intrinsics.checkNotNullExpressionValue(errorCl, "errorCl");
            final DetailFragment detailFragment2 = DetailFragment.this;
            ViewExtKt.setDebounceClickListener$default(errorCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailFragment.C06581.invoke$lambda$1(detailFragment2, view);
                }
            }, 0L, 2, null);
            ConstraintLayout collectCl = bodyBinding.collectCl;
            Intrinsics.checkNotNullExpressionValue(collectCl, "collectCl");
            final DetailFragment detailFragment3 = DetailFragment.this;
            ViewExtKt.setDebounceClickListener$default(collectCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailFragment.C06581.invoke$lambda$2(detailFragment3, view);
                }
            }, 0L, 2, null);
            ConstraintLayout pointCl = bodyBinding.pointCl;
            Intrinsics.checkNotNullExpressionValue(pointCl, "pointCl");
            final DetailFragment detailFragment4 = DetailFragment.this;
            ViewExtKt.setDebounceClickListener$default(pointCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailFragment.C06581.invoke$lambda$3(detailFragment4, view);
                }
            }, 0L, 2, null);
            bodyBinding.nineGridView.setDividerLineSize(1, 2);
            bodyBinding.nineGridView.setEnableEditMode(false);
            bodyBinding.nineGridView.setSingleImageSize(1, R2.attr.app_welcome_center_img, 180);
            NineGridView nineGridView = bodyBinding.nineGridView;
            DefaultNgvAdapter defaultNgvAdapter = DetailFragment.this.mNineGridAdapter;
            DefaultNgvAdapter defaultNgvAdapter2 = null;
            if (defaultNgvAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mNineGridAdapter");
                defaultNgvAdapter = null;
            }
            nineGridView.setAdapter(defaultNgvAdapter);
            bodyBinding.nine1GridView.setDividerLineSize(1, 2);
            bodyBinding.nine1GridView.setEnableEditMode(false);
            bodyBinding.nine1GridView.setSingleImageSize(1, R2.attr.app_welcome_center_img, 180);
            NineGridView nineGridView2 = bodyBinding.nine1GridView;
            DefaultNgvAdapter defaultNgvAdapter3 = DetailFragment.this.mNineGridAdapter;
            if (defaultNgvAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mNineGridAdapter");
            } else {
                defaultNgvAdapter2 = defaultNgvAdapter3;
            }
            nineGridView2.setAdapter(defaultNgvAdapter2);
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungFragmentDetailBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.DetailFragment$viewShowAudio$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06611 extends Lambda implements Function1<GarbledHeartlungFragmentDetailBinding, Unit> {
        public C06611() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(DetailFragment this$0, GarbledHeartlungFragmentDetailBinding this_bodyBinding, View view) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            HeartlungDetailActivity heartlungDetailActivity = this$0.mActivity;
            if (heartlungDetailActivity == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mActivity");
                heartlungDetailActivity = null;
            }
            heartlungDetailActivity.audioPlayerStart(this$0.mData.getAudioUrl(), this$0.mIndex);
            this_bodyBinding.playImgv.setVisibility(4);
            this_bodyBinding.pauseImgv.setVisibility(0);
            this$0.viewSeekBarCanSeek(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(DetailFragment this$0, GarbledHeartlungFragmentDetailBinding this_bodyBinding, View view) throws IllegalStateException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            HeartlungDetailActivity heartlungDetailActivity = this$0.mActivity;
            if (heartlungDetailActivity == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mActivity");
                heartlungDetailActivity = null;
            }
            heartlungDetailActivity.audioPause(this$0.mData.getAudioUrl());
            this_bodyBinding.playImgv.setVisibility(0);
            this_bodyBinding.pauseImgv.setVisibility(4);
            this$0.viewSeekBarCanSeek(true);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungFragmentDetailBinding garbledHeartlungFragmentDetailBinding) {
            invoke2(garbledHeartlungFragmentDetailBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final GarbledHeartlungFragmentDetailBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            LogExtKt.logw("音频时长参数 " + DetailFragment.this.mData.getAudioTime(), DetailFragment.TAG);
            if (DetailFragment.this.mData.getAudioTime() == 0) {
                bodyBinding.totalTimeTv.setText("--:--:--");
                bodyBinding.seekBar.setMax(DetailFragment.this.maxSeekBarDefault);
            } else {
                bodyBinding.totalTimeTv.setText(DateUtil.second2Time1(DetailFragment.this.mData.getAudioTime()));
                bodyBinding.seekBar.setMax(DetailFragment.this.mData.getAudioTime() * 1000);
            }
            ImageView playImgv = bodyBinding.playImgv;
            Intrinsics.checkNotNullExpressionValue(playImgv, "playImgv");
            final DetailFragment detailFragment = DetailFragment.this;
            ViewExtKt.setDebounceClickListener$default(playImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                    DetailFragment.C06611.invoke$lambda$0(detailFragment, bodyBinding, view);
                }
            }, 0L, 2, null);
            ImageView pauseImgv = bodyBinding.pauseImgv;
            Intrinsics.checkNotNullExpressionValue(pauseImgv, "pauseImgv");
            final DetailFragment detailFragment2 = DetailFragment.this;
            ViewExtKt.setDebounceClickListener$default(pauseImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalStateException {
                    DetailFragment.C06611.invoke$lambda$1(detailFragment2, bodyBinding, view);
                }
            }, 0L, 2, null);
            DetailFragment.this.viewSeekBarCanSeek(false);
            SeekBar seekBar = bodyBinding.seekBar;
            final DetailFragment detailFragment3 = DetailFragment.this;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.viewShowAudio.1.3
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(@Nullable SeekBar seekBar2, int progress, boolean fromUser) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(@Nullable SeekBar seekBar2) throws IllegalStateException {
                    if (seekBar2 != null) {
                        HeartlungDetailActivity heartlungDetailActivity = detailFragment3.mActivity;
                        if (heartlungDetailActivity == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mActivity");
                            heartlungDetailActivity = null;
                        }
                        heartlungDetailActivity.audioPlayerSeek(seekBar2.getProgress(), "onStartTrackingTouch");
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(@Nullable SeekBar seekBar2) throws IllegalStateException {
                    if (seekBar2 != null) {
                        HeartlungDetailActivity heartlungDetailActivity = detailFragment3.mActivity;
                        if (heartlungDetailActivity == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mActivity");
                            heartlungDetailActivity = null;
                        }
                        heartlungDetailActivity.audioPlayerSeek(seekBar2.getProgress(), "onStopTrackingTouch");
                    }
                }
            });
        }
    }

    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/yddmi/doctor/pages/heartlung/DetailFragment$viewShowIndicator$1", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerIndicator;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getTitleView", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerTitleView;", "index", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.DetailFragment$viewShowIndicator$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06631 extends CommonNavigatorAdapter {
        public C06631() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getTitleView$lambda$0(int i2, DetailFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            LogExtKt.logd("指示器点击 " + i2, DetailFragment.TAG);
            this$0.mFragmentContainerHelper.handlePageSelected(i2);
            this$0.viewShowIndicatorChange(i2);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return DetailFragment.access$getViewModel(DetailFragment.this).getMIndicatorList().size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerIndicator getIndicator(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(IndicatorUtils.dp2px(4.0f));
            linePagerIndicator.setLineWidth(IndicatorUtils.dp2px(16.0f));
            linePagerIndicator.setRoundRadius(IndicatorUtils.dp2px(2.0f));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(14.0f));
            linePagerIndicator.setColors(Integer.valueOf(ContextExtKt.getColorM(context, R.color.c_3776ff)));
            return linePagerIndicator;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        @NotNull
        public IPagerTitleView getTitleView(@NotNull Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            SimplePagerSizeTitleView simplePagerSizeTitleView = new SimplePagerSizeTitleView(context);
            simplePagerSizeTitleView.setText(DetailFragment.access$getViewModel(DetailFragment.this).getMIndicatorList().get(index).getName());
            simplePagerSizeTitleView.setNormalColor(ContextExtKt.getColorM(context, R.color.c_50536b));
            simplePagerSizeTitleView.setSelectedColor(ContextExtKt.getColorM(context, R.color.c_2f2f46));
            simplePagerSizeTitleView.setNormalSize(14.0f);
            simplePagerSizeTitleView.setSelectedSize(16.0f);
            final DetailFragment detailFragment = DetailFragment.this;
            simplePagerSizeTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailFragment.C06631.getTitleView$lambda$0(index, detailFragment, view);
                }
            });
            return simplePagerSizeTitleView;
        }
    }

    public DetailFragment(int i2, int i3, int i4, @NotNull HeartDetail data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mIndex = i2;
        this.mTotal = i3;
        this.mCode = i4;
        this.mData = data;
        this.maxSeekBarDefault = 100000;
        this.mPictureList = new ArrayList<>();
        this.mFragmentContainerHelper = new FragmentContainerHelper();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ GarbledHeartlungFragmentDetailBinding access$getBodyBinding(DetailFragment detailFragment) {
        return (GarbledHeartlungFragmentDetailBinding) detailFragment.getBodyBinding();
    }

    public static final /* synthetic */ DetailViewModel access$getViewModel(DetailFragment detailFragment) {
        return detailFragment.getViewModel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostAppFavoriteSave(final boolean master2) {
        FavoriteSaveReq favoriteSaveReq = new FavoriteSaveReq(0, 0, (String) null, 7, (DefaultConstructorMarker) null);
        String medicalKnowledgeId = this.mData.getMedicalKnowledgeId();
        if (medicalKnowledgeId == null) {
            medicalKnowledgeId = "";
        }
        favoriteSaveReq.setMedicalKnowledgeId(medicalKnowledgeId);
        if (master2) {
            favoriteSaveReq.setMastered(this.mData.isMastered() != 0 ? 0 : 1);
            favoriteSaveReq.setFavorite(this.mData.isFavorite());
        } else {
            favoriteSaveReq.setMastered(this.mData.isMastered());
            favoriteSaveReq.setFavorite(this.mData.isFavorite() != 0 ? 0 : 1);
        }
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postAppFavoriteSave(favoriteSaveReq), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.httpPostAppFavoriteSave.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message == null || message.length() == 0) {
                    return;
                }
                Toaster.show((CharSequence) it.getMessage());
            }
        }, new Function1<FavoriteSaveReq, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.httpPostAppFavoriteSave.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FavoriteSaveReq favoriteSaveReq2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                invoke2(favoriteSaveReq2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable FavoriteSaveReq favoriteSaveReq2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                if (favoriteSaveReq2 != null) {
                    DetailFragment.this.mData.setFavorite(favoriteSaveReq2.isFavorite());
                    DetailFragment.this.mData.setMastered(favoriteSaveReq2.isMastered());
                    if (master2) {
                        if (favoriteSaveReq2.isMastered() == 1) {
                            Toaster.show((CharSequence) "掌握了");
                        } else {
                            Toaster.show((CharSequence) "取消掌握");
                        }
                    } else if (favoriteSaveReq2.isFavorite() == 1) {
                        Toaster.show((CharSequence) "收藏成功");
                    } else {
                        Toaster.show((CharSequence) "取消收藏");
                    }
                    DetailFragment.this.viewShowGoBtnCollectImgv();
                }
            }
        });
    }

    public static /* synthetic */ void httpPostAppFavoriteSave$default(DetailFragment detailFragment, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        detailFragment.httpPostAppFavoriteSave(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showImagePreview(int position) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewSeekBarCanSeek(final boolean can) {
        bodyBinding(new Function1<GarbledHeartlungFragmentDetailBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.viewSeekBarCanSeek.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungFragmentDetailBinding garbledHeartlungFragmentDetailBinding) {
                invoke2(garbledHeartlungFragmentDetailBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungFragmentDetailBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.seekBar.setClickable(can);
                bodyBinding.seekBar.setEnabled(can);
                bodyBinding.seekBar.setFocusable(can);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowAudio() {
        bodyBinding(new C06611());
    }

    private final void viewShowCode() {
        bodyBinding(new Function1<GarbledHeartlungFragmentDetailBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.viewShowCode.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungFragmentDetailBinding garbledHeartlungFragmentDetailBinding) {
                invoke2(garbledHeartlungFragmentDetailBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungFragmentDetailBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                int i2 = DetailFragment.this.mCode;
                if (i2 == 1) {
                    bodyBinding.mediaCl.setVisibility(0);
                    DetailFragment.this.viewShowAudio();
                    return;
                }
                if (i2 == 2 || i2 == 3) {
                    bodyBinding.mediaCl.setVisibility(8);
                    return;
                }
                if (i2 != 4 && i2 != 5) {
                    bodyBinding.mediaCl.setVisibility(8);
                    return;
                }
                bodyBinding.mediaCl.setVisibility(8);
                DetailFragment.this.mAdapter = new AdapterScore();
                RecyclerView recyclerView = bodyBinding.rv;
                DetailFragment detailFragment = DetailFragment.this;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
                linearLayoutManager.setOrientation(1);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(detailFragment.mAdapter);
                AdapterScore adapterScore = DetailFragment.this.mAdapter;
                if (adapterScore != null) {
                    RatingCriteria ratingCriteria = DetailFragment.this.mData.getRatingCriteria();
                    adapterScore.set(ratingCriteria != null ? ratingCriteria.getMTreeNodes() : null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowGoBtnCollectImgv() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Drawable drawableBuild;
        if (1 == this.mData.isMastered()) {
            DrawableCreator.Builder cornersRadius = new DrawableCreator.Builder().setCornersRadius(IndicatorUtils.dp2px(24.0f));
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            drawableBuild = cornersRadius.setSolidColor(ContextExtKt.getColorM(contextRequireContext, R.color.c_dfe0e6)).build();
            Intrinsics.checkNotNullExpressionValue(drawableBuild, "Builder().setCornersRadi….color.c_dfe0e6)).build()");
            BLTextView bLTextView = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).goBtv;
            Intrinsics.checkNotNullExpressionValue(bLTextView, "bodyBinding.goBtv");
            TextViewExtKt.setTextColorRes(bLTextView, R.color.c_8f91a1);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).goBtv.setText("掌握了");
        } else {
            DrawableCreator.Builder cornersRadius2 = new DrawableCreator.Builder().setCornersRadius(IndicatorUtils.dp2px(24.0f));
            Context contextRequireContext2 = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext()");
            drawableBuild = cornersRadius2.setSolidColor(ContextExtKt.getColorM(contextRequireContext2, R.color.c_3776ff)).build();
            Intrinsics.checkNotNullExpressionValue(drawableBuild, "Builder().setCornersRadi….color.c_3776ff)).build()");
            BLTextView bLTextView2 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).goBtv;
            Intrinsics.checkNotNullExpressionValue(bLTextView2, "bodyBinding.goBtv");
            TextViewExtKt.setTextColorRes(bLTextView2, R.color.white);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).goBtv.setText("已掌握");
        }
        BLTextView bLTextView3 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).goBtv;
        Intrinsics.checkNotNullExpressionValue(bLTextView3, "bodyBinding.goBtv");
        ViewExtKt.setBackgroundJellyBean16(bLTextView3, drawableBuild);
        if (this.mData.isFavorite() == 0) {
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).collectImgv.setImageResource(R.drawable.garbled_heart_bottom_save);
        } else {
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).collectImgv.setImageResource(R.drawable.garbled_heart_bottom_saved);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void viewShowImage() {
        this.mPictureList.clear();
        ArrayList<String> arrayList = new ArrayList();
        int mVmCode = getViewModel().getMVmCode();
        if (mVmCode == 1 || mVmCode == 2 || mVmCode == 3) {
            String imageUrl = this.mData.getImageUrl();
            if (!(imageUrl == null || imageUrl.length() == 0)) {
                arrayList.addAll(StringsKt__StringsKt.split$default((CharSequence) this.mData.getImageUrl(), new String[]{","}, false, 0, 6, (Object) null));
            }
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nineGridView.setVisibility(0);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nine1GridView.setVisibility(8);
        } else if (mVmCode == 4 || mVmCode == 5) {
            String fileUrl = this.mData.getFileUrl();
            if (!(fileUrl == null || fileUrl.length() == 0)) {
                arrayList.addAll(StringsKt__StringsKt.split$default((CharSequence) this.mData.getFileUrl(), new String[]{","}, false, 0, 6, (Object) null));
            }
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nineGridView.setVisibility(8);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nine1GridView.setVisibility(0);
        }
        if (!arrayList.isEmpty()) {
            for (String str : arrayList) {
                if (str.length() > 0) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(YddHostConfig.INSTANCE.getInstance().getFileFullPrivateUrl(str));
                    this.mPictureList.add(localMedia);
                }
            }
        }
        if (!this.mPictureList.isEmpty()) {
            if (this.mPictureList.size() > 3) {
                ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nineGridView.setHorizontalChildCount(4);
                ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nine1GridView.setHorizontalChildCount(4);
            } else {
                ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nineGridView.setHorizontalChildCount(this.mPictureList.size());
                ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nine1GridView.setHorizontalChildCount(this.mPictureList.size());
                if (this.mPictureList.size() == 1) {
                    ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).nineGridView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
            DefaultNgvAdapter<LocalMedia> defaultNgvAdapter = this.mNineGridAdapter;
            if (defaultNgvAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mNineGridAdapter");
                defaultNgvAdapter = null;
            }
            defaultNgvAdapter.setDataList(this.mPictureList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(requireContext());
        commonNavigator.setScrollPivotX(1.2f);
        commonNavigator.setAdapter(new C06631());
        ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).magicIndicator.setNavigator(commonNavigator);
        this.mFragmentContainerHelper.attachMagicIndicator(((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).magicIndicator);
        viewShowIndicatorChange(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowIndicatorChange(int index) {
        int i2 = this.mCode;
        if (i2 == 1) {
            if (index == 0) {
                BLTextView bLTextView = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
                String part = this.mData.getPart();
                bLTextView.setText(part != null ? part : "");
                return;
            }
            if (index == 1) {
                BLTextView bLTextView2 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
                String mechanism = this.mData.getMechanism();
                bLTextView2.setText(mechanism != null ? mechanism : "");
                return;
            } else if (index == 2) {
                BLTextView bLTextView3 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
                String characteristics = this.mData.getCharacteristics();
                bLTextView3.setText(characteristics != null ? characteristics : "");
                return;
            } else {
                if (index != 3) {
                    return;
                }
                BLTextView bLTextView4 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
                String commonDiseases = this.mData.getCommonDiseases();
                bLTextView4.setText(commonDiseases != null ? commonDiseases : "");
                return;
            }
        }
        if (i2 == 2) {
            BLTextView bLTextView5 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
            String diagnosticFormula = this.mData.getDiagnosticFormula();
            bLTextView5.setText(diagnosticFormula != null ? diagnosticFormula : "");
            return;
        }
        if (i2 == 3) {
            if (index == 0) {
                BLTextView bLTextView6 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
                String characteristics2 = this.mData.getCharacteristics();
                bLTextView6.setText(characteristics2 != null ? characteristics2 : "");
                return;
            } else {
                if (index != 1) {
                    return;
                }
                BLTextView bLTextView7 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
                String mnemonic = this.mData.getMnemonic();
                bLTextView7.setText(mnemonic != null ? mnemonic : "");
                return;
            }
        }
        if (i2 == 4) {
            if (index != 0) {
                if (index != 1) {
                    return;
                }
                ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).oneLl.setVisibility(8);
                ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).rv.setVisibility(0);
                return;
            }
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).oneLl.setVisibility(0);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).rv.setVisibility(8);
            BLTextView bLTextView8 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
            String briefMedicalHistory = this.mData.getBriefMedicalHistory();
            bLTextView8.setText(briefMedicalHistory != null ? briefMedicalHistory : "");
            return;
        }
        if (i2 != 5) {
            return;
        }
        if (index != 0) {
            if (index != 1) {
                return;
            }
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).oneLl.setVisibility(8);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).rv.setVisibility(0);
            return;
        }
        ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).oneLl.setVisibility(0);
        ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).rv.setVisibility(8);
        BLTextView bLTextView9 = ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).detailTv;
        String patientSummary = this.mData.getPatientSummary();
        bLTextView9.setText(patientSummary != null ? patientSummary : "");
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06561(null), 3, null);
        getViewModel().dealIndicator();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        getViewModel().setMVmCode(this.mCode);
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity");
        this.mActivity = (HeartlungDetailActivity) activity;
        DefaultNgvAdapter<LocalMedia> defaultNgvAdapter = new DefaultNgvAdapter<>(9, new GlideDisplayer());
        this.mNineGridAdapter = defaultNgvAdapter;
        defaultNgvAdapter.setOnChildClickListener(new DefaultNgvAdapter.OnChildClickedListener<LocalMedia>() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.initParam.1
            @Override // com.lwkandroid.widget.ngv.DefaultNgvAdapter.OnChildClickedListener
            public void onImageDeleted(int position, @Nullable LocalMedia data) {
            }

            @Override // com.lwkandroid.widget.ngv.DefaultNgvAdapter.OnChildClickedListener
            public void onPlusImageClicked(@Nullable ImageView plusImageView, int dValueToLimited) {
            }

            @Override // com.lwkandroid.widget.ngv.DefaultNgvAdapter.OnChildClickedListener
            public void onContentImageClicked(int position, @Nullable LocalMedia data, @Nullable NgvChildImageView childImageView) {
                DetailFragment.this.showImagePreview(position);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        bodyBinding(new C06581());
        viewShowImage();
        viewShowCode();
        viewShowGoBtnCollectImgv();
    }

    public final void viewAudioComplete() {
        LogExtKt.logd("viewAudioComplete 播放结束", TAG);
        bodyBinding(new Function1<GarbledHeartlungFragmentDetailBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.DetailFragment.viewAudioComplete.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungFragmentDetailBinding garbledHeartlungFragmentDetailBinding) {
                invoke2(garbledHeartlungFragmentDetailBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungFragmentDetailBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                DetailFragment.access$getBodyBinding(DetailFragment.this).seekBar.setProgress(0);
                DetailFragment.access$getBodyBinding(DetailFragment.this).currentTimeTv.setText("00:00:00");
                bodyBinding.playImgv.setVisibility(0);
                bodyBinding.pauseImgv.setVisibility(4);
                DetailFragment.this.viewSeekBarCanSeek(false);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void viewAudioProgress(int currentProgress, int total) {
        LogExtKt.logd("viewAudioProgress " + currentProgress + " " + total + " " + ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.getMax(), TAG);
        if (this.mData.getAudioTime() == 0 && this.maxSeekBarDefault == ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.getMax() && total > 1000) {
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.setMax(total);
            this.mData.setAudioTime(total / 1000);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).totalTimeTv.setText(DateUtil.second2Time1(this.mData.getAudioTime()));
        }
        if (currentProgress <= ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.getMax()) {
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.setProgress(currentProgress);
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).currentTimeTv.setText(DateUtil.second2Time1(currentProgress / 1000));
        } else {
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.setProgress(((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).seekBar.getMax());
            ((GarbledHeartlungFragmentDetailBinding) getBodyBinding()).currentTimeTv.setText(DateUtil.second2Time1(this.mData.getAudioTime()));
        }
    }
}
