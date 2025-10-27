package com.azhon.appupdate.view;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.azhon.appupdate.config.Constant;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.listener.OnDownloadListenerAdapter;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.service.DownloadService;
import com.azhon.appupdate.util.ApkUtil;
import com.azhon.appupdate.util.DensityUtil;
import com.azhon.appupdate.util.LogUtil;
import com.catchpig.mvvm.R;
import com.plv.socket.user.PLVSocketUserConstant;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001e2\u00020\u00012\u00020\u0002:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010\u0019\u001a\u00020\u00122\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0012H\u0014J\b\u0010\u001d\u001a\u00020\u0012H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/azhon/appupdate/view/UpdateDialogActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/view/View$OnClickListener;", "()V", "apk", "Ljava/io/File;", "btnUpdate", "Landroid/widget/Button;", "error", "", "install", "listenerAdapter", "Lcom/azhon/appupdate/listener/OnDownloadListenerAdapter;", PLVSocketUserConstant.USERTYPE_MANAGER, "Lcom/azhon/appupdate/manager/DownloadManager;", "progressBar", "Lcom/azhon/appupdate/view/NumberProgressBar;", "finish", "", "init", "initView", "onBackPressed", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setWindowSize", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UpdateDialogActivity extends AppCompatActivity implements View.OnClickListener {

    @NotNull
    private static final String TAG = "UpdateDialogActivity";
    private File apk;
    private Button btnUpdate;

    @Nullable
    private DownloadManager manager;
    private NumberProgressBar progressBar;
    private final int install = 69;
    private final int error = 70;

    @NotNull
    private final OnDownloadListenerAdapter listenerAdapter = new OnDownloadListenerAdapter() { // from class: com.azhon.appupdate.view.UpdateDialogActivity$listenerAdapter$1
        @Override // com.azhon.appupdate.listener.OnDownloadListenerAdapter, com.azhon.appupdate.listener.OnDownloadListener
        public void done(@NotNull File apk) {
            Intrinsics.checkNotNullParameter(apk, "apk");
            this.this$0.apk = apk;
            Button button = this.this$0.btnUpdate;
            Button button2 = null;
            if (button == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button = null;
            }
            button.setTag(Integer.valueOf(this.this$0.install));
            Button button3 = this.this$0.btnUpdate;
            if (button3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button3 = null;
            }
            button3.setEnabled(true);
            Button button4 = this.this$0.btnUpdate;
            if (button4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
            } else {
                button2 = button4;
            }
            button2.setText(this.this$0.getResources().getString(R.string.app_update_click_hint));
        }

        @Override // com.azhon.appupdate.listener.OnDownloadListenerAdapter, com.azhon.appupdate.listener.OnDownloadListener
        public void downloading(int max, int progress) {
            NumberProgressBar numberProgressBar = null;
            if (max == -1) {
                NumberProgressBar numberProgressBar2 = this.this$0.progressBar;
                if (numberProgressBar2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("progressBar");
                } else {
                    numberProgressBar = numberProgressBar2;
                }
                numberProgressBar.setVisibility(8);
                return;
            }
            int i2 = (int) ((progress / max) * 100.0d);
            NumberProgressBar numberProgressBar3 = this.this$0.progressBar;
            if (numberProgressBar3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("progressBar");
            } else {
                numberProgressBar = numberProgressBar3;
            }
            numberProgressBar.setProgress(i2);
        }

        @Override // com.azhon.appupdate.listener.OnDownloadListenerAdapter, com.azhon.appupdate.listener.OnDownloadListener
        public void error(@NotNull Throwable e2) {
            Intrinsics.checkNotNullParameter(e2, "e");
            Button button = this.this$0.btnUpdate;
            Button button2 = null;
            if (button == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button = null;
            }
            button.setTag(Integer.valueOf(this.this$0.error));
            Button button3 = this.this$0.btnUpdate;
            if (button3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button3 = null;
            }
            button3.setEnabled(true);
            Button button4 = this.this$0.btnUpdate;
            if (button4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
            } else {
                button2 = button4;
            }
            button2.setText(this.this$0.getResources().getString(R.string.app_update_continue_downloading));
        }

        @Override // com.azhon.appupdate.listener.OnDownloadListenerAdapter, com.azhon.appupdate.listener.OnDownloadListener
        public void start() {
            Button button = this.this$0.btnUpdate;
            Button button2 = null;
            if (button == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button = null;
            }
            button.setEnabled(false);
            Button button3 = this.this$0.btnUpdate;
            if (button3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
            } else {
                button2 = button3;
            }
            button2.setText(this.this$0.getResources().getString(R.string.app_update_background_downloading));
        }
    };

    private final void init() throws Resources.NotFoundException {
        DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
        this.manager = instance$mvvm_release$default;
        if (instance$mvvm_release$default == null) {
            LogUtil.INSTANCE.e(TAG, "An exception occurred by DownloadManager=null,please check your code!");
            return;
        }
        Intrinsics.checkNotNull(instance$mvvm_release$default);
        if (instance$mvvm_release$default.getForcedUpgrade()) {
            DownloadManager downloadManager = this.manager;
            Intrinsics.checkNotNull(downloadManager);
            downloadManager.getOnDownloadListeners$mvvm_release().add(this.listenerAdapter);
        }
        setWindowSize();
        DownloadManager downloadManager2 = this.manager;
        Intrinsics.checkNotNull(downloadManager2);
        initView(downloadManager2);
    }

    private final void initView(DownloadManager manager) throws Resources.NotFoundException {
        View viewFindViewById = findViewById(R.id.ib_close);
        ImageView imageView = (ImageView) findViewById(R.id.iv_bg);
        TextView textView = (TextView) findViewById(R.id.versionTv);
        TextView textView2 = (TextView) findViewById(R.id.tv_size);
        TextView textView3 = (TextView) findViewById(R.id.tv_description);
        View viewFindViewById2 = findViewById(R.id.np_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.np_bar)");
        this.progressBar = (NumberProgressBar) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.btn_update);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.btn_update)");
        this.btnUpdate = (Button) viewFindViewById3;
        NumberProgressBar numberProgressBar = this.progressBar;
        Button button = null;
        if (numberProgressBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progressBar");
            numberProgressBar = null;
        }
        numberProgressBar.setVisibility(8);
        Button button2 = this.btnUpdate;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
            button2 = null;
        }
        button2.setTag(0);
        Button button3 = this.btnUpdate;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
            button3 = null;
        }
        button3.setOnClickListener(this);
        viewFindViewById.setOnClickListener(this);
        if (manager.getDialogImage() != -1) {
            imageView.setBackgroundResource(manager.getDialogImage());
        }
        if (manager.getDialogButtonTextColor() != -1) {
            Button button4 = this.btnUpdate;
            if (button4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button4 = null;
            }
            button4.setTextColor(manager.getDialogButtonTextColor());
        }
        if (manager.getDialogProgressBarColor() != -1) {
            NumberProgressBar numberProgressBar2 = this.progressBar;
            if (numberProgressBar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("progressBar");
                numberProgressBar2 = null;
            }
            numberProgressBar2.setReachedBarColor(manager.getDialogProgressBarColor());
            NumberProgressBar numberProgressBar3 = this.progressBar;
            if (numberProgressBar3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("progressBar");
                numberProgressBar3 = null;
            }
            numberProgressBar3.setProgressTextColor(manager.getDialogProgressBarColor());
        }
        if (manager.getDialogButtonColor() != -1) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(manager.getDialogButtonColor());
            gradientDrawable.setCornerRadius(DensityUtil.INSTANCE.dip2px(this, 3.0f));
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);
            stateListDrawable.addState(new int[0], gradientDrawable);
            Button button5 = this.btnUpdate;
            if (button5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
            } else {
                button = button5;
            }
            button.setBackground(stateListDrawable);
        }
        if (manager.getForcedUpgrade()) {
            viewFindViewById.setVisibility(8);
        } else {
            viewFindViewById.setVisibility(0);
        }
        if (manager.getApkVersionName().length() > 0) {
            textView.setText(manager.getApkVersionName());
        }
        if (manager.getApkSize().length() > 0) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String string = getResources().getString(R.string.app_update_dialog_new_size);
            Intrinsics.checkNotNullExpressionValue(string, "resources.getString(R.st…p_update_dialog_new_size)");
            String str = String.format(string, Arrays.copyOf(new Object[]{manager.getApkSize()}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
            textView2.setText(str);
            textView2.setVisibility(0);
        }
        textView3.setText(manager.getApkDescription());
    }

    private final void setWindowSize() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75f);
        attributes.height = -2;
        attributes.gravity = 17;
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        OnButtonClickListener onButtonClickListener;
        DownloadManager downloadManager = this.manager;
        boolean z2 = false;
        if (downloadManager != null && downloadManager.getForcedUpgrade()) {
            z2 = true;
        }
        if (z2) {
            return;
        }
        super.onBackPressed();
        DownloadManager downloadManager2 = this.manager;
        if (downloadManager2 == null || (onButtonClickListener = downloadManager2.getOnButtonClickListener()) == null) {
            return;
        }
        onButtonClickListener.onButtonClick(1);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View v2) {
        OnButtonClickListener onButtonClickListener;
        OnButtonClickListener onButtonClickListener2;
        Button button = null;
        File file = null;
        Integer numValueOf = v2 != null ? Integer.valueOf(v2.getId()) : null;
        int i2 = R.id.ib_close;
        boolean z2 = false;
        if (numValueOf != null && numValueOf.intValue() == i2) {
            DownloadManager downloadManager = this.manager;
            if (downloadManager != null && !downloadManager.getForcedUpgrade()) {
                z2 = true;
            }
            if (z2) {
                finish();
            }
            DownloadManager downloadManager2 = this.manager;
            if (downloadManager2 == null || (onButtonClickListener2 = downloadManager2.getOnButtonClickListener()) == null) {
                return;
            }
            onButtonClickListener2.onButtonClick(1);
            return;
        }
        int i3 = R.id.btn_update;
        if (numValueOf != null && numValueOf.intValue() == i3) {
            Button button2 = this.btnUpdate;
            if (button2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                button2 = null;
            }
            if (Intrinsics.areEqual(button2.getTag(), Integer.valueOf(this.install))) {
                ApkUtil.Companion companion = ApkUtil.INSTANCE;
                String authorities = Constant.INSTANCE.getAUTHORITIES();
                Intrinsics.checkNotNull(authorities);
                File file2 = this.apk;
                if (file2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("apk");
                } else {
                    file = file2;
                }
                companion.installApk(this, authorities, file);
                return;
            }
            DownloadManager downloadManager3 = this.manager;
            if (downloadManager3 != null && downloadManager3.getForcedUpgrade()) {
                Button button3 = this.btnUpdate;
                if (button3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                    button3 = null;
                }
                button3.setEnabled(false);
                Button button4 = this.btnUpdate;
                if (button4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btnUpdate");
                } else {
                    button = button4;
                }
                button.setText(getResources().getString(R.string.app_update_background_downloading));
            } else {
                finish();
            }
            DownloadManager downloadManager4 = this.manager;
            if (downloadManager4 != null && (onButtonClickListener = downloadManager4.getOnButtonClickListener()) != null) {
                onButtonClickListener.onButtonClick(0);
            }
            startService(new Intent(this, (Class<?>) DownloadService.class));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setTitle("");
        setContentView(R.layout.app_update_dialog_update);
        init();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        List<OnDownloadListener> onDownloadListeners$mvvm_release;
        super.onDestroy();
        DownloadManager downloadManager = this.manager;
        if (downloadManager == null || (onDownloadListeners$mvvm_release = downloadManager.getOnDownloadListeners$mvvm_release()) == null) {
            return;
        }
        onDownloadListeners$mvvm_release.remove(this.listenerAdapter);
    }
}
