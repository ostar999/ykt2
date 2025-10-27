package com.ykb.ebook.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.android.exoplayer2.util.MimeTypes;
import com.psychiatrygarden.utils.Constants;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.model.AllDrawLine;
import com.ykb.ebook.model.AllNotes;
import com.ykb.ebook.model.AllParagraphComment;
import com.ykb.ebook.model.Draw;
import com.ykb.ebook.model.Notes;
import com.ykb.ebook.model.ParagraphReviewList;
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

@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0011\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0012J\u0016\u0010\u0014\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0015J\u0016\u0010\u0017\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018J&\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001e2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018J0\u0010\u001a\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010\u001f\u001a\u00020\u00152\b\b\u0002\u0010'\u001a\u00020\u001eJ&\u0010\t\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020%2\u0006\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020%J0\u0010\r\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020%2\u0006\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020%2\b\b\u0002\u0010,\u001a\u00020\u001eJ0\u0010\u0010\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020%2\u0006\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020%2\b\b\u0002\u0010,\u001a\u00020\u001eR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\nR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\nR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\nR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\n¨\u0006-"}, d2 = {"Lcom/ykb/ebook/vm/AllTagViewModel;", "Lcom/ykb/ebook/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "allDrawLine", "Landroidx/lifecycle/MutableLiveData;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/AllDrawLine;", "getAllDrawLine", "()Landroidx/lifecycle/MutableLiveData;", "allNotes", "Lcom/ykb/ebook/model/AllNotes;", "getAllNotes", "allParagraphComment", "Lcom/ykb/ebook/model/AllParagraphComment;", "getAllParagraphComment", "delLine", "Lcom/ykb/ebook/model/Draw;", "getDelLine", "delNote", "Lcom/ykb/ebook/model/Notes;", "getDelNote", "delReviewComment", "Lcom/ykb/ebook/model/ParagraphReviewList;", "getDelReviewComment", "editNote", "getEditNote", "", "id", "", "item", "editComment", ClientCookie.COMMENT_ATTR, "pic", "content", "position", "", "parentPos", "display_status", "bookId", "isReverse", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "keywords", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class AllTagViewModel extends BaseViewModel {

    @NotNull
    private final MutableLiveData<BaseListResponse<AllDrawLine>> allDrawLine;

    @NotNull
    private final MutableLiveData<BaseListResponse<AllNotes>> allNotes;

    @NotNull
    private final MutableLiveData<BaseListResponse<AllParagraphComment>> allParagraphComment;

    @NotNull
    private final MutableLiveData<Draw> delLine;

    @NotNull
    private final MutableLiveData<Notes> delNote;

    @NotNull
    private final MutableLiveData<ParagraphReviewList> delReviewComment;

    @NotNull
    private final MutableLiveData<Notes> editNote;

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$delLine$1", f = "AllTagViewModel.kt", i = {}, l = {91}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$delLine$1, reason: invalid class name */
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
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$delNote$1", f = "AllTagViewModel.kt", i = {}, l = {81}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$delNote$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10591 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10591(HashMap<String, String> map, Continuation<? super C10591> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10591 c10591 = new C10591(this.$params, continuation);
            c10591.L$0 = obj;
            return c10591;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10591) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.delNote(map, this);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$delReviewComment$1", f = "AllTagViewModel.kt", i = {}, l = {71}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$delReviewComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10611 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10611(HashMap<String, String> map, Continuation<? super C10611> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10611 c10611 = new C10611(this.$params, continuation);
            c10611.L$0 = obj;
            return c10611;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10611) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$editComment$1", f = "AllTagViewModel.kt", i = {}, l = {125}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$editComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10631 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10631(HashMap<String, String> map, Continuation<? super C10631> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10631 c10631 = new C10631(this.$params, continuation);
            c10631.L$0 = obj;
            return c10631;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10631) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$editNote$1", f = "AllTagViewModel.kt", i = {}, l = {104}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$editNote$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10651 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<Object>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10651(HashMap<String, String> map, Continuation<? super C10651> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10651 c10651 = new C10651(this.$params, continuation);
            c10651.L$0 = obj;
            return c10651;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<Object>> continuation) {
            return ((C10651) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.editNote(map, this);
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

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/AllDrawLine;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$getAllDrawLine$1", f = "AllTagViewModel.kt", i = {}, l = {35}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$getAllDrawLine$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10671 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BaseListResponse<AllDrawLine>>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10671(HashMap<String, String> map, Continuation<? super C10671> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10671 c10671 = new C10671(this.$params, continuation);
            c10671.L$0 = obj;
            return c10671;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BaseListResponse<AllDrawLine>>> continuation) {
            return ((C10671) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.allDrawLine(map, this);
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

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/AllNotes;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$getAllNotes$1", f = "AllTagViewModel.kt", i = {}, l = {48}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$getAllNotes$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10691 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BaseListResponse<AllNotes>>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10691(HashMap<String, String> map, Continuation<? super C10691> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10691 c10691 = new C10691(this.$params, continuation);
            c10691.L$0 = obj;
            return c10691;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BaseListResponse<AllNotes>>> continuation) {
            return ((C10691) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.allNotes(map, this);
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

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/AllParagraphComment;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.vm.AllTagViewModel$getAllParagraphComment$1", f = "AllTagViewModel.kt", i = {}, l = {61}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.vm.AllTagViewModel$getAllParagraphComment$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10711 extends SuspendLambda implements Function2<ApiService, Continuation<? super BaseResponse<BaseListResponse<AllParagraphComment>>>, Object> {
        final /* synthetic */ HashMap<String, String> $params;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10711(HashMap<String, String> map, Continuation<? super C10711> continuation) {
            super(2, continuation);
            this.$params = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10711 c10711 = new C10711(this.$params, continuation);
            c10711.L$0 = obj;
            return c10711;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull ApiService apiService, @Nullable Continuation<? super BaseResponse<BaseListResponse<AllParagraphComment>>> continuation) {
            return ((C10711) create(apiService, continuation)).invokeSuspend(Unit.INSTANCE);
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
                obj = apiService.allParagraphComment(map, this);
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
    public AllTagViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.allDrawLine = new MutableLiveData<>();
        this.allNotes = new MutableLiveData<>();
        this.allParagraphComment = new MutableLiveData<>();
        this.delReviewComment = new MutableLiveData<>();
        this.delLine = new MutableLiveData<>();
        this.delNote = new MutableLiveData<>();
        this.editNote = new MutableLiveData<>();
    }

    public static /* synthetic */ void editNote$default(AllTagViewModel allTagViewModel, String str, int i2, int i3, Notes notes, String str2, int i4, Object obj) {
        if ((i4 & 16) != 0) {
            str2 = "";
        }
        allTagViewModel.editNote(str, i2, i3, notes, str2);
    }

    public static /* synthetic */ void getAllNotes$default(AllTagViewModel allTagViewModel, String str, int i2, int i3, int i4, String str2, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            str2 = "";
        }
        allTagViewModel.getAllNotes(str, i2, i3, i4, str2);
    }

    public static /* synthetic */ void getAllParagraphComment$default(AllTagViewModel allTagViewModel, String str, int i2, int i3, int i4, String str2, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            str2 = "";
        }
        allTagViewModel.getAllParagraphComment(str, i2, i3, i4, str2);
    }

    public final void delLine(@NotNull String id, @NotNull final Draw item) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new AnonymousClass1(MapsKt__MapsKt.hashMapOf(new Pair("id", id)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.delLine.2
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
                AllTagViewModel.this.getDelLine().setValue(item);
            }
        });
    }

    public final void delNote(@NotNull String id, @NotNull final Notes item) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C10591(MapsKt__MapsKt.hashMapOf(new Pair("id", id)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.delNote.2
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
                AllTagViewModel.this.getDelNote().setValue(item);
            }
        });
    }

    public final void delReviewComment(@NotNull String id, @NotNull final ParagraphReviewList item) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C10611(MapsKt__MapsKt.hashMapOf(new Pair("id", id)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.delReviewComment.2
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
                AllTagViewModel.this.getDelReviewComment().setValue(item);
            }
        });
    }

    public final void editComment(@NotNull String comment, @NotNull String id, @NotNull String pic, @NotNull ParagraphReviewList item) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(pic, "pic");
        Intrinsics.checkNotNullParameter(item, "item");
        RequestKt.executeRequest(this, new C10631(MapsKt__MapsKt.hashMapOf(new Pair("id", id), new Pair(ClientCookie.COMMENT_ATTR, comment), new Pair("picture", pic)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.editComment.2
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

    public final void editNote(@NotNull final String content, final int position, final int parentPos, @NotNull final Notes item, @NotNull String display_status) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(display_status, "display_status");
        RequestKt.executeRequest(this, new C10651(MapsKt__MapsKt.hashMapOf(new Pair("id", item.getId()), new Pair("notes_content", content), new Pair("display_status", display_status)), null), new Function1<Object, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.editNote.2
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
                item.setNotesContent(content);
                item.setEditPos(position);
                item.setParentPos(parentPos);
                this.getEditNote().setValue(item);
            }
        });
    }

    @NotNull
    public final MutableLiveData<BaseListResponse<AllDrawLine>> getAllDrawLine() {
        return this.allDrawLine;
    }

    @NotNull
    public final MutableLiveData<BaseListResponse<AllNotes>> getAllNotes() {
        return this.allNotes;
    }

    @NotNull
    public final MutableLiveData<BaseListResponse<AllParagraphComment>> getAllParagraphComment() {
        return this.allParagraphComment;
    }

    @NotNull
    public final MutableLiveData<Draw> getDelLine() {
        return this.delLine;
    }

    @NotNull
    public final MutableLiveData<Notes> getDelNote() {
        return this.delNote;
    }

    @NotNull
    public final MutableLiveData<ParagraphReviewList> getDelReviewComment() {
        return this.delReviewComment;
    }

    @NotNull
    public final MutableLiveData<Notes> getEditNote() {
        return this.editNote;
    }

    public final void getAllDrawLine(@NotNull String bookId, int isReverse, int page, int pageSize) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        RequestKt.executeRequest(this, new C10671(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page)), new Pair("page_size", String.valueOf(pageSize)), new Pair("book_id", bookId), new Pair("is_reverse", String.valueOf(isReverse))), null), new Function1<BaseListResponse<AllDrawLine>, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.getAllDrawLine.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseListResponse<AllDrawLine> baseListResponse) {
                invoke2(baseListResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull BaseListResponse<AllDrawLine> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                AllTagViewModel.this.getAllDrawLine().setValue(it);
            }
        });
    }

    public final void getAllNotes(@NotNull String bookId, int isReverse, int page, int pageSize, @NotNull String keywords) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(keywords, "keywords");
        RequestKt.executeRequest(this, new C10691(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page)), new Pair("page_size", String.valueOf(pageSize)), new Pair("book_id", bookId), new Pair("is_reverse", String.valueOf(isReverse)), new Pair(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, String.valueOf(keywords))), null), new Function1<BaseListResponse<AllNotes>, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.getAllNotes.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseListResponse<AllNotes> baseListResponse) {
                invoke2(baseListResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull BaseListResponse<AllNotes> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                AllTagViewModel.this.getAllNotes().setValue(it);
            }
        });
    }

    public final void getAllParagraphComment(@NotNull String bookId, int isReverse, int page, int pageSize, @NotNull String keywords) {
        Intrinsics.checkNotNullParameter(bookId, "bookId");
        Intrinsics.checkNotNullParameter(keywords, "keywords");
        RequestKt.executeRequest(this, new C10711(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page)), new Pair("page_size", String.valueOf(pageSize)), new Pair("book_id", bookId), new Pair("is_reverse", String.valueOf(isReverse)), new Pair(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, String.valueOf(keywords))), null), new Function1<BaseListResponse<AllParagraphComment>, Unit>() { // from class: com.ykb.ebook.vm.AllTagViewModel.getAllParagraphComment.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseListResponse<AllParagraphComment> baseListResponse) {
                invoke2(baseListResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull BaseListResponse<AllParagraphComment> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                AllTagViewModel.this.getAllParagraphComment().setValue(it);
            }
        });
    }
}
