package com.yddmi.doctor.pages.login;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.network.api.YddApi;
import com.yddmi.doctor.repository.YddClinicRepository;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u000fH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/yddmi/doctor/pages/login/PopupHostChange;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "bodyTv", "Landroid/widget/TextView;", "changeHost", "Lcom/yddmi/doctor/config/YddHostConfig$YddHost;", "currentHost", "filePrivateTv", "fileTv", "hostChangeTv", "hostTv", "dealInit", "", "dealSave", "getImplLayoutId", "", "onCreate", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupHostChange extends CenterPopupView {
    private TextView bodyTv;

    @Nullable
    private YddHostConfig.YddHost changeHost;
    private YddHostConfig.YddHost currentHost;
    private TextView filePrivateTv;
    private TextView fileTv;
    private TextView hostChangeTv;
    private TextView hostTv;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[YddHostConfig.YddHost.values().length];
            try {
                iArr[YddHostConfig.YddHost.DEV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[YddHostConfig.YddHost.TEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[YddHostConfig.YddHost.TEST192.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[YddHostConfig.YddHost.TEST126.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[YddHostConfig.YddHost.UAT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[YddHostConfig.YddHost.BASE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupHostChange(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void dealInit() {
        View viewFindViewById = findViewById(R.id.hostTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.hostTv)");
        this.hostTv = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.hostChangeTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.hostChangeTv)");
        this.hostChangeTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.fileTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.fileTv)");
        this.fileTv = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.filePrivateTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.filePrivateTv)");
        this.filePrivateTv = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.bodyTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.bodyTv)");
        this.bodyTv = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById6, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$0(this.f25881c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById7 = findViewById(R.id.devTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById<BLTextView>(R.id.devTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById7, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$1(this.f25883c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById8 = findViewById(R.id.testTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<BLTextView>(R.id.testTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById8, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$2(this.f25885c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById9 = findViewById(R.id.test192Tv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById<BLTextView>(R.id.test192Tv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById9, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$3(this.f25887c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById10 = findViewById(R.id.test126Tv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById<BLTextView>(R.id.test126Tv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById10, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$4(this.f25889c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById11 = findViewById(R.id.uatTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById<BLTextView>(R.id.uatTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById11, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$5(this.f25891c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById12 = findViewById(R.id.formalTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById<BLTextView>(R.id.formalTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById12, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$6(this.f25893c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById13 = findViewById(R.id.saveTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById<BLTextView>(R.id.saveTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById13, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.login.u0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHostChange.dealInit$lambda$7(this.f25896c, view);
            }
        }, 0L, 2, null);
        YddHostConfig.YddHost currentHost = YddHostConfig.INSTANCE.getInstance().getCurrentHost();
        this.currentHost = currentHost;
        TextView textView = null;
        if (currentHost == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentHost");
            currentHost = null;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[currentHost.ordinal()]) {
            case 1:
                TextView textView2 = this.hostTv;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView2;
                }
                textView.setText("当前在开发环境：http://192.168.3.113/");
                break;
            case 2:
                TextView textView3 = this.hostTv;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView3;
                }
                textView.setText("当前在测试环境：http://59.173.18.239:2336/");
                break;
            case 3:
                TextView textView4 = this.hostTv;
                if (textView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView4;
                }
                textView.setText("当前在测试内网：https://192.168.3.123/");
                break;
            case 4:
                TextView textView5 = this.hostTv;
                if (textView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView5;
                }
                textView.setText("当前在测试内网126：https://192.168.3.126/");
                break;
            case 5:
                TextView textView6 = this.hostTv;
                if (textView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView6;
                }
                textView.setText("当前在Uat预发：https://192.168.3.192/");
                break;
            case 6:
                TextView textView7 = this.hostTv;
                if (textView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView7;
                }
                textView.setText("当前在自定义Base：" + YddApi.INSTANCE.getBaseUrl());
                break;
            default:
                TextView textView8 = this.hostTv;
                if (textView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hostTv");
                } else {
                    textView = textView8;
                }
                textView.setText("当前在正式环境：https://www.medmeta.com/");
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$0(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$1(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeHost = YddHostConfig.YddHost.DEV;
        TextView textView = this$0.hostChangeTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostChangeTv");
            textView = null;
        }
        textView.setText("替换BaseUrl开发环境：http://192.168.3.113/");
        TextView textView3 = this$0.bodyTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyTv");
            textView3 = null;
        }
        textView3.setText("Web加载地址：http://192.168.3.113/");
        TextView textView4 = this$0.fileTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTv");
            textView4 = null;
        }
        textView4.setText("文件加载地址：http://192.168.3.113/");
        TextView textView5 = this$0.filePrivateTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filePrivateTv");
        } else {
            textView2 = textView5;
        }
        textView2.setText("私有文件地址：" + YddHostConfig.INSTANCE.getInstance().servicePrivateGet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$2(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeHost = YddHostConfig.YddHost.TEST;
        TextView textView = this$0.hostChangeTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostChangeTv");
            textView = null;
        }
        textView.setText("替换BaseUrl测试环境：http://59.173.18.239:2336/");
        TextView textView3 = this$0.bodyTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyTv");
            textView3 = null;
        }
        textView3.setText("Web加载地址：http://59.173.18.239:2336/");
        TextView textView4 = this$0.fileTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTv");
            textView4 = null;
        }
        textView4.setText("文件加载地址：http://59.173.18.239:2336/");
        TextView textView5 = this$0.filePrivateTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filePrivateTv");
        } else {
            textView2 = textView5;
        }
        textView2.setText("私有文件地址：" + YddHostConfig.INSTANCE.getInstance().servicePrivateGet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$3(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeHost = YddHostConfig.YddHost.TEST192;
        TextView textView = this$0.hostChangeTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostChangeTv");
            textView = null;
        }
        textView.setText("替换BaseUrl测试内网：https://192.168.3.123/");
        TextView textView3 = this$0.bodyTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyTv");
            textView3 = null;
        }
        textView3.setText("Web加载地址：https://192.168.3.123/");
        TextView textView4 = this$0.fileTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTv");
            textView4 = null;
        }
        textView4.setText("文件加载地址：https://192.168.3.123/");
        TextView textView5 = this$0.filePrivateTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filePrivateTv");
        } else {
            textView2 = textView5;
        }
        textView2.setText("私有文件地址：" + YddHostConfig.INSTANCE.getInstance().servicePrivateGet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$4(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeHost = YddHostConfig.YddHost.TEST126;
        TextView textView = this$0.hostChangeTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostChangeTv");
            textView = null;
        }
        textView.setText("替换BaseUrl测试126：https://192.168.3.126/");
        TextView textView3 = this$0.bodyTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyTv");
            textView3 = null;
        }
        textView3.setText("Web加载地址：https://192.168.3.126/");
        TextView textView4 = this$0.fileTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTv");
            textView4 = null;
        }
        textView4.setText("文件加载地址：https://192.168.3.126/");
        TextView textView5 = this$0.filePrivateTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filePrivateTv");
        } else {
            textView2 = textView5;
        }
        textView2.setText("私有文件地址：" + YddHostConfig.INSTANCE.getInstance().servicePrivateGet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$5(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeHost = YddHostConfig.YddHost.UAT;
        TextView textView = this$0.hostChangeTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostChangeTv");
            textView = null;
        }
        textView.setText("替换BaseUrl预发环境：https://192.168.3.192/");
        TextView textView3 = this$0.bodyTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyTv");
            textView3 = null;
        }
        textView3.setText("Web加载地址：https://192.168.3.192/");
        TextView textView4 = this$0.fileTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTv");
            textView4 = null;
        }
        textView4.setText("文件加载地址：https://192.168.3.192/");
        TextView textView5 = this$0.filePrivateTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filePrivateTv");
        } else {
            textView2 = textView5;
        }
        textView2.setText("私有文件地址：" + YddHostConfig.INSTANCE.getInstance().servicePrivateGet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$6(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.changeHost = YddHostConfig.YddHost.FORMAL;
        TextView textView = this$0.hostChangeTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostChangeTv");
            textView = null;
        }
        textView.setText("替换BaseUrl正式环境：https://www.medmeta.com/");
        TextView textView3 = this$0.bodyTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bodyTv");
            textView3 = null;
        }
        textView3.setText("Web加载地址：https://www.medmeta.com/");
        TextView textView4 = this$0.fileTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTv");
            textView4 = null;
        }
        textView4.setText("文件加载地址：https://file.medmeta.com/");
        TextView textView5 = this$0.filePrivateTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filePrivateTv");
        } else {
            textView2 = textView5;
        }
        textView2.setText("私有文件地址：https://file.medmeta.com/");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dealInit$lambda$7(PopupHostChange this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dealSave();
    }

    private final void dealSave() {
        if (this.changeHost != null) {
            YddHostConfig.YddHost yddHost = this.currentHost;
            if (yddHost == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentHost");
                yddHost = null;
            }
            if (yddHost != this.changeHost) {
                YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
                YddHostConfig.YddHost yddHost2 = this.changeHost;
                Intrinsics.checkNotNull(yddHost2);
                companion.dealChangeHost(yddHost2);
                YddClinicRepository.INSTANCE.dealOtherService();
            }
        }
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_login_host;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        dealInit();
    }
}
