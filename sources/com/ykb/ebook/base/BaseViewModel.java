package com.ykb.ebook.base;

import android.app.Application;
import android.content.Context;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.google.android.exoplayer2.util.MimeTypes;
import com.tencent.connect.common.Constants;
import com.ykb.ebook.App;
import com.ykb.ebook.util.Coroutine;
import java.io.IOException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004Jk\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001a\"\u0004\b\u0000\u0010\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u0005\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010!\u001a\u00020\u001e2'\u0010\"\u001a#\b\u0001\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0$\u0012\u0006\u0012\u0004\u0018\u00010%0#¢\u0006\u0002\b&ø\u0001\u0000¢\u0006\u0002\u0010'R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006("}, d2 = {"Lcom/ykb/ebook/base/BaseViewModel;", "Landroidx/lifecycle/AndroidViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "context$delegate", "Lkotlin/Lazy;", "errorEvent", "Landroidx/lifecycle/MutableLiveData;", "", "getErrorEvent", "()Landroidx/lifecycle/MutableLiveData;", "loadEvent", "", "getLoadEvent", "toastReShow", "", "getToastReShow", "()Z", "setToastReShow", "(Z)V", "execute", "Lcom/ykb/ebook/util/Coroutine;", ExifInterface.GPS_DIRECTION_TRUE, Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/CoroutineContext;", "start", "Lkotlinx/coroutines/CoroutineStart;", "executeContext", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lcom/ykb/ebook/util/Coroutine;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public class BaseViewModel extends AndroidViewModel {

    /* renamed from: context$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy context;

    @NotNull
    private final MutableLiveData<String> errorEvent;

    @NotNull
    private final MutableLiveData<Integer> loadEvent;
    private boolean toastReShow;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.context = LazyKt__LazyJVMKt.lazy(new Function0<App>() { // from class: com.ykb.ebook.base.BaseViewModel$context$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final App invoke() {
                return (App) this.this$0.getApplication();
            }
        });
        this.loadEvent = new MutableLiveData<>();
        this.errorEvent = new MutableLiveData<>();
    }

    public static /* synthetic */ Coroutine execute$default(BaseViewModel baseViewModel, CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, CoroutineContext coroutineContext2, Function2 function2, int i2, Object obj) throws IOException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: execute");
        }
        if ((i2 & 1) != 0) {
            coroutineScope = ViewModelKt.getViewModelScope(baseViewModel);
        }
        CoroutineScope coroutineScope2 = coroutineScope;
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getIO();
        }
        CoroutineContext coroutineContext3 = coroutineContext;
        if ((i2 & 4) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        CoroutineStart coroutineStart2 = coroutineStart;
        if ((i2 & 8) != 0) {
            coroutineContext2 = Dispatchers.getMain();
        }
        return baseViewModel.execute(coroutineScope2, coroutineContext3, coroutineStart2, coroutineContext2, function2);
    }

    @NotNull
    public final <T> Coroutine<T> execute(@NotNull CoroutineScope scope, @NotNull CoroutineContext context, @NotNull CoroutineStart start, @NotNull CoroutineContext executeContext, @NotNull Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(executeContext, "executeContext");
        Intrinsics.checkNotNullParameter(block, "block");
        return Coroutine.INSTANCE.async(scope, context, start, executeContext, block);
    }

    @NotNull
    public final Context getContext() {
        Object value = this.context.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-context>(...)");
        return (Context) value;
    }

    @NotNull
    public final MutableLiveData<String> getErrorEvent() {
        return this.errorEvent;
    }

    @NotNull
    public final MutableLiveData<Integer> getLoadEvent() {
        return this.loadEvent;
    }

    public final boolean getToastReShow() {
        return this.toastReShow;
    }

    public final void setToastReShow(boolean z2) {
        this.toastReShow = z2;
    }
}
