package com.psychiatrygarden.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.event.ScreenShotEvent;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.QrCodeUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class ShareDialogActivity extends BaseActivity {
    private static final String TAG = "ShareDialogActivity";
    private String bookAuthor;
    private String bookImageUrl;
    private String bookName;
    private String bookShareText;
    private TextView book_author;
    private TextView book_name;
    private RoundedImageView book_share_img;
    private TextView book_share_text;
    private ImageView imgLeftMark;
    private ImageView imgLog2;
    private ImageView imgLogo;
    private ImageView imgRightMark;
    private ImageView imgYkbLogo;
    private LinearLayout layoutContent;
    private RelativeLayout layoutImageRoot;
    private RelativeLayout lyCode;
    private ScrollView lyPicView;
    private LinearLayout lyShare;
    private TextView mBtnCancel;
    private LinearLayout mBtnQq;
    private LinearLayout mBtnSave;
    private LinearLayout mBtnSina;
    private LinearLayout mBtnWechat;
    private LinearLayout mBtnWxCircle;
    private ImageView mImgCode;
    private ImageView shareBotIv;
    private View shareCenterV;
    private FrameLayout shareChangeFl;
    private ImageView shareTopIv;
    private View topEmptyView;
    private String theme = "1";
    private boolean outAnimation = false;

    private void getData() {
        setAnimationIn();
        Uri data = getIntent().getData();
        if (data != null && "ebook".equals(data.getScheme())) {
            this.bookName = data.getQueryParameter("bookName");
            this.bookAuthor = data.getQueryParameter("bookAuthor");
            this.bookImageUrl = data.getQueryParameter("bookImageUrl");
            this.bookShareText = data.getQueryParameter("bookShareText");
            this.theme = data.getQueryParameter("theme");
        }
        GlideUtils.loadImage(this, this.bookImageUrl, this.book_share_img);
        this.book_name.setText(this.bookName);
        this.book_author.setText(this.bookAuthor);
        this.book_share_text.setText("    " + this.bookShareText);
        this.book_share_text.setMovementMethod(new ScrollingMovementMethod());
        Log.d(TAG, "init: --" + this.bookName + "--" + this.bookAuthor + "--" + this.bookImageUrl + "---" + this.bookShareText);
        initTheme(this.theme, this.mContext);
    }

    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triggerReceiver);
    }

    private void initTheme(String theme, Context context) {
        theme.hashCode();
        switch (theme) {
            case "0":
                this.shareBotIv.setImageDrawable(context.getDrawable(R.drawable.icon_white_theme_share_dialog_bottom_bg));
                this.shareTopIv.setImageDrawable(context.getDrawable(R.drawable.icon_white_theme_share_dialog_bg));
                this.imgLeftMark.setImageResource(R.drawable.icon_white_theme_left_mark_svg);
                this.imgRightMark.setImageResource(R.drawable.icon_white_theme_right_mark_svg);
                this.lyShare.setBackgroundResource(R.drawable.white_top_13);
                this.shareCenterV.setBackgroundColor(getColor(R.color.white));
                this.imgYkbLogo.setImageResource(R.mipmap.ic_screen_ykb_logo);
                this.imgLog2.setImageResource(R.mipmap.ic_screen_ykb_info_logo);
                this.imgLogo.setImageDrawable(context.getDrawable(R.drawable.app_icon));
                break;
            case "1":
                this.shareBotIv.setImageDrawable(context.getDrawable(R.drawable.icon_yellow_theme_share_dialog_bottom_bg));
                this.shareTopIv.setImageDrawable(context.getDrawable(R.drawable.icon_yellow_theme_share_dialog_bg));
                this.imgRightMark.setImageResource(R.drawable.icon_yellow_theme_right_mark_svg);
                this.imgLeftMark.setImageResource(R.drawable.icon_yellow_theme_left_mark_svg);
                this.lyShare.setBackgroundResource(R.drawable.yellow_top_13);
                this.shareCenterV.setBackgroundColor(getColor(R.color.color_FFF9E9));
                this.imgYkbLogo.setImageResource(R.mipmap.ic_screen_ykb_logo);
                this.imgLog2.setImageResource(R.mipmap.ic_screen_ykb_info_logo);
                this.imgLogo.setImageDrawable(context.getDrawable(R.drawable.app_icon));
                break;
            case "2":
                this.shareBotIv.setImageDrawable(context.getDrawable(R.drawable.icon_blue_theme_share_dialog_bottom_bg));
                this.shareTopIv.setImageDrawable(context.getDrawable(R.drawable.icon_blue_theme_share_dialog_bg));
                this.imgLeftMark.setImageResource(R.drawable.icon_blue_theme_left_mark_svg);
                this.imgRightMark.setImageResource(R.drawable.icon_blue_theme_right_mark_svg);
                this.book_name.setTextColor(getColor(R.color.color_7380a9));
                this.mBtnCancel.setTextColor(getColor(R.color.color_7380a9));
                TextView textView = (TextView) findViewById(R.id.tv_share_to);
                TextView textView2 = (TextView) findViewById(R.id.tv_weibo);
                TextView textView3 = (TextView) findViewById(R.id.tv_qq);
                TextView textView4 = (TextView) findViewById(R.id.tv_friendship);
                TextView textView5 = (TextView) findViewById(R.id.tv_wechat);
                TextView textView6 = (TextView) findViewById(R.id.tv_save);
                findViewById(R.id.line).setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
                textView6.setTextColor(Color.parseColor("#606A8A"));
                textView5.setTextColor(Color.parseColor("#606A8A"));
                textView4.setTextColor(Color.parseColor("#606A8A"));
                textView2.setTextColor(Color.parseColor("#606A8A"));
                textView3.setTextColor(Color.parseColor("#606A8A"));
                textView.setTextColor(getColor(R.color.color_7380a9));
                this.book_author.setTextColor(getColor(R.color.color_575F79));
                this.book_share_text.setTextColor(getColor(R.color.color_7380a9));
                this.lyShare.setBackgroundResource(R.drawable.night_top_13);
                this.shareCenterV.setBackgroundColor(getColor(R.color.color_121622));
                this.imgYkbLogo.setImageResource(R.mipmap.ic_screen_ykb_logo_night);
                this.imgLog2.setImageResource(R.mipmap.ic_screen_ykb_info_logo_night);
                this.imgLogo.setImageDrawable(context.getDrawable(R.mipmap.app_icon_night));
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchTouchEvent$12(Long l2) throws Exception {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAnimationIn$8(Long l2) throws Exception {
        this.shareChangeFl.setAlpha(0.0f);
        this.shareChangeFl.setVisibility(0);
        this.shareChangeFl.animate().alpha(1.0f).setDuration(400L).setInterpolator(new LinearInterpolator()).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAnimationOut$9(Long l2) throws Exception {
        this.shareChangeFl.setAlpha(1.0f);
        this.shareChangeFl.setVisibility(0);
        this.shareChangeFl.animate().alpha(0.0f).setDuration(400L).setInterpolator(new LinearInterpolator()).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(Long l2) throws Exception {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        setAnimationOut();
        Observable.timer(450L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.kk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f12594c.lambda$setListenerForWidget$0((Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) throws IOException {
        if (ContextCompat.checkSelfPermission(this, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            saveViewToGallery(this, this.layoutImageRoot);
        } else {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.ak
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f10997a.lambda$setListenerForWidget$2();
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        shareAppControl(this, 0, this.bookImageUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        shareAppControl(this, 1, this.bookImageUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        shareAppControl(this, 2, this.bookImageUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        shareAppControl(this, 3, this.bookImageUrl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$10(Bitmap bitmap) {
        this.mImgCode.setImageBitmap(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$11(int i2, String str, Bitmap bitmap, int i3) {
        final Bitmap bitmapCreateQRcodeImage = QrCodeUtils.createQRcodeImage(i2, i2, str, bitmap);
        QrCodeUtils.createQRcodeImage(i3, i3, str, bitmap);
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.ik
            @Override // java.lang.Runnable
            public final void run() {
                this.f12515c.lambda$setQrCode$10(bitmapCreateQRcodeImage);
            }
        });
    }

    private boolean pointNotInsideView(View view, int pointY) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[1];
        return pointY < i2 || pointY > view.getHeight() + i2;
    }

    private void saveViewToGallery(Context context, View view) throws IOException {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + ("ykb_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MediaScannerConnection.scanFile(context, new String[]{str}, null, null);
            ToastUtil.shortToast(context, "保存成功");
            finish();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void setAnimationIn() {
        Observable.timer(0L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.ck
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11627c.lambda$setAnimationIn$8((Long) obj);
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(400L);
        translateAnimation.setFillAfter(true);
        this.lyShare.setVisibility(0);
        this.lyShare.startAnimation(translateAnimation);
    }

    private void setAnimationOut() {
        Observable.timer(0L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.jk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f12564c.lambda$setAnimationOut$9((Long) obj);
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        translateAnimation.setDuration(400L);
        translateAnimation.setFillAfter(true);
        this.lyShare.startAnimation(translateAnimation);
    }

    public static void setFullView(Activity context) {
        context.getWindow().addFlags(1024);
        int i2 = Build.VERSION.SDK_INT;
        context.getWindow().getDecorView().setSystemUiVisibility(1024);
        if (i2 >= 28) {
            WindowManager.LayoutParams attributes = context.getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            context.getWindow().setAttributes(attributes);
        }
    }

    private void setQrCode(Context context) {
        final String str = "https://m.yikaobang.com.cn/download/yikaobang.html";
        final Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);
        final int iDp2px = SizeUtil.dp2px(context, 33);
        final int iDp2px2 = SizeUtil.dp2px(context, 56);
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.bk
            @Override // java.lang.Runnable
            public final void run() {
                this.f11116c.lambda$setQrCode$11(iDp2px, str, bitmapDecodeResource, iDp2px2);
            }
        }).start();
    }

    private void shareAppControl(final Context context, int i2, String imgPath) {
        RelativeLayout relativeLayout = this.layoutImageRoot;
        relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(relativeLayout.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.layoutImageRoot.getHeight(), 1073741824));
        this.layoutImageRoot.layout(0, this.topEmptyView.getHeight(), this.layoutImageRoot.getMeasuredWidth(), this.layoutImageRoot.getMeasuredHeight() + this.topEmptyView.getHeight());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.layoutImageRoot.getMeasuredWidth(), this.layoutImageRoot.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        this.layoutImageRoot.draw(new Canvas(bitmapCreateBitmap));
        new ShareAction(this).withMedia(new UMImage(context, bitmapCreateBitmap)).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(new UMShareListener() { // from class: com.psychiatrygarden.activity.ShareDialogActivity.1
            @Override // com.umeng.socialize.UMShareListener
            public void onCancel(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(context, "用户取消分享");
                ShareDialogActivity.this.finish();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onError(SHARE_MEDIA shareMedia, Throwable throwable) {
                ToastUtil.shortToast(context, "未检测到QQ/微信应用或请安装正式版QQ/微信分享！");
                ShareDialogActivity.this.finish();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onResult(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(context, "分享成功");
                ShareDialogActivity.this.finish();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onStart(SHARE_MEDIA shareMedia) {
            }
        }).share();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, android.app.Activity, android.view.Window.Callback, cn.webdemo.com.supporfragment.ISupportActivity
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int rawY = (int) ev.getRawY();
        if (!pointNotInsideView(this.lyPicView, rawY) || !pointNotInsideView(this.lyShare, rawY) || this.outAnimation) {
            return super.dispatchTouchEvent(ev);
        }
        this.outAnimation = true;
        setAnimationOut();
        Observable.timer(400L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.lk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f12671c.lambda$dispatchTouchEvent$12((Long) obj);
            }
        });
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.layoutImageRoot = (RelativeLayout) findViewById(R.id.layoutImageRoot);
        this.shareChangeFl = (FrameLayout) findViewById(R.id.share_change_fl);
        this.imgYkbLogo = (ImageView) findViewById(R.id.img_ykb_logo);
        this.imgLog2 = (ImageView) findViewById(R.id.imgLog2);
        this.lyCode = (RelativeLayout) findViewById(R.id.ly_code);
        this.imgLeftMark = (ImageView) findViewById(R.id.imgLeftMark);
        this.imgRightMark = (ImageView) findViewById(R.id.imgRightMark);
        this.mBtnCancel = (TextView) findViewById(R.id.btn_cancel);
        this.mBtnSave = (LinearLayout) findViewById(R.id.btn_save);
        this.mBtnWechat = (LinearLayout) findViewById(R.id.btn_wechat);
        this.mBtnWxCircle = (LinearLayout) findViewById(R.id.btn_wxcircle);
        this.mBtnQq = (LinearLayout) findViewById(R.id.btn_qq);
        this.mBtnSina = (LinearLayout) findViewById(R.id.btn_sina);
        this.mImgCode = (ImageView) findViewById(R.id.img_code);
        this.book_share_img = (RoundedImageView) findViewById(R.id.book_share_img);
        this.book_name = (TextView) findViewById(R.id.book_name);
        this.book_author = (TextView) findViewById(R.id.book_author);
        this.book_share_text = (TextView) findViewById(R.id.book_share_text);
        this.layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        this.lyShare = (LinearLayout) findViewById(R.id.ly_share);
        this.lyPicView = (ScrollView) findViewById(R.id.ly_pic_view);
        this.shareBotIv = (ImageView) findViewById(R.id.share_bot_iv);
        this.shareTopIv = (ImageView) findViewById(R.id.share_top_iv);
        this.shareCenterV = findViewById(R.id.share_center_v);
        this.imgLogo = (ImageView) findViewById(R.id.img_logo);
        this.topEmptyView = findViewById(R.id.topEmptyView);
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        setFullView(this);
        super.onCreate(savedInstanceState);
        hideNav();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    public void onEventMainThread(ScreenShotEvent event) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults.length > 0 && grantResults[0] == -1) {
            boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA);
            boolean zShouldShowRequestPermissionRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE);
            if (zShouldShowRequestPermissionRationale || zShouldShowRequestPermissionRationale2) {
                return;
            }
            ToastUtil.shortToast(this, "请检查app相机及存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setSwipeBackEnable(false);
        setContentView(R.layout.layout_share_dialog_ebook);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14253c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnSave.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                this.f12249c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnWechat.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ek
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12324c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnWxCircle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12360c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnQq.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12448c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mBtnSina.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12481c.lambda$setListenerForWidget$7(view);
            }
        });
    }
}
