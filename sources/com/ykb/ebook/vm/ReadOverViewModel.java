package com.ykb.ebook.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.network.RequestKt;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\n\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/vm/ReadOverViewModel;", "Lcom/ykb/ebook/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "bookInfo", "Landroidx/lifecycle/MutableLiveData;", "Lcom/ykb/ebook/model/BookInfo;", "getBookInfo", "()Landroidx/lifecycle/MutableLiveData;", "publishReview", "", "getPublishReview", "", "id", "", "rate", "bookId", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ReadOverViewModel extends BaseViewModel {

    @NotNull
    private final MutableLiveData<BookInfo> bookInfo;

    @NotNull
    private final MutableLiveData<Object> publishReview;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/BookInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadOverViewModel$bookInfo$1", f = "ReadOverViewModel.kt", i = {}, l = {30}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadOverViewModel$bookInfo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BookInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(HashMap<String, String> map, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$paramMap, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BookInfo>> continuation) {
            return ((AnonymousClass1) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                HashMap<String, String> map = this.$paramMap;
                this.label = 1;
                obj = apiService.bookInfo(map, this);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadOverViewModel$publishReview$1", f = "ReadOverViewModel.kt", i = {}, l = {22}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadOverViewModel$publishReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11231 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11231(HashMap<String, String> map, Continuation<? super C11231> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11231 c11231 = new C11231(this.$params, continuation);
            c11231.L$0 = obj;
            return c11231;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11231) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                HashMap<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.publishReview(map, this);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReadOverViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.publishReview = new MutableLiveData<>();
        this.bookInfo = new MutableLiveData<>();
    }

    public final void bookInfo(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new AnonymousClass1(map, null), new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.vm.ReadOverViewModel.bookInfo.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookInfo bookInfo) {
                invoke2(bookInfo);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull BookInfo it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ReadOverViewModel.this.getBookInfo().setValue(it);
            }
        });
    }

    @NotNull
    public final MutableLiveData<BookInfo> getBookInfo() {
        return this.bookInfo;
    }

    @NotNull
    public final MutableLiveData<Object> getPublishReview() {
        return this.publishReview;
    }

    public final void publishReview(@NotNull String rate, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(rate, "rate");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C11231(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("rate", rate)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadOverViewModel.publishReview.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke2(obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Object it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ReadOverViewModel.this.getPublishReview().setValue(it);
            }
        });
    }
}
