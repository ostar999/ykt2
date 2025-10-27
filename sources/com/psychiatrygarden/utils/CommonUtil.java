package com.psychiatrygarden.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.source.VidSts;
import com.aliyun.private_service.PrivateService;
import com.aliyun.sls.android.producer.LogProducerClient;
import com.aliyun.vod.common.utils.UriUtil;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.catchpig.mvvm.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.huawei.hms.push.HmsMessageService;
import com.hyphenate.easeui.constants.EaseConstant;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.enums.PopupAnimation;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.thirdpart.blankj.utilcode.util.RegexUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActivityLifecycleCallbacks;
import com.psychiatrygarden.activity.EditNoteActivity;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.chat.ChatActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.rank.bean.ActivityBean;
import com.psychiatrygarden.activity.rank.utils.UMShareListenerIml;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.z3;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.QuestionListNumBean;
import com.psychiatrygarden.bean.QuestionLocalBean;
import com.psychiatrygarden.bean.VideoHandout;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.CustomLineSpacingSpan;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.NoticePopWindow;
import com.psychiatrygarden.widget.OnSelectListener;
import com.psychiatrygarden.widget.PolyvLiveloginView;
import com.psychiatrygarden.widget.SelectableTextHelper;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.yikaobang.yixue.BuildConfig;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class CommonUtil {
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static long lastClickTime;
    private static long lastClickTimeNew;

    public interface ClickShareLisenter {
        void refreshData();
    }

    public static class SaveImage extends AsyncTask<String, Void, String> {
        public Activity activity;
        public String saveImgUrl;

        public SaveImage(String imgurl, Activity activity) {
            this.saveImgUrl = imgurl;
            this.activity = activity;
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... params) throws IOException {
            try {
                String string = Environment.getExternalStorageDirectory().toString();
                File file = new File(string + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(string + "/Download/" + new Date().getTime() + this.saveImgUrl.substring(this.saveImgUrl.lastIndexOf(StrPool.DOT)));
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.saveImgUrl).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(20000);
                InputStream inputStream = httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : null;
                byte[] bArr = new byte[4096];
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 == -1) {
                        fileOutputStream.close();
                        String str = "图片已保存至：" + file2.getAbsolutePath();
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(file2));
                        this.activity.sendBroadcast(intent);
                        return str;
                    }
                    fileOutputStream.write(bArr, 0, i2);
                }
            } catch (Exception e2) {
                return "保存失败！" + e2.getLocalizedMessage();
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            NewToast.showShort(this.activity, result, 1).show();
        }
    }

    public static String BlurTelNumber(String srcTel) {
        return (srcTel == null || srcTel.length() != 11) ? srcTel : String.format("%1$s****%2$s", srcTel.substring(0, 3), srcTel.substring(7, 11));
    }

    public static String BluridCardNumber(String srcTel) {
        if (srcTel.length() < 5) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = (srcTel.length() - srcTel.substring(0, 4).length()) - srcTel.substring(srcTel.length() - 4).length();
        for (int i2 = 0; i2 < length; i2++) {
            sb.append("*");
        }
        return srcTel.substring(0, 4) + " " + ((Object) sb) + " " + srcTel.substring(srcTel.length() - 4);
    }

    public static Date ConverToDate(String strDate) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(strDate);
    }

    public static void Toast_pop(View v2, int flag) {
        ImageView imageView = new ImageView(v2.getContext());
        if (flag == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (flag == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.utils.j
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                CommonUtil.lambda$Toast_pop$0();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        v2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        v2.getLocalVisibleRect(rect);
        if (flag == 0) {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        } else {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        }
        new Handler().postDelayed(new z3(popupWindow), 1000L);
    }

    public static void addLog(String activity_class, String activity_class_alias, String start_timestamp, String end_timestamp) {
        addLog(activity_class, activity_class_alias, start_timestamp, end_timestamp, "", "", "", "1");
    }

    private static void alertNewVoice(Context context) throws IllegalStateException, IOException, IllegalArgumentException {
        try {
            AssetManager assets = context.getAssets();
            MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptorOpenFd = assets.openFd("sound.mp3");
            mediaPlayer.reset();
            mediaPlayer.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static long convertDateToTimestamp(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr).getTime();
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1L;
        }
    }

    public static String convertDateToYear(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return new SimpleDateFormat("yyyy年M月d日", Locale.getDefault()).format(simpleDateFormat.parse(dateStr));
        } catch (ParseException e2) {
            e2.printStackTrace();
            return dateStr;
        }
    }

    public static String convertNumbersToUpperCase(String input) throws NumberFormatException {
        int i2 = Integer.parseInt(input);
        String[] strArr = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        if (i2 < 10) {
            return strArr[i2];
        }
        if (i2 < 20) {
            return "十" + strArr[i2 % 10];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strArr[i2 / 10]);
        sb.append("十");
        int i3 = i2 % 10;
        sb.append(i3 == 0 ? "" : strArr[i3]);
        return sb.toString();
    }

    public static void copyContent(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        ClipData clipDataNewPlainText = ClipData.newPlainText("Simple text", content);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipDataNewPlainText);
        }
        ToastUtil.shortToast(context, "复制成功");
    }

    public static void copyEncryptedFile(final Context context) {
        AsyncHandler.post(new Runnable() { // from class: com.psychiatrygarden.utils.i
            @Override // java.lang.Runnable
            public final void run() {
                CommonUtil.lambda$copyEncryptedFile$7(context);
            }
        });
    }

    public static SelectableTextHelper copySelect(final Context context, View tv2) throws Resources.NotFoundException {
        try {
            SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder((TextView) tv2).setSelectedColor(context.getResources().getColor(R.color.trans_app_theme_red)).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(context.getResources().getColor(R.color.app_theme_red)).build();
            selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.utils.d
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    CommonUtil.lambda$copySelect$5(context, charSequence);
                }
            });
            return selectableTextHelperBuild;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void delete2File(File file) {
        if (!file.isDirectory()) {
            if (file.exists()) {
                file.delete();
                deleteFile(file);
                return;
            }
            return;
        }
        for (File file2 : file.listFiles()) {
            delete2File(file2);
        }
    }

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.isDirectory()) {
            if (file.exists()) {
                file.delete();
                return;
            }
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            deleteFile(file2);
        }
    }

    public static void dialogNote(View v2, final Activity mContext, String content, final long question_id) {
        if (mContext == null) {
            return;
        }
        View viewInflate = ((LayoutInflater) mContext.getSystemService("layout_inflater")).inflate(R.layout.popu_note, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        TextView textView = (TextView) viewInflate.findViewById(R.id.popu_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.popu_edit);
        ((TextView) viewInflate.findViewById(R.id.tv_note_content)).setText(content);
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommonUtil.lambda$dialogNote$1(popupWindow, view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommonUtil.lambda$dialogNote$2(popupWindow, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommonUtil.lambda$dialogNote$3(mContext, question_id, popupWindow, view);
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.utils.h
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                CommonUtil.lambda$dialogNote$4();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(v2, 80, 0, 0);
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String doDoubleNum(Double value) {
        return new DecimalFormat("#0.00").format(value);
    }

    public static void doPicture(Context context, List<String> lists, int position, ImageView fiv, int type) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(lists);
        ImageViewerPopupViewCustom longPressListener = new ImageViewerPopupViewCustom(context).setSrcView(fiv, position).setImageUrls(arrayList).isInfinite(false).isShowPlaceholder(true).setPlaceholderColor(-1).setPlaceholderStrokeColor(-1).setPlaceholderRadius(-1).isShowSaveButton(true).setBgColor(Color.rgb(32, 36, 46)).setSrcViewUpdateListener(null).setXPopupImageLoader(new ImageLoaderUtilsCustom()).setLongPressListener(null);
        longPressListener.popupInfo = new PopupInfo();
        longPressListener.show();
    }

    public static String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("字符串长度必须是正数");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(62)));
        }
        return sb.toString();
    }

    public static String getCStr() {
        new ArrayList();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.questionids, ProjectApp.instance(), "");
        if ("".equals(strConfig)) {
            return "";
        }
        List<QuestionLocalBean.QLocalBean.QCLocalBean> list = (List) new Gson().fromJson(strConfig, new TypeToken<List<QuestionLocalBean.QLocalBean.QCLocalBean>>() { // from class: com.psychiatrygarden.utils.CommonUtil.8
        }.getType());
        StringBuilder sb = new StringBuilder("");
        for (QuestionLocalBean.QLocalBean.QCLocalBean qCLocalBean : list) {
            if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "").equals("40") && "year".equals(qCLocalBean.getSource()) && !"-1".equals(qCLocalBean.getType())) {
                if (!"".equals(sb.toString())) {
                    sb.append(" and ");
                }
                if ("0".equals(qCLocalBean.getType())) {
                    sb.append("(Year<2017 or Year=30000)");
                } else if ("1".equals(qCLocalBean.getType())) {
                    sb.append("Year>=2017");
                } else if ("2".equals(qCLocalBean.getType())) {
                    sb.append("Year>=2012");
                } else if ("3".equals(qCLocalBean.getType())) {
                    sb.append("Year>=2007");
                } else if ("4".equals(qCLocalBean.getType())) {
                    sb.append("Year>=2002");
                } else if (!"5".equals(qCLocalBean.getType())) {
                    if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO.equals(qCLocalBean.getType())) {
                        sb.append("Year>=1997");
                    } else if ("7".equals(qCLocalBean.getType())) {
                        sb.append("(Year<2019 or Year=30000)");
                    } else if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_PUBLISHVIDEO.equals(qCLocalBean.getType())) {
                        sb.append("(Year<2021 or Year=30000)");
                    } else if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(qCLocalBean.getType())) {
                        sb.append("Year>=2019");
                    }
                }
            }
            if ("question".equals(qCLocalBean.getSource()) && !"-1".equals(qCLocalBean.getType())) {
                if (!"".equals(sb.toString())) {
                    sb.append(" and ");
                }
                if ("0".equals(qCLocalBean.getType())) {
                    sb.append("Type=1");
                } else if ("1".equals(qCLocalBean.getType())) {
                    sb.append("Type=2");
                } else if ("2".equals(qCLocalBean.getType())) {
                    sb.append("Type<3");
                } else if ("3".equals(qCLocalBean.getType())) {
                    sb.append("Type>2");
                }
            }
        }
        return sb.toString();
    }

    public static String getChinese(int key, int length) {
        switch (key) {
            case 0:
                if (length == 1) {
                }
                break;
            case 1:
                if (length == 2) {
                }
                break;
        }
        return "";
    }

    public static void getCourseDownAk(final String vid, final CustomAliPlayerView aliplerView, boolean isShowProgress, final boolean showLoading) {
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.CommonUtil.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (showLoading) {
                    ProjectApp.instance().hideDialogWindow();
                }
                ToastUtil.shortToast(ProjectApp.instance(), "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                if (showLoading) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                if (showLoading) {
                    ProjectApp.instance().hideDialogWindow();
                }
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code"))) {
                        ToastUtil.shortToast(ProjectApp.instance(), jSONObject.optString("message"));
                        return;
                    }
                    if (aliplerView != null) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId"));
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret"));
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st"));
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(vid);
                        vidSts.setRegion(GlobalPlayerConfig.mRegion);
                        vidSts.setAccessKeyId(strDecode);
                        vidSts.setSecurityToken(strDecode3);
                        vidSts.setAccessKeySecret(strDecode2);
                        vidSts.setQuality(AliPlayUtils.getPlayVideoDefinition(aliplerView.getContext()), true);
                        aliplerView.setVidSts(vidSts);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }.progress(isShowProgress, 1));
    }

    public static String getCurrentData() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
    }

    public static String getDataByHourStr(String dateString) {
        return new SimpleDateFormat(DateUtil.TIME_FORMAT_MONTH_DAY_HM, Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static String getDataStr(String dateString) {
        return new SimpleDateFormat(DateUtil.TIME_FORMAT_MONTH_DAY, Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static String getDate(String dateString) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static String getDate2(String dateString) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static String getDateByTimes(String dateString) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static String getDateByTimesTemp(String dateString) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static int getDaysBetween(String startDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return (int) ((simpleDateFormat.parse(endDateStr).getTime() - simpleDateFormat.parse(startDateStr).getTime()) / 86400000);
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static float getFileSize(String fileSize) {
        return Math.round((Float.parseFloat(fileSize) / 1048576.0f) * 100.0f) / 100.0f;
    }

    public static int getGapCount(String startDate, String endDate) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ConverToDate(startDate));
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(ConverToDate(endDate));
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return (int) ((calendar2.getTime().getTime() - calendar.getTime().getTime()) / 86400000);
    }

    public static String getHoursByTimes(String dateString) {
        return new SimpleDateFormat(DateUtil.TIME_FORMAT_WITH_HM, Locale.CHINA).format(new Date(Long.parseLong(dateString) * 1000));
    }

    public static String getHtmlData(Context mContext, String bodyHTML) {
        return "<html>" + (SkinManager.getCurrentSkinType(mContext) == 0 ? "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#ffffff;color:#444;text-align:justify;margin:0;letter-spacing:1.2;line-height:28px;word-break:break-all;}</style></head>" : "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#121622;color:#64729F;text-align:justify;margin:0;letter-spacing:1.2;line-height:28px;word-break:break-all;}</style></head>") + "<body>" + bodyHTML + "</body></html>";
    }

    public static String getHtmlData2(Context mContext, String bodyHTML) {
        return "<html>" + (SkinManager.getCurrentSkinType(mContext) == 0 ? "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#ffffff;color:#444;text-align:justify;margin:0;letter-spacing:1.2;font-weight: bold;line-height:28px;word-break:break-all;}</style></head>" : "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#121622;color:#64729F;text-align:justify;margin:0;letter-spacing:1.2;font-weight: bold;line-height:28px;word-break:break-all;}</style></head>") + "<body>" + bodyHTML + "</body></html>";
    }

    @SuppressLint({"UseCompatLoadingForColorStateLists"})
    public static void getImageData(Context mContext, String stringBuffer, TextView mTextView, String keywords) {
        try {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            if (keywords != null && !"".equals(keywords)) {
                for (String str : keywords.split("\\s+")) {
                    String strReplace = str.replace("\\s+", "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, mContext.getResources().getColorStateList(R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
                        }
                    }
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    public static long getLongSize(String sql) {
        long j2 = 0;
        try {
            Cursor cursorRawQuery = ProjectApp.mDaoSession.getDatabase().rawQuery(sql, new String[0]);
            if (cursorRawQuery != null) {
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        do {
                            j2 = cursorRawQuery.getLong(0);
                        } while (cursorRawQuery.moveToNext());
                    }
                } finally {
                }
            }
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return j2;
    }

    public static Date getNetTime() throws IOException {
        try {
            URLConnection uRLConnectionOpenConnection = new URL("https://www.yikaobang.com.cn/").openConnection();
            uRLConnectionOpenConnection.setReadTimeout(5000);
            uRLConnectionOpenConnection.setConnectTimeout(5000);
            uRLConnectionOpenConnection.connect();
            return new Date(uRLConnectionOpenConnection.getDate());
        } catch (Exception e2) {
            e2.printStackTrace();
            return new Date();
        }
    }

    public static String getNumber(double num) {
        return new BigDecimal(num).setScale(2, 4).doubleValue() + "";
    }

    public static String getProcessName() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/cmdline")));
            String strTrim = bufferedReader.readLine().trim();
            bufferedReader.close();
            return strTrim;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static List<QuestionCategoryBean> getQuestionCategoryList(Context context) {
        List<QuestionCategoryBean> list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.questionCategoryData, context), new TypeToken<List<QuestionCategoryBean>>() { // from class: com.psychiatrygarden.utils.CommonUtil.1
        }.getType());
        return list == null ? new ArrayList() : list;
    }

    public static List<QuestionCategoryBean> getQuestionCategoryListZuTi(Context context) {
        List<QuestionCategoryBean> list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.questionCategoryDataZuTi, context), new TypeToken<List<QuestionCategoryBean>>() { // from class: com.psychiatrygarden.utils.CommonUtil.2
        }.getType());
        return list == null ? new ArrayList() : list;
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static String getSortNum(String chapterid, long question_id) {
        try {
            List list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.mRandomChapterIdValue + chapterid + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), ProjectApp.instance()), new TypeToken<List<Long>>() { // from class: com.psychiatrygarden.utils.CommonUtil.6
            }.getType());
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (question_id == ((Long) list.get(i2)).longValue()) {
                    return (i2 + 1) + "";
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String[] getTimeFromInt(long time) {
        String str;
        String str2;
        String str3;
        String[] strArr = new String[4];
        if (time <= 0) {
            strArr[0] = "0";
            strArr[1] = TarConstants.VERSION_POSIX;
            strArr[2] = TarConstants.VERSION_POSIX;
            strArr[3] = TarConstants.VERSION_POSIX;
            return strArr;
        }
        long j2 = time / 86400;
        long j3 = (time / 3600) % 24;
        long j4 = (time / 60) % 60;
        long j5 = (time / 1) % 60;
        if (j3 < 10) {
            str = "0" + j3;
        } else {
            str = j3 + "";
        }
        if (j4 < 10) {
            str2 = "0" + j4;
        } else {
            str2 = j4 + "";
        }
        if (j5 < 10) {
            str3 = "0" + j5;
        } else {
            str3 = j5 + "";
        }
        strArr[0] = j2 + "";
        strArr[1] = str + "";
        strArr[2] = str2 + "";
        strArr[3] = str3 + "";
        return strArr;
    }

    public static String getTodayDate() {
        return Build.VERSION.SDK_INT >= 26 ? LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getUnitChinese(int length) {
        return length != 2 ? length != 3 ? length != 4 ? "" : "千" : "百" : "十";
    }

    public static String getUrlFromClipboard(Context context) {
        if (context == null) {
            return null;
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        ClipData primaryClip = clipboardManager != null ? clipboardManager.getPrimaryClip() : null;
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        String strValueOf = String.valueOf(primaryClip.getItemAt(0).getText());
        if (Patterns.WEB_URL.matcher(strValueOf).matches()) {
            return strValueOf;
        }
        return null;
    }

    public static String getVideoMd5key(String videourl) {
        String[] strArrSplit = videourl.split("/");
        String str = "";
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (i2 > 2) {
                str = str + "/" + strArrSplit[i2];
            }
        }
        Long lValueOf = Long.valueOf((System.currentTimeMillis() / 1000) + 1800);
        return videourl + "?auth_key=" + lValueOf + "-0-0-" + Md5Util.MD5Encode(str + "-" + lValueOf + "-0-0-" + CommonParameter.Video_wutaojuan_key);
    }

    public static String getVideoMd5keyMyvalue(String videourl, Long ss) {
        String[] strArrSplit = videourl.split("/");
        String str = "";
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (i2 > 2) {
                str = str + "/" + strArrSplit[i2];
            }
        }
        return videourl + "?auth_key=" + ss + "-0-0-" + Md5Util.MD5Encode(str + "-" + ss + "-0-0-" + CommonParameter.Video_wutaojuan_key);
    }

    public static String getWebHtml(Context mContext) {
        return SkinManager.getCurrentSkinType(mContext) == 0 ? "<html><head></head><script type='text/javascript'> function onBodyClick(){javaScxript.onBodyClick();}; function aOnClick( obj ){var aon = document.getElementsByTagName('a'); var alen = aon.length; for(var i=0;i<alen;i++){if(obj==aon[i]){javaScxript.getCoument(obj.getAttribute('url'));window.event.cancelBubble = true;}}};  function prictaction( obj ){ var image = document.getElementsByTagName('img'); var imglen = image.length; for(var i=0;i<imglen;i++){if(obj==image[i]){javaScxript.getPrictData(i);window.event.cancelBubble = true;}}};   </script><body onclick='onBodyClick()' style=\"background-color:#ffffff;color:#000000;text-align:justify;margin:0;letter-spacing:1.2;line-height:28px;word-break:break-all;\">%s</body></html>" : "<html><head></head><script type='text/javascript'> function onBodyClick(){javaScxript.onBodyClick();}; function aOnClick( obj ){var aon = document.getElementsByTagName('a'); var alen = aon.length; for(var i=0;i<alen;i++){if(obj==aon[i]){javaScxript.getCoument(obj.getAttribute('url'));window.event.cancelBubble = true;}}};  function prictaction( obj ){ var image = document.getElementsByTagName('img'); var imglen = image.length; for(var i=0;i<imglen;i++){if(obj==image[i]){javaScxript.getPrictData(i);window.event.cancelBubble = true;}}};   </script><body onclick='onBodyClick()' style=\"background-color:#121622;color:#64729F;text-align:justify;margin:0;letter-spacing:1.2;line-height:28px;word-break:break-all;\">%s</body></html>";
    }

    public static int getYear() {
        return Calendar.getInstance().get(1);
    }

    public static void getvideo(Activity activity) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void gotoLive(Context context, String appIddecrypt, String appSecretDecrypt, String userIdDecrypt, String channel, List<VideoHandout> handoutList) {
        new PolyvLiveloginView(context, appIddecrypt, appSecretDecrypt, userIdDecrypt, channel, handoutList);
    }

    public static String handleCouponsTime(String startTime, String endTime) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long j2 = !TextUtils.isEmpty(startTime) ? Long.parseLong(startTime) : 0L;
        long j3 = TextUtils.isEmpty(endTime) ? 0L : Long.parseLong(endTime);
        if (j2 < jCurrentTimeMillis && jCurrentTimeMillis <= j3) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(Long.parseLong(endTime) * 1000)) + "到期";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String str = simpleDateFormat.format(new Date(Long.parseLong(endTime) * 1000));
        String str2 = simpleDateFormat.format(new Date(Long.parseLong(startTime) * 1000));
        String[] strArrSplit = str2.split("-");
        String[] strArrSplit2 = str.split("-");
        if (strArrSplit2[0].equals("2025")) {
            return str2.substring(5) + "至" + str.substring(5);
        }
        return (strArrSplit[0] + "-" + strArrSplit[1] + "-" + strArrSplit[2]) + "至" + (strArrSplit2[0] + "-" + strArrSplit2[1] + "-" + strArrSplit2[2]);
    }

    public static boolean hasRequiredCameraPermissions(Context context) {
        return ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0;
    }

    public static boolean hasRequiredPermissions(Context context) {
        return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_MEDIA_IMAGES) == 0 : ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE) == 0;
    }

    public static boolean hasRequiredPermissionsNoCamera(Context context) {
        return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(context, Permission.READ_MEDIA_IMAGES) == 0 : ContextCompat.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE) == 0;
    }

    public static boolean hasRequiredPermissionsWriteStorage(Context context) {
        return Build.VERSION.SDK_INT >= 33 || ContextCompat.checkSelfPermission(context, Permission.WRITE_EXTERNAL_STORAGE) == 0;
    }

    public static void hideKeyboard(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static void initDownAliyunVideo(String vid, String akId, String akSecret, String st) {
        try {
            if (TextUtils.isEmpty(vid) || ProjectApp.downloadManager == null) {
                return;
            }
            VidSts vidSts = new VidSts();
            vidSts.setVid(vid);
            vidSts.setAccessKeyId(akId);
            vidSts.setAccessKeySecret(akSecret);
            vidSts.setSecurityToken(st);
            vidSts.setQuality(AliPlayUtils.getDownloadVideoDefinition(ProjectApp.instance().getApplicationContext()), true);
            ProjectApp.downloadManager.prepareDownload(vidSts);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String intToChinese(int number) {
        String strValueOf = String.valueOf(number);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int length = strValueOf.length();
        if (length > 1) {
            double d3 = length - 1;
            spannableStringBuilder.append((CharSequence) getChinese(number / ((int) Math.pow(10.0d, d3)), length)).append((CharSequence) getUnitChinese(length));
            if (number % ((int) Math.pow(10.0d, d3)) == 0) {
                return spannableStringBuilder.toString();
            }
        }
        if (length == 1) {
            spannableStringBuilder.append((CharSequence) getChinese(number, 1));
        }
        if (length == 2) {
            spannableStringBuilder.append((CharSequence) getChinese(number % 10, 0));
        }
        if (length == 3) {
            int i2 = number % 100;
            if (i2 < 10) {
                spannableStringBuilder.append((CharSequence) "零").append((CharSequence) getChinese(i2, 3));
            } else {
                spannableStringBuilder.append((CharSequence) getChinese(i2 / 10, 3)).append((CharSequence) "十").append((CharSequence) getChinese(number % 10, 0));
            }
        }
        if (length == 4) {
            int i3 = number % 1000;
            if (i3 < 10) {
                spannableStringBuilder.append((CharSequence) "零").append((CharSequence) getChinese(i3, 3));
            } else if (i3 < 100) {
                spannableStringBuilder.append((CharSequence) "零").append((CharSequence) getChinese(i3 / 10, 3)).append((CharSequence) "十").append((CharSequence) getChinese(number % 10, 0));
            } else {
                spannableStringBuilder.append((CharSequence) intToChinese(i3));
            }
        }
        return spannableStringBuilder.toString();
    }

    public static boolean isAlphabet(String str) {
        return Pattern.compile("[a-zA-Z]*").matcher(str).matches();
    }

    public static boolean isChinese(String str) {
        return Pattern.compile("[Α-￥]+$").matcher(str).matches();
    }

    public static synchronized boolean isFastClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastClickTime < 800) {
            return true;
        }
        lastClickTime = jCurrentTimeMillis;
        return false;
    }

    public static boolean isFastClickNew() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastClickTimeNew < 300) {
            return true;
        }
        lastClickTimeNew = jCurrentTimeMillis;
        return false;
    }

    public static boolean isMainProcess(Context context) {
        return getProcessName() != null && getProcessName().equals(context.getPackageName());
    }

    public static boolean isMobileSimple(CharSequence input) {
        return RegexUtils.isMatch("^(1\\d{10}|900\\d{8})$", input);
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return activeNetworkInfo.isAvailable();
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static boolean isVip() {
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.vip_available, ProjectApp.instance()).equals("1") || !UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            return true;
        }
        Intent intent = new Intent(ProjectApp.instance(), (Class<?>) MemberCenterActivity.class);
        intent.setFlags(268435456);
        ProjectApp.instance().startActivity(intent);
        return false;
    }

    public static boolean isWifi(Context mContext) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    public static void jump1v1(Context context, String qrcode, String wx_id) {
        jumpWxProgram(context, "subPages/selectSchool/coach1v1?we_chat_id=" + wx_id + "&qr_code=" + qrcode);
    }

    public static void jumpWxProgram(Context context, String page) {
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(context, SdkConstant.getWxAppId());
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_14d59acf6877";
        req.path = page;
        req.miniprogramType = 2;
        iwxapiCreateWXAPI.sendReq(req);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$0() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$copyEncryptedFile$7(Context context) {
        if (!Storage.moveEncryptedToStorage(context.getAssets())) {
            Log.e("saveFile", "onFailed: 复制失败");
        } else {
            PrivateService.initService(context, Storage.getEncryptedFile().getAbsolutePath());
            Log.e("saveFile", "onSuccess: 复制成功");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$copySelect$5(Context context, CharSequence charSequence) {
        try {
            ((ClipboardManager) context.getSystemService("clipboard")).setText(charSequence.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$1(PopupWindow popupWindow, View view) {
        if (isFastClick()) {
            return;
        }
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$2(PopupWindow popupWindow, View view) {
        if (isFastClick()) {
            return;
        }
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$3(Activity activity, long j2, PopupWindow popupWindow, View view) {
        if (isFastClick()) {
            return;
        }
        Intent intent = new Intent(activity, (Class<?>) EditNoteActivity.class);
        intent.putExtra("question_id", j2);
        activity.startActivity(intent);
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$4() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showFristDialog$6(FragmentActivity fragmentActivity, NoticePopWindow noticePopWindow) throws IllegalStateException, IOException, IllegalArgumentException {
        new XPopup.Builder(fragmentActivity).popupAnimation(PopupAnimation.ScaleAlphaFromCenter).dismissOnBackPressed(Boolean.FALSE).dismissOnTouchOutside(Boolean.TRUE).asCustom(noticePopWindow).show();
        alertNewVoice(fragmentActivity);
    }

    public static void launchLiveing(Context context, String userId, String appId, String appSecret, String channel) {
        try {
            new PolyvLiveloginView(context, DesUtil.decode(CommonParameter.DES_KEY_LIVEING, appId), DesUtil.decode(CommonParameter.DES_KEY_LIVEING, appSecret), DesUtil.decode(CommonParameter.DES_KEY_LIVEING, userId), channel);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void launchLiving(Context context, String userId, String appId, String appSecret, String channel, String courseId) {
        try {
            String strDecode = DesUtil.decode(CommonParameter.DES_KEY_LIVEING, userId);
            String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_LIVEING, appId);
            String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_LIVEING, appSecret);
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_ROOM_ID, channel, context);
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_COURSE_ID, courseId, context);
            new PolyvLiveloginView(context, strDecode2, strDecode3, strDecode, channel);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
        if (context != null) {
            GlideApp.with(context).load((Object) GlideUtils.generateUrl(imageUrl)).dontAnimate().diskCacheStrategy(DiskCacheStrategy.RESOURCE).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.utils.CommonUtil.9
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    ImageView imageView2 = imageView;
                    if (imageView2 == null) {
                        return false;
                    }
                    ImageView.ScaleType scaleType = imageView2.getScaleType();
                    ImageView.ScaleType scaleType2 = ImageView.ScaleType.FIT_XY;
                    if (scaleType != scaleType2) {
                        imageView.setScaleType(scaleType2);
                    }
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.height = Math.round(resource.getIntrinsicHeight() * (((imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight()) / resource.getIntrinsicWidth())) + imageView.getPaddingTop() + imageView.getPaddingBottom();
                    imageView.setLayoutParams(layoutParams);
                    return false;
                }
            }).into(imageView);
        }
    }

    public static void mDoDrawable(Activity activity, TextView view, int img, int type) throws Resources.NotFoundException {
        Drawable drawable = activity.getResources().getDrawable(img);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (type == 0) {
            view.setCompoundDrawables(drawable, null, null, null);
            return;
        }
        if (type == 1) {
            view.setCompoundDrawables(null, drawable, null, null);
        } else if (type == 2) {
            view.setCompoundDrawables(null, null, drawable, null);
        } else {
            if (type != 3) {
                return;
            }
            view.setCompoundDrawables(null, null, null, drawable);
        }
    }

    public static void mPlayerData(String vid, CustomAliPlayerView aliplerView, boolean isShowProgress) {
        if (YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            Iterator<AliyunDownloadMediaInfo> it = YkBManager.getInstance().mDownloadMediaLists.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AliyunDownloadMediaInfo next = it.next();
                if (next.getVid().equals(vid)) {
                    if (!TextUtils.isEmpty(next.getSavePath()) && next.getProgress() == 100) {
                        if (new File(next.getSavePath()).exists()) {
                            aliplerView.changePlayLocalSource(next.getSavePath(), "");
                            return;
                        } else {
                            getCourseDownAk(vid, aliplerView, isShowProgress);
                            return;
                        }
                    }
                }
            }
        }
        getCourseDownAk(vid, aliplerView, isShowProgress);
    }

    public static void mPutChatData(Activity mActivity, String mNickName, String username) {
        try {
            Intent intent = new Intent(mActivity, (Class<?>) ChatActivity.class);
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, 1);
            intent.putExtra("name", username);
            intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, mNickName);
            mActivity.startActivity(intent);
        } catch (Exception unused) {
            NewToast.showShort(mActivity, "进入聊天失败！", 0).show();
        }
    }

    public static void mPutShareData(Context mContext, String activity_id, String item_id, String category, ClickShareLisenter lisenter) {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(item_id) && !TextUtils.isEmpty(category)) {
            ajaxParams.put("item_id", "" + item_id);
            ajaxParams.put(UriUtil.QUERY_CATEGORY, category);
        } else if (TextUtils.isEmpty(activity_id)) {
            ajaxParams.put(PushConstants.INTENT_ACTIVITY_NAME, "volunteer_2020");
        } else {
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + activity_id);
        }
        YJYHttpUtils.post(mContext, NetworkRequestsURL.mshareApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.CommonUtil.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                EventBus.getDefault().post(EventBusConstant.EVENT_CHOOSE_SCHOOL_PERMISSION_SHARE);
            }
        });
        lisenter.refreshData();
    }

    public static void mShareData(Activity mContext, String activity_id, ActivityBean.DataBean.ShareInfoBean shareInfoBean, UMShareListenerIml umShareListener, ClickShareLisenter lisenter, MemInterface.ShareSuccessListener successListener) {
        int share_type;
        int iNextInt = shareInfoBean.getShare_type() == 0 ? new Random().nextInt(3) : (shareInfoBean.getShare_type() <= 4 && (share_type = shareInfoBean.getShare_type()) != 1) ? share_type != 2 ? share_type != 3 ? shareInfoBean.getShare_type() : 1 : 0 : 2;
        UMImage uMImage = new UMImage(mContext, shareInfoBean.getShare_img());
        if (shareInfoBean.getShare_img().equals("")) {
            uMImage = new UMImage(mContext, R.drawable.ic_launcher);
        }
        UMWeb uMWeb = new UMWeb(shareInfoBean.getShare_url().trim());
        uMWeb.setTitle(shareInfoBean.getShare_title());
        uMWeb.setDescription(shareInfoBean.getShare_content());
        ArrayList<SnsPlatform> arrayList = ProjectApp.platforms;
        LogUtils.e("share_info", "index:" + iNextInt + ";;;;;==========>" + ProjectApp.platforms.get(iNextInt).mPlatform + ";list=>" + new Gson().toJson(arrayList));
        uMWeb.setThumb(uMImage);
        new ShareAction(mContext).withMedia(uMWeb).setPlatform(arrayList.get(iNextInt).mPlatform).setCallback(umShareListener).share();
        if (successListener == null && activity_id != null) {
            mPutShareData(mContext, activity_id, shareInfoBean.getItem_id(), shareInfoBean.getCategory(), lisenter);
        }
        if (successListener != null) {
            successListener.shareSuccess(activity_id);
        }
    }

    public static void mToastShow(Context context) {
        ToastUtil.shortToast(context, "视频审核中,暂不能操作！");
    }

    public static void mWChat(Activity activity, String coroId, String url) {
        try {
            IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(activity, SdkConstant.getWxAppId());
            if (iwxapiCreateWXAPI.isWXAppInstalled()) {
                iwxapiCreateWXAPI.getWXAppSupportAPI();
                if (iwxapiCreateWXAPI.getWXAppSupportAPI() >= 671090490) {
                    WXOpenCustomerServiceChat.Req req = new WXOpenCustomerServiceChat.Req();
                    req.corpId = DesUtil.decode(CommonParameter.DES_KEY_ALI, coroId);
                    req.url = DesUtil.decode(CommonParameter.DES_KEY_ALI, url);
                    iwxapiCreateWXAPI.sendReq(req);
                }
            } else {
                ToastUtil.shortToast(activity.getApplicationContext(), "您的设备未安装微信客户端");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ToastUtil.shortToast(activity, "启动失败！");
        }
    }

    public static void onlineService(Activity activity, OnlineServiceBean onlineServiceBean) {
        try {
            if ("wechat_enterprise_personal".equals(onlineServiceBean.getCs_type())) {
                mWChat(activity, onlineServiceBean.getWechat_corpid(), onlineServiceBean.getContact());
            } else if ("own_chat".equals(onlineServiceBean.getCs_type())) {
                mPutChatData(activity, "" + DesUtil.decode(CommonParameter.DES_KEY_ALI, onlineServiceBean.getContact() + ""), "" + onlineServiceBean.getCs_name());
            } else if ("leyu".equals(onlineServiceBean.getCs_type())) {
                String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, onlineServiceBean.getContact() + "");
                Intent intent = new Intent(activity, (Class<?>) WebLongSaveActivity.class);
                intent.putExtra("url", "" + strDecode);
                intent.putExtra("title", "" + onlineServiceBean.getCs_name());
                activity.startActivity(intent);
            } else if (AliyunLogCommon.TERMINAL_TYPE.equals(onlineServiceBean.getCs_type())) {
                Intent intent2 = new Intent("android.intent.action.DIAL");
                intent2.setData(Uri.parse(WebView.SCHEME_TEL + onlineServiceBean.getContact()));
                activity.startActivity(intent2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Calendar parseDateToCalendar(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = simpleDateFormat.parse(dateStr);
            if (date != null) {
                calendar.setTime(date);
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return calendar;
    }

    public static long[] putSortData(String chapterid, long[] listlong) {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.mRandomChapterIdValue + chapterid + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()), ProjectApp.instance());
        if (!"".equals(strConfig)) {
            ArrayList arrayList = new ArrayList();
            try {
                List list = (List) new Gson().fromJson(strConfig, new TypeToken<List<Long>>() { // from class: com.psychiatrygarden.utils.CommonUtil.7
                }.getType());
                for (int i2 = 0; i2 < list.size(); i2++) {
                    for (long j2 : listlong) {
                        if (j2 == ((Long) list.get(i2)).longValue()) {
                            QuestionListNumBean questionListNumBean = new QuestionListNumBean();
                            questionListNumBean.setQuestion_id(j2);
                            questionListNumBean.setNumpage(i2 + 1);
                            arrayList.add(questionListNumBean);
                        }
                    }
                }
                if (arrayList.size() > 0) {
                    Collections.sort(arrayList);
                    long[] jArr = new long[arrayList.size()];
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        jArr[i3] = ((QuestionListNumBean) arrayList.get(i3)).getQuestion_id();
                    }
                    return jArr;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return new long[0];
    }

    public static void putTimeData() {
        String userId = UserConfig.getUserId();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.forum_time, ProjectApp.instance(), "");
        final String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.forum_time_end, ProjectApp.instance(), "");
        String strConfig3 = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance());
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(strConfig)) {
            return;
        }
        if (TextUtils.isEmpty(strConfig2)) {
            strConfig2 = (System.currentTimeMillis() / 1000) + "";
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("enter_time", "" + strConfig);
        ajaxParams.put("out_time", "" + strConfig2);
        if ("40".equals(strConfig3)) {
            ajaxParams.put("module_type", "16");
        } else {
            ajaxParams.put("module_type", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR);
        }
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getactiveApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.CommonUtil.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time_end, strConfig2 + "", ProjectApp.instance());
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time, "", ProjectApp.instance());
                        SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time_end, "", ProjectApp.instance());
                    } else {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.forum_time_end, strConfig2 + "", ProjectApp.instance());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static void roteImg(ImageView imageView, int repeatcount) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(720L);
        rotateAnimation.setRepeatCount(repeatcount);
        rotateAnimation.setFillAfter(true);
        imageView.setAnimation(rotateAnimation);
    }

    public static void setHideAnimation(final LinearLayout line_c_filtrate, final LinearLayout linecicle, final LinearLayout linepai, final View view, final TextView titleluntan, int duration) {
        if (view == null || duration < 0) {
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.utils.CommonUtil.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation arg0) {
                linepai.setVisibility(0);
                line_c_filtrate.setVisibility(0);
                view.setVisibility(8);
                view.setClickable(false);
                view.setEnabled(false);
                if ("1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.isHideExp, titleluntan.getContext()))) {
                    titleluntan.setVisibility(0);
                    linecicle.setVisibility(8);
                } else {
                    titleluntan.setVisibility(8);
                    linecicle.setVisibility(0);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation arg0) {
            }
        });
        view.startAnimation(alphaAnimation);
    }

    public static void setParagraphSpacing(TextView textView, String rawText, int extraSpacePx) {
        String[] strArrSplit = rawText.split("\n");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append((CharSequence) strArrSplit[i2]);
            int length2 = spannableStringBuilder.length();
            if (i2 != strArrSplit.length - 1) {
                spannableStringBuilder.setSpan(new CustomLineSpacingSpan(extraSpacePx), length, length2, 33);
                spannableStringBuilder.append((CharSequence) "\n");
            }
        }
        textView.setText(spannableStringBuilder);
    }

    public static void setShowAnimation(final View view, int duration) {
        if (view == null || duration < 0) {
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.utils.CommonUtil.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation arg0) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation arg0) {
                view.setVisibility(0);
                view.setClickable(true);
                view.setEnabled(true);
            }
        });
        view.startAnimation(alphaAnimation);
    }

    public static SpannableStringBuilder setSpanImg(Context context, int drawable, int start, int end, int flag, String txt) throws Resources.NotFoundException {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(txt);
        Drawable drawable2 = context.getResources().getDrawable(drawable);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        spannableStringBuilder.setSpan(new StickerSpan(drawable2, 1), start, end, flag);
        return spannableStringBuilder;
    }

    public static void showDialog(Activity activity, String fileUrl) {
        if (Build.VERSION.SDK_INT <= 23) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(fileUrl)));
            intent.setType(MimeTypes.ANY_TYPE);
            activity.startActivity(Intent.createChooser(intent, "分享到："));
            return;
        }
        Uri uriForFile = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", new File(fileUrl));
        Intent intent2 = new Intent("android.intent.action.SEND");
        intent2.putExtra("android.intent.extra.STREAM", uriForFile);
        intent2.setType(MimeTypes.ANY_TYPE);
        activity.startActivity(Intent.createChooser(intent2, "分享到："));
    }

    public static void showFristDialog(JSONObject ob) {
        final FragmentActivity fragmentActivity = (FragmentActivity) ((ActivityLifecycleCallbacks) ProjectApp.instance().activityLifecycleCallbacks).current();
        if (fragmentActivity != null) {
            if (!"1".equals(ob.optString("push"))) {
                String strOptString = ob.optString("push_text_web_view");
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                ToastUtil.showToastView(fragmentActivity, strOptString);
                return;
            }
            String strOptString2 = ob.optString("push_img");
            if (TextUtils.isEmpty(strOptString2) || !strOptString2.startsWith("http")) {
                return;
            }
            final NoticePopWindow noticePopWindow = new NoticePopWindow(fragmentActivity, strOptString2, ob.optString("consecutive_posting_str"), ob.optString("push_url"), null);
            fragmentActivity.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.utils.k
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException, IOException, IllegalArgumentException {
                    CommonUtil.lambda$showFristDialog$6(fragmentActivity, noticePopWindow);
                }
            });
        }
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static boolean validateEndTime(Context context, String start_time, String end_time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(start_time);
            Date date2 = simpleDateFormat.parse(end_time);
            if (getDaysBetween(start_time, end_time) >= 365) {
                NewToast.showShort(context, "规划周期不能大于365天");
                return false;
            }
            if (!date2.before(date)) {
                return true;
            }
            NewToast.showShort(context, "结束时间必须大于开始时间");
            return false;
        } catch (ParseException e2) {
            e2.printStackTrace();
            ToastUtil.shortToast(context, "日期格式错误");
            return false;
        }
    }

    public static void addLog(String activity_class, String activity_class_alias, String target_id, String target_alias, String start_timestamp, String end_timestamp, String target_data) {
        addLog(activity_class, activity_class_alias, start_timestamp, end_timestamp, target_id, target_alias, target_data, "1");
    }

    public static void addLog(String activity_class, String activity_class_alias, String start_timestamp, String end_timestamp, String target_id, String target_alias, String target_data, String event_type) {
        com.aliyun.sls.android.producer.Log log = new com.aliyun.sls.android.producer.Log();
        log.putContent(AliyunLogKey.KEY_UUID, Md5Util.MD5Encode(AndroidBaseUtils.getIMEI(ProjectApp.instance()) + System.currentTimeMillis() + generate(10) + activity_class));
        log.putContent("user_id", UserConfig.getUserId());
        log.putContent("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
        log.putContent(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, ProjectApp.instance()));
        log.putContent("app_type", BuildConfig.FLAVOR);
        log.putContent("app_client", "android");
        log.putContent("event_type", event_type);
        log.putContent("activity_class", activity_class);
        log.putContent("activity_class_alias", activity_class_alias);
        log.putContent("device_model", AndroidBaseUtils.getDeviceName());
        try {
            log.putContent("app_version", AndroidBaseUtils.getAPPVersionCode(ProjectApp.instance()));
        } catch (Exception unused) {
        }
        log.putContent("start_timestamp", start_timestamp);
        if (!end_timestamp.isEmpty()) {
            log.putContent("end_timestamp", end_timestamp);
        }
        if (!target_id.isEmpty()) {
            log.putContent("target_id", target_id);
        }
        if (!target_alias.isEmpty()) {
            log.putContent("target_alias", target_alias);
        }
        if (!target_data.isEmpty()) {
            log.putContent("target_data", target_data);
        }
        for (Map.Entry<String, String> entry : log.getContent().entrySet()) {
            entry.getKey();
            entry.getValue();
        }
        LogProducerClient logProducerClient = HomePageNewActivity.client;
        if (logProducerClient != null) {
            logProducerClient.addLog(log);
        }
    }

    public static void getCourseDownAk(final String vid, final CustomAliPlayerView aliplerView, boolean isShowProgress) {
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.CommonUtil.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                ToastUtil.shortToast(ProjectApp.instance(), "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                ProjectApp.instance().hideDialogWindow();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code"))) {
                        ToastUtil.shortToast(ProjectApp.instance(), jSONObject.optString("message"));
                    } else if (aliplerView != null) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId"));
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret"));
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st"));
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(vid);
                        vidSts.setRegion(GlobalPlayerConfig.mRegion);
                        vidSts.setAccessKeyId(strDecode);
                        vidSts.setSecurityToken(strDecode3);
                        vidSts.setAccessKeySecret(strDecode2);
                        vidSts.setQuality(AliPlayUtils.getPlayVideoDefinition(aliplerView.getContext()), true);
                        aliplerView.setVidSts(vidSts);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }.progress(isShowProgress, 1));
    }

    public static int getTodayDate(String format) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern(format)));
        }
        return Integer.parseInt(new SimpleDateFormat(format).format(new Date()));
    }

    public static void launchLiving(final Context context, String userId, String appId, String appSecret, final String channel, String courseId, String liveId) {
        try {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context));
            ajaxParams.put("live_id", liveId);
            final String strDecode = DesUtil.decode(CommonParameter.DES_KEY_LIVEING, userId);
            final String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_LIVEING, appId);
            final String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_LIVEING, appSecret);
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_ROOM_ID, liveId, context);
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_COURSE_ID, courseId, context);
            YJYHttpUtils.post(context, NetworkRequestsURL.liveHandout, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.utils.CommonUtil.13
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    CommonUtil.gotoLive(context, strDecode2, strDecode3, strDecode, channel, new ArrayList());
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass13) s2);
                    try {
                        JSONObject jSONObject = new JSONObject(s2);
                        if ("200".equals(jSONObject.optString("code"))) {
                            CommonUtil.gotoLive(context, strDecode2, strDecode3, strDecode, channel, (List) new Gson().fromJson(jSONObject.optString("data", ""), new TypeToken<List<VideoHandout>>() { // from class: com.psychiatrygarden.utils.CommonUtil.13.1
                            }.getType()));
                        } else {
                            CommonUtil.gotoLive(context, strDecode2, strDecode3, strDecode, channel, new ArrayList());
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        CommonUtil.gotoLive(context, strDecode2, strDecode3, strDecode, channel, new ArrayList());
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static SpannableStringBuilder setSpanImg(Context context, int drawable, float scaleFactor, int start, int end, int flag, String txt) throws Resources.NotFoundException {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(txt);
        Drawable drawable2 = context.getResources().getDrawable(drawable);
        drawable2.setBounds(0, 0, (int) (drawable2.getIntrinsicWidth() * scaleFactor), (int) (drawable2.getIntrinsicHeight() * scaleFactor));
        spannableStringBuilder.setSpan(new StickerSpan(drawable2, 1), start, end, flag);
        return spannableStringBuilder;
    }

    public static void mPlayerData(String vid, CustomAliPlayerView aliplerView, boolean isShowProgress, boolean showLoading) {
        if (YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            Iterator<AliyunDownloadMediaInfo> it = YkBManager.getInstance().mDownloadMediaLists.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AliyunDownloadMediaInfo next = it.next();
                if (next.getVid().equals(vid)) {
                    if (!TextUtils.isEmpty(next.getSavePath()) && next.getProgress() == 100) {
                        if (new File(next.getSavePath()).exists()) {
                            aliplerView.changePlayLocalSource(next.getSavePath(), "");
                            return;
                        } else {
                            getCourseDownAk(vid, aliplerView, isShowProgress, showLoading);
                            return;
                        }
                    }
                }
            }
        }
        getCourseDownAk(vid, aliplerView, isShowProgress, showLoading);
    }

    public static void showFristDialog(JSONObject ob, NoticePopWindow.NoticeClickIml mNoticeClickIml) {
        FragmentActivity fragmentActivity = (FragmentActivity) ((ActivityLifecycleCallbacks) ProjectApp.instance().activityLifecycleCallbacks).current();
        if (fragmentActivity != null) {
            if ("1".equals(ob.optString("push"))) {
                new XPopup.Builder(fragmentActivity).popupAnimation(PopupAnimation.ScaleAlphaFromCenter).dismissOnBackPressed(Boolean.FALSE).dismissOnTouchOutside(Boolean.TRUE).asCustom(new NoticePopWindow(fragmentActivity, ob.optString("push_img"), ob.optString("consecutive_posting_str"), ob.optString("push_url"), mNoticeClickIml)).show();
            } else {
                mNoticeClickIml.mNoticeClick();
            }
        }
    }

    public static void showDialog(Activity activity, String fileUrl, String type) {
        String strFromExtension = MimeTypes.fromExtension(type);
        if (Build.VERSION.SDK_INT > 23) {
            Uri uriForFile = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", new File(fileUrl));
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setType(strFromExtension);
            activity.startActivity(Intent.createChooser(intent, "分享到："));
            return;
        }
        Intent intent2 = new Intent("android.intent.action.SEND");
        intent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(fileUrl)));
        intent2.setType(strFromExtension);
        activity.startActivity(Intent.createChooser(intent2, "分享到："));
    }

    public static void mShareData(Activity mContext, String activity_id, ActivityBean.DataBean.ShareInfoBean shareInfoBean, UMShareListenerIml umShareListener, ClickShareLisenter lisenter) {
        int share_type;
        int share_type2;
        if (shareInfoBean.getShare_type() == 0) {
            share_type2 = new Random().nextInt(3);
        } else if (shareInfoBean.getShare_type() > 4 || (share_type = shareInfoBean.getShare_type()) == 1) {
            share_type2 = 2;
        } else if (share_type != 2) {
            share_type2 = share_type != 3 ? shareInfoBean.getShare_type() : 1;
        } else {
            share_type2 = 0;
        }
        UMImage uMImage = new UMImage(mContext, shareInfoBean.getShare_img());
        if (shareInfoBean.getShare_img().equals("")) {
            uMImage = new UMImage(mContext, R.drawable.ic_launcher);
        }
        UMWeb uMWeb = new UMWeb(shareInfoBean.getShare_url().trim());
        uMWeb.setTitle(shareInfoBean.getShare_title());
        uMWeb.setDescription(shareInfoBean.getShare_content());
        ArrayList<SnsPlatform> arrayList = ProjectApp.platforms;
        LogUtils.e("share_info", "index:" + share_type2 + ";;;;;==========>" + ProjectApp.platforms.get(share_type2).mPlatform + ";list=>" + new Gson().toJson(arrayList));
        uMWeb.setThumb(uMImage);
        new ShareAction(mContext).withMedia(uMWeb).setPlatform(arrayList.get(share_type2).mPlatform).setCallback(umShareListener).share();
        if (activity_id != null) {
            mPutShareData(mContext, activity_id, shareInfoBean.getItem_id(), shareInfoBean.getCategory(), lisenter);
        }
    }
}
