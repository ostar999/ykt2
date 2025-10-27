package com.ykb.ebook.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.android.exoplayer2.util.MimeTypes;
import com.psychiatrygarden.utils.Constants;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.model.CommentData;
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
import kotlin.jvm.internal.Ref;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fJ\u001e\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'J\u000e\u0010\n\u001a\u00020\u001d2\u0006\u0010(\u001a\u00020\u001fJ\u001e\u0010\u0010\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u001fJ\u0016\u0010\u0012\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'2\u0006\u0010#\u001a\u00020\u0013J.\u0010\u0015\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020'2\u0006\u0010#\u001a\u00020\u0013J\u001e\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u00020'2\u0006\u00100\u001a\u00020\u001fJ8\u0010\u0017\u001a\u00020\u001d2\u0006\u00101\u001a\u00020%2\u0006\u0010+\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020'2\b\u0010#\u001a\u0004\u0018\u00010\u0013J&\u0010\u0019\u001a\u00020\u001d2\u0006\u00102\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u001fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\tR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\t¨\u00063"}, d2 = {"Lcom/ykb/ebook/vm/BookReviewViewModel;", "Lcom/ykb/ebook/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "accountBanned", "Landroidx/lifecycle/MutableLiveData;", "", "getAccountBanned", "()Landroidx/lifecycle/MutableLiveData;", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "getBookInfo", "commentBookReview", "Lcom/ykb/ebook/model/CommentData;", "getCommentBookReview", "commentReport", "getCommentReport", "delReviewComment", "Lcom/ykb/ebook/model/BookReview;", "getDelReviewComment", "editComment", "getEditComment", "publishBookReview", "getPublishBookReview", "publishReview", "getPublishReview", "supportAndOppose", "getSupportAndOppose", "", "day", "", "reasonId", "userId", "addSupportOrOppse", "item", "isSupport", "", "position", "", "id", "commentId", "reason", ClientCookie.COMMENT_ATTR, "pic", "getBookReview", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "bookId", "isReply", "rate", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BookReviewViewModel extends BaseViewModel {

    @NotNull
    private final MutableLiveData<Object> accountBanned;

    @NotNull
    private final MutableLiveData<BookInfo> bookInfo;

    @NotNull
    private final MutableLiveData<CommentData> commentBookReview;

    @NotNull
    private final MutableLiveData<Object> commentReport;

    @NotNull
    private final MutableLiveData<BookReview> delReviewComment;

    @NotNull
    private final MutableLiveData<BookReview> editComment;

    @NotNull
    private final MutableLiveData<BookReview> publishBookReview;

    @NotNull
    private final MutableLiveData<Object> publishReview;

    @NotNull
    private final MutableLiveData<BookReview> supportAndOppose;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$accountBanned$1", f = "BookReviewViewModel.kt", i = {}, l = {177}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$accountBanned$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(HashMap<String, String> map, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$params, continuation);
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
                HashMap<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.accountBanned(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$addSupportOrOppse$1", f = "BookReviewViewModel.kt", i = {}, l = {133, 135}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$addSupportOrOppse$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10791 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ boolean $isSupport;
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10791(boolean z2, HashMap<String, String> map, Continuation<? super C10791> continuation) {
            super(2, continuation);
            this.$isSupport = z2;
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10791 c10791 = new C10791(this.$isSupport, this.$params, continuation);
            c10791.L$0 = obj;
            return c10791;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10791) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 != 0) {
                if (i2 == 1) {
                    ResultKt.throwOnFailure(obj);
                    return (BaseResponse) obj;
                }
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return (BaseResponse) obj;
            }
            ResultKt.throwOnFailure(obj);
            ApiService apiService = (ApiService) this.L$0;
            if (this.$isSupport) {
                HashMap<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.addSupport(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return (BaseResponse) obj;
            }
            HashMap<String, String> map2 = this.$params;
            this.label = 2;
            obj = apiService.addOppose(map2, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
            return (BaseResponse) obj;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/BookInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$bookInfo$1", f = "BookReviewViewModel.kt", i = {}, l = {48}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$bookInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10811 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BookInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10811(HashMap<String, String> map, Continuation<? super C10811> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10811 c10811 = new C10811(this.$paramMap, continuation);
            c10811.L$0 = obj;
            return c10811;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BookInfo>> continuation) {
            return ((C10811) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$commentReport$1", f = "BookReviewViewModel.kt", i = {}, l = {164}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$commentReport$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10831 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10831(HashMap<String, String> map, Continuation<? super C10831> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10831 c10831 = new C10831(this.$params, continuation);
            c10831.L$0 = obj;
            return c10831;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10831) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.commentReport(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$delReviewComment$1", f = "BookReviewViewModel.kt", i = {}, l = {151}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$delReviewComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10851 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10851(HashMap<String, String> map, Continuation<? super C10851> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10851 c10851 = new C10851(this.$params, continuation);
            c10851.L$0 = obj;
            return c10851;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10851) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.delFloorComment(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$editComment$1", f = "BookReviewViewModel.kt", i = {}, l = {115}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$editComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10871 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Ref.ObjectRef<HashMap<String, String>> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10871(Ref.ObjectRef<HashMap<String, String>> objectRef, Continuation<? super C10871> continuation) {
            super(2, continuation);
            this.$params = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10871 c10871 = new C10871(this.$params, continuation);
            c10871.L$0 = obj;
            return c10871;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10871) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                HashMap<String, String> map = this.$params.element;
                this.label = 1;
                obj = apiService.editComment(map, this);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/CommentData;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$getBookReview$1", f = "BookReviewViewModel.kt", i = {}, l = {40}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$getBookReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10891 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<CommentData>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10891(HashMap<String, String> map, Continuation<? super C10891> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10891 c10891 = new C10891(this.$params, continuation);
            c10891.L$0 = obj;
            return c10891;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<CommentData>> continuation) {
            return ((C10891) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.bookReviewListAll(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$publishBookReview$1", f = "BookReviewViewModel.kt", i = {}, l = {93}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$publishBookReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10911 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Ref.ObjectRef<HashMap<String, String>> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10911(Ref.ObjectRef<HashMap<String, String>> objectRef, Continuation<? super C10911> continuation) {
            super(2, continuation);
            this.$params = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10911 c10911 = new C10911(this.$params, continuation);
            c10911.L$0 = obj;
            return c10911;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10911) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                HashMap<String, String> map = this.$params.element;
                this.label = 1;
                obj = apiService.publishBookComment(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.BookReviewViewModel$publishReview$1", f = "BookReviewViewModel.kt", i = {}, l = {62}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.BookReviewViewModel$publishReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10931 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10931(HashMap<String, String> map, Continuation<? super C10931> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10931 c10931 = new C10931(this.$params, continuation);
            c10931.L$0 = obj;
            return c10931;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10931) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    public BookReviewViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.commentBookReview = new MutableLiveData<>();
        this.bookInfo = new MutableLiveData<>();
        this.publishReview = new MutableLiveData<>();
        this.publishBookReview = new MutableLiveData<>();
        this.editComment = new MutableLiveData<>();
        this.supportAndOppose = new MutableLiveData<>();
        this.delReviewComment = new MutableLiveData<>();
        this.commentReport = new MutableLiveData<>();
        this.accountBanned = new MutableLiveData<>();
    }

    public final void accountBanned(@NotNull String day, @NotNull String reasonId, @NotNull String userId) {
        Intrinsics.checkNotNullParameter(day, "day");
        Intrinsics.checkNotNullParameter(reasonId, "reasonId");
        Intrinsics.checkNotNullParameter(userId, "userId");
        RequestKt.executeRequest(this, new AnonymousClass1(MapsKt__MapsKt.hashMapOf(new Pair("days", day), new Pair("reason_id", reasonId), new Pair("target_user_id", userId), new Pair("type", "ebook")), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.accountBanned.2
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
                BookReviewViewModel.this.getAccountBanned().setValue(it);
            }
        });
    }

    public final void addSupportOrOppse(@NotNull final BookReview item, final boolean isSupport, final int position) {
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C10791(isSupport, MapsKt__MapsKt.hashMapOf(new Pair("review_id", item.getId())), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.addSupportOrOppse.2
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
                if (position != -1) {
                    item.setActionSupport(isSupport);
                    item.setTmpPosition(position);
                    this.getSupportAndOppose().setValue(item);
                }
            }
        });
    }

    public final void bookInfo(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new C10811(map, null), new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.bookInfo.2
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
                BookReviewViewModel.this.getBookInfo().setValue(it);
            }
        });
    }

    public final void commentReport(@NotNull String commentId, @NotNull String reasonId, @NotNull String reason) {
        Intrinsics.checkNotNullParameter(commentId, "commentId");
        Intrinsics.checkNotNullParameter(reasonId, "reasonId");
        Intrinsics.checkNotNullParameter(reason, "reason");
        RequestKt.executeRequest(this, new C10831(MapsKt__MapsKt.hashMapOf(new Pair("review_id", commentId), new Pair("reason_id", reasonId), new Pair("content", reason)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.commentReport.2
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
                BookReviewViewModel.this.getCommentReport().setValue(it);
            }
        });
    }

    public final void delReviewComment(final int position, @NotNull final BookReview item) {
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C10851(MapsKt__MapsKt.hashMapOf(new Pair("id", item.getId())), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.delReviewComment.2
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
                item.setTmpPosition(position);
                this.getDelReviewComment().setValue(item);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [T, java.util.HashMap] */
    public final void editComment(@NotNull final String comment, @NotNull String id, @NotNull String pic, final int position, @NotNull final BookReview item) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Intrinsics.checkNotNullParameter(item, "item");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = MapsKt__MapsKt.hashMapOf(new Pair("id", id), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("picture", pic));
        RequestKt.executeRequest(this, new C10871(objectRef, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.editComment.2
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
                item.setTmpPosition(position);
                item.setComment(comment);
                this.getEditComment().setValue(item);
            }
        });
    }

    @NotNull
    public final MutableLiveData<Object> getAccountBanned() {
        return this.accountBanned;
    }

    @NotNull
    public final MutableLiveData<BookInfo> getBookInfo() {
        return this.bookInfo;
    }

    public final void getBookReview(int page, int pageSize, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C10891(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page)), new Pair("page_size", String.valueOf(pageSize)), new Pair("book_id", bookId), new Pair("review_type", "1")), null), new Function1<CommentData, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.getBookReview.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(CommentData commentData) {
                invoke2(commentData);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull CommentData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                BookReviewViewModel.this.getCommentBookReview().setValue(it);
            }
        });
    }

    @NotNull
    public final MutableLiveData<CommentData> getCommentBookReview() {
        return this.commentBookReview;
    }

    @NotNull
    public final MutableLiveData<Object> getCommentReport() {
        return this.commentReport;
    }

    @NotNull
    public final MutableLiveData<BookReview> getDelReviewComment() {
        return this.delReviewComment;
    }

    @NotNull
    public final MutableLiveData<BookReview> getEditComment() {
        return this.editComment;
    }

    @NotNull
    public final MutableLiveData<BookReview> getPublishBookReview() {
        return this.publishBookReview;
    }

    @NotNull
    public final MutableLiveData<Object> getPublishReview() {
        return this.publishReview;
    }

    @NotNull
    public final MutableLiveData<BookReview> getSupportAndOppose() {
        return this.supportAndOppose;
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [T, java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r8v1, types: [T, java.util.HashMap] */
    public final void publishBookReview(final boolean isReply, @NotNull String comment, @NotNull String bookId, @NotNull String pic, final int position, @Nullable final BookReview item) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("picture", pic), new Pair("review_type", "1"));
        if (isReply) {
            Intrinsics.checkNotNull(item);
            objectRef.element = MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("parent_id", item.getId()), new Pair("picture", pic), new Pair("reply_primary_id", item.getReplyPrimaryId()), new Pair("picture", pic), new Pair("review_type", "1"));
        }
        RequestKt.executeRequest(this, new C10911(objectRef, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.publishBookReview.2
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
                if (isReply) {
                    BookReview bookReview = item;
                    Intrinsics.checkNotNull(bookReview);
                    bookReview.setActionReply(isReply);
                    BookReview bookReview2 = item;
                    Intrinsics.checkNotNull(bookReview2);
                    bookReview2.setTmpPosition(position);
                }
                this.getPublishBookReview().setValue(item);
            }
        });
    }

    public final void publishReview(@NotNull String rate, @NotNull String comment, @NotNull String pic, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(rate, "rate");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C10931(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("rate", rate), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("picture", pic)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.BookReviewViewModel.publishReview.2
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
                BookReviewViewModel.this.getPublishReview().setValue(it);
            }
        });
    }
}
