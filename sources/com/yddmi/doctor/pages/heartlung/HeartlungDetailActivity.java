package com.yddmi.doctor.pages.heartlung;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.blankj.utilcode.util.KeyboardUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.exoplayer2.ExoPlayer;
import com.gxx.audioplaylibrary.AudioPlayManager;
import com.gxx.audioplaylibrary.VolumeChangeObserver;
import com.gxx.audioplaylibrary.inter.OnAudioPlayListener;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.databinding.GarbledHeartlungActivityDetailBinding;
import com.yddmi.doctor.entity.request.FeedBackReq;
import com.yddmi.doctor.entity.result.HeartDetail;
import com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity;
import com.yddmi.doctor.pages.heartlung.PopupBottomCorrection;
import com.yddmi.doctor.pages.heartlung.PopupBottomMastered;
import com.yddmi.doctor.pages.heartlung.PopupFullX;
import com.yddmi.doctor.repository.YddClinicRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
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
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000U\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001b*\u0001\n\b\u0007\u0018\u0000 82\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u00042\u00020\u0005:\u00018B\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0016\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0016J\u0018\u0010\u001a\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001b\u001a\u00020\bJ\u001a\u0010\u001c\u001a\u00020\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\bH\u0002J\b\u0010 \u001a\u00020\u0014H\u0002J\b\u0010!\u001a\u00020\u0014H\u0016J\b\u0010\"\u001a\u00020\u0014H\u0016J\b\u0010#\u001a\u00020\u0014H\u0016J\b\u0010$\u001a\u00020\u0014H\u0016J\b\u0010%\u001a\u00020\u0014H\u0014J\b\u0010&\u001a\u00020\u0014H\u0014J\b\u0010'\u001a\u00020\u0014H\u0014J\u0012\u0010(\u001a\u00020\u00142\b\u0010)\u001a\u0004\u0018\u00010\u0016H\u0016J\"\u0010*\u001a\u00020\u00142\b\u0010)\u001a\u0004\u0018\u00010\u00162\u0006\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\bH\u0016J\u0012\u0010-\u001a\u00020\u00142\b\u0010)\u001a\u0004\u0018\u00010\u0016H\u0016J\"\u0010.\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010/\u001a\u00020\b2\b\u0010)\u001a\u0004\u0018\u00010\u0016H\u0016J\u0012\u00100\u001a\u00020\u00142\b\u0010)\u001a\u0004\u0018\u00010\u0016H\u0016J\u0010\u00101\u001a\u00020\u00142\u0006\u00102\u001a\u00020\bH\u0016J\b\u00103\u001a\u00020\u0014H\u0016J\u000e\u00104\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u00105\u001a\u00020\u0014J\u0006\u00106\u001a\u00020\u0014J\b\u00107\u001a\u00020\u0014H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/HeartlungDetailActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityDetailBinding;", "Lcom/yddmi/doctor/pages/heartlung/HeartlungDetailViewModel;", "Lcom/gxx/audioplaylibrary/inter/OnAudioPlayListener;", "Lcom/gxx/audioplaylibrary/VolumeChangeObserver$VolumeChangeListener;", "()V", "audioFragmentIndex", "", "mHandler", "com/yddmi/doctor/pages/heartlung/HeartlungDetailActivity$mHandler$1", "Lcom/yddmi/doctor/pages/heartlung/HeartlungDetailActivity$mHandler$1;", "mVolumeChangeObserver", "Lcom/gxx/audioplaylibrary/VolumeChangeObserver;", "vpAdapter", "Landroidx/viewpager2/adapter/FragmentStateAdapter;", "vpFragments", "", "Lcom/yddmi/doctor/pages/heartlung/DetailFragment;", "audioPause", "", "path", "", "audioPlayerSeek", "progress", "callBackName", "audioPlayerStart", "mIndex", "httpPostFeedBackAdd", "m", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "feedbackType", "httpPostKnowledgeLearnRecordSave", "initFlow", "initParam", "initView", "onBackPressed", "onDestroy", "onPause", "onResume", "onVoiceComplete", "voiceId", "onVoiceError", "what", PushConstants.EXTRA, "onVoiceFocusLoss", "onVoicePlayPosition", "duration", "onVoicePrepared", "onVolumeChanged", "volume", "screenOrientation", "viewShowCorrectionPop", "viewShowFullXPop", "viewShowMasteredPop", "viewVpShow", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true, transparent = true)
@SourceDebugExtension({"SMAP\nHeartlungDetailActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungDetailActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungDetailActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,403:1\n18#2,2:404\n1#3:406\n1864#4,3:407\n*S KotlinDebug\n*F\n+ 1 HeartlungDetailActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungDetailActivity\n*L\n103#1:404,2\n103#1:406\n276#1:407,3\n*E\n"})
/* loaded from: classes6.dex */
public final class HeartlungDetailActivity extends BaseVMActivity<GarbledHeartlungActivityDetailBinding, HeartlungDetailViewModel> implements OnAudioPlayListener, VolumeChangeObserver.VolumeChangeListener {

