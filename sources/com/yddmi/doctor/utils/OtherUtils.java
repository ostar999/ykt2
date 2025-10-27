package com.yddmi.doctor.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.text.StrPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.blankj.utilcode.util.AppUtils;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.ClipboardUtil;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.toast.Toaster;
import com.mobile.auth.BuildConfig;
import com.noober.background.drawable.DrawableCreator;
import com.psychiatrygarden.utils.MimeTypes;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\bH\u0007J\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0017J\u000e\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0019\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, d2 = {"Lcom/yddmi/doctor/utils/OtherUtils;", "", "()V", "copyToDownloadAndroidQ", "Landroid/net/Uri;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "mimeType", "", "input", "Ljava/io/InputStream;", "fileName", "saveDirName", "dealClipboard", "", "enableNotification", "getGoDrawable", "Landroid/graphics/drawable/Drawable;", AliyunLogKey.KEY_OBJECT_KEY, "", "radius", "", "getHttpHeaderMap", "", "getMIMEType", "f", "Ljava/io/File;", "openFile", "path", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nOtherUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OtherUtils.kt\ncom/yddmi/doctor/utils/OtherUtils\n+ 2 Uri.kt\nandroidx/core/net/UriKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,279:1\n36#2:280\n29#2:282\n1#3:281\n*S KotlinDebug\n*F\n+ 1 OtherUtils.kt\ncom/yddmi/doctor/utils/OtherUtils\n*L\n147#1:280\n218#1:282\n*E\n"})
/* loaded from: classes6.dex */
public final class OtherUtils {

    @NotNull
    public static final OtherUtils INSTANCE = new OtherUtils();

    private OtherUtils() {
    }

    @RequiresApi(api = 29)
    @Nullable
    public final Uri copyToDownloadAndroidQ(@NotNull Context context, @Nullable String mimeType, @NotNull InputStream input, @NotNull String fileName, @NotNull String saveDirName) throws IOException {
        OutputStream outputStreamOpenOutputStream;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(saveDirName, "saveDirName");
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + saveDirName, fileName);
        if (file.exists()) {
            Uri uriFromFile = Uri.fromFile(file);
            Intrinsics.checkNotNullExpressionValue(uriFromFile, "fromFile(this)");
            return uriFromFile;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", fileName);
        contentValues.put("mime_type", mimeType);
        contentValues.put("relative_path", "Download/" + new Regex("/").replace(saveDirName, "") + "/");
        Uri EXTERNAL_CONTENT_URI = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriInsert = contentResolver.insert(EXTERNAL_CONTENT_URI, contentValues);
        if (uriInsert == null) {
            return null;
        }
        try {
            outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (outputStreamOpenOutputStream == null) {
            return null;
        }
        byte[] bArr = new byte[R2.attr.ease_round_show_right_down];
        while (true) {
            int i2 = input.read(bArr);
            if (i2 == -1) {
                break;
            }
            outputStreamOpenOutputStream.write(bArr, 0, i2);
        }
        return uriInsert;
    }

    public final void dealClipboard() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (companion.getInstance().userIsLogin() || !companion.getInstance().userCanTpush()) {
            return;
        }
        GlobalAction globalAction = GlobalAction.INSTANCE;
        LogExtKt.logd("dealClipboard() 是否读取剪切板:" + globalAction.getLoginShareCodeReadClipboard(), "dealClipboard");
        if (globalAction.getLoginShareCodeReadClipboard()) {
            String str = ClipboardUtil.getTextFromClipboard(ContextManager.INSTANCE.getInstance().getContext());
            Intrinsics.checkNotNullExpressionValue(str, "str");
            if (StringsKt__StringsJVMKt.startsWith$default(str, YddConfig.shareCodeHost, false, 2, null)) {
                String strSubstring = str.substring(22);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                if (strSubstring.length() > 0) {
                    companion.getInstance().loginShareCodeSet(strSubstring);
                }
                if (AppInformationUtil.isApkDebugable()) {
                    Toaster.showLong((CharSequence) ("剪切板邀请码:" + strSubstring));
                    return;
                }
                return;
            }
            if (StringsKt__StringsJVMKt.startsWith$default(str, YddConfig.shareCodeHostMore, false, 2, null)) {
                Uri uri = Uri.parse(str);
                Intrinsics.checkNotNullExpressionValue(uri, "parse(this)");
                String queryParameter = uri.getQueryParameter("shareCode");
                if (queryParameter == null) {
                    queryParameter = "";
                }
                String queryParameter2 = uri.getQueryParameter("fromType");
                LogExtKt.logd("剪切板参数读取：" + queryParameter + "  " + (queryParameter2 != null ? queryParameter2 : ""), YddConfig.TAG);
                if (queryParameter.length() > 0) {
                    companion.getInstance().loginShareCodeSet(queryParameter);
                }
                if (AppInformationUtil.isApkDebugable()) {
                    Toaster.showLong((CharSequence) ("剪切板邀请码:" + queryParameter));
                }
            }
        }
    }

