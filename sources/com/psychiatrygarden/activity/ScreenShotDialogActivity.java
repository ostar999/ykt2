package com.psychiatrygarden.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.event.ScreenShotEvent;
import com.psychiatrygarden.utils.QrCodeUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes5.dex */
public class ScreenShotDialogActivity extends BaseActivity {
    private TextView mBtnCancel;
    private LinearLayout mBtnQq;
    private LinearLayout mBtnSave;
    private LinearLayout mBtnSina;
    private LinearLayout mBtnWechat;
    private LinearLayout mBtnWxCircle;
    private int mImageHeight;
    private String mImagePath;
    private ImageView mImgCode;
    private ImageView mImgCodeBig;
    private RoundedImageView mImgPic;
    private RoundedImageView mImgPicBig;
    private LinearLayout mLyPicViewBig;

    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triggerReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) throws IOException {
        if (ContextCompat.checkSelfPermission(this, Permission.CAMERA) == 0 && ContextCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE) == 0) {
            saveViewToGallery(this, this.mLyPicViewBig);
        } else {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.ai
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f10995a.lambda$setListenerForWidget$1();
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        shareAppControl(this, 0, this.mImagePath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        shareAppControl(this, 1, this.mImagePath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        shareAppControl(this, 2, this.mImagePath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        shareAppControl(this, 3, this.mImagePath);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$7(Bitmap bitmap, Bitmap bitmap2) {
        this.mImgCode.setImageBitmap(bitmap);
        this.mImgCodeBig.setImageBitmap(bitmap2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrCode$8(int i2, String str, Bitmap bitmap, int i3) {
        final Bitmap bitmapCreateQRcodeImage = QrCodeUtils.createQRcodeImage(i2, i2, str, bitmap);
        final Bitmap bitmapCreateQRcodeImage2 = QrCodeUtils.createQRcodeImage(i3, i3, str, bitmap);
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.bi
            @Override // java.lang.Runnable
            public final void run() {
                this.f11112c.lambda$setQrCode$7(bitmapCreateQRcodeImage, bitmapCreateQRcodeImage2);
            }
        });
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

    private void setQrCode(Context context) {
        final String str = "https://m.yikaobang.com.cn/download/yikaobang.html";
        final Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);
        final int iDp2px = SizeUtil.dp2px(context, 33);
        final int iDp2px2 = SizeUtil.dp2px(context, 56);
        new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.ci
            @Override // java.lang.Runnable
            public final void run() {
                this.f11483c.lambda$setQrCode$8(iDp2px, str, bitmapDecodeResource, iDp2px2);
            }
        }).start();
    }

    private void shareAppControl(final Context context, int i2, String imgPath) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mLyPicViewBig.getWidth(), this.mLyPicViewBig.getHeight(), Bitmap.Config.ARGB_8888);
        this.mLyPicViewBig.draw(new Canvas(bitmapCreateBitmap));
        new ShareAction(this).withMedia(new UMImage(context, bitmapCreateBitmap)).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(new UMShareListener() { // from class: com.psychiatrygarden.activity.ScreenShotDialogActivity.1
            @Override // com.umeng.socialize.UMShareListener
            public void onCancel(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(context, "用户取消分享");
                ScreenShotDialogActivity.this.finish();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onError(SHARE_MEDIA shareMedia, Throwable throwable) {
                ToastUtil.shortToast(context, "未检测到QQ/微信应用或请安装正式版QQ/微信分享！");
                ScreenShotDialogActivity.this.finish();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onResult(SHARE_MEDIA shareMedia) {
                ToastUtil.shortToast(context, "分享成功");
                ScreenShotDialogActivity.this.finish();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onStart(SHARE_MEDIA shareMedia) {
            }
        }).share();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mImagePath = getIntent().getStringExtra("imagePath");
        this.mImageHeight = getIntent().getIntExtra("imgHeight", 0);
        this.mLyPicViewBig = (LinearLayout) findViewById(R.id.ly_pic_view_big);
        this.mImgPic = (RoundedImageView) findViewById(R.id.img_pic);
        this.mImgPicBig = (RoundedImageView) findViewById(R.id.img_pic_big);
        this.mBtnCancel = (TextView) findViewById(R.id.btn_cancel);
        this.mBtnSave = (LinearLayout) findViewById(R.id.btn_save);
        this.mBtnWechat = (LinearLayout) findViewById(R.id.btn_wechat);
        this.mBtnWxCircle = (LinearLayout) findViewById(R.id.btn_wxcircle);
        this.mBtnQq = (LinearLayout) findViewById(R.id.btn_qq);
        this.mBtnSina = (LinearLayout) findViewById(R.id.btn_sina);
        this.mImgCode = (ImageView) findViewById(R.id.img_code);
        this.mImgCodeBig = (ImageView) findViewById(R.id.img_code_big);
        updateImageData(this.mImagePath, this.mImageHeight);
        setQrCode(this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundResource(R.color.transparent);
        super.onCreate(savedInstanceState);
        hideNav();
    }

    public void onEventMainThread(ScreenShotEvent event) {
        Log.e("screen_shot", "截屏event：");
        if (TextUtils.isEmpty(event.imgPath)) {
            return;
        }
        updateImageData(event.imgPath, event.imgHeight);
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
        setContentView(R.layout.layout_screen_shot_window);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.uh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13990c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnSave.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                this.f14030c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mBtnWechat.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14154c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnWxCircle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14186c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnQq.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14218c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnSina.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14251c.lambda$setListenerForWidget$6(view);
            }
        });
    }

    public void updateImageData(String imgPath, int height) {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(imgPath);
        Log.e("screen_shot", "bitmap:" + bitmapDecodeFile);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mImgPicBig.getLayoutParams();
        layoutParams.height = height;
        this.mImgPicBig.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mLyPicViewBig.getLayoutParams();
        layoutParams2.height = height + ScreenUtil.getPxByDp((Context) this, 88);
        this.mLyPicViewBig.setLayoutParams(layoutParams2);
        this.mImgPic.setImageBitmap(bitmapDecodeFile);
        this.mImgPicBig.setImageBitmap(bitmapDecodeFile);
    }
}
