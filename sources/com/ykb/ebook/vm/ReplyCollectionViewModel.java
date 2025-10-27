package com.ykb.ebook.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.android.exoplayer2.util.MimeTypes;
import com.psychiatrygarden.utils.Constants;
import com.yikaobang.yixue.R2;
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

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fJX\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u001f2\b\u0010,\u001a\u0004\u0018\u00010\u00132\u0006\u0010-\u001a\u00020.J\u001e\u0010/\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u00132\u0006\u00100\u001a\u00020$2\u0006\u0010-\u001a\u00020.J\u0010\u0010\n\u001a\u00020\u001d2\b\b\u0002\u00101\u001a\u00020\u001fJ\u001e\u0010\u0010\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\u001fJ\u0016\u0010\u0012\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020.2\u0006\u0010,\u001a\u00020\u0013J.\u0010\u0015\u001a\u00020\u001d2\u0006\u00103\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.2\u0006\u0010,\u001a\u00020\u0013J&\u00104\u001a\u00020\u001d2\u0006\u00105\u001a\u00020.2\u0006\u00106\u001a\u00020.2\u0006\u0010%\u001a\u00020\u001f2\u0006\u00107\u001a\u00020\u001fJ8\u0010\u0017\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$2\u0006\u00103\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020.2\b\u0010,\u001a\u0004\u0018\u00010\u0013J\u0016\u0010\u0019\u001a\u00020\u001d2\u0006\u00108\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u001fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\tR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\t¨\u00069"}, d2 = {"Lcom/ykb/ebook/vm/ReplyCollectionViewModel;", "Lcom/ykb/ebook/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "accountBanned", "Landroidx/lifecycle/MutableLiveData;", "", "getAccountBanned", "()Landroidx/lifecycle/MutableLiveData;", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "getBookInfo", "commentBookReview", "Lcom/ykb/ebook/model/CommentData;", "getCommentBookReview", "commentReport", "getCommentReport", "delComment", "Lcom/ykb/ebook/model/BookReview;", "getDelComment", "editComment", "getEditComment", "publishBookReview", "getPublishBookReview", "publishReview", "getPublishReview", "supportAndOppose", "getSupportAndOppose", "", "reasonId", "", "day", "userId", "addParagraphReview", "isReply", "", "bookId", "chapterId", "paragraphId", "paragraphContent", "commentId", "pic", "content", "item", "position", "", "addSupportOrOppse", "isSupport", "id", "reason", ClientCookie.COMMENT_ATTR, "getBookReview", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "parentId", "rate", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ReplyCollectionViewModel extends BaseViewModel {

    @NotNull
    private final MutableLiveData<Object> accountBanned;

    @NotNull
    private final MutableLiveData<BookInfo> bookInfo;

    @NotNull
    private final MutableLiveData<CommentData> commentBookReview;

    @NotNull
    private final MutableLiveData<Object> commentReport;

    @NotNull
    private final MutableLiveData<BookReview> delComment;

    @NotNull
    private final MutableLiveData<BookReview> editComment;

    @NotNull
    private final MutableLiveData<BookReview> publishBookReview;

    @NotNull
    private final MutableLiveData<Object> publishReview;

    @NotNull
    private final MutableLiveData<BookReview> supportAndOppose;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$accountBanned$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {R2.array.ease_other_file_suffix}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$accountBanned$1, reason: invalid class name */
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$addParagraphReview$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {132}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$addParagraphReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11251 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11251(HashMap<String, String> map, Continuation<? super C11251> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11251 c11251 = new C11251(this.$params, continuation);
            c11251.L$0 = obj;
            return c11251;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11251) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$addSupportOrOppse$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {153, 155}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$addSupportOrOppse$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11271 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ boolean $isSupport;
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11271(boolean z2, HashMap<String, String> map, Continuation<? super C11271> continuation) {
            super(2, continuation);
            this.$isSupport = z2;
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11271 c11271 = new C11271(this.$isSupport, this.$params, continuation);
            c11271.L$0 = obj;
            return c11271;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11271) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$bookInfo$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {68}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$bookInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11291 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BookInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11291(HashMap<String, String> map, Continuation<? super C11291> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11291 c11291 = new C11291(this.$paramMap, continuation);
            c11291.L$0 = obj;
            return c11291;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BookInfo>> continuation) {
            return ((C11291) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$commentReport$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {182}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$commentReport$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11311 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11311(HashMap<String, String> map, Continuation<? super C11311> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11311 c11311 = new C11311(this.$params, continuation);
            c11311.L$0 = obj;
            return c11311;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11311) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$delComment$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {169}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$delComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11331 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11331(HashMap<String, String> map, Continuation<? super C11331> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11331 c11331 = new C11331(this.$params, continuation);
            c11331.L$0 = obj;
            return c11331;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11331) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$editComment$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {213}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$editComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11351 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Ref.ObjectRef<HashMap<String, String>> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11351(Ref.ObjectRef<HashMap<String, String>> objectRef, Continuation<? super C11351> continuation) {
            super(2, continuation);
            this.$params = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11351 c11351 = new C11351(this.$params, continuation);
            c11351.L$0 = obj;
            return c11351;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11351) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$getBookReview$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {41}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$getBookReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11371 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<CommentData>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11371(HashMap<String, String> map, Continuation<? super C11371> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11371 c11371 = new C11371(this.$params, continuation);
            c11371.L$0 = obj;
            return c11371;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<CommentData>> continuation) {
            return ((C11371) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.replyCommentFloorList(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$publishBookReview$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {99}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$publishBookReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11391 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Ref.ObjectRef<HashMap<String, String>> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11391(Ref.ObjectRef<HashMap<String, String>> objectRef, Continuation<? super C11391> continuation) {
            super(2, continuation);
            this.$params = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11391 c11391 = new C11391(this.$params, continuation);
            c11391.L$0 = obj;
            return c11391;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11391) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReplyCollectionViewModel$publishReview$1", f = "ReplyCollectionViewModel.kt", i = {}, l = {77}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReplyCollectionViewModel$publishReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11411 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11411(HashMap<String, String> map, Continuation<? super C11411> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11411 c11411 = new C11411(this.$params, continuation);
            c11411.L$0 = obj;
            return c11411;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11411) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    public ReplyCollectionViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.commentBookReview = new MutableLiveData<>();
        this.bookInfo = new MutableLiveData<>();
        this.publishReview = new MutableLiveData<>();
        this.publishBookReview = new MutableLiveData<>();
        this.supportAndOppose = new MutableLiveData<>();
        this.delComment = new MutableLiveData<>();
        this.commentReport = new MutableLiveData<>();
        this.accountBanned = new MutableLiveData<>();
        this.editComment = new MutableLiveData<>();
    }

    public static /* synthetic */ void bookInfo$default(ReplyCollectionViewModel replyCollectionViewModel, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "1";
        }
        replyCollectionViewModel.bookInfo(str);
    }

    public final void accountBanned(@NotNull String reasonId, @NotNull String day, @NotNull String userId) {
        Intrinsics.checkNotNullParameter(reasonId, "reasonId");
        Intrinsics.checkNotNullParameter(day, "day");
        Intrinsics.checkNotNullParameter(userId, "userId");
        RequestKt.executeRequest(this, new AnonymousClass1(MapsKt__MapsKt.hashMapOf(new Pair("days", day), new Pair("reason_id", reasonId), new Pair("target_user_id", userId), new Pair("type", "ebook")), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.accountBanned.2
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
                ReplyCollectionViewModel.this.getAccountBanned().setValue(it);
            }
        });
    }

    public final void addParagraphReview(final boolean isReply, @NotNull String bookId, @NotNull String chapterId, @NotNull String paragraphId, @NotNull String paragraphContent, @NotNull String commentId, @NotNull String pic, @NotNull String content, @Nullable final BookReview item, final int position) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(commentId, "commentId");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNull(item);
        RequestKt.executeRequest(this, new C11251(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("chapter_id", chapterId), new Pair("paragraph_id", paragraphId), new Pair("paragraph_content", paragraphContent), new Pair("parent_id", commentId), new Pair("picture", pic), new Pair(ClientCookie.COMMENT_ATTR, content), new Pair("review_type", item.getReviewType()), new Pair("reply_primary_id", item.getReplyPrimaryId())), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.addParagraphReview.2
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

    public final void addSupportOrOppse(@NotNull final BookReview item, final boolean isSupport, final int position) {
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C11271(isSupport, MapsKt__MapsKt.hashMapOf(new Pair("review_id", item.getId())), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.addSupportOrOppse.2
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
                item.setActionSupport(isSupport);
                item.setTmpPosition(position);
                this.getSupportAndOppose().setValue(item);
            }
        });
    }

    public final void bookInfo(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new C11291(map, null), new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.bookInfo.2
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
                ReplyCollectionViewModel.this.getBookInfo().setValue(it);
            }
        });
    }

    public final void commentReport(@NotNull String commentId, @NotNull String reasonId, @NotNull String reason) {
        Intrinsics.checkNotNullParameter(commentId, "commentId");
        Intrinsics.checkNotNullParameter(reasonId, "reasonId");
        Intrinsics.checkNotNullParameter(reason, "reason");
        RequestKt.executeRequest(this, new C11311(MapsKt__MapsKt.hashMapOf(new Pair("review_id", commentId), new Pair("reason_id", reasonId), new Pair("content", reason)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.commentReport.2
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
                ReplyCollectionViewModel.this.getCommentReport().setValue(it);
            }
        });
    }

    public final void delComment(final int position, @NotNull final BookReview item) {
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C11331(MapsKt__MapsKt.hashMapOf(new Pair("id", item.getId())), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.delComment.2
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
                this.getDelComment().setValue(item);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r11v4, types: [T, java.util.HashMap] */
    public final void editComment(@NotNull final String comment, @NotNull String id, @NotNull final String pic, final int position, @NotNull final BookReview item) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Intrinsics.checkNotNullParameter(item, "item");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = MapsKt__MapsKt.hashMapOf(new Pair("id", id), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("picture", pic));
        RequestKt.executeRequest(this, new C11351(objectRef, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.editComment.2
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
                item.setPicture(pic);
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

    public final void getBookReview(int page, int pageSize, @NotNull String bookId, @NotNull String parentId) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(parentId, "parentId");
        RequestKt.executeRequest(this, new C11371(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page)), new Pair("page_size", String.valueOf(pageSize)), new Pair("book_id", bookId), new Pair("parent_id", parentId)), null), new Function1<CommentData, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.getBookReview.2
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
                ReplyCollectionViewModel.this.getCommentBookReview().setValue(it);
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
    public final MutableLiveData<BookReview> getDelComment() {
        return this.delComment;
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

    /* JADX WARN: Type inference failed for: r7v6, types: [T, java.util.HashMap] */
    public final void publishBookReview(final boolean isReply, @NotNull String comment, @NotNull String bookId, @NotNull String pic, final int position, @Nullable final BookReview item) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Intrinsics.checkNotNull(item);
        objectRef.element = MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("parent_id", item.getId()), new Pair("picture", pic), new Pair("review_type", item.getReviewType()), new Pair("reply_primary_id", item.getReplyPrimaryId()));
        RequestKt.executeRequest(this, new C11391(objectRef, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.publishBookReview.2
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

    public final void publishReview(@NotNull String rate, @NotNull String bookId) {
        Intrinsics.checkNotNullParameter(rate, "rate");
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C11411(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId), new Pair("rate", rate)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReplyCollectionViewModel.publishReview.2
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
                ReplyCollectionViewModel.this.getPublishReview().setValue(it);
            }
        });
    }
}