    public final void enableNotification(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
            intent.putExtra("android.provider.extra.CHANNEL_ID", context.getApplicationInfo().uid);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            context.startActivity(intent);
        } catch (Exception unused) {
            Intent intent2 = new Intent();
            intent2.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts("package", context.getPackageName(), null));
            context.startActivity(intent2);
        }
    }

    @NotNull
    public final Drawable getGoDrawable(boolean ok, float radius) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (!ok) {
            Drawable drawableBuild = new DrawableCreator.Builder().setCornersRadius(radius).setSolidColor(ContextExtKt.getColorM(ContextManager.INSTANCE.getInstance().getContext(), R.color.c_dfe0e6)).build();
            Intrinsics.checkNotNullExpressionValue(drawableBuild, "Builder().setCornersRadi…e6)\n            ).build()");
            return drawableBuild;
        }
        DrawableCreator.Builder gradientAngle = new DrawableCreator.Builder().setCornersRadius(radius).setGradientAngle(270);
        ContextManager.Companion companion = ContextManager.INSTANCE;
        Drawable drawableBuild2 = gradientAngle.setGradientColor(ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.c_69d2ff), ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.c_416bc3)).build();
        Intrinsics.checkNotNullExpressionValue(drawableBuild2, "Builder().setCornersRadi…                ).build()");
        return drawableBuild2;
    }

    @NotNull
    public final Map<String, String> getHttpHeaderMap() throws UnsupportedEncodingException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            String strValueOf = String.valueOf(DateUtil.getTimeInMillisLong());
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "randomUUID().toString()");
            String strSubstring = StringsKt__StringsJVMKt.replace$default(string, "-", "", false, 4, (Object) null).substring(0, 10);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            byte[] bytes = (strSubstring + YddConfig.yddCodeSecret + strValueOf).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            String string2 = Base64.encode(bytes).toString();
            String strSubstring2 = string2.substring(24, 40);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
            String strSubstring3 = string2.substring(0, 16);
            Intrinsics.checkNotNullExpressionValue(strSubstring3, "this as java.lang.String…ing(startIndex, endIndex)");
            String a5 = AESUtil.encrypt(YddConfig.yddCodeSecret1, strSubstring2, strSubstring3, "AES/CBC/PKCS5Padding");
            Intrinsics.checkNotNullExpressionValue(a5, "a5");
            String encrypted = URLEncoder.encode(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(a5, Marker.ANY_NON_NULL_MARKER, "-", false, 4, (Object) null), "/", StrPool.UNDERLINE, false, 4, (Object) null), "=", "", false, 4, (Object) null), "UTF-8");
            linkedHashMap.put("timestat", strValueOf);
            linkedHashMap.put("requestId", string);
            Intrinsics.checkNotNullExpressionValue(encrypted, "encrypted");
            linkedHashMap.put("requestToken", encrypted);
        } catch (Exception e2) {
            LogExtKt.logd("签名加密异常：" + e2, "OtherUtils");
        }
        return linkedHashMap;
    }

    @NotNull
    public final String getMIMEType(@NotNull File f2) {
        Intrinsics.checkNotNullParameter(f2, "f");
        String name = f2.getName();
        Intrinsics.checkNotNullExpressionValue(name, "f.name");
        String strSubstring = name.substring(StringsKt__StringsKt.lastIndexOf$default((CharSequence) name, StrPool.DOT, 0, false, 6, (Object) null) + 1, name.length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        String lowerCase = strSubstring.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        switch (lowerCase.hashCode()) {
            case 52316:
                return !lowerCase.equals("3gp") ? MimeTypes.ANY_TYPE : MimeTypes.VIDEO_ALL;
            case 96796:
                return !lowerCase.equals("apk") ? MimeTypes.ANY_TYPE : "application/vnd.android.package-archive";
            case 97669:
                return !lowerCase.equals(ImgUtil.IMAGE_TYPE_BMP) ? MimeTypes.ANY_TYPE : MimeTypes.IMAGE_ALL;
            case 99640:
                return !lowerCase.equals("doc") ? MimeTypes.ANY_TYPE : MimeTypes.APP_MSWORD;
            case 100882:
                return !lowerCase.equals("exe") ? MimeTypes.ANY_TYPE : "application/octet-stream";
            case 102340:
                return !lowerCase.equals(ImgUtil.IMAGE_TYPE_GIF) ? MimeTypes.ANY_TYPE : MimeTypes.IMAGE_ALL;
            case 105441:
                return !lowerCase.equals("jpg") ? MimeTypes.ANY_TYPE : MimeTypes.IMAGE_ALL;
            case 106458:
                return !lowerCase.equals("m4a") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 107332:
                return !lowerCase.equals(BuildConfig.FLAVOR_type) ? MimeTypes.ANY_TYPE : "text/plain";
            case 108104:
                return !lowerCase.equals("mid") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 108272:
                return !lowerCase.equals("mp3") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 108273:
                return !lowerCase.equals("mp4") ? MimeTypes.ANY_TYPE : MimeTypes.VIDEO_ALL;
            case 108308:
                return !lowerCase.equals("mov") ? MimeTypes.ANY_TYPE : MimeTypes.VIDEO_ALL;
            case 109967:
                return !lowerCase.equals("ogg") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 110834:
                return !lowerCase.equals("pdf") ? MimeTypes.ANY_TYPE : MimeTypes.APP_PDF;
            case 111145:
                return !lowerCase.equals("png") ? MimeTypes.ANY_TYPE : MimeTypes.IMAGE_ALL;
            case 111220:
                return !lowerCase.equals("ppt") ? MimeTypes.ANY_TYPE : "application/vnd.ms-powerpoint";
            case 114597:
                return !lowerCase.equals(ArchiveStreamFactory.TAR) ? MimeTypes.ANY_TYPE : "application/x-tar";
            case 114791:
                return !lowerCase.equals("tgz") ? MimeTypes.ANY_TYPE : "application/x-compressed";
            case 115312:
                return !lowerCase.equals("txt") ? MimeTypes.ANY_TYPE : "text/plain";
            case 117484:
                return !lowerCase.equals("wav") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 118783:
                return !lowerCase.equals("xls") ? MimeTypes.ANY_TYPE : MimeTypes.APP_EXCEL;
            case 118801:
                return !lowerCase.equals("xmf") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 118807:
                return !lowerCase.equals(AliyunVodHttpCommon.Format.FORMAT_XML) ? MimeTypes.ANY_TYPE : "text/plain";
            case 120609:
                return !lowerCase.equals("zip") ? MimeTypes.ANY_TYPE : "application/x-zip-compressed";
            case 3088960:
                return !lowerCase.equals("docx") ? MimeTypes.ANY_TYPE : MimeTypes.APP_DOCX;
            case 3268712:
                return !lowerCase.equals("jpeg") ? MimeTypes.ANY_TYPE : MimeTypes.IMAGE_ALL;
            case 3358096:
                return !lowerCase.equals("mpg4") ? MimeTypes.ANY_TYPE : MimeTypes.VIDEO_ALL;
            case 3358141:
                return !lowerCase.equals("mpga") ? MimeTypes.ANY_TYPE : MimeTypes.AUDIO_ALL;
            case 3447940:
                return !lowerCase.equals("pptx") ? MimeTypes.ANY_TYPE : "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case 3682393:
                return !lowerCase.equals("xlsx") ? MimeTypes.ANY_TYPE : MimeTypes.APP_XLSX;
            default:
                return MimeTypes.ANY_TYPE;
        }
    }

    @Nullable
    public final Object openFile(@NotNull String str, @NotNull Continuation<? super Unit> continuation) {
        File file;
        Uri uriFromFile;
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(268435456);
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 29) {
                file = new File(str);
                uriFromFile = copyToDownloadAndroidQ(ContextManager.INSTANCE.getInstance().getContext(), getMIMEType(file), new FileInputStream(file), String.valueOf(DateUtil.getTimeInMillis()), "ydd");
            } else if (i2 >= 24) {
                file = new File(str);
                intent.setFlags(1);
                uriFromFile = FileProvider.getUriForFile(ContextManager.INSTANCE.getInstance().getContext(), AppUtils.getAppPackageName(), file);
            } else {
                file = new File("file://" + str);
                uriFromFile = Uri.fromFile(file);
            }
            LogExtKt.logd("打开文件" + file.getAbsolutePath() + "\nuri:" + uriFromFile, YddConfig.TAG);
            intent.setDataAndType(uriFromFile, getMIMEType(file));
            ContextManager.INSTANCE.getInstance().getContext().startActivity(intent);
        } catch (Exception e2) {
            Toaster.show(R.string.common_file_cant);
            e2.printStackTrace();
        }
        return Unit.INSTANCE;
    }
}
