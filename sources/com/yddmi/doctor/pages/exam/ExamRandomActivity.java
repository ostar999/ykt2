package com.yddmi.doctor.pages.exam;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwnerKt;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.databinding.ExamActivityRandomBinding;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.pages.exam.ExamRandomActivity;
import com.yddmi.doctor.pages.product.ProductActivity;
import com.yddmi.doctor.pages.u3d.U3dActivity;
import com.yddmi.doctor.pages.u3d.U3dGoActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.widget.FullVideoView;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00009\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005*\u0001\u0006\b\u0007\u0018\u0000 \u001a2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0004J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000bH\u0002J\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u000bH\u0014J\b\u0010\u0014\u001a\u00020\u000bH\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u000bH\u0002J\b\u0010\u0018\u001a\u00020\u000bH\u0002J\b\u0010\u0019\u001a\u00020\u000bH\u0002R\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/pages/exam/ExamRandomActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/ExamActivityRandomBinding;", "Lcom/yddmi/doctor/pages/exam/ExamRandomViewModel;", "()V", "mHandler", "com/yddmi/doctor/pages/exam/ExamRandomActivity$mHandler$1", "Lcom/yddmi/doctor/pages/exam/ExamRandomActivity$mHandler$1;", "maxSkillNum", "", "dealGoBuyAll", "", "type", "skillId", "", "httpGetSkillTimes", "initFlow", "initParam", "initView", "onDestroy", "viewMaxNumShow", "viewPopMax", "", "viewShowCard", "viewVideoShow", "viewVideoStop", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nExamRandomActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamRandomActivity.kt\ncom/yddmi/doctor/pages/exam/ExamRandomActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,319:1\n18#2,2:320\n1#3:322\n15#4,3:323\n1864#5,3:326\n*S KotlinDebug\n*F\n+ 1 ExamRandomActivity.kt\ncom/yddmi/doctor/pages/exam/ExamRandomActivity\n*L\n68#1:320,2\n68#1:322\n175#1:323,3\n197#1:326,3\n*E\n"})
/* loaded from: classes6.dex */
public final class ExamRandomActivity extends BaseVMActivity<ExamActivityRandomBinding, ExamRandomViewModel> {

    @NotNull
    private static final String TAG = "ExamRandomActivity";

