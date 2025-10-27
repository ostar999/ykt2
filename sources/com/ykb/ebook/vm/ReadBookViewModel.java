package com.ykb.ebook.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.android.exoplayer2.util.MimeTypes;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.psychiatrygarden.utils.Constants;
import com.umeng.socialize.tracker.a;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.common.ConstantKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common.UrlConfigKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.ParagraphComment;
import com.ykb.ebook.model.PermissionInfo;
import com.ykb.ebook.model.QuestionListData;
import com.ykb.ebook.network.RequestKt;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010)\u001a\u00020*2\b\b\u0002\u0010+\u001a\u00020,J\u001a\u0010\n\u001a\u00020*2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020,0.J\u001a\u0010\f\u001a\u00020*2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020,0.J\u001a\u0010\u000e\u001a\u00020*2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020,0.J\u0010\u0010/\u001a\u00020*2\b\b\u0002\u00100\u001a\u00020,J\u001a\u0010\u0017\u001a\u00020*2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020,0.J\u001a\u0010\u0019\u001a\u00020*2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020,0.J&\u0010%\u001a\u00020*2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u00020,2\u0006\u00105\u001a\u00020,J\u000e\u00106\u001a\u00020*2\u0006\u00107\u001a\u00020,J\u000e\u00108\u001a\u00020*2\u0006\u00109\u001a\u00020,J\u0018\u0010:\u001a\u00020*2\u0006\u00107\u001a\u00020,2\b\u0010;\u001a\u0004\u0018\u00010,J \u0010<\u001a\u00020*2\u0006\u00107\u001a\u00020,2\u0006\u00104\u001a\u00020,2\b\b\u0002\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020*2\u0006\u0010+\u001a\u00020,J\u000e\u0010@\u001a\u00020*2\u0006\u0010+\u001a\u00020,R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\tR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\tR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010 \"\u0004\b!\u0010\"R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0006¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\tR\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u0006¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\t¨\u0006A"}, d2 = {"Lcom/ykb/ebook/vm/ReadBookViewModel;", "Lcom/ykb/ebook/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "addBookData", "Landroidx/lifecycle/MutableLiveData;", "", "getAddBookData", "()Landroidx/lifecycle/MutableLiveData;", "addCorrect", "getAddCorrect", "addDrawLine", "getAddDrawLine", "addNote", "getAddNote", "addRecord", "getAddRecord", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "getBookInfo", "delBookData", "getDelBookData", "deleteDrawLine", "getDeleteDrawLine", "editDrawLine", "getEditDrawLine", "error", "", "getError", "isInitFinish", "", "()Z", "setInitFinish", "(Z)V", "paragraphReview", "Lcom/ykb/ebook/model/ParagraphComment;", "getParagraphReview", "questionList", "Lcom/ykb/ebook/model/QuestionListData;", "getQuestionList", "addBook", "", "id", "", "params", "", "delBook", "ids", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "chapterId", "paragraphId", "getPayWays", "bookId", "getQuestList", "question_id", a.f23806c, "userId", "saveReadProgress", "duration", "", "uploadDownloadCount", "uploadShareCount", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ReadBookViewModel extends BaseViewModel {

    @NotNull
    private final MutableLiveData<Object> addBookData;

    @NotNull
    private final MutableLiveData<Object> addCorrect;

    @NotNull
    private final MutableLiveData<Object> addDrawLine;

    @NotNull
    private final MutableLiveData<Object> addNote;

    @NotNull
    private final MutableLiveData<Object> addRecord;

    @NotNull
    private final MutableLiveData<BookInfo> bookInfo;

    @NotNull
    private final MutableLiveData<Object> delBookData;

    @NotNull
    private final MutableLiveData<Object> deleteDrawLine;

    @NotNull
    private final MutableLiveData<Object> editDrawLine;

    @NotNull
    private final MutableLiveData<Throwable> error;
    private boolean isInitFinish;

    @NotNull
    private final MutableLiveData<ParagraphComment> paragraphReview;

    @NotNull
    private final MutableLiveData<QuestionListData> questionList;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$addBook$1", f = "ReadBookViewModel.kt", i = {}, l = {181}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$addBook$1, reason: invalid class name */
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$addCorrect$1", f = "ReadBookViewModel.kt", i = {}, l = {126}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$addCorrect$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10951 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Map<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10951(Map<String, String> map, Continuation<? super C10951> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10951 c10951 = new C10951(this.$params, continuation);
            c10951.L$0 = obj;
            return c10951;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10951) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                Map<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.addCorrect(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$addDrawLine$1", f = "ReadBookViewModel.kt", i = {}, l = {105}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$addDrawLine$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10971 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Map<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10971(Map<String, String> map, Continuation<? super C10971> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10971 c10971 = new C10971(this.$params, continuation);
            c10971.L$0 = obj;
            return c10971;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10971) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                Map<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.addDrawLine(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$addNote$1", f = "ReadBookViewModel.kt", i = {}, l = {119}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$addNote$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10991 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Map<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10991(Map<String, String> map, Continuation<? super C10991> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10991 c10991 = new C10991(this.$params, continuation);
            c10991.L$0 = obj;
            return c10991;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10991) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                Map<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.addNote(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$delBook$1", f = "ReadBookViewModel.kt", i = {}, l = {189}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$delBook$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11011 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11011(HashMap<String, String> map, Continuation<? super C11011> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11011 c11011 = new C11011(this.$paramMap, continuation);
            c11011.L$0 = obj;
            return c11011;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11011) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$deleteDrawLine$1", f = "ReadBookViewModel.kt", i = {}, l = {112}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$deleteDrawLine$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11031 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Map<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11031(Map<String, String> map, Continuation<? super C11031> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11031 c11031 = new C11031(this.$params, continuation);
            c11031.L$0 = obj;
            return c11031;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11031) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                Map<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.delLine(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$editDrawLine$1", f = "ReadBookViewModel.kt", i = {}, l = {98}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$editDrawLine$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11051 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ Map<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11051(Map<String, String> map, Continuation<? super C11051> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11051 c11051 = new C11051(this.$params, continuation);
            c11051.L$0 = obj;
            return c11051;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11051) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                Map<String, String> map = this.$params;
                this.label = 1;
                obj = apiService.editDrawLine(map, this);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/ParagraphComment;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$getParagraphReview$1", f = "ReadBookViewModel.kt", i = {}, l = {87}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$getParagraphReview$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11071 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<ParagraphComment>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11071(HashMap<String, String> map, Continuation<? super C11071> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11071 c11071 = new C11071(this.$params, continuation);
            c11071.L$0 = obj;
            return c11071;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<ParagraphComment>> continuation) {
            return ((C11071) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.paragraphReviewList(map, this);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/PermissionInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$getPayWays$1", f = "ReadBookViewModel.kt", i = {}, l = {136}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$getPayWays$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11091 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<PermissionInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11091(HashMap<String, String> map, Continuation<? super C11091> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11091 c11091 = new C11091(this.$params, continuation);
            c11091.L$0 = obj;
            return c11091;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<PermissionInfo>> continuation) {
            return ((C11091) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.permissionMethod(map, this);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/QuestionListData;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$getQuestList$1", f = "ReadBookViewModel.kt", i = {}, l = {R2.anim.window_bottom_out}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$getQuestList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11111 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<QuestionListData>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11111(HashMap<String, String> map, Continuation<? super C11111> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11111 c11111 = new C11111(this.$params, continuation);
            c11111.L$0 = obj;
            return c11111;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<QuestionListData>> continuation) {
            return ((C11111) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.getQuestionList(map, this);
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

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$getQuestList$3", f = "ReadBookViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$getQuestList$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass3 anonymousClass3 = ReadBookViewModel.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = th;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBookViewModel.this.getError().setValue((Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/BookInfo;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$initData$1", f = "ReadBookViewModel.kt", i = {}, l = {46}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$initData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11131 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<BookInfo>>, Object> {
        final /* synthetic */ HashMap<String, String> $bookParams;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11131(HashMap<String, String> map, Continuation<? super C11131> continuation) {
            super(2, continuation);
            this.$bookParams = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C11131(this.$bookParams, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<BookInfo>> continuation) {
            return ((C11131) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService api = ApiServiceKt.getAPI();
                HashMap<String, String> map = this.$bookParams;
                this.label = 1;
                obj = api.bookInfo(map, this);
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

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/BookInfo;", "book", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$initData$2", f = "ReadBookViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nReadBookViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadBookViewModel.kt\ncom/ykb/ebook/vm/ReadBookViewModel$initData$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,211:1\n1864#2,3:212\n*S KotlinDebug\n*F\n+ 1 ReadBookViewModel.kt\ncom/ykb/ebook/vm/ReadBookViewModel$initData$2\n*L\n50#1:212,3\n*E\n"})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$initData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C11142 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<BookInfo>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $userId;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11142(String str, Continuation<? super C11142> continuation) {
            super(3, continuation);
            this.$userId = str;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<BookInfo> baseResponse, @Nullable Continuation<? super Unit> continuation) {
            C11142 c11142 = ReadBookViewModel.this.new C11142(this.$userId, continuation);
            c11142.L$0 = baseResponse;
            return c11142.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BookInfo bookInfo = (BookInfo) ((BaseResponse) this.L$0).getData();
            if (bookInfo != null) {
                ReadBookViewModel readBookViewModel = ReadBookViewModel.this;
                String str = this.$userId;
                int i2 = 0;
                for (Object obj2 : bookInfo.getChapterList()) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    Chapter chapter = (Chapter) obj2;
                    chapter.setIndex(i2);
                    if (Intrinsics.areEqual("1", bookInfo.getPass())) {
                        chapter.setPay(true);
                    } else if (bookInfo.getFreeChapterCount() > 0 && i3 <= bookInfo.getFreeChapterCount()) {
                        chapter.setPay(true);
                    }
                    i2 = i3;
                }
                readBookViewModel.setInitFinish(true);
                ReadConfig.INSTANCE.setBookIsUnlock(Intrinsics.areEqual("1", bookInfo.getPass()));
                ReadBook readBook = ReadBook.INSTANCE;
                if (str == null) {
                    str = "1";
                }
                readBook.setUserId(str);
                ReadBook.upData$default(readBook, bookInfo, false, 2, null);
                readBook.setChapterSize(bookInfo.getChapterList().size());
                readBookViewModel.getBookInfo().setValue(bookInfo);
                readBook.upMsg(null);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$initData$3", f = "ReadBookViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$initData$3, reason: invalid class name and case insensitive filesystem */
    public static final class C11153 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        int label;

        public C11153(Continuation<? super C11153> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return ReadBookViewModel.this.new C11153(continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBookViewModel.this.setInitFinish(true);
            ReadBook.INSTANCE.upMsg("书籍加载失败！");
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$saveReadProgress$1", f = "ReadBookViewModel.kt", i = {}, l = {155}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$saveReadProgress$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11161 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ String $bookId;
        final /* synthetic */ String $chapterId;
        final /* synthetic */ long $duration;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11161(long j2, String str, String str2, Continuation<? super C11161> continuation) {
            super(2, continuation);
            this.$duration = j2;
            this.$bookId = str;
            this.$chapterId = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11161 c11161 = new C11161(this.$duration, this.$bookId, this.$chapterId, continuation);
            c11161.L$0 = obj;
            return c11161;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11161) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                HashMap mapHashMapOf = MapsKt__MapsKt.hashMapOf(TuplesKt.to("duration", String.valueOf(this.$duration)), TuplesKt.to("book_id", this.$bookId), TuplesKt.to("chapter_id", this.$chapterId));
                this.label = 1;
                obj = apiService.saveReadProgress(mapHashMapOf, this);
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

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$saveReadProgress$3", f = "ReadBookViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$saveReadProgress$3, reason: invalid class name and case insensitive filesystem */
    public static final class C11183 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public C11183(Continuation<? super C11183> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C11183 c11183 = new C11183(continuation);
            c11183.L$0 = th;
            return c11183.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Throwable th = (Throwable) this.L$0;
            Log log = Log.INSTANCE;
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            log.logE("error", message);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$uploadDownloadCount$1", f = "ReadBookViewModel.kt", i = {}, l = {198}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$uploadDownloadCount$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11191 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11191(HashMap<String, String> map, Continuation<? super C11191> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11191 c11191 = new C11191(this.$paramMap, continuation);
            c11191.L$0 = obj;
            return c11191;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11191) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.uploadEbookDownloadCount(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.ReadBookViewModel$uploadShareCount$1", f = "ReadBookViewModel.kt", i = {}, l = {206}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.ReadBookViewModel$uploadShareCount$1, reason: invalid class name and case insensitive filesystem */
    public static final class C11211 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $paramMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11211(HashMap<String, String> map, Continuation<? super C11211> continuation) {
            super(2, continuation);
            this.$paramMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C11211 c11211 = new C11211(this.$paramMap, continuation);
            c11211.L$0 = obj;
            return c11211;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C11211) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService apiService = (ApiService) this.L$0;
                String str = ConstantKt.getBOOK_MANAGE_URL() + UrlConfigKt.EBOOK_SHARE_COUNT;
                HashMap<String, String> map = this.$paramMap;
                this.label = 1;
                obj = apiService.uploadEbookShareCount(str, map, this);
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
    public ReadBookViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.bookInfo = new MutableLiveData<>();
        this.paragraphReview = new MutableLiveData<>();
        this.addDrawLine = new MutableLiveData<>();
        this.editDrawLine = new MutableLiveData<>();
        this.deleteDrawLine = new MutableLiveData<>();
        this.addNote = new MutableLiveData<>();
        this.addCorrect = new MutableLiveData<>();
        this.addRecord = new MutableLiveData<>();
        this.questionList = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.addBookData = new MutableLiveData<>();
        this.delBookData = new MutableLiveData<>();
    }

    public static /* synthetic */ void addBook$default(ReadBookViewModel readBookViewModel, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "1";
        }
        readBookViewModel.addBook(str);
    }

    public static /* synthetic */ void delBook$default(ReadBookViewModel readBookViewModel, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "1";
        }
        readBookViewModel.delBook(str);
    }

    public static /* synthetic */ void saveReadProgress$default(ReadBookViewModel readBookViewModel, String str, String str2, long j2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            j2 = 0;
        }
        readBookViewModel.saveReadProgress(str, str2, j2);
    }

    public final void addBook(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new AnonymousClass1(map, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.addBook.2
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
                ReadBookViewModel.this.getAddBookData().setValue(it);
            }
        });
    }

    public final void addCorrect(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        RequestKt.executeRequest(this, new C10951(params, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.addCorrect.2
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
                ReadBookViewModel.this.getAddCorrect().setValue(it);
            }
        });
    }

    public final void addDrawLine(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        RequestKt.executeRequest(this, new C10971(params, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.addDrawLine.2
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
                ReadBookViewModel.this.getAddDrawLine().setValue(it);
            }
        });
    }

    public final void addNote(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        RequestKt.executeRequest(this, new C10991(params, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.addNote.2
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
                ReadBookViewModel.this.getAddNote().setValue(it);
            }
        });
    }

    public final void delBook(@NotNull String ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        HashMap map = new HashMap();
        map.put("ids", ids);
        RequestKt.executeRequest(this, new C11011(map, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.delBook.2
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
                ReadBookViewModel.this.getDelBookData().setValue(it);
            }
        });
    }

    public final void deleteDrawLine(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        RequestKt.executeRequest(this, new C11031(params, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.deleteDrawLine.2
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
                ReadBookViewModel.this.getDeleteDrawLine().setValue(it);
            }
        });
    }

    public final void editDrawLine(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        RequestKt.executeRequest(this, new C11051(params, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.editDrawLine.2
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
                ReadBookViewModel.this.getEditDrawLine().setValue(it);
            }
        });
    }

    @NotNull
    public final MutableLiveData<Object> getAddBookData() {
        return this.addBookData;
    }

    @NotNull
    public final MutableLiveData<Object> getAddCorrect() {
        return this.addCorrect;
    }

    @NotNull
    public final MutableLiveData<Object> getAddDrawLine() {
        return this.addDrawLine;
    }

    @NotNull
    public final MutableLiveData<Object> getAddNote() {
        return this.addNote;
    }

    @NotNull
    public final MutableLiveData<Object> getAddRecord() {
        return this.addRecord;
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
    public final MutableLiveData<Object> getDeleteDrawLine() {
        return this.deleteDrawLine;
    }

    @NotNull
    public final MutableLiveData<Object> getEditDrawLine() {
        return this.editDrawLine;
    }

    @NotNull
    public final MutableLiveData<Throwable> getError() {
        return this.error;
    }

    @NotNull
    public final MutableLiveData<ParagraphComment> getParagraphReview() {
        return this.paragraphReview;
    }

    public final void getPayWays(@NotNull String bookId) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C11091(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId)), null), new Function1<PermissionInfo, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.getPayWays.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PermissionInfo permissionInfo) {
                invoke2(permissionInfo);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull PermissionInfo it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.getWays() != null) {
                    ReadBook.INSTANCE.setPayWays(it.getWays());
                } else {
                    ReadBook.INSTANCE.setPayWays(new ArrayList());
                }
            }
        });
    }

    public final void getQuestList(@NotNull String question_id) {
        Intrinsics.checkNotNullParameter(question_id, "question_id");
        Coroutine.onError$default(RequestKt.executeRequest(this, new C11111(MapsKt__MapsKt.hashMapOf(new Pair("question_id", question_id)), null), new Function1<QuestionListData, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.getQuestList.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(QuestionListData questionListData) {
                invoke2(questionListData);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull QuestionListData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ReadBookViewModel.this.getQuestionList().setValue(it);
            }
        }), null, new AnonymousClass3(null), 1, null);
    }

    @NotNull
    public final MutableLiveData<QuestionListData> getQuestionList() {
        return this.questionList;
    }

    public final void initData(@NotNull String bookId, @Nullable String userId) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Coroutine.onError$default(Coroutine.onSuccess$default(BaseViewModel.execute$default(this, null, null, null, null, new C11131(MapsKt__MapsKt.hashMapOf(new Pair("book_id", bookId)), null), 15, null), null, new C11142(userId, null), 1, null), null, new C11153(null), 1, null);
    }

    /* renamed from: isInitFinish, reason: from getter */
    public final boolean getIsInitFinish() {
        return this.isInitFinish;
    }

    public final void saveReadProgress(@NotNull String bookId, @NotNull String chapterId, long duration) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Coroutine.onError$default(RequestKt.executeRequest(this, new C11161(duration, bookId, chapterId, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.saveReadProgress.2
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
                ReadBookViewModel.this.getAddRecord().setValue(it);
            }
        }), null, new C11183(null), 1, null);
    }

    public final void setInitFinish(boolean z2) {
        this.isInitFinish = z2;
    }

    public final void uploadDownloadCount(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new C11191(map, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.uploadDownloadCount.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke2(obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Object it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        });
    }

    public final void uploadShareCount(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        HashMap map = new HashMap();
        map.put("book_id", id);
        RequestKt.executeRequest(this, new C11211(map, null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.uploadShareCount.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke2(obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Object it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        });
    }

    public final void getParagraphReview(int page, int pageSize, @NotNull String chapterId, @NotNull String paragraphId) {
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
        RequestKt.executeRequest(this, new C11071(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page)), new Pair("page_size", String.valueOf(pageSize)), new Pair("chapter_id", chapterId), new Pair("paragraph_id", paragraphId), new Pair("parent_id", "0")), null), new Function1<ParagraphComment, Unit>() { // from class: com.ykb.ebook.vm.ReadBookViewModel.getParagraphReview.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ParagraphComment paragraphComment) {
                invoke2(paragraphComment);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ParagraphComment it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ReadBookViewModel.this.getParagraphReview().setValue(it);
            }
        });
    }
}
