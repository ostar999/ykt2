package com.yddmi.doctor.pages.product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.noober.background.drawable.DrawableCreator;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLImageView;
import com.noober.background.view.BLTextView;
import com.psychiatrygarden.db.SQLHelper;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.ProductActivityBinding;
import com.yddmi.doctor.entity.result.BatchInfo;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillPayWx;
import com.yddmi.doctor.entity.result.SkillTicket;
import com.yddmi.doctor.pages.product.PopupBottomTicket;
import com.yddmi.doctor.pages.product.ProductActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.pay.JPay;
import com.yddmi.doctor.utils.pay.wx.WeiXinPay;
import com.yddmi.doctor.widget.FigureIndicatorView;
import com.yikaobang.yixue.R2;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.indicator.base.IIndicator;
import java.util.List;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 $2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\b\u0010\u0016\u001a\u00020\u0010H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0014J\b\u0010\u0019\u001a\u00020\u0010H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u001a\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\nH\u0002J\b\u0010 \u001a\u00020\u0010H\u0002J\b\u0010!\u001a\u00020\u0010H\u0002J\u0018\u0010\"\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/yddmi/doctor/pages/product/ProductActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/ProductActivityBinding;", "Lcom/yddmi/doctor/pages/product/ProductViewModel;", "()V", "dp58", "", "mAdapter", "Lcom/yddmi/doctor/pages/product/BannerAdapter;", "mAll", "", "mFromHome", "mPayType", "mSkillAdapter", "Lcom/yddmi/doctor/pages/product/AdapterSkill;", "httpSkillBuy", "", "m", "Lcom/yddmi/doctor/entity/result/SkillHome;", "payType", "initFlow", "initParam", "initView", "onBackPressed", "onDestroy", "screenOrientation", "setupIndicatorView", "Lcom/zhpan/indicator/base/IIndicator;", "viewBgChange", "view", "Landroid/view/View;", SQLHelper.SELECTED, "viewShowPopTicket", "viewShowProduct", "viewSkillChange", "position", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true, transparent = true)
@SourceDebugExtension({"SMAP\nProductActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProductActivity.kt\ncom/yddmi/doctor/pages/product/ProductActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,412:1\n18#2,2:413\n1#3:415\n1#3:416\n*S KotlinDebug\n*F\n+ 1 ProductActivity.kt\ncom/yddmi/doctor/pages/product/ProductActivity\n*L\n65#1:413,2\n65#1:415\n*E\n"})
/* loaded from: classes6.dex */
public final class ProductActivity extends BaseVMActivity<ProductActivityBinding, ProductViewModel> {

