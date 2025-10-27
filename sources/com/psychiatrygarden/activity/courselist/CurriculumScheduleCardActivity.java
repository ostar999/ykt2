package com.psychiatrygarden.activity.courselist;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumLiveFragment;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumScheduleCardFragment;
import com.psychiatrygarden.activity.courselist.widget.CurriculumPopWindow;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CourseWxPopwindow;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class CurriculumScheduleCardActivity extends BaseActivity {
    public String activity_id;
    public String course_id;
    private String course_id_p;
    public String cover;
    public ImageView downimg2;
    public String have_chapter;
    public ImageView iconBack;
    public boolean isDestroyTrue;
    public String is_hide_teacher;
    public String is_open_qrcode;
    public RelativeLayout parentview;
    public ImageView qrcodeimg;
    public TextView qrshowimg;
    public ArrayList<String> set_meal_ids = new ArrayList<>();
    public String title;
    public TextView titleContent;
    public String type;
    private String type_course;
    public View view1;
    public String wechat_number;
    public String wechat_qrcode;
    public String wechat_tips;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(String str) {
        if (str.equals("我的下载")) {
            EventBus.getDefault().post("downimg2");
        } else {
            getIntentToAc(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        new XPopup.Builder(this).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.courselist.CurriculumScheduleCardActivity.1
            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void beforeShow(BasePopupView popupView) {
                super.beforeShow(popupView);
            }

            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onDismiss(BasePopupView popupView) {
                super.onDismiss(popupView);
            }
        }).atView(this.view1).offsetY(-10).asCustom(new CurriculumPopWindow(this, new CurriculumPopWindow.ClickIml() { // from class: com.psychiatrygarden.activity.courselist.u2
            @Override // com.psychiatrygarden.activity.courselist.widget.CurriculumPopWindow.ClickIml
            public final void onClickView(String str) {
                this.f12177a.lambda$init$1(str);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setQrshowimg$3(View view) {
        if (this.qrshowimg.getAnimation() != null) {
            this.qrshowimg.getAnimation().cancel();
            this.qrshowimg.clearAnimation();
        }
        this.qrshowimg.setVisibility(8);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.Groupcommunication, true, this);
        new XPopup.Builder(this).hasBlurBg(true).asCustom(new CourseWxPopwindow(this, this.wechat_qrcode, this.wechat_tips, this.wechat_number)).toggle();
    }

    private void updateStatusBar(@ColorRes int daycColorInt, @ColorRes int nightColorInt) {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, daycColorInt), 0);
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, nightColorInt), 0);
        }
    }

    public void getIntentToAc(String title) {
        Intent intent = new Intent(this, (Class<?>) CurriculumScheduleCardActivity.class);
        intent.putStringArrayListExtra("set_meal_id", this.set_meal_ids);
        intent.putExtra("course_id", "" + this.course_id);
        intent.putExtra("type", "" + this.type);
        intent.putExtra("have_chapter", "" + this.have_chapter);
        intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        intent.putExtra(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "" + this.cover);
        intent.putExtra("title", "" + title);
        intent.putExtra("is_open_qrcode", "" + this.is_open_qrcode);
        intent.putExtra("is_hide_teacher", "" + this.is_hide_teacher);
        if ("1".equals(this.is_open_qrcode + "")) {
            intent.putExtra("wechat_qrcode", "" + this.wechat_qrcode);
            intent.putExtra("wechat_tips", "" + this.wechat_tips);
            intent.putExtra("wechat_number", "" + this.wechat_number);
        }
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.set_meal_ids = getIntent().getStringArrayListExtra("set_meal_id");
        this.title = getIntent().getExtras().getString("title");
        this.cover = getIntent().getExtras().getString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
        this.type_course = getIntent().getExtras().getString("type_course");
        this.course_id = getIntent().getExtras().getString("course_id");
        this.course_id_p = getIntent().getExtras().getString("course_id_p");
        this.type = getIntent().getExtras().getString("type");
        this.activity_id = getIntent().getExtras().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
        this.have_chapter = getIntent().getExtras().getString("have_chapter");
        this.is_open_qrcode = getIntent().getExtras().getString("is_open_qrcode");
        this.is_hide_teacher = getIntent().getExtras().getString("is_hide_teacher");
        if ("1".equals(this.is_open_qrcode)) {
            this.wechat_qrcode = getIntent().getExtras().getString("wechat_qrcode");
            this.wechat_tips = getIntent().getExtras().getString("wechat_tips");
            this.wechat_number = getIntent().getExtras().getString("wechat_number");
        }
        this.view1 = findViewById(R.id.view1);
        this.titleContent = (TextView) findViewById(R.id.title);
        this.parentview = (RelativeLayout) findViewById(R.id.parentview);
        this.qrcodeimg = (ImageView) findViewById(R.id.qrcodeimg);
        this.downimg2 = (ImageView) findViewById(R.id.downimg2);
        this.qrshowimg = (TextView) findViewById(R.id.qrshowimg);
        if (this.title.contains("我的")) {
            this.titleContent.setText(this.title);
        } else {
            this.titleContent.setText("课程表");
        }
        ImageView imageView = (ImageView) findViewById(R.id.iconBack);
        this.iconBack = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.s2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12166c.lambda$init$0(view);
            }
        });
        this.downimg2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.t2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12173c.lambda$init$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        super.initwriteStatusBar();
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_bg_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
        onDestryView();
        new Intent().setAction(EventBusConstant.CLOSE_PIP);
        super.onDestroy();
    }

    public void onDestryView() {
        TextView textView;
        if (this.isDestroyTrue || (textView = this.qrshowimg) == null || textView.getAnimation() == null) {
            return;
        }
        this.qrshowimg.getAnimation().cancel();
        this.qrshowimg.clearAnimation();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            onDestryView();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BaseFragment curriculumScheduleCardFragment = (!"1".equals(this.type) || "1".equals(this.have_chapter)) ? new CurriculumScheduleCardFragment() : new CurriculumLiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString("course_id", "" + this.course_id);
        bundle.putString("course_id_p", this.course_id_p);
        bundle.putString("type_course", this.type_course);
        bundle.putString("type", "" + this.type);
        bundle.putString("have_chapter", "" + this.have_chapter);
        bundle.putString("is_hide_teacher", "" + this.is_hide_teacher);
        bundle.putStringArrayList("set_meal_id", this.set_meal_ids);
        bundle.putString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        bundle.putString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "" + this.cover);
        bundle.putString("title", "" + this.title);
        curriculumScheduleCardFragment.setArguments(bundle);
        loadRootFragment(R.id.schedulefid, curriculumScheduleCardFragment);
        setQrshowimg();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_curriculum_schedule_card);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setQrshowimg() {
        if (this.qrshowimg != null) {
            if (this.title.contains("我的")) {
                this.titleContent.setText(this.title);
                this.qrcodeimg.setVisibility(8);
                this.downimg2.setVisibility(8);
                this.qrshowimg.setVisibility(8);
                return;
            }
            if (!"1".equals(this.is_open_qrcode)) {
                this.qrcodeimg.setVisibility(8);
                this.qrshowimg.setVisibility(8);
                return;
            }
            this.qrcodeimg.setVisibility(0);
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Groupcommunication, false, this)) {
                this.qrshowimg.setVisibility(8);
            } else {
                this.qrshowimg.setVisibility(0);
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.qrshowimg, "translationY", -10.0f);
                objectAnimatorOfFloat.setDuration(1000L);
                objectAnimatorOfFloat.setRepeatMode(2);
                objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
                objectAnimatorOfFloat.setRepeatCount(-1);
                objectAnimatorOfFloat.start();
            }
            this.qrcodeimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.v2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12189c.lambda$setQrshowimg$3(view);
                }
            });
        }
    }
}