    @NotNull
    private final ExamRandomActivity$mHandler$1 mHandler;
    private int maxSkillNum;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamRandomActivity$initFlow$1", f = "ExamRandomActivity.kt", i = {}, l = {157}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamRandomActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06441 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06441(Continuation<? super C06441> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamRandomActivity.this.new C06441(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06441) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> examRandomListMsf = ExamRandomActivity.this.getViewModel().getExamRandomListMsf();
                final ExamRandomActivity examRandomActivity = ExamRandomActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            examRandomActivity.hideLoading();
                            examRandomActivity.viewShowCard();
                            examRandomActivity.httpGetSkillTimes();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (examRandomListMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/ExamActivityRandomBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nExamRandomActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamRandomActivity.kt\ncom/yddmi/doctor/pages/exam/ExamRandomActivity$initView$1\n+ 2 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,319:1\n15#2,3:320\n11#2,7:323\n15#2,3:330\n11#2,7:333\n*S KotlinDebug\n*F\n+ 1 ExamRandomActivity.kt\ncom/yddmi/doctor/pages/exam/ExamRandomActivity$initView$1\n*L\n112#1:320,3\n113#1:323,7\n130#1:330,3\n131#1:333,7\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamRandomActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06451 extends Lambda implements Function1<ExamActivityRandomBinding, Unit> {
        public C06451() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(ExamRandomActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(ExamRandomActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.viewPopMax()) {
                return;
            }
            this$0.viewVideoShow();
            if (1 == this$0.getViewModel().getType()) {
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-BASEOPERATE-REEXTRACT", "【基本操作】模拟考试-重新抽题", null, 4, null);
            } else {
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-BODYCHECK-REEXTRACT", "【体格检查】模拟考试-重新抽题", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(ExamRandomActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (1 != this$0.getViewModel().getType()) {
                if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                    this$0.loadingDialog();
                    Intent intent = new Intent();
                    intent.putExtra("typeId", this$0.getViewModel().getM3SkillId());
                    intent.putExtra("url", this$0.getViewModel().getM3SkillUrl());
                    intent.putExtra("isExam", true);
                    intent.putExtra("skillType", 3);
                    intent.setClass(this$0, U3dGoActivity.class);
                    this$0.startActivity(intent);
                    Intent intent2 = new Intent();
                    intent2.setClass(this$0, U3dActivity.class);
                    this$0.startActivity(intent2);
                    this$0.mHandler.sendEmptyMessageDelayed(102, 1200L);
                    YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-BODYCHECK-STARTEXAM", "【体格检查】模拟考试-开始考试", null, 4, null);
                    return;
                }
                return;
            }
            List<SkillHome> mRandomSkillList = this$0.getViewModel().getMRandomSkillList();
            SkillHome skillHome = mRandomSkillList != null ? mRandomSkillList.get(0) : null;
            if (skillHome == null) {
                Toaster.show(R.string.common_no_data2);
            } else if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                this$0.loadingDialog();
                Intent intent3 = new Intent();
                intent3.putExtra("typeId", skillHome.getId());
                intent3.putExtra("url", skillHome.getUrl());
                intent3.putExtra("isExam", true);
                intent3.putExtra("CurrentProject", 1);
                intent3.putExtra("skillType", skillHome.getType());
                intent3.setClass(this$0, U3dGoActivity.class);
                this$0.startActivity(intent3);
                Intent intent4 = new Intent();
                intent4.setClass(this$0, U3dActivity.class);
                this$0.startActivity(intent4);
                this$0.mHandler.sendEmptyMessageDelayed(102, 1200L);
            }
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-BASEOPERATE-STARTEXAM", "【基本操作】模拟考试-开始考试", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(ExamRandomActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewVideoStop();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
            invoke2(examActivityRandomBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull ExamActivityRandomBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView xImgv = bodyBinding.xImgv;
            Intrinsics.checkNotNullExpressionValue(xImgv, "xImgv");
            final ExamRandomActivity examRandomActivity = ExamRandomActivity.this;
            ViewExtKt.setDebounceClickListener$default(xImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamRandomActivity.C06451.invoke$lambda$0(examRandomActivity, view);
                }
            }, 0L, 2, null);
            TextView resetTv = bodyBinding.resetTv;
            Intrinsics.checkNotNullExpressionValue(resetTv, "resetTv");
            final ExamRandomActivity examRandomActivity2 = ExamRandomActivity.this;
            ViewExtKt.setDebounceClickListener$default(resetTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamRandomActivity.C06451.invoke$lambda$1(examRandomActivity2, view);
                }
            }, 0L, 2, null);
            TextView startTv = bodyBinding.startTv;
            Intrinsics.checkNotNullExpressionValue(startTv, "startTv");
            final ExamRandomActivity examRandomActivity3 = ExamRandomActivity.this;
            ViewExtKt.setDebounceClickListener$default(startTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamRandomActivity.C06451.invoke$lambda$2(examRandomActivity3, view);
                }
            }, 0L, 2, null);
            FullVideoView videoV = bodyBinding.videoV;
            Intrinsics.checkNotNullExpressionValue(videoV, "videoV");
            final ExamRandomActivity examRandomActivity4 = ExamRandomActivity.this;
            ViewExtKt.setDebounceClickListener(videoV, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamRandomActivity.C06451.invoke$lambda$3(examRandomActivity4, view);
                }
            }, 500L);
            if (1 == ExamRandomActivity.this.getViewModel().getType()) {
                bodyBinding.card1Fl.setVisibility(0);
                bodyBinding.card2Fl.setVisibility(8);
                bodyBinding.card3Fl.setVisibility(8);
            } else {
                bodyBinding.card1Fl.setVisibility(0);
                bodyBinding.card2Fl.setVisibility(0);
                bodyBinding.card3Fl.setVisibility(0);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.exam.ExamRandomActivity$mHandler$1] */
    public ExamRandomActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what == 102) {
                    this.this$0.hideLoading();
                    this.this$0.closeActivity();
                }
            }
        };
    }

    private final void dealGoBuyAll(int type, String skillId) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("name", "");
        intent.putExtra("skillId", skillId);
        intent.putExtra("skill24", getViewModel().getType());
        intent.setClass(this, ProductActivity.class);
        startActivity(intent);
    }

    public static /* synthetic */ void dealGoBuyAll$default(ExamRandomActivity examRandomActivity, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "-1";
        }
        examRandomActivity.dealGoBuyAll(i2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetSkillTimes() {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getSkillTimes(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.httpGetSkillTimes.1
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
                LogExtKt.logw("httpGetSkillTimes 获取抽题次数error " + it, ExamRandomActivity.TAG);
                ExamRandomActivity.this.maxSkillNum = 0;
                ExamRandomActivity.this.viewMaxNumShow();
            }
        }, new Function1<Integer, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.httpGetSkillTimes.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2) {
                ExamRandomActivity.this.maxSkillNum = i2;
                ExamRandomActivity.this.viewMaxNumShow();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewMaxNumShow() {
        GlobalAction.INSTANCE.setGlobalMaxSkillNum(this.maxSkillNum);
        TextView viewMaxNumShow$lambda$3 = ((ExamActivityRandomBinding) getBodyBinding()).maxNumTv;
        viewMaxNumShow$lambda$3.setText("");
        viewMaxNumShow$lambda$3.append("您今日有");
        Intrinsics.checkNotNullExpressionValue(viewMaxNumShow$lambda$3, "viewMaxNumShow$lambda$3");
        SpanExtKt.appendColorSpan(viewMaxNumShow$lambda$3, String.valueOf(this.maxSkillNum), ContextExtKt.getColorM(this, R.color.c_ff585a));
        viewMaxNumShow$lambda$3.append("次抽题机会");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean viewPopMax() {
        if (this.maxSkillNum > 0) {
            return false;
        }
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(new PopupExamCall(this)).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowCard() {
        List<SkillHome> mRandomSkillList = getViewModel().getMRandomSkillList();
        int i2 = 0;
        if (mRandomSkillList == null || mRandomSkillList.isEmpty()) {
            Toaster.show(R.string.common_no_data2);
            return;
        }
        if (1 == getViewModel().getType()) {
            List<SkillHome> mRandomSkillList2 = getViewModel().getMRandomSkillList();
            Intrinsics.checkNotNull(mRandomSkillList2);
            final SkillHome skillHome = mRandomSkillList2.get(0);
            bodyBinding(new Function1<ExamActivityRandomBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.viewShowCard.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
                    invoke2(examActivityRandomBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull ExamActivityRandomBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    bodyBinding.name1Tv.setText(skillHome.getName());
                    ImageView img1V = bodyBinding.img1V;
                    Intrinsics.checkNotNullExpressionValue(img1V, "img1V");
                    String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(skillHome.getIcon());
                    int i3 = R.drawable.home_icon;
                    ImageViewExtKt.load(img1V, fileFullUrl, (261628 & 2) != 0 ? 0 : i3, (261628 & 4) != 0 ? 0 : i3, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                    bodyBinding.starBar1.setRating(skillHome.getStar());
                }
            });
            return;
        }
        List<SkillHome> mRandomSkillList3 = getViewModel().getMRandomSkillList();
        if (mRandomSkillList3 != null) {
            for (Object obj : mRandomSkillList3) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                final SkillHome skillHome2 = (SkillHome) obj;
                if (i2 == 0) {
                    bodyBinding(new Function1<ExamActivityRandomBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity$viewShowCard$2$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
                            invoke2(examActivityRandomBinding);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(@NotNull ExamActivityRandomBinding bodyBinding) {
                            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                            bodyBinding.name1Tv.setText(skillHome2.getName());
                            ImageView img1V = bodyBinding.img1V;
                            Intrinsics.checkNotNullExpressionValue(img1V, "img1V");
                            String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(skillHome2.getIcon());
                            int i4 = R.drawable.home_icon1;
                            ImageViewExtKt.load(img1V, fileFullUrl, (261628 & 2) != 0 ? 0 : i4, (261628 & 4) != 0 ? 0 : i4, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                            bodyBinding.starBar1.setRating(skillHome2.getStar());
                        }
                    });
                } else if (i2 == 1) {
                    bodyBinding(new Function1<ExamActivityRandomBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity$viewShowCard$2$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
                            invoke2(examActivityRandomBinding);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(@NotNull ExamActivityRandomBinding bodyBinding) {
                            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                            bodyBinding.name2Tv.setText(skillHome2.getName());
                            ImageView img2V = bodyBinding.img2V;
                            Intrinsics.checkNotNullExpressionValue(img2V, "img2V");
                            String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(skillHome2.getIcon());
                            int i4 = R.drawable.home_icon1;
                            ImageViewExtKt.load(img2V, fileFullUrl, (261628 & 2) != 0 ? 0 : i4, (261628 & 4) != 0 ? 0 : i4, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                            bodyBinding.starBar2.setRating(skillHome2.getStar());
                        }
                    });
                } else if (i2 == 2) {
                    bodyBinding(new Function1<ExamActivityRandomBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity$viewShowCard$2$3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
                            invoke2(examActivityRandomBinding);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(@NotNull ExamActivityRandomBinding bodyBinding) {
                            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                            bodyBinding.name3Tv.setText(skillHome2.getName());
                            ImageView img3V = bodyBinding.img3V;
                            Intrinsics.checkNotNullExpressionValue(img3V, "img3V");
                            String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(skillHome2.getIcon());
                            int i4 = R.drawable.home_icon1;
                            ImageViewExtKt.load(img3V, fileFullUrl, (261628 & 2) != 0 ? 0 : i4, (261628 & 4) != 0 ? 0 : i4, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                            bodyBinding.starBar3.setRating(skillHome2.getStar());
                        }
                    });
                }
                i2 = i3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v10, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v6, types: [T, java.lang.String] */
    public final void viewVideoShow() {
        getViewModel().httpGetSkillRandom();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = "";
        if (1 == getViewModel().getType()) {
            objectRef.element = "android.resource://" + getBaseContext().getPackageName() + "/" + R.raw.exam_one;
        } else {
            objectRef.element = "android.resource://" + getBaseContext().getPackageName() + "/" + R.raw.exam_three;
        }
        bodyBinding(new Function1<ExamActivityRandomBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.viewVideoShow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
                invoke2(examActivityRandomBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull final ExamActivityRandomBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.videoFl.setVisibility(0);
                bodyBinding.videoImgv.setVisibility(0);
                bodyBinding.videoV.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.viewVideoShow.1.1
                    @Override // android.media.MediaPlayer.OnPreparedListener
                    public void onPrepared(@Nullable MediaPlayer mp) {
                        LogExtKt.logd("videoView 播放器 onPrepared ", ExamRandomActivity.TAG);
                        bodyBinding.videoImgv.setVisibility(8);
                    }
                });
                bodyBinding.videoV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.viewVideoShow.1.2
                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public void onCompletion(@Nullable MediaPlayer mp) {
                        LogExtKt.logd("videoView 播放器 onCompletion ", ExamRandomActivity.TAG);
                        bodyBinding.videoFl.setVisibility(8);
                    }
                });
                bodyBinding.videoV.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.viewVideoShow.1.3
                    @Override // android.media.MediaPlayer.OnErrorListener
                    public boolean onError(@Nullable MediaPlayer mp, int what, int extra) {
                        LogExtKt.logd("videoView 播放器 onError " + what + " " + extra, ExamRandomActivity.TAG);
                        bodyBinding.videoFl.setVisibility(8);
                        return true;
                    }
                });
                bodyBinding.videoV.setVideoURI(Uri.parse(objectRef.element));
                bodyBinding.videoV.start();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewVideoStop() {
        bodyBinding(new Function1<ExamActivityRandomBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamRandomActivity.viewVideoStop.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ExamActivityRandomBinding examActivityRandomBinding) {
                invoke2(examActivityRandomBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ExamActivityRandomBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.videoV.stopPlayback();
                bodyBinding.videoFl.setVisibility(8);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06441(null), 3, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            getViewModel().setType(intent.getIntExtra("type", 1));
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06451());
        viewVideoShow();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        removeCallbacksAndMessages(null);
    }
}