    @NotNull
    private static final String TAG = "ProductActivity";
    private int dp58;
    private BannerAdapter mAdapter;
    private boolean mFromHome;
    private AdapterSkill mSkillAdapter;
    private boolean mAll = true;
    private int mPayType = 100;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.product.ProductActivity$initFlow$1", f = "ProductActivity.kt", i = {}, l = {R2.array.ease_numbers_file_suffix}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.product.ProductActivity$initFlow$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ProductActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> dataChangeMsf = ProductActivity.this.getViewModel().getDataChangeMsf();
                final ProductActivity productActivity = ProductActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.product.ProductActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            productActivity.hideLoading();
                            productActivity.viewShowProduct();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.product.ProductActivity$initFlow$2", f = "ProductActivity.kt", i = {}, l = {202}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.product.ProductActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C08612 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08612(Continuation<? super C08612> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ProductActivity.this.new C08612(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08612) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> ticketChangeMsf = ProductActivity.this.getViewModel().getTicketChangeMsf();
                final ProductActivity productActivity = ProductActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.product.ProductActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            productActivity.hideLoading();
                            productActivity.viewShowPopTicket();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ticketChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/ProductActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nProductActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProductActivity.kt\ncom/yddmi/doctor/pages/product/ProductActivity$initView$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,412:1\n1#2:413\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.product.ProductActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08631 extends Lambda implements Function1<ProductActivityBinding, Unit> {
        public C08631() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(ProductActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            GlobalAction.INSTANCE.setHomePayCancelTicket(true);
            this$0.closeActivity();
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-SHOP-RETURN", "返回", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$11(ProductActivity this$0, ProductActivityBinding this_bodyBinding, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this$0.mPayType = 101;
            BLConstraintLayout aliCl = this_bodyBinding.aliCl;
            Intrinsics.checkNotNullExpressionValue(aliCl, "aliCl");
            this$0.viewBgChange(aliCl, false);
            BLConstraintLayout wxCl = this_bodyBinding.wxCl;
            Intrinsics.checkNotNullExpressionValue(wxCl, "wxCl");
            this$0.viewBgChange(wxCl, true);
            if (this$0.getViewModel().getCurrentSkill() != null) {
                YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                SkillHome currentSkill = this$0.getViewModel().getCurrentSkill();
                Intrinsics.checkNotNull(currentSkill);
                String label = currentSkill.getLabel();
                if (label.length() == 0) {
                    SkillHome currentSkill2 = this$0.getViewModel().getCurrentSkill();
                    Intrinsics.checkNotNull(currentSkill2);
                    label = currentSkill2.getName();
                }
                YddPointManager.addPoint$default(companion, "SJ-A-HOME-SHOP-WXPAY", "微信支付(" + ((Object) label) + ")", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$13(ProductActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.loadingDialog();
            this$0.getViewModel().httpGetUserCoupon();
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-SHOP-COUPON", "优惠卷", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(ProductActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            SkillHome data = this$0.getViewModel().getData();
            Intrinsics.checkNotNull(data);
            this$0.httpSkillBuy(data, this$0.mPayType);
            if (this$0.mFromHome) {
                YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                SkillHome data2 = this$0.getViewModel().getData();
                Intrinsics.checkNotNull(data2);
                String label = data2.getLabel();
                if (label.length() == 0) {
                    SkillHome data3 = this$0.getViewModel().getData();
                    Intrinsics.checkNotNull(data3);
                    label = data3.getName();
                }
                YddPointManager.addPoint$default(companion, "SJ-A-HOME-SHOP-UNLOCK", "立即解锁(" + ((Object) label) + ")", null, 4, null);
                return;
            }
            if (1 == this$0.getViewModel().getSkill24()) {
                YddPointManager companion2 = YddPointManager.INSTANCE.getInstance();
                SkillHome data4 = this$0.getViewModel().getData();
                Intrinsics.checkNotNull(data4);
                String label2 = data4.getLabel();
                if (label2.length() == 0) {
                    SkillHome data5 = this$0.getViewModel().getData();
                    Intrinsics.checkNotNull(data5);
                    label2 = data5.getName();
                }
                YddPointManager.addPoint$default(companion2, "SJ-A-HOME-BASEOPERATE-BUYING", "购买(" + ((Object) label2) + ")", null, 4, null);
                return;
            }
            YddPointManager companion3 = YddPointManager.INSTANCE.getInstance();
            SkillHome data6 = this$0.getViewModel().getData();
            Intrinsics.checkNotNull(data6);
            String label3 = data6.getLabel();
            if (label3.length() == 0) {
                SkillHome data7 = this$0.getViewModel().getData();
                Intrinsics.checkNotNull(data7);
                label3 = data7.getName();
            }
            YddPointManager.addPoint$default(companion3, "SJ-A-HOME-BODYCHECK-BUYING", "购买(" + ((Object) label3) + ")", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(ProductActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            if (this_bodyBinding.detailsTv.getMaxLines() == 1000) {
                this_bodyBinding.detailsTv.setMaxLines(1);
                this_bodyBinding.stateImgv.setImageResource(R.drawable.common_right);
            } else {
                this_bodyBinding.detailsTv.setMaxLines(1000);
                this_bodyBinding.stateImgv.setImageResource(R.drawable.common_down);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(ProductActivityBinding this_bodyBinding, ProductActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this_bodyBinding.rv.getHeight() == this$0.dp58) {
                this_bodyBinding.rightImgv.setImageResource(R.drawable.common_down);
                RecyclerView rv = this_bodyBinding.rv;
                Intrinsics.checkNotNullExpressionValue(rv, "rv");
                ViewExtKt.setWh(rv, -1, CommonExtKt.dp2px(this$0, R2.attr.badgeWidePadding));
                this_bodyBinding.rvFl.requestLayout();
                return;
            }
            this_bodyBinding.rightImgv.setImageResource(R.drawable.common_right);
            RecyclerView rv2 = this_bodyBinding.rv;
            Intrinsics.checkNotNullExpressionValue(rv2, "rv");
            ViewExtKt.setWh(rv2, -1, this$0.dp58);
            this_bodyBinding.rvFl.requestLayout();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$9(ProductActivity this$0, ProductActivityBinding this_bodyBinding, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this$0.mPayType = 100;
            BLConstraintLayout aliCl = this_bodyBinding.aliCl;
            Intrinsics.checkNotNullExpressionValue(aliCl, "aliCl");
            this$0.viewBgChange(aliCl, true);
            BLConstraintLayout wxCl = this_bodyBinding.wxCl;
            Intrinsics.checkNotNullExpressionValue(wxCl, "wxCl");
            this$0.viewBgChange(wxCl, false);
            if (this$0.getViewModel().getCurrentSkill() != null) {
                YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                SkillHome currentSkill = this$0.getViewModel().getCurrentSkill();
                Intrinsics.checkNotNull(currentSkill);
                String label = currentSkill.getLabel();
                if (label.length() == 0) {
                    SkillHome currentSkill2 = this$0.getViewModel().getCurrentSkill();
                    Intrinsics.checkNotNull(currentSkill2);
                    label = currentSkill2.getName();
                }
                YddPointManager.addPoint$default(companion, "SJ-A-HOME-SHOP-ZFBPAY", "支付宝支付(" + ((Object) label) + ")", null, 4, null);
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ProductActivityBinding productActivityBinding) {
            invoke2(productActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final ProductActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            BLImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final ProductActivity productActivity = ProductActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ProductActivity.C08631.invoke$lambda$0(productActivity, view);
                }
            }, 0L, 2, null);
            BLTextView unlockTv = bodyBinding.unlockTv;
            Intrinsics.checkNotNullExpressionValue(unlockTv, "unlockTv");
            final ProductActivity productActivity2 = ProductActivity.this;
            ViewExtKt.setDebounceClickListener$default(unlockTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ProductActivity.C08631.invoke$lambda$4(productActivity2, view);
                }
            }, 0L, 2, null);
            ConstraintLayout goodsCl = bodyBinding.goodsCl;
            Intrinsics.checkNotNullExpressionValue(goodsCl, "goodsCl");
            ViewExtKt.setDebounceClickListener$default(goodsCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ProductActivity.C08631.invoke$lambda$5(bodyBinding, view);
                }
            }, 0L, 2, null);
            RecyclerView rv = bodyBinding.rv;
            Intrinsics.checkNotNullExpressionValue(rv, "rv");
            ViewExtKt.setWh(rv, -1, ProductActivity.this.dp58);
            ImageView rightImgv = bodyBinding.rightImgv;
            Intrinsics.checkNotNullExpressionValue(rightImgv, "rightImgv");
            final ProductActivity productActivity3 = ProductActivity.this;
            ViewExtKt.setDebounceClickListener$default(rightImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ProductActivity.C08631.invoke$lambda$6(bodyBinding, productActivity3, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView = bodyBinding.rv;
            ProductActivity productActivity4 = ProductActivity.this;
            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
            AdapterSkill adapterSkill = productActivity4.mSkillAdapter;
            BannerAdapter bannerAdapter = null;
            if (adapterSkill == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSkillAdapter");
                adapterSkill = null;
            }
            recyclerView.setAdapter(adapterSkill);
            recyclerView.setNestedScrollingEnabled(false);
            BLConstraintLayout aliCl = bodyBinding.aliCl;
            Intrinsics.checkNotNullExpressionValue(aliCl, "aliCl");
            final ProductActivity productActivity5 = ProductActivity.this;
            ViewExtKt.setDebounceClickListener$default(aliCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    ProductActivity.C08631.invoke$lambda$9(productActivity5, bodyBinding, view);
                }
            }, 0L, 2, null);
            BLConstraintLayout wxCl = bodyBinding.wxCl;
            Intrinsics.checkNotNullExpressionValue(wxCl, "wxCl");
            final ProductActivity productActivity6 = ProductActivity.this;
            ViewExtKt.setDebounceClickListener$default(wxCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    ProductActivity.C08631.invoke$lambda$11(productActivity6, bodyBinding, view);
                }
            }, 0L, 2, null);
            BannerViewPager bannerViewPager = bodyBinding.banner;
            ProductActivity productActivity7 = ProductActivity.this;
            bannerViewPager.setCanLoop(true);
            bannerViewPager.setAutoPlay(true);
            BannerAdapter bannerAdapter2 = productActivity7.mAdapter;
            if (bannerAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            } else {
                bannerAdapter = bannerAdapter2;
            }
            bannerViewPager.setAdapter(bannerAdapter);
            bannerViewPager.registerLifecycleObserver(productActivity7.getLifecycle());
            bannerViewPager.setIndicatorGravity(4);
            bannerViewPager.setIndicatorView(productActivity7.setupIndicatorView());
            bannerViewPager.create();
            ConstraintLayout ticketCl = bodyBinding.ticketCl;
            Intrinsics.checkNotNullExpressionValue(ticketCl, "ticketCl");
            final ProductActivity productActivity8 = ProductActivity.this;
            ViewExtKt.setDebounceClickListener$default(ticketCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ProductActivity.C08631.invoke$lambda$13(productActivity8, view);
                }
            }, 0L, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpSkillBuy(SkillHome m2, int payType) {
        String couponRecordId;
        if (this.mAll) {
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-SHOP-ALLSKILL", "全部技能", null, 4, null);
        } else {
            YddPointManager companion = YddPointManager.INSTANCE.getInstance();
            String label = m2.getLabel();
            if (label.length() == 0) {
                label = m2.getName();
            }
            companion.addPoint("SJ-A-HOME-SHOP-SKILL", label, String.valueOf(m2.getId()));
        }
        if (payType != 100) {
            if (payType != 101) {
                return;
            }
            FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.getPrepayWx(String.valueOf(m2.getId()), this.mAll ? 1 : 0), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.product.ProductActivity.httpSkillBuy.4
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
                    ProductActivity.this.hideLoading();
                    String message = it.getMessage();
                    if (message == null || message.length() == 0) {
                        return;
                    }
                    Toaster.show((CharSequence) it.getMessage());
                }
            }, new Function1<SkillPayWx, Unit>() { // from class: com.yddmi.doctor.pages.product.ProductActivity.httpSkillBuy.5
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SkillPayWx skillPayWx) {
                    invoke2(skillPayWx);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable SkillPayWx skillPayWx) {
                    if (skillPayWx != null) {
                        WeiXinPay.getInstance(ProductActivity.this).init(YddUserManager.INSTANCE.getInstance().getmWxApi());
                        JPay intance = JPay.getIntance(ProductActivity.this);
                        String appid = skillPayWx.getAppid();
                        String partnerId = skillPayWx.getPartnerId();
                        String prepayId = skillPayWx.getPrepayId();
                        String nonceStr = skillPayWx.getNonceStr();
                        String timestamp = skillPayWx.getTimestamp();
                        String sign = skillPayWx.getSign();
                        final ProductActivity productActivity = ProductActivity.this;
                        intance.toWxPay(appid, partnerId, prepayId, nonceStr, timestamp, sign, new JPay.PayListener() { // from class: com.yddmi.doctor.pages.product.ProductActivity.httpSkillBuy.5.1
                            @Override // com.yddmi.doctor.utils.pay.JPay.PayListener
                            public void onPayCancel() {
                                LogExtKt.logd("支付取消 onPayCancel", ProductActivity.TAG);
                            }

                            @Override // com.yddmi.doctor.utils.pay.JPay.PayListener
                            public void onPayError(int error_code, @Nullable String message) {
                                LogExtKt.logd("微信支付失败 onPayError " + error_code + " " + message, ProductActivity.TAG);
                                Toaster.show(R.string.product_buy_error);
                            }

                            @Override // com.yddmi.doctor.utils.pay.JPay.PayListener
                            public void onPaySuccess() {
                                LogExtKt.logd("微信支付成功 onPaySuccess", ProductActivity.TAG);
                                Toaster.show(R.string.product_buy_success);
                                GlobalAction globalAction = GlobalAction.INSTANCE;
                                globalAction.setHomeDataRefresh(true);
                                globalAction.setHomeShowBuyStatus(true);
                                productActivity.closeActivity();
                            }
                        });
                    }
                }
            });
        } else {
            SkillTicket currentTicket = getViewModel().getCurrentTicket();
            if (currentTicket == null || (couponRecordId = currentTicket.getCouponRecordId()) == null) {
                couponRecordId = "";
            }
            FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.getOrderStr(String.valueOf(m2.getId()), this.mAll ? 1 : 0, couponRecordId), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.product.ProductActivity.httpSkillBuy.2
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
                    ProductActivity.this.hideLoading();
                    String message = it.getMessage();
                    if (message == null || message.length() == 0) {
                        return;
                    }
                    Toaster.show((CharSequence) it.getMessage());
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.product.ProductActivity.httpSkillBuy.3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable String str) {
                    if (str == null || str.length() == 0) {
                        return;
                    }
                    JPay intance = JPay.getIntance(ProductActivity.this);
                    final ProductActivity productActivity = ProductActivity.this;
                    intance.toAliPay(str, new JPay.PayListener() { // from class: com.yddmi.doctor.pages.product.ProductActivity.httpSkillBuy.3.1
                        @Override // com.yddmi.doctor.utils.pay.JPay.PayListener
                        public void onPayCancel() {
                            LogExtKt.logd("支付取消 onPayCancel", ProductActivity.TAG);
                        }

                        @Override // com.yddmi.doctor.utils.pay.JPay.PayListener
                        public void onPayError(int error_code, @Nullable String message) {
                            LogExtKt.logd("支付宝支付失败 onPayError " + error_code + " " + message, ProductActivity.TAG);
                            Toaster.show(R.string.product_buy_error);
                        }

                        @Override // com.yddmi.doctor.utils.pay.JPay.PayListener
                        public void onPaySuccess() {
                            LogExtKt.logd("支付宝支付成功 onPaySuccess", ProductActivity.TAG);
                            Toaster.show(R.string.product_buy_success);
                            GlobalAction globalAction = GlobalAction.INSTANCE;
                            globalAction.setHomeDataRefresh(true);
                            globalAction.setHomeShowBuyStatus(true);
                            productActivity.closeActivity();
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IIndicator setupIndicatorView() {
        FigureIndicatorView figureIndicatorView = new FigureIndicatorView(this);
        figureIndicatorView.setRadius(getResources().getDimensionPixelOffset(R.dimen.dp_18));
        figureIndicatorView.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_13));
        figureIndicatorView.setBackgroundColor(ContextExtKt.getColorM(this, R.color.c_3f000000));
        return figureIndicatorView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewBgChange(View view, boolean selected) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (selected) {
            Drawable select = new DrawableCreator.Builder().setCornersRadius(CommonExtKt.dp2px(this, 6)).setSolidColor(ContextExtKt.getColorM(this, R.color.color_white)).setStrokeColor(ContextExtKt.getColorM(this, R.color.c_fd600d)).setStrokeWidth(CommonExtKt.dp2px(this, 1)).build();
            Intrinsics.checkNotNullExpressionValue(select, "select");
            ViewExtKt.setBackgroundJellyBean16(view, select);
        } else {
            Drawable noSelect = new DrawableCreator.Builder().setCornersRadius(CommonExtKt.dp2px(this, 6)).setSolidColor(ContextExtKt.getColorM(this, R.color.c_f6f6f6)).build();
            Intrinsics.checkNotNullExpressionValue(noSelect, "noSelect");
            ViewExtKt.setBackgroundJellyBean16(view, noSelect);
        }
    }

    public static /* synthetic */ void viewBgChange$default(ProductActivity productActivity, View view, boolean z2, int i2, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        productActivity.viewBgChange(view, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowPopTicket() {
        String coupon;
        List<SkillTicket> mSkillTicketList = getViewModel().getMSkillTicketList();
        if (mSkillTicketList == null || mSkillTicketList.isEmpty()) {
            getViewModel().setCurrentTicket(null);
            BLTextView bLTextView = ((ProductActivityBinding) getBodyBinding()).ticketNameTv;
            SkillHome data = getViewModel().getData();
            Intrinsics.checkNotNull(data);
            String coupon2 = data.getCoupon();
            if (coupon2 == null || coupon2.length() == 0) {
                coupon = getString(R.string.product_ticket3);
            } else {
                SkillHome data2 = getViewModel().getData();
                Intrinsics.checkNotNull(data2);
                coupon = data2.getCoupon();
            }
            bLTextView.setText(coupon);
        }
        final PopupBottomTicket popupBottomTicket = new PopupBottomTicket(this);
        popupBottomTicket.setOnPopupTicketClickListener(new PopupBottomTicket.OnPopupTicketClickListener() { // from class: com.yddmi.doctor.pages.product.ProductActivity.viewShowPopTicket.1
            @Override // com.yddmi.doctor.pages.product.PopupBottomTicket.OnPopupTicketClickListener
            public void onGoTicketSelect(@Nullable SkillTicket m2) {
                if (ProductActivity.this.getViewModel().getCurrentSkill() != null) {
                    ProductViewModel viewModel = ProductActivity.this.getViewModel();
                    SkillHome currentSkill = ProductActivity.this.getViewModel().getCurrentSkill();
                    Intrinsics.checkNotNull(currentSkill);
                    viewModel.httpGetProductInfo(currentSkill, m2, m2 != null);
                }
                popupBottomTicket.dismiss();
            }
        });
        popupBottomTicket.setPopupData(getViewModel().getCurrentTicket(), getViewModel().getMSkillTicketList());
        new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasShadowBg(Boolean.TRUE).isViewMode(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupBottomTicket).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowProduct() {
        if (getViewModel().getData() != null) {
            bodyBinding(new Function1<ProductActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.product.ProductActivity.viewShowProduct.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ProductActivityBinding productActivityBinding) {
                    invoke2(productActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull ProductActivityBinding bodyBinding) {
                    String coupon;
                    Object preferentialPrice;
                    Object marketPrice;
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    TextView textView = bodyBinding.maxMoneyTv;
                    textView.setPaintFlags(textView.getPaintFlags() | 16);
                    SkillHome currentSkill = ProductActivity.this.getViewModel().getCurrentSkill();
                    if (Intrinsics.areEqual(currentSkill != null ? currentSkill.getSkillId() : null, ProductActivity.this.getViewModel().getAllItem().getSkillId())) {
                        if (3 == ProductActivity.this.getViewModel().getSkill24()) {
                            bodyBinding.f25744tv.setText(ProductActivity.this.getString(R.string.product_all3));
                            bodyBinding.detailsTv.setText(ProductActivity.this.getString(R.string.product_all_body));
                        } else {
                            bodyBinding.f25744tv.setText(ProductActivity.this.getString(R.string.product_all2));
                            bodyBinding.detailsTv.setText(ProductActivity.this.getString(R.string.product_all24));
                        }
                        TextView textView2 = bodyBinding.moneyTv;
                        SkillHome data = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data);
                        BatchInfo batchInfo = data.getBatchInfo();
                        if (batchInfo == null || (preferentialPrice = batchInfo.getPreferentialPrice()) == null) {
                            preferentialPrice = 0;
                        }
                        textView2.setText(String.valueOf(preferentialPrice));
                        TextView textView3 = bodyBinding.maxMoneyTv;
                        SkillHome data2 = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data2);
                        BatchInfo batchInfo2 = data2.getBatchInfo();
                        if (batchInfo2 == null || (marketPrice = batchInfo2.getMarketPrice()) == null) {
                            marketPrice = 0;
                        }
                        textView3.setText(String.valueOf(marketPrice));
                        bodyBinding.banner.refreshData(ProductActivity.this.getViewModel().getIntroduceUrlsAllList());
                    } else {
                        TextView textView4 = bodyBinding.f25744tv;
                        SkillHome data3 = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data3);
                        textView4.setText(data3.getName());
                        TextView textView5 = bodyBinding.detailsTv;
                        SkillHome data4 = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data4);
                        textView5.setText(data4.getIntroduce());
                        TextView textView6 = bodyBinding.moneyTv;
                        SkillHome data5 = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data5);
                        textView6.setText(String.valueOf(data5.getPreferentialPrice()));
                        TextView textView7 = bodyBinding.maxMoneyTv;
                        SkillHome data6 = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data6);
                        textView7.setText(String.valueOf(data6.getMarketPrice()));
                        BannerViewPager bannerViewPager = bodyBinding.banner;
                        SkillHome data7 = ProductActivity.this.getViewModel().getData();
                        Intrinsics.checkNotNull(data7);
                        bannerViewPager.refreshData(data7.getIntroduceUrlsList());
                    }
                    BLTextView bLTextView = bodyBinding.ticketNameTv;
                    SkillHome data8 = ProductActivity.this.getViewModel().getData();
                    Intrinsics.checkNotNull(data8);
                    String coupon2 = data8.getCoupon();
                    boolean z2 = coupon2 == null || coupon2.length() == 0;
                    ProductActivity productActivity = ProductActivity.this;
                    if (z2) {
                        coupon = productActivity.getString(R.string.product_ticket3);
                    } else {
                        SkillHome data9 = productActivity.getViewModel().getData();
                        Intrinsics.checkNotNull(data9);
                        coupon = data9.getCoupon();
                    }
                    bLTextView.setText(coupon);
                }
            });
            AdapterSkill adapterSkill = this.mSkillAdapter;
            AdapterSkill adapterSkill2 = null;
            if (adapterSkill == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSkillAdapter");
                adapterSkill = null;
            }
            SkillHome currentSkill = getViewModel().getCurrentSkill();
            Intrinsics.checkNotNull(currentSkill);
            adapterSkill.setCurrentSkill(currentSkill);
            AdapterSkill adapterSkill3 = this.mSkillAdapter;
            if (adapterSkill3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSkillAdapter");
            } else {
                adapterSkill2 = adapterSkill3;
            }
            adapterSkill2.set(getViewModel().getMSkillList());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewSkillChange(SkillHome m2, int position) {
        String skillId = m2.getSkillId();
        SkillHome currentSkill = getViewModel().getCurrentSkill();
        if (Intrinsics.areEqual(skillId, currentSkill != null ? currentSkill.getSkillId() : null)) {
            return;
        }
        boolean z2 = position == 0;
        this.mAll = z2;
        if (this.mFromHome) {
            if (z2) {
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-SHOP-ALLSKILL", "全部技能", null, 4, null);
            } else {
                YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                String label = m2.getLabel();
                if (label.length() == 0) {
                    label = m2.getName();
                }
                YddPointManager.addPoint$default(companion, "SJ-A-HOME-SHOP-SKILL", "单个技能(" + ((Object) label) + ")", null, 4, null);
            }
        }
        loadingDialog();
        ProductViewModel.httpGetProductInfo$default(getViewModel(), m2, null, true, 2, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08612(null), 3, null);
        viewSkillChange(getViewModel().getAllItem(), 0);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_white);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            getViewModel().setType(intent.getIntExtra("type", 100));
            ProductViewModel viewModel = getViewModel();
            String stringExtra = intent.getStringExtra("name");
            if (stringExtra == null) {
                stringExtra = "";
            } else {
                Intrinsics.checkNotNullExpressionValue(stringExtra, "it.getStringExtra(\"name\") ?: \"\"");
            }
            viewModel.setName(stringExtra);
            ProductViewModel viewModel2 = getViewModel();
            String stringExtra2 = intent.getStringExtra("skillId");
            if (stringExtra2 == null) {
                stringExtra2 = "-1";
            } else {
                Intrinsics.checkNotNullExpressionValue(stringExtra2, "it.getStringExtra(\"skillId\") ?: \"-1\"");
            }
            viewModel2.setSkillId(stringExtra2);
            getViewModel().setSkill24(intent.getIntExtra("skill24", 1));
            this.mFromHome = intent.getBooleanExtra("fromHome", false);
        }
        getViewModel().dealData();
        this.dp58 = CommonExtKt.dp2px(this, 58);
        this.mAdapter = new BannerAdapter();
        AdapterSkill adapterSkill = new AdapterSkill(null);
        this.mSkillAdapter = adapterSkill;
        adapterSkill.setOnItemClickListener(new Function2<SkillHome, Integer, Unit>() { // from class: com.yddmi.doctor.pages.product.ProductActivity.initParam.3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(SkillHome skillHome, Integer num) {
                invoke(skillHome, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull SkillHome m2, int i2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                ProductActivity.this.viewSkillChange(m2, i2);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C08631());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        LogExtKt.logd("onBackPressed 返回点击", TAG);
        GlobalAction.INSTANCE.setHomePayCancelTicket(true);
        closeActivity();
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-SHOP-RETURN", "返回", null, 4, null);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