    @NotNull
    private static final String TAG = "HeartlungDetailActivity";

    @NotNull
    private final HeartlungDetailActivity$mHandler$1 mHandler;

    @Nullable
    private VolumeChangeObserver mVolumeChangeObserver;
    private FragmentStateAdapter vpAdapter;

    @NotNull
    private final List<DetailFragment> vpFragments = new ArrayList();
    private int audioFragmentIndex = -1;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$initFlow$1", f = "HeartlungDetailActivity.kt", i = {}, l = {163}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06721 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06721(Continuation<? super C06721> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungDetailActivity.this.new C06721(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> detailsChangeMsf = HeartlungDetailActivity.this.getViewModel().getDetailsChangeMsf();
                final HeartlungDetailActivity heartlungDetailActivity = HeartlungDetailActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            heartlungDetailActivity.viewVpShow();
                            removeMessages(100);
                            sendEmptyMessageDelayed(100, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (detailsChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$initFlow$2", f = "HeartlungDetailActivity.kt", i = {}, l = {172}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C06732 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06732(Continuation<? super C06732> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungDetailActivity.this.new C06732(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06732) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> autoVpGoMsf = HeartlungDetailActivity.this.getViewModel().getAutoVpGoMsf();
                final HeartlungDetailActivity heartlungDetailActivity = HeartlungDetailActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0 && heartlungDetailActivity.getViewModel().getAutoGoVpIndex() > 0 && heartlungDetailActivity.getViewModel().getAutoGoVpIndex() < heartlungDetailActivity.vpFragments.size()) {
                            LogExtKt.logd("vp滚动到" + heartlungDetailActivity.getViewModel().getAutoGoVpIndex(), HeartlungDetailActivity.TAG);
                            HeartlungDetailActivity.access$getBodyBinding(heartlungDetailActivity).vp.setCurrentItem(heartlungDetailActivity.getViewModel().getAutoGoVpIndex(), false);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (autoVpGoMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityDetailBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06741 extends Lambda implements Function1<GarbledHeartlungActivityDetailBinding, Unit> {
        public C06741() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(HeartlungDetailActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpPostKnowledgeLearnRecordSave();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungActivityDetailBinding garbledHeartlungActivityDetailBinding) {
            invoke2(garbledHeartlungActivityDetailBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungActivityDetailBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final HeartlungDetailActivity heartlungDetailActivity = HeartlungDetailActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.u
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungDetailActivity.C06741.invoke$lambda$0(heartlungDetailActivity, view);
                }
            }, 0L, 2, null);
            bodyBinding.titleTv.setText(HeartlungDetailActivity.this.getViewModel().getCategoryName());
            ViewPager2 viewPager2 = bodyBinding.vp;
            FragmentStateAdapter fragmentStateAdapter = HeartlungDetailActivity.this.vpAdapter;
            if (fragmentStateAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vpAdapter");
                fragmentStateAdapter = null;
            }
            viewPager2.setAdapter(fragmentStateAdapter);
            ViewPager2 viewPager22 = bodyBinding.vp;
            final HeartlungDetailActivity heartlungDetailActivity2 = HeartlungDetailActivity.this;
            viewPager22.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.initView.1.2
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageSelected(int position) throws IllegalStateException {
                    super.onPageSelected(position);
                    heartlungDetailActivity2.getViewModel().setCurrentVpIndex(position);
                    heartlungDetailActivity2.getViewModel().setLastVpIndex(position);
                    if (1 == heartlungDetailActivity2.getViewModel().getCode()) {
                        heartlungDetailActivity2.audioPause("");
                    }
                    if (position <= 0 || heartlungDetailActivity2.getViewModel().getMIsExchange() != 0) {
                        return;
                    }
                    heartlungDetailActivity2.viewShowFullXPop();
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$mHandler$1] */
    public HeartlungDetailActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what == 100) {
                    this.this$0.hideLoading();
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ GarbledHeartlungActivityDetailBinding access$getBodyBinding(HeartlungDetailActivity heartlungDetailActivity) {
        return (GarbledHeartlungActivityDetailBinding) heartlungDetailActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostFeedBackAdd(HeartDetail m2, int feedbackType) {
        if (m2 != null) {
            if (!(m2.getMDescription().length() == 0)) {
                FeedBackReq feedBackReq = new FeedBackReq((String) null, 0, (String) null, 7, (DefaultConstructorMarker) null);
                feedBackReq.setDescription(m2.getMDescription());
                feedBackReq.setFeedbackType(feedbackType);
                String medicalKnowledgeId = m2.getMedicalKnowledgeId();
                if (medicalKnowledgeId == null) {
                    medicalKnowledgeId = "";
                }
                feedBackReq.setMedicalKnowledgeId(medicalKnowledgeId);
                FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postFeedBackAdd(feedBackReq), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.httpPostFeedBackAdd.1
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
                }, new Function1<Boolean, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.httpPostFeedBackAdd.2
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                        invoke(bool.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(boolean z2) {
                        if (!z2) {
                            Toaster.show(R.string.common_commit_error);
                        } else {
                            YddConfig.INSTANCE.getKvData().put(YddConfig.KV_HEARTLUNG_CORRECTION, "");
                            Toaster.show(R.string.common_commit_success);
                        }
                    }
                });
                return;
            }
        }
        LogExtKt.logd("纠错内容是空", TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostKnowledgeLearnRecordSave() {
        List<HeartDetail> mData;
        if (NoDoubleClickUtil.isDoubleClick()) {
            return;
        }
        List<HeartDetail> mData2 = getViewModel().getMData();
        HeartDetail heartDetail = null;
        if (!(mData2 == null || mData2.isEmpty()) && (mData = getViewModel().getMData()) != null) {
            heartDetail = mData.get(getViewModel().getCurrentVpIndex());
        }
        if (heartDetail == null) {
            closeActivity();
            return;
        }
        YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
        String medicalKnowledgeId = heartDetail.getMedicalKnowledgeId();
        if (medicalKnowledgeId == null) {
            medicalKnowledgeId = "";
        }
        FlowExtKt.lifecycleLoadingDialog(yddClinicRepository.postKnowledgeLearnRecordSave(medicalKnowledgeId), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.httpPostKnowledgeLearnRecordSave.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                HeartlungDetailActivity.this.closeActivity();
            }
        }, new Function1<Boolean, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.httpPostKnowledgeLearnRecordSave.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z2) {
                HeartlungDetailActivity.this.closeActivity();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewVpShow() {
        if (getViewModel().getMData() != null) {
            this.vpFragments.clear();
            List<HeartDetail> mData = getViewModel().getMData();
            if (mData != null) {
                int i2 = 0;
                for (Object obj : mData) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    HeartDetail heartDetail = (HeartDetail) obj;
                    if (heartDetail != null) {
                        List<HeartDetail> mData2 = getViewModel().getMData();
                        Intrinsics.checkNotNull(mData2);
                        this.vpFragments.add(new DetailFragment(i2, mData2.size(), getViewModel().getCode(), heartDetail));
                    }
                    i2 = i3;
                }
            }
            LogExtKt.logd("vp大小 " + this.vpFragments.size(), TAG);
            FragmentStateAdapter fragmentStateAdapter = this.vpAdapter;
            if (fragmentStateAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vpAdapter");
                fragmentStateAdapter = null;
            }
            fragmentStateAdapter.notifyDataSetChanged();
            if (this.vpFragments.size() > 3) {
                ((GarbledHeartlungActivityDetailBinding) getBodyBinding()).vp.setOffscreenPageLimit(3);
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.yddmi.doctor.pages.heartlung.t
                @Override // java.lang.Runnable
                public final void run() {
                    HeartlungDetailActivity.viewVpShow$lambda$3(this.f25811c);
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void viewVpShow$lambda$3(HeartlungDetailActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.vpFragments.size() > 0) {
            ((GarbledHeartlungActivityDetailBinding) this$0.getBodyBinding()).vp.setOffscreenPageLimit(this$0.vpFragments.size());
        }
        if (this$0.getViewModel().getAutoGoVpIndex() <= 0 || this$0.getViewModel().getAutoGoVpIndex() >= this$0.vpFragments.size()) {
            return;
        }
        LogExtKt.logd("vp滚动到" + this$0.getViewModel().getAutoGoVpIndex(), TAG);
        ((GarbledHeartlungActivityDetailBinding) this$0.getBodyBinding()).vp.setCurrentItem(this$0.getViewModel().getAutoGoVpIndex(), false);
    }

    public final void audioPause(@Nullable String path) throws IllegalStateException {
        LogExtKt.logd("audioPause " + path + " " + YddHostConfig.INSTANCE.getInstance().getFileFullUrl(path), TAG);
        AudioPlayManager.Companion companion = AudioPlayManager.INSTANCE;
        if (companion.getInstance().isPlayIng()) {
            companion.getInstance().pause();
        }
    }

    public final void audioPlayerSeek(int progress, @NotNull String callBackName) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(callBackName, "callBackName");
        if (Intrinsics.areEqual(callBackName, "onStartTrackingTouch")) {
            LogExtKt.logd("用户开始拖动进度条 " + progress, TAG);
            AudioPlayManager.INSTANCE.getInstance().pause();
            return;
        }
        if (Intrinsics.areEqual(callBackName, "onStopTrackingTouch")) {
            LogExtKt.logd("用户停止拖动进度条 " + progress, TAG);
            AudioPlayManager.INSTANCE.getInstance().seekTo(progress);
        }
    }

    public final void audioPlayerStart(@Nullable String path, int mIndex) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        YddHostConfig.Companion companion = YddHostConfig.INSTANCE;
        LogExtKt.logd("播放器 audioPlayerStart() " + path + " " + companion.getInstance().getFileFullUrl(path), TAG);
        if (path == null || path.length() == 0) {
            Toaster.show(R.string.heartlung_audio_tip);
        } else {
            this.audioFragmentIndex = mIndex;
            AudioPlayManager.INSTANCE.getInstance().prepareAsync(companion.getInstance().getFileFullUrl(path), path, 1.0f, Boolean.FALSE, this);
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06721(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06732(null), 3, null);
        loadingDialog();
        getViewModel().httpGetKnowledgeDetails();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_white);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            getViewModel().setCode(intent.getIntExtra("code", 1));
            HeartlungDetailViewModel viewModel = getViewModel();
            String stringExtra = intent.getStringExtra("categoryId");
            if (stringExtra == null) {
                stringExtra = "";
            } else {
                Intrinsics.checkNotNullExpressionValue(stringExtra, "it.getStringExtra(\"categoryId\") ?: \"\"");
            }
            viewModel.setCategoryId(stringExtra);
            HeartlungDetailViewModel viewModel2 = getViewModel();
            String stringExtra2 = intent.getStringExtra("categoryName");
            if (stringExtra2 == null) {
                stringExtra2 = "";
            } else {
                Intrinsics.checkNotNullExpressionValue(stringExtra2, "it.getStringExtra(\"categoryName\") ?: \"\"");
            }
            viewModel2.setCategoryName(stringExtra2);
            HeartlungDetailViewModel viewModel3 = getViewModel();
            String stringExtra3 = intent.getStringExtra("medicalKnowledgeId");
            viewModel3.setMedicalKnowledgeId(stringExtra3 != null ? stringExtra3 : "");
            getViewModel().setFavorite(intent.getIntExtra("isFavorite", 2));
            getViewModel().setMIsExchange(intent.getIntExtra("isExchange", 0));
            LogExtKt.logd("参数 " + getViewModel().getCode() + " " + getViewModel().getCategoryId() + " " + getViewModel().getCategoryName(), TAG);
        }
        this.vpAdapter = new FragmentStateAdapter() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.initParam.3
            {
                super(HeartlungDetailActivity.this);
            }

            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            @NotNull
            public Fragment createFragment(int position) {
                return (Fragment) HeartlungDetailActivity.this.vpFragments.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return HeartlungDetailActivity.this.vpFragments.size();
            }
        };
        if (1 == getViewModel().getCode()) {
            if (this.mVolumeChangeObserver == null) {
                this.mVolumeChangeObserver = new VolumeChangeObserver(this);
            }
            VolumeChangeObserver volumeChangeObserver = this.mVolumeChangeObserver;
            Intrinsics.checkNotNull(volumeChangeObserver);
            volumeChangeObserver.setVolumeChangeListener(this);
            AudioPlayManager.Builder builder = new AudioPlayManager.Builder();
            Application application = getApplication();
            Intrinsics.checkNotNullExpressionValue(application, "this.application");
            builder.setApplication(application).build();
            LogExtKt.logd("音频播放器已初始化", TAG);
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06741());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        httpPostKnowledgeLearnRecordSave();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() throws IllegalStateException {
        super.onDestroy();
        if (1 == getViewModel().getCode()) {
            AudioPlayManager.INSTANCE.getInstance().releaseAll();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() throws IllegalStateException {
        super.onPause();
        if (1 == getViewModel().getCode()) {
            AudioPlayManager.INSTANCE.getInstance().pause();
            VolumeChangeObserver volumeChangeObserver = this.mVolumeChangeObserver;
            if (volumeChangeObserver != null) {
                volumeChangeObserver.unregisterReceiver();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        VolumeChangeObserver volumeChangeObserver;
        super.onResume();
        if (1 == getViewModel().getCode() && (volumeChangeObserver = this.mVolumeChangeObserver) != null) {
            volumeChangeObserver.registerReceiver();
        }
        getViewModel().dealVpAutoGo();
    }

    @Override // com.gxx.audioplaylibrary.inter.OnAudioPlayListener
    public void onVoiceComplete(@Nullable String voiceId) {
        LogExtKt.logd("onVoiceComplete " + voiceId, TAG);
        int i2 = this.audioFragmentIndex;
        if (i2 <= -1 || i2 >= this.vpFragments.size()) {
            return;
        }
        LogExtKt.logd("播放结束了", TAG);
        this.vpFragments.get(this.audioFragmentIndex).viewAudioComplete();
    }

    @Override // com.gxx.audioplaylibrary.inter.OnAudioPlayListener
    public void onVoiceError(@Nullable String voiceId, int what, int extra) {
        LogExtKt.logd("onVoiceError 播放器异常了 " + voiceId, TAG);
        int i2 = this.audioFragmentIndex;
        if (i2 <= -1 || i2 >= this.vpFragments.size()) {
            return;
        }
        LogExtKt.logd("播放结束了", TAG);
        this.vpFragments.get(this.audioFragmentIndex).viewAudioComplete();
    }

    @Override // com.gxx.audioplaylibrary.inter.OnAudioPlayListener
    public void onVoiceFocusLoss(@Nullable String voiceId) {
        LogExtKt.logd("onVoiceFocusLoss 播放器音频焦点丢失 " + voiceId, TAG);
    }

    @Override // com.gxx.audioplaylibrary.inter.OnAudioPlayListener
    public void onVoicePlayPosition(int progress, int duration, @Nullable String voiceId) {
        LogExtKt.logd("onVoicePlayPosition " + duration + " " + progress + " " + voiceId, TAG);
        int i2 = this.audioFragmentIndex;
        if (i2 <= -1 || i2 >= this.vpFragments.size()) {
            return;
        }
        LogExtKt.logd("播放进度更新 total:" + duration + " " + progress + " ", TAG);
        this.vpFragments.get(this.audioFragmentIndex).viewAudioProgress(progress, duration);
    }

    @Override // com.gxx.audioplaylibrary.inter.OnAudioPlayListener
    public void onVoicePrepared(@Nullable String voiceId) throws IllegalStateException {
        LogExtKt.logd("onVoicePrepared " + voiceId, TAG);
        AudioPlayManager.Companion companion = AudioPlayManager.INSTANCE;
        if (companion.getInstance().isPlayIng()) {
            return;
        }
        companion.getInstance().start();
    }

    @Override // com.gxx.audioplaylibrary.VolumeChangeObserver.VolumeChangeListener
    public void onVolumeChanged(int volume) {
        AudioPlayManager.INSTANCE.getInstance().setStreamVolume(volume);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }

    public final void viewShowCorrectionPop(@NotNull HeartDetail m2) {
        Intrinsics.checkNotNullParameter(m2, "m");
        final PopupBottomCorrection popupBottomCorrection = new PopupBottomCorrection(this);
        popupBottomCorrection.setData(m2).setOnPopupCorrectionClickListener(new PopupBottomCorrection.OnPopupCorrectionClickListener() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.viewShowCorrectionPop.1
            @Override // com.yddmi.doctor.pages.heartlung.PopupBottomCorrection.OnPopupCorrectionClickListener
            public void onClick(@NotNull View view, @Nullable HeartDetail m3, int feedbackType) {
                Intrinsics.checkNotNullParameter(view, "view");
                popupBottomCorrection.dismiss();
                KeyboardUtils.hideSoftInput(this);
                int id = view.getId();
                if (id != R.id.historyTv) {
                    if (id == R.id.submitTv) {
                        this.httpPostFeedBackAdd(m3, feedbackType);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("categoryId", m3 != null ? m3.getCategoryId() : null);
                    intent.putExtra("medicalKnowledgeId", m3 != null ? m3.getMedicalKnowledgeId() : null);
                    HeartlungDetailActivity heartlungDetailActivity = this;
                    intent.setClass(heartlungDetailActivity, CorrectionActivity.class);
                    heartlungDetailActivity.startActivity(intent);
                }
            }
        });
        XPopup.Builder builderEnableDrag = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false);
        Boolean bool = Boolean.TRUE;
        builderEnableDrag.hasShadowBg(bool).isViewMode(false).autoOpenSoftInput(bool).dismissOnTouchOutside(bool).asCustom(popupBottomCorrection).show();
    }

    public final void viewShowFullXPop() {
        try {
            final PopupFullX popupFullX = new PopupFullX(this);
            popupFullX.setOnPopupFullXClickListener(new PopupFullX.OnPopupFullXClickListener() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.viewShowFullXPop.1
                @Override // com.yddmi.doctor.pages.heartlung.PopupFullX.OnPopupFullXClickListener
                public void onClick(@NotNull View v2) {
                    Intrinsics.checkNotNullParameter(v2, "v");
                    HeartlungDetailActivity.access$getBodyBinding(HeartlungDetailActivity.this).vp.setCurrentItem(0, false);
                    popupFullX.dismiss();
                }
            });
            new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasShadowBg(Boolean.TRUE).isViewMode(false).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupFullX).show();
        } catch (Throwable th) {
            LogExtKt.logd("355 " + th, TAG);
        }
    }

    public final void viewShowMasteredPop() {
        final PopupBottomMastered popupBottomMastered = new PopupBottomMastered(this);
        popupBottomMastered.setData(getViewModel().getMData()).setOnPopupMasteredClickListener(new PopupBottomMastered.OnPopupMasteredClickListener() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungDetailActivity.viewShowMasteredPop.1
            @Override // com.yddmi.doctor.pages.heartlung.PopupBottomMastered.OnPopupMasteredClickListener
            public void onClick(@NotNull HeartDetail m2, int index) {
                Intrinsics.checkNotNullParameter(m2, "m");
                popupBottomMastered.dismiss();
                List<HeartDetail> mData = this.getViewModel().getMData();
                Intrinsics.checkNotNull(mData);
                if (index < mData.size()) {
                    HeartlungDetailActivity.access$getBodyBinding(this).vp.setCurrentItem(index, true);
                }
            }
        });
        XPopup.Builder builderEnableDrag = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false);
        Boolean bool = Boolean.TRUE;
        builderEnableDrag.hasShadowBg(bool).isViewMode(false).dismissOnTouchOutside(bool).asCustom(popupBottomMastered).show();
    }
}
