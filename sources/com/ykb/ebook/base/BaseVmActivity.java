package com.ykb.ebook.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewbinding.ViewBinding;
import com.ykb.ebook.base.BaseViewModel;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.LoadingDialog;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ToastUtilsKt;
import de.greenrobot.event.EventBus;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000f\u001a\u00028\u0001H$¢\u0006\u0002\u0010\u000bJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0014J\u0012\u0010\u0014\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0013H\u0014J\b\u0010\u0018\u001a\u00020\u0013H\u0004J\b\u0010\u0019\u001a\u00020\u0013H\u0004R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u00028\u0001X\u0086.¢\u0006\u0010\n\u0002\u0010\u000e\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u001a"}, d2 = {"Lcom/ykb/ebook/base/BaseVmActivity;", "VB", "Landroidx/viewbinding/ViewBinding;", "VM", "Lcom/ykb/ebook/base/BaseViewModel;", "Lcom/ykb/ebook/base/BaseActivity;", "()V", "requestDialog", "Lcom/ykb/ebook/dialog/BasicDialog;", "viewModel", "getViewModel", "()Lcom/ykb/ebook/base/BaseViewModel;", "setViewModel", "(Lcom/ykb/ebook/base/BaseViewModel;)V", "Lcom/ykb/ebook/base/BaseViewModel;", "initViewModel", "isShowLoading", "", "observeViewModel", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "startProgress", "stopProgress", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class BaseVmActivity<VB extends ViewBinding, VM extends BaseViewModel> extends BaseActivity<VB> {

    @Nullable
    private BasicDialog requestDialog;
    public VM viewModel;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(BaseVmActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().setToastReShow(false);
    }

    @NotNull
    public final VM getViewModel() {
        VM vm = this.viewModel;
        if (vm != null) {
            return vm;
        }
        Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        return null;
    }

    @NotNull
    public abstract VM initViewModel();

    public boolean isShowLoading() {
        return true;
    }

    public void observeViewModel() {
        MutableLiveData<Integer> loadEvent = getViewModel().getLoadEvent();
        final Function1<Integer, Unit> function1 = new Function1<Integer, Unit>(this) { // from class: com.ykb.ebook.base.BaseVmActivity.observeViewModel.1
            final /* synthetic */ BaseVmActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke2(num);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Integer num) {
                Log.INSTANCE.logD("loadEvent", String.valueOf(num));
                if (this.this$0.isShowLoading()) {
                    if (num != null && num.intValue() == 0) {
                        this.this$0.showLoading();
                    } else {
                        this.this$0.hideLoading();
                    }
                }
            }
        };
        loadEvent.observe(this, new Observer() { // from class: com.ykb.ebook.base.a
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseVmActivity.observeViewModel$lambda$1(function1, obj);
            }
        });
        MutableLiveData<String> errorEvent = getViewModel().getErrorEvent();
        final Function1<String, Unit> function12 = new Function1<String, Unit>(this) { // from class: com.ykb.ebook.base.BaseVmActivity.observeViewModel.2
            final /* synthetic */ BaseVmActivity<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String str) {
                if (!this.this$0.getViewModel().getToastReShow()) {
                    if (TextUtils.equals(str, "请登录") || TextUtils.equals(str, "user_id参数错误")) {
                        EventBus.getDefault().post("ebook_logout");
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setPackage(this.this$0.getPackageName());
                        intent.setData(Uri.parse("yikaobang.app://ykb_login/"));
                        this.this$0.startActivity(intent);
                        this.this$0.finish();
                        return;
                    }
                    ToastUtilsKt.toastOnUi$default(this.this$0, str, 0, 2, (Object) null);
                }
                this.this$0.hideLoading();
                Log.INSTANCE.logD("loadEvent", "error");
            }
        };
        errorEvent.observe(this, new Observer() { // from class: com.ykb.ebook.base.b
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseVmActivity.observeViewModel$lambda$2(function12, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(initViewModel());
        observeViewModel();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.ykb.ebook.base.c
            @Override // java.lang.Runnable
            public final void run() {
                BaseVmActivity.onCreate$lambda$0(this.f26287c);
            }
        }, 500L);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        BasicDialog basicDialog = this.requestDialog;
        if (basicDialog != null) {
            basicDialog.dismiss();
        }
        this.requestDialog = null;
        getViewModel().setToastReShow(true);
    }

    public final void setViewModel(@NotNull VM vm) {
        Intrinsics.checkNotNullParameter(vm, "<set-?>");
        this.viewModel = vm;
    }

    public final void startProgress() {
        if (this.requestDialog == null) {
            this.requestDialog = new LoadingDialog.Builder(this).create();
        }
        BasicDialog basicDialog = this.requestDialog;
        if (basicDialog != null) {
            basicDialog.show();
        }
    }

    public final void stopProgress() {
        BasicDialog basicDialog = this.requestDialog;
        if (basicDialog != null) {
            basicDialog.dismiss();
        }
    }
}
