package com.ykb.ebook.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.common.ConstantKt;
import com.ykb.ebook.common.UrlConfigKt;
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
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u000bJ\u0010\u0010\r\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u000bJ\u0010\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010\u0019\u001a\u00020\u000bJ&\u0010\u0012\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\t¨\u0006\u001e"}, d2 = {"Lcom/ykb/ebook/vm/BookInfoViewModel;", "Lcom/ykb/ebook/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "addBookData", "Landroidx/lifecycle/MutableLiveData;", "", "getAddBookData", "()Landroidx/lifecycle/MutableLiveData;", "bookError", "", "getBookError", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "getBookInfo", "delBookData", "getDelBookData", "publishReview", "", "getPublishReview", "addBook", "", "id", "delBook", "ids", "rate", ClientCookie.COMMENT_ATTR, "pic", "bookId", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BookInfoViewModel extends BaseViewModel {

    @NotNull
    private final MutableLiveData<Object> addBookData;

    @NotNull
    private final MutableLiveData<String> bookError;

    @NotNull
    private final MutableLiveData<BookInfo> bookInfo;

    @NotNull
    private final MutableLiveData<Object> delBookData;

    @NotNull
    private final MutableLiveData<Integer> publishReview;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.BookInfoViewModel$addBook$1", f = "BookInfoViewModel.kt", i = {}, l = {49}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookInfoViewModel$addBook$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
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
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
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
                String str = ConstantKt.getBOOK_MANAGE_URL() + UrlConfigKt.ADD_BOOK;
                HashMap<String, String> map = this.$paramMap;
                this.label = 1;
                obj = apiService.addBook(str, map, this);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/BookInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.BookInfoViewModel$bookInfo$1", f = "BookInfoViewModel.kt", i = {}, l = {27}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookInfoViewModel$bookInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10731 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BookInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10731(HashMap<String, String> map, Continuation<? super C10731> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10731 c10731 = new C10731(this.$paramMap, continuation);
            c10731.L$0 = obj;
            return c10731;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BookInfo>> continuation) {
            return ((C10731) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookInfoViewModel$delBook$1", f = "BookInfoViewModel.kt", i = {}, l = {57}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookInfoViewModel$delBook$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10751 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10751(HashMap<String, String> map, Continuation<? super C10751> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10751 c10751 = new C10751(this.$paramMap, continuation);
            c10751.L$0 = obj;
            return c10751;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10751) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                String str = ConstantKt.getBOOK_MANAGE_URL() + UrlConfigKt.DEL_BOOK;
                HashMap<String, String> map = this.$paramMap;
                this.label = 1;
                obj = apiService.delBook(str, map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookInfoViewModel$publishReview$1", f = "BookInfoViewModel.kt", i = {}, l = {41}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookInfoViewModel$publishReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10771 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10771(HashMap<String, String> map, Continuation<? super C10771> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10771 c10771 = new C10771(this.$params, continuation);
            c10771.L$0 = obj;
            return c10771;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10771) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    public BookInfoViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.bookInfo = new MutableLiveData<>();
        this.bookError = new MutableLiveData<>();
        this.publishReview = new MutableLiveData<>();
        this.addBookData = new MutableLiveData<>();
        this.delBookData = new MutableLiveData<>();
    }

    public static /* synthetic */ void addBook$default(BookInfoViewModel bookInfoViewModel, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "1";
        }
        bookInfoViewModel.addBook(str);
    }

    public static /* synthetic */ void bookInfo$default(BookInfoViewModel bookInfoViewModel, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "1";
        }
        bookInfoViewModel.bookInfo(str);
    }

    public static /* synthetic */ void delBook$default(BookInfoViewModel bookInfoViewModel, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "1";
        }
        bookInfoViewModel.delBook(str);
    }

    public final void addBook(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new AnonymousClass1(map, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookInfoViewModel.addBook.2
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
                BookInfoViewModel.this.getAddBookData().setValue(it);
            }
        });
    }

    public final void bookInfo(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new C10731(map, null), new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.vm.BookInfoViewModel.bookInfo.2
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
                BookInfoViewModel.this.getBookInfo().setValue(it);
            }
        });
    }

    public final void delBook(@NotNull String ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        HashMap map = new HashMap();
        map.put("ids", ids);
        RequestKt.executeRequest(this, new C10751(map, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookInfoViewModel.delBook.2
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
                BookInfoViewModel.this.getDelBookData().setValue(it);
            }
        });
    }

    @NotNull
    public final MutableLiveData<Object> getAddBookData() {
        return this.addBookData;
    }

    @NotNull
    public final MutableLiveData<String> getBookError() {
        return this.bookError;
    }

    @NotNull
    public final MutableLiveData<BookInfo> getBookInfo() {
        return this.bookInfo;
    }

    @NotNull
    public final MutableLiveData<Object> getDelBookData() {
        return this.delBookData;
    }

    @NotNull
    public final MutableLiveData<Integer> getPublishReview() {
        return this.publishReview;
    }

    public final void publishReview(final int rate, @NotNull String comment, @NotNull String pic, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C10771(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("rate", String.valueOf(rate)), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("picture", pic)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookInfoViewModel.publishReview.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                BookInfoViewModel.this.getPublishReview().setValue(Integer.valueOf(rate));
            }
        });
    }
}
