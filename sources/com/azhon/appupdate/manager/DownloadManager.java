package com.azhon.appupdate.manager;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.azhon.appupdate.base.BaseHttpDownloadManager;
import com.azhon.appupdate.config.Constant;
import com.azhon.appupdate.listener.LifecycleCallbacksAdapter;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.service.DownloadService;
import com.azhon.appupdate.util.ApkUtil;
import com.azhon.appupdate.util.LogUtil;
import com.azhon.appupdate.view.UpdateDialogActivity;
import com.catchpig.mvvm.R;
import com.google.android.exoplayer2.util.MimeTypes;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u0000 m2\u00020\u0001:\u0002lmB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010d\u001a\u00020eJ\b\u0010f\u001a\u000203H\u0002J\b\u0010g\u001a\u000203H\u0002J\b\u0010h\u001a\u00020eH\u0002J\u0006\u0010i\u001a\u00020eJ\r\u0010j\u001a\u00020eH\u0000¢\u0006\u0002\bkR\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u001a\u0010\u0014\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\b\"\u0004\b\u001b\u0010\nR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\b\"\u0004\b \u0010\nR\u001a\u0010!\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010#\"\u0004\b(\u0010%R\u001a\u0010)\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010#\"\u0004\b+\u0010%R\u001a\u0010,\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010#\"\u0004\b.\u0010%R\u001a\u0010/\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\b\"\u0004\b1\u0010\nR\u001a\u00102\u001a\u000203X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u00108\u001a\u000203X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00105\"\u0004\b:\u00107R\u001c\u0010;\u001a\u0004\u0018\u00010<X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010A\u001a\u000203X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u00105\"\u0004\bC\u00107R\u001c\u0010D\u001a\u0004\u0018\u00010EX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001a\u0010J\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010#\"\u0004\bL\u0010%R\u001c\u0010M\u001a\u0004\u0018\u00010NX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR \u0010S\u001a\b\u0012\u0004\u0012\u00020U0TX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u001a\u0010Z\u001a\u000203X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u00105\"\u0004\b\\\u00107R\u000e\u0010]\u001a\u000203X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010^\u001a\u000203X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u00105\"\u0004\b`\u00107R\u001a\u0010a\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010#\"\u0004\bc\u0010%¨\u0006n"}, d2 = {"Lcom/azhon/appupdate/manager/DownloadManager;", "Ljava/io/Serializable;", "builder", "Lcom/azhon/appupdate/manager/DownloadManager$Builder;", "(Lcom/azhon/appupdate/manager/DownloadManager$Builder;)V", "apkDescription", "", "getApkDescription$mvvm_release", "()Ljava/lang/String;", "setApkDescription$mvvm_release", "(Ljava/lang/String;)V", "apkMD5", "getApkMD5$mvvm_release", "setApkMD5$mvvm_release", "apkName", "getApkName$mvvm_release", "setApkName$mvvm_release", "apkSize", "getApkSize$mvvm_release", "setApkSize$mvvm_release", "apkUrl", "getApkUrl$mvvm_release", "setApkUrl$mvvm_release", "apkVersionCode", "", "apkVersionName", "getApkVersionName$mvvm_release", "setApkVersionName$mvvm_release", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "contextClsName", "getContextClsName$mvvm_release", "setContextClsName$mvvm_release", "dialogButtonColor", "getDialogButtonColor$mvvm_release", "()I", "setDialogButtonColor$mvvm_release", "(I)V", "dialogButtonTextColor", "getDialogButtonTextColor$mvvm_release", "setDialogButtonTextColor$mvvm_release", "dialogImage", "getDialogImage$mvvm_release", "setDialogImage$mvvm_release", "dialogProgressBarColor", "getDialogProgressBarColor$mvvm_release", "setDialogProgressBarColor$mvvm_release", "downloadPath", "getDownloadPath$mvvm_release", "setDownloadPath$mvvm_release", "downloadState", "", "getDownloadState", "()Z", "setDownloadState", "(Z)V", "forcedUpgrade", "getForcedUpgrade$mvvm_release", "setForcedUpgrade$mvvm_release", "httpManager", "Lcom/azhon/appupdate/base/BaseHttpDownloadManager;", "getHttpManager$mvvm_release", "()Lcom/azhon/appupdate/base/BaseHttpDownloadManager;", "setHttpManager$mvvm_release", "(Lcom/azhon/appupdate/base/BaseHttpDownloadManager;)V", "jumpInstallPage", "getJumpInstallPage$mvvm_release", "setJumpInstallPage$mvvm_release", "notificationChannel", "Landroid/app/NotificationChannel;", "getNotificationChannel$mvvm_release", "()Landroid/app/NotificationChannel;", "setNotificationChannel$mvvm_release", "(Landroid/app/NotificationChannel;)V", RemoteMessageConst.Notification.NOTIFY_ID, "getNotifyId$mvvm_release", "setNotifyId$mvvm_release", "onButtonClickListener", "Lcom/azhon/appupdate/listener/OnButtonClickListener;", "getOnButtonClickListener$mvvm_release", "()Lcom/azhon/appupdate/listener/OnButtonClickListener;", "setOnButtonClickListener$mvvm_release", "(Lcom/azhon/appupdate/listener/OnButtonClickListener;)V", "onDownloadListeners", "", "Lcom/azhon/appupdate/listener/OnDownloadListener;", "getOnDownloadListeners$mvvm_release", "()Ljava/util/List;", "setOnDownloadListeners$mvvm_release", "(Ljava/util/List;)V", "showBgdToast", "getShowBgdToast$mvvm_release", "setShowBgdToast$mvvm_release", "showNewerToast", "showNotification", "getShowNotification$mvvm_release", "setShowNotification$mvvm_release", "smallIcon", "getSmallIcon$mvvm_release", "setSmallIcon$mvvm_release", "cancel", "", "checkParams", "checkVersionCode", "clearListener", AliyunLogCommon.SubModule.download, "release", "release$mvvm_release", "Builder", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DownloadManager implements Serializable {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "DownloadManager";

    @Nullable
    private static DownloadManager instance;

    @NotNull
    private String apkDescription;

    @NotNull
    private String apkMD5;

    @NotNull
    private String apkName;

    @NotNull
    private String apkSize;

    @NotNull
    private String apkUrl;
    private int apkVersionCode;

    @NotNull
    private String apkVersionName;

    @NotNull
    private Application application;

    @NotNull
    private String contextClsName;
    private int dialogButtonColor;
    private int dialogButtonTextColor;
    private int dialogImage;
    private int dialogProgressBarColor;

    @NotNull
    private String downloadPath;
    private boolean downloadState;
    private boolean forcedUpgrade;

    @Nullable
    private BaseHttpDownloadManager httpManager;
    private boolean jumpInstallPage;

    @Nullable
    private NotificationChannel notificationChannel;
    private int notifyId;

    @Nullable
    private OnButtonClickListener onButtonClickListener;

    @NotNull
    private List<OnDownloadListener> onDownloadListeners;
    private boolean showBgdToast;
    private boolean showNewerToast;
    private boolean showNotification;
    private int smallIcon;

    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0006J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0006J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0006J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0006J\u0006\u0010i\u001a\u00020jJ\u000e\u0010)\u001a\u00020\u00002\u0006\u0010)\u001a\u00020\u0018J\u000e\u0010,\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0018J\u000e\u0010/\u001a\u00020\u00002\u0006\u0010/\u001a\u00020\u0018J\u000e\u00102\u001a\u00020\u00002\u0006\u00102\u001a\u00020\u0018J\u000e\u0010k\u001a\u00020\u00002\u0006\u0010l\u001a\u000209J\u000e\u00108\u001a\u00020\u00002\u0006\u00108\u001a\u000209J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010>\u001a\u00020?J\u000e\u0010D\u001a\u00020\u00002\u0006\u0010D\u001a\u000209J\u000e\u0010G\u001a\u00020\u00002\u0006\u0010G\u001a\u00020HJ\u000e\u0010M\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u0018J\u000e\u0010P\u001a\u00020\u00002\u0006\u0010P\u001a\u00020QJ\u000e\u0010m\u001a\u00020\u00002\u0006\u0010m\u001a\u00020XJ\u000e\u0010]\u001a\u00020\u00002\u0006\u0010]\u001a\u000209J\u000e\u0010`\u001a\u00020\u00002\u0006\u0010`\u001a\u000209J\u000e\u0010c\u001a\u00020\u00002\u0006\u0010c\u001a\u000209J\u000e\u0010f\u001a\u00020\u00002\u0006\u0010f\u001a\u00020\u0018R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u001a\u0010\u0014\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR\u001a\u0010\u0017\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\b\"\u0004\b\u001f\u0010\nR\u001a\u0010 \u001a\u00020!X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\b\"\u0004\b(\u0010\nR\u001a\u0010)\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001a\"\u0004\b+\u0010\u001cR\u001a\u0010,\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001a\"\u0004\b.\u0010\u001cR\u001a\u0010/\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001a\"\u0004\b1\u0010\u001cR\u001a\u00102\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001a\"\u0004\b4\u0010\u001cR\u001c\u00105\u001a\u0004\u0018\u00010\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\b\"\u0004\b7\u0010\nR\u001a\u00108\u001a\u000209X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001c\u0010>\u001a\u0004\u0018\u00010?X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001a\u0010D\u001a\u000209X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010;\"\u0004\bF\u0010=R\u001c\u0010G\u001a\u0004\u0018\u00010HX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u001a\u0010M\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u001a\"\u0004\bO\u0010\u001cR\u001c\u0010P\u001a\u0004\u0018\u00010QX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR \u0010V\u001a\b\u0012\u0004\u0012\u00020X0WX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001a\u0010]\u001a\u000209X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010;\"\u0004\b_\u0010=R\u001a\u0010`\u001a\u000209X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010;\"\u0004\bb\u0010=R\u001a\u0010c\u001a\u000209X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010;\"\u0004\be\u0010=R\u001a\u0010f\u001a\u00020\u0018X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u0010\u001a\"\u0004\bh\u0010\u001c¨\u0006n"}, d2 = {"Lcom/azhon/appupdate/manager/DownloadManager$Builder;", "", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "apkDescription", "", "getApkDescription$mvvm_release", "()Ljava/lang/String;", "setApkDescription$mvvm_release", "(Ljava/lang/String;)V", "apkMD5", "getApkMD5$mvvm_release", "setApkMD5$mvvm_release", "apkName", "getApkName$mvvm_release", "setApkName$mvvm_release", "apkSize", "getApkSize$mvvm_release", "setApkSize$mvvm_release", "apkUrl", "getApkUrl$mvvm_release", "setApkUrl$mvvm_release", "apkVersionCode", "", "getApkVersionCode$mvvm_release", "()I", "setApkVersionCode$mvvm_release", "(I)V", "apkVersionName", "getApkVersionName$mvvm_release", "setApkVersionName$mvvm_release", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "getApplication$mvvm_release", "()Landroid/app/Application;", "setApplication$mvvm_release", "(Landroid/app/Application;)V", "contextClsName", "getContextClsName$mvvm_release", "setContextClsName$mvvm_release", "dialogButtonColor", "getDialogButtonColor$mvvm_release", "setDialogButtonColor$mvvm_release", "dialogButtonTextColor", "getDialogButtonTextColor$mvvm_release", "setDialogButtonTextColor$mvvm_release", "dialogImage", "getDialogImage$mvvm_release", "setDialogImage$mvvm_release", "dialogProgressBarColor", "getDialogProgressBarColor$mvvm_release", "setDialogProgressBarColor$mvvm_release", "downloadPath", "getDownloadPath$mvvm_release", "setDownloadPath$mvvm_release", "forcedUpgrade", "", "getForcedUpgrade$mvvm_release", "()Z", "setForcedUpgrade$mvvm_release", "(Z)V", "httpManager", "Lcom/azhon/appupdate/base/BaseHttpDownloadManager;", "getHttpManager$mvvm_release", "()Lcom/azhon/appupdate/base/BaseHttpDownloadManager;", "setHttpManager$mvvm_release", "(Lcom/azhon/appupdate/base/BaseHttpDownloadManager;)V", "jumpInstallPage", "getJumpInstallPage$mvvm_release", "setJumpInstallPage$mvvm_release", "notificationChannel", "Landroid/app/NotificationChannel;", "getNotificationChannel$mvvm_release", "()Landroid/app/NotificationChannel;", "setNotificationChannel$mvvm_release", "(Landroid/app/NotificationChannel;)V", RemoteMessageConst.Notification.NOTIFY_ID, "getNotifyId$mvvm_release", "setNotifyId$mvvm_release", "onButtonClickListener", "Lcom/azhon/appupdate/listener/OnButtonClickListener;", "getOnButtonClickListener$mvvm_release", "()Lcom/azhon/appupdate/listener/OnButtonClickListener;", "setOnButtonClickListener$mvvm_release", "(Lcom/azhon/appupdate/listener/OnButtonClickListener;)V", "onDownloadListeners", "", "Lcom/azhon/appupdate/listener/OnDownloadListener;", "getOnDownloadListeners$mvvm_release", "()Ljava/util/List;", "setOnDownloadListeners$mvvm_release", "(Ljava/util/List;)V", "showBgdToast", "getShowBgdToast$mvvm_release", "setShowBgdToast$mvvm_release", "showNewerToast", "getShowNewerToast$mvvm_release", "setShowNewerToast$mvvm_release", "showNotification", "getShowNotification$mvvm_release", "setShowNotification$mvvm_release", "smallIcon", "getSmallIcon$mvvm_release", "setSmallIcon$mvvm_release", "build", "Lcom/azhon/appupdate/manager/DownloadManager;", "enableLog", "enable", "onDownloadListener", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {

        @NotNull
        private String apkDescription;

        @NotNull
        private String apkMD5;

        @NotNull
        private String apkName;

        @NotNull
        private String apkSize;

        @NotNull
        private String apkUrl;
        private int apkVersionCode;

        @NotNull
        private String apkVersionName;

        @NotNull
        private Application application;

        @NotNull
        private String contextClsName;
        private int dialogButtonColor;
        private int dialogButtonTextColor;
        private int dialogImage;
        private int dialogProgressBarColor;

        @Nullable
        private String downloadPath;
        private boolean forcedUpgrade;

        @Nullable
        private BaseHttpDownloadManager httpManager;
        private boolean jumpInstallPage;

        @Nullable
        private NotificationChannel notificationChannel;
        private int notifyId;

        @Nullable
        private OnButtonClickListener onButtonClickListener;

        @NotNull
        private List<OnDownloadListener> onDownloadListeners;
        private boolean showBgdToast;
        private boolean showNewerToast;
        private boolean showNotification;
        private int smallIcon;

        public Builder(@NotNull Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Application application = activity.getApplication();
            Intrinsics.checkNotNullExpressionValue(application, "activity.application");
            this.application = application;
            String name = activity.getClass().getName();
            Intrinsics.checkNotNullExpressionValue(name, "activity.javaClass.name");
            this.contextClsName = name;
            this.apkUrl = "";
            this.apkName = "";
            this.apkVersionCode = Integer.MIN_VALUE;
            this.apkVersionName = "";
            File externalCacheDir = this.application.getExternalCacheDir();
            this.downloadPath = externalCacheDir != null ? externalCacheDir.getPath() : null;
            this.smallIcon = -1;
            this.apkDescription = "";
            this.apkSize = "";
            this.apkMD5 = "";
            this.onDownloadListeners = new ArrayList();
            this.showNotification = true;
            this.jumpInstallPage = true;
            this.showBgdToast = true;
            this.notifyId = 1011;
            this.dialogImage = -1;
            this.dialogButtonColor = -1;
            this.dialogButtonTextColor = -1;
            this.dialogProgressBarColor = -1;
        }

        @NotNull
        public final Builder apkDescription(@NotNull String apkDescription) {
            Intrinsics.checkNotNullParameter(apkDescription, "apkDescription");
            this.apkDescription = apkDescription;
            return this;
        }

        @NotNull
        public final Builder apkMD5(@NotNull String apkMD5) {
            Intrinsics.checkNotNullParameter(apkMD5, "apkMD5");
            this.apkMD5 = apkMD5;
            return this;
        }

        @NotNull
        public final Builder apkName(@NotNull String apkName) {
            Intrinsics.checkNotNullParameter(apkName, "apkName");
            this.apkName = apkName;
            return this;
        }

        @NotNull
        public final Builder apkSize(@NotNull String apkSize) {
            Intrinsics.checkNotNullParameter(apkSize, "apkSize");
            this.apkSize = apkSize;
            return this;
        }

        @NotNull
        public final Builder apkUrl(@NotNull String apkUrl) {
            Intrinsics.checkNotNullParameter(apkUrl, "apkUrl");
            this.apkUrl = apkUrl;
            return this;
        }

        @NotNull
        public final Builder apkVersionCode(int apkVersionCode) {
            this.apkVersionCode = apkVersionCode;
            return this;
        }

        @NotNull
        public final Builder apkVersionName(@NotNull String apkVersionName) {
            Intrinsics.checkNotNullParameter(apkVersionName, "apkVersionName");
            this.apkVersionName = apkVersionName;
            return this;
        }

        @NotNull
        public final DownloadManager build() {
            DownloadManager instance$mvvm_release = DownloadManager.INSTANCE.getInstance$mvvm_release(this);
            Intrinsics.checkNotNull(instance$mvvm_release);
            return instance$mvvm_release;
        }

        @NotNull
        public final Builder dialogButtonColor(int dialogButtonColor) {
            this.dialogButtonColor = dialogButtonColor;
            return this;
        }

        @NotNull
        public final Builder dialogButtonTextColor(int dialogButtonTextColor) {
            this.dialogButtonTextColor = dialogButtonTextColor;
            return this;
        }

        @NotNull
        public final Builder dialogImage(int dialogImage) {
            this.dialogImage = dialogImage;
            return this;
        }

        @NotNull
        public final Builder dialogProgressBarColor(int dialogProgressBarColor) {
            this.dialogProgressBarColor = dialogProgressBarColor;
            return this;
        }

        @NotNull
        public final Builder enableLog(boolean enable) {
            LogUtil.INSTANCE.enable(enable);
            return this;
        }

        @NotNull
        public final Builder forcedUpgrade(boolean forcedUpgrade) {
            this.forcedUpgrade = forcedUpgrade;
            return this;
        }

        @NotNull
        /* renamed from: getApkDescription$mvvm_release, reason: from getter */
        public final String getApkDescription() {
            return this.apkDescription;
        }

        @NotNull
        /* renamed from: getApkMD5$mvvm_release, reason: from getter */
        public final String getApkMD5() {
            return this.apkMD5;
        }

        @NotNull
        /* renamed from: getApkName$mvvm_release, reason: from getter */
        public final String getApkName() {
            return this.apkName;
        }

        @NotNull
        /* renamed from: getApkSize$mvvm_release, reason: from getter */
        public final String getApkSize() {
            return this.apkSize;
        }

        @NotNull
        /* renamed from: getApkUrl$mvvm_release, reason: from getter */
        public final String getApkUrl() {
            return this.apkUrl;
        }

        /* renamed from: getApkVersionCode$mvvm_release, reason: from getter */
        public final int getApkVersionCode() {
            return this.apkVersionCode;
        }

        @NotNull
        /* renamed from: getApkVersionName$mvvm_release, reason: from getter */
        public final String getApkVersionName() {
            return this.apkVersionName;
        }

        @NotNull
        /* renamed from: getApplication$mvvm_release, reason: from getter */
        public final Application getApplication() {
            return this.application;
        }

        @NotNull
        /* renamed from: getContextClsName$mvvm_release, reason: from getter */
        public final String getContextClsName() {
            return this.contextClsName;
        }

        /* renamed from: getDialogButtonColor$mvvm_release, reason: from getter */
        public final int getDialogButtonColor() {
            return this.dialogButtonColor;
        }

        /* renamed from: getDialogButtonTextColor$mvvm_release, reason: from getter */
        public final int getDialogButtonTextColor() {
            return this.dialogButtonTextColor;
        }

        /* renamed from: getDialogImage$mvvm_release, reason: from getter */
        public final int getDialogImage() {
            return this.dialogImage;
        }

        /* renamed from: getDialogProgressBarColor$mvvm_release, reason: from getter */
        public final int getDialogProgressBarColor() {
            return this.dialogProgressBarColor;
        }

        @Nullable
        /* renamed from: getDownloadPath$mvvm_release, reason: from getter */
        public final String getDownloadPath() {
            return this.downloadPath;
        }

        /* renamed from: getForcedUpgrade$mvvm_release, reason: from getter */
        public final boolean getForcedUpgrade() {
            return this.forcedUpgrade;
        }

        @Nullable
        /* renamed from: getHttpManager$mvvm_release, reason: from getter */
        public final BaseHttpDownloadManager getHttpManager() {
            return this.httpManager;
        }

        /* renamed from: getJumpInstallPage$mvvm_release, reason: from getter */
        public final boolean getJumpInstallPage() {
            return this.jumpInstallPage;
        }

        @Nullable
        /* renamed from: getNotificationChannel$mvvm_release, reason: from getter */
        public final NotificationChannel getNotificationChannel() {
            return this.notificationChannel;
        }

        /* renamed from: getNotifyId$mvvm_release, reason: from getter */
        public final int getNotifyId() {
            return this.notifyId;
        }

        @Nullable
        /* renamed from: getOnButtonClickListener$mvvm_release, reason: from getter */
        public final OnButtonClickListener getOnButtonClickListener() {
            return this.onButtonClickListener;
        }

        @NotNull
        public final List<OnDownloadListener> getOnDownloadListeners$mvvm_release() {
            return this.onDownloadListeners;
        }

        /* renamed from: getShowBgdToast$mvvm_release, reason: from getter */
        public final boolean getShowBgdToast() {
            return this.showBgdToast;
        }

        /* renamed from: getShowNewerToast$mvvm_release, reason: from getter */
        public final boolean getShowNewerToast() {
            return this.showNewerToast;
        }

        /* renamed from: getShowNotification$mvvm_release, reason: from getter */
        public final boolean getShowNotification() {
            return this.showNotification;
        }

        /* renamed from: getSmallIcon$mvvm_release, reason: from getter */
        public final int getSmallIcon() {
            return this.smallIcon;
        }

        @NotNull
        public final Builder httpManager(@NotNull BaseHttpDownloadManager httpManager) {
            Intrinsics.checkNotNullParameter(httpManager, "httpManager");
            this.httpManager = httpManager;
            return this;
        }

        @NotNull
        public final Builder jumpInstallPage(boolean jumpInstallPage) {
            this.jumpInstallPage = jumpInstallPage;
            return this;
        }

        @NotNull
        public final Builder notificationChannel(@NotNull NotificationChannel notificationChannel) {
            Intrinsics.checkNotNullParameter(notificationChannel, "notificationChannel");
            this.notificationChannel = notificationChannel;
            return this;
        }

        @NotNull
        public final Builder notifyId(int notifyId) {
            this.notifyId = notifyId;
            return this;
        }

        @NotNull
        public final Builder onButtonClickListener(@NotNull OnButtonClickListener onButtonClickListener) {
            Intrinsics.checkNotNullParameter(onButtonClickListener, "onButtonClickListener");
            this.onButtonClickListener = onButtonClickListener;
            return this;
        }

        @NotNull
        public final Builder onDownloadListener(@NotNull OnDownloadListener onDownloadListener) {
            Intrinsics.checkNotNullParameter(onDownloadListener, "onDownloadListener");
            this.onDownloadListeners.add(onDownloadListener);
            return this;
        }

        public final void setApkDescription$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.apkDescription = str;
        }

        public final void setApkMD5$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.apkMD5 = str;
        }

        public final void setApkName$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.apkName = str;
        }

        public final void setApkSize$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.apkSize = str;
        }

        public final void setApkUrl$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.apkUrl = str;
        }

        public final void setApkVersionCode$mvvm_release(int i2) {
            this.apkVersionCode = i2;
        }

        public final void setApkVersionName$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.apkVersionName = str;
        }

        public final void setApplication$mvvm_release(@NotNull Application application) {
            Intrinsics.checkNotNullParameter(application, "<set-?>");
            this.application = application;
        }

        public final void setContextClsName$mvvm_release(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.contextClsName = str;
        }

        public final void setDialogButtonColor$mvvm_release(int i2) {
            this.dialogButtonColor = i2;
        }

        public final void setDialogButtonTextColor$mvvm_release(int i2) {
            this.dialogButtonTextColor = i2;
        }

        public final void setDialogImage$mvvm_release(int i2) {
            this.dialogImage = i2;
        }

        public final void setDialogProgressBarColor$mvvm_release(int i2) {
            this.dialogProgressBarColor = i2;
        }

        public final void setDownloadPath$mvvm_release(@Nullable String str) {
            this.downloadPath = str;
        }

        public final void setForcedUpgrade$mvvm_release(boolean z2) {
            this.forcedUpgrade = z2;
        }

        public final void setHttpManager$mvvm_release(@Nullable BaseHttpDownloadManager baseHttpDownloadManager) {
            this.httpManager = baseHttpDownloadManager;
        }

        public final void setJumpInstallPage$mvvm_release(boolean z2) {
            this.jumpInstallPage = z2;
        }

        public final void setNotificationChannel$mvvm_release(@Nullable NotificationChannel notificationChannel) {
            this.notificationChannel = notificationChannel;
        }

        public final void setNotifyId$mvvm_release(int i2) {
            this.notifyId = i2;
        }

        public final void setOnButtonClickListener$mvvm_release(@Nullable OnButtonClickListener onButtonClickListener) {
            this.onButtonClickListener = onButtonClickListener;
        }

        public final void setOnDownloadListeners$mvvm_release(@NotNull List<OnDownloadListener> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.onDownloadListeners = list;
        }

        public final void setShowBgdToast$mvvm_release(boolean z2) {
            this.showBgdToast = z2;
        }

        public final void setShowNewerToast$mvvm_release(boolean z2) {
            this.showNewerToast = z2;
        }

        public final void setShowNotification$mvvm_release(boolean z2) {
            this.showNotification = z2;
        }

        public final void setSmallIcon$mvvm_release(int i2) {
            this.smallIcon = i2;
        }

        @NotNull
        public final Builder showBgdToast(boolean showBgdToast) {
            this.showBgdToast = showBgdToast;
            return this;
        }

        @NotNull
        public final Builder showNewerToast(boolean showNewerToast) {
            this.showNewerToast = showNewerToast;
            return this;
        }

        @NotNull
        public final Builder showNotification(boolean showNotification) {
            this.showNotification = showNotification;
            return this;
        }

        @NotNull
        public final Builder smallIcon(int smallIcon) {
            this.smallIcon = smallIcon;
            return this;
        }
    }

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0000¢\u0006\u0002\b\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/azhon/appupdate/manager/DownloadManager$Companion;", "", "()V", "TAG", "", "instance", "Lcom/azhon/appupdate/manager/DownloadManager;", "getInstance", "builder", "Lcom/azhon/appupdate/manager/DownloadManager$Builder;", "getInstance$mvvm_release", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ DownloadManager getInstance$mvvm_release$default(Companion companion, Builder builder, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                builder = null;
            }
            return companion.getInstance$mvvm_release(builder);
        }

        @Nullable
        public final DownloadManager getInstance$mvvm_release(@Nullable Builder builder) {
            if (DownloadManager.instance != null && builder != null) {
                DownloadManager downloadManager = DownloadManager.instance;
                Intrinsics.checkNotNull(downloadManager);
                downloadManager.release$mvvm_release();
            }
            if (DownloadManager.instance == null) {
                DefaultConstructorMarker defaultConstructorMarker = null;
                if (builder == null) {
                    return null;
                }
                DownloadManager.instance = new DownloadManager(builder, defaultConstructorMarker);
            }
            DownloadManager downloadManager2 = DownloadManager.instance;
            Intrinsics.checkNotNull(downloadManager2);
            return downloadManager2;
        }
    }

    private DownloadManager(Builder builder) {
        this.application = builder.getApplication();
        this.contextClsName = builder.getContextClsName();
        this.apkUrl = builder.getApkUrl();
        this.apkName = builder.getApkName();
        this.apkVersionCode = builder.getApkVersionCode();
        this.apkVersionName = builder.getApkVersionName();
        String downloadPath = builder.getDownloadPath();
        if (downloadPath == null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            downloadPath = String.format(Constant.INSTANCE.getAPK_PATH(), Arrays.copyOf(new Object[]{this.application.getPackageName()}, 1));
            Intrinsics.checkNotNullExpressionValue(downloadPath, "format(format, *args)");
        }
        this.downloadPath = downloadPath;
        this.showNewerToast = builder.getShowNewerToast();
        this.smallIcon = builder.getSmallIcon();
        this.apkDescription = builder.getApkDescription();
        this.apkSize = builder.getApkSize();
        this.apkMD5 = builder.getApkMD5();
        this.httpManager = builder.getHttpManager();
        this.notificationChannel = builder.getNotificationChannel();
        this.onDownloadListeners = builder.getOnDownloadListeners$mvvm_release();
        this.onButtonClickListener = builder.getOnButtonClickListener();
        this.showNotification = builder.getShowNotification();
        this.jumpInstallPage = builder.getJumpInstallPage();
        this.showBgdToast = builder.getShowBgdToast();
        this.forcedUpgrade = builder.getForcedUpgrade();
        this.notifyId = builder.getNotifyId();
        this.dialogImage = builder.getDialogImage();
        this.dialogButtonColor = builder.getDialogButtonColor();
        this.dialogButtonTextColor = builder.getDialogButtonTextColor();
        this.dialogProgressBarColor = builder.getDialogProgressBarColor();
        this.application.registerActivityLifecycleCallbacks(new LifecycleCallbacksAdapter() { // from class: com.azhon.appupdate.manager.DownloadManager.1
            @Override // com.azhon.appupdate.listener.LifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(@NotNull Activity activity) {
                Intrinsics.checkNotNullParameter(activity, "activity");
                super.onActivityDestroyed(activity);
                if (Intrinsics.areEqual(DownloadManager.this.getContextClsName(), activity.getClass().getName())) {
                    DownloadManager.this.clearListener();
                }
            }
        });
    }

    public /* synthetic */ DownloadManager(Builder builder, DefaultConstructorMarker defaultConstructorMarker) {
        this(builder);
    }

    private final boolean checkParams() {
        if (this.apkUrl.length() == 0) {
            LogUtil.INSTANCE.e(TAG, "apkUrl can not be empty!");
            return false;
        }
        if (this.apkName.length() == 0) {
            LogUtil.INSTANCE.e(TAG, "apkName can not be empty!");
            return false;
        }
        if (!StringsKt__StringsJVMKt.endsWith$default(this.apkName, Constant.APK_SUFFIX, false, 2, null)) {
            LogUtil.INSTANCE.e(TAG, "apkName must endsWith .apk!");
            return false;
        }
        if (this.smallIcon == -1) {
            LogUtil.INSTANCE.e(TAG, "smallIcon can not be empty!");
            return false;
        }
        Constant.INSTANCE.setAUTHORITIES(this.application.getPackageName() + ".fileProvider");
        return true;
    }

    private final boolean checkVersionCode() {
        if (this.apkVersionCode == Integer.MIN_VALUE) {
            return true;
        }
        if (this.apkDescription.length() == 0) {
            LogUtil.INSTANCE.e(TAG, "apkDescription can not be empty!");
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearListener() {
        this.onButtonClickListener = null;
        this.onDownloadListeners.clear();
    }

    public final void cancel() {
        BaseHttpDownloadManager baseHttpDownloadManager = this.httpManager;
        if (baseHttpDownloadManager != null) {
            baseHttpDownloadManager.cancel();
        }
    }

    public final void download() throws Resources.NotFoundException {
        if (checkParams()) {
            if (checkVersionCode()) {
                this.application.startService(new Intent(this.application, (Class<?>) DownloadService.class));
                return;
            }
            if (this.apkVersionCode > ApkUtil.INSTANCE.getVersionCode(this.application)) {
                this.application.startActivity(new Intent(this.application, (Class<?>) UpdateDialogActivity.class).setFlags(268435456));
                return;
            }
            if (this.showNewerToast) {
                Toast.makeText(this.application, R.string.app_update_latest_version, 0).show();
            }
            LogUtil.Companion companion = LogUtil.INSTANCE;
            String string = this.application.getResources().getString(R.string.app_update_latest_version);
            Intrinsics.checkNotNullExpressionValue(string, "application.resources.ge…pp_update_latest_version)");
            companion.d(TAG, string);
        }
    }

    @NotNull
    /* renamed from: getApkDescription$mvvm_release, reason: from getter */
    public final String getApkDescription() {
        return this.apkDescription;
    }

    @NotNull
    /* renamed from: getApkMD5$mvvm_release, reason: from getter */
    public final String getApkMD5() {
        return this.apkMD5;
    }

    @NotNull
    /* renamed from: getApkName$mvvm_release, reason: from getter */
    public final String getApkName() {
        return this.apkName;
    }

    @NotNull
    /* renamed from: getApkSize$mvvm_release, reason: from getter */
    public final String getApkSize() {
        return this.apkSize;
    }

    @NotNull
    /* renamed from: getApkUrl$mvvm_release, reason: from getter */
    public final String getApkUrl() {
        return this.apkUrl;
    }

    @NotNull
    /* renamed from: getApkVersionName$mvvm_release, reason: from getter */
    public final String getApkVersionName() {
        return this.apkVersionName;
    }

    @NotNull
    /* renamed from: getContextClsName$mvvm_release, reason: from getter */
    public final String getContextClsName() {
        return this.contextClsName;
    }

    /* renamed from: getDialogButtonColor$mvvm_release, reason: from getter */
    public final int getDialogButtonColor() {
        return this.dialogButtonColor;
    }

    /* renamed from: getDialogButtonTextColor$mvvm_release, reason: from getter */
    public final int getDialogButtonTextColor() {
        return this.dialogButtonTextColor;
    }

    /* renamed from: getDialogImage$mvvm_release, reason: from getter */
    public final int getDialogImage() {
        return this.dialogImage;
    }

    /* renamed from: getDialogProgressBarColor$mvvm_release, reason: from getter */
    public final int getDialogProgressBarColor() {
        return this.dialogProgressBarColor;
    }

    @NotNull
    /* renamed from: getDownloadPath$mvvm_release, reason: from getter */
    public final String getDownloadPath() {
        return this.downloadPath;
    }

    public final boolean getDownloadState() {
        return this.downloadState;
    }

    /* renamed from: getForcedUpgrade$mvvm_release, reason: from getter */
    public final boolean getForcedUpgrade() {
        return this.forcedUpgrade;
    }

    @Nullable
    /* renamed from: getHttpManager$mvvm_release, reason: from getter */
    public final BaseHttpDownloadManager getHttpManager() {
        return this.httpManager;
    }

    /* renamed from: getJumpInstallPage$mvvm_release, reason: from getter */
    public final boolean getJumpInstallPage() {
        return this.jumpInstallPage;
    }

    @Nullable
    /* renamed from: getNotificationChannel$mvvm_release, reason: from getter */
    public final NotificationChannel getNotificationChannel() {
        return this.notificationChannel;
    }

    /* renamed from: getNotifyId$mvvm_release, reason: from getter */
    public final int getNotifyId() {
        return this.notifyId;
    }

    @Nullable
    /* renamed from: getOnButtonClickListener$mvvm_release, reason: from getter */
    public final OnButtonClickListener getOnButtonClickListener() {
        return this.onButtonClickListener;
    }

    @NotNull
    public final List<OnDownloadListener> getOnDownloadListeners$mvvm_release() {
        return this.onDownloadListeners;
    }

    /* renamed from: getShowBgdToast$mvvm_release, reason: from getter */
    public final boolean getShowBgdToast() {
        return this.showBgdToast;
    }

    /* renamed from: getShowNotification$mvvm_release, reason: from getter */
    public final boolean getShowNotification() {
        return this.showNotification;
    }

    /* renamed from: getSmallIcon$mvvm_release, reason: from getter */
    public final int getSmallIcon() {
        return this.smallIcon;
    }

    public final void release$mvvm_release() {
        BaseHttpDownloadManager baseHttpDownloadManager = this.httpManager;
        if (baseHttpDownloadManager != null) {
            baseHttpDownloadManager.release();
        }
        clearListener();
        instance = null;
    }

    public final void setApkDescription$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.apkDescription = str;
    }

    public final void setApkMD5$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.apkMD5 = str;
    }

    public final void setApkName$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.apkName = str;
    }

    public final void setApkSize$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.apkSize = str;
    }

    public final void setApkUrl$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.apkUrl = str;
    }

    public final void setApkVersionName$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.apkVersionName = str;
    }

    public final void setContextClsName$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.contextClsName = str;
    }

    public final void setDialogButtonColor$mvvm_release(int i2) {
        this.dialogButtonColor = i2;
    }

    public final void setDialogButtonTextColor$mvvm_release(int i2) {
        this.dialogButtonTextColor = i2;
    }

    public final void setDialogImage$mvvm_release(int i2) {
        this.dialogImage = i2;
    }

    public final void setDialogProgressBarColor$mvvm_release(int i2) {
        this.dialogProgressBarColor = i2;
    }

    public final void setDownloadPath$mvvm_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.downloadPath = str;
    }

    public final void setDownloadState(boolean z2) {
        this.downloadState = z2;
    }

    public final void setForcedUpgrade$mvvm_release(boolean z2) {
        this.forcedUpgrade = z2;
    }

    public final void setHttpManager$mvvm_release(@Nullable BaseHttpDownloadManager baseHttpDownloadManager) {
        this.httpManager = baseHttpDownloadManager;
    }

    public final void setJumpInstallPage$mvvm_release(boolean z2) {
        this.jumpInstallPage = z2;
    }

    public final void setNotificationChannel$mvvm_release(@Nullable NotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
    }

    public final void setNotifyId$mvvm_release(int i2) {
        this.notifyId = i2;
    }

    public final void setOnButtonClickListener$mvvm_release(@Nullable OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public final void setOnDownloadListeners$mvvm_release(@NotNull List<OnDownloadListener> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.onDownloadListeners = list;
    }

    public final void setShowBgdToast$mvvm_release(boolean z2) {
        this.showBgdToast = z2;
    }

    public final void setShowNotification$mvvm_release(boolean z2) {
        this.showNotification = z2;
    }

    public final void setSmallIcon$mvvm_release(int i2) {
        this.smallIcon = i2;
    }
}
