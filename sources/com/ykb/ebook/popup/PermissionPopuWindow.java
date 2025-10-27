package com.ykb.ebook.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.RecyclerView;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ruffian.library.widget.RTextView;
import com.tencent.connect.common.Constants;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.model.PermissionInfo;
import com.ykb.ebook.model.Ways;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.ImageLoader;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tH\u0002J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/popup/PermissionPopuWindow;", "Landroid/widget/PopupWindow;", d.R, "Landroid/content/Context;", "bookId", "", "(Landroid/content/Context;Ljava/lang/String;)V", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/Ways;", "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "", "item", "loadData", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class PermissionPopuWindow extends PopupWindow {

    @NotNull
    private CommonAdapter<Ways> mAdapter;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/PermissionInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.popup.PermissionPopuWindow$loadData$1", f = "PermissionPopuWindow.kt", i = {}, l = {62}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.popup.PermissionPopuWindow$loadData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10541 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<PermissionInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10541(HashMap<String, String> map, Continuation<? super C10541> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C10541(this.$params, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<PermissionInfo>> continuation) {
            return ((C10541) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService api = ApiServiceKt.getAPI();
                HashMap<String, String> map = this.$params;
                this.label = 1;
                obj = api.permissionMethod(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/PermissionInfo;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.popup.PermissionPopuWindow$loadData$2", f = "PermissionPopuWindow.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.popup.PermissionPopuWindow$loadData$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<PermissionInfo>, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<PermissionInfo> baseResponse, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass2 anonymousClass2 = PermissionPopuWindow.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = baseResponse;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            PermissionInfo permissionInfo = (PermissionInfo) ((BaseResponse) this.L$0).getData();
            if (permissionInfo != null) {
                PermissionPopuWindow permissionPopuWindow = PermissionPopuWindow.this;
                List<Ways> ways = permissionInfo.getWays();
                if (!(ways == null || ways.isEmpty())) {
                    permissionPopuWindow.mAdapter.submitList(permissionInfo.getWays());
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.popup.PermissionPopuWindow$loadData$3", f = "PermissionPopuWindow.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.popup.PermissionPopuWindow$loadData$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return new AnonymousClass3(continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionPopuWindow(@NotNull final Context context, @NotNull String bookId) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_permission_window, (ViewGroup) null);
        setContentView(viewInflate);
        View viewFindViewById = viewInflate.findViewById(R.id.recyclerview);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "contentView.findViewById(R.id.recyclerview)");
        CommonAdapter<Ways> commonAdapter = new CommonAdapter<>(R.layout.item_permission, null, 2, null);
        this.mAdapter = commonAdapter;
        commonAdapter.setConvert(new Function3<QuickViewHolder, Integer, Ways, Unit>() { // from class: com.ykb.ebook.popup.PermissionPopuWindow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, Ways ways) {
                invoke(quickViewHolder, num.intValue(), ways);
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable Ways ways) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                PermissionPopuWindow permissionPopuWindow = PermissionPopuWindow.this;
                Context context2 = context;
                Intrinsics.checkNotNull(ways);
                permissionPopuWindow.convert(context2, holder, i2, ways);
            }
        });
        ((RecyclerView) viewFindViewById).setAdapter(this.mAdapter);
        loadData(bookId);
        setWidth(-1);
        setHeight(-2);
        setBackgroundDrawable(new ColorDrawable(0));
        setAnimationStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
        setOutsideTouchable(false);
        setFocusable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void convert(Context context, QuickViewHolder holder, int position, Ways item) {
        ImageLoader.INSTANCE.load(context, item.getIcon()).placeholder(R.drawable.personal_headimg_icon).into((ImageView) holder.getView(R.id.leftimg));
        holder.setText(R.id.mfristTxt, item.getTitle()).setText(R.id.mDofristTxt, item.getDescription());
        ((RTextView) holder.getView(R.id.tv_to_lock)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.popup.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PermissionPopuWindow.convert$lambda$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(View view) {
    }

    private final void loadData(String bookId) {
        Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new C10541(MapsKt__MapsKt.hashMapOf(new Pair("user_id", "583383"), new Pair("book_id", bookId), new Pair("app_id", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)), null), 15, null), null, new AnonymousClass2(null), 1, null), null, new AnonymousClass3(null), 1, null);
    }
}
